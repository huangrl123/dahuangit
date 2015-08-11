Ext.define("Ext.sparkline.Box",{extend:"Ext.sparkline.Base",alias:"widget.sparklinebox",config:{raw:false,boxLineColor:"#000",boxFillColor:"#cdf",whiskerColor:"#000",outlierLineColor:"#333",outlierFillColor:"#fff",medianColor:"#f00",showOutliers:true,outlierIQR:1.5,spotRadius:1.5,target:null,targetColor:"#4a2",chartRangeMin:null,chartRangeMax:null,tipTpl:new Ext.XTemplate("{field:this.fields}: {value}",{fields:function(A){var B={lq:"Lower Quartile",med:"Median",uq:"Upper Quartile",lo:"Left Outlier",ro:"Right Outlier",lw:"Left Whisker",rw:"Right Whisker"};return B[A]}}),tooltipFormatFieldlistKey:"field"},quartile:function(C,B){var A;if(B===2){A=Math.floor(C.length/2);return C.length%2?C[A]:(C[A-1]+C[A])/2}else{if(C.length%2){A=(C.length*B+B)/4;return A%1?(C[Math.floor(A)]+C[Math.floor(A)-1])/2:C[A-1]}else{A=(C.length*B+2)/4;return A%1?(C[Math.floor(A)]+C[Math.floor(A)-1])/2:C[A-1]}}},applyValues:function(A){A=Ext.Array.map(Ext.Array.from(A),Number);this.disabled=!(A&&A.length);return A},getRegion:function(){return 1},getRegionFields:function(){var A=[{field:"lq",value:this.quartiles[0]},{field:"med",value:this.quartiles[1]},{field:"uq",value:this.quartiles[2]}];if(this.loutlier!==undefined){A.push({field:"lo",value:this.loutlier})}if(this.routlier!==undefined){A.push({field:"ro",value:this.routlier})}if(this.lwhisker!==undefined){A.push({field:"lw",value:this.lwhisker})}if(this.rwhisker!==undefined){A.push({field:"rw",value:this.rwhisker})}return A},renderHighlight:Ext.emptyFn,renderGraph:function(){var M=this,B=M.canvas,U=M.values,b=U.length,I=M.getWidth(),V=M.getHeight(),E=M.getChartRangeMin(),J=M.getChartRangeMax(),L=E==null?Math.min.apply(Math,U):E,D=J==null?Math.max.apply(Math,U):J,A=0,X,H,F,d,Z,T,Y,G,K,N,W,C=M.getSpotRadius(),P=M.getOutlierLineColor(),a=M.getOutlierFillColor(),S=M.getShowOutliers(),O=M.getOutlierIQR(),c=M.getLineColor(),Q=M.getWhiskerColor(),R=M.getTargetColor();if(!M.callParent()){return}if(M.raw){if(S&&U.length>5){H=U[0];X=U[1];d=U[2];Z=U[3];T=U[4];Y=U[5];G=U[6]}else{X=U[0];d=U[1];Z=U[2];T=U[3];Y=U[4]}}else{U.sort(function(f,e){return f-e});d=M.quartile(U,1);Z=M.quartile(U,2);T=M.quartile(U,3);F=T-d;if(S){X=Y=null;for(K=0;K<b;K++){if(X==null&&U[K]>d-(F*O)){X=U[K]}if(U[K]<T+(F*O)){Y=U[K]}}H=U[0];G=U[b-1]}else{X=U[0];Y=U[b-1]}}M.quartiles=[d,Z,T];M.lwhisker=X;M.rwhisker=Y;M.loutlier=H;M.routlier=G;W=I/(D-L+1);if(S){A=Math.ceil(C);I-=2*Math.ceil(C);W=I/(D-L+1);if(H<X){B.drawCircle((H-L)*W+A,V/2,C,P,a).append()}if(G>Y){B.drawCircle((G-L)*W+A,V/2,C,P,a).append()}}B.drawRect(Math.round((d-L)*W+A),Math.round(V*0.1),Math.round((T-d)*W),Math.round(V*0.8),M.getBoxLineColor(),M.getBoxFillColor()).append();B.drawLine(Math.round((X-L)*W+A),Math.round(V/2),Math.round((d-L)*W+A),Math.round(V/2),c).append();B.drawLine(Math.round((X-L)*W+A),Math.round(V/4),Math.round((X-L)*W+A),Math.round(V-V/4),Q).append();B.drawLine(Math.round((Y-L)*W+A),Math.round(V/2),Math.round((T-L)*W+A),Math.round(V/2),c).append();B.drawLine(Math.round((Y-L)*W+A),Math.round(V/4),Math.round((Y-L)*W+A),Math.round(V-V/4),Q).append();B.drawLine(Math.round((Z-L)*W+A),Math.round(V*0.1),Math.round((Z-L)*W+A),Math.round(V*0.9),M.getMedianColor()).append();if(M.target){N=Math.ceil(M.spotRadius);B.drawLine(Math.round((M.target-L)*W+A),Math.round((V/2)-N),Math.round((M.target-L)*W+A),Math.round((V/2)+N),R).append();B.drawLine(Math.round((M.target-L)*W+A-N),Math.round(V/2),Math.round((M.target-L)*W+A+N),Math.round(V/2),R).append()}if(M.currentPageXY&&M.el.getRegion().contains(M.currentPageXY)){M.currentRegion=null;M.updateDisplay()}B.render()}});
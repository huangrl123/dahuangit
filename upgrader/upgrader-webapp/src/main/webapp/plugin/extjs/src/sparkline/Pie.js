Ext.define("Ext.sparkline.Pie",{extend:"Ext.sparkline.Base",alias:"widget.sparklinepie",config:{offset:0,sliceColors:["#3366cc","#dc3912","#ff9900","#109618","#66aa00","#dd4477","#0099c6","#990099"],borderWidth:0,borderColor:"#000",tipTpl:new Ext.XTemplate('&#9679; {value} ({percent:number("0.0")}%)')},applyValues:function(A){A=Ext.Array.map(Ext.Array.from(A),Number);this.disabled=!(A&&A.length);return A},onUpdate:function(){var C=this,D=C.values,B=0,A;C.callParent(arguments);C.shapes={};C.valueShapes={};if(D.length>0){for(A=D.length;A--;){B+=D[A]}}C.total=B;C.radius=Math.floor(Math.min(C.getWidth(),C.getHeight())/2)},getRegion:function(B,C){var A=this.canvas.getShapeAt(B,C);return(A!=null&&this.shapes[A]!=null)?this.shapes[A]:null},getRegionFields:function(B){var A=this.getSliceColors();return{isNull:this.values[B]==null,value:this.values[B],percent:this.values[B]/this.total*100,color:A[B%A.length],offset:B}},renderHighlight:function(A){this.renderSlice(A,true).append()},renderSlice:function(P,M){var F=this,A=F.canvas,E=F.radius,G=F.getBorderWidth(),J=F.getOffset(),C=2*Math.PI,L=F.values,O=F.total,K=J?(2*Math.PI)*(J/360):0,Q,H,D,N,B,I=this.getSliceColors();N=L.length;for(D=0;D<N;D++){Q=K;H=K;if(O>0){H=K+(C*(L[D]/O))}if(P===D){B=I[D%I.length];if(M){B=F.calcHighlightColor(B)}return A.drawPieSlice(E,E,E-G,Q,H,null,B)}K=H}},renderGraph:function(){var A=this,B=A.canvas,H=A.values,G=A.radius,D=A.getBorderWidth(),C,E,I=A.shapes||(A.shapes={}),F=A.valueShapes||(A.valueShapes={});if(!A.callParent()){return}if(D){B.drawCircle(G,G,Math.floor(G-(D/2)),A.getBorderColor(),null,D).append()}for(E=H.length;E--;){if(H[E]){C=A.renderSlice(E).append();F[E]=C.id;I[C.id]=E}}if(A.currentPageXY&&A.el.getRegion().contains(A.currentPageXY)){A.currentRegion=null;A.updateDisplay()}B.render()}});
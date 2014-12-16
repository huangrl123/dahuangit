Ext.define("Ext.chart.Legend",{requires:["Ext.chart.LegendItem"],visible:true,update:true,position:"bottom",x:0,y:0,labelColor:"#000",labelFont:"12px Helvetica, sans-serif",boxStroke:"#000",boxStrokeWidth:1,boxFill:"#FFF",itemSpacing:10,padding:5,width:0,height:0,boxZIndex:100,constructor:function(A){var B=this;if(A){Ext.apply(B,A)}B.items=[];B.isVertical=("left|right|float".indexOf(B.position)!==-1);B.origX=B.x;B.origY=B.y},create:function(){var C=this,D=C.chart.series.items,A,E,B;C.createBox();if(C.rebuild!==false){C.createItems()}if(!C.created&&C.isDisplayed()){C.created=true;for(A=0,E=D.length;A<E;A++){B=D[A];B.on("titlechange",C.redraw,C)}}},init:Ext.emptyFn,redraw:function(){var A=this;A.create();A.updatePosition()},isDisplayed:function(){return this.visible&&this.chart.series.findIndex("showInLegend",true)!==-1},createItems:function(){var F=this,C=F.chart.series.items,H=F.items,I,D,A,B,E,G,J;F.removeItems();for(D=0,A=C.length;D<A;D++){G=C[D];if(G.showInLegend){I=[].concat(G.yField);for(B=0,E=I.length;B<E;B++){J=F.createLegendItem(G,B);H.push(J)}}}F.alignItems()},removeItems:function(){var B=this,D=B.items,C=D?D.length:0,A;if(C){for(A=0;A<C;A++){D[A].destroy()}}D.length=[]},alignItems:function(){var H=this,B=H.padding,C=H.isVertical,A=Math.floor,E,G,I,F,D;E=H.updateItemDimensions();G=E.maxWidth;I=E.maxHeight;F=E.totalWidth;D=E.totalHeight;H.width=A((C?G:F)+B*2);H.height=A((C?D:I)+B*2)},updateItemDimensions:function(){var E=this,M=E.items,K=E.padding,N=E.itemSpacing,G=0,H=0,R=0,A=0,J=E.isVertical,O=Math.floor,P=Math.max,C=0,D,F,B,I,L,Q;for(D=0,F=M.length;D<F;D++){B=M[D];I=B.getBBox();L=I.width;Q=I.height;C=(D===0?0:N);B.x=K+O(J?0:R+C);B.y=K+O(J?A+C:0)+Q/2;R+=C+L;A+=C+Q;G=P(G,L);H=P(H,Q)}return{totalWidth:R,totalHeight:A,maxWidth:G,maxHeight:H}},createLegendItem:function(C,A){var B=this;return new Ext.chart.LegendItem({legend:B,series:C,surface:B.chart.surface,yFieldIndex:A})},getBBox:function(){var A=this;return{x:Math.round(A.x)-A.boxStrokeWidth/2,y:Math.round(A.y)-A.boxStrokeWidth/2,width:A.width+A.boxStrokeWidth,height:A.height+A.boxStrokeWidth}},createBox:function(){var B=this,A,C;if(B.boxSprite){B.boxSprite.destroy()}C=B.getBBox();if(isNaN(C.width)||isNaN(C.height)){B.boxSprite=false;return}A=B.boxSprite=B.chart.surface.add(Ext.apply({type:"rect",stroke:B.boxStroke,"stroke-width":B.boxStrokeWidth,fill:B.boxFill,zIndex:B.boxZIndex},C));A.redraw()},calcPosition:function(){var K=this,L,M,N=K.width,I=K.height,D=K.chart,F=D.chartBBox,B=D.insetPadding,J=F.width-(B*2),C=F.height-(B*2),G=F.x+B,H=F.y+B,E=D.surface,A=Math.floor;switch(K.position){case"left":L=B;M=A(H+C/2-I/2);break;case"right":L=A(E.width-N)-B;M=A(H+C/2-I/2);break;case"top":L=A(G+J/2-N/2);M=B;break;case"bottom":L=A(G+J/2-N/2);M=A(E.height-I)-B;break;default:L=A(K.origX)+B;M=A(K.origY)+B}return{x:L,y:M}},updatePosition:function(){var C=this,E=C.items,B,A,D,F;if(C.isDisplayed()){B=C.calcPosition();C.x=B.x;C.y=B.y;for(A=0,D=E.length;A<D;A++){E[A].updatePosition()}F=C.getBBox();if(isNaN(F.width)||isNaN(F.height)){if(C.boxSprite){C.boxSprite.hide(true)}}else{if(!C.boxSprite){C.createBox()}C.boxSprite.setAttributes(F,true);C.boxSprite.show(true)}}},toggle:function(B){var C=this,A=0,E=C.items,D=E.length;if(C.boxSprite){if(B){C.boxSprite.show(true)}else{C.boxSprite.hide(true)}}for(;A<D;++A){if(B){E[A].show(true)}else{E[A].hide(true)}}C.visible=B}});
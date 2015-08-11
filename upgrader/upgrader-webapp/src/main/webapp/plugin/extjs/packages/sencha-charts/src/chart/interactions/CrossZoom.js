Ext.define("Ext.chart.interactions.CrossZoom",{extend:"Ext.chart.interactions.Abstract",type:"crosszoom",alias:"interaction.crosszoom",config:{axes:true,gestures:{dragstart:"onGestureStart",drag:"onGesture",dragend:"onGestureEnd",dblclick:"onDoubleTap"},undoButton:{}},stopAnimationBeforeSync:false,zoomAnimationInProgress:false,constructor:function(){this.callParent(arguments);this.zoomHistory=[]},applyAxes:function(B){var A={};if(B===true){return{top:{},right:{},bottom:{},left:{}}}else{if(Ext.isArray(B)){A={};Ext.each(B,function(C){A[C]={}})}else{if(Ext.isObject(B)){Ext.iterate(B,function(C,D){if(D===true){A[C]={}}else{if(D!==false){A[C]=D}}})}}}return A},applyUndoButton:function(B,A){var C=this;if(B){if(A){A.destroy()}return Ext.create("Ext.Button",Ext.apply({cls:[],text:"Undo Zoom",disabled:true,handler:function(){C.undoZoom()}},B))}else{if(A){A.destroy()}}},getSurface:function(){return this.getChart()&&this.getChart().getSurface("main")},setSeriesOpacity:function(A){var B=this.getChart()&&this.getChart().getSurface("series");if(B){B.element.setStyle("opacity",A)}},onGestureStart:function(A){var H=this,F=H.getChart(),E=H.getSurface(),B=F.getInnerRect(),C=B[2],G=B[3],D=F.getEventXY(A),I=D[0],J=D[1];if(H.zoomAnimationInProgress){return}if(I>0&&I<C&&J>0&&J<G){H.gestureEvent="drag";H.lockEvents(H.gestureEvent);H.startX=I;H.startY=J;H.selectionRect=E.add({type:"rect",globalAlpha:0.5,fillStyle:"rgba(80,80,140,0.5)",strokeStyle:"rgba(80,80,140,1)",lineWidth:2,x:I,y:J,width:0,height:0,zIndex:10000});H.setSeriesOpacity(0.8);return false}},onGesture:function(A){var H=this;if(H.zoomAnimationInProgress){return}if(H.getLocks()[H.gestureEvent]===H){var F=H.getChart(),E=H.getSurface(),B=F.getInnerRect(),C=B[2],G=B[3],D=F.getEventXY(A),I=D[0],J=D[1];if(I<0){I=0}else{if(I>C){I=C}}if(J<0){J=0}else{if(J>G){J=G}}H.selectionRect.setAttributes({width:I-H.startX,height:J-H.startY});if(Math.abs(H.startX-I)<11||Math.abs(H.startY-J)<11){H.selectionRect.setAttributes({globalAlpha:0.5})}else{H.selectionRect.setAttributes({globalAlpha:1})}E.renderFrame();return false}},onGestureEnd:function(A){var H=this;if(H.zoomAnimationInProgress){return}if(H.getLocks()[H.gestureEvent]===H){var F=H.getChart(),E=H.getSurface(),B=F.getInnerRect(),C=B[2],G=B[3],D=F.getEventXY(A),I=D[0],J=D[1];if(I<0){I=0}else{if(I>C){I=C}}if(J<0){J=0}else{if(J>G){J=G}}if(Math.abs(H.startX-I)<11||Math.abs(H.startY-J)<11){E.remove(H.selectionRect)}else{H.zoomBy([Math.min(H.startX,I)/C,1-Math.max(H.startY,J)/G,Math.max(H.startX,I)/C,1-Math.min(H.startY,J)/G]);H.selectionRect.setAttributes({x:Math.min(H.startX,I),y:Math.min(H.startY,J),width:Math.abs(H.startX-I),height:Math.abs(H.startY-J)});H.selectionRect.fx.setConfig(F.getAnimation()||{duration:0});H.selectionRect.setAttributes({globalAlpha:0,x:0,y:0,width:C,height:G});H.zoomAnimationInProgress=true;F.suspendThicknessChanged();H.selectionRect.fx.on("animationend",function(){F.resumeThicknessChanged();E.remove(H.selectionRect);H.selectionRect=null;H.zoomAnimationInProgress=false})}E.renderFrame();H.sync();H.unlockEvents(H.gestureEvent);H.setSeriesOpacity(1);if(!H.zoomAnimationInProgress){E.remove(H.selectionRect);H.selectionRect=null}}},zoomBy:function(D){var J=this,A=J.getAxes(),E=J.getChart(),H=E.getAxes(),F=E.getInherited().rtl,B,L={},I,G;if(F){D=D.slice();I=1-D[0];G=1-D[2];D[0]=Math.min(I,G);D[2]=Math.max(I,G)}for(var K=0;K<H.length;K++){var C=H[K];B=A[C.getPosition()];if(B&&B.allowZoom!==false){var M=C.isSide(),N=C.getVisibleRange();L[C.getId()]=N.slice(0);if(!M){C.setVisibleRange([(N[1]-N[0])*D[0]+N[0],(N[1]-N[0])*D[2]+N[0]])}else{C.setVisibleRange([(N[1]-N[0])*D[1]+N[0],(N[1]-N[0])*D[3]+N[0]])}}}J.zoomHistory.push(L);J.getUndoButton().setDisabled(false)},undoZoom:function(){var C=this.zoomHistory.pop(),A=this.getChart().getAxes();if(C){for(var B=0;B<A.length;B++){var D=A[B];if(C[D.getId()]){D.setVisibleRange(C[D.getId()])}}}this.getUndoButton().setDisabled(this.zoomHistory.length===0);this.sync()},onDoubleTap:function(A){this.undoZoom()}});
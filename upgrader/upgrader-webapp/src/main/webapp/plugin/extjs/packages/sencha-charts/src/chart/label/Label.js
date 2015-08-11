Ext.define("Ext.chart.label.Label",{extend:"Ext.draw.sprite.Text",requires:["Ext.chart.label.Callout"],inheritableStatics:{def:{processors:{callout:"limited01",calloutHasLine:"bool",calloutPlaceX:"number",calloutPlaceY:"number",calloutStartX:"number",calloutStartY:"number",calloutEndX:"number",calloutEndY:"number",calloutColor:"color",calloutWidth:"number",calloutVertical:"bool",labelOverflowPadding:"number",display:"enums(none,under,over,rotate,insideStart,insideEnd,inside,outside)",orientation:"enums(horizontal,vertical)",renderer:"default"},defaults:{callout:0,calloutHasLine:true,calloutPlaceX:0,calloutPlaceY:0,calloutStartX:0,calloutStartY:0,calloutEndX:0,calloutEndY:0,calloutWidth:1,calloutVertical:false,calloutColor:"black",labelOverflowPadding:5,display:"none",orientation:"",renderer:null},dirtyTriggers:{callout:"transform",calloutPlaceX:"transform",calloutPlaceY:"transform",labelOverflowPadding:"transform",calloutRotation:"transform",display:"hidden"},updaters:{hidden:function(A){A.hidden=A.display==="none"}}}},config:{fx:{customDuration:{callout:200}},field:null,calloutLine:true},applyCalloutLine:function(A){if(A){return Ext.apply({},A)}},prepareModifiers:function(){this.callParent(arguments);this.calloutModifier=new Ext.chart.label.Callout({sprite:this});this.fx.setNext(this.calloutModifier);this.calloutModifier.setNext(this.topModifier)},render:function(D,C){var B=this,A=B.attr;C.save();C.globalAlpha*=A.callout;if(C.globalAlpha>0&&A.calloutHasLine){C.strokeStyle=A.calloutColor;C.fillStyle=A.calloutColor;C.lineWidth=A.calloutWidth;C.beginPath();C.moveTo(B.attr.calloutStartX,B.attr.calloutStartY);C.lineTo(B.attr.calloutEndX,B.attr.calloutEndY);C.stroke();C.beginPath();C.arc(B.attr.calloutStartX,B.attr.calloutStartY,1*A.calloutWidth,0,2*Math.PI,true);C.fill();C.beginPath();C.arc(B.attr.calloutEndX,B.attr.calloutEndY,1*A.calloutWidth,0,2*Math.PI,true);C.fill()}C.restore();Ext.draw.sprite.Text.prototype.render.apply(B,arguments)}});
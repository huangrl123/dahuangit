Ext.define("Ext.draw.sprite.Line",{extend:"Ext.draw.sprite.Sprite",alias:"sprite.line",type:"line",inheritableStatics:{def:{processors:{fromX:"number",fromY:"number",toX:"number",toY:"number"},defaults:{fromX:0,fromY:0,toX:1,toY:1}}},render:function(D,C){var B=this.attr,A=this.attr.matrix;A.toContext(C);C.beginPath();C.moveTo(B.fromX,B.fromY);C.lineTo(B.toX,B.toY);C.stroke()}});
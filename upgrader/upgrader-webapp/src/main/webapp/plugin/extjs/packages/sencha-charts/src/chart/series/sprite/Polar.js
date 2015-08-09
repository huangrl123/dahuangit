Ext.define("Ext.chart.series.sprite.Polar",{mixins:{markerHolder:"Ext.chart.MarkerHolder"},extend:"Ext.draw.sprite.Sprite",inheritableStatics:{def:{processors:{dataMinX:"number",dataMaxX:"number",dataMinY:"number",dataMaxY:"number",rangeX:"data",rangeY:"data",dataY:"data",dataX:"data",centerX:"number",centerY:"number",startAngle:"number",endAngle:"number",startRho:"number",endRho:"number",baseRotation:"number",labels:"default",labelOverflowPadding:"number"},defaults:{dataY:null,dataX:null,dataMinX:0,dataMaxX:1,dataMinY:0,dataMaxY:1,centerX:0,centerY:0,startAngle:0,endAngle:Math.PI,startRho:0,endRho:150,baseRotation:0,labels:null,labelOverflowPadding:10},dirtyTriggers:{dataX:"bbox",dataY:"bbox",dataMinX:"bbox",dataMaxX:"bbox",dataMinY:"bbox",dataMaxY:"bbox",centerX:"bbox",centerY:"bbox",startAngle:"bbox",endAngle:"bbox",startRho:"bbox",endRho:"bbox",baseRotation:"bbox"}}},config:{store:null,field:null},updatePlainBBox:function(A){var B=this.attr;A.x=B.centerX-B.endRho;A.y=B.centerY+B.endRho;A.width=B.endRho*2;A.height=B.endRho*2}});
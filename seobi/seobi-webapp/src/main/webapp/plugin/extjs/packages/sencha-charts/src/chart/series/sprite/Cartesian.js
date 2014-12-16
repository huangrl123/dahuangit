Ext.define("Ext.chart.series.sprite.Cartesian",{extend:"Ext.draw.sprite.Sprite",mixins:{markerHolder:"Ext.chart.MarkerHolder"},inheritableStatics:{def:{processors:{dataMinX:"number",dataMaxX:"number",dataMinY:"number",dataMaxY:"number",rangeX:"data",rangeY:"data",dataY:"data",dataX:"data",labels:"default",labelOverflowPadding:"number",selectionTolerance:"number",flipXY:"bool",renderer:"default",visibleMinX:"number",visibleMinY:"number",visibleMaxX:"number",visibleMaxY:"number",innerWidth:"number",innerHeight:"number"},defaults:{dataY:null,dataX:null,dataMinX:0,dataMaxX:1,dataMinY:0,dataMaxY:1,labels:null,labelOverflowPadding:10,selectionTolerance:20,flipXY:false,renderer:null,transformFillStroke:false,visibleMinX:0,visibleMinY:0,visibleMaxX:1,visibleMaxY:1,innerWidth:1,innerHeight:1},dirtyTriggers:{dataX:"dataX,bbox",dataY:"dataY,bbox",dataMinX:"bbox",dataMaxX:"bbox",dataMinY:"bbox",dataMaxY:"bbox",visibleMinX:"panzoom",visibleMinY:"panzoom",visibleMaxX:"panzoom",visibleMaxY:"panzoom",innerWidth:"panzoom",innerHeight:"panzoom"},updaters:{dataX:function(A){this.processDataX();if(!A.dirtyFlags.dataY){A.dirtyFlags.dataY=[]}A.dirtyFlags.dataY.push("dataY")},dataY:function(){this.processDataY()},panzoom:function(D){var C=D.visibleMaxX-D.visibleMinX,F=D.visibleMaxY-D.visibleMinY,B=D.flipXY?D.innerHeight:D.innerWidth,A=!D.flipXY?D.innerHeight:D.innerWidth,G=this.getSurface(),E=G?G.getInherited().rtl:false;if(E&&!D.flipXY){D.translationX=B+D.visibleMinX*B/C}else{D.translationX=-D.visibleMinX*B/C}D.translationY=-D.visibleMinY*A/F;D.scalingX=(E&&!D.flipXY?-1:1)*B/C;D.scalingY=A/F;D.scalingCenterX=0;D.scalingCenterY=0;this.applyTransformations(true)}}}},config:{store:null,field:null},processDataY:Ext.emptyFn,processDataX:Ext.emptyFn,updatePlainBBox:function(A){var B=this.attr;A.x=B.dataMinX;A.y=B.dataMinY;A.width=B.dataMaxX-B.dataMinX;A.height=B.dataMaxY-B.dataMinY},binarySearch:function(A){var B=this.attr.dataX,F=0,D=B.length;if(A<=B[0]){return F}if(A>=B[D-1]){return D-1}while(F+1<D){var C=(F+D)>>1,E=B[C];if(E===A){return C}else{if(E<A){F=C}else{D=C}}}return F},render:function(G,E,B){var C=this,F=C.attr,A=F.inverseMatrix.clone();A.appendMatrix(G.inverseMatrix);if(F.dataX===null||F.dataX===undefined){return}if(F.dataY===null||F.dataY===undefined){return}if(A.getXX()*A.getYX()||A.getXY()*A.getYY()){console.log("Cartesian Series sprite does not support rotation/sheering");return}var D=A.transformList([[B[0]-1,B[3]+1],[B[0]+B[2]+1,-1]]);D=D[0].concat(D[1]);C.renderClipped(G,E,D,B)},renderClipped:Ext.emptyFn,getIndexNearPoint:function(S,T){var I=this,O=I.attr.matrix,V=I.attr.dataX,A=I.attr.dataY,K=I.attr.selectionTolerance,C,N,F=-1,E=O.clone().prependMatrix(I.surfaceMatrix).inverse(),U=E.transformPoint([S,T]),P=E.transformPoint([S-K,T-K]),M=E.transformPoint([S+K,T+K]),R=Math.min(P[0],M[0]),H=Math.max(P[0],M[0]),Q=Math.min(P[1],M[1]),L=Math.max(P[1],M[1]),D,J,G,B;for(G=0,B=V.length;G<B;G++){D=V[G];J=A[G];if(D>R&&D<H&&J>Q&&J<L){if(F===-1||(Math.abs(D-U[0])<C)&&(Math.abs(J-U[1])<N)){C=Math.abs(D-U[0]);N=Math.abs(J-U[1]);F=G}}}return F}});
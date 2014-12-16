Ext.define("Ext.fx.State",{isAnimatable:{"background-color":true,"background-image":true,"background-position":true,"border-bottom-color":true,"border-bottom-width":true,"border-color":true,"border-left-color":true,"border-left-width":true,"border-right-color":true,"border-right-width":true,"border-spacing":true,"border-top-color":true,"border-top-width":true,"border-width":true,"bottom":true,"color":true,"crop":true,"font-size":true,"font-weight":true,"height":true,"left":true,"letter-spacing":true,"line-height":true,"margin-bottom":true,"margin-left":true,"margin-right":true,"margin-top":true,"max-height":true,"max-width":true,"min-height":true,"min-width":true,"opacity":true,"outline-color":true,"outline-offset":true,"outline-width":true,"padding-bottom":true,"padding-left":true,"padding-right":true,"padding-top":true,"right":true,"text-indent":true,"text-shadow":true,"top":true,"vertical-align":true,"visibility":true,"width":true,"word-spacing":true,"z-index":true,"zoom":true,"transform":true},constructor:function(A){this.data={};this.set(A)},setConfig:function(A){this.set(A);return this},setRaw:function(A){this.data=A;return this},clear:function(){return this.setRaw({})},setTransform:function(C,E){var G=this.data,A=Ext.isArray(E),D=G.transform,F,B;if(!D){D=G.transform={translateX:0,translateY:0,translateZ:0,scaleX:1,scaleY:1,scaleZ:1,rotate:0,rotateX:0,rotateY:0,rotateZ:0,skewX:0,skewY:0}}if(typeof C=="string"){switch(C){case"translate":if(A){F=E.length;if(F==0){break}D.translateX=E[0];if(F==1){break}D.translateY=E[1];if(F==2){break}D.translateZ=E[2]}else{D.translateX=E}break;case"rotate":if(A){F=E.length;if(F==0){break}D.rotateX=E[0];if(F==1){break}D.rotateY=E[1];if(F==2){break}D.rotateZ=E[2]}else{D.rotate=E}break;case"scale":if(A){F=E.length;if(F==0){break}D.scaleX=E[0];if(F==1){break}D.scaleY=E[1];if(F==2){break}D.scaleZ=E[2]}else{D.scaleX=E;D.scaleY=E}break;case"skew":if(A){F=E.length;if(F==0){break}D.skewX=E[0];if(F==1){break}D.skewY=E[1]}else{D.skewX=E}break;default:D[C]=E}}else{for(B in C){if(C.hasOwnProperty(B)){E=C[B];this.setTransform(B,E)}}}},set:function(B,C){var D=this.data,A;if(typeof B!="string"){for(A in B){C=B[A];if(A==="transform"){this.setTransform(C)}else{D[A]=C}}}else{if(B==="transform"){this.setTransform(C)}else{D[B]=C}}return this},unset:function(A){var B=this.data;if(B.hasOwnProperty(A)){delete B[A]}return this},getData:function(){return this.data}});
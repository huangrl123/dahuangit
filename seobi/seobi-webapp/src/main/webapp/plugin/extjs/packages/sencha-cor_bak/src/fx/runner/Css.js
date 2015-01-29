Ext.define("Ext.fx.runner.Css",{extend:"Ext.Evented",requires:["Ext.fx.Animation"],prefixedProperties:{"transform":true,"transform-origin":true,"perspective":true,"transform-style":true,"transition":true,"transition-property":true,"transition-duration":true,"transition-timing-function":true,"transition-delay":true,"animation":true,"animation-name":true,"animation-duration":true,"animation-iteration-count":true,"animation-direction":true,"animation-timing-function":true,"animation-delay":true},lengthProperties:{"top":true,"right":true,"bottom":true,"left":true,"width":true,"height":true,"max-height":true,"max-width":true,"min-height":true,"min-width":true,"margin-bottom":true,"margin-left":true,"margin-right":true,"margin-top":true,"padding-bottom":true,"padding-left":true,"padding-right":true,"padding-top":true,"border-bottom-width":true,"border-left-width":true,"border-right-width":true,"border-spacing":true,"border-top-width":true,"border-width":true,"outline-width":true,"letter-spacing":true,"line-height":true,"text-indent":true,"word-spacing":true,"font-size":true,"translate":true,"translateX":true,"translateY":true,"translateZ":true,"translate3d":true},durationProperties:{"transition-duration":true,"transition-delay":true,"animation-duration":true,"animation-delay":true},angleProperties:{rotate:true,rotateX:true,rotateY:true,rotateZ:true,skew:true,skewX:true,skewY:true},lengthUnitRegex:/([a-z%]*)$/,DEFAULT_UNIT_LENGTH:"px",DEFAULT_UNIT_ANGLE:"deg",DEFAULT_UNIT_DURATION:"ms",formattedNameCache:{},constructor:function(){var A=Ext.feature.has.Css3dTransforms;if(A){this.transformMethods=["translateX","translateY","translateZ","rotate","rotateX","rotateY","rotateZ","skewX","skewY","scaleX","scaleY","scaleZ"]}else{this.transformMethods=["translateX","translateY","rotate","skewX","skewY","scaleX","scaleY"]}this.vendorPrefix=Ext.browser.getStyleDashPrefix();this.ruleStylesCache={};return this},getStyleSheet:function(){var A=this.styleSheet,C,B;if(!A){C=document.createElement("style");C.type="text/css";(document.head||document.getElementsByTagName("head")[0]).appendChild(C);B=document.styleSheets;this.styleSheet=A=B[B.length-1]}return A},applyRules:function(C){var F=this.getStyleSheet(),K=this.ruleStylesCache,D=F.cssRules,I,H,A,G,B,J,E;for(I in C){H=C[I];A=K[I];if(A===undefined){B=D.length;F.insertRule(I+"{}",B);A=K[I]=D.item(B).style}G=A.$cache;if(!G){G=A.$cache={}}for(J in H){E=this.formatValue(H[J],J);J=this.formatName(J);if(G[J]!==E){G[J]=E;if(E===null){A.removeProperty(J)}else{A.setProperty(J,E,"important")}}}}return this},applyStyles:function(D){var G,F,A,B,C,E;for(G in D){if(D.hasOwnProperty(G)){F=document.getElementById(G);if(!F){return this}A=F.style;B=D[G];for(C in B){if(B.hasOwnProperty(C)){E=this.formatValue(B[C],C);C=this.formatName(C);if(E===null){A.removeProperty(C)}else{A.setProperty(C,E,"important")}}}}}return this},formatName:function(C){var A=this.formattedNameCache,B=A[C];if(!B){if((Ext.os.is.Tizen||!Ext.feature.has.CssTransformNoPrefix)&&this.prefixedProperties[C]){B=this.vendorPrefix+C}else{B=C}A[C]=B}return B},formatValue:function(F,C){var J=typeof F,A=this.DEFAULT_UNIT_LENGTH,B,I,G,D,E,H,K;if(F===null){return""}if(J=="string"){if(this.lengthProperties[C]){K=F.match(this.lengthUnitRegex)[1];if(K.length>0){if(K!==A){Ext.Logger.error("Length unit: '"+K+"' in value: '"+F+"' of property: '"+C+"' is not valid for animation. Only 'px' is allowed")}}else{return F+A}}return F}else{if(J=="number"){if(F==0){return"0"}if(this.lengthProperties[C]){return F+A}if(this.angleProperties[C]){return F+this.DEFAULT_UNIT_ANGLE}if(this.durationProperties[C]){return F+this.DEFAULT_UNIT_DURATION}}else{if(C==="transform"){B=this.transformMethods;E=[];for(G=0,D=B.length;G<D;G++){I=B[G];E.push(I+"("+this.formatValue(F[I],I)+")")}return E.join(" ")}else{if(Ext.isArray(F)){H=[];for(G=0,D=F.length;G<D;G++){H.push(this.formatValue(F[G],C))}return(H.length>0)?H.join(", "):"none"}}}}return F}});
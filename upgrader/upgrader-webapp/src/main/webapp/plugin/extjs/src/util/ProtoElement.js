Ext.define("Ext.util.ProtoElement",function(){var B=Ext.String.splitWords,A=Ext.Array.toMap;return{isProtoEl:true,clsProp:"cls",styleProp:"style",removedProp:"removed",styleIsText:false,constructor:function(C){var D=this,E,F;if(C){Ext.apply(D,C);E=D.cls;F=D.style;delete D.cls}D.classList=E?B(E):[];D.classMap=E?A(D.classList):{};if(F){if(typeof F==="string"){D.style=Ext.Element.parseStyles(F)}else{if(Ext.isFunction(F)){D.styleFn=F;delete D.style}else{D.style=Ext.apply({},F)}}}},flush:function(){this.flushClassList=[];this.removedClasses={};delete this.style;delete this.unselectableAttr},addCls:function(F){if(!F){return this}var H=this,D=(typeof F==="string")?B(F):F,E=D.length,C=H.classList,J=H.classMap,K=H.flushClassList,G=0,I;for(;G<E;++G){I=D[G];if(!J[I]){J[I]=true;C.push(I);if(K){K.push(I);delete H.removedClasses[I]}}}return H},hasCls:function(C){return C in this.classMap},removeCls:function(F){var J=this,E=J.classList,C=(J.classList=[]),H=A(B(F)),G=E.length,L=J.classMap,D=J.removedClasses,I,K;for(I=0;I<G;++I){K=E[I];if(H[K]){if(D){if(L[K]){D[K]=true;Ext.Array.remove(J.flushClassList,K)}}delete L[K]}else{C.push(K)}}return J},setStyle:function(D,E){var C=this,F=C.style||(C.style={});if(typeof D=="string"){if(arguments.length===1){C.setStyle(Ext.Element.parseStyles(D))}else{F[D]=E}}else{Ext.apply(F,D)}return C},unselectable:function(){this.addCls(Ext.dom.Element.unselectableCls);if(Ext.isOpera){this.unselectableAttr=true}},writeTo:function(D){var E=this,G=E.flushClassList||E.classList,C=E.removedClasses,F;if(E.styleFn){F=Ext.apply({},E.styleFn());Ext.apply(F,E.style)}else{F=E.style}D[E.clsProp]=G.join(" ");if(F){D[E.styleProp]=E.styleIsText?Ext.DomHelper.generateStyles(F,null,true):F}if(C){C=Ext.Object.getKeys(C);if(C.length){D[E.removedProp]=C.join(" ")}}if(E.unselectableAttr){D.unselectable="on"}return D}}});
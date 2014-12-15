Ext.define("Ext.draw.sprite.AttributeDefinition",{requires:["Ext.draw.sprite.AttributeParser","Ext.draw.sprite.AnimationParser"],config:{defaults:{},aliases:{},animationProcessors:{},processors:{},dirtyTriggers:{},updaters:{}},inheritableStatics:{processorRe:/^(\w+)\(([\w\-,]*)\)$/},constructor:function(A){var B=this;B.initConfig(A)},applyDefaults:function(B,A){A=Ext.apply(A||{},this.normalize(B));return A},applyAliases:function(A,B){return Ext.apply(B||{},A)},applyProcessors:function(A,G){this.getAnimationProcessors();var C,E=G||{},B=Ext.draw.sprite.AttributeParser,J=this.self.processorRe,F={},H,D,I;for(C in A){I=A[C];if(!Ext.isFunction(I)){if(Ext.isString(I)){D=I.match(J);if(D){I=B[D[1]].apply(B,D[2].split(","))}else{F[C]=I;H=true;I=B[I]}}else{continue}}E[C]=I}if(H){this.setAnimationProcessors(F)}return E},applyAnimationProcessors:function(E,B){var D=Ext.draw.sprite.AnimationParser,A;if(!B){B={}}for(var C in E){A=E[C];if(A==="none"){B[C]=null}else{if(Ext.isString(A)&&!(C in B)){if(A in D){while(Ext.isString(D[A])){A=D[A]}B[C]=D[A]}}else{if(Ext.isObject(A)){B[C]=A}}}}return B},applyDirtyTriggers:function(A,C){if(!C){C={}}for(var B in A){C[B]=A[B].split(",")}return C},applyUpdaters:function(B,A){return Ext.apply(A||{},B)},batchedNormalize:function(C,I){if(!C){return{}}var M=this,A=M.getProcessors(),F=M.getAliases(),B=C.translation||C.translate,E={},H,D,N,K,P,O,G,L,J;if("rotation" in C){P=C.rotation}else{P=("rotate" in C)?C.rotate:undefined}if("scaling" in C){O=C.scaling}else{O=("scale" in C)?C.scale:undefined}if(typeof O!=="undefined"){if(Ext.isNumber(O)){E.scalingX=O;E.scalingY=O}else{if("x" in O){E.scalingX=O.x}if("y" in O){E.scalingY=O.y}if("centerX" in O){E.scalingCenterX=O.centerX}if("centerY" in O){E.scalingCenterY=O.centerY}}}if(typeof P!=="undefined"){if(Ext.isNumber(P)){P=Ext.draw.Draw.rad(P);E.rotationRads=P}else{if("rads" in P){E.rotationRads=P.rads}else{if("degrees" in P){if(Ext.isArray(P.degrees)){E.rotationRads=Ext.Array.map(P.degrees,function(Q){return Ext.draw.Draw.rad(Q)})}else{E.rotationRads=Ext.draw.Draw.rad(P.degrees)}}}if("centerX" in P){E.rotationCenterX=P.centerX}if("centerY" in P){E.rotationCenterY=P.centerY}}}if(typeof B!=="undefined"){if("x" in B){E.translationX=B.x}if("y" in B){E.translationY=B.y}}if("matrix" in C){G=Ext.draw.Matrix.create(C.matrix);J=G.split();E.matrix=G;E.rotationRads=J.rotation;E.rotationCenterX=0;E.rotationCenterY=0;E.scalingX=J.scaleX;E.scalingY=J.scaleY;E.scalingCenterX=0;E.scalingCenterY=0;E.translationX=J.translateX;E.translationY=J.translateY}for(N in C){K=C[N];if(typeof K==="undefined"){continue}else{if(Ext.isArray(K)){if(N in F){N=F[N]}if(N in A){E[N]=[];for(H=0,D=K.length;H<D;H++){L=A[N].call(this,K[H]);if(typeof L!=="undefined"){E[N][H]=L}}}else{if(I){E[N]=K}}}else{if(N in F){N=F[N]}if(N in A){K=A[N].call(this,K);if(typeof K!=="undefined"){E[N]=K}}else{if(I){E[N]=K}}}}}return E},normalize:function(M,G){if(!M){return{}}var J=this,A=J.getProcessors(),E=J.getAliases(),B=M.translation||M.translate,D={},C,F,L,H,K,I;if("rotation" in M){L=M.rotation}else{L=("rotate" in M)?M.rotate:undefined}if("scaling" in M){H=M.scaling}else{H=("scale" in M)?M.scale:undefined}if(B){if("x" in B){D.translationX=B.x}if("y" in B){D.translationY=B.y}}if(typeof H!=="undefined"){if(Ext.isNumber(H)){D.scalingX=H;D.scalingY=H}else{if("x" in H){D.scalingX=H.x}if("y" in H){D.scalingY=H.y}if("centerX" in H){D.scalingCenterX=H.centerX}if("centerY" in H){D.scalingCenterY=H.centerY}}}if(typeof L!=="undefined"){if(Ext.isNumber(L)){L=Ext.draw.Draw.rad(L);D.rotationRads=L}else{if("rads" in L){D.rotationRads=L.rads}else{if("degrees" in L){D.rotationRads=Ext.draw.Draw.rad(L.degrees)}}if("centerX" in L){D.rotationCenterX=L.centerX}if("centerY" in L){D.rotationCenterY=L.centerY}}}if("matrix" in M){K=Ext.draw.Matrix.create(M.matrix);I=K.split();D.matrix=K;D.rotationRads=I.rotation;D.rotationCenterX=0;D.rotationCenterY=0;D.scalingX=I.scaleX;D.scalingY=I.scaleY;D.scalingCenterX=0;D.scalingCenterY=0;D.translationX=I.translateX;D.translationY=I.translateY}for(C in M){F=M[C];if(typeof F==="undefined"){continue}if(C in E){C=E[C]}if(C in A){F=A[C].call(this,F);if(typeof F!=="undefined"){D[C]=F}}else{if(G){D[C]=F}}}return D},setBypassingNormalization:function(C,A,B){return A.pushDown(C,B)},set:function(C,A,B){B=this.normalize(B);return this.setBypassingNormalization(C,A,B)}});
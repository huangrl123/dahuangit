Ext.define("Ext.util.translatable.Abstract",{extend:"Ext.Evented",requires:["Ext.fx.easing.Linear"],config:{useWrapper:null,easing:null,easingX:null,easingY:null},x:0,y:0,activeEasingX:null,activeEasingY:null,isAnimating:false,isTranslatable:true,constructor:function(A){this.mixins.observable.constructor.call(this,A);this.position={x:0,y:0}},factoryEasing:function(A){return Ext.factory(A,Ext.fx.easing.Linear,null,"easing")},applyEasing:function(A){if(!this.getEasingX()){this.setEasingX(this.factoryEasing(A))}if(!this.getEasingY()){this.setEasingY(this.factoryEasing(A))}},applyEasingX:function(A){return this.factoryEasing(A)},applyEasingY:function(A){return this.factoryEasing(A)},doTranslate:Ext.emptyFn,translate:function(B,C,A){if(A){return this.translateAnimated(B,C,A)}if(this.isAnimating){this.stopAnimation()}if(!isNaN(B)&&typeof B=="number"){this.x=B}if(!isNaN(C)&&typeof C=="number"){this.y=C}this.doTranslate(B,C)},translateAxis:function(E,C,A){var B,D;if(E=="x"){B=C}else{D=C}return this.translate(B,D,A)},getPosition:function(){var B=this,A=B.position;A.x=-B.x;A.y=-B.y;return A},animate:function(A,B){this.activeEasingX=A;this.activeEasingY=B;this.isAnimating=true;this.lastX=null;this.lastY=null;Ext.AnimationQueue.start(this.doAnimationFrame,this);this.fireEvent("animationstart",this,this.x,this.y);return this},translateAnimated:function(F,G,H){var E=this;if(!Ext.isObject(H)){H={}}if(E.isAnimating){E.stopAnimation()}E.callback=H.callback;E.callbackScope=H.scope;var A=Ext.Date.now(),D=H.easing,C=(typeof F=="number")?(H.easingX||D||E.getEasingX()||true):null,B=(typeof G=="number")?(H.easingY||D||E.getEasingY()||true):null;if(C){C=E.factoryEasing(C);C.setStartTime(A);C.setStartValue(E.x);C.setEndValue(F);if("duration" in H){C.setDuration(H.duration)}}if(B){B=E.factoryEasing(B);B.setStartTime(A);B.setStartValue(E.y);B.setEndValue(G);if("duration" in H){B.setDuration(H.duration)}}return E.animate(C,B)},doAnimationFrame:function(){var A=this,D=A.activeEasingX,E=A.activeEasingY,F=Date.now(),B,C;if(!A.isAnimating){return}A.lastRun=F;if(D===null&&E===null){A.stopAnimation();return}if(D!==null){A.x=B=Math.round(D.getValue());if(D.isEnded){A.activeEasingX=null;A.fireEvent("axisanimationend",A,"x",B)}}else{B=A.x}if(E!==null){A.y=C=Math.round(E.getValue());if(E.isEnded){A.activeEasingY=null;A.fireEvent("axisanimationend",A,"y",C)}}else{C=A.y}if(A.lastX!==B||A.lastY!==C){A.doTranslate(B,C);A.lastX=B;A.lastY=C}A.fireEvent("animationframe",A,B,C)},stopAnimation:function(){var A=this;if(!A.isAnimating){return}A.activeEasingX=null;A.activeEasingY=null;A.isAnimating=false;Ext.AnimationQueue.stop(A.doAnimationFrame,A);A.fireEvent("animationend",A,A.x,A.y);if(A.callback){A.callback.call(A.callbackScope);A.callback=null}},refresh:function(){this.translate(this.x,this.y)},destroy:function(){if(this.isAnimating){this.stopAnimation()}this.callParent(arguments)}});
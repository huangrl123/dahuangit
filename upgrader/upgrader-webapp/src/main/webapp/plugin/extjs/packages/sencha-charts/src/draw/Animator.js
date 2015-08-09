Ext.define("Ext.draw.Animator",{uses:["Ext.draw.Draw"],singleton:true,frameCallbacks:{},frameCallbackId:0,scheduled:0,frameStartTimeOffset:Ext.now(),animations:[],running:false,animationTime:function(){return Ext.AnimationQueue.frameStartTime-this.frameStartTimeOffset},add:function(A){if(!this.contains(A)){this.animations.push(A);Ext.draw.Animator.ignite();if("fireEvent" in A){A.fireEvent("animationstart",A)}}},remove:function(A){var D=this,E=D.animations,B=0,C=E.length;for(;B<C;++B){if(E[B]===A){E.splice(B,1);if("fireEvent" in A){A.fireEvent("animationend",A)}return}}},contains:function(A){return Ext.Array.indexOf(this.animations,A)>-1},empty:function(){return this.animations.length===0},step:function(D){var C=this,E=C.animations,A,B=0,F=E.length;for(;B<F;B++){A=E[B];A.step(D);if(!A.animating){E.splice(B,1);B--;F--;if(A.fireEvent){A.fireEvent("animationend")}}}},schedule:function(A,B){B=B||this;var C="frameCallback"+(this.frameCallbackId++);if(Ext.isString(A)){A=B[A]}Ext.draw.Animator.frameCallbacks[C]={fn:A,scope:B,once:true};this.scheduled++;Ext.draw.Animator.ignite();return C},cancel:function(A){if(Ext.draw.Animator.frameCallbacks[A]&&Ext.draw.Animator.frameCallbacks[A].once){this.scheduled--;delete Ext.draw.Animator.frameCallbacks[A]}},addFrameCallback:function(A,B){B=B||this;if(Ext.isString(A)){A=B[A]}var C="frameCallback"+(this.frameCallbackId++);Ext.draw.Animator.frameCallbacks[C]={fn:A,scope:B};return C},removeFrameCallback:function(A){delete Ext.draw.Animator.frameCallbacks[A]},fireFrameCallbacks:function(){var D=this.frameCallbacks,C,B,A;for(C in D){A=D[C];B=A.fn;if(Ext.isString(B)){B=A.scope[B]}B.call(A.scope);if(D[C]&&A.once){this.scheduled--;delete D[C]}}},handleFrame:function(){this.step(this.animationTime());this.fireFrameCallbacks();if(!this.scheduled&&this.empty()){Ext.AnimationQueue.stop(this.handleFrame,this);this.running=false}},ignite:function(){if(!this.running){this.running=true;Ext.AnimationQueue.start(this.handleFrame,this);Ext.draw.Draw.updateIOS()}}});
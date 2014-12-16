Ext.define("Ext.fx.layout.card.Scroll",{extend:"Ext.fx.layout.card.Abstract",requires:["Ext.fx.easing.Linear"],alias:"fx.layout.card.scroll",config:{duration:150},constructor:function(A){this.initConfig(A)},getEasing:function(){var A=this.easing;if(!A){this.easing=A=new Ext.fx.easing.Linear()}return A},updateDuration:function(A){this.getEasing().setDuration(A)},onActiveItemChange:function(E,M,G,I,H){var A=this.getDirection(),J=this.getEasing(),L,K,B,F,C,D;if(M&&G){if(this.isAnimating){this.stopAnimation()}M.setWidth("100%");M.setHeight("100%");L=this.getLayout().container.innerElement;F=L.getWidth();C=L.getHeight();K=M.renderElement;B=G.renderElement;this.oldItem=G;this.newItem=M;this.currentEventController=H;this.containerElement=L;this.isReverse=D=this.getReverse();M.show();if(A=="right"){A="left";this.isReverse=D=!D}else{if(A=="down"){A="up";this.isReverse=D=!D}}if(A=="left"){if(D){J.setConfig({startValue:F,endValue:0});L.dom.scrollLeft=F;B.setLeft(F)}else{J.setConfig({startValue:0,endValue:F});K.setLeft(F)}}else{if(D){J.setConfig({startValue:C,endValue:0});L.dom.scrollTop=C;B.setTop(C)}else{J.setConfig({startValue:0,endValue:C});K.setTop(C)}}this.startAnimation();H.pause()}},startAnimation:function(){this.isAnimating=true;this.getEasing().setStartTime(Date.now());Ext.AnimationQueue.start(this.doAnimationFrame,this)},doAnimationFrame:function(){var B=this.getEasing(),D=this.getDirection(),C="scrollTop",A;if(D=="left"||D=="right"){C="scrollLeft"}if(B.isEnded){this.stopAnimation()}else{A=B.getValue();this.containerElement.dom[C]=A}},stopAnimation:function(){var C=this,E=C.getDirection(),D="setTop",A=C.oldItem,B=C.newItem;if(E=="left"||E=="right"){D="setLeft"}C.currentEventController.resume();if(C.isReverse&&A&&A.renderElement&&A.renderElement.dom){A.renderElement[D](null)}else{if(B&&B.renderElement&&B.renderElement.dom){B.renderElement[D](null)}}Ext.AnimationQueue.stop(this.doAnimationFrame,this);C.isAnimating=false;C.fireEvent("animationend",C)}});
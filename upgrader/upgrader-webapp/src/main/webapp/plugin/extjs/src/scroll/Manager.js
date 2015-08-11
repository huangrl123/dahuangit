Ext.define("Ext.scroll.Manager",{extend:"Ext.util.Observable",requires:["Ext.scroll.Scroller","Ext.scroll.Indicator","Ext.GlobalEvents"],minIndicatorLength:24,refreshCounter:0,translationMethods:{1:"scrollparent",2:"csstransform"},constructor:function(B){var C=this,D={dragend:"onDragEnd",dragcancel:"onDragEnd",scope:C},A;if(Ext.supports.touchScroll===2){D.mousewheel="onMouseWheel";D.scroll={fn:"onElementScroll",delegated:false,scope:C}}C.callParent(arguments);C.scroller=new Ext.scroll.Scroller({autoRefresh:false,element:C.el,direction:C.direction,momentumEasing:{bounce:{springTension:1}},outOfBoundRestrictFactor:0,translatable:{translationMethod:C.translationMethods[Ext.supports.touchScroll]},listeners:{scrollstart:"onScrollStart",scroll:"onScroll",scrollend:"onScrollEnd",scope:C}});Ext.GlobalEvents.on("idle",C.doRefresh,C);A=C.containerEl=C.el.parent();C.owner.mon(A,D);C.initIndicators()},onElementScroll:function(B,A){A.scrollTop=A.scrollLeft=0},destroy:function(){var A=this;A.clearListeners();Ext.GlobalEvents.un("idle",A.doRefresh,A);A.scroller.destroy()},initIndicators:function(){var D=this,A=D.containerEl,C=D.scroller,B=D.minIndicatorLength;if(Ext.supports.touchScroll===2){D.xIndicator=new Ext.scroll.Indicator({axis:"x",scroller:C,containerEl:A,minLength:B});D.yIndicator=new Ext.scroll.Indicator({axis:"y",scroller:C,containerEl:A,minLength:B});D.refreshIndicators()}},invokeIndicators:function(C,F,D){var A=this,B=A.xIndicator,E=A.yIndicator;if(B&&A.isAxisEnabled("x")){B[C].apply(B,F)}if(E&&A.isAxisEnabled("y")){E[C].apply(E,D||F)}},getPosition:function(){return this.scroller.getPosition()},refresh:function(A){++this.refreshCounter;if(A){this.doRefresh()}},refreshIndicators:function(){var D=this,C=D.scroller,B=C.getMaxPosition(),A=C.getSize();D.invokeIndicators("setMaxScrollPosition",[B.x],[B.y]);D.invokeIndicators("setScrollSize",[A.x],[A.y]);D.invokeIndicators("setHasOpposite",[D.isAxisEnabled("y")],[D.isAxisEnabled("x")])},doRefresh:function(){var B=this,A=B.scroller;if(B.refreshCounter){A.refresh();B.refreshIndicators();B.refreshCounter=0}},onScrollStart:function(){this.isTouching=Ext.isScrolling=true;this.invokeIndicators("show");this.toggleOthers(true)},onScroll:function(A,C,D){var B=this;B.invokeIndicators("setValue",[C],[D]);B.fireEvent("scroll",B,C,D)},onScrollEnd:function(){var A=this;Ext.isScrolling=false;if(A.isTouching){return}A.invokeIndicators("hide")},onDragEnd:function(){this.isTouching=false;this.toggleOthers(false)},onMouseWheel:function(A){var J=this,G=J.scroller,K=A.getWheelDeltas(),M=-K.x,B=-K.y,I=G.getPosition(),H=G.getMaxPosition(),L=G.getMinPosition(),F=Math.max,E=Math.min,C=F(E(I.x+M,H.x),L.x),D=F(E(I.y+B,H.y),L.y);M=C-I.x;B=D-I.y;if(!M&&!B){return}A.stopEvent();J.onScrollStart();J.scrollBy(M,B);J.onScroll(G,C,D);J.onScrollEnd(G)},isAxisEnabled:function(A){return this.scroller.isAxisEnabled(A)},setScrollX:function(B){var A=this.scroller;A.scrollTo(B,A.getPosition().y)},setScrollY:function(B){var A=this.scroller;A.scrollTo(A.getPosition().x,B)},scrollTo:function(B,C,A){this.scroller.scrollTo(B,C,A)},scrollBy:function(B,C,A){if(B.length){A=C;C=B[1];B=B[0]}else{if(!Ext.isNumber(B)){A=C;C=B.y;B=B.x}}this.scroller.scrollBy(B,C,A)},scrollIntoView:function(D,H,B){var I=this,E=I.containerEl,C=I.scroller,A=C.getPosition(),J=A.x,L=A.y,G=Ext.fly(D).getScrollIntoViewXY(E,J,L),K=G.x,F=G.y;if(H===false){K=J}if(K!==J||F!==L){C.scrollTo(K,F,B)}},toggleOthers:function(C){var B=Ext.scroll.Scroller.instances,A,D;for(D in B){A=B[D];if(A!==this.scroller){A.setDisabled(C)}}},preventDefault:function(A){if(A.touches.length===1){A.preventDefault()}}});
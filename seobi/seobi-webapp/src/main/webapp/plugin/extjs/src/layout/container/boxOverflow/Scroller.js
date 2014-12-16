Ext.define("Ext.layout.container.boxOverflow.Scroller",{extend:"Ext.layout.container.boxOverflow.None",requires:["Ext.util.ClickRepeater","Ext.Element"],alternateClassName:"Ext.layout.boxOverflow.Scroller",alias:["box.overflow.scroller","box.overflow.Scroller"],mixins:{observable:"Ext.mixin.Observable"},animateScroll:false,scrollIncrement:20,wheelIncrement:10,scrollRepeatInterval:60,scrollDuration:400,scrollerCls:Ext.baseCSSPrefix+"box-scroller",beforeSuffix:"-before-scroller",afterSuffix:"-after-scroller",constructor:function(A){var B=this;B.mixins.observable.constructor.call(B,A);B.scrollPosition=0;B.scrollSize=0},getPrefixConfig:function(){return{role:"presentation",id:this.layout.owner.id+this.beforeSuffix,cls:this.createScrollerCls("beforeX"),style:"display:none"}},getSuffixConfig:function(){return{role:"presentation",id:this.layout.owner.id+this.afterSuffix,cls:this.createScrollerCls("afterX"),style:"display:none"}},createScrollerCls:function(C){var G=this,E=G.layout,A=E.owner,F=Ext.baseCSSPrefix,H=G.getOwnerType(A),D=G.scrollerCls,B=D+" "+D+"-"+E.names[C]+" "+D+"-"+H+" "+D+"-"+H+"-"+A.ui;if(A.plain){B+=" "+D+"-plain"}return B},getOverflowCls:function(A){return this.scrollerCls+"-body-"+A},beginLayout:function(A){var B=this.layout;A.innerCtScrollPos=this.getScrollPosition();this.callParent(arguments)},finishedLayout:function(B){var G=this,E=B.state.boxPlan,F=G.layout,C=F.names,H=Math.min(G.getMaxScrollPosition(),B.innerCtScrollPos),A,D;if(E&&E.tooNarrow){A=B.childItems[B.childItems.length-1].props;G.scrollSize=A[C.x]+A[C.width];G.updateScrollButtons()}F.innerCt[C.setScrollLeft](H);this.callParent([B])},handleOverflow:function(A){var H=this,C=H.layout.names,D=C.getWidth,B=C.parallelMargins,E,G,F,I;H.showScrollers();F=H.getBeforeScroller();I=H.getAfterScroller();E=F[D]()+I[D]()+F.getMargin(B)+I.getMargin(B);G=A.targetContext.getPaddingInfo()[C.width];return{reservedSpace:Math.max(E-G,0)}},getBeforeScroller:function(){var A=this;return A._beforeScroller||(A._beforeScroller=A.createScroller(A.beforeSuffix,"beforeRepeater","scrollLeft"))},getAfterScroller:function(){var A=this;return A._afterScroller||(A._afterScroller=A.createScroller(A.afterSuffix,"afterRepeater","scrollRight"))},createScroller:function(D,C,A){var E=this,F=E.layout.owner,G=E.scrollerCls,B;B=F.el.getById(F.id+D);B.addClsOnOver(G+"-hover");B.addClsOnClick(G+"-pressed");B.setVisibilityMode(Ext.Element.DISPLAY);E[C]=new Ext.util.ClickRepeater(B,{interval:E.scrollRepeatInterval,handler:A,scope:E});return B},createWheelListener:function(){var A=this;A.layout.innerCt.on({mousewheel:function(B){A.scrollBy(A.getWheelDelta(B)*A.wheelIncrement*-1,false)},stopEvent:true})},getWheelDelta:function(A){return A.getWheelDelta()},clearOverflow:function(){this.hideScrollers()},showScrollers:function(){var A=this;A.createWheelListener();A.getBeforeScroller().show();A.getAfterScroller().show();A.layout.owner.addClsWithUI(A.layout.direction==="vertical"?"vertical-scroller":"scroller")},hideScrollers:function(){var B=this,A=B.getBeforeScroller(),C=B.getAfterScroller();if(A){A.hide();C.hide();B.layout.owner.removeClsWithUI(B.layout.direction==="vertical"?"vertical-scroller":"scroller")}},destroy:function(){var A=this;Ext.destroy(A.beforeRepeater,A.afterRepeater,A._beforeScroller,A._afterScroller)},scrollBy:function(A,B){this.scrollTo(this.getScrollPosition()+A,B)},getScrollAnim:function(){return{duration:this.scrollDuration,callback:this.updateScrollButtons,scope:this}},updateScrollButtons:function(){var C=this,A=C.getBeforeScroller(),D=C.getAfterScroller(),B;if(!A||!D){return}B=C.scrollerCls+"-disabled";A[C.atExtremeBefore()?"addCls":"removeCls"](B);D[C.atExtremeAfter()?"addCls":"removeCls"](B);C.scrolling=false},scrollLeft:function(){this.scrollBy(-this.scrollIncrement,false)},scrollRight:function(){this.scrollBy(this.scrollIncrement,false)},getScrollPosition:function(){var B=this,C=B.layout,A;if(isNaN(B.scrollPosition)){A=C.innerCt[C.names.getScrollLeft]()}else{A=B.scrollPosition}return A},getMaxScrollPosition:function(){var A=this,B=A.layout,C=A.scrollSize-B.innerCt[B.names.getWidth]();return(C<0)?0:C},atExtremeBefore:function(){return !this.getScrollPosition()},atExtremeAfter:function(){return this.getScrollPosition()>=this.getMaxScrollPosition()},setVertical:function(){var B=this,A=B.getBeforeScroller(),C=B.getAfterScroller(),D=B.layout.names,E=B.scrollerCls;A.removeCls(E+"-"+D.beforeY);C.removeCls(E+"-"+D.afterY);A.addCls(E+"-"+D.beforeX);C.addCls(E+"-"+D.afterX);this.callParent()},scrollTo:function(D,C){var A=this,F=A.layout,G=F.names,E=A.getScrollPosition(),B=Ext.Number.constrain(D,0,A.getMaxScrollPosition());if(B!=E&&!A.scrolling){A.scrollPosition=NaN;if(C===undefined){C=A.animateScroll}F.innerCt[G.scrollTo](G.beforeScrollX,B,C?A.getScrollAnim():false);if(C){A.scrolling=true}else{A.updateScrollButtons()}A.fireEvent("scroll",A,B,C?A.getScrollAnim():false)}},scrollToItem:function(J,E){var H=this,G=H.layout,A=G.owner,C=G.names,B=G.innerCt,I,F,D;J=H.getItem(J);if(J!==undefined){if(J==A.items.first()){D=0}else{if(J===A.items.last()){D=H.getMaxScrollPosition()}else{I=H.getItemVisibility(J);if(!I.fullyVisible){F=J.getBox(false,true);D=F[C.x];if(I.hiddenEnd){D-=(B[C.getWidth]()-F[C.width])}}}}if(D!==undefined){H.scrollTo(D,E)}}},getItemVisibility:function(I){var H=this,D=H.getItem(I).getBox(true,true),F=H.layout,B=F.names,A=D[B.x],G=A+D[B.width],E=H.getScrollPosition(),C=E+F.innerCt[B.getWidth]();return{hiddenStart:A<E,hiddenEnd:G>C,fullyVisible:A>E&&G<C}}});
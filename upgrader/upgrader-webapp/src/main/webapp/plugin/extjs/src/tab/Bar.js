Ext.define("Ext.tab.Bar",{extend:"Ext.panel.Bar",xtype:"tabbar",baseCls:Ext.baseCSSPrefix+"tab-bar",requires:["Ext.tab.Tab","Ext.util.Point","Ext.layout.component.Body"],componentLayout:"body",isTabBar:true,config:{tabRotation:"default",tabStretchMax:true},defaultType:"tab",plain:false,ariaRole:"tablist",childEls:["body","strip"],_stripCls:Ext.baseCSSPrefix+"tab-bar-strip",_baseBodyCls:Ext.baseCSSPrefix+"tab-bar-body",renderTpl:'<div id="{id}-body" data-ref="body" role="presentation" class="{baseBodyCls} {baseBodyCls}-{ui} {bodyCls} {bodyTargetCls}{childElCls}"<tpl if="bodyStyle"> style="{bodyStyle}"</tpl>>{%this.renderContainer(out,values)%}</div><div id="{id}-strip" data-ref="strip" role="presentation" class="{stripCls} {stripCls}-{ui}{childElCls}"></div>',_reverseDockNames:{left:"right",right:"left"},_layoutAlign:{top:"end",right:"begin",bottom:"begin",left:"end"},initComponent:function(){var B=this,C=B.initialConfig.layout,E=C&&C.align,A=C&&C.overflowHandler,D;if(B.plain){B.addCls(B.baseCls+"-plain")}B.callParent();B.setLayout({align:E||(B.getTabStretchMax()?"stretchmax":B._layoutAlign[B.dock]),overflowHandler:A||"scroller"});B.on({click:B.onClick,element:"el",scope:B})},initRenderData:function(){var A=this;return Ext.apply(this.callParent(),{bodyCls:A.bodyCls,baseBodyCls:A._baseBodyCls,bodyTargetCls:A.bodyTargetCls,stripCls:A._stripCls,dock:A.dock})},setDock:function(C){var B=this,D=B.items,E=B.ownerCt,A,F;D=D&&D.items;if(D){for(A=0,F=D.length;A<F;A++){D[A].setTabPosition(C)}}if(B.rendered){B.resetItemMargins();if(E&&E.isHeader){E.resetItemMargins()}B.needsScroll=true}B.callParent([C])},updateTabRotation:function(A){var C=this,D=C.items,B,E;D=D&&D.items;if(D){for(B=0,E=D.length;B<E;B++){D[B].setRotation(A)}}if(C.rendered){C.resetItemMargins();C.needsScroll=true;C.updateLayout()}},onRender:function(){var A=this;A.callParent();if(Ext.isIE8&&A.vertical){A.el.on({mousemove:A.onMouseMove,scope:A})}},afterLayout:function(){this.adjustTabPositions();this.callParent(arguments)},adjustTabPositions:function(){var F=this.items.items,B=F.length,D,A,E,C;if(!Ext.isIE8){while(B--){D=F[B];E=D.el;A=D.lastBox;C=D.getActualRotation();if(C===1&&D.isVisible()){E.setStyle("left",(A.x+A.width)+"px")}else{if(C===2&&D.isVisible()){E.setStyle("left",(A.x-A.height)+"px")}}}}},onAdded:function(B,A,C){if(B.isHeader){this.addCls(B.baseCls+"-"+B.ui+"-tab-bar")}this.callParent([B,A,C])},onRemove:function(A){var B=this;if(A===B.previousTab){B.previousTab=null}B.callParent(arguments)},onRemoved:function(A){var B=this.ownerCt;if(B.isHeader){this.removeCls(B.baseCls+"-"+B.ui+"-tab-bar")}this.callParent([A])},afterComponentLayout:function(D){var B=this,C=B.needsScroll,A=B.layout.overflowHandler;B.callParent(arguments);if(A&&C&&B.tooNarrow&&A.scrollToItem){A.scrollToItem(B.activeTab)}delete B.needsScroll},onMouseMove:function(A){var C=this,E=C._overTab,D,B;if(A.getTarget("."+Ext.baseCSSPrefix+"box-scroller")){return}D=C.getTabInfoFromPoint(A.getXY());B=D.tab;if(B!==E){if(E&&E.rendered){E.onMouseLeave(A);C._overTab=null}if(B){B.onMouseEnter(A);C._overTab=B;if(!B.disabled){C.el.setStyle("cursor","pointer")}}else{C.el.setStyle("cursor","default")}}},onMouseLeave:function(A){var B=this._overTab;if(B&&B.rendered){B.onMouseLeave(A)}},getTabInfoFromPoint:function(H){var K=this,L=K.items.items,A=L.length,U=K.layout.innerCt,Y=U.getXY(),E=new Ext.util.Point(H[0],H[1]),I=0,D,O,S,N,T,V,M,F,Q,G,R,P,X,B,W,J,C;for(;I<A;I++){C=L[I];D=C.lastBox;if(!D){continue}G=Y[0]+D.x;R=Y[1]-U.dom.scrollTop+D.y;P=D.width;X=D.height;O=new Ext.util.Region(R,G+P,R+X,G);if(O.contains(E)){S=C.closeEl;if(S){if(K._isTabReversed===undefined){K._isTabReversed=W=(C.btnWrap.dom.currentStyle.filter.indexOf("rotation=2")!==-1)}J=W?this._reverseDockNames[K.dock]:K.dock;F=S.getWidth();Q=S.getHeight();T=K.getCloseXY(S,G,R,P,X,F,Q,J);V=T[0];M=T[1];B=new Ext.util.Region(M,V+F,M+Q,V);N=B.contains(E)}break}}return{tab:C,close:N}},getCloseXY:function(A,J,I,G,H,E,D,B){var F=A.getXY(),K,C;if(B==="right"){K=J+G-((F[1]-I)+D);C=I+(F[0]-J)}else{K=J+(F[1]-I);C=I+J+H-F[0]-E}return[K,C]},closeTab:function(D){var C=this,A=D.card,E=C.tabPanel,B;if(A&&A.fireEvent("beforeclose",A)===false){return false}B=C.findNextActivatable(D);Ext.suspendLayouts();if(E&&A){delete D.ownerCt;A.fireEvent("close",A);E.remove(A);if(!E.getComponent(A)){D.fireClose();C.remove(D)}else{D.ownerCt=C;Ext.resumeLayouts(true);return false}}if(B){if(E){E.setActiveTab(B.card)}else{C.setActiveTab(B)}B.focus()}Ext.resumeLayouts(true)},findNextActivatable:function(B){var A=this;if(B.active&&A.items.getCount()>1){return(A.previousTab&&A.previousTab!==B&&!A.previousTab.disabled)?A.previousTab:(B.next("tab[disabled=false]")||B.prev("tab[disabled=false]"))}},setActiveTab:function(A,B){var C=this;if(!A.disabled&&A!==C.activeTab){if(C.activeTab){if(C.activeTab.isDestroyed){C.previousTab=null}else{C.previousTab=C.activeTab;C.activeTab.deactivate()}}A.activate();C.activeTab=A;C.needsScroll=true;if(!B){C.fireEvent("change",C,A,A.card);C.updateLayout()}}},privates:{applyTargetCls:function(A){this.bodyTargetCls=A},getTargetEl:function(){return this.body||this.frameBody||this.el},onClick:function(C,F){var A=this,H=A.tabPanel,B,D,E,G;if(C.getTarget("."+Ext.baseCSSPrefix+"box-scroller")){return}if(Ext.isIE8&&A.vertical){G=A.getTabInfoFromPoint(C.getXY());D=G.tab;E=G.close}else{B=C.getTarget("."+Ext.tab.Tab.prototype.baseCls);D=B&&Ext.getCmp(B.id);E=D&&D.closeEl&&(F===D.closeEl.dom)}if(E){C.preventDefault()}if(D&&D.isDisabled&&!D.isDisabled()){if(D.closable&&E){D.onCloseClick()}else{if(H){H.setActiveTab(D.card)}else{A.setActiveTab(D)}}D.afterClick(E)}}}});
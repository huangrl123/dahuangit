Ext.define("Ext.layout.container.Fit",{extend:"Ext.layout.container.Container",alternateClassName:"Ext.layout.FitLayout",alias:"layout.fit",itemCls:Ext.baseCSSPrefix+"fit-item",targetCls:Ext.baseCSSPrefix+"layout-fit",type:"fit",manageMargins:true,sizePolicies:{0:{readsWidth:1,readsHeight:1,setsWidth:0,setsHeight:0},1:{readsWidth:0,readsHeight:1,setsWidth:1,setsHeight:0},2:{readsWidth:1,readsHeight:0,setsWidth:0,setsHeight:1},3:{readsWidth:0,readsHeight:0,setsWidth:1,setsHeight:1}},getItemSizePolicy:function(D,C){var A=C||this.owner.getSizeModel(),B=(A.width.shrinkWrap?0:1)|(A.height.shrinkWrap?0:2);return this.sizePolicies[B]},beginLayoutCycle:function(E,O){var I=this,J=I.lastHeightModel&&I.lastHeightModel.calculated,F=I.lastWidthModel&&I.lastWidthModel.calculated,H=F||J,D=0,M=0,K,B,G,C,A,Q,P,N,R,L;I.callParent(arguments);if(H&&E.targetContext.el.dom.tagName.toUpperCase()!="TD"){H=F=J=false}B=E.childItems;A=B.length;for(G=0;G<A;++G){C=B[G];if(O){K=C.target;P=K.minHeight;N=K.minWidth;if(N||P){Q=C.marginInfo||C.getMarginInfo();P+=Q.height;N+=Q.height;if(D<P){D=P}if(M<N){M=N}}}if(H){R=C.el.dom.style;if(J){R.height=""}if(F){R.width=""}}}if(O){E.maxChildMinHeight=D;E.maxChildMinWidth=M}K=E.target;E.overflowX=(!E.widthModel.shrinkWrap&&E.maxChildMinWidth&&K.scrollFlags.x)||L;E.overflowY=(!E.heightModel.shrinkWrap&&E.maxChildMinHeight&&K.scrollFlags.y)||L},calculate:function(B){var A=this,D=B.childItems,F=D.length,I=A.getContainerSize(B),L={length:F,ownerContext:B,targetSize:I},C=B.widthModel.shrinkWrap,K=B.heightModel.shrinkWrap,N=B.overflowX,M=B.overflowY,G,H,E,J,P,O;if(N||M){G=A.getScrollbarsNeeded(N&&I.width,M&&I.height,B.maxChildMinWidth,B.maxChildMinHeight);if(G){H=Ext.getScrollbarSize();if(G&1){I.height-=H.height}if(G&2){I.width-=H.width}}}if(F>0){for(J=0;J<F;++J){L.index=J;A.fitItem(D[J],L)}}else{L.contentWidth=L.contentHeight=0}if(K||C){E=B.targetContext.getPaddingInfo();if(C){if(M&&!I.gotHeight){A.done=false}else{P=L.contentWidth+E.width;if(G&2){P+=H.width}if(!B.setContentWidth(P)){A.done=false}}}if(K){if(N&&!I.gotWidth){A.done=false}else{O=L.contentHeight+E.height;if(G&1){O+=H.height}if(!B.setContentHeight(O)){A.done=false}}}}},fitItem:function(A,C){var B=this;if(A.invalid){B.done=false;return}C.margins=A.getMarginInfo();C.needed=C.got=0;B.fitItemWidth(A,C);B.fitItemHeight(A,C);if(C.got!=C.needed){B.done=false}},fitItemWidth:function(A,C){var D,B;if(C.ownerContext.widthModel.shrinkWrap){B=A.getProp("width")+C.margins.width;D=C.contentWidth;if(D===undefined){C.contentWidth=B}else{C.contentWidth=Math.max(D,B)}}else{if(A.widthModel.calculated){++C.needed;if(C.targetSize.gotWidth){++C.got;this.setItemWidth(A,C)}else{return}}}this.positionItemX(A,C)},fitItemHeight:function(B,D){var C,A;if(D.ownerContext.heightModel.shrinkWrap){A=B.getProp("height")+D.margins.height;C=D.contentHeight;if(C===undefined){D.contentHeight=A}else{D.contentHeight=Math.max(C,A)}}else{if(B.heightModel.calculated){++D.needed;if(D.targetSize.gotHeight){++D.got;this.setItemHeight(B,D)}else{return}}}this.positionItemY(B,D)},positionItemX:function(A,C){var B=C.margins;if(C.index||B.left){A.setProp("x",B.left)}if(B.width){A.setProp("margin-right",B.width)}},positionItemY:function(A,C){var B=C.margins;if(C.index||B.top){A.setProp("y",B.top)}if(B.height){A.setProp("margin-bottom",B.height)}},setItemHeight:function(A,B){A.setHeight(B.targetSize.height-B.margins.height)},setItemWidth:function(A,B){A.setWidth(B.targetSize.width-B.margins.width)}});
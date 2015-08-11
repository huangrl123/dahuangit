Ext.define("Ext.window.Window",{extend:"Ext.panel.Panel",alternateClassName:"Ext.Window",requires:["Ext.util.ComponentDragger","Ext.util.Region"],alias:"widget.window",baseCls:Ext.baseCSSPrefix+"window",resizable:true,draggable:true,constrain:false,constrainHeader:false,plain:false,minimizable:false,maximizable:false,minHeight:50,minWidth:50,expandOnShow:true,collapsible:false,closable:true,hidden:true,autoRender:true,hideMode:"offsets",floating:true,itemCls:Ext.baseCSSPrefix+"window-item",initialAlphaNum:/^[a-z0-9]/,overlapHeader:true,ignoreHeaderBorderManagement:true,alwaysFramed:true,isRootCfg:{isRoot:true},isWindow:true,ariaRole:"dialog",initComponent:function(){var A=this;A.frame=false;A.callParent();if(A.plain){A.addClsWithUI("plain")}A.addStateEvents(["maximize","restore","resize","dragend"])},getElConfig:function(){var A=this,B;B=A.callParent();B.tabIndex=-1;return B},getState:function(){var C=this,E=C.callParent()||{},D=!!C.maximized,B=C.ghostBox,A;E.maximized=D;if(D){A=C.restorePos}else{if(B){A=[B.x,B.y]}else{A=C.getPosition()}}Ext.apply(E,{size:D?C.restoreSize:C.getSize(),pos:A});return E},applyState:function(B){var A=this;if(B){A.maximized=B.maximized;if(A.maximized){A.hasSavedRestore=true;A.restoreSize=B.size;A.restorePos=B.pos}else{Ext.apply(A,{width:B.size.width,height:B.size.height,x:B.pos[0],y:B.pos[1]})}}},onRender:function(C,B){var A=this;A.callParent(arguments);A.focusEl=A.el;if(A.maximizable){A.header.on({scope:A,dblclick:A.toggleMaximize})}},afterRender:function(){var B=this,A=B.header,C;if(B.maximized){B.maximized=false;B.maximize();if(A){A.removeCls(A.indicateDragCls)}}B.callParent();if(B.closable){C=B.getKeyMap();C.on(27,B.onEsc,B)}else{C=B.keyMap}if(C&&B.hidden){C.disable()}},onEsc:function(C,A){var B=Ext["FocusManager"];if(!Ext.enableFocusManager||B.focusedCmp===this){A.stopEvent();this.close()}},beforeDestroy:function(){var A=this;if(A.rendered){Ext.un("resize",A.onWindowResize,A);delete A.animateTarget;A.hide();Ext.destroy(A.keyMap)}A.callParent()},addTools:function(){var C=this,B=C.tools,A=[];C.callParent();if(C.minimizable){B.push({type:"minimize",handler:Ext.Function.bind(C.minimize,C,A)})}if(C.maximizable){if(C.header&&"titlePosition" in C.header&&C.header.titlePosition>=B.length){C.header.titlePosition++}Ext.Array.push(B,{type:"maximize",handler:Ext.Function.bind(C.maximize,C,A)},{type:"restore",handler:Ext.Function.bind(C.restore,C,A),hidden:true})}},getDefaultFocus:function(){var C=this,B,A=C.defaultButton||C.defaultFocus,D;if(A!==undefined){if(Ext.isNumber(A)){B=C.query("button")[A]}else{if(Ext.isString(A)){D=A;if(D.match(C.initialAlphaNum)){B=C.down(Ext.makeIdSelector(D))}if(!B){B=C.down(D)}}else{if(A.focus){B=A}}}}return B||C.el},onFocus:function(){var B=this,A;if(Ext.enableFocusManager||((A=B.getDefaultFocus())===B)){B.callParent(arguments)}else{A.focus()}},onShow:function(){var A=this;A.callParent(arguments);if(A.expandOnShow){A.expand(false)}A.syncMonitorWindowResize();if(A.keyMap){A.keyMap.enable()}},doClose:function(){var A=this;if(A.hidden){A.fireEvent("close",A);if(A.closeAction=="destroy"){A.destroy()}}else{A.hide(A.animateTarget,A.doClose,A)}},afterHide:function(){var A=this;A.syncMonitorWindowResize();if(A.keyMap){A.keyMap.disable()}A.callParent(arguments)},onWindowResize:function(){var B=this,A;if(B.maximized){B.fitContainer()}else{A=B.getSizeModel();if(A.width.natural||A.height.natural){B.updateLayout()}B.doConstrain()}},minimize:function(){this.fireEvent("minimize",this);return this},resumeHeaderLayout:function(A){this.header.resumeLayouts(A?this.isRootCfg:null)},afterCollapse:function(){var C=this,A=C.header,B=C.tools;if(A&&C.maximizable){A.suspendLayouts();B.maximize.hide();B.restore.hide();this.resumeHeaderLayout(true)}if(C.resizer){C.resizer.disable()}C.callParent(arguments)},afterExpand:function(){var C=this,A=C.header,B=C.tools,D;if(A){A.suspendLayouts();if(C.maximized){B.restore.show();D=true}else{if(C.maximizable){B.maximize.show();D=true}}this.resumeHeaderLayout(D)}if(C.resizer){C.resizer.enable()}C.callParent(arguments)},maximize:function(B){var G=this,H=G.header,C=G.tools,F=G.width,A=G.height,D,E;if(!G.maximized){G.expand(false);if(!G.hasSavedRestore){D=G.restoreSize={width:Ext.isNumber(F)?F:null,height:Ext.isNumber(A)?A:null};G.restorePos=G.getPosition(true)}if(H){H.suspendLayouts();if(C.maximize){C.maximize.hide();E=true}if(C.restore){C.restore.show();E=true}if(G.collapseTool){G.collapseTool.hide();E=true}G.resumeHeaderLayout(E)}G.el.disableShadow();if(G.dd){G.dd.disable();if(H){H.removeCls(H.indicateDragCls)}}if(G.resizer){G.resizer.disable()}G.el.addCls(Ext.baseCSSPrefix+"window-maximized");G.container.addCls(Ext.baseCSSPrefix+"window-maximized-ct");G.syncMonitorWindowResize();G.fitContainer(B=(B||!!G.animateTarget)?{callback:function(){G.maximized=true;G.fireEvent("maximize",G)}}:null);if(!B){G.maximized=true;G.fireEvent("maximize",G)}}return G},restore:function(C){var D=this,E=D.tools,A=D.header,B=D.restoreSize,F;if(D.maximized){D.hasSavedRestore=null;D.removeCls(Ext.baseCSSPrefix+"window-maximized");if(A){A.suspendLayouts();if(E.restore){E.restore.hide();F=true}if(E.maximize){E.maximize.show();F=true}if(D.collapseTool){D.collapseTool.show();F=true}D.resumeHeaderLayout(F)}B.x=D.restorePos[0];B.y=D.restorePos[1];D.setBox(B,C=(C||!!D.animateTarget)?{callback:function(){D.el.enableShadow(true);D.maximized=false;D.fireEvent("restore",D)}}:null);D.restorePos=D.restoreSize=null;if(D.dd){D.dd.enable();if(A){A.addCls(A.indicateDragCls)}}if(D.resizer){D.resizer.enable()}D.container.removeCls(Ext.baseCSSPrefix+"window-maximized-ct");D.syncMonitorWindowResize();if(!C){D.el.enableShadow(true);D.maximized=false;D.fireEvent("restore",D)}}return D},syncMonitorWindowResize:function(){var C=this,B=C._monitoringResize,D=C.constrain||C.constrainHeader||C.maximized,A=C.hidden||C.destroying||C.isDestroyed;if(D&&!A){if(!B){Ext.on("resize",C.onWindowResize,C,{buffer:1});C._monitoringResize=true}}else{if(B){Ext.un("resize",C.onWindowResize,C);C._monitoringResize=false}}},toggleMaximize:function(){return this[this.maximized?"restore":"maximize"]()},createGhost:function(){var A=this.callParent(arguments);A.xtype="window";return A},privates:{getFocusEl:function(){return this.getDefaultFocus()},initDraggable:function(){this.initSimpleDraggable()},initResizable:function(){this.callParent(arguments);if(this.maximized){this.resizer.disable()}}}});
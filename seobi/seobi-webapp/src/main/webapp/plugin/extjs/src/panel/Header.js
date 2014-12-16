Ext.define("Ext.panel.Header",{extend:"Ext.panel.Bar",requires:["Ext.panel.Title","Ext.panel.Tool"],xtype:"header",isHeader:true,defaultType:"tool",indicateDrag:false,weight:-1,shrinkWrap:3,iconAlign:"left",titleAlign:"left",titlePosition:0,titleRotation:"default",beforeRenderConfig:{glyph:null,icon:null,iconCls:null,iconAlign:null,title:{$value:{ariaRole:"presentation",focusable:false,xtype:"title",flex:1},merge:function(B,A){if(typeof B=="string"){B={text:B}}return Ext.merge(A?Ext.Object.chain(A):{},B)}},titleAlign:null,titlePosition:null,titleRotation:null},headerCls:Ext.baseCSSPrefix+"header",initComponent:function(){var C=this,D=C.items,A=C.itemPosition,B=[C.headerCls];C.tools=C.tools||[];C.items=D=(D?D.slice():[]);if(A!==undefined){C._userItems=D.slice();C.items=D=[]}C.indicateDragCls=C.headerCls+"-draggable";if(C.indicateDrag){B.push(C.indicateDragCls)}C.addCls(B);C.syncNoBorderCls();Ext.Array.push(D,C.tools);C.tools.length=0;C.callParent();C.on({dblclick:C.onDblClick,click:C.onClick,element:"el",scope:C})},addTool:function(A){this.add(Ext.ComponentManager.create(A,"tool"))},afterLayout:function(){var C=this,D,B,A,E;if(C.vertical){B=C.frameTR;if(B){D=C.frameBR;A=C.frameTL;E=(C.getWidth()-B.getPadding("r")-((A)?A.getPadding("l"):C.el.getBorderWidth("l")))+"px";D.setStyle("background-position-x",E);B.setStyle("background-position-x",E)}}this.callParent()},applyTitle:function(C,E){var B=this,A,D;C=C||"";if(typeof C==="string"){C={text:C}}if(E){Ext.suspendLayouts();E.setConfig(C);Ext.resumeLayouts(true);C=E}else{C.ui=B.ui;C.headerRole=B.headerRole;D=("rotation" in C);C=new Ext.panel.Title(C);if(!D&&B.vertical&&B.titleRotation==="default"){C.rotation=1}}return C},applyTitlePosition:function(A){var B=this.items.getCount();if(this._titleInItems){--B}return Math.max(Math.min(A,B),0)},beforeLayout:function(){this.callParent();this.syncBeforeAfterTitleClasses()},beforeRender:function(){var B=this,A=B.itemPosition;B.protoEl.unselectable();B.callParent();if(A!==undefined){B.insert(A,B._userItems)}},getTools:function(){return this.tools.slice()},onAdd:function(B,C){var A=this.tools;this.callParent([B,C]);if(B.isTool){A.push(B);A[B.type]=B}},onAdded:function(B,A,C){this.syncNoBorderCls();this.callParent([B,A,C])},onRemoved:function(B,A,C){this.syncNoBorderCls();this.callParent([B,A,C])},setDock:function(C){var B=this,E=B.getTitle(),A=B.getTitleRotation(),D=E.getRotation();Ext.suspendLayouts();B.callParent([C]);if(A==="default"){A=(B.vertical?1:0);if(A!==D){E.setRotation(A)}if(B.rendered){B.resetItemMargins()}}Ext.resumeLayouts(true)},updateGlyph:function(A){this.getTitle().setGlyph(A)},updateIcon:function(A){this.getTitle().setIcon(A)},updateIconAlign:function(B,A){this.getTitle().setIconAlign(B)},updateIconCls:function(A){this.getTitle().setIconCls(A)},updateTitle:function(A,B){if(!B){this.insert(this.getTitlePosition(),A);this._titleInItems=true}this.titleCmp=A},updateTitleAlign:function(B,A){this.getTitle().setTextAlign(B)},updateTitlePosition:function(A){this.insert(A,this.getTitle())},updateTitleRotation:function(A){if(A==="default"){A=(this.vertical?1:0)}this.getTitle().setRotation(A)},privates:{fireClickEvent:function(A,C){var B="."+Ext.panel.Tool.prototype.baseCls;if(!C.getTarget(B)){this.fireEvent(A,this,C)}},getFocusEl:function(){return this.el},getFramingInfoCls:function(){var B=this,A=B.callParent(),C=B.ownerCt;if(!B.expanding&&C&&(C.collapsed||B.isCollapsedExpander)){A+="-"+C.collapsedCls}return A+"-"+B.dock},onClick:function(A){this.fireClickEvent("click",A)},onDblClick:function(A){this.fireClickEvent("dblclick",A)},syncBeforeAfterTitleClasses:function(B){var I=this,K=I.items,D=K.items,A=I.getTitlePosition(),F=D.length,E=K.generation,G=I.syncBeforeAfterGen,J,C,H,L;if(!B&&(G===E)){return}I.syncBeforeAfterGen=E;for(H=0;H<F;++H){L=D[H];J=L.afterTitleCls||(L.afterTitleCls=L.baseCls+"-after-title");C=L.beforeTitleCls||(L.beforeTitleCls=L.baseCls+"-before-title");if(!I.title||H<A){if(G){L.removeCls(J)}L.addCls(C)}else{if(H>A){if(G){L.removeCls(C)}L.addCls(J)}}}},syncNoBorderCls:function(){var B=this,C=this.ownerCt,A=B.headerCls+"-noborder";if(C?(C.border===false&&!C.frame):B.border===false){B.addCls(A)}else{B.removeCls(A)}}}});
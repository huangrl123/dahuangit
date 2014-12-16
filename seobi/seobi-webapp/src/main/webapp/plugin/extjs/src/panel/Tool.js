Ext.define("Ext.panel.Tool",{extend:"Ext.Component",uses:["Ext.tip.QuickTipManager"],xtype:"tool",isTool:true,baseCls:Ext.baseCSSPrefix+"tool",disabledCls:Ext.baseCSSPrefix+"tool-disabled",toolPressedCls:Ext.baseCSSPrefix+"tool-pressed",toolOverCls:Ext.baseCSSPrefix+"tool-over",ariaRole:"button",childEls:["toolEl"],renderTpl:['<img id="{id}-toolEl" data-ref="toolEl" src="{blank}" class="{baseCls}-img {baseCls}-{type}{childElCls}" role="presentation"/>'],toolOwner:null,tooltipType:"qtip",stopEvent:true,cacheHeight:true,cacheWidth:true,_toolTypes:{close:1,collapse:1,down:1,expand:1,gear:1,help:1,left:1,maximize:1,minimize:1,minus:1,next:1,pin:1,plus:1,prev:1,print:1,refresh:1,restore:1,right:1,save:1,search:1,toggle:1,unpin:1,up:1},initComponent:function(){var A=this;if(A.id&&A._toolTypes[A.id]){Ext.Error.raise("When specifying a tool you should use the type option, the id can conflict now that tool is a Component")}A.type=A.type||A.id;Ext.applyIf(A.renderData,{baseCls:A.baseCls,blank:Ext.BLANK_IMAGE_URL,type:A.type});A.tooltip=A.tooltip||A.qtip;A.callParent()},afterRender:function(){var B=this,A;B.callParent(arguments);B.el.on({click:B.onClick,mousedown:B.onMouseDown,mouseover:B.onMouseOver,mouseout:B.onMouseOut,scope:B});A=B.tooltip;if(A){B.setTooltip(A)}},tipAttrs:{qtip:"data-qtip"},setTooltip:function(A,G){var E=this,H=E.tooltip,F=E.tooltipType,D=E.id,C=E.el,B;if(H&&Ext.quickTipsActive&&Ext.isObject(H)){Ext.tip.QuickTipManager.unregister(D)}E.tooltip=A;if(G){E.tooltipType=G}if(A){if(Ext.quickTipsActive&&Ext.isObject(A)){Ext.tip.QuickTipManager.register(Ext.apply({target:D},A))}else{if(C){if(G&&F&&G!==F){B=E.tipAttrs[F]||"title";C.dom.removeAttribute(B)}B=E.tipAttrs[G||F]||"title";C.dom.setAttribute(B,A)}}}},setType:function(A){var B=this,C=B.type;B.type=A;if(B.rendered){if(C){B.toolEl.removeCls(B.baseCls+"-"+C)}B.toolEl.addCls(B.baseCls+"-"+A)}else{B.renderData.type=A}return B},onDestroy:function(){var A=this,B=A.keyMap;A.setTooltip(null);if(B){B.destroy();A.keyMap=null}delete A.toolOwner;A.callParent()},privates:{getFocusEl:function(){return this.el},onClick:function(A,C){var B=this;if(B.disabled){return false}B.el.removeCls(B.toolPressedCls+" "+B.toolOverCls);if(B.stopEvent!==false){A.stopEvent()}if(B.handler){Ext.callback(B.handler,B.scope,[A,C,B.ownerCt,B],0,B)}else{if(B.callback){Ext.callback(B.callback,B.scope,[B.toolOwner||B.ownerCt,B,A],0,B)}}B.fireEvent("click",B,A,B.toolOwner||B.ownerCt);return true},onMouseDown:function(){if(this.disabled){return false}this.el.addCls(this.toolPressedCls)},onMouseOver:function(){if(this.disabled){return false}this.el.addCls(this.toolOverCls)},onMouseOut:function(){this.el.removeCls(this.toolOverCls)}}});
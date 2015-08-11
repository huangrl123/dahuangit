Ext.define("Ext.aria.panel.Panel",{override:"Ext.panel.Panel",requires:["Ext.aria.panel.AbstractPanel","Ext.aria.panel.Header"],closeText:"Close Panel",collapseText:"Collapse Panel",expandText:"Expand Panel",untitledText:"Untitled Panel",ariaToolFocusCls:Ext.baseCSSPrefix+"aria-tool-focus",ariaSkipContainerTitleCheck:true,onBoxReady:function(){var F=this,A=F.ariaToolFocusCls,E=F.collapseTool,B,D,C,G;F.callParent();if(E){E.ariaUpdate({"aria-label":F.collapsed?F.expandText:F.collapseText});E.ariaAddKeyMap({key:[Ext.EventObject.ENTER,Ext.EventObject.SPACE],handler:F.toggleCollapse,scope:F})}if(F.closable){toolBtn=F.down("tool[type=close]");toolBtn.ariaUpdate({"aria-label":F.closeText});toolBtn.ariaAddKeyMap({key:[Ext.EventObject.ENTER,Ext.EventObject.SPACE],handler:F.close,scope:F})}B=F.getHeader();if(B){D=B.getTools();for(C=0,G=D.length;C<G;C++){D[C].ariaFocusCls=A}}},setTitle:function(A){var B=this;B.callParent(arguments);B.ariaUpdate({"aria-label":A})},createReExpander:function(E,F){var D=this,A,B,C;A=D.getOppositeDirection(E);B=D.callParent(arguments);C=B.down("tool[type=expand-"+A+"]");if(C){C.on("boxready",function(){C.ariaUpdate({"aria-label":D.collapsed?D.expandText:D.collapseText});C.ariaAddKeyMap({key:[Ext.EventObject.ENTER,Ext.EventObject.SPACE],handler:D.toggleCollapse,scope:D})},{single:true})}return B},ariaGetRenderAttributes:function(){var B=this,A;A=B.callParent();if(B.collapsible){A["aria-expanded"]=!B.collapsed}return A},ariaGetAfterRenderAttributes:function(){var D=this,B={},C,A,E;C=D.callParent(arguments);if(D.ariaRole==="presentation"){return C}if(D.title){E=D.ariaGetTitleTextEl();if(E){B={"aria-labelledby":E.id}}else{B={title:D.title}}}else{if(D.ariaLabel){B={title:D.ariaLabel}}}Ext.apply(C,B);if(!D.ariaSkipPanelTitleCheck&&!Ext.theme){D.ariaCheckPanelTitle(C)}return C},ariaCheckPanelTitle:function(A){var B=this;if(!A["aria-labelledby"]&&!A.title&&B.ariaIsFocusableContainer){Ext.log.error("Panel "+B.id+" has ARIA role of '"+B.ariaRole+"', but does not have a title. WAI-ARIA requires that all focusable panels have a title.")}},ariaGetTitleTextEl:function(){var A=this.header;return A&&A.titleCmp&&A.titleCmp.textEl||null},onExpand:function(){var A=this;A.callParent(arguments);A.ariaUpdate({"aria-expanded":true});if(A.collapseTool){A.ariaUpdate(A.collapseTool.getEl(),{"aria-label":A.collapseText})}},onCollapse:function(){var A=this;A.callParent(arguments);A.ariaUpdate({"aria-expanded":false});if(A.collapseTool){A.ariaUpdate(A.collapseTool.getEl(),{"aria-label":A.expandText})}}});
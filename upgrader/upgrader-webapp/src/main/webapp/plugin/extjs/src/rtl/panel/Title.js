Ext.define("Ext.rtl.panel.Title",{override:"Ext.panel.Title",getIconRenderData:function(){var B=this,C=B.callParent(),A=B.ownerCt;if(A&&A.isParentRtl()){C.childElCls=" "+B._rtlCls}return C},privates:{_getVerticalAdjustDirection:function(){var A=this.ownerCt;return(A&&A.isParentRtl())?"right":"left"}}});
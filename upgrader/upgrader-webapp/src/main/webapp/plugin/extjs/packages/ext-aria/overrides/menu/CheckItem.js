Ext.define("Ext.aria.menu.CheckItem",{override:"Ext.menu.CheckItem",ariaGetRenderAttributes:function(){var B=this,A;A=B.callParent();A["aria-checked"]=B.menu?"mixed":!!B.checked;return A}});
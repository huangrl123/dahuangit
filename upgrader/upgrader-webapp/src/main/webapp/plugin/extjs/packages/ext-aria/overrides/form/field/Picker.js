Ext.define("Ext.aria.form.field.Picker",{override:"Ext.form.field.Picker",requires:["Ext.aria.form.field.Trigger"],getAriaRenderAttributes:function(){var B=this,A;A=B.callParent();A["aria-haspopup"]=true;return A},ariaGetAfterRenderAttributes:function(){var B=this,A,C;A=B.callParent();C=B.getPicker();if(C){A["aria-owns"]=C.id}return A}});
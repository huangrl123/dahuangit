Ext.define("Ext.form.field.Trigger",{extend:"Ext.form.field.Text",alias:["widget.triggerfield","widget.trigger"],requires:["Ext.dom.Helper","Ext.util.ClickRepeater"],alternateClassName:["Ext.form.TriggerField","Ext.form.TwinTriggerField","Ext.form.Trigger"],triggerCls:Ext.baseCSSPrefix+"form-arrow-trigger",inheritableStatics:{warnDeprecated:function(){Ext.log.warn("Ext.form.field.Trigger is deprecated. Use Ext.form.field.Text instead.")}},onClassExtended:function(){this.warnDeprecated()},constructor:function(A){this.self.warnDeprecated();this.callParent([A])}});
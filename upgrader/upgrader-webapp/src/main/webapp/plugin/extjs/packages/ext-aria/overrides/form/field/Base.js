Ext.define("Ext.aria.form.field.Base",{override:"Ext.form.field.Base",requires:["Ext.util.Format","Ext.aria.Component"],ariaRenderAttributesToElement:false,msgTarget:"side",getSubTplData:function(){var C=this,A=Ext.util.Format.attributes,D,B;D=C.callParent();B=C.ariaGetRenderAttributes();delete B.role;D.inputAttrTpl=[D.inputAttrTpl,A(B)].join(" ");return D},ariaGetEl:function(){return this.inputEl},ariaGetRenderAttributes:function(){var C=this,A=C.readOnly,D=C.formatText,B;B=C.callParent();if(A!=null){B["aria-readonly"]=!!A}if(D&&!B.title){B.title=Ext.String.format(D,C.format)}return B},ariaGetAfterRenderAttributes:function(){var B=this,A=B.labelEl,C;C=B.callParent();if(A){C["aria-labelledby"]=A.id}return C},setReadOnly:function(A){var B=this;B.callParent(arguments);B.ariaUpdate({"aria-readonly":A})},markInvalid:function(C,B){var A=this;A.callParent(arguments);A.ariaUpdate({"aria-invalid":true})},clearInvalid:function(){var A=this;A.callParent(arguments);A.ariaUpdate({"aria-invalid":false})}});
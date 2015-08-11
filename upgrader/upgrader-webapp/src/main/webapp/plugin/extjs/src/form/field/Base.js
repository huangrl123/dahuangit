Ext.define("Ext.form.field.Base",{extend:"Ext.Component",mixins:["Ext.form.Labelable","Ext.form.field.Field"],xtype:"field",alternateClassName:["Ext.form.Field","Ext.form.BaseField"],requires:["Ext.util.DelayedTask","Ext.XTemplate"],shrinkWrap:true,fieldSubTpl:['<input id="{id}" data-ref="inputEl" type="{type}" role="{role}" {inputAttrTpl}',' size="1"','<tpl if="name"> name="{name}"</tpl>','<tpl if="value"> value="{[Ext.util.Format.htmlEncode(values.value)]}"</tpl>','<tpl if="placeholder"> placeholder="{placeholder}"</tpl>','{%if (values.maxLength !== undefined){%} maxlength="{maxLength}"{%}%}','<tpl if="readOnly"> readonly="readonly"</tpl>','<tpl if="disabled"> disabled="disabled"</tpl>','<tpl if="tabIdx"> tabIndex="{tabIdx}"</tpl>','<tpl if="fieldStyle"> style="{fieldStyle}"</tpl>',' class="{fieldCls} {typeCls} {typeCls}-{ui} {editableCls} {inputCls}" autocomplete="off"/>',{disableFormats:true}],defaultBindProperty:"value",subTplInsertions:["inputAttrTpl"],childEls:["inputEl"],inputType:"text",invalidText:"The value in this field is invalid",fieldCls:Ext.baseCSSPrefix+"form-field",focusCls:"form-focus",dirtyCls:Ext.baseCSSPrefix+"form-dirty",checkChangeEvents:Ext.isIE&&(!document.documentMode||document.documentMode<=9)?["change","propertychange","keyup"]:["change","input","textInput","keyup","dragdrop"],ignoreChangeRe:/data\-errorqtip|style\.|className/,checkChangeBuffer:50,liquidLayout:true,readOnly:false,readOnlyCls:Ext.baseCSSPrefix+"form-readonly",validateOnBlur:true,hasFocus:false,baseCls:Ext.baseCSSPrefix+"field",fieldBodyCls:Ext.baseCSSPrefix+"field-body",maskOnDisable:false,stretchInputElFixed:true,statics:{initTip:function(){var A=this.tip;if(!A){A=this.tip=Ext.create("Ext.tip.QuickTip",{sticky:true,ui:"form-invalid"});A.tagConfig=Ext.apply({},{attribute:"errorqtip"},A.tagConfig)}},destroyTip:function(){var A=this.tip;if(A){A.destroy();delete this.tip}}},initComponent:function(){var A=this;A.callParent();A.subTplData=A.subTplData||{};A.initLabelable();A.initField();if(!A.name){A.name=A.getInputId()}if(A.readOnly){A.addCls(A.readOnlyCls)}A.addCls(Ext.baseCSSPrefix+"form-type-"+A.inputType)},getInputId:function(){return this.inputId||(this.inputId=this.id+"-inputEl")},getSubTplData:function(){var C=this,A=C.inputType,B=C.getInputId(),D;D=Ext.apply({ui:C.ui,id:B,cmpId:C.id,name:C.name||B,disabled:C.disabled,readOnly:C.readOnly,value:C.getRawValue(),type:A,fieldCls:C.fieldCls,fieldStyle:C.getFieldStyle(),tabIdx:C.tabIndex,inputCls:C.inputCls,typeCls:Ext.baseCSSPrefix+"form-"+(A==="password"?"text":A),role:C.ariaRole},C.subTplData);C.getInsertionRenderData(D,C.subTplInsertions);return D},getSubTplMarkup:function(){var A=this,E=A.getSubTplData(),C=A.getTpl("preSubTpl"),B=A.getTpl("postSubTpl"),D="";if(C){D+=C.apply(E)}D+=A.getTpl("fieldSubTpl").apply(E);if(B){D+=B.apply(E)}return D},initRenderData:function(){return Ext.applyIf(this.callParent(),this.getLabelableRenderData())},setFieldStyle:function(C){var B=this,A=B.inputEl;if(A){A.applyStyles(C)}B.fieldStyle=C},getFieldStyle:function(){var A=this.fieldStyle;return Ext.isObject(A)?Ext.DomHelper.generateStyles(A,null,true):A||""},onRender:function(){this.callParent(arguments);Ext.form.field.Base.initTip();this.renderActiveError()},isFileUpload:function(){return this.inputType==="file"},getSubmitData:function(){var A=this,C=null,B;if(!A.disabled&&A.submitValue){B=A.getSubmitValue();if(B!==null){C={};C[A.getName()]=B}}return C},getSubmitValue:function(){return this.processRawValue(this.getRawValue())},getRawValue:function(){var A=this,B=(A.inputEl?A.inputEl.getValue():Ext.valueFrom(A.rawValue,""));A.rawValue=B;return B},setRawValue:function(C){var B=this,A=B.rawValue;if(!B.transformRawValue.$nullFn){C=B.transformRawValue(C)}C=Ext.valueFrom(C,"");if(A===undefined||A!==C){B.rawValue=C;if(B.inputEl){B.bindPropertyChange(false);B.inputEl.dom.value=C;B.bindPropertyChange(true)}if(B.rendered&&B.reference){B.publishState("rawValue",C)}}return C},transformRawValue:Ext.identityFn,valueToRaw:function(A){return""+Ext.valueFrom(A,"")},rawToValue:Ext.identityFn,processRawValue:Ext.identityFn,getValue:function(){var A=this,B=A.rawToValue(A.processRawValue(A.getRawValue()));A.value=B;return B},setValue:function(B){var A=this;A.setRawValue(A.valueToRaw(B));return A.mixins.field.setValue.call(A,B)},onBoxReady:function(){var A=this;A.callParent();if(A.setReadOnlyOnBoxReady){A.setReadOnly(A.readOnly)}},onDisable:function(){var B=this,A=B.inputEl;B.callParent();if(A){A.dom.disabled=true;if(B.hasActiveError()){B.clearInvalid();B.needsValidateOnEnable=true}}},onEnable:function(){var B=this,A=B.inputEl;B.callParent();if(A){A.dom.disabled=false;if(B.needsValidateOnEnable){delete B.needsValidateOnEnable;B.forceValidation=true;B.isValid();delete B.forceValidation}}},setReadOnly:function(B){var C=this,A=C.inputEl;B=!!B;C[B?"addCls":"removeCls"](C.readOnlyCls);C.readOnly=B;if(A){A.dom.readOnly=B}else{if(C.rendering){C.setReadOnlyOnBoxReady=true}}C.fireEvent("writeablechange",C,B)},fireKey:function(A){if(A.isSpecialKey()){this.fireEvent("specialkey",this,A)}},initEvents:function(){var E=this,F=E.inputEl,I,B,G=E.checkChangeEvents,A=E.ignoreChangeRe,H=G.length,C,D;if(F){E.mon(F,Ext.supports.SpecialKeyDownRepeat?"keydown":"keypress",E.fireKey,E);I=new Ext.util.DelayedTask(E.checkChange,E);E.onChangeEvent=B=function(J){if(!(J.type=="propertychange"&&A.test(J.browserEvent.propertyName))){I.delay(E.checkChangeBuffer)}};for(C=0;C<H;C++){D=G[C];if(D==="propertychange"){E.usesPropertychange=true}E.mon(F,D,B)}}E.callParent()},doComponentLayout:function(){this.bindPropertyChange(false);this.callParent(arguments);this.bindPropertyChange(true)},bindPropertyChange:function(A){var B=this,C=B.usesPropertychange;if(C){B[A?"mon":"mun"](B.inputEl,"propertychange",B.onChangeEvent)}},onDirtyChange:function(B){var A=this;A[B?"addCls":"removeCls"](A.dirtyCls);if(A.rendered&&A.reference){A.publishState("dirty",B)}},isValid:function(){var B=this,C=B.disabled,A=B.forceValidation||!C;return A?B.validateValue(B.processRawValue(B.getRawValue())):C},validateValue:function(D){var C=this,A=C.getErrors(D),B=Ext.isEmpty(A);if(!C.preventMark){if(B){C.clearInvalid()}else{C.markInvalid(A)}}return B},markInvalid:function(B){var D=this,A=D.getActiveError(),C;D.setActiveErrors(Ext.Array.from(B));C=D.getActiveError();if(A!==C){D.setError(C)}},clearInvalid:function(){var B=this,A=B.hasActiveError();delete B.needsValidateOnEnable;B.unsetActiveError();if(A){B.setError("")}},setError:function(D){var B=this,A=B.msgTarget,C;if(B.rendered){if(A=="title"||A=="qtip"){C=A=="qtip"?"data-errorqtip":"title";B.getActionEl().dom.setAttribute(C,D||"")}else{B.updateLayout()}}},renderActiveError:function(){var B=this,A=B.hasActiveError(),C=B.invalidCls+"-field";if(B.inputEl){B.inputEl[A?"addCls":"removeCls"]([C,C+"-"+B.ui])}B.mixins.labelable.renderActiveError.call(B)},privates:{applyBind:function(E,A){var C=this,D=A&&A.value,B;B=C.callParent([E,A]);if(B.value!==D&&C.getInherited().modelValidation){C.updateValueBinding(B)}return B},applyRenderSelectors:function(){var A=this;A.callParent();if(!A.inputEl){A.inputEl=A.el.getById(A.getInputId())}},getActionEl:function(){return this.inputEl||this.el},getFocusEl:function(){return this.inputEl},initRenderTpl:function(){var A=this;if(!A.hasOwnProperty("renderTpl")){A.renderTpl=A.getTpl("labelableRenderTpl")}return A.callParent()},updateValueBinding:function(C){var B=this,A=C.value,D=C.validation;if(D&&D.autoVal){D.destroy();C.validation=null}if(A&&A.bindValidation&&!C.validation){D=A.bindValidation("setValidation",B);if(D){C.validation=D;D.autoVal=true}}}}});
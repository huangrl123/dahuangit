Ext.define("Ext.form.field.Text",{extend:"Ext.form.field.Base",alias:"widget.textfield",requires:["Ext.form.field.VTypes","Ext.form.trigger.Trigger","Ext.util.TextMetrics"],alternateClassName:["Ext.form.TextField","Ext.form.Text"],config:{hideTrigger:false,triggers:undefined},growMin:30,growMax:800,growAppend:"W",allowBlank:true,validateBlank:false,allowOnlyWhitespace:true,minLength:0,maxLength:Number.MAX_VALUE,minLengthText:"The minimum length for this field is {0}",maxLengthText:"The maximum length for this field is {0}",blankText:"This field is required",regexText:"",emptyCls:Ext.baseCSSPrefix+"form-empty-field",requiredCls:Ext.baseCSSPrefix+"form-required-field",valueContainsPlaceholder:false,ariaRole:"textbox",editable:true,repeatTriggerClick:false,triggerWrapCls:Ext.baseCSSPrefix+"form-trigger-wrap",triggerWrapFocusCls:Ext.baseCSSPrefix+"form-trigger-wrap-focus",triggerWrapInvalidCls:Ext.baseCSSPrefix+"form-trigger-wrap-invalid",fieldBodyCls:Ext.baseCSSPrefix+"form-text-field-body",inputWrapCls:Ext.baseCSSPrefix+"form-text-wrap",inputWrapFocusCls:Ext.baseCSSPrefix+"form-text-wrap-focus",inputWrapInvalidCls:Ext.baseCSSPrefix+"form-text-wrap-invalid",growCls:Ext.baseCSSPrefix+"form-text-grow",monitorTab:true,mimicing:false,childEls:["triggerWrap","inputWrap"],preSubTpl:['<div id="{cmpId}-triggerWrap" data-ref="triggerWrap" class="{triggerWrapCls} {triggerWrapCls}-{ui}">','<div id={cmpId}-inputWrap data-ref="inputWrap" class="{inputWrapCls} {inputWrapCls}-{ui}">'],postSubTpl:["</div>",'<tpl for="triggers">{[values.renderTrigger(parent)]}</tpl>',"</div>"],initComponent:function(){var A=this,B=A.emptyCls;if(A.allowOnlyWhitespace===false){A.allowBlank=false}if(A.size){Ext.log.warn('Ext.form.field.Text "size" config was deprecated in Ext 5.0. Please specify a "width" or use a layout instead.')}if(A.size){A.defaultBodyWidth=A.size*6.5+20}if(!A.onTrigger1Click){A.onTrigger1Click=A.onTriggerClick}A.callParent();A.setReadOnly(A.readOnly);A.fieldFocusCls=A.baseCls+"-focus";A.emptyUICls=B+" "+B+"-"+A.ui;A.addStateEvents("change");A.setGrowSizePolicy()},setGrowSizePolicy:function(){if(this.grow){this.shrinkWrap|=1}},initEvents:function(){var B=this,A=B.inputEl;this.mon(Ext.GlobalEvents,"beforefocus",this.onOtherFocus,this);B.callParent();if(B.selectOnFocus||B.emptyText){B.mon(A,"mousedown",B.onMouseDown,B)}if(B.maskRe||(B.vtype&&B.disableKeyFilter!==true&&(B.maskRe=Ext.form.field.VTypes[B.vtype+"Mask"]))){B.mon(A,"keypress",B.filterKeys,B)}if(B.enableKeyEvents){B.mon(A,{scope:B,keyup:B.onKeyUp,keydown:B.onKeyDown,keypress:B.onKeyPress})}},isEqual:function(A,B){return this.isEqualAsString(A,B)},onChange:function(B,A){this.callParent(arguments);this.autoSize()},getSubTplData:function(){var C=this,D=C.getRawValue(),B=C.emptyText&&D.length<1,A=C.maxLength,E;if(C.enforceMaxLength){if(A===Number.MAX_VALUE){A=undefined}}else{A=undefined}if(B){if(Ext.supports.Placeholder){E=C.emptyText}else{D=C.emptyText;C.valueContainsPlaceholder=true}}return Ext.apply(C.callParent(),{triggerWrapCls:C.triggerWrapCls,inputWrapCls:C.inputWrapCls,triggers:C.orderedTriggers,maxLength:A,readOnly:!C.editable||C.readOnly,placeholder:E,value:D,fieldCls:C.fieldCls+((B&&(E||D))?" "+C.emptyUICls:"")+(C.allowBlank?"":" "+C.requiredCls)})},onRender:function(){var C=this,D=C.getTriggers(),B=[],E,A;if(Ext.supports.FixedTableWidthBug){C.el._needsTableWidthFix=true}C.callParent();if(D){this.invokeTriggers("onFieldRender");for(E in D){B.push(D[E].el)}A=C.triggerEl=C.triggerCell=new Ext.CompositeElement(B,true)}C.inputCell=C.inputWrap},afterRender:function(){var A=this;A.autoSize();A.callParent();this.invokeTriggers("afterFieldRender")},onMouseDown:function(){var A=this;if(!A.hasFocus){A.mon(A.inputEl,"mouseup",Ext.emptyFn,A,{single:true,preventDefault:true})}},applyTriggers:function(K){var H=this,D=H.getHideTrigger(),B=H.readOnly,F=H.orderedTriggers=[],J=H.repeatTriggerClick,G,C,A,I,E;if(H.rendered){Ext.Error.raise("Cannot set triggers after field has already been rendered.")}if((H.triggerCls&&!K)||H.trigger1Cls){Ext.log.warn("Ext.form.field.Text: 'triggerCls' and 'trigger<n>Cls' are deprecated.  Use 'triggers' instead.")}if(!K){K={};if(H.triggerCls&&!H.trigger1Cls){H.trigger1Cls=H.triggerCls}for(E=1;I=H["trigger"+E+"Cls"];E++){K["trigger"+E]={cls:I,extraCls:Ext.baseCSSPrefix+"trigger-index-"+E,handler:"onTrigger"+E+"Click",compat4Mode:true,scope:H}}}for(G in K){if(K.hasOwnProperty(G)){C=K[G];C.field=H;C.id=G;if((B&&C.hideOnReadOnly!==false)||(D&&C.hidden!==false)){C.hidden=true}if(J&&(C.repeatClick!==false)){C.repeatClick=true}A=K[G]=Ext.form.trigger.Trigger.create(C);F.push(A)}}Ext.Array.sort(F,Ext.form.trigger.Trigger.weightComparator);return K},invokeTriggers:function(B,F){var C=this,D=C.getTriggers(),A,E;if(D){for(A in D){if(D.hasOwnProperty(A)){E=D[A];E[B].apply(E,F||[])}}}},getTrigger:function(A){return this.getTriggers()[A]},updateHideTrigger:function(A){if(this.rendered){this.invokeTriggers(A?"hide":"show")}},setEditable:function(A){var B=this;B.editable=A;if(B.rendered){B.setReadOnlyAttr(!A||B.readOnly)}},setReadOnly:function(A){var B=this,D=B.getTriggers(),C=B.getHideTrigger(),E,F;A=!!A;B.callParent([A]);if(B.rendered){B.setReadOnlyAttr(A||!B.editable);if(D){for(F in D){E=D[F];if(E.hideOnReadOnly===true||(E.hideOnReadOnly!==false&&!C)){E[A?"hide":"show"].call(E)}}}}},setReadOnlyAttr:function(B){var C=this,D="readonly",A=C.inputEl.dom;if(B){A.setAttribute(D,D)}else{A.removeAttribute(D)}},processRawValue:function(B){var A=this,C=A.stripCharsRe,D;if(C){D=B.replace(C,"");if(D!==B){A.setRawValue(D);B=D}}return B},onDisable:function(){this.callParent();if(Ext.isIE){this.inputEl.dom.unselectable="on"}},onEnable:function(){this.callParent();if(Ext.isIE){this.inputEl.dom.unselectable=""}},onKeyDown:function(A){this.fireEvent("keydown",this,A)},onKeyUp:function(A){this.fireEvent("keyup",this,A)},onKeyPress:function(A){this.fireEvent("keypress",this,A)},reset:function(){this.callParent();this.applyEmptyText()},applyEmptyText:function(){var B=this,C=B.emptyText,A;if(B.rendered&&C){A=B.getRawValue().length<1&&!B.hasFocus;if(Ext.supports.Placeholder){B.inputEl.dom.placeholder=C}else{if(A){B.setRawValue(C);B.valueContainsPlaceholder=true}}if(A){B.inputEl.addCls(B.emptyUICls)}B.autoSize()}},afterFirstLayout:function(){this.callParent();if(Ext.isIE&&this.disabled){var A=this.inputEl;if(A){A.dom.unselectable="on"}}},toggleInvalidCls:function(A){var B=A?"addCls":"removeCls";this.callParent();this.triggerWrap[B](this.triggerWrapInvalidCls);this.inputWrap[B](this.inputWrapInvalidCls)},beforeFocus:function(){var C=this,A=C.inputEl,D=C.emptyText,B;C.callParent(arguments);if((D&&!Ext.supports.Placeholder)&&(A.dom.value===C.emptyText&&C.valueContainsPlaceholder)){C.setRawValue("");B=true;A.removeCls(C.emptyUICls);C.valueContainsPlaceholder=false}else{if(Ext.supports.Placeholder){A.removeCls(C.emptyUICls)}}if(C.selectOnFocus||B){if(Ext.isWebKit){if(!C.inputFocusTask){C.inputFocusTask=new Ext.util.DelayedTask(C.focusInput,C)}C.inputFocusTask.delay(1)}else{C.focusInput()}}},focusInput:function(){var A=this.inputEl;if(A){A=A.dom;if(A){A.select()}}},onFocus:function(A){var B=this;B.otherFocused=false;B.callParent(arguments);if(B.emptyText){B.autoSize()}if(!B.mimicing){B.addCls(B.fieldFocusCls);B.triggerWrap.addCls(B.triggerWrapFocusCls);B.inputWrap.addCls(B.inputWrapFocusCls);B.invokeTriggers("onFieldFocus",[A]);B.mimicing=true;B.mon(Ext.getDoc(),"mousedown",B.mimicBlur,B,{delay:10});if(B.monitorTab){B.on("specialkey",B.checkTab,B)}}},onBlur:function(){if(this.blurring||this.otherFocused){this.triggerBlur();this.otherFocused=false}},onOtherFocus:function(A){this.otherFocused=(this.hasFocus&&!this.bodyEl.contains(A))},checkTab:function(B,A){if(!this.ignoreMonitorTab&&A.getKey()===A.TAB){this.triggerBlur()}},mimicBlur:function(A){if(!this.isDestroyed&&!this.bodyEl.contains(A.target)){this.triggerBlur(A)}},triggerBlur:function(A){var B=this;B.mimicing=false;B.mun(Ext.getDoc(),"mousedown",B.mimicBlur,B);if(B.monitorTab&&B.inputEl){B.un("specialkey",B.checkTab,B)}Ext.form.field.Text.superclass.onBlur.call(B,A);B.removeCls(B.fieldFocusCls);B.triggerWrap.removeCls(B.triggerWrapFocusCls);B.inputWrap.removeCls(B.inputWrapFocusCls);B.invokeTriggers("onFieldBlur",[A])},postBlur:function(){var A=this.inputFocusTask;this.callParent(arguments);this.applyEmptyText();if(A){A.cancel()}},filterKeys:function(B){if(B.ctrlKey&&!B.altKey){return}var A=B.getKey(),C=String.fromCharCode(B.getCharCode());if((Ext.isGecko||Ext.isOpera)&&(B.isNavKeyPress()||A===B.BACKSPACE||(A===B.DELETE&&B.button===-1))){return}if((!Ext.isGecko&&!Ext.isOpera)&&B.isSpecialKey()&&!C){return}if(!this.maskRe.test(C)){B.stopEvent()}},getState:function(){return this.addPropertyToState(this.callParent(),"value")},applyState:function(A){this.callParent(arguments);if(A.hasOwnProperty("value")){this.setValue(A.value)}},getRawValue:function(){var A=this,B=A.callParent();if(B===A.emptyText&&A.valueContainsPlaceholder){B=""}return B},setValue:function(C){var B=this,A=B.inputEl;if(A&&B.emptyText&&!Ext.isEmpty(C)){A.removeCls(B.emptyUICls);B.valueContainsPlaceholder=false}B.callParent(arguments);B.applyEmptyText();return B},getErrors:function(E){var I=this,H=I.callParent(arguments),K=I.validator,B=I.vtype,F=Ext.form.field.VTypes,J=I.regex,A=Ext.String.format,C,G,D;E=E||I.processRawValue(I.getRawValue());if(Ext.isFunction(K)){C=K.call(I,E);if(C!==true){H.push(C)}}G=I.allowOnlyWhitespace?E:Ext.String.trim(E);if(G.length<1||(E===I.emptyText&&I.valueContainsPlaceholder)){if(!I.allowBlank){H.push(I.blankText)}if(!I.validateBlank){return H}D=true}if(!D&&E.length<I.minLength){H.push(A(I.minLengthText,I.minLength))}if(E.length>I.maxLength){H.push(A(I.maxLengthText,I.maxLength))}if(B){if(!F[B](E,I)){H.push(I.vtypeText||F[B+"Text"])}}if(J&&!J.test(E)){H.push(I.regexText||I.invalidText)}return H},selectText:function(A,C){var E=this,B=E.getRawValue(),F=true,D=E.inputEl.dom,G,H;if(B.length>0){A=A===G?0:A;C=C===G?B.length:C;if(D.setSelectionRange){D.setSelectionRange(A,C)}else{if(D.createTextRange){H=D.createTextRange();H.moveStart("character",A);H.moveEnd("character",C-B.length);H.select()}}F=Ext.isGecko||Ext.isOpera}if(F){E.focus()}},autoSize:function(){var E=this,I,C,A,H,F,G,D,B;if(E.grow&&E.rendered){F=E.inputEl;I=E.getTriggers();H=0;B=Ext.util.Format.htmlEncode(F.dom.value||(E.hasFocus?"":E.emptyText)||"");B+=E.growAppend;for(C in I){H+=I[C].el.getWidth()}D=F.getTextWidth(B)+H+E.inputWrap.getBorderWidth("lr")+E.triggerWrap.getBorderWidth("lr");D=Math.min(Math.max(D,E.growMin),E.growMax);E.bodyEl.setWidth(D);E.updateLayout();E.fireEvent("autosize",E,D)}},onDestroy:function(){var A=this;A.invokeTriggers("destroy");Ext.destroy(A.triggerRepeater);A.callParent();if(A.inputFocusTask){A.inputFocusTask.cancel();A.inputFocusTask=null}},onTriggerClick:Ext.emptyFn,privates:{getTdType:function(){return"textfield"}},deprecated:{5:{methods:{getTriggerWidth:function(){var A=this.getTriggers(),B=0,C;if(A&&this.rendered){for(C in A){if(A.hasOwnProperty(C)){B+=A[C].el.getWidth()}}}return B}}}}});
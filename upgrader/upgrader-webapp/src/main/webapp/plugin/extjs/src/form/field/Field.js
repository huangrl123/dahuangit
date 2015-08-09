Ext.define("Ext.form.field.Field",{mixinId:"field",isFormField:true,config:{validation:null},disabled:false,submitValue:true,validateOnChange:true,suspendCheckChange:0,dirty:false,initField:function(){this.initValue();var B=["tagName","nodeName","children","childNodes"],A=this.name;if(A&&Ext.Array.indexOf(B,A)>-1){Ext.log.warn(['It is recommended to not use "',A,'" as a field name, because it ',"can cause naming collisions during form submission."].join(""))}},initValue:function(){var A=this;A.suspendCheckChange++;A.setValue(A.value);A.suspendCheckChange--;A.originalValue=A.lastValue=A.getValue()},getFieldIdentifier:function(){return this.isEditorComponent?this.dataIndex:this.name},getName:function(){return this.name},getValue:function(){return this.value},setValue:function(B){var A=this;A.value=B;A.checkChange();return A},isEqual:function(A,B){return String(A)===String(B)},isEqualAsString:function(A,B){return String(Ext.valueFrom(A,""))===String(Ext.valueFrom(B,""))},getSubmitData:function(){var A=this,B=null;if(!A.disabled&&A.submitValue){B={};B[A.getName()]=""+A.getValue()}return B},getModelData:function(C,D){var B=this,A=null;if(!B.disabled&&(B.submitValue||!D)){A={};A[B.getFieldIdentifier()]=B.getValue()}return A},reset:function(){var A=this;A.beforeReset();A.setValue(A.originalValue);A.clearInvalid();delete A.wasValid},beforeReset:Ext.emptyFn,resetOriginalValue:function(){this.originalValue=this.getValue();this.checkDirty()},checkChange:function(){var C=this,A,B;if(!C.suspendCheckChange){A=C.getValue();B=C.lastValue;if(!C.isDestroyed&&C.didValueChange(A,B)){C.lastValue=A;C.fireEvent("change",C,A,B);C.onChange(A,B)}}},didValueChange:function(B,A){return !this.isEqual(B,A)},onChange:function(B){var A=this;if(A.validateOnChange){A.validate()}A.checkDirty();A.publishValue(B)},publishValue:function(A){if(this.rendered){this.publishState("value",A)}},isDirty:function(){var A=this;return !A.disabled&&!A.isEqual(A.getValue(),A.originalValue)},checkDirty:function(){var A=this,B=A.isDirty();if(B!==A.wasDirty){A.dirty=B;A.fireEvent("dirtychange",A,B);A.onDirtyChange(B);A.wasDirty=B}},onDirtyChange:Ext.emptyFn,getErrors:function(C){var A=[],B=this.getValidation();if(B&&B!==true){A.push(B)}return A},isValid:function(){var A=this;return A.disabled||Ext.isEmpty(A.getErrors())},validate:function(){var B=this,A=B.isValid();if(A!==B.wasValid){B.wasValid=A;B.fireEvent("validitychange",B,A)}return A},batchChanges:function(A){try{this.suspendCheckChange++;A()}catch(B){throw B}finally{this.suspendCheckChange--}this.checkChange()},isFileUpload:function(){return false},extractFileInput:function(){return null},markInvalid:Ext.emptyFn,clearInvalid:Ext.emptyFn,updateValidation:function(){this.validate()}});
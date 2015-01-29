Ext.define("Ext.app.bind.Binding",{extend:"Ext.app.bind.BaseBinding",constructor:function(C,A,E,D){var B=this;B.callParent([C.owner,A,E,D]);B.stub=C;B.depth=C.depth;if(!C.isLoading()&&!C.scheduled){B.schedule()}},destroy:function(C){var B=this,A=B.stub;if(A&&!C){A.unbind(B);B.stub=null}B.callParent()},bindValidation:function(A,C){var B=this.stub;return B&&B.bindValidation(A,C)},getFullName:function(){return this.fullName||(this.fullName="@("+this.stub.getFullName()+")")},getValue:function(){var B=this,A=B.stub,C=A&&A.getValue();if(B.transform){C=B.transform(C)}return C},isLoading:function(){var A=this.stub;return A&&A.isLoading()},isReadOnly:function(){var A=this.stub,B=this.options,C;if(A&&A.set&&!(B&&B.twoWay===false)){C=A.formula;if(!C||C.set){return false}}return true},refresh:function(){},setValue:function(B){if(this.isReadOnly()){Ext.Error.raise("Cannot setValue on a readonly binding")}var A=this.stub,C=A.formula;if(C){C.set.call(A.owner,B)}else{A.set(B)}},privates:{getDataObject:function(){var A=this.stub;return A&&A.getDataObject()},getRawValue:function(){var B=this,A=B.stub,C=A&&A.getRawValue();if(B.transform){C=B.transform(C)}return C},isDescendantOf:function(B){var A=this.stub;return A?(B===A)||A.isDescendantOf(B):false},react:function(){this.notify(this.getValue())},schedule:function(){if(!this.stub.scheduled){this.callParent()}},sort:function(){var A=this.stub;A.scheduler.sortItem(A)}}});
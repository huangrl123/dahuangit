Ext.define("Ext.form.field.Picker",{extend:"Ext.form.field.Text",alias:"widget.pickerfield",alternateClassName:"Ext.form.Picker",requires:["Ext.util.KeyNav"],config:{triggers:{picker:{handler:"onTriggerClick",scope:"this"}}},matchFieldWidth:true,pickerAlign:"tl-bl?",openCls:Ext.baseCSSPrefix+"pickerfield-open",editable:true,applyTriggers:function(C){var A=this,B=C.picker;if(!B.cls){B.cls=A.triggerCls}return A.callParent([C])},initEvents:function(){var A=this;A.callParent();A.keyNav=new Ext.util.KeyNav(A.inputEl,{down:A.onDownArrow,esc:{handler:A.onEsc,scope:A,defaultEventAction:false},scope:A,forceKeyDown:true});if(!A.editable){A.mon(A.inputEl,"click",A.onTriggerClick,A)}if(Ext.isGecko){A.inputEl.dom.setAttribute("autocomplete","off")}},onEsc:function(A){if(Ext.isIE){A.preventDefault()}if(this.isExpanded){this.collapse();A.stopEvent()}},onDownArrow:function(){if(!this.isExpanded){this.onTriggerClick()}},expand:function(){var A=this,C,B,D;if(A.rendered&&!A.isExpanded&&!A.isDestroyed){A.expanding=true;C=A.bodyEl;B=A.getPicker();D=A.collapseIf;B.show();A.isExpanded=true;A.alignPicker();C.addCls(A.openCls);A.mon(Ext.getDoc(),{mousewheel:D,mousedown:D,scope:A});Ext.on("resize",A.alignPicker,A);A.fireEvent("expand",A);A.onExpand();delete A.expanding}},onExpand:Ext.emptyFn,alignPicker:function(){var B=this,A,C=B.getPicker();if(B.isExpanded){if(B.matchFieldWidth){A=B.bodyEl.getWidth();C.setWidth(A)}if(C.isFloating()){B.doAlign()}}},doAlign:function(){var A=this,B=A.picker,C="-above",D;A.picker.alignTo(A.triggerWrap,A.pickerAlign,A.pickerOffset);D=B.el.getY()<A.inputEl.getY();A.bodyEl[D?"addCls":"removeCls"](A.openCls+C);B[D?"addCls":"removeCls"](B.baseCls+C)},collapse:function(){var B=this;if(B.isExpanded&&!B.isDestroyed&&!B.destroying){var E=B.openCls,C=B.picker,F=Ext.getDoc(),A=B.collapseIf,D="-above";C.hide();B.isExpanded=false;B.bodyEl.removeCls([E,E+D]);C.el.removeCls(C.baseCls+D);F.un("mousewheel",A,B);F.un("mousedown",A,B);Ext.un("resize",B.alignPicker,B);B.fireEvent("collapse",B);B.onCollapse()}},onCollapse:Ext.emptyFn,collapseIf:function(A){var B=this;if(!B.isDestroyed&&!A.within(B.bodyEl,false,true)&&!B.owns(A.target)){B.collapse()}},getPicker:function(){var A=this;return A.picker||(A.picker=A.createPicker())},getRefItems:function(){var A=[];if(this.picker){A[0]=this.picker}return A},createPicker:Ext.emptyFn,onTriggerClick:function(){var A=this;if(!A.readOnly&&!A.disabled){if(A.isExpanded){A.collapse()}else{A.expand()}if(!Ext.supports.TouchEvents){A.inputEl.focus()}}},onOtherFocus:function(A){if(this.hasFocus&&!this.owns(A)){this.callParent([A])}},triggerBlur:function(){this.callParent(arguments);this.collapse()},mimicBlur:function(A){var B=this,C=B.picker;if(!C||!B.owns(A.target)){B.callParent(arguments)}else{B.inputEl.focus()}},beforeDestroy:function(){var A=this,B=A.picker;A.collapse();A.callParent();Ext.un("resize",A.alignPicker,A);Ext.destroy(A.keyNav,B);if(B){delete A.picker;delete B.pickerField}}});
Ext.define("Ext.aria.container.Container",{override:"Ext.container.Container",statics:{ariaSectionContainers:{region:true,dialog:true,alertdialog:true,document:true,application:true}},ariaFocusableContainerCls:Ext.baseCSSPrefix+"aria-focusable-container",initCls:function(){var B=this,A;A=B.callParent();if(B.ariaIsFocusableContainer()){A.push(B.ariaFocusableContainerCls)}return A},onBoxReady:function(){var A=this,B=A.scrollFlags;A.callParent(arguments);if(B.y||B.x){A.getTargetEl().on("scroll",function(){Ext.globalEvents.fireEvent("afterlayout",A)})}},addFocusListener:function(){var A=this;if(A.ariaIsFocusableContainer()){A.callParent(arguments)}},ariaIsFocusableContainer:function(){return this.ariaIsSection()||this.focusableContainer},ariaGetAfterRenderAttributes:function(){var B=this,A,C;A=B.callParent();if(!B.ariaSkipContainerTitleCheck&&!Ext.theme){B.ariaCheckContainerTitle(A)}return A},ariaCheckContainerTitle:function(A){var B=this;if(B.ariaIsFocusableContainer()){if(!A["aria-labelledby"]){title=B.title||B.ariaLabel||A["aria-label"];if(!title){if(B.ariaIsSection()){Ext.log.error("Container "+B.id+" has ARIA role of '"+B.ariaRole+"', but does not have a title. WAI-ARIA requires that all focusable containers have a title.")}else{if(B.focusableContainer){Ext.log.error("Container "+B.id+" is set to be focusable, but does not have a title. WAI-ARIA requires that all focusable containers have a title.")}}}delete A["aria-label"];A.title=title}}},ariaGetFocusCls:function(){var A=this;return A.ariaIsFocusableContainer()?A.ariaFocusCls:""},ariaFirstChild:function(){var A=this.ariaGetFocusItems(false);return A[0]||null},ariaLastChild:function(){var A=this.ariaGetFocusItems(true),B=A.length;return B?A[B-1]:null},ariaGetFocusItems:function(B){var E=this.getRefItems(),D=E.length,F=[],C,A;for(A=0;A<D;A++){C=E[A];if(C.isVisible()){if(C.focusListenerAdded){if(C.isFocusable()){F.push(C)}}else{if(C.isContainer){F=F.concat(C.ariaGetFocusItems(B))}}}}return F},ariaIsSection:function(){return Ext.container.Container.ariaSectionContainers[this.ariaRole]}});
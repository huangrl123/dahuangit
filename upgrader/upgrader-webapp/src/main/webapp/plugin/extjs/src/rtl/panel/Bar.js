Ext.define("Ext.rtl.panel.Bar",{override:"Ext.panel.Bar",rtlPositions:{top:"top",right:"left",bottom:"bottom",left:"right"},_rtlRotationClasses:{1:Ext.baseCSSPrefix+"title-rotate-left",2:Ext.baseCSSPrefix+"title-rotate-right"},_rtlRotationAngles:{1:270,2:90},onAdded:function(C,B,D){var A=this;if(A.isParentRtl()){A._rotationClasses=A._rtlRotationClasses;A._rotationAngles=A._rtlRotationAngles}this.callParent([C,B,D])},privates:{getDockName:function(){var A=this,B=A.dock;return A.isParentRtl()?A.rtlPositions[B]:B}}});
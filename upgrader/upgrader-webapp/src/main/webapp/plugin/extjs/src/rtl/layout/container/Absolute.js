Ext.define("Ext.rtl.layout.container.Absolute",{override:"Ext.layout.container.Absolute",adjustWidthAnchor:function(D,B){if(this.owner.getInherited().rtl){var A=this.targetPadding,C=B.getStyle("right");return D-C+A.right}else{return this.callParent(arguments)}}});
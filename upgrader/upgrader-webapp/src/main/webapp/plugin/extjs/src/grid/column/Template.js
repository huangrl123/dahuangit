Ext.define("Ext.grid.column.Template",{extend:"Ext.grid.column.Column",alias:["widget.templatecolumn"],requires:["Ext.XTemplate"],alternateClassName:"Ext.grid.TemplateColumn",initComponent:function(){var A=this;A.tpl=(!Ext.isPrimitive(A.tpl)&&A.tpl.compile)?A.tpl:new Ext.XTemplate(A.tpl);A.hasCustomRenderer=true;A.callParent(arguments)},defaultRenderer:function(B,A,C){var D=Ext.apply({},C.data,C.getAssociatedData());return this.tpl.apply(D)},updater:function(B,A){B.firstChild.innerHTML=Ext.grid.column.CheckColumn.prototype.defaultRenderer.call(this,A)}});
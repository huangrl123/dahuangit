Ext.define("Ext.toolbar.TextItem",{extend:"Ext.toolbar.Item",requires:["Ext.XTemplate"],alias:"widget.tbtext",alternateClassName:"Ext.Toolbar.TextItem",text:"",renderTpl:"{text}",baseCls:Ext.baseCSSPrefix+"toolbar-text",ariaRole:null,beforeRender:function(){var A=this;A.callParent();Ext.apply(A.renderData,{text:A.text})},setText:function(B){var A=this;A.text=B;if(A.rendered){A.el.setHtml(B);A.updateLayout()}}});
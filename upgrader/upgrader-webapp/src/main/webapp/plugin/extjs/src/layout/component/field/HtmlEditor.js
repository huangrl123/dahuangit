Ext.define("Ext.layout.component.field.HtmlEditor",{extend:"Ext.layout.component.field.FieldContainer",alias:["layout.htmleditor"],type:"htmleditor",naturalHeight:150,naturalWidth:300,beginLayout:function(B){var C=this.owner,A;if(Ext.isGecko){A=C.textareaEl.dom;this.lastValue=A.value;A.value=""}this.callParent(arguments);B.toolbarContext=B.context.getCmp(C.toolbar);B.inputCmpContext=B.context.getCmp(C.inputCmp);B.bodyCellContext=B.getEl("bodyEl");B.textAreaContext=B.getEl("textareaEl");B.iframeContext=B.getEl("iframeEl")},beginLayoutCycle:function(E){var D=this,B=E.widthModel,C=E.heightModel,G=D.owner,A=G.iframeEl,F=G.textareaEl;D.callParent(arguments);if(B.shrinkWrap){A.setStyle("width","");F.setStyle("width","")}else{if(B.natural){E.bodyCellContext.setWidth(D.naturalWidth)}}if(C.natural||C.shrinkWrap){A.setHeight(D.naturalHeight);F.setHeight(D.naturalHeight)}},finishedLayout:function(){var A=this.owner;this.callParent(arguments);if(Ext.isGecko){A.textareaEl.dom.value=this.lastValue}},publishOwnerWidth:function(A,B){this.callParent(arguments);B-=A.inputCmpContext.getBorderInfo().width;A.textAreaContext.setWidth(B);A.iframeContext.setWidth(B)},publishInnerWidth:function(D,E){var A=D.inputCmpContext.getBorderInfo().width,B=Ext.isIE8,C=D.widthModel.natural;this.callParent(arguments);E=D.bodyCellContext.props.width-A;if(C){if(B){E-=2}D.textAreaContext.setWidth(E);D.iframeContext.setWidth(E)}else{if(B){D.textAreaContext.setWidth(E)}}},publishInnerHeight:function(C,A){var B=C.toolbarContext.getProp("height");this.callParent(arguments);A=C.bodyCellContext.props.height;if(B!==undefined){A-=B+C.inputCmpContext.getFrameInfo().height;if(Ext.isIE8){A-=2}C.iframeContext.setHeight(A);C.textAreaContext.setHeight(A)}else{this.done=false}}});
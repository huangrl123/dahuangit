Ext.define("Ext.aria.tab.Tab",{override:"Ext.tab.Tab",closeText:"closable",initKeyNav:function(){var A=this;A.keyNav=new Ext.util.KeyNav(A.el,{enter:A.onEnterKey,space:A.onEnterKey,del:A.onDeleteKey,scope:A})},ariaGetAfterRenderAttributes:function(){var B=this,A;A=B.callParent(arguments);A["aria-selected"]=!!B.active;if(B.card&&B.card.getEl()){A["aria-controls"]=B.card.getEl().id}return A}});
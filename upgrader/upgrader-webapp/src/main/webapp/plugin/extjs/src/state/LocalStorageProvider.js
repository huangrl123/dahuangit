Ext.define("Ext.state.LocalStorageProvider",{extend:"Ext.state.Provider",requires:["Ext.util.LocalStorage"],alias:"state.localstorage",constructor:function(){var A=this;A.callParent(arguments);A.store=A.getStorageObject();if(A.store){A.state=A.readLocalStorage()}else{A.state={}}},readLocalStorage:function(){var B=this.store,E={},D=B.getKeys(),C=D.length,A;while(C--){A=D[C];E[A]=this.decodeValue(B.getItem(A))}return E},set:function(B,C){var A=this;A.clear(B);if(C!=null){A.store.setItem(B,A.encodeValue(C));A.callParent(arguments)}},clear:function(A){this.store.removeItem(A);this.callParent(arguments)},getStorageObject:function(){var B=this.prefix,C=B,A=C.length-1;if(C.charAt(A)==="-"){C=C.substring(0,A)}return new Ext.util.LocalStorage({id:C,prefix:B})}});
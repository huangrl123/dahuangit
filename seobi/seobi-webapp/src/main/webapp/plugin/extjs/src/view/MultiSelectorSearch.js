Ext.define("Ext.view.MultiSelectorSearch",{extend:"Ext.panel.Panel",xtype:"multiselector-search",layout:"fit",floating:true,resizable:true,minWidth:200,minHeight:200,border:true,defaultListenerScope:true,referenceHolder:true,searchText:"Search...",initComponent:function(){var D=this,G=D.owner,F=D.makeItems(),A,C,B,E;D.dockedItems=D.makeDockedItems();D.items=F;E=Ext.data.StoreManager.lookup(D.store);for(A=F.length;A--;){if((C=F[A]).xtype==="grid"){C.store=E;C.isSearchGrid=true;C.selModel=C.selModel||{selType:"checkboxmodel",pruneRemoved:false,mode:"SIMPLE",listeners:{selectionchange:"onSelectionChange"}};Ext.merge(C,D.grid);if(!C.columns){C.hideHeaders=true;C.columns=[{flex:1,dataIndex:D.field}]}break}}D.callParent();B=D.getOwnerStore().getRange();if(!G.convertSelectionRecord.$nullFn){for(A=B.length;A--;){B[A]=G.convertSelectionRecord(B[A])}}if(E.isLoading()||(E.loadCount===0&&!E.getCount())){E.on("load",function(){if(!D.isDestroyed){D.selectRecords(B)}},null,{single:true})}else{D.selectRecords(B)}},getOwnerStore:function(){return this.owner.getStore()},afterShow:function(){var A=this.lookupReference("searchField");this.callParent(arguments);if(A){A.focus()}},getSearchStore:function(){var A=this.lookupReference("searchGrid");return A.getStore()},makeDockedItems:function(){return[{xtype:"textfield",reference:"searchField",dock:"top",hideFieldLabel:true,emptyText:this.searchText,triggers:{clear:{cls:"x-form-clear-trigger",handler:"onClearSearch",hidden:true}},listeners:{change:"onSearchChange",buffer:300}}]},makeItems:function(){return[{xtype:"grid",reference:"searchGrid",trailingBufferZone:2,leadingBufferZone:2,viewConfig:{deferEmptyText:false,emptyText:"No results."}}]},selectRecords:function(A){var B=this.lookupReference("searchGrid");return B.getSelectionModel().select(A)},deselectRecords:function(A){var B=this.lookupReference("searchGrid");return B.getSelectionModel().deselect(A)},search:function(B){var A=this,C=A.searchFilter,D=A.getSearchStore().getFilters();if(B){D.beginUpdate();if(C){C.setValue(B)}else{A.searchFilter=C=new Ext.util.Filter({id:"search",property:A.field,value:B})}D.add(C);D.endUpdate()}else{if(C){D.remove(C)}}},privates:{onClearSearch:function(){var A=this.lookupReference("searchField");A.setValue(null);A.focus()},onSearchChange:function(C){var A=C.getValue(),B=C.getTrigger("clear");B.setHidden(!A);this.search(A)},onSelectionChange:function(A,F){var B=this.owner,I=B.getStore(),C=I.data,E=0,K={},D,G,H,J;for(G=F.length;G--;){J=F[G];H=J.id;K[H]=J;if(!C.containsKey(H)){(D||(D=[])).push(B.convertSearchRecord(J))}}for(G=C.length;G--;){J=C.getAt(G);if(!K[J.id]){(E||(E=[])).push(J)}}if(D||E){C.splice(C.length,E,D)}}}});
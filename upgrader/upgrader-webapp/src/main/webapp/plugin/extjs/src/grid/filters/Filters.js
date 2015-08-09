Ext.define("Ext.grid.filters.Filters",{extend:"Ext.plugin.Abstract",requires:["Ext.grid.filters.filter.*"],alias:"plugin.gridfilters",pluginId:"gridfilters",defaultFilterTypes:{"boolean":"boolean","int":"number",date:"date",number:"number"},filterCls:Ext.baseCSSPrefix+"grid-filters-filtered-column",menuFilterText:"Filters",showMenu:true,stateId:undefined,init:function(D){var B=this,A,C;Ext.Assert.falsey(B.grid);B.grid=D;D.filters=B;if(B.grid.normalGrid){B.isLocked=true}D.clearFilters=B.clearFilters.bind(B);A=D.store;C=D.headerCt;C.on({scope:B,add:B.onAdd,menucreate:B.onMenuCreate});D.on({scope:B,beforedestroy:B.destroy,beforereconfigure:B.onBeforeReconfigure,reconfigure:B.onReconfigure});B.bindStore(A);if(D.stateful){A.statefulFilters=true}B.initColumns()},initColumns:function(){var A,C,F,E,D,B;F=this.grid.columnManager.getColumns();for(A=0,C=F.length;A<C;A++){E=F[A];D=E.filter;if(D&&!D.isGridFilter){if(!B){B=this.grid.store.getFilters();B.beginUpdate()}this.createColumnFilter(E)}}if(B){B.endUpdate()}},createColumnFilter:function(G){var C=this,E=G.filter,D={column:G,grid:C.grid,owner:C},F,B,A;if(Ext.isString(E)){D.type=E}else{Ext.apply(D,E)}if(!D.type){B=C.store.model;F=B&&B.getField(G.dataIndex);A=F&&F.type;D.type=(A&&C.defaultFilterTypes[A])||G.defaultFilterType||"string"}return G.filter=Ext.Factory.gridFilter(D)},onAdd:function(A,D,B){var C=D.filter;if(C&&!C.isGridFilter){this.createColumnFilter(D)}},onMenuCreate:function(A,B){B.on({beforeshow:this.onMenuBeforeShow,scope:this})},onMenuBeforeShow:function(F){var C=this,B,D,A,E;if(C.showMenu){if(!C.menuItems){C.menuItems={}}A=F.up("grid");E=A.id;B=C.menuItems[E];if(!B||B.isDestroyed){B=C.createMenuItem(F,E)}C.activeFilterMenuItem=B;D=C.getMenuFilter(A.headerCt);if(D){D.showMenu(B)}B.setVisible(!!D);C.sep.setVisible(!!D)}},createMenuItem:function(C,B){var A=this;A.sep=C.add("-");return A.menuItems[B]=C.add({checked:false,itemId:"filters",text:A.menuFilterText,listeners:{scope:A,checkchange:A.onCheckChange}})},destroy:function(){Ext.destroyMembers(this,"menuItem","sep");this.callParent()},bindStore:function(A){var B=this;if(A){if(B.store){B.store.destroyStore();B.store.getFilters().un("remove",B.onFilterRemove,B)}B.local=!A.remoteFilter;A.getFilters().on("remove",B.onFilterRemove,B)}B.store=A},onFilterRemove:function(D,B){var E=B.items.length,F=this.grid.columnManager,A,C,G;for(A=0;A<E;A++){C=B.items[A];G=F.getHeaderByDataIndex(C.getProperty()).filter;if(!G.settingValue){G.onFilterRemove(C.getOperator())}}},getMenuFilter:function(A){return A.getMenu().activeHeader.filter},onCheckChange:function(A,C){var D=this.isLocked?A.up("grid"):this.grid,B=this.getMenuFilter(D.headerCt);B.setActive(C)},getHeaders:function(){return this.grid.view.headerCt.columnManager.getColumns()},isStateful:function(){return this.grid.stateful},addFilter:function(K){var A=this,J=A.grid,H=A.store,D=true,G,B,E,I,C,F;if(!Ext.isArray(K)){K=[K]}for(E=0,I=K.length;E<I;E++){C=K[E];G=C.dataIndex;if(C.value){D=false}if(B=J.columnManager.getHeaderByDataIndex(G)){F=B.filter;if(!F||(F&&!F.isGridFilter)){B.filter=Ext.apply(F||{},C)}else{Ext.destroy(F);B.filter=C}}}H.suppressNextFilter=D;A.initColumns();H.suppressNextFilter=false},addFilters:function(A){if(A){this.addFilter(A)}},getFilter:function(A){return this.filters.get(A)},clearFilters:function(B){var I=this.grid,H=I.columnManager.getColumns(),F=I.store,J=F.getAutoFilter(),A,C,E,G,D;if(B!==undefined){F.setAutoFilter(B)}for(E=0,G=H.length;E<G;E++){A=H[E];C=A.filter;if(C&&C.isGridFilter){if(!D){D=F.getFilters();D.beginUpdate()}C.setActive(false)}}if(D){D.endUpdate()}if(B!==undefined){F.setAutoFilter(J)}},onBeforeReconfigure:function(C,A,B){if(B){A.getFilters().beginUpdate()}this.reconfiguring=true},onReconfigure:function(E,A,B,D){var C=this;if(D!==A){C.bindStore(A)}if(B){C.initColumns();A.getFilters().endUpdate()}C.reconfiguring=false}});
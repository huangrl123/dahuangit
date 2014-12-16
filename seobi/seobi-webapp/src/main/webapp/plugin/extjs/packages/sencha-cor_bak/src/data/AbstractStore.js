Ext.define("Ext.data.AbstractStore",{mixins:["Ext.mixin.Observable","Ext.mixin.Factoryable"],requires:["Ext.util.Collection","Ext.data.schema.Schema","Ext.util.Filter"],factoryConfig:{defaultType:"store",type:"store"},$configPrefixed:false,$configStrict:false,config:{autoFilter:true,autoSort:true,filters:undefined,autoDestroy:undefined,storeId:null,statefulFilters:false,sorters:undefined,remoteSort:false,remoteFilter:false,groupField:undefined,groupDir:"ASC",grouper:null,pageSize:25},currentPage:1,loading:false,isDestroyed:false,isStore:true,updating:0,constructor:function(B){var C=this,A;C.isInitializing=true;C.mixins.observable.constructor.call(C,B);C.isInitializing=false;A=C.getStoreId();if(!A&&(B&&B.id)){C.setStoreId(A=B.id)}if(A){Ext.data.StoreManager.register(C)}},getId:function(){return this.getObservableId()},getCount:function(){return this.getData().getCount()},rangeCached:function(B,A){return this.getData().getCount()>=Math.max(B,A)},find:function(A,C,G,F,B,D){var H=!F,E=H&&D;return this.getData().findIndex(A,C,G,H,E,!B)},findRecord:function(){var A=this,B=A.find.apply(A,arguments);return B!==-1?A.getAt(B):null},findExact:function(A,B,C){return this.getData().findIndexBy(function(D){return D.isEqual(D.get(A),B)},this,C)},findBy:function(A,B,C){return this.getData().findIndexBy(A,B,C)},getAt:function(A){return this.getData().getAt(A)||null},getRange:function(D,C,B){var A=this.getData().getRange(D,Ext.isNumber(C)?C+1:C);if(B&&B.callback){B.callback.call(B.scope||this,A,D,C,B)}return A},applyFilters:function(B,A){if(!A){A=this.createFiltersCollection();A.setRootProperty("data")}A.add(B);return A},applySorters:function(A,B){if(!B){B=this.createSortersCollection();B.setRootProperty("data")}B.add(A);return B},updateAutoFilter:function(A){var B=this.getData();if(B.setAutoFilter){B.setAutoFilter(A)}},updateAutoSort:function(A){var B=this.getData();if(B.setAutoSort){B.setAutoSort(A)}},filter:function(A,B){if(Ext.isString(A)){A={property:A,value:B}}this.getFilters().add(A)},removeFilter:function(B){var A=this,C=A.getFilters();if(B instanceof Ext.util.Filter){C.remove(B)}else{C.removeByKey(B)}},updateRemoteSort:function(B){var A=this.getSorters();if(B){A.on("endupdate",this.onSorterEndUpdate,this)}else{A.un("endupdate",this.onSorterEndUpdate,this)}},updateRemoteFilter:function(B){var A=this.getFilters();if(B){A.on("endupdate",this.onFilterEndUpdate,this)}else{A.un("endupdate",this.onFilterEndUpdate,this)}},addFilter:function(A){this.getFilters().add(A)},filterBy:function(A,B){this.getFilters().add({filterFn:A,scope:B||this})},clearFilter:function(A){var B=this,C=B.getFilters(false);if(!C||C.getCount()===0){return}B.suppressNextFilter=!!A;C.removeAll();B.suppressNextFilter=false},isFiltered:function(){return this.getFilters().getCount()>0},isSorted:function(){return this.getSorters().getCount()>0||this.isGrouped()},addFieldTransform:function(B){if(B.getTransform()){return}var D=B.getProperty(),A=this.getModel(),E,C;if(A){E=A.getField(D);C=E?E.getSortType():null}if(C&&C!==Ext.identityFn){B.setTransform(C)}},beginUpdate:function(){if(!this.updating++){this.fireEvent("beginupdate")}},endUpdate:function(){if(this.updating&&!--this.updating){this.fireEvent("endupdate");this.onEndUpdate()}},getState:function(){var E=this,G=[],H=E.getFilters(),C=E.getGrouper(),F,B,D,A;E.getSorters().each(function(I){G[G.length]=I.getState();B=true});if(E.statefulFilters&&E.saveStatefulFilters){B=true;F=[];H.each(function(I){F[F.length]=I.getState()})}if(C){B=true}if(B){A={};if(G.length){A.sorters=G}if(F){A.filters=F}if(C){A.grouper=C.getState()}}return A},applyState:function(G){var D=this,B=D.getSorters(),F=D.getFilters(),A=G.sorters,E=G.filters,C=G.grouper;if(A){B.replaceAll(A)}if(E){D.saveStatefulFilters=true;F.replaceAll(E)}if(C){this.setGrouper(C)}},hasPendingLoad:Ext.emptyFn,isLoading:Ext.emptyFn,destroy:function(){var A=this;if(A.isDestroyed){return}A.isDestroyed=true;A.clearListeners();if(A.getStoreId()){Ext.data.StoreManager.unregister(A)}A.onDestroy()},sort:function(C,D,A){var B=this;if(arguments.length===0){if(B.getRemoteSort()){B.attemptLoad()}else{B.forceLocalSort()}}else{B.getSorters().addSort(C,D,A)}},onSorterEndUpdate:function(){var B=this,A=B.getSorters().getRange();if(A.length){if(B.getRemoteSort()){B.attemptLoad({callback:function(){B.fireEvent("sort",B,A)}})}else{B.fireEvent("datachanged",B);B.fireEvent("refresh",B);B.fireEvent("sort",B,A)}}else{B.fireEvent("sort",B,A)}},onFilterEndUpdate:function(){var B=this,A=B.suppressNextFilter;if(B.getAutoFilter()){if(B.getRemoteFilter()){B.currentPage=1;if(!A){B.attemptLoad()}}else{if(!A){B.fireEvent("datachanged",B);B.fireEvent("refresh",B)}}}if(B.trackStateChanges){B.saveStatefulFilters=true}B.fireEvent("filterchange",B,B.getFilters().getRange())},updateGroupField:function(A){var B=this.getData();if(A){B.setGrouper({property:A,direction:this.getGroupDir()})}else{B.setGrouper(null)}},getGrouper:function(){return this.getData().getGrouper()},group:function(E,B,C){var D=this,A=E||D.getSorters().getCount()>0;if(E&&typeof E==="string"){E={property:E,direction:B||D.getGroupDir()}}D.getData().setGrouper(E);if(D.isLoadBlocked()){return}if(A){if(D.getRemoteSort()){D.attemptLoad({callback:function(){D.fireEvent("groupchange",D,D.getGrouper())}})}else{D.fireEvent("datachanged",D);D.fireEvent("refresh",D);D.fireEvent("groupchange",D,D.getGrouper())}}else{D.fireEvent("groupchange",D,D.getGrouper())}},clearGrouping:function(){this.group(null)},getGroupField:function(){var B=this.getGrouper(),A="";if(B){A=B.getProperty()}return A},isGrouped:function(){return !!this.getGrouper()},applyGrouper:function(A){this.group(A);return this.getData().getGrouper()},getGroups:function(){return this.getData().getGroups()},onEndUpdate:Ext.emptyFn,deprecated:{5:{methods:{destroyStore:function(){this.destroy()}}}}});
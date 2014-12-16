Ext.define("Ext.data.ProxyStore",{extend:"Ext.data.AbstractStore",requires:["Ext.data.proxy.Proxy","Ext.data.proxy.Memory","Ext.data.operation.*"],config:{model:undefined,fields:null,proxy:undefined,autoLoad:undefined,autoSync:false,batchUpdateMode:"operation",sortOnLoad:true,trackRemoved:true,session:undefined},onClassExtended:function(B,E,C){var D=E.model,A;if(typeof D==="string"){A=C.onBeforeCreated;C.onBeforeCreated=function(){var F=this,G=arguments;Ext.require(D,function(){A.apply(F,G)})}}},implicitModel:false,blockLoadCounter:0,autoSyncSuspended:0,constructor:function(A){var B=this;var D=B.model;B.removed=[];B.blockLoad();B.callParent(arguments);B.unblockLoad();if(!B.getModel()&&B.useModelWarning!==false&&B.getStoreId()!=="ext-empty-store"){var C=[Ext.getClassName(B)||"Store"," created with no model."];if(typeof D==="string"){C.push(" The name '",D,"'"," does not correspond to a valid model.")}Ext.log.warn(C.join(""))}},updateAutoLoad:function(A){var B=this,C;B.getData();if(A){C=B.loadTask||(B.loadTask=new Ext.util.DelayedTask());C.delay(1,B.attemptLoad,B,Ext.isObject(A)?[A]:undefined)}},getTotalCount:function(){return this.totalCount||0},applyFields:function(D){var B=this,C,A;if(D){B.implicitModel=true;B.setModel(C=Ext.define(null,{extend:"Ext.data.Model",fields:D,proxy:(A=B.getProxy())}));if(A&&!A.getModel()){A.setModel(C)}}},applyModel:function(A){if(A){A=Ext.data.schema.Schema.lookupEntity(A)}else{this.getFields();return this.getModel()}return A},applyProxy:function(A){var B=this,C=B.getModel();if(A){if(A.isProxy){A.setModel(C)}else{if(Ext.isString(A)){A={type:A,model:C}}else{if(!A.model){A=Ext.apply({model:C},A)}}A=Ext.createByAlias("proxy."+A.type,A)}}else{if(C){A=C.getProxy()}}if(!A){A=Ext.createByAlias("proxy.memory")}if(!B.disableMetaChangeEvent){A.on("metachange",B.onMetaChange,B)}return A},updateTrackRemoved:function(A){this.removed=A?[]:null},onMetaChange:function(A,B){this.fireEvent("metachange",this,B)},create:function(F,D){var A=this,B=new A.model(F),C=A.getProxy(),E;D=Ext.apply({},D);if(!D.records){D.records=[B]}D.internalScope=A;D.internalCallback=A.onProxyWrite;E=C.createOperation("create",D);return E.execute()},read:function(){return this.load.apply(this,arguments)},update:function(C){var A=this,B=A.getProxy(),D;C=Ext.apply({},C);if(!C.records){C.records=A.getUpdatedRecords()}C.internalScope=A;C.internalCallback=A.onProxyWrite;D=B.createOperation("update",C);return D.execute()},onProxyWrite:function(D){var C=this,A=D.wasSuccessful(),B=D.getRecords();switch(D.getAction()){case"create":C.onCreateRecords(B,D,A);break;case"update":C.onUpdateRecords(B,D,A);break;case"destroy":C.onDestroyRecords(B,D,A);break}if(A){C.fireEvent("write",C,D);C.fireEvent("datachanged",C);C.fireEvent("refresh",C)}},onCreateRecords:Ext.emptyFn,onUpdateRecords:Ext.emptyFn,onDestroyRecords:function(B,C,A){if(A){this.removed.length=0}},erase:function(C){var A=this,B=A.getProxy(),D;C=Ext.apply({},C);if(!C.records){C.records=A.getRemovedRecords()}C.internalScope=A;C.internalCallback=A.onProxyWrite;D=B.createOperation("destroy",C);return D.execute()},onBatchOperationComplete:function(B,A){return this.onProxyWrite(A)},onBatchComplete:function(F,E){var D=this,C=F.operations,A=C.length,B;if(D.batchUpdateMode!="operation"){D.suspendEvents();for(B=0;B<A;B++){D.onProxyWrite(C[B])}D.resumeEvents()}D.isSyncing=false;D.fireEvent("datachanged",D);D.fireEvent("refresh",D)},onBatchException:function(B,A){},filterNew:function(A){return A.phantom===true&&A.isValid()},getNewRecords:function(){return[]},getUpdatedRecords:function(){return[]},getModifiedRecords:function(){return[].concat(this.getNewRecords(),this.getUpdatedRecords())},filterUpdated:function(A){return A.dirty===true&&A.phantom!==true&&A.isValid()},getRemovedRecords:function(){return this.removed},sync:function(D){var A=this,F={},G=A.getNewRecords(),C=A.getUpdatedRecords(),B=A.getRemovedRecords(),E=false;if(A.isSyncing){Ext.Error.warn("Sync called while a sync operation is in progress. Consider configuring autoSync as false.")}A.needsSync=false;if(G.length>0){F.create=G;E=true}if(C.length>0){F.update=C;E=true}if(B.length>0){F.destroy=B;E=true}if(E&&A.fireEvent("beforesync",F)!==false){A.isSyncing=true;D=D||{};A.proxy.batch(Ext.apply(D,{operations:F,listeners:A.getBatchListeners()}))}return A},getBatchListeners:function(){var A=this,B={scope:A,exception:A.onBatchException,complete:A.onBatchComplete};if(A.batchUpdateMode=="operation"){B.operationcomplete=A.onBatchOperationComplete}return B},save:function(){return this.sync.apply(this,arguments)},blockLoad:function(A){if(A!==undefined){this.blockLoadCounter=A}else{++this.blockLoadCounter}},unblockLoad:function(C){var A=this,B=A.blockLoadCounter;if(C){A.blockLoadCounter=0}else{if(B){--A.blockLoadCounter}}return B},isLoadBlocked:function(){return !!this.blockLoadCounter},attemptLoad:function(A){if(!this.isLoadBlocked()){this.load(A)}},load:function(F){if(this.isLoadBlocked()){return}var A=this,B=A.getProxy(),G=A.loadTask,D={internalScope:A,internalCallback:A.onProxyLoad},H,C,E;if(A.getRemoteFilter()){H=A.getFilters();if(H.getCount()){D.filters=H.getRange()}}if(A.getRemoteSort()){C=A.getSorters();if(C.getCount()){D.sorters=C.getRange()}}Ext.apply(D,F);D.scope=D.scope||A;if(!D.recordCreator){E=A.getSession();if(E){D.recordCreator=E.recordCreator}}A.lastOptions=D;D=B.createOperation("read",D);if(A.fireEvent("beforeload",A,D)!==false){A.loading=true;if(G){G.cancel();A.loadTask=null}D.execute()}return A},reload:function(B){var A=Ext.apply({},B,this.lastOptions);return this.load(A)},onEndUpdate:function(){var A=this;if(A.needsSync&&A.autoSync&&!A.autoSyncSuspended){A.sync()}},afterReject:function(B){var A=this;if(A.contains(B)){A.onUpdate(B,Ext.data.Model.REJECT,null);A.fireEvent("update",A,B,Ext.data.Model.REJECT,null)}},afterCommit:function(B,C){var A=this;if(!C){C=null}if(A.contains(B)){A.onUpdate(B,Ext.data.Model.COMMIT,C);A.fireEvent("update",A,B,Ext.data.Model.COMMIT,C)}},afterErase:function(A){this.onErase(A)},onErase:Ext.emptyFn,onUpdate:Ext.emptyFn,onDestroy:function(){var A=this;A.blockLoad();A.clearData();A.setProxy(null);A.setModel(null);A.setSession(null)},hasPendingLoad:function(){return !!this.loadTask||this.isLoading()},isLoading:function(){return !!this.loading},isLoaded:function(){return this.loadCount>0},suspendAutoSync:function(){++this.autoSyncSuspended},resumeAutoSync:function(B){var A=this;if(!A.autoSyncSuspended){Ext.log.warn("Mismatched call to resumeAutoSync - auto synchronization is currently not suspended.")}if(A.autoSyncSuspended&&!--A.autoSyncSuspended){if(B){A.sync()}}},removeAll:Ext.emptyFn,clearData:Ext.emptyFn});
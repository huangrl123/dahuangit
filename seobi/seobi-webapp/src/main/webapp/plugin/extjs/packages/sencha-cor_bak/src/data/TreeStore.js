Ext.define("Ext.data.TreeStore",{extend:"Ext.data.NodeStore",alias:"store.tree",requires:["Ext.util.Sorter","Ext.data.TreeModel","Ext.data.NodeInterface"],isTreeStore:true,config:{root:null,rootVisible:false,recursive:true,defaultRootProperty:"children"},clearOnLoad:true,clearRemovedOnLoad:true,nodeParam:"node",defaultRootId:"root",defaultRootText:"Root",fillCount:0,folderSort:false,constructor:function(A){var B=this;B.byIdMap={};B.byInternalIdMap={};B.callParent([A]);if(Ext.isDefined(B.nodeParameter)){if(Ext.isDefined(Ext.global.console)){Ext.global.console.warn("Ext.data.TreeStore: nodeParameter has been deprecated. Please use nodeParam instead.")}B.nodeParam=B.nodeParameter;delete B.nodeParameter}},applyFields:function(B){var A=this;if(B){if(A.defaultRootProperty!==A.self.prototype.config.defaultRootProperty){B=B.concat({name:A.defaultRootProperty,type:"auto",defaultValue:null,persist:false})}A.setModel(Ext.define(null,{extend:"Ext.data.TreeModel",fields:B,proxy:A.getProxy()}));A.implicitModel=true}},onSorterEndUpdate:function(){var C=this,D=C.getSorters(),B=D.getRange(),A=C.getRoot();if(A&&B.length){if(C.getRemoteSort()){C.attemptLoad({callback:function(){C.fireEvent("sort",C,B)}})}else{A.sort(this.getSortFn(),true);C.fireEvent("datachanged",C);C.fireEvent("refresh",C);C.fireEvent("sort",C,B)}}else{C.fireEvent("sort",C,B)}},updateRemoteFilter:function(){var B=this.getData(),A=this.getFilters();B.setFilters(null);A.on("endupdate",this.onFilterEndUpdate,this)},updateRemoteSort:function(){var B=this.getData(),A=this.getSorters();B.setSorters(null);A.on("endupdate",this.onSorterEndUpdate,this)},getSortFn:function(){return this._sortFn||(this._sortFn=this.createSortFn())},createSortFn:function(){var B=this.sorters.getSortFn(),A=B;if(this.getFolderSort()){A=function(C,D){var F=C.get("leaf")?1:0,E=D.get("leaf")?1:0;return(F-E)||B(C,D)}}return A},updateRootVisible:function(B){var A=this.getRoot(),C;if(A){C=this.getData();if(B){C.insert(0,A)}else{C.remove(A)}}},updateTrackRemoved:function(A){this.callParent(arguments);this.removedNodes=this.removed;this.removed=null},getRemovedRecords:function(){return this.removedNodes},onDestroyRecords:function(B,C,A){if(A){this.removedNodes.length=0}},applyProxy:function(A){var B;A=this.callParent(arguments);if(A.setIdParam){A.setIdParam(this.nodeParam)}if(Ext.isEmpty(A.getReader().getRootProperty())){B=A.getReader();B.setRootProperty(this.defaultRootProperty);B.buildExtractors(true)}return A},applyModel:function(A){A=this.callParent(arguments);if(!A){A=Ext.data.TreeModel}return A},updateModel:function(B){var A=B.prototype.isNode;Ext.data.NodeInterface.decorate(B);if(!A){this.getProxy().getReader().buildExtractors(true)}},onFilterEndUpdate:function(D){var A=this,B,C=D.length,G=D.getFilterFn(),F=A.getRoot(),E;if(C){E=[];F.cascadeBy({after:function(H){H.set("visible",G(H))}});for(B=0,C=F.childNodes.length;B<C;B++){if(F.childNodes[B].get("visible")){E.push(F.childNodes[B])}}}else{E=F.childNodes}A.onNodeFilter(F,E);F.fireEvent("filterchange",F,E);A.fireEvent("filterchange",A,D)},clearFilter:function(){var B=this,A=B.getRoot();B.callParent();B.filterFn=null;A.cascadeBy(function(C){C.set("visible",true)});B.onNodeFilter(A,A.childNodes);A.fireEvent("filterchange",A,A.childNodes);B.fireEvent("filterchange",B,[])},onNodeFilter:function(B,C){var A=this,E=A.getData(),D=[];A.handleNodeExpand(B,C,D);A.suspendEvents();E.splice(0,E.getCount(),D);A.resumeEvents();A.fireEvent("datachanged",A);A.fireEvent("refresh",A)},onBeforeNodeExpand:function(G,B,D,A){var H=this,L,C,K,I,E,F,J;if(G.isLoaded()){F=[G.childNodes];if(A){F.push.apply(F,A)}Ext.callback(B,D||G,F)}else{if(G.isLoading()){H.on("load",function(){F=[G.childNodes];if(A){F.push.apply(F,A)}Ext.callback(B,D||G,F)},H,{single:true,priority:1001})}else{L=H.getProxy().getReader();C=G.getProxy();K=C?C.getReader():null;I=K&&K.initialConfig.rootProperty?K:L;if(E=I.getRoot(G.raw||G.data)){J=G.childType;H.fillNode(G,I.extractData(E,J?{model:J}:undefined));F=[G.childNodes];if(A){F.push.apply(F,A)}Ext.callback(B,D||G,F)}else{H.read({node:G,onChildNodesAvailable:function(){delete H.lastOptions.onChildNodesAvailable;F=[G.childNodes];if(A){F.push.apply(F,A)}Ext.callback(B,D||G,F)}})}}}},onNodeExpand:function(D,A){var C=this,B=C.indexOf(D)+1,E=[];C.handleNodeExpand(D,A,E);if(!C.refreshCounter&&D.isRoot()&&!D.get("visible")){C.loadRecords(E)}else{C.insert(B,E)}},handleNodeExpand:function(E,C,G){var D=this,F=C?C.length:0,B,A;if(E!==this.getRoot()&&!D.isVisible(E)){return}if(F){for(B=0;B<F;B++){A=C[B];if(A.get("visible")){G.push(A);if(A.isExpanded()){if(A.isLoaded()){D.handleNodeExpand(A,A.childNodes,G)}else{A.set("expanded",false);A.expand()}}}}}},onNodeCollapse:function(J,E,A,C){var I=this,D=I.indexOf(J)+1,G,B,H,F;if(!I.recursive&&J!==I.getRoot()){return}if(!I.remoteFilter&&I.getFilters().getCount()){E=Ext.Array.filter(E,I.filterVisible)}if(E.length&&I.data.contains(E[0])){G=J;while(G.parentNode){for(H=G.nextSibling;H&&!H.get("visible");H=H.nextSibling){}if(H){F=true;B=I.indexOf(H);break}else{G=G.parentNode}}if(!F){B=I.getCount()}I.removeAt(D,B-D)}Ext.callback(A,C)},getNewRecords:function(){return Ext.Array.filter(Ext.Object.getValues(this.byIdMap),this.filterNew)},getUpdatedRecords:function(){return Ext.Array.filter(Ext.Object.getValues(this.byIdMap),this.filterUpdated)},beforeNodeRemove:function(B,C){if(!Ext.isArray(C)){C=[C]}var A,D=C.length,E;for(A=0;A<D;A++){E=C[A];if(!E.isLeaf()&&E.isExpanded()){this.onNodeCollapse(E,E.childNodes)}}},onNodeRemove:function(C,A,F){var G=this,B=G.removedNodes,H=A.length,D,E,I;G.remove(A,F);for(D=0;D<H;D++){E=A[D];I=!E.phantom;if(B&&!F&&I&&!G.loading){E.removedFrom=G.indexOf(E);B.push(E)}G.unregisterNode(E,true)}},onNodeAppend:function(C,A,B){this.onNodeInsert(C,A,B)},onNodeInsert:function(M,F,C){var I=this,G,H,N,A,E,J,D=F.raw||F.data,B,K,L;if(I.filterFn){K=I.filterFn(F);F.set("visible",K);if(K){M.set("visible",I.filterFn(M))}}I.registerNode(F,true);F.join(I);I.beginUpdate();if(I.isVisible(F)){if(C===0||!F.previousSibling){G=M}else{for(H=F.previousSibling;H&&!H.get("visible");H=H.previousSibling){}while(H.isExpanded()&&H.lastChild){H=H.lastChild}G=H}I.insert(I.indexOf(G)+1,F);if(!F.isLeaf()&&F.isExpanded()){if(F.isLoaded()){I.onNodeExpand(F,F.childNodes)}else{if(!I.fillCount){F.set("expanded",false);F.expand()}}}}else{I.needsSync=I.needsSync||F.phantom||F.dirty}if(!F.isLeaf()&&!F.isLoaded()&&!I.lazyFill){N=I.getProxy().getReader();A=F.getProxy();E=A?A.getReader():null;J=E&&E.initialConfig.rootProperty?E:N;B=J.getRoot(D);if(B){L=F.childType;I.fillNode(F,J.extractData(B,L?{model:L}:undefined))}}I.endUpdate()},afterEdit:function(A,B){if(this.getData().indexOf(A)!==-1){this.callParent(arguments)}},registerNode:function(D,A){var E=this,F,B,C;E.byIdMap[D.id]=D;E.byInternalIdMap[D.internalId]=D;if(A===true){F=D.childNodes;B=F.length;for(C=0;C<B;C++){E.registerNode(F[C],true)}}},unregisterNode:function(D,A){var E=this,F,B,C;delete E.byIdMap[D.id];delete E.byInternalIdMap[D.internalId];if(A===true){F=D.childNodes;B=F.length;for(C=0;C<B;C++){E.unregisterNode(F[C],true)}}},onNodeSort:function(B,A){var C=this;C.suspendAutoSync();if((C.indexOf(B)!==-1||(B===C.getRoot()&&!C.rootVisible)&&B.isExpanded())){Ext.suspendLayouts();C.onNodeCollapse(B,A);C.onNodeExpand(B,A);Ext.resumeLayouts(true)}C.resumeAutoSync(C.autoSync)},applyRoot:function(B){var C=this,A=C.getModel(),D=A.prototype.idProperty;if(B&&!B.isNode){B=Ext.apply({text:C.defaultRootText,root:true,isFirst:true,isLast:true,depth:0,index:0,parentId:null,allowDrag:false},B);if(C.defaultRootId&&B[D]===undefined){B[D]=C.defaultRootId}B=new A(B)}return B},updateRoot:function(B,A){var C=this,D;C.byIdMap={};C.byInternalIdMap={};C.getTrackRemoved();C.suspendEvents();C.getData().clear();if(B){if(B.fireEventArgs("beforeappend",[null,B])===false){B=null}else{if(D=B.parentNode){if(!D.removeChild(B,false,false,D.getTreeStore()===C)){return}}else{if((D=B.getTreeStore())&&D!==C&&B===D.getRoot()){D.setRoot(null)}}B.set("root",true);B.updateInfo(true,{isFirst:true,isLast:true,depth:0,index:0,parentId:null});B.fireEvent("append",null,B,false);B.fireEvent("rootchange",B);C.onNodeAppend(null,B,0)}}C.fireEvent("rootchange",B,A);if(A&&A.isModel){A.set("root",false);C.remove(A);A.fireEvent("remove",null,A,false);A.fireEvent("rootchange",null);A.unjoin(C)}C.resumeEvents();if(B&&!B.isLoaded()&&(C.autoLoad===true||B.isExpanded())){B.data.expanded=false;B.expand()}else{C.fireEvent("datachanged",C);C.fireEvent("refresh",C)}return B},getNodeById:function(B){var A=this.byIdMap[B];if(A&&A.data.visible){return A}},load:function(F){F=F||{};F.params=F.params||{};var B=this,E=F.node||B.getRoot(),C=B.getProxy(),A=F.callback,G=F.scope,D;if(!E){B.setRoot({expanded:true});return}if(E.data.expanded){E.data.loaded=false;if(B.clearOnLoad){E.data.expanded=false}F.callback=function(){if(!B.clearOnLoad){E.collapse()}E.expand();Ext.callback(A,G,arguments)}}F.id=E.getId();F=Ext.apply({filters:B.getFilters().items,sorters:B.getSorters().items,node:F.node||E,internalScope:B,internalCallback:B.onProxyLoad},F);B.lastOptions=Ext.apply({},F);D=C.createOperation("read",F);if(B.fireEvent("beforeload",B,D)!==false){B.loading=true;if(B.clearOnLoad){if(B.clearRemovedOnLoad){B.clearRemoved(E)}E.removeAll(false)}D.execute()}if(B.loading&&E){E.set("loading",true)}return B},clearRemoved:function(G){var J=this,C=J.removedNodes,I=G.getId(),B=C.length,E=B,A={},K=[],H={},F,D,L;if(G===J.getRoot()){J.removedNodes=[];return}for(;E--;){F=C[E];H[F.getId()]=F}for(E=B;E--;){F=C[E];D=F;while(D&&D.getId()!==I){L=D.get("parentId");D=D.parentNode||J.getNodeById(L)||H[L]}if(D){A[F.getId()]=F}}for(E=0;E<B;E++){F=C[E];if(!A[F.getId()]){K.push(F)}}J.removedNodes=K},fillNode:function(F,J){var H=this,I=J?J.length:0,A=H.getSorters(),D,B=false,E=H.sortOnLoad&&I>1&&!H.remoteSort&&A&&A.items&&A.items.length,G,C;if(I){if(!H.remoteFilter&&H.getFilters().getCount()){J[0].set("visible",H.filterFn(J[0]))}for(D=1;!B&&D<I;D++){G=J[D];C=J[D-1];if(H.filterFn){G.set("visible",H.filterFn(G))}B=G.data.index!==C.data.index}if(E){if(B){H.indexSorter=A.insert(0,H.indexSorter)}Ext.Array.sort(J,A.getSortFn());if(B){A.remove(H.indexSorter)}}else{if(B){Ext.Array.sort(J,H.sortByIndex)}}}F.set("loaded",true);if(J.length){F.appendChild(J,undefined,true)}return J},beginFill:function(){var A=this;if(!A.fillCount++){A.beginUpdate();A.suspendEvents();A.suspendAutoSync();A.fillArray=[]}},endFill:function(F,D){var C=this,A,E,B;C.fillArray.push(D);if(!--C.fillCount){C.resumeAutoSync();C.resumeEvents();for(A=0,E=C.fillArray.length;A<E;A++){B=C.indexOf(C.fillArray[A][0]);if(B!==-1){C.fireEvent("add",C,C.fillArray[A],B)}}C.fillArray=null;C.endUpdate()}},sortByIndex:function(A,B){return A.data.index-B.data.index},onIdChanged:function(D,F,C){var B=D.childNodes,E=B&&B.length,A;this.callParent(arguments);delete this.byIdMap[F];this.byIdMap[C]=D;for(A=0;A<E;A++){B[A].set("parentId",C)}},onProxyLoad:function(E){var G=this,D=E.initialConfig,H=E.wasSuccessful(),C=E.getRecords(),F=D.node,B=E.getScope()||G,A=[C,E,H];if(G.isDestroyed){return}G.loading=false;F.set("loading",false);if(H){if(!G.clearOnLoad){C=G.cleanRecords(F,C)}C=G.fillNode(F,C)}Ext.callback(D.onChildNodesAvailable,B,A);G.fireEvent("load",G,E.node,C,H)},cleanRecords:function(G,E){var A={},D=G.childNodes,F=0,H=D.length,B=[],C;for(;F<H;++F){A[D[F].getId()]=true}for(F=0,H=E.length;F<H;++F){C=E[F];if(!A[C.getId()]){B.push(C)}}return B},removeAll:function(){this.suspendEvents();this.setRoot(null);this.resumeEvents();this.callParent()},doSort:function(B){var A=this;if(A.remoteSort){A.load()}else{A.tree.sort(B,true);A.fireEvent("datachanged",A);A.fireEvent("refresh",A)}A.fireEvent("sort",A,A.sorters.getRange())},filterVisible:function(A){return A.get("visible")},isVisible:function(C){var A=C.parentNode,B=C.data.visible,D=this.getRoot();while(B&&A){B=A.data.expanded&&A.data.visible;A=A.parentNode}return B&&!(C===D&&!this.rootVisible)},getRootNode:function(){return this.getRoot()},setRootNode:function(A){this.setRoot(A);return this.getRoot()},deprecated:{5:{properties:{tree:null}}}},function(){var A=this.prototype;A.indexSorter=new Ext.util.Sorter({sorterFn:A.sortByIndex})});
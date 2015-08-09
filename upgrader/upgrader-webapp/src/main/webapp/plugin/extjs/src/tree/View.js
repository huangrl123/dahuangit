Ext.define("Ext.tree.View",{extend:"Ext.view.Table",alias:"widget.treeview",requires:["Ext.data.NodeStore"],isTreeView:true,loadingCls:Ext.baseCSSPrefix+"grid-tree-loading",expandedCls:Ext.baseCSSPrefix+"grid-tree-node-expanded",leafCls:Ext.baseCSSPrefix+"grid-tree-node-leaf",expanderSelector:"."+Ext.baseCSSPrefix+"tree-expander",checkboxSelector:"."+Ext.baseCSSPrefix+"tree-checkbox",expanderIconOverCls:Ext.baseCSSPrefix+"tree-expander-over",nodeAnimWrapCls:Ext.baseCSSPrefix+"tree-animator-wrap",ariaRole:"tree",loadMask:false,rootVisible:true,expandDuration:250,collapseDuration:250,toggleOnDblClick:true,stripeRows:false,uiFields:["expanded","loaded","checked","expandable","leaf","icon","iconCls","loading","qtip","qtitle"],treeRowTpl:["{%","this.processRowValues(values);","this.nextTpl.applyOut(values, out, parent);","%}",{priority:10,processRowValues:function(A){var B=A.record,C=A.view;A.rowAttr["data-qtip"]=B.get("qtip")||"";A.rowAttr["data-qtitle"]=B.get("qtitle")||"";if(B.isExpanded()){A.rowClasses.push(C.expandedCls)}if(B.isLeaf()){A.rowClasses.push(C.leafCls)}if(B.isLoading()){A.rowClasses.push(C.loadingCls)}}}],initComponent:function(){var A=this;if(A.bufferedRenderer){A.animate=false}else{if(A.initialConfig.animate===undefined){A.animate=Ext.enableFx}}A.store=A.panel.getStore();A.onRootChange(A.store.getRoot());A.animQueue={};A.animWraps={};A.callParent(arguments);A.store.setRootVisible(A.rootVisible);A.addRowTpl(Ext.XTemplate.getTpl(A,"treeRowTpl"))},onFillComplete:function(F,D,A){var C=this,B=C.store,E=B.indexOf(A[0]);D.triggerUIUpdate();if(!A.length||E===-1){return}C.onAdd(C.store,A,E);C.refreshPartner()},onBeforeSort:function(){this.store.suspendEvents()},onSort:function(A){if(A.isStore){this.store.resumeEvents();this.refresh();this.refreshPartner()}},refreshPartner:function(){var A=this.lockingPartner;if(A){A.refresh()}},getMaskStore:function(){return this.panel.getStore()},afterRender:function(){var A=this;A.callParent(arguments);A.el.on({scope:A,delegate:A.expanderSelector,mouseover:A.onExpanderMouseOver,mouseout:A.onExpanderMouseOut,click:{delegate:A.checkboxSelector,fn:A.onCheckboxChange,scope:A}})},afterComponentLayout:function(){var B=this,C=B.stretcher,A=B.scrollManager;B.callParent(arguments);if(C){C.setWidth((this.getWidth()-Ext.getScrollbarSize().width))}if(A){A.refresh()}},processUIEvent:function(A){if(A.getTarget("."+this.nodeAnimWrapCls,this.el)){return false}return this.callParent(arguments)},onClear:function(){this.store.removeAll()},setRootNode:function(A,B){if(!B){this.store.setNode(A)}this.node=A},onCheckboxChange:function(A,D){var C=this,B=A.getTarget(C.getItemSelector(),C.getTargetEl());if(B){C.onCheckChange(C.getRecord(B))}},onCheckChange:function(A){var B=A.get("checked");if(Ext.isBoolean(B)){B=!B;A.set("checked",B);this.fireEvent("checkchange",A,B)}},getChecked:function(){var A=[];this.node.cascadeBy(function(B){if(B.get("checked")){A.push(B)}});return A},isItemChecked:function(A){return A.get("checked")},createAnimWrap:function(C,D){var A=this,B=A.getNode(C),E;E=Ext.fly(B).insertSibling({role:"presentation",tag:"div",cls:A.nodeAnimWrapCls},"after");return{record:C,node:B,el:E,expanding:false,collapsing:false,animateEl:E,targetEl:E}},getAnimWrap:function(C,A){if(!this.animate){return null}var D=this.animWraps,B=D[C.internalId];if(A!==false){while(!B&&C){C=C.parentNode;if(C){B=D[C.internalId]}}}return B},doAdd:function(G,C){var H=this,L=H.bufferRender(G,C,true),J=G[0],K=J.parentNode,E=H.all,D,B=H.getAnimWrap(K),A,F,I;if(!B||!B.expanding){return H.callParent(arguments)}K=B.record;A=B.targetEl;F=A.dom.childNodes;I=F.length;D=C-H.indexInStore(K)-1;if(!I||D>=I){A.appendChild(L,true)}else{Ext.fly(F[D]).insertSibling(L,"before",true)}E.insert(C,L);return L},onRemove:function(A,C,E){var D=this,F,B;if(D.viewReady){F=D.store.getCount()===0;if(F){D.refresh()}else{for(B=C.length-1,E+=B;B>=0;--B,--E){D.doRemove(C[B],E)}}if(D.hasListeners.itemremove){for(B=C.length-1,E+=B;B>=0;--B,--E){D.fireEvent("itemremove",C[B],E,D)}}}},doRemove:function(E,F){var B=this,A=B.all,C=B.getAnimWrap(E),D=A.item(F),G=D?D.dom:null;if(!G||!C||!C.collapsing){return B.callParent(arguments)}C.targetEl.dom.insertBefore(G,C.targetEl.dom.firstChild);A.removeElement(F)},onBeforeExpand:function(E,B,D){var C=this,A;if(C.rendered&&C.all.getCount()&&C.animate){if(C.getNode(E)){A=C.getAnimWrap(E,false);if(!A){A=C.animWraps[E.internalId]=C.createAnimWrap(E);A.animateEl.setHeight(0)}else{if(A.collapsing){A.targetEl.select(C.itemSelector).destroy()}}A.expanding=true;A.collapsing=false}}},onExpand:function(H){var G=this,D=G.animQueue,F=H.getId(),E=G.getNode(H),C=E?G.indexOf(E):-1,B,I,A;if(G.singleExpand){G.ensureSingleExpand(H)}if(C===-1){return}B=G.getAnimWrap(H,false);if(!B){G.refreshSelection();H.isExpandingOrCollapsing=false;G.fireEvent("afteritemexpand",H,C,E);G.refreshSize();return}I=B.animateEl;A=B.targetEl;I.stopAnimation();D[F]=true;I.dom.style.height="0px";I.animate({from:{height:0},to:{height:A.dom.scrollHeight},duration:G.expandDuration,listeners:{afteranimate:function(){var J=A.dom.childNodes;if(J.length){B.el.insertSibling(J,"before",true)}B.el.destroy();delete G.animWraps[B.record.internalId];delete D[F];if(!G.isDestroyed){G.refreshSize()}}},callback:function(){G.refreshSelection();H.isExpandingOrCollapsing=false;G.fireEvent("afteritemexpand",H,C,E)}});B.isAnimating=true},onBeforeCollapse:function(G,C,F,A,D){var E=this,B;if(E.rendered&&E.all.getCount()){if(E.animate){if(Ext.Array.contains(G.joined,E.store)){B=E.getAnimWrap(G);if(!B){B=E.animWraps[G.internalId]=E.createAnimWrap(G,F)}else{if(B.expanding){B.targetEl.select(this.itemSelector).destroy()}}B.expanding=false;B.collapsing=true;B.callback=A;B.scope=D}}else{E.onCollapseCallback=A;E.onCollapseScope=D}}},onCollapse:function(G){var F=this,C=F.animQueue,E=G.getId(),D=F.getNode(G),B=D?F.indexOf(D):-1,A=F.getAnimWrap(G),H;if(!F.all.getCount()||!Ext.Array.contains(G.joined,F.store)){return}if(!A){F.refreshSelection();G.isExpandingOrCollapsing=false;F.fireEvent("afteritemcollapse",G,B,D);F.refreshSize();Ext.callback(F.onCollapseCallback,F.onCollapseScope);F.onCollapseCallback=F.onCollapseScope=null;return}H=A.animateEl;C[E]=true;H.stopAnimation();H.animate({to:{height:0},duration:F.collapseDuration,listeners:{afteranimate:function(){A.el.destroy();delete F.animWraps[A.record.internalId];delete C[E];if(!F.isDestroyed){F.refreshSize()}}},callback:function(){F.refreshSelection();G.isExpandingOrCollapsing=false;F.fireEvent("afteritemcollapse",G,B,D);Ext.callback(A.callback,A.scope);A.callback=A.scope=null}});A.isAnimating=true},isAnimating:function(A){return !!this.animQueue[A.getId()]},expand:function(E,D,A,F){var B=this,G=!!B.animate,C;if(!G||!E.isExpandingOrCollapsing){if(!E.isLeaf()){E.isExpandingOrCollapsing=G}Ext.suspendLayouts();C=E.expand(D,A,F);Ext.resumeLayouts(true);return C}},collapse:function(D,C,A,E){var B=this,F=!!B.animate;if(!F||!D.isExpandingOrCollapsing){if(!D.isLeaf()){D.isExpandingOrCollapsing=F}return D.collapse(C,A,E)}},toggle:function(C,B,A,D){if(C.isExpanded()){this.collapse(C,B,A,D)}else{this.expand(C,B,A,D)}},onItemDblClick:function(D,C,E){var A=this,B=A.editingPlugin;A.callParent(arguments);if(A.toggleOnDblClick&&D.isExpandable()&&!(B&&B.clicksToEdit===2)){A.focusRow(D);A.toggle(D)}},onBeforeItemMouseDown:function(C,B,D,A){if(A.getTarget(this.expanderSelector,B)){return false}return this.callParent(arguments)},onItemClick:function(C,B,D,A){if(A.getTarget(this.expanderSelector,B)&&C.isExpandable()){this.focusRow(C);this.toggle(C,A.ctrlKey);return false}return this.callParent(arguments)},onExpanderMouseOver:function(A,B){A.getTarget(this.cellSelector,10,true).addCls(this.expanderIconOverCls)},onExpanderMouseOut:function(A,B){A.getTarget(this.cellSelector,10,true).removeCls(this.expanderIconOverCls)},getStoreListeners:function(){var B=this,A=Ext.apply(B.callParent(),{rootchange:B.onRootChange,fillcomplete:B.onFillComplete});if(!this.getStore().remoteSort){Ext.apply(A,{beforesort:B.onBeforeSort,sort:B.onSort})}return A},onRootChange:function(B,A){var C=this;if(C.rootListeners){C.rootListeners.destroy()}if(B){C.rootListeners=B.on({beforeexpand:C.onBeforeExpand,expand:C.onExpand,beforecollapse:C.onBeforeCollapse,collapse:C.onCollapse,scope:C})}},ensureSingleExpand:function(A){var B=A.parentNode;if(B){B.eachChild(function(C){if(C!==A&&C.isExpanded()){C.collapse()}})}},shouldUpdateCell:function(B,E,D){if(D){var A=0,C=D.length;for(;A<C;++A){if(Ext.Array.contains(this.uiFields,D[A])){return true}}}return this.callParent(arguments)}});
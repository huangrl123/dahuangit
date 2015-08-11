Ext.define("Ext.selection.TreeModel",{extend:"Ext.selection.RowModel",alias:"selection.treemodel",constructor:function(A){this.callParent(arguments);if(this.pruneRemoved){this.pruneRemoved=false;this.pruneRemovedNodes=true}},bindStore:function(A,B){var C=this;C.callParent(arguments);if(C.pruneRemovedNodes){C.view.mon(C.treeStore,{remove:C.onNodeRemove,scope:C})}},onNodeRemove:function(B,A,C){if(!C){this.deselectDeletedRecords([A])}},onKeyRight:function(A,B){this.navExpand(A,B)},navExpand:function(B,D){var C=this,E=C.getLastFocused(),A=C.view;if(E){if(E.isExpanded()){C.onKeyDown(B,D)}else{if(E.isExpandable()){if(!A.isTreeView){A=A.lockingPartner}A.expand(E);if(E){C.onLastFocusChanged(null,E)}}}}},onKeyLeft:function(A,B){this.navCollapse(A,B)},navCollapse:function(B,D){var C=this,E=C.getLastFocused(),A=C.view,F;if(E){F=E.parentNode;if(E.isExpanded()){if(!A.isTreeView){A=A.lockingPartner}A.collapse(E);C.onLastFocusChanged(null,E)}else{if(F&&!F.isRoot()){if(B.shiftKey){C.selectRange(F,E,B.ctrlKey,"up");C.setLastFocused(F)}else{if(B.ctrlKey){C.setLastFocused(F)}else{C.select(F)}}}}this.onLastFocusChanged(null,E)}},onKeySpace:function(A,B){if(A.record.data.checked!=null){this.toggleCheck(A)}else{this.callParent(arguments)}},onKeyEnter:function(A,B){if(A.record.data.checked!=null){this.toggleCheck(A)}else{this.callParent(arguments)}},toggleCheck:function(A){var B=this.view,C=this.getLastSelected();A.stopEvent();if(C){if(!B.isTreeView){B=B.lockingPartner}B.onCheckChange(C)}}});
Ext.define("Ext.rtl.grid.plugin.HeaderResizer",{override:"Ext.grid.plugin.HeaderResizer",onBeforeStart:function(A){var B=this;if(this.headerCt.isOppositeRootDirection()){B.dragHd=B.activeHd;if(!!B.dragHd&&!B.headerCt.dragging){B.xDelta=B.dragHd.getX()-B.tracker.getXY()[0];this.tracker.constrainTo=this.getConstrainRegion();return true}else{B.headerCt.dragging=false;return false}}else{return this.callParent(arguments)}},adjustColumnWidth:function(A){if(this.headerCt.isOppositeRootDirection()){A=-A}this.callParent([A])},adjustConstrainRegion:function(E,D,A,C,B){return this.headerCt.isOppositeRootDirection()?E.adjust(D,-B,C,-A):this.callParent(arguments)},calculateDragX:function(A){var B=A.getX(),C=this.tracker.getXY("point")[0];if(this.headerCt.isOppositeRootDirection()){return C-B+this.xDelta}else{return this.callParent(arguments)}},getMovingMarker:function(A){if(this.headerCt.isOppositeRootDirection()){return A.getLhsMarker()}else{return A.getRhsMarker()}},setMarkerX:function(A,B){var C=this.headerCt;if(C.getInherited().rtl&&!C.isOppositeRootDirection()){A.rtlSetLocalX(B)}else{this.callParent(arguments)}}});
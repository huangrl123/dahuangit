Ext.define("Ext.tree.ViewDragZone",{extend:"Ext.view.DragZone",isPreventDrag:function(A,B){return(B.get("allowDrag")===false)||!!A.getTarget(this.view.expanderSelector)},getDragText:function(){var B=this.dragData.records,A=B.length,D=B[0].get(this.displayField),C="s";if(A===1&&D){return D}else{if(!D){C=""}}return Ext.String.format(this.dragText,A,C)},afterRepair:function(){var F=this,A=F.view,G=A.selectedItemCls,D=F.dragData.records,C,E=D.length,B=Ext.fly,H;if(Ext.enableFx&&F.repairHighlight){for(C=0;C<E;C++){H=A.getNode(D[C]);B(H.firstChild).highlight(F.repairHighlightColor,{listeners:{beforeanimate:function(){if(A.isSelected(H)){B(H).removeCls(G)}},afteranimate:function(){if(A.isSelected(H)){B(H).addCls(G)}}}})}}F.dragging=false}});
Ext.define("Ext.mixin.Selectable",{extend:"Ext.Mixin",mixinConfig:{id:"selectable",after:{updateStore:"updateStore"}},config:{disableSelection:null,mode:"SINGLE",allowDeselect:false,lastSelected:null,lastFocused:null,deselectOnContainerClick:true},modes:{SINGLE:true,SIMPLE:true,MULTI:true},selectableEventHooks:{addrecords:"onSelectionStoreAdd",removerecords:"onSelectionStoreRemove",updaterecord:"onSelectionStoreUpdate",load:"refreshSelection",refresh:"refreshSelection"},constructor:function(){this.selected=new Ext.util.MixedCollection();this.callParent(arguments)},applyMode:function(A){A=A?A.toUpperCase():"SINGLE";return this.modes[A]?A:"SINGLE"},updateStore:function(D,C){var B=this,A=Ext.apply({},B.selectableEventHooks,{scope:B});if(C&&Ext.isObject(C)&&C.isStore){if(C.autoDestroy){C.destroy()}else{C.un(A);if(D){D.un("clear","onSelectionStoreClear",this)}}}if(D){D.on(A);D.onBefore("clear","onSelectionStoreClear",this);B.refreshSelection()}},selectAll:function(C){var B=this,A=B.getStore().getRange();B.select(A,true,C)},deselectAll:function(A){var B=this,C=B.getStore().getRange();B.deselect(C,A);B.selected.clear();B.setLastSelected(null);B.setLastFocused(null)},selectWithEvent:function(B){var A=this,C=A.isSelected(B);switch(A.getMode()){case"MULTI":case"SIMPLE":if(C){A.deselect(B)}else{A.select(B,true)}break;case"SINGLE":if(A.getAllowDeselect()&&C){A.deselect(B)}else{A.select(B,false)}break}},selectRange:function(D,G,H){var F=this,A=F.getStore(),B=[],C,E;if(F.getDisableSelection()){return}if(D>G){C=G;G=D;D=C}for(E=D;E<=G;E++){B.push(A.getAt(E))}this.doMultiSelect(B,H)},select:function(A,D,B){var C=this,E;if(C.getDisableSelection()){return}if(typeof A==="number"){A=[C.getStore().getAt(A)]}if(!A){return}if(C.getMode()=="SINGLE"&&A){E=A.length?A[0]:A;C.doSingleSelect(E,B)}else{C.doMultiSelect(A,D,B)}},doSingleSelect:function(C,B){var A=this,D=A.selected;if(A.getDisableSelection()){return}if(A.isSelected(C)){return}if(D.getCount()>0){A.deselect(A.getLastSelected(),B)}D.add(C);A.setLastSelected(C);A.onItemSelect(C,B);A.setLastFocused(C);if(!B){A.fireSelectionChange([C])}},doMultiSelect:function(D,I,B){if(D===null||this.getDisableSelection()){return}D=!Ext.isArray(D)?[D]:D;var H=this,F=H.selected,C=D.length,A=false,E=0,G;if(!I&&F.getCount()>0){A=true;H.deselect(H.getSelection(),true)}for(;E<C;E++){G=D[E];if(I&&H.isSelected(G)){continue}A=true;H.setLastSelected(G);F.add(G);if(!B){H.setLastFocused(G)}H.onItemSelect(G,B)}if(A&&!B){this.fireSelectionChange(D)}},deselect:function(E,C){var A=this;if(A.getDisableSelection()){return}E=Ext.isArray(E)?E:[E];var G=A.selected,B=false,F=0,H=A.getStore(),D=E.length,I;for(;F<D;F++){I=E[F];if(typeof I==="number"){I=H.getAt(I)}if(G.remove(I)){if(A.getLastSelected()==I){A.setLastSelected(G.last())}B=true}if(I){A.onItemDeselect(I,C)}}if(B&&!C){A.fireSelectionChange(E)}},updateLastFocused:function(B,A){this.onLastFocusChanged(A,B)},fireSelectionChange:function(A){var B=this;B.fireAction("beforeselectionchange",[B],function(){B.fireAction("selectionchange",[B,A],"getSelection")})},getSelection:function(){return this.selected.getRange()},isSelected:function(A){A=Ext.isNumber(A)?this.getStore().getAt(A):A;return this.selected.indexOf(A)!==-1},hasSelection:function(){return this.selected.getCount()>0},refreshSelection:function(){var B=this,A=B.getSelection();B.deselectAll(true);if(A.length){B.select(A,false,true)}},onSelectionStoreRemove:function(A,B){var D=this,G=D.selected,F=B.length,E,C;if(D.getDisableSelection()){return}for(C=0;C<F;C++){E=B[C];if(G.remove(E)){if(D.getLastSelected()==E){D.setLastSelected(null)}if(D.getLastFocused()==E){D.setLastFocused(null)}D.fireSelectionChange([E])}}},onSelectionStoreClear:function(A){var B=A.getData().items;this.onSelectionStoreRemove(A,B)},getSelectionCount:function(){return this.selected.getCount()},onSelectionStoreAdd:Ext.emptyFn,onSelectionStoreUpdate:Ext.emptyFn,onItemSelect:Ext.emptyFn,onItemDeselect:Ext.emptyFn,onLastFocusChanged:Ext.emptyFn,onEditorKey:Ext.emptyFn},function(){});
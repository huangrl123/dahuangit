Ext.define("Ext.grid.ViewDropZone",{extend:"Ext.view.DropZone",indicatorHtml:'<div class="'+Ext.baseCSSPrefix+'grid-drop-indicator-left" role="presentation"></div><div class="'+Ext.baseCSSPrefix+'grid-drop-indicator-right" role="presentation"></div>',indicatorCls:Ext.baseCSSPrefix+"grid-drop-indicator",handleNodeDrop:function(C,I,F){var A=this.view,G=A.getStore(),B,D,E,H;if(C.copy){D=C.records;C.records=[];for(E=0,H=D.length;E<H;E++){C.records.push(D[E].copy())}}else{C.view.store.remove(C.records,C.view===A)}if(I&&F){B=G.indexOf(I);if(F!=="before"){B++}G.insert(B,C.records)}else{G.add(C.records)}A.getSelectionModel().select(C.records)}});
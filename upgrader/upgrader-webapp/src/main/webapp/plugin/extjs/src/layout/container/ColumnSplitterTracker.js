Ext.define("Ext.layout.container.ColumnSplitterTracker",{extend:"Ext.resizer.SplitterTracker",performResize:function(A,D){var I=this,K=I.getPrevCmp(),H=I.getNextCmp(),G=I.getSplitter(),C=G.ownerCt,J=D[0],B,E,F;if(K&&H){K.width=B=I.prevBox.width+J;H.width=E=I.nextBox.width-J;F=(K.columnWidth+H.columnWidth)/(B+E);K.columnWidth=B*F;H.columnWidth=E*F}G.el.dom.style.left="";C.updateLayout()}});
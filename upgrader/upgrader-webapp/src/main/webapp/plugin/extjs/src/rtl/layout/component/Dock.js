Ext.define("Ext.rtl.layout.component.Dock",{override:"Ext.layout.component.Dock",rtlPositions:{top:"top",right:"left",bottom:"bottom",left:"right"},getDockCls:function(A){return"docked-"+(this.owner.getInherited().rtl?this.rtlPositions[A]:A)},getBorderClassTable:function(){var A=this;if(!A.borderTablesInited){A.initBorderTables()}return A.owner.getInherited().rtl?A.noBorderClassTableRtl:A.noBorderClassTableLtr},getBorderCollapseTable:function(){var A=this,B=A.callParent();if(!B.rtl){A.setupBorderTable(B,B.rtl=[])}return A.owner.getInherited().rtl?B.rtl:B},initBorderTables:function(){var B=Ext.layout.component.Dock.prototype,C=B.noBorderClassTable,A=[];B.setupBorderTable(C,A);B.noBorderClassTableLtr=C;B.noBorderClassTableRtl=A;B.borderTablesInited=true},setupBorderTable:function(B,A){A[0]=B[0];A[1]=B[4];A[2]=B[2];A[3]=B[6];A[4]=B[1];A[5]=B[5];A[6]=B[3];A[7]=B[7];A[8]=B[8];A[9]=B[12];A[10]=B[10];A[11]=B[14];A[12]=B[9];A[13]=B[13];A[14]=B[11];A[15]=B[15]}});
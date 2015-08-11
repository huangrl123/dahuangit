Ext.define("Ext.grid.column.Check",{extend:"Ext.grid.column.Column",alternateClassName:["Ext.ux.CheckColumn","Ext.grid.column.CheckColumn"],alias:"widget.checkcolumn",align:"center",stopSelection:true,tdCls:Ext.baseCSSPrefix+"grid-cell-checkcolumn",innerCls:Ext.baseCSSPrefix+"grid-cell-inner-checkcolumn",clickTargetName:"el",defaultFilterType:"boolean",constructor:function(){this.scope=this;this.callParent(arguments)},processEvent:function(D,A,L,F,C,B,J,G){var H=this,I=D==="keydown"&&B.getKey(),E=D=="mousedown";if(!H.disabled&&(E||(I==B.ENTER||I==B.SPACE))){var K=!H.isRecordChecked(J);if(H.fireEvent("beforecheckchange",H,F,K)!==false){H.setRecordCheck(J,K,L,G,B);H.fireEvent("checkchange",H,F,K);if(E){B.stopEvent()}if(!H.stopSelection){A.selModel.selectByPosition({row:F,column:C})}return false}else{return !H.stopSelection}}else{return H.callParent(arguments)}},onEnable:function(){this.callParent(arguments);this._setDisabled(false)},onDisable:function(){this._setDisabled(true)},_setDisabled:function(D){var B=this,A=B.disabledCls,C;C=B.up("tablepanel").el.select(B.getCellSelector());if(D){C.addCls(A)}else{C.removeCls(A)}},defaultRenderer:function(C,B){var A=Ext.baseCSSPrefix,D=A+"grid-checkcolumn";if(this.disabled){B.tdCls+=" "+this.disabledCls}if(C){D+=" "+A+"grid-checkcolumn-checked"}return'<img class="'+D+'" src="'+Ext.BLANK_IMAGE_URL+'"/>'},isRecordChecked:function(A){var B=this.property;if(B){return A[B]}return A.get(this.dataIndex)},setRecordCheck:function(D,F,G,C,B){var A=this,E=A.property;if(E){D[E]=F;A.updater(G,F)}else{D.set(A.dataIndex,F)}},updater:function(C,B){var A={};C.firstChild.innerHTML=this.defaultRenderer(B,A);Ext.fly(C).addCls(A.tdCls)}});
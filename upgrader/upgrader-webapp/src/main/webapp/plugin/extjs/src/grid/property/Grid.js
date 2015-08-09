Ext.define("Ext.grid.property.Grid",{extend:"Ext.grid.Panel",alias:"widget.propertygrid",alternateClassName:"Ext.grid.PropertyGrid",uses:["Ext.grid.plugin.CellEditing","Ext.grid.property.Store","Ext.grid.property.HeaderContainer","Ext.XTemplate","Ext.grid.CellEditor","Ext.form.field.Date","Ext.form.field.Text","Ext.form.field.Number","Ext.form.field.ComboBox"],valueField:"value",nameField:"name",inferTypes:true,enableColumnMove:false,columnLines:true,stripeRows:false,trackMouseOver:false,clicksToEdit:1,enableHdMenu:false,gridCls:Ext.baseCSSPrefix+"property-grid",initComponent:function(){var A=this;A.source=A.source||{};A.addCls(A.gridCls);A.plugins=A.plugins||[];A.plugins.push(new Ext.grid.plugin.CellEditing({clicksToEdit:A.clicksToEdit,startEdit:function(B,C){return this.self.prototype.startEdit.call(this,B,A.valueColumn)}}));A.selModel={selType:"cellmodel",onCellSelect:function(B){B.columnHeader=A.valueColumn;B.column=A.valueColumn.getVisibleIndex();return this.self.prototype.onCellSelect.call(this,B)}};A.sourceConfig=Ext.apply({},A.sourceConfig);if(!A.store){A.propStore=A.store=new Ext.grid.property.Store(A,A.source)}A.configure(A.sourceConfig);if(A.sortableColumns){A.store.sort("name","ASC")}A.columns=new Ext.grid.property.HeaderContainer(A,A.store);A.callParent();A.getView().walkCells=this.walkCells;A.editors={"date":new Ext.grid.CellEditor({field:new Ext.form.field.Date({selectOnFocus:true})}),"string":new Ext.grid.CellEditor({field:new Ext.form.field.Text({selectOnFocus:true})}),"number":new Ext.grid.CellEditor({field:new Ext.form.field.Number({selectOnFocus:true})}),"boolean":new Ext.grid.CellEditor({field:new Ext.form.field.ComboBox({editable:false,store:[[true,A.headerCt.trueText],[false,A.headerCt.falseText]]})})};A.store.on("update",A.onUpdate,A)},configure:function(A){var I=this,E=I.store,G=0,J=I.store.getCount(),D=I.nameField,H=I.valueField,C,F,B,K;I.configureLegacy(A);if(I.inferTypes){for(;G<J;++G){B=E.getAt(G);C=B.get(D);if(!I.getConfig(C,"type")){F=B.get(H);if(Ext.isDate(F)){K="date"}else{if(Ext.isNumber(F)){K="number"}else{if(Ext.isBoolean(F)){K="boolean"}else{K="string"}}}I.setConfig(C,"type",K)}}}},getConfig:function(D,A,B){var E=this.sourceConfig[D],C;if(E){C=E[A]}return C||B},setConfig:function(D,B,A){var E=this.sourceConfig,C=E[D];if(!C){C=E[D]={__copied:true}}else{if(!C.__copied){C=Ext.apply({__copied:true},C);E[D]=C}}C[B]=A;return A},configureLegacy:function(A){var B=this;B.copyLegacyObject(A,B.customRenderers,"renderer");B.copyLegacyObject(A,B.customEditors,"editor");B.copyLegacyObject(A,B.propertyNames,"displayName");if(B.customRenderers||B.customEditors||B.propertyNames){if(Ext.global.console&&Ext.global.console.warn){Ext.global.console.warn(this.$className,'customRenderers, customEditors & propertyNames have been consolidated into a new config, see "sourceConfig". These configurations will be deprecated.')}}},copyLegacyObject:function(C,B,D){var A;for(A in B){if(B.hasOwnProperty(A)){if(!C[A]){C[A]={}}C[A][D]=B[A]}}},onUpdate:function(A,B,D){var E=this,C,F;if(E.rendered&&D==Ext.data.Model.EDIT){C=B.get(E.valueField);F=B.modified.value;if(E.fireEvent("beforepropertychange",E.source,B.getId(),C,F)!==false){if(E.source){E.source[B.getId()]=C}B.commit();E.fireEvent("propertychange",E.source,B.getId(),C,F)}else{B.reject()}}},walkCells:function(E,B,A,H,C,D){var G=this,F=G.ownerCt.valueColumn;if(B=="left"){B="up"}else{if(B=="right"){B="down"}}E=Ext.view.Table.prototype.walkCells.call(G,E,B,A,H,C,D);E.columnHeader=F;E.column=F.getVisibleIndex();return E},getCellEditor:function(G,D){var F=this,B=G.get(F.nameField),E=G.get(F.valueField),A=F.getConfig(B,"editor"),C=F.getConfig(B,"type"),H=F.editors;if(A){if(!(A instanceof Ext.grid.CellEditor)){if(!(A instanceof Ext.form.field.Base)){A=Ext.ComponentManager.create(A,"textfield")}A=F.setConfig(B,"editor",new Ext.grid.CellEditor({field:A}))}}else{if(C){switch(C){case"date":A=H.date;break;case"number":A=H.number;break;case"boolean":A=F.editors["boolean"];break;default:A=H.string}}else{if(Ext.isDate(E)){A=H.date}else{if(Ext.isNumber(E)){A=H.number}else{if(Ext.isBoolean(E)){A=H["boolean"]}else{A=H.string}}}}}A.editorId=B;A.field.column=F.valueColumn;return A},beforeDestroy:function(){var A=this;A.callParent();A.destroyEditors(A.editors);A.destroyEditors(A.customEditors);delete A.source},destroyEditors:function(B){for(var A in B){if(B.hasOwnProperty(A)){Ext.destroy(B[A])}}},setSource:function(B,A){var C=this;C.source=B;if(A!==undefined){C.sourceConfig=Ext.apply({},A);C.configure(C.sourceConfig)}C.propStore.setSource(B)},getSource:function(){return this.propStore.getSource()},setProperty:function(B,C,A){this.propStore.setValue(B,C,A)},removeProperty:function(A){this.propStore.remove(A)}});
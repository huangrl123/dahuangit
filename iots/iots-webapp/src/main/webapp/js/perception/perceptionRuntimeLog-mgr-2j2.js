function showPerceptionDetailwin(perceptionId) {
	// ///////////////////////////////////运行日志信息面板////////////////////////////////////////////
	var perceptionRuntimeLogStore = Ext.create('Ext.data.Store', {
				fields : ['createDateTime', 'perceptionAddr', 'perceptionId', 'perceptionName', 'perceptionParamId',
						'perceptionParamName', 'perceptionParamValueInfoId', 'perceptionParamValueDesc',
						'perceptionRuntimeLogId', 'perceptionTypeId', 'perceptionTypeName', 'remark'],

				proxy : {
					type : 'ajax',
					url : 'spring/perception/findPerceptionRuntimeLogByPage',

					extraParams : {
						perceptionId : perceptionId
					},

					actionMethods : {
						read : 'POST'
					},

					reader : {
						type : 'json',
						rootProperty : 'results',
						totalProperty : 'totalCount'
					}
				}
			});

	var perceptionRuntimeLogPg = Ext.create('Ext.PagingToolbar', {
				store : perceptionRuntimeLogStore,
				pageSize : 25,
				displayInfo : true,
				displayMsg : '显示{0}-{1}条，共{2}条',
				emptyMsg : "没有数据"
			});

	var perceptionRuntimeLogGrid = Ext.create('Ext.grid.Panel', {
				title : '运行日志',
				width : 600,
				store : perceptionRuntimeLogStore,
				columns : [{
							xtype : 'rownumberer'
						}, {
							text : "运行动作执行时间",
							width : 30,
							dataIndex : 'createDateTime',
							sortable : true
						}, {
							text : "运行动作",
							width : 30,
							dataIndex : 'perceptionParamName',
							sortable : true
						}, {
							text : "运动动作结果",
							width : 60,
							dataIndex : 'perceptionParamValueDesc',
							sortable : true
						}],
				bbar : perceptionRuntimeLogPg,
				forceFit : true,
				height : 505,
				split : true
			});

	// ///////////////////////////////////基本信息面板////////////////////////////////////////////
	var form = null;
	var opt = null;
	// 电机1开关状态combox
	var switchStatus_machine1_combobox = Ext.create('Ext.form.field.ComboBox', {
				fieldLabel : '电机1开关状态',
				store : Ext.create('Ext.data.Store', {
							singleton : true,
							proxy : {
								type : 'ajax',
								url : 'spring/perception/getPerceptionParamListByTypeId',
								actionMethods : {
									read : 'POST'
								},
								extraParams : {
									paramId : 180
								},
								reader : {
									type : 'json',
									rootProperty : 'root'
								}
							},
							fields : ['value', 'text'],
							autoLoad : true
						}),
				listeners : {
					beforeselect : function(combo, record, index, eOpts) {
						var msg;
						if (record.data.value == '1') {
							opt = 5;// 电机1通电控制
							msg = '是否电机1进行通电控制?';
						} else {
							opt = 6;// 电机1断电控制
							msg = '是否电机1进行断电控制?';
						}

						Ext.Msg.show({
									title : '提示',
									message : msg,
									buttons : Ext.Msg.YESNO,
									icon : Ext.Msg.QUESTION,
									fn : function(btn) {
										Ext.Msg.show({
													title : '提示',
													message : msg,
													buttons : Ext.Msg.YESNO,
													icon : Ext.Msg.QUESTION,
													fn : function(btn) {
														if (btn === 'yes') {

															remoteCtrlPerception();
														} else if (btn === 'no') {
															this.close();
														} else {
															this.close();
														}
													}
												});
									}
								});
					}
				},
				displayField : 'text',
				valueField : 'value',
				emptyText : "请选择",
				editable : false,
				mode : "local",
				triggerAction : 'all',
				width : 500
			});

	// 电机1旋转状态combox
	var rotateStatus_machine1_combobox = Ext.create('Ext.form.field.ComboBox', {
				fieldLabel : '电机1旋转状态',
				store : Ext.create('Ext.data.Store', {
							singleton : true,
							proxy : {
								type : 'ajax',
								url : 'spring/perception/getPerceptionParamListByTypeId',
								actionMethods : {
									read : 'POST'
								},
								extraParams : {
									paramId : 179
								},
								reader : {
									type : 'json',
									rootProperty : 'root'
								}
							},
							fields : ['value', 'text'],
							autoLoad : true
						}),
				listeners : {
					beforeselect : function(combo, record, index, eOpts) {
						var msg;
						if (record.data.value == '1') {
							opt = 3;// 电机1正转控制
							msg = '是否要对电机1进行正转控制?';
						} else {
							opt = 4;// 电机1反转控制
							msg = '是否要对电机1进行反转控制?';
						}

						Ext.Msg.show({
									title : '提示',
									message : msg,
									buttons : Ext.Msg.YESNO,
									icon : Ext.Msg.QUESTION,
									fn : function(btn) {
										if (btn === 'yes') {

											remoteCtrlPerception();
										} else if (btn === 'no') {
											this.close();
										} else {
											this.close();
										}
									}
								});
					}
				},
				displayField : 'text',
				valueField : 'value',
				emptyText : "请选择",
				editable : false,
				mode : 'remote',
				triggerAction : 'all',
				width : 500
			});

	// 电机2开关状态combox
	var switchStatus_machine2_combobox = Ext.create('Ext.form.field.ComboBox', {
				fieldLabel : '电机2开关状态',
				store : Ext.create('Ext.data.Store', {
							singleton : true,
							proxy : {
								type : 'ajax',
								url : 'spring/perception/getPerceptionParamListByTypeId',
								actionMethods : {
									read : 'POST'
								},
								extraParams : {
									paramId : 188
								},
								reader : {
									type : 'json',
									rootProperty : 'root'
								}
							},
							fields : ['value', 'text'],
							autoLoad : true
						}),
				listeners : {
					beforeselect : function(combo, record, index, eOpts) {
						var msg;
						if (record.data.value == '1') {
							opt = 11;// 0x0B：电机2通电控制
							msg = '是否要对电机2进行通电控制?';
						} else {
							opt = 12;// 0x0C：电机2断电控制
							msg = '是否要对电机2进行断电控制?';
						}

						Ext.Msg.show({
									title : '提示',
									message : msg,
									buttons : Ext.Msg.YESNO,
									icon : Ext.Msg.QUESTION,
									fn : function(btn) {
										Ext.Msg.show({
													title : '提示',
													message : msg,
													buttons : Ext.Msg.YESNO,
													icon : Ext.Msg.QUESTION,
													fn : function(btn) {
														if (btn === 'yes') {

															remoteCtrlPerception();
														} else if (btn === 'no') {
															this.close();
														} else {
															this.close();
														}
													}
												});
									}
								});
					}
				},
				displayField : 'text',
				valueField : 'value',
				emptyText : "请选择",
				editable : false,
				mode : "local",
				triggerAction : 'all',
				width : 500
			});

	// 电机2旋转状态combox
	var rotateStatus_machine2_combobox = Ext.create('Ext.form.field.ComboBox', {
				fieldLabel : '电机2旋转状态',
				store : Ext.create('Ext.data.Store', {
							singleton : true,
							proxy : {
								type : 'ajax',
								url : 'spring/perception/getPerceptionParamListByTypeId',
								actionMethods : {
									read : 'POST'
								},
								extraParams : {
									paramId : 187
								},
								reader : {
									type : 'json',
									rootProperty : 'root'
								}
							},
							fields : ['value', 'text'],
							autoLoad : true
						}),
				listeners : {
					beforeselect : function(combo, record, index, eOpts) {
						var msg;
						if (record.data.value == '1') {
							opt = 9;// 电机2正转控制
							msg = '是否要对电机2进行正转控制?';
						} else {
							opt = 10;// 0x0A: 电机2反转控制
							msg = '是否要对电机2进行反转控制?';
						}

						Ext.Msg.show({
									title : '提示',
									message : msg,
									buttons : Ext.Msg.YESNO,
									icon : Ext.Msg.QUESTION,
									fn : function(btn) {
										if (btn === 'yes') {

											remoteCtrlPerception();
										} else if (btn === 'no') {
											this.close();
										} else {
											this.close();
										}
									}
								});
					}
				},
				displayField : 'text',
				valueField : 'value',
				emptyText : "请选择",
				editable : false,
				mode : 'remote',
				triggerAction : 'all',
				width : 500
			});

	// i2c状态combox
	var i2cStatus_combobox = Ext.create('Ext.form.field.ComboBox', {
				fieldLabel : 'i2c状态',
				store : Ext.create('Ext.data.Store', {
							singleton : true,
							proxy : {
								type : 'ajax',
								url : 'spring/perception/getPerceptionParamListByTypeId',
								actionMethods : {
									read : 'POST'
								},
								extraParams : {
									paramId : 182
								},
								reader : {
									type : 'json',
									rootProperty : 'root'
								}
							},
							fields : ['value', 'text'],
							autoLoad : true
						}),
				listeners : {
					beforeselect : function(combo, record, index, eOpts) {
						var msg;
						if (record.data.value == '1') {
							opt = 7;// I2C打开控制
							msg = '是否要对i2c进行打开控制?';
						} else {
							opt = 8;// I2C关闭控制
							msg = '是否要对i2c进行关闭控制?';
						}

						Ext.Msg.show({
									title : '提示',
									message : msg,
									buttons : Ext.Msg.YESNO,
									icon : Ext.Msg.QUESTION,
									fn : function(btn) {
										if (btn === 'yes') {

											remoteCtrlPerception();
										} else if (btn === 'no') {
											this.close();
										} else {
											this.close();
										}
									}
								});
					}
				},
				displayField : 'text',
				valueField : 'value',
				emptyText : "请选择",
				editable : false,
				mode : 'remote',
				triggerAction : 'all',
				width : 500
			});

	// 红外
	var infraredStatus_field = new Ext.form.TextField({
				fieldLabel : '红外状态',
				disabled : true,
				width : 500
			});

	// 按键
	var pressKeyStatus_field = new Ext.form.TextField({
				fieldLabel : '按键状态',
				disabled : true,
				width : 500
			});

	var inTimeQueryBtn = Ext.create("Ext.Button", {
				text : "实时状态获取",
				listeners : {
					"click" : function() {
						remoteQueryPerception();
					}
				}
			});

	var perceptionRuntimeBasic = Ext.create('Ext.form.Panel', {
				title : '状态参数信息',
				width : 600,
				height : 500,
				bodyPadding : 5,
				frame : true,
				items : [switchStatus_machine1_combobox, rotateStatus_machine1_combobox,
						switchStatus_machine2_combobox, rotateStatus_machine2_combobox, i2cStatus_combobox,
						infraredStatus_field, pressKeyStatus_field, {
							layout : 'column',
							padding : '0 0 0 180',
							items : [{
										layout : 'form',
										items : inTimeQueryBtn
									}]
						}]
			});

	form = perceptionRuntimeBasic.getForm();

	// ///////////////////////////////////总体面板//////////////////////////////////////////////
	var perceptionRuntimeMainWin = Ext.create('Ext.Window', {
				title : '感知端设备详情',
				closable : true,
				modal : true,
				draggable : false,
				closeAction : 'destroy',
				layout : 'column',
				height : 538,
				items : [perceptionRuntimeBasic, perceptionRuntimeLogGrid]
			});

	perceptionRuntimeMainWin.show();

	var perceptionRuntimeLogStoreLoad = function() {
		perceptionRuntimeLogStore.load({
					params : {
						start : 0,
						limit : 25
					}
				});
	}

	perceptionRuntimeLogStoreLoad();

	// 使用ajax异步获取设备状态
	var remoteQueryPerception = function() {

		if (form.isValid()) {
			form.submit({
						url : 'spring/perception/remoteQuery2j2Machine',
						waitMsg : '获取设备实时状态，请稍后...',
						params : {
							perceptionId : perceptionId
						},
						success : function(form, action, args) {
							var response = Ext.JSON.decode(action.response.responseText);
							switchStatus_machine1_combobox.setValue(response.machine1SwitchStatus);
							rotateStatus_machine1_combobox.setValue(response.machine1RotateStatus);

							switchStatus_machine2_combobox.setValue(response.machine2SwitchStatus);
							rotateStatus_machine2_combobox.setValue(response.machine2RotateStatus);

							i2cStatus_combobox.setValue(response.i2cStatus);

							pressKeyStatus_field.setValue(response.infraredStatus);
							infraredStatus_field.setValue(response.pressKeyStatus);

							perceptionRuntimeLogStoreLoad();
						},
						failure : function(form, action, args) {
							var response = Ext.JSON.decode(action.response.responseText);
							Ext.Msg.alert('提示', response.msg);
						}
					});
		}
	}

	var remoteCtrlPerception = function() {

		if (form.isValid()) {
			form.submit({
						url : 'spring/perception/remoteCtrlPerception',
						waitMsg : '远程控制设备，请稍后...',
						params : {
							perceptionId : perceptionId,
							opt : opt
						},
						success : function(form, action, args) {
							var response = Ext.JSON.decode(action.response.responseText);
							if (response.success) {
								Ext.Msg.alert('提示', '远程控制设备成功!');
								perceptionRuntimeLogStoreLoad();
							} else {
								Ext.Msg.alert('提示', '远程控制设备失败!');
							}
						},
						failure : function(form, action, args) {
							Ext.Msg.alert('提示', '远程控制设备失败!');
						}
					});
		}
	}

	remoteQueryPerception();
}
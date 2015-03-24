function showPerceptionDetailwin6$6(perceptionId) {
	Ext.require('Ext.tab.Panel');

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

	var perceptionRuntimeLogStoreLoad = function() {
		perceptionRuntimeLogStore.load({
					params : {
						start : 0,
						limit : 25
					}
				});
	}

	var perceptionRuntimeLogPg = Ext.create('Ext.PagingToolbar', {
				store : perceptionRuntimeLogStore,
				pageSize : 25,
				displayInfo : true,
				displayMsg : '显示{0}-{1}条，共{2}条',
				emptyMsg : "没有数据"
			});

	var perceptionRuntimeLogGrid = Ext.create('Ext.grid.Panel', {
				id : 'perceptionRuntimeLogGrid',
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

	// ///////////////////////////////////现场视频信息面板////////////////////////////////////////////
	var perceptionRuntimeVideoPanel = Ext.create('Ext.form.Panel', {
				title : '现场视频',
				width : 600,
				height : 500,
				bodyPadding : 5,
				frame : true
			});
	// ///////////////////////////////////视频日志面板////////////////////////////////////////////
	var vediaLogStore = Ext.create('Ext.data.Store', {
				fields : ['fileId', 'perceptionId', 'fileName', 'filePath', 'createDateTime'],

				proxy : {
					type : 'ajax',
					url : 'spring/perception/findPerceptionVediaFileByPage',

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

	var vediaLogStoreLoad = function() {
		vediaLogStore.load({
					params : {
						start : 0,
						limit : 25
					}
				});
	}

	var vediaLogPg = Ext.create('Ext.PagingToolbar', {
				store : vediaLogStore,
				pageSize : 25,
				displayInfo : true,
				displayMsg : '显示{0}-{1}条，共{2}条',
				emptyMsg : "没有数据"
			});

	var vediaLogGrid = Ext.create('Ext.grid.Panel', {
				id : 'vediaLogGridGrid',
				title : '视频日志',
				width : 600,
				store : vediaLogStore,
				columns : [{
							xtype : 'rownumberer'
						}, {
							text : "创建时间",
							width : 40,
							dataIndex : 'createDateTime',
							sortable : true
						}, {
							text : "文件名称",
							width : 100,
							dataIndex : 'fileName',
							sortable : true
						}, {
							text : "操作",
							width : 20,
							dataIndex : 'fileId',
							sortable : true,
							renderer : function(value, modal) {
							   return '<span style="color:blue;cursor:hand;" onmousemove="this.style.color=\'red\';" onmouseout="this.style.color=\'blue\'" onclick="javascript:playVedia(\''
									+ value + '\')">播放<span/>';
							}
						}],
				bbar : vediaLogPg,
				forceFit : true,
				height : 505,
				split : true
			});
	// ///////////////////////////////////右侧tab面板////////////////////////////////////////////
	var perceptionRuntimeTabPanel = Ext.create('Ext.tab.Panel', {
				width : 600,
				height : 500,
				items : [perceptionRuntimeVideoPanel, perceptionRuntimeLogGrid, vediaLogGrid],
				listeners : {
					tabchange : function(tabPanel, newCard, oldCard, eOpts) {
						if (newCard.id == 'perceptionRuntimeLogGrid') {
							perceptionRuntimeLogStoreLoad();
						}

						else if (newCard.id == 'vediaLogGridGrid') {
							vediaLogStoreLoad();
						}

					}
				}
			});

	// ///////////////////////////////////基本信息面板////////////////////////////////////////////
	var form = null;
	var opt = null;
	// 开关状态combox
	var switchStatus_combobox = Ext.create('Ext.form.field.ComboBox', {
				fieldLabel : '开关状态',
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
							opt = 5;// 正转
							msg = '是否要对开关状态进行正转远程控制?';
						} else {
							opt = 6;// 反转
							msg = '是否要对开关状态进行反转远程控制?';
						}

						Ext.Msg.show({
									title : '提示',
									message : msg,
									buttons : Ext.Msg.YESNOCANCEL,
									icon : Ext.Msg.QUESTION,
									fn : function(btn) {
										Ext.Msg.show({
													title : '提示',
													message : msg,
													buttons : Ext.Msg.YESNOCANCEL,
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

	// 旋转状态combox
	var rotateStatus_combobox = Ext.create('Ext.form.field.ComboBox', {
				fieldLabel : '旋转状态',
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
							opt = 3;// 正转
							msg = '是否要对旋转状态进行正转远程控制?';
						} else {
							opt = 4;// 反转
							msg = '是否要对旋转状态进行反转远程控制?';
						}

						Ext.Msg.show({
									title : '提示',
									message : msg,
									buttons : Ext.Msg.YESNOCANCEL,
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

	// 红外状态combox
	var infraredStatus_combobox = Ext.create('Ext.form.field.ComboBox', {
				fieldLabel : '红外状态',
				store : Ext.create('Ext.data.Store', {
							singleton : true,
							proxy : {
								type : 'ajax',
								url : 'spring/perception/getPerceptionParamListByTypeId',
								actionMethods : {
									read : 'POST'
								},
								extraParams : {
									paramId : 183
								},
								reader : {
									type : 'json',
									rootProperty : 'root'
								}
							},
							fields : ['value', 'text'],
							autoLoad : true
						}),
				displayField : 'text',
				valueField : 'value',
				emptyText : "请选择",
				editable : false,
				mode : 'remote',
				triggerAction : 'all',
				width : 500
			});

	// 振动状态combox
	var vibrateStatus_combobox = Ext.create('Ext.form.field.ComboBox', {
				fieldLabel : '振动状态',
				store : Ext.create('Ext.data.Store', {
							singleton : true,
							proxy : {
								type : 'ajax',
								url : 'spring/perception/getPerceptionParamListByTypeId',
								actionMethods : {
									read : 'POST'
								},
								extraParams : {
									paramId : 184
								},
								reader : {
									type : 'json',
									rootProperty : 'root'
								}
							},
							fields : ['value', 'text'],
							autoLoad : true
						}),
				displayField : 'text',
				valueField : 'value',
				emptyText : "请选择",
				editable : false,
				mode : 'remote',
				triggerAction : 'all',
				width : 500
			});
	// 压力状态combox
	var pressureStatus_combobox = Ext.create('Ext.form.field.ComboBox', {
				fieldLabel : '振动状态',
				store : Ext.create('Ext.data.Store', {
							singleton : true,
							proxy : {
								type : 'ajax',
								url : 'spring/perception/getPerceptionParamListByTypeId',
								actionMethods : {
									read : 'POST'
								},
								extraParams : {
									paramId : 185
								},
								reader : {
									type : 'json',
									rootProperty : 'root'
								}
							},
							fields : ['value', 'text'],
							autoLoad : true
						}),
				displayField : 'text',
				valueField : 'value',
				emptyText : "请选择",
				editable : false,
				mode : 'remote',
				triggerAction : 'all',
				width : 500
			});

	// 接近状态combox
	var approachStatus_combobox = Ext.create('Ext.form.field.ComboBox', {
				fieldLabel : '接近状态',
				store : Ext.create('Ext.data.Store', {
							singleton : true,
							proxy : {
								type : 'ajax',
								url : 'spring/perception/getPerceptionParamListByTypeId',
								actionMethods : {
									read : 'POST'
								},
								extraParams : {
									paramId : 186
								},
								reader : {
									type : 'json',
									rootProperty : 'root'
								}
							},
							fields : ['value', 'text'],
							autoLoad : true
						}),
				displayField : 'text',
				valueField : 'value',
				emptyText : "请选择",
				editable : false,
				mode : 'remote',
				triggerAction : 'all',
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
				items : [switchStatus_combobox, rotateStatus_combobox, approachStatus_combobox, vibrateStatus_combobox,
						pressureStatus_combobox, infraredStatus_combobox, {
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
				items : [perceptionRuntimeBasic, perceptionRuntimeTabPanel]
			});

	perceptionRuntimeMainWin.show();

	// 使用ajax异步获取设备状态
	var remoteQueryPerception = function() {
		if (form.isValid()) {
			form.submit({
						url : 'spring/perception/remoteQueryPerception',
						waitMsg : '获取设备实时状态，请稍后...',
						params : {
							perceptionId : perceptionId
						},
						success : function(form, action, args) {
							var response = Ext.JSON.decode(action.response.responseText);
							switchStatus_combobox.setValue(response.switchStatus);
							rotateStatus_combobox.setValue(response.rotateStatus);
							approachStatus_combobox.setValue(response.approachStatus);
							vibrateStatus_combobox.setValue(response.vibrateStatus);
							pressureStatus_combobox.setValue(response.pressureStatus);
							infraredStatus_combobox.setValue(response.infraredStatus);
							perceptionRuntimeLogStoreLoad();
							vediaLogStoreLoad();
						},
						failure : function(form, action, args) {
							var response = Ext.JSON.decode(action.response.responseText);
							Ext.Msg.alert('Failurs', '设备实时状态获取失败,原因:' + response.msg);
						}
					});
		}
	}

	var remoteCtrlPerception = function() {
		var switchStatus = switchStatus_combobox.getValue();
		var rotateStatus = rotateStatus_combobox.getValue();

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
								Ext.Msg.alert('Success', '远程控制设备成功!');
								perceptionRuntimeLogStoreLoad();
							} else {
								Ext.Msg.alert('Failurs', '远程控制设备失败!');
							}
						},
						failure : function(form, action, args) {
							Ext.Msg.alert('Failurs', '远程控制设备失败!');
						}
					});
		}
	}

	remoteQueryPerception();
}


var playVedia = function(fileId) {
    alert('播放文件');
}
Ext.require('Ext.grid.*');
Ext.require('Ext.data.*');
Ext.require('Ext.panel.*');
Ext.require('Ext.layout.container.Border');
Ext.require('Ext.toolbar.Toolbar');
Ext.require('Ext.PagingToolbar');

Ext.onReady(function() {
			var store = Ext.create('Ext.data.Store', {
						fields : ['pid', 'proxyIp', 'proxyPort', 'isTelnetAvailable', 'isHttpGetAvailable',
								'isHttpPostAvailable', 'lastTestTime', 'remark'],

						proxy : {
							type : 'ajax',
							url : 'spring/proxyMgr/findByPage',

							// 将提交方式改为post
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

			var reflesh = function() {
				store.load({
							params : {
								start : 0,
								limit : 25
							}
						});
			};

			var pg = Ext.create('Ext.PagingToolbar', {
						store : store,
						pageSize : 25,
						displayInfo : true,
						displayMsg : '显示{0}-{1}条，共{2}条',
						emptyMsg : "没有数据"
					});

			var grid = Ext.create('Ext.grid.Panel', {
						title : '代理服务器列表',
						renderTo : Ext.getBody(),
						store : store,
						tbar : [{
									xtype : 'tbfill'
								}, {
									xtype : 'button',
									text : '导入...',
									handler : function() {
										showProxyImpWin(reflesh);
									}
								}],
						columns : [{
									xtype : 'rownumberer'
								}, {
									text : "代理主机地址",
									width : 80,
									dataIndex : 'proxyIp',
									sortable : true
								}, {
									text : "主机端口",
									width : 30,
									dataIndex : 'proxyPort',
									sortable : true
								}, {
									text : "支持telnet？",
									width : 30,
									dataIndex : 'isTelnetAvailable',
									sortable : true,
									renderer : function(val) {
										if ('是' != val)
											return "<span style='color:red'>" + val + "</span>";
										else
											return val;
									}
								}, {
									text : "支持http get？",
									width : 30,
									dataIndex : 'isHttpGetAvailable',
									sortable : true,
									renderer : function(val) {
										if ('是' != val)
											return "<span style='color:red'>" + val + "</span>";
										else
											return val;
									}
								}, {
									text : "支持http post？",
									width : 30,
									dataIndex : 'isHttpPostAvailable',
									sortable : true,
									renderer : function(val) {
										if ('是' != val)
											return "<span style='color:red'>" + val + "</span>";
										else
											return val;
									}
								}, {
									text : "最后通信时间",
									width : 30,
									dataIndex : 'lastTestTime',
									sortable : true
								}, {
									text : "备注",
									width : 40,
									dataIndex : 'remark',
									sortable : true
								}],
						bbar : pg,
						forceFit : true,
						height : 600,
						split : true
					});

			grid.getSelectionModel().on('selectionchange', function(sm, selectedRecord) {
					});

			reflesh();
		});
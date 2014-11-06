Ext.require(['Ext.grid.*', 'Ext.data.*', 'Ext.panel.*', 'Ext.layout.container.Border', 'Ext.toolbar.*',
		'Ext.PagingToolbar']);

Ext.onReady(function() {
			var store = Ext.create('Ext.data.Store', {
						fields : ['pid', 'proxyIp', 'proxyPort', 'protocol', 'available', 'lastTestTime', 'remark'],

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
				store.load();
			}

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
									width : 100,
									dataIndex : 'proxyIp',
									sortable : true
								}, {
									text : "主机端口",
									width : 30,
									dataIndex : 'proxyPort',
									sortable : true
								}, {
									text : "通信协议",
									width : 20,
									dataIndex : 'protocol',
									sortable : true
								}, {
									text : "是否可用",
									width : 20,
									dataIndex : 'available',
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
						bbar : Ext.create('Ext.PagingToolbar', {
									store : store,
									displayInfo : true,
									displayMsg : '显示{0}-{1}条，共{2}条',
									emptyMsg : "没有数据"
								}),
						forceFit : true,
						height : 600,
						split : true
					});

			grid.getSelectionModel().on('selectionchange', function(sm, selectedRecord) {
					});

			reflesh();
		});
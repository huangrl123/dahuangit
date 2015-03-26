$(function() {
			var grid = $('#perception-datagrid').datagrid({
						singleSelect : true,
						fitColumns : true,
						columns : [[{
									field : 'perceptionAddr',
									title : '设备地址',
									width : 80
								}, {
									field : 'perceptionName',
									title : '设备名称',
									width : 100
								}, {
									field : 'perceptionTypeName',
									title : '设备类型',
									width : 80
								}, {
									field : 'onlineStatus',
									title : '在线状态',
									width : 80,
									formatter : function(value, row) {
										if (0 == value) {
											return '<font color="red">离线</font>';
										} else if (1 == value) {
											return '<font color="blue">在线</font>';
										}
									}
								}, {
									field : 'perceptionId',
									title : '操作',
									width : 80,
									align : 'center',
									formatter : function(value, row) {
										return '<a href="#" onclick="toPerceptionStatusPage(' + value + ')">状态查看</a>';
									}
								}]],
						onClickRow : function(rowIndex, row) {
							$(this).datagrid('unselectRow', rowIndex);
						}
					});

			var load = function(pageNumber, pageSize, pg, conditionParams) {
				var start = 0;
				if (pageNumber > 0) {
					var willStart = (pageNumber - 1) * pageSize;
					if (willStart < pg.pagination('options').total) {
						start = willStart;
					}
				}

				var queryParams = {};
				queryParams['start'] = start;
				queryParams['limit'] = pageSize;
				if (conditionParams) {
					for (key in conditionParams) {
						queryParams[key] = conditionParams[key];
					}
				}

				grid.datagrid({
							url : '../perception/findPerceptionByPage',
							method : 'post',
							queryParams : queryParams,
							onLoadSuccess : function(data) {
								pg.pagination({
											total : data.total,
											pageNumber : pageNumber
										});

								$(window).resize();
							}
						});
			}

			var getConditionParams = function() {
				var conditionParams = {};
				conditionParams.perceptionAddr = $('#perceptionAddr').val();
				conditionParams.perceptionTypeId = $('#perceptionType').val();
				conditionParams.onlineStatus = $('#onlineStatus').val();
				return conditionParams;
			}

			var pg = $('#perception-pagination').pagination({
						pageSize : 10,
						total : 0,
						pageList : [10, 30, 50],
						beforePageText : '第',
						afterPageText : '页    共 {pages} 页',
						displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
						onSelectPage : function(pageNumber, pageSize) {
							load(pageNumber, pageSize, pg, getConditionParams());
						}
					});

			// 查询按钮的点击事件
			$('#queryBtn').click(function() {
						load(1, pg.pagination('options').pageSize, pg, getConditionParams());
					});

			// 进入页面时，加载数据
			load(1, pg.pagination('options').pageSize, pg, getConditionParams());
		});

var toPerceptionStatusPage = function(perceptionId) {
	window.location.href = '../perception/perceptionStatusPage?perceptionId=' + perceptionId;
}
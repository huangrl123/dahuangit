$(function() {
			parent.window['leftMenu'].updateArea('设备动作查询');

			var grid = $('#perceptionParam-datagrid').datagrid({
						singleSelect : true,
						fitColumns : true,
						columns : [[{
									field : 'perceptionRuntimeLogId',
									hidden : true,
									title : '日志主键id'
								}, {
									field : 'crateDatatime',
									title : '时间',
									width : 100
								}, {
									field : 'perceptionParamValueDesc',
									title : '动作',
									width : 80
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
							url : '../perception/findPerceptionParamLogByPage',
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
				conditionParams.perceptionId = $('#perceptionId').val();
				conditionParams.paramId = $('#perceptionParamId').val();
				conditionParams.perceptionParamValue = $('#perceptionParamValue').val();
				conditionParams.startTime = $('#startTime').val();
				conditionParams.endTime = $('#endTime').val();
				return conditionParams;
			}

			var pg = $('#perceptionParam-pagination').pagination({
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

			$('#startTime').datebox({
						editable : false
					});
			$('#endTime').datebox({
						editable : false
					});

			// 查询按钮的点击事件
			$('#queryBtn').click(function() {
						load(1, pg.pagination('options').pageSize, pg, getConditionParams());
					});

			// 返回按钮点击事件处理
			$('#backBtn').click(function() {
						parent.window['leftMenu'].updateArea('设备状态查询');
						window.location.href = '../perception/perceptionStatusPage?perceptionId=' + $('#perceptionId').val();
					});

			// 进入页面时，加载数据
			load(1, pg.pagination('options').pageSize, pg, getConditionParams());
		});

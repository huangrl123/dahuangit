$(function() {
			parent.window['leftMenu'].updateArea('设备查询');

			var grid = $('#perception-datagrid').datagrid({
				title : '设备列表',
				singleSelect : true,
				fitColumns : true,
				rownumbers : true,
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
								var s = JSON.stringify(row);
								s = s.replaceAll('"', '&quot;');
								return '<a href="#" onclick="toPerceptionStatusPage(' + value
										+ ')" style="padding-right:10px;">状态查看</a><a href="#" style="padding-right:10px;" onclick="showSavePerceptionWin(\'' + s
										+ '\')">修改</a><a href="#" style="padding-right:10px;" onclick="showRelateUserWin(\'' + s + '\')">关联用户</a><a onclick="deletePerception(' + value + ')">删除</a>';
							}
						}]],
				onClickRow : function(rowIndex, row) {
					$(this).datagrid('unselectRow', rowIndex);
				},
				toolbar : [{
							text : '添加',
							iconCls : 'icon-add',
							plain : 'true',
							handler : function() {
								showSavePerceptionWin();
							}
						}]
			});

			var loadByPage = function(pageNumber, pageSize, pg, conditionParams) {
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
				conditionParams.perceptionAddr = $('#queryPerceptionAddr').val();
				conditionParams.perceptionTypeId = $('#queryPerceptionType').val();
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
							loadByPage(pageNumber, pageSize, pg, getConditionParams());
						}
					});

			load = function() {
				loadByPage(1, pg.pagination('options').pageSize, pg, getConditionParams());
			}

			// 查询按钮的点击事件
			$('#queryBtn').click(function() {
						load();
					});

			// 进入页面时，加载数据
			load();
		});

var toPerceptionStatusPage = function(perceptionId) {
	window.location.href = '../perception/perceptionStatusPage?perceptionId=' + perceptionId;
}

function deletePerception(perceptionId) {
	showLoading();

	$.ajax({
				url : '../perception/deletePerception',
				cache : false,
				type : 'POST',
				dataType : 'JSON',
				data : {
					perceptionId : perceptionId
				},
				success : function(result) {
					if (result.success == true) {
						load();
					} else {
						showAlert(result.msg);
					}
					hideLoading();
				},
				error : function(result) {
					showAlert(result.msg);
					hideLoading();
				}
			});
}

function showRelateUserWin(perceptionJsonStr) {
	showLoading();

	var perception = JSON.parse(perceptionJsonStr);

	$.ajax({
				url : '../perception/getRelateUser',
				cache : false,
				type : 'POST',
				dataType : 'JSON',
				data : {
					perceptionId : perception.perceptionId
				},
				success : function(result) {
					if (result.success == true) {
						relateUser(perception, result.allUserList, result.relateUserList);
					}

					hideLoading();
				},
				error : function() {
					hideLoading();
				}
			});

}

function relateUser(perception, allUserList, relateUserList) {
	$("#select1").empty();
	for (var i = 0; i < allUserList.length; i++) {
		var u = allUserList[i];
		$('<option value="' + u.userId + '">' + u.userName + '</option>').appendTo('#select1');
	}

	$("#select2").empty();
	for (var i = 0; i < relateUserList.length; i++) {
		var u = relateUserList[i];
		$('<option value="' + u.userId + '">' + u.userName + '</option>').appendTo('#select2');
	}

	// 判断select中是否存在值为value的项
	var isExistOption = function(id, value) {
		var isExist = false;

		var count = $('#' + id).find('option').length;
		for (var i = 0; i < count; i++) {
			if ($('#' + id).get(0).options[i].value == value) {
				isExist = true;
				break;
			}
		}

		return isExist;
	}

	// 增加select项
	var moveOption = function(srcSelectId, targetSelectId) {
		var optionArr = $('#' + srcSelectId + ' option:selected');

		for (var i = 0; i < optionArr.length; i++) {
			var o = optionArr[i];
			var value = o.value;
			var text = o.text;

			var isExist = isExistOption(targetSelectId, value);
			if (!isExist) {
				$('#' + targetSelectId).append("<option value=" + value + ">" + text + "</option");
				$('#' + srcSelectId + ' option[value="' + value + '"]').remove();
			}
		}
	}

	// 移到右边
	$('#add').click(function() {
				moveOption('select1', 'select2');
			});

	// 移到左边
	$('#remove').click(function() {
				moveOption('select2', 'select1');
			});

	// 全部移到右边
	$('#add_all').click(function() {
				moveOption('select1', 'select2');
			});

	// 全部移到左边
	$('#remove_all').click(function() {
				moveOption('select2', 'select1');
			});

	// 双击选项
	$('#select1').dblclick(function() { // 绑定双击事件
				moveOption('select1', 'select2');
			});

	// 双击选项
	$('#select2').dblclick(function() {
				moveOption('select2', 'select1');
			});

	var relateUserWin = $('#relateUserWin').window({
				title : '设备(' + perception.perceptionAddr + ')关联用户',
				width : 386,
				height : 405,
				collapsible : false,
				minimizable : false,
				maximizable : false,
				closable : false,
				resizable : false,
				draggable : false,
				modal : true
			});

	$('#relateUserWinBtn').unbind('click');
	$('#relateUserWinBtn').bind('click', function() {
				var optionArr = $('#select2 option');
				var userIds = '';
				for (var i = 0; i < optionArr.length; i++) {
					if (i == optionArr.length - 1) {
						userIds = userIds + optionArr[i].value;
					} else {
						userIds = userIds + optionArr[i].value + ',';
					}
				}

				showLoading();

				$.ajax({
							url : '../perception/updatePerceptionManagers',
							cache : false,
							type : 'POST',
							dataType : 'JSON',
							data : {
								perceptionId : perception.perceptionId,
								userIds : userIds
							},
							success : function(result) {
								if (result.success == true) {
									relateUserWin.window('close');
								} else {
									showAlert(result.msg);
								}
								hideLoading();
							},
							error : function(result) {
								showAlert(result.msg);
								hideLoading();
							}
						});
			});

	$('#relateUserWinCloseBtn').unbind('click');
	$('#relateUserWinCloseBtn').bind('click', function() {
				relateUserWin.window('close');
			});
}
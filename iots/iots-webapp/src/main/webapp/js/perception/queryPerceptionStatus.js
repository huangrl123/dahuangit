$(function() {
			parent.window['leftMenu'].updateArea('设备状态查询');

			$('#backBtn').click(function() {
						parent.window['leftMenu'].updateArea('设备查询');
						window.location.href = '../perception/queryPerceptionPage';
					});

			var refreshByInterval = function() {
				var time = 3 * 1000;
				window.setTimeout(time);
				window.setInterval('query()', time);
			}

			refreshByInterval();

			var videoListLookBtn = $('#videoListLookBtn');
			if (videoListLookBtn) {
				videoListLookBtn.unbind('click');
				videoListLookBtn.bind('click', function() {
							showVideoListWin();
						});
			}

			var realTimeVideoLookBtn = $('#realTimeVideoLookBtn');
			if (realTimeVideoLookBtn) {
				realTimeVideoLookBtn.unbind('click');
				realTimeVideoLookBtn.bind('click', function() {
							showRealTimeVideoWin();
						});
			}
		});

function query() {
	$.ajax({
				url : '../appMgrPerceptionController/appPerceptionFunctionListAjax',
				type : 'GET',
				dataType : 'JSON',
				data : {
					perceptionId : $('#perceptionId').val(),
					isInit : false
				},
				cache : false,
				success : function(result, textStatus) {
					if (result.success) {
						$('#onlineStatus').text(result.onlineStatusDesc);

						if (null != result.lastCommTime) {
							$('#lastCommTime').text(result.lastCommTime);
						}

						var warningParamInfos = result.warningParamInfos;
						for (var i = 0; i < warningParamInfos.length; i++) {
							var param = warningParamInfos[i];
							$('#' + param.paramId).text(param.paramValueDesc);
						}

						var ctrlParamInfos = result.ctrlParamInfos;
						for (var i = 0; i < ctrlParamInfos.length; i++) {
							var param = ctrlParamInfos[i];
							$('#' + param.paramId).val(param.paramValue);
						}

					}
				}
			});
}

function remoteCtrl(select) {
	var progress = $.messager.progress({
				title : '提示',
				text : '',
				msg : '正在请求,请稍后...'
			});

	var id = select.attr('id');
	var value = select.val();
	$.ajax({
				url : '../perception/remoteCtrlPerception',
				type : 'POST',
				dataType : 'JSON',
				data : {
					perceptionId : $('#perceptionId').val(),
					paramId : id,
					paramValue : value,
					perceptionTypeId : $('#perceptionTypeId').val()
				},
				cache : false,
				success : function(result) {
					$.messager.progress('close');

					if (result.success == false) {
						showAlert(result.msg);
					} else {
						showAlert('远程控制成功');
					}
				},
				error : function(result) {
					$.messager.progress('close');
					showAlert(result.msg);
				}
			});
}

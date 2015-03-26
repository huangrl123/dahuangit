var query = function() {
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

var remoteCtrl = function(select) {
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
					if (result.success == false) {
						alert(result.msg);
					}
				},
				error : function(result) {
				}
			});
}

$(function() {
			$('#backBtn').click(function() {
						history.back();
					});

			var refreshByInterval = function() {
				var time = 3 * 1000;
				window.setTimeout(time);
				window.setInterval('query()', time);
			}

			refreshByInterval();
		});

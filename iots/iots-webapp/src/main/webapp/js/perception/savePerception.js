function showSavePerceptionWin(perceptionJsonStr) {
	var perception;
	var url;
	var title;
	if (perceptionJsonStr) {
		perception = JSON.parse(perceptionJsonStr);
		url = '../perception/updatePerception';
		title = '修改设备';
		$('#perceptionId').val(perception.perceptionId);
		$('#perceptionTypeId').val(perception.perceptionTypeId);
		$('#perceptionTypeId').attr('disabled', true);
		$('#perceptionAddr').val(perception.perceptionAddr);
		$('#perceptionName').val(perception.perceptionName);
	} else {
		url = '../perception/addPerception';
		title = '添加设备';
		$('#savePerceptionForm')[0].reset();
		$('#perceptionTypeId').attr('disabled', false);
	}

	var savePerceptionWin = $('#savePerceptionWin').window({
				title : title,
				width : 300,
				height : 200,
				collapsible : false,
				minimizable : false,
				maximizable : false,
				closable : false,
				resizable : false,
				draggable : false,
				modal : true
			});

	$('#savePerceptionBtn').unbind('click');
	$('#savePerceptionBtn').bind('click', function() {
				showLoading();

				$.ajax({
							url : url,
							cache : false,
							type : 'POST',
							dataType : 'JSON',
							data : $('#savePerceptionForm').serialize(),
							success : function(result) {
								if (result.success == true) {
									load();
									savePerceptionWin.window('close');
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

	$('#savePerceptionCloseBtn').unbind('click');
	$('#savePerceptionCloseBtn').bind('click', function() {
				savePerceptionWin.window('close');
			});
}
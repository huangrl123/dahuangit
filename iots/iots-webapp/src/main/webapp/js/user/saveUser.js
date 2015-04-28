function showSaveUserWin(userJsonStr) {
	var user;
	var url;
	if (userJsonStr) {
		user = JSON.parse(userJsonStr);
		url = '../userController/updateUser';
		$('#userId').val(user.userId);
		$('#userName').val(user.userName);
		$('#userAbbr').val(user.userAbbr);
	} else {
		url = '../userController/addUser';
		$('#userId').val('');
		$('#userName').val('');
		$('#userAbbr').val('');
	}

	var addUserWin = $('#saveUserWin').window({
				title : '用户添加',
				width : 300,
				height : 160,
				collapsible : false,
				minimizable : false,
				maximizable : false,
				closable : false,
				resizable : false,
				draggable : false,
				modal : true
			});

	$('#saveUserBtn').bind('click', function() {
				showLoading();

				$.ajax({
							url : url,
							cache : false,
							type : 'POST',
							dataType : 'JSON',
							data : $('#saveUserForm').serialize(),
							success : function(result) {
								if (result.success == true) {
									load();
									addUserWin.window('close');
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

	$('#saveUserCloseBtn').bind('click', function() {
				addUserWin.window('close');
			});
}
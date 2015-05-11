function showSaveUserWin(userJsonStr) {
	var user;
	var url;
	var title;
	if (userJsonStr) {
		user = JSON.parse(userJsonStr);
		title = '修改用户';
		url = '../userController/updateUser';
		$('#userId').val(user.userId);
		$('#userName').val(user.userName);
		$('#userAbbr').val(user.userAbbr);
	} else {
		url = '../userController/addUser';
		title = '添加用户';
		$('#userId').val('');
		$('#userName').val('');
		$('#userAbbr').val('');
	}

	var saveUserWin = $('#saveUserWin').window({
				title : title,
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

	$('#saveUserBtn').unbind('click');
	$('#saveUserBtn').bind('click', function() {
				var userName = $('#userName').val();
				userName = $.trim(userName);
				if (!userName) {
					showAlert('用户名不能为空');
					return;
				}

				var userAbbr = $('#userAbbr').val();
				userAbbr = $.trim(userAbbr);
				if (!userAbbr) {
					showAlert('用户昵称不能为空');
					return;
				}

				$('#userName').val(userName);
				$('#userAbbr').val(userAbbr);

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
									saveUserWin.window('close');
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

	$('#saveUserCloseBtn').unbind('click');
	$('#saveUserCloseBtn').bind('click', function() {
				saveUserWin.window('close');
			});
}
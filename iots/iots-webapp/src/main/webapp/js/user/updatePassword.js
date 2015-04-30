$(function() {
			$('#updatePasswordBtn').unbind('click');
			$('#updatePasswordBtn').bind('click', function() {
						var oldUserPassword = $('#oldUserPassword').val();
						var oldPassword = $('#oldPassword').val();
						oldPassword = $.trim(oldPassword);
						if (!oldPassword) {
							showAlert('旧密码输入不能为空');
							return;
						}

						if (oldPassword != $('#oldUserPassword').val()) {
							showAlert('旧密码输入错误');
							return;
						}

						var newPassword = $('#newPassword').val();
						newPassword = $.trim(newPassword);
						if (!newPassword) {
							showAlert('新密码输入不能为空');
							return;
						}

						if (oldPassword == newPassword) {
							showAlert('新密码和旧密码不能相同');
							return;
						}

						var newConfirmPassword = $('#newConfirmPassword').val();
						newConfirmPassword = $.trim(newConfirmPassword);
						if (!newConfirmPassword) {
							showAlert('新确认密码输入不能为空');
							return;
						}

						if (newConfirmPassword != newPassword) {
							showAlert('新密码和新确认密码不能相同');
							return;
						}

						showLoading();

						$.ajax({
									url : '../userController/updateUser',
									cache : false,
									type : 'POST',
									dataType : 'JSON',
									data : {
										userId : $('#userId').val(),
										password : newPassword
									},
									success : function(result) {
										if (result.success == true) {
											showAlert('密码修改成功');
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

			$('#updatePasswordResetBtn').unbind('click');
			$('#updatePasswordResetBtn').bind('click', function() {
						$('#updatePasswordForm')[0].reset();
					});
		})
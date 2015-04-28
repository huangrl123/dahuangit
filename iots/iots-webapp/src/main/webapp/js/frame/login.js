$(function() {
			var loginAndRegDialog = $("#loginAndRegDialog");

			loginAndRegDialog.dialog({
						closable : false,
						modal : true,
						draggable : false
					});

			var loginInputForm = $('#loginInputForm');

			$('#resetLoginFormBtn').bind('click', function() {
						loginInputForm[0].reset();
					});

			$('#submitSysBtn').bind('click', function() {
						var userName = $('#userName').val();
						if (!userName || $.trim(userName) == '') {
							showAlert('用户名不能为空');
							return;
						}

						var password = $('#password').val();
						if (!password || $.trim(password) == '') {
							showAlert('密码不能为空');
							return;
						}

						showLoading();
						$.ajax({
									url : '../userController/login',
									cache : false,
									type : 'POST',
									dataType : 'JSON',
									data : loginInputForm.serialize(),
									success : function(result) {
										if (result.success == true) {
											window.location.href = '../frame/main';
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

		});
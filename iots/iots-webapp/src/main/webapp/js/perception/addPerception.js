$(function() {
			$('#addPerceptionBtn').click(function() {
						var perceptionType = $('#perceptionType').val();

						var percetionAddr = $('#perceptionAddr').val();
						if (percetionAddr == '') {
							alert('设备地址不能为空');
							return;
						}

						if (perceptionAddr.length > 32) {
							alert('设备地址应为长度不不能大于32的数字或者字母');
							return;
						}

						var perceptionName = $('#perceptionName').val();
						if (perceptionName == '') {
							alert('设备名称不能为空');
							return;
						}

						$.ajax({
									url : '../perception/addPerception',
									type : 'POST',
									dataType : 'JSON',
									cache : false,
									data : {
										perceptionTypeId : perceptionType,
										percetionAddr : percetionAddr,
										perceptionName : perceptionName
									},
									success : function(result) {
										if (result.success) {
											parent.window['leftMenu'].updateArea('设备查询');
											window.location.href = '../perception/queryPerceptionPage';
										} else {
											alert(result.msg);
										}
									},
									error : function() {
										alert('保存失败');
									}
								});
					});
		});
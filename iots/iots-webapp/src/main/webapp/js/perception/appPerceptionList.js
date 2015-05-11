$(function() {
			$.ajax({
						url : '../perception/findPerceptionByPage',
						type : 'POST',
						dataType : 'JSON',
						cache : false,
						success : function(result) {
							var rows = result.rows;

							var perceptionImg = {};
							perceptionImg['1-0'] = '../image/2jia2offline.jpg';
							perceptionImg['1-1'] = '../image/2jia2online.jpg';
							perceptionImg['2-0'] = '../image/6jia6offline.jpg';
							perceptionImg['2-1'] = '../image/6jia6online.jpg';
							perceptionImg['3-0'] = '../image/5jia7offline.jpg';
							perceptionImg['3-1'] = '../image/5jia7online.jpg';

							var s = '';
							for (var i = 0; i < rows.length; i++) {
								var p = rows[i];
								var key = p.perceptionTypeId.toString() + '-' + p.onlineStatus.toString();
								var userId = $('#userId').val();
								var url = '../appMgrPerceptionController/appPerceptionFunctionList?userId=' + userId + '&perceptionId=' + p.perceptionId;

								var td = '<td width="50%" height="50%" align="center"><a href="' + url + '" data-ajax="false" style="color:#866E54;text-decoration:none;"><img alt="" src="'
										+ perceptionImg[key] + '" width="100%">' + p.perceptionName + '</a></td>';

								if (i % 2 == 0) {
									s = s + '<tr>';
									s = s + td;
								} else {
									s = s + td;
									s = s + '</tr>';
								}
							}

							$('#perceptionListTable').append(s);
						}
					});

		})

function exitToLogin() {
	var userAgent = navigator.userAgent;

	if (userAgent.indexOf("Android") > -1) {
		window.app.exitSys();
		return;
	}

	window.location.href = '../spring/mobile/exitSys';
}
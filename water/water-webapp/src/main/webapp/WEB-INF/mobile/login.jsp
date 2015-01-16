<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>登录界面</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta http-equiv="content-language" content="zh-CN" />
<link rel="stylesheet" href="${ctx }/plugin/jquerymobile/jquery.mobile-1.4.5.min.css" />
<script src="${ctx }/plugin/jquery/jquery-2.1.3.min.js"></script>
<script src="${ctx }/plugin/jquery/jquery-utils.js"></script>
<script src="${ctx }/plugin/jquerymobile/jquery.mobile-1.4.5.min.js"></script>
<script type="text/javascript">
	window.onload = function() {
		try{
			hideLoader();
		}catch(e){
		}
	}
</script>
<script type="text/javascript">
	function login() {
		var formData = $("#loginForm").serialize();

		showLoader();

		$.ajax({
			url : '${ctx}/spring/mobile/submitLogin',
			type : 'POST',
			data : formData,
			dataType : 'JSON',
			cache : false,
			success : function(result, textStatus) {
				if (result.success) {
					window.location.href = '${ctx}/spring/mobile/functionList';
				} else {
					showAlertDialog(result.msg);
				}

				hideLoader();
			},
		});

	}
</script>
</head>
<body>

	<div data-role="page">
		<div data-role="header" data-position="fixed">
			<h1>用户登录</h1>
		</div>

		<div data-role="content">

			<form id="loginForm" action="${ctx }/spring/mobile/login" method="post">
				<div data-role="fieldcontain">
					<input type="text" id="optNum" name="optNum" value="admin" placeholder="请输入账号" />
				</div>
				<div data-role="fieldcontain">
					<input type="password" id="password" name="password" value="admin" placeholder="请输入密码" />
				</div>
				<div data-role="fieldcontain">
					<input type="button" value="登录" onclick="login()" />
				</div>
			</form>

		</div>

	</div>

</body>
</html>


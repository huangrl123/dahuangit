<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>工业自动化设备管理系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<script src="../plugin/jquery/jquery-1.11.2.min.js"></script>

<link rel="stylesheet" type="text/css" href="../plugin/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../plugin/jquery-easyui/themes/icon.css">
<script type="text/javascript" src="../plugin/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../plugin/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../plugin/jquery-easyui/jquery.easyui-util.js"></script>

<script type="text/javascript" src="../js/frame/login.js"></script>

</head>
<body>
	<div id="loginAndRegDialog" title="工业自动化设备管理系统" style="width: 320px; height: 160px;">
		<form id="loginInputForm" action="post">
			<table style="padding-top: 5px;padding-left:28px;">
				<tr>
					<td>用户名:</td>
					<td><input id="userName" name="userName" type="text" /></td>
				</tr>
				<tr>
					<td>密码:</td>
					<td><input id="password" name="password" type="password" /></td>
				</tr>
			</table>
			<div style="text-align: center; padding-top: 25px">
				<a id="submitSysBtn" href="javascript:void(0)" class="easyui-linkbutton" style="margin-right: 5px; width: 90px;" onclick="submitForm()">登 录</a> <a id="resetLoginFormBtn" href="javascript:void(0)" style="margin-left: 5px; width: 90px;"
					class="easyui-linkbutton" onclick="clearForm()">重 置</a>
			</div>
		</form>
	</div>
</body>
</html>

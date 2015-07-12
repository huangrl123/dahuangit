<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../common/base.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<title>功能列表</title>
<meta name="viewport" content="width=device-width,initial-scale=1, minimum-scale=1, maximum-scale=1">
<meta http-equiv="content-language" content="zh-CN" />
<style type="text/css">
#shuibiaoDiv {
	height: 120px;
	margin-top: -1;
}

#exit-link {
	font-size: 16px;
	color: #ffffff;
	text-shadow: none;
	padding-top: 3px;
}
.header-title1-right1 {
	float: right;
	width: 15%;
	padding-right: 10px;
	margin-top: 8px;
}
</style>
<link rel="stylesheet" href="../plugin/jquerymobile/jquery.mobile-1.4.5.min.css" />
<script src="../plugin/jquery/jquery-2.1.3.min.js"></script>
<script src="../plugin/jquery/jquery-utils.js"></script>
<script src="../plugin/jquerymobile/jquery.mobile-1.4.5.min.js"></script>

<link rel="stylesheet" href="../css/water.css" />

<script src="../js/perception/appPerceptionList.js"></script>

</head>
<body style="margin: 0px;">
    <input type="hidden" id="userId" value="${userId }">
	<div data-role="header" data-position="fixed" data-tap-toggle="true" class="header-div" style="border-color: #2C3640; margin-top: -1px;">
		<div class="header-title1">
			<span class="header-title1-left"></span> <span class="header-title1-center">首页</span> <span class="header-title1-right1" onclick="exitToLogin();" id="exit-link">退出</span>
		</div>
	</div>

	<table id="perceptionListTable" width="100%" border="0" cellspacing="10" cellpadding="0" bordercolor="#FFFFFF">
	</table>
</body>
</html>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>功能列表</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta http-equiv="content-language" content="zh-CN" />
<style type="text/css">
#headDiv {
	height: 60px;
	text-align: center;
	background-color: #2C3640;
	font-size: 30;
	color: #FFFFFF;
	padding-top: 22px;
}

#shuibiaoDiv {
	height: 120px;
}
</style>
<script type="text/javascript">
	function gotoFunction(url) {
		window.location.href = url + '?systemId=${systemId}';
	}
</script>
</head>
<body style="margin: 0px;">
	<div id="headDiv">
		<span style="padding-left:50px;">首页</span><span style="padding-right: 20px;float: right; font-size: 20; padding-top: 5px;" onclick="window.app.exitSys()">退出</span>
	</div>
	<div id="shuibiaoDiv">
		<img alt="" src="../../images/shuibiao.png" height="120px" width="100%">
	</div>
	<table width="100%" border="0" cellspacing="10" cellpadding="0" bordercolor="#FFFFFF">
		<tr>
			<td><img alt="" src="../../images/shouzhi.png" width="100%" onclick="gotoFunction('${ctx }/spring/mobile/shouzhiQuery')"></td>
			<td><img alt="" src="../../images/yujing.png" width="100%" onclick="gotoFunction('${ctx }/spring/mobile/yujingQuery')"></td>
		</tr>
		<tr>
			<td><img alt="" src="../../images/sunyi.png" width="100%" onclick="gotoFunction('${ctx }/spring/mobile/sunyiQuery')"></td>
			<td><img alt="" src="../../images/yongshui.png" width="100%" onclick="gotoFunction('${ctx }/spring/mobile/yongshui')"></td>
		</tr>
		<tr>
			<td><img alt="" src="../../images/wajue.png" width="100%"></td>
			<td><img alt="" src="../../images/blank.png" width="100%"></td>
		</tr>
	</table>
</body>
</html>


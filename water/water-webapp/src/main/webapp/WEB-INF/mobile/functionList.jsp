<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>功能列表</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta http-equiv="content-language" content="zh-CN" />
<style type="text/css">
#shuibiaoDiv {
	height: 120px;
}

#exit-link {
	font-size: 16px;
	color: #ffffff;
	text-shadow: none;
	padding-top: 3px;
}
</style>
<link rel="stylesheet" href="${ctx }/plugin/jquerymobile/jquery.mobile-1.4.5.min.css" />
<script src="${ctx }/plugin/jquery/jquery-2.1.3.min.js"></script>
<script src="${ctx }/plugin/jquery/jquery-utils.js"></script>
<script src="${ctx }/plugin/jquerymobile/jquery.mobile-1.4.5.min.js"></script>

<link rel="stylesheet" href="${ctx }/css/water.css" />

</head>
<body style="margin: 0px;">

	<div class="header-title1">
		<span class="header-title1-left"></span> <span class="header-title1-center">首页</span> <span class="header-title1-right" onclick="exitToLogin();" id="exit-link">退出</span>
	</div>

	<div id="shuibiaoDiv">
		<img alt="" src="../../images/shuibiao.png" height="120px" width="100%">
	</div>
	<table width="100%" border="0" cellspacing="10" cellpadding="0" bordercolor="#FFFFFF">
		<tr>
			<td width="50%" height="50%"><img alt="" src="../../images/shouzhi.png" width="100%" onclick="gotoFunction('${ctx }/spring/mobile/shouzhiQuery?systemId=${systemId}')"></td>
			<td width="50%" height="50%"><img alt="" src="../../images/yujing.png" width="100%" onclick="gotoFunction('${ctx }/spring/mobile/yujingQuery?systemId=${systemId}')"></td>
		</tr>
		<tr>
			<td width="50%" height="50%"><img alt="" src="../../images/sunyi.png" width="100%" onclick="gotoFunction('${ctx }/spring/mobile/sunyiQuery?systemId=${systemId}')"></td>
			<td width="50%" height="50%"><img alt="" src="../../images/yongshui.png" width="100%" onclick="gotoFunction('${ctx }/spring/mobile/yongshui?systemId=${systemId}')"></td>
		</tr>
		<tr>
			<td width="50%" height="50%"><img alt="" src="../../images/wajue.png" width="100%"></td>
			<td width="50%" height="50%"></td>
		</tr>
	</table>
</body>
<script type="text/javascript">
	function exitToLogin() {
		var userAgent = navigator.userAgent;

		if (userAgent.indexOf("Android") > -1) {
			window.app.exitSys();
			return;
		}

		window.location.href = '${ctx }/spring/mobile/exitSys';
	}

	$(window).resize();
</script>
</html>


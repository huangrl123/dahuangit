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
	height: 40px;
	text-align: center;
	background-color: #2C3640;
	text-shadow: none;
	font-size: 22px;
	font-weight: 900;
	color: #FFFFFF;
	padding-top: 11px;
}

#shuibiaoDiv {
	height: 120px;
}

#exit-link {
	padding-right: 20px;
	float: right;
	font-size: 16;
	font-weight: normal;
	padding-top: 4px;
}
</style>
<link rel="stylesheet" href="${ctx }/plugin/jquerymobile/jquery.mobile-1.4.5.min.css" />
<script src="${ctx }/plugin/jquery/jquery-2.1.3.min.js"></script>
<script src="${ctx }/plugin/jquery/jquery-utils.js"></script>
<script src="${ctx }/plugin/jquerymobile/jquery.mobile-1.4.5.min.js"></script>

</head>
<body style="margin: 0px;">
	<div id="headDiv">
		<span style="padding-left: 50px;">首页</span><span id="exit-link" onclick="window.app.exitSys()">退出</span>
	</div>
	<div id="shuibiaoDiv">
		<img alt="" src="../../images/shuibiao.png" height="120px" width="100%">
	</div>
	<table width="100%" border="0" cellspacing="10" cellpadding="0" bordercolor="#FFFFFF">
		<tr>
			<td><img alt="" src="../../images/shouzhi.png" width="100%" onclick="gotoFunction('${ctx }/spring/mobile/shouzhiQuery?systemId=${systemId}')"></td>
			<td><img alt="" src="../../images/yujing.png" width="100%" onclick="gotoFunction('${ctx }/spring/mobile/yujingQuery?systemId=${systemId}')"></td>
		</tr>
		<tr>
			<td><img alt="" src="../../images/sunyi.png" width="100%" onclick="gotoFunction('${ctx }/spring/mobile/sunyiQuery?systemId=${systemId}')"></td>
			<td><img alt="" src="../../images/yongshui.png" width="100%" onclick="gotoFunction('${ctx }/spring/mobile/yongshui?systemId=${systemId}')"></td>
		</tr>
		<tr>
			<td><img alt="" src="../../images/wajue.png" width="100%"></td>
			<td></td>
		</tr>
	</table>
	<script type="text/javascript">
		$(window).resize();
	</script>
</body>
</html>


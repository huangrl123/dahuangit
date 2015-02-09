<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>移动应用首页</title>
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
</head>
<body>

	<div data-role="page">
		<div data-role="header" data-position="fixed" style="background:#70A4C9;">
			<h1><font color="#FFFFFF">功能列表</font></h1>
		</div>

		<div data-role="content">
			<ul data-role="listview" data-inset="true">
				<li><a href="${ctx }/spring/mobile/shouzhi" data-ajax="false" onclick="showLoader()">收资统计表</a></li>
				<li><a href="${ctx }/spring/mobile/yujing" data-ajax="false" onclick="showLoader()">设备预警表</a></li>
				<li><a href="${ctx }/spring/mobile/sunyiQuery" data-ajax="false" onclick="showLoader()">损益表</a></li>
				<li><a href="${ctx }/spring/mobile/yongshui" data-ajax="false" onclick="showLoader()">楼栋用水总额登记</a></li>
			</ul>

			<form action="${ctx }/spring/mobile/index" method="get" data-ajax="false">
				<div data-role="fieldcontain">
					<input type="submit" name="submit" id="submit" value="退出当前账号" onclick="showLoader('正在退出...')"/>
				</div>
			</form>
		</div>

	</div>

</body>
</html>


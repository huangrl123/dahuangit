<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>移动应用首页</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta http-equiv="content-language" content="zh-CN" />

</head>
<body>

	<div data-role="page">
		<link rel="stylesheet" href="${ctx }/plugin/jquerymobile/jquery.mobile-1.4.5.min.css" />
		<script src="${ctx }/plugin/jquery/jquery-2.1.3.min.js"></script>
		<script src="${ctx }/plugin/jquerymobile/jquery.mobile-1.4.5.min.js"></script>
		<div data-role="header" data-position="fixed">
			<h1>功能列表</h1>
		</div>

		<div data-role="content">

			<ul data-role="listview" data-inset="true">
				<li><a href="${ctx }/spring/mobile/shouzhi" data-ajax="false">收资统计表</a></li>
				<li><a href="${ctx }/spring/mobile/yujing" data-ajax="false">设备预警表</a></li>
				<li><a href="${ctx }/spring/mobile/jiankong" data-ajax="false">热泵监控表</a></li>
				<li><a href="${ctx }/spring/mobile/sunyiQuery" data-ajax="false">损益表</a></li>
				<li><a href="${ctx }/spring/mobile/yongshui" data-ajax="false">楼栋用水总额登记</a></li>
				<li><a href="${ctx }/spring/mobile/shouzhi" data-ajax="false">个人信息</a></li>
			</ul>

			<form action="${ctx }/spring/mobile/index" method="get" data-ajax="false">

				<div data-role="fieldcontain">
					<input type="submit" name="submit" id="submit" value="退出当前账号"/>
				</div>

			</form>
		</div>

	</div>

</body>
</html>


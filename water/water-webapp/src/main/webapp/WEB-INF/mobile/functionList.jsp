<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>移动应用首页</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet" href="${ctx }/plugin/jquerymobile/jquery.mobile-1.4.5.min.css" />
<script src="${ctx }/plugin/jquery/jquery-2.1.3.min.js"></script>
<script src="${ctx }/plugin/jquerymobile/jquery.mobile-1.4.5.min.js"></script>

</head>
<body>

	<div data-role="page">

		<div data-role="header" data-position="fixed">
			<h1>功能列表</h1>
		</div>

		<div data-role="content">

			<ul data-role="listview" data-inset="true">
				<li><a href="${ctx }/spring/mobile/shouzhi">收资统计表</a></li>
				<li><a href="${ctx }/spring/mobile/yujing">设备预警表</a></li>
				<li><a href="${ctx }/spring/mobile/jiankong">热泵监控表</a></li>
				<li><a href="${ctx }/spring/mobile/sunyiQuery">损益表</a></li>
				<li><a href="${ctx }/spring/mobile/yongshui">楼栋用水总额登记</a></li>
				<li><a href="${ctx }/spring/mobile/shouzhi">个人信息</a></li>
			</ul>

		</div>

	</div>

</body>
</html>


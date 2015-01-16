<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>个人账户信息页面</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta http-equiv="content-language" content="zh-CN" />
<link rel="stylesheet" href="${ctx }/plugin/jquerymobile/jquery.mobile-1.4.5.min.css" />
<script src="${ctx }/plugin/jquery/jquery-2.1.3.min.js"></script>
<script src="${ctx }/plugin/jquery/jquery-utils.js"></script>
<script src="${ctx }/plugin/jquerymobile/jquery.mobile-1.4.5.min.js"></script>
<style type="text/css">
	.listItemLeft {
		float: left; 
		margin-right: 10; 
		color: #866E54
	}
	.listItemRight {
		float: right; 
		color: #469E6F;
	}
</style>
</head>
<body>

	<div data-role="page">

		<div data-role="header" data-position="fixed">
			<a href="${ctx }/spring/mobile/functionList" data-icon="home" data-ajax="false" onclick="showLoader()">首页</a>
			<h1>个人信息</h1>
		</div>

		<div data-role="content">

			<ul data-role="listview">
				<li data-role="list-divider">角色列表</li>
				<li>
					<span class="listItemLeft">1</span>
					<span class="listItemRight">抄表员</span>
				</li>
				<li>
					<span class="listItemLeft">2</span>
					<span class="listItemRight">系统管理员</span>
				</li>
			</ul>

		</div>

	</div>
</body>
</html>


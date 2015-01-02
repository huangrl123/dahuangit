<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>收支</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta http-equiv="content-language" content="zh-CN" />
<link rel="stylesheet" href="${ctx }/plugin/jquerymobile/jquery.mobile-1.4.5.min.css" />
<script src="${ctx }/plugin/jquery/jquery-2.1.3.min.js"></script>
<script src="${ctx }/plugin/jquerymobile/jquery.mobile-1.4.5.min.js"></script>

<style type="text/css">
</style>
</head>
<body>

	<div data-role="page">

		<div data-role="header" data-position="fixed">
			<a href="${ctx }/spring/mobile/index" data-icon="home">首页</a>
			<h1>收支统计表</h1>
			<a href="${ctx }/spring/mobile/shouzhiQuery" data-icon="search">查询</a>
		</div>

		<div data-role="content">

			<ul data-role="listview">
				<li data-role="list-divider">项目1<span style="color: red;">(￥4565)</span></li>
				<li><span style="float: left; margin-right: 10; color: #866E54">2014-12-26 34:45:32</span><span
					style="float: left; margin-left: 10; color: #CACACA">操作员1</span><span style="float: right; color: #469E6F;">￥4565</span></li>
				<li><span style="float: left; margin-right: 10; color: #866E54">2014-12-26 34:45:32</span><span
					style="float: left; margin-left: 10; color: #CACACA">操作员1</span><span style="float: right; color: #469E6F;">￥4565</span></li>
				<li><span style="float: left; margin-right: 10; color: #866E54">2014-12-26 34:45:32</span><span
					style="float: left; margin-left: 10; color: #CACACA">操作员1</span><span style="float: right; color: #469E6F;">￥4565</span></li>
				<li><span style="float: left; margin-right: 10; color: #866E54">2014-12-26 34:45:32</span><span
					style="float: left; margin-left: 10; color: #CACACA">操作员1</span><span style="float: right; color: #469E6F;">￥4565</span></li>
				<li data-role="list-divider">项目1<span style="color: red;">(￥4565)</span></li>
				<li><span style="float: left; margin-right: 10; color: #866E54">2014-12-26 34:45:32</span><span
					style="float: left; margin-left: 10; color: #CACACA">操作员1</span><span style="float: right; color: #469E6F;">￥4565</span></li>
				<li><span style="float: left; margin-right: 10; color: #866E54">2014-12-26 34:45:32</span><span
					style="float: left; margin-left: 10; color: #CACACA">操作员1</span><span style="float: right; color: #469E6F;">￥4565</span></li>
				<li><span style="float: left; margin-right: 10; color: #866E54">2014-12-26 34:45:32</span><span
					style="float: left; margin-left: 10; color: #CACACA">操作员1</span><span style="float: right; color: #469E6F;">￥4565</span></li>
				<li><span style="float: left; margin-right: 10; color: #866E54">2014-12-26 34:45:32</span><span
					style="float: left; margin-left: 10; color: #CACACA">操作员1</span><span style="float: right; color: #469E6F;">￥4565</span></li>
				<li><span style="float: left; margin-right: 10; color: #866E54">2014-12-26 34:45:32</span><span
					style="float: left; margin-left: 10; color: #CACACA">操作员1</span><span style="float: right; color: #469E6F;">￥4565</span></li>
				<li><span style="float: left; margin-right: 10; color: #866E54">2014-12-26 34:45:32</span><span
					style="float: left; margin-left: 10; color: #CACACA">操作员1</span><span style="float: right; color: #469E6F;">￥4565</span></li>
				<li><span style="float: left; margin-right: 10; color: #866E54">2014-12-26 34:45:32</span><span
					style="float: left; margin-left: 10; color: #CACACA">操作员1</span><span style="float: right; color: #469E6F;">￥4565</span></li>
				<li><span style="float: left; margin-right: 10; color: #866E54">2014-12-26 34:45:32</span><span
					style="float: left; margin-left: 10; color: #CACACA">操作员1</span><span style="float: right; color: #469E6F;">￥4565</span></li>
				<li><span style="float: left; margin-right: 10; color: #866E54">2014-12-26 34:45:32</span><span
					style="float: left; margin-left: 10; color: #CACACA">操作员1</span><span style="float: right; color: #469E6F;">￥4565</span></li>
				<li><span style="float: left; margin-right: 10; color: #866E54">2014-12-26 34:45:32</span><span
					style="float: left; margin-left: 10; color: #CACACA">操作员1</span><span style="float: right; color: #469E6F;">￥4565</span></li>
				<li><span style="float: left; margin-right: 10; color: #866E54">2014-12-26 34:45:32</span><span
					style="float: left; margin-left: 10; color: #CACACA">操作员1</span><span style="float: right; color: #469E6F;">￥4565</span></li>
				<li><span style="float: left; margin-right: 10; color: #866E54">2014-12-26 34:45:32</span><span
					style="float: left; margin-left: 10; color: #CACACA">操作员1</span><span style="float: right; color: #469E6F;">￥4565</span></li>
			</ul>

		</div>

		<div data-role="footer" data-position="fixed" style="text-align: center;">
			<a href="credits.html" data-ajax="false">首页</a>&nbsp;&nbsp;<a href="credits.html" data-ajax="false">上一页</a>&nbsp;&nbsp;3/10&nbsp;&nbsp;<a
				href="contact.html" data-ajax="false">下一页</a>&nbsp;&nbsp;<a href="credits.html" data-ajax="false">末页</a>
		</div>
	</div>

</body>
</html>


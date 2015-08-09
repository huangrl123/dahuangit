<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../common/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta http-equiv="content-language" content="zh-CN" />
<meta name="format-detection" content="telephone=no" />

<link rel="stylesheet" href="../plugin/jquerymobile/jquery.mobile-1.4.5.min.css" />
<script src="../plugin/jquery/jquery-2.1.3.min.js"></script>
<script src="../plugin/jquery/jquery-utils.js"></script>

<script src="../js/downloadApp.js"></script>

<link rel="stylesheet" href="../css/water.css" />

<style type="text/css">
.liStatus {
	line-height: 20px;
}

.liStatusSelect {
	line-height: 60px;
}

.liLable {
	float: left;
	margin-right: 10;
	color: #866E54;
}

.liLableSelect {
	float: left;
	color: #866E54;
	font-weight:bold;
	width: 65%;
}

.liStatusValue {
	float: right;
	color: #469E6F;
}

.liStatusValue-warning {
	float: right;
	color: red;
}
</style>
</head>
<body>

	<div data-role="page">
		<div data-role="header" data-position="fixed" data-tap-toggle="false" class="header-div">
			<div class="header-title1">
				<span class="header-title1-left"></span> 
				<span class="header-title1-center">下载app</span>
				<span class="header-title1-right"></span>
			</div>
		</div>

		<div data-role="content">
			<ul data-role="listview">
				<li class="liStatus"><span class="liLable"><a href="#"  onclick="downloadApp('${response.appFileUrl}')">${response.appName}</a></span></span></li>
			</ul>
		</div>
  </div>
</body>
</html>


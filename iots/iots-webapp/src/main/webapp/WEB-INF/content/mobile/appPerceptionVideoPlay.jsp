<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../common/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>参数日志列表界面</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta http-equiv="content-language" content="zh-CN" />
<link rel="stylesheet" href="../plugin/jquerymobile/jquery.mobile-1.4.5.min.css" />
<link rel="stylesheet" href="../plugin/huiyi8/css/history.css" />
<script src="../plugin/jquery/jquery-2.1.3.min.js"></script>
<script src="../plugin/jquery/jquery-utils.js"></script>
<script src="../plugin/jquerymobile/jquery.mobile-1.4.5.min.js"></script>
<script src="../plugin/huiyi8/js/main.js"></script>

<link rel="stylesheet" href="../css/water.css" />

</head>
<body>
	<div data-role="page">
		<div data-role="header" data-position="fixed" data-tap-toggle="false" class="header-div">
			<div class="header-title1">
				<span class="header-title1-left" onclick="gotoFunction('../perception/appGetPerceptionVedioList?perceptionAddr=${req.perceptionAddr }&perceptionName=${req.perceptionName}&userId=${req.userId}&perceptionId=${req.perceptionId}')"><img alt="" src="../image/home.png" height="25" width="25"></span> <span
					class="header-title1-center">[${req.perceptionName }]视频播放</span> <span class="header-title1-right"></span>
			</div>
		</div>

		<div data-role="content">
			<video src="${req.videoUrl }" width="100%" height="320px" controls preload></video>
		</div>
	</div>
</body>
</html>


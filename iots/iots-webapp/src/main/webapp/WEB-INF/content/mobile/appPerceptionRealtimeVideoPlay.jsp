<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../common/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta http-equiv="content-language" content="zh-CN" />
<link rel="stylesheet" href="../plugin/jquerymobile/jquery.mobile-1.4.5.min.css" />
<script src="../plugin/jquery/jquery-2.1.3.min.js"></script>
<script src="../plugin/jquery/jquery-utils.js"></script>
<script src="../plugin/jquerymobile/jquery.mobile-1.4.5.min.js"></script>

<link rel="stylesheet" href="../css/water.css" />

<script type="text/javascript" src="../plugin/jwplayer/jwplayer.js"></script>

<script src="../js/perception/appPerceptionRealtimeVideoPlay.js"></script>

</head>
<body>
	<div data-role="page">
		<div data-role="header" data-position="fixed" data-tap-toggle="false" class="header-div">
			<div class="header-title1">
				<span class="header-title1-left" onclick="gotoFunction('../appMgrPerceptionController/appPerceptionFunctionList?userId=${req.userId}&perceptionId=${req.perceptionId }')">
					<img alt="" src="../image/home.png" height="25" width="25">
				</span> 
				<span class="header-title1-center">实时视频播放</span> 
				<span class="header-title1-right"></span>
			</div>
		</div>

		<div data-role="content">
			<iframe src="${realTimePlayerUrl }" width="100%" height="100%" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes">
			</iframe>
		</div>
	</div>
</body>
</html>


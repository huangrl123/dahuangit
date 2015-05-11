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

<style type="text/css">
.onlineStatus {
	float: right;
	padding-right: 40px;
	color: green;
}

.offlineStatus {
	float: right;
	padding-right: 40px;
	color: red;
}
</style>
</head>
<body>
<div data-role="page">
	<div data-role="header" data-position="fixed" data-tap-toggle="false" class="header-div">
		<div class="header-title1">
			<span class="header-title1-left" onclick="gotoFunction('../appMgrPerceptionController/appPerceptionFunctionList?perceptionId=${req.perceptionId}')"><img alt="" src="../image/home.png" height="25" width="25"></span> 
			<span class="header-title1-center">${req.paramDesc }日志</span>
			<span class="header-title1-right"></span>
		</div>
	</div>
		
	<div data-role="content">
		<ul data-role="listview">
		    <c:choose>
			    <c:when test="${fn:length(queryResult.rows) > 0}">
				    <c:forEach var="log" items="${queryResult.rows }" varStatus="status">
						<li> 
							<span>${log.createDateTime }</span>
							<span class="onlineStatus">${log.perceptionParamValueDesc }</span>
						</li>
					</c:forEach>
			    </c:when>
			    <c:otherwise>
				    <li> 
						<span>(无数据)</span>
						<span class="onlineStatus"></span>
					</li>
			    </c:otherwise>
			</c:choose>
		</ul>
	</div>
</div>
</body>
</html>


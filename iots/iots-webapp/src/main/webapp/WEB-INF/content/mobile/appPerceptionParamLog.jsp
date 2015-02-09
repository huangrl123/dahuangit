<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../common/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>参数日志列表界面</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta http-equiv="content-language" content="zh-CN" />
<link rel="stylesheet" href="${ctx }/plugin/jquerymobile/jquery.mobile-1.4.5.min.css" />
<link rel="stylesheet" href="${ctx }/plugin/huiyi8/css/history.css" />
<script src="${ctx }/plugin/jquery/jquery-2.1.3.min.js"></script>
<script src="${ctx }/plugin/jquery/jquery-utils.js"></script>
<script src="${ctx }/plugin/jquerymobile/jquery.mobile-1.4.5.min.js"></script>
<script src="${ctx }/plugin/huiyi8/js/main.js"></script>
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
	<div data-role="header" data-position="fixed">
		<a href="${ctx }/spring/appMgrPerceptionController/appPerceptionFunctionList?perceptionId=${req.perceptionId}" data-icon="back" data-ajax="false">返回</a>
		<h1>${req.paramDesc }日志</h1>
	</div>
	
	<div data-role="content">
		<ul data-role="listview">
			<c:forEach var="log" items="${queryResult.results }" varStatus="status">
				<li> 
					<span>${log.createDateTime }</span>
					<span class="onlineStatus">${log.perceptionParamValueDesc }</span>
				</li>
			</c:forEach>
		</ul>
	</div>
</div>
</body>
</html>


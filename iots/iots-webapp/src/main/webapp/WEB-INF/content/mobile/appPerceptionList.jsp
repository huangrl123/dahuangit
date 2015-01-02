<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../common/base.jsp"%>
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
			<a href="${ctx }/spring/appMgrPerceptionController/appLogin" data-icon="minus" data-ajax="false">退出</a>
			<h1>感知端列表</h1>
		</div>

		<div data-role="content">

			<ul data-role="listview">
				<c:forEach var="perception" items="${perceptionList }" varStatus="status">
					<li><a href="${ctx }/spring/appMgrPerceptionController/appPerceptionFunctionList?perceptionId=${perception.perceptionId}" data-ajax="false">${perception.perceptionName }(${perception.perceptionAddr }) 
					        <c:choose>
								<c:when test="${perception.isOnline eq true}">
									<span class="onlineStatus">在线</span>
								</c:when>
								<c:otherwise>
									<span class="offlineStatus">离线</span>
								</c:otherwise>
							</c:choose>
					</a></li>

				</c:forEach>
			</ul>

		</div>
	</div>

</body>
</html>


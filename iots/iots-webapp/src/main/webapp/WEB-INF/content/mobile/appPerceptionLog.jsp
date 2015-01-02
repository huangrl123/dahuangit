<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../common/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>端告警信息</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta http-equiv="content-language" content="zh-CN" />
<link rel="stylesheet" href="${ctx }/plugin/jquerymobile/jquery.mobile-1.4.5.min.css" />
<script src="${ctx }/plugin/jquery/jquery-2.1.3.min.js"></script>
<script src="${ctx }/plugin/jquerymobile/jquery.mobile-1.4.5.min.js"></script>

<style type="text/css">
.ListItem-left {
	float: left;
	margin-right: 2;
	color: #866E54;
}

.ListItem-center {
	float: left;
	margin-left: 5;
	color: #CACACA;
}

.ListItem-right {
	float: right;
	color: #469E6F;
}
</style>
</head>
<body>

	<div data-role="page">

		<div data-role="header" data-position="fixed">
			<a href="${ctx }/spring/appMgrPerceptionController/appPerceptionFunctionList?perceptionId=${perceptionId}" data-icon="back" data-ajax="false">返回</a>
			<h1>端告警信息</h1>
			<a href="${ctx }/spring/appMgrPerceptionController/appPerceptionLog?perceptionId=${perceptionId}&reqPage=${curPage}" data-icon="refresh" data-ajax="false">刷新</a>
		</div>

		<div data-role="content">

			<ul data-role="listview">
				<c:forEach var="log" items="${PerceptionRuntimeLogList }" varStatus="status">
					<li>
						<span class="ListItem-left"><fmt:formatDate value="${log.createDateTime }" pattern="yyyy-MM-dd hh:ss:mm" /></span>
						<span class="ListItem-center">${log.perceptionParamName }</span>
						<span class="ListItem-right">${log.perceptionParamValueDesc }</span>
					</li>
				</c:forEach>
			</ul>

		</div>

		<div data-role="footer" data-position="fixed" style="text-align: center;">
		    <c:choose>
		    	<c:when test="${curPage eq 1}">
				    <input type="button" value="首页" disabled="true"/>
				    <input type="button" value="上一页" disabled="true"/>
		    	</c:when>
		    	<c:otherwise>
			    	<a href="${ctx }/spring/appMgrPerceptionController/appPerceptionLog?perceptionId=${perceptionId}&reqPage=1" data-ajax="false">首页</a>
				    <a href="${ctx }/spring/appMgrPerceptionController/appPerceptionLog?perceptionId=${perceptionId}&reqPage=${curPage - 1}" data-ajax="false">上一页</a>
		    	</c:otherwise>
		    </c:choose>
			${curPage }/${totalPage }
			<c:choose>
		    	<c:when test="${curPage eq totalPage}">
			        <input type="button" value="下一页" disabled="true"/>
				    <input type="button" value="末页" disabled="true"/>
		    	</c:when>
		    	<c:otherwise>
					<a href="${ctx }/spring/appMgrPerceptionController/appPerceptionLog?perceptionId=${perceptionId}&reqPage=${curPage + 1}" data-ajax="false">下一页</a>
					<a href="${ctx }/spring/appMgrPerceptionController/appPerceptionLog?perceptionId=${perceptionId}&reqPage=${totalPage}" data-ajax="false">末页</a>
		    	</c:otherwise>
		    </c:choose>
		</div>
	</div>

</body>
</html>


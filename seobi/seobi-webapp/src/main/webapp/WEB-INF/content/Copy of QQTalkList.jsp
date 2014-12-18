<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/include/common_inc.jsp"%>
<%@taglib prefix="page" uri="http://www.dahuangit.com/taglib"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>QQ说说列表</title>
<link type="text/css" rel="stylesheet" href="${ctx}/css/base.css">
</head>
<body>
	<div style="border: 1px solid #999;">
		<div class="listHead">说说列表</div>
		<c:forEach var="response" items="${QQTalkMsgPageQueryResult.results}" varStatus="status">
			<div>
				<a href="${ctx}/spring/qQTalkMsgQueryController/getRelatedSearchKeysByQQTalkMsgId?qQTalkMsgId=${response.tmId }">
					<c:out value="${response.talkContent }" />
				</a>
			</div>
			<div style="text-align: right">
				<c:choose>
					<c:when
						test="${!empty response.originalityPercent && response.originalityPercent > 0 && response.originalityPercent < 1}">
						原创度:<fmt:formatNumber value="${response.originalityPercent}" type="number" pattern="0.00%" />
					</c:when>
				</c:choose>
			</div>
			<div style="text-align: right">
				<c:choose>
					<c:when test="${!empty response.fromDevice}">
						<c:out value="${response.fromDevice }" />/<fmt:formatDate value="${response.publishTime }"
							pattern="yyyy-MM-dd hh:ss:mm" />
					</c:when>
					<c:otherwise>
						<fmt:formatDate value="${response.publishTime }" pattern="yyyy-MM-dd hh:ss:mm" />
					</c:otherwise>
				</c:choose>
			</div>

			<c:if test="${!status.last}">
				<hr style="border: 1px dashed #000; height: 1px">
			</c:if>
		</c:forEach>
	</div>

	<page:outpage pageSize="${ page.pageSize}" totalPage="${page.totalPage }"
		baseUrl="${ctx}/spring/qQTalkMsgQueryController/findQQTalkMsgByPage" curPage="${page.curPage }" />
</body>
</html>
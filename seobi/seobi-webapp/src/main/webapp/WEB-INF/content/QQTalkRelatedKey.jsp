<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/common_inc.jsp"%>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

<meta name="viewport" content="width=device-width"/>
<meta name="viewport" content="width=device-width; initial-scale=1.0; minimum-scale=1.0; maximum-scale=2.0" />
<meta name="viewport" content="width=320"/>

<title>QQ说说关键字</title>
<link type="text/css" rel="stylesheet" href="${ctx}/css/base.css">
</head>
<body>
	<div>
		<c:out value="${response.talkContent }" />
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

	<c:if test="${!empty  relatedSearchKeyResponseList && fn:length(relatedSearchKeyResponseList) > 0}">
        本条说说相关搜索关键词:
		<c:forEach var="key" items="${relatedSearchKeyResponseList }" varStatus="status">
			<div>
				<a href="http://www.baidu.com/s?wd=${key.relatedSearchKey}&rn=20&lm=0" target="_blank"> <c:out
						value="${key.relatedSearchKey }" />
				</a>
			</div>
		</c:forEach>
	</c:if>
</body>
</html>
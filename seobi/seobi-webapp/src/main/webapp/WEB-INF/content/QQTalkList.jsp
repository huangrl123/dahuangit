<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/include/common_inc.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

<meta name="viewport" content="width=device-width"/>
<meta name="viewport" content="width=device-width; initial-scale=1.0; minimum-scale=1.0; maximum-scale=2.0" />
<meta name="viewport" content="width=320"/>

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

	<table align="center">
		<tr>
			<td width="100%" colspan="9" align="right">本页${page.curPageCount}条记录/共${page.totalCount }条记录 <c:choose>
					<c:when test="${page.curPage-1 gt 0}">
						<a href='${ctx}/spring/qQTalkMsgQueryController/findQQTalkMsgByPage?page.curPage=1'>首页|</a>
						<a href='${ctx}/spring/qQTalkMsgQueryController/findQQTalkMsgByPage?page.curPage=${page.curPage-1}'>上一页|</a>
					</c:when>
					<c:otherwise>
					         首页|
						上一页|
					</c:otherwise>
				</c:choose> <c:choose>
					<c:when test="${page.curPage lt page.totalPage}">
						<a href='${ctx}/spring/qQTalkMsgQueryController/findQQTalkMsgByPage?page.curPage=${page.curPage+1}'>下一页|</a>
						<a href='${ctx}/spring/qQTalkMsgQueryController/findQQTalkMsgByPage?page.curPage=${page.totalPage}'>末页</a>
					</c:when>
					<c:otherwise>
						下一页|
						末页
					</c:otherwise>
				</c:choose> 第${page.curPage}页/共${page.totalPage}页
			</td>
		</tr>
	</table>

</body>
</html>
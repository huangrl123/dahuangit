<%@page contentType="text/html;charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="path" value="" />
<c:if test="${pageContext.request.contextPath!='/'}">
	<c:set var="path" value="${pageContext.request.contextPath}" />
</c:if>
<c:set var="base"
	value="${pageContext.request.scheme}://${pageContext.request.serverName }:${pageContext.request.serverPort}${path}" />
<c:set var="theme" value="${sessionScope.themes}" />
<c:set var="baseHeight" value="74" />
<script>
	var baseHeight = "${baseHeight}";
</script>

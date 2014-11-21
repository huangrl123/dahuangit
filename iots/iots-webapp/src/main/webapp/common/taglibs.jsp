<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script>
function ctx() {
	return "${ctx}";
}

function img(name) {
	return "${ctx}/pic/" + name;
}
</script>
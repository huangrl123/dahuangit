<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>403 - 超过用户并发登陆次数</title>
</head>

<body>
<div>
	<div><h1>您的账号超过所允许并发登录次数3次，您被迫下线。</h1></div>
	<div><a href="<c:url value="/"/>">返回首页</a> <a href="<c:url value="/j_spring_security_logout"/>">退出登录</a></div>
</div>
<script type="text/javascript">
    alert('-----------');
</script>
</body>
</html>
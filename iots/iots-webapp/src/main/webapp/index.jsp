<%
	String agent = request.getHeader("user-agent");

	//跳转到手机版本
	if (null != agent && agent.contains("Mobile")) {
		response.sendRedirect(request.getContextPath() + "/spring/appMgrPerceptionController/appLogin");
	}

	//调转到普通pc浏览器版本
	else {
		response.sendRedirect(request.getContextPath() + "/spring/perception/perceptionList");
	}
%>
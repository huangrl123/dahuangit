<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="common/base.jsp"%>
<%
	String agent = request.getHeader("user-agent");

	//跳转到手机版本
	if (null != agent && agent.contains("Mobile")) {
		response.sendRedirect(request.getContextPath() + "/appUpgrade/toDownloadAppPage");
	}

	//调转到普通pc浏览器版本
	else {
		response.sendRedirect(request.getContextPath() + "/appUpgrade/toDownloadAppPage");
	}
%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglibs.jsp"%>

<%@page import="java.text.DateFormat"%><html>
<%@page import="java.util.Locale" %>

<link rel="stylesheet" type="text/css" href="${ctx}/css/style.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/index.css" />
<script type="text/javascript" src="${ctx}/js/top.js"></script>


<div id="header"></div>
<div id="menu">
	<div class="left">[欢迎您，${currentUser.dept.deptName} ${currentUser.name}] <%=DateFormat.getDateInstance(DateFormat.FULL, Locale.CHINA).format(new java.util.Date())%></div>
	<div class="right"><div id="logoutBtn"></div></div>
</div>
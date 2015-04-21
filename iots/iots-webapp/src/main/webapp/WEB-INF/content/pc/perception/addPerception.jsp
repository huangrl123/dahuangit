<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../../../common/base.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<link href="../css/css_style.css" rel="stylesheet" type="text/css" />
<link href="../css/table_style.css" rel="stylesheet" type="text/css" />

<script src="../plugin/jquery/jquery-1.11.2.min.js"></script>

<script src="../js/perception/addPerception.js"></script>
</head>

<body>
	<form id="addPerceptionForm">
		<div style="border: 1px solid #99BBE8; margin: 5px;">
			<div style="background-color: #DFE8F6; border-bottom: 1px #99BBE8 solid; height: 22px; line-height: 22px; padding-left: 5px; font-weight: bolder; font-size: 10pt;">设备基本信息</div>
			<div>
				<span style="padding: 0px;height: 33px; font-size: 10pt;margin:0px;">设备类型</span> <span style="width: 50%;height: 33px;"> <select id="perceptionType"
					style="font-size: 10pt;">
						<c:forEach items="${perceptionTypeList }" var="perceptionType">
							<option value="${perceptionType.perceptionTypeId }">${perceptionType.perceptionTypeName }</option>
						</c:forEach>
				</select>
				</span>
			</div>
		</div>
	</form>
</body>
</html>

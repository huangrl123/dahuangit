<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../../../common/base.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../css/css_style.css" rel="stylesheet" type="text/css" />
<link href="../css/table_style.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" type="text/css" href="../plugin/jquery-easyui/themes/default/easyui.css">
<script src="../plugin/jquery/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="../plugin/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../plugin/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript" src="../js/perception/queryPerception.js"></script>
</head>

<body style="background-color: #FFFFFF; padding: 5px;">
	<form id="queryForm">
		<table width='100%' border="0" cellspacing="0" cellpadding="0" class="inquiry" style="margin: 0px 0px 5px 0px;">
			<tr>
				<td align="left" style="padding: 0px 0px 2px 0px;"><a>&nbsp;<span class="blue">设备地址：</span></a> <label> <input id="perceptionAddr" type="text" class="text_box" size="33" />
				</label> <a class="blue"><span class="blue">&nbsp;&nbsp;&nbsp;&nbsp; 设备类型：</span></a> <select id="perceptionType" style="margin-top: 1px;">
						<option value="">全部</option>
						<c:forEach items="${perceptionTypeList }" var="perceptionType">
							<option value="${perceptionType.perceptionTypeId }">${perceptionType.perceptionTypeName }</option>
						</c:forEach>
				</select> <a class="blue"><span class="blue">&nbsp;&nbsp;&nbsp;&nbsp; 在线状态：</span></a> <select id="onlineStatus" style="margin-top: 1px;">
						<option value="">全部</option>
						<option value="0">离线</option>
						<option value="1">在线</option>
				</select></td>
				<td width=130 align="right" style="padding-right: 5px;"><label> <input id="queryBtn" type="button" class="inp_L1" onmouseover="this.className='inp_L2'" onmouseout="this.className='inp_L1'" value="查 询" /> <input
						type="reset" class="inp_L1" onclick="" onmouseover="this.className='inp_L2'" onmouseout="this.className='inp_L1'" value="重 置" /> &nbsp; </td>
			</tr>
		</table>
	</form>

	<div id="perception-datagrid" style="width: 100%"></div>
	<div id="perception-pagination" style="border: 1px solid #95B8E7; margin-top: 5px;"></div>
</body>
</html>

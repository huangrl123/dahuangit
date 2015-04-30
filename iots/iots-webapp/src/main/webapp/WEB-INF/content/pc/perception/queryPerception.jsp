<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../../../common/base.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../css/css_style.css" rel="stylesheet" type="text/css" />
<link href="../css/table_style.css" rel="stylesheet" type="text/css" />

<script src="../plugin/jquery/jquery-1.11.2.min.js"></script>

<link rel="stylesheet" type="text/css" href="../plugin/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../plugin/jquery-easyui/themes/icon.css">
<script type="text/javascript" src="../plugin/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../plugin/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../plugin/jquery-easyui/jquery.easyui-util.js"></script>

<script type="text/javascript" src="../plugin/json2.js"></script>

<script type="text/javascript" src="../js/perception/queryPerception.js"></script>
<script type="text/javascript" src="../js/perception/savePerception.js"></script>
</head>

<body style="background-color: #FFFFFF; padding: 5px;">
	<form id="queryForm">
		<table width='100%' border="0" cellspacing="0" cellpadding="0" class="inquiry" style="margin: 0px 0px 5px 0px;">
			<tr>
				<td align="left" style="padding: 0px 0px 2px 0px;"><a>&nbsp;<span class="blue">设备地址：</span></a> <label> <input id="queryPerceptionAddr" type="text" class="text_box" size="33" />
				</label> <a class="blue"><span class="blue">&nbsp;&nbsp;&nbsp;&nbsp; 设备类型：</span></a> <select id="queryPerceptionType" style="margin-top: 1px;">
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
	<div style="display: none;">
		<div id="savePerceptionWin">
			<div style="padding: 5px 5px 5px 5px">
				<form id="savePerceptionForm" method="post">
					<input type="hidden" id="perceptionId" name="perceptionId">
					<table>
						<tr>
							<td>设备类型:</td>
							<td><select id="perceptionTypeId" name="perceptionTypeId" style="height: 23px; width: 150px; margin-left: 2px;">
									<c:forEach items="${perceptionTypeList }" var="perceptionType">
										<option value="${perceptionType.perceptionTypeId }">${perceptionType.perceptionTypeName }</option>
									</c:forEach>
							</select></td>
						</tr>
						<tr>
							<td>设备地址:</td>
							<td><input type="text" id="perceptionAddr" name="perceptionAddr"></input></td>
						</tr>
						<tr>
							<td>设备名称:</td>
							<td><input type="text" id="perceptionName" name="perceptionName"></input></td>
						</tr>
					</table>

					<div style="text-align: center; padding-top: 25px;">
						<a id="savePerceptionBtn" href="#" class="easyui-linkbutton" style="margin-right: 5px; width: 90px;">确定</a> <a id="savePerceptionCloseBtn" href="#" class="easyui-linkbutton" style="margin-left: 5px; width: 90px;" onclick="">关闭</a>
					</div>
				</form>
			</div>
		</div>

		<div id="relateUserWin">
			<div style="width: 365px; height: 280px; margin: 5px 5px 0px 5px;">
				<div style="float: left;">
					<select multiple="multiple" id="select1" style="width: 150px; height: 280px;">
					</select>
				</div>
				<div style="height: 240px; float: left; margin: 0px 5px 0px 5px;">
					<div style="padding-top: 5px;">
						<input id="add" type="button" style="width: 50px; height: 30px; cursor: pointer;" value="&gt" />
					</div>
					<div style="padding-top: 5px;">
						<input id="add_all" type="button" style="width: 50px; height: 30px; cursor: pointer;" value="&gt&gt" />
					</div>
					<div style="padding-top: 5px;">
						<input id="remove" type="button" style="width: 50px; height: 30px; cursor: pointer;" value="&lt" />
					</div>
					<div style="padding-top: 5px;">
						<input id="remove_all" type="button" style="width: 50px; height: 30px; cursor: pointer;" value="&lt&lt" />
					</div>
				</div>
				<div style="height: 240px; float: left;">
					<select multiple="multiple" id="select2" style="width: 150px; height: 280px;">
					</select>
				</div>
			</div>
			<div style="text-align: center; width: 365px; margin: 25px 5px 0px 5px;">
				<a id="relateUserWinBtn" href="#" class="easyui-linkbutton" style="width: 90px;" onclick="">确定</a> <a id="relateUserWinCloseBtn" href="#" class="easyui-linkbutton" style="width: 90px;" onclick="">关闭</a>
			</div>
		</div>
</body>
</html>

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
</head>

<body style="background-color: #FFFFFF; padding: 5px;">
	<table width='100%' border="0" cellspacing="0" cellpadding="0" class="inquiry" style="margin: 0px 0px 5px 0px;">
		<tr>
			<td align="left" style="padding: 0px 0px 2px 0px;"><a>&nbsp;<span class="blue">设备地址：</span></a> <label> <input name="textfield" type="text" class="text_box" size="33" />
			</label> <a class="blue"><span class="blue">&nbsp;&nbsp;&nbsp;&nbsp; 设备类型：</span></a> <select name="menu1" style="margin-top: 1px;">
					<option value="">全部</option>
					<c:forEach items="${perceptionTypeList }" var="perceptionType">
						<option value="${perceptionType.perceptionTypeId }">${perceptionType.perceptionTypeName }</option>
					</c:forEach>
			</select></td>
			<td width=130 align="right" style="padding-right: 5px;"><label> <input name="submit" type="submit" class="inp_L1" onclick="alert('对不起，这是演示页面，查询动作取消。')" onmouseover="this.className='inp_L2'"
					onmouseout="this.className='inp_L1'" value="查 询" /> <input name="submit2" type="submit" class="inp_L1" onclick="alert('对不起，这是演示页面，重置动作取消。')" onmouseover="this.className='inp_L2'" onmouseout="this.className='inp_L1'" value="重 置" />
					&nbsp; </td>
		</tr>
	</table>

	<div id="perception-datagrid"></div>
	<div id="perception-pagination" style="border: 1px solid #95B8E7; margin-top: 5px;"></div>

	<script type="text/javascript">
		$(function() {
			var grid = $('#perception-datagrid').datagrid({
				rownumbers : true,
				singleSelect : true,
				fitColumns : true,
				columns : [ [ {
					field : 'perceptionAddr',
					title : '设备地址',
					width : 80
				}, {
					field : 'perceptionName',
					title : '设备名称',
					width : 100
				}, {
					field : 'perceptionTypeName',
					title : '设备类型',
					width : 80
				}, {
					field : 'status',
					title : '在线状态',
					width : 80
				} ] ]
			});

			var pg = $('#perception-pagination').pagination({
				pageSize : 10,
				total : 0,
				pageList : [ 10, 30, 50 ],
				beforePageText : '第',
				afterPageText : '页    共 {pages} 页',
				displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
				onSelectPage : function(pageNumber, pageSize) {
					grid.datagrid({
						url : '../perception/findPerceptionByPage',
						method : 'post',
						queryParams : {
							start : pageNumber,
							limit : pageSize
						},
						onLoadSuccess : function(data) {
							pg.pagination({
								total : data.total
							});
						}
					});
				}
			});

			$(window).resize();
		});
	</script>
</body>
</html>

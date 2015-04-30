<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../../../common/base.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="../css/css_style.css" rel="stylesheet" type="text/css" />
<link href="../css/table_style.css" rel="stylesheet" type="text/css" />

<script src="../plugin/jquery/jquery-1.11.2.min.js"></script>

<link rel="stylesheet" type="text/css" href="../plugin/jquery-easyui/themes/default/easyui.css">
<script type="text/javascript" src="../plugin/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../plugin/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../plugin/jquery-easyui/jquery.easyui-util.js"></script>

</head>

<body style="background-color: #FFFFFF;">
	<table width="100%" height="100%" cellspacing="5">
		<tr>
			<td height="235" valign="top">
				<form id="updatePasswordForm">
					<table width="100%" border="0" cellspacing="0" cellpadding="0" style="border: 1px #99BBE8 solid;">
						<tr>
							<td height="22" style="border-bottom: 1px #99BBE8 solid; background-color: #DFE8F6; padding: 3px 0px 3px 10px; font-size: 12px;">app下载列表</td>
						</tr>

						<tr>
							<td height="22" style="background-color: #f4f4f4; padding: 3px 0px 3px 15px; font-weight: bolder; font-size: 12px;">app信息</td>
						</tr>

						<tr>
							<td height="50" style="border-bottom: 1px #99BBE8 solid; padding-left: 15px;"><span style="float: left;">安卓版本app</span><span style="float: left;">&nbsp;&nbsp;&nbsp;<a href="../apk/${apkName}"
									style="color: blue; text-decoration: underline;">${apkName} </a></span>&nbsp;&nbsp;&nbsp;</td>
						</tr>
						<tr>
							<td height="50" style="padding-left: 15px;">苹果版本app<img alt="" src="../image/erweima.png"></td>
						</tr>

					</table>
				</form>
			</td>
		</tr>
	</table>
</body>
</html>

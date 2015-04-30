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

<script type="text/javascript" src="../js/user/updatePassword.js"></script>

</head>

<body style="background-color: #FFFFFF;">
	<table width="100%" height="100%" cellspacing="5">
		<tr>
			<td height="235" valign="top">
				<form id="updatePasswordForm">
					<input type="hidden" id="oldUserPassword" value="${user.password}"> <input type="hidden" id="userId" value="${user.userId} ">
					<table width="100%" border="0" cellspacing="0" cellpadding="0" style="border: 1px #99BBE8 solid;">
						<tr>
							<td height="22" style="border-bottom: 1px #99BBE8 solid; background-color: #DFE8F6; padding: 3px 0px 3px 10px; font-size: 12px;">当前用户(${user.userName})密码修改</td>
						</tr>

						<tr>
							<td height="22" style="background-color: #f4f4f4; padding: 3px 0px 3px 15px; font-weight: bolder; font-size: 12px;">密码信息</td>
						</tr>

						<tr>
							<td align="left" valign="middle" style="padding: 5px 5px 0px 5px;">
								<table width="98%" border="0" bgcolor=#ffffff>
									<tbody cellspacing="0">
										<tr bgcolor=#ffffff>
											<td class="color0366CB" width=100 align="left" height="25"><a style="color: blue">旧密码：</a></td>
											<td align="left" width=200><input id="oldPassword" type="password" style="width: 300px;"></td>
											<td width=200></td>
											<td width=200></td>
										</tr>
										<tr bgcolor=#ffffff>
											<td class="color0366CB" width=100 align="left" height="25"><a style="color: blue">新密码：</a></td>
											<td align="left" width=200><input id="newPassword" type="password" style="width: 300px;"></td>
											<td width=200></td>
											<td width=200></td>
										</tr>
										<tr bgcolor=#ffffff>
											<td class="color0366CB" width=100 align="left" height="25"><a style="color: blue">新密码确认：</a></td>
											<td align="left" width=200><input type="password" id="newConfirmPassword" style="width: 300px;"></td>
											<td width=200></td>
											<td width=200></td>
										</tr>
									</tbody>
								</TABLE>
							</td>
						</tr>

						<tr>
							<td>
								<div style="text-align: center; padding-top: 25px; padding-bottom: 5px;">
									<a id="updatePasswordBtn" href="#" class="easyui-linkbutton" style="margin-right: 5px; width: 90px;">确定</a> <a id="updatePasswordResetBtn" href="#" class="easyui-linkbutton" style="margin-left: 5px; width: 90px;" onclick="">重置</a>
								</div>
							</td>
						</tr>
					</table>
				</form>
			</td>
		</tr>
	</table>
</body>
</html>

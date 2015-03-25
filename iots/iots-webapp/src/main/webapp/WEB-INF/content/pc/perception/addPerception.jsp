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

<body style="background-color: #FFFFFF;" onLoad="setup()">
	<form id="addPerceptionForm">
		<table width="100%" height="100%" cellspacing="5">
			<tr>
				<td height="235" valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="0" style="border: 1px #99BBE8 solid;">
						<tr>
							<td height="22" style="border-bottom: 1px #99BBE8 solid; background-color: #DFE8F6; padding: 3px 0px 3px 10px;">设备添加</td>
						</tr>
						<tr>
							<td align="center" valign="middle" style="padding: 5px 5px 0px 5px;">
								<fieldset style="color: #B7B7B7; border-style: groove;">
									<legend style="font-size: 10pt;"> 设备信息 </legend>

									<table width="98%" border="0" cellpadding="4" cellspacing="1" bgcolor=#ffffff>
										<tr id='workNameTR' style="display: none">
											<td>&nbsp;</td>
										</tr>

										<tbody width="100%" cellspacing="0" id="no2" name="no2">
											<tr bgcolor=#ffffff>
												<td class="color0366CB" width=100 align="right" height="23">设备类型：</td>
												<td height="23" align="left" colspan=5><select id="perceptionType" style="width: 50%">
														<c:forEach items="${perceptionTypeList }" var="perceptionType">
															<option value="${perceptionType.perceptionTypeId }">${perceptionType.perceptionTypeName }</option>
														</c:forEach>
												</select></td>
											</tr>
											<tr bgcolor=#ffffff>
												<td class="color0366CB" width=100 height="23" align="right">设备地址：</td>
												<td height="23" align="left" colspan=5><input id="perceptionAddr" style="width: 50%"></td>
											</tr>
											<tr bgcolor=#ffffff>
												<td class="color0366CB" width=100 height="23" align="right">设备名称：</td>
												<td height="23" align="left" colspan=5><input id="perceptionName" style="width: 50%"></td>
											</tr>
										</tbody>

									</table>

								</fieldset>
							</td>
						</tr>
						<tr>
							<td height="23" id="no6" name="no6" style="text-align: center; padding: 5px 0px 5px 0px;"><input id="addPerceptionBtn" type="button" class="inp_L1" value="提交" onmouseover="this.className='inp_L2'"
								onmouseout="this.className='inp_L1'" />&nbsp; <input id="submitCertButton" type="reset" class="inp_L1" value="重置" onmouseover="this.className='inp_L2'" onmouseout="this.className='inp_L1'" />&nbsp;
						</tr>
					</table>

				</td>
			</tr>
		</table>
	</form>
</body>
</html>

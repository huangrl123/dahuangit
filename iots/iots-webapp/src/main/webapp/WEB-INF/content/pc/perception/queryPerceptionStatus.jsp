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
<script type="text/javascript" src="../plugin/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../plugin/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript" src="../js/perception/queryPerceptionStatus.js"></script>
</head>


<body style="background-color: #FFFFFF;">
	<input id="perceptionId" type="hidden" value="${perceptionOpResponse.perceptionId }">
	<input id="perceptionTypeId" type="hidden" value="${perceptionOpResponse.perceptionTypeId }">
	<form id="frm" name="frm">
		<table width="100%" height="100%" cellspacing="5">
			<tr>
				<td height="235" valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="0" style="border: 1px #99BBE8 solid;">
						<tr>
							<td height="22" style="border-bottom: 1px #99BBE8 solid; background-color: #DFE8F6; padding: 3px 0px 3px 10px;">设备状态</td>
						</tr>

						<tr>
							<td align="center" valign="middle" style="padding: 5px 5px 0px 5px;">
								<fieldset style="color: #B7B7B7; border-style: groove;">
									<legend style="font-size: 10pt;" id='legendUser'> 基本信息 </legend>
									<table width="98%" border="0" bgcolor=#ffffff>
										<tbody cellspacing="0">
											<tr bgcolor=#ffffff>
												<td class="color0366CB" width=100 align="right" height="23">设备地址：</td>
												<td align="left" width=200>${ perceptionOpResponse.perceptionAddr}</td>
												<td class="color0366CB" width=100 height="23" align="right">设备名称：</td>
												<td align="left">${perceptionOpResponse.perceptionName }</td>
											</tr>
										</tbody>
										<tbody cellspacing="0">
											<tr bgcolor=#ffffff>
												<td class="color0366CB" width=100 align="right" height="23">设备类型：</td>
												<td align="left" width=200>${perceptionOpResponse.perceptionTypeName }</td>
												<td class="color0366CB" width=100 height="23" align="right">在线状态：</td>
												<td id="onlineStatus" align="left">${perceptionOpResponse.onlineStatusDesc }</td>
											</tr>
										</tbody>
										<tbody cellspacing="0">
											<tr bgcolor=#ffffff>
												<td class="color0366CB" width=100 align="right" height="23">最后通信时间：</td>
												<td id="lastCommTime" align="left" width=200>${perceptionOpResponse.lastCommTime }</td>
												<td class="color0366CB" width=100 height="23" align="right"></td>
												<td align="left"></td>
											</tr>
										</tbody>

									</TABLE>
								</fieldset>
							</td>
						</tr>
						<tr>
							<td align="center" valign="middle" style="padding: 5px 5px 0px 5px;">
								<fieldset style="color: #B7B7B7; border-style: groove;">
									<legend style="font-size: 10pt;" id='legendUser'> 告警信息 </legend>
									<table width="98%" border="0" bgcolor=#ffffff>
										<c:forEach items="${perceptionOpResponse.warningParamInfos }" var="warningParamInfo" varStatus="varStatus">
											<c:choose>
												<c:when test="${varStatus.index%2 == 0}">
													<tbody cellspacing="0">
														<tr bgcolor=#ffffff>
															<td class="color0366CB" width=100 align="right" height="23">${warningParamInfo.paramDesc }：</td>
															<td id="${warningParamInfo.paramId }" align="left" width=200>${warningParamInfo.paramValueDesc }</td>
												</c:when>
												<c:otherwise>
													<td class="color0366CB" width=100 height="23" align="right">${warningParamInfo.paramDesc }：</td>
													<td id="${warningParamInfo.paramId }" align="left">${warningParamInfo.paramValueDesc }</td>
													</tr>
													</tbody>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</TABLE>
								</fieldset>
							</td>
						</tr>

						<tr>
							<td align="center" valign="middle" style="padding: 5px 5px 0px 5px;">
								<fieldset style="color: #B7B7B7; border-style: groove;">
									<legend style="font-size: 10pt;"> 操控信息 </legend>
									<table width="98%" border="0" bgcolor=#ffffff>
										<c:forEach items="${perceptionOpResponse.ctrlParamInfos }" var="ctrlParamInfo" varStatus="varStatus">
											<c:choose>
												<c:when test="${varStatus.index%2 == 0}">
													<tbody cellspacing="0" id="no7" name="no7">
														<tr bgcolor=#ffffff>
															<td class="color0366CB" width=100 align="right" height="23">${ctrlParamInfo.paramDesc }：</td>
															<td align="left" width=200><select id="${ctrlParamInfo.paramId }" onchange="remoteCtrl($(this))">
																	<c:forEach var="item" items="${ctrlParamInfo.comboboxData.root }">
																		<c:choose>
																			<c:when test="${ctrlParamInfo.paramValue eq item.value }">
																				<option value="${item.value }" selected="true">${item.text }</option>
																			</c:when>
																			<c:otherwise>
																				<option value="${item.value }">${item.text }</option>
																			</c:otherwise>
																		</c:choose>
																	</c:forEach>
															</select></td>
												</c:when>
												<c:otherwise>
													<td class="color0366CB" width=100 height="23" align="right">${ctrlParamInfo.paramDesc }：</td>
													<td align="left"><select id="${ctrlParamInfo.paramId }" onchange="remoteCtrl($(this))">
															<c:forEach var="item" items="${ctrlParamInfo.comboboxData.root }">
																<c:choose>
																	<c:when test="${ctrlParamInfo.paramValue eq item.value }">
																		<option value="${item.value }" selected="true">${item.text }</option>
																	</c:when>
																	<c:otherwise>
																		<option value="${item.value }">${item.text }</option>
																	</c:otherwise>
																</c:choose>
															</c:forEach>
													</select></td>
													</tr>
													</tbody>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</TABLE>
								</fieldset>
							</td>
						</tr>

						<tr>
							<td height="23" id="no6" name="no6" style="text-align: center; padding: 5px 0px 5px 0px;"><input id="backBtn" type="button" class="inp_L1" value="返回" onmouseover="this.className='inp_L2'" onmouseout="this.className='inp_L1'" />&nbsp;
							
						</tr>
					</table>

				</td>
			</tr>
		</table>

	</form>
</body>
</html>

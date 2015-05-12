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
<script type="text/javascript" src="../plugin/jquery-easyui/jquery.easyui-util.js"></script>

<script type="text/javascript" src="../js/perception/videoList.js"></script>
<script type="text/javascript" src="../js/perception/realTimeVideo.js"></script>

<script type="text/javascript" src="../js/perception/queryPerceptionStatus.js"></script>
</head>


<body style="background-color: #FFFFFF;">
	<input id="perceptionId" type="hidden" value="${perceptionOpResponse.perceptionId }">
	<input id="perceptionTypeId" type="hidden" value="${perceptionOpResponse.perceptionTypeId }">
	<input id="perceptionAddr" type="hidden" value="${perceptionOpResponse.perceptionAddr }">
	<input id="realTimePlayerUrl" type="hidden" value="${realTimePlayerUrl }">
	<input id="rtmpBaseUrl" type="hidden" value="${rtmpBaseUrl }">
	<form id="frm" name="frm">
		<table width="100%" height="100%" cellspacing="5">
			<tr>
				<td height="235" valign="top">
					<table width="100%" border="0" cellspacing="0" cellpadding="0" style="border: 1px #99BBE8 solid;">
						<tr>
							<td height="22" style="border-bottom: 1px #99BBE8 solid; background-color: #DFE8F6; padding: 3px 0px 3px 10px; font-size: 12px;">设备状态</td>
						</tr>

						<tr>
							<td height="22" style="background-color: #f4f4f4; padding: 3px 0px 3px 15px; font-weight: bolder; font-size: 12px;">设备状态</td>
						</tr>

						<tr>
							<td align="center" valign="middle" style="padding: 5px 5px 0px 5px;">
								<table width="98%" border="0" bgcolor=#ffffff>
									<tbody cellspacing="0">
										<tr bgcolor=#ffffff>
											<td class="color0366CB" width=200 align="right" height="23"><a style="color: blue">设备地址：</a></td>
											<td align="left" width=200>${ perceptionOpResponse.perceptionAddr}</td>
											<td class="color0366CB" width=200 height="23" align="right"><a style="color: blue">设备名称：</a></td>
											<td align="left">${perceptionOpResponse.perceptionName }</td>
										</tr>
									</tbody>
									<tbody cellspacing="0">
										<tr bgcolor=#ffffff>
											<td class="color0366CB" width=200 align="right" height="23"><a style="color: blue">设备类型：</a></td>
											<td align="left" width=200>${perceptionOpResponse.perceptionTypeName }</td>
											<td class="color0366CB" width=200 height="23" align="right"><a style="color: blue">在线状态：</a></td>
											<td id="onlineStatus" align="left">${perceptionOpResponse.onlineStatusDesc }</td>
										</tr>
									</tbody>
									<tbody cellspacing="0">
										<tr bgcolor=#ffffff>
											<td class="color0366CB" width=200 align="right" height="23"><a style="color: blue">最后动作时间：</a></td>
											<td id="lastCommTime" align="left" width=200>${perceptionOpResponse.lastCommTime }</td>
											<td class="color0366CB" width=200 height="23" align="right"></td>
											<td align="left"></td>
										</tr>
									</tbody>

								</TABLE>
							</td>
						</tr>

						<tr>
							<td height="22" style="background-color: #f4f4f4; padding: 3px 0px 3px 15px; font-weight: bolder; font-size: 12px;">告警信息</td>
						</tr>

						<tr>
							<td align="center" valign="middle" style="padding: 5px 5px 0px 5px;">
								<table width="98%" border="0" bgcolor=#ffffff>
									<c:forEach items="${perceptionOpResponse.warningParamInfos }" var="warningParamInfo" varStatus="varStatus">
										<c:choose>
											<c:when test="${varStatus.index%2 == 0}">
												<tbody cellspacing="0">
													<tr bgcolor=#ffffff>
														<td class="color0366CB" width=200 align="right" height="23"><a href="../perception/queryPerceptionParamLog?perceptionId=${perceptionOpResponse.perceptionId }&perceptionParamId=${warningParamInfo.paramId}"
															style="color: blue;">${warningParamInfo.paramDesc }</a>=></td>
														<td align="left" width=200><span id="${warningParamInfo.paramId }">${warningParamInfo.paramValueDesc }</span></td>
											</c:when>
											<c:otherwise>
												<td class="color0366CB" width=200 height="23" align="right"><a href="../perception/queryPerceptionParamLog?perceptionId=${perceptionOpResponse.perceptionId }&perceptionParamId=${warningParamInfo.paramId}"
													style="color: blue;">${warningParamInfo.paramDesc }</a>=></td>
												<td align="left"><span id="${warningParamInfo.paramId }">${warningParamInfo.paramValueDesc }</span></td>
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
							<td height="22" style="background-color: #f4f4f4; padding: 3px 0px 3px 15px; font-weight: bolder; font-size: 12px;">控制信息</td>
						</tr>

						<tr>
							<td align="center" valign="middle" style="padding: 5px 5px 0px 5px;">
								<table width="98%" border="0" bgcolor=#ffffff>
									<c:forEach items="${perceptionOpResponse.ctrlParamInfos }" var="ctrlParamInfo" varStatus="varStatus">
										<c:choose>
											<c:when test="${varStatus.index%2 == 0}">
												<tbody cellspacing="0" id="no7" name="no7">
													<tr bgcolor=#ffffff>
														<td class="color0366CB" width=200 align="right" height="23"><a href="../perception/queryPerceptionParamLog?perceptionId=${perceptionOpResponse.perceptionId }&perceptionParamId=${ctrlParamInfo.paramId}"
															style="color: blue;">${ctrlParamInfo.paramDesc }</a>=></td>
														<td align="left" width=200><select id="${ctrlParamInfo.paramId }" onchange="remoteCtrl($(this))" style="width: 200px;">
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
												<td class="color0366CB" width=200 height="23" align="right"><a href="../perception/queryPerceptionParamLog?perceptionId=${perceptionOpResponse.perceptionId }&perceptionParamId=${ctrlParamInfo.paramId}" style="color: blue;">${ctrlParamInfo.paramDesc }</a>=></td>
												<td align="left"><select id="${ctrlParamInfo.paramId }" onchange="remoteCtrl($(this))" style="width: 200px;">
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
							<td height="23" id="no6" name="no6" style="text-align: center; padding: 5px 0px 5px 0px;">
								<a id="backBtn" href="#" class="easyui-linkbutton" style="width: 90px;" onclick="">返回</a>
								<c:if test="${perceptionOpResponse.perceptionTypeId==2}">
									<a id="videoListLookBtn" class="easyui-linkbutton" style="width: 90px;">视频列表查看</a>
									<a id="realTimeVideoLookBtn" class="easyui-linkbutton" style="width: 90px;">实时视频查看</a>
								</c:if>
							</td>
						</tr>
					</table>

				</td>
			</tr>
		</table>

	</form>
	
	<div style="display: none;">
		<div id="videoListWin">
			<div id="perception-video-list-datagrid" style="width: 100%;height:300px;border:0px;">
			</div>
			<div style="text-align: center; margin: 25px 5px 0px 5px;">
				<a id="videoListWinCloseBtn" href="#" class="easyui-linkbutton" style="width: 90px;" onclick="">关闭</a>
			</div>
		</div>
		<div id="playVideoWin">
			<div style="width: 100%;height:320px;border:0px;">
			    <video id="videoUrl" width="100%" height="320px" controls preload></video>
				<div style="text-align: center; margin: 10px 5px 0px 5px;">
					<a id="playVideoWinCloseBtn" href="#" class="easyui-linkbutton" style="width: 90px;" onclick="">关闭</a>
				</div>
			</div>
		</div>
		<div id="realTimeVideoWin">
			<div style="width: 100%;height:320px;border:0px;">
				<iframe id="realTimeIframe" style="width: 100%;height:100%;border:0px;">
				</iframe>
				<div style="text-align: center; margin: 10px 5px 0px 5px;">
					<a id="realTimeWinCloseBtn" href="#" class="easyui-linkbutton" style="width: 90px;" onclick="">关闭</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>

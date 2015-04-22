<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>设备预警查询界面</title>
<meta name="viewport" content="width=device-width,initial-scale=1, minimum-scale=1, maximum-scale=1">
<meta http-equiv="content-language" content="zh-CN" />
<meta name="format-detection" content="telephone=no" />
<link rel="stylesheet" href="${ctx }/plugin/jquerymobile/jquery.mobile-1.4.5.min.css" />
<link rel="stylesheet" href="${ctx }/plugin/mobiscroll/css/mobiscroll.custom-2.5.0.min.css" />
<script src="${ctx }/plugin/jquery/jquery-2.1.3.min.js"></script>
<script src="${ctx }/plugin/jquery/jquery-utils.js"></script>
<script src="${ctx }/plugin/jquerymobile/jquery.mobile-1.4.5.min.js"></script>
<script src="${ctx }/plugin/mobiscroll/js/mobiscroll.custom-2.5.0.min.js"></script>
<script src="${ctx }/plugin/My97DatePicker/WdatePicker.js"></script>

<script src="${ctx }/js/util.js"></script>

<link rel="stylesheet" href="${ctx }/css/water.css" />

<style type="text/css">
</style>
<script type="text/javascript">
	window.onload = function() {
		try {
			hideLoader();
		} catch (e) {
		}
	}

	function submitQuery() {

		var text = $('#projectSelect option:selected').text();
		$('#projectName').val(text);

		showLoader('正在查询，请稍后...');
		$('#queryForm').submit();
	}
</script>
</head>
<body>
	<div data-role="page" data-ajax="false" class="query-page-body">

		<div data-role="header" data-position="fixed" data-tap-toggle="false" class="header-div">
			<div class="header-title1">
				<span class="header-title1-left" onclick="gotoFunction('${ctx }/spring/mobile/functionList?systemId=${systemId }')"><img alt="" src="${ctx }/images/home.png" height="25" width="25"></span> <span class="header-title1-center">设备预警情况查询</span>
				<span class="header-title1-right"></span>
			</div>
			<div class="header-title2-query-condition">
				<div class="header-title2-top">查询条件</div>
			</div>
		</div>

		<div data-role="content">

			<form id="queryForm" action="${ctx }/spring/mobile/yujing" method="get" data-ajax="false">
				<input type="hidden" name="systemId" value="${systemId }"> <input id="projectName" type="hidden" name="projectName">
				<ul data-role="listview">

					<li>
						<div data-role="fieldcontain">
							<label for="name" style="font-weight: bolder;">项目:</label> <select placeholder="请选择" id="projectSelect" name="projectId">
								<c:forEach items="${projectInfos }" var="project" varStatus="varIndex">
									<option value="${project.projectId }">${project.projectName }</option>
								</c:forEach>
							</select>
						</div>
					</li>
					<li style="border-bottom: 0px;">
						<div style="text-align: center;" data-role="fieldcontain">
							<img alt="" src="${ctx }/images/query.png" height="50" width="290" onclick="submitQuery()">
						</div>
					</li>
				</ul>

			</form>


		</div>

	</div>
</body>
</html>


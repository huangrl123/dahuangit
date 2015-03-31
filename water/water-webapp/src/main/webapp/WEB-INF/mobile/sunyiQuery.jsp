<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>损益查询界面</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta http-equiv="content-language" content="zh-CN" />
<link rel="stylesheet" href="${ctx }/plugin/jquerymobile/jquery.mobile-1.4.5.min.css" />
<link rel="stylesheet" href="${ctx }/plugin/mobiscroll/css/mobiscroll.custom-2.5.0.min.css" />
<script src="${ctx }/plugin/jquery/jquery-2.1.3.min.js"></script>
<script src="${ctx }/plugin/jquery/jquery-utils.js"></script>
<script src="${ctx }/plugin/jquerymobile/jquery.mobile-1.4.5.min.js"></script>
<script src="${ctx }/plugin/mobiscroll/js/mobiscroll.custom-2.5.0.min.js"></script>
<script src="${ctx }/plugin/My97DatePicker/WdatePicker.js"></script>

<style type="text/css">
</style>
<script type="text/javascript">
	window.onload = function() {
		try {
			hideLoader();
		} catch (e) {
		}
	}

	function submit() {
		showLoader('正在查询，请稍后...');
		
		var text = $('#projectSelect option:selected').text();
		$('#projectName').val(text);
		
		$('#queryForm').submit();
	}
</script>
<style type="text/css">
.headDiv {
	height: 40px;
	text-align: center;
	background-color: #2C3640;
	font-size: 25;
	padding-top: 10px;
	color: #FFFFFF;
}
</style>
</head>
<body>
	<div data-role="page" data-ajax="false" style="background-color: #FFFFFF;">

		<div class="headDiv" data-position="fixed">
			<span style="float: left; padding-left:10px;padding-top:5px;" onclick="window.location.href='${ctx }/spring/mobile/functionList'"><img alt="" src="${ctx }/images/home.png" height="25" width="25"></span><span style="margin-right:35px;">损益情况查询</span>
		</div>

		<div data-role="content">

			<form id="queryForm" action="${ctx }/spring/mobile/sunyi" method="get" data-ajax="false">
			    <input id="projectName" type="hidden" name="projectName">
				<input type="hidden" name="systemId" value="${systemId }">
				<ul data-role="listview">
					<li data-role="list-divider" style="font-weight: bolder;color: #7a7b7f;font-size: 15;background-color: e8ebe8;">查询条件</li>

					<li>
						<div data-role="fieldcontain">
							<label for="name" style="font-weight: bolder;">项目:</label> <select id="projectSelect" placeholder="请选择" name="projectId">
								<c:forEach items="${projectInfos }" var="project" varStatus="varIndex">
									<option value="${project.projectId }">${project.projectName }</option>
								</c:forEach>
							</select>
						</div>
					</li>
					<li style="border-bottom: 0px;">
						<div style="text-align: center;" data-role="fieldcontain">
				    		<img alt="" src="${ctx }/images/query.png" height="50" width="300" onclick="submit()">
						</div>
					</li>
				</ul>

			</form>

			
		</div>

	</div>
</body>
</html>


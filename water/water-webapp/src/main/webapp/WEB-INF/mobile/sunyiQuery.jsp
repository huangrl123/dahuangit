<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>收支查询界面</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta http-equiv="content-language" content="zh-CN" />
<link rel="stylesheet" href="${ctx }/plugin/jquerymobile/jquery.mobile-1.4.5.min.css" />
<script src="${ctx }/plugin/jquery/jquery-2.1.3.min.js"></script>
<script src="${ctx }/plugin/jquery/jquery-utils.js"></script>
<script src="${ctx }/plugin/jquerymobile/jquery.mobile-1.4.5.min.js"></script>
<script type="text/javascript">
window.onload = function() {
	try{
		hideLoader();
	}catch(e){
	}
}

function submit() {
	showLoader('正在查询，请稍后...');
	
	var text = $('#projectSelect option:selected').text();
	$('#projectName').val(text);
	
	$('#sunyiQueryForm').submit();
}
</script>
</head>
<body>
	<div data-role="page">

		<div data-role="header" data-position="fixed">
			<a href="${ctx }/spring/mobile/functionList" data-icon="home" data-ajax="false">首页</a>
			<h1>收支情况查询</h1>
		</div>

		<div data-role="content">

			<form id="sunyiQueryForm" action="${ctx }/spring/mobile/sunyi" method="get" data-ajax="false">
				<ul data-role="listview" data-inset="true">
                    <li data-role="list-divider">查询条件</li>
                    <li>
						<div data-role="fieldcontain">
							<label for="name">项目:</label> 
							<select id="projectSelect" name="projectId">
								<c:forEach items="${projectInfos }" var="project" varStatus="varIndex">
									<option value="${project.projectId }">${project.projectName }</option>
								</c:forEach>
							</select>
						</div>
                    </li>
               </ul>
               <input id="projectName" type="hidden" name="projectName">
			</form>
			
     		<div data-role="fieldcontain">
				<input type="button" value="查询" onclick="submit()">
			</div>
		</div>

	</div>
</body>
</html>


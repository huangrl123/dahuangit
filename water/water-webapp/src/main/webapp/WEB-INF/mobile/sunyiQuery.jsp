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
<script src="${ctx }/plugin/jquerymobile/jquery.mobile-1.4.5.min.js"></script>
</head>
<body>
	<div data-role="page">

		<div data-role="header">
			<a href="javascript:history.back();" data-icon="back" data-ajax="false">返回</a>
			<h1>收支情况查询</h1>
		</div>

		<div data-role="content">

			<form action="${ctx }/spring/mobile/sunyi" method="get" data-ajax="false">

				<div data-role="fieldcontain">
					<label for="name">项目:</label> <select>
						<c:forEach items="${projectInfos }" var="project" varStatus="varIndex">
							<option value="${project.projectId }">${project.projectName }</option>
						</c:forEach>
					</select>
				</div>

				<div data-role="fieldcontain">
					<input type="submit" name="submit" id="submit" value="查询" data-ajax="false" />
				</div>

			</form>

		</div>

	</div>
</body>
</html>


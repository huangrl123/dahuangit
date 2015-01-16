<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>预警</title>
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
</script>
<style type="text/css">
	.listItemLeft {
		float: left; 
		margin-right: 10; 
		color: #866E54
	}
	.listItemCenter {
		float: left; 
		margin-left: 10; 
		color: #CACACA
	}
	.listItemRight {
		float: right; 
		color: #469E6F;
	}
	.pageDiv {
		text-align: center;
	}
</style>
</head>
<body>

	<div data-role="page">

		<div data-role="header" data-position="fixed">
			<a href="${ctx}/spring/mobile/functionList" data-icon="home" data-ajax="false" onclick="showLoader()">首页</a>
			<h1>设备预警表</h1>
			<a href="${ctx }/spring/mobile/yujingQuery" data-icon="search" data-ajax="false" onclick="showLoader()">查询</a>
		</div>

		<div data-role="content">

			<ul data-role="listview">
			    <c:forEach items="${yujingMap }" var="item" varStatus="mapStatus">
					<li data-role="list-divider">${item.key }</li>
			        <c:forEach items="${item.value }" var="yujing">
						<li>
							<span class="listItemLeft">${yujing.buildName }</span>
							<span class="listItemCenter">${yujing.roomName }</span>
							<span class="listItemRight">￥${yujing.sumMoney }</span>
						</li>
			        </c:forEach>
			    </c:forEach>
			</ul>

		</div>

	</div>

</body>
</html>


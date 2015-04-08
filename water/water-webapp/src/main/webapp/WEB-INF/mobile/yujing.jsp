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

<link rel="stylesheet" href="${ctx }/css/water.css" />

<script type="text/javascript">
	window.onload = function() {
		try {
			hideLoader();
		} catch (e) {
		}
	}
</script>
<style type="text/css">
.listItemLeft {
	float: left;
	margin-right: 10;
	font-weight: bolder;
	color: #000000;
}

.listItemCenter {
	float: left;
	margin-left: 10;
	font-weight: bolder;
	color: #7a7b7f;
}

.listItemRight {
	float: right;
	font-weight: bolder;
	color: #469E6F;
}
</style>
</head>
<body>

	<div data-role="page">
		<div data-role="header" data-position="fixed" data-tap-toggle="false" class="header-div">
			<div class="header-title1">
				<span class="header-title1-left" onclick="gotoFunction('${ctx }/spring/mobile/functionList?systemId=${systemId }')"><img alt="" src="${ctx }/images/home.png" height="25" width="25"></span> <span class="header-title1-center">设备预警表</span>
				<span class="header-title1-right" onclick="gotoFunction('${ctx }/spring/mobile/yujingQuery?systemId=${systemId }')"><img alt="" src="${ctx }/images/search.png" height="25" width="25"></span>
			</div>
			<div class="header-title2">
				<div class="header-title2-top">${projectName }</div>
				<div class="header-title2-bottom ">${request.beginTime }至${request.endTime }</div>
			</div>
		</div>

		<div data-role="content">

			<ul data-role="listview">
				<c:choose>
					<c:when test="${!empty yujingMap }">
						<c:forEach items="${yujingMap }" var="item" varStatus="mapStatus">
							<c:forEach items="${item.value }" var="yujing">
								<li><span class="listItemLeft">${yujing.buildName }</span> <span class="listItemCenter">${yujing.roomName }</span> <span class="listItemRight"><font size="2">￥</font>${yujing.sumMoney }</span></li>
							</c:forEach>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<li><span class="listItemLeft">没有符合条件的记录</span> <span class="listItemCenter"></span> <span class="listItemRight"></span></li>
					</c:otherwise>
				</c:choose>

			</ul>

		</div>

	</div>

</body>
</html>


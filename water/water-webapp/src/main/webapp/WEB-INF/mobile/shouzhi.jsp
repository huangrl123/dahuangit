<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>收支</title>
<meta name="viewport" content="width=device-width,initial-scale=1, minimum-scale=1, maximum-scale=1">
<meta http-equiv="content-language" content="zh-CN" />
<meta name="format-detection" content="telephone=no" />
<link rel="stylesheet" href="${ctx }/plugin/jquerymobile/jquery.mobile-1.4.5.min.css" />
<script src="${ctx }/plugin/jquery/jquery-2.1.3.min.js"></script>
<script src="${ctx }/plugin/jquery/jquery-utils.js"></script>
<script src="${ctx }/plugin/jquerymobile/jquery.mobile-1.4.5.min.js"></script>

<link rel="stylesheet" href="${ctx }/css/water.css" />

</head>
<body>

	<div data-role="page">

		<div data-role="header" data-position="fixed" data-tap-toggle="false" class="header-div">
			<div class="header-title1">
				<span class="header-title1-left" onclick="gotoFunction('${ctx }/spring/mobile/functionList?systemId=${systemId }')"><img alt="" src="${ctx }/images/home.png" height="25" width="25"></span> <span class="header-title1-center">收支统计表</span>
				<span class="header-title1-right" onclick="gotoFunction('${ctx }/spring/mobile/shouzhiQuery?systemId=${systemId }')"><img alt="" src="${ctx }/images/search.png" height="25" width="25"></span>
			</div>
			<div class="header-title2">
				<div class="header-title2-top">${projectName }</div>
				<div class="header-title2-bottom">${request.beginTime }至${request.endTime }</div>
			</div>
		</div>
		<div data-role="content">
			<ul data-role="listview">
				<c:choose>
					<c:when test="${!empty shouzhiMap }">
						<c:forEach items="${shouzhiMap }" var="shouzhiMapItem" varStatus="shouzhiMapStatus">
							<c:forEach items="${shouzhiMapItem.value }" var="shouzhi" varStatus="shouzhiStatus">
								<li><span class="two-col-listItemLeft">${shouzhi.operatorName }</span> <c:choose>
										<c:when test="${shouzhi.sumMoney<=0 }">
											<span class="two-col-listItemRightWarning"><font size="2">￥</font>${shouzhi.sumMoney }</span>
										</c:when>
										<c:otherwise>
											<span class="two-col-listItemRight"><font size="2">￥</font>${shouzhi.sumMoney }</span>
										</c:otherwise>
									</c:choose></li>
							</c:forEach>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<li><span class="two-col-listItemLeft">（没有符合条件的记录）</span> <span class="two-col-listItemRightWarning"><font size="2"></span></li>
					</c:otherwise>
				</c:choose>
			</ul>

		</div>

	</div>
	<script type="text/javascript">
		$(window).resize();
	</script>
</body>
</html>


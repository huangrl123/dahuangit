<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>收支</title>
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
		font-weight:bolder;
		color: #000000
	}
	.listItemRight {
		float: right; 
		font-weight:bolder;
		color: #469E6F;
	}
	.listItemRightWarning {
		float: right; 
		font-weight:bolder;
		color: #d03e3e;
	}
	.pageDiv {
		text-align: center;
	}
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

	<div data-role="page">

	    <div class="headDiv" data-position="fixed">
			<span style="float: left; padding-left:10px;padding-top:5px;" onclick="window.location.href='${ctx }/spring/mobile/functionList'"><img alt="" src="${ctx }/images/home.png" height="25" width="25">&nbsp;</span>收支统计表<span style="float: right; padding-right:10px;padding-top:5px;"><img alt="" src="${ctx }/images/search.png" height="25" width="25" onclick="window.location.href='${ctx }/spring/mobile/shouzhiQuery?sytemId=${systemId }'"></span>
		</div>

		<div data-role="content">

			<ul data-role="listview">
			    <c:forEach items="${shouzhiMap }" var="shouzhiMapItem" varStatus="shouzhiMapStatus">
			    	<li data-role="list-divider" style="font-weight: bolder;color: #7a7b7f;font-size: 16;background-color: e8ebe8;">${shouzhiMapItem.key }<br><span style="color: #b0b0b0;font-size: 15;">${request.beginTime }至${request.endTime }</span></li>
			    	<c:forEach items="${shouzhiMapItem.value }" var="shouzhi" varStatus="shouzhiStatus">
						<li>
							<span class="listItemLeft">${shouzhi.operatorName }</span>
							<c:choose>
								<c:when test="${shouzhi.sumMoney<=0 }">
									<span class="listItemRightWarning">￥${shouzhi.sumMoney }</span>
								</c:when>
								<c:otherwise>
									<span class="listItemRight">￥${shouzhi.sumMoney }</span>
								</c:otherwise>
							</c:choose>
						</li>
			    	</c:forEach>
			    </c:forEach>
				
			</ul>

		</div>

	</div>

</body>
</html>


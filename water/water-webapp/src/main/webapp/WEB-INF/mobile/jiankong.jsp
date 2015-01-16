<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>监控</title>
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
			<h1>热泵监控表</h1>
			<a href="${ctx }/spring/mobile/jiankongQuery" data-icon="search" data-ajax="false" onclick="showLoader()">查询</a>
		</div>

		<div data-role="content">

			<ul data-role="listview">
				<li data-role="list-divider">项目0</li>
				<li>
					<span class="listItemLeft">项目0</span>
					<span class="listItemCenter">1111101</span>
					<span class="listItemRight">1110001</span>
				</li>
				
			</ul>

		</div>

		<div data-role="footer" data-position="fixed" class="pageDiv">
			<a href="${ctx }/spring/mobile/yujing" data-ajax="false" onclick="showLoader()">首页</a>
			<a href="${ctx }/spring/mobile/yujing" data-ajax="false" onclick="showLoader()">上一页</a>
				3/10
			<a href="${ctx }/spring/mobile/yujing" data-ajax="false" onclick="showLoader()">下一页</a>
			<a href="${ctx }/spring/mobile/yujing" data-ajax="false" onclick="showLoader()">末页</a>
		</div>
	</div>

</body>
</html>


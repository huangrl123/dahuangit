<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>损益统计表</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta http-equiv="content-language" content="zh-CN" />
<link rel="stylesheet" href="${ctx }/plugin/jquerymobile/jquery.mobile-1.4.5.min.css" />
<script src="${ctx }/plugin/jquery/jquery-2.1.3.min.js"></script>
<script src="${ctx }/plugin/jquery/jquery-utils.js"></script>
<script src="${ctx }/plugin/jquerymobile/jquery.mobile-1.4.5.min.js"></script>
<script src="${ctx }/plugin/highcharts/highcharts-4.0.4.js"></script>
<script type="text/javascript">
$(function() {
	$.ajax({
		url : '${ctx}/spring/mobile/getSunyiAjaxData',
		type : 'POST',
		data : {
			projectId : '${projectId}'
		},
		dataType : 'JSON',
		cache : false,
		success : function(result, textStatus) {
			if (result.success) {
				loadChart(result);
			} else {
				showAlertDialog(result.msg);
			}

			hideLoader();
		},
	});	
});

function loadChart(result) {
	//学期每月收入
	$('#semesterMonthInfos').highcharts({
		title : {
			text : '学期每月收入'
		},

		chart : {
			type : 'column'
		},

		xAxis : {
			categories : result.semesterMonthChartInfo.categories
		},
		yAxis : {
			min : 0,
			title : {
				text : '单位(元)'
			}
		},

		series : [ {
			name : '${projectName}',
			data : result.semesterMonthChartInfo.data

		} ]
	});

	//近三年收入
	$('#recentYearSemesterMonthInfos').highcharts({
		title : {
			text : '学期收入',
			x : -20
		},

		xAxis : {
			labels : {
				rotation : -45,
				align : 'right'
			},
			categories : result.recentYearSemesterMonthChartInfo.categories
		},
		yAxis : {
			title : {
				text : '单位(元)'
			},
			plotLines : [ {
				value : 0,
				width : 1,
				color : '#808080'
			} ]
		},

		series : [ {
			name : '${projectName}',
			data : result.recentYearSemesterMonthChartInfo.data
		} ]
	});
}
</script>
<style type="text/css">
.list-item-left {
	float: left; 
	margin-right: 10; 
	color: #866E54;
}

.list-item-right {
	float: right; 
	color: #469E6F;
}

#semesterMonthInfos {
	width: 90%; 
	height: 400px;
}

#recentYearSemesterMonthInfos {
	width: 90%; 
	height: 400px;
}
</style>
</head>
<body>
	<div data-role="page">

		<div data-role="header" data-position="fixed">
			<a href="${ctx }/spring/mobile/functionList" data-icon="home" data-ajax="false" onclick="showLoader()">首页</a>
			<h1>损益统计表</h1>
			<a href="${ctx }/spring/mobile/sunyiQuery" data-icon="search" data-ajax="false" onclick="showLoader()">查询</a>
		</div>

		<div data-role="content">

			<ul data-role="listview">
				<li data-role="list-divider">学期概况</li>
				<li>
					<span class="list-item-left">收支合计</span>
					<span class="list-item-right">￥${semesterSumInfo.sumInout }</span>
				</li>
				<li>
					<span class="list-item-left">消费合计</span>
					<span class="list-item-right">￥${semesterSumInfo.sumXf }</span>
				</li>
				<li>
					<span class="list-item-left">赠送合计</span>
					<span class="list-item-right">￥${semesterSumInfo.sumZs }</span>
				</li>
				<li>
					<span class="list-item-left">成本合计</span>
					<span class="list-item-right">￥${semesterSumInfo.sumCb }</span>
				</li>
				<li>
					<span class="list-item-left">项目收益</span>
					<span class="list-item-right">￥${semesterSumInfo.sumSy }</span>
				</li>
				
				<li data-role="list-divider">学期月份收益</li>
				<li>
					<span id="semesterMonthInfos"></span>
				</li>

				<li data-role="list-divider">最近三年月份收益</li>
				<li>
					<span id="recentYearSemesterMonthInfos"></span>
				</li>
			</ul>

		</div>

	</div>
</body>
</html>


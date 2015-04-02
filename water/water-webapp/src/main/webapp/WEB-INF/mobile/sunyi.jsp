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
			text : '${projectName}',
			style : {
				color : '#3c3837',
				fontSize: 16,
				fontWeight: 'bolder'
			}
		},

		chart : {
			type : 'column'
		},

		legend : {
			enabled : false
		},
		
		xAxis : {
			categories : result.semesterMonthChartInfo.categories
		},
		yAxis : {
			min : 0,
			title : {
				text : '单位(元)',
				style : {
					color : '#7a7b7f',
					fontSize: 10,
					fontWeight: 'bolder'
				}
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
			text : '${projectName}',
			style : {
				color : '#3c3837',
				fontSize: 16,
				fontWeight: 'bolder'
			},
			x : -20
		},

		legend : {
			enabled : false
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
				text : '单位(元)',
				style : {
					color : '#7a7b7f',
					fontSize: 10,
					fontWeight: 'bolder'
				}
			},
			plotLines : [ {
				value : 0,
				width : 1,
				color : '#808080'
			} ]
		},

		series : [ {
			name : '${projectName}',
			color : '#e95b69',
			data : result.recentYearSemesterMonthChartInfo.data
		} ]
	});
}
</script>
<style type="text/css">
.list-item-left {
	float: left; 
	margin-right: 10; 
	font-weight:bolder;
	color: #000000;
}

.list-item-right {
	float: right; 
	font-weight:bolder;
	color: #7a7b7f;
}
.list-item-right-total {
	float: right; 
	font-weight:bolder;
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
.list-divider{
	font-weight: bolder;
	color: #7a7b7f;
	font-size: 16;
	background-color: e8ebe8;
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
			<span style="float: left; padding-left:10px;padding-top:5px;" onclick="window.location.href='${ctx }/spring/mobile/functionList?systemId=${systemId }'"><img alt="" src="${ctx }/images/home.png" height="25" width="25">&nbsp;</span>损益统计表<span style="float: right; padding-right:10px;padding-top:5px;"><img alt="" src="${ctx }/images/search.png" height="25" width="25" onclick="window.location.href='${ctx }/spring/mobile/sunyiQuery?systemId=${systemId }'">&nbsp;</span>
		</div>
		
		<div data-role="content">

			<ul data-role="listview">
				<li data-role="list-divider" style="font-weight: bolder;color: #7a7b7f;font-size: 16;background-color: e8ebe8;">学期概况</li>
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
					<span class="list-item-right-total">￥${semesterSumInfo.sumSy }</span>
				</li>
				
				<li data-role="list-divider" style="font-weight: bolder;color: #7a7b7f;font-size: 16;background-color: e8ebe8;">学期月份收益</li>
				<li>
					<span id="semesterMonthInfos"></span>
				</li>

				<li data-role="list-divider" style="font-weight: bolder;color: #7a7b7f;font-size: 16;background-color: e8ebe8;">最近三年收益</li>
				<li>
					<span id="recentYearSemesterMonthInfos"></span>
				</li>
			</ul>

		</div>

	</div>
</body>
</html>


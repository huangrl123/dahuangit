<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>损益统计表</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta http-equiv="content-language" content="zh-CN" />

</head>
<body>

	<div data-role="page">

		<div data-role="header" data-position="fixed">
			<a href="${ctx }/spring/mobile/functionList" data-icon="home" data-ajax="false">首页</a>
			<h1>损益统计表</h1>
			<a href="${ctx }/spring/mobile/sunyiQuery" data-icon="search" data-ajax="false">查询</a>
		</div>

		<div data-role="content">

			<ul data-role="listview">
				<li data-role="list-divider">学期概况</li>
				<li><span style="float: left; margin-right: 10; color: #866E54">收支合计</span><span
					style="float: right; color: #469E6F;">￥4565</span></li>
				<li><span style="float: left; margin-right: 10; color: #866E54">消费合计</span><span
					style="float: right; color: #469E6F;">￥4565</span></li>
				<li><span style="float: left; margin-right: 10; color: #866E54">赠送合计</span><span
					style="float: right; color: #469E6F;">￥4565</span></li>
				<li><span style="float: left; margin-right: 10; color: #866E54">成本合计</span><span
					style="float: right; color: #469E6F;">￥4565</span></li>
				<li><span style="float: left; margin-right: 10; color: #866E54">项目收益</span><span
					style="float: right; color: #469E6F;">￥4565</span></li>

				<li data-role="list-divider">学期月份收益</li>
				<li><span id="container" style="width: 90%; height: 400px;"></span></li>

				<li data-role="list-divider">最近三年月份收益</li>
				<li><span id="container1" style="width: 90%; height: 400px;"></span></li>
			</ul>

		</div>

	</div>
	<link rel="stylesheet" href="${ctx }/plugin/jquerymobile/jquery.mobile-1.4.5.min.css" />
	<script src="${ctx }/plugin/jquery/jquery-2.1.3.min.js"></script>
	<script src="${ctx }/plugin/jquerymobile/jquery.mobile-1.4.5.min.js"></script>

	<script src="${ctx }/plugin/highcharts/highcharts-4.0.4.js"></script>
	<script type="text/javascript">
		$(function() {
			//学期每月收入
			$('#container').highcharts({
				title : {
					text : '2014年下学期每月收入'
				},

				chart : {
					type : 'column'
				},

				xAxis : {
					categories : [ '1月', '2月', '3月', '4月', '5月', '6月' ]
				},
				yAxis : {
					min : 0,
					tickInterval : 50000,
					title : {
						text : '单位(元)'
					},
					labels : {
						formatter : function() {
							return this.value / 1000 + 'k';
						}
					}
				},

				series : [ {
					name : '项目1',
					data : [ 49000.9, 71000.5, 106000.4, 129000.2, 144000.0, 176000.0 ]

				} ]
			});

			//近三年收入
			$('#container1').highcharts({
				title : {
					text : '2012-2014学期收入',
					x : -20
				},

				xAxis : {
					labels : {
						rotation : -45,
						align : 'right'
					},
					categories : [ '2012上', '2012下', '2013上', '2013下', '2014上', '2014下' ]
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
					name : '项目1',
					data : [ 6 * 49000.9, 6 * 71000.5, 6 * 106000.4, 6 * 129000.2, 6 * 144000.0, 6 * 176000.0 ]
				} ]
			});

		});
	</script>
</body>
</html>


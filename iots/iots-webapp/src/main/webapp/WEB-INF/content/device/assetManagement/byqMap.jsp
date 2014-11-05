<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8" />
		
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta name="description" content="" />
		<meta name="author" content="Olas Navigator" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>配电网综合管理平台</title>
<link type="text/css"  href="${pageContext.request.contextPath}/css/device/bootstrap.css" rel="stylesheet" />
<link type="text/css"  href="${pageContext.request.contextPath}/css/device/bootstrap-responsive.css" rel="stylesheet" />

<link  rel="stylesheet" type="text/css"  href="${pageContext.request.contextPath}/css/device/css.css" />
<link  rel="stylesheet" type="text/css"  href="${pageContext.request.contextPath}/css/device/assetManagement.css" />
<link  rel="stylesheet" type="text/css"  href="${pageContext.request.contextPath}/blugin/jquery/chosen/chosen.css" />


 <script type="text/javascript" src="${pageContext.request.contextPath}/blugin/jquery/jquery-1.10.2.js"></script>		
 
 <script type="text/javascript" src="${pageContext.request.contextPath}/blugin/hightCharts/highcharts.js"></script>	
  <script type="text/javascript" src="${pageContext.request.contextPath}/blugin/hightCharts/highcharts-more.js"></script>	
 
 
 <script type="text/javascript" src="${pageContext.request.contextPath}/blugin/hightCharts/exporting.js"></script>		
 <script type="text/javascript" src="${pageContext.request.contextPath}/blugin/jquery/chosen/chosen.jquery.js"></script>			

	<!-- >script src="${pageContext.request.contextPath}/blugin/hightCharts/mychart.js"></script>	
	<script src="${pageContext.request.contextPath}/blugin/hightCharts/myChartTheme.js"></script-->	
<script src="${pageContext.request.contextPath}/js/device/assetManagement/capacity_ratio_top.js"></script>	
<script src="${pageContext.request.contextPath}/js/device/assetManagement/capacity_ratio.js"></script>
<script src="${pageContext.request.contextPath}/js/device/assetManagement/rated_capacity.js"></script>
<script src="${pageContext.request.contextPath}/js/device/assetManagement/operating_period.js"></script>
<script src="${pageContext.request.contextPath}/js/device/assetManagement/vender_column.js"></script>
<script src="${pageContext.request.contextPath}/js/device/assetManagement/vender_pie.js"></script>
<script src="${pageContext.request.contextPath}/js/device/assetManagement/load_condition.js"></script>
	
		

		
		
	
	
	
		
</head>
<body>
<div class="top">
	<div class="logo">
		<span><img src="${pageContext.request.contextPath}/image/logo.png" /></span>
		配电网综合管理平台
	</div>
	<div class="siteinfo">
		<div class="siteinfo-text siteinfobg1"><a href="${pageContext.request.contextPath}/spring/device/toByq">图标显示</a></div>
		<div class="siteinfo-text siteinfobg2"><a href="${pageContext.request.contextPath}/spring/device/toByqMap">地图信息</a></div>
	</div>
</div>
<div style="clear:both"></div>
<div class="wrap">
    <ul class="breadcrumb breadcrumb-main2">
		<li><a href="${pageContext.request.contextPath}">首页</a> 》</li>
		<li><a href="${pageContext.request.contextPath}/spring/device/toByq">资产</a> 》</li>
		<li class="text-info">地图</li>
	</ul>
</div>

<div class="wrap nav_bg">
	<div class="sub">
	<div class="sub_con">
			<div class="nav_img nav_byq">
				<a href="#">变压器</a>
			</div>
			
			<div class="nav_img nav_kg">
				<a href="#">开关</a>
			</div>
			
			<div class="nav_img nav_gt">
				<a href="#">杆塔</a>
			</div>
			
			<div class="nav_img nav_xn">
				<a href="#">线缆</a>
			</div>
			
			<div class="nav_img nav_df">
				<a href="#">电房</a>
			</div>
			
			<div class="nav_img nav_dyzc">
				<a href="#">低压资产</a>
			</div>
		</div>
		<div id='suo_infor' class="suo_tab_middle"><h3>供电所</h3>
			<div class="subtext">
				<a href="#">大良所</a>
				<a href="#">勒流所</a>
				<a href="#">乐从所</a>
				<a href="#">陈村所</a>
				<a href="#">北滘所</a>
				<a href="#">龙江所</a>
				<a href="#">杏坛所</a>
				<a href="#">伦滘所</a>
				<a href="#">容桂所</a>
				<a href="#">均安所</a>
			</div>
		</div>
		<div id='suo_infor' class="suo_tab_middle"><h3>变电站</h3>
			<div class="subtext">
				<a href="#">大良站</a>
				<a href="#">富安站</a>
			</div>
		</div>
		<div class="sub_con">
			 <h3>线路</h3>
			 <div class="sub_con" style="padding-left:9px; width:175px; margin-bottom:25px;">
				<select data-placeholder="请选择线路" class="chosen-select" style="width:175px;">
					<option value=""></option>
					<option value="702演艺线">702演艺线</option>
					<option value="703公园甲线">703公园甲线</option>
					<option value="707五沙乙线">707五沙乙线</option>
					<option value="708逢桂线">708逢桂线</option>
					<option value="709五沙甲线">709五沙甲线</option>
					<option value="731公园乙线">731公园乙线</option>
					<option value="701市府线">701市府线</option>
					<option value="703会展甲线">703会展甲线</option>
					<option value="704信心线">704信心线</option>
					<option value="705嘉信Ⅰ线">705嘉信Ⅰ线</option>
					<option value="706悦城线">706悦城线</option>
					<option value="707祥德线">707祥德线</option>
					<option value="708南西线">708南西线</option>
					<option value="709电信线">709电信线</option>
					<option value="710金桂线">710金桂线</option>
					<option value="711文化Ⅰ线">711文化Ⅰ线</option>
					<option value="713会展乙线">713会展乙线</option>
					<option value="714广场线">714广场线</option>
					<option value="715嘉信Ⅱ线">715嘉信Ⅱ线</option>
					<option value="717西小线">717西小线</option>
					<option value="718书馆线">718书馆线</option>
			        <option value="719龙盘线">719龙盘线</option>
				</select>
			</div>
		</div>
	
	</div>
	
	<div class="main">
		<div class="con">
			<img src="${pageContext.request.contextPath}/image/byqMap.png" />			
	   </div>
  </div>

</div>
<div class="page">
顺德供电局版权所有
</div>

<script type="text/javascript">
	var config = {'.chosen-select' : {}};
	for (var selector in config) {
		$(selector).chosen(config[selector]);
	}
</script>
</body>
</html>
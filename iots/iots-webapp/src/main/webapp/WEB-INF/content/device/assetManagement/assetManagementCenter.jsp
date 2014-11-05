<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta name="description" content="" />
		<meta name="author" content="Olas Navigator" />

		<!-- required styles -->
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/device/library/assets/css/bootstrap.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/device/library/assets/css/bootstrap-responsive.css" />
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/device/library/css/styles.css" />
		
		<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
		<!--[if lt IE 9]>
		<script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /></head>
	<body>
			
		
		<div id="left_layout">
			<!-- main content -->
			<div id="main_content" class="container-fluid">
			
				<!-- page heading -->
				<div class="page-heading">
				
					<h2 class="page-title muted">
						<i class="micon-stats-up"></i> Charts
					</h2>
					
					<div class="page-info hidden-phone">
						<ul class="stats">
							<li>
								<span class="large text-warning">2354</span>
								<span class="mini muted">visitors</span>
							</li>
							<li>
								<span class="large text-info">4523</span>
								<span class="mini muted">members</span>
							</li>
							<li>
								<span class="large text-success">5673</span>
								<span class="mini muted">orders</span>
							</li>
							<li>
								<span class="large text-error">15</span>
								<span class="mini muted">cancellation</span>
							</li>
						</ul>
					</div>
				</div>
				<!-- ./ page heading -->
				
				<ul class="breadcrumb breadcrumb-main">
					<li><a href="#">Home</a> <span class="divider"><i class="icon-caret-right"></i></span></li>
					<li><a href="#">Elements</a> <span class="divider"><i class="icon-caret-right"></i></span></li>
					<li class="text-info">Charts</li>
				</ul>
				
				<!-- post wrapper -->				
				<div class="row-fluid">
					<div class="span6">
						<div class="well widget">
							<div class="widget-header">
								<h3 class="title">Horizontal Bar Chart</h3>
							</div>
							<!-- ** horizontal bar chart ** -->
							<div id="horizontal-bar-div" style="height:300px;width:100%;" data-value='[[[2,1], [4,2], [6,3], [3,4]],[[5,1], [1,2], [3,3], [4,4]],[[4,1], [7,2], [1,3], [2,4]]]' data-labels="[{label:'Label 1'},{label:'Label 2'},{label:'Label 3'}]" data-colors='[ "#feb847","#425eb8","#409627","#b64b53","#3f4143","#50204a" ]'></div>
							<!-- ** ./ horizontal bar chart ** -->
						</div>
						<div class="well widget">
							<div class="widget-header">
								<h3 class="title">Vertical Bar Chart</h3>
							</div>
							<!-- ** vertical bar chart ** -->
							<div id="vertical-bar-div" data-value='[[200, 600, 700, 1000],[460, 210, 690, 820],[260, 440, 320, 200]]' data-ticks='["May", "June", "July", "August"]' data-labels="[{label:'One'},{label:'Two'},{label:'Three'}]" data-colors='[ "#feb847","#425eb8","#409627","#b64b53","#3f4143","#50204a" ]' style="width:100%;height:300px;"></div>
							<!-- ** ./ vertical bar chart ** -->
						</div>
						<div class="well widget">
							<div class="widget-header">
								<h3 class="title">Line Chart</h3>
							</div>
							<div id="line-chart-div" style="width:100%; height:300px;" data-content="[[1,2,3,4,5,6,7,8,9], [2,3,4,5,6,7,8,9,10], [3,4,5,6,7,8,9,10,11], [4,5,6,7,8,9,10,11,12]]" data-colors='[ "#feb847","#425eb8","#409627","#b64b53","#3f4143","#50204a" ]'></div>
						</div>
					</div>
					<div class="span6">
						<div class="well widget">
							<div class="widget-header">
								<h3 class="title">Area Chart</h3>
							</div>
							<div id="area-chart-div" style="width:100%; height:300px;" data-content="[[6,  11, 10, 13, 11,  7], [3,   6,  7,  7,  5,  3], [4,   8,  9, 10, 8,   6], [9,  13, 14, 16, 17, 19], [15, 17, 16, 18, 13, 11]]" data-colors='[ "#feb847","#425eb8","#409627","#b64b53","#3f4143","#50204a" ]' data-fill="1|3|0" data-fill-color="rgba(150, 197, 246, 0.5)"></div>
						</div>
						<div class="well widget">
							<div class="widget-header">
								<h3 class="title">Pie Chart</h3>
							</div>
							<!-- ** pie chart ** -->						
							<div id="pie-div" style="width:100%;height:300px;" data-content="[['Heavy Industry', 12],['Retail', 9], ['Light Industry', 14], ['Out of home', 16],['Commuting', 7], ['Orientation', 9]]" data-colors='[ "#feb847","#425eb8","#409627","#b64b53","#3f4143","#50204a" ]'></div>
							<!-- ** ./ pie chart ** -->
						</div>
						<div class="well widget">
							<div class="widget-header">
								<h3 class="title">Donut Chart</h3>
							</div>
							<!-- ** donut chart ** -->
							<div id="donut-pie-div" data-value="[['abcd',16], ['efgh',8], ['ijkl',14], ['mnop',16], ['qrste',8], ['opresf',14]]" data-value2="0" data-colors='[ "#feb847","#425eb8","#409627","#b64b53","#3f4143","#50204a" ]' style="width:100%;height:300px;">
							</div>
							<!-- ** ./ donut chart ** -->
						</div>
					</div>
				</div>
				<!-- ./ post wrapper -->
			</div>
			<!-- end main content -->
			
			
		</div>
		
		<!-- external api -->
		<script src="http://maps.google.com/maps/api/js?v=3.5&sensor=false"></script>
		
		<!-- base -->
		<script src="library/assets/js/jquery.js"></script>
		<script src="library/assets/js/bootstrap.min.js"></script>
		
		<!-- addons >
		<script src="library/plugins/chart-plugins.js"></script>
		<script src="library/plugins/jquery-ui-slider.js"></script>
		<script src="library/plugins/redactor/redactor.min.js"></script>
		<script src="library/plugins/jmapping/markermanager.js"></script>
		<script src="library/plugins/jmapping/StyledMarker.js"></script>
		<script src="library/plugins/jmapping/jquery.metadata.js"></script>
		<script src="library/plugins/jmapping/jquery.jmapping.min.js"></script>
		<script src="library/plugins/jquery.uniform.js"></script>
		<script src="library/plugins/chosen.jquery.min.js"></script>
		<script src="library/plugins/bootstrap-datepicker.js"></script>
		<script src="library/plugins/jquery.timePicker.min.js"></script>
				
		< plugins loader -->
		
		<script src="${pageContext.request.contextPath}/js/device/assetManagement/chart-plugins.js"></script>
		
		<script src="${pageContext.request.contextPath}/js/device/assetManagement/loader.js"></script>
	</body>
</html>
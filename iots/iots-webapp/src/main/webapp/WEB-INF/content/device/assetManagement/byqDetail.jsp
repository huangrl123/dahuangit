<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8" />
		
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<meta name="description" content="" />
		<meta name="author" content="Olas Navigator" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>详情</title>
<link type="text/css"  href="${pageContext.request.contextPath}/css/device/bootstrap.css" rel="stylesheet" />
<link type="text/css"  href="${pageContext.request.contextPath}/css/device/bootstrap-responsive.css" rel="stylesheet" />

<link  rel="stylesheet" type="text/css"  href="${pageContext.request.contextPath}/css/device/css.css" />
<link  rel="stylesheet" type="text/css"  href="${pageContext.request.contextPath}/css/device/assetManagement.css" />

 <script type="text/javascript" src="${pageContext.request.contextPath}/blugin/jquery/jquery-1.10.2.js"></script>		



	<link rel="stylesheet" href="${pageContext.request.contextPath}/blugin/jquery/date/jquery-ui.css">
	<script src="${pageContext.request.contextPath}/blugin/jquery/date/jquery-1.10.2.js"></script>
	<script src="${pageContext.request.contextPath}/blugin/jquery/date/jquery-ui.js"></script>

	<script src="${pageContext.request.contextPath}/blugin/jquery/date/datepicker-zh-TW.js"></script>
	<link rel="${pageContext.request.contextPath}/blugin/jquery/date/stylesheet" href="style.css">



 <script type="text/javascript" src="${pageContext.request.contextPath}/blugin/hightCharts/highcharts.js"></script>	
 <script type="text/javascript" src="${pageContext.request.contextPath}/blugin/hightCharts/highcharts-more.js"></script>	
 <script type="text/javascript" src="${pageContext.request.contextPath}/blugin/hightCharts/exporting.js"></script>		


 <link href="${pageContext.request.contextPath}/blugin/ext-3.2.1/resources/css/ext-all.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/blugin/ext-3.2.1/adapter/ext/ext-base-debug.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/blugin/ext-3.2.1/ext-all-debug.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/blugin/ext-3.2.1/ext-lang-zh_CN.js"></script>
 


<script src="${pageContext.request.contextPath}/js/device/assetManagement/oil_record_detail.js"></script>

	
 <script>
  $(function() {
    $( "#datepicker" ).datepicker({

        changeMonth: true,
        changeYear: true,
        dateFormat: 'yy年MM月',
        //showButtonPanel: true,
        onClose: function(dateText, inst) {
            var month = $("#ui-datepicker-div .ui-datepicker-month option:selected").val();//得到选中的月份值
            var year = $("#ui-datepicker-div .ui-datepicker-year option:selected").val();//得到选中的年份值
            $('#Datepicker').val(year+'-'+(parseInt(month)+1));//给input赋值，其中要对月值加1才是实际的月份
        }
      }  );

  });
  </script>

</head>
<body>
<div class="top">
	<div class="logo">
		<span><img src="${pageContext.request.contextPath}/image/logo.png" /></span>
		 配电网综合管理平台
	</div>
	<div class="siteinfo">
		<div class="siteinfo-text siteinfobg1"><a href="#">图标显示</a></div>
		<div class="siteinfo-text siteinfobg2"><a href="#">地图显示</a></div>
	</div>
</div>
<div style="clear:both"></div>
<div class="wrap">
	

			
		<ul class="breadcrumb breadcrumb-main">
					<li><a href="${pageContext.request.contextPath}">首页</a>》</li>
					<li><a href="javascript:history.go(-3);">资产</a>》</li>
					<li><a href="javascript:history.go(-2);">变压器列表</a>》</li>
					<li><a href="javascript:history.go(-1);">变压器详情</a>》</li>
					<li class="text-info">详情</li>
		</ul>
		
		
			<!-- img src="${pageContext.request.contextPath}/image/5.png" /-->
			

		

		
		
		<div class="con2">
			
			<h4>油压记录详情</h4>
			<p><font size='4' color="#526776">统计日期:</font> <input type="text" id="datepicker">&nbsp;</p>
	
			<div id="oil_record_detail" style="min-width: 801px; height: 353px; max-width: 801px; margin: 0 auto"></div>
			

		
  </div>

</div>
<div class="page">
顺德供电局版权所有
</div>


</body>
</html>

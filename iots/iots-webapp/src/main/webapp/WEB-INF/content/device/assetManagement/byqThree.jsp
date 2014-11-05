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


<link rel="stylesheet" href="${pageContext.request.contextPath}/blugin/button/css/buttons.css" type="text/css" media="screen" />


<link  rel="stylesheet" type="text/css"  href="${pageContext.request.contextPath}/css/device/assetManagement.css" />

 <script type="text/javascript" src="${pageContext.request.contextPath}/blugin/jquery/jquery-1.10.2.js"></script>		

 <script type="text/javascript" src="${pageContext.request.contextPath}/blugin/hightCharts/highcharts.js"></script>	
 <script type="text/javascript" src="${pageContext.request.contextPath}/blugin/hightCharts/highcharts-more.js"></script>	
 <script type="text/javascript" src="${pageContext.request.contextPath}/blugin/hightCharts/exporting.js"></script>		


<%--  <link href="${pageContext.request.contextPath}/blugin/ext-3.2.1/resources/css/ext-all.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/blugin/ext-3.2.1/adapter/ext/ext-base-debug.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/blugin/ext-3.2.1/ext-all-debug.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/blugin/ext-3.2.1/ext-lang-zh_CN.js"></script>
  --%>
 <link href="${pageContext.request.contextPath}/blugin/extjs/resources/css/ext-all.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/blugin/extjs/adapter/ext/ext-base-debug.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/blugin/extjs/ext-all-debug.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/blugin/extjs/ext-lang-zh_CN.js"></script>

  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/blugin/extjs/examples/ux/gridfilters/css/GridFilters.css" />
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/blugin/extjs/examples/ux/gridfilters/css/RangeMenu.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/blugin/extjs/examples/ux/gridfilters/menu/RangeMenu.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/blugin/extjs/examples/ux/gridfilters/menu/ListMenu.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/blugin/extjs/examples/ux/gridfilters/GridFilters.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/blugin/extjs/examples/ux/gridfilters/filter/Filter.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/blugin/extjs/examples/ux/gridfilters/filter/StringFilter.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/blugin/extjs/examples/ux/gridfilters/filter/DateFilter.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/blugin/extjs/examples/ux/gridfilters/filter/ListFilter.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/blugin/extjs/examples/ux/gridfilters/filter/NumericFilter.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/blugin/extjs/examples/ux/gridfilters/filter/BooleanFilter.js"></script>













<script src="${pageContext.request.contextPath}/js/device/assetManagement/oil_record.js"></script>
<script src="${pageContext.request.contextPath}/js/device/assetManagement/oil_temperature_record.js"></script>
<script src="${pageContext.request.contextPath}/js/device/assetManagement/fault_conditions.js"></script>
<script src="${pageContext.request.contextPath}/js/device/assetManagement/three_phase_unbalance_rate.js"></script>
<script src="${pageContext.request.contextPath}/js/device/assetManagement/load_condition_line.js"></script>
<script src="${pageContext.request.contextPath}/js/device/assetManagement/cost_column.js"></script>	

 <script src="${pageContext.request.contextPath}/js/device/assetManagement/discard_information_recording.js"></script>
	


</head>
<body>
<div class="top">
	<div class="logo">
		<span><img src="${pageContext.request.contextPath}/image/logo.png" /></span>
		配电网综合管理平台
	</div>
	<div class="siteinfo">
		<div class="siteinfo-text siteinfobg1"><a href="#">图标显示</a></div>
		<div class="siteinfo-text siteinfobg2"><a href="#">地图信息</a></div>
	</div>
</div>
<div style="clear:both"></div>
<div class="wrap ">
	
	
	<div class="main">
		
			
		<ul class="breadcrumb breadcrumb-main2">
					<li><a href="${pageContext.request.contextPath}">首页</a> 》</li>
					<li><a href="javascript:history.go(-2);">资产</a> 》</li>
					<li><a href="javascript:history.go(-1);">变压器列表</a> 》</li>
					<li class="text-info">变压器详情</li>
		</ul>
		
		
			<!-- img src="${pageContext.request.contextPath}/image/5.png" /-->
			

		
		
		
		<div class="con nav_bg">

		<div class="module_top ">
			
			<div class="module_small_con module_small_con_img">
			<img src="${pageContext.request.contextPath}/image/b1.png"  />
			
			<!-- img src="${pageContext.request.contextPath}/image/3.png" /-->
			
			
			</div>
		</div>
		  <div class="module_big">
				
				<div class="module_big_con">
						<div id="" style="min-width: 309px; height: 160px; max-width: 399px; margin: 0 auto">
						
					  
					  
					   <!--  <div id="green-button"  style="float:left"> <a href="#" class="green-button pcb"> <span>待转移资产</span> </a> </div>
					     <div id="green-button"  style="float:left"> <a href="#" class="green-button pcb"> <span>用户资产</span> </a> </div> -->
						<div style="float:left"> 
							<table  style="float:left;width:480px">
								<tr>
									<td width="180px">  <div style="float:left;margin: 0 0 0 20px;"> <font size="5" color="black">冠联通HR7300</font></div></td>
									<td><div class="progress-bar2 green2 glow glow_margin"><span style="width: 70%;"></span><div style="float:left"></div> </div></td>
									<td>净值：3000元</td>
								</tr>
							</table>
						
						   </div> 
									
				
					     
					    
					    <hr style="margin: 0 0 0 20px;"  width="633" />
					    <div class="t_list">
					        <dl >
					  <dt>资产编号：</dt>
					  <dd>BYQ-201409</dd>      
					  <dt>型号：</dt>
					  <dd>TND/SVC-0.5K</dd>
					  <dt>是否用户资产：</dt>
					  <dd><font color="red">是</font></dd>
					  <dt>是否待转移资产：</dt>
					   <dd><font color="green">否</font></dd>
					  <dt>单价(元)：</dt>
					  <dd>200000</dd>
					  <dt>来源项目：</dt>
					  <dd>顺德项目及时通</dd>
					  <dt>容量(kVA)：</dt>
					  <dd>512</dd>
					  <dt>出厂日期：</dt>
					  <dd>2010-08-05</dd>   
					   <dt>安装日期：</dt>
					  <dd>2010-10-23</dd>  
					   <dt>安装人：</dt>
					  <dd>李四</dd>  
					   <dt>所在杆塔：</dt>
					  <dd>A230</dd>  
					   <dt>所在路线：</dt>
					  <dd>西山线干线</dd>  
					  
					   <dt>厂家：</dt>
					  <dd>顺特电气有限公司</dd>  
					   <dt>安装单位：</dt>
					  <dd id="dd2">深圳乐容电子股份有限公司</dd>
					   <dt>安装地址：</dt>
					  <dd id="dd2">大良变电站/733 西山线//G3011-1168 东城花园配电房/</dd>    
					 </dl>
					    </div>
						</div>
			
				
				</div>
			</div>

		</div>
		
		<div class="con2">
			
			<div class="module_small2">
			<h4>油量记录</h4>
			<div class="module_small_con2">
			<div id="oil_record" style="min-width: 401px; height: 223px; max-width: 401px; margin: 0 auto"></div>
			
			
			<!-- img src="${pageContext.request.contextPath}/image/3.png" /-->
			
			
			</div>
		</div>
		
		<div class="module_big2">
				<h4>油温记录</h4>
				<div class="module_big_con2">
				
			
					<div id="oil_temperature_record"  style="min-width: 309px; height: 223px; max-width: 389px; margin: 0 auto"></div>
				<!-- img src="${pageContext.request.contextPath}/image/4.png" /-->
				</div>
			</div>
		
  </div>
  
  
  <div class="con2">
			
			<div class="module_small2">
			<h4>三相不平衡率</h4>
			<div class="module_small_con2">
			<div id="three_phase_unbalance_rate" style="min-width: 309px; height: 223px; max-width: 389px; margin: 0 auto"></div>
			
			<!-- img src="${pageContext.request.contextPath}/image/5.png" /-->
			
			
			</div>
		</div>
		
		<div class="module_big2">
				<h4>故障情况</h4>
				
				<!--代码添加开始 -->
				<div class="module_big_con2">
					
				 <div id="fault_conditions" style="min-width: 309px; height: 223px; max-width: 389px; margin: 0 auto"></div>
			
				</div>
				<!--代码添加结束 -->
				
				
				
				
				
				
	</div>
		
  </div>
   <div class="con2">
			
			<div class="module_small2">
			<h4>负载情况</h4>
			<div class="module_small_con2">
			<div id="load_condition_line" style="min-width: 309px; height: 223px; max-width: 389px; margin: 0 auto"></div>
			
			<!-- img src="${pageContext.request.contextPath}/image/5.png" /-->
			
			
			</div>
		</div>
		
		<div class="module_big2">
				<h4>成本</h4>
				
				<!--代码添加开始 -->
				<div class="module_big_con2">
					
				 <div id="cost_column" style="min-width: 309px; height: 223px; max-width: 389px; margin: 0 auto"></div>
			
				</div>
				<!--代码添加结束 -->
				
				
				
				
				
				
	</div>
		
  </div>
  <div class="con3">
			
			<div class="">
			<h4>异动信息</h4>
			<div class="wrap">
			<div id="discard_information_recording" style="min-width: 900px; height: 174px; max-width:900px; margin: 0 auto"></div>
			
			<!-- img src="${pageContext.request.contextPath}/image/5.png" /-->
			
			
			</div>
		</div>
	
		
  </div>
</div>



</div>
<div class="page">
顺德供电局版权所有
</div>


</body>
</html>

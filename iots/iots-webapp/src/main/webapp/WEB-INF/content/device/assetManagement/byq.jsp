<!DOCTYPE HTML>
<html>
<head>
		
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

	
		
<script>
					 $(function(){
						
						$('#suo_infor').find("a").click(
							function(e){
								$('#suo_infor').find("a").css({
									'display':'block',
								"fontWeight":"normal",
								'font':'normal 13px/27px "宋体"',
								'color':'#797979',
								'text-decoration':'none',
								'border-left':'solid 0px #bfbfbf',
								'text-align':'center',
	                             'width':'80px',
								'border-right':'solid 0px #bfbfbf',
								'float':'left',
								
								'height':'29px',
								"backgroundPosition":"-80px -90px" 
								})//取消所有的
					
								
								$(this).css({
								'display':'block',
								'cursor': 'pointer',
								'text-align':'center',
	                             'width':'80px',
								'color':'#000',
								'text-decoration':'none',
								'font':'bold 13px/27px "宋体"',
								'height':'30px',
								 "backgroundPosition":"-80px -90px"
							 	
					
								})//设置当前的
								
								var src = e.target;
							}
						)
						
					})
			</script>
		
	<script>
					 $(function(){
						
						$('#bdz_infor').find("a").click(
							function(e){
								$('#bdz_infor').find("a").css({
									'display':'block',
								"fontWeight":"normal",
								'font':'normal 13px/27px "宋体"',
								'color':'#797979',
								'text-decoration':'none',
								'border-left':'solid 0px #bfbfbf',
								'text-align':'center',
	                             'width':'80px',
								'border-right':'solid 0px #bfbfbf',
								'float':'left',
								
								'height':'29px',
								"backgroundPosition":"-80px -90px" 
								})//取消所有的
					
								
								$(this).css({
								'display':'block',
								'cursor': 'pointer',
								'text-align':'center',
	                             'width':'80px',
								'color':'#000',
								'text-decoration':'none',
								'font':'bold 13px/27px "宋体"',
								'height':'30px',
								 "backgroundPosition":"-80px -90px"
							 	
					
								})//设置当前的
								
								var src = e.target;
							}
						)
						
					})
			</script>	
	
	 <script>
					 $(function(){
						
						$('#tab2_lab').find("a").click(
							function(e){
								$('#tab2_lab').find("a").css({
									'float':'left',
									'width':'103px',
									'height':'30px',
									'margin-right':'15px',
									'text-align':'center',
									'font':'16px/30px "微软雅黑"',
									'display':'block',
									'color':'#797979',
									'background':'url(../../image/tab-bg.png) repeat-x'			

								})//取消所有的
					
								
								$(this).css({
									'color':'#33a0e9',
									'text-decoration':'none',
									'background':'url(../../image/tab_hover.png) repeat-x 0 bottom'
								})//设置当前的
								
								var src = e.target;
							}
						)
						
					})
			</script>
	
		
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
		<li class="text-info">资产</li>
	</ul>	
</div>
		
<div class="wrap nav_bg">
	<div class="sub">
	<div class="sub_con">
			
			<div class="nav_img nav_byq">
				<a href="#" class="click">变压器</a>
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
		<div class="suo_tab">

<script type="text/javascript">
  function getBYQJson(id){
	  var gbNum=0;
      var zbNum=0;
      var gbProp=0;
      var zbProp=0;
      var gbCapacity=0;
      var zbCapacity=0;
      var gbCapacityProp=0;
      var runNames=['0~5年','5~10年','10~15年','15年以上'];
	  var runNums=[0,0,0,0];
	  var cjNames=['森智嘉', '三和弈佳', '深圳嘉奕', '沈阳奕辰', '广州仟奕', '深圳奕马成'];
	  var dvCJNums=[0, 0, 0, 0, 0, 0];
	  var gbModelProp=0;
	  var names=['30%以下','30%-50%','50%-80%','80%-100%'];
	  var dvNums=[0,0,0,0];
	  
	  
	  if(id==1){
		  //类型数量占比
	      gbNum=340;
	      zbNum=110;
	      gbProp=((gbNum/(gbNum+zbNum)).toFixed(4))*100;
	      zbProp=(100-gbProp).toFixed(2);
	      showNumProp (gbProp,zbProp);
		  document.getElementById('gbNumDiv').innerHTML=gbNum+'台';
		  document.getElementById('zbNumDiv').innerHTML=zbNum+'台';
		  document.getElementById('byqNumDiv').innerHTML=(gbNum+zbNum)+'台';
		  //类型容量占比
	      gbCapacity=4030;
	      zbCapacity=3520;
	      gbCapacityProp=((gbCapacity/(gbCapacity+zbCapacity)).toFixed(4))*100;
	      showCapacityProp (gbCapacityProp);
		  document.getElementById('gbCapacityDiv').innerHTML=gbCapacity+'kVA';
		  document.getElementById('zbCapacityDiv').innerHTML=zbCapacity+'kVA';
		  document.getElementById('byqCapacityDiv').innerHTML=(gbCapacity+zbCapacity)+'kVA';
		  //运行年限
		  runNums=[24,56,7,3];
	      showRunYear (runNames,runNums);
		  //厂家
		  cjNames=['广州科琳', '广东海鸿', '明珠电气', '顺特电气', '南方华力通', '广州仟奕'];
		  dvCJNums=[36, 42, 13, 27, 26, 45];
		  showManufacturer (cjNames, dvCJNums);
	      //型号
		  gbModelProp=28.5;
		  showModelProp (gbModelProp);
	      //负载情况
		  dvNums=[21, 59, 49, 29];
		  showLoad(names, dvNums);
	  }else if(id==2){
		  //类型数量占比
	      gbNum=240;
	      zbNum=310;
	      gbProp=((gbNum/(gbNum+zbNum)).toFixed(4))*100;
	      zbProp=(100-gbProp).toFixed(2);
	      showNumProp (gbProp,zbProp);
		  document.getElementById('gbNumDiv').innerHTML=gbNum+'台';
		  document.getElementById('zbNumDiv').innerHTML=zbNum+'台';
		  document.getElementById('byqNumDiv').innerHTML=(gbNum+zbNum)+'台';
		  //类型容量占比
		  gbCapacity=6530;
	      zbCapacity=3520;
	      gbCapacityProp=((gbCapacity/(gbCapacity+zbCapacity)).toFixed(4))*100;
	      showCapacityProp (gbCapacityProp);
		  document.getElementById('gbCapacityDiv').innerHTML=gbCapacity+'kVA';
		  document.getElementById('zbCapacityDiv').innerHTML=zbCapacity+'kVA';
		  document.getElementById('byqCapacityDiv').innerHTML=(gbCapacity+zbCapacity)+'kVA';
		  //运行年限
		  runNums=[17,24,14,12];
	      showRunYear (runNames,runNums);
		  //厂家
		  cjNames=['广州科琳', '广东海鸿', '明珠电气', '沈阳奕辰', '深圳嘉奕', '明珠电气'];
		  dvCJNums=[23, 35, 15, 41, 21, 12];
		  showManufacturer (cjNames, dvCJNums);
	      //型号
		  gbModelProp=44.28;
		  showModelProp (gbModelProp);
	      //负载情况
		  dvNums=[25, 33, 12, 17];
		  showLoad(names, dvNums);
	  }
		
   }
</script>
		<div id='suo_infor' class="suo_tab_middle"><h3>供电所</h3>
			<span><a href="###"  value="condition_currentDay" class="click" onclick="getBYQJson(1);" >大良所</a></span>
			<span><a href="###"  value="condition_currentWeek"  onclick="getBYQJson(2);" >勒流所</a></span>
			<span><a href="###"  value="condition_currentMonth" >乐从所</a></span>
			<span><a href="###"  value="condition_currentThreeMonth" >陈村所</a></span>
			<span><a href="###"  value="condition_currentYear" >北滘所</a></span>
			<span><a href="###"  value="condition_currentYear" >龙江所</a></span>
			<span><a href="###"  value="condition_currentYear" >杏坛所</a></span>
			<span><a href="###"  value="condition_currentYear" >容桂所</a></span>
			<span><a href="###"  value="condition_currentYear" >均安所</a></span>
			<span><a href="###"  value="condition_currentYear" >伦滘所</a></span>
		</div>
		
	</div>
		
		<div class="bdz_tab">
		
		<div id='bdz_infor' class="bdz_tab_middle"><div><h3>变电站</h3></div>
			<span><a href="###"  value="condition_currentDay" href="#"  class="click">大良站</a></span>
			<span><a href="###"  value="condition_currentWeek"  href="#">富安站</a></span>
		</div>
		


	</div>
		
		<div class="sub_con">
			<h3>线路</h3>
		</div>
	
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
	
	<div class="main">
		<!-- <div class="tab">
			<a href="#" >专用变压器</a>
			<a href="#">公用奕压器</a>
			<a href="#"  class="click">全部变压器</a>
		</div> -->
		
		<div class="tab2">
	
		<div id='tab2_lab' class="tab2_middle">
		<a href="###"  value="condition_currentDay" href="#" >专用变压器</a>
				<a href="###"  value="condition_currentWeek"  href="#">公用奕压器</a>
			<a href="###" value="condition_all" href="#"  class="click">全部变压器</a>
		</div>
		
	</div>
		<div class="con">
			<!-- div class="module_small">
			
				
			<img src="${pageContext.request.contextPath}/image/1.png" />
			
			</div-->
			
			
			<!--数量占比代码添加开始 -->

			<div class="module_small">

				<div class="module_small_con">

					<h4><a href="#">数量占比</a></h4>

					<!-- span><img src="${pageContext.request.contextPath}/image/1.png" /></span-->
					<span><div id="capacity_ratio_top" style="min-width: 210px; width:10%; max-width: 210px; height: 36px; margin: 0 0 0 0 auto"></div>
					<div style="float:left; top:28px;  position: relative;width:100px;">
					<span style="float:left; padding-left:220px;width:120px;"><div  style="float:left; hight:1px;width:14px;background:#fa9326;border:1px solid #fff">&nbsp;</div>公变:<font id="gbNum" class="blue"><div id="gbNumDiv" style="display:inline;">200台</div></font></span>
					<span style="float:left;padding-left:220px; width:120px;"><div  style="float:left; hight:1px;width:14px;background:#88da20;border:1px solid #fff">&nbsp;</div>专变:<font id="zbNum" class="blue"><div id="zbNumDiv" style="display:inline;">300台</div></font></span>
					<span style="float:left;padding-left:220px; width:120px;">共:<font id="byqNumDiv" class="blue"><div id="byqNumDiv" style="display:inline;">500台</div></font></span>
					</div>
					</span>

					

				</div>

				

				

			  <div class="module_small_con">

					<h4><a href="#">容量占比</a></h4>

					<span><div id="container" style="float:left;min-width: 210px;  max-width: 210px; height: 160px; margin: -10px 0 0 10px auto;"></div>
					<div style="float:left; top:78px;  position: relative;width:100px;">
					<span style="float:left;padding-left:12px;width:120px;"><div  style="float:left; hight:1px;width:14px;background:#fa9326;border:1px solid #fff">&nbsp;</div>公变:<font class="blue"><div id="gbCapacityDiv" style="display:inline;">7000kVA</div></font></span>
					<span style="float:left;padding-left:12px;width:120px;"><div  style="float:left; hight:1px;width:14px;background:#88da20;border:1px solid #fff">&nbsp;</div>专变:<font class="blue"><div id="zbCapacityDiv" style="display:inline;">3000kVA</div></font></span>
					<span style="float:left;padding-left:12px;width:120px;">共:<font class="blue"><div id="byqCapacityDiv" style="display:inline;">10000kVA</div></font></span>
					
					
					</div>
						
					</span>

	

				</div>

				

			</div>

			

			<!--数量占比代码添加结束 -->
			
		  <div class="module_big">
				<h4>额定容量</h4>
				<div class="module_big_con">
				<!-- <div id="rated_capacity" style="min-width: 409px; height: 254px; max-width: 599px; margin: 0 auto"></div>
			 -->
				<img src="${pageContext.request.contextPath}/image/2.png"  />
				</div>
			</div>

		</div>
		
		<div class="con">
			
			<div class="module_small">
			<h4>运行年限</h4>
			<div class="module_small_con">
			<div id="operating_period" style="min-width: 309px; height: 223px; max-width: 399px; margin: 0 auto"></div>
			
			
			<!-- img src="${pageContext.request.contextPath}/image/3.png" /-->
			
			
			</div>
		</div>
		
		<div class="module_big">
				<h4>厂家</h4>
				<div class="module_big_con">
				
			
					<div class="cj"><div id="vender_column" style="min-width: 179px; height: 223px; max-width: 199px; margin: 0 auto"></div></div>
					<div class="cj2"><div id="vender_pie" style="min-width: 179px; height: 223px; max-width: 199px; margin: 0 auto"></div></div>
				<!-- img src="${pageContext.request.contextPath}/image/4.png" /-->
				</div>
			</div>
		
  </div>
  
  
  <div class="con">
			
			<div class="module_small">
			<h4>负载情况</h4>
			<div class="module_small_con">
			<div id="load_condition" style="min-width: 309px; height: 223px; max-width: 399px; margin: 0 auto"></div>
			
			<!-- img src="${pageContext.request.contextPath}/image/5.png" /-->
			
			
			</div>
		</div>
		
		<div class="module_big">
				<h4>净值占比</h4>
				
				<!--代码添加开始 -->
				<div class="module_big_con">
					<div class="jz"><img src="${pageContext.request.contextPath}/image/jz.png" /></div>
				  <div class="jz2">
						<h5>占比</h5>
						<span>
						<div>
							
							
							  <table  width="340" height="152" border="0" cellpadding="0" cellspacing="0">    
							    <tr>        
							    	<td>30%以下占比200台</td>        
							    	<td>
								    	<div class="progress-bar green glow">
								    	<span style="width: 55%">55%</span>
										</div>
									</td>      
							    </tr>     
								     <tr>        
									     <td>30%以下占比100台   </td>        
									     <td>
										     <div class="progress-bar green glow">
										
										   		<span style="width: 85%">85%</span>
										 
											</div>
										 </td>      
								     </tr>      
								     <tr>        
								     	<td>30%以下占比204台</td>        
									     <td>
										     <div class="progress-bar green glow">
										    	<span style="width: 25%">25%</span>
											</div>
										</td>      
								     </tr>      
							    
							     </table>
							

						
						
						</span>
					</div>
				</div>
				<!--代码添加结束 -->
				
				
				
				
				
				
	</div>
		
  </div>
  
</div>



</div>

<div style="clear:both;"></div>
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

<!DOCTYPE html>
<html>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/device/assetManagement.css" />

<link href="${pageContext.request.contextPath}/css/device/library/assets/css/bootstrap.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/css/device/library/assets/css/bootstrap-responsive.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/device/library/css/styles.css" />
		
		
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/blugin/jquery/jquery-ui.css" />
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/blugin/jquery/css/chosen/chosen.css" />
		
 <script type="text/javascript" src="${pageContext.request.contextPath}/blugin/jquery/jquery-1.10.2.js"></script>		
	<script src="${pageContext.request.contextPath}/blugin/jquery/jquery-ui-1.10.4.custom.js"></script>	
	<script src="${pageContext.request.contextPath}/blugin/jquery/chosen.jquery.js"></script>		
<script>
	$(function() {
	

		
		$( ".button" ).button();
		
	});
	</script>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<title></title>
	<meta content="" name="keywords" />
	
	
	<style type="text/css">
body {
	padding:0px;
 margin:0px;
 background-color:#404040;
 
}


</style>
	
	
	
</head>
<body >

<div >
  <%@ include file="top.jsp"%>

 
		<!-- end header -->		
		
		<div id="left_layout">
			<!-- main content -->
			<div id="main_content" class="container-fluid">
			
			
			
				
				<!-- post wrapper -->				
				<div class="row-fluid">
				
				<%@ include file="left.jsp"%>
					 <div id="frameBox">
		<iframe id="assetManagement" name="assetManagement" frameborder="0" scrolling="no"
		src="${pageContext.request.contextPath}/spring/device/toAssetManagementCenter" 
		allowTransparency="false" style="width:100%;">
		</iframe>
	</div>
					<!-- div class="span8">
					
					
						
						
						
						
						
					</div-->
				</div>
				<!-- ./ post wrapper -->
			</div>
			<!-- end main content -->
			
		
			
		
			
		
		</div>

    
    
    
    
    
    
    

</div>
<script type="text/javascript">
	var newHeight = document.body.scrollHeight + 20 + "px";        
    document.getElementById("frameBox").style.height = newHeight;
    document.getElementById("assetManagement").style.height = newHeight;
</script>

</body>
 </html>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>收支查询界面</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta http-equiv="content-language" content="zh-CN" />
<link rel="stylesheet" href="${ctx }/plugin/jquerymobile/jquery.mobile-1.4.5.min.css" />
<link rel="stylesheet" href="${ctx }/plugin/mobiscroll/css/mobiscroll.custom-2.5.0.min.css" />
<script src="${ctx }/plugin/jquery/jquery-2.1.3.min.js"></script>
<script src="${ctx }/plugin/jquery/jquery-utils.js"></script>
<script src="${ctx }/plugin/jquerymobile/jquery.mobile-1.4.5.min.js"></script>
<script src="${ctx }/plugin/mobiscroll/js/mobiscroll.custom-2.5.0.min.js"></script>
<script src="${ctx }/plugin/My97DatePicker/WdatePicker.js"></script>
<style type="text/css">
</style>
<script type="text/javascript">
	window.onload = function() {
		try{
			hideLoader();
		}catch(e){
		}
	}
	
	 $(function(){
		var startTimeOpt = {
	        preset : 'date', 
	        theme : 'default', 
	        display : 'modal',  
	        mode : 'mixed', 
	        dateFormat : 'yy-mm-dd', 
	        minDate: new Date(2000, 1, 1),
            //maxDate: new Date(), 
	        setText : '确定', 
	        cancelText : '取消',
	        dateOrder : 'yymmdd', 
	        dayText : '日', monthText: '月', yearText: '年',
	        
	    };
	    
	    $("#startTime").mobiscroll(startTimeOpt);
	    $("#endTime").mobiscroll(startTimeOpt);
	});
	 
	function submit() {
		var startTime = $('#startTime').val();
		if(!startTime) {
			showAlertDialog('开始时间不能空');
			return;
		}
		startTime = startTime + ' 00:00:00';
		
		var endTime = $('#endTime').val();
		if(!endTime) {
			showAlertDialog('结束时间不能空');
			return;
		}
		endTime = endTime + ' 00:00:00';
		
		if(Date.parse(startTime) > Date.parse(endTime) || Date.parse(startTime) == Date.parse(endTime)) {
			showAlertDialog('结束时间必须大于开始时间');
			return;
		}
		
		showLoader('正在查询，请稍后...');
		$('#shouzhiQueryForm').submit();
	}
</script>
</head>
<body>
	<div data-role="page" data-ajax="false">

		<div data-role="header">
			<a href="${ctx }/spring/mobile/shouzhi" data-icon="back" data-ajax="false" onclick="showLoader()">返回</a>
			<h1>收支情况查询</h1>
		</div>

		<div data-role="content">

			<form id="shouzhiQueryForm" action="${ctx }/spring/mobile/shouzhi" method="get" data-ajax="false">
                <ul data-role="listview" data-inset="true">
                    <li data-role="list-divider">查询条件</li>
                    <li>
						<div data-role="fieldcontain">
							<label for="name">开始时间:</label><input id="startTime" name="beginTime" type="text" data-role="datebox" placeholder="请选择">
						</div>
	                </li>
	                
	                <li>
						<div data-role="fieldcontain">
							<label for="name">结束时间:</label> <input id="endTime" name="endTime" type="text" data-role="datebox" placeholder="请选择">
						</div>
	                </li>
	                
	                <li>
						<div data-role="fieldcontain">
							<label for="name">项目:</label> <select placeholder="请选择" name="projectId">
							<c:forEach items="${projectInfos }" var="project" varStatus="varIndex">
								<option value="${project.projectId }">${project.projectName }</option>
							</c:forEach>
							</select>
						</div>
					</li>
                </ul>

			</form>

	        <div data-role="fieldcontain">
				<input type="button" value="查询" onclick="submit()">
			</div>
		</div>

	</div>
</body>
</html>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../common/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>感知端信息</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta http-equiv="content-language" content="zh-CN" />
<link rel="stylesheet" href="${ctx }/plugin/jquerymobile/jquery.mobile-1.4.5.min.css" />
<script src="${ctx }/plugin/jquery/jquery-2.1.3.min.js"></script>
<script src="${ctx }/plugin/jquery/jquery-utils.js"></script>
<script src="${ctx }/plugin/jquerymobile/jquery.mobile-1.4.5.min.js"></script>
<style type="text/css">
.liStatus {
	line-height: 20px;
}

.liStatusSelect {
	line-height: 60px;
}

.liLable {
	float: left;
	margin-right: 10;
	color: #866E54;
}

.liLableSelect {
	float: left;
	color: #866E54;
	width: 65%;
}

.liStatusValue {
	float: right;
	color: #469E6F;
}
.liStatusValue-warning {
	float: right;
	color: red;
}
</style>
</head>
<body>

<div data-role="page">
	<div data-role="header" data-position="fixed">
		<a href="${ctx }/spring/appMgrPerceptionController/appPerceptionList" data-icon="back" data-ajax="false">返回</a>
		<h1>感知端信息</h1>
	</div>

	<div data-role="content">
		<ul data-role="listview" id="perceptionFunctionList" data-inset="true">
			<li data-role="list-divider">基本信息</li>
			<li class="liStatus">
				<span class="liLable">设备名称</span>
				<span class="liStatusValue">${perceptionOpResponse.perceptionName }(${ perceptionOpResponse.perceptionAddr})</span>
			</li>
			<li class="liStatus">
				<span class="liLable">在线状态</span>
				<span id="onlineStatus" class="liStatusValue">${perceptionOpResponse.onlineStatusDesc }</span>
			</li>
			<li class="liStatus">
				<span class="liLable">最后工作时间</span>
				<span id="lastCommTime" class="liStatusValue">${perceptionOpResponse.lastCommTime }</span>
			</li>
			
			<li data-role="list-divider">告警信息</li>
			<c:forEach items="${perceptionOpResponse.warningParamInfos }" var="warningParamInfo">
			    <c:choose>
			    	<c:when test="${warningParamInfo.paramId == 183 }">
						<li class="liStatus">
							<a href="${ctx }/spring/appMgrPerceptionController/getRuntimeLogByParamId?perceptionId=${perceptionOpResponse.perceptionId }&perceptionName=${perceptionOpResponse.perceptionName }&paramId=${warningParamInfo.paramId}&paramDesc=${warningParamInfo.paramDesc}" data-ajax="false">
								<span class="liLable">${warningParamInfo.paramDesc }</span>
								<span id="${warningParamInfo.paramId }" class="liStatusValue">${warningParamInfo.paramValueDesc }</span>
							</a>
						</li>
			    	</c:when>
			    	<c:otherwise>
			    		<li class="liStatus">
							<span class="liLable">${warningParamInfo.paramDesc }</span><span id="${warningParamInfo.paramId }" class="liStatusValue">${warningParamInfo.paramValueDesc }</span>
						</li>
			    	</c:otherwise>
			    </c:choose>
			</c:forEach>
			
			<li data-role="list-divider">操作信息</li>
			<c:forEach items="${perceptionOpResponse.ctrlParamInfos }" var="ctrlParamInfo">
				<li class="liStatusSelect">
					<span class="liLableSelect">${ctrlParamInfo.paramDesc }</span> 
					<select class="liStatusValue" onchange="remoteCtrl($(this))" id="${ctrlParamInfo.paramId }">
					    <c:forEach var="item" items="${ctrlParamInfo.comboboxData.root }">
	                           <c:choose>
	                           	<c:when test="${ctrlParamInfo.paramValue eq item.value }">
	                           		<option value="${item.value }" selected="true">${item.text }</option>
	                           	</c:when>
	                           	<c:otherwise>
									<option value="${item.value }">${item.text }</option>
	                           	</c:otherwise>
	                           </c:choose>							
					    </c:forEach>
					</select>
				</li>
			</c:forEach>
			
		</ul>

	</div>
    
    <script type="text/javascript">
        function query() {
    		$.ajax({
    			url : '${ctx }/spring/appMgrPerceptionController/appPerceptionFunctionListAjax',
    			type : 'GET',
    			dataType : 'JSON',
    			data : {
    				perceptionId : ${perceptionOpResponse.perceptionId},
    				isInit : false
    			},
    			cache : false,
    			success : function(result, textStatus) {
    				if(result.success) {
	    				$('#onlineStatus').text(result.onlineStatusDesc);
	    				$('#lastCommTime').text(result.lastCommTime);
	    				
	    			    var warningParamInfos = result.warningParamInfos;
	    		        for (var i = 0; i < warningParamInfos.length; i++) {
	    		        	var param = warningParamInfos[i];
	    		        	$('#' + param.paramId).text(param.paramValueDesc);
	    		        }
	    		        
	    		        var ctrlParamInfos = result.ctrlParamInfos;
	    		        for(var i = 0; i < ctrlParamInfos.length; i++) {
	    		        	var param = ctrlParamInfos[i];
	    		        	$('#' + param.paramId).val(param.paramValue).selectmenu("refresh");
	    		        }
	    		        
    				}
    			}
    		});
        }
        
    	window.onload = function() {
    		var time = 3 * 1000;
    		window.setTimeout(time);
    		window.setInterval('query()', time);
    	}
    	
    	function remoteCtrl(select) {
    		var id = select.attr('id');
    		var value = select.val();
    	
    		showLoader('正在请求，请稍后...');
    		
    		$.ajax({
    			url : '${ctx}/spring/perception/remoteCtrlPerception',
    			type : 'POST',
    			dataType : 'JSON',
    			data : {
    				perceptionId : ${perceptionOpResponse.perceptionId},
    				paramId : id,
    				paramValue : value,
    				perceptionTypeId : ${perceptionOpResponse.perceptionTypeId}
    			},
    			cache : false,
    			success : function(result, textStatus) {
    				hideLoader();
                    
    				if(result.success == false) {
    					showAlertDialog(result.msg);
    				}
    				
    			}
    		});
    	}
    </script>
</body>
</html>


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
				<li data-role="list-divider">告警信息</li>
				<li class="liStatus"><span class="liLable">设备名称</span><span class="liStatusValue">${perceptionOpResponse.perceptionName }(${ perceptionOpResponse.perceptionAddr})</span></li>
				<li class="liStatus"><span class="liLable">在线状态</span><span id="onlineStatus" class="liStatusValue">${perceptionOpResponse.onlineStatus }</span></li>
				<li class="liStatus"><span class="liLable">最后工作时间</span><span id="lastCommTime" class="liStatusValue"><fmt:formatDate value="${perceptionOpResponse.lastCommTime }" pattern="yyyy-MM-dd hh:ss:mm" /></span></li>
				<li class="liStatus"><span class="liLable">红外状态</span><span id="infraredStatus" class="liStatusValue">${query2j2MachineResponse.infraredStatus }</span></li>
				<li class="liStatus"><span class="liLable">按键状态</span><span id="pressKeyStatus" class="liStatusValue">${query2j2MachineResponse.pressKeyStatus }</span></li>

				<li data-role="list-divider">操作选择</li>
				<li class="liStatusSelect">
					<span class="liLableSelect">电机1旋转</span> 
					<select class="liStatusValue" id="rotateStatus_machine1_combobox" onchange="remoteCtrl($(this))">
					    <c:forEach var="item" items="${rotateStatus_machine1_combobox.root }" varStatus="status">
                            <c:choose>
                            	<c:when test="${item.value eq query2j2MachineResponse.machine1RotateStatus}">
                            		<option value="${item.value }" selected="true">${item.text }</option>
                            	</c:when>
                            	<c:otherwise>
									<option value="${item.value }">${item.text }</option>
                            	</c:otherwise>
                            </c:choose>							
					    </c:forEach>
					</select>
				</li>
				
				<li class="liStatusSelect">
					<span class="liLableSelect">电机2旋转</span> 
					<select class="liStatusValue" id="rotateStatus_machine2_combobox" onchange="remoteCtrl($(this))">
					    <c:forEach var="item" items="${rotateStatus_machine2_combobox.root }" varStatus="status">
                            <c:choose>
                            	<c:when test="${item.value eq query2j2MachineResponse.machine2RotateStatus}">
                            		<option value="${item.value }" selected="true">${item.text }</option>
                            	</c:when>
                            	<c:otherwise>
									<option value="${item.value }">${item.text }</option>
                            	</c:otherwise>
                            </c:choose>							
					    </c:forEach>
					</select>
				</li>
				
				<li class="liStatusSelect">
					<span class="liLableSelect">I2C状态</span> 
					<select class="liStatusValue" id="i2cStatus_combobox" onchange="remoteCtrl($(this))">
					    <c:forEach var="item" items="${i2cStatus_combobox.root }" varStatus="status">
                            <c:choose>
                            	<c:when test="${item.value eq query2j2MachineResponse.i2cStatus}">
                            		<option value="${item.value }" selected="true">${item.text }</option>
                            	</c:when>
                            	<c:otherwise>
									<option value="${item.value }">${item.text }</option>
                            	</c:otherwise>
                            </c:choose>							
					    </c:forEach>
					</select>
				</li>
			</ul>

			<ul data-role="listview" data-inset="true">
				<li><a href="${ctx }/spring/appMgrPerceptionController/appPerceptionLog?perceptionId=${perceptionOpResponse.perceptionId}" data-ajax="false">警告历史信息查看</a></li>
			</ul>
		</div>
    
    <script type="text/javascript">
        function query() {
    		$.ajax({
    			url : '${ctx }/spring/appMgrPerceptionController/query2j2Machine',
    			type : 'POST',
    			dataType : 'JSON',
    			data : {
    				perceptionId : ${perceptionOpResponse.perceptionId}
    			},
    			cache : false,
    			success : function(result, textStatus) {
    				if(result.success) {
	    				$('#onlineStatus').text(result.onlineStatus);
	    				$('#lastCommTime').text(result.createDatetime);
	    				$('#infraredStatus').text(result.infraredStatus);
	    				$('#pressKeyStatus').text(result.pressKeyStatus);
	    				
	    				$('#rotateStatus_machine1_combobox').val(result.machine1RotateStatus).selectmenu("refresh");
	    				$('#rotateStatus_machine2_combobox').val(result.machine2RotateStatus).selectmenu("refresh");
	    				$('#i2cStatus_combobox').val(result.i2cStatus).selectmenu("refresh");
	    				
	    				if(result.needWarning == true) {
	    					try {
			    				window.iots.playMp3('${perceptionOpResponse.perceptionId}', 'warning_msg_receive');
	    					} catch(e) {
	    					}
	    				}
    				}
    			}
    		});
        }
        
    	window.onload = function() {
    		window.setInterval('query()', 3 * 1000);
    	}
    	
    	function remoteCtrl(select) {
    		var id = select.attr('id');
    		var value = select.val();
    		
    		var opt = null;
    		var msg = null;
    		
    		switch(id) {
    		    //电机1旋转状态
    			case 'rotateStatus_machine1_combobox':
    				if (value == '1') {
						opt = 3;// 电机1正转控制
						msg = '正在对电机1进行正转控制...';
					} else if (value == '2') {
						opt = 4;// 电机1反转控制
						msg = '正在对电机1进行反转控制...';
					} else if (value == '3') {
						opt = 6;// 电机1断电控制
						msg = '正在对电机1进行停控制...';
					}
    				break;
    				
    			//电机2旋转状态
    			case 'rotateStatus_machine2_combobox':
    				if (value == '1') {
						opt = 9;// 电机2正转控制
						msg = '正在对电机2进行正转控制...';
					} else if (value == '2') {
						opt = 10;// 0x0A: 电机2反转控制
						msg = '正在对电机2进行反转控制...';
					} else if (value == '3') {
						opt = 12;// 0x0C：电机2断电控制
						msg = '正在对电机2进行断电控制...';
					}
    				break;
    				
    			//i2c状态
    			case 'i2cStatus_combobox':
    				if (value == '1') {
    					opt = 7;// I2C打开控制
    					msg = '正在对i2c进行打开控制...';
    				} else {
    					opt = 8;// I2C关闭控制
    					msg = '正在对i2c进行关闭控制...';
    				}
    				break;
    		}
    		
    		showLoader(msg);
    		
    		$.ajax({
    			url : '${ctx}/spring/perception/remoteCtrlPerception',
    			type : 'POST',
    			dataType : 'JSON',
    			data : {
    				perceptionId : ${perceptionOpResponse.perceptionId},
    				opt : opt
    			},
    			cache : false,
    			success : function(result, textStatus) {
    				hideLoader();
                    
    				if(result.success) {
    					showAlertDialog('操作成功!');
    				}
    				
    				else {
    					showAlertDialog(result.msg);
    				}
    			}
    		});
    	}
    </script>
</body>
</html>


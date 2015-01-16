<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>楼栋用水总额登记</title>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta http-equiv="content-language" content="zh-CN" />
<link rel="stylesheet" href="${ctx }/plugin/jquerymobile/jquery.mobile-1.4.5.min.css" />
<script src="${ctx }/plugin/jquery/jquery-2.1.3.min.js"></script>
<script src="${ctx }/plugin/jquery/jquery-utils.js"></script>
<script src="${ctx }/plugin/jquerymobile/jquery.mobile-1.4.5.min.js"></script>
<script type="text/javascript">
	window.onload = function() {
		try{
			hideLoader();
		}catch(e){
		}
	}
	
	function projectOnchange(projectId) {
		if('-1' == projectId) {
			removeYongshuiRecordUl();
			$('#ldSelect').empty();
			$('#ldSelect').selectmenu('refresh');
			return;
		}
		
		showLoader('查询楼栋信息，请稍后...');
		
		$.ajax({
			url : '${ctx}/spring/mobile/getLdList',
			type : 'POST',
			data : {
				projectId : projectId
			},
			dataType : 'JSON',
			cache : false,
			success : function(result, textStatus) {
				if (result.success) {
					$('#ldSelect').empty();
					$('#ldSelect').append('<option value="-1">请选择</option>');
					
					for(var i = 0; i < result.ldInfos.length; i++) {
						var info = result.ldInfos[i];
						$('#ldSelect').append('<option value="' + info.ldId + '">' + info.ldName + '</option>');
					}
					
					$('#ldSelect').selectmenu('refresh');
				} else {
					showAlertDialog(result.msg);
				}

				hideLoader();
			}
		});
	}
	
	function ldOnchange(ldId) {
		if('-1' == ldId) {
			removeYongshuiRecordUl();
			return;
		}
		
		showLoader('正在查询楼栋最近用水记录，请稍后...');
		
		$.ajax({
			url : '${ctx}/spring/mobile/getYongshuiRecord',
			type : 'POST',
			dataType : 'JSON',
			data : {
				projectId : $('#projectSelect').val(),
				ldId : ldId
			},
			cache : false,
			success : function(result, textStatus) {
				if(result.success) {
					refreshYongshuiRecord(result.yongshuiRecords);
				} else {
					showAlertDialog(result.msg);
				}
				
				hideLoader();
			}
		});
	}
	
	//刷新用水记录
	function refreshYongshuiRecord(yongshuiRecords) {
		var contentDiv = $('div[data-role="content"]');
		var html = '<li data-role="list-divider">楼栋用水历史信息</li>';
		
		for (var i = 0; i < yongshuiRecords.length; i++) {
			var record = yongshuiRecords[i];
			html = html + '<li>';
			html = html + '<span class="listItemLeft">' + record.month + '</span>';
			html = html + '<span class="listItemRight">￥' + record.consumedata + '</span>';
			html = html + '</li>';
		}
		
		html = '<ul id="yongshuiRecordUl" data-role="listview" data-inset="true">' + html + '</ul>';
		
		removeYongshuiRecordUl();
		
		contentDiv.append(html);
		
		$('div[data-role="content"] ul').listview(); 
	}
	
	//移除用水记录ul
	function removeYongshuiRecordUl() {
		if($('#yongshuiRecordUl').length > 0) {
			$('#yongshuiRecordUl').remove();
		}
	}
	
	function submit() {
		var projectId = $('#projectSelect').val();
		if(!projectId || '-1' == projectId) {
			showAlertDialog('请选择项目');
			return;
		}
		
		var ldId = $('#ldSelect').val();
		if(!ldId || '-1' == ldId) {
			showAlertDialog('请选择楼栋');
			return;
		}
		
		var yongshuiSum = $('#yongshuiSum').val();
		if(!yongshuiSum || 0 > yongshuiSum) {
			showAlertDialog('用水总额输入非法');
			return;
		}
		
		showLoader('正在保存，请稍后...');
		
		var yongshuiForm = $("#yongshuiForm");
		
		$.ajax({
			url : '${ctx}/spring/mobile/submitYongshui',
			type : 'POST',
			dataType : 'JSON',
			data : yongshuiForm.serialize(),
			cache : false,
			success : function(result) {
				if(result.success) {
					refreshYongshuiRecord(result.yongshuiRecords);
					yongshuiForm[0].reset();
					showAlertDialog('用水记录登记成功!');
				} else {
					showAlertDialog(result.msg);
				}
				
				hideLoader();
			}
		});
	}
</script>
<style type="text/css">
	.listItemLeft {
		float: left; 
		margin-right: 10; 
		color: #866E54
	}
	.listItemRight {
		float: right; 
		color: #469E6F;
	}
</style>
</head>
<body>
	<div data-role="page">

		<div data-role="header" data-position="fixed">
			<a href="${ctx}/spring/mobile/functionList" data-icon="home" data-ajax="false" onclick="showLoader()">首页</a>
			<h1>楼栋用水总额登记</h1>
		</div>

		<div data-role="content">

			<form id="yongshuiForm" action="${ctx }/spring/mobile/submitYongshui" method="post" data-ajax="false">

   				<ul id="yongshuiListview" data-role="listview" data-inset="true">
	                <li data-role="list-divider">用水信息</li>
	                <li>
						<div data-role="fieldcontain">
							<label for="name">项目:</label> 
							<select id="projectSelect" name="projectId" onchange="projectOnchange($(this).val())">
								<option value="-1">请选择</option>
								<c:forEach items="${projectInfos }" var="project" varStatus="varIndex">
									<option value="${project.projectId }">${project.projectName }</option>
								</c:forEach>
							</select>
						</div>
                    </li>
	                <li>
						<div data-role="fieldcontain">
							<label for="name">楼栋:</label> 
							<select id="ldSelect" name="ldId" onchange="ldOnchange($(this).val())">
							</select>
						</div>
                    </li>
	                <li>
						<div data-role="fieldcontain">
							<label for="name">用水总额:</label> 
							<input type="number" id="yongshuiSum" name="yongshuiSum">
						</div>
                    </li>
				</ul>
			</form>
			
			<input type="button" value="提交" onclick="submit()"/>
		</div>

	</div>
</body>
</html>


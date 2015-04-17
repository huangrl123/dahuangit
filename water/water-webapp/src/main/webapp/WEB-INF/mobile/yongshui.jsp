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

<link rel="stylesheet" href="${ctx }/css/water.css" />

<script type="text/javascript">
	window.onload = function() {
		try {
			hideLoader();
		} catch (e) {
		}
	}

	function projectOnchange(projectId) {
		if ('-1' == projectId) {
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
				projectId : projectId,
				systemId : '${systemId}'
			},
			dataType : 'JSON',
			cache : false,
			success : function(result, textStatus) {
				if (result.success) {
					$('#ldSelect').empty();
					$('#ldSelect').append('<option value="-1">请选择</option>');

					for ( var i = 0; i < result.ldInfos.length; i++) {
						var info = result.ldInfos[i];
						$('#ldSelect').append('<option value="' + info.ldId + '">' + info.areaName + '-' + info.ldName + '</option>');
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
		if ('-1' == ldId) {
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
				systemId : '${systemId}',
				ldId : ldId
			},
			cache : false,
			success : function(result, textStatus) {
				if (result.success) {
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
		var html = '<li data-role="list-divider" style="font-weight: bolder;color: #7a7b7f;font-size: 15;background-color: e8ebe8;">楼栋水电成本历史信息</li>';

		for ( var i = 0; i < yongshuiRecords.length; i++) {
			var record = yongshuiRecords[i];
			html = html + '<li>';
			html = html + '<span class="listItemLeft">' + record.month + '</span>';
			html = html + '<span class="listItemCenter"><img src="${ctx}/images/yongshuiSum.png" width="12px" height="12px"><font size="2">￥</font>' + record.consumedata + '</span>';
			html = html + '<span class="listItemRight"><img src="${ctx}/images/yongdianSum.png" width="12px" height="12px"><font size="2">￥</font>' + record.consumeWire + '</span>';
			html = html + '</li>';
		}

		html = '<ul id="yongshuiRecordUl" data-role="listview">' + html + '</ul>';

		removeYongshuiRecordUl();

		contentDiv.append(html);

		$('div[data-role="content"] ul').listview();
	}

	//移除用水记录ul
	function removeYongshuiRecordUl() {
		if ($('#yongshuiRecordUl').length > 0) {
			$('#yongshuiRecordUl').remove();
		}
	}


	function submitFormData() {
		var projectId = $('#projectSelect').val();
		if (!projectId || '-1' == projectId) {
			showAlertDialog('请选择项目');
			return;
		}

		var ldId = $('#ldSelect').val();
		if (!ldId || '-1' == ldId) {
			showAlertDialog('请选择楼栋');
			return;
		}

		var yongshuiSum = $('#yongshuiSum').val();
		yongshuiSum = parseFloat(yongshuiSum).toFixed(2); 
		if('NaN' == yongshuiSum) {
			showAlertDialog('用电总额输入非法');
			return;
		}
		
		if (0.00 > yongshuiSum ||  yongshuiSum >= 1000000.00) {
			showAlertDialog('用水总额输入必须在0.00与1000000.00之间');
			return;
		}
		
		var yongdianSum = $('#yongdianSum').val();
		yongdianSum = parseFloat(yongdianSum).toFixed(2); 
		if('NaN' == yongdianSum) {
			showAlertDialog('用电总额输入非法');
			return;
		}
		
		if (0.00 > yongdianSum || yongdianSum > 1000000.00) {
			showAlertDialog('用电总额输入必须在0.00与1000000.00之间');
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
				if (result.success == true) {
					refreshYongshuiRecord(result.yongshuiRecords);
					$('#yongshuiSum').val('');
					$('#yongdianSum').val('');
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
	font-weight: bolder;
	color: #3c3837;
}

.listItemCenter {
	margin-left: 40px;
	text-align: center;
	color: #CACACA
}

.listItemRight {
	float: right;
	color: #1a875e;
	font-weight: bolder;
}

.headDiv {
	height: 40px;
	text-align: center;
	background-color: #2C3640;
	font-size: 25;
	padding-top: 10px;
	color: #FFFFFF;
}
</style>
</head>
<body>
	<div data-role="page">

		<div data-role="header" data-position="fixed" data-tap-toggle="false" class="header-div">
			<div class="header-title1">
				<span class="header-title1-left" onclick="gotoFunction('${ctx }/spring/mobile/functionList?systemId=${systemId }')"><img alt="" src="${ctx }/images/home.png" height="25" width="25"></span> <span class="header-title1-center">楼栋水电成本登记</span>
			</div>
			<div class="header-title2-query-condition">
				<div class="header-title2-top">水电成本信息</div>
			</div>
		</div>
		<div data-role="content">

			<form id="yongshuiForm" action="${ctx }/spring/mobile/submitYongshui" method="post" data-ajax="false">
				<input type="hidden" name="systemId" value="${systemId }">
				<ul id="yongshuiListview" data-role="listview">
					<li>
						<div data-role="fieldcontain">
							<label for="name" style="font-weight: bolder;">项目:</label> <select id="projectSelect" name="projectId" onchange="projectOnchange($(this).val())">
								<option value="-1">请选择</option>
								<c:forEach items="${projectInfos }" var="project" varStatus="varIndex">
									<option value="${project.projectId }">${project.projectName }</option>
								</c:forEach>
							</select>
						</div>
					</li>
					<li>
						<div data-role="fieldcontain">
							<label for="name" style="font-weight: bolder;">楼栋:</label> <select id="ldSelect" name="ldId" onchange="ldOnchange($(this).val())">
							</select>
						</div>
					</li>
					<li>
						<div data-role="fieldcontain">
							<label for="name" style="font-weight: bolder;">用水总额:</label> <input type="number" id="yongshuiSum" name="yongshuiSum" style="background-color: #e8e8e8;">
						</div>
					</li>
					<li>
						<div data-role="fieldcontain">
							<label for="name" style="font-weight: bolder;">用电总额:</label> <input type="number" id="yongdianSum" name="yongdianSum" style="background-color: #e8e8e8;">
						</div>
					</li>
					<li style="border-bottom: 0px;">
						<div style="text-align: center;" data-role="fieldcontain">
							<img alt="" src="${ctx }/images/submit.png" height="50" width="290" onclick="submitFormData()">
						</div>
					</li>
				</ul>
			</form>
		</div>

	</div>
</body>
</html>


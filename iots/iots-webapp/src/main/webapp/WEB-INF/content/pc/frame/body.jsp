<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../../../common/base.jsp"%>
<HTML>
<HEAD>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<title></title>
<script src="../plugin/jquery/jquery-1.11.2.min.js"></script>

<link rel="stylesheet" type="text/css" href="../plugin/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="../plugin/jquery-easyui/themes/icon.css">
<script type="text/javascript" src="../plugin/jquery-easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="../plugin/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../plugin/jquery-easyui/jquery.easyui-util.js"></script>

<script type="text/javascript" src="../js/frame/login.js"></script>

<link href="../css/css_style.css" rel="stylesheet" type="text/css" />
<!-- IE浏览器下的样式 -->
<!--[if gte IE 6]>
<style type="text/css">
.Position_1{
	width:35%;
	height:23px;
	float:left;
	padding:5px 0px 3px 26px;
	background-image: url(../image/now.gif);
	background-repeat: no-repeat;
	background-position: 7px 6px;
}

.Position_2{
    width:370px;
    height:23px;
    float:right;
	padding:5px 0px 3px 0px;
}
</style>
<![endif]-->
<script type="text/javascript">
	function collapseMenu(target, ptarget, btnImage) {
		if (parent) {
			var targetObj = document.getElementById(target);
			if (targetObj.style.display == "") {
				targetObj.style.display = "none";
				document.getElementById(ptarget).style.width = "5px";
				btnImage.src = "../image/2.gif"
			} else {
				targetObj.style.display = "";
				document.getElementById(ptarget).style.width = "198px";
				btnImage.src = "../image/1.gif";
			}
		}
	}

	function exitSystem() {
		showConfirm('确认退出系统？', function() {
			$.ajax({
				url : '../userController/logout',
				cache : false,
				type : 'POST',
				dataType : 'JSON',
				data : {
					userName : $('#userName').text()
				},
				success : function(result) {
					if (result.success == true) {
						parent.window.location.href = '../frame/login';
					} else {
						showAlert(result.msg);
					}
					hideLoading();
				},
				error : function(result) {
					showAlert(result.msg);
					hideLoading();
				}
			});
		});
	}

	function heartAndReceiveNotice() {
		$.ajax({
			url : '../userController/heart',
			cache : false,
			type : 'POST',
			dataType : 'JSON',
			success : function(result) {
				if (result.success == true) {
					var noticeList = result.noticeInfos;

					if (noticeList.length > 0) {

						$('embed').remove();

						var content = '';
						for ( var i = 0; i < noticeList.length; i++) {
							var info = noticeList[i];
							content = content + '<div style="text-decoration:underline;cursor:hand;" onclick=""><span>【' + info.when + '】</span><span>设备(' + info.perceptionAddr + ')<span><span>'
									+ info.paramDesc + '</span><span style="color:red;">' + info.paramValueDesc + '</span></div>';
						}

						var msg = '<div style="height:100px;width:420px;overflow:scroll;overflow-x:hidden;">' + content + '</div>';

						$.messager.show({
							title : '设备动作变化信息提示',
							msg : msg,
							width : 450,
							height : 150,
							timeout : 10 * 1000,
							showType : 'slide'
						});

						$('body').append('<embed src="../media/appleline.mp3" loop="0" autostart="true" hidden="true"></embed>');
					}
				}
			}
		});
	}

	$(function() {
		setInterval('heartAndReceiveNotice()', 4000);
	})
</script>

</HEAD>
<body class="content_bottom" style="margin: 0px 5px 5px 5px;">
	<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td valign="top" style="width: 198px;">
							<TABLE id="menuList" style="WIDTH: 198px; HEIGHT: 100%;" cellSpacing="0" cellPadding="0" align="left" border="0">
								<TBODY>
									<TR>
										<td id="outLookBarShow" vAlign="middle" align="center" class="left_menu"><iframe name="leftMenu" scrolling="no" src="../frame/leftMenu" frameborder="0" width="100%" height="100%"></iframe></td>
										<td align="center" valign="middle" width="5"><img src="../image/1.gif" width="5" height="35" onClick="collapseMenu('outLookBarShow', 'menuList', this);" style="cursor: hand;" /></td>
									</TR>
								</TBODY>
							</TABLE>
						</td>

						<td width="100%"><table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" class="table_line">
								<tr>
									<td width="100%" valign="top">
										<div class="Position">
											<span class="Position_1"><a>当前位置：</a><a><span class="gray" id="areaSpan"></span></a> </span> <span class="Position_2"> <a>欢迎您：</a><a><span id="userName" class="gray" style="padding-right: 50px;">${sessionScope.userName }</span></a><a
												style="padding-right: 20px;">今天是 ${today }</a><span class="gray"><a href="#" onclick="exitSystem()">退出系统</a></span>
											</span>
										</div>
									</td>
								</tr>
								<tr>
									<td valign="top" width="100%" height="100%"><iframe src="../perception/queryPerceptionPage" scrolling="auto" frameborder="0" width="100%" height="100%" id="page" name="page"></iframe></td>
								</tr>
							</table></td>
					</tr>
				</table>

			</td>
		</tr>
	</table>

	</td>
	</tr>
	</table>

</body>
</html>

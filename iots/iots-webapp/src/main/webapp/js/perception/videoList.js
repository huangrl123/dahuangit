var load;
var showVideoListWin = function() {
	var videoListWin = $('#videoListWin').window({
				title : '视频列表',
				width : 600,
				height : 400,
				collapsible : false,
				minimizable : false,
				maximizable : false,
				closable : false,
				resizable : false,
				draggable : true,
				modal : true
			});

	var grid = $('#perception-video-list-datagrid').datagrid({
		singleSelect : true,
		fitColumns : true,
		rownumbers : true,
		border : false,
		columns : [[{
					field : 'fileName',
					title : '视频名称',
					width : 80
				}, {
					field : 'url',
					title : '操作',
					width : 80,
					align : 'center',
					formatter : function(value, row) {
						return '<a href="' + value + '" style="padding-right:10px;color:blue;">下载</a><a href="#" style="padding-right:10px;color:blue;" onclick="showPlayVideoWin(\'' + value
								+ '\')">播放</a><a  href="#" style="color:blue;" onclick="delPerceptionVideo(\'' + row.fileName + '\')">删除</a>';
					}
				}]],
		onClickRow : function(rowIndex, row) {
			$(this).datagrid('unselectRow', rowIndex);
		}
	});

	load = function() {
		grid.datagrid({
					url : '../perception/getPerceptionVedioList',
					method : 'post',
					queryParams : {
						perceptionAddr : $('#perceptionAddr').val()
					},
					onLoadSuccess : function(data) {
						$(window).resize();
					}
				});
	}

	// 进入页面时，加载数据
	load();

	$('#videoListWinCloseBtn').unbind('click');
	$('#videoListWinCloseBtn').bind('click', function() {
				videoListWin.window('close');
			});

	videoListWin.window('open');
}

var delPerceptionVideo = function(fileName) {
	var progress = $.messager.progress({
				title : '提示',
				text : '',
				msg : '正在请求,请稍后...'
			});

	$.ajax({
				url : '../perception/delPerceptionVideo',
				type : 'POST',
				dataType : 'JSON',
				data : {
					perceptionAddr : $('#perceptionAddr').val(),
					fileName : fileName
				},
				cache : false,
				success : function(result) {
					$.messager.progress('close');

					if (result.success == true) {
						load();
					} else {
						showAlert(result.msg);
					}
				},
				error : function(result) {
					$.messager.progress('close');
					showAlert(result.msg);
				}
			});
}

var showPlayVideoWin = function(url) {
	var playVideoWin = $('#playVideoWin').window({
				title : '视频列表',
				width : 614,
				height : 400,
				collapsible : false,
				minimizable : false,
				maximizable : false,
				closable : false,
				resizable : false,
				draggable : true,
				modal : true
			});

	var src = $('#videoPlayerUrl').val();
	src = src + '?url=' + url;
	
	$('#videoiframe').attr('src', src);

	$('#playVideoWinCloseBtn').unbind('click');
	$('#playVideoWinCloseBtn').bind('click', function() {
				playVideoWin.window('close');
				$('#videoiframe').attr('src', '');
			});

	playVideoWin.window('open');
}
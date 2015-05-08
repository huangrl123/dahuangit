var showRealTimeVideoWin = function() {
	var realTimeVideoWin = $('#realTimeVideoWin').window({
				title : '实时视频',
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

	$('#realTimeWinCloseBtn').unbind('click');
	$('#realTimeWinCloseBtn').bind('click', function() {
				realTimeVideoWin.window('close');
				$('#videoiframe').attr('src', '');
			});

	var src = $('#realTimePlayerUrl').val();
	var rtmp = $('#rtmpBaseUrl').val();
	src = src + '?url=' + rtmp + '&playName=' + $('#perceptionAddr').val();

	$('#realTimeIframe').attr('src', src);

	realTimeVideoWin.window('open');
}
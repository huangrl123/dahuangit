/**
 * 显示加载器
 */
function showLoader(msg) {
	var info = '';
	if (msg) {
		info = msg;
	} else {
		info = '加载中...';
	}

	$.mobile.loading('show', {
				textVisible : true,
				theme : 'a',
				text : info,
				textonly : false,
				html : ''
			});
}

/**
 * 隐藏加载器
 */
function hideLoader() {
	$.mobile.loading('hide');
}

function gotoFunction(url) {
	window.location.href = url;
}

/**
 * 显示提示框
 * 
 * @param {}
 *            text
 */
function showAlertDialog(text) {
	var popupDialogId = 'popupDialog';

	$('<div data-role="popup" id="' + popupDialogId + '" data-confirmed="no" data-transition="pop" data-overlay-theme="a" data-theme="a" data-dismissible="false" style="width:300px;">'
			+ '<div data-role="header" data-theme="a">' + '    <h1>提示</h1>' + '</div>' + '<div role="main" class="ui-content">' + '    <h3 class="ui-title">' + text + '</h3>'
			+ '    <a href="#" data-role="button" data-rel="back">确认</a>' + '</div>' + '</div>').appendTo($.mobile.pageContainer);

	var popupDialogObj = $('#' + popupDialogId);
	popupDialogObj.trigger('create');

	popupDialogObj.popup({
				afterclose : function(event, ui) {
					$(event.target).remove();
				}
			});

	popupDialogObj.popup('open');
}
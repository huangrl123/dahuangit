/**
 * 显示加载器
 */
function showLoader() {
	$.mobile.loading('show', {
				text : '加载中...',
				textVisible : true,
				theme : 'a',
				textonly : false,
				html : "" // 要显示的html内容，如图片等
			});
}

/**
 * 隐藏加载器
 */
function hideLoader() {
	$.mobile.loading('hide');
}
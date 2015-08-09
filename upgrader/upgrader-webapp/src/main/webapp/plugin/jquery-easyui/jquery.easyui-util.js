function showLoading() {
	$.messager.progress({
				modal : true,
				draggable : false,
				text : '加载中..'
			});
}

function hideLoading() {
	$.messager.progress('close');
}

function showAlert(msg) {
	$.messager.alert('提示', msg);
}

function showConfirm(msg, yesFn, noFn) {
	$.messager.confirm('提示', msg, function(r) {
				if (r) {
					yesFn();
				} else {
					if (noFn) {
						noFn();
					}
				}
			});
}

String.prototype.replaceAll = function(replaced, replaceWith) {
	return this.replace(new RegExp(replaced, "gm"), replaceWith);
}
Date.prototype.Format = function(fmt) { // author: meizz
	var o = {
		"M+" : this.getMonth() + 1, // 月份
		"d+" : this.getDate(), // 日
		"h+" : this.getHours(), // 小时
		"m+" : this.getMinutes(), // 分
		"s+" : this.getSeconds(), // 秒
		"q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
		"S" : this.getMilliseconds()
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for (var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}

// 获得上个月在昨天这一天的日期
function getLastMonthYestdy() {
	var date = new Date();
	var daysInMonth = new Array([0], [31], [28], [31], [30], [31], [30], [31], [31], [30], [31], [30], [31]);
	var strYear = date.getFullYear();
	var strDay = date.getDate();
	var strMonth = date.getMonth() + 1;
	if (strYear % 4 == 0 && strYear % 100 != 0) {
		daysInMonth[2] = 29;
	}
	if (strMonth - 1 == 0) {
		strYear -= 1;
		strMonth = 12;
	} else {
		strMonth -= 1;
	}
	strDay = daysInMonth[strMonth] >= strDay ? strDay : daysInMonth[strMonth];
	if (strMonth < 10) {
		strMonth = "0" + strMonth;
	}
	if (strDay < 10) {
		strDay = "0" + strDay;
	}
	datastr = strYear + "-" + strMonth + "-" + strDay;
	return datastr;
}

String.prototype.replaceAll = function(replaced, replaceWith) {
	return this.replace(new RegExp(replaced, "gm"), replaceWith);
}
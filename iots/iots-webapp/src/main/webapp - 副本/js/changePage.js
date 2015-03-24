/**
 * 转页函数
 */
function changePage(e, url){
	var rowIndex = findTR(e.srcElement);
	if(rowIndex!=0){
		location.href=url;
	}
}

function findTR(which){
	if(which.tagName && which.tagName.toLowerCase()=='input'){
		return 0;
	}
	while(which.tagName!="TR"){ 
		which=which.parentElement||which.parentNode;
	}
	return which.rowIndex
}
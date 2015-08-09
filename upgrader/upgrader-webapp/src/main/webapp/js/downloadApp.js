function downloadApp(appNameFileUrl) {
	$.ajax({
				url : '../appUpgrade/addDownloadRecord',
				type : 'POST',
				dataType : 'JSON',
				cache : false,
				success : function(result) {
					if (result.success) {
						window.location.href = appNameFileUrl;
					} 
				}
			});
}
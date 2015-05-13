$(function() {
//			jwplayer("mediaplayer").setup({
//						flashplayer : "../plugin/jwplayer/player.swf",
//						image : "../plugin/jwplayer/preview.jpg",
//						file : "rtmp://192.168.1.178/live/t",
//						rtmp : {
//							subscribe : true
//						},
//						height : 360,
//						// primary : "flash",
//						width : 640
//					});

			jwplayer("mediaplayer").setup({
						playlist : [{
									flashplayer : "../plugin/jwplayer/player.swf",
									image : "../plugin/jwplayer/preview.jpg",
									sources : [{
												file : "rtmp://192.168.1.178/live/t"
											}, {
												file : "/assets/myVideo.flv"
											}]
								}],
						height : 360,
						primary : "html",
						width : 640
					});
		})
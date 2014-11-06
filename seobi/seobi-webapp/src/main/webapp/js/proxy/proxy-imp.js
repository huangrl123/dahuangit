function showProxyImpWin(successfn) {

	var form = Ext.create('Ext.form.Panel', {
				width : 400,
				bodyPadding : 5,
				frame : true,
				items : [{
							xtype : 'filefield',
							name : 'multipartFile',
							fieldLabel : '文件',
							labelWidth : 50,
							width : '100%',
							buttonText : '选择代理服务器文件...'
						}],

				buttons : [{
							text : '上传',
							handler : function() {
								var form = this.up('form').getForm();
								if (form.isValid()) {
									form.submit({
												url : 'spring/proxyMgr/impProxy',
												waitMsg : '正在上传代理服务器信息，请稍后...',
												success : function(form, action, args) {
													var success = Ext.JSON.decode(action.response.responseText).success;
													
													if (!success) {
														Ext.Msg.alert('Failurs', '代理服务器信息上传失败');
														return;
													}
													
													successfn();
													
													Ext.Msg.alert('Success', '代理服务器信息上传成功');
												}
											});
								}
							}
						}, {
							text : '取消',
							handler : function() {
								win.close();
							}
						}]
			});

	var win = Ext.create('Ext.Window', {
				title : '导入代理服务器',
				plain : true,
				closable : true,
				closeAction : 'destroy',
				layout : 'fit',
				items : [form]
			});

	win.show();
}
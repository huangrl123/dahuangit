Ext.define("Ext.window.MessageBox",{extend:"Ext.window.Window",requires:["Ext.toolbar.Toolbar","Ext.form.field.Text","Ext.form.field.TextArea","Ext.form.field.Display","Ext.button.Button","Ext.layout.container.Anchor","Ext.layout.container.HBox","Ext.ProgressBar"],alias:"widget.messagebox",OK:1,YES:2,NO:4,CANCEL:8,OKCANCEL:9,YESNO:6,YESNOCANCEL:14,INFO:Ext.baseCSSPrefix+"message-box-info",WARNING:Ext.baseCSSPrefix+"message-box-warning",QUESTION:Ext.baseCSSPrefix+"message-box-question",ERROR:Ext.baseCSSPrefix+"message-box-error",hideMode:"offsets",closeAction:"hide",resizable:false,autoScroll:true,title:"&#160;",defaultMinWidth:250,defaultMaxWidth:600,defaultMinHeight:110,defaultMaxHeight:500,minWidth:null,maxWidth:null,minHeight:null,maxHeight:null,constrain:true,cls:[Ext.baseCSSPrefix+"message-box",Ext.baseCSSPrefix+"hidden-offsets"],layout:{type:"vbox",align:"stretch"},shrinkWrapDock:true,defaultTextHeight:75,minProgressWidth:250,minPromptWidth:250,buttonText:{ok:"OK",yes:"Yes",no:"No",cancel:"Cancel"},buttonIds:["ok","yes","no","cancel"],titleText:{confirm:"Confirm",prompt:"Prompt",wait:"Loading...",alert:"Attention"},baseIconCls:Ext.baseCSSPrefix+"message-box-icon",ariaRole:"alertdialog",makeButton:function(B){var A=this.buttonIds[B];return new Ext.button.Button({handler:this.btnCallback,itemId:A,scope:this,text:this.buttonText[A],minWidth:75})},btnCallback:function(B){var A=this,C,D;if(A.cfg.prompt||A.cfg.multiline){if(A.cfg.multiline){D=A.textArea}else{D=A.textField}C=D.getValue();D.reset()}A.hide();A.userCallback(B.itemId,C,A.cfg)},hide:function(){var B=this,A=B.cfg?B.cfg.cls:"";B.progressBar.reset();if(A){B.removeCls(A)}B.callParent(arguments)},constructor:function(B){var A=this;A.callParent(arguments);A.minWidth=A.defaultMinWidth=(A.minWidth||A.defaultMinWidth);A.maxWidth=A.defaultMaxWidth=(A.maxWidth||A.defaultMaxWidth);A.minHeight=A.defaultMinHeight=(A.minHeight||A.defaultMinHeight);A.maxHeight=A.defaultMaxHeight=(A.maxHeight||A.defaultMaxHeight)},initComponent:function(D){var B=this,E=B.id,A,C;B.title=B.title||"&#160;";B.iconCls=B.iconCls||"";B.topContainer=new Ext.container.Container({layout:"hbox",padding:10,style:{overflow:"hidden"},items:[B.iconComponent=new Ext.Component({cls:B.baseIconCls}),B.promptContainer=new Ext.container.Container({flex:1,layout:{type:"vbox",align:"stretch"},items:[B.msg=new Ext.form.field.Display({id:E+"-displayfield",cls:B.baseCls+"-text"}),B.textField=new Ext.form.field.Text({id:E+"-textfield",enableKeyEvents:true,listeners:{keydown:B.onPromptKey,scope:B}}),B.textArea=new Ext.form.field.TextArea({id:E+"-textarea",height:75})]})]});B.progressBar=new Ext.ProgressBar({id:E+"-progressbar",margin:"0 10 10 10"});B.items=[B.topContainer,B.progressBar];B.msgButtons=[];for(A=0;A<4;A++){C=B.makeButton(A);B.msgButtons[C.itemId]=C;B.msgButtons.push(C)}B.bottomTb=new Ext.toolbar.Toolbar({id:E+"-toolbar",ui:"footer",dock:"bottom",layout:{pack:"center"},items:[B.msgButtons[0],B.msgButtons[1],B.msgButtons[2],B.msgButtons[3]]});B.dockedItems=[B.bottomTb];B.on("close",B.onClose,B);B.callParent()},onClose:function(){var A=this.header.child("[type=close]");A.itemId="cancel";this.btnCallback(A);delete A.itemId},onPromptKey:function(C,A){var B=this;if(A.keyCode===A.RETURN||A.keyCode===10){if(B.msgButtons.ok.isVisible()){B.msgButtons.ok.handler.call(B,B.msgButtons.ok)}else{if(B.msgButtons.yes.isVisible()){B.msgButtons.yes.handler.call(B,B.msgButtons.yes)}}}},reconfigure:function(F){var E=this,R=0,Q=true,H=E.buttonText,P=E.resizer,J=E.header,B=J&&!J.isHeader,K=F&&(F.message||F.msg),A,N,S,D,O,M,C,I,G,L;E.updateButtonText();E.cfg=F=F||{};if(F.width){N=F.width}if(F.height){S=F.height}E.minWidth=F.minWidth||E.defaultMinWidth;E.maxWidth=F.maxWidth||E.defaultMaxWidth;E.minHeight=F.minHeight||E.defaultMinHeight;E.maxHeight=F.maxHeight||E.defaultMaxHeight;if(P){A=P.resizeTracker;P.minWidth=A.minWidth=E.minWidth;P.maxWidth=A.maxWidth=E.maxWidth;P.minHeight=A.minHeight=E.minHeight;P.maxHeight=A.maxHeight=E.maxHeight}delete E.defaultFocus;if(F.defaultFocus){E.defaultFocus=F.defaultFocus}E.animateTarget=F.animateTarget||undefined;E.modal=F.modal!==false;E.setTitle(F.title||(B&&J.title)||E.title);E.setIconCls(F.iconCls||(B&&J.iconCls)||E.iconCls);if(Ext.isObject(F.buttons)){E.buttonText=F.buttons;R=0}else{E.buttonText=F.buttonText||E.buttonText;R=Ext.isNumber(F.buttons)?F.buttons:0}R=R|E.updateButtonText();E.buttonText=H;Ext.suspendLayouts();delete E.width;delete E.height;if(N||S){if(N){E.setWidth(N)}if(S){E.setHeight(S)}}E.hidden=false;if(!E.rendered){E.render(Ext.getBody())}E.closable=F.closable!==false&&!F.wait;J=E.header;if(J){J.child("[type=close]").setVisible(E.closable);if(!F.title&&!E.closable&&!F.iconCls){J.hide()}else{J.show()}}E.liveDrag=!F.proxyDrag;E.userCallback=Ext.Function.bindCallback(F.callback||F.fn||Ext.emptyFn,F.scope||Ext.global);E.setIcon(F.icon);C=E.msg;if(K){C.setValue(K);C.show()}else{C.hide()}O=E.textArea;M=E.textField;if(F.prompt||F.multiline){E.multiline=F.multiline;if(F.multiline){O.setValue(F.value);O.setHeight(F.defaultTextHeight||E.defaultTextHeight);O.show();M.hide();E.defaultFocus=O}else{M.setValue(F.value);O.hide();M.show();E.defaultFocus=M}}else{O.hide();M.hide()}I=E.progressBar;if(F.progress||F.wait){I.show();E.updateProgress(0,F.progressText);L=F.wait;if(L===true){F=F.waitConfig}if(L){I.wait(L)}}else{I.hide()}G=E.msgButtons;for(D=0;D<4;D++){if(R&Math.pow(2,D)){if(!E.defaultFocus){E.defaultFocus=G[D]}G[D].show();Q=false}else{G[D].hide()}}if(Q){E.bottomTb.hide()}else{E.bottomTb.show()}Ext.resumeLayouts(true)},updateButtonText:function(){var B=this,E=B.buttonText,D=0,A,C;for(A in E){if(E.hasOwnProperty(A)){C=B.msgButtons[A];if(C){if(B.cfg&&B.cfg.buttonText){D=D|Math.pow(2,Ext.Array.indexOf(B.buttonIds,A))}if(C.text!=E[A]){C.setText(E[A])}}}}return D},show:function(C){var B=this,A;C=C||{};if(Ext.Component.layoutSuspendCount){Ext.on({resumelayouts:function(){B.show(C)},single:true});return B}B.reconfigure(C);if(C.cls){B.addCls(C.cls)}A=B.query("textfield:not([hidden]),textarea:not([hidden]),button:not([hidden])");B.preventFocusOnActivate=!A.length;B.hidden=true;B.callParent();return B},onShow:function(){this.callParent(arguments);this.center()},updateText:function(A){this.msg.setValue(A)},setIcon:function(D,E,A){var C=this,F=C.iconComponent,B=C.messageIconCls;if(B){F.removeCls(B)}if(D){F.show();if(E||A){F.setSize(E||F.getWidth(),A||F.getHeight())}F.addCls(Ext.baseCSSPrefix+"dlg-icon");F.addCls(C.messageIconCls=D)}else{F.removeCls(Ext.baseCSSPrefix+"dlg-icon");F.hide()}return C},updateProgress:function(A,B,C){this.progressBar.updateProgress(A,B);if(C){this.updateText(C)}return this},onEsc:function(){if(this.closable!==false){this.callParent(arguments)}},confirm:function(C,D,B,A){if(Ext.isString(C)){C={title:C,icon:this.QUESTION,message:D,buttons:this.YESNO,callback:B,scope:A}}return this.show(C)},prompt:function(D,F,C,E,B,A){if(Ext.isString(D)){D={prompt:true,title:D,minWidth:this.minPromptWidth,message:F,buttons:this.OKCANCEL,callback:C,scope:E,multiline:B,value:A}}return this.show(D)},wait:function(C,B,A){if(Ext.isString(C)){C={title:B,message:C,closable:false,wait:true,modal:true,minWidth:this.minProgressWidth,waitConfig:A}}return this.show(C)},alert:function(B,D,A,C){if(Ext.isString(B)){B={title:B,message:D,buttons:this.OK,fn:A,scope:C,minWidth:this.minWidth}}return this.show(B)},progress:function(A,C,B){if(Ext.isString(A)){A={title:A,message:C,progress:true,progressText:B}}return this.show(A)}},function(){Ext.MessageBox=Ext.Msg=new this()});
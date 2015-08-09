Ext.define("Ext.data.flash.BinaryXhr",{statics:{flashPluginActivated:function(){Ext.data.flash.BinaryXhr.flashPluginActive=true;Ext.data.flash.BinaryXhr.flashPlugin=document.getElementById("ext-flash-polyfill");Ext.GlobalEvents.fireEvent("flashready")},flashPluginActive:false,flashPluginInjected:false,connectionIndex:1,liveConnections:{},flashPlugin:null,onFlashStateChange:function(A,D,B){var C;C=this.liveConnections[Number(A)];if(C){C.onFlashStateChange(D,B)}else{Ext.warn.log("onFlashStateChange for unknown connection ID: "+A)}},registerConnection:function(B){var A=this.connectionIndex;this.conectionIndex=this.connectionIndex+1;this.liveConnections[A]=B;return A},injectFlashPlugin:function(){var B=this,C,A;B.flashPolyfillEl=Ext.getBody().appendChild({id:"ext-flash-polyfill",cn:[{tag:"p",html:"To view this page ensure that Adobe Flash Player version 11.1.0 or greater is installed."},{tag:"a",href:"http://www.adobe.com/go/getflashplayer",cn:[{tag:"img",src:window.location.protocol+"//www.adobe.com/images/shared/download_buttons/get_flash_player.gif",alt:"Get Adobe Flash player"}]}]});C=[Ext.Loader.getPath("Ext.data.Connection"),"../../../plugins/flash/swfobject.js"].join("/");A="/plugins/flash/FlashPlugin.swf";A=[Ext.Loader.getPath("Ext.data.Connection"),"../../plugins/flash/FlashPlugin.swf"].join("/");if(Ext.flashPluginPath){A=Ext.flashPluginPath}Ext.Loader.loadScript({url:C,onLoad:function(){var E="11.4.0";var D="playerProductInstall.swf";var F={};var G={};G.quality="high";G.bgcolor="#ffffff";G.allowscriptaccess="sameDomain";G.allowfullscreen="true";var H={};H.id="ext-flash-polyfill";H.name="polyfill";H.align="middle";swfobject.embedSWF(A,"ext-flash-polyfill","0","0",E,D,F,G,H)},onError:function(){Ext.Error.raise("Could not load flash-loader file swfobject.js from "+flashLoader)},scope:B});Ext.data.flash.BinaryXhr.flashPluginInjected=true}},readyState:0,status:0,statusText:"",responseBytes:null,javascriptId:null,constructor:function(A){if(!Ext.data.flash.BinaryXhr.flashPluginInjected){Ext.data.flash.BinaryXhr.injectFlashPlugin()}var B=this;Ext.apply(B,A);B.requestHeaders={}},abort:function(){var A=this;if(A.readyState==4){Ext.warn.log("Aborting a connection that's completed its transfer: "+this.url);return}A.aborted=true;if(!Ext.data.flash.BinaryXhr.flashPluginActive){Ext.GlobalEvents.removeListener("flashready",A.onFlashReady,A);return}Ext.data.flash.BinaryXhr.flashPlugin.abortRequest(A.javascriptId);delete Ext.data.flash.BinaryXhr.liveConnections[A.javascriptId]},getAllResponseHeaders:function(){var A=[];Ext.Object.each(this.responseHeaders,function(B,C){A.push(B+": "+C)});return A.join("\x0d\x0a")},getResponseHeader:function(B){var A=this.responseHeaders;return(A&&A[B])||null},open:function(D,B,E,F,A){var C=this;C.method=D;C.url=B;C.async=E!==false;C.user=F;C.password=A;if(!C.async){Ext.Error.raise("Binary posts are only supported in async mode: "+B)}if(C.method!="POST"){Ext.log.warn("Binary data can only be sent as a POST request: "+B)}},overrideMimeType:function(A){this.mimeType=A},send:function(A){var B=this;B.body=A;if(!Ext.data.flash.BinaryXhr.flashPluginActive){Ext.GlobalEvents.addListener("flashready",B.onFlashReady,B)}else{this.onFlashReady()}},onFlashReady:function(){var B=this,A,C;B.javascriptId=Ext.data.flash.BinaryXhr.registerConnection(B);A={method:B.method,url:B.url,user:B.user,password:B.password,mimeType:B.mimeType,requestHeaders:B.requestHeaders,body:B.body,javascriptId:B.javascriptId};C=Ext.data.flash.BinaryXhr.flashPlugin.postBinary(A)},setReadyState:function(B){var A=this;if(A.readyState!=B){A.readyState=B;A.onreadystatechange()}},setRequestHeader:function(A,B){this.requestHeaders[A]=B},onreadystatechange:Ext.emptyFn,parseData:function(B){var A=this;this.status=B.status||0;A.responseHeaders={};if(A.mimeType){A.responseHeaders["content-type"]=A.mimeType}if(B.reason=="complete"){this.responseBytes=B.data;A.responseHeaders["content-length"]=B.data.length}else{if(B.reason=="error"||B.reason=="securityError"){this.statusText=B.text;A.responseHeaders["content-length"]=0}else{Ext.Error.raise("Unkown reason code in data: "+B.reason)}}},onFlashStateChange:function(C,A){var B=this;if(C==4){B.parseData(A);delete Ext.data.flash.BinaryXhr.liveConnections[B.javascriptId]}B.setReadyState(C)}});
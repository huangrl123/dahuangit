Ext.define("Ext.data.JsonP",{singleton:true,requestCount:0,requests:{},timeout:30000,disableCaching:true,disableCachingParam:"_dc",callbackKey:"callback",request:function(F){F=Ext.apply({},F);if(!F.url){Ext.Error.raise("A url must be specified for a JSONP request.")}var I=this,G=Ext.isDefined(F.disableCaching)?F.disableCaching:I.disableCaching,J=F.disableCachingParam||I.disableCachingParam,H=++I.requestCount,M=F.callbackName||"callback"+H,K=F.callbackKey||I.callbackKey,C=Ext.isDefined(F.timeout)?F.timeout:I.timeout,E=Ext.apply({},F.params),L=F.url,B=Ext.name,D,A;if(G&&!E[J]){E[J]=Ext.Date.now()}F.params=E;E[K]=B+".data.JsonP."+M;A=I.createScript(L,E,F);I.requests[H]=D={url:L,params:E,script:A,id:H,scope:F.scope,success:F.success,failure:F.failure,callback:F.callback,callbackKey:K,callbackName:M};if(C>0){D.timeout=setTimeout(Ext.bind(I.handleTimeout,I,[D]),C)}I.setupErrorHandling(D);I[M]=Ext.bind(I.handleResponse,I,[D],true);I.loadScript(D);return D},abort:function(C){var D=this,B=D.requests,A;if(C){if(!C.id){C=B[C]}D.handleAbort(C)}else{for(A in B){if(B.hasOwnProperty(A)){D.abort(B[A])}}}},setupErrorHandling:function(A){A.script.onerror=Ext.bind(this.handleError,this,[A])},handleAbort:function(A){A.errorType="abort";this.handleResponse(null,A)},handleError:function(A){A.errorType="error";this.handleResponse(null,A)},cleanupErrorHandling:function(A){A.script.onerror=null},handleTimeout:function(A){A.errorType="timeout";this.handleResponse(null,A)},handleResponse:function(C,D){var A=true,B=Ext.GlobalEvents;if(D.timeout){clearTimeout(D.timeout)}delete this[D.callbackName];delete this.requests[D.id];this.cleanupErrorHandling(D);Ext.fly(D.script).destroy();if(D.errorType){A=false;Ext.callback(D.failure,D.scope,[D.errorType])}else{Ext.callback(D.success,D.scope,[C])}Ext.callback(D.callback,D.scope,[A,C,D.errorType]);if(B.hasListeners.idle){B.fireEvent("idle")}},createScript:function(A,D,B){var C=document.createElement("script");C.setAttribute("src",Ext.urlAppend(A,Ext.Object.toQueryString(D)));C.setAttribute("async",true);C.setAttribute("type","text/javascript");return C},loadScript:function(A){Ext.getHead().appendChild(A.script)}});
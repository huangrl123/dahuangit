Ext.define("Ext.state.CookieProvider",{extend:"Ext.state.Provider",constructor:function(A){var B=this;B.path="/";B.expires=new Date(Ext.Date.now()+(1000*60*60*24*7));B.domain=null;B.secure=false;B.callParent(arguments);B.state=B.readCookies()},set:function(B,C){var A=this;if(typeof C=="undefined"||C===null){A.clear(B);return}A.setCookie(B,C);A.callParent(arguments)},clear:function(A){this.clearCookie(A);this.callParent(arguments)},readCookies:function(){var A={},H=document.cookie+";",B=/\s?(.*?)=(.*?);/g,G=this.prefix,D=G.length,F,C,E;while((F=B.exec(H))!=null){C=F[1];E=F[2];if(C&&C.substring(0,D)==G){A[C.substr(D)]=this.decodeValue(E)}}return A},setCookie:function(B,C){var A=this;document.cookie=A.prefix+B+"="+A.encodeValue(C)+((A.expires==null)?"":("; expires="+A.expires.toUTCString()))+((A.path==null)?"":("; path="+A.path))+((A.domain==null)?"":("; domain="+A.domain))+((A.secure==true)?"; secure":"")},clearCookie:function(B){var A=this;document.cookie=A.prefix+B+"=null; expires=Thu, 01-Jan-1970 00:00:01 GMT"+((A.path==null)?"":("; path="+A.path))+((A.domain==null)?"":("; domain="+A.domain))+((A.secure==true)?"; secure":"")}});
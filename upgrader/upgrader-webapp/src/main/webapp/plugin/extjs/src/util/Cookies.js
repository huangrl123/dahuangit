Ext.define("Ext.util.Cookies",{singleton:true,set:function(F,C){var H=arguments,B=arguments.length,A=(B>2)?H[2]:null,E=(B>3)?H[3]:"/",D=(B>4)?H[4]:null,G=(B>5)?H[5]:false;document.cookie=F+"="+escape(C)+((A===null)?"":("; expires="+A.toUTCString()))+((E===null)?"":("; path="+E))+((D===null)?"":("; domain="+D))+((G===true)?"; secure":"")},get:function(D){var A=document.cookie.split("; "),E=A.length,C,B;for(B=0;B<E;++B){C=A[B].split("=");if(C[0]===D){return C[1]||""}}return null},clear:function(A,B){if(this.get(A)){B=B||"/";document.cookie=A+"=; expires=Thu, 01-Jan-1970 00:00:01 GMT; path="+B}},getCookieVal:function(A){var C=document.cookie,B=C.indexOf(";",A);if(B==-1){B=C.length}return unescape(C.substring(A,B))}});
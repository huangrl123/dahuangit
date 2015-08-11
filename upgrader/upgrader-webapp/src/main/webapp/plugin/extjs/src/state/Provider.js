Ext.define("Ext.state.Provider",{mixins:{observable:"Ext.util.Observable"},prefix:"ext-",constructor:function(A){var B=this;Ext.apply(B,A);B.state={};B.mixins.observable.constructor.call(B)},get:function(A,B){return typeof this.state[A]=="undefined"?B:this.state[A]},clear:function(B){var A=this;delete A.state[B];A.fireEvent("statechange",A,B,null)},set:function(B,C){var A=this;A.state[B]=C;A.fireEvent("statechange",A,B,C)},decodeValue:function(D){var G=this,A=/^(a|n|d|b|s|o|e)\:(.*)$/,F=A.exec(unescape(D)),C,H,I,E,J,B;if(!F||!F[1]){return}H=F[1];D=F[2];switch(H){case"e":return null;case"n":return parseFloat(D);case"d":return new Date(Date.parse(D));case"b":return(D=="1");case"a":C=[];if(D!=""){E=D.split("^");J=E.length;for(B=0;B<J;B++){D=E[B];C.push(G.decodeValue(D))}}return C;case"o":C={};if(D!=""){E=D.split("^");J=E.length;for(B=0;B<J;B++){D=E[B];I=D.split("=");C[I[0]]=G.decodeValue(I[1])}}return C;default:return D}},encodeValue:function(E){var C="",B=0,F,D,A;if(E==null){return"e:1"}else{if(typeof E=="number"){F="n:"+E}else{if(typeof E=="boolean"){F="b:"+(E?"1":"0")}else{if(Ext.isDate(E)){F="d:"+E.toUTCString()}else{if(Ext.isArray(E)){for(D=E.length;B<D;B++){C+=this.encodeValue(E[B]);if(B!=D-1){C+="^"}}F="a:"+C}else{if(typeof E=="object"){for(A in E){if(typeof E[A]!="function"&&E[A]!==undefined){C+=A+"="+this.encodeValue(E[A])+"^"}}F="o:"+C.substring(0,C.length-1)}else{F="s:"+E}}}}}}return escape(F)}});
Ext.define("Ext.app.bind.Template",{requires:["Ext.util.Format"],numberRe:/^(?:\d+(?:\.\d*)?)$/,stringRe:/^(?:["][^"]*["])$/,tokenRe:/\{[!]?(?:(?:(\d+)|([a-z_][\w\-\.]*))(?::([a-z_\.]+)(?:\(([^\)]*?)?\))?)?)\}/gi,formatRe:/^([a-z_]+)(?:\(([^\)]*?)?\))?$/i,buffer:null,slots:null,tokens:null,constructor:function(B){var A=this,D=A._initters,C;A.text=B;for(C in D){A[C]=D[C]}},_initters:{apply:function(B,A){return this.parse().apply(B,A)},getTokens:function(){return this.parse().getTokens()}},apply:function(H,B){var A=this,G=A.slots,I=A.buffer,C=G.length,E,F,D;for(E=0;E<C;++E){F=G[E];if(F){if((D=H[F.pos])==null){D=""}if(F.not){D=!D}if(F.format){D=F.format(D,B)}I[E]=D}}return I.join("")},getTokens:function(){return this.tokens},parse:function(){var K=this,H=K.text,L=[],J=[],E=[],N={},G=0,B=K.tokenRe,I=0,D,F,M,C,A,O,P;for(F in K._initters){delete K[F]}K.buffer=L;K.slots=J;K.tokens=E;while((C=B.exec(H))){M=C.index-G;if(M){L[I++]=H.substring(G,G+M);G+=M}G+=(A=C[0]).length;O={fmt:(D=C[3]||null),index:C[1]?parseInt(C[1],10):null,not:A.charAt(1)==="!",token:C[2]||null};P=O.token||String(O.index);if(P in N){O.pos=N[P]}else{N[P]=O.pos=E.length;E.push(P)}if(D){if(D.substring(0,5)==="this."){O.fmt=D.substring(5)}else{if(!(D in Ext.util.Format)){Ext.Error.raise('Invalid format specified: "'+D+'"')}O.scope=Ext.util.Format}K.parseArgs(C[4],O)}J[I++]=O}if(G<H.length){L[I++]=H.substring(G)}return K},parseArgs:function(I,H){var G=this,C=G.numberRe,F=G.stringRe,E,A,D,B;if(!I){A=[]}else{if(I.indexOf(",")<0){A=[I]}else{A=I.split(",")}}H=H||{};B=A.length;H.args=A;for(D=0;D<B;++D){E=A[D];if(E==="true"){A[D]=true}else{if(E==="false"){A[D]=false}else{if(E==="null"){A[D]=null}else{if(C.test(E)){A[D]=parseFloat(E)}else{if(F.test(E)){A[D]=E.substring(1,E.length-1)}else{H.fn=Ext.functionFactory("return ["+I+"];");H.format=G._formatEval;break}}}}}}if(!H.format){A.unshift(0);H.format=G._formatArgs}return H},parseFormat:function(A){var D=this,C=D.formatRe.exec(A),B={fmt:A,scope:Ext.util.Format},E;if(!C){Ext.Error.raise('Invalid format syntax: "'+B+'"')}E=C[2];if(E){B.fmt=C[1];D.parseArgs(E,B)}else{B.args=[0];B.format=D._formatArgs}return B},_formatArgs:function(B,A){A=this.scope||A;this.args[0]=B;return A[this.fmt].apply(A,this.args)},_formatEval:function(B,A){var C=this.fn();C.unshift(B);A=this.scope||A;return A[this.fmt].apply(A,C)}});
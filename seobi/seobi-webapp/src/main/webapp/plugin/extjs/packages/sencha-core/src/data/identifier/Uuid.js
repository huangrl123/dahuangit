Ext.define("Ext.data.identifier.Uuid",{extend:"Ext.data.identifier.Generator",alias:"data.identifier.uuid",isUnique:true,config:{id:null},constructor:function(A){this.callParent([A]);this.reconfigure(A)},reconfigure:function(A){var B=this.self;this.generate=(A&&A.version===1)?B.createSequential(A.salt,A.timestamp,A.clockSeq):B.createRandom()},clone:null,statics:{createRandom:function(){var D="xxxxxxxx-xxxx-4xxx-Rxxx-xMxxxxxxxxxx".split(""),C="0123456789abcdef".split(""),B=D.length,A=[];return function(){for(var E,G,F=0;F<B;++F){G=D[F];if(G!=="-"&&G!=="4"){E=Math.random()*16;E=(G==="R")?(E&3|8):(E|((G==="M")?1:0));G=C[E]}A[F]=G}return A.join("")}},createSequential:function(B,A,D){var H=[],J=Math.pow(2,32),I=B.lo,F=B.hi,G=A.lo,C=A.hi,E=function(M,K){var L=M.toString(16).toLowerCase();if(L.length>K){L=L.substring(L.length-K)}else{if(L.length<K){L=Ext.String.leftPad(L,K,"0")}}return L};if(typeof B==="number"){F=Math.floor(B/J);I=Math.floor(B-F*J)}if(typeof A==="number"){C=Math.floor(A/J);G=Math.floor(A-C*J)}F|=256;H[3]=E(128|((D>>>8)&63),2)+E(D&255,2);H[4]=E(F,4)+E(I,8);return function(){H[0]=E(G,8);H[1]=E(C&65535,4);H[2]=E(((C>>>16)&4095)|(1<<12),4);++G;if(G>=J){G=0;++C}return H.join("-")}}}},function(){this.Global=new this({id:"uuid"})});
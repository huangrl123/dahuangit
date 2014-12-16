Ext.define("Ext.data.amf.Encoder",{alias:"data.amf.Encoder",config:{format:3,bytes:[]},constructor:function(A){this.initConfig(A);this.clear()},clear:function(){this.setBytes([])},applyFormat:function(B){var A={0:{writeUndefined:this.write0Undefined,writeNull:this.write0Null,writeBoolean:this.write0Boolean,writeNumber:this.write0Number,writeString:this.write0String,writeXml:this.write0Xml,writeDate:this.write0Date,writeArray:this.write0Array,writeGenericObject:this.write0GenericObject},3:{writeUndefined:this.write3Undefined,writeNull:this.write3Null,writeBoolean:this.write3Boolean,writeNumber:this.write3Number,writeString:this.write3String,writeXml:this.write3Xml,writeDate:this.write3Date,writeArray:this.write3Array,writeGenericObject:this.write3GenericObject}}[B];if(A){Ext.apply(this,A);return B}else{Ext.Error.raise("Unsupported AMF format: "+B+". Only '3' (AMF3) or '0' (AMF0) are supported at this point.");return}},writeObject:function(A){var B=typeof(A);if(B==="undefined"){this.writeUndefined()}else{if(A===null){this.writeNull()}else{if(Ext.isBoolean(A)){this.writeBoolean(A)}else{if(Ext.isString(A)){this.writeString(A)}else{if(B==="number"||A instanceof Number){this.writeNumber(A)}else{if(B==="object"){if(A instanceof Date){this.writeDate(A)}else{if(Ext.isArray(A)){this.writeArray(A)}else{if(this.isXmlDocument(A)){this.writeXml(A)}else{this.writeGenericObject(A)}}}}else{Ext.log.warn("AMF Encoder: Unknown item type "+B+" can't be written to stream: "+A)}}}}}}},write3Undefined:function(){this.writeByte(0)},write0Undefined:function(){this.writeByte(6)},write3Null:function(){this.writeByte(1)},write0Null:function(){this.writeByte(5)},write3Boolean:function(A){if(typeof(A)!=="boolean"){Ext.log.warn("Encoder: writeBoolean argument is not a boolean. Coercing.")}if(A){this.writeByte(3)}else{this.writeByte(2)}},write0Boolean:function(A){if(typeof(A)!=="boolean"){Ext.log.warn("Encoder: writeBoolean argument is not a boolean. Coercing.")}this.writeByte(1);if(A){this.writeByte(1)}else{this.writeByte(0)}},encode29Int:function(C){var E=[],D=C,B,A;if(D==0){return[0]}if(D>2097151){B=D&255;E.unshift(B);D=D>>8}while(D>0){B=D&127;E.unshift(B);D=D>>7}for(A=0;A<E.length-1;A++){E[A]=E[A]|128}return E},write3Number:function(B){var D;var C=536870911,A=-268435455;if(typeof(B)!=="number"&&!(B instanceof Number)){Ext.log.warn("Encoder: writeNumber argument is not numeric. Can't coerce.")}if(B instanceof Number){B=B.valueOf()}if(B%1===0&&B>=A&&B<=C){B=B&C;D=this.encode29Int(B);D.unshift(4);this.writeBytes(D)}else{D=this.encodeDouble(B);D.unshift(5);this.writeBytes(D)}},write0Number:function(A){var B;if(typeof(A)!=="number"&&!(A instanceof Number)){Ext.log.warn("Encoder: writeNumber argument is not numeric. Can't coerce.")}if(A instanceof Number){A=A.valueOf()}B=this.encodeDouble(A);B.unshift(0);this.writeBytes(B)},encodeUtf8Char:function(D){var F=[],E,C,A,B;if(D>1114111){Ext.Error.raise("UTF 8 char out of bounds")}if(D<=127){F.push(D)}else{if(D<=2047){C=2}else{if(D<=65535){C=3}else{C=4}}B=128;for(A=1;A<C;A++){E=(D&63)|128;F.unshift(E);D=D>>6;B=(B>>1)|128}E=D|B;F.unshift(E)}return F},encodeUtf8String:function(B){var A,C=[];for(A=0;A<B.length;A++){var D=this.encodeUtf8Char(B.charCodeAt(A));C.push.apply(C,D)}return C},encode3Utf8StringLen:function(B){var A=B.length,C=[];if(A<=268435455){A=A<<1;A=A|1;C=this.encode29Int(A)}else{Ext.Error.raise("UTF8 encoded string too long to serialize to AMF: "+A)}return C},write3String:function(A){if(!Ext.isString(A)){Ext.log.warn("Encoder: writString argument is not a string.")}if(A==""){this.writeByte(6);this.writeByte(1)}else{var B=this.encodeUtf8String(A);var C=this.encode3Utf8StringLen(B);this.writeByte(6);this.writeBytes(C);this.writeBytes(B)}},encodeXInt:function(C,B){var D=[],A;for(A=0;A<B;A++){D.unshift(C&255);C=C>>8}return D},write0String:function(B){if(!Ext.isString(B)){Ext.log.warn("Encoder: writString argument is not a string.")}if(B==""){this.writeByte(2);this.writeBytes([0,0])}else{var C=this.encodeUtf8String(B);var A;var D;if(C.length<=65535){A=2;D=this.encodeXInt(C.length,2)}else{A=12;D=this.encodeXInt(C.length,4)}this.writeByte(A);this.writeBytes(D);this.writeBytes(C)}},write3XmlWithType:function(C,A){if(A!==7&&A!==11){Ext.Error.raise("write XML with unknown AMF3 code: "+A)}if(!this.isXmlDocument(C)){Ext.log.warn("Encoder: write3XmlWithType argument is not an xml document.")}var E=this.convertXmlToString(C);if(E==""){this.writeByte(A);this.writeByte(1)}else{var B=this.encodeUtf8String(E);var D=this.encode3Utf8StringLen(B);this.writeByte(A);this.writeBytes(D);this.writeBytes(B)}},write3XmlDocument:function(A){this.write3XmlWithType(A,7)},write3Xml:function(A){this.write3XmlWithType(A,11)},write0Xml:function(B){if(!this.isXmlDocument(B)){Ext.log.warn("Encoder: write0Xml argument is not an xml document.")}var D=this.convertXmlToString(B);this.writeByte(15);var A=this.encodeUtf8String(D);var C=this.encodeXInt(A.length,4);this.writeBytes(C);this.writeBytes(A)},write3Date:function(A){if(!(A instanceof Date)){Ext.Error.raise("Serializing a non-date object as date: "+A)}this.writeByte(8);this.writeBytes(this.encode29Int(1));this.writeBytes(this.encodeDouble(new Number(A)))},write0Date:function(A){if(!(A instanceof Date)){Ext.Error.raise("Serializing a non-date object as date: "+A)}this.writeByte(11);this.writeBytes(this.encodeDouble(new Number(A)));this.writeBytes([0,0])},write3Array:function(B){if(!Ext.isArray(B)){Ext.Error.raise("Serializing a non-array object as array: "+B)}if(B.length>268435455){Ext.Error.raise("Array size too long to encode in AMF3: "+B.length)}this.writeByte(9);var A=B.length;A=A<<1;A=A|1;this.writeBytes(this.encode29Int(A));this.writeByte(1);Ext.each(B,function(C){this.writeObject(C)},this)},write0ObjectProperty:function(A,C){if(!(A instanceof String)&&(typeof(A)!=="string")){A=A+""}var B=this.encodeUtf8String(A);var D;D=this.encodeXInt(B.length,2);this.writeBytes(D);this.writeBytes(B);this.writeObject(C)},write0Array:function(C){var A;if(!Ext.isArray(C)){Ext.Error.raise("Serializing a non-array object as array: "+C)}this.writeByte(8);var B=0;for(A in C){B++}this.writeBytes(this.encodeXInt(B,4));for(A in C){this.write0ObjectProperty(A,C[A])}this.writeBytes([0,0,9])},write0StrictArray:function(B){if(!Ext.isArray(B)){Ext.Error.raise("Serializing a non-array object as array: "+B)}this.writeByte(10);var A=B.length;this.writeBytes(this.encodeXInt(A,4));Ext.each(B,function(C){this.writeObject(C)},this)},write3ByteArray:function(B){if(!Ext.isArray(B)){Ext.Error.raise("Serializing a non-array object as array: "+B)}if(B.length>268435455){Ext.Error.raise("Array size too long to encode in AMF3: "+B.length)}this.writeByte(12);var A=B.length;A=A<<1;A=A|1;this.writeBytes(this.encode29Int(A));this.writeBytes(B)},write3GenericObject:function(A){var C;if(!Ext.isObject(A)){Ext.Error.raise("Serializing a non-object object: "+A)}this.writeByte(10);var D=11;this.writeByte(D);this.writeByte(1);for(C in A){var E=new String(C).valueOf();if(E==""){Ext.Error.raise("Can't encode non-string field name: "+C)}var B=(this.encodeUtf8String(C));this.writeBytes(this.encode3Utf8StringLen(C));this.writeBytes(B);this.writeObject(A[C])}this.writeByte(1)},write0GenericObject:function(C){var D,B,A;if(!Ext.isObject(C)){Ext.Error.raise("Serializing a non-object object: "+C)}D=!!C.$flexType;B=D?16:3;this.writeByte(B);if(D){this.write0ShortUtf8String(C.$flexType)}for(A in C){if(A!="$flexType"){this.write0ObjectProperty(A,C[A])}}this.writeBytes([0,0,9])},writeByte:function(A){if(A<0||A>255){Ex.Error.raise("ERROR: Value being written outside byte range: "+A)}this.getBytes().push(A)},writeBytes:function(B){var A;if(!Ext.isArray(B)){Ext.Error.raise("Decoder: writeBytes parameter is not an array: "+B)}for(A=0;A<B.length;A++){if(B[A]<0||B[A]>255||!Ext.isNumber(B[A])){Ext.Error.raise("ERROR: Value "+A+" being written outside byte range: "+B[A])}}this.getBytes().push.apply(this.getBytes(),B)},convertXmlToString:function(B){var A;if(window.XMLSerializer){A=new window.XMLSerializer().serializeToString(B)}else{A=B.xml}return A},isXmlDocument:function(A){if(window.DOMParser){if(Ext.isDefined(A.doctype)){return true}}if(Ext.isString(A.xml)){return true}return false},encodeDouble:function(G){var N=11,D=52;var K=(1<<(N-1))-1,B,A,M,F,J,C,H,E=[];var L=[127,240,0,0,0,0,0,0],O=[255,240,0,0,0,0,0,0],I=[255,248,0,0,0,0,0,0];if(isNaN(G)){E=I}else{if(G===Infinity){E=L}else{if(G==-Infinity){E=O}else{if(G===0){A=0;M=0;B=(1/G===-Infinity)?1:0}else{B=G<0;G=Math.abs(G);if(G>=Math.pow(2,1-K)){F=Math.min(Math.floor(Math.log(G)/Math.LN2),K);A=F+K;M=Math.round(G*Math.pow(2,D-F)-Math.pow(2,D))}else{A=0;M=Math.round(G/Math.pow(2,1-K-D))}}C=[];for(J=D;J;J-=1){C.push(M%2?1:0);M=Math.floor(M/2)}for(J=N;J;J-=1){C.push(A%2?1:0);A=Math.floor(A/2)}C.push(B?1:0);C.reverse();H=C.join("");E=[];while(H.length){E.push(parseInt(H.substring(0,8),2));H=H.substring(8)}}}}return E},write0ShortUtf8String:function(A){var B=this.encodeUtf8String(A),C;C=this.encodeXInt(B.length,2);this.writeBytes(C);this.writeBytes(B)},writeAmfPacket:function(A,C){var B;if(this.config.format!=0){Ext.Error.raise("Trying to write a packet on an AMF3 Encoder. Only AMF0 is supported!")}if(!Ext.isArray(A)){Ext.Error.raise("headers is not an array: "+A)}if(!Ext.isArray(C)){Ext.Error.raise("messages is not an array: "+C)}this.writeBytes([0,0]);this.writeBytes(this.encodeXInt(A.length,2));for(B in A){this.writeAmfHeader(A[B].name,A[B].mustUnderstand,A[B].value)}this.writeBytes(this.encodeXInt(C.length,2));for(B in C){this.writeAmfMessage(C[B].targetUri,C[B].responseUri,C[B].body)}},writeAmfHeader:function(B,A,C){if(this.config.format!=0){Ext.Error.raise("Trying to write a header on an AMF3 Encoder. Only AMF0 is supported!")}if(!Ext.isString(B)){Ext.Error.raise("targetURI is not a string: "+targetUri)}if((typeof(A)!=="boolean")&&!Ext.isBoolean(A)){Ext.Error.raise("mustUnderstand is not a boolean value: "+A)}this.write0ShortUtf8String(B);var D=A?1:0;this.writeByte(D);this.writeBytes(this.encodeXInt(-1,4));this.writeObject(C)},writeAmfMessage:function(C,A,B){if(this.config.format!=0){Ext.Error.raise("Trying to write a message on an AMF3 Encoder. Only AMF0 is supported!")}if(!Ext.isString(C)){Ext.Error.raise("targetURI is not a string: "+C)}if(!Ext.isString(A)){Ext.Error.raise("targetURI is not a string: "+A)}if(!Ext.isArray(B)){Ext.Error.raise("body is not an array: "+typeof(B))}this.write0ShortUtf8String(C);this.write0ShortUtf8String(A);this.writeBytes(this.encodeXInt(-1,4));this.write0StrictArray(B)}});Ext.define("Ext.data.amf.Packet",{config:{headers:[],messages:[],version:0},typeMap:{0:{0:"readDouble",1:"readBoolean",2:"readAmf0String",3:"readAmf0Object",5:"readNull",6:"readUndefined",7:"readReference",8:"readEcmaArray",10:"readStrictArray",11:"readAmf0Date",12:"readLongString",13:"readUnsupported",15:"readAmf0Xml",16:"readTypedObject"},3:{0:"readUndefined",1:"readNull",2:"readFalse",3:"readTrue",4:"readUInt29",5:"readDouble",6:"readAmf3String",7:"readAmf3Xml",8:"readAmf3Date",9:"readAmf3Array",10:"readAmf3Object",11:"readAmf3Xml",12:"readByteArray"}},decode:function(F){var D=this,A=[],B=[],C,E;D.twoPowN52=Math.pow(2,-52);D.twoPow8=Math.pow(2,8);D.setHeaders(A);D.setMessages(B);D.pos=0;if(F instanceof ArrayBuffer){F=new Uint8Array(F)}D.bytes=F;D.strings=[];D.objects=[];D.traits=[];D.setVersion(D.readUInt(2));for(C=D.readUInt(2);C--;){D.getHeaders().push({name:D.readAmf0String(),mustUnderstand:D.readBoolean(),byteLength:D.readUInt(4),value:D.readValue()});D.strings=[];D.objects=[];D.traits=[]}for(E=D.readUInt(2);E--;){D.getMessages().push({targetURI:D.readAmf0String(),responseURI:D.readAmf0String(),byteLength:D.readUInt(4),body:D.readValue()});D.strings=[];D.objects=[];D.traits=[]}D.pos=0;D.bytes=D.strings=D.objects=D.traits=null;return D},decodeValue:function(B){var A=this;A.bytes=B;A.pos=0;A.setVersion(3);A.strings=[];A.objects=[];A.traits=[];return A.readValue()},parseXml:function(A){var B;if(window.DOMParser){B=(new DOMParser()).parseFromString(A,"text/xml")}else{B=new ActiveXObject("Microsoft.XMLDOM");B.loadXML(A)}return B},readAmf0Date:function(){var A=new Date(this.readDouble());this.pos+=2;return A},readAmf0Object:function(B){var C=this,A;B=B||{};C.objects.push(B);while((A=C.readAmf0String())||C.bytes[C.pos]!==9){B[A]=C.readValue()}C.pos++;return B},readAmf0String:function(){return this.readUtf8(this.readUInt(2))},readAmf0Xml:function(){return this.parseXml(this.readLongString())},readAmf3Array:function(){var E=this,B=E.readUInt29(),C,A,F,D;if(B&1){C=(B>>1);A=E.readAmf3String();if(A){F={};E.objects.push(F);do{F[A]=E.readValue()}while((A=E.readAmf3String()));for(D=0;D<C;D++){F[D]=E.readValue()}}else{F=[];E.objects.push(F);for(D=0;D<C;D++){F.push(E.readValue())}}}else{F=E.objects[B>>1]}return F},readAmf3Date:function(){var B=this,A=B.readUInt29(),C;if(A&1){C=new Date(B.readDouble());B.objects.push(C)}else{C=B.objects[A>>1]}return C},readAmf3Object:function(){var G=this,J=G.readUInt29(),A=[],K,C,I,L,B,D,H,F,E;if(J&1){K=(J&7);if(K===3){I=G.readAmf3String();L=!!(J&8);C=(J>>4);for(E=0;E<C;E++){A.push(G.readAmf3String())}B={className:I,dynamic:L,members:A};G.traits.push(B)}else{if((J&3)===1){B=G.traits[J>>2];I=B.className;L=B.dynamic;A=B.members;C=A.length}else{if(K===7){}}}if(I){F=Ext.ClassManager.getByAlias("amf."+I);D=F?new F():{$className:I}}else{D={}}G.objects.push(D);for(E=0;E<C;E++){D[A[E]]=G.readValue()}if(L){while((H=G.readAmf3String())){D[H]=G.readValue()}}if((!F)&&this.converters[I]){D=this.converters[I](D)}}else{D=G.objects[J>>1]}return D},readAmf3String:function(){var B=this,A=B.readUInt29(),C;if(A&1){C=B.readUtf8(A>>1);if(C){B.strings.push(C)}return C}else{return B.strings[A>>1]}},readAmf3Xml:function(){var B=this,A=B.readUInt29(),C;if(A&1){C=B.parseXml(B.readUtf8(A>>1));B.objects.push(C)}else{C=B.objects[A>>1]}return C},readBoolean:function(){return !!this.bytes[this.pos++]},readByteArray:function(){var B=this.readUInt29(),C,A;if(B&1){A=this.pos+(B>>1);C=Array.prototype.slice.call(this.bytes,this.pos,A);this.objects.push(C);this.pos=A}else{C=this.objects[B>>1]}return C},readDouble:function(){var D=this.bytes[this.pos++],E=this.bytes[this.pos++],C=(D>>7)?-1:1,A=(((D&127)<<4)|(E>>4)),F=(E&15),G=A?1:0,B=6;while(B--){F=(F*this.twoPow8)+this.bytes[this.pos++]}if(!A){if(!F){return 0}A=1}if(A===2047){return F?NaN:(Infinity*C)}return C*Math.pow(2,A-1023)*(G+this.twoPowN52*F)},readEcmaArray:function(){this.pos+=4;return this.readAmf0Object()},readFalse:function(){return false},readLongString:function(){return this.readUtf8(this.readUInt(4))},readNull:function(){return null},readReference:function(){return this.objects[this.readUInt(2)]},readStrictArray:function(){var A=this,B=A.readUInt(4),C=[];A.objects.push(C);while(B--){C.push(A.readValue())}return C},readTrue:function(){return true},readTypedObject:function(){var D=this,A=D.readAmf0String(),E,C,B;E=Ext.ClassManager.getByAlias("amf."+A);C=E?new E():{$className:A};B=D.readAmf0Object(C);if((!E)&&this.converters[A]){B=this.converters[A](C)}return B},readUInt:function(C){var A=1,B;B=this.bytes[this.pos++];for(;A<C;++A){B=(B<<8)|this.bytes[this.pos++]}return B},readUInt29:function(){var B=this.bytes[this.pos++],A;if(B&128){A=this.bytes[this.pos++];B=((B&127)<<7)|(A&127);if(A&128){A=this.bytes[this.pos++];B=(B<<7)|(A&127);if(A&128){A=this.bytes[this.pos++];B=(B<<8)|A}}}return B},readUndefined:Ext.emptyFn,readUnsupported:Ext.emptyFn,readUtf8:function(E){var H=this.pos+E,B=[],D=0,J=65535,F=1,A=[],G=0,K,C,I;K=[B];while(this.pos<H){I=this.bytes[this.pos++];if(I>127){if(I>239){C=4;I=(I&7)}else{if(I>223){C=3;I=(I&15)}else{C=2;I=(I&31)}}while(--C){I=((I<<6)|(this.bytes[this.pos++]&63))}}B.push(I);if(++D===J){K.push(B=[]);D=0;F++}}for(;G<F;G++){A.push(String.fromCharCode.apply(String,K[G]))}return A.join("")},readValue:function(){var B=this,A=B.bytes[B.pos++];if(A===17){B.setVersion(3);A=B.bytes[B.pos++]}return B[B.typeMap[B.getVersion()][A]]()},converters:{"flex.messaging.io.ArrayCollection":function(A){return A.source||[]}}});Ext.define("Ext.data.amf.RemotingMessage",{alias:"data.amf.remotingmessage",config:{$flexType:"flex.messaging.messages.RemotingMessage",body:[],clientId:"",destination:"",headers:[],messageId:"",operation:"",source:"",timestamp:[],timeToLive:[]},constructor:function(A){this.initConfig(A)},encodeMessage:function(){var B=Ext.create("Ext.data.amf.XmlEncoder"),A;A=Ext.copyTo({},this.getCurrentConfig(),"$flexType,body,clientId,destination,headers,messageId,operation,source,timestamp,timeToLive",true);B.writeObject(A);return B.getBody()}});Ext.define("Ext.data.amf.XmlDecoder",{alias:"data.amf.xmldecoder",statics:{readXml:function(A){var B;if(window.DOMParser){B=(new DOMParser()).parseFromString(A,"text/xml")}else{B=new ActiveXObject("Microsoft.XMLDOM");B.loadXML(A)}return B},readByteArray:function(B){var D=[],E,A,C;C=B.firstChild.nodeValue;for(A=0;A<C.length;A=A+2){E=C.substr(A,2);D.push(parseInt(E,16))}return D},readAMF3Value:function(A){var B;B=Ext.create("Ext.data.amf.Packet");return B.decodeValue(A)},decodeTidFromFlexUID:function(A){var B;B=A.substr(0,8);return parseInt(B,16)}},constructor:function(A){this.initConfig(A);this.clear()},clear:function(){this.objectReferences=[];this.traitsReferences=[];this.stringReferences=[]},readAmfxMessage:function(D){var F,C,B,A,E={};this.clear();F=Ext.data.amf.XmlDecoder.readXml(D);C=F.getElementsByTagName("amfx")[0];if(!C){Ext.warn.log("No AMFX tag in message")}if(C.getAttribute("ver")!="3"){Ext.Error.raise("Unsupported AMFX version: "+C.getAttribute("ver"))}B=C.getElementsByTagName("body")[0];E.targetURI=B.getAttribute("targetURI");E.responseURI=B.getAttribute("responseURI");for(A=0;A<B.childNodes.length;A++){if(B.childNodes.item(A).nodeType!=1){continue}E.message=this.readValue(B.childNodes.item(A));break}return E},readValue:function(A){var B;if(typeof A.normalize==="function"){A.normalize()}if(A.tagName=="null"){return null}else{if(A.tagName=="true"){return true}else{if(A.tagName=="false"){return false}else{if(A.tagName=="string"){return this.readString(A)}else{if(A.tagName=="int"){return parseInt(A.firstChild.nodeValue)}else{if(A.tagName=="double"){return parseFloat(A.firstChild.nodeValue)}else{if(A.tagName=="date"){B=new Date(parseFloat(A.firstChild.nodeValue));this.objectReferences.push(B);return B}else{if(A.tagName=="dictionary"){return this.readDictionary(A)}else{if(A.tagName=="array"){return this.readArray(A)}else{if(A.tagName=="ref"){return this.readObjectRef(A)}else{if(A.tagName=="object"){return this.readObject(A)}else{if(A.tagName=="xml"){return Ext.data.amf.XmlDecoder.readXml(A.firstChild.nodeValue)}else{if(A.tagName=="bytearray"){return Ext.data.amf.XmlDecoder.readAMF3Value(Ext.data.amf.XmlDecoder.readByteArray(A))}}}}}}}}}}}}}Ext.Error.raise("Unknown tag type: "+A.tagName);return null},readString:function(A){var B;if(A.getAttributeNode("id")){return this.stringReferences[parseInt(A.getAttribute("id"))]}B=(A.firstChild?A.firstChild.nodeValue:"")||"";this.stringReferences.push(B);return B},readTraits:function(B){var D=[],A,C;if(B===null){return null}if(B.getAttribute("externalizable")=="true"){return null}if(B.getAttributeNode("id")){return this.traitsReferences[parseInt(B.getAttributeNode("id").value)]}C=B.childNodes;for(A=0;A<C.length;A++){if(C.item(A).nodeType!=1){continue}D.push(this.readValue(C.item(A)))}this.traitsReferences.push(D);return D},readObjectRef:function(A){var B;B=parseInt(A.getAttribute("id"));return this.objectReferences[B]},readObject:function(G){var I,J=[],C,E,D,F,A,H,B=null,K;K=G.getAttribute("type");if(K){B=Ext.ClassManager.getByAlias("amfx."+K)}I=B?new B():(K?{$className:K}:{});if((!B)&&this.converters[K]){I=this.converters[K](this,G);return I}C=G.getElementsByTagName("traits")[0];J=this.readTraits(C);if(J===null){Ext.Error.raise("No support for externalizable object: "+K)}this.objectReferences.push(I);D=0;for(E=0;E<G.childNodes.length;E++){F=G.childNodes.item(E);if(F.nodeType!=1){continue}if(F.tagName=="traits"){continue}A=J[D];H=this.readValue(F);D=D+1;I[A]=H;if(D>J.length){Ext.Error.raise("Too many items for object, not enough traits: "+K)}}return I},readArray:function(F){var I=[],E,D,B,G,A,H,J,K,C;this.objectReferences.push(I);J=parseInt(F.getAttributeNode("length").value);D=0;for(G=0;G<F.childNodes.length;G++){E=F.childNodes.item(G);if(E.nodeType!=1){continue}if(E.tagName=="item"){A=E.getAttributeNode("name").value;K=E.childNodes;for(B=0;B<K.length;B++){C=K.item(B);if(C.nodeType!=1){continue}H=this.readValue(C);break}I[A]=H}else{I[D]=this.readValue(E);D++;if(D>J){Ext.Error.raise("Array has more items than declared length: "+D+" > "+J)}}}if(D<J){Ext.Error.raise("Array has less items than declared length: "+D+" < "+J)}return I},readDictionary:function(E){var H={},G,C,F,B,D,A;A=parseInt(E.getAttribute("length"));this.objectReferences.push(H);G=null;C=null;B=0;for(F=0;F<E.childNodes.length;F++){D=E.childNodes.item(F);if(D.nodeType!=1){continue}if(!G){G=this.readValue(D);continue}C=this.readValue(D);B=B+1;H[G]=C;G=null;C=null}if(B!=A){Ext.Error.raise("Incorrect number of dictionary values: "+B+" != "+A)}return H},convertObjectWithSourceField:function(C){var A,B,D;for(A=0;A<C.childNodes.length;A++){B=C.childNodes.item(A);if(B.tagName=="bytearray"){D=this.readValue(B);this.objectReferences.push(D);return D}}return null},converters:{"flex.messaging.io.ArrayCollection":function(B,A){return B.convertObjectWithSourceField(A)},"mx.collections.ArrayList":function(B,A){return B.convertObjectWithSourceField(A)},"mx.collections.ArrayCollection":function(B,A){return B.convertObjectWithSourceField(A)}}});Ext.define("Ext.data.amf.XmlEncoder",{alias:"data.amf.xmlencoder",config:{body:""},statics:{generateFlexUID:function(E){var B="",A,D,C;if(E===undefined){E=this.randomInt(0,4294967295)}C=(E+4294967296).toString(16).toUpperCase();B=C.substr(C.length-8,8);for(D=0;D<3;D++){B+="-";for(A=0;A<4;A++){B+=this.randomInt(0,15).toString(16).toUpperCase()}}B+="-";C=new Number(new Date()).valueOf().toString(16).toUpperCase();D=0;if(C.length<8){for(A=0;A<C.length-8;A++){D++;B+="0"}}B+=C.substr(-(8-D));for(A=0;A<4;A++){B+=this.randomInt(0,15).toString(16).toUpperCase()}return B},randomInt:function(A,B){return Math.floor(Math.random()*(B-A+1)+A)}},constructor:function(A){this.initConfig(A);this.clear()},clear:function(){this.setBody("")},encodeUndefined:function(){return this.encodeNull()},writeUndefined:function(){this.write(this.encodeUndefined())},encodeNull:function(){return"<null />"},writeNull:function(){this.write(this.encodeNull())},encodeBoolean:function(B){var A;if(B){A="<true />"}else{A="<false />"}return A},writeBoolean:function(A){this.write(this.encodeBoolean(A))},encodeString:function(A){var B;if(A===""){B="<string />"}else{B="<string>"+A+"</string>"}return B},writeString:function(A){this.write(this.encodeString(A))},encodeInt:function(A){return"<int>"+A.toString()+"</int>"},writeInt:function(A){this.write(this.encodeInt(A))},encodeDouble:function(A){return"<double>"+A.toString()+"</double>"},writeDouble:function(A){this.write(this.encodeDouble(A))},encodeNumber:function(C){var B=536870911,A=-268435455;if(typeof(C)!=="number"&&!(C instanceof Number)){Ext.log.warn("Encoder: writeNumber argument is not numeric. Can't coerce.")}if(C instanceof Number){C=C.valueOf()}if(C%1===0&&C>=A&&C<=B){return this.encodeInt(C)}else{return this.encodeDouble(C)}},writeNumber:function(A){this.write(this.encodeNumber(A))},encodeDate:function(A){return"<date>"+(new Number(A)).toString()+"</date>"},writeDate:function(A){this.write(this.encodeDate(A))},encodeEcmaElement:function(A,C){var B='<item name="'+A.toString()+'">'+this.encodeObject(C)+"</item>";return B},encodeArray:function(B){var A=[],G,E=[],C=B.length,D,F;for(D in B){if(Ext.isNumeric(D)&&(D%1==0)){A[D]=this.encodeObject(B[D])}else{E.push(this.encodeEcmaElement(D,B[D]))}}G=A.length;for(D=0;D<A.length;D++){if(A[D]===undefined){G=D;break}}if(G<A.length){for(D=firstNonOrdinals;D<A.length;D++){if(A[D]!==undefined){E.push(this.encodeEcmaElement(D,A[D]))}}A=A.slice(0,G)}F='<array length="'+A.length+'"';if(E.length>0){F+=' ecma="true"'}F+=">";for(D=0;D<A.length;D++){F+=A[D]}for(D in E){F+=E[D]}F+="</array>";return F},writeArray:function(A){this.write(this.encodeArray(A))},encodeXml:function(B){var A=this.convertXmlToString(B);return"<xml><![CDATA["+A+"]]></xml>"},writeXml:function(A){this.write(this.encodeXml(A))},encodeGenericObject:function(B){var F=[],D=[],C=null,A,E;for(A in B){if(A=="$flexType"){C=B[A]}else{F.push(this.encodeString(new String(A)));D.push(this.encodeObject(B[A]))}}if(C){E='<object type="'+C+'">'}else{E="<object>"}if(F.length>0){E+="<traits>";E+=F.join("");E+="</traits>"}else{E+="<traits />"}E+=D.join("");E+="</object>";return E},writeGenericObject:function(A){this.write(this.encodeGenericObject(A))},encodeByteArray:function(B){var D,C,A;if(B.length>0){D="<bytearray>";for(C=0;C<B.length;C++){if(!Ext.isNumber(B[C])){Ext.Error.raise("Byte array contains a non-number: "+B[C]+" in index: "+C)}if(B[C]<0||B[C]>255){Ext.Error.raise("Byte array value out of bounds: "+B[C])}A=B[C].toString(16).toUpperCase();if(B[C]<16){A="0"+A}D+=A}D+="</bytearray>"}else{D="<bytearray />"}return D},writeByteArray:function(A){this.write(this.encodeByteArray(A))},encodeObject:function(A){var B=typeof(A);if(B==="undefined"){return this.encodeUndefined()}else{if(A===null){return this.encodeNull()}else{if(Ext.isBoolean(A)){return this.encodeBoolean(A)}else{if(Ext.isString(A)){return this.encodeString(A)}else{if(B==="number"||A instanceof Number){return this.encodeNumber(A)}else{if(B==="object"){if(A instanceof Date){return this.encodeDate(A)}else{if(Ext.isArray(A)){return this.encodeArray(A)}else{if(this.isXmlDocument(A)){return this.encodeXml(A)}else{return this.encodeGenericObject(A)}}}}else{Ext.log.warn("AMFX Encoder: Unknown item type "+B+" can't be written to stream: "+A)}}}}}}return null},writeObject:function(A){this.write(this.encodeObject(A))},encodeAmfxRemotingPacket:function(C){var A,B;B='<amfx ver="3" xmlns="http://www.macromedia.com/2005/amfx"><body>';B+=C.encodeMessage();B+="</body></amfx>";return B},writeAmfxRemotingPacket:function(A){this.write(this.encodeAmfxRemotingPacket(A))},convertXmlToString:function(B){var A;if(window.XMLSerializer){A=new window.XMLSerializer().serializeToString(B)}else{A=B.xml}return A},isXmlDocument:function(A){if(window.DOMParser){if(Ext.isDefined(A.doctype)){return true}}if(Ext.isString(A.xml)){return true}return false},write:function(A){this.setBody(this.getBody()+A)}});Ext.define("Ext.direct.AmfRemotingProvider",{alias:"direct.amfremotingprovider",extend:Ext.direct.JsonProvider,config:{namespace:undefined,url:null,endpoint:null,enableUrlEncode:null,binary:false,enableBuffer:10,maxRetries:1,timeout:undefined,actions:{},clientId:null,DSId:null},constructor:function(A){var B=this;B.callParent(arguments);B.transactions=Ext.create("Ext.util.Collection",function(C){return C.getId()});B.callBuffer=[]},applyNamespace:function(A){if(Ext.isString(A)){return Ext.ns(A)}return A||window},initAPI:function(){var A=this.getActions(),H=this.getNamespace(),G,C,D,E,B,F;for(G in A){if(A.hasOwnProperty(G)){C=H[G];if(!C){C=H[G]={}}D=A[G];for(E=0,B=D.length;E<B;++E){F=Ext.create("Ext.direct.RemotingMethod",D[E]);C[F.getName()]=this.createHandler(G,F)}}}},createHandler:function(A,D){var C=this,B;if(!D.getFormHandler()){B=function(){C.configureRequest(A,D,Array.prototype.slice.call(arguments,0))}}else{B=function(F,E,G){C.configureFormRequest(A,D,F,E,G)}}B.directCfg={action:A,method:D};return B},isConnected:function(){return !!this.connected},connect:function(){var A=this;if(A.getUrl()){A.setClientId(Ext.data.amf.XmlEncoder.generateFlexUID());A.initAPI();A.connected=true;A.fireEvent("connect",A);A.setDSId(null)}else{Ext.Error.raise("Error initializing RemotingProvider, no url configured.")}},disconnect:function(){var A=this;if(A.connected){A.connected=false;A.fireEvent("disconnect",A)}},runCallback:function(C,F){var A=!!F.getStatus(),B=A?"success":"failure",E=C&&C.getCallback(),D;if(E){D=F.getResult();if(Ext.isFunction(E)){E(D,F,A)}else{Ext.callback(E[B],E.scope,[D,F,A]);Ext.callback(E.callback,E.scope,[D,F,A])}}},onData:function(G,B,J){var H=this,F=0,D,I,E,A,C;if(B){I=H.createEvents(J);for(D=I.length;F<D;++F){E=I[F];A=H.getTransaction(E);H.fireEvent("data",H,E);if(A){H.runCallback(A,E,true);Ext.direct.Manager.removeTransaction(A)}}}else{C=[].concat(G.transaction);for(D=C.length;F<D;++F){A=H.getTransaction(C[F]);if(A&&A.getRetryCount()<H.getMaxRetries()){A.retry()}else{E=Ext.create("Ext.direct.ExceptionEvent",{data:null,transaction:A,code:Ext.direct.Manager.exceptions.TRANSPORT,message:"Unable to connect to the server.",xhr:J});H.fireEvent("data",H,E);if(A){H.runCallback(A,E,false);Ext.direct.Manager.removeTransaction(A)}}}}},getTransaction:function(A){return A&&A.getTid?Ext.direct.Manager.getTransaction(A.getTid()):null},configureRequest:function(H,G,B){var F=this,I=G.getCallData(B),D=I.data,C=I.callback,E=I.scope,A;A=Ext.create("Ext.direct.Transaction",{provider:F,args:B,action:H,method:G.getName(),data:D,callback:E&&Ext.isFunction(C)?Ext.Function.bind(C,E):C});if(F.fireEvent("beforecall",F,A,G)!==false){Ext.direct.Manager.addTransaction(A);F.queueTransaction(A);F.fireEvent("call",F,A,G)}},getCallData:function(A){if(this.getBinary()){return{targetUri:A.getAction()+"."+A.getMethod(),responseUri:"/"+A.getId(),body:A.getData()||[]}}else{return new Ext.data.amf.RemotingMessage({body:A.data||[],clientId:this.getClientId(),destination:A.getAction(),headers:{DSEndpoint:this.getEndpoint(),DSId:this.getDSId()||"nil"},messageId:Ext.data.amf.XmlEncoder.generateFlexUID(A.getId()),operation:A.getMethod(),timestamp:0,timeToLive:0})}},sendRequest:function(B){var I=this,E={url:I.getUrl(),callback:I.onData,scope:I,transaction:B,timeout:I.getTimeout()},F,K=I.getEnableUrlEncode(),G=0,D,H,A,C=[],J=[];if(Ext.isArray(B)){if(!I.getBinary()){Ext.Error.raise("Mutltiple messages in the same call are not supported in AMFX")}for(G=0,D=B.length;G<D;++G){C.push(I.getCallData(B[G]))}}else{C.push(I.getCallData(B))}if(I.getBinary()){A=new Ext.data.amf.Encoder({format:0});A.writeAmfPacket(J,C);E.binaryData=A.getBytes();E.binary=true;E.headers={"Content-Type":"application/x-amf"}}else{A=new Ext.data.amf.XmlEncoder();A.writeAmfxRemotingPacket(C[0]);E.xmlData=A.getBody()}Ext.Ajax.request(E)},queueTransaction:function(B){var A=this,C=false;if(B.getForm()){A.sendFormRequest(B);return}A.callBuffer.push(B);if(C){if(!A.callTask){A.callTask=Ext.create("Ext.util.DelayedTask",A.combineAndSend,A)}A.callTask.delay(Ext.isNumber(C)?C:10)}else{A.combineAndSend()}},combineAndSend:function(){var A=this.callBuffer,B=A.length;if(B>0){this.sendRequest(B==1?A[0]:A);this.callBuffer=[]}},createEvents:function(I){var D=null,A=[],H=[],E,F=0,G,B;try{if(this.getBinary()){B=new Ext.data.amf.Packet();D=B.decode(I.responseBytes)}else{B=new Ext.data.amf.XmlDecoder();D=B.readAmfxMessage(I.responseText)}}catch(C){E=new Ext.direct.ExceptionEvent({data:C,xhr:I,code:Ext.direct.Manager.exceptions.PARSE,message:"Error parsing AMF response: \n\n "+D});return[E]}if(this.getBinary()){for(F=0;F<D.getMessages().length;F++){H.push(this.createEvent(D.getMessages()[F]))}}else{H.push(this.createEvent(D))}return H},createEvent:function(F){var D=F.targetURI.split("/"),C,E,G,B,A=this;if(A.getBinary()){C=D[1];B=2}else{C=Ext.data.amf.XmlDecoder.decodeTidFromFlexUID(F.message.correlationId);B=1}if(D[B]=="onStatus"){G={tid:C,data:(A.getBinary()?F.body:F.message),code:(A.getBinary()?F.body.code:F.message.faultCode),message:(A.getBinary()?F.body.message:F.message.faultString)};E=Ext.create("direct.exception",G)}else{if(D[B]=="onResult"){G={tid:C,data:(A.getBinary()?F.body:F.message),result:(A.getBinary()?F.body:F.message.body)};E=Ext.create("direct.rpc",G)}else{Ext.Error.raise("Unknown AMF return status: "+D[B])}}return E}});
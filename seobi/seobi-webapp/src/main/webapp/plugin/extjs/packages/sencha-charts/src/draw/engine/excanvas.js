if(!document.createElement("canvas").getContext){(function(){var Ae=Math;var K=Ae.round;var G=Ae.sin;var n=Ae.cos;var r=Ae.abs;var y=Ae.sqrt;var T=10;var q=T/2;var I=+navigator.userAgent.match(/MSIE ([\d.]+)?/)[1];function l(){return this.context_||(this.context_=new A(this))}var S=Array.prototype.slice;function Ac(m,Z,i){var j=S.call(arguments,2);return function(){return m.apply(Z,j.concat(S.call(arguments)))}}function a(Z){return String(Z).replace(/&/g,"&amp;").replace(/"/g,"&quot;")}function P(j,i,Z){Ext.onReady(function(){if(!j.namespaces[i]){j.namespaces.add(i,Z,"#default#VML")}})}function f(i){P(i,"g_vml_","urn:schemas-microsoft-com:vml");P(i,"g_o_","urn:schemas-microsoft-com:office:office");if(!i.styleSheets["ex_canvas_"]){var Z=i.createStyleSheet();Z.owningElement.id="ex_canvas_";Z.cssText="canvas{display:inline-block;overflow:hidden;text-align:left;width:300px;height:150px}"}}f(document);var N={init:function(Z){var i=Z||document;i.createElement("canvas");i.attachEvent("onreadystatechange",Ac(this.init_,this,i))},init_:function(m){var j=m.getElementsByTagName("canvas");for(var Z=0;Z<j.length;Z++){this.initElement(j[Z])}},initElement:function(Z){if(!Z.getContext){Z.getContext=l;f(Z.ownerDocument);Z.innerHTML="";Z.attachEvent("onpropertychange",e);Z.attachEvent("onresize",V);var i=Z.attributes;if(i.width&&i.width.specified){Z.style.width=i.width.nodeValue+"px"}else{Z.width=Z.clientWidth}if(i.height&&i.height.specified){Z.style.height=i.height.nodeValue+"px"}else{Z.height=Z.clientHeight}}return Z}};function e(i){var Z=i.srcElement;switch(i.propertyName){case"width":Z.getContext().clearRect();Z.style.width=Z.attributes.width.nodeValue+"px";Z.firstChild.style.width=Z.clientWidth+"px";break;case"height":Z.getContext().clearRect();Z.style.height=Z.attributes.height.nodeValue+"px";Z.firstChild.style.height=Z.clientHeight+"px";break}}function V(i){var Z=i.srcElement;if(Z.firstChild){Z.firstChild.style.width=Z.clientWidth+"px";Z.firstChild.style.height=Z.clientHeight+"px"}}N.init();var s=[];for(var Ab=0;Ab<16;Ab++){for(var z=0;z<16;z++){s[Ab*16+z]=Ab.toString(16)+z.toString(16)}}function w(){return[[1,0,0],[0,1,0],[0,0,1]]}function b(m,Ah){var Z=w();for(var j=0;j<3;j++){for(var p=0;p<3;p++){var Ag=0;for(var i=0;i<3;i++){Ag+=m[j][i]*Ah[i][p]}Z[j][p]=Ag}}return Z}function Af(Z,i){i.fillStyle=Z.fillStyle;i.lineCap=Z.lineCap;i.lineJoin=Z.lineJoin;i.lineWidth=Z.lineWidth;i.miterLimit=Z.miterLimit;i.shadowBlur=Z.shadowBlur;i.shadowColor=Z.shadowColor;i.shadowOffsetX=Z.shadowOffsetX;i.shadowOffsetY=Z.shadowOffsetY;i.strokeStyle=Z.strokeStyle;i.globalAlpha=Z.globalAlpha;i.font=Z.font;i.textAlign=Z.textAlign;i.textBaseline=Z.textBaseline;i.arcScaleX_=Z.arcScaleX_;i.arcScaleY_=Z.arcScaleY_;i.lineScale_=Z.lineScale_}var h={aliceblue:"#F0F8FF",antiquewhite:"#FAEBD7",aquamarine:"#7FFFD4",azure:"#F0FFFF",beige:"#F5F5DC",bisque:"#FFE4C4",black:"#000000",blanchedalmond:"#FFEBCD",blueviolet:"#8A2BE2",brown:"#A52A2A",burlywood:"#DEB887",cadetblue:"#5F9EA0",chartreuse:"#7FFF00",chocolate:"#D2691E",coral:"#FF7F50",cornflowerblue:"#6495ED",cornsilk:"#FFF8DC",crimson:"#DC143C",cyan:"#00FFFF",darkblue:"#00008B",darkcyan:"#008B8B",darkgoldenrod:"#B8860B",darkgray:"#A9A9A9",darkgreen:"#006400",darkgrey:"#A9A9A9",darkkhaki:"#BDB76B",darkmagenta:"#8B008B",darkolivegreen:"#556B2F",darkorange:"#FF8C00",darkorchid:"#9932CC",darkred:"#8B0000",darksalmon:"#E9967A",darkseagreen:"#8FBC8F",darkslateblue:"#483D8B",darkslategray:"#2F4F4F",darkslategrey:"#2F4F4F",darkturquoise:"#00CED1",darkviolet:"#9400D3",deeppink:"#FF1493",deepskyblue:"#00BFFF",dimgray:"#696969",dimgrey:"#696969",dodgerblue:"#1E90FF",firebrick:"#B22222",floralwhite:"#FFFAF0",forestgreen:"#228B22",gainsboro:"#DCDCDC",ghostwhite:"#F8F8FF",gold:"#FFD700",goldenrod:"#DAA520",grey:"#808080",greenyellow:"#ADFF2F",honeydew:"#F0FFF0",hotpink:"#FF69B4",indianred:"#CD5C5C",indigo:"#4B0082",ivory:"#FFFFF0",khaki:"#F0E68C",lavender:"#E6E6FA",lavenderblush:"#FFF0F5",lawngreen:"#7CFC00",lemonchiffon:"#FFFACD",lightblue:"#ADD8E6",lightcoral:"#F08080",lightcyan:"#E0FFFF",lightgoldenrodyellow:"#FAFAD2",lightgreen:"#90EE90",lightgrey:"#D3D3D3",lightpink:"#FFB6C1",lightsalmon:"#FFA07A",lightseagreen:"#20B2AA",lightskyblue:"#87CEFA",lightslategray:"#778899",lightslategrey:"#778899",lightsteelblue:"#B0C4DE",lightyellow:"#FFFFE0",limegreen:"#32CD32",linen:"#FAF0E6",magenta:"#FF00FF",mediumaquamarine:"#66CDAA",mediumblue:"#0000CD",mediumorchid:"#BA55D3",mediumpurple:"#9370DB",mediumseagreen:"#3CB371",mediumslateblue:"#7B68EE",mediumspringgreen:"#00FA9A",mediumturquoise:"#48D1CC",mediumvioletred:"#C71585",midnightblue:"#191970",mintcream:"#F5FFFA",mistyrose:"#FFE4E1",moccasin:"#FFE4B5",navajowhite:"#FFDEAD",oldlace:"#FDF5E6",olivedrab:"#6B8E23",orange:"#FFA500",orangered:"#FF4500",orchid:"#DA70D6",palegoldenrod:"#EEE8AA",palegreen:"#98FB98",paleturquoise:"#AFEEEE",palevioletred:"#DB7093",papayawhip:"#FFEFD5",peachpuff:"#FFDAB9",peru:"#CD853F",pink:"#FFC0CB",plum:"#DDA0DD",powderblue:"#B0E0E6",rosybrown:"#BC8F8F",royalblue:"#4169E1",saddlebrown:"#8B4513",salmon:"#FA8072",sandybrown:"#F4A460",seagreen:"#2E8B57",seashell:"#FFF5EE",sienna:"#A0522D",skyblue:"#87CEEB",slateblue:"#6A5ACD",slategray:"#708090",slategrey:"#708090",snow:"#FFFAFA",springgreen:"#00FF7F",steelblue:"#4682B4",tan:"#D2B48C",thistle:"#D8BFD8",tomato:"#FF6347",turquoise:"#40E0D0",violet:"#EE82EE",wheat:"#F5DEB3",whitesmoke:"#F5F5F5",yellowgreen:"#9ACD32"};function O(i){var m=i.indexOf("(",3);var j=i.indexOf(")",m+1);var Z=i.substring(m+1,j).split(",");if(Z.length!=4||i.charAt(3)!="a"){Z[3]=1}return Z}function F(Z){return parseFloat(Z)/100}function C(i,Z,j){return Math.min(j,Math.max(Z,i))}function W(Aj){var Z,Ak,Ai,Ag,i,Ah;Ag=parseFloat(Aj[0])/360%360;if(Ag<0){Ag++}i=C(F(Aj[1]),0,1);Ah=C(F(Aj[2]),0,1);if(i==0){Z=Ak=Ai=Ah}else{var m=Ah<0.5?Ah*(1+i):Ah+i-Ah*i;var j=2*Ah-m;Z=U(j,m,Ag+1/3);Ak=U(j,m,Ag);Ai=U(j,m,Ag-1/3)}return"#"+s[Math.floor(Z*255)]+s[Math.floor(Ak*255)]+s[Math.floor(Ai*255)]}function U(i,j,Z){if(Z<0){Z++}if(Z>1){Z--}if(6*Z<1){return i+(j-i)*6*Z}else{if(2*Z<1){return j}else{if(3*Z<2){return i+(j-i)*(2/3-Z)*6}else{return i}}}}var B={};function k(Ag){if(Ag in B){return B[Ag]}var m,Ah=1;Ag=String(Ag);if(Ag.charAt(0)=="#"){m=Ag}else{if(/^rgb/.test(Ag)){var Z=O(Ag);var m="#",p;for(var j=0;j<3;j++){if(Z[j].indexOf("%")!=-1){p=Math.floor(F(Z[j])*255)}else{p=+Z[j]}m+=s[C(p,0,255)]}Ah=+Z[3]}else{if(/^hsl/.test(Ag)){var Z=O(Ag);m=W(Z);Ah=Z[3]}else{m=h[Ag]||Ag}}}return B[Ag]={color:m,alpha:Ah}}var v={style:"normal",variant:"normal",weight:"normal",size:10,family:"sans-serif"};var H={};function X(m){if(H[m]){return H[m]}var Z=document.createElement("div");var j=Z.style;try{j.font=m}catch(i){}return H[m]={style:j.fontStyle||v.style,variant:j.fontVariant||v.variant,weight:j.fontWeight||v.weight,size:j.fontSize||v.size,family:j.fontFamily||v.family}}function D(j,Ag){var Z={};for(var i in j){Z[i]=j[i]}var m=parseFloat(Ag.currentStyle.fontSize),Ah=parseFloat(j.size);if(typeof j.size=="number"){Z.size=j.size}else{if(j.size.indexOf("px")!=-1){Z.size=Ah}else{if(j.size.indexOf("em")!=-1){Z.size=m*Ah}else{if(j.size.indexOf("%")!=-1){Z.size=(m/100)*Ah}else{if(j.size.indexOf("pt")!=-1){Z.size=Ah/0.75}else{Z.size=m}}}}}Z.size*=0.981;return Z}function o(Z){return Z.style+" "+Z.variant+" "+Z.weight+" "+Z.size+"px "+Z.family}var E={"butt":"flat","round":"round"};function Ad(Z){return E[Z]||"square"}function A(i){this.m_=w();this.mStack_=[];this.aStack_=[];this.currentPath_=[];this.strokeStyle="#000";this.fillStyle="#000";this.lineWidth=1;this.lineJoin="miter";this.lineCap="butt";this.miterLimit=T*1;this.globalAlpha=1;this.font="10px sans-serif";this.textAlign="left";this.textBaseline="alphabetic";this.canvas=i;var m="width:"+i.clientWidth+"px;height:"+i.clientHeight+"px;overflow:hidden;position:absolute";var Z=i.ownerDocument.createElement("div");Z.style.cssText=m;i.appendChild(Z);var j=Z.cloneNode(false);j.style.backgroundColor="red";j.style.filter="alpha(opacity=0)";i.appendChild(j);this.element_=Z;this.arcScaleX_=1;this.arcScaleY_=1;this.lineScale_=1}var c=A.prototype;c.clearRect=function(){if(this.textMeasureEl_){this.textMeasureEl_.removeNode(true);this.textMeasureEl_=null}this.element_.innerHTML=""};c.beginPath=function(){this.currentPath_=[]};c.moveTo=function(j,Z){var i=L(this,j,Z);this.currentPath_.push({type:"moveTo",x:i.x,y:i.y});this.currentX_=i.x;this.currentY_=i.y};c.lineTo=function(j,Z){var i=L(this,j,Z);this.currentPath_.push({type:"lineTo",x:i.x,y:i.y});this.currentX_=i.x;this.currentY_=i.y};c.bezierCurveTo=function(Ag,m,j,i,Ah,Ai){var Z=L(this,Ah,Ai);var Aj=L(this,Ag,m);var Ak=L(this,j,i);R(this,Aj,Ak,Z)};function R(Z,i,m,j){Z.currentPath_.push({type:"bezierCurveTo",cp1x:i.x,cp1y:i.y,cp2x:m.x,cp2y:m.y,x:j.x,y:j.y});Z.currentX_=j.x;Z.currentY_=j.y}c.quadraticCurveTo=function(Ai,Ah,i,j){var Aj=L(this,Ai,Ah);var Z=L(this,i,j);var m={x:this.currentX_+2/3*(Aj.x-this.currentX_),y:this.currentY_+2/3*(Aj.y-this.currentY_)};var Ag={x:m.x+(Z.x-this.currentX_)/3,y:m.y+(Z.y-this.currentY_)/3};R(this,m,Ag,Z)};c.arc=function(Ai,Am,Ak,j,m,i){Ak*=T;var Z=i?"at":"wa";var Ao=Ai+n(j)*Ak-q;var Ap=Am+G(j)*Ak-q;var An=Ai+n(m)*Ak-q;var Aj=Am+G(m)*Ak-q;if(Ao==An&&!i){Ao+=0.125}var Ag=L(this,Ai,Am);var Ah=L(this,Ao,Ap);var Al=L(this,An,Aj);this.currentPath_.push({type:Z,x:Ag.x,y:Ag.y,radius:Ak,xStart:Ah.x,yStart:Ah.y,xEnd:Al.x,yEnd:Al.y})};c.rect=function(i,Z,j,m){this.moveTo(i,Z);this.lineTo(i+j,Z);this.lineTo(i+j,Z+m);this.lineTo(i,Z+m);this.closePath()};c.strokeRect=function(i,Z,j,m){var p=this.currentPath_;this.beginPath();this.moveTo(i,Z);this.lineTo(i+j,Z);this.lineTo(i+j,Z+m);this.lineTo(i,Z+m);this.closePath();this.stroke();this.currentPath_=p};c.fillRect=function(i,Z,j,m){var p=this.currentPath_;this.beginPath();this.moveTo(i,Z);this.lineTo(i+j,Z);this.lineTo(i+j,Z+m);this.lineTo(i,Z+m);this.closePath();this.fill();this.currentPath_=p};c.createLinearGradient=function(i,m,Z,j){var p=new Aa("gradient");p.x0_=i;p.y0_=m;p.x1_=Z;p.y1_=j;return p};c.createRadialGradient=function(i,Ag,Ah,Z,p,j){var m=new Aa("gradientradial");m.x0_=i;m.y0_=Ag;m.r0_=Ah;m.x1_=Z;m.y1_=p;m.r1_=j;return m};c.drawImage=function(As,j){var p,Ao,Ap,Am,Aq,Ag,Ah,Z;var Ai=As.runtimeStyle.width;var Au=As.runtimeStyle.height;As.runtimeStyle.width="auto";As.runtimeStyle.height="auto";var An=As.width;var i=As.height;As.runtimeStyle.width=Ai;As.runtimeStyle.height=Au;if(arguments.length==3){p=arguments[1];Ao=arguments[2];Aq=Ag=0;Ah=Ap=An;Z=Am=i}else{if(arguments.length==5){p=arguments[1];Ao=arguments[2];Ap=arguments[3];Am=arguments[4];Aq=Ag=0;Ah=An;Z=i}else{if(arguments.length==9){Aq=arguments[1];Ag=arguments[2];Ah=arguments[3];Z=arguments[4];p=arguments[5];Ao=arguments[6];Ap=arguments[7];Am=arguments[8]}else{throw Error("Invalid number of arguments")}}}var Ak=L(this,p,Ao);var Ar=[];var Aj=10;var Al=10;var At=this.m_;Ar.push(" <g_vml_:group",' coordsize="',T*Aj,",",T*Al,'"',' coordorigin="0,0"',' style="width:',K(Aj*At[0][0]),"px;height:",K(Al*At[1][1]),"px;position:absolute;","top:",K(Ak.y/T),"px;left:",K(Ak.x/T),"px; rotation:",K(Math.atan(At[0][1]/At[1][1])*180/Math.PI),";");Ar.push('" >','<g_vml_:image src="',As.src,'"',' style="width:',T*Ap,"px;"," height:",T*Am,'px"',' cropleft="',Aq/An,'"',' croptop="',Ag/i,'"',' cropright="',(An-Aq-Ah)/An,'"',' cropbottom="',(i-Ag-Z)/i,'"'," />","</g_vml_:group>");this.element_.insertAdjacentHTML("BeforeEnd",Ar.join(""))};c.stroke=function(Ai){var Al=[];var Aj=10;var Z=10;Al.push("<g_vml_:shape",' filled="',!!Ai,'"',' style="position:absolute;width:',Aj,"px;height:",Z,'px;left:0px;top:0px;"',' coordorigin="0,0"',' coordsize="',T*Aj,",",T*Z,'"',' stroked="',!Ai,'"',' path="');var m={x:null,y:null};var Ag={x:null,y:null};for(var Ah=0;Ah<this.currentPath_.length;Ah++){var j=this.currentPath_[Ah];var Ak;switch(j.type){case"moveTo":Ak=j;Al.push(" m ",K(j.x),",",K(j.y));break;case"lineTo":Al.push(" l ",K(j.x),",",K(j.y));break;case"close":Al.push(" x ");j=null;break;case"bezierCurveTo":Al.push(" c ",K(j.cp1x),",",K(j.cp1y),",",K(j.cp2x),",",K(j.cp2y),",",K(j.x),",",K(j.y));break;case"at":case"wa":Al.push(" ",j.type," ",K(j.x-this.arcScaleX_*j.radius),",",K(j.y-this.arcScaleY_*j.radius)," ",K(j.x+this.arcScaleX_*j.radius),",",K(j.y+this.arcScaleY_*j.radius)," ",K(j.xStart),",",K(j.yStart)," ",K(j.xEnd),",",K(j.yEnd));break}if(j){if(m.x==null||j.x<m.x){m.x=j.x}if(Ag.x==null||j.x>Ag.x){Ag.x=j.x}if(m.y==null||j.y<m.y){m.y=j.y}if(Ag.y==null||j.y>Ag.y){Ag.y=j.y}}}Al.push(' ">');if(!Ai){M(this,Al)}else{t(this,Al,m,Ag)}Al.push("</g_vml_:shape>");this.element_.insertAdjacentHTML("beforeEnd",Al.join(""))};function M(p,Z){var Ag=k(p.strokeStyle);var i=Ag.color;var j=Ag.alpha*p.globalAlpha;var m=p.lineScale_*p.lineWidth;if(m<1){j*=m}Z.push("<g_vml_:stroke",' opacity="',j,'"',' joinstyle="',p.lineJoin,'"',' miterlimit="',p.miterLimit,'"',' endcap="',Ad(p.lineCap),'"',' weight="',m,'px"',' color="',i,'" />')}function t(Ah,j,AA,AJ){var Aj=Ah.fillStyle;var At=Ah.arcScaleX_;var p=Ah.arcScaleY_;var Ag=AJ.x-AA.x;var Ak=AJ.y-AA.y;if(Aj instanceof Aa){var Aq=0;var AF={x:0,y:0};var AK=0;var AG=1;if(Aj.type_=="gradient"){var Ar=Aj.x0_/At;var AH=Aj.y0_/p;var Am=Aj.x1_/At;var An=Aj.y1_/p;var Au=L(Ah,Ar,AH);var Ap=L(Ah,Am,An);var AD=Ap.x-Au.x;var Ax=Ap.y-Au.y;Aq=Math.atan2(AD,Ax)*180/Math.PI;if(Aq<0){Aq+=360}if(Aq<1e-06){Aq=0}}else{var Au=L(Ah,Aj.x0_,Aj.y0_);AF={x:(Au.x-AA.x)/Ag,y:(Au.y-AA.y)/Ak};Ag/=At*T;Ak/=p*T;var Z=Ae.max(Ag,Ak);AK=2*Aj.r0_/Z;AG=2*Aj.r1_/Z-AK}var Av=Aj.colors_;Av.sort(function(i,AM){return i.offset-AM.offset});var AC=Av.length;var AI=Av[0].color;var Aw=Av[AC-1].color;var Az=Av[0].alpha*Ah.globalAlpha;var Al=Av[AC-1].alpha*Ah.globalAlpha;var As=[];for(var Ao=0;Ao<AC;Ao++){var Ay=Av[Ao];As.push(Ay.offset*AG+AK+" "+Ay.color)}j.push('<g_vml_:fill type="',Aj.type_,'"',' method="none" focus="100%"',' color="',AI,'"',' color2="',Aw,'"',' colors="',As.join(","),'"',' opacity="',Al,'"',' g_o_:opacity2="',Az,'"',' angle="',Aq,'"',' focusposition="',AF.x,",",AF.y,'" />')}else{if(Aj instanceof Y){if(Ag&&Ak){var AB=-AA.x;var AE=-AA.y;j.push("<g_vml_:fill",' position="',AB/Ag*At*At,",",AE/Ak*p*p,'"',' type="tile"',' src="',Aj.src_,'" />')}}else{var AL=k(Ah.fillStyle);var m=AL.color;var Ai=AL.alpha*Ah.globalAlpha;j.push('<g_vml_:fill color="',m,'" opacity="',Ai,'" />')}}}c.fill=function(){this.$stroke(true)};c.closePath=function(){this.currentPath_.push({type:"close"})};function L(p,j,Z){var i=p.m_;return{x:T*(j*i[0][0]+Z*i[1][0]+i[2][0])-q,y:T*(j*i[0][1]+Z*i[1][1]+i[2][1])-q}}c.save=function(){var Z={};Af(this,Z);this.aStack_.push(Z);this.mStack_.push(this.m_);this.m_=b(w(),this.m_)};c.restore=function(){if(this.aStack_.length){Af(this.aStack_.pop(),this);this.m_=this.mStack_.pop()}};function g(Z){return isFinite(Z[0][0])&&isFinite(Z[0][1])&&isFinite(Z[1][0])&&isFinite(Z[1][1])&&isFinite(Z[2][0])&&isFinite(Z[2][1])}function d(p,i,j){if(!g(i)){return}p.m_=i;if(j){var Z=i[0][0]*i[1][1]-i[0][1]*i[1][0];p.lineScale_=y(r(Z))}}c.translate=function(j,Z){var i=[[1,0,0],[0,1,0],[j,Z,1]];d(this,b(i,this.m_),false)};c.rotate=function(i){var m=n(i);var Z=G(i);var j=[[m,Z,0],[-Z,m,0],[0,0,1]];d(this,b(j,this.m_),false)};c.scale=function(j,Z){this.arcScaleX_*=j;this.arcScaleY_*=Z;var i=[[j,0,0],[0,Z,0],[0,0,1]];d(this,b(i,this.m_),true)};c.transform=function(p,m,Ah,Ag,j,Z){var i=[[p,m,0],[Ah,Ag,0],[j,Z,1]];d(this,b(i,this.m_),true)};c.setTransform=function(p,j,Ai,Ah,i,Z){var Ag=[[p,j,0],[Ai,Ah,0],[i,Z,1]];d(this,Ag,true)};c.drawText_=function(Z,As,At,Ag,An){var Ah=this.m_,Ak=1000,Aq=0,p=Ak,Am={x:0,y:0},Ap=[];var j=D(X(this.font),this.element_);var Ao=o(j);var Ar=this.element_.currentStyle;var Aj=this.textAlign.toLowerCase();switch(Aj){case"left":case"center":case"right":break;case"end":Aj=Ar.direction=="ltr"?"right":"left";break;case"start":Aj=Ar.direction=="rtl"?"right":"left";break;default:Aj="left"}switch(this.textBaseline){case"hanging":case"top":Am.y=j.size/1.75;break;case"middle":break;default:case null:case"alphabetic":case"ideographic":case"bottom":Am.y=-j.size/2.25;break}switch(Aj){case"right":Aq=Ak;p=0.05;break;case"center":Aq=p=Ak/2;break}var Al=L(this,As+Am.x,At+Am.y);Ap.push('<g_vml_:line from="',-Aq,' 0" to="',p,' 0.05" ',' coordsize="100 100" coordorigin="0 0"',' filled="',!An,'" stroked="',!!An,'" style="position:absolute;width:1px;height:1px;left:0px;top:0px;">');if(An){M(this,Ap)}else{t(this,Ap,{x:-Aq,y:0},{x:p,y:j.size})}var Ai=Ah[0][0].toFixed(3)+","+Ah[1][0].toFixed(3)+","+Ah[0][1].toFixed(3)+","+Ah[1][1].toFixed(3)+",0,0";var i=K(Al.x/T)+","+K(Al.y/T);Ap.push('<g_vml_:skew on="t" matrix="',Ai,'" ',' offset="',i,'" origin="',Aq,' 0" />','<g_vml_:path textpathok="true" />','<g_vml_:textpath on="true" string="',a(Z),'" style="v-text-align:',Aj,";font:",a(Ao),'" /></g_vml_:line>');this.element_.insertAdjacentHTML("beforeEnd",Ap.join(""))};c.fillText=function(i,j,m,Z){this.drawText_(i,j,m,Z,false)};c.strokeText=function(i,j,m,Z){this.drawText_(i,j,m,Z,true)};c.measureText=function(i){if(!this.textMeasureEl_){var Z='<span style="position:absolute;top:-20000px;left:0;padding:0;margin:0;border:none;white-space:pre;"></span>';this.element_.insertAdjacentHTML("beforeEnd",Z);this.textMeasureEl_=this.element_.lastChild}var j=this.element_.ownerDocument;this.textMeasureEl_.innerHTML="";this.textMeasureEl_.style.font=this.font;this.textMeasureEl_.appendChild(j.createTextNode(i));return{width:this.textMeasureEl_.offsetWidth}};c.clip=function(){};c.arcTo=function(){};c.createPattern=function(i,Z){return new Y(i,Z)};function Aa(Z){this.type_=Z;this.x0_=0;this.y0_=0;this.r0_=0;this.x1_=0;this.y1_=0;this.r1_=0;this.colors_=[]}Aa.prototype.addColorStop=function(Z,i){i=k(i);this.colors_.push({offset:Z,color:i.color,alpha:i.alpha})};function Y(i,Z){J(i);switch(Z){case"repeat":case null:case"":this.repetition_="repeat";break;case"repeat-x":case"repeat-y":case"no-repeat":this.repetition_=Z;break;default:x("SYNTAX_ERR")}this.src_=i.src;this.width_=i.width;this.height_=i.height}function x(Z){throw new Q(Z)}function J(Z){if(!Z||Z.nodeType!=1||Z.tagName!="IMG"){x("TYPE_MISMATCH_ERR")}if(Z.readyState!="complete"){x("INVALID_STATE_ERR")}}function Q(Z){this.code=this[Z];this.message=Z+": DOM Exception "+this.code}var u=Q.prototype=new Error;u.INDEX_SIZE_ERR=1;u.DOMSTRING_SIZE_ERR=2;u.HIERARCHY_REQUEST_ERR=3;u.WRONG_DOCUMENT_ERR=4;u.INVALID_CHARACTER_ERR=5;u.NO_DATA_ALLOWED_ERR=6;u.NO_MODIFICATION_ALLOWED_ERR=7;u.NOT_FOUND_ERR=8;u.NOT_SUPPORTED_ERR=9;u.INUSE_ATTRIBUTE_ERR=10;u.INVALID_STATE_ERR=11;u.SYNTAX_ERR=12;u.INVALID_MODIFICATION_ERR=13;u.NAMESPACE_ERR=14;u.INVALID_ACCESS_ERR=15;u.VALIDATION_ERR=16;u.TYPE_MISMATCH_ERR=17;G_vmlCanvasManager=N;CanvasRenderingContext2D=A;CanvasGradient=Aa;CanvasPattern=Y;DOMException=Q})()};
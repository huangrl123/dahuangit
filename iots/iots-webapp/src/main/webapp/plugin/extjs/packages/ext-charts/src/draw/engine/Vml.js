Ext.define("Ext.draw.engine.Vml",{extend:"Ext.draw.Surface",requires:["Ext.draw.Draw","Ext.draw.Color","Ext.draw.Sprite","Ext.draw.Matrix","Ext.Element"],engine:"Vml",map:{M:"m",L:"l",C:"c",Z:"x",m:"t",l:"r",c:"v",z:"x"},bitesRe:/([clmz]),?([^clmz]*)/gi,valRe:/-?[^,\s\-]+/g,fillUrlRe:/^url\(\s*['"]?([^\)]+?)['"]?\s*\)$/i,pathlike:/^(path|rect)$/,NonVmlPathRe:/[ahqstv]/ig,partialPathRe:/[clmz]/g,fontFamilyRe:/^['"]+|['"]+$/g,baseVmlCls:Ext.baseCSSPrefix+"vml-base",vmlGroupCls:Ext.baseCSSPrefix+"vml-group",spriteCls:Ext.baseCSSPrefix+"vml-sprite",measureSpanCls:Ext.baseCSSPrefix+"vml-measure-span",zoom:21600,coordsize:1000,coordorigin:"0 0",zIndexShift:0,orderSpritesByZIndex:false,path2vml:function(L){var M=this,J=M.NonVmlPathRe,O=M.map,I=M.valRe,C=M.zoom,B=M.bitesRe,H=Ext.Function.bind(Ext.draw.Draw.pathToAbsolute,Ext.draw.Draw),N,G,D,A,K,E,F,P;if(String(L).match(J)){H=Ext.Function.bind(Ext.draw.Draw.path2curve,Ext.draw.Draw)}else{if(!String(L).match(M.partialPathRe)){N=String(L).replace(B,function(Q,T,V){var U=[],R=T.toLowerCase()=="m",S=O[T];V.replace(I,function(W){if(R&&U.length===2){S+=U+O[T=="m"?"l":"L"];U=[]}U.push(Math.round(W*C))});return S+U});return N}}G=H(L);N=[];for(K=0,E=G.length;K<E;K++){D=G[K];A=G[K][0].toLowerCase();if(A=="z"){A="x"}for(F=1,P=D.length;F<P;F++){A+=Math.round(D[F]*M.zoom)+(F!=P-1?",":"")}N.push(A)}return N.join(" ")},translateAttrs:{radius:"r",radiusX:"rx",radiusY:"ry",lineWidth:"stroke-width",fillOpacity:"fill-opacity",strokeOpacity:"stroke-opacity",strokeLinejoin:"stroke-linejoin"},minDefaults:{circle:{fill:"none",stroke:null,"stroke-width":null,opacity:null,"fill-opacity":null,"stroke-opacity":null},ellipse:{cx:0,cy:0,rx:0,ry:0,fill:"none",stroke:null,"stroke-width":null,opacity:null,"fill-opacity":null,"stroke-opacity":null},rect:{x:0,y:0,width:0,height:0,rx:0,ry:0,fill:"none",stroke:null,"stroke-width":null,opacity:null,"fill-opacity":null,"stroke-opacity":null},text:{x:0,y:0,"text-anchor":"start",font:'10px "Arial"',fill:"#000",stroke:null,"stroke-width":null,opacity:null,"fill-opacity":null,"stroke-opacity":null},path:{d:"M0,0",fill:"none",stroke:null,"stroke-width":null,opacity:null,"fill-opacity":null,"stroke-opacity":null},image:{x:0,y:0,width:0,height:0,preserveAspectRatio:"none",opacity:null}},onMouseEnter:function(A){this.fireEvent("mouseenter",A)},onMouseLeave:function(A){this.fireEvent("mouseleave",A)},processEvent:function(B,A){var C=A.getTarget(),D;this.fireEvent(B,A);D=this.items.get(C.id);if(D){D.fireEvent(B,D,A)}},createSpriteElement:function(A){var H=this,D=A.attr,J=A.type,B=H.zoom,I=A.vml||(A.vml={}),E=(J==="image")?H.createNode("image"):H.createNode("shape"),G,F,C;E.coordsize=B+" "+B;E.coordorigin=D.coordorigin||"0 0";Ext.get(E).addCls(H.spriteCls);if(J=="text"){I.path=G=H.createNode("path");G.textpathok=true;I.textpath=C=H.createNode("textpath");C.on=true;E.appendChild(C);E.appendChild(G)}E.id=A.id;A.el=Ext.get(E);A.el.setStyle("zIndex",-H.zIndexShift);H.el.appendChild(E);if(J!=="image"){F=H.createNode("skew");F.on=true;E.appendChild(F);A.skew=F}A.matrix=new Ext.draw.Matrix();A.bbox={plain:null,transform:null};this.applyAttrs(A);this.applyTransformations(A);A.fireEvent("render",A);return A.el},getBBoxText:function(B){var A=B.vml;return{x:A.X+(A.bbx||0)-A.W/2,y:A.Y-A.H/2,width:A.W,height:A.H}},applyAttrs:function(B){var N=this,L=B.group,O=B.attr,I=B.el,D=I.dom,K,A,J,F,C,G,H,E,M;if(L){A=[].concat(L);F=A.length;for(J=0;J<F;J++){L=A[J];N.getGroup(L).add(B)}delete B.group}C=N.scrubAttrs(B)||{};if(B.zIndexDirty){N.setZIndex(B)}Ext.applyIf(C,N.minDefaults[B.type]);if(B.type=="image"){Ext.apply(B.attr,{x:C.x,y:C.y,width:C.width,height:C.height});I.setStyle({width:C.width+"px",height:C.height+"px"});D.src=C.src}if(D.href){D.href=C.href}if(D.title){D.title=C.title}if(D.target){D.target=C.target}if(D.cursor){D.cursor=C.cursor}if(B.dirtyHidden){(C.hidden)?N.hidePrim(B):N.showPrim(B);B.dirtyHidden=false}if(B.dirtyPath){if(B.type=="circle"||B.type=="ellipse"){G=C.x;H=C.y;E=C.rx||C.r||0;M=C.ry||C.r||0;D.path=Ext.String.format("ar{0},{1},{2},{3},{4},{1},{4},{1}",Math.round((G-E)*N.zoom),Math.round((H-M)*N.zoom),Math.round((G+E)*N.zoom),Math.round((H+M)*N.zoom),Math.round(G*N.zoom));B.dirtyPath=false}else{B.attr.path=C.path=N.setPaths(B,C)||C.path;D.path=N.path2vml(C.path);B.dirtyPath=false}}if("clip-rect" in C){N.setClip(B,C)}if(B.type=="text"){N.setTextAttributes(B,C)}if(C.opacity||C["stroke-opacity"]||C.fill){N.setFill(B,C)}if(C.stroke||C["stroke-opacity"]||C.fill){N.setStroke(B,C)}K=O.style;if(K){I.setStyle(K)}B.dirty=false},setZIndex:function(B){var F=this,E=B.attr.zIndex,C=F.zIndexShift,G,A,H,D;if(E<C){G=F.items.items;A=G.length;for(D=0;D<A;D++){if((E=G[D].attr.zIndex)&&E<C){C=E}}F.zIndexShift=C;for(D=0;D<A;D++){H=G[D];if(H.el){H.el.setStyle("zIndex",H.attr.zIndex-C)}H.zIndexDirty=false}}else{if(B.el){B.el.setStyle("zIndex",E-C);B.zIndexDirty=false}}},setPaths:function(B,C){var A=B.attr;B.bbox.plain=null;B.bbox.transform=null;if(B.type=="circle"){A.rx=A.ry=C.r;return Ext.draw.Draw.ellipsePath(B)}else{if(B.type=="ellipse"){A.rx=C.rx;A.ry=C.ry;return Ext.draw.Draw.ellipsePath(B)}else{if(B.type=="rect"){A.rx=A.ry=C.r;return Ext.draw.Draw.rectPath(B)}else{if(B.type=="path"&&A.path){return Ext.draw.Draw.pathToAbsolute(A.path)}}}}return false},setFill:function(B,G){var I=this,F=B.el.dom,A=F.fill,C=false,H,J,E,D;if(!A){A=F.fill=I.createNode("fill");C=true}if(Ext.isArray(G.fill)){G.fill=G.fill[0]}if(G.fill=="none"){A.on=false}else{if(typeof G.opacity=="number"){A.opacity=G.opacity}if(typeof G["fill-opacity"]=="number"){A.opacity=G["fill-opacity"]}A.on=true;if(typeof G.fill=="string"){J=G.fill.match(I.fillUrlRe);if(J){J=J[1];if(J.charAt(0)=="#"){H=I.gradientsColl.getByKey(J.substring(1))}if(H){E=G.rotation;D=-(H.angle+270+(E?E.degrees:0))%360;if(D===0){D=180}A.angle=D;A.type="gradient";A.method="sigma";if(A.colors){A.colors.value=H.colors}else{A.colors=H.colors}}else{A.src=J;A.type="tile"}}else{A.color=Ext.draw.Color.toHex(G.fill);A.src="";A.type="solid"}}}if(C){F.appendChild(A)}},setStroke:function(A,E){var G=this,D=A.el.dom,C=A.strokeEl,H=false,F,B;if(!C){C=A.strokeEl=G.createNode("stroke");H=true}if(Ext.isArray(E.stroke)){E.stroke=E.stroke[0]}if(!E.stroke||E.stroke=="none"||E.stroke==0||E["stroke-width"]==0){C.on=false}else{C.on=true;if(E.stroke&&!E.stroke.match(G.fillUrlRe)){C.color=Ext.draw.Color.toHex(E.stroke)}C.dashstyle=E["stroke-dasharray"]?"dash":"solid";C.joinstyle=E["stroke-linejoin"];C.endcap=E["stroke-linecap"]||"round";C.miterlimit=E["stroke-miterlimit"]||8;F=parseFloat(E["stroke-width"]||1)*0.75;B=E["stroke-opacity"]||1;if(Ext.isNumber(F)&&F<1){C.weight=1;C.opacity=B*F}else{C.weight=F;C.opacity=B}}if(H){D.appendChild(C)}},setClip:function(D,E){var C=this,A=D.clipEl,B=String(E["clip-rect"]).split(C.separatorRe);if(!A){A=D.clipEl=C.el.insertFirst(Ext.getDoc().dom.createElement("div"));A.addCls(Ext.baseCSSPrefix+"vml-sprite")}if(B.length==4){B[2]=+B[2]+(+B[0]);B[3]=+B[3]+(+B[1]);A.setStyle("clip",Ext.String.format("rect({1}px {2}px {3}px {0}px)",B[0],B[1],B[2],B[3]));A.setSize(C.el.width,C.el.height)}else{A.setStyle("clip","")}},setTextAttributes:function(A,G){var H=this,I=A.vml,E=I.textpath.style,B=H.span.style,D=H.zoom,F={fontSize:"font-size",fontWeight:"font-weight",fontStyle:"font-style"},J,C;if(A.dirtyFont){if(G.font){E.font=B.font=G.font}if(G["font-family"]){E.fontFamily='"'+G["font-family"].split(",")[0].replace(H.fontFamilyRe,"")+'"';B.fontFamily=G["font-family"]}for(J in F){C=G[F[J]];if(C){E[J]=B[J]=C}}H.setText(A,G.text);if(I.textpath.string){H.span.innerHTML=String(I.textpath.string).replace(/</g,"&#60;").replace(/&/g,"&#38;").replace(/\n/g,"<br/>")}I.W=H.span.offsetWidth;I.H=H.span.offsetHeight+2;if(G["text-anchor"]=="middle"){E["v-text-align"]="center"}else{if(G["text-anchor"]=="end"){E["v-text-align"]="right";I.bbx=-Math.round(I.W/2)}else{E["v-text-align"]="left";I.bbx=Math.round(I.W/2)}}}I.X=G.x;I.Y=G.y;I.path.v=Ext.String.format("m{0},{1}l{2},{1}",Math.round(I.X*D),Math.round(I.Y*D),Math.round(I.X*D)+1);A.bbox.plain=null;A.bbox.transform=null;A.dirtyFont=false},setText:function(B,A){B.vml.textpath.string=Ext.htmlDecode(A)},hide:function(){this.el.hide()},show:function(){this.el.show()},hidePrim:function(A){A.el.addCls(Ext.baseCSSPrefix+"hide-visibility")},showPrim:function(A){A.el.removeCls(Ext.baseCSSPrefix+"hide-visibility")},setSize:function(C,A){var B=this;C=C||B.width;A=A||B.height;B.width=C;B.height=A;if(B.el){if(C!=undefined){B.el.setWidth(C)}if(A!=undefined){B.el.setHeight(A)}}B.callParent(arguments)},applyViewBox:function(){var D=this,G=D.viewBox,F=D.width,A=D.height,E,C,B;D.callParent();if(G&&(F||A)){E=D.items.items;C=E.length;for(B=0;B<C;B++){D.applyTransformations(E[B])}}},onAdd:function(A){this.callParent(arguments);if(this.el){this.renderItem(A)}},onRemove:function(A){if(A.el){A.el.destroy();delete A.el}this.callParent(arguments)},render:function(D){var B=this,E=Ext.getDoc().dom,A;if(!B.createNode){try{if(!E.namespaces.rvml){E.namespaces.add("rvml","urn:schemas-microsoft-com:vml")}B.createNode=function(F){return E.createElement("<rvml:"+F+' class="rvml">')}}catch(C){B.createNode=function(F){return E.createElement("<"+F+' xmlns="urn:schemas-microsoft.com:vml" class="rvml">')}}}if(!B.el){A=E.createElement("div");B.el=Ext.get(A);B.el.addCls(B.baseVmlCls);B.span=E.createElement("span");Ext.get(B.span).addCls(B.measureSpanCls);A.appendChild(B.span);B.el.setSize(B.width||0,B.height||0);D.appendChild(A);B.el.on({scope:B,mouseup:B.onMouseUp,mousedown:B.onMouseDown,mouseover:B.onMouseOver,mouseout:B.onMouseOut,mousemove:B.onMouseMove,mouseenter:B.onMouseEnter,mouseleave:B.onMouseLeave,click:B.onClick,dblclick:B.onDblClick})}B.renderAll()},renderAll:function(){this.items.each(this.renderItem,this)},redraw:function(A){A.dirty=true;this.renderItem(A)},renderItem:function(A){if(!this.el){return}if(!A.el){this.createSpriteElement(A)}if(A.dirty){this.applyAttrs(A);if(A.dirtyTransform){this.applyTransformations(A)}}},rotationCompensation:function(D,B,C){var A=new Ext.draw.Matrix();A.rotate(-D,0.5,0.5);return{x:A.x(B,C),y:A.y(B,C)}},transform:function(D,S){var E=this,H=E.getBBox(D,true),G=new Ext.draw.Matrix(),F=D.transformations,O=F.length,C=0,L=0,A=1,B=1,R=D.el,K=R.dom,I=K.style,N=D.skew,P=E.viewBoxShift,M,Q,J;for(;C<O;C++){M=F[C];Q=M.type;if(Q=="translate"){G.translate(M.x,M.y)}else{if(Q=="rotate"){G.rotate(M.degrees,M.x,M.y);L+=M.degrees}else{if(Q=="scale"){G.scale(M.x,M.y,M.centerX,M.centerY);A*=M.x;B*=M.y}}}}D.matrix=G.clone();if(S){return}if(P){G.prepend(P.scale,0,0,P.scale,P.dx*P.scale,P.dy*P.scale)}if(D.type!="image"&&N){N.origin="0,0";N.matrix=G.toString();J=G.offset();if(J[0]>32767){J[0]=32767}else{if(J[0]<-32768){J[0]=-32768}}if(J[1]>32767){J[1]=32767}else{if(J[1]<-32768){J[1]=-32768}}N.offset=J}else{I.filter=G.toFilter();I.left=Math.min(G.x(H.x,H.y),G.x(H.x+H.width,H.y),G.x(H.x,H.y+H.height),G.x(H.x+H.width,H.y+H.height))+"px";I.top=Math.min(G.y(H.x,H.y),G.y(H.x+H.width,H.y),G.y(H.x,H.y+H.height),G.y(H.x+H.width,H.y+H.height))+"px"}},createItem:function(A){return Ext.create("Ext.draw.Sprite",A)},getRegion:function(){return this.el.getRegion()},addCls:function(B,A){if(B&&B.el){B.el.addCls(A)}},removeCls:function(B,A){if(B&&B.el){B.el.removeCls(A)}},addGradient:function(F){var C=this.gradientsColl||(this.gradientsColl=Ext.create("Ext.util.MixedCollection")),E=[],B=Ext.create("Ext.util.MixedCollection"),G,I,A,H,J,D;B.addAll(F.stops);B.sortByKey("ASC",function(L,K){L=parseInt(L,10);K=parseInt(K,10);return L>K?1:(L<K?-1:0)});G=B.keys;I=B.items;A=G.length;for(D=0;D<A;D++){H=G[D];J=I[D];E.push(H+"% "+J.color)}C.add(F.id,{colors:E.join(","),angle:F.angle})},destroy:function(){var A=this;A.callParent(arguments);if(A.el){A.el.destroy()}delete A.el}});
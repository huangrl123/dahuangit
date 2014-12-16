Ext.define("Ext.draw.sprite.Composite",{extend:"Ext.draw.sprite.Sprite",alias:"sprite.composite",type:"composite",constructor:function(){this.callParent(arguments);this.sprites=[];this.sprites.map={}},add:function(C){if(!C.isSprite){C=Ext.create("sprite."+C.type,C);C.setParent(this);C.setSurface(this.getSurface())}var A=C.applyTransformations,B=this,D=B.attr;C.applyTransformations=function(){if(C.attr.dirtyTransform){D.dirtyTransform=true;D.bbox.plain.dirty=true;D.bbox.transform.dirty=true}A.call(C)};this.sprites.push(C);this.sprites.map[C.id]=C.getId();D.bbox.plain.dirty=true;D.bbox.transform.dirty=true;return C},updateSurface:function(C){for(var A=0,B=this.sprites.length;A<B;A++){this.sprites[A].setSurface(C)}},addAll:function(B){if(B.isSprite||B.type){this.add(B)}else{if(Ext.isArray(B)){var A=0;while(A<B.length){this.add(B[A++])}}}},updatePlainBBox:function(H){var A=this,F=Infinity,C=-Infinity,E=Infinity,I=-Infinity,B,J,G,D;for(G=0,D=A.sprites.length;G<D;G++){B=A.sprites[G];B.applyTransformations();J=B.getBBox();if(F>J.x){F=J.x}if(C<J.x+J.width){C=J.x+J.width}if(E>J.y){E=J.y}if(I<J.y+J.height){I=J.y+J.height}}H.x=F;H.y=E;H.width=C-F;H.height=I-E},render:function(F,D,C){var E=this.attr.matrix,A,B;E.toContext(D);for(A=0,B=this.sprites.length;A<B;A++){F.renderSprite(this.sprites[A],C)}}});
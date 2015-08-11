Ext.define("Ext.draw.Surface",{extend:"Ext.draw.SurfaceBase",xtype:"surface",requires:["Ext.draw.sprite.*","Ext.draw.gradient.*","Ext.draw.sprite.AttributeDefinition","Ext.draw.Matrix","Ext.draw.Draw"],uses:["Ext.draw.engine.Canvas"],devicePixelRatio:window.devicePixelRatio||1,statics:{stableSort:function(E){if(E.length<2){return}var F={},B,D=[],C,G,A;for(C=0,G=E.length;C<G;C++){A=E[C].attr.zIndex;if(!F[A]){F[A]=[E[C]]}else{F[A].push(E[C])}}B=Ext.Object.getKeys(F).sort(function(I,H){return I-H});for(C=0,G=B.length;C<G;C++){D.push.apply(D,F[B[C]])}for(C=0,G=E.length;C<G;C++){E[C]=D[C]}}},config:{cls:Ext.baseCSSPrefix+"surface",rect:null,background:null,items:[],dirty:false,flipRtlText:false},isSurface:true,dirtyPredecessor:0,constructor:function(A){var B=this;B.predecessors=[];B.successors=[];B.pendingRenderFrame=false;B.map={};B.callParent([A]);B.matrix=new Ext.draw.Matrix();B.inverseMatrix=B.matrix.inverse(B.inverseMatrix);B.resetTransform()},roundPixel:function(A){return Math.round(this.devicePixelRatio*A)/this.devicePixelRatio},waitFor:function(C){var B=this,A=B.predecessors;if(!Ext.Array.contains(A,C)){A.push(C);C.successors.push(B);if(C._dirty){B.dirtyPredecessor++}}},setDirty:function(E){if(this._dirty!==E){var C=this.successors,B,A,D=C.length;for(A=0;A<D;A++){B=C[A];if(E){B.dirtyPredecessor++;B.setDirty(true)}else{B.dirtyPredecessor--;if(B.dirtyPredecessor===0&&B.pendingRenderFrame){B.renderFrame()}}}this._dirty=E}},applyElement:function(A,B){if(B){B.set(A)}else{B=Ext.Element.create(A)}this.setDirty(true);return B},applyBackground:function(B,A){this.setDirty(true);if(Ext.isString(B)){B={fillStyle:B}}return Ext.factory(B,Ext.draw.sprite.Rect,A)},applyRect:function(A,B){if(B&&A[0]===B[0]&&A[1]===B[1]&&A[2]===B[2]&&A[3]===B[3]){return}if(Ext.isArray(A)){return[A[0],A[1],A[2],A[3]]}else{if(Ext.isObject(A)){return[A.x||A.left,A.y||A.top,A.width||(A.right-A.left),A.height||(A.bottom-A.top)]}}},updateRect:function(D){var A=this,F=D[0],E=D[1],C=F+D[2],G=E+D[3],H=A.getBackground(),B=A.element;B.setLocalXY(Math.floor(F),Math.floor(E));B.setSize(Math.ceil(C-Math.floor(F)),Math.ceil(G-Math.floor(E)));if(H){H.setAttributes({x:0,y:0,width:Math.ceil(C-Math.floor(F)),height:Math.ceil(G-Math.floor(E))})}A.setDirty(true)},resetTransform:function(){this.matrix.set(1,0,0,1,0,0);this.inverseMatrix.set(1,0,0,1,0,0);this.setDirty(true)},get:function(A){return this.map[A]||this.items[A]},add:function(){var G=this,B=Array.prototype.slice.call(arguments),C=Ext.isArray(B[0]),F=[],A,H,I,E,D;I=Ext.Array.clean(C?B[0]:B);if(!I.length){return F}H=G.prepareItems(I);for(E=0,D=H.length;E<D;E++){A=H[E];G.map[A.getId()]=A;F.push(A);A.setParent(G);A.setSurface(G);G.onAdd(A)}I=G.getItems();if(I){I.push.apply(I,F)}G.dirtyZIndex=true;G.setDirty(true);if(!C&&F.length===1){return F[0]}else{return F}},onAdd:Ext.emptyFn,remove:function(B,A){if(B){delete this.map[B.getId()];if(A){B.destroy()}else{B.setParent(null);B.setSurface(null);Ext.Array.remove(this.getItems(),B)}this.dirtyZIndex=true;this.setDirty(true)}},removeAll:function(D){var C=this.getItems(),A=C.length,B;if(D){while(A>0){C[--A].destroy()}}else{while(A>0){A--;B=C[A];B.setParent(null);B.setSurface(null)}}C.length=0;this.map={};this.dirtyZIndex=true},applyItems:function(A){if(this.getItems()){this.removeAll(true)}return Ext.Array.from(this.add(A))},prepareItems:function(E){E=[].concat(E);var D=this,C,B,F,G,A=function(H){this.remove(H,false)};for(B=0,F=E.length;B<F;B++){C=E[B];if(!(C instanceof Ext.draw.sprite.Sprite)){C=E[B]=D.createItem(C)}C.on("beforedestroy",A,D)}return E},createItem:function(A){return Ext.create(A.xclass||"sprite."+A.type,A)},getBBox:function(I,D){var I=Ext.Array.from(I),F=Infinity,B=-Infinity,E=Infinity,H=-Infinity,A,J,G,C;for(G=0,C=I.length;G<C;G++){A=I[G];J=A.getBBox(D);if(F>J.x){F=J.x}if(B<J.x+J.width){B=J.x+J.width}if(E>J.y){E=J.y}if(H<J.y+J.height){H=J.y+J.height}}return{x:F,y:E,width:B-F,height:H-E}},emptyRect:[0,0,0,0],getEventXY:function(A){var H=this,F=H.getInherited().rtl,I=A.getXY(),G=H.el.up(),E=G.getXY(),C=H.getRect()||H.emptyRect,B=[],D;if(F){D=G.getWidth();if(Ext.isIE8){B[0]=I[0]+E[0]-C[0]+D-document.body.clientWidth}else{B[0]=E[0]-I[0]-C[0]+D}}else{B[0]=I[0]-E[0]-C[0]}B[1]=I[1]-E[1]-C[1];return B},clear:Ext.emptyFn,orderByZIndex:function(){var C=this,D=C.getItems(),B=false,A,E;if(C.getDirty()){for(A=0,E=D.length;A<E;A++){if(D[A].attr.dirtyZIndex){B=true;break}}if(B){Ext.draw.Surface.stableSort(D);this.setDirty(true)}for(A=0,E=D.length;A<E;A++){D[A].attr.dirtyZIndex=false}}},repaint:function(){var A=this;A.repaint=Ext.emptyFn;setTimeout(function(){delete A.repaint;A.element.repaint()},1)},renderFrame:function(){if(!this.element){return}if(this.dirtyPredecessor>0){this.pendingRenderFrame=true;return}var E=this,D=this.getRect(),A=E.getBackground(),F=E.getItems(),C,B,G;if(!D){return}E.orderByZIndex();if(E.getDirty()){E.clear();E.clearTransform();if(A){E.renderSprite(A)}for(B=0,G=F.length;B<G;B++){C=F[B];if(false===E.renderSprite(C)){return}C.attr.textPositionCount=E.textPosition}E.setDirty(false)}},renderSprite:Ext.emptyFn,clearTransform:Ext.emptyFn,getDirty:function(){return this._dirty},destroy:function(){var A=this;A.removeAll();A.setBackground(null);A.predecessors=null;A.successors=null;A.callParent()}});
Ext.define("Ext.draw.CompositeSprite",{extend:"Ext.util.MixedCollection",mixins:{animate:"Ext.util.Animate"},autoDestroy:false,isCompositeSprite:true,constructor:function(A){var B=this;Ext.apply(B,A);B.id=Ext.id(null,"ext-sprite-group-");B.callParent()},onClick:function(A){this.fireEvent("click",A)},onMouseUp:function(A){this.fireEvent("mouseup",A)},onMouseDown:function(A){this.fireEvent("mousedown",A)},onMouseOver:function(A){this.fireEvent("mouseover",A)},onMouseOut:function(A){this.fireEvent("mouseout",A)},attachEvents:function(A){var B=this;A.on({scope:B,mousedown:B.onMouseDown,mouseup:B.onMouseUp,mouseover:B.onMouseOver,mouseout:B.onMouseOut,click:B.onClick})},add:function(A,B){var C=this.callParent(arguments);this.attachEvents(C);return C},insert:function(C,A,B){return this.callParent(arguments)},remove:function(A){var B=this;A.un({scope:B,mousedown:B.onMouseDown,mouseup:B.onMouseUp,mouseover:B.onMouseOver,mouseout:B.onMouseOut,click:B.onClick});return B.callParent(arguments)},getBBox:function(){var E=0,A,B,K=this.items,J=this.length,C=Infinity,F=C,L=-C,I=C,G=-C,H,D;for(;E<J;E++){A=K[E];if(A.el&&!A.bboxExcluded){B=A.getBBox();F=Math.min(F,B.x);I=Math.min(I,B.y);L=Math.max(L,B.height+B.y);G=Math.max(G,B.width+B.x)}}return{x:F,y:I,height:L-I,width:G-F}},setAttributes:function(B,E){var A=0,D=this.items,C=this.length;for(;A<C;A++){D[A].setAttributes(B,E)}return this},hide:function(D){var A=0,C=this.items,B=this.length;for(;A<B;A++){C[A].hide(D)}return this},show:function(D){var A=0,C=this.items,B=this.length;for(;A<B;A++){C[A].show(D)}return this},redraw:function(){var B=this,A=0,D=B.items,E=B.getSurface(),C=B.length;if(E){for(;A<C;A++){E.renderItem(D[A])}}return B},setStyle:function(C){var B=0,F=this.items,E=this.length,D,A;for(;B<E;B++){D=F[B];A=D.el;if(A){A.setStyle(C)}}},addCls:function(B){var A=0,D=this.items,E=this.getSurface(),C=this.length;if(E){for(;A<C;A++){E.addCls(D[A],B)}}},removeCls:function(B){var A=0,D=this.items,E=this.getSurface(),C=this.length;if(E){for(;A<C;A++){E.removeCls(D[A],B)}}},getSurface:function(){var A=this.first();if(A){return A.surface}return null},destroy:function(){var B=this,D=B.getSurface(),C=B.autoDestroy,A;if(D){while(B.getCount()>0){A=B.first();B.remove(A);D.remove(A,C)}}B.clearListeners()}});
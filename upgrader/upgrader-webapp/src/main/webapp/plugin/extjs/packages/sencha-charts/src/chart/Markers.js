Ext.define("Ext.chart.Markers",{extend:"Ext.draw.sprite.Instancing",defaultCategory:"default",constructor:function(){this.callParent(arguments);this.categories={};this.revisions={}},clear:function(A){A=A||this.defaultCategory;if(!(A in this.revisions)){this.revisions[A]=1}else{this.revisions[A]++}},putMarkerFor:function(B,G,D,F,E){B=B||this.defaultCategory;var C=this,A=C.categories[B]||(C.categories[B]={});if(D in A){C.setAttributesFor(A[D],G,F)}else{A[D]=C.instances.length;C.createInstance(G,null,F)}C.instances[A[D]].category=B;if(!E){C.instances[A[D]].revision=C.revisions[B]||(C.revisions[B]=1)}},getMarkerBBoxFor:function(B,D,A){if(B in this.categories){var C=this.categories[B];if(D in C){return this.getBBoxFor(C[D],A)}}return{x:Infinity,y:Infinity,width:-Infinity,height:-Infinity}},getBBox:function(){return null},render:function(F,H,J){var A=this,G=A.revisions,K=A.attr.matrix,B=A.getTemplate(),D=B.attr,C=A.instances,I,E=A.instances.length;K.toContext(H);B.preRender(F,H,J);B.useAttributes(H);for(I=0;I<E;I++){if(C[I].hidden||C[I].revision!==G[C[I].category]){continue}H.save();B.attr=C[I];B.applyTransformations();B.useAttributes(H);B.render(F,H,J);H.restore()}B.attr=D}});
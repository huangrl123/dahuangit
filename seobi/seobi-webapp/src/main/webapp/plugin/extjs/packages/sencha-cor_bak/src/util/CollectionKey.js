Ext.define("Ext.util.CollectionKey",{mixins:["Ext.mixin.Identifiable"],config:{collection:null,keyFn:null,property:null,rootProperty:null,unique:true},generation:0,map:null,mapRebuilds:0,constructor:function(A){this.initConfig(A);if(!Ext.isFunction(this.getKey)){Ext.Error.raise("CollectionKey requires a keyFn or property config")}},get:function(A){var B=this.map||this.getMap();return B[A]||null},getRootProperty:function(){var B=this,A=this.callParent();return A!==null?A:B.getCollection().getRootProperty()},indexOf:function(G,A){var I=this.map||this.getMap(),J=I[G],B=this.getCollection(),D=B.length,E,C,H,F;if(!J){return -1}if(A===undefined){A=-1}if(J instanceof Array){H=J;C=D;for(F=H.length;F-->0;){E=B.indexOf(H[F]);if(E<C&&E>A){C=E}}if(C===D){return -1}}else{C=B.indexOf(J)}return(C>A)?C:-1},updateKey:function(D,E){var B=this,C=B.map,A,F;if(C){A=C[E];if(A instanceof Array){F=Ext.Array.indexOf(A,D);if(F>=0){if(A.length>2){A.splice(F,1)}else{C[E]=A[1-F]}}}else{if(A){if(B.getUnique()&&A!==D){Ext.Error.raise('Incorrect oldKey "'+E+'" for item with newKey "'+B.getKey(D)+'"')}delete C[E]}}B.add([D])}},onCollectionAdd:function(A,B){if(this.map){this.add(B.items)}},onCollectionItemChange:function(A,B){this.map=null},onCollectionRefresh:function(){this.map=null},onCollectionRemove:function(B,A){var E=this,H=E.map,G=A.items,C=G.length,D,I,F;if(H){if(E.getUnique()&&C<B.length/2){for(D=0;D<C;++D){F=E.getKey(I=G[D]);delete H[F]}}else{E.map=null}}},add:function(G){var E=this,H=E.map,C,D,I,F,B,A;B=G.length;A=E.getUnique();for(D=0;D<B;++D){F=E.getKey(I=G[D]);if(A||!(F in H)){H[F]=I}else{if(!((C=H[F]) instanceof Array)){H[F]=C=[C]}C.push(I)}}},applyKeyFn:function(A){if(Ext.isString(A)){this.getKey=function(B){return B[A]()}}else{this.getKey=A}},applyProperty:function(B){var A=this.getRootProperty();this.getKey=function(C){return(A?C[A]:C)[B]}},getMap:function(){var B=this,A=B.map;if(!A){B.map=A={};B.keysByItemKey={};++B.mapRebuilds;B.add(B.getCollection().items)}return A},updateCollection:function(A){A.addObserver(this)}});
Ext.define("Ext.data.session.ChildChangesVisitor",{extend:"Ext.data.session.ChangesVisitor",constructor:function(){this.seen={};this.callParent(arguments)},onDirtyRecord:function(A){if(this.callParent(arguments)!==false){if(!A.$source&&(A.dropped||!A.phantom)){this.readEntity(A)}}},readEntity:function(H){var G=this,I=G.readKey,C=H.entityName,F=H.id,E=G.seen,A=C+F,B,D;if(E[A]){return}E[A]=true;B=G.result||(G.result={});D=B[C]||(B[C]={});D=D[I]||(D[I]=[]);D.push(Ext.apply({},H.modified,H.data))}});
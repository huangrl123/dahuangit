Ext.define("Ext.dashboard.Dashboard",{extend:"Ext.panel.Panel",xtype:"dashboard",requires:["Ext.layout.container.SplitColumn","Ext.dashboard.DropZone","Ext.dashboard.Column","Ext.dashboard.Part"],isDashboard:true,cls:Ext.baseCSSPrefix+"dashboard",bodyCls:Ext.baseCSSPrefix+"dashboard-body",firstColumnCls:Ext.baseCSSPrefix+"dashboard-column-first",lastColumnCls:Ext.baseCSSPrefix+"dashboard-column-last",defaultType:"dashboard-column",autoScroll:true,layout:null,stateful:true,idSeed:1,maxColumns:4,config:{parts:null},initComponent:function(){var A=this;if(!A.layout){A.layout={type:"split-column",firstColumnCls:A.firstColumnCls,lastColumnCls:A.lastColumnCls}}A.callParent()},applyParts:function(A,B){if(!B){B=new Ext.util.Collection({decoder:Ext.Factory.part})}var D,C;for(D in A){C=A[D];if(Ext.isString(C)){C={type:C}}C.id=D;C.dashboard=this;B.add(C)}return B},getPart:function(A){var B=this.getParts();return B.getByKey(A)},addNew:function(A){var B=this,C=B.getPart(A);C.displayForm(null,null,function(D){D.type=A;B.addView(D)})},addView:function(E,B,F){var G=this,I=G.items,H=I.getCount(),D=B||0,A=E.id?E:G.createView(E),J=G.columnWidths,C;if(D>=H){D=H-1;F=1}if(!F){C=I.getAt(D);return C.add(A)}if(F>0){++D}C=G.createColumn();if(J){C.columnWidth=J[D]}if(!C.items){C.items=[]}C.items.push(A);C=G.add(D,C);I=C.items;return I.getAt(I.getCount()-1)},createColumn:function(A){return Ext.apply({items:[],bubbleEvents:["add","remove","move","resize"],listeners:{remove:this.onRemoveItem,scope:this}},A)},createView:function(B){var C=this,A=B.type,D=C.getPart(A),E=D.createView(B);if(!E.id){E.id=C.id+"_"+A+(C.idSeed++)}return E},initEvents:function(){this.callParent();this.dd=new Ext.dashboard.DropZone(this,this.dropConfig)},beforeDestroy:function(){if(this.dd){this.dd.unreg()}this.callParent()},applyState:function(F){delete F.items;this.callParent([F]);var C=F.columnWidths,E=this.items.items,A=E.length,B,D;if(C){D=C.length;for(B=0;B<A;++B){E[B].columnWidth=(B<D)?C[B]:(1/A)}}},getState:function(){var E=this,C=[],F=E.items.items,G=E.callParent()||{},A=F.length,B,D;for(B=0;B<A;++B){if(!(D=F[B]).isSplitter){C.push(D.columnWidth)}}G.columnWidths=C;G.idSeed=E.idSeed;G.items=E.serializeItems();return G},initItems:function(){var B=this,A=B.defaultContent,C;if(B.stateful){C=Ext.state.Manager.get(B.getStateId());A=(C&&C.items)||A}if(!B.items&&A){B.items=B.deserializeItems(A)}B.callParent()},deserializeItems:function(D){var A=this,F=D.length,E=[],J=A.columnWidths,H=A.maxColumns,C,B,I,K,G;for(I=0;I<F;++I){G=D[I];B=Math.min(G.columnIndex||0,H-1);delete G.columnIndex;if(!(C=E[B])){E[B]=C=A.createColumn();if(J){C.columnWidth=J[B]}}K=A.createView(G);C.items.push(K)}for(I=0,F=E.length;I<F;++I){C=E[I];if(!C.columnWidth){C.columnWidth=1/F}}return E},serializeItem:function(A){return Ext.apply({type:A.part.id,id:A.id,columnIndex:A.columnIndex},A._partConfig)},serializeItems:function(){var H=this,I=H.items.items,E=I.length,J=[],A=0,B,C,F,K,D,G;for(F=0;F<E;++F){K=I[F];if(!K.isSplitter){C=K.items.items;for(D=0,G=C.length;D<G;++D){B=C[D];B.columnIndex=A;J.push(H.serializeItem(B))}++A}}return J},onRemoveItem:function(B,A){if(!A.isMoving){Ext.state.Manager.clear(A.getStateId())}}});
Ext.define("Ext.chart.Chart",{extend:"Ext.draw.Component",alias:"widget.chart",mixins:["Ext.chart.theme.Theme","Ext.chart.Mask","Ext.chart.Navigation","Ext.util.StoreHolder","Ext.util.Observable"],uses:["Ext.chart.series.Series"],requires:["Ext.util.MixedCollection","Ext.data.StoreManager","Ext.chart.Legend","Ext.chart.theme.Base","Ext.chart.theme.Theme","Ext.util.DelayedTask"],viewBox:false,animate:false,legend:false,insetPadding:10,background:false,refreshBuffer:1,constructor:function(B){var C=this,A;B=Ext.apply({},B);C.initTheme(B.theme||C.theme);if(C.gradients){Ext.apply(B,{gradients:C.gradients})}if(C.background){Ext.apply(B,{background:C.background})}if(B.animate){A={easing:"ease",duration:500};if(Ext.isObject(B.animate)){B.animate=Ext.applyIf(B.animate,A)}else{B.animate=A}}C.mixins.observable.constructor.call(C,B);if(B.mask){B=Ext.apply({enableMask:true},B)}if(B.enableMask){C.mixins.mask.constructor.call(C,B)}C.mixins.navigation.constructor.call(C);C.callParent([B])},getChartStore:function(){return this.substore||this.store},initComponent:function(){var B=this,A,C;B.callParent();Ext.applyIf(B,{zoom:{width:1,height:1,x:0,y:0}});B.maxGutters={left:0,right:0,bottom:0,top:0};B.store=Ext.data.StoreManager.lookup(B.store);A=B.axes;B.axes=new Ext.util.MixedCollection(false,function(D){return D.position});if(A){B.axes.addAll(A)}C=B.series;B.series=new Ext.util.MixedCollection(false,function(D){return D.seriesId||(D.seriesId=Ext.id(null,"ext-chart-series-"))});if(C){B.series.addAll(C)}if(B.legend!==false){B.legend=new Ext.chart.Legend(Ext.applyIf({chart:B},B.legend))}B.on({mousemove:B.onMouseMove,mouseleave:B.onMouseLeave,mousedown:B.onMouseDown,mouseup:B.onMouseUp,click:B.onClick,dblclick:B.onDblClick,scope:B})},afterComponentLayout:function(E,A,C,B){var D=this;if(Ext.isNumber(E)&&Ext.isNumber(A)){if(E!==C||A!==B){D.curWidth=E;D.curHeight=A;D.redraw(true);D.needsRedraw=false}else{if(D.needsRedraw){D.redraw();D.needsRedraw=false}}}this.callParent(arguments)},redraw:function(J){var H=this,E=H.series.items,K=E.length,I=H.axes.items,G=I.length,B=0,F,L,D=H.chartBBox={x:0,y:0,height:H.curHeight,width:H.curWidth},A=H.legend,C;H.surface.setSize(D.width,D.height);for(F=0;F<K;F++){L=E[F];if(!L.initialized){C=H.initializeSeries(L,F,B)}else{C=L}C.onRedraw();if(Ext.isArray(L.yField)){B+=L.yField.length}else{++B}}for(F=0;F<G;F++){L=I[F];if(!L.initialized){H.initializeAxis(L)}}for(F=0;F<G;F++){I[F].processView()}for(F=0;F<G;F++){I[F].drawAxis(true)}if(A!==false&&A.visible){if(A.update||!A.created){A.create()}}H.alignAxes();if(A!==false&&A.visible){A.updatePosition()}H.getMaxGutters();H.resizing=!!J;for(F=0;F<G;F++){I[F].drawAxis()}for(F=0;F<K;F++){H.drawCharts(E[F])}H.resizing=false},afterRender:function(){var B=this,A=B.legend;B.callParent(arguments);if(B.categoryNames){B.setCategoryNames(B.categoryNames)}if(A){A.init()}B.bindStore(B.store,true);B.refresh();if(B.surface.engine==="Vml"){B.on("added",B.onAddedVml,B);B.mon(Ext.GlobalEvents,"added",B.onContainerAddedVml,B)}},onAddedVml:function(){this.needsRedraw=true},onContainerAddedVml:function(A){if(this.isDescendantOf(A)){this.needsRedraw=true}},getEventXY:function(A){var D=this.surface.getRegion(),B=A.getXY(),C=B[0]-D.left,E=B[1]-D.top;return[C,E]},onClick:function(A){this.handleClick("itemclick",A)},onDblClick:function(A){this.handleClick("itemdblclick",A)},handleClick:function(B,A){var G=this,F=G.getEventXY(A),D=G.series.items,E,C,H,I;for(E=0,C=D.length;E<C;E++){H=D[E];if(Ext.draw.Draw.withinBox(F[0],F[1],H.bbox)){if(H.getItemForPoint){I=H.getItemForPoint(F[0],F[1]);if(I){H.fireEvent(B,I)}}}}},onMouseDown:function(A){var F=this,E=F.getEventXY(A),C=F.series.items,D,B,G,H;if(F.enableMask){F.mixins.mask.onMouseDown.call(F,A)}for(D=0,B=C.length;D<B;D++){G=C[D];if(Ext.draw.Draw.withinBox(E[0],E[1],G.bbox)){if(G.getItemForPoint){H=G.getItemForPoint(E[0],E[1]);if(H){G.fireEvent("itemmousedown",H)}}}}},onMouseUp:function(A){var F=this,E=F.getEventXY(A),C=F.series.items,D,B,G,H;if(F.enableMask){F.mixins.mask.onMouseUp.call(F,A)}for(D=0,B=C.length;D<B;D++){G=C[D];if(Ext.draw.Draw.withinBox(E[0],E[1],G.bbox)){if(G.getItemForPoint){H=G.getItemForPoint(E[0],E[1]);if(H){G.fireEvent("itemmouseup",H)}}}}},onMouseMove:function(A){var I=this,G=I.getEventXY(A),D=I.series.items,E,C,J,K,F,B,H;if(I.enableMask){I.mixins.mask.onMouseMove.call(I,A)}for(E=0,C=D.length;E<C;E++){J=D[E];if(Ext.draw.Draw.withinBox(G[0],G[1],J.bbox)){if(J.getItemForPoint){K=J.getItemForPoint(G[0],G[1]);F=J._lastItemForPoint;B=J._lastStoreItem;H=J._lastStoreField;if(K!==F||K&&(K.storeItem!=B||K.storeField!=H)){if(F){J.fireEvent("itemmouseout",F);delete J._lastItemForPoint;delete J._lastStoreField;delete J._lastStoreItem}if(K){J.fireEvent("itemmouseover",K);J._lastItemForPoint=K;J._lastStoreItem=K.storeItem;J._lastStoreField=K.storeField}}}}else{F=J._lastItemForPoint;if(F){J.fireEvent("itemmouseout",F);delete J._lastItemForPoint;delete J._lastStoreField;delete J._lastStoreItem}}}},onMouseLeave:function(B){var D=this,E=D.series.items,A,F,C;if(D.enableMask){D.mixins.mask.onMouseLeave.call(D,B)}for(A=0,F=E.length;A<F;A++){C=E[A];delete C._lastItemForPoint}},delayRefresh:function(){var A=this;if(!A.refreshTask){A.refreshTask=new Ext.util.DelayedTask(A.refresh,A)}A.refreshTask.delay(A.refreshBuffer)},refresh:function(){var A=this;if(A.rendered&&A.curWidth!==undefined&&A.curHeight!==undefined){if(!A.isVisible(true)){if(!A.refreshPending){A.setShowListeners("mon");A.refreshPending=true}return}if(A.fireEvent("beforerefresh",A)!==false){A.redraw();A.fireEvent("refresh",A)}}},onShow:function(){var A=this;A.callParent(arguments);if(A.refreshPending){A.delayRefresh();A.setShowListeners("mun")}delete A.refreshPending},setShowListeners:function(B){var A=this;A[B](Ext.GlobalEvents,{scope:A,single:true,show:A.forceRefresh,expand:A.forceRefresh})},doRefresh:function(){this.setSubStore(null);this.refresh()},forceRefresh:function(B){var A=this;if(A.isDescendantOf(B)&&A.refreshPending){A.setShowListeners("mun");A.delayRefresh()}delete A.refreshPending},bindStore:function(A,B){var C=this;C.mixins.storeholder.bindStore.apply(C,arguments);if(C.store&&!B){C.refresh()}},getStoreListeners:function(){var B=this.doRefresh,A=this.delayRefresh;return{refresh:B,add:A,remove:A,update:A,clear:B}},setSubStore:function(A){this.substore=A},initializeAxis:function(C){var H=this,E=H.chartBBox,D=E.width,F=E.height,I=E.x,J=E.y,A=H.themeAttrs,G=H.axes,B={chart:H};if(A){B.axisStyle=Ext.apply({},A.axis);B.axisLabelLeftStyle=Ext.apply({},A.axisLabelLeft);B.axisLabelRightStyle=Ext.apply({},A.axisLabelRight);B.axisLabelTopStyle=Ext.apply({},A.axisLabelTop);B.axisLabelBottomStyle=Ext.apply({},A.axisLabelBottom);B.axisTitleLeftStyle=Ext.apply({},A.axisTitleLeft);B.axisTitleRightStyle=Ext.apply({},A.axisTitleRight);B.axisTitleTopStyle=Ext.apply({},A.axisTitleTop);B.axisTitleBottomStyle=Ext.apply({},A.axisTitleBottom);H.configureAxisStyles(B)}switch(C.position){case"top":Ext.apply(B,{length:D,width:F,x:I,y:J});break;case"bottom":Ext.apply(B,{length:D,width:F,x:I,y:F});break;case"left":Ext.apply(B,{length:F,width:D,x:I,y:F});break;case"right":Ext.apply(B,{length:F,width:D,x:D,y:F});break}if(!C.chart){Ext.apply(B,C);C=Ext.createByAlias("axis."+C.type.toLowerCase(),B);G.replace(C)}else{Ext.apply(C,B)}C.initialized=true},configureAxisStyles:Ext.emptyFn,getInsets:function(){var B=this,A=B.insetPadding;return{top:A,right:A,bottom:A,left:A}},calculateInsets:function(){var I=this,E=I.legend,K=I.axes,B=["top","right","bottom","left"],J,F,G,H,C,A,L;function D(N){var M=K.findIndex("position",N);return(M<0)?null:K.getAt(M)}J=I.getInsets();for(F=0,G=B.length;F<G;F++){H=B[F];C=(H==="left"||H==="right");A=D(H);if(E!==false){if(E.position===H){L=E.getBBox();J[H]+=(C?L.width:L.height)+I.insetPadding}}if(A&&A.bbox){L=A.bbox;J[H]+=(C?L.width:L.height)}}return J},alignAxes:function(){var G=this,I=G.axes.items,H,D,E,F,A,B,C;H=G.calculateInsets();D={x:H.left,y:H.top,width:G.curWidth-H.left-H.right,height:G.curHeight-H.top-H.bottom};G.chartBBox=D;for(E=0,F=I.length;E<F;E++){A=I[E];B=A.position;C=B==="left"||B==="right";A.x=(B==="right"?D.x+D.width:D.x);A.y=(B==="top"?D.y:D.y+D.height);A.width=(C?D.width:D.height);A.length=(C?D.height:D.width)}},initializeSeries:function(J,F,C){var I=this,A=I.themeAttrs,L,H,N,E,K,D=[],G=(J instanceof Ext.chart.series.Series).i=0,M,B;if(!J.initialized){B={chart:I,seriesId:J.seriesId};if(A){N=A.seriesThemes;K=A.markerThemes;L=Ext.apply({},A.series);H=Ext.apply({},A.marker);B.seriesStyle=Ext.apply(L,N[C%N.length]);B.seriesLabelStyle=Ext.apply({},A.seriesLabel);B.markerStyle=Ext.apply(H,K[C%K.length]);if(A.colors){B.colorArrayStyle=A.colors}else{D=[];for(M=N.length;i<M;i++){E=N[i];if(E.fill||E.stroke){D.push(E.fill||E.stroke)}}if(D.length){B.colorArrayStyle=D}}B.seriesIdx=F;B.themeIdx=C}if(G){Ext.applyIf(J,B)}else{Ext.applyIf(B,J);J=I.series.replace(Ext.createByAlias("series."+J.type.toLowerCase(),B))}}J.initialize();J.initialized=true;return J},getMaxGutters:function(){var I=this,E=I.series.items,H,D,J,C,F=0,B=0,G=0,A=0;for(H=0,D=E.length;H<D;H++){C=E[H].getGutters();if(C){if(C.verticalAxis){G=Math.max(G,C.lower);A=Math.max(A,C.upper)}else{F=Math.max(F,C.lower);B=Math.max(B,C.upper)}}}I.maxGutters={left:F,right:B,bottom:G,top:A}},drawAxis:function(A){A.drawAxis()},drawCharts:function(A){A.triggerafterrender=false;A.drawSeries();if(!this.animate){A.fireEvent("afterrender",A)}},save:function(A){return Ext.draw.Surface.save(this.surface,A)},destroy:function(){var A=this,B=A.refreshTask;if(B){B.cancel();A.refreshTask=null}Ext.destroy(A.surface);A.bindStore(null);A.callParent(arguments)}});
Ext.define("Ext.chart.series.StackedCartesian",{extend:"Ext.chart.series.Cartesian",config:{stacked:true,hidden:[]},animatingSprites:0,themeColorCount:function(){var A=this,B=A.getYField();return(Ext.isArray(B)?B.length:1)},updateStacked:function(){this.processData()},coordinateY:function(){return this.coordinateStacked("Y",1,2)},getFields:function(D){var B=this,F=[],C,A,E;for(A=0,E=D.length;A<E;A++){C=B["get"+D[A]+"Field"]();if(Ext.isArray(C)){F.push.apply(F,C)}else{F.push(C)}}return F},updateLabelOverflowPadding:function(A){this.getLabel().setAttributes({labelOverflowPadding:A})},getSprites:function(){var H=this,C=this.getChart(),K=C&&C.getAnimation(),J=H.getFields(H.fieldCategoryY),G=H.getItemInstancing(),I=H.sprites,B,A=H.getHidden(),E=false,F,D=J.length;if(!C){return[]}for(F=0;F<D;F++){B=I[F];if(!B){B=H.createSprite();B.setAttributes({zIndex:-F});B.setField(J[F]);E=true;A.push(false);if(G){B.itemsMarker.getTemplate().setAttributes(H.getStyleByIndex(F))}else{B.setAttributes(H.getStyleByIndex(F))}}if(K){if(G){B.itemsMarker.getTemplate().fx.setConfig(K)}B.fx.setConfig(K)}}if(E){H.updateHidden(A)}return I},getItemForPoint:function(K,L){if(this.getSprites()){var J=this,G,E,B,H=J.getItemInstancing(),I=J.getSprites(),F=J.getStore(),A=J.getHidden(),M,C,D;for(G=0,E=I.length;G<E;G++){if(!A[G]){B=I[G];C=B.getIndexNearPoint(K,L);if(C!==-1){D=J.getYField();M={series:J,index:C,category:H?"items":"markers",record:F.getData().items[C],field:typeof D==="string"?D:D[G],sprite:B};return M}}}return null}},provideLegendInfo:function(H){var A=this,I=A.getSprites(),D=A.getTitle(),G=A.getYField(),B=A.getHidden(),J=I.length===1,F,C;for(var E=0;E<I.length;E++){F=A.getStyleByIndex(E);if(Ext.isArray(D)){C=D[E]}else{if(J){C=D}else{if(Ext.isArray(G)){C=G[E]}else{C=A.getId()}}}H.push({name:C,mark:F.fillStyle||F.strokeStyle||"black",disabled:B[E],series:A.getId(),index:E})}},onSpriteAnimationStart:function(A){this.animatingSprites++;if(this.animatingSprites===1){this.fireEvent("animationstart")}},onSpriteAnimationEnd:function(A){this.animatingSprites--;if(this.animatingSprites===0){this.fireEvent("animationend")}}});
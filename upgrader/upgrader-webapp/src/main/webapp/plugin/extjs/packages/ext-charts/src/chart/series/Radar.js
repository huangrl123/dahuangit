Ext.define("Ext.chart.series.Radar",{extend:"Ext.chart.series.Series",requires:["Ext.chart.Shape","Ext.fx.Anim"],type:"radar",alias:"series.radar",rad:Math.PI/180,showInLegend:false,style:{},constructor:function(A){this.callParent(arguments);var B=this,C=B.chart.surface;B.group=C.getGroup(B.seriesId);if(B.showMarkers){B.markerGroup=C.getGroup(B.seriesId+"-markers")}},drawSeries:function(){var I=this,V=I.chart.getChartStore(),g=V.data.items,Q,C,A=I.group,G=I.chart,S=G.series.items,c,M,b,O=I.field||I.yField,n=G.surface,P=G.chartBBox,k=I.colorArrayStyle,K,L,N,f,J=0,F=0,Y=[],R=Math.max,Z=Math.cos,D=Math.sin,e=Math.PI*2,j=V.getCount(),X,W,H,a,m,h,B,E=I.seriesStyle,T=G.axes&&G.axes.get(0),U=!(T&&T.maximum);I.setBBox();J=U?0:(T.maximum||0);F=T.minimum||0;Ext.apply(E,I.style||{});if(!V||!V.getCount()||I.seriesIsHidden){I.hide();I.items=[];if(I.radar){I.radar.hide(true)}I.radar=null;return}if(!E["stroke"]){E["stroke"]=k[I.themeIdx%k.length]}I.unHighlightItem();I.cleanHighlights();K=I.centerX=P.x+(P.width/2);L=I.centerY=P.y+(P.height/2);I.radius=f=Math.min(P.width,P.height)/2;I.items=N=[];if(U){for(c=0,M=S.length;c<M;c++){b=S[c];Y.push(b.yField)}for(Q=0;Q<j;Q++){C=g[Q];for(h=0,B=Y.length;h<B;h++){J=R(+C.get(Y[h]),J)}}}J=J||1;if(F>=J){F=J-1}X=[];W=[];for(h=0;h<j;h++){C=g[h];m=f*(C.get(O)-F)/(J-F);if(m<0){m=0}H=m*Z(h/j*e);a=m*D(h/j*e);if(h==0){W.push("M",H+K,a+L);X.push("M",0.01*H+K,0.01*a+L)}else{W.push("L",H+K,a+L);X.push("L",0.01*H+K,0.01*a+L)}N.push({sprite:false,point:[K+H,L+a],storeItem:C,series:I})}W.push("Z");if(!I.radar){I.radar=n.add(Ext.apply({type:"path",group:A,path:X},E||{}))}if(G.resizing){I.radar.setAttributes({path:X},true)}if(G.animate){I.onAnimate(I.radar,{to:Ext.apply({path:W},E||{})})}else{I.radar.setAttributes(Ext.apply({path:W},E||{}),true)}if(I.showMarkers){I.drawMarkers()}I.renderLabels();I.renderCallouts()},drawMarkers:function(){var K=this,H=K.chart,E=H.surface,A=H.getChartStore(),P=Ext.apply({},K.markerStyle||{}),D=Ext.apply(P,K.markerConfig,{fill:K.colorArrayStyle[K.themeIdx%K.colorArrayStyle.length]}),N=K.items,M=D.type,B=K.markerGroup,I=K.centerX,L=K.centerY,J,F,O,C,G;delete D.type;for(F=0,O=N.length;F<O;F++){J=N[F];C=B.getAt(F);if(!C){C=Ext.chart.Shape[M](E,Ext.apply({group:B,x:0,y:0,translate:{x:I,y:L}},D))}else{C.show()}J.sprite=C;if(H.resizing){C.setAttributes({x:0,y:0,translate:{x:I,y:L}},true)}C._to={translate:{x:J.point[0],y:J.point[1]}};G=K.renderer(C,A.getAt(F),C._to,F,A);G=Ext.applyIf(G||{},D||{});if(H.animate){K.onAnimate(C,{to:G})}else{C.setAttributes(G,true)}}},isItemInPoint:function(E,F,D){var C,A=10,B=Math.abs;C=D.point;return(B(C[0]-E)<=A&&B(C[1]-F)<=A)},onCreateLabel:function(B,J,D,I){var F=this,E=F.labelsGroup,A=F.label,H=F.centerX,G=F.centerY,C=Ext.apply({},A,F.seriesLabelStyle||{});return F.chart.surface.add(Ext.apply({"type":"text","text-anchor":"middle","group":E,"x":H,"y":G},C||{}))},onPlaceLabel:function(T,R,C,F,P,M,E){var H=this,S=H.chart,U=S.resizing,A=H.label,O=A.renderer,B=A.field,V=H.centerX,W=H.centerY,I={x:Number(C.point[0]),y:Number(C.point[1])},J=I.x-V,K=I.y-W,N=Math.atan2(K,J||1),L=N*180/Math.PI,Q,G;function D(X){if(X<0){X+=360}return X%360}T.setAttributes({text:O(R.get(B),T,R,C,F,P,M,E),hidden:true},true);Q=T.getBBox();L=D(L);if((L>45&&L<135)||(L>225&&L<315)){G=(L>45&&L<135?1:-1);I.y+=G*Q.height/2}else{G=(L>=135&&L<=225?-1:1);I.x+=G*Q.width/2}if(U){T.setAttributes({x:V,y:W},true)}if(M){T.show(true);H.onAnimate(T,{to:I})}else{T.setAttributes(I,true);T.show(true)}},toggleAll:function(C){var D=this,B,F,A,E;if(!C){Ext.chart.series.Radar.superclass.hideAll.call(D)}else{Ext.chart.series.Radar.superclass.showAll.call(D)}if(D.radar){D.radar.setAttributes({hidden:!C},true);if(D.radar.shadows){for(B=0,E=D.radar.shadows,F=E.length;B<F;B++){A=E[B];A.setAttributes({hidden:!C},true)}}}},hideAll:function(){this.toggleAll(false);this.hideMarkers(0)},showAll:function(){this.toggleAll(true)},hideMarkers:function(D){var C=this,B=C.markerGroup&&C.markerGroup.getCount()||0,A=D||0;for(;A<B;A++){C.markerGroup.getAt(A).hide(true)}},getAxesForXAndYFields:function(){var B=this,A=B.chart,D=A.axes,C=[].concat(D&&D.get(0));return{yAxis:C}}});
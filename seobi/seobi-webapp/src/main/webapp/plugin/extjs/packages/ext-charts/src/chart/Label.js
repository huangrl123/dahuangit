Ext.define("Ext.chart.Label",{requires:["Ext.draw.Color"],colorStringRe:/url\s*\(\s*#([^\/)]+)\s*\)/,constructor:function(A){var B=this;B.label=Ext.applyIf(B.label||{},{display:"none",stackedDisplay:"none",color:"#000",field:"name",minMargin:50,font:"11px Helvetica, sans-serif",orientation:"horizontal",renderer:Ext.identityFn});if(B.label.display!=="none"){B.labelsGroup=B.chart.surface.getGroup(B.seriesId+"-labels")}},renderLabels:function(){var M=this,K=M.chart,N=K.gradients,S=M.items,b=K.animate,V=M.label,O=V.display,h=V.stackedDisplay,P=V.renderer,I=V.color,r=[].concat(V.field),F=M.labelsGroup,W=(F||0)&&F.length,Z=M.chart.getChartStore(),T=Z.getCount(),R=(S||0)&&S.length,g=R/T,X=(N||0)&&N.length,Y=Ext.draw.Color,U=[],a,q,D,L,E,o,p,f,l,d,m,Q,e,B,t,C,G,s,n,c,J,A,H;if(O=="none"||!F){return}if(R==0){while(W--){U.push(W)}}else{for(q=0,D=0,L=0;q<T;q++){E=0;for(o=0;o<g;o++){m=S[D];Q=F.getAt(L);e=Z.getAt(q);while(this.__excludes&&this.__excludes[E]){E++}if(!m&&Q){Q.hide(true);L++}if(m&&r[o]){if(!Q){Q=M.onCreateLabel(e,m,q,O);if(!Q){break}}Q.setAttributes({fill:String(I)},true);M.onPlaceLabel(Q,e,m,q,O,b,E);L++;if(V.contrast&&m.sprite){B=m.sprite;if(b&&B._endStyle){s=B._endStyle.fill}else{if(b&&B._to){s=B._to.fill}else{s=B.attr.fill}}s=s||B.attr.fill;t=Y.fromString(s);if(s&&!t){s=s.match(M.colorStringRe)[1];for(p=0;p<X;p++){a=N[p];if(a.id==s){d=0;f=0;for(l in a.stops){d++;f+=Y.fromString(a.stops[l].color).getGrayscale()}C=(f/d)/255;break}}}else{C=t.getGrayscale()/255}if(Q.isOutside){C=1}G=Y.fromString(Q.attr.fill||Q.attr.color).getHSL();G[2]=C>0.5?0.2:0.8;Q.setAttributes({fill:String(Y.fromHSL.apply({},G))},true)}if(M.stacked&&h&&(m.totalPositiveValues||m.totalNegativeValues)){c=(m.totalPositiveValues||0);J=(m.totalNegativeValues||0);n=c+J;if(h=="total"){A=P(n)}else{if(h=="balances"){if(c==0&&J==0){A=P(0)}else{A=P(c);H=P(J)}}}if(A){Q=F.getAt(L);if(!Q){Q=M.onCreateLabel(e,m,q,"over")}G=Y.fromString(Q.attr.color||Q.attr.fill).getHSL();Q.setAttributes({text:A,style:V.font,fill:String(Y.fromHSL.apply({},G))},true);M.onPlaceLabel(Q,e,m,q,"over",b,E);L++}if(H){Q=F.getAt(L);if(!Q){Q=M.onCreateLabel(e,m,q,"under")}G=Y.fromString(Q.attr.color||Q.attr.fill).getHSL();Q.setAttributes({text:H,style:V.font,fill:String(Y.fromHSL.apply({},G))},true);M.onPlaceLabel(Q,e,m,q,"under",b,E);L++}}}D++;E++}}W=F.length;while(W>L){U.push(L);L++}}M.hideLabels(U)},hideLabels:function(B){var A=this.labelsGroup,C=!!B&&B.length;if(!A){return}if(C===false){C=A.getCount();while(C--){A.getAt(C).hide(true)}}else{while(C--){A.getAt(B[C]).hide(true)}}}});
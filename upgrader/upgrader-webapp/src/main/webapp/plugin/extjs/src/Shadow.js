Ext.define("Ext.Shadow",{requires:["Ext.ShadowPool"],localXYNames:{get:"getLocalXY",set:"setLocalXY"},constructor:function(C){var D=this,E,A,B;Ext.apply(D,C);if(!Ext.isString(D.mode)){D.mode=D.defaultMode}A=D.offset;B=Math.floor(A/2);D.opacity=50;switch(D.mode.toLowerCase()){case"drop":if(Ext.supports.CSS3BoxShadow){E={t:A,l:A,h:-A,w:-A}}else{E={t:-B,l:-B,h:-B,w:-B}}break;case"sides":if(Ext.supports.CSS3BoxShadow){E={t:A,l:0,h:-A,w:0}}else{E={t:-(1+B),l:1+B-2*A,h:-1,w:B-1}}break;case"frame":if(Ext.supports.CSS3BoxShadow){E={t:0,l:0,h:0,w:0}}else{E={t:1+B-2*A,l:1+B-2*A,h:A-B-1,w:A-B-1}}break;case"bottom":if(Ext.supports.CSS3BoxShadow){E={t:A,l:0,h:-A,w:0}}else{E={t:A,l:0,h:0,w:0}}break}D.adjusts=E},getShadowSize:function(){var D=this,A=D.el?D.offset:0,C=[A,A,A,A],B=D.mode.toLowerCase();if(D.el&&B!=="frame"){C[0]=0;if(B=="drop"){C[3]=0}}return C},offset:4,defaultMode:"drop",boxShadowProperty:(function(){var A="boxShadow",B=document.documentElement.style;if(!("boxShadow" in B)){if("WebkitBoxShadow" in B){A="WebkitBoxShadow"}else{if("MozBoxShadow" in B){A="MozBoxShadow"}}}return A}()),show:function(D){var B=this,C,A;D=Ext.get(D);C=(parseInt(D.getStyle("z-index"),10)-1)||0;A=D[B.localXYNames.get]();if(!B.el){B.el=Ext.ShadowPool.pull();if(B.fixed){B.el.dom.style.position="fixed"}else{B.el.dom.style.position=""}if(B.el.dom.nextSibling!=D.dom){B.el.insertBefore(D)}}B.el.setStyle("z-index",B.zIndex||C);if(Ext.isIE&&!Ext.supports.CSS3BoxShadow){B.el.dom.style.filter="progid:DXImageTransform.Microsoft.alpha(opacity="+B.opacity+") progid:DXImageTransform.Microsoft.Blur(pixelradius="+(B.offset)+")"}B.realign(A[0],A[1],D.dom.offsetWidth,D.dom.offsetHeight);B.el.dom.style.display="block"},isVisible:function(){return this.el?true:false},realign:function(G,C,K,D){if(!this.el){return}var I=this.adjusts,E=this.el,B=E.dom.style,J,H,F,A;E[this.localXYNames.set](G+I.l,C+I.t);J=Math.max(K+I.w,0);H=Math.max(D+I.h,0);F=J+"px";A=H+"px";if(B.width!=F||B.height!=A){B.width=F;B.height=A;if(Ext.supports.CSS3BoxShadow){B[this.boxShadowProperty]="0 0 "+(this.offset+2)+"px #888"}}},hide:function(){var A=this;if(A.el){A.el.dom.style.display="none";Ext.ShadowPool.push(A.el);delete A.el}},setZIndex:function(A){this.zIndex=A;if(this.el){this.el.setStyle("z-index",A)}},setOpacity:function(A){if(this.el){if(Ext.isIE&&!Ext.supports.CSS3BoxShadow){A=Math.floor(A*100/2)/100}this.opacity=A;this.el.setOpacity(A)}}});
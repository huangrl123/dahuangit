Ext.define("Ext.util.TextMetrics",{statics:{shared:null,measure:function(A,D,E){var B=this,C=B.shared;if(!C){C=B.shared=new B(A,E)}C.bind(A);C.setFixedWidth(E||"auto");return C.getSize(D)},destroy:function(){var A=this;Ext.destroy(A.shared);A.shared=null}},constructor:function(D,C){var B=this,A=Ext.getBody().createChild({"data-sticky":true,role:"presentation",cls:Ext.baseCSSPrefix+"textmetrics"});B.measure=A;if(D){B.bind(D)}A.position("absolute");A.setLocalXY(-1000,-1000);A.hide();if(C){A.setWidth(C)}},getSize:function(B){var C=this.measure,A;C.setHtml(B);A=C.getSize();C.setHtml("");return A},bind:function(A){var B=this;B.el=Ext.get(A);B.measure.setStyle(B.el.getStyle(["font-size","font-style","font-weight","font-family","line-height","text-transform","letter-spacing","word-break"]))},setFixedWidth:function(A){this.measure.setWidth(A)},getWidth:function(A){this.measure.dom.style.width="auto";return this.getSize(A).width},getHeight:function(A){return this.getSize(A).height},destroy:function(){var A=this;A.measure.destroy();delete A.el;delete A.measure}},function(){Ext.Element.override({getTextWidth:function(B,A,C){return Ext.Number.constrain(Ext.util.TextMetrics.measure(this.dom,Ext.valueFrom(B,this.dom.innerHTML,true)).width,A||0,C||1000000)}})});
Ext.define("Ext.panel.Proxy",{alternateClassName:"Ext.dd.PanelProxy",moveOnDrag:true,constructor:function(A,B){var C=this;C.panel=A;C.id=C.panel.id+"-ddproxy";Ext.apply(C,B)},insertProxy:true,setStatus:Ext.emptyFn,reset:Ext.emptyFn,update:Ext.emptyFn,stop:Ext.emptyFn,sync:Ext.emptyFn,getEl:function(){return this.ghost.el},getGhost:function(){return this.ghost},getProxy:function(){return this.proxy},hide:function(){var A=this;if(A.ghost){if(A.proxy){A.proxy.destroy();delete A.proxy}A.panel.unghost(null,A.moveOnDrag);delete A.ghost}},show:function(){var B=this,A;if(!B.ghost){A=B.panel.getSize();B.panel.el.setVisibilityMode(Ext.Element.DISPLAY);B.ghost=B.panel.ghost();if(B.insertProxy){B.proxy=B.panel.el.insertSibling({role:"presentation",cls:Ext.baseCSSPrefix+"panel-dd-spacer"});B.proxy.setSize(A)}}},repair:function(A,C,B){this.hide();Ext.callback(C,B||this)},moveProxy:function(A,B){if(this.proxy){A.insertBefore(this.proxy.dom,B)}}});
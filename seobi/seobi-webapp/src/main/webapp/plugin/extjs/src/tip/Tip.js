Ext.define("Ext.tip.Tip",{extend:"Ext.panel.Panel",alias:"widget.tip",alternateClassName:"Ext.Tip",minWidth:40,maxWidth:500,shadow:"sides",defaultAlign:"tl-bl?",constrainPosition:true,autoRender:true,hidden:true,baseCls:Ext.baseCSSPrefix+"tip",floating:{shadow:true,shim:true},focusOnToFront:false,closeAction:"hide",alwaysFramed:true,frameHeader:false,initComponent:function(){var A=this;A.floating=Ext.apply({},{shadow:A.shadow,constrain:A.constrainPosition},A.self.prototype.floating);A.callParent(arguments);A.constrain=A.constrain||A.constrainPosition},showAt:function(A){var B=this;this.callParent(arguments);if(B.isVisible()){B.setPagePosition(A[0],A[1]);if(B.constrainPosition||B.constrain){B.doConstrain()}B.toFront(true)}},privates:{initDraggable:function(){var A=this;A.draggable={el:A.getDragEl(),delegate:A.header.el,constrain:A,constrainTo:A.el.dom.parentNode};Ext.Component.prototype.initDraggable.call(A)}},ghost:undefined,unghost:undefined});
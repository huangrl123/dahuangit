Ext.define("Ext.dd.DragZone",{extend:"Ext.dd.DragSource",constructor:function(A,B){var C=this,D=C.containerScroll;C.callParent([A,B]);if(D){A=C.scrollEl||A;A=Ext.get(A);if(Ext.isObject(D)){A.ddScrollConfig=D}Ext.dd.ScrollManager.register(A)}},getDragData:function(A){return Ext.dd.Registry.getHandleFromEvent(A)},onInitDrag:function(A,B){this.proxy.update(this.dragData.ddel.cloneNode(true));this.onStartDrag(A,B);return true},getRepairXY:function(A){return Ext.fly(this.dragData.ddel).getXY()},destroy:function(){this.callParent();if(this.containerScroll){Ext.dd.ScrollManager.unregister(this.scrollEl||this.el)}}});
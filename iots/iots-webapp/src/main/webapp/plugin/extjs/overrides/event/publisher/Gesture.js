Ext.define("Ext.overrides.event.publisher.Gesture",{override:"Ext.event.publisher.Gesture"},function(){if(Ext.isIE9m){this.override({updateTouches:function(B,D){var C=B.browserEvent,A=B.getXY();C.pageX=A[0];C.pageY=A[1];this.callParent([B,D])},initHandlers:function(){var B=this,A;B.callParent();A=B.onDelegatedEvent;B.onDelegatedEvent=function(C){A.call(B,Ext.event.Event.enableIEAsync(C))}}})}});
Ext.define("Ext.aria.window.Window",{override:"Ext.window.Window",requires:["Ext.aria.panel.Panel","Ext.util.ComponentDragger","Ext.util.Region","Ext.EventManager"],closeText:"Close Window",moveText:"Move Window",resizeText:"Resize Window",deltaMove:10,deltaResize:10,ariaFocusableContainerCls:"",initComponent:function(){var B=this,A=B.tools;if(!A){B.tools=A=[]}A.unshift({type:"resize",tooltip:B.resizeText},{type:"move",tooltip:B.moveText});B.callParent(arguments);B.on("move",B.onMove,B)},afterHide:function(){Ext.aria.FocusManager.removeWindow(this);this.callParent(arguments)},ariaGetFocusFallback:function(){var A=this.focusFallbackCmp;if(A&&!A.isDestroyed){return A}return this.callParent()},onBoxReady:function(){var B=this,C=Ext.event.Event,A;B.callParent();if(B.draggable){A=B.down("tool[type=move]");B.ariaUpdate(A.getEl(),{"aria-label":B.moveText});A.keyMap=new Ext.util.KeyMap({target:A.el,key:[C.UP,C.DOWN,C.LEFT,C.RIGHT],handler:B.moveWindow,scope:B})}if(B.resizable){A=B.down("tool[type=resize]");B.ariaUpdate(A.getEl(),{"aria-label":B.resizeText});A.keyMap=new Ext.util.KeyMap({target:A.el,key:[C.UP,C.DOWN,C.LEFT,C.RIGHT],handler:B.resizeWindow,scope:B})}},onEsc:function(C,A){var B=this;if(B.hasFocus){A.stopEvent();B.close()}},getFocusEl:function(){return this.el},onFocus:function(){var A=this;A.superclass.superclass.onFocus.call(A,arguments)},onShow:function(){var A=this;A.callParent(arguments);A.focusFallbackCmp=Ext.aria.FocusManager.getFocusedCmp();Ext.aria.FocusManager.addWindow(A,A.getDefaultFocus())},moveWindow:function(E,C){var B=this,A=B.deltaMove,D=B.getPosition(),F=Ext.EventObject;switch(E){case F.RIGHT:D[0]+=A;break;case F.LEFT:D[0]-=A;break;case F.UP:D[1]-=A;break;case F.DOWN:D[1]+=A;break}B.setPagePosition(D);C.stopEvent()},resizeWindow:function(D,C){var B=this,A=B.deltaResize,G=B.getWidth(),E=B.getHeight(),F=Ext.EventObject;switch(D){case F.RIGHT:G+=A;break;case F.LEFT:G-=A;break;case F.UP:E-=A;break;case F.DOWN:E+=A;break}B.setSize(G,E);C.stopEvent()},getDefaultFocus:function(){var B=this,A;A=B.callParent();if(A===B.el){A=B}return A}});
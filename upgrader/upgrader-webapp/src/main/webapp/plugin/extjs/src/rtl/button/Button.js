Ext.define("Ext.rtl.button.Button",{override:"Ext.button.Button",getTriggerRegion:function(){var A=this,B=A._triggerRegion;if(!Ext.rootInheritedState.rtl!==!this.getInherited().rtl&&A.arrowAlign==="right"){B.begin=0;B.end=A.btnEl.getX()-A.el.getX()}else{B=A.callParent()}return B}});
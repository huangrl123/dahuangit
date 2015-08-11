Ext.define("Ext.panel.Pinnable",{extend:"Ext.Mixin",mixinId:"pinnable",pinnable:true,pinnedTip:"Unpin this item",unpinnedTip:"Pin this item",initPinnable:function(){var A=this,B=A.isPinned();A.addTool(A.pinTool=Ext.widget({xtype:"tool",type:B?"unpin":"pin",callback:"togglePin",scope:A,tooltip:B?A.pinnedTip:A.unpinnedTip}))},isPinned:function(){return !this.floating},setPinned:function(B){var A=this,C;if(B!==A.isPinned()){C=[A,B];if(A.fireEventArgs("beforepinchange",C)!==false){A.updatePinned(B);A.fireEventArgs("pinchange",C)}}},togglePin:function(){this.setPinned(!this.isPinned())},updatePinned:function(C){var B=this,A=B.pinTool;A.setTooltip(C?B.pinnedTip:B.unpinnedTip);A.setType(C?"unpin":"pin")}});
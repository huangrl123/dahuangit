Ext.define("Ext.fx.animation.Fade",{extend:"Ext.fx.animation.Abstract",alternateClassName:"Ext.fx.animation.FadeIn",alias:["animation.fade","animation.fadeIn"],config:{out:false,before:{display:null,opacity:0},after:{opacity:null},reverse:null},updateOut:function(C){var B=this.getTo(),A=this.getFrom();if(C){A.set("opacity",1);B.set("opacity",0)}else{A.set("opacity",0);B.set("opacity",1)}}});
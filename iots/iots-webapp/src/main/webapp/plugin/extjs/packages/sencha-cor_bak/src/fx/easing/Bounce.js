Ext.define("Ext.fx.easing.Bounce",{extend:"Ext.fx.easing.Abstract",config:{springTension:0.3,acceleration:30,startVelocity:0},getValue:function(){var C=Ext.Date.now()-this.getStartTime(),B=(C/this.getAcceleration()),A=B*Math.pow(Math.E,-this.getSpringTension()*B);return this.getStartValue()+(this.getStartVelocity()*A)}});
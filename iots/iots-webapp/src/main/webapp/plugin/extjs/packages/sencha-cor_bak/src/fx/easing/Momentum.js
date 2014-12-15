Ext.define("Ext.fx.easing.Momentum",{extend:"Ext.fx.easing.Abstract",config:{acceleration:30,friction:0,startVelocity:0},alpha:0,updateFriction:function(A){var B=Math.log(1-(A/10));this.theta=B;this.alpha=B/this.getAcceleration()},updateStartVelocity:function(A){this.velocity=A*this.getAcceleration()},updateAcceleration:function(A){this.velocity=this.getStartVelocity()*A;this.alpha=this.theta/A},getValue:function(){return this.getStartValue()-this.velocity*(1-this.getFrictionFactor())/this.theta},getFrictionFactor:function(){var A=Ext.Date.now()-this.getStartTime();return Math.exp(A*this.alpha)},getVelocity:function(){return this.getFrictionFactor()*this.velocity}});
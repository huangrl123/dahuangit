Ext.define("Ext.data.validator.Presence",{extend:"Ext.data.validator.Validator",alias:"data.validator.presence",type:"presence",config:{message:"Must be present"},validate:function(B){var A=!(B===undefined||B===null);return A?true:this.getMessage()}});
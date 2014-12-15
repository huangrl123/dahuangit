Ext.define("Ext.data.validator.Bound",{extend:"Ext.data.validator.Validator",alias:"data.validator.bound",type:"bound",config:{min:undefined,max:undefined,emptyMessage:"Must be present",minOnlyMessage:null,maxOnlyMessage:null,bothOnlyMessage:null},constructor:function(){var A=this;A.preventConfigure=true;A.callParent(arguments);delete A.preventConfigure;A.configure()},setConfig:function(){var A=this;A.preventConfigure=true;A.callParent(arguments);delete A.preventConfigure;A.configure()},configure:function(){var B=this,A,E,D,C;if(B.preventConfigure){return}D=B.getMin();C=B.getMax();A=B.hasMin=D!==undefined;E=B.hasMax=C!==undefined;if(A&&E){B._bothMsg=Ext.String.format(B.getBothMessage(),D,C)}else{if(A){B._minMsg=Ext.String.format(B.getMinOnlyMessage(),D)}else{if(E){B._maxMsg=Ext.String.format(B.getMaxOnlyMessage(),C)}}}},updateMin:function(){this.configure()},updateMax:function(){this.configure()},updateMinOnlyMessage:function(A){this.configure()},updateMaxOnlyMessage:function(){this.configure()},updateBothMessage:function(){this.configure()},validate:function(E){var F=this,H=F.hasMin,A=F.hasMax,B=F.getMin(),C=F.getMax(),D=true,G;if(E===undefined||E===null){return F.getEmptyMessage()}E=F.getValue(E);if(H&&A){if(E<B||E>C){D=F._bothMsg}}else{if(H){if(E<B){D=F._minMsg}}else{if(A){if(E>C){D=F._maxMsg}}}}return D},getValue:Ext.identityFn});
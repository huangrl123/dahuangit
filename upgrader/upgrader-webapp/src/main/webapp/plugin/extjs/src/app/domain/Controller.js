Ext.define("Ext.app.domain.Controller",{extend:"Ext.app.EventDomain",singleton:true,requires:["Ext.app.Controller"],type:"controller",prefix:"controller.",idMatchRe:/^\#/,constructor:function(){var A=this;A.callParent();A.monitor(Ext.app.BaseController)},match:function(D,C){var A=false,B=D.alias;if(C==="*"){A=true}else{if(C==="#"){A=!!D.isApplication}else{if(this.idMatchRe.test(C)){A=D.getId()===C.substring(1)}else{if(B){A=Ext.Array.indexOf(B,this.prefix+C)>-1}}}}return A}});
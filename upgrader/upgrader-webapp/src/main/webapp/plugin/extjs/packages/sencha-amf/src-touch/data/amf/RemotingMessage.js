Ext.define("Ext.data.amf.RemotingMessage",{alias:"data.amf.remotingmessage",config:{$flexType:"flex.messaging.messages.RemotingMessage",body:[],clientId:"",destination:"",headers:[],messageId:"",operation:"",source:"",timestamp:[],timeToLive:[]},constructor:function(A){this.initConfig(A)},encodeMessage:function(){var B=Ext.create("Ext.data.amf.XmlEncoder"),A;A=Ext.copyTo({},this.getCurrentConfig(),"$flexType,body,clientId,destination,headers,messageId,operation,source,timestamp,timeToLive",true);B.writeObject(A);return B.getBody()}});
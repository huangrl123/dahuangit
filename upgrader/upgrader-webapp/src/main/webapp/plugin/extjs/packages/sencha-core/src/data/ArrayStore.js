Ext.define("Ext.data.ArrayStore",{extend:"Ext.data.Store",alias:"store.array",alternateClassName:["Ext.data.SimpleStore"],requires:["Ext.data.proxy.Memory","Ext.data.reader.Array"],config:{proxy:{type:"memory",reader:"array"}},loadData:function(E,C){if(this.expandData){var A=[],B=0,D=E.length;for(;B<D;B++){A[A.length]=[E[B]]}E=A}this.callParent([E,C])}});
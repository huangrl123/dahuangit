describe("Ext.data.proxy.Proxy",function(){var A,E=Ext.data.proxy.Proxy,G="spec.Alien",D={extend:"Ext.data.Model",fields:[{name:"name",type:"string"},{name:"age",type:"int"},{name:"planet",type:"string"}]},C,H,F="spec.Human",B={extend:"Ext.data.Model",fields:[{name:"name",type:"string"},{name:"age",type:"int"},{name:"planet",type:"string",defaultValue:"Earth"}]};beforeEach(function(){Ext.ClassManager.enableNamespaceParseCache=false;A=new E({});C=Ext.define(G,D);H=Ext.define(F,B)});afterEach(function(){Ext.ClassManager.enableNamespaceParseCache=true;Ext.data.Model.schema.clear();Ext.undefine("spec.Alien");Ext.undefine("spec.Human")});it("should mixin Ext.mixins.Observable",function(){expect(A.mixins.observable).toEqual(Ext.mixin.Observable.prototype)});describe("instantiation",function(){it("should default the batch order to create/update/destroy",function(){expect(A.getBatchOrder()).toBe("create,update,destroy")})});describe("methods",function(){describe("getModel",function(){it("should return the proxy model",function(){A.setModel(C);expect(A.getModel()).toEqual(C)})});describe("setModel",function(){it("should have a model equal to AlienModel",function(){A.setModel(C);expect(A.getModel()).toEqual(C)});describe("if the Reader has already been instantiated",function(){beforeEach(function(){A.setReader(new Ext.data.reader.Reader({model:null}));spyOn(A.getReader(),"setModel").andReturn(true)});it("should set the Reader's Model",function(){A.setModel(C);expect(A.getReader().setModel).toHaveBeenCalledWith(C)})})});describe("batch",function(){var J,I={create:[C,H],update:[C]},K={complete:{fn:Ext.emptyFn,scope:this}};it("should run Ext.data.Batch.prototype.add 2 times",function(){J=spyOn(Ext.data.Batch.prototype,"add").andCallThrough();A.batch(I,K);expect(J.callCount).toEqual(2)});it("should run Ext.data.Batch.prototype.start 1 times",function(){J=spyOn(Ext.data.Batch.prototype,"start").andCallThrough();A.batch(I,K);expect(J.callCount).toEqual(1)})})});describe("metachange event",function(){var J=false,I={success:true,data:[{name:"alex"},{name:"ben"},{name:"don"},{name:"evan"},{name:"nige"},{name:"phil"}],metaData:{root:"data",fields:["occupation"]}},M,K,L;beforeEach(function(){A=new E({listeners:{metachange:function(N,O){J=true;M=arguments;K=N;L=O}}});A.getReader().readRecords(I)});afterEach(function(){J=false;M=K=L=null});it("should call the listener",function(){expect(J).toBe(true)});it("should return the proxy",function(){expect(K).toBe(A)});it("should return the meta data",function(){expect(L).toEqual(I.metaData)});it("should return the proxy as the first arg",function(){expect(M[0]).toBe(A)});it("should return the meta data as the second arg",function(){expect(M[1]).toBe(L)})})});
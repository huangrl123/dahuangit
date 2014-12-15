describe("Ext.data.operation.Operation",function(){var B;function A(C){B=new Ext.data.operation.Operation(C)}afterEach(function(){B=null});describe("state",function(){describe("initial state",function(){beforeEach(function(){A()});it("should not be started",function(){expect(B.isStarted()).toBe(false)});it("should not be running",function(){expect(B.isRunning()).toBe(false)});it("should not be complete",function(){expect(B.isComplete()).toBe(false)});it("should not be successful",function(){expect(B.wasSuccessful()).toBe(false)});it("should have no error",function(){expect(B.getError()).toBeUndefined()});it("should have no exception",function(){expect(B.hasException()).toBe(false)})});describe("starting",function(){beforeEach(function(){A();B.execute()});it("should be started",function(){expect(B.isStarted()).toBe(true)});it("should be running",function(){expect(B.isRunning()).toBe(true)});it("should not be complete",function(){expect(B.isComplete()).toBe(false)});it("should not be successful",function(){expect(B.wasSuccessful()).toBe(false)});it("should have no error",function(){expect(B.getError()).toBeUndefined()});it("should have no exception",function(){expect(B.hasException()).toBe(false)})});describe("completing successfully",function(){beforeEach(function(){A();B.execute();B.setSuccessful(true)});it("should be started",function(){expect(B.isStarted()).toBe(true)});it("should not be running",function(){expect(B.isRunning()).toBe(false)});it("should be complete",function(){expect(B.isComplete()).toBe(true)});it("should be successful",function(){expect(B.wasSuccessful()).toBe(true)});it("should have no error",function(){expect(B.getError()).toBeUndefined()});it("should have no exception",function(){expect(B.hasException()).toBe(false)})});describe("completing with failure",function(){beforeEach(function(){A();B.execute();B.setException("Failed")});it("should be started",function(){expect(B.isStarted()).toBe(true)});it("should not be running",function(){expect(B.isRunning()).toBe(false)});it("should be complete",function(){expect(B.isComplete()).toBe(true)});it("should not be successful",function(){expect(B.wasSuccessful()).toBe(false)});it("should have the passed error",function(){expect(B.getError()).toBe("Failed")});it("should have an exception",function(){expect(B.hasException()).toBe(true)})})});describe("aborting",function(){var D,C;beforeEach(function(){D=new Ext.data.proxy.Proxy();A({proxy:D});B.doExecute=function(){C=new Ext.data.Request();return C};spyOn(D,"abort")});afterEach(function(){D=C=null});it("should not call if the operation has not started",function(){B.abort();expect(D.abort).not.toHaveBeenCalled()});it("should not call if the operation has been completed",function(){B.execute();B.setSuccessful(true);B.abort();expect(D.abort).not.toHaveBeenCalled()});it("should padss the request for this operation to abort",function(){B.execute();B.abort();expect(D.abort).toHaveBeenCalledWith(C)})});describe("callbacks",function(){it("should trigger when setting completed",function(){var C=false;A({callback:function(){C=true}});B.execute();B.setSuccessful(true);expect(C).toBe(true)});it("should trigger when setting an exception",function(){var C=false;A({callback:function(){C=true}});B.execute();B.setException("Failed");expect(C).toBe(true)});it("should default the scope to the operation",function(){var C;A({callback:function(){C=this}});B.execute();B.setSuccessful(true);expect(C).toBe(B)});it("should use a passed scope",function(){var C={},D;A({scope:C,callback:function(){D=this}});B.execute();B.setSuccessful(true);expect(D).toBe(C)});it("should pass the records, operation and success state",function(){var C=jasmine.createSpy();A({callback:C});B.execute();B.setSuccessful(true);expect(C).toHaveBeenCalledWith(B.getRecords(),B,true)})});describe("process",function(){var G=Ext.data.reader.Reader.prototype.nullResultSet,F,D,E,C;beforeEach(function(){C=Ext.define(null,{extend:"Ext.data.Model",fields:["id"]});F={};D=new Ext.data.Request();A();B.setRecords([new C()])});afterEach(function(){C=F=D=E=null});it("should set the resultSet",function(){B.process(G,D,F);expect(B.getResultSet()).toBe(G)});it("should set the response",function(){B.process(G,D,F);expect(B.getResponse()).toBe(F)});describe("result set with success: false",function(){it("should set an exception ",function(){B.process(new Ext.data.ResultSet({success:false}),D,F);expect(B.hasException()).toBe(true);expect(B.wasSuccessful()).toBe(false)});it("should set the error to the message returned by the result set",function(){B.process(new Ext.data.ResultSet({success:false,message:"Failed"}),D,F);expect(B.getError()).toBe("Failed")})});describe("result set with success: true",function(){it("should set success if the result set is successful",function(){B.process(G,D,F);expect(B.wasSuccessful()).toBe(true)});it("should call doProcess",function(){spyOn(B,"doProcess");B.process(G,D,F);expect(B.doProcess).toHaveBeenCalledWith(G,D,F)})})});describe("retrying an operation",function(){beforeEach(function(){A();B.doExecute=function(){return new Ext.data.Request()};B.setException("Err");B.execute()});it("should clear any error",function(){expect(B.getError()).toBeUndefined()});it("should clear the success flag",function(){expect(B.wasSuccessful()).toBe(false)});it("should clear the complete flag",function(){expect(B.isComplete()).toBe(false)});it("should clear the exception flag",function(){expect(B.hasException()).toBe(false)})})});
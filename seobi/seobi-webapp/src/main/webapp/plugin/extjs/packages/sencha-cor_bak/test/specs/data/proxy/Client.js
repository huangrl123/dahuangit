describe("Ext.data.proxy.Client",function(){var A;beforeEach(function(){A=new Ext.data.proxy.Client()});it("should extend Ext.data.proxy.Proxy",function(){expect(A.superclass).toEqual(Ext.data.proxy.Proxy.prototype)});it("should throw an error on clear",function(){expect(function(){A.clear()}).toRaiseExtError()})});
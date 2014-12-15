describe("AbstractManager",function(){var A;beforeEach(function(){A=new Ext.AbstractManager()});afterEach(function(){A=null});describe("get/register/unregister",function(){it("should return undefined for an item not in the collection",function(){expect(A.get("notthere")).toBeUndefined()});it("should return the object if it exists in the collection",function(){var B={id:"item"};A.register(B);expect(A.get("item")).toBe(B)});it("should register multiple items",function(){var B={id:"item1"},C={id:"item2"};A.register(B);A.register(C);expect(A.get("item1")).toBe(B);expect(A.get("item2")).toBe(C)});it("should remove items when unregistered",function(){var B={id:"item1"},C={id:"item2"};A.register(B);A.register(C);A.unregister(C);expect(A.get("item1")).toBe(B);expect(A.get("item2")).toBeUndefined()})});describe("registerType/isRegistered/create",function(){afterEach(function(){delete Ext.util.Filter.type});it("should copy the type name onto the prototype",function(){A.registerType("filter",Ext.util.Filter);expect(Ext.util.Filter.type).toEqual("filter")});it("should return true when a type is registered",function(){A.registerType("filter",Ext.util.Filter);expect(A.isRegistered("filter")).toBe(true)});it("should return false when a type is not registered",function(){expect(A.isRegistered("notRegged")).toBe(false)});it("should thrown an exception when trying to create a type that doesn't exist",function(){expect(function(){A.create("filter")}).toRaiseExtError()});it("should return an instance of the type",function(){A.registerType("filter",Ext.util.Filter);expect(A.create({type:"filter",filterFn:Ext.emptyFn}) instanceof Ext.util.Filter).toBe(true)});it("should fallback to the default type",function(){A.registerType("filter",Ext.util.Filter);expect(A.create({filterFn:Ext.emptyFn},"filter") instanceof Ext.util.Filter).toBe(true)});it("should pass the config to the constructor",function(){A.registerType("filter",Ext.util.Filter);var B=A.create({type:"filter",property:"name",value:"x"});expect(B.getProperty()).toBe("name")})});describe("onAvailable",function(){it("should never fire if no items are added",function(){var B=jasmine.createSpy("spy");A.onAvailable("item",B);expect(B.callCount).toBe(0)});it("should never fire if items with no matching id are added",function(){var B=jasmine.createSpy("spy");A.onAvailable("item",B);A.register({id:"other"});expect(B.callCount).toBe(0)});it("should fire the function if an item is added with a matching id",function(){var B=jasmine.createSpy("spy");A.onAvailable("item",B);A.register({id:"item"});expect(B.callCount).toBe(1)});it("should fire the function if the onAvailable is bound when the item already exists",function(){var B=jasmine.createSpy("spy");A.register({id:"item"});A.onAvailable("item",B);expect(B.callCount).toBe(1)});it("should pass the item as a parameter",function(){var B={id:"item"},D,C=function(E){D=E};A.onAvailable("item",C);A.register(B);expect(D).toBe(B)});it("should default the scope to the item if not specified",function(){var B={id:"item"},D,C=function(){D=this};A.onAvailable("item",C);A.register(B);expect(D).toBe(B)});it("should use the passed scope",function(){var B={id:"item"},E,D={},C=function(){E=this};A.onAvailable("item",C,D);A.register(B);expect(E).toBe(D)});it("should remove the listener once the component is created",function(){var E=function(){++D},F=function(){++B},D=0,B=0,C={id:"item"};A.onAvailable("item",E);A.register(C);A.unregister(C);A.onAvailable("item",F);A.register(C);expect(D).toBe(1);expect(B).toBe(1)})});describe("each",function(){it("should not iterate if there are no items",function(){var B=jasmine.createSpy("spy");A.each(B);expect(B.callCount).toBe(0)});it("should loop over each item",function(){var C=jasmine.createSpy("spy"),B=0;for(;B<5;++B){A.register({id:"id"+B})}A.each(C);expect(C.callCount).toBe(5)});it("should default the scope to the manager",function(){var B={id:"item"},D,C=function(){D=this};A.register(B);A.each(C);expect(D).toBe(A)});it("should use the passed scope",function(){var B={id:"item"},D={},E,C=function(){E=this};A.register(B);A.each(C,D);expect(E).toBe(D)})});describe("getCount",function(){it("should return 0 by default",function(){expect(A.getCount()).toBe(0)});it("should return the correct count after adding items",function(){A.register({id:"a"});expect(A.getCount()).toBe(1);A.register({id:"b"});expect(A.getCount()).toBe(2)});it("should return the correct count after removing items",function(){var B={id:"item"};A.register(B);A.unregister(B);expect(A.getCount()).toBe(0)})})});
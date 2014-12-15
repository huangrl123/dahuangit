describe("Ext.Object",function(){describe("size",function(){it("should return 0 when there are no properties",function(){var A={};expect(Ext.Object.getSize(A)).toBe(0)});it("should return the number of keys",function(){var A={key1:true,key2:true,key3:true,key4:true};expect(Ext.Object.getSize(A)).toBe(4)})});describe("clear",function(){it("should remove a single key",function(){var A={x:42};Ext.Object.clear(A);expect(A.hasOwnProperty("x")).toBe(false)});it("should remove multiple keys",function(){var A={a:1,b:2,c:3};Ext.Object.clear(A);expect(A.hasOwnProperty("a")).toBe(false);expect(A.hasOwnProperty("b")).toBe(false);expect(A.hasOwnProperty("c")).toBe(false)});it("should retain items that are not hasOwnProperty on the object",function(){var A=Ext.Object.chain({a:1,b:2});A.c=3;Ext.Object.clear(A);expect(A.hasOwnProperty("c")).toBe(false);expect(A.a).toBe(1);expect(A.b).toBe(2)});it("should return the object",function(){var A={};expect(Ext.Object.clear(A)).toBe(A)})});describe("isEmpty",function(){it("should return true if there are no properties",function(){var A={};expect(Ext.Object.isEmpty(A)).toBe(true)});it("should return false if there are properties",function(){var A={key1:true};expect(Ext.Object.isEmpty(A)).toBe(false)})});describe("getKeys",function(){var A=Ext.Object.getKeys;it("should return an empty array for a null value",function(){expect(A(null)).toEqual([])});it("should return an empty array for an empty object",function(){expect(A({})).toEqual([])});it("should return all the keys in the object",function(){expect(A({foo:1,bar:2,baz:3})).toEqual(["foo","bar","baz"])})});describe("getValues",function(){var A=Ext.Object.getValues;it("should return an empty array for a null value",function(){expect(A(null)).toEqual([])});it("should return an empty array for an empty object",function(){expect(A({})).toEqual([])});it("should return all the values in the object",function(){expect(A({foo:1,bar:2,baz:3})).toEqual([1,2,3])})});describe("getKey",function(){var A=Ext.Object.getKey;it("should return null for a null object",function(){expect(A(null,"foo")).toBeNull()});it("should return null for an empty object",function(){expect(A({},"foo")).toBeNull()});it("should return null if the value doesn't exist",function(){expect(A({foo:1,bar:2},3)).toBeNull()});it("should only do strict matching",function(){expect(A({foo:1},"1")).toBeNull()});it("should return the correct key if it matches",function(){expect(A({foo:1},1)).toEqual("foo")});it("should only return the first matched value",function(){expect(A({bar:1,foo:1},1)).toEqual("bar")})});describe("equals",function(){var A=Ext.Object.equals;it("should match undefined",function(){expect(A(undefined,undefined)).toBe(true)});it("should match null",function(){expect(A(null,null)).toBe(true)});it("should not match if one object is null",function(){expect(A({},null)).toBe(false)});it("should not match if the objects have different keys",function(){var B={foo:true};var C={bar:true};expect(A(B,C)).toBe(false)});it("should not match if keys have different values",function(){var B={foo:1};var C={foo:2};expect(A(B,C)).toBe(false)});it("should use strict equality",function(){var B={foo:1};var C={foo:"1"};expect(A(B,C)).toBe(false)});it("should match objects with the same keys/values",function(){var B={foo:"value",bar:true};var C={foo:"value",bar:true};expect(A(B,C)).toBe(true)});it("should ignore key ordering",function(){var B={bar:true,foo:"value"};var C={foo:"value",bar:true};expect(A(B,C)).toBe(true)})});describe("each",function(){var A=Ext.Object.each;describe("scope/params",function(){it("should execute using the passed scope",function(){var B={},C;A({foo:1,bar:"value",baz:false},function(){C=this},B);expect(C).toBe(B)});it("should default the scope to the object",function(){var B={foo:1,bar:"value",baz:false},C;A(B,function(){C=this});expect(C).toBe(B)});it("should execute passing the key value and object",function(){var C=[],D=[],E={foo:1,bar:"value",baz:false},B;A(E,function(F,H,G){C.push(F);D.push(H);B=G});expect(C).toEqual(["foo","bar","baz"]);expect(D).toEqual([1,"value",false]);expect(B).toBe(E)})});describe("stopping",function(){it("should not stop by default",function(){var B=0;A({a:1,b:2,c:3,d:4},function(){++B});expect(B).toEqual(4)});it("should only stop if the function returns false",function(){var B=0;A({a:1,b:2,c:3,d:4},function(){++B;return null});expect(B).toEqual(4)});it("should stop immediately when false is returned",function(){var B=0;A({a:1,b:2,c:3,d:4},function(C){++B;return C!="b"});expect(B).toEqual(2)})});describe("IE workarounds",function(){it('should iterate the "constructor" property',function(){var B;Ext.Object.each({constructor:42},function(D,C){B=C});expect(B).toBe(42)});it('should iterate the "toString" property',function(){var B;Ext.Object.each({toString:42},function(D,C){B=C});expect(B).toBe(42)});it('should iterate the "valueOf" property',function(){var B;Ext.Object.each({valueOf:42},function(D,C){B=C});expect(B).toBe(42)});it('should iterate the "toLocaleString" property',function(){var B;Ext.Object.each({toLocaleString:42},function(D,C){B=C});expect(B).toBe(42)})})});describe("toQueryString",function(){var A=Ext.Object.toQueryString;describe("defaults",function(){it("should return an empty string for a null object",function(){expect(A(null)).toEqual("")});it("should return an empty string for an empty object",function(){expect(A({})).toEqual("")})});describe("simple values",function(){describe("empty values",function(){it("undefined",function(){expect(A({foo:undefined})).toEqual("foo=")});it("null",function(){expect(A({foo:null})).toEqual("foo=")});it("empty string",function(){expect(A({foo:""})).toEqual("foo=")});it("empty array",function(){expect(A({foo:""})).toEqual("foo=")});it("should join empty values correctly",function(){expect(A({foo:"",bar:"baz"})).toEqual("foo=&bar=baz")})});it("should separate a property/value by an =",function(){expect(A({foo:1})).toEqual("foo=1")});it("should separate pairs by an &",function(){expect(A({foo:1,bar:2})).toEqual("foo=1&bar=2")});it("should encode dates",function(){var B=new Date(2011,0,1);expect(A({foo:B})).toEqual("foo=2011-01-01T00%3A00%3A00")});it("should url encode the key",function(){expect(A({"a prop":1})).toEqual("a%20prop=1")});it("should url encode the value",function(){expect(A({prop:"$300 & 5 cents"})).toEqual("prop=%24300%20%26%205%20cents")});it("should encode both key and value at the same time",function(){expect(A({"a prop":"$300"})).toEqual("a%20prop=%24300")})});describe("arrays",function(){it("should support an array value",function(){expect(A({foo:[1,2,3]})).toEqual("foo=1&foo=2&foo=3")});it("should be able to support multiple arrays",function(){expect(A({foo:[1,2],bar:[3,4]})).toEqual("foo=1&foo=2&bar=3&bar=4")});it("should be able to mix arrays and normal values",function(){expect(A({foo:"val1",bar:["val2","val3"],baz:"val4"})).toEqual("foo=val1&bar=val2&bar=val3&baz=val4")})});describe("recursive",function(){it("should support both nested arrays and objects",function(){expect(decodeURIComponent(Ext.Object.toQueryString({username:"Jacky",dateOfBirth:{day:1,month:2,year:1911},hobbies:["coding","eating","sleeping",[1,2]]},true))).toEqual("username=Jacky&dateOfBirth[day]=1&dateOfBirth[month]=2&dateOfBirth[year]=1911&hobbies[0]=coding&hobbies[1]=eating&hobbies[2]=sleeping&hobbies[3][0]=1&hobbies[3][1]=2")})})});describe("merge",function(){var A=Ext.Object.merge;describe("complex values",function(){it("should copy a simple object but not have the same reference",function(){var B={foo:"prop",tada:{blah:"bleh"}},C=A({},B);expect(C).toEqual({foo:"prop",tada:{blah:"bleh"}});expect(C).not.toBe(B)});it("should NOT merge an instance (the constructor of which is not Object)",function(){var B=new Ext.Base(),C={foo:B},D=A({},C);expect(D.foo).toBe(B)})});describe("overwriting properties",function(){it("should merge objects if an object exists on the source and the passed value is an object literal",function(){expect(A({prop:{foo:1}},{prop:{bar:2}})).toEqual({prop:{foo:1,bar:2}})});it("should replace the value of the target object if it is not an object",function(){var B=new Ext.Base(),C=A({prop:1},{prop:B});expect(C.prop).toEqual(B);expect(C.prop).toBe(B)});it("should overwrite simple values",function(){expect(A({prop:1},{prop:2})).toEqual({prop:2})})});describe("merging objects",function(){it("should merge objects",function(){expect(A({},{foo:1})).toEqual({foo:1})});it("should merge right to left",function(){expect(A({},{foo:1},{foo:2},{foo:3})).toEqual({foo:3})})});it("should modify and return the source",function(){var B={},C=A(B,{foo:"bar"});expect(C.foo).toEqual("bar");expect(C).toBe(B)})});describe("toQueryObjects",function(){var A={username:"Jacky",dateOfBirth:{day:1,month:2,year:1911},hobbies:["coding","eating","sleeping",[1,2,3]]};it("simple key value",function(){expect(Ext.Object.toQueryObjects("username","Jacky")).toEqual([{name:"username",value:"Jacky"}])});it("non-recursive array",function(){expect(Ext.Object.toQueryObjects("hobbies",["eating","sleeping","coding"])).toEqual([{name:"hobbies",value:"eating"},{name:"hobbies",value:"sleeping"},{name:"hobbies",value:"coding"}])});it("recursive object",function(){expect(Ext.Object.toQueryObjects("dateOfBirth",{day:1,month:2,year:1911,somethingElse:{nested:{very:"very",deep:{inHere:true}}}},true)).toEqual([{name:"dateOfBirth[day]",value:1},{name:"dateOfBirth[month]",value:2},{name:"dateOfBirth[year]",value:1911},{name:"dateOfBirth[somethingElse][nested][very]",value:"very"},{name:"dateOfBirth[somethingElse][nested][deep][inHere]",value:true}])});it("recursive array",function(){expect(Ext.Object.toQueryObjects("hobbies",["eating","sleeping","coding",["even",["more"]]],true)).toEqual([{name:"hobbies[0]",value:"eating"},{name:"hobbies[1]",value:"sleeping"},{name:"hobbies[2]",value:"coding"},{name:"hobbies[3][0]",value:"even"},{name:"hobbies[3][1][0]",value:"more"}])})});describe("fromQueryString",function(){var A=Ext.Object.fromQueryString;describe("standard mode",function(){it("empty string",function(){expect(A("")).toEqual({})});it("simple single key value pair",function(){expect(A("name=Jacky")).toEqual({name:"Jacky"})});it("simple single key value pair with empty value",function(){expect(A("name=")).toEqual({name:""})});it("multiple key value pairs",function(){expect(A("name=Jacky&loves=food")).toEqual({name:"Jacky",loves:"food"})});it("multiple key value pairs with URI encoded component",function(){expect(A("a%20property=%24300%20%26%205%20cents")).toEqual({"a property":"$300 & 5 cents"})});it("simple array",function(){expect(A("foo=1&foo=2&foo=3")).toEqual({foo:["1","2","3"]})})});describe("recursive mode",function(){it("empty string",function(){expect(A("",true)).toEqual({})});it("simple single key value pair",function(){expect(A("name=Jacky",true)).toEqual({name:"Jacky"})});it("simple single key value pair with empty value",function(){expect(A("name=",true)).toEqual({name:""})});it("multiple key value pairs",function(){expect(A("name=Jacky&loves=food",true)).toEqual({name:"Jacky",loves:"food"})});it("multiple key value pairs with URI encoded component",function(){expect(A("a%20property=%24300%20%26%205%20cents",true)).toEqual({"a property":"$300 & 5 cents"})});it("simple array (last value with the same name will overwrite previous value)",function(){expect(A("foo=1&foo=2&foo=3",true)).toEqual({foo:"3"})});it("simple array with empty brackets",function(){expect(A("foo[]=1&foo[]=2&foo[]=3",true)).toEqual({foo:["1","2","3"]})});it("simple array with non-empty brackets",function(){expect(A("foo[0]=1&foo[1]=2&foo[2]=3",true)).toEqual({foo:["1","2","3"]})});it("simple array with non-empty brackets and non sequential keys",function(){expect(A("foo[3]=1&foo[1]=2&foo[2]=3&foo[0]=0",true)).toEqual({foo:["0","2","3","1"]})});it("simple array with non-empty brackets and non sequential keys and holes",function(){expect(A("foo[3]=1&foo[1]=2&foo[2]=3",true)).toEqual({foo:[undefined,"2","3","1"]})});it("nested array",function(){expect(A("some[0][0]=stuff&some[0][1]=morestuff&some[0][]=otherstuff&some[1]=thingelse",true)).toEqual({some:[["stuff","morestuff","otherstuff"],"thingelse"]})});it("nested object",function(){expect(A("dateOfBirth[day]=1&dateOfBirth[month]=2&dateOfBirth[year]=1911&dateOfBirth[extra][hour]=4&dateOfBirth[extra][minute]=30",true)).toEqual({dateOfBirth:{day:"1",month:"2",year:"1911",extra:{hour:"4",minute:"30"}}})});it("nested mixed types",function(){expect(A("username=Jacky&dateOfBirth[day]=1&dateOfBirth[month]=2&dateOfBirth[year]=1911&hobbies[0]=coding&hobbies[1]=eating&hobbies[2]=sleeping&hobbies[3][0]=nested&hobbies[3][1]=stuff",true)).toEqual({username:"Jacky",dateOfBirth:{day:"1",month:"2",year:"1911"},hobbies:["coding","eating","sleeping",["nested","stuff"]]})})})})});
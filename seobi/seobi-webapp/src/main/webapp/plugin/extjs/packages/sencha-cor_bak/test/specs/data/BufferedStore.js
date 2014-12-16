describe("Ext.data.BufferedStore",function(){var C,A=false;Ext.ux.ajax.SimManager.init({delay:10,defaultSimlet:null}).register({"/data/Store/reload":{data:(function(){var D=0,E=[];for(;D<5000;D++){E.push({id:D,title:"Title"+D})}return E}()),stype:"json"}});function B(D){C=new Ext.data.BufferedStore(Ext.apply({id:"store",model:"Foo",pageSize:100,proxy:{type:"ajax",url:"/data/Store/reload",reader:{type:"json"}},listeners:{prefetch:function(F,G,E,H){A=true}}},D))}beforeEach(function(){Ext.define("spec.ForumThread",{extend:"Ext.data.Model",fields:["title","forumtitle","forumid","username",{name:"replycount",type:"int"},{name:"lastpost",mapping:"lastpost",type:"date",dateFormat:"timestamp"},"lastposter","excerpt","threadid"],idProperty:"threadid"});Ext.define("Foo",{extend:"Ext.data.Model",fields:["id","title"]});MockAjaxManager.addMethods()});afterEach(function(){MockAjaxManager.removeMethods();if(C){C.destroy();C=null}Ext.data.Model.schema.clear();Ext.undefine("spec.ForumThread");Ext.undefine("Foo");A=false});it("should be able to start from any page",function(){B({model:"spec.ForumThread",pageSize:100,proxy:{type:"ajax",url:"/data/Store/reload",reader:{rootProperty:"topics",totalProperty:"totalCount"}}});C.loadPage(10);waitsFor(function(){return Ext.Object.getKeys(C.pageRequests).length===0});runs(function(){expect(C.currentPage).toBe(10);var D=C.getRange(900,999);expect(D.length).toBe(100);expect(D[0].get("title")).toEqual("Title900");expect(D[99].get("title")).toEqual("Title999")})});it("should be able to find records in a buffered store",function(){B({model:"spec.ForumThread",pageSize:100,proxy:{type:"ajax",url:"/data/Store/reload",reader:{rootProperty:"topics",totalProperty:"totalCount"}},autoLoad:true});waitsFor(function(){return C.getCount()});runs(function(){expect(C.findBy(function(D){return D.get("title")==="Title10"})).toEqual(10);expect(C.findExact("title","Title10")).toEqual(10);expect(C.find("title","title10")).toEqual(10)})});it("should clear the data when calling sort with parameters when remote sorting",function(){C=new Ext.data.BufferedStore({model:"spec.ForumThread",pageSize:100,proxy:{type:"ajax",url:"/data/Store/reload",reader:{rootProperty:"topics",totalProperty:"totalCount"}}});C.load();waitsFor(function(){return C.getCount()});runs(function(){C.sort();expect(C.getCount()).toBe(0);waitsFor(function(){return C.getCount()});runs(function(){expect(C.getCount()).toBe(100)})})});it("should load the store when filtered",function(){var D=false;B({model:"spec.ForumThread",pageSize:5,viewSize:100,proxy:{type:"ajax",url:"/data/Store/reload",reader:{rootProperty:"topics",totalProperty:"totalCount"}},autoLoad:false,listeners:{load:function(){D=true}}});C.filter("title","panel");waitsFor(function(){return D})});it("should load the store when sorted",function(){var D=false;B({model:"spec.ForumThread",pageSize:5,viewSize:100,proxy:{type:"ajax",url:"/data/Store/reload",reader:{rootProperty:"topics",totalProperty:"totalCount"}},autoLoad:false,listeners:{load:function(){D=true}}});C.sort("title","ASC");waitsFor(function(){return D})});it("should load the requested range when the pageSize is small",function(){var D=false;B({model:"spec.ForumThread",pageSize:5,viewSize:100,proxy:{type:"ajax",url:"/data/Store/reload",reader:{rootProperty:"topics",totalProperty:"totalCount"}},autoLoad:true,listeners:{load:function(){D=true}}});waitsFor(function(){return D})});describe("load",function(){it("should pass the records loaded, the operation & success to the callback",function(){var D,E,F;C=new Ext.data.BufferedStore({model:"spec.ForumThread",pageSize:100,proxy:{type:"ajax",url:"url",reader:{type:"json"}}});C.load({callback:function(I,G,H){D=I;E=G;F=H}});Ext.Ajax.mockComplete({status:200,responseText:Ext.JSON.encode([{}])});expect(Ext.isArray(D)).toBe(true);expect(D[0].isModel).toBe(true);expect(E.action).toBe("read");expect(E.$className).toBe("Ext.data.operation.Read");expect(F).toBe(true)});describe("should assign dataset index numbers to the records in the Store dependent upon configured pageSize",function(){var D;it("should not exceed 100 records",function(){B();C.load({callback:function(E){A=true;D=E.length-1}});waitsFor(function(){return A});runs(function(){expect(C.getAt(0).index).toBe(0);expect(C.getAt(99).index).toBe(99);expect(D).toBe(99)})});it("should not exceed 50 records",function(){B({pageSize:50});C.load({callback:function(E,H,F,G){A=true;D=E.length-1}});waitsFor(function(){return A});runs(function(){expect(C.getAt(0).index).toBe(0);expect(C.getAt(49).index).toBe(49);expect(D).toBe(49)})})})});describe("reload",function(){it("should not increase the number of pages when reloading",function(){var D;B({autoLoad:true});waitsFor(function(){return A});runs(function(){A=false;C.reload()});waitsFor(function(){return A});runs(function(){D=C.data.length;A=false;C.reload()});waitsFor(function(){return A});runs(function(){expect(C.data.length).toBe(D)})})})});
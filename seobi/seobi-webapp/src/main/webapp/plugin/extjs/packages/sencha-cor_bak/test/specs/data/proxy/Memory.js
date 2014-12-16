describe("Ext.data.proxy.Memory",function(){var D,E,B;function F(){D=new Ext.data.proxy.Memory({data:{users:[{id:1,name:"Ed Spencer",phoneNumber:"555 1234"},{id:2,name:"Abe Elias",phoneNumber:"666 1234"}]},model:"spec.User",reader:{type:"json",rootProperty:"users"}})}function C(I){var G=[],H;for(H=1;H<=100;++H){G.push({id:H,name:"Item "+H})}D=new Ext.data.proxy.Memory({data:G,model:"spec.User",enablePaging:I})}function A(){E=new Ext.data.operation.Read({})}beforeEach(function(){Ext.define("spec.User",{extend:"Ext.data.Model",fields:[{name:"id",type:"int"},{name:"name",type:"string"},{name:"phone",type:"string",mapping:"phoneNumber"}]})});afterEach(function(){Ext.data.Model.schema.clear();Ext.undefine("spec.User")});describe("reading data",function(){beforeEach(function(){F();A();D.read(E);B=E.getRecords()});it("should read the records correctly",function(){expect(B.length).toEqual(2);expect(B[0].get("phone")).toEqual("555 1234")})});describe("filtering",function(){it("should filter data",function(){C();A();E.setFilters([new Ext.util.Filter({filterFn:function(G){return G.getId()%2===0}})]);D.read(E);expect(E.getRecords().length).toBe(50)});it("should filter with paging",function(){C();A();E.setFilters([new Ext.util.Filter({filterFn:function(G){return G.getId()<10}})]);E.setStart(0);E.setLimit(20);D.read(E);expect(E.getRecords().length).toBe(9);expect(E.getResultSet().getTotal()).toBe(9)})});describe("sorting",function(){it("should apply sorting",function(){C();A();E.setSorters([new Ext.util.Sorter({root:"data",property:"id",direction:"DESC"})]);D.read(E);expect(E.getRecords()[0].getId()).toBe(100)})});describe("paging",function(){it("should page the data",function(){C(true);A();E.setStart(0);E.setLimit(20);D.read(E);B=E.getRecords();expect(E.getResultSet().getTotal()).toBe(100);expect(B[0].getId()).toBe(1);expect(B[B.length-1].getId()).toBe(20)})});describe("with a store",function(){var G;function H(I){G=new Ext.data.Store(Ext.apply({model:"spec.User"},I))}afterEach(function(){G.destroy();G=null});it("should load the store with correctly paged data",function(){C(true);H({proxy:D,pageSize:10});G.load();expect(G.getCount()).toBe(10)});it("should load filtered data",function(){C(true);H({proxy:D,pageSize:10000,filters:[{filterFn:function(I){return I.getId()%4===0}}]});G.load();expect(G.getCount()).toBe(25)});it("should load sorted data",function(){F();H({proxy:D,pageSize:10000,sorters:[{property:"name",direction:"ASC"}]});G.load();expect(G.first().get("name")).toBe("Abe Elias");expect(G.last().get("name")).toBe("Ed Spencer")})})});
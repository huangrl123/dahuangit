describe("Ext.data.writer.Xml",function(){var B,H,C,F,A,G={id:1,title:"Article 1",body:"content1"},D="<record><id>1</id><title>Article 1</title><body>content1</body></record>",E="<xmlData>"+D+"</xmlData>";beforeEach(function(){Ext.ClassManager.enableNamespaceParseCache=false;H=function(I){I=Ext.apply({writeAllFields:true},I);B=new Ext.data.writer.Xml(I)};A=Ext.define("spec.Article",{extend:"Ext.data.Model",fields:[{name:"id",type:"int"},{name:"title",type:"string"},{name:"body",type:"string",writeName:"content"}]});F=function(J){var I=[];Ext.each(J,function(K){I.push(new A(K))});return I};C=function(I){return new Ext.data.operation.Create({records:I})}});afterEach(function(){B=H=F=C=null;Ext.ClassManager.enableNamespaceParseCache=true;Ext.data.Model.schema.clear();Ext.undefine("spec.Article")});describe("initialization",function(){it("should default documentRoot to xmlData",function(){H();expect(B.getDocumentRoot()).toBe("xmlData")});it("should default header to ''",function(){H();expect(B.getHeader()).toBe("")});it("should default record to record",function(){H();expect(B.getRecord()).toBe("record")})});describe("header",function(){it("should not push a header if one is not specified",function(){H();var I=B.write(new Ext.data.Request({operation:C(F([G]))}));expect(I.getXmlData()).toBe(E)});it("should append any header",function(){H({header:"foo"});var I=B.write(new Ext.data.Request({operation:C(F([G]))}));expect(I.getXmlData()).toBe("foo"+E)})});describe("root",function(){it("should include the root by default",function(){H();var I=B.write(new Ext.data.Request({operation:C(F([G]))}));expect(I.getXmlData()).toBe(E)});it("should use any custom root specified",function(){H({documentRoot:"customRoot"});var I=B.write(new Ext.data.Request({operation:C(F([G]))}));expect(I.getXmlData()).toBe(E.replace(/xmlData/g,"customRoot"))});it("should exclude the root if the root is empty and there's 1 record",function(){H({documentRoot:""});var I=B.write(new Ext.data.Request({operation:C(F([G]))}));expect(I.getXmlData()).toBe(D)});it("should force the defaultDocumentRoot if root is empty and there's more than 1 record",function(){H({documentRoot:""});var I=B.write(new Ext.data.Request({operation:C(F([G,{id:2,title:"Article 2",body:"content2"}]))}));var J=["<xmlData>",D,D.replace(/1/g,"2"),"</xmlData>"];expect(I.getXmlData()).toBe(J.join(""))});it("should respect a custom defaultDocumentRoot",function(){H({documentRoot:"",defaultDocumentRoot:"otherRoot"});var I=B.write(new Ext.data.Request({operation:C(F([G,{id:2,title:"Article 2",body:"content2"}]))}));var J=["<otherRoot>",D,D.replace(/1/g,"2"),"</otherRoot>"];expect(I.getXmlData()).toBe(J.join(""))})});describe("transform",function(){it("should invoke the transform function",function(){var K=function(L){return[{id:10,title:"Article 10",body:"content10"}]};H({transform:K});var I=B.write(new Ext.data.Request({operation:C(F([G]))}));var J="<xmlData><record><id>10</id><title>Article 10</title><body>content10</body></record></xmlData>";expect(I.getXmlData()).not.toBe(E);expect(I.getXmlData()).toEqual(J)});it("should invoke the transform function with the specified scope",function(){var I={};var L=function(M){expect(this).toEqual(I);return[{id:10,title:"Article 10",body:"content10"}]};H({transform:{fn:L,scope:I}});var J=B.write(new Ext.data.Request({operation:C(F([G]))}));var K="<xmlData><record><id>10</id><title>Article 10</title><body>content10</body></record></xmlData>";expect(J.getXmlData()).not.toBe(E);expect(J.getXmlData()).toEqual(K)})})});
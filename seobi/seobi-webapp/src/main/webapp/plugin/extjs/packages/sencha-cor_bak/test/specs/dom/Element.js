describe("Ext.dom.Element",function(){describe("instantiation",function(){var C,B;beforeEach(function(){B=document.createElement("div");document.body.appendChild(B)});afterEach(function(){document.body.removeChild(B)});it("should set dom element id if it hasn't already one",function(){C=new Ext.dom.Element(B);expect(B.id).toBeDefined()});it("should not set dom element id if it has already one",function(){var D=Ext.id();B.id=D;C=new Ext.dom.Element(B);expect(B.id).toEqual(D)});it("should set dom property to dom element",function(){C=new Ext.dom.Element(B);expect(C.dom).toBe(B)});it("should set id property to dom id",function(){var D=Ext.id();B.id=D;C=new Ext.dom.Element(B);expect(C.id).toEqual(D)});it("should find a dom element if a string corresponding to it's id is passed as first argument",function(){var D=Ext.id();B.id=D;C=new Ext.dom.Element(D);expect(C.dom).toBe(B)});it("should throw error if the Element has an invalid id",function(){function D(F){var E=document.createElement("div");E.id=F;document.body.appendChild(E);expect(function(){new Ext.Element(E)}).toThrow('Invalid Element "id": "'+F+'"');document.body.removeChild(E)}D(".abcdef");D("0a...");D("12345");D(".abc-def");D("<12345/>");D("1<>234.567")})});function A(B){describe("methods (using "+(B?"Ext.fly()":"new Ext.dom.Element()")+")",function(){var D,E;function C(F){D=document.createElement(F);document.body.appendChild(D);return B?Ext.fly(D):Ext.get(D)}afterEach(function(){if(E){E.destroy();E=null}});describe("set",function(){beforeEach(function(){E=C("div")});it("should call Ext.core.DomHelper.applyStyles if object passed as first argument has style property",function(){var F={width:"100px"};spyOn(E,"applyStyles");E.set({style:F});expect(E.applyStyles).toHaveBeenCalledWith(F)});it("should set dom element className if object passed as first argument has cls property",function(){var F="x-test-class";E.set({cls:F});expect(E.dom.className).toEqual(F)});it("should use setAttribute by default",function(){spyOn(E.dom,"setAttribute");E.set({align:"center"});expect(E.dom.setAttribute).toHaveBeenCalledWith("align","center")});it("should be able to use expandos",function(){spyOn(E.dom,"setAttribute");E.set({align:"center"},false);expect(E.dom.align).toEqual("center")})});describe("is",function(){beforeEach(function(){E=C("div")});it("Returns true if this element matches the passed simple selector",function(){E.set({cls:"x-test-class"});expect(E.is("div.x-test-class")).toBe(true)})});describe("focus",function(){beforeEach(function(){E=C("div")});it("should focus dom element",function(){spyOn(E.dom,"focus");E.focus();expect(E.dom.focus).toHaveBeenCalled()});it("should be able to defer dom element focus",function(){spyOn(E.dom,"focus");E.focus(1);waitsFor(function(){return E.dom.focus.calls.length===1},"element.dom.focus was never called");runs(function(){expect(E.dom.focus).toHaveBeenCalled()})});it("should ignore any exception",function(){E.dom.focus=function(){throw"error"};expect(E.focus.bind(E)).not.toThrow("error")})});describe("blur",function(){beforeEach(function(){E=C("div")});it("should blur dom element",function(){spyOn(E.dom,"blur");E.blur();expect(E.dom.blur).toHaveBeenCalled()});it("should ignore any exception",function(){E.dom.blur=function(){throw"error"};expect(E.blur.bind(E)).not.toThrow("error")})});describe("getValue",function(){beforeEach(function(){E=C("div");E.dom.value="10"});it("should return the dom value",function(){expect(E.getValue()).toEqual("10")});it("should return the dom value as Number",function(){expect(E.getValue(true)).toEqual(10)})});describe("listeners",function(){var F;beforeEach(function(){F={delay:10}});xdescribe("deprecated (EventManager)",function(){describe("addListener",function(){beforeEach(function(){E=C("div")});it("should call Ext.EventManager.on",function(){spyOn(Ext.EventManager,"on");E.addListener("click",Ext.emptyFn,fakeScope,F);expect(Ext.EventManager.on).toHaveBeenCalledWith(E,"click",Ext.emptyFn,fakeScope,F)})});describe("removeListener",function(){beforeEach(function(){E=C("div")});it("should call Ext.EventManager.un",function(){spyOn(Ext.EventManager,"un");E.removeListener("click",Ext.emptyFn,fakeScope);expect(Ext.EventManager.un).toHaveBeenCalledWith(E,"click",Ext.emptyFn,fakeScope)})});describe("removeAllListener",function(){beforeEach(function(){E=C("div")});it("should call Ext.EventManager.removeAll",function(){spyOn(Ext.EventManager,"removeAll");E.removeAllListeners();expect(Ext.EventManager.removeAll).toHaveBeenCalledWith(E.dom)})});describe("purgeAllListener",function(){it("should call Ext.EventManager.purgeElement",function(){E=C("div");spyOn(Ext.EventManager,"purgeElement");E.purgeAllListeners();expect(Ext.EventManager.purgeElement).toHaveBeenCalledWith(E)});it("should work with images",function(){E=C("img");expect(function(){E.purgeAllListeners()}).not.toThrow();E.destroy()})})})});describe("addUnits",function(){beforeEach(function(){E=C("div")});it("should return an empty string if size passed is an empty string",function(){expect(E.addUnits("")).toEqual("")});it("should return auto if size passed is 'auto' string",function(){expect(E.addUnits("auto")).toEqual("auto")});it("should return an empty string if size passed is undefined",function(){expect(E.addUnits(undefined)).toEqual("")});it("should return an empty string if size passed is null",function(){expect(E.addUnits(null)).toEqual("")})});describe("destroy",function(){var G,F;beforeEach(function(){E=C("div");G=E.id;F=E.dom});beforeEach(function(){E.destroy()});it("should remove dom property",function(){expect(E.dom).toBe(null)});it("should should remove the cache entry",function(){expect(G in Ext.cache).toBe(false)});it("should remove the element from the dom",function(){expect(F.parentNode).toBeNull()})});describe("hover",function(){var H,F,G;beforeEach(function(){E=C("div");H=function(){return 1};F=function(){return 2};G={foo:true};spyOn(E,"on")});describe("mouseenter event",function(){it("should add a listener on mouseenter",function(){E.hover(H,F,fakeScope,G);expect(E.on).toHaveBeenCalledWith("mouseenter",H,fakeScope,G)});it("should set scope to element.dom if it is not passed in arguments",function(){E.hover(H,F,null,G);expect(E.on).toHaveBeenCalledWith("mouseenter",H,E.dom,G)})});describe("mouseleave event",function(){it("should add a listener on mouseleave",function(){E.hover(H,F,fakeScope,G);expect(E.on).toHaveBeenCalledWith("mouseleave",F,fakeScope,G)});it("should set scope to element.dom if it is not passed in arguments",function(){E.hover(H,F,null,G);expect(E.on).toHaveBeenCalledWith("mouseleave",F,E.dom,G)})})});describe("contains",function(){});describe("mask",function(){describe("masking the body el",function(){var I,F,H,K,J;function G(L){I=new Ext.Component({height:L||200,renderTo:Ext.getBody()});F=Ext.getBody().mask({msg:"Tom Sawyer"});H=document.body;K=H.scrollHeight;J=H.scrollWidth}afterEach(function(){Ext.removeNode(F.dom.nextSibling);Ext.removeNode(F.dom);Ext.destroy(I,F);I=F=H=K=J=null})})});xdescribe("deprecated 5.0",function(){describe("getAttributeNS",function(){beforeEach(function(){E=C("div")});it("should call element getAttribute",function(){spyOn(E,"getAttribute");E.getAttributeNS("ns1","align");expect(E.getAttribute).toHaveBeenCalledWith("align","ns1")})})});describe("getAttribute",function(){var G,F;beforeEach(function(){E=C("div");G=Ext.getBody().createChild({tag:"div"});if(E.dom.setAttribute){E.dom.setAttribute("qtip","bar");G.dom.setAttribute("ext:qtip","foo")}else{E.dom["qtip"]="bar";G.dom["ext:qtip"]="foo"}if(E.dom.setAttributeNS){F=Ext.getBody().createChild({tag:"div"});F.dom.setAttributeNS("ext","qtip","foobar")}});afterEach(function(){G.destroy();if(F){F.destroy()}});describe("without namespace",function(){it("should return the attribute value if it exists",function(){expect(E.getAttribute("qtip")).toEqual("bar")});it("should return null if the attribute does not exist",function(){expect(E.getAttribute("nothing")).toBeNull()})});describe("with namespace",function(){it("should return null on a non-namespaced attribute",function(){expect(E.getAttribute("qtip","ext")).toBeNull()});it("should return null if the attribute belong to another namespace",function(){expect(G.getAttribute("qtip","nothing")).toBeNull()});it("should return the attribute value if it belongs to the namespace",function(){if(F){expect(F.getAttribute("qtip","ext")).toEqual("foobar")}});it("should handle xml namespace",function(){expect(G.getAttribute("qtip","ext")).toEqual("foo")})})});describe("update",function(){beforeEach(function(){E=C("div");E.dom.innerHTML="hello world"});it("should update dom element innerHTML",function(){E.update("foobar");expect(E.dom).hasHTML("foobar")});it("should return element",function(){expect(E.update("foobar")).toBe(E)})});describe("prototype aliases",function(){beforeEach(function(){E=C("div")});it("should aliases addListener with on",function(){expect(typeof(E.on)).toEqual("function")});it("should aliases removeListener with un",function(){expect(typeof(E.un)).toEqual("function")});it("should aliases removeAllListeners with clearListeners",function(){expect(typeof(E.clearListeners)).toEqual("function")})});describe("visibilityMode",function(){beforeEach(function(){E=C("div")});it("must be able to setVisibilityMode and getVisibilityMode",function(){E.setVisibilityMode(Ext.dom.Element.DISPLAY);expect(E.getVisibilityMode()).toBe(Ext.dom.Element.DISPLAY);E.setVisibilityMode(Ext.dom.Element.VISIBILITY);expect(E.getVisibilityMode()).toBe(Ext.dom.Element.VISIBILITY)});it("should retain visibilityMode on flyweights",function(){Ext.fly(E.dom).setVisibilityMode(Ext.dom.Element.DISPLAY);expect(Ext.fly(E.dom).getVisibilityMode()).toBe(Ext.dom.Element.DISPLAY)})});describe("visibility",function(){var H,G,F=[Ext.dom.Element.DISPLAY,Ext.dom.Element.VISIBILITY];beforeEach(function(){E=C("div");H=E.createChild({tag:"div"});if(H){H.setVisible(true);G=H.createChild({tag:"div"});if(G){G.setVisible(true)}}});afterEach(function(){if(G){G.destroy()}if(H){H.destroy()}});it("should toggle the visibility of the element itself",function(){for(var I in F){E.setVisibilityMode(F[I]);E.setVisible(false);expect(E.isVisible(false)).toBe(false);E.setVisible(true);expect(E.isVisible(false)).toBe(true)}});it("should toggle the 'deep' visibility of the grand-child",function(){for(var I in F){E.setVisibilityMode(F[I]);E.setVisible(false);expect(G.isVisible(true)).toBe(false);E.setVisible(true);expect(G.isVisible(true)).toBe(true)}})});if(!B){describe("setVertical",function(){beforeEach(function(){var F=document.styleSheets[0],H=".vert",G=["-webkit-transform: rotate(90deg);","-moz-transform: rotate(90deg);","-o-transform: rotate(90deg);","transform: rotate(90deg);","filter: progid:DXImageTransform.Microsoft.BasicImage(rotation=1);",].join("");if(F.insertRule){F.insertRule(H+"{"+G+"}",1)}else{F.addRule(H,G)}E=C("div");E.setWidth(100);E.setHeight(30);E.setVertical(90,"vert")});afterEach(function(){var F=document.styleSheets[0];if(F.deleteRule){F.deleteRule(1)}else{F.removeRule(F.rules.length-1)}});it("should add the css class",function(){expect(E.hasCls("vert")).toBe(true)});it("should get the width using getWidth()",function(){expect(E.getWidth()).toBe(30)});it("should get the width using getStyle('width')",function(){expect(E.getStyle("width")).toBe("30px")});it("should get the height using getHeight",function(){expect(E.getHeight()).toBe(100)});it("should get the height using getStyle('height')",function(){expect(E.getStyle("height")).toBe("100px")});it("should set the width using setWidth()",function(){E.setWidth(200);expect(E.getWidth()).toBe(200)});it("should set the width using setStyle('width')",function(){E.setStyle("width","200px");expect(E.getWidth()).toBe(200)});it("should set the height using setHeight()",function(){E.setHeight(200);expect(E.getHeight()).toBe(200)});it("should set the height using setStyle('height')",function(){E.setStyle("height","200px");expect(E.getHeight()).toBe(200)});describe("setHorizontal",function(){beforeEach(function(){E.setHorizontal()});it("should remove the css class",function(){expect(E.hasCls("vert")).toBe(false)});it("should get the width using getWidth()",function(){expect(E.getWidth()).toBe(100)});it("should get the width using getStyle('width')",function(){expect(E.getStyle("width")).toBe("100px")});it("should get the height using getHeight",function(){expect(E.getHeight()).toBe(30)});it("should get the height using getStyle('height')",function(){expect(E.getStyle("height")).toBe("30px")});it("should set the width using setWidth()",function(){E.setWidth(200);expect(E.getWidth()).toBe(200)});it("should set the width using setStyle('width')",function(){E.setStyle("width","200px");expect(E.getWidth()).toBe(200)});it("should set the height using setHeight()",function(){E.setHeight(200);expect(E.getHeight()).toBe(200)});it("should set the height using setStyle('height')",function(){E.setStyle("height","200px");expect(E.getHeight()).toBe(200)})})})}})}A();A(true);describe("class methods",function(){var E,B,D,C,F;beforeEach(function(){E=Ext.getBody().createChild({tag:"div"});D=E.dom;F=Ext.id();C=document.createElement("div");C.id=F;document.body.appendChild(C)});afterEach(function(){E.destroy();if(B){B.destroy()}if(C&&C.parentNode===document.body){document.body.removeChild(C)}});describe("get",function(){describe("alias",function(){it("should alias Ext.dom.Element.get with Ext.get",function(){spyOn(Ext.dom.Element,"get");Ext.get();expect(Ext.dom.Element.get).toHaveBeenCalled()})});describe("passing string id as first argument",function(){describe("with a dom element which is not already encapsulated",function(){it("should return a new Ext.dom.Element",function(){B=Ext.get(F);expect(B instanceof Ext.dom.Element).toBe(true)});it("should encapsulate the dom element in the Ext.dom.Element",function(){B=Ext.get(F);expect(B.dom).toBe(C)});it("should add element to Ext.cache",function(){B=Ext.get(F);expect(Ext.cache[F]===B)})});describe("with a dom element which is already encapsulated",function(){it("should return the corresponding Ext.Element",function(){expect(Ext.get(D)).toBe(E)})})});describe("passing dom element as first argument",function(){describe("with a dom element which is not already encapsulated",function(){it("should return a new Ext.dom.Element",function(){B=Ext.get(C);expect(B instanceof Ext.dom.Element).toBe(true)});it("should encapsulate the dom element in the Ext.dom.Element",function(){B=Ext.get(C);expect(B.dom).toBe(C)});it("should add element to Ext.cache",function(){expect(Ext.cache[C.id]===C)})});describe("with a dom element which is already encapsulated",function(){it("should return the corresponding Ext.Element",function(){expect(Ext.get(D.id)).toBe(E)})})});describe("passing an Ext.dom.Element as first argument",function(){it("should return Ext.dom.Element",function(){expect(Ext.get(E)).toBe(E)})});describe("passing a Ext.dom.FlyWeight as first argument",function(){it("should return Ext.dom.Element",function(){var G=Ext.get(Ext.fly(D));expect(G).toBe(E);expect(G.isFly).toBeUndefined()})});describe("passing a CompositeElement as first argument",function(){var G;beforeEach(function(){G=Ext.select("div")});it("should return Ext.dom.Element",function(){expect(Ext.get(G)).toBe(G)})});describe("passing an array as first argument",function(){it("should call Ext.dom.Element.select",function(){var G=[D,C];spyOn(Ext.dom.Element,"select");Ext.get(G);expect(Ext.dom.Element.select).toHaveBeenCalledWith(G)})});describe("passing document as first argument",function(){it("should return an Ext.dom.Element",function(){expect(Ext.get(document) instanceof Ext.dom.Element).toBe(true)});xit("should return a bogus Ext.dom.Element",function(){expect(Ext.get(document).id).not.toBeDefined()});it("should return an Ext.dom.Element that encapsulate document",function(){expect(Ext.get(document).dom).toBe(document)})});it("should wrap a documentFragment",function(){var G=document.createDocumentFragment(),H=Ext.get(G);expect(H instanceof Ext.dom.Element).toBe(true);expect(H.dom).toBe(G)});it("should wrap the window object",function(){var G=window,H=Ext.get(G);expect(H instanceof Ext.dom.Element).toBe(true);expect(H.dom).toBe(G)});it("should wrap the document object",function(){var G=document,H=Ext.get(G);expect(H instanceof Ext.dom.Element).toBe(true);expect(H.dom).toBe(G)});describe("document and window within iframe",function(){var G;beforeEach(function(){G=document.createElement("iframe");document.body.appendChild(G)});afterEach(function(){document.body.removeChild(G)});it("should wrap an iframe's window object",function(){var H=G.contentWindow,I=Ext.get(H);expect(I instanceof Ext.dom.Element).toBe(true);expect(I.dom).toBe(H)});it("should wrap an iframe's document object",function(){var H=G.contentWindow.document,I=Ext.get(H);expect(I instanceof Ext.dom.Element).toBe(true);expect(I.dom).toBe(H)})});it("should not wrap a text node",function(){expect(Ext.get(document.createTextNode(("foo")))).toBe(null)})});xdescribe("garbageCollector",function(){});describe("fly",function(){var G;beforeEach(function(){spyOn(Ext,"getDom").andCallThrough()});describe("use strict",function(){var H;beforeEach(function(){Ext.fly=(function(I){H=I;return function(J,K){return I(J,K||"_global")}}(Ext.fly))});afterEach(function(){Ext.fly=H});it("should work when called by strict mode function",function(){var I=Ext.fly(C)})});describe("global flyweight",function(){beforeEach(function(){G=Ext.fly(C)});it("should return an Ext.dom.Element.Fly",function(){expect(G instanceof Ext.dom.Fly).toBe(true)});it("should not cache a dom element",function(){expect(Ext.cache[C.id]).toBeUndefined()});it("should call Ext.getDom",function(){expect(Ext.getDom).toHaveBeenCalledWith(C)})});describe("named reusable flyweight",function(){beforeEach(function(){G=Ext.fly(C,"myflyweight")});it("should return an Ext.dom.Element.Flyweight",function(){expect(G instanceof Ext.dom.Fly).toBe(true)});it("should not cache a dom element",function(){expect(Ext.cache[C.id]).toBeUndefined()});it("should call Ext.getDom",function(){expect(Ext.getDom).toHaveBeenCalledWith(C)})});it("should wrap a documentFragment",function(){var H=document.createDocumentFragment(),I=Ext.fly(H);expect(I instanceof Ext.dom.Fly).toBe(true);expect(I.dom).toBe(H)});it("should wrap the window object",function(){var H=window,I=Ext.fly(H);expect(I instanceof Ext.dom.Fly).toBe(true);expect(I.dom).toBe(H)});it("should wrap the document object",function(){var H=document,I=Ext.fly(H);expect(I instanceof Ext.dom.Fly).toBe(true);expect(I.dom).toBe(H)});describe("document and window within iframe",function(){var H;beforeEach(function(){H=document.createElement("iframe");document.body.appendChild(H)});afterEach(function(){document.body.removeChild(H)});it("should wrap an iframe's window object",function(){var I=H.contentWindow,J=Ext.fly(I);expect(J instanceof Ext.dom.Fly).toBe(true);expect(J.dom).toBe(I)});it("should wrap an iframe's document object",function(){var I=H.contentWindow.document,J=Ext.fly(I);expect(J instanceof Ext.dom.Fly).toBe(true);expect(J.dom).toBe(I)})});it("should not wrap a text node",function(){expect(Ext.fly(document.createTextNode(("foo")))).toBe(null)})});describe("aliases",function(){it("should aliases Ext.dom.Element.get with Ext.get",function(){spyOn(Ext.dom.Element,"get");Ext.get();expect(Ext.dom.Element.get).toHaveBeenCalled()});it("should aliases Ext.fly with Ext.Element.fly",function(){spyOn(Ext,"fly");Ext.Element.fly();expect(Ext.fly).toHaveBeenCalled()})})});describe("getXY",function(){var B;beforeEach(function(){B=document.createElement("div")});it("should not throw when reading unattached element",function(){Ext.fly(B).getXY()})});describe("Ext",function(){it("should return false when an Ext.Element instance is passed to Ext.isElement",function(){expect(Ext.isElement(Ext.getBody())).toBe(false)});it("should return false when an Ext.Element instance is passed to Ext.isTextNode",function(){expect(Ext.isTextNode(Ext.getBody())).toBe(false)})})},"/src/dom/Element.js");
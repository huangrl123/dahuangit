describe("Ext.Function",function(){var E,G,F,B,C,H=function(J,I,K){K=K||1;waitsFor(function(){return J.calls.length>=K});runs(I)},D=function(){F=[];B=[];C=[];E=window.setTimeout;window.setTimeout=function(J,I){F.push(I);var K=E.apply(this,arguments);B.push(K);return K};G=window.clearTimeout;window.clearTimeout=function(I){C.push(I);G.apply(this,arguments)}},A=function(){F=undefined;B=undefined;C=undefined;window.setTimeout=E;window.clearTimeout=G};describe("bind",function(){var I,J;beforeEach(function(){I=jasmine.createSpy("bindSpy")});it("should return a function if a function is passed as first argument",function(){J=Ext.Function.bind(I,this);expect(typeof J==="function").toBe(true)});it("should use the correct scope",function(){J=Ext.Function.bind(I,fakeScope);J();expect(I.calls[0].object).toBe(fakeScope)});it("should call the first function when it is executed",function(){J=Ext.Function.bind(I,this);J();expect(I).toHaveBeenCalled()});describe("argument passing",function(){it("should use default args if none are passed",function(){J=Ext.Function.bind(I,this,["a","b"]);J();expect(I).toHaveBeenCalledWith("a","b")});it("should use passed args if they are present",function(){J=Ext.Function.bind(I,this);J("c","d");expect(I).toHaveBeenCalledWith("c","d")});it("should append args",function(){J=Ext.Function.bind(I,this,["a","b"],true);J("c","d");expect(I).toHaveBeenCalledWith("c","d","a","b")});it("should append args at the given index",function(){J=Ext.Function.bind(I,this,["a","b"],0);J("c","d");expect(I).toHaveBeenCalledWith("a","b","c","d")})})});describe("pass",function(){it("should pass the specified array of arguments as the first arguments to the given function",function(){var J=jasmine.createSpy(),K=[0,1,2],I=Ext.Function.pass(J,K);I(3,4,5);expect(J).toHaveBeenCalledWith(0,1,2,3,4,5)});it("should pass the specified string argument as the first argument to the given function",function(){var J=jasmine.createSpy(),K="a",I=Ext.Function.pass(J,K);I("b","c");expect(J).toHaveBeenCalledWith("a","b","c")});it("should pass the specified numeric argument as the first argument to the given function",function(){var J=jasmine.createSpy(),K=0,I=Ext.Function.pass(J,K);I(1);expect(J).toHaveBeenCalledWith(0,1)});it("should pass the specified 'arguments' argument as the first argument to the given funciton",function(){var I=function(){var K=jasmine.createSpy(),L=arguments,J=Ext.Function.pass(K,L);J(3,4,5);expect(K).toHaveBeenCalledWith(0,1,2,3,4,5)};I(0,1,2)});it("should discard the argument if it's undefined",function(){var J=jasmine.createSpy(),K=undefined,I=Ext.Function.pass(J,K);I(1);expect(J).toHaveBeenCalledWith(1)});it("should use 'this' as default scope",function(){var K="a",J=jasmine.createSpy().andCallFake(function(){K=this.foo}),I=Ext.Function.pass(J,"c");I("d");expect(J).toHaveBeenCalledWith("c","d");expect(K).toBeUndefined()});it("should override 'this' with the specified scope",function(){var K="a",L={foo:"b"},J=jasmine.createSpy().andCallFake(function(){K=this.foo}),I=Ext.Function.pass(J,"c",L);I("d");expect(J).toHaveBeenCalledWith("c","d");expect(K).toBe("b")})});describe("clone",function(){it("should clone the given function",function(){var K=jasmine.createSpy().andCallFake(function(L){return"bar"}),I=Ext.Function.clone(K),J=I("foo");expect(J).toBe("bar");expect(K).toHaveBeenCalledWith("foo")})});describe("createInterceptor",function(){var M,L,K,I,J;beforeEach(function(){I=false;J=false;L=jasmine.createSpy("interceptorSpy").andCallFake(function(){I=true});K=jasmine.createSpy("interceptedSpy").andCallFake(function(){J=I})});describe("if no function is passed",function(){it("should return the same function",function(){expect(Ext.Function.createInterceptor(K)).toEqual(K)})});describe("if a function is passed",function(){beforeEach(function(){M=Ext.Function.createInterceptor(K,L,fakeScope);M()});it("should return a new function",function(){expect(typeof M==="function").toBe(true);expect(M).not.toEqual(K)});it("should set the correct scope for the interceptor function",function(){expect(L.calls[0].object).toBe(fakeScope)});it("should call the interceptor function first",function(){expect(J).toBe(true)})});describe("if the interceptor function returns false",function(){it("should not execute the original function",function(){M=Ext.Function.createInterceptor(K,function(){return false});M();expect(K).not.toHaveBeenCalled()})});describe("returnValue",function(){beforeEach(function(){K=function(){return"Original"};L=function(){return false}});describe("when interceptorFn returns false",function(){it("should return null as a default",function(){M=Ext.Function.createInterceptor(K,L);expect(M()).toBeNull()});it("should accept a custom returnValue",function(){M=Ext.Function.createInterceptor(K,L,null,"Custom");expect(M()).toBe("Custom")});it("should accept a falsy returnValue",function(){M=Ext.Function.createInterceptor(K,L,null,false);expect(M()).toBe(false)})});it("should return the value of the original function if false is not returned",function(){L=function(){return};M=Ext.Function.createInterceptor(K,L);expect(M()).toBe("Original")})})});describe("createDelayed",function(){(Ext.isIE8?xit:it)("should create bind to the given function to be called after x milliseconds",function(){D();var I=jasmine.createSpy(),J=Ext.Function.createDelayed(I,2);J("foo");expect(F.shift()).toBe(2);expect(I).not.toHaveBeenCalled();H(I,function(){expect(I).toHaveBeenCalledWith("foo")});A()});it("should use the specified scope as 'this'",function(){var J={x:"foo"},I=jasmine.createSpy().andCallFake(function(){this.x="bar"}),K=Ext.Function.createDelayed(I,2,J);K();expect(I).not.toHaveBeenCalled();expect(J.x).toBe("foo");H(I,function(){expect(J.x).toBe("bar")})});it("should override the call arguments with the specified arguments",function(){var J={},L=[0,1,2],I=jasmine.createSpy(),K=Ext.Function.createDelayed(I,2,J,L);K(3,4,5);expect(I).not.toHaveBeenCalled();H(I,function(){expect(I).toHaveBeenCalledWith(0,1,2)})});it("should append the specified arguments to the call arguments when appendArgs is true",function(){var J={},L=[0,1,2],I=jasmine.createSpy(),K=Ext.Function.createDelayed(I,2,J,L,true);K(3,4,5);expect(I).not.toHaveBeenCalled();H(I,function(){expect(I).toHaveBeenCalledWith(3,4,5,0,1,2)})});it("should insert the specified arguments into the call arguments at the position specified by appendArgs",function(){var J={},L=[0,1,2],I=jasmine.createSpy(),K=Ext.Function.createDelayed(I,2,J,L,2);K(3,4,5);expect(I).not.toHaveBeenCalled();H(I,function(){expect(I).toHaveBeenCalledWith(3,4,0,1,2,5)})})});describe("defer",function(){var I;beforeEach(function(){I=jasmine.createSpy("deferSpy")});it("should execute the function after the specified number of milliseconds",function(){Ext.defer(I,10);waitsFor(function(){return I.calls.length===1},"fn was never called");runs(function(){expect(I).toHaveBeenCalled()})});it("should execute the function directly if the specified number of milliseconds is <= 0",function(){Ext.defer(I,0);expect(I).toHaveBeenCalled()});it("should set the correct scope",function(){Ext.defer(I,10,fakeScope);waitsFor(function(){return I.calls.length===1},"fn was never called");runs(function(){expect(I.calls[0].object).toBe(fakeScope)})});it("should pass the correct arguments",function(){Ext.defer(I,10,this,[1,2,3]);waitsFor(function(){return I.calls.length===1},"fn was never called");runs(function(){expect(I).toHaveBeenCalledWith(1,2,3)})});it("should return a timeout number",function(){expect(typeof Ext.defer(function(){},10)==="number").toBe(true)})});describe("createSequence",function(){var I,L,J,K,M;beforeEach(function(){K=false;M=false;J=jasmine.createSpy("interceptedSpy").andCallFake(function(){K=true});L=jasmine.createSpy("sequenceSpy").andCallFake(function(){M=K})});describe("if no function is passed",function(){it("should return the same function",function(){expect(Ext.Function.createSequence(J)).toEqual(J)})});describe("if a function is passed",function(){beforeEach(function(){I=Ext.Function.createSequence(J,L,fakeScope);I()});it("should return a new function",function(){expect(typeof I==="function").toBe(true);expect(I).not.toEqual(J)});it("should set the correct scope for the sequence function",function(){expect(L.calls[0].object).toBe(fakeScope)});it("should call the sequence function first",function(){expect(M).toBe(true)})})});describe("createBuffered",function(){(Ext.isIE8?xit:it)("should prevent the execution of multiple calls of the buffered function within the timeout period",function(){D();var J=jasmine.createSpy(),I=Ext.Function.createBuffered(J,2);I();expect(F.shift()).toBe(2);I();expect(C.shift()).toBe(B.shift());expect(F.shift()).toBe(2);expect(J).not.toHaveBeenCalled();H(J,function(){expect(J.calls.length).toBe(1)});A()});it("should use the specified scope as 'this'",function(){var K={x:1},J=jasmine.createSpy().andCallFake(function(){this.x++}),I=Ext.Function.createBuffered(J,20,K);I();expect(K.x).toBe(1);I();H(J,function(){expect(K.x).toBe(2)})});it("should override the call arguments with the specified ones",function(){var K={},L=["bar1","bar2"],J=jasmine.createSpy(),I=Ext.Function.createBuffered(J,20,K,L);I("foo1","foo2");expect(J).not.toHaveBeenCalled();H(J,function(){expect(J).toHaveBeenCalledWith("bar1","bar2")})})});(Ext.isIE8?xdescribe:xdescribe)("createThrottled",function(){it("should execute only once per each specified time interval",function(){D();var J=jasmine.createSpy(),I=Ext.Function.createThrottled(J,10);expect(J).not.toHaveBeenCalled();I();expect(C.shift()).toBeUndefined();expect(J.calls.length).toBe(1);I();expect(F.shift()).not.toBeGreaterThan(10);expect(C.shift()).toBeUndefined();I();expect(F.shift()).not.toBeGreaterThan(10);expect(C.shift()).toBe(B.shift());I();expect(F.shift()).not.toBeGreaterThan(10);expect(C.shift()).toBe(B.shift());expect(J.calls.length).toBe(1);H(J,function(){expect(J.calls.length).toEqual(2);I();expect(J.calls.length).not.toBeLessThan(2);expect(J.calls.length).not.toBeGreaterThan(3)},2);A()});it("should use the specified scope as 'this'",function(){var K={},J=jasmine.createSpy().andCallFake(function(L){this.x=L}),I=Ext.Function.createThrottled(J,10,K);I("foo");I("bar");I("baz");I("qux");expect(J).toHaveBeenCalledWith("foo");expect(K.x).toBe("foo");expect(J.calls.length).toBe(1)})});describe("interceptAfter",function(){it("should execute interceptor after each method call",function(){var I={phrases:[],addPhrase:function(K){this.phrases.push(K)}},J=jasmine.createSpy().andCallFake(function(K){this.phrases.push(K+" too")});Ext.Function.interceptAfter(I,"addPhrase",J);I.addPhrase("I like you");I.addPhrase("I love you");expect(I.phrases).toEqual(["I like you","I like you too","I love you","I love you too"]);expect(J).toHaveBeenCalledWith("I like you");expect(J).toHaveBeenCalledWith("I love you")});it("should execute interceptor after each method call with the specified scope as 'this'",function(){var K={phrases:[],addPhrase:function(L){this.phrases.push(L)}},I={phrases:[]},J=jasmine.createSpy().andCallFake(function(L){this.phrases.push("He said: "+L)});Ext.Function.interceptAfter(K,"addPhrase",J,I);K.addPhrase("I like you");K.addPhrase("I love you");expect(K.phrases).toEqual(["I like you","I love you"]);expect(I.phrases).toEqual(["He said: I like you","He said: I love you"]);expect(J).toHaveBeenCalledWith("I like you");expect(J).toHaveBeenCalledWith("I love you")})})});
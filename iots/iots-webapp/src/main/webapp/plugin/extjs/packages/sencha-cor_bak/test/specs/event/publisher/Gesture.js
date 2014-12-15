describe("Ext.event.publisher.Gesture",function(){describe("removing the target el before a gesture is complete",function(){var C=Ext.dom.GarbageCollector,E=Ext.testHelper,B=C.interval,D;beforeEach(function(){D=Ext.getBody().createChild({id:"gesture-target"});spyOn(D,"clearListeners");C.interval=60;C.pause();C.resume()});afterEach(function(){D.destroy();C.interval=B;C.pause();C.resume()});function F(){document.body.removeChild(D.dom)}function A(G){if(G){expect("gesture-target" in Ext.cache).toBe(false);expect(D.clearListeners).toHaveBeenCalled()}else{expect("gesture-target" in Ext.cache).toBe(true);expect(D.clearListeners).not.toHaveBeenCalled()}}it("should not garbage collect the target element until the current gesture is complete",function(){runs(function(){E.touchStart(D,{id:1,x:10,y:10});E.touchMove(D,{id:1,x:15,y:15});F()});waits(90);runs(function(){A(false);E.touchEnd(Ext.supports.TouchEvents?D:document.body,{id:1,x:15,y:15})});waits(90);runs(function(){A(true)})})})});
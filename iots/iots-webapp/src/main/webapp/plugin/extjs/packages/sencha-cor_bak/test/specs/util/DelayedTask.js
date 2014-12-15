describe("Ext.util.DelayedTask",function(){it("should delay the call",function(){var A,B=false;A=new Ext.util.DelayedTask(function(){B=true});A.delay(100);expect(B).toBe(false);waits(150);runs(function(){expect(B).toBe(true)})});it("should cancel any previous invocations",function(){var B,A=0,D=0,C=function(E){A++;D=E};B=new Ext.util.DelayedTask(C);B.delay(500,null,null,[1]);B.delay(1500,null,null,[2]);B.delay(2500,null,null,[3]);waits(1000);runs(function(){expect(A).toBe(0);expect(D).toBe(0);waits(1000);runs(function(){expect(A).toBe(0);expect(D).toBe(0);waits(1000);runs(function(){expect(A).toBe(1);expect(D).toBe(3)})})})});it("should not cancel any previous invocations",function(){var B,A=0,D,C=function(E){A++;D=E};B=new Ext.util.DelayedTask(C,null,null,false);B.delay(100,null,null,[1]);B.delay(150,null,null,[2]);B.delay(300,null,null,[3]);waits(150);runs(function(){expect(A).toBe(1);expect(D).toBe(3);waits(100);runs(function(){expect(A).toBe(1);expect(D).toBe(3);waits(100);runs(function(){expect(A).toBe(1);expect(D).toBe(3)})})})});it("should accept a delay of 0",function(){var B=true;var A=new Ext.util.DelayedTask(function(){B=true});A.delay(1000);A.delay(0);waits(50);runs(function(){expect(B).toBe(true)})})});
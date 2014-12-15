describe("Ext.Number",function(){var A=Ext.Number;describe("constraining a number",function(){describe("integers",function(){describe("if the number is within the constaints",function(){it("should leave the number alone if it is equal to the min and the max",function(){expect(A.constrain(1,1,1)).toEqual(1)});it("should leave the number alone if it is equal to the min",function(){expect(A.constrain(1,1,5)).toEqual(1)});it("should leave the number alone if it is equal to the max",function(){expect(A.constrain(5,1,5)).toEqual(5)});it("should leave the number alone if it is within the min and the max",function(){expect(A.constrain(3,1,5)).toEqual(3)});it("should leave a negative number alone if it is within the min and the max",function(){expect(A.constrain(-3,-5,-1)).toEqual(-3)})});describe("if the number is not within the constraints",function(){it("should make the number equal to the min value",function(){expect(A.constrain(1,3,5)).toEqual(3)});it("should make the number equal to the max value",function(){expect(A.constrain(100,1,5)).toEqual(5)});describe("and the number is negative",function(){it("should make the number equal to the min value",function(){expect(A.constrain(-10,-50,-30)).toEqual(-30)});it("should make the number equal to the max value",function(){expect(A.constrain(-100,-50,-30)).toEqual(-50)})})});describe("constrain NaN",function(){it("should never constrain a NaN between two numbers",function(){expect(isNaN(A.constrain(NaN,3,5))).toEqual(true)});it("should never constrain a NaN between zero and undefined",function(){expect(isNaN(A.constrain(NaN,0,undefined))).toEqual(true)});it("should never constrain a NaN between undefined and zero",function(){expect(isNaN(A.constrain(NaN,undefined,0))).toEqual(true)});it("should never constrain a NaN between a number and undefined",function(){expect(isNaN(A.constrain(NaN,10,undefined))).toEqual(true)});it("should never constrain a NaN between undefined and a number",function(){expect(isNaN(A.constrain(NaN,undefined,10))).toEqual(true)});it("should never constrain a NaN between two undefined values",function(){expect(isNaN(A.constrain(NaN,undefined,undefined))).toEqual(true)})});describe("constrain with NaN/undefined max",function(){it("should ignore NaN max",function(){expect(A.constrain(2,1,NaN)).toEqual(2)});it("should ignore NaN max and limit to min",function(){expect(A.constrain(2,5,NaN)).toEqual(5)});it("should ignore undefined max",function(){expect(A.constrain(2,1,undefined)).toEqual(2)});it("should ignore undefined max and limit to min",function(){expect(A.constrain(2,5,undefined)).toEqual(5)})});describe("constrain with NaN/undefined min",function(){it("should ignore NaN min",function(){expect(A.constrain(2,NaN,5)).toEqual(2)});it("should ignore NaN min and limit to max",function(){expect(A.constrain(20,NaN,5)).toEqual(5)});it("should ignore undefined min",function(){expect(A.constrain(2,undefined,5)).toEqual(2)});it("should ignore undefined min and limit to max",function(){expect(A.constrain(20,undefined,5)).toEqual(5)})});describe("constrain with NaN/undefined min/max",function(){it("should ignore NaN min/max",function(){expect(A.constrain(2,NaN,NaN)).toEqual(2)});it("should ignore undefined min/max",function(){expect(A.constrain(2,undefined,undefined)).toEqual(2)});it("should ignore NaN min and undefined max",function(){expect(A.constrain(2,NaN,undefined)).toEqual(2)});it("should ignore undefined min and NaN max",function(){expect(A.constrain(2,undefined,NaN)).toEqual(2)})})});describe("floating point numbers",function(){describe("if the number is within the constaints",function(){it("should leave the number alone",function(){expect(A.constrain(3.3,3.1,3.5)).toEqual(3.3)});it("should leave a negative number alone",function(){expect(A.constrain(-3.3,-3.5,-3.1)).toEqual(-3.3)})});describe("and the number is negative",function(){it("should make the number equal to the min value",function(){expect(A.constrain(-3.3,-3.1,-3)).toEqual(-3.1)});it("should make the number equal to the max value",function(){expect(A.constrain(-2.1,-3.1,-3)).toEqual(-3)})})})});describe("toFixed",function(){var B=A.toFixed;it("should return a string",function(){expect(typeof B(1)).toEqual("string")});it("should default precision to 0",function(){expect(B(1.23456)).toEqual("1")});it("should output the correct number of decimal places",function(){expect(B(1,3)).toEqual("1.000")});it("should round correctly",function(){expect(B(1.9834657,1)).toEqual("2.0")});it("should round with negative numbers",function(){expect(B(-3.4265,2)).toEqual("-3.43")})});describe("snap",function(){var B=A.snap;it("should enforce minValue if increment is zero",function(){expect(B(40,0,50,100)).toEqual(50)});it("should enforce maxValue if increment is zero",function(){expect(B(5000,0,0,100)).toEqual(100)});it("should enforce minValue if passed",function(){expect(B(0,2,1,100)).toEqual(1)});it("should not enforce a minimum if no minValue passed",function(){expect(B(21,2,undefined,100)).toEqual(22)});it("should enforce maxValue if passed",function(){expect(B(1000,2,undefined,100)).toEqual(100)});it("should not enforce a maximum if no maxValue passed",function(){expect(B(21,2,undefined,undefined)).toEqual(22)});it("should snap to a snap point based upon zero",function(){expect(B(56,2,55,65)).toEqual(56);expect(B(100,2,55,66)).toEqual(66)});it("should enforce the minValue",function(){expect(B(20,2,55,65)).toEqual(55)});it("should round to the nearest snap point",function(){expect(B(4,5,0,100)).toEqual(5)});it("should snap negative numbers",function(){expect(B(-9,10,-100,0)).toBe(-10);expect(B(-1,10,-100,0)).toBe(0)})});describe("snapInRange",function(){var B=A.snapInRange;it("should enforce minValue if increment is zero",function(){expect(B(50,0,0,100)).toEqual(50)});it("should enforce maxValue if increment is zero",function(){expect(B(5000,0,0,100)).toEqual(100)});it("should enforce minValue if passed",function(){expect(B(0,2,1,100)).toEqual(1)});it("should not enforce a minimum if no minValue passed",function(){expect(B(21,2,undefined,100)).toEqual(22)});it("should enforce maxValue if passed",function(){expect(B(1000,2,undefined,100)).toEqual(100)});it("should not enforce a maximum if no maxValue passed",function(){expect(B(21,2,undefined,undefined)).toEqual(22)});it("should snap to a snap point based upon the minValue",function(){expect(B(56,2,55,65)).toEqual(57)});it("should enforce the minValue",function(){expect(B(20,2,55,65)).toEqual(55)});it("should snap to a snap point based upon the minValue even if maxValue is not on a snap point",function(){expect(B(100,2,55,66)).toEqual(67)});it("should round to the nearest snap point",function(){expect(B(4,5,0,100)).toEqual(5);expect(B(10,10,1,101)).toBe(11);expect(B(11,10,1,101)).toBe(11);expect(B(12,10,1,101)).toBe(11);expect(B(20,10,1,101)).toBe(21);expect(B(21,10,1,101)).toBe(21);expect(B(22,10,1,101)).toBe(21)});it("should handle negative ranges",function(){expect(B(-10,10,-101,-1)).toBe(-11);expect(B(-11,10,-101,-1)).toBe(-11);expect(B(-12,10,-101,-1)).toBe(-11);expect(B(-20,10,-101,-1)).toBe(-21);expect(B(-21,10,-101,-1)).toBe(-21);expect(B(-22,10,-101,-1)).toBe(-21)})});describe("from",function(){var B=A.from;it("should handle numbers",function(){expect(B(2,1)).toBe(2);expect(B(-2,1)).toBe(-2);expect(B(999999,1)).toBe(999999);expect(B(-999999,1)).toBe(-999999);expect(B(999999.999,1)).toBe(999999.999);expect(B(-999999.999,1)).toBe(-999999.999)});it("should handle strings that represent numbers",function(){expect(B("2",1)).toBe(2);expect(B("-2",1)).toBe(-2);expect(B("999999",1)).toBe(999999);expect(B("-999999",1)).toBe(-999999);expect(B("999999.999",1)).toBe(999999.999);expect(B("-999999.999",1)).toBe(-999999.999)});it("should handle infinity",function(){expect(B(1/0,1)).toBe(window.Number.POSITIVE_INFINITY);expect(B(-1/0,1)).toBe(window.Number.NEGATIVE_INFINITY)});it("should return default value if value is not a number or numeric string",function(){expect(B("",100)).toBe(100);expect(B(true,100)).toBe(100);expect(B(false,100)).toBe(100);expect(B("I would like to be a number",100)).toBe(100);expect(B("12345ImAlmostANumber",100)).toBe(100)})});describe("randomInt",function(){var B=A.randomInt;it("should return a random integer within the specified range",function(){expect(B(0,100)).not.toBeLessThan(0);expect(B(0,100)).not.toBeGreaterThan(100);expect(B(-100,0)).not.toBeLessThan(-100);expect(B(-100,0)).not.toBeGreaterThan(0);expect(B(1,1)).toBe(1);expect(B(1,1)).toBe(1)})});describe("correctFloat",function(){var B=A.correctFloat;it("should correct a small positive overflow",function(){expect(B(0.1+0.2)).toBe(0.3)});it("should correct a small negative overflow",function(){expect(B(-0.1+-0.2)).toBe(-0.3)});it("should correct large overflows",function(){expect(B(10000000.123)).toBe(10000000.123)})});describe("clipIndices",function(){function C(D){if(D===undefined){return undefined}return Ext.encode(D)}var B=[{length:0,indices:[0],options:null,expect:[0,0]},{length:0,indices:[3],options:null,expect:[0,0]},{length:0,indices:[-1],options:null,expect:[0,0]},{length:0,indices:[-5],options:null,expect:[0,0]},{length:0,indices:[],options:null,expect:[0,0]},{length:0,indices:null,options:null,expect:[0,0]},{length:0,options:null,expect:[0,0]},{length:8,indices:[],options:null,expect:[0,8]},{length:8,indices:null,options:null,expect:[0,8]},{length:8,options:null,expect:[0,8]},{length:8,indices:[0],options:null,expect:[0,8]},{length:8,indices:[3],options:null,expect:[3,8]},{length:8,indices:[8],options:null,expect:[8,8]},{length:8,indices:[9],options:null,expect:[8,8]},{length:8,indices:[-1],options:null,expect:[7,8]},{length:8,indices:[-3],options:null,expect:[5,8]},{length:8,indices:[-7],options:null,expect:[1,8]},{length:8,indices:[-8],options:null,expect:[0,8]},{length:8,indices:[-9],options:null,expect:[0,8]},{length:8,indices:[-10],options:null,expect:[0,8]},{length:8,indices:[0,0],options:null,expect:[0,0]},{length:8,indices:[0,3],options:null,expect:[0,3]},{length:8,indices:[0,8],options:null,expect:[0,8]},{length:8,indices:[0,9],options:null,expect:[0,8]},{length:8,indices:[0,-1],options:null,expect:[0,7]},{length:8,indices:[0,-3],options:null,expect:[0,5]},{length:8,indices:[0,-7],options:null,expect:[0,1]},{length:8,indices:[0,-8],options:null,expect:[0,0]},{length:8,indices:[0,-9],options:null,expect:[0,0]},{length:8,indices:[0,-10],options:null,expect:[0,0]},{length:8,indices:[1,0],options:null,expect:[1,1]},{length:8,indices:[1,3],options:null,expect:[1,3]},{length:8,indices:[1,8],options:null,expect:[1,8]},{length:8,indices:[1,9],options:null,expect:[1,8]},{length:8,indices:[1,-1],options:null,expect:[1,7]},{length:8,indices:[1,-3],options:null,expect:[1,5]},{length:8,indices:[1,-7],options:null,expect:[1,1]},{length:8,indices:[1,-8],options:null,expect:[1,1]},{length:8,indices:[1,-9],options:null,expect:[1,1]},{length:8,indices:[1,-10],options:null,expect:[1,1]},{length:8,indices:[3,0],options:null,expect:[3,3]},{length:8,indices:[3,3],options:null,expect:[3,3]},{length:8,indices:[3,8],options:null,expect:[3,8]},{length:8,indices:[3,9],options:null,expect:[3,8]},{length:8,indices:[3,-1],options:null,expect:[3,7]},{length:8,indices:[3,-3],options:null,expect:[3,5]},{length:8,indices:[3,-7],options:null,expect:[3,3]},{length:8,indices:[3,-8],options:null,expect:[3,3]},{length:8,indices:[3,-9],options:null,expect:[3,3]},{length:8,indices:[3,-10],options:null,expect:[3,3]},{length:8,indices:[7,0],options:null,expect:[7,7]},{length:8,indices:[7,3],options:null,expect:[7,7]},{length:8,indices:[7,8],options:null,expect:[7,8]},{length:8,indices:[7,9],options:null,expect:[7,8]},{length:8,indices:[7,-1],options:null,expect:[7,7]},{length:8,indices:[7,-3],options:null,expect:[7,7]},{length:8,indices:[7,-7],options:null,expect:[7,7]},{length:8,indices:[7,-8],options:null,expect:[7,7]},{length:8,indices:[7,-9],options:null,expect:[7,7]},{length:8,indices:[7,-10],options:null,expect:[7,7]},{length:8,indices:[-1,0],options:null,expect:[7,7]},{length:8,indices:[-1,3],options:null,expect:[7,7]},{length:8,indices:[-1,8],options:null,expect:[7,8]},{length:8,indices:[-1,9],options:null,expect:[7,8]},{length:8,indices:[-1,-1],options:null,expect:[7,7]},{length:8,indices:[-1,-3],options:null,expect:[7,7]},{length:8,indices:[-1,-7],options:null,expect:[7,7]},{length:8,indices:[-1,-8],options:null,expect:[7,7]},{length:8,indices:[-1,-9],options:null,expect:[7,7]},{length:8,indices:[-1,-10],options:null,expect:[7,7]},{length:8,indices:[-5,0],options:null,expect:[3,3]},{length:8,indices:[-5,3],options:null,expect:[3,3]},{length:8,indices:[-5,8],options:null,expect:[3,8]},{length:8,indices:[-5,9],options:null,expect:[3,8]},{length:8,indices:[-5,-1],options:null,expect:[3,7]},{length:8,indices:[-5,-3],options:null,expect:[3,5]},{length:8,indices:[-5,-7],options:null,expect:[3,3]},{length:8,indices:[-5,-8],options:null,expect:[3,3]},{length:8,indices:[-5,-9],options:null,expect:[3,3]},{length:8,indices:[-5,-10],options:null,expect:[3,3]},{length:8,indices:[-7,0],options:null,expect:[1,1]},{length:8,indices:[-7,3],options:null,expect:[1,3]},{length:8,indices:[-7,8],options:null,expect:[1,8]},{length:8,indices:[-7,9],options:null,expect:[1,8]},{length:8,indices:[-7,-1],options:null,expect:[1,7]},{length:8,indices:[-7,-3],options:null,expect:[1,5]},{length:8,indices:[-7,-7],options:null,expect:[1,1]},{length:8,indices:[-7,-8],options:null,expect:[1,1]},{length:8,indices:[-7,-9],options:null,expect:[1,1]},{length:8,indices:[-7,-10],options:null,expect:[1,1]},{length:8,indices:[-8,0],options:null,expect:[0,0]},{length:8,indices:[-8,3],options:null,expect:[0,3]},{length:8,indices:[-8,8],options:null,expect:[0,8]},{length:8,indices:[-8,9],options:null,expect:[0,8]},{length:8,indices:[-8,-1],options:null,expect:[0,7]},{length:8,indices:[-8,-3],options:null,expect:[0,5]},{length:8,indices:[-8,-7],options:null,expect:[0,1]},{length:8,indices:[-8,-8],options:null,expect:[0,0]},{length:8,indices:[-8,-9],options:null,expect:[0,0]},{length:8,indices:[-8,-10],options:null,expect:[0,0]},{length:8,indices:[0,0],options:"COUNT",expect:[0,0]},{length:8,indices:[0,3],options:"COUNT",expect:[0,3]},{length:8,indices:[0,8],options:"COUNT",expect:[0,8]},{length:8,indices:[0,9],options:"COUNT",expect:[0,8]},{length:8,indices:[0,-1],options:"COUNT",expect:[0,0]},{length:8,indices:[0,-3],options:"COUNT",expect:[0,0]},{length:8,indices:[1,0],options:"COUNT",expect:[1,1]},{length:8,indices:[1,3],options:"COUNT",expect:[1,4]},{length:8,indices:[1,6],options:"COUNT",expect:[1,7]},{length:8,indices:[1,8],options:"COUNT",expect:[1,8]},{length:8,indices:[1,9],options:"COUNT",expect:[1,8]},{length:8,indices:[1,-1],options:"COUNT",expect:[1,1]},{length:8,indices:[3,0],options:"COUNT",expect:[3,3]},{length:8,indices:[3,3],options:"COUNT",expect:[3,6]},{length:8,indices:[3,4],options:"COUNT",expect:[3,7]},{length:8,indices:[3,8],options:"COUNT",expect:[3,8]},{length:8,indices:[3,9],options:"COUNT",expect:[3,8]},{length:8,indices:[3,-1],options:"COUNT",expect:[3,3]},{length:8,indices:[7,0],options:"COUNT",expect:[7,7]},{length:8,indices:[7,3],options:"COUNT",expect:[7,8]},{length:8,indices:[7,8],options:"COUNT",expect:[7,8]},{length:8,indices:[7,9],options:"COUNT",expect:[7,8]},{length:8,indices:[7,-1],options:"COUNT",expect:[7,7]},{length:8,indices:[-1,0],options:"COUNT",expect:[7,7]},{length:8,indices:[-1,3],options:"COUNT",expect:[7,8]},{length:8,indices:[-1,8],options:"COUNT",expect:[7,8]},{length:8,indices:[-1,9],options:"COUNT",expect:[7,8]},{length:8,indices:[-1,-1],options:"COUNT",expect:[7,7]},{length:8,indices:[-5,0],options:"COUNT",expect:[3,3]},{length:8,indices:[-5,3],options:"COUNT",expect:[3,6]},{length:8,indices:[-5,4],options:"COUNT",expect:[3,7]},{length:8,indices:[-5,8],options:"COUNT",expect:[3,8]},{length:8,indices:[-5,9],options:"COUNT",expect:[3,8]},{length:8,indices:[-5,-1],options:"COUNT",expect:[3,3]},{length:8,indices:[-7,0],options:"COUNT",expect:[1,1]},{length:8,indices:[-7,3],options:"COUNT",expect:[1,4]},{length:8,indices:[-7,6],options:"COUNT",expect:[1,7]},{length:8,indices:[-7,7],options:"COUNT",expect:[1,8]},{length:8,indices:[-7,8],options:"COUNT",expect:[1,8]},{length:8,indices:[-7,9],options:"COUNT",expect:[1,8]},{length:8,indices:[-7,-1],options:"COUNT",expect:[1,1]},{length:8,indices:[-8,0],options:"COUNT",expect:[0,0]},{length:8,indices:[-8,3],options:"COUNT",expect:[0,3]},{length:8,indices:[-8,7],options:"COUNT",expect:[0,7]},{length:8,indices:[-8,8],options:"COUNT",expect:[0,8]},{length:8,indices:[-8,9],options:"COUNT",expect:[0,8]},{length:8,indices:[-8,-1],options:"COUNT",expect:[0,0]},{length:8,indices:[0,0],options:"INCLUSIVE",expect:[0,1]},{length:8,indices:[0,3],options:"INCLUSIVE",expect:[0,4]},{length:8,indices:[0,6],options:"INCLUSIVE",expect:[0,7]},{length:8,indices:[0,7],options:"INCLUSIVE",expect:[0,8]},{length:8,indices:[0,8],options:"INCLUSIVE",expect:[0,8]},{length:8,indices:[0,-1],options:"INCLUSIVE",expect:[0,8]},{length:8,indices:[0,-3],options:"INCLUSIVE",expect:[0,6]},{length:8,indices:[0,-7],options:"INCLUSIVE",expect:[0,2]},{length:8,indices:[0,-8],options:"INCLUSIVE",expect:[0,1]},{length:8,indices:[0,-9],options:"INCLUSIVE",expect:[0,0]},{length:8,indices:[0,-10],options:"INCLUSIVE",expect:[0,0]},{length:8,indices:[1,0],options:"INCLUSIVE",expect:[1,1]},{length:8,indices:[1,3],options:"INCLUSIVE",expect:[1,4]},{length:8,indices:[1,8],options:"INCLUSIVE",expect:[1,8]},{length:8,indices:[1,9],options:"INCLUSIVE",expect:[1,8]},{length:8,indices:[1,-1],options:"INCLUSIVE",expect:[1,8]},{length:8,indices:[1,-3],options:"INCLUSIVE",expect:[1,6]},{length:8,indices:[1,-7],options:"INCLUSIVE",expect:[1,2]},{length:8,indices:[1,-8],options:"INCLUSIVE",expect:[1,1]},{length:8,indices:[1,-9],options:"INCLUSIVE",expect:[1,1]},{length:8,indices:[1,-10],options:"INCLUSIVE",expect:[1,1]},{length:8,indices:[3,0],options:"INCLUSIVE",expect:[3,3]},{length:8,indices:[3,3],options:"INCLUSIVE",expect:[3,4]},{length:8,indices:[3,6],options:"INCLUSIVE",expect:[3,7]},{length:8,indices:[3,7],options:"INCLUSIVE",expect:[3,8]},{length:8,indices:[3,8],options:"INCLUSIVE",expect:[3,8]},{length:8,indices:[3,-1],options:"INCLUSIVE",expect:[3,8]},{length:8,indices:[3,-3],options:"INCLUSIVE",expect:[3,6]},{length:8,indices:[3,-7],options:"INCLUSIVE",expect:[3,3]},{length:8,indices:[3,-8],options:"INCLUSIVE",expect:[3,3]},{length:8,indices:[3,-9],options:"INCLUSIVE",expect:[3,3]},{length:8,indices:[3,-10],options:"INCLUSIVE",expect:[3,3]},{length:8,indices:[7,0],options:"INCLUSIVE",expect:[7,7]},{length:8,indices:[7,3],options:"INCLUSIVE",expect:[7,7]},{length:8,indices:[7,8],options:"INCLUSIVE",expect:[7,8]},{length:8,indices:[7,9],options:"INCLUSIVE",expect:[7,8]},{length:8,indices:[7,-1],options:"INCLUSIVE",expect:[7,8]},{length:8,indices:[7,-3],options:"INCLUSIVE",expect:[7,7]},{length:8,indices:[7,-7],options:"INCLUSIVE",expect:[7,7]},{length:8,indices:[7,-8],options:"INCLUSIVE",expect:[7,7]},{length:8,indices:[7,-9],options:"INCLUSIVE",expect:[7,7]},{length:8,indices:[7,-10],options:"INCLUSIVE",expect:[7,7]},{length:8,indices:[-1,0],options:"INCLUSIVE",expect:[7,7]},{length:8,indices:[-1,3],options:"INCLUSIVE",expect:[7,7]},{length:8,indices:[-1,8],options:"INCLUSIVE",expect:[7,8]},{length:8,indices:[-1,9],options:"INCLUSIVE",expect:[7,8]},{length:8,indices:[-1,-1],options:"INCLUSIVE",expect:[7,8]},{length:8,indices:[-1,-3],options:"INCLUSIVE",expect:[7,7]},{length:8,indices:[-1,-7],options:"INCLUSIVE",expect:[7,7]},{length:8,indices:[-1,-8],options:"INCLUSIVE",expect:[7,7]},{length:8,indices:[-1,-9],options:"INCLUSIVE",expect:[7,7]},{length:8,indices:[-1,-10],options:"INCLUSIVE",expect:[7,7]},{length:8,indices:[-5,0],options:"INCLUSIVE",expect:[3,3]},{length:8,indices:[-5,3],options:"INCLUSIVE",expect:[3,4]},{length:8,indices:[-5,8],options:"INCLUSIVE",expect:[3,8]},{length:8,indices:[-5,9],options:"INCLUSIVE",expect:[3,8]},{length:8,indices:[-5,-1],options:"INCLUSIVE",expect:[3,8]},{length:8,indices:[-5,-3],options:"INCLUSIVE",expect:[3,6]},{length:8,indices:[-5,-7],options:"INCLUSIVE",expect:[3,3]},{length:8,indices:[-5,-8],options:"INCLUSIVE",expect:[3,3]},{length:8,indices:[-5,-9],options:"INCLUSIVE",expect:[3,3]},{length:8,indices:[-5,-10],options:"INCLUSIVE",expect:[3,3]},{length:8,indices:[-7,0],options:"INCLUSIVE",expect:[1,1]},{length:8,indices:[-7,3],options:"INCLUSIVE",expect:[1,4]},{length:8,indices:[-7,8],options:"INCLUSIVE",expect:[1,8]},{length:8,indices:[-7,9],options:"INCLUSIVE",expect:[1,8]},{length:8,indices:[-7,-1],options:"INCLUSIVE",expect:[1,8]},{length:8,indices:[-7,-3],options:"INCLUSIVE",expect:[1,6]},{length:8,indices:[-7,-7],options:"INCLUSIVE",expect:[1,2]},{length:8,indices:[-7,-8],options:"INCLUSIVE",expect:[1,1]},{length:8,indices:[-7,-9],options:"INCLUSIVE",expect:[1,1]},{length:8,indices:[-7,-10],options:"INCLUSIVE",expect:[1,1]},{length:8,indices:[-8,0],options:"INCLUSIVE",expect:[0,1]},{length:8,indices:[-8,3],options:"INCLUSIVE",expect:[0,4]},{length:8,indices:[-8,8],options:"INCLUSIVE",expect:[0,8]},{length:8,indices:[-8,9],options:"INCLUSIVE",expect:[0,8]},{length:8,indices:[-8,-1],options:"INCLUSIVE",expect:[0,8]},{length:8,indices:[-8,-3],options:"INCLUSIVE",expect:[0,6]},{length:8,indices:[-8,-7],options:"INCLUSIVE",expect:[0,2]},{length:8,indices:[-8,-8],options:"INCLUSIVE",expect:[0,1]},{length:8,indices:[-8,-9],options:"INCLUSIVE",expect:[0,0]},{length:8,indices:[-8,-10],options:"INCLUSIVE",expect:[0,0]},{length:8,indices:[0],options:"NOWRAP",expect:[0,8]},{length:8,indices:[3],options:"NOWRAP",expect:[3,8]},{length:8,indices:[8],options:"NOWRAP",expect:[8,8]},{length:8,indices:[9],options:"NOWRAP",expect:[8,8]},{length:8,indices:[-1],options:"NOWRAP",expect:[0,8]},{length:8,indices:[-9],options:"NOWRAP",expect:[0,8]},{length:8,indices:[0,0],options:"NOWRAP",expect:[0,0]},{length:8,indices:[0,3],options:"NOWRAP",expect:[0,3]},{length:8,indices:[0,8],options:"NOWRAP",expect:[0,8]},{length:8,indices:[0,9],options:"NOWRAP",expect:[0,8]},{length:8,indices:[0,-1],options:"NOWRAP",expect:[0,0]},{length:8,indices:[0,-10],options:"NOWRAP",expect:[0,0]},{length:8,indices:[1,0],options:"NOWRAP",expect:[1,1]},{length:8,indices:[1,3],options:"NOWRAP",expect:[1,3]},{length:8,indices:[1,8],options:"NOWRAP",expect:[1,8]},{length:8,indices:[1,9],options:"NOWRAP",expect:[1,8]},{length:8,indices:[1,-1],options:"NOWRAP",expect:[1,1]},{length:8,indices:[1,-10],options:"NOWRAP",expect:[1,1]},{length:8,indices:[3,0],options:"NOWRAP",expect:[3,3]},{length:8,indices:[3,3],options:"NOWRAP",expect:[3,3]},{length:8,indices:[3,8],options:"NOWRAP",expect:[3,8]},{length:8,indices:[3,9],options:"NOWRAP",expect:[3,8]},{length:8,indices:[3,-1],options:"NOWRAP",expect:[3,3]},{length:8,indices:[3,-10],options:"NOWRAP",expect:[3,3]},{length:8,indices:[7,0],options:"NOWRAP",expect:[7,7]},{length:8,indices:[7,3],options:"NOWRAP",expect:[7,7]},{length:8,indices:[7,8],options:"NOWRAP",expect:[7,8]},{length:8,indices:[7,9],options:"NOWRAP",expect:[7,8]},{length:8,indices:[7,-1],options:"NOWRAP",expect:[7,7]},{length:8,indices:[7,-10],options:"NOWRAP",expect:[7,7]},{length:8,indices:[-1,0],options:"NOWRAP",expect:[0,0]},{length:8,indices:[-1,3],options:"NOWRAP",expect:[0,3]},{length:8,indices:[-1,8],options:"NOWRAP",expect:[0,8]},{length:8,indices:[-1,9],options:"NOWRAP",expect:[0,8]},{length:8,indices:[-1,-1],options:"NOWRAP",expect:[0,0]},{length:8,indices:[-1,-10],options:"NOWRAP",expect:[0,0]}];Ext.each(B,function(D){it("should produce "+Ext.encode(D.expect)+" given length="+D.length+" and indices="+C(D.indices)+(D.options?" options: "+D.options:""),function(){var E=D.options&&Ext.Number.Clip[D.options];var F=Ext.Number.clipIndices(D.length,D.indices,E);expect(F).toEqual(D.expect)})})})});
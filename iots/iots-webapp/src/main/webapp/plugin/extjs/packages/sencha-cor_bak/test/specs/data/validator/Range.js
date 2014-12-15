describe("Ext.data.validator.Range",function(){var B;function A(E,D,F){B=new Ext.data.validator.Range({min:D,max:F});return B.validate(E)}function C(){return Ext.String.format.apply(Ext.String,arguments)}afterEach(function(){B=null});describe("invalid values",function(){it("should not validate undefined",function(){expect(A(undefined)).toBe(B.getEmptyMessage())});it("should not validate null",function(){expect(A(null)).toBe(B.getEmptyMessage())});describe("min only",function(){describe("string",function(){it("should not validate if the value is less than the minimum",function(){var D="B";expect(A("A",D)).toBe(C(B.getMinOnlyMessage(),D))})});describe("number",function(){it("should not validate if the value is less than the minimum",function(){var D=10;expect(A(9,D)).toBe(C(B.getMinOnlyMessage(),D))})})});describe("max only",function(){describe("string",function(){it("should not validate if the value is greater than the maximum",function(){var D="D";expect(A("E",undefined,D)).toBe(C(B.getMaxOnlyMessage(),D))})});describe("number",function(){it("should not validate if the value is greater than the maximum",function(){var D=10;expect(A(20,undefined,D)).toBe(C(B.getMaxOnlyMessage(),D))})})});describe("min & max",function(){describe("string",function(){var D="E",E="J";it("should not validate if the value is less than the minimum",function(){expect(A("A",D,E)).toBe(C(B.getBothMessage(),D,E))});it("should not validate if the value is greater than the maximum",function(){expect(A("Z",D,E)).toBe(C(B.getBothMessage(),D,E))})})})});describe("valid values",function(){describe("min only",function(){describe("string",function(){it("should validate if the value is equal to the minimum",function(){expect(A("F","F")).toBe(true)});it("should validate if the value is greater than the minimum",function(){expect(A("G","F")).toBe(true)})});describe("number",function(){it("should validate if the value is equal to the minimum",function(){expect(A(3,3)).toBe(true)});it("should validate if the value is greater than the minimum",function(){expect(A(7,3)).toBe(true)})})});describe("max only",function(){describe("string",function(){it("should validate if the value is equal to the maximum",function(){expect(A("F",undefined,"F")).toBe(true)});it("should validate if the value is less than the maximum",function(){expect(A("A",undefined,"F")).toBe(true)})});describe("number",function(){it("should validate if the value is equal to the maximum",function(){expect(A(18,undefined,18)).toBe(true)});it("should validate if the value is less than the maximum",function(){expect(A(18,undefined,22)).toBe(true)})})});describe("both",function(){describe("string",function(){it("should validate if the value is equal to the minimum",function(){expect(A("E","E","J")).toBe(true)});it("should validate if the value is equal to the maximum",function(){expect(A("J","E","J")).toBe(true)});it("should validate if the value is between minimum/maximum",function(){expect(A("G","E","J")).toBe(true)});it("should validate if the min === max and the value === min === max",function(){expect(A("G","G","G")).toBe(true)})});describe("number",function(){it("should validate if the value is equal to the minimum",function(){expect(A(30,30,50)).toBe(true)});it("should validate if the value is equal to the maximum",function(){expect(A(50,30,50)).toBe(true)});it("should validate if the value is between minimum/maximum",function(){expect(A(43,30,50)).toBe(true)});it("should validate if the min === max and the value === min === max",function(){expect(A(70,70,70)).toBe(true)})})})});describe("messages",function(){it("should accept a custom empty message",function(){B=new Ext.data.validator.Range({emptyMessage:"Foo"});expect(B.validate(undefined)).toBe("Foo")});it("should accept a custom min message",function(){B=new Ext.data.validator.Range({minOnlyMessage:"Foo{0}",min:1});expect(B.validate(0)).toBe("Foo1")});it("should accept a custom max message",function(){B=new Ext.data.validator.Range({maxOnlyMessage:"Foo{0}",max:3});expect(B.validate(10)).toBe("Foo3")});it("should accept a custom both message",function(){B=new Ext.data.validator.Range({bothMessage:"Foo{0}{1}",min:5,max:7});expect(B.validate(3)).toBe("Foo57")})});describe("runtime changes",function(){var D=function(E,F){B=new Ext.data.validator.Range({min:E,max:F})};describe("min value",function(){it("should be able to change the min value",function(){D(3);expect(B.validate(1)).not.toBe(true);B.setMin(1);expect(B.validate(1)).toBe(true)});it("should update the minMsg after changing the min value",function(){D(3);expect(B.validate(1)).toBe(C(B.getMinOnlyMessage(),3));B.setMin(2);expect(B.validate(1)).toBe(C(B.getMinOnlyMessage(),2))})});describe("max value",function(){it("should be able to change the max value",function(){D(undefined,3);expect(B.validate(4)).not.toBe(true);B.setMax(10);expect(B.validate(4)).toBe(true)});it("should update the maxMsg after changing the max value",function(){D(undefined,3);expect(B.validate(5)).toBe(C(B.getMaxOnlyMessage(),3));B.setMax(4);expect(B.validate(5)).toBe(C(B.getMaxOnlyMessage(),4))})});describe("both",function(){it("should be able to clear the minimum value",function(){D(3,5);expect(B.validate(2)).not.toBe(true);B.setMin(undefined);expect(B.validate(2)).toBe(true)});it("should be able to clear the maximum value",function(){D(3,5);expect(B.validate(7)).not.toBe(true);B.setMax(undefined);expect(B.validate(7)).toBe(true)});describe("messages",function(){it("should update the bothMsg when the min value changes",function(){D(3,5);expect(B.validate(7)).toBe(C(B.getBothMessage(),3,5));B.setMin(2);expect(B.validate(7)).toBe(C(B.getBothMessage(),2,5))});it("should update the bothMsg when the max value changes",function(){D(3,5);expect(B.validate(7)).toBe(C(B.getBothMessage(),3,5));B.setMax(6);expect(B.validate(7)).toBe(C(B.getBothMessage(),3,6))});it("should switch to the max msg when clearing the min value",function(){D(3,5);B.setMin(undefined);expect(B.validate(7)).toBe(C(B.getMaxOnlyMessage(),5))});it("should switch to the min msg when clearing the max value",function(){D(3,5);B.setMax(undefined);expect(B.validate(1)).toBe(C(B.getMinOnlyMessage(),3))})})})})});
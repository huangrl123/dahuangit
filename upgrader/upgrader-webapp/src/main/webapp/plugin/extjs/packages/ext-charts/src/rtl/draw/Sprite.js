Ext.define("Ext.rtl.draw.Sprite",{override:"Ext.draw.Sprite",RLM:"\u200F",rtlRe:/[\u0591-\u07FF\uFB1D-\uFDFD\uFE70-\uFEFC]/,transformText:function(B){var A=this;if(B&&A.surface.isRtl&&!Ext.isNumber(B)&&A.rtlRe.test(B)){return A.RLM+B}return A.callParent(arguments)}});
Ext.define("Ext.chart.TipSurface",{extend:"Ext.draw.Component",spriteArray:false,renderFirst:true,constructor:function(A){this.callParent([A]);if(A.sprites){this.spriteArray=[].concat(A.sprites);delete A.sprites}},onRender:function(){var E=this,A=0,D=0,C,B;this.callParent(arguments);B=E.spriteArray;if(E.renderFirst&&B){E.renderFirst=false;for(D=B.length;A<D;A++){C=E.surface.add(B[A]);C.setAttributes({hidden:false},true)}}}});
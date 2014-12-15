Ext.define("Ext.chart.theme.Base",{requires:["Ext.chart.theme.Theme"],constructor:function(A){var B=Ext.identityFn;Ext.chart.theme.call(this,A,{background:false,axis:{stroke:"#444","stroke-width":1},axisLabelTop:{fill:"#444",font:"12px Arial, Helvetica, sans-serif",spacing:2,padding:5,renderer:B},axisLabelRight:{fill:"#444",font:"12px Arial, Helvetica, sans-serif",spacing:2,padding:5,renderer:B},axisLabelBottom:{fill:"#444",font:"12px Arial, Helvetica, sans-serif",spacing:2,padding:5,renderer:B},axisLabelLeft:{fill:"#444",font:"12px Arial, Helvetica, sans-serif",spacing:2,padding:5,renderer:B},axisTitleTop:{font:"bold 18px Arial",fill:"#444"},axisTitleRight:{font:"bold 18px Arial",fill:"#444",rotate:{x:0,y:0,degrees:270}},axisTitleBottom:{font:"bold 18px Arial",fill:"#444"},axisTitleLeft:{font:"bold 18px Arial",fill:"#444",rotate:{x:0,y:0,degrees:270}},series:{"stroke-width":0},seriesLabel:{font:"12px Arial",fill:"#333"},marker:{stroke:"#555",radius:3,size:3},colors:["#94ae0a","#115fa6","#a61120","#ff8809","#ffd13e","#a61187","#24ad9a","#7c7474","#a66111"],seriesThemes:[{fill:"#94ae0a"},{fill:"#115fa6"},{fill:"#a61120"},{fill:"#ff8809"},{fill:"#ffd13e"},{fill:"#a61187"},{fill:"#24ad9a"},{fill:"#7c7474"},{fill:"#115fa6"},{fill:"#94ae0a"},{fill:"#a61120"},{fill:"#ff8809"},{fill:"#ffd13e"},{fill:"#a61187"},{fill:"#24ad9a"},{fill:"#7c7474"},{fill:"#a66111"}],markerThemes:[{fill:"#115fa6",type:"circle"},{fill:"#94ae0a",type:"cross"},{fill:"#115fa6",type:"plus"},{fill:"#94ae0a",type:"circle"},{fill:"#a61120",type:"cross"}]})}},function(){var C=["#b1da5a","#4ce0e7","#e84b67","#da5abd","#4d7fe6","#fec935"],B=["Green","Sky","Red","Purple","Blue","Yellow"],G=0,E=0,H=C.length,F=Ext.chart.theme,A=[["#f0a50a","#c20024","#2044ba","#810065","#7eae29"],["#6d9824","#87146e","#2a9196","#d39006","#1e40ac"],["#fbbc29","#ce2e4e","#7e0062","#158b90","#57880e"],["#ef5773","#fcbd2a","#4f770d","#1d3eaa","#9b001f"],["#7eae29","#fdbe2a","#910019","#27b4bc","#d74dbc"],["#44dce1","#0b2592","#996e05","#7fb325","#b821a1"]],D=A.length;for(;G<H;G++){F[B[G]]=(function(I){return Ext.extend(F.Base,{constructor:function(J){F.Base.prototype.constructor.call(this,Ext.apply({baseColor:I},J))}})}(C[G]))}for(G=0;G<D;G++){F["Category"+(G+1)]=(function(I){return Ext.extend(F.Base,{constructor:function(J){F.Base.prototype.constructor.call(this,Ext.apply({colors:I},J))}})}(A[G]))}});
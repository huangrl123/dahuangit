Ext.define("Ext.chart.Shape",{singleton:true,circle:function(B,A){return B.add(Ext.apply({type:"circle",x:A.x,y:A.y,stroke:null,radius:A.radius},A))},line:function(B,A){return B.add(Ext.apply({type:"rect",x:A.x-A.radius,y:A.y-A.radius,height:2*A.radius,width:2*A.radius/5},A))},square:function(B,A){return B.add(Ext.applyIf({type:"rect",x:A.x-A.radius,y:A.y-A.radius,height:2*A.radius,width:2*A.radius,radius:null},A))},triangle:function(B,A){A.radius*=1.75;return B.add(Ext.apply({type:"path",stroke:null,path:"M".concat(A.x,",",A.y,"m0-",A.radius*0.58,"l",A.radius*0.5,",",A.radius*0.87,"-",A.radius,",0z")},A))},diamond:function(C,B){var A=B.radius;A*=1.5;return C.add(Ext.apply({type:"path",stroke:null,path:["M",B.x,B.y-A,"l",A,A,-A,A,-A,-A,A,-A,"z"]},B))},cross:function(C,B){var A=B.radius;A=A/1.7;return C.add(Ext.apply({type:"path",stroke:null,path:"M".concat(B.x-A,",",B.y,"l",[-A,-A,A,-A,A,A,A,-A,A,A,-A,A,A,A,-A,A,-A,-A,-A,A,-A,-A,"z"])},B))},plus:function(C,B){var A=B.radius/1.3;return C.add(Ext.apply({type:"path",stroke:null,path:"M".concat(B.x-A/2,",",B.y-A/2,"l",[0,-A,A,0,0,A,A,0,0,A,-A,0,0,A,-A,0,0,-A,-A,0,0,-A,"z"])},B))},arrow:function(C,B){var A=B.radius;return C.add(Ext.apply({type:"path",path:"M".concat(B.x-A*0.7,",",B.y-A*0.4,"l",[A*0.6,0,0,-A*0.4,A,A*0.8,-A,A*0.8,0,-A*0.4,-A*0.6,0],"z")},B))},drop:function(F,D,E,C,A,B){A=A||30;B=B||0;F.add({type:"path",path:["M",D,E,"l",A,0,"A",A*0.4,A*0.4,0,1,0,D+A*0.7,E-A*0.7,"z"],fill:"#000",stroke:"none",rotate:{degrees:22.5-B,x:D,y:E}});B=(B+90)*Math.PI/180;F.add({type:"text",x:D+A*Math.sin(B)-10,y:E+A*Math.cos(B)+5,text:C,"font-size":A*12/40,stroke:"none",fill:"#fff"})}});
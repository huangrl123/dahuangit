Ext.define("Ext.chart.interactions.RotatePie3D",{extend:"Ext.chart.interactions.Rotate",type:"rotatePie3d",alias:"interaction.rotatePie3d",getAngle:function(B){var A=this.getChart(),D=A.getInherited().rtl,G=D?-1:1,C=B.getXY(),F=A.element.getXY(),E=A.getMainRect();return G*Math.atan2(C[1]-F[1]-E[3]*0.5,C[0]-F[0]-E[2]*0.5)}});
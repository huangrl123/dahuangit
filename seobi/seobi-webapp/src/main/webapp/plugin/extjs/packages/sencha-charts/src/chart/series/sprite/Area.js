Ext.define("Ext.chart.series.sprite.Area",{alias:"sprite.areaSeries",extend:"Ext.chart.series.sprite.StackedCartesian",inheritableStatics:{def:{processors:{step:"bool"},defaults:{step:false}}},renderClipped:function(O,I,V){var B=this,N=B.attr,Z=N.dataX,A=N.dataY,P=N.dataStartY,K=N.matrix,W,X,F,H,G,C,U,S=K.elements[0],M=K.elements[4],D=K.elements[3],T=K.elements[5],E=B.surfaceMatrix,J={},R=Math.min(V[0],V[2]),Q=Math.max(V[0],V[2]),Y=Math.max(0,this.binarySearch(R)),L=Math.min(Z.length-1,this.binarySearch(Q)+1);I.beginPath();C=Z[Y]*S+M;U=A[Y]*D+T;I.moveTo(C,U);if(N.step){G=U;for(F=Y;F<=L;F++){W=Z[F]*S+M;X=A[F]*D+T;I.lineTo(W,G);I.lineTo(W,G=X)}}else{for(F=Y;F<=L;F++){W=Z[F]*S+M;X=A[F]*D+T;I.lineTo(W,X)}}if(P){if(N.step){H=Z[L]*S+M;for(F=L;F>=Y;F--){W=Z[F]*S+M;X=P[F]*D+T;I.lineTo(H,X);I.lineTo(H=W,X)}}else{for(F=L;F>=Y;F--){W=Z[F]*S+M;X=P[F]*D+T;I.lineTo(W,X)}}}else{I.lineTo(Z[L]*S+M,X);I.lineTo(Z[L]*S+M,T);I.lineTo(C,T);I.lineTo(C,A[F]*D+T)}if(N.transformFillStroke){N.matrix.toContext(I)}I.fill();if(N.transformFillStroke){N.inverseMatrix.toContext(I)}I.beginPath();I.moveTo(C,U);if(N.step){for(F=Y;F<=L;F++){W=Z[F]*S+M;X=A[F]*D+T;I.lineTo(W,G);I.lineTo(W,G=X);J.translationX=E.x(W,X);J.translationY=E.y(W,X);B.putMarker("markers",J,F,!N.renderer)}}else{for(F=Y;F<=L;F++){W=Z[F]*S+M;X=A[F]*D+T;I.lineTo(W,X);J.translationX=E.x(W,X);J.translationY=E.y(W,X);B.putMarker("markers",J,F,!N.renderer)}}if(N.transformFillStroke){N.matrix.toContext(I)}I.stroke()}});
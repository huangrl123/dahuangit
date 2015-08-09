(function(){var G=2.0943951023932,E=Math.abs,B=Math.cos,C=Math.cos,H=Math.acos,D=Math.sqrt,F=Math.exp,A=Math.log;Ext.define("Ext.draw.Solver",{singleton:true,cubicRoot:function(I){if(I>0){return F(A(I)/3)}else{if(I<0){return -F(A(-I)/3)}else{return 0}}},linearFunction:function(K,J){var I;if(K===0){I=function(L){return J};I.solve=function(L){return[]}}else{I=function(L){return K*L+J};I.solve=function(L){return[(L-J)/K]}}return I},quadraticFunction:function(O,P,N){var I;if(O===0){return this.linearFunction(P,N)}else{I=function(Q){return(O*Q+P)*Q+N};var M=P*P-4*O*N,J=function(Q){return M+4*O*Q},K=1/O*0.5,L=-K*P;K=E(K);I.solve=function(Q){var R=J(Q);if(R<0){return[]}R=D(R);return[L-R*K,L+R*K]}}return I},cubicFunction:function(T,R,U,K){var J;if(T===0){return this.quadraticFunction(R,U,K)}else{J=function(W){return((T*W+R)*W+U)*W+K};var S=R/T/3,V=U/T,P=K/T,N=S*S,O=(S*V-P)*0.5-S*N,L=N-V/3,I=L*L*L;if(L===0){J.solve=function(W){return[-S+this.cubicRoot(O*2+W/T)]}}else{if(L>0){var M=D(L),Q=M*M*M;M+=M}J.solve=function(d){d/=T;var b=O+d*0.5,a=b*b-I;if(a>0){a=D(a);return[-S+this.cubicRoot(b+a)+this.cubicRoot(b-a)]}else{if(a===0){var W=this.cubicRoot(b),X=-S-W;if(b>=0){return[X,X,-S+2*W]}else{return[-S+2*W,X,X]}}else{var c=H(b/Q)/3,Y=M*C(c)-S,Z=M*C(c+G)-S,e=M*C(c-G)-S;if(Y<Z){if(Z<e){return[Y,Z,e]}else{if(Y<e){return[Y,e,Z]}else{return[e,Y,Z]}}}else{if(Y<e){return[Z,Y,e]}else{if(Z<e){return[Z,e,Y]}else{return[e,Z,Y]}}}}}}}}return J},createBezierSolver:function(L,J,K,I){return this.cubicFunction(3*(J-K)+I-L,3*(L-2*J+K),3*(J-L),L)}})})();
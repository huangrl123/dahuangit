Ext.define("Ext.data.Validation",{extend:"Ext.data.Model",isValidation:true,syncGeneration:0,attach:function(A){this.record=A;delete this.data.id},getValidation:function(){return null},isValid:function(){var A=this;if(A.syncGeneration!==A.record.generation){A.refresh()}return A.dirty},refresh:function(F){var I=this,T=I.data,J=I.record,P=J.fields,A=J.generation,H=J.validators,D=J.data,K,E=J.validationSeparator,U=null,Q,L,B,C,G,S,O,M,N,R,V;if(F||I.syncGeneration!==A){I.syncGeneration=A;for(G=0,M=P.length;G<M;++G){B=P[G];V=B.name;R=D[V];K=B.defaultInvalidMessage;L=0;if(!(V in T)){T[V]=Q=true}else{Q=T[V]}if(B.validate!==Ext.emptyFn){N=B.validate(R,E);if(N!==true){L=N||K}}if((E||!L)&&H){C=H[V];if(C){for(S=0,O=C.length;S<O;++S){N=C[S].validate(R,I);if(N!==true){if(L){L+=E;L+=N||K}else{L=N||K;if(!E){break}}}}}}if(!L){L=true}if(L!==Q){(U||(U={}))[V]=L}}if(U){I.set(U)}}}});
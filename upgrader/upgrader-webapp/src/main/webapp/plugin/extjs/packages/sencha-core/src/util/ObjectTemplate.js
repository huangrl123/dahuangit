Ext.define("Ext.util.ObjectTemplate",{requires:["Ext.XTemplate"],isObjectTemplate:true,excludeProperties:{},valueRe:/^[{][a-z\.]+[}]$/i,statics:{create:function(B,A){if(!Ext.isObject(B)){Ext.Error.raise("The template is not an Object")}return B.isObjectTemplate?B:new Ext.util.ObjectTemplate(B,A)}},constructor:function(B,A){Ext.apply(this,A);this.template=B},apply:function(A){var B=this;delete B.apply;B.apply=B.compile(B.template);return B.apply(A)},privates:{compile:function(E){var D=this,C=D.excludeProperties,A,B,F,G;if(Ext.isString(E)){if(E.indexOf("{")<0){G=function(){return E}}else{if(D.valueRe.test(E)){E=E.substring(1,E.length-1).split(".");G=function(H){for(var J=H,I=0;J&&I<E.length;++I){J=J[E[I]]}return J}}else{E=new Ext.XTemplate(E);G=function(H){return E.apply(H)}}}}else{if(Ext.isPrimitive(E)){G=function(){return E}}else{if(E instanceof Array){A=[];for(B=0,F=E.length;B<F;++B){A[B]=D.compile(E[B])}G=function(H){var J=[],I;for(I=0;I<F;++I){J[I]=A[I](H)}return J}}else{A={};for(B in E){if(!C[B]){A[B]=D.compile(E[B])}}G=function(H){var K={},I,J;for(I in E){J=C[I]?E[I]:A[I](H);if(J!==undefined){K[I]=J}}return K}}}}return G}}});
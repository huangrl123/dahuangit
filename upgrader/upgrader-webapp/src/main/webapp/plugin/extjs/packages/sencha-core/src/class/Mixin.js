Ext.define("Ext.Mixin",function(A){return{statics:{addHook:function(G,C,E,H){var B=Ext.isFunction(G),F=function(){var K=arguments,J=B?G:H[G],I=this.callParent(K);J.apply(this,K);return I},D=C.hasOwnProperty(E)&&C[E];if(B){G.$previous=Ext.emptyFn}F.$name=E;F.$owner=C.self;if(D){F.$previous=D.$previous;D.$previous=F}else{C[E]=F}}},onClassExtended:function(H,E){var J=E.mixinConfig,F=E.xhooks,I=H.superclass,G=E.onClassMixedIn,B,D,C,K;if(F){delete E.xhooks;(J||(E.mixinConfig=J={})).on=F}if(J){B=I.mixinConfig;if(B){E.mixinConfig=J=Ext.merge({},B,J)}E.mixinId=J.id;if(J.beforeHooks){Ext.Error.raise('Use of "beforeHooks" is deprecated - use "before" instead')}if(J.hooks){Ext.Error.raise('Use of "hooks" is deprecated - use "after" instead')}if(J.afterHooks){Ext.Error.raise('Use of "afterHooks" is deprecated - use "after" instead')}D=J.before;C=J.after;F=J.on;K=J.extended}if(D||C||F||K){E.onClassMixedIn=function(M){var O=this.prototype,N=M.prototype,L;if(D){Ext.Object.each(D,function(P,Q){M.addMember(P,function(){if(O[Q].apply(this,arguments)!==false){return this.callParent(arguments)}})})}if(C){Ext.Object.each(C,function(P,Q){M.addMember(P,function(){var R=this.callParent(arguments);O[Q].apply(this,arguments);return R})})}if(F){for(L in F){A.addHook(F[L],N,L,O)}}if(K){M.onExtended(function(){var P=Ext.Array.slice(arguments,0);P.unshift(M);return K.apply(this,P)},this)}if(G){G.apply(this,arguments)}}}}}});
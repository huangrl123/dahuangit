Ext.define("Ext.app.Controller",{extend:"Ext.app.BaseController",requires:["Ext.app.Util","Ext.data.StoreManager","Ext.ComponentManager","Ext.app.domain.Component","Ext.app.domain.Store","Ext.app.route.Router"],statics:{strings:{model:{getter:"getModel",upper:"Model"},view:{getter:"getView",upper:"View"},controller:{getter:"getController",upper:"Controller"},store:{getter:"getStore",upper:"Store"}},controllerRegex:/^(.*)\.controller\./,createGetter:function(B,A){return function(){return this[B](A)}},getGetterName:function(D,A){var C="get",B=D.split("."),F=B.length,E;for(E=0;E<F;E++){C+=Ext.String.capitalize(B[E])}C+=A;return C},processDependencies:function(E,A,K,O,D){if(!D||!D.length){return}var J=this,N=J.strings[O],I,C,L,F,G,H,M,B;if(!Ext.isArray(D)){D=[D]}for(G=0,H=D.length;G<H;G++){F=D[G];I=J.getFullName(F,O,K);C=I.absoluteName;L=I.shortName;A.push(C);M=J.getGetterName(L,N.upper);E[M]=B=J.createGetter(N.getter,F);if(O!=="controller"){B["Ext.app.getter"]=true}}},getFullName:function(E,F,A){var B=E,D,C;if((D=E.indexOf("@"))>0){B=E.substring(0,D);C=E.substring(D+1)+"."+B}else{if(E.indexOf(".")>0&&(Ext.ClassManager.isCreated(E)||this.hasRegisteredPrefix(E))){C=E}else{if(!A){Ext.log.warn("Cannot find namespace for "+F+" "+E+", assuming it is fully qualified class name")}if(A){C=A+"."+F+"."+E;B=E}else{C=E}}}return{absoluteName:C,shortName:B}},hasRegisteredPrefix:function(A){var C=Ext.ClassManager,B=C.getPrefix(A);return B&&B!==A}},config:{application:null,refs:null,active:true,moduleClassName:null},onClassExtended:function(B,D,C){var A=C.onBeforeCreated;C.onBeforeCreated=function(H,F){var K=Ext.app.Controller,G=K.controllerRegex,E=[],J,M,E,I,L;I=H.prototype;J=Ext.getClassName(H);M=F.$namespace||F.namespace||Ext.app.getNamespace(J)||((L=G.exec(J))&&L[1]);if(M){I.$namespace=M}else{Ext.log.warn("Missing namespace for "+J+", please define it in namespaces property of your Application class.")}K.processDependencies(I,E,M,"model",F.models);K.processDependencies(I,E,M,"view",F.views);K.processDependencies(I,E,M,"store",F.stores);K.processDependencies(I,E,M,"controller",F.controllers);Ext.require(E,Ext.Function.pass(A,arguments,this))}},constructor:function(A){this.callParent(arguments);this.initAutoGetters()},normalizeRefs:function(C){var B=this,A=[];if(C){if(Ext.isObject(C)){Ext.Object.each(C,function(D,E){if(Ext.isString(E)){E={selector:E}}E.ref=D;A.push(E)})}else{if(Ext.isArray(C)){A=Ext.Array.merge(A,C)}}}C=B.refs;if(C){B.refs=null;C=B.normalizeRefs(C);if(C){A=Ext.Array.merge(A,C)}}return A},applyRefs:function(A){return this.normalizeRefs(Ext.clone(A))},updateRefs:function(A){if(A){this.ref(A)}},initAutoGetters:function(){var B=this.self.prototype,C,A;for(C in B){A=B[C];if(A&&A["Ext.app.getter"]){A.call(this)}}},doInit:function(A){var B=this;if(!B._initialized){B.init(A);B._initialized=true}},finishInit:function(C){var E=this,F=E.controllers,A,B,D;if(E._initialized&&F&&F.length){for(B=0,D=F.length;B<D;B++){A=E.getController(F[B]);A.finishInit(C)}}},init:Ext.emptyFn,onLaunch:Ext.emptyFn,activate:function(){this.setActive(true)},deactivate:function(){this.setActive(false)},isActive:function(){return this.getActive()},ref:function(G){var E=this,A=0,B=G.length,F,C,D;G=Ext.Array.from(G);E.references=E.references||[];for(;A<B;A++){F=G[A];C=F.ref;D="get"+Ext.String.capitalize(C);if(!E[D]){E[D]=Ext.Function.pass(E.getRef,[C,F],E)}E.references.push(C.toLowerCase())}},addRef:function(A){this.ref(A)},getRef:function(A,E,B){var C=this,F=C.refCache||(C.refCache={}),D=F[A];E=E||{};B=B||{};Ext.apply(E,B);if(E.forceCreate){return Ext.ComponentManager.create(E,"component")}if(!D){if(E.selector){F[A]=D=Ext.ComponentQuery.query(E.selector)[0]}if(!D&&E.autoCreate){F[A]=D=Ext.ComponentManager.create(E,"component")}if(D){D.on("beforedestroy",function(){F[A]=null})}}return D},hasRef:function(A){var B=this.references;return B&&Ext.Array.indexOf(B,A.toLowerCase())!==-1},getController:function(B){var A=this.application;if(B===this.getId()){return this}return A&&A.getController(B)},getStore:function(B){var A,C;A=(B.indexOf("@")===-1)?B:B.split("@")[0];C=Ext.StoreManager.get(A);if(!C){B=Ext.app.Controller.getFullName(B,"store",this.$namespace);if(B){C=Ext.create(B.absoluteName,{storeId:A})}}return C},getModel:function(C){var A=Ext.app.Controller.getFullName(C,"model",this.$namespace),B=Ext.ClassManager.get(A.absoluteName);if(!B){B=Ext.data.schema.Schema.lookupEntity(C)}return B},getView:function(A){var B=Ext.app.Controller.getFullName(A,"view",this.$namespace);return B&&Ext.ClassManager.get(B.absoluteName)},ensureId:function(){var A=this.getId();if(!A){this.setId(this.getModuleClassName(this.$className,"controller"))}},destroy:function(B,E){var C=this,A=C.application,F,D;if(!E&&A){A.unregister(C)}C.application=null;if(B){F=C.refCache;for(D in F){if(F.hasOwnProperty(D)){Ext.destroy(F[D])}}}C.callParent()}});
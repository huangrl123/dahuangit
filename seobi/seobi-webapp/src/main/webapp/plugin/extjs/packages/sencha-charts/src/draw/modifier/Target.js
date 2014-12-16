Ext.define("Ext.draw.modifier.Target",{requires:["Ext.draw.Matrix"],extend:"Ext.draw.modifier.Modifier",alias:"modifier.target",statics:{uniqueId:0},prepareAttributes:function(A){if(this._previous){this._previous.prepareAttributes(A)}A.attributeId="attribute-"+Ext.draw.modifier.Target.uniqueId++;if(!A.hasOwnProperty("canvasAttributes")){A.bbox={plain:{dirty:true},transform:{dirty:true}};A.dirty=true;A.dirtyFlags={};A.canvasAttributes={};A.matrix=new Ext.draw.Matrix();A.inverseMatrix=new Ext.draw.Matrix()}},setDirtyFlags:function(G,M){Ext.apply(G,M);var B=this._sprite,J=B.self.def._dirtyTriggers,D,L=G.dirtyFlags,I,K=false,E,A,H,F,C;for(D in M){if((E=J[D])){H=0;while((A=E[H++])){if(!(I=L[A])){I=L[A]=[]}I.push(D)}}}for(D in M){K=true;break}if(!K){return}if(L.canvas){C=L.canvas;delete L.canvas;for(H=0,F=C.length;H<F;H++){D=C[H];G.canvasAttributes[D]=G[D]}}if(G.hasOwnProperty("children")){for(H=0,F=G.children.length;H<F;H++){Ext.apply(G.children[H].dirtyFlags,L);B.updateDirtyFlags(G.children[H])}}B.setDirty(true)},popUp:function(A,B){this.setDirtyFlags(A,B);this._sprite.updateDirtyFlags(A)},pushDown:function(B,A){if(this._previous){A=this._previous.pushDown(B,A)}this.setDirtyFlags(B,A);this._sprite.updateDirtyFlags(B);return A}});
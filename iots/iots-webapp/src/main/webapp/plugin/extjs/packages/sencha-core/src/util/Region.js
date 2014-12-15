Ext.define("Ext.util.Region",{requires:["Ext.util.Offset"],isRegion:true,statics:{getRegion:function(A){return Ext.fly(A).getRegion()},from:function(A){return new this(A.top,A.right,A.bottom,A.left)}},constructor:function(E,A,D,B){var C=this;C.y=C.top=C[1]=E;C.right=A;C.bottom=D;C.x=C.left=C[0]=B},contains:function(B){var A=this;return(B.x>=A.x&&B.right<=A.right&&B.y>=A.y&&B.bottom<=A.bottom)},intersect:function(F){var C=this,E=Math.max(C.y,F.y),A=Math.min(C.right,F.right),D=Math.min(C.bottom,F.bottom),B=Math.max(C.x,F.x);if(D>E&&A>B){return new this.self(E,A,D,B)}else{return false}},union:function(F){var C=this,E=Math.min(C.y,F.y),A=Math.max(C.right,F.right),D=Math.max(C.bottom,F.bottom),B=Math.min(C.x,F.x);return new this.self(E,A,D,B)},constrainTo:function(A){var B=this,C=Ext.Number.constrain;B.top=B.y=C(B.top,A.y,A.bottom);B.bottom=C(B.bottom,A.y,A.bottom);B.left=B.x=C(B.left,A.x,A.right);B.right=C(B.right,A.x,A.right);return B},adjust:function(E,A,D,B){var C=this;C.top=C.y+=E;C.left=C.x+=B;C.right+=A;C.bottom+=D;return C},getOutOfBoundOffset:function(C,B){if(!Ext.isObject(C)){if(C=="x"){return this.getOutOfBoundOffsetX(B)}else{return this.getOutOfBoundOffsetY(B)}}else{B=C;var A=new Ext.util.Offset();A.x=this.getOutOfBoundOffsetX(B.x);A.y=this.getOutOfBoundOffsetY(B.y);return A}},getOutOfBoundOffsetX:function(A){if(A<=this.x){return this.x-A}else{if(A>=this.right){return this.right-A}}return 0},getOutOfBoundOffsetY:function(A){if(A<=this.y){return this.y-A}else{if(A>=this.bottom){return this.bottom-A}}return 0},isOutOfBound:function(B,A){if(!Ext.isObject(B)){if(B=="x"){return this.isOutOfBoundX(A)}else{return this.isOutOfBoundY(A)}}else{A=B;return(this.isOutOfBoundX(A.x)||this.isOutOfBoundY(A.y))}},isOutOfBoundX:function(A){return(A<this.x||A>this.right)},isOutOfBoundY:function(A){return(A<this.y||A>this.bottom)},restrict:function(D,B,A){if(Ext.isObject(D)){var C;A=B;B=D;if(B.copy){C=B.copy()}else{C={x:B.x,y:B.y}}C.x=this.restrictX(B.x,A);C.y=this.restrictY(B.y,A);return C}else{if(D=="x"){return this.restrictX(B,A)}else{return this.restrictY(B,A)}}},restrictX:function(B,A){if(!A){A=1}if(B<=this.x){B-=(B-this.x)*A}else{if(B>=this.right){B-=(B-this.right)*A}}return B},restrictY:function(B,A){if(!A){A=1}if(B<=this.y){B-=(B-this.y)*A}else{if(B>=this.bottom){B-=(B-this.bottom)*A}}return B},getSize:function(){return{width:this.right-this.x,height:this.bottom-this.y}},copy:function(){return new this.self(this.y,this.right,this.bottom,this.x)},copyFrom:function(A){var B=this;B.top=B.y=B[1]=A.y;B.right=A.right;B.bottom=A.bottom;B.left=B.x=B[0]=A.x;return this},toString:function(){return"Region["+this.top+","+this.right+","+this.bottom+","+this.left+"]"},translateBy:function(B,C){if(arguments.length==1){C=B.y;B=B.x}var A=this;A.top=A.y+=C;A.right+=B;A.bottom+=C;A.left=A.x+=B;return A},round:function(){var A=this;A.top=A.y=Math.round(A.y);A.right=Math.round(A.right);A.bottom=Math.round(A.bottom);A.left=A.x=Math.round(A.x);return A},equals:function(A){return(this.top===A.top&&this.right===A.right&&this.bottom===A.bottom&&this.left===A.left)}});
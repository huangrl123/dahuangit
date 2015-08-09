Ext.define("Ext.dd.DragDrop",{requires:["Ext.dd.DragDropManager"],constructor:function(C,B,A){if(C){this.init(C,B,A)}},id:null,config:null,dragElId:null,handleElId:null,invalidHandleTypes:null,invalidHandleIds:null,invalidHandleClasses:null,startPageX:0,startPageY:0,groups:null,locked:false,lock:function(){this.locked=true},moveOnly:false,unlock:function(){this.locked=false},isTarget:true,padding:null,_domRef:null,__ygDragDrop:true,constrainX:false,constrainY:false,minX:0,maxX:0,minY:0,maxY:0,maintainOffset:false,xTicks:null,yTicks:null,primaryButtonOnly:true,available:false,hasOuterHandles:false,triggerEvent:"mousedown",b4StartDrag:function(A,B){},startDrag:function(A,B){},b4Drag:function(A){},onDrag:function(A){},onDragEnter:function(A,B){},b4DragOver:function(A){},onDragOver:function(A,B){},b4DragOut:function(A){},onDragOut:function(A,B){},b4DragDrop:function(A){},onDragDrop:function(A,B){},onInvalidDrop:function(A){},b4EndDrag:function(A){},endDrag:function(A){},b4MouseDown:function(A){},onMouseDown:function(A){},onMouseUp:function(A){},onAvailable:function(){},defaultPadding:{left:0,right:0,top:0,bottom:0},constrainTo:function(D,J,F){if(Ext.isNumber(J)){J={left:J,right:J,top:J,bottom:J}}J=J||this.defaultPadding;var K=Ext.get(this.getEl()).getBox(),C=Ext.get(D),A=C.getScroll(),H,G=C.dom,B,E,I;if(G==document.body){H={x:A.left,y:A.top,width:Ext.Element.getViewportWidth(),height:Ext.Element.getViewportHeight()}}else{B=C.getXY();H={x:B[0],y:B[1],width:G.clientWidth,height:G.clientHeight}}E=K.y-H.y;I=K.x-H.x;this.resetConstraints();this.setXConstraint(I-(J.left||0),H.width-I-K.width-(J.right||0),this.xTickSize);this.setYConstraint(E-(J.top||0),H.height-E-K.height-(J.bottom||0),this.yTickSize)},getEl:function(){if(!this._domRef){this._domRef=Ext.getDom(this.id)}return this._domRef},getDragEl:function(){return Ext.getDom(this.dragElId)},init:function(D,C,B){var A=this;A.el=A.el||Ext.get(D);A.initTarget(D,C,B);Ext.get(A.id).on(A.triggerEvent,A.handleMouseDown,A)},initTarget:function(C,B,A){this.config=A||{};this.DDMInstance=Ext.dd.DragDropManager;this.groups={};if(typeof C!=="string"){C=Ext.id(C)}this.id=C;this.addToGroup((B)?B:"default");this.handleElId=C;this.setDragElId(C);this.invalidHandleTypes={A:"A"};this.invalidHandleIds={};this.invalidHandleClasses=[];this.applyConfig();this.handleOnAvailable()},applyConfig:function(){this.padding=this.config.padding||[0,0,0,0];this.isTarget=(this.config.isTarget!==false);this.maintainOffset=(this.config.maintainOffset);this.primaryButtonOnly=(this.config.primaryButtonOnly!==false)},handleOnAvailable:function(){this.available=true;this.resetConstraints();this.onAvailable()},setPadding:function(D,C,B,A){if(!C&&0!==C){this.padding=[D,D,D,D]}else{if(!B&&0!==B){this.padding=[D,C,D,C]}else{this.padding=[D,C,B,A]}}},setInitPosition:function(C,D){var A=this.getEl(),B,F,E;if(!this.DDMInstance.verifyEl(A)){return}B=C||0;F=D||0;E=Ext.fly(A).getXY();this.initPageX=E[0]-B;this.initPageY=E[1]-F;this.lastPageX=E[0];this.lastPageY=E[1];this.setStartPosition(E)},setStartPosition:function(B){var A=B||Ext.fly(this.getEl()).getXY();this.deltaSetXY=null;this.startPageX=A[0];this.startPageY=A[1]},addToGroup:function(A){this.groups[A]=true;this.DDMInstance.regDragDrop(this,A)},removeFromGroup:function(A){if(this.groups[A]){delete this.groups[A]}this.DDMInstance.removeDDFromGroup(this,A)},setDragElId:function(A){this.dragElId=A},setHandleElId:function(A){if(typeof A!=="string"){A=Ext.id(A)}this.handleElId=A;this.DDMInstance.regHandle(this.id,A)},setOuterHandleElId:function(A){if(typeof A!=="string"){A=Ext.id(A)}Ext.get(A).on(this.triggerEvent,this.handleMouseDown,this);this.setHandleElId(A);this.hasOuterHandles=true},unreg:function(){var B=this,A;if(B._domRef){A=Ext.fly(B.id);if(A){A.un(B.triggerEvent,B.handleMouseDown,B)}}B._domRef=null;B.DDMInstance._remove(B,B.autoGroup)},destroy:function(){this.unreg();this.isDestroyed=true},isLocked:function(){return(this.DDMInstance.isLocked()||this.locked)},handleMouseDown:function(A,C){var B=this;if((B.primaryButtonOnly&&A.button!=0)||B.isLocked()){return}B.DDMInstance.refreshCache(B.groups);if(B.hasOuterHandles||B.DDMInstance.isOverTarget(A.getPoint(),B)){if(B.clickValidator(A)){B.setStartPosition();B.b4MouseDown(A);B.onMouseDown(A);B.DDMInstance.handleMouseDown(A,B);B.DDMInstance.stopEvent(A)}}},clickValidator:function(A){var B=A.getTarget();return(this.isValidHandleChild(B)&&(this.id==this.handleElId||this.DDMInstance.handleWasClicked(B,this.id)))},addInvalidHandleType:function(A){var B=A.toUpperCase();this.invalidHandleTypes[B]=B},addInvalidHandleId:function(A){if(typeof A!=="string"){A=Ext.id(A)}this.invalidHandleIds[A]=A},addInvalidHandleClass:function(A){this.invalidHandleClasses.push(A)},removeInvalidHandleType:function(A){var B=A.toUpperCase();delete this.invalidHandleTypes[B]},removeInvalidHandleId:function(A){if(typeof A!=="string"){A=Ext.id(A)}delete this.invalidHandleIds[A]},removeInvalidHandleClass:function(B){for(var A=0,C=this.invalidHandleClasses.length;A<C;++A){if(this.invalidHandleClasses[A]==B){delete this.invalidHandleClasses[A]}}},isValidHandleChild:function(C){var D=true,F,A,E;try{F=C.nodeName.toUpperCase()}catch(B){F=C.nodeName}D=D&&!this.invalidHandleTypes[F];D=D&&!this.invalidHandleIds[C.id];for(A=0,E=this.invalidHandleClasses.length;D&&A<E;++A){D=!Ext.fly(C).hasCls(this.invalidHandleClasses[A])}return D},setXTicks:function(D,B){this.xTicks=[];this.xTickSize=B;var C={},A;for(A=this.initPageX;A>=this.minX;A=A-B){if(!C[A]){this.xTicks[this.xTicks.length]=A;C[A]=true}}for(A=this.initPageX;A<=this.maxX;A=A+B){if(!C[A]){this.xTicks[this.xTicks.length]=A;C[A]=true}}Ext.Array.sort(this.xTicks,this.DDMInstance.numericSort)},setYTicks:function(D,B){this.yTicks=[];this.yTickSize=B;var C={},A;for(A=this.initPageY;A>=this.minY;A=A-B){if(!C[A]){this.yTicks[this.yTicks.length]=A;C[A]=true}}for(A=this.initPageY;A<=this.maxY;A=A+B){if(!C[A]){this.yTicks[this.yTicks.length]=A;C[A]=true}}Ext.Array.sort(this.yTicks,this.DDMInstance.numericSort)},setXConstraint:function(A,B,C){this.leftConstraint=A;this.rightConstraint=B;this.minX=this.initPageX-A;this.maxX=this.initPageX+B;if(C){this.setXTicks(this.initPageX,C)}this.constrainX=true},clearConstraints:function(){this.constrainX=false;this.constrainY=false;this.clearTicks()},clearTicks:function(){this.xTicks=null;this.yTicks=null;this.xTickSize=0;this.yTickSize=0},setYConstraint:function(A,C,B){this.topConstraint=A;this.bottomConstraint=C;this.minY=this.initPageY-A;this.maxY=this.initPageY+C;if(B){this.setYTicks(this.initPageY,B)}this.constrainY=true},resetConstraints:function(){if(this.initPageX||this.initPageX===0){var A=(this.maintainOffset)?this.lastPageX-this.initPageX:0,B=(this.maintainOffset)?this.lastPageY-this.initPageY:0;this.setInitPosition(A,B)}else{this.setInitPosition()}if(this.constrainX){this.setXConstraint(this.leftConstraint,this.rightConstraint,this.xTickSize)}if(this.constrainY){this.setYConstraint(this.topConstraint,this.bottomConstraint,this.yTickSize)}},getTick:function(G,E){if(!E){return G}else{if(E[0]>=G){return E[0]}else{var A,F,D,B,C;for(A=0,F=E.length;A<F;++A){D=A+1;if(E[D]&&E[D]>=G){B=G-E[A];C=E[D]-G;return(C>B)?E[A]:E[D]}}return E[E.length-1]}}},toString:function(){return("DragDrop "+this.id)}});
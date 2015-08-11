Ext.define("Ext.ProgressBar",{extend:"Ext.Component",alias:"widget.progressbar",requires:["Ext.Template","Ext.CompositeElement","Ext.TaskManager","Ext.layout.component.ProgressBar"],uses:["Ext.fx.Anim"],config:{value:0,textTpl:null},baseCls:Ext.baseCSSPrefix+"progress",animate:false,text:"",waitTimer:null,childEls:["bar"],defaultBindProperty:"value",renderTpl:['<tpl if="internalText">','<div class="{baseCls}-text {baseCls}-text-back">{text}</div>',"</tpl>",'<div id="{id}-bar" data-ref="bar" class="{baseCls}-bar {baseCls}-bar-{ui}" role="presentation" style="width:{percentage}%">','<tpl if="internalText">','<div class="{baseCls}-text">',"<div>{text}</div>","</div>","</tpl>","</div>"],componentLayout:"progressbar",ariaRole:"progressbar",initRenderData:function(){var A=this;return Ext.apply(A.callParent(),{internalText:!A.hasOwnProperty("textEl"),text:A.text||"&#160;",percentage:A.value?A.value*100:0})},onRender:function(){var A=this;A.callParent(arguments);if(A.textEl){A.textEl=Ext.get(A.textEl);A.updateText(A.text)}else{A.textEl=A.el.select("."+A.baseCls+"-text")}},updateValue:function(A){this.updateProgress(A,Math.round(A*100)+"%")},updateProgress:function(D,C,B){var A=this,E=A.value,F=A.getTextTpl();A.value=D||(D=0);if(C!=null){A.updateText(C)}else{if(F){A.updateText(F.apply({value:D,percent:D*100}))}}if(A.rendered&&!A.isDestroyed){if(B===true||(B!==false&&A.animate)){A.bar.stopAnimation();A.bar.animate(Ext.apply({from:{width:(E*100)+"%"},to:{width:(D*100)+"%"}},A.animate))}else{A.bar.setStyle("width",(D*100)+"%")}}A.fireEvent("update",A,D,C);return A},updateText:function(B){var A=this;A.text=B;if(A.rendered){A.textEl.setHtml(A.text)}return A},applyTextTpl:function(A){if(!A.isTemplate){A=new Ext.XTemplate(A)}return A},applyText:function(A){this.updateText(A)},getText:function(){return this.text},wait:function(A){var B=this,C;if(!B.waitTimer){C=B;A=A||{};B.updateText(A.text);B.waitTimer=Ext.TaskManager.start({run:function(D){var E=A.increment||10;D-=1;B.updateProgress(((((D+E)%E)+1)*(100/E))*0.01,null,A.animate)},interval:A.interval||1000,duration:A.duration,onStop:function(){if(A.fn){A.fn.apply(A.scope||B)}B.reset()},scope:C})}return B},isWaiting:function(){return this.waitTimer!==null},reset:function(B){var A=this;A.updateProgress(0);A.clearTimer();if(B===true){A.hide()}return A},clearTimer:function(){var A=this;if(A.waitTimer){A.waitTimer.onStop=null;Ext.TaskManager.stop(A.waitTimer);A.waitTimer=null}},onDestroy:function(){var A=this,B=A.bar;A.clearTimer();if(A.rendered){if(A.textEl.isComposite){A.textEl.clear()}Ext.destroyMembers(A,"textEl","progressBar");if(B&&A.animate){B.stopAnimation()}}A.callParent()}});
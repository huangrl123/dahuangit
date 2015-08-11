Ext.define("Ext.ProgressBarWidget",{extend:"Ext.Widget",alias:"widget.progressbarwidget",requires:["Ext.ProgressBar"],config:{text:null,value:0,animate:false,textTpl:null},cachedConfig:{baseCls:Ext.baseCSSPrefix+"progress",textCls:Ext.baseCSSPrefix+"progress-text",ui:"default"},template:[{reference:"backgroundEl"},{reference:"barEl",children:[{reference:"textEl"}]}],defaultBindProperty:"value",updateUi:function(A,C){var B=this.getBaseCls()+"-";if(C){this.element.removeCls(B+C);this.barEl.removeCls(B+"bar-"+C)}this.element.addCls(B+A);this.barEl.addCls(B+"bar-"+A)},updateBaseCls:function(A,B){if(B){Ext.Error.raise("You cannot configure baseCls - use a subclass")}this.element.addCls(A);this.barEl.addCls(A+"-bar")},updateTextCls:function(A){this.backgroundEl.addCls(A+" "+A+"-back");this.textEl.addCls(A)},updateValue:function(C,A){var B=this,D=B.getTextTpl();if(D){B.setText(D.apply({value:C,percent:C*100}))}if(B.getAnimate()){B.barEl.stopAnimation();B.barEl.animate(Ext.apply({from:{width:(A*100)+"%"},to:{width:(C*100)+"%"}},B.animate))}else{B.barEl.setStyle("width",(C*100)+"%")}},updateText:function(B){var A=this;A.backgroundEl.setHtml(B);A.textEl.setHtml(B)},applyTextTpl:function(A){if(!A.isTemplate){A=new Ext.XTemplate(A)}return A}});
Ext.define("Ext.chart.LegendBase",{extend:"Ext.dataview.DataView",config:{itemTpl:['<span class="',Ext.baseCSSPrefix,"legend-item-marker {[ values.disabled ? Ext.baseCSSPrefix + 'legend-inactive' : '' ]}\" style=\"background:{mark};\"></span>{name}"],inline:true,horizontalHeight:48,verticalHeight:150},constructor:function(){this.callParent(arguments);var A=this.getScrollable().getScroller(),B=A.onDrag;A.onDrag=function(C){C.stopPropagation();B.call(this,C)}},doSetDocked:function(A){this.callParent(arguments);if(A==="top"||A==="bottom"){this.setLayout({type:"hbox",pack:"center"});this.setInline(true);this.setWidth(null);this.setHeight(this.getHorizontalHeight());if(this.getScrollable()){this.setScrollable({direction:"horizontal"})}}else{this.setLayout({pack:"center"});this.setInline(false);this.setWidth(this.getVerticalWidth());this.setHeight(null);if(this.getScrollable()){this.setScrollable({direction:"vertical"})}}},setDocked:function(A){this.callParent(arguments);if(A==="top"||A==="bottom"){this.setLayout({type:"hbox",pack:"center"});this.setInline(true);this.setWidth(null);this.setHeight(this.getHorizontalHeight())}else{this.setLayout({pack:"center"});this.setInline(false);this.setWidth(this.getVerticalWidth());this.setHeight(null)}},onItemTap:function(B,D,C,A){this.callParent(arguments);this.toggleItem(C)}});
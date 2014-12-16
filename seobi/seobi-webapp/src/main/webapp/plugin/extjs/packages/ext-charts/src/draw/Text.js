Ext.define("Ext.draw.Text",{extend:"Ext.draw.Component",uses:["Ext.util.CSS"],alias:"widget.text",text:"",focusable:false,viewBox:false,autoSize:true,baseCls:Ext.baseCSSPrefix+"surface "+Ext.baseCSSPrefix+"draw-text",initComponent:function(){var A=this;A.textConfig=Ext.apply({type:"text",text:A.text,rotate:{degrees:A.degrees||0}},A.textStyle);Ext.apply(A.textConfig,A.getStyles(A.styleSelectors||A.styleSelector));A.initialConfig.items=[A.textConfig];A.callParent(arguments)},getStyles:function(D){D=Ext.Array.from(D);var A=0,F=D.length,B,E,G,C={};for(;A<F;A++){B=Ext.util.CSS.getRule(D[A]);if(B){E=B.style;if(E){Ext.apply(C,{"font-family":E.fontFamily,"font-weight":E.fontWeight,"line-height":E.lineHeight,"font-size":E.fontSize,fill:E.color})}}}return C},setAngle:function(A){var B=this,D,C;if(B.rendered){D=B.surface;C=D.items.items[0];B.degrees=A;C.setAttributes({rotate:{degrees:A}},true);if(B.autoSize||B.viewBox){B.updateLayout()}}else{B.degrees=A}},setText:function(B){var A=this,D,C;if(A.rendered){D=A.surface;C=D.items.items[0];A.text=B||"";D.remove(C);A.textConfig.type="text";A.textConfig.text=A.text;C=D.add(A.textConfig);C.setAttributes({rotate:{degrees:A.degrees}},true);if(A.autoSize||A.viewBox){A.updateLayout()}}else{A.on({render:function(){A.setText(B)},single:true})}}});
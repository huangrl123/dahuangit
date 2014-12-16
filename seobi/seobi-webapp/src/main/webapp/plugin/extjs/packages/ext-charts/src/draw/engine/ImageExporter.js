Ext.define("Ext.draw.engine.ImageExporter",{singleton:true,defaultUrl:"http://svg.sencha.io",supportedTypes:["image/png","image/jpeg"],widthParam:"width",heightParam:"height",typeParam:"type",svgParam:"svg",formCls:Ext.baseCSSPrefix+"hide-display",generate:function(E,C){C=C||{};var D=this,A=C.type,B;if(Ext.Array.indexOf(D.supportedTypes,A)===-1){return false}B=Ext.getBody().createChild({tag:"form",method:"POST",action:C.url||D.defaultUrl,cls:D.formCls,children:[{tag:"input",type:"hidden",name:C.widthParam||D.widthParam,value:C.width||E.width},{tag:"input",type:"hidden",name:C.heightParam||D.heightParam,value:C.height||E.height},{tag:"input",type:"hidden",name:C.typeParam||D.typeParam,value:A},{tag:"input",type:"hidden",name:C.svgParam||D.svgParam}]});B.last(null,true).value=Ext.draw.engine.SvgExporter.generate(E);B.dom.submit();B.remove();return true}});
Ext.define("Ext.rtl.chart.series.Gauge",{override:"Ext.chart.series.Gauge",initialize:function(){var A=this;A.callParent(arguments);if(A.chart.getInherited().rtl){A.reverse=true}}});
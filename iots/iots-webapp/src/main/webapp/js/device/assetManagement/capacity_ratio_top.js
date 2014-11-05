//数量占比capacity_ratio_top
$(function () {
	var gbProp=40;
	var zbProp=60;
	showNumProp (gbProp,zbProp);
});
 function showNumProp (gbProp,zbProp){
    $('#capacity_ratio_top').highcharts({

        chart: {
            type: 'gauge',
            margin: [0, 0,0, 0],
            height: 230
        },

        title: {
            text: ''
        },
        pane: [{
            startAngle: -90,
            endAngle: 90,
            background: null,
           // center: ['45%', '90%'],
            size: 170
        }],

        yAxis: [{
            min: 0,
            max: 100,
           // minorTickPosition: 'outside',
            tickPosition: 'outside',
            labels: {
               // rotation: 'auto',
                distance: 10
            },
            plotBands: [{
                from: 0,
                to: gbProp,
                color: '#fa9326',
              innerRadius: '-1%'
	        //	outerRadius: '45%'
            }, {
	            from: gbProp,
	            to: 100,innerRadius: '-1%',
	            color: '#88da20' 
	        }],
            pane: 0,
            title: {
                text: '',
                y: -40
            }
        }],
  		exporting: {            enabled:false},  	
	    credits: {                                                         
            enabled: false                                                 
        },   
        tooltip: { 
			formatter: function() {
				//var kmh = this.y,
                //mph = zbProp;
				
				return  '<b>公变:<b>'+ gbProp +'%'+' <b>专变:<b>'+zbProp+'%'; 
			}
		},

        plotOptions: {
            gauge: {
             //   dataLabels: {
              //      enabled: false
              //  },
            	
                dial: {
                    radius: '110%'
                }
            }
           
        },


         series: [{
	        data: [gbProp],
	        yAxis: 0//,
             /*dataLabels: {
                   //x: 0,
            	 y: 12,
	            formatter: function () {
	                var kmh = this.y,
	                    mph = 100-kmh;
                    return '<span style="color:#fa9326">公变：'+ kmh  + ' %</span> <span style="color:#88da20">专变：' + mph + ' %</span>';
	            },
	            backgroundColor: {
	                linearGradient: {
	                    x1: 0,
	                    y1: 0,
	                    x2: 0,
	                    y2: 1
	                },
	                stops: [
	                    [0, '#DDD'],
	                    [1, '#FFF']
	                ]
	            }
	        }*/
	    }]

    },

        // Let the music play
        function (chart) {
           

        });
};
//});
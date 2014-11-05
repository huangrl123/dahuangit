//型号
$(function () {
	var gbProp=68.23;
	showModelProp (gbProp);
});
function showModelProp (gbProp){
    $('#vender_pie').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: 0,
            margin: [0, 0, 40, 0],
            plotShadow: false
        },
        title: {
            text: '',
            align: 'center',
            verticalAlign: 'middle',
            y: 50
        },
        colors: ['#fa9326', '#88da20'],

        tooltip: {
        	 pointFormat: '{series.data[0]}{point.percentage:.1f}%'
        },

		legend: {
			layout: 'horizontal', //【图例】显示的样式：水平（horizontal）/垂直（vertical）
			backgroundColor: '#FFFFFF',
			borderColor: '#CCC',
			//borderWidth: 1,
			align: 'center',
			verticalAlign: 'bottom',
			enabled:true//,
			//x: 130,
			
			//margin: -100
			//itemWidth:160,
			//shadow: true
		},

        plotOptions: {
            pie: {
                dataLabels: {
                    enabled: true,
                    distance: -50,
                    style: {
                        fontWeight: 'bold',
                        color: 'white',
                        textShadow: '0px 1px 2px black'
                    }
                },
                showInLegend: true//,

               // startAngle: -90,
             //   endAngle: 90,
             //   center: ['50%', '75%']
            }
        },
        credits: {                                                         
            enabled: false                                                 
        },   
        exporting: {            enabled:false},  
        series: [{
            type: 'pie',
            name: '',
            innerSize: '70%',
            data: [
                ['油变', 100-gbProp],
                ['干变', gbProp]
            ]
        }]
    });
};   
//});                                                                                                                                      				
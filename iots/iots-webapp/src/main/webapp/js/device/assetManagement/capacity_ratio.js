//容量占比
$(function () {
	var gbProp=70;
	showCapacityProp (gbProp);
});

function showCapacityProp (gbProp){
    $('#container').highcharts({
        chart: {
           // plotBackgroundColor: null,
            //plotBorderWidth: 0//,
            margin: [0, 0, 0, 0]//,
            //plotShadow: false
        },
        title: {
            text: ''//,
           // align: 'center',
           // verticalAlign: 'middle',
          //  y: 50
        },
         colors: ['#fa9326', '#88da20'],

        tooltip: {
            pointFormat: '<b>{point.percentage:.1f}%</b>'
        },

		/*legend: {
			layout: 'vertical', //【图例】显示的样式：水平（horizontal）/垂直（vertical）
			backgroundColor: '#FFFFFF',
			borderColor: '#CCC',
			//borderWidth: 1,
			align: 'center',
			verticalAlign: 'bottom',
			enabled:true,
			x: 130,
			
			margin: -100
			//itemWidth:160,
			//shadow: true
		},*/

        plotOptions: {
            pie: {
                dataLabels: {
                    //enabled: true,
                    distance: 5/*,
                    style: {
                        fontWeight: 'bold',
                        color: 'white',
                        textShadow: '0px 1px 2px black'
                    }*/
                },
                showInLegend: false,

                startAngle: -90,
                endAngle: 90,
                center: ['50%', '75%']
            }
        },
        credits: {                                                         
            enabled: false                                                 
        },   
        exporting: {            enabled:false},  
        series: [{
            type: 'pie',
            name: '',
            innerSize: '60%',
            data: [
                ['公变', gbProp],
                ['专变', 100-gbProp]
            ]
        }]
    });
};   
//});
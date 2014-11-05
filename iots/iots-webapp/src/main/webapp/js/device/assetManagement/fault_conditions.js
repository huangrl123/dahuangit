//故障情况
$(function () {
    $('#fault_conditions').highcharts({
        chart: {
            type: 'column',
              margin: [20, 10, 40, 60]
        },
        title: {
            text: ''
        },
        xAxis: {
            categories: ['2010', '2011', '2012', '2013', '2014'],
             labels: {
                //rotation: 45,
              //  align: 'right',
                y:22,	//控制x轴坐标上的标题与坐标线的距离

                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        },
        colors: ['#99CCFF','#3399CC','#336699'],
        yAxis: {
            min: 0,
            title: {
                text: '故障次'
            },tickPixelInterval:5
        },
        tooltip: {
            pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b> ({point.percentage:.0f}%)<br/>',
            shared: true
        },
        
         legend: {
            enabled: false
        },
        credits: {                                                         
            enabled: false                                                 
        },   
        exporting: {            enabled:false},  
        plotOptions: {
            column: {
                stacking: 'percent'
            }
        },
            series: [{
            name: '轻度故障',
            data: [5, 15, 4, 7, 2]
        }, {
            name: '中度故障',
            data: [2, 2, 3, 2, 1]
        }, {
            name: '严重故障',
            data: [3, 4, 4, 2, 5]
        }]
    });
});				
				
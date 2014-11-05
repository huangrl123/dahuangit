//油量记录
$(function () {
    $('#oil_record').highcharts({
        chart: {
            type: 'line',
            margin: [20, 10, 40, 60]
        },
        title: {
            text: ''
        },
 
        xAxis: {
            categories: ['1', '2', '3', '4', '5', '6', '7'],
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
         colors: ['#FF9900'],
 
          yAxis: {
        	min: 0,
			title: {
				text: '油量' 
			},
			tickPixelInterval:5,  
			//001墙壁的横线会变粗 
			plotLines: [{
				value: 0,
				width: 1,
				color: '#808080' 
			}]

        },
        tooltip: {
            enabled: false,
            formatter: function() {
                return '<b>'+ this.series.name +'</b><br/>'+this.x +': '+ this.y +'';
            }
        },
          legend: {
            enabled: false
        },
        credits: {                                                         
            enabled: false                                                 
        },   
        exporting: {            enabled:false},  
        plotOptions: {
            line: {
                dataLabels: {
                    enabled: true
                },
                enableMouseTracking: true
            },
             series: {
        cursor: 'pointer',
        events: {
            click: function(event) {
                window.location.href="toByqDetail";
            }
        }
    }
        },
        series: [{
            name: '油量',
            data: [0, 5, 10, 11, 16, 24, 32]
        }]
    });
});				
				
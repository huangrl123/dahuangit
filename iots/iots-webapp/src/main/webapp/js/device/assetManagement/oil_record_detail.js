//油压记录详情查询
$(function () {
    $('#oil_record_detail').highcharts({
        chart: {
            type: 'line',
            margin: [20, 10, 70, 60]
        },
        title: {
            text: ''
        },
 
        xAxis: {
            categories: ['2014-10-01','2014-10-02','2014-10-03','2014-10-04','2014-10-05','2014-10-06','2014-10-07','2014-10-08','2014-10-09','2014-10-10','2014-10-11','2014-10-12','2014-10-13','2014-10-14','2014-10-15','2014-10-16','2014-10-17','2014-10-18','2014-10-19','2014-10-20','2014-10-21','2014-10-22','2014-10-23','2014-10-24','2014-10-25','2014-10-26','2014-10-27','2014-10-28','2014-10-29','2014-10-30','2014-10-31'],
            labels: {
                rotation: -35,
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
				text: '油压' 
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
            name: '油压',
            data: [6, 5, 10, 11, 16, 24, 32,10, 5, 10, 11, 161, 24, 220, 150, 10, 111, 16, 24, 320, 5, 14, 11, 136, 24, 120, 115, 10, 24, 32,0]
        }]
    });
});				
				
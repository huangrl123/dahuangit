//三相不平衡率
$(function () {
    $('#three_phase_unbalance_rate').highcharts({
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
        colors: ['#99CCFF'],
       /* colors: ['#50B432','#ED561B', '#DDDF00', '#24CBE5', '#64E572', '#8B5F65',
		           '#551A8B', '#6AF9C4', '#FFB90F', '#FF6EB4', '#000','#CDAD00','#CAFF70',
		           '#A2CD5A','#828282','red','#388E8E'],
 */
          yAxis: {
        	min: 0,
			title: {
				text: '三相不平衡率' 
			},
			tickPixelInterval:5,  
			//001墙壁的横线会变粗 
			plotLines: [{
				value: 0,
				width: 1,
				color: '#608080' 
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
            }
        },
        series: [{
            name: '三相不平衡率',
            data: [10, 0, 30, 0, 16, 24, 32]
        }]
    });
});				
				
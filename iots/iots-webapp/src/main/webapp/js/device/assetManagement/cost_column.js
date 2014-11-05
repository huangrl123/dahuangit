//成本
$(function () {
    $('#cost_column').highcharts({
        chart: {
            type: 'column',
            	margin: [20, 10, 40, 50]
        },
        title: {
            text: ""
        },
        xAxis: {
        	title: {
				text: null
			},

            categories: [
                '2010',
                '2011',
                '2012',
                '2013',
                '2014'
            ],
            labels: {
                rotation: -15,
              //  align: 'right',
                y:22,	//控制x轴坐标上的标题与坐标线的距离

                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        },
        yAxis: {
        	min: 0,
        	tickPixelInterval:20,  
        	title: {
				text: '' 
			},
        	labels: {
				formatter: function() {
					//格式化标签名称   
					return this.value + '';
				},
				style: {
					color: '#89A54E' //设置标签颜色
				}
			}, 
			//001墙壁的横线会变粗 
			plotLines: [{
				value: 0,
				width: 1,
				color: '#808080' 
			}]

        },
        legend: {
            enabled: false
        },
        plotOptions: {
			column: {
				
				dataLabels: {
					enabled: true
				}
			}
		},

        tooltip: {
        	formatter: function() {
				return '<b>'+ this.series.name +'</b><br/>'+
				this.x +'成本为:'+ this.y +''; 
			}

        },
        credits: {                                                         
            enabled: false                                                 
        },   
        exporting: {            enabled:false},  
        series: [{
            name: '成本',
            data: [40, 82, 62, 72, 93],
            dataLabels: {
                enabled: true,
             //   rotation: -90,
                color: '#808080',
              //  align: 'right',
              //  x: 7,
              //  y: 2,
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif',
                    textShadow: '0 0 3px black'
                }
            }
        }]
    });
});
				
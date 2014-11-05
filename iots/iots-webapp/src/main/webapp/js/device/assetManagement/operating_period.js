//运行年限
$(function () {
	var names=['0~5年','5~10年','10~15年','15年以上'];
	var dvNums=[60,53,27,18];
	showRunYear (names,dvNums);
});
function showRunYear (names,dvNums){
    $('#operating_period').highcharts({
        chart: {
            type: 'column',
            margin: [20, 10, 40, 60]
        },
        title: {
            text: ""
        },
        xAxis: {
        	title: {
				text: null
			},

            categories: names,
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
			title: {
				text: '' 
			},
			tickPixelInterval:20,  
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
				return '<b>'+ this.series.name +'</b><br/>'+'已运行'+
				this.x +'设备数量为:'+ this.y +'个'; 
			}

        },
        credits: {                                                         
            enabled: false                                                 
        },   
        exporting: {            enabled:false},  
        series: [{
            name: '运行年限',
            data: dvNums,
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
};    
//});
				
//负载情况
$(function () {
	var names=['30%以下','30%-50%','50%-80%','80%-100%',"100%以上"];
	var dvNums=[14, 21, 40, 80, 15];
	showLoad(names, dvNums);
});
	
	function showLoad (names, dvNums) {
	    $('#load_condition').highcharts({
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
	            categories:names,
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
				}, tickPixelInterval:10,  
	        	labels: {
					formatter: function() {
						//格式化标签名称   
						return this.value + '台';
					},
					style: {
						color: '#89A54E' //设置标签颜色
					}
				}, 
				//001墙壁的横线会变粗 
				plotLines: [{
					value: 0,
					width: 1,
					zIndex:1,
					color: '#808080' 
				}]
	
	        },
	        legend: {
	            enabled: false
	        },
	        exporting: {            enabled:false},  
	        plotOptions: {
				column: {
					cursor: 'pointer',     
					events :{
						click:function(){
							
							window.location.href="toByqSecend";
						}
					},
					dataLabels: {
						enabled: true
					}
				}
			},
	
	        tooltip: {
	        	formatter: function() {
					return '<b>'+ this.series.name +'</b><br/>'+'已使用'+
					this.x +'额定容量设备数量为:'+ this.y +'个'; 
				}
	
	        },
	        credits: {                                                         
	            enabled: false                                                 
	        },   
	        series: [{
	            name: '额定容量',
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
				
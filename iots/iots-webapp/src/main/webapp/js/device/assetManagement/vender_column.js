//厂家
$(function () {
	var cjNames=['森智嘉', '三和弈佳', '深圳嘉奕', '沈阳奕辰', '广州仟奕', '深圳奕马成'];
	var dvNums=[19, 40, 37, 20, 48, 13];
	showManufacturer (cjNames, dvNums);
});
function showManufacturer (cjNames, dvNums){                                                               
    $('#vender_column').highcharts({                                           
        chart: {                                                           
            type: 'bar',
            margin: [0, 0, 30, 85]
        },                                                                 
        title: {                                                           
            text: ''                    
        },                                                                
        xAxis: {                                                           
            categories: cjNames,
            
            title: {                                                       
                text: null                                                 
            }                                                              
        },                                                                 
        yAxis: {                                                           
            min: 0,
           
            title: {
				text: '' 
			},                                                          
            labels: {                                                      
            	overflow: 'justify'                              
            },
          //001墙壁的横线会变粗 
			plotLines: [{
				value: 0,
				width: 1,
				color: '#808080' 
			}]
        },                                                                 
                                                                
        plotOptions: {                                                     
            bar: {                                                         
                dataLabels: {                                              
                    enabled: true                                          
                }                                                          
            }                                                              
        },        
        legend: {
            enabled: false
        },
        exporting: {            enabled:false},  
  /*      legend: {                                                          
            layout: 'vertical',                                            
            align: 'right',                                                
            verticalAlign: 'top',                                          
            x: -40,                                                        
            y: 100,                                                        
            floating: true,                                                
            borderWidth: 1,                                                
            backgroundColor: '#FFFFFF',                                    
            shadow: true                                                   
        },*/
        tooltip: {
        	formatter: function() {
				return '<b>'+ this.x +'</b><br/>'+
				'设备数量为:'+ this.y +'个'; 
			}

        },
        credits: {                                                         
            enabled: false                                                 
        },                                                                 
        series: [{                                                         
            data: dvNums                                   
        }]                                                                 
    });
};
//});                                                                                                                                              				
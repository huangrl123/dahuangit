/*!
 * Ext JS Library 3.2.0
 * Copyright(c) 2006-2010 Ext JS, Inc.
 * licensing@extjs.com
 * http://www.extjs.com/license
 */
 
Ext.onReady(function(){
Ext.BLANK_IMAGE_URL = "../../js/ext-3.2.1/resources/images/default/s.gif";

    Ext.QuickTips.init();
    

  
  /*  var encode = false;
    
  
    var local = true;

      var record = new Ext.data.Record.create([{
       name : "id",
       type : "string",
       mapping : "id"
    },{
       name : "company",
       mapping : "company"
    },{
       name : "price",
       mapping : "price"
    }, {
            name: 'size',
             mapping : "size"
        }]);
    var reader = new Ext.data.JsonReader({totalProperty : "totalCount",
		root : 'results'},record);
    var proxy = new Ext.data.HttpProxy({
        url :"toByqSecendData",
        timeout : 360000
    });
    
        
    
	var store = new Ext.data.Store({
		 idProperty: 'id',
    	reader : reader,
    	proxy : proxy
   	});
   
  
    var filters = new Ext.ux.grid.GridFilters({
     //   encode: encode, // json encode the filter query
     //   local: local,   // defaults to false (remote filtering)
        filters: [{
            type: 'string',
            dataIndex: 'id'
        }, {
            type: 'string',
            dataIndex: 'company',
            disabled: true
        }, {
            type: 'string',
            dataIndex: 'price'
        }, {
            type: 'string',
            dataIndex: 'size'//,
        //    options: ['1', '2', '3', '4'],
           // phpMode: true
        }]
    });    
    

    var createColModel = function (finish, start) {

        var columns = [{
            dataIndex: 'id',
            header: 'id',
            filterable: true
        }, {
            dataIndex: 'company',
            header: 'company',
            id: 'company',
            filter: {
                type: 'string'
              
            }
        }, {
            dataIndex: 'price',
            header: 'price',
            filter: {
                //type: 'numeric'  // specify type here or in store fields config
            }
        }, {
            dataIndex: 'size',
            header: 'size',
            filter: {
                type: 'list',
                options: ['small', 'medium', 'large', 'extra large']
                //,phpMode: true
            }
        }];

        return new Ext.grid.ColumnModel({
            columns: columns.slice(start || 0, finish),
            defaults: {
                sortable: true
            }
        });
    };
    

    
    var grid = new Ext.grid.GridPanel({
        border: false,
        store: store,
        colModel: createColModel(4),
        loadMask: true,
        plugins: [filters],
        //autoExpandColumn: 'company',
        listeners: {
            render: {
                fn: function(){
                    store.load({
                        params: {
                            start: 0,
                            limit: 50
                        }
                    });
                    alert(store.getCount());
                }
            }
        },
        bbar: new Ext.PagingToolbar({
            store: store,
            pageSize: 50,
            plugins: [filters]
        })
    });
    
    
 */
    
   ///////////////////////////////////////22222222222222222222222222 
    /* var columnModel = new Ext.grid.ColumnModel([{
    	header : "啊啊啊",
    	dataIndex : "price",
    	sortable : true,
		align : "center",
		menuDisabled : true,
		width : 150
    }]);
    
    
 
    
    
    var record = new Ext.data.Record.create([{
       name : "price",
       mapping : "price"
    }]);
    var reader = new Ext.data.JsonReader({totalProperty : "totalCount",
		root : 'results'},record);
    var proxy = new Ext.data.HttpProxy({
        url : "toByqSecendData"//,
        //timeout : 360000000
    });
	var check_store = new Ext.data.Store({
		
    	reader : reader,
    	proxy : proxy
   	});

   	
   	

   	
 // 分页navigator
	var paginationNav = new Ext.PagingToolbar({
		pageSize : 20,
		store : check_store,
		autoWidth : true,
		displayInfo : true,
		nextText : '下一页',
		firstText : '第一页',
		prevText : '上一页',
		lastText : '末页',
		displayMsg : "本页显示第{0}~{1}条,总数:{2}",
		emptyMsg : "没有查询到相关的记录"
	});
   	

    
    
    
    
    
    
    
    // 创建表格布局
	var grid = new Ext.grid.GridPanel({
				//title : "情况",
				id:'gridPanel',
				loadMask : {
					msg : "正在加载请稍候...."
				},
				store : check_store,
				cm : columnModel,
				 listeners: {
		            render: {
		                fn: function(){
		                    check_store.load({
		                        params: {
		                            start: 0,
		                            limit: 50
		                        }
		                    });
		                    alert(check_store.getCount());
		                }
		            }
		        },
				bbar : paginationNav
			});
	

    
    
    setTimeout(function(){
     check_store.load({
		                        params: {
		                            start: 0,
		                            limit: 50
		                        }
		                    });
		                    alert(check_store.getCount());
    
    }, 300 );
    */
    
    //////////////////////////////////3333333333
    
    
    
     Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

    // sample static data for the store
    var myData = [
        ['西山1号公用配电房#2变','/大良变电站/733 西山线//G3011-1171 西山1号公用配电房/02 西山1号公用配电房','广州科琳电气设备有限公司',4271.72,'SCB10-500/10','公变','11'],
        ['西山2号公用配电房#2变','/大良变电站/733 西山线//G3011-1172 西山2号公用配电房/02 西山2号公用配电房','明珠电气有限公司',2229.01,'SGB10-1250/10','专变','9'],
        ['东城花园四期配电房#2变','/大良变电站/733 西山线//G3011-1169 东城花园四期配电房/02 东城花园四期配电房2号变','广东海鸿变压器有限公司',6583.81,'SGB10-315/10','专变','13'],
        ['东城花园四期配电房#1变','/大良变电站/733 西山线//G3011-1169 东城花园四期配电房/01 东城花园四期配电房1号变','广州高压电器厂第一分厂',4652.55,'SGB10-1250/10','公变','23'],
        ['东城花园会所配电房#2变','/大良变电站/733 西山线//G3011-1167 东城花园会所配电房/02 东城花园会所配电房2号变','广州科琳电气设备有限公司',4764.13,'SCB10-500/10','专变','24'],
        ['东城花园配电房#3变','/大良变电站/733 西山线//G3011-1168 东城花园配电房/03 东城花园3号配变','明珠电气有限公司',3631.61,'SGB10-315/10','公变','16'],
        ['东城花园配电房#1变','/大良变电站/733 西山线//G3011-1168 东城花园配电房/01 东城花园1号配变','顺德特种变压器厂(顺特电气有限公司)',6375.43,'SGB10-1250/10','专变','25'],
        ['西山1号公用配电房#1变','/大良变电站/733 西山线//G3011-1171 西山1号公用配电房/01 西山1号公用配电房','明珠电气有限公司',9867.27,'S7-1600/10','公变','23'],
        ['西山2号公用配电房#1变','/大良变电站/733 西山线//G3011-1172 西山2号公用配电房/01 西山2号公用配电房','广东海鸿变压器有限公司',4249.37,'SGB10-315/10','专变','7'],
        ['西山3号公用配电房#1变','/大良变电站/733 西山线//G3011-1173 西山3号公用配电房/01 西山3号公用配电房','广州高压电器厂第一分厂',4240.48,'SCB10-500/10','专变','19'],
        ['东城花园会所配电房#1变','/大良变电站/733 西山线//G3011-1167 东城花园会所配电房/01 东城花园会所配电房1号变','顺德特种变压器厂(顺特电气有限公司)',5268.1,'SGB10-800/10','公变','17'],
        ['东城花园四期配电房#3变','/大良变电站/733 西山线//G3011-1169 东城花园四期配电房/03 东城花园四期配电房3号变','广州科琳电气设备有限公司',2134.14,'SGB10-1250/10','专变','15'],
        ['兴华楼配电房#1变','/鸡洲变电站/731 东康2线|/大良变电站/733 西山线//G3013-3166 兴华楼配电房/01 #1 变压器','珠海南方华力通特种变压器有限公司',4530.27,'SGB10-315/10','专变','25'],
        ['东城花园配电房#2变','/大良变电站/733 西山线//G3011-1168 东城花园配电房/02 东城花园2号配变','顺德特种变压器厂(顺特电气有限公司)',4436.53,'SCB10-500/10','专变','28'],
        ['大象山2号变','/红岗变电站/701 古鉴线/大象山2号公用台变/G3012-2027T 大象山2号变','广东海鸿变压器有限公司',5638.77,'SGB10-800/10','公变','8'],
        ['大象山','/红岗变电站/701 古鉴线/大象山公用台变/变压器','广州高压电器厂第一分厂',8919.88,'SGB10-315/10','专变','15'],
        ['古鉴工业#1配变','/红岗变电站/701 古鉴线//古鉴工业公用配电站/01 #1 变压器','广州科琳电气设备有限公司',8681.41,'SGB10-800/10','专变','17'],
        ['古鉴成丰配变','/红岗变电站/701 古鉴线/古鉴成丰公用台变/G3012-2041T 古鉴成丰配变','珠海南方华力通特种变压器有限公司',6664.72,'SGB10-800/10','公变','12'],
        ['獭岗#2配变','/红岗变电站/701 古鉴线//獭岗公用配电站/02 02变压器 ','顺德特种变压器厂(顺特电气有限公司)',7645.73,'SGB10-1250/10','公变','13'],
        ['古鉴村口','/红岗变电站/701 古鉴线/古鉴村口公用台变/01 变压器','广东海鸿变压器有限公司',4536.76,'SCB10-500/10','专变','15'],
        ['古鉴拆迁区#1配变','/红岗变电站/701 古鉴线//古鉴拆迁区公用配电站/01 #1 变压器','珠海南方华力通特种变压器有限公司',6540.96,'SGB10-800/10','专变','23'],
        ['亚丰石化#1配变','/红岗变电站/701 古鉴线//亚丰石化公用配电站/01 亚丰石化','广州科琳电气设备有限公司',6725.84,'S7-1600/10','公变','17'],
        ['獭岗#1配变','/红岗变电站/701 古鉴线//獭岗公用配电站/01 变压器','明珠电气有限公司',8727.96,'SGB10-315/10','专变','19'],
        ['古鉴上龙公用台变','/红岗变电站/701 古鉴线/古鉴上龙公用台变/G3012-2067T 古鉴上龙公用台变','广东海鸿变压器有限公司',5445.07,'SCB10-500/10','公变','22'],
        ['古鉴中路#1配变 ','/红岗变电站/701 古鉴线//古鉴中路公用配电站/01 古鉴中路配电房 ','明珠电气有限公司',6534.64,'S7-1600/10','专变','13'],
        ['象岗工业','/红岗变电站/702 沙田线/G3012-2035T 象岗工业/变压器','广州科琳电气设备有限公司',5761.91,'SGB10-315/10','公变','27'],
        ['沙田工业配电房#1配变','/红岗变电站/702 沙田线//G3012-2023 沙田工业配电房/01 沙田工业配电房','广州高压电器厂第一分厂',7663.26,'SGB10-315/10','专变','14'],
        ['红岗居委会#2配变','/红岗变电站/702 沙田线//G3012-2022 红岗居委会/02 #2 变压器','顺德特种变压器厂(顺特电气有限公司)',5635.57,'SCB10-500/10','专变','21'],            
        ['利德楼','/红岗变电站/702 沙田线/G3012-2039T 利德楼/01 变压器','珠海南方华力通特种变压器有限公司',6745.45,'SGB10-1250/10','公变','11']
    ];

    /**
     * Custom function used for column renderer
     * @param {Object} val
     */
    function change(val){
         if(val=='专变'){
        	 return '<span style="color:green;">' + val + '</span>';
         }else{
        	 return '<span style="color:blue;">' + val + '</span>';
         }
         
    }

    function showDetial(val){
      alert(val);
       //  return '<span style="color:green;">' + val + '</span>';
    }
    
    /**
     * Custom function used for column renderer
     * @param {Object} val
     */
    function pctChange(val){
        if(val > 0){
            return '<span style="color:green;">' + val + '%</span>';
        }else if(val < 0){
            return '<span style="color:red;">' + val + '%</span>';
        }
        return val;
    }
   
    
    

    // create the data store
    var store = new Ext.data.ArrayStore({
        fields: [
           {name: 'dvName'},
           {name: 'azPath'},
           {name: 'company'},
           {name: 'price'},
           {name: 'change'},
           {name: 'pctChange'},
           {name: 'lastChange'}
        ]
    });

    // manually load local data
    store.loadData(myData);
	var paginationNav = new Ext.PagingToolbar({
		pageSize : 20,
		store : store,
		autoWidth : true,
		displayInfo : true,
		nextText : '下一页',
		firstText : '第一页',
		prevText : '上一页',
		lastText : '末页',
		displayMsg : "本页显示第{0}~{1}条,总数:{2}",
		emptyMsg : "没有查询到相关的记录"
	});
    // create the Grid
    var grid = new Ext.grid.GridPanel({
        store: store,
        columns: [
            new Ext.grid.RowNumberer({
										width : 20
									}),
            {id:'dvName',header: '设备运行名称', width: 160,
            renderer : function(value,metaData,record,rowIndex) {
			        return "<a href='toByqThree' >"+value+"</a>";
	        },sortable: true, dataIndex: 'dvName'},
            {header: '安装地址', width: 360, sortable: true, dataIndex: 'azPath' },
            {header: '厂家', width: 160, sortable: true, dataIndex: 'company' },
            {header: '类型', width: 65, sortable: true, renderer: change, dataIndex: 'pctChange',align:'center'},
            {header: '型号', width: 100, sortable: true, renderer: pctChange, dataIndex: 'change',align:'center'},
            {header: '年限', width: 65, sortable: true, dataIndex: 'lastChange',align:'center' },
            {header: '净值', width: 70, sortable: true, dataIndex: 'price',align:'center'}
        ],
        stripeRows: true,
        //bodyStyle: 'border-width:1px;border-color:#E9E9E9;',
        autoExpandColumn: 'dvName',
        height: 350,
        border:false,
        width: 999,
        //title: '',
        //config options for stateful behavior
        stateful: true,
        bbar : paginationNav,
        stateId: 'grid'       
    });

    
// 主布局
	var panel = new Ext.Panel({
				height: 800,
        width: 999,
				 layout: 'fit',
				autoScroll : false,border:false,
				bodyBorder : false,
				items : [grid],
				renderTo : "grid-example"
	});

    
});
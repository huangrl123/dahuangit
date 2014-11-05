/*!
 *报废信息记录
 *author: 杨柯飞
 */
 
Ext.onReady(function(){
Ext.BLANK_IMAGE_URL = "../../js/ext-3.2.1/resources/images/default/s.gif";

    Ext.QuickTips.init();
    

  //////////////////////////////////////////
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
	

    
    
 
    
    //////////////////////////////////3333333333
    
    
    
   /*  Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

    var myData = [
        ['原因不明','3','不知','林华','9/1 12:00am'],
        ['原因不明','21','不知','林华','9/1 12:00am'],
        ['原因不明','11','不知','林华','9/1 12:00am'],
        ['原因不明','52','不知','刘博','9/1 12:00am'],
        ['原因不明','64','不知','刘博','9/1 12:00am']
    ];



    function change(val){
      
         return '<span style="color:green;">' + val + '</span>';
    }

    function showMoney(val){
      
         return '<span style="color:green;">' + val + 'W</span>';
    }
    


    function pctChange(val){
        if(val > 0){
            return '<span style="color:green;">' + val + '%</span>';
        }else if(val < 0){
            return '<span style="color:red;">' + val + '%</span>';
        }
        return val;
    }
   
    
    

    var store = new Ext.data.ArrayStore({
        fields: [
           {name: 'company'},
           {name: 'price', type: 'float'},
           {name: 'change'},
           {name: 'pctChange'},
           {name: 'lastChange', type: 'date', dateFormat: 'n/j h:ia'}
        ]
    });


    store.loadData(myData);

   
    var grid = new Ext.grid.GridPanel({
        store: store,
        columns: [
            {id:'lastChange',header: '报废时间', width: 80,sortable: true, renderer: Ext.util.Format.dateRenderer('Y-m-d'),dataIndex: 'lastChange'},
            {header: '报废原因',width: 581, sortable: true, dataIndex: 'company' },
            {header: '操作人', width: 75, sortable: true, renderer: change, dataIndex: 'pctChange'},
           {header: '剩余价值', width: 75, sortable: true, renderer: showMoney, dataIndex: 'price'},
             {header: '资产变型', width: 75, sortable: true, renderer: pctChange, dataIndex: 'change'}
        ],
        stripeRows: true,bodyStyle: 'border-width:1px;border-color:#E9E9E9;',
        height: 150,
        border:false,
        width: 935,
        stateful: true,
        stateId: 'grid'       
    });*/
    
    
  var filters = new Ext.ux.grid.GridFilters({
     //   encode: encode, // json encode the filter query
     //   local: local,   // defaults to false (remote filtering)
        filters: [{
            type: 'numeric',
            dataIndex: 'pctChange'
        }, {
            type: 'string',
            dataIndex: 'company'/*,
            disabled: true*/
        }, {
            type: 'string',
            dataIndex: 'price'
        }, {
            type: 'string',
            dataIndex: 'price',
        options: ['1', '2', '3', '4'],phpMode: true
        }]
    });    
      
    
    
    
var columnModel = new Ext.grid.ColumnModel([ 
	{id:'lastChange',filterable: true,header: '报废时间', width: 80,sortable: true,dataIndex: 'lastChange',filter: {type: 'numeric'}},
         {header: '报废原因',width: 581, sortable: true, dataIndex: 'company',
         filter: {
                type: 'string'
                // specify disabled to disable the filter menu
                //, disabled: true
            } },
           {header: '操作人', width: 75, sortable: true,  dataIndex: 'pctChange', filterable: true},
           {header: '剩余价值', width: 75, sortable: true,  dataIndex: 'price'},
             {header: '资产变型', width: 75, sortable: true,  dataIndex: 'change'}]);
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
    },{
       name : "size",
       mapping : "size"
    },{
       name : "lastChange",
       mapping : "lastChange"
    },{
       name : "pctChange",
       mapping : "pctChange"
    },{
       name : "change",
       mapping : "change"
    }]);
    var reader = new Ext.data.JsonReader({},record);
    var proxy = new Ext.data.HttpProxy({
        url : "toByqSecendData",
        timeout : 360000000
    });
	var check_store = new Ext.data.Store({
		
    	reader : reader,
    	proxy : proxy
   	});
   	var dateMonth = "2014-01";
   
	var paginationNav = new Ext.PagingToolbar({
		pageSize : 20,
		store : check_store,
		autoWidth : true,
		displayInfo : true,
		nextText : '下一页',
		firstText : '第一页',
		prevText : '上一页',
		lastText : '末页',
		 plugins: [filters],
		displayMsg : "本页显示第{0}~{1}条,总数:{2}",
		emptyMsg : "没有查询到相关的记录"
	});
   	
	// 创建表格布局
	var grid = new Ext.grid.GridPanel({
				//title : "巡视到位情况统计",
				 border: false,
				  plugins: [filters],
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
		                  
		                }
		            }
		        },
				bbar : paginationNav
			});
	
	


 
    // 参数传递

    
// 主布局
	var panel = new Ext.Panel({
				height: 160,
        width: 935,
				 layout: 'fit',
				autoScroll : false, border:false,
				bodyBorder : false,
				items : [grid],
				renderTo : "discard_information_recording"
	});

    
});
<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@page import="global.Constants"%>
<%@page import="util.DateUtils"%>
<%
		String baseUrl = request.getContextPath();
	%>
<html>
<head>
    <title>登陆历史记录</title>
    <style type="text/css">
        html, body {
            font: normal 11px verdana;
        }
        #main-panel td {
            padding:5px;
        }
    </style>
    <script type="text/javascript" src="<%=baseUrl%>/websrc/js/anyChart/anychart.js"></script>
    <script type="text/javascript">
        Ext.onReady(function() {
        	// 全局参数
        	Ext.chart.Chart.CHART_URL = '<%=baseUrl %>/websrc/js/ext-3.3.0/resources/charts.swf';
			var baseParams = {start:0, limit:5, fromDate:"<%=DateUtils.getFirstDayOfMonthAsString(DateUtils.DATE_PATTON_1)%>", toDate:"<%=DateUtils.getLastDayOfMonthAsString(DateUtils.DATE_PATTON_1)%>"};
            var loginInfos;

            var loginInfosFormPanel = new Ext.FormPanel({
            	labelWidth: 70,
		        items: [{
		        	layout:'form',
		            border: false,
		            //bodyStyle: 'padding:5px;background-color:transparent;',
		            bodyStyle: 'background:#fff; padding:15px;',
		            items:[{
			                xtype:'textfield',
			                id: 'loginUserName',
			                name: 'loginUserName',
			                fieldLabel: '登录人',
			                readOnly: true,
			                anchor:'95%'
			           },{
			               xtype:'textfield',
			               id:'loginSumNum',
			               name:'loginSumNum',
			               fieldLabel:'登陆次数',
			               anchor:'95%'
			           },{
			               xtype:'textfield',
			               id:'loginIP',
			               name:'loginIP',
			               fieldLabel:'常用登陆IP',
			               anchor:'95%'
			           },{
			               xtype:'textfield',
			               id:'loginSumTime',
			               name:'loginSumTime',
			               fieldLabel:'登陆时长',
			               anchor:'95%'
			           },{
				           items : [{
				        	   width : 270,
	                           html : '<iframe id="clock" name="clock_info" src="<%=baseUrl %>/websrc/page/system/clock.jsp" width="300"  height=75" frameborder="0" marginwidth="0" marginheight="0" scrolling="no" style="background-color:transparent"></iframe> '
					        }]
				       }]
		        }]
		    });

            Ext.Ajax.request({
                url : '<%=baseUrl %>/loginAction.do?method=queryLoginInfo',
                success : function(response,options){
	               	 loginInfos = Ext.decode(response.responseText);
	               	 loginInfosFormPanel.getForm().setValues(loginInfos);
                }
           });
            
		    var loginHistoryStore = new Ext.data.JsonStore({
		        url: Ext.ANYNOTENEW_URL + '/loginHistory/getLoginHistory.do',
		        root: 'datas',
		        totalProperty: 'results',
		        fields: ['id', 'loginIP','loginOut','loginSumTime','loginTime','loginUserId','loginUserName'],
		        baseParams: baseParams,
		        autoLoad: true
		    });
		    
		    
			var sm = new Ext.grid.CheckboxSelectionModel();
			var loginHistoryGrid = new Ext.grid.GridPanel({
				id: 'loginHistoryGrid',
				border: false,
				columnLines: true,
				stateful: true,
			    autoScroll: 'auto',
			    height: 250,
			    loadMask: true,
		        store: loginHistoryStore,
		        cm: new Ext.grid.ColumnModel({
		            defaults: {
		                width: 120,
		                sortable: true
		            },
			        columns: [
						new Ext.grid.RowNumberer({header:'№'}),
			            {id:'id',header: '登陆历史记录ID', width: 100, sortable: true, dataIndex: 'id', hidden: true},
			            {id:'loginUserName',header: '登录人', width: 70, sortable: true, dataIndex: 'loginUserName', align:'center'},
			            {id:'loginTime',header: '登陆时间', width: 200, sortable: true, dataIndex: 'loginTime', align:'center'},
			            {id:'loginOut',header: '登出时间', width: 200, sortable: true, dataIndex: 'loginOut', align:'center'},
			            {id:'loginSumTime',header: '单次登陆总时间', width: 250, sortable: true, dataIndex: 'loginSumTime', align:'center'},
			            {id:'loginIP',header: '登陆IP', width: 250, sortable: true, dataIndex: 'loginIP', align:'center'}
			        ]
		        }),
		        columnLines: true,
		        bbar: new Ext.PagingToolbar({
					pageSize: 5,
					store: loginHistoryStore,
					displayInfo: true, 		
					displayMsg: Anynote.PAGINGTOOLBAR_DISPLAYMSG,
					emptyMsg: Anynote.PAGINGTOOLBAR_EMPTYMSG,
					doLoad: function(start){
					    var param = {};
						param.start = start;
						param.limit = baseParams.limit;
						this.store.load({params: param});
					}
		        })
		        //listeners: noteListStore.on("load", function(){getSum();})
		    });

			var currentLoginHistoryStore = new Ext.data.JsonStore({
				//url: Ext.ANYNOTENEW_URL + '/loginHistory/getCurrentMonthLoginHistory.do',
				url : '<%=baseUrl %>/loginAction.do?method=getCurrentMonthLoginHistory',
		        root: 'datas',
		        fields: ['loginTime', 'loginSumTime'],
		        autoLoad: true
		    });

		    //本月登陆记录曲线图
		   var currentLoginHistoryPanel =  new Ext.Panel({
		        width:780,
		        height:240,
		        layout:'fit',
		        items: {
		            xtype: 'linechart',
		            store: currentLoginHistoryStore,
		            xField: 'loginTime',
		            yField: 'loginSumTime',
		           　　　 // yAxis: new Ext.chart.NumericAxis({
                    //   title: 'rewr'
                    //}),        
		           　　     extraStyle: {
                       xAxis: {labelRotation: 45 }        
		           　　　  },
		            style: {
	                    color: 0x99BBE8
	                },
					listeners: {
						itemclick: function(o){
							var rec = currentLoginHistoryStore.getAt(o.index);
							Ext.example.msg('Item Selected', 'You chose {0}.', rec.get('name'));
						}
					}
		        }
		    });
		    
            var panel = new Ext.Panel({
                id:'main-panel',
                baseCls:'x-plain',
                renderTo: 'loginHistoryDiv',
                layout:'table',
                autoScroll: 'true',
                height: 570,
                layoutConfig: {columns:3},
                defaults: {frame:true, height: 269},
                items:[{
                    title:'登陆概况',
                    width:288,
                    items : [loginInfosFormPanel]
                },
                //{
                //    title:'登陆时长分布',
                //    width:368
                //},
                {
                    title:'每月登陆情况趋势(分钟/日期)',
                    width:796, //418
                    colspan : 2,
                    items:[currentLoginHistoryPanel]
                },{
                    title:'登陆详细信息',
                    width:1105,
                    colspan:3,
                    items: [loginHistoryGrid]
                }]
            });
        });
    </script>
</head>
<body>
  <div id="loginHistoryDiv" style="height:1000px;background-image:url('<%=baseUrl %>/websrc/image/login/login-bg.jpg');background-size:cover;">
      
  </div>
</body>
</html>

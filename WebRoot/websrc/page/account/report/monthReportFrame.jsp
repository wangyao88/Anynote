<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="global.Constants"%>
<%@page import="util.DateUtils"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>月收支图表</title>
    <script type="text/javascript" src="<%=path%>/websrc/js/highcharts/js/jquery-1.7.2.js"></script>
	<script type="text/javascript" src="<%=basePath%>websrc/js/anyChart/anychart.js"></script>
	<script type="text/javascript">
         Ext.onReady(function() {
               // 工具栏
			    var todoListToolbar = new Ext.Toolbar({
			    	renderTo: 'monthReportToolBarDiv',
			    	items: [
			    	    new Ext.Button({
						    id: 'month-cast-data-button',
							text: '月支出数据列表',
							iconCls: 'folder_export'
						}),
						 new Ext.Button({
						    id: 'month-import-data-button',
							text: '月收入数据列表',
							iconCls: 'pictures'
						})
					]
				});
				
			   // 月支出数据列表按钮
				$("#month-cast-data-button").click(function(){
					    // 全局参数
					    var baseParams = {start:0, limit:"<%=Constants.PAGE_SIZE %>",fromDate:"<%=DateUtils.getFirstDayOfMonthAsString(DateUtils.DATE_PATTON_1)%>", toDate:"<%=DateUtils.getLastDayOfMonthAsString(DateUtils.DATE_PATTON_1)%>"};
		                // 账目数据源
					    var accountListStore = new Ext.data.JsonStore({
					        url: '<%=path%>/accountAction.do?method=queryMonthCastReportData',
					        root: 'datas',
					        totalProperty: 'results',
					        fields: ['accountId', 'accountBookId', 'accountBookName', 'accountType', 'categoryName', 'accountDateStr', 'money', 'remark', 'updateDateStr'],
					        baseParams: baseParams,
					        autoLoad: true
					    });
					    
						// 账目数据表格
						var accountListGrid = new Ext.grid.GridPanel({
							id: 'accountListGrid',
							//renderTo: 'accountListGridDiv',
							border: false,
							columnLines: true,
							stateful: true,
						    autoScroll: 'auto',
						    height: 510,
					        store: accountListStore,
					        loadMask: true,
					        cm: new Ext.grid.ColumnModel({
					            defaults: {
					                width: 120,
					                sortable: true
					            },
						        columns: [
									new Ext.grid.RowNumberer({header:'№'}),
						            {id:'accountBookId',header: '账本ID', width: 100, sortable: true, dataIndex: 'accountBookId', hidden: true},
						            {id:'accountBookName',header: '账本', width: 150, sortable: true, dataIndex: 'accountBookName'},
						            {id:'accountId',header: '账目ID', width: 100, sortable: true, dataIndex: 'accountId', hidden: true},
						            {id:'accountType',header: '账目类型', width: 100, sortable: true, dataIndex: 'accountType'},
						            {id:'categoryName',header: '收支项目', width: 150, sortable: true, dataIndex: 'categoryName'},
						            {id:'money',header: '金额', width: 100, sortable: true, dataIndex: 'money', align: 'right', renderer: Ext.util.Format.numberRenderer('0,000.00')},
						            {id:'accountDateStr',header: '账目日期', width: 100, sortable: true, dataIndex: 'accountDateStr'},
						            {id:'remark',header: '备注', width: 320, sortable: true, dataIndex: 'remark'}
						        ]
					        }),
					        columnLines: true,
					        bbar: new Ext.PagingToolbar({
								pageSize: <%=Constants.PAGE_SIZE %>,
								store: accountListStore,
								displayInfo: true,
								displayMsg: Anynote.PAGINGTOOLBAR_DISPLAYMSG,
								emptyMsg: Anynote.PAGINGTOOLBAR_EMPTYMSG,
								doLoad: function(start){
									//var form = searchAccountFormPanel.getForm();
									//var param = form.getValues();
									var param = {};
									param.start = start;
									param.limit = baseParams.limit;
									this.store.load({params: param});
								}
					        })
					        //listeners: accountListStore.on("load", function(){getSum();})
					    });
			
						// 设置Grid高度和宽度
						Anynote.resizeGridTo("accountListGrid", 0, 169);
						var accountListWin =   new Ext.Window({
					            title: '月支出数据列表',
								width: 1000,
								height: 520,
								modal: true,
								maximizable: false,
								resizable: true,
								layout:'fit',
								bodyStyle: 'background-color:#CFC0FF;',
								items:[accountListGrid]
					    });
					    accountListWin.show();
				});
				
				// 月支出数据列表按钮
				$("#month-import-data-button").click(function(){
					    // 全局参数
					    var baseParams = {start:0, limit:"<%=Constants.PAGE_SIZE %>",fromDate:"<%=DateUtils.getFirstDayOfMonthAsString(DateUtils.DATE_PATTON_1)%>", toDate:"<%=DateUtils.getLastDayOfMonthAsString(DateUtils.DATE_PATTON_1)%>"};
		                // 账目数据源
					    var accountListStore = new Ext.data.JsonStore({
					        url: '<%=path%>/accountAction.do?method=queryMonthImportReportData',
					        root: 'datas',
					        totalProperty: 'results',
					        fields: ['accountId', 'accountBookId', 'accountBookName', 'accountType', 'categoryName', 'accountDateStr', 'money', 'remark', 'updateDateStr'],
					        baseParams: baseParams,
					        autoLoad: true
					    });
					    
						// 账目数据表格
						var accountListGrid = new Ext.grid.GridPanel({
							id: 'accountListGrid',
							//renderTo: 'accountListGridDiv',
							border: false,
							columnLines: true,
							stateful: true,
						    autoScroll: 'auto',
						    height: 510,
					        store: accountListStore,
					        loadMask: true,
					        cm: new Ext.grid.ColumnModel({
					            defaults: {
					                width: 120,
					                sortable: true
					            },
						        columns: [
									new Ext.grid.RowNumberer({header:'№'}),
						            {id:'accountBookId',header: '账本ID', width: 100, sortable: true, dataIndex: 'accountBookId', hidden: true},
						            {id:'accountBookName',header: '账本', width: 150, sortable: true, dataIndex: 'accountBookName'},
						            {id:'accountId',header: '账目ID', width: 100, sortable: true, dataIndex: 'accountId', hidden: true},
						            {id:'accountType',header: '账目类型', width: 100, sortable: true, dataIndex: 'accountType'},
						            {id:'categoryName',header: '收支项目', width: 150, sortable: true, dataIndex: 'categoryName'},
						            {id:'money',header: '金额', width: 100, sortable: true, dataIndex: 'money', align: 'right', renderer: Ext.util.Format.numberRenderer('0,000.00')},
						            {id:'accountDateStr',header: '账目日期', width: 100, sortable: true, dataIndex: 'accountDateStr'},
						            {id:'remark',header: '备注', width: 320, sortable: true, dataIndex: 'remark'}
						        ]
					        }),
					        columnLines: true,
					        bbar: new Ext.PagingToolbar({
								pageSize: <%=Constants.PAGE_SIZE %>,
								store: accountListStore,
								displayInfo: true,
								displayMsg: Anynote.PAGINGTOOLBAR_DISPLAYMSG,
								emptyMsg: Anynote.PAGINGTOOLBAR_EMPTYMSG,
								doLoad: function(start){
									//var form = searchAccountFormPanel.getForm();
									//var param = form.getValues();
									var param = {};
									param.start = start;
									param.limit = baseParams.limit;
									this.store.load({params: param});
								}
					        })
					        //listeners: accountListStore.on("load", function(){getSum();})
					    });
			
						// 设置Grid高度和宽度
						Anynote.resizeGridTo("accountListGrid", 0, 169);
						var accountListWin =   new Ext.Window({
					            title: '月支出数据列表',
								width: 1000,
								height: 520,
								modal: true,
								maximizable: false,
								resizable: true,
								layout:'fit',
								bodyStyle: 'background-color:#CFC0FF;',
								items:[accountListGrid]
					    });
					    accountListWin.show();
				});
			
               var panel = new Ext.Panel({
		                id:'report-main-panel',
		                baseCls:'x-plain',
		                renderTo: 'monthReportDiv',
		                layout:'table',
		                anchor:'100%',
		                bbar: todoListToolbar,
		                layoutConfig: {columns:2},
		                defaults: {frame:true, width:558, height: 560},
		                items:[{
		                    //title:'月收支折线图',
		                    colspan: 2,
		                    width:1120,
		                    html: '<iframe src="<%=path%>/websrc/page/account/report/monthReportLine.jsp" width="1150px" height="545px;" scrolling="no" style="margin:0px;" frameborder="0"></iframe>'
		                }]
		            });
         
         });
	</script>
  </head>
  
  <body>
       <div id="monthReportToolBarDiv"></div>
       <div id="monthReportDiv" style="width:100%;background-image:url('<%=path%>/websrc/image/login/login-bg.jpg');background-size:cover;">
       </div>
  </body>
</html>

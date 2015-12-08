<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@page import="global.Constants"%>
<%@page import="util.DateUtils"%>
<%
		String baseUrl = request.getContextPath();
	%>
<html>
<head>
    <title>Table Layout</title>
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
            Ext.chart.Chart.CHART_URL = '<%=baseUrl %>/websrc/js/ext-3.3.0/resources/charts.swf';  
            // 全局参数
			var baseParams = {start:0, limit:5, fromDate:"<%=DateUtils.getFirstDayOfMonthAsString(DateUtils.DATE_PATTON_1)%>", toDate:"<%=DateUtils.getLastDayOfMonthAsString(DateUtils.DATE_PATTON_1)%>"};
            //最新笔记
		    var noteListStore = new Ext.data.JsonStore({
		        url: '<%=baseUrl %>/noteAction.do?method=query',
		        root: 'datas',
		        totalProperty: 'results',
		        fields: ['noteId', 'title'],
		        baseParams: baseParams,
		        autoLoad: true
		    });
		    
		    
			// 账目数据表格
			var sm = new Ext.grid.CheckboxSelectionModel();
			var noteListGrid = new Ext.grid.GridPanel({
				id: 'noteListGrid',
				//renderTo: 'noteListGridDiv',
				border: false,
				columnLines: true,
				stateful: true,
			    autoScroll: 'auto',
			    height: 250,
			    loadMask: true,
		        store: noteListStore,
		        cm: new Ext.grid.ColumnModel({
		            defaults: {
		                width: 120,
		                sortable: true
		            },
			        columns: [
						new Ext.grid.RowNumberer({header:'№'}),
			            {id:'noteId',header: '笔记ID', width: 100, sortable: true, dataIndex: 'noteId', hidden: true},
			            {id:'title',header: '标题', width: 270, sortable: true, dataIndex: 'title', align:'center'}
			        ]
		        }),
		        columnLines: true,
		        bbar: new Ext.PagingToolbar({
					pageSize: 5,
					store: noteListStore,
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
		    
			// 行双击事件
			noteListGrid.on("rowdblclick", function(){
				showNoteDetailWin();
			});
			
			function showNoteDetailWin(){
						var records = noteListGrid.getSelectionModel().getSelections();
						var noteId = records[0].get("noteId");
						// 发送请求
						Anynote.ajaxRequest({
							baseUrl: '<%=baseUrl %>',
							params: {noteId:noteId},
							action: '/noteAction.do?method=getNote&noteId='+noteId,
							callback: function(jsonResult){
					            var content = jsonResult.content;
					            var contentHtml = '<marquee height="470px" direction="up" onmouseover="this.stop();" onmouseout="this.start();">'+content+'</marquee>';
								var noteDetailPanel = new Ext.Panel({
								    //baseCls:'x-plain',
								    height:490,
								    html:contentHtml
								});
								noteDetailWindow = new Ext.Window({
									title: '笔记明细',
									width: 1000,
									height: 500,
									modal: true,
									items:[noteDetailPanel]
								});
								noteDetailWindow.show();
							}
						});
			}
			
			//本月待办已办柱状图
			var columnStore = new Ext.data.JsonStore({
		        url: '<%=baseUrl %>/todoAction.do?method=todoMonth',
		        root: 'datas',
		        totalProperty: 'results',
		        fields: ['delflag', 'count'],
		        autoLoad: true
		    });
		    
		    var columnPanel = new Ext.Panel({
		        width: 360,
		        height: 260,
		        items: {
		            xtype: 'columnchart',
		            store: columnStore,
		            yField: 'count',
			       // url: '<%=baseUrl%>/websrc/js/ext-3.3.0/ux/swf/Column2D.swf',
		            xField: 'delflag',
		            xAxis: new Ext.chart.CategoryAxis({
		                title: '类别'
		            }),
		            yAxis: new Ext.chart.NumericAxis({
		               
		            })
		        }
		    });
		    
		    //待办已办比例图
			var pieStore = new Ext.data.JsonStore({
		        url: '<%=baseUrl %>/todoAction.do?method=todoYear',
		        root: 'datas',
		        totalProperty: 'results',
		        fields: ['month', 'count'],
		        autoLoad: true
		    });
		    
		    var piePanel = new Ext.Panel({
		        width: 360,
		        height: 240,
		        items: {
			            store: pieStore,
			            xtype: 'piechart',
			            dataField: 'count',
			            categoryField: 'month',
			            extraStyle:{
			                legend:{
			                    display: 'bottom',
			                    padding: 5,
			                    font:{
			                        family: 'Tahoma',
			                        size: 13
			                    }
			                }
			            }
			        }
		    });
		    
		    //最新筆記明細滾動內容
			var newNoteContentPanel = new Ext.Panel({
			    height:410,
			    html : '<iframe src="<%=baseUrl %>/websrc/page/home/newNoteContent.jsp" width="1090px" height="380px;" style="margin:0px;" frameborder="0"></iframe>'
			});
        
            var panel = new Ext.Panel({
                id:'main-panel',
                baseCls:'x-plain',
                renderTo: 'homeDiv',
                layout:'table',
                autoScroll: 'true',
                height: 570,
                layoutConfig: {columns:3},
                defaults: {frame:true, width:358, height: 269},
                items:[{
                    title:'图片新闻',
                    width:388,
                    html: '<iframe src="<%=baseUrl %>/websrc/page/home/pictureNews.jsp" width="366px" height="260px;" style="margin:0px;" frameborder="0"></iframe>'
                },{
                    title:'最新笔记',
                    width:328,
                    items: [noteListGrid]
                },{
                    title:'本月待办已办柱状图',
                    //items:[columnPanel]
                    html: '<iframe src="<%=baseUrl %>/websrc/page/home/todoColumn.jsp" width="345px" height="225px;" scrolling="no" style="margin:0px;" frameborder="0"></iframe>'
                },{
                    title:'日支出滚动图',
                    width:726,
                    colspan:2,
                    html: '<iframe src="<%=baseUrl %>/websrc/page/home/runningDayCastReport.jsp" width="715px" height="260px;" style="margin:0px;" frameborder="0"></iframe>'
                },{
                    title:'各月已办比例图',
                    //items:[piePanel]
                    html: '<iframe src="<%=baseUrl %>/websrc/page/home/todoPie.jsp" width="345px" height="225px;" scrolling="no" style="margin:0px;" frameborder="0"></iframe>'
                },{
                    title:'年收支仪表盘',
                    height:320,
                    width:395,
                    html: '<iframe src="<%=baseUrl %>/websrc/page/account/report/yearRateGauge.jsp" width="375px" height="275px;" scrolling="no" style="margin:0px;" frameborder="0"></iframe>'
                },{
                    title:'笔记明细滚动',
                    colspan:2,
                    width:700,
                    height:320,
                    items:[newNoteContentPanel]
                }]
            });
        });
    </script>
</head>
<body>
  <div id="homeDiv" style="height:1000px;background-image:url('<%=baseUrl %>/websrc/image/login/login-bg.jpg');background-size:cover;">
      
  </div>
</body>
</html>

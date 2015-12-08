<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="global.Constants"%>
<%@page import="util.DateUtils"%>
<%@page import="global.security.SessionUtils"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String userId = SessionUtils.getCurrentUserId();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>节假日列表</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<style type="text/css">
	    .late-x-grid-cell-inner {
		    background: #FF1B02;
		}
		.early-x-grid-cell-inner {
		    background: #FF02E1;
		}
		.success-x-grid-cell-inner {
		    background: #09F919;
		}
		.week-x-grid-cell-inner {
		    background: #96F747;
		}
		.leave-x-grid-cell-inner {
		    background: #96F747;
		}
		.absence-x-grid-cell-inner {
		    background: #F9F109;
		}
	</style>
	<script type="text/javascript" src="<%=path%>/websrc/js/highcharts/js/jquery-1.7.2.js"></script>
	<script type="text/javascript">
		Ext.onReady(function() {
			Ext.chart.Chart.CHART_URL = '<%=path%>/websrc/js/ext-3.3.0/resources/charts.swf';
			var baseParams = {start:0, limit:11, fromDate:"<%=DateUtils.getFirstDayOfMonthAsString(DateUtils.DATE_PATTON_1)%>", toDate:"<%=DateUtils.getLastDayOfMonthAsString(DateUtils.DATE_PATTON_1)%>"};
			var recordBaseParams = {start:0, limit:20, fromDate:"<%=DateUtils.getFirstDayOfMonthAsString(DateUtils.DATE_PATTON_1)%>", toDate:"<%=DateUtils.getLastDayOfMonthAsString(DateUtils.DATE_PATTON_1)%>"};
			var height = Ext.get("attendenceTimeDiv").getHeight();
			var width = Ext.get("attendenceTimeDiv").getWidth();
			var atId;

			var attendenceTimeDetailFormPanel = new Ext.FormPanel({
            	labelWidth: 100,
            	//title : '上班时间',
		        items: [{
		        	layout:'form',
		            border: false,
		            bodyStyle: 'background:#fff; padding:15px;',
		            defaults: {width:100},
		            items:[{
			                xtype:'textfield',
			                id: 'amOnTime',
			                name: 'amOnTime',
			                fieldLabel: '上午上班时间',
			                readOnly: true,
			                anchor:'95%'
			           },{
			               xtype:'textfield',
			               id:'amOffTime',
			               name:'amOffTime',
			               fieldLabel:'上午下班时间',
			               anchor:'95%'
			           },{
			               xtype:'textfield',
			               id:'pmOnTime',
			               name:'pmOnTime',
			               fieldLabel:'下午上班时间',
			               anchor:'95%'
			           },{
			               xtype:'textfield',
			               id:'pmOffTime',
			               name:'pmOffTime',
			               fieldLabel:'下午下班时间',
			               anchor:'95%'
			           },{
			               xtype:'hidden',
			               id:'atId',
			               name:'atId'
			           }]
		        }]
		    });

            Ext.Ajax.request({
                url : Ext.ANYNOTENEW_URL + 'attendenceTime/getStanderAttendenceTimeInfo.do',
                success : function(response,options){
                	 var attendenceTimeInfo = Ext.decode(response.responseText);
                	 attendenceTimeDetailFormPanel.getForm().setValues(attendenceTimeInfo);
                	 atId = Ext.getCmp('atId').getValue();
                }
           });

            
            //节假日编辑窗口
            var editHolidyFormPanel = new Ext.FormPanel({
				id : 'editHolidyFormPanel',
		        labelWidth: 60,
		        border: false,
		        bodyStyle: 'background-color:transparent;',
		        items: [{
		            layout:'form',
		            border: false,
		            bodyStyle: 'padding:5px;background-color:transparent;',
		            items:[{
                            xtype:'textfield',
                            name:'hName',
                            fieldLabel:'假日名称',
                            anchor:'100%',
			                allowBlank:false 
			            },{// 节假日开始时间
			                xtype:'datefield',
			                name: 'hStart',
			                fieldLabel: '开始时间',
			                format: 'Y-m-d H:i:s',
			                anchor:'100%',
			                allowBlank:false 
			           },{// 节假日结束时间
			                xtype:'datefield',
			                name: 'hEnd',
			                fieldLabel: '结束时间',
			                format: 'Y-m-d H:i:s',
			                anchor:'100%',
			                allowBlank:false 
			           },{
                            xtype:'hidden',
                            id:'hId',
                            name:'hId'
				      }]
		        }]
		    });

		    function saveHolidy(){
		    	var holidyDatas = editHolidyFormPanel.getForm().getValues();
		    	var holidy = Ext.encode(holidyDatas);
		    	Ext.Msg.alert('',holidy);
		    	var waiting = Ext.Msg.wait('正在处理，请稍等...','','');
		    	Ext.Ajax.request({
                    url : Ext.ANYNOTENEW_URL + 'attendenceTime/saveHolidyInfo.do',
                    params:{
                              holidy: Ext.encode(holidyDatas),
                              atId : atId
                    },
                    success : function(response,options){
		    		     waiting.hide();
                         var result = response.responseText;
                         if(result == 'yes'){
                        	 holidyWindow.hide();
                        	 holidyGrid.getStore().reload();
                        	 Ext.Msg.alert('提示','保存成功');
                         }else{
                        	 Ext.Msg.alert('提示','保存失败');
                         }
                    }
			    });
			}

		    var holidyWindow = new Ext.Window({
                 title:'节假日操作',
                 width:300,
                 height:160,
                 modal:true,
                 maximizable:false,
                 resizable:false,
                 closable:true,
                 closeAction:'hide',
                 buttonAlign:'center',
             	 buttons : [{
	                               text : '保存',
	                               handler : function(){
	                                    saveHolidy();
	                               }
                 	         },{
	                 	        	text : '取消',
	                                handler : function(){
		                 	        	editHolidyFormPanel.getForm().reset();
		   				    	        holidyWindow.hide();
	                                } 
                           }],
                 layout:'fit',
                 plain:true,
                 items:[editHolidyFormPanel]
			});

		    var holidyStore = new Ext.data.JsonStore({
		        url: Ext.ANYNOTENEW_URL + 'attendenceTime/getStanderAttendenceTimeHolidyInfo.do?atId='+atId,
		        root: 'datas',
		        totalProperty: 'results',
		        fields: ['hId', 'hName','hStart','hEnd'],
		        baseParams: baseParams,
		        autoLoad: true
		    });

         // 工具栏
		    var holidyGridToolbar = new Ext.Toolbar({
		    	id: 'holidyGridToolbar',
		    	items: [
				    new Ext.Button({
					    id: 'holidy-add-button',
						text: '添加',
						iconCls: 'note_add',
						handler : function(){
				    	     editHolidyFormPanel.getForm().reset();
				    	     editHolidyFormPanel.getForm().findField('hId').setValue('');
				    	     holidyWindow.show();
						}
					}),
					new Ext.Button({
					    id: 'holidy-edit-button',
					    text: '修改',
						iconCls: 'note_edit',
						handler : function(){
							 var records = holidyGrid.getSelectionModel().getSelections();
							 if(records.length < 1 || records.length > 1){
                                 Ext.Msg.alert('提示','一次只能修改一条记录');
                                 return;
						     }
							 updateHolidy();
						}
					}),
					new Ext.Button({
					    id: 'holidy-delete-button',
					    text: '删除',
						iconCls: 'note_delete',
						handler : function(){
							var records = holidyGrid.getSelectionModel().getSelections();
                            var length = records.length;
                            if(length < 1){
                                Ext.Msg.alert('提示','请至少选择一条数据');
                                return;
                            }
                            Ext.Msg.confirm('警告','确定要删除吗?',function(btn){
                                 if(btn == 'yes'){
                                     var waitting = Ext.Msg.wait('删除中,请稍等......','','');
                                     var hIds = "";
                                	 for(var i=0;i<length;i++){
                                         hIds = hIds + records[i].get('hId') + ',';
         						     }
         						     Ext.Ajax.request({
                                         url : Ext.ANYNOTENEW_URL + 'attendenceTime/deleteHolidyInfoByIds.do',
                                         params : {
         						    	     hIds : hIds
                                         },
                                         success : function(response,options){
                                        	 waitting.hide();
                                       	     Ext.Msg.alert('提示',response.responseText);
                                       	     holidyGrid.getStore().reload();
                                         }
             						 });
                                 }
                            });
						}
					}),
					'-',
		            new Ext.ux.form.SearchField({
		                store: holidyStore,
		                width: 150,
		                paramName: 'hName',
		                emptyText: '输入节假日名称...'
		            })
				]
			});
			var holidySM = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
			var holidyGrid = new Ext.grid.GridPanel({
				id: 'holidyGrid',
				//title : '节假日',
				height:height*2/3 - 35,
				border: false,
				columnLines: true,
				stateful: true,
			    autoScroll: 'auto',
			    loadMask: true,
		        store: holidyStore,
		        tbar : holidyGridToolbar,
		        cm: new Ext.grid.ColumnModel({
		            defaults: {
		                width: 120,
		                sortable: true
		            },
			        columns: [
						holidySM,
						new Ext.grid.RowNumberer({header:'№'}),
			            {id:'hId',header: '节假日ID',sortable: true, dataIndex: 'hId', hidden: true},
			            {id:'hName',header: '节假日名称', width: 93, sortable: true, dataIndex: 'hName', align:'center'},
			            {id:'hStart',header: '开始时间', width: 110, sortable: true, dataIndex: 'hStart', align:'center'},
			            {id:'hEnd',header: '结束时间', width: 110, sortable: true, dataIndex: 'hEnd', align:'center'}
			        ]
		        }),
		        sm:holidySM,
		        columnLines: true,
		        bbar: new Ext.PagingToolbar({
					pageSize: 11,
					store: holidyStore,
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

            function updateHolidy(){
            	var records = holidyGrid.getSelectionModel().getSelections();
				var hId = records[0].get("hId");
				Ext.Ajax.request({
                     url : Ext.ANYNOTENEW_URL + 'attendenceTime/getStanderAttendenceTimeHolidyInfoByhId.do',
                     params : {
                         hId : hId
                     },
                     success : function(response,options){
                           var holidyInfo = Ext.decode(response.responseText);
                           editHolidyFormPanel.getForm().setValues(holidyInfo);
                           holidyWindow.show();
                     }
                     
			    });
            }
			
			holidyGrid.on("rowdblclick", function(){
				updateHolidy();
			});

           //考勤记录开始
           var attendenceTimeRecordStore = new Ext.data.JsonStore({
		        url: Ext.ANYNOTENEW_URL + 'attendenceTime/getAttendenceTimeRecords.do?userId='+'<%=userId%>',
		        root: 'datas',
		        totalProperty: 'results',
		        fields: ['atrId', 'amOnTime','amOffTime','pmOnTime','pmOffTime','userId','atrRemark','atrDate','atrAmOnTimeType','atrAmOffTimeType','atrPmOnTimeType','atrPmOffTimeType'],
		        baseParams: recordBaseParams,
		        autoLoad: true
		    });

         // 工具栏
		    var attendenceTimeRecordGridToolbar = new Ext.Toolbar({
		    	id: 'attendenceTimeRecordGridToolbar',
		    	items: [
					new Ext.Button({
					    id: 'attendenceTimeRecord-delete-button',
					    text: '本月考勤情况',
						iconCls: 'note_delete',
						handler : function(){
						    
						}
					}),
					'-',
					'<span class="late-x-grid-cell-inner">迟到</span>',
					'-',
					'<span class="early-x-grid-cell-inner">早退</span>',
					'-',
					'<span class="success-x-grid-cell-inner">正常</span>',
					'-',
					'<span class="week-x-grid-cell-inner">周末</span>',
					'-',
					'<span class="leave-x-grid-cell-inner">请假</span>',
					'-',
					'<span class="absence-x-grid-cell-inner">缺勤</span>',
					'-'
				]
			});
			var attendenceTimeRecordSM = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
			var attendenceTimeRecordGrid = new Ext.grid.GridPanel({
				id: 'attendenceTimeRecordGrid',
				//title : '节假日',
				height:height - 35,
				border: false,
				columnLines: true,
				stateful: true,
			    autoScroll: 'auto',
			    loadMask: true,
		        store: attendenceTimeRecordStore,
		        tbar : attendenceTimeRecordGridToolbar,
		        cm: new Ext.grid.ColumnModel({
		            defaults: {
		                width: 120,
		                sortable: true
		            },
			        columns: [
						attendenceTimeRecordSM,
						new Ext.grid.RowNumberer({header:'№'}),
			            {id:'atrId',header: '考勤ID',dataIndex: 'atrId', hidden: true},
			            {id:'atrDate',header: '日期', width: width/9-10, sortable: true, dataIndex: 'atrDate', align:'center'},
			            {id:'amOnTime',header: '上午上班打卡', width: width/9-10, sortable: true, dataIndex: 'amOnTime', align:'center',renderer:rendeAmOnTime},
			            {id:'amOffTime',header: '上午下班打卡', width: width/9-10, sortable: true, dataIndex: 'amOffTime', align:'center',renderer:rendeAmOffTime},
			            {id:'pmOnTime',header: '下午上班打卡', width: width/9-10, sortable: true, dataIndex: 'pmOnTime', align:'center',renderer:rendePmOnTime},
			            {id:'pmOffTime',header: '下午下班打卡', width: width/9-10, sortable: true, dataIndex: 'pmOffTime', align:'center',renderer:rendePmOffTime},
			            {id:'atrRemark',header: '备注', width: width/7, sortable: true, dataIndex: 'atrRemark', align:'center'},
				        {id:'userId',header: '打卡人',dataIndex: 'userId',hidden:true}
			        ]
		        }),
		        sm:attendenceTimeRecordSM,
		        columnLines: true,
		        bbar: new Ext.PagingToolbar({
					pageSize: recordBaseParams.limit,
					store: attendenceTimeRecordStore,
					displayInfo: true, 		
					displayMsg: Anynote.PAGINGTOOLBAR_DISPLAYMSG,
					emptyMsg: Anynote.PAGINGTOOLBAR_EMPTYMSG,
					doLoad: function(start){
					    var param = {};
						param.start = start;
						param.limit = recordBaseParams.limit;
						this.store.load({params: param});
					}
		        })
		    });

            function rendeAmOnTime(v, m, r){
				 if (r.get('atrAmOnTimeType') == 'late') {
					 return"<div class='late-x-grid-cell-inner'>"+v+"</div>";
			     }else if(r.get('atrAmOnTimeType') == 'early'){
			        return"<div class='early-x-grid-cell-inner'>"+v+"</div>";
			     }else if(r.get('atrAmOnTimeType') == 'success'){
			        return"<div class='success-x-grid-cell-inner'>"+v+"</div>";
			     }else if(r.get('atrAmOnTimeType') == 'week'){
			        return"<div class='week-x-grid-cell-inner'>"+v+"</div>";
			     }else if(r.get('atrAmOnTimeType') == 'leave'){
			    	return"<div class='leave-x-grid-cell-inner'>"+v+"</div>";
				 }else if(r.get('atrAmOnTimeType') == 'absence'){
			    	return"<div class='absence-x-grid-cell-inner'>"+v+"</div>";
				 }
			     return v;
	        }

			function rendeAmOffTime(v, m, r){
				 if (r.get('atrAmOffTimeType') == 'late') {
					 return"<div class='late-x-grid-cell-inner'>"+v+"</div>";
			     }else if(r.get('atrAmOffTimeType') == 'early'){
			        return"<div class='early-x-grid-cell-inner'>"+v+"</div>";
			     }else if(r.get('atrAmOffTimeType') == 'success'){
			        return"<div class='success-x-grid-cell-inner'>"+v+"</div>";
			     }else if(r.get('atrAmOffTimeType') == 'week'){
			        return"<div class='week-x-grid-cell-inner'>"+v+"</div>";
			     }else if(r.get('atrAmOffTimeType') == 'leave'){
			    	return"<div class='leave-x-grid-cell-inner'>"+v+"</div>";
				 }else if(r.get('atrAmOffTimeType') == 'absence'){
			    	return"<div class='absence-x-grid-cell-inner'>"+v+"</div>";
				 }
			     return v;
	        }

	        function rendePmOnTime(v, m, r){
	        	 if (r.get('atrPmOnTimeType') == 'late') {
					 return"<div class='late-x-grid-cell-inner'>"+v+"</div>";
			     }else if(r.get('atrPmOnTimeType') == 'early'){
			        return"<div class='early-x-grid-cell-inner'>"+v+"</div>";
			     }else if(r.get('atrPmOnTimeType') == 'success'){
			        return"<div class='success-x-grid-cell-inner'>"+v+"</div>";
			     }else if(r.get('atrPmOnTimeType') == 'week'){
			        return"<div class='week-x-grid-cell-inner'>"+v+"</div>";
			     }else if(r.get('atrPmOnTimeType') == 'leave'){
			    	return"<div class='leave-x-grid-cell-inner'>"+v+"</div>";
				 }else if(r.get('atrPmOnTimeType') == 'absence'){
			    	return"<div class='absence-x-grid-cell-inner'>"+v+"</div>";
				 }
			     return v;
	        }

	        function rendePmOffTime(v, m, r){
	        	if (r.get('atrPmOffTimeType') == 'late') {
					 return"<div class='late-x-grid-cell-inner'>"+v+"</div>";
			     }else if(r.get('atrPmOffTimeType') == 'early'){
			        return"<div class='early-x-grid-cell-inner'>"+v+"</div>";
			     }else if(r.get('atrPmOffTimeType') == 'success'){
			        return"<div class='success-x-grid-cell-inner'>"+v+"</div>";
			     }else if(r.get('atrPmOffTimeType') == 'week'){
			        return"<div class='week-x-grid-cell-inner'>"+v+"</div>";
			     }else if(r.get('atrPmOffTimeType') == 'leave'){
			    	return"<div class='leave-x-grid-cell-inner'>"+v+"</div>";
				 }else if(r.get('atrPmOffTimeType') == 'absence'){
			    	return"<div class='absence-x-grid-cell-inner'>"+v+"</div>";
				 }
			     return v;
	        }
		    
           //考勤记录结束
           
			var attendenceTimePanel = new Ext.Panel({  
			    id:'attendenceTimePanel',  
			    renderTo:'attendenceTimeDiv',  
			    baseCls:'x-plain',
			    width:width,  
			    height:height,  
			    layout:'table',  
			    autoScroll:true,
			    layoutConfig:{columns:2},
			    defaults:{frame:true},
			    items:[{
				         title:'上班时间',
				         width:width/3,
                         height:height/3,
                         items:[attendenceTimeDetailFormPanel]
				},{
					     title:'考勤表',
                         width:width*2/3,
                         height:height,
                         rowspan:2,
                         items:[attendenceTimeRecordGrid]
			    },{
			    	     title:'节假日',
			    	     width:width/3,
                         height:height*2/3,
	                     items:[holidyGrid]
				}]  
			});  
	    });


	    
	</script>
  </head>
  <body>
    <div id="attendenceTimeDiv" style="height:100%; width:100%;background-image:url('<%=path%>/websrc/image/login/login-bg.jpg');background-size:cover;">
      
    </div>
  </body>
</html>

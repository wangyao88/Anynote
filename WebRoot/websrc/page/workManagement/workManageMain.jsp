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
    
    <title>工作管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="<%=path%>/websrc/js/highcharts/js/jquery-1.7.2.js"></script>
	<script type="text/javascript">
	      Ext.onReady(function(){
	    	  Ext.chart.Chart.CHART_URL = '<%=path%>/websrc/js/ext-3.3.0/resources/charts.swf';
			  var workMainBaseParams = {start:0, limit:23, fromDate:"<%=DateUtils.getFirstDayOfMonthAsString(DateUtils.DATE_PATTON_1)%>", toDate:"<%=DateUtils.getLastDayOfMonthAsString(DateUtils.DATE_PATTON_1)%>"};
			  var height = Ext.get("workManageDiv").getHeight();
			  var width = Ext.get("workManageDiv").getWidth();

			  var workMainImportPanel = new Ext.form.FormPanel({
				     baseCls : 'x-plain',
				     labelWidth : 70,
				     fileUpload : true,
				     defaultType : 'textfield',
				     items : [{
				        xtype : 'textfield',
				        fieldLabel : '上传文件名',
				        name : 'userfile',
				        id : 'userfile',
				        inputType : 'file',
				        blankText : '请选择文件',
				        anchor : '100%' // anchor width by percentage
				     }]
			  });

			  function uploadFile(){
				  var waiting = null;
				  if(workMainImportPanel.getForm().isValid()){   
					  waiting = Ext.Msg.wait('正在上传，请稍等...','','');
				  }else{
                      Ext.Msg.alert('提示','请重新选择文件');
                      return;
			      }
				  workMainImportPanel.getForm().submit({       
			            url:Ext.ANYNOTENEW_URL + 'workManage/addWorkMainByExcel.do?userId='+'<%=userId%>',
			            success: function(form, action){  
			            	workMainImportPanel.getForm().reset();
			            	waiting.hide(); 
			                workMainGrid.getStore().reload();
			                Ext.Msg.alert('提示','上传成功.');   
			                workMainImportWin.hide();  
			                //var result = Ext.encode(action.result);
			                //Ext.Msg.alert('',result);
			             },       
			             failure: function(form, action){       
			                //... action生成的json{msg:上传失败},页面就可以用action.result.msg得到非常之灵活   
			                Ext.Msg.alert('Error', '上传失败');       
			             }   
			      })    
		      }

			  var workMainImportWin = new Ext.Window({
	                 title:'工作日志导入',
	                 width:300,
	                 height:160,
	                 modal:true,
	                 maximizable:false,
	                 resizable:false,
	                 closable:true,
	                 closeAction:'hide',
	                 buttonAlign:'center',
	                 buttons : [{
                             text:'上传',
                             handler : function(){
                                 uploadFile();
                             }
		                 },{
		                	 text:'取消',
                             handler : function(){
		                	     workMainImportPanel.getForm().reset();
		                	     workMainImportWin.hide();     
                             }

				     }],
				     layout:'fit',
	                 plain:true,
	                 items:[workMainImportPanel]
			  });

			  var workMainStore = new Ext.data.JsonStore({
			        url: Ext.ANYNOTENEW_URL + 'workManage/getWorkMainPage.do?userId='+'<%=userId%>',
			        root: 'datas',
			        totalProperty: 'results',
			        fields: ['wmId', 'wmName','wmYear','wmAchievement','wmRemark','userId','wmTitle','wmItemNum'],
			        baseParams: workMainBaseParams,
			        autoLoad: true
			    });

	         // 工具栏
			    var workMainGridToolbar = new Ext.Toolbar({
			    	id: 'workMainGridToolbar',
			    	items: [
					    new Ext.Button({
						    id: 'workMain-add-button',
							text: '添加',
							iconCls: 'note_add',
							handler : function(){
					    	     
							}
						}),
						'-',
						new Ext.Button({
						    id: 'workMain-edit-button',
						    text: '修改',
							iconCls: 'note_edit',
							handler : function(){
								 
							}
						}),
						'-',
						new Ext.Button({
						    id: 'workMain-delete-button',
						    text: '删除',
							iconCls: 'note_delete',
							handler : function(){
								
							}
						}),
						'-',
						new Ext.Button({
						    id: 'workMain-import-button',
						    text: '导入',
							iconCls: 'note_add',
							handler : function(){
							     workMainImportWin.show();
							}
						}),
						'-',
			            new Ext.ux.form.SearchField({
			                store: workMainStore,
			                width: 150,
			                paramName: 'wmTitle',
			                emptyText: '输入工作日志名称...'
			            })
					]
				});
				var workMainSM = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
				var workMainGrid = new Ext.grid.GridPanel({
					id: 'workMainGrid',
					title : '工作日志',
					renderTo:'workManageDiv',
					height:height,
					widht:width,
					border: false,
					columnLines: true,
					stateful: true,
				    autoScroll: 'auto',
				    loadMask: true,
			        store: workMainStore,
			        tbar : workMainGridToolbar,
			        cm: new Ext.grid.ColumnModel({
			            defaults: {
			                width: 120,
			                sortable: true
			            },
				        columns: [
							workMainSM,
							new Ext.grid.RowNumberer({header:'№'}),
				            {id:'wmId',header: '工作日志ID',sortable: true, dataIndex: 'wmId', hidden: true},
				            {id:'wmName',header: '姓名', width: 90, sortable: true, dataIndex: 'wmName', align:'center'},
				            {id:'wmYear',header: '年度', width: 110, sortable: true, dataIndex: 'wmYear', align:'center'},
				            {id:'wmTitle',header: '标题', width: 160, sortable: true, dataIndex: 'wmTitle', align:'center'},
				            {id:'wmItemNum',header: '条目数', width: 30, sortable: true, dataIndex: 'wmItemNum', align:'center'},
				            {id:'wmAchievement',header: '交付物', width: 210, sortable: true, dataIndex: 'wmAchievement', align:'center'},
				            {id:'wmRemark',header: '问题交流', width: width-670, sortable: true, dataIndex: 'wmRemark', align:'center'}
				        ]
			        }),
			        sm:workMainSM,
			        columnLines: true,
			        bbar: new Ext.PagingToolbar({
						pageSize: workMainBaseParams.limit,
						store: workMainStore,
						displayInfo: true, 		
						displayMsg: Anynote.PAGINGTOOLBAR_DISPLAYMSG,
						emptyMsg: Anynote.PAGINGTOOLBAR_EMPTYMSG,
						doLoad: function(start){
						    var param = {};
							param.start = start;
							param.limit = workMainBaseParams.limit;
							this.store.load({params: param});
						}
			        })
			        //listeners: noteListStore.on("load", function(){getSum();})
			    });
			    
				workMainGrid.on("rowdblclick", function(){
					var workMainDetailWin = initWorkMainDetailPanelData();
					workMainDetailWin.show();
				});

				function initWorkMainDetailLeftPanel(){
					var workMainDetailLeftPanel = new Ext.form.FormPanel({
            		    id : 'workMainDetailLeftPanel',
            		    title:'基本信息',
	      		        labelWidth: 60,
	      		        border: false,
	      		        bodyStyle: 'background-color:transparent;',
	      		        items: [{
	      		            layout:'form',
	      		            border: false,
	      		            bodyStyle: 'padding:5px;background-color:transparent;',
	      		            items:[{
	                                  xtype:'textfield',
	                                  name:'wmName',
	                                  fieldLabel:'姓名',
	                                  anchor:'100%',
	      			                  allowBlank:false 
	      			            },{
	                                  xtype:'textfield',
	                                  name:'wmYear',
	                                  fieldLabel:'年度',
	                                  anchor:'100%',
	      			                  allowBlank:false 
	      			            },{
	                                  xtype:'textfield',
	                                  name:'wmTitle',
	                                  fieldLabel:'标题',
	                                  anchor:'100%',
	      			                  allowBlank:false 
	      			            },{
	                                  xtype:'textarea',
	                                  name:'wmAchievement',
	                                  fieldLabel:'交付物',
	                                  anchor:'100%',
	                                  height:130,
	      			                  allowBlank:false 
	      			            },{
	                                  xtype:'textarea',
	                                  name:'wmRemark',
	                                  fieldLabel:'问题交流',
	                                  anchor:'100%',
	                                  height:200,
	      			                  allowBlank:false 
	      			            },{
	                                  xtype:'hidden',
	                                  id:'wmId',
	                                  name:'wmId'
	      				      }]
	      		        }]
    		        });
	    		    return workMainDetailLeftPanel;
			    }
			    
				function initWorkMainDetailLeftPanelData(workMainDetailLeftPanel,wmId){
                      Ext.Ajax.request({
                           url:Ext.ANYNOTENEW_URL + 'workManage/getWorkMainById.do',
                           params:{
                                wmId:wmId
                           },
                           success:function(response,options){
	                    	   var workMainInfo = Ext.decode(response.responseText);
	                    	   workMainDetailLeftPanel.getForm().setValues(workMainInfo);
                           }
                      });
			    }

				function initWorkMainDetailRightPanelData(wmId){
					var workItemStore = new Ext.data.JsonStore({
				        url: Ext.ANYNOTENEW_URL + 'workManage/getWorkItemsByWmId.do',
				        root: 'datas',
				        totalProperty: 'results',
				        fields: ['wiId', 'wiDate','wiTitile','wiImportantLevel','wiEmergentLevel','wiIsTemporary','wiCostTime','wiFinishRate'],
                        baseParams : {
                             wmId:wmId
	                    },
				        autoLoad: true
				    });
				    return workItemStore;
			    }

				function initWorkMainDetailRightPanel(workItemStore){
					var workItemSM = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
					var workItemGrid = new Ext.grid.GridPanel({
						id: 'workMainGrid',
						title : '工作日志项',
						height:470,
						widht:400,
						border: false,
						columnLines: true,
						stateful: true,
					    autoScroll: 'auto',
					    loadMask: true,
				        store: workItemStore,
				        cm: new Ext.grid.ColumnModel({
				            defaults: {
				                width: 120,
				                sortable: true
				            },
					        columns: [
								workItemSM,
								new Ext.grid.RowNumberer({header:'№'}),
					            {id:'wiId',header: '工作日志项ID',sortable: true, dataIndex: 'wiId', hidden: true},
					            {id:'wiDate',header: '日期', width: 90, sortable: true, dataIndex: 'wiDate', align:'center'},
					            {id:'wiTitile',header: '工作内容', width: 150, sortable: true, dataIndex: 'wiTitile', align:'center'},
					            {id:'wiImportantLevel',header: '重要性', width: 60, sortable: true, dataIndex: 'wiImportantLevel', align:'center'},
					            {id:'wiEmergentLevel',header: '紧急性', width: 60, sortable: true, dataIndex: 'wiEmergentLevel', align:'center'},
					            {id:'wiIsTemporary',header: '是否临时任务', width: 90, sortable: true, dataIndex: 'wiIsTemporary', align:'center'},
					            {id:'wiCostTime',header: '花费时间', width:60, sortable: true, dataIndex: 'wiCostTime', align:'center'},
					            {id:'wiFinishRate',header: '完成率', width: 60, sortable: true, dataIndex: 'wiFinishRate', align:'center'}
					        ]
				        }),
				        sm:workItemSM,
				        columnLines: true,
				    });
				    return workItemGrid;
			    }
			    
				function initWorkMainDetailPanelData(){
					  var records = workMainGrid.getSelectionModel().getSelections();
					  var wmId = records[0].get('wmId');
					  
					  var workMainDetailLeftPanel = initWorkMainDetailLeftPanel();
					  initWorkMainDetailLeftPanelData(workMainDetailLeftPanel,wmId);

                      var workItemStore = initWorkMainDetailRightPanelData(wmId);
					  var workMainDetailRightPanel = initWorkMainDetailRightPanel(workItemStore);

	                  var workMainDetailPanel = new Ext.Panel({
	                         id:'workMainDetailPanel',
	                         baseCls:'x-plain',
	                         width:603,
	                         height:470,
	                         layout:'table',
	                         layoutConfig:{
	                             columns:2
	                         },
	                         autoScroll:true,
	                         defaults:{
	                             frame:true
	                         },
	                         items:[{
	                                   width:230,
	                                   height:475,
	                                   items:[workMainDetailLeftPanel]
	                            },{
	                                   width:652,
	                                   height:475,
	                                   items:[workMainDetailRightPanel]
	                         }]
	                  });

	                  var workMainDetailWin = new Ext.Window({
	    	                 title:'工作日志详情',
	    	                 width:905,
	    	                 height:510,
	    	                 modal:true,
	    	                 maximizable:false,
	    	                 resizable:false,
	    	                 closable:true,
	    	                 closeAction:'hide',
	    				     layout:'fit',
	    	                 plain:true,
	    	                 items:[workMainDetailPanel]
	    			  });

	    			  return workMainDetailWin;
	              }
		  })
	</script>

  </head>
  
  <body>
     <div id="workManageDiv" style="height:100%; width:100%;background-image:url('<%=path%>/websrc/image/login/login-bg.jpg');background-size:cover;">
      
    </div>
  </body>
</html>

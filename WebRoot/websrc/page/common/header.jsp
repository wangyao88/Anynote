<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="global.security.SessionUtils"%>
<%@page import="global.Constants"%>
<%@page import="util.DateUtils"%>
<%@page import="global.Constants"%>
<html>
<head>
	<%
		String baseUrl = request.getContextPath();
		String theme = "xtheme-purple.css";
		if(SessionUtils.getUserMeta().get("theme") != null){
		     theme = SessionUtils.getUserMeta().get("theme").toString();
		}
		String userId = SessionUtils.getCurrentUserId();
	%>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<style type="text/css">
	    #weather{
	        position:relative;
	        left:-100px;
	    }
	</style>
	<script type="text/javascript">
		$(document).ready(function(){
			Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
			// 主题
		    //var themeFormPanel = new Anynote.themeComboBox({
		    //	renderTo: 'themeChangeDiv'
		    //});
		    $("#sayHelloSpan").text(Anynote.sayHello());
		    //$("#todayDate").html("<a href='javascript:openCalendar()'>"+Anynote.getToday()+"</a>");
		    //提示框--天气预报
		    setTimeout(initWeather,10000);
		    //提示框--待办
		    setTimeout(todoWin,17000);
		    //提示框--月支出比例图
		    setTimeout(openMonthRateWin,22500);
		    //提示框--使用本系统分钟数(一小时提醒一次)
		    var hourTime = 1000*60*60;
		    setInterval(userSystemTime,hourTime);
		    
		   // showClock();
		});
		
		function showClock(){
		    var updateClock = function(){
		   		Ext.fly('clock').update(new Date().format('g:i:s A'));
			} 
			var task = {
			    run: updateClock,
			    interval: 1000 //1 second
			}
			var runner = new Ext.util.TaskRunner();
			runner.start(task);
		}
		
		
		function userSystemTime(){
		    var msg = '<br><br><center><font size=4><strong>您已连续使用系统一小时,<br>请离开电脑，休息一下!</strong></font></center>';
		    var rightBottomTipWin = Anynote.rightBottomTipWin(msg);
	        rightBottomTipWin.show();
		}
		
		function openMonthRateWin(){
		    // 发送请求
			Anynote.ajaxRequest({
				baseUrl: '<%=baseUrl %>',
				action: '/accountAction.do?method=getMonthRate',
				callback: function(jsonResult){
				    var html = '<iframe src="<%=baseUrl%>/websrc/page/account/report/monthRateGauge.jsp" width="410px" height="285px;" style="margin:0px;" frameborder="0"></iframe>';
					var rightBottomTipWin = Anynote.rightBottomTipWin(html,380,270);
	                rightBottomTipWin.show();
				}
		   });
		}
		
		function initWeather(){
			// 发送请求
			Anynote.ajaxRequest({
				baseUrl: '<%=baseUrl %>',
				action: '/settingAction.do?method=checkNet',
				callback: function(jsonResult){
				    var flag = jsonResult.flag;
				    var url = "http://tianqi.xixik.com/cframe/10";
				    var msg  = "网络状态良好";
				    if(flag == true){
				       var frame = document.getElementById('weather');
				       frame.src = url;
				    }else{
				       msg = "网络未连接";
				    }
					var rightBottomTipWin = Anynote.rightBottomTipWin(msg);
		            rightBottomTipWin.show();
				}
			});
		}
		
		function todoWin(){
		    // 发送请求
			Anynote.ajaxRequest({
				baseUrl: '<%=baseUrl %>',
				action: '/todoAction.do?method=checkTodayTodo',
				callback: function(jsonResult){
				    var msg  = jsonResult.msg
					var rightBottomTipWin = Anynote.rightBottomTipWin(msg);
                    rightBottomTipWin.show();
				}
			});
		}

		// 账户信息
		function editUser(){
			editUserWindow = new Ext.Window({
				title: '账户信息',
				width: 350,
				height: 400,
				modal: true,
				maximizable: false,
				resizable: false,
				layout:'fit',
				plain: true,
				autoLoad:{url:'<%=baseUrl %>/websrc/page/user/editUser.jsp',scripts:true,nocache:true},
				listeners: {
					close: function(){
						var buttonType = Ext.getCmp("buttonType").getValue();
						if(buttonType == "save"){
							Anynote.changeTheme(Ext.getCmp("themeCombo").getValue());
						}else{
							Anynote.changeTheme("<%=theme%>");
						}
					}
				}
			});
			editUserWindow.show();
		}

		// 修改密码
		function editPassword(){
			changePwdWindow = new Ext.Window({
				title: '修改密码',
				width: 300,
				height: 150,
				modal: true,
				maximizable: false,
				resizable: false,
				layout:'fit',
				plain: true,
				autoLoad:{url:'<%=baseUrl %>/websrc/page/user/changePwd.jsp',scripts:true,nocache:true}
			});
			changePwdWindow.show();
		}
		
		// 用户退出
		function logout(){
			Ext.Msg.confirm("警告", "确定要退出吗？", function(btn){
				if(btn=="yes"){
					// 发送请求
					Anynote.ajaxRequest({
						baseUrl: '<%=baseUrl %>',
						action: '/loginAction.do?method=logout',
						callback: function(jsonResult){
							Ext.state.Manager.set('Anynote_autoLogin', false);
							location.href="<%=baseUrl %>";
						},
						showWaiting: true
					});
				}
			});
		}

		// 锁定
		function lock(){
			Ext.Msg.confirm("警告", "确定要锁定吗？", function(btn){
				if(btn=="yes"){
					// 用户登录Form
					var lockFormPanel = new Ext.FormPanel({
				        border: false,
				        labelWidth: 40,
				        bodyStyle: 'padding:10px 5px 0px 5px;background-color:transparent;',
				        url: '<%=baseUrl %>/loginAction.do?method=login',
				        items: [{// 笔记标题
				        	xtype:'label',
		    				html: '系统正在使用，并被锁定，请输入密码解除锁定',
		    				style: 'font-size:12px;',
		    				cls: 'x-form-item-label'
						},{
				            layout:'column',
				            border: false,
				            bodyStyle: 'background-color:transparent;margin-top:10px;',
				            items:[{// 分类ID
				        		columnWidth: .8,
								layout: 'form',
								border: false,
								bodyStyle: 'background-color:transparent;',
				        		items: [new Ext.form.TextField ({// 密码
				        			inputType: 'password',
					                name: 'password',
					                fieldLabel: '密码',
					                anchor:'98%',
					                maxLength: 20,
					                listeners:{
										specialKey:function(field, e){
											if(e.getKey() == Ext.EventObject.ENTER){
												Ext.getCmp("lock-tick-button").handler();
											}
										}
					               }
					           })]
				        	},{// 分类ID
				        		columnWidth: .2,
								layout: 'form',
								border: false,
								bodyStyle: 'background-color:transparent;',
				        		items: [new Ext.Button({
					        		id: 'lock-tick-button',
								    text: '确定',
									iconCls: 'tick',
									handler: function(){
										var userId = '<%=SessionUtils.getCurrentUserId() %>';
				        				var password = lockFormPanel.getForm().findField('password').getValue();
				        				if(password!=''){
				        					// 发送请求
				        					Ext.getCmp('lockWindowStatusPanel').body.update("&nbsp;<img src='<%=baseUrl %>/websrc/js/ext-3.3.0/resources/images/default/shared/loading-balls.gif'/>");
											Ext.Ajax.request({
												url: '<%=baseUrl %>/loginAction.do?method=loginCheck',
												params: {userId:userId, password:password},
												callback: function(options, success, response){
													var jsonResult = Ext.decode(response.responseText);
													if(jsonResult.success==true){
														Ext.getCmp('lockWindow').close();
														$('#lockDiv').hide();
														Ext.state.Manager.set('Anynote_hasLocked', false);
														window.onbeforeunload = null;
													}else{
														var message = '发生异常.';
														if(jsonResult.message && jsonResult.message != ''){// 后台设定的业务消息
															message = jsonResult.message;
														}
														Ext.getCmp('lockWindowStatusPanel').body.update('<font color="red">'+message+'</font>');
														setTimeout(function(){Ext.getCmp('lockWindowStatusPanel').body.update('当前用户：<%=SessionUtils.getCurrentUserName()%>');},3000);
													}
												}
											});
					        			}else{
					        				Ext.getCmp('lockWindowStatusPanel').body.update('<font color="red">请输入密码.</font>');
					        				setTimeout(function(){Ext.getCmp('lockWindowStatusPanel').body.update('当前用户：<%=SessionUtils.getCurrentUserName()%>');},3000);
						        		}
									}
								})]
				        	}]
				        }]
				    });
					$('#lockDiv').show();
					// 用户登录窗口
					var lockWindow = new Ext.Window({
						id: 'lockWindow',
						renderTo: 'lockWindowDiv',
						title: '系统已锁定',
						width: 300,
						height: 120,
						maximizable: false,
						resizable: false,
						closable: false,
						draggable: false,
						layout:'fit',
						plain: true,
						items: [lockFormPanel],
						bbar: new Ext.Panel({
							id: 'lockWindowStatusPanel',
							html: '当前用户：<%=SessionUtils.getCurrentUserName()%>',
							border: false,
							bodyStyle: 'background-color:transparent;padding:3px 5px;'
						})
					});
					lockWindow.show();
					Ext.state.Manager.set('Anynote_hasLocked', true);
					window.onbeforeunload = function(){
						return "系统已锁定，离开页面将退出登录，确定吗？";
					}
				}
			});
		}

		function openCalendar(){
			var calendarWindow = new Ext.Window({
				title: '万年历',
				width: 437,
				height: 550,
				modal: true,
				maximizable: false,
				resizable: false,
				layout:'fit',
				bodyStyle: 'background-color:#ffffff;',
				html: '<iframe src="<%=baseUrl %>/websrc/page/common/calendar.jsp" width="450px" height="520px;" style="margin-left:10px;" frameborder="0"></iframe>'
			});
			calendarWindow.show();
		}
		
		function refresh(){
		   location.reload(true);
		}
		
		function openRadio(){
		   var radioWindow = new Ext.Window({
				title: '电台',
				width: 590,
				height: 340,
				modal: false,
				maximizable: true,
				minimizable : true,
				resizable: true,
				collapsible : true,
				layout:'fit',
				//baseCls:'x-plain',
				bodyStyle: 'background-color:#CFC0FF;',
				html: '<iframe src="http://player.kuwo.cn/webmusic/webdiantai/kuwoBaiduPlay.jsp" width="580px" height="310px;" style="margin-left:10px;" frameborder="0"></iframe>',
				listeners : {
				    minimize : function(){
				        if(this.minimizable){
				             this.hide('slow',function(){},this);
				        }
				    }
				}
			});
			radioWindow.show();
		}
		
		function openSignInHistoryData(){
		        // 全局参数
			    var baseParams = {start:0, limit:"<%=Constants.PAGE_SIZE %>",fromDate:"<%=DateUtils.getFirstDayOfMonthAsString(DateUtils.DATE_PATTON_1)%>", toDate:"<%=DateUtils.getLastDayOfMonthAsString(DateUtils.DATE_PATTON_1)%>"};
	               // 签到数据源
			    var signInListStore = new Ext.data.JsonStore({
			        url: '<%=baseUrl%>/signInAction.do?method=querySignInHistoryData',
			        root: 'datas',
			        totalProperty: 'results',
			        fields: ['id', 'signUserId', 'signUserName', 'dateStr', 'address', 'ip', 'remark'],
			        baseParams: baseParams,
			        autoLoad: true
			    });
			    
				// 账目数据表格
				var signInListGrid = new Ext.grid.GridPanel({
					id: 'signInListGrid',
					//renderTo: 'accountListGridDiv',
					border: false,
					columnLines: true,
					stateful: true,
				    autoScroll: 'auto',
				    height: 510,
			        store: signInListStore,
			        loadMask: true,
			        cm: new Ext.grid.ColumnModel({
			            defaults: {
			                width: 120,
			                sortable: true
			            },
				        columns: [
							new Ext.grid.RowNumberer({header:'№'}),
				            {id:'id',header: '签到ID', width: 100, sortable: true, dataIndex: 'id', hidden: true},
				            {id:'signUserName',header: '签到人', width: 150, sortable: true, dataIndex: 'signUserName'},
				            {id:'dateStr',header: '签到日期', width: 150, sortable: true, dataIndex: 'dateStr'},
				            {id:'address',header: '签到地點', width: 150, sortable: true, dataIndex: 'address'},
				            {id:'ip',header: 'IP', width: 150, sortable: true, dataIndex: 'ip'},
				            {id:'remark',header: '签到內容', width: 360, sortable: true, dataIndex: 'remark'}
				        ]
			        }),
			        columnLines: true,
			        bbar: new Ext.PagingToolbar({
						pageSize: <%=Constants.PAGE_SIZE %>,
						store: signInListStore,
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
				Anynote.resizeGridTo("signInListGrid", 0, 169);
				var signInListWin =   new Ext.Window({
			            title: '签到数据列表',
						width: 1000,
						height: 515,
						modal: true,
						maximizable: false,
						resizable: true,
						layout:'fit',
						bodyStyle: 'background-color:#CFC0FF;',
						items:[signInListGrid]
			    });
			    signInListWin.show();
		}
		
		
		
		
		function openSsignIn(){
		    var signInFormPanel = new Ext.FormPanel({
		          id: 'signInFormPanel',
		          labelWidth: 40,
		          border: false,
		          buttonAlign: 'center',
		          style: 'border-bottom:0px;',
		          bodyStyle: 'background:#fff; padding:15px;',
			      //bodyStyle: 'padding:10px;background-color:transparent;',
		          items: [
				              new Ext.form.TextField ({// 地点
			            		inputType: 'textfiled',
				                id: 'address',
				                name: 'address',
				                fieldLabel: '地点',
				                anchor:'98%',
				                allowBlank:false,
				                maxLength: 20
				           }),new Ext.form.TextArea ({// 内容
				               id:'remark',
				               name:'remark',
				               fieldLabel:'内容',
				               anchor:'98%',
				               allowBlank:false,
				               height: 70
						})
		          ],
		          buttons: [{
					id:'login-button',
                    text:'签到',
                    handler: function(){
						var address = $("#address").val();
						var remark = $("#remark").val();
						if(address=="" || remark == ""){
							Ext.Msg.alert('提示', '请输入用地点和内容.');
						}else{
							// 发送请求
							Anynote.ajaxRequest({
								baseUrl: '<%=baseUrl %>',
								baseParams: {address:address, remark:remark},
								action: '/signInAction.do?method=signIn',
								callback: function(jsonResult){
								       Ext.Msg.confirm("提示", "签到成功!是否查看签到历史数据？", function(btn){
											if(btn=="yes"){
											    signInWin.close();
												openSignInHistoryData();
											}else{
											    signInWin.close();
											}
										});
								},
								showWaiting: true,
								failureMsg:'签到失败'
							});
						}
                	}
                },{
                    text: '打卡',
                    handler: function(){
                		Ext.Ajax.request({
                             url : Ext.ANYNOTENEW_URL + 'attendenceTime/addAttendenceTimeRecord.do',
                             params : {
                                  userId : '<%=userId%>'
                             },
                             success : function(response,options){
                                  Ext.Msg.alert('提示','打卡成功!');
                                  signInWin.close();
                             }
                        });
                    }
                },{
                    text: '查看',
                    handler: function(){
                		signInWin.close();
	                    openSignInHistoryData();
                    }
                }]
		    });

		    var attendenceTimeRecord_Store = new Ext.data.ArrayStore({  
		    	fields : ['attendenceTimeRecordTypeName','attendenceTimeRecordType'],  
	            data : Anynote.ATTENDENCETIME_DATA
	        });

		    var attendenceTimeRecordPanel = new Ext.FormPanel({
			    id : 'attendenceTimeRecordPanel',
            	labelWidth: 70,
		        items: [{
		        	layout:'form',
		            border: false,
		            bodyStyle: 'background:#fff; padding:15px;',
		            items:[{
			            	xtype:'combo',
			                hiddenName: 'attendenceTimeRecordType',
			                fieldLabel: '打卡时间',
			                store: attendenceTimeRecord_Store,
			                mode : 'local',
			                triggerAction: "all",
			                valueField: 'attendenceTimeRecordType',
			                displayField: 'attendenceTimeRecordTypeName',
			                editable: false,
		                    anchor:'95%'
			           },{
				           items : [{
				        	   width : 270,
	                           html : '<iframe id="clock" name="clock_info" src="<%=baseUrl %>/websrc/page/system/clock.jsp" width="300"  height=75" frameborder="0" marginwidth="0" marginheight="0" scrolling="no" style="background-color:transparent"></iframe> '
					        }]
				       }]
		        }],
		        buttons : [{
                     id : 'add-attendenceTimeRecord-button',
                     text : '打卡',
                     handler : function(){
                          var attendenceTimeRecordType = attendenceTimeRecordPanel.getForm().findField('attendenceTimeRecordType').getValue();
                          Ext.Ajax.request({
                               url : Ext.ANYNOTENEW_URL + 'attendenceTime/addAttendenceTimeRecord.do',
                               params : {
                                   userId : '<%=userId%>',
                                   attendenceTimeRecordType : attendenceTimeRecordType
                               },
                               success : function(response,options){
                                   var result = response.responseText;
                                   if(result == 'early'){
                                	   Ext.Msg.alert('提示','打卡成功!早退!');
                                   }else if(result == 'late'){
                                	   Ext.Msg.alert('提示','打卡成功!迟到!');
                                   }else if(result == 'success'){
                                	   Ext.Msg.alert('提示','打卡成功');
                                   }else if(result == 'repeat'){
                                	   Ext.Msg.alert('提示','已经打过卡.本次打卡无效!');
                                   }else if(result == 'week'){
                                	   Ext.Msg.alert('提示','周末,无须打卡!');
                                   }else{
                                	   Ext.Msg.alert('提示',result);
                                   }
                                   signInWin.close();
                            	   return;
                               }
                          });
                     }
			    }]
		    });

            //请假
		    var leaveRecordPanel = new Ext.FormPanel({
			    id : 'leaveRecordPanel',
            	labelWidth: 70,
		        items: [{
		        	layout:'form',
		            border: false,
		            bodyStyle: 'background:#fff; padding:15px;',
		            items:[{// 请假开始时间
		                xtype:'datefield',
		                name: 'lrStartTime',
		                fieldLabel: '开始时间',
		                format: 'Y-m-d H:i:s',
		                anchor:'100%',
		                allowBlank:false 
		           },{// 请假结束时间
		                xtype:'datefield',
		                name: 'lrEndTime',
		                fieldLabel: '结束时间',
		                format: 'Y-m-d H:i:s',
		                anchor:'100%',
		                allowBlank:false 
		           },{
                        xtype:'textarea',
                        name:'lrRemark',
                        fieldLabel:'请假是由',
                        anchor:'100%',
                        allowBlank:false,
                        height:50
			       }]
		        }],
		        buttons : [{
                     id : 'add-leave_record-button',
                     text : '请假',
                     handler : function(){
                          var leaveRecord = leaveRecordPanel.getForm().getValues();
                          var leaveRecordData  = Ext.encode(leaveRecord);
                          Ext.Ajax.request({
                               url : Ext.ANYNOTENEW_URL + 'leaveRecord/addLeaveRecord.do',
                               params : {
                                   userId : '<%=userId%>',
                                   leaveRecordData : leaveRecordData
                               },
                               success : function(response,options){
                                   var result = response.responseText;
                                   if(result == 'success'){
                                	   Ext.Msg.alert('提示','请假成功');
                                   }else{
                                	   Ext.Msg.alert('提示', result);
                                   }
                                   signInWin.close();
                            	   return;
                               }
                          });
                     }
			    }]
		    });
		    

		    var asignInAndAttendenceTimeRecordPanel = new Ext.Panel({  
			    id:'asignInAndAttendenceTimeRecordPanel',  
			    baseCls:'x-plain',
			    layout:'table',  
			    autoScroll:true,
			    layoutConfig:{columns:3},
			    defaults:{frame:true},//attendenceTimeDetailFormPanel,holidyGrid
			    items:[{
				         title:'签到',
				         width:300,
				         height: 210,
                         items:[signInFormPanel]
				},{
					     title:'考勤',
                         width:305,
                         height:210,
                         items : [attendenceTimeRecordPanel]
			    },{
                         title:'请假',
                         width:300,
                         height:210,
                         items:[leaveRecordPanel]
				}]  
			});  
		    
		    var signInWin = new Ext.Window({
		            title: '签到考勤',
					width: 920,
					height: 250,
					modal: true,
					maximizable: false,
					resizable: true,
					layout:'fit',
					bodyStyle: 'background-color:#CFC0FF;',
					items:[asignInAndAttendenceTimeRecordPanel]
		    });
		    signInWin.show();
		}
	</script>
</head>
<body>
	<div id="headerDiv" style="background-image:url('<%=baseUrl %>/websrc/image/login/login-bg-small.png');background-size:cover;">
		<table style="width:100%;height:100%;table-layout:fixed;">
			<tr>
				<td width="180px">
					  <a href="<%=baseUrl %>"><img src="<%=baseUrl %>/websrc/image/Anynote-s.png"></img></a>
				</td>
				<td>
				      <span style="padding:3px 10px 3px 18px;" class="user" style="height:20px;">
						  <%=SessionUtils.getCurrentUserName()+"["+Constants.ROLE_MAP.get(SessionUtils.getUserRole())+"]" %>，<span id="sayHelloSpan"></span>
					  </span>
				</td>
				<td class="loginInfo" style="position:relative;left:-320px;">
					<table>
					    <tr>
					       <td>
					            <a href="javascript:editUser();"><span style="padding:3px 10px 3px 18px;" class="user_edit" style="height:20px;"></span></a>
								<a href="javascript:editPassword();"><span style="padding:3px 10px 3px 18px;" class="key" style="height:20px;"></span></a>
								<a href="javascript:openCalendar();"><span style="padding:3px 10px 3px 18px;" class="report_disk" style="height:20px;"></span></a>
								<a href="javascript:refresh();"><span style="padding:3px 10px 3px 18px;" class="refresh" style="height:20px;"></span></a>
					       </td>
					    </tr>
					    <tr>
					       <td>
								<a href="javascript:lock();"><span style="padding:3px 10px 3px 18px;" class="lock" style="height:20px;"></span></a>
								<a href="javascript:logout();"><span style="padding:3px 10px 3px 18px;" class="user_go" style="height:20px;"></span></a>
								<a href="javascript:openSsignIn();"><span style="padding:3px 10px 3px 18px;" class=tag_blue_add style="height:20px;"></span></a>
								<a href="javascript:openRadio();"><span style="padding:3px 10px 3px 18px;" class=rar style="height:20px;"></span></a>
					       </td>
					    </tr>
					</table>
				</td>
				<td width="180px">
					 <iframe id="weather"
					         name="weather_inc" 
					         src="" 
					         width="300" 
					         height="25" 
					         frameborder="0" 
					         marginwidth="0" 
					         marginheight="0" 
					         scrolling="no"
					         style="background-color:transparent">
					 </iframe> 
				</td>
			</tr>
		</table>
    </div>
</body>
</html>
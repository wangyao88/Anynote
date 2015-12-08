<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import="global.security.SessionUtils"%>
<html>
<head>
	<%
		String baseUrl = request.getContextPath();
		String userId = SessionUtils.getCurrentUserId();
	%>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<script type="text/javascript">
		$(document).ready(function(){
			// 工具栏
		    var changePwdToolbar = new Ext.Toolbar({
		    	renderTo: 'changePwdToolBarDiv',
		    	items: [
				    new Ext.Button({
					    id: 'changePwd-save-button',
						text: '保存',
						iconCls: 'tick'
					}),
					new Ext.Button({
					    id: 'changePwd-cancel-button',
						text: '取消',
						iconCls: 'cancel'
					})
				]
			});

		 	// 编辑用户Form
			var changePwdFormPanel = new Ext.FormPanel({
				renderTo: 'changePwdFormDiv',
		        labelWidth: 60,
		        border: false,
		        bodyStyle: 'background-color:transparent;',
		        url: '<%=baseUrl %>/userAction.do?method=changePwd',
		        items: [{
		            layout:'form',
		            border: false,
		            bodyStyle: 'padding:5px;background-color:transparent;',
		            items:[new Ext.form.TextField ({// 原密码
	            		inputType: 'password',
		                id: 'oldpassword',
		                name: 'oldpassword',
		                fieldLabel: '原密码',
		                anchor:'95%',
		                allowBlank:false,
		                maxLength: 20
		           }),new Ext.form.TextField ({// 新密码
		        	   inputType: 'password',
		               id:'password',
		               name:'password',
		               fieldLabel:'新密码',
		               anchor:'95%',
		               allowBlank:false,
		               maxLength: 20
		           }),new Ext.form.TextField ({// 确认密码
	            	   inputType: 'password',
		        	   id:'repassword',
		               name:'repassword',
		               fieldLabel:'确认密码',
		               anchor:'95%',
		               allowBlank:false,
		               maxLength: 20
	                }),{// 账户ID
		               xtype:'hidden',
		               name: 'userId',
		               value: '<%=SessionUtils.getCurrentUserId()%>'
		           },{// 操作类型
		               xtype:'hidden',
		               name: 'type',
		               value: 'UPDATE'
		           }]
		        }]
		    });

		    // 保存按钮
		    $("#changePwd-save-button").click(function(){
			    var form = changePwdFormPanel.getForm();
			    if(form.isValid()){
			    	// 发送请求
					Anynote.ajaxRequest({
						baseUrl: '<%=baseUrl %>',
						baseParams: form.getValues(),
						action: '/userAction.do?method=changePwd',
						callback: function(jsonResult){
							location.href="<%=baseUrl %>/websrc/page/login.jsp";
						},
						showWaiting: true,
						showMsg: true,
						successMsg: '密码修改成功，请重新登录！'
					});
				}
			});

			// 取消按钮
		    $("#changePwd-cancel-button").click(function(){
		    	changePwdWindow.close();
			});
		});
	</script>
</head>
<body>
<div id="changePwdDiv" style="width:100%;height:100%;">
	<div id="changePwdToolBarDiv"></div>
	<div id="changePwdFormDiv""></div>
</div>
</body>
</html>
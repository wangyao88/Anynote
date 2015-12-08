<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/websrc/page/common/jsloader.jsp"%>
<%@ page import="global.Constants"%>
<html>
<head>
	<%
		String baseUrl = request.getContextPath();
		String openRegister = (String)request.getAttribute("openRegister");
	%>
	<title>Anynote</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<script type="text/javascript">
		$(document).ready(function(){
			Ext.QuickTips.init();
			Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
			// 设置主题
			var theme = Ext.state.Manager.get('Anynote_theme');
			if(theme && theme!=''){
				Anynote.changeTheme(theme);
			}else{
				Anynote.changeTheme(Anynote.THEME_DEFAULT);
			}
			// 密码重置Form
			var resetPwdFormPanel = new Ext.FormPanel({
				id: 'resetPwdFormPanel',
		        labelWidth: 50,
		        border: false,
		        buttonAlign: 'center',
		        style: 'border-bottom:0px;',
		        bodyStyle: 'padding:10px;background-color:transparent;',
		        url: '<%=baseUrl %>/resetPwdAction.do?method=resetPwd',
	            items:[new Ext.form.TextField ({// 账号
	            		inputType: 'textfiled',
		                id: 'userId',
		                name: 'userId',
		                fieldLabel: '账号',
		                anchor:'98%',
		                allowBlank:false,
		                maxLength: 20
		           }),new Ext.form.TextField ({// 邮箱
		        	   inputType: 'textfiled',
		               id:'email',
		               name:'email',
		               fieldLabel:'邮箱',
		               anchor:'98%',
		               allowBlank:false,
		               maxLength: 50,
		               vtype:'email',
		               listeners:{
							specialKey:function(field, e){
								if(e.getKey() == Ext.EventObject.ENTER){
									Ext.getCmp("resetPwd-button").handler();
								}
							}
		               }
				}),{// 验证码
		               xtype:'textfield',
		               id:'verifyCode',
		               name:'verifyCode',
		               fieldLabel:'验证码',
		               anchor:'98%',
		               allowBlank:false,
		               maxLength: 20
		           },{
					   width: 110,
					   border: false,
					   style: 'margin-left:55px;line-height:20px;',
					   bodyStyle: 'background-color:transparent;',
					   html:'<img id="VerifyCodeImg" src="<%=baseUrl %>/loginAction.do?method=getVerifyCode" style="float:left;"/><span style="float:right;"><a href="javascript:changeVerifyCode();">换一张</a></span>'
			       }],
				buttons: [{
					id:'resetPwd-button',
                    text:'提交',
                    handler: function(){
						var userId = $("#userId").val();
						var email = $("#email").val();
						var verifyCode = $("#verifyCode").val();
						if(userId=="" || email == ""){
							Ext.Msg.alert('提示','请输入用户名和邮箱.');
						}else{
							// 发送请求
							Anynote.ajaxRequest({
								baseUrl: '<%=baseUrl %>',
								baseParams: {userId:userId, email:email, verifyCode:verifyCode},
								action: '/loginAction.do?method=resetPwd',
								callback: function(jsonResult){
									location.href="<%=baseUrl %>/websrc/page/login.jsp";
								},
								showWaiting: true,
								showMsg: true,
								successMsg: '密码重置成功，请查收邮件！'
							});
						}
                	}
                },{
                    text: '取消',
                    handler: function(){
                		resetPwdFormPanel.getForm().reset();
                    }
                }]
		    });

			// 状态栏HTMl
		    var bbarHtml = "<a href='javascript:login();'>登录</a>";
			if('<%=openRegister%>'=='true'){
				bbarHtml = bbarHtml + "&nbsp;&nbsp;<a href='javascript:register();'>注册</a>";
			}
			// 密码重置窗口
			var resetPwdWindow = new Ext.Window({
				renderTo: 'resetPwd-win-div',
				id: 'resetPwdWindow',
				title: '密码重置',
				width: 300,
				height: 210,
				closeAction: 'hide',
				maximizable: false,
				resizable: false,
				closable: false,
				draggable: false,
				layout:'fit',
				plain: true,
				buttonAlign: 'center',
				items: [resetPwdFormPanel],
				bbar: new Ext.Panel({
					html: bbarHtml,
					border: false,
					bodyStyle: 'background-color:transparent;padding:3px 10px;'
				})
			}).show();

			// 窗口大小改变时，从新设置窗口位置
		    window.onresize = function() {    
				var left = ($(window).width() - resetPwdWindow.getWidth())/2;
				resetPwdWindow.setPosition(left);
			};
		});

		// 注册
		function register(){
			// 转到登录页面
			location.href="<%=baseUrl %>/websrc/page/user/register.jsp";
		}
		// 登录
		function login(){
			// 转到登录页面
			location.href="<%=baseUrl %>/websrc/page/login.jsp";
		}
		// 更换验证码
		function changeVerifyCode(){
			$("#VerifyCodeImg").attr("src", "<%=baseUrl %>/loginAction.do?method=getVerifyCode&time="+new Date());
		}
	</script>
</head>
<body class="x-border-layout-ct" style="position:static;overflow:hidden;">
	<table id="logo-table">
		<tr>
			<td align="center" height="40"><a href="<%=baseUrl %>"><img src="<%=baseUrl %>/websrc/image/Anynote.png"></img></a></td>
		</tr>
	</table>
	<div id="resetPwd-win-div"></div>
</body>
</html>
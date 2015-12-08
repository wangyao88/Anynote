<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>日支出图表</title>
    <script type="text/javascript" src="<%=path%>/websrc/js/highcharts/js/jquery-1.7.2.js"></script>
	<script type="text/javascript" language="javascript" src="<%=basePath%>websrc/js/anyChart/anychart.js"></script>
	<script type="text/javascript">
         Ext.onReady(function() {
               var panel = new Ext.Panel({
		                id:'report-main-panel',
		                baseCls:'x-plain',
		                renderTo: 'dayReportDiv',
		                layout:'table',
		                anchor:'100%',
		                layoutConfig: {columns:2},
		                defaults: {frame:true, width:557, height: 560},
		                items:[{
		                    title:'日支出圆柱图',
		                    html: '<iframe src="<%=path%>/websrc/page/account/report/dayReportCyliner.jsp" width="560px" height="545px;" scrolling="no" style="margin:0px;" frameborder="0"></iframe>'
		                },{
		                    title:'日支出比例图',
		                    html: '<iframe src="<%=path%>/websrc/page/account/report/dayReportPiee.jsp" width="560px" height="545px;" scrolling="no" style="margin:0px;" frameborder="0"></iframe>'
		                }]
		            });
         
         });
	</script>
  </head>
  
  <body>
       <div id="dayReportDiv" style="width:100%;background-image:url('<%=path%>/websrc/image/login/login-bg.jpg');background-size:cover;">
            
       </div>
  </body>
</html>

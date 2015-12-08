<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String data = session.getAttribute("pieData").toString();
%>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>待办已办对比图</title>
    <style type="text/css">
       DIV#container object{
          margin-top:-28px !important;
          margin-left:-28px !important;
       }
    </style>
    <script type="text/javascript" src="<%=path%>/websrc/js/highcharts/js/jquery-1.7.2.js"></script>
	<script type="text/javascript" language="javascript" src="<%=basePath%>websrc/js/anyChart/anychart.js"></script>
</head> 
<body> 
    <div id="container" style="width:200px;height:100px;">
    
    </div>
    <script type="text/javascript" language="javascript"> 
    
    $(function () {
	    $(document).ready(function () {
                 var data = <%=data%>;
                 initPieChart(data);
	    });
	});
    
    function initPieChart(data){
	         var JSONData = {
			    "charts": {
			        "chart": {
			            "plot_type" : "Doughnut",
						"data_plot_settings" : {
						    "enable_3d_mode" : "true",
						    "pie_series":{
		                        "tooltip_settings":{
		                              "enabled": "true",
		                              "format":"{%Name}\n条数:{%YValue}{numDecimals:0}"
		                        },
		                        "label_settings":{
		                              "enabled": "true",
		                              "position":{
	                                      "anchor": "Center",
	                                      "halign": "Center",
	                                      "valign": "Center"
		                              },
		                              "format":"{%YPercentOfSeries}{numDecimals:1}"
		                        }
						    }
						 },
			            "chart_settings": {
			                "title": {
			                    "text": "已办各月比例图"
			                }
			            },
			            "data": {
			                "series": [
			                    {
				                    "name": "",
				                    "palette": "default",
			                        "point": data
			                    }
			                ]
			            }
			        }
			    }
	    
			};
	
		    var chart = new AnyChart('<%=basePath%>websrc/js/anyChart/swf/AnyChart.swf'); 
		    chart.width = 385; 
		    chart.height = 266; 
		    chart.setJSData(JSONData);
		    chart.write("container"); 
    }
    </script> 
</body> 
</html>

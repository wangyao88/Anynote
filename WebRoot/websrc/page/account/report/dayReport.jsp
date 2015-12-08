<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String data = session.getAttribute("columnData").toString();
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
                 initColumnChart(data);
	    });
	});
    
    function initColumnChart(data){
	         var JSONData = {
			    "charts": {
			        "chart": {
						"data_plot_settings" : {
						    "enable_3d_mode" : "true",
						    "bar_series":{
		                        "tooltip_settings":{
		                              "enabled": "true",
		                              "format":"{%SeriesName}{%Name}\n条数:{%YValue}{numDecimals:0,trailingZeros:false}"
		                        },
		                        "label_settings":{
		                              "enabled": "true",
		                              "position":{
	                                      "anchor": "Center",
	                                      "halign": "Center",
	                                      "valign": "Center"
		                              },
		                              "format":"{%YValue}{numDecimals:0,trailingZeros:false}"
		                        }
						    }
						 },
			            "chart_settings": {
			                "title": {
			                    "text": "代办已办对比柱状图"
			                },
			                "axes":{
			                    "x_axis": {
			                	    "enabled":"true",
			                        "title":{
			                            "text": ""
			                         }
			                    },
			                    "y_axis": {
			                	    "enabled":"true",
			                        "title":{
			                            "text": ""
			                         },
			                         "labels":{
			                        	 "format":"{%Value}{numDecimals:0,trailingZeros:false}"
				                     }
			                    }
			   			    }
			            },
			            "data": {
			                "series": [
			                    {
				                    "name": "",
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

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String data = session.getAttribute("reportDataOfYear").toString();
%>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>My JSP 'index.jsp' starting page</title>
	<style type="text/css">
       DIV#lineContainer object{
          margin-top:-28px !important;
          margin-left:-28px !important;
       }
    </style>
    <script type="text/javascript" src="<%=path%>/websrc/js/highcharts/js/jquery-1.7.2.js"></script>
	<script type="text/javascript" src="<%=path%>/websrc/js/anyChart/anychart.js"></script>
</head> 
<body> 
    <div id="lineContainer">
  
    </div>
    
    <script type="text/javascript"> 
    $(function () {
	    $(document).ready(function () {
                 var data = <%=data%>;
                 initLineChart(data);
	    });
	});
	
	function initLineChart(data){
		     var JSONData = {
			    "charts": {
			        "chart": {
	    	            "plot_type":"CategorizedVertical",
						"data_plot_settings" : {
						   // "enable_3d_mode" : "true",
						    "default_series_type":"Line",
						    "line_series":{
		                        "tooltip_settings":{
		                              "enabled": "true",
		                              "format":"{%Name}{%SeriesName}\n金额:{%YValue}{numDecimals:0,trailingZeros:false}"
		                        },
		                        "label_settings":{
		                              "enabled": "true",
		                              "position":{
	                                      "anchor": "Center",
	                                      "halign": "Center",
	                                      "valign": "Center"
		                              },
		                              "format":"{%YValue}{numDecimals:0,trailingZeros:false}",
		                              "font":{
	                                     "size":"14"
			                          }
		                        }
						    }
						 },
			            "chart_settings": {
			                "title": {
			                    "text": "年收支线形图"
			                },
			                "axes":{
			                    "x_axis": {
			                	    "enabled":"true",
			                        "title":{
			                            "text": ""
			                         },
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
			            "data": data
			        }
			    }
	    
			};
	
		    var chart = new AnyChart('<%=path%>/websrc/js/anyChart/swf/AnyChart.swf'); 
		    chart.width = 1150; 
			chart.height = 560; 
		    chart.setJSData(JSONData);
		    chart.write("lineContainer"); 
    }
    </script> 
</body> 
</html>

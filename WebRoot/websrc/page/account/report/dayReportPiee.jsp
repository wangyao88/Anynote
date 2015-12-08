<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String data = session.getAttribute("reportDataOfDay").toString();
%>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>日支出金字塔图</title>
    <style type="text/css">
       DIV#pyramidContainer object{
          margin-top:-28px !important;
          margin-left:-28px !important;
       }
    </style>
    <script type="text/javascript" src="<%=path%>/websrc/js/highcharts/js/jquery-1.7.2.js"></script>
	<script type="text/javascript" src="<%=path%>/websrc/js/anyChart/anychart.js"></script>
</head> 
<body> 
    <div id="pyramidContainer">
    
    </div>
    <script type="text/javascript"> 
    
    $(function () {
	    $(document).ready(function () {
                 var data = <%=data%>;
                 initPyramidChart(data);
	    });
	});
    
    function initPyramidChart(data){
	         var JSONData = {
			    "charts": {
			        "chart": {
			            "plot_type" : "Pie",
						"data_plot_settings" : {
						    "enable_3d_mode" : "true",
						    "pie_series":{
		                        "tooltip_settings":{
		                              "enabled": "true",
		                              "format":"{%SeriesName}{%Name}\n金额:{%YValue}{numDecimals:0,trailingZeros:false}"
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
			                    "text": "日支出比例图"
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
			                        //"type": "Bar",
			                        //"shape_type": "Pyramid",
				                    "name": "",
				                    "palette": "default",
			                        "point": data
			                    }
			                ]
			            }
			        }
			    }
	    
			};
	
		    var chart = new AnyChart('<%=path%>/websrc/js/anyChart/swf/AnyChart.swf'); 
		    chart.width = 585; 
		    chart.height = 560; 
		    chart.setJSData(JSONData);
		    chart.write("pyramidContainer"); 
    }
    </script> 
</body> 
</html>

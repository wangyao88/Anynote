<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
double rate = 1.00;
if(session.getAttribute("yearRate") != null){
    rate = Double.valueOf(session.getAttribute("yearRate").toString());
}
%>

<html>
  <head>
    <base href="<%=basePath%>">
    <style type="text/css">
       DIV#gauge object{
          margin-top:-45px !important;
          margin-left:-50px !important;
       }
    </style>
    <script type="text/javascript" src="<%=path%>/websrc/js/highcharts/js/jquery-1.7.2.js"></script>
	<script type="text/javascript" src="<%=path%>/websrc/js/anyChart/anychart.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
               var rate = <%=rate%>;
               if(rate < 50){
                   pointColor =  'Green';
               }else if(rate < 80){
                   pointColor =  'Yellow';
               }else if(rate < 100){
                   pointColor =  'Red';
               }
               var XMLData = "<anychart>" +
								  "<gauges>" +
								    "<gauge>" +
								      "<chart_settings>" +
								        "<title>" +
								          "<text><![CDATA[年支出/收入比例图]]></text>" +
								        "</title>" +
								         "<chart_background enabled='false'>" +
								           "<border enabled='false' />" +
								         "</chart_background>" +
								      "</chart_settings>" +
								      "<circular>" +
								        "<axis radius='50' start_angle='90' sweep_angle='180'>" +
								          "<scale minimum='0' maximum='100' major_interval='10' minor_interval='50' />" +
									      "<labels enabled='true'>" +
										    "<font bold='true' />" +
										    "<format><![CDATA[{%Value}{numDecimals:2} %]]></format>" +
									      "</labels>" +
										  "<minor_tickmark enabled='false' />" +
										  "<color_ranges>" +
										    "<color_range start='0' end='50' color='Green' />" +
										    "<color_range start='50' end='80' color='Yellow' />" +
										    "<color_range start='80' end='100' color='Red' />" +
										  "</color_ranges>" +
										  "<custom_labels>" +
										    "<custom_label value='"+rate+"' enabled='true'>" +
										      "<label enabled='true' align='Outside' padding='20'>" +
										        "<format><![CDATA["+rate+"%]]></format>" +
										      "</label>" +
										      "<tickmark enabled='true' shape='Star5' auto_rotate='false' width='10' length='10' align='Inside' padding='-12'>" +
										        "<fill color='"+pointColor+"' />" +
										        "<border color='DarkColor(Yellow)' />" +
										      "</tickmark>" +
										    "</custom_label>" +
										  "</custom_labels>" +
								        "</axis>" +
								        "<pointers>" +
								          "<pointer type='pie' value='"+rate+"' color='"+pointColor+"' />" +
								        "</pointers>" +
								      "</circular>" +
								    "</gauge>" +
								  "</gauges>" +
								"</anychart>";
								
				var chart = new AnyChart('<%=path%>/websrc/js/anyChart/swf/AnyChart.swf'); 
			    chart.width = 450; 
			    chart.height = 330; 
			    chart.setData(XMLData); 
			    chart.write('gauge');
        });
    </script>
  </head>
  
  <body>
      <div id="gauge">
      
      </div>
  </body>
</html>

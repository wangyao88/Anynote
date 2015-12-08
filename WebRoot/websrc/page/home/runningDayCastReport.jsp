<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
  <head>
    <base href="<%=basePath%>">
    <title>支出滚动日报</title>
    <script type="text/javascript" src="<%=path%>/websrc/js/highcharts/js/jquery-1.7.2.js"></script>
    <script type="text/javascript">
            function getRunningDayReportData(){
                 var url = '<%=path%>/accountAction.do?method=getRunningDayReportSumMoney';
                 $.ajax({
                      type:'POST',
                      url:url,
                      dataType:'json',
                      success:function(data){
                          initHightCharts(data.datas);
                      }
                 });
            }
            
            function initHightCharts(data){
                 $('#container').highcharts({
			            chart: {
			                type: 'spline',
			                animation: Highcharts.svg, // don't animate in old IE
			                marginRight: 10,
			                events: {
			                    load: function () {
			                        // set up the updating of the chart each second
			                        var series = this.series[0];
			                        var length = data.length;
			                        var i = 0;
			                        setInterval(function () {
			                            var x = (new Date()).getTime(), // current time
			                                y = data[i].money;
			                                i++;
			                                if(i >= length){
			                                    i = 0;
			                                }
			                            series.addPoint([x, y], true, true);
			                        }, 1000);
			                    }
			                }
			            },
			            title: {
			                text: '支出滚动日报'
			            },
			            xAxis: {
			                type: 'datetime',
			                tickPixelInterval: 150
			            },
			            yAxis: {
			                title: {
			                    text: '花费'
			                },
			                min: 0,
			                plotLines: [{
			                    value: 0,
			                    width: 1,
			                    color: '#808080'
			                }]
			            },
			            tooltip: {
			                formatter: function () {
			                    return '<b>' + this.series.name + '</b><br/>' +
			                           Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
			                           Highcharts.numberFormat(this.y, 2);
			                }
			            },
			            legend: {
			                enabled: false
			            },
			            exporting: {
			                enabled: false
			            },
			            series: [{
			                name: '支出滚动日报',
			                data: (function () {
			                    // generate an array of random data
			                    var data = [],
			                        time = (new Date()).getTime(),
			                        i;
			                    for (i = -19; i <= 0; i += 1) {
			                        data.push({
			                            x: time + i * 1000,
			                            y: (Math.random()) * 10
			                        });
			                    }
			                    return data;
			                }()),
			                zones: [{
										value: 0,
										color: '#FC0AF0',
					                    dashStyle: 'dot'
									}, {
										value: 10,
										color: '#07F20F'
									},{
									    value: 40,
										color: '#0429F7'
									},{
									    value: 80,
										color: '#F704DF'
									},{
									    value: 130,
										color: '#F70425'
									}
					            ]
			            }]
			        });
            }
            
			$(function () {
			    $(document).ready(function () {
			        getRunningDayReportData();
			        Highcharts.setOptions({
			            global: {
			                useUTC: false
			            }
			        });
			    });
			});
     </script>
  </head>
  
  <body>
    <script src="<%=path%>/websrc/js/highcharts/js/highcharts.js"></script>
    <script src="<%=path%>/websrc/js/highcharts/js/modules/exporting.js"></script>
    <div id="container" style="min-width: 320px; height: 240px; margin: 0 auto"></div>

	</body>
  </body>
</html>

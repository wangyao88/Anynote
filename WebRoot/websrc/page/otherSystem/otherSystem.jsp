<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>其他系统</title>
    <style type="text/css">
       .img{
           width : 100;
           height : 100;
           align : center;
       }
       #otherSyste_table{
           align : center;
           width: 100%;
           height : 100%;
       }
       .otherSystem_td{
          text-align : center;
       }
       #tip{
          width : 100;
          height : 50;
       }
    </style>
    <script type="text/javascript" src="<%=path%>/websrc/js/highcharts/js/jquery-1.7.2.js"></script>
    <script type="text/javascript">
	    $(function () {
		    $(document).ready(function () {
		    	var tipObj = $("#tip");
	              $(".img").hover(
            		  function () {
                		  var offset = $(this).offset();
                		  var left = offset.left - 250;
                		  var top = offset.top + 35;
                		  tipObj.css("position","absolute");
                		  tipObj.css({"left":left,"top":top});
                		  var alt = $(this).attr("alt");
                		  tipObj.css("font-size","25");
                		  tipObj.html(alt);
                		  tipObj.fadeIn("fast");
            		  },
            		  function () {
            			  tipObj.fadeOut("slow");
            		  }
            	  );
		    });
		});

		function openTab(systemName){
			var tabTitle,tabUrl;
			if(systemName == 'attendenceTime'){
				tabTitle = "考勤管理";
				tabUrl = "<%=path%>/websrc/page/attendenceTime/attendenceTimeDetail.jsp";
			}else if(systemName == 'workManagement'){
				tabTitle = "工作管理";
				tabUrl = "<%=path%>/websrc/page/workManagement/workManageMain.jsp";
			}
			Anynote.openTab(tabTitle, tabUrl);
	    }    
    </script>
  </head>
  
  <body>
    <table id="otherSyste_table">
       <tr>
          <td class="otherSystem_td">
             <a href="javascript:void(0)" onclick="openTab('attendenceTime');"><img class="img" alt="考勤管理" src="<%=path%>/websrc/image/otherSystem/Advertising.png"></a>
          </td>
          <td class="otherSystem_td">
             <a href="javascript:void(0)" onclick="openTab('workManagement');"><img class="img" alt="工作管理" src="<%=path%>/websrc/image/otherSystem/Advertising.png"></a>
          </td>
          <td class="otherSystem_td">
             <img class="img" alt="" src="<%=path%>/websrc/image/otherSystem/Painting.png">
          </td>
          <td class="otherSystem_td">
             <img class="img" alt="" src="<%=path%>/websrc/image/otherSystem/Illustrations.png">
          </td>
       </tr>
       <tr>
          <td class="otherSystem_td">
             <img class="img" alt="" src="<%=path%>/websrc/image/otherSystem/45.png">
          </td>
          <td class="otherSystem_td">
             <img class="img" alt="" src="<%=path%>/websrc/image/otherSystem/48.png">
          </td>
          <td class="otherSystem_td">
             <img class="img" alt="" src="<%=path%>/websrc/image/otherSystem/51.png">
          </td>
          <td class="otherSystem_td">
             <img class="img" alt="" src="<%=path%>/websrc/image/otherSystem/53.png">
          </td>
       </tr>
       <tr>
          <td class="otherSystem_td">
             <img class="img" alt="" src="<%=path%>/websrc/image/otherSystem/55.png">
          </td>
          <td class="otherSystem_td">
             <img class="img" alt="" src="<%=path%>/websrc/image/otherSystem/57.png">
          </td>
          <td class="otherSystem_td">
             <img class="img" alt="" src="<%=path%>/websrc/image/otherSystem/59.png">
          </td>
          <td class="otherSystem_td">
             <img class="img" alt="" src="<%=path%>/websrc/image/otherSystem/61.png">
          </td>
       </tr>
    </table>
    <div id="tip"></div>
  </body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String newNoteContent = "-";
if(session.getAttribute("newNoteContent") != null){
     newNoteContent = session.getAttribute("newNoteContent").toString();
}
%>
<html>
  <head>
    <base href="<%=basePath%>">
  </head>
  <body>
        <marquee height="360px" 
                 direction="up">
           <%=newNoteContent%>
        </marquee>
  </body>
</html>

<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@page import="global.Constants"%>
<%@ page import="global.security.SessionUtils"%>
<%@ page import="util.MessageUtils"%>
<html>
<head>
	<%
		String baseUrl = request.getContextPath();
		String index = request.getParameter("index");
	%>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<script type="text/javascript">
		// 当前查看图片下标
		var currentIndex = "<%=index%>";
		// 图片大小
		var galleryWindow = Ext.getCmp('galleryWindow');
		var galleryImgWidth = galleryWindow.getWidth() - 30;
		var galleryImgHeight = galleryWindow.getHeight() - 40;
		// 图片数据Store
		var pictureListStore = Ext.getCmp('pictureDataView').getStore();
		$(document).ready(function(){
			// 鼠标样式设定
			$('#galleriaImage').mousemove(function(e){
				var mouseX = e.pageX;
				var targetElemX = $(this).position().left;
				if((mouseX - targetElemX) < galleryImgWidth/2){
					$(this).removeClass('cursorRight');
					$(this).addClass('cursorLeft');
				}else{
					$(this).removeClass('cursorLeft');
					$(this).addClass('cursorRight');
				}
			});

			// 图片切换事件
			$('#galleriaImage').click(function(e){
				var mouseX = e.pageX;
				var targetElemX = $(this).position().left;
				if((mouseX - targetElemX) < galleryImgWidth/2){
					prev();
				}else{
					next();
				}
			});
			// 窗口大小改变事件
			galleryWindow.on('resize',function(){
				galleryImgWidth = galleryWindow.getWidth() - 30;
				galleryImgHeight = galleryWindow.getHeight() - 40;
				show(currentIndex);
			});
			// 显示图片
			show(currentIndex);

			// 显示图片
			function show(index){
				$("#galleriaImage").html('<img src="<%=baseUrl%>/websrc/image/loading.gif"/>')
				// 图片Store
				var currentRecord = pictureListStore.getAt(index);
				var url = "<%=baseUrl + MessageUtils.setParamMessage(Constants.UPLOAD_PICTURE_PATH, new String[]{SessionUtils.getCurrentUserId()}) %>/"+currentRecord.get('lpath');
				// 显示图片
				var image = new Image();
				image.onload = function (){
					getThumbImage(image, galleryImgWidth, galleryImgHeight);
					$("#galleriaImage").html(image);
		        }
				image.src = url;
				// 设定标题
				galleryWindow.setTitle('查看图片 - '+(Number(currentIndex)+1)+'张/'+pictureListStore.getCount()+'张');
			}
			// 上一张
			function prev(){
				if(currentIndex==0){
					currentIndex = pictureListStore.getCount() - 1;
				}else{
					currentIndex--;
				}
				show(currentIndex);
			}
			// 下一张
			function next(){
				if(currentIndex==pictureListStore.getCount() - 1){
					currentIndex = 0;
				}else{
					currentIndex++;
				}
				show(currentIndex);
			}
			// 生成缩略图
			function getThumbImage(image, toWidth, toHeight){
				// 设置图片尺寸
				var imgWidth = image.width;
				var imgHeight = image.height;
				var ratio = 1;
				// 宽度设定
				if(imgWidth>toWidth){
					ratio = toWidth / imgWidth;
					imgWidth = toWidth;
					imgHeight = imgHeight * ratio;
				}
				// 高度设定
				if(imgHeight>toHeight){
					ratio = toHeight / imgHeight;
					imgWidth = imgWidth * ratio;
					imgHeight = toHeight;
				}
				image.width = imgWidth;
				image.height = imgHeight;
			}
		});
	</script>
</head>
<body>
<table style="width:100%;height:100%;text-align:center;">
	<tr>
		<td id="galleriaImage"></td>
	</tr>
</table>
</body>
</html>
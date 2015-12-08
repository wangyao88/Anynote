﻿<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import="global.security.SessionUtils"%>
<html>
<head>
	<%
		String baseUrl = request.getContextPath();
		String albumId = request.getParameter("albumId");
	%>
	<script src="<%=baseUrl %>/websrc/js/ext-3.3.0/ux/SWFUpload2.2/js/handlers.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
			// 工具栏
		    var pictureUploadToolbar = new Ext.Toolbar({
		    	renderTo: 'pictureUploadToolBarDiv',
		    	items: [
				    new Ext.Button({
					    id: 'pictureUpload-add-button',
						text: '添加',
						iconCls: 'add'
					}),
					new Ext.Button({
					    id: 'pictureUpload-delete-button',
						text: '删除',
						iconCls: 'delete'
					}),
					new Ext.Button({
					    id: 'pictureUpload-upload-button',
						text: '上传',
						iconCls: 'upload'
					}),
					new Ext.Button({
					    id: 'pictureUpload-cancel-button',
						text: '取消',
						iconCls: 'stop2'
					}),
					new Ext.Button({
					    id: 'pictureUpload-close-button',
						text: '关闭',
						iconCls: 'cross'
					})
				]
			});

			// 笔记分类数据源
		    var pictureUploadStore = new Ext.data.JsonStore({
		        root: 'datas',
		        totalProperty: 'results',
		        fields: ['fileName']
		    });
		 	// 图片上传表格
		 	var sm = new Ext.grid.CheckboxSelectionModel();
		    var pictureUploadGrid = new Ext.grid.GridPanel({
				id: 'pictureUploadGrid',
				border: false,
				columnLines: true,
				stateful: true,
			    autoScroll: 'auto',
			    clicksToEdit: 1,
			    height: 395,
		        store: pictureUploadStore,
		        cm: new Ext.grid.ColumnModel({
		            defaults: {
		                width: 200,
		                sortable: true
		            },
			        columns: [
						sm,
						new Ext.grid.RowNumberer({header:'№'}),
						{id:'fileId',header: '文件ID', width: 200, sortable: true, dataIndex: 'fileId', hidden: true},
			            {id:'fileName',header: '文件名', width: 250, sortable: true, dataIndex: 'fileName'},
			            {id:'status',header: '状态', width: 150, sortable: true, dataIndex: 'status'}
			        ]
		        }),
		        sm: sm,
		        columnLines: true
		    });

			// 查看笔记面板
			var pictureUploadPanel = new Ext.Panel({
				renderTo: 'pictureUploadPanelDiv',
				layout: 'fit',
				border: false,
				autoScroll: true,
				items: [pictureUploadGrid],
				bbar: new Ext.Panel({
					contentEl: 'divStatus',
					border: false,
					bodyStyle: 'background-color:transparent;padding:5px;'
				})
			});
			pictureWindow.on("beforeclose", function(){swfu.destroy();});

			var swfu = new SWFUpload({
				flash_url : "<%=baseUrl %>/websrc/js/ext-3.3.0/ux/SWFUpload2.2/swfupload.swf",
				upload_url: "<%=baseUrl %>/pictureAction.do?method=upload",	
				post_params: {albumId: 1},
				file_post_name : "fileData",
				file_size_limit : "1MB",
				file_types : "*.jpg;*.jpeg;*.png;",
				file_types_description : "图片文件",
				file_upload_limit : 10,  //配置上传个数
				file_queue_limit : 0,
				use_query_string: true,
				debug: false,

				// Button settings
				button_image_url: "<%=baseUrl %>/websrc/js/ext-3.3.0/resources/icons/add-upload.png",
				button_width: "50",
				button_height: "20",
				button_placeholder_id: "pictureUpload-add-button",
				button_text: '添加',
				button_text_style: '',
				button_text_left_padding: 17,
				button_cursor: SWFUpload.CURSOR.HAND,
				button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
				
				file_queued_handler : fileQueued,
				file_queue_error_handler : fileQueueError,
				file_dialog_complete_handler : fileDialogComplete,
				upload_start_handler : uploadStart,
				upload_progress_handler : uploadProgress,
				upload_error_handler : uploadError,
				upload_success_handler : uploadSuccess,
				upload_complete_handler : uploadComplete,
				queue_complete_handler : queueComplete	
			});	

			
			// 添加按钮
		    $("#pictureUpload-add-button").click(function(){
		    	$("#SWFUpload_0").click();
			});

		 	// 删除按钮
		    $("#pictureUpload-delete-button").click(function(){
		    	var records = Ext.getCmp("pictureUploadGrid").getSelectionModel().getSelections();
				if(records.length < 1){
					Ext.Msg.alert("提示", "请至少选择一条数据.");
				}else{
					var albumIds = "";
					for(var i=0;i<records.length;i++){
						var record = records[i];
						pictureUploadStore.remove(record);
						swfu.cancelUpload(record.get("fileId"), false);
					}
					var status = $("#divStatus").html(pictureUploadStore.getCount() + " 个文件等待上传.");
				}
			});

		 	// 上传按钮
		    $("#pictureUpload-upload-button").click(function(){
			    var albumId = "<%=albumId%>";
			    if(pictureUploadStore.getCount()<1){
			    	Ext.Msg.alert("提示", "请添加图片.");
				}
				if(albumId==""){
					Ext.Msg.alert("提示", "请选择相册.");
				}
				else{
					swfu.setPostParams({albumId: albumId, userId: "<%=SessionUtils.getCurrentUserId()%>", actionType: "UPLOAD_ACTION"});
					swfu.startUpload();
				}
			});

			// 取消按钮
		    $("#pictureUpload-cancel-button").click(function(){
			    if(currentUploadRecord!=null){
			    	swfu.cancelUpload(currentUploadRecord.get("fileId"), false);
			    	currentUploadRecord.set("status", "已取消");
				}
			});

			// 关闭按钮
		    $("#pictureUpload-close-button").click(function(){
		    	pictureWindow.close();
			});

	});
</script>
</head>
<body>
<div id="pictureUploadDiv" style="width:100%;height:100%;">
	<div id="pictureUploadToolBarDiv"></div>
	<div id="pictureUploadPanelDiv" style="width:100%;height:100%;"></div>
</div>
<div id="divStatus">准备就绪</div>

</body>
</html>
package action;

import global.beanUtils.BeanUtils;
import global.comparator.NameComparator;
import global.comparator.SizeComparator;
import global.comparator.TypeComparator;
import global.security.SessionUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import service.NoteService;
import util.DateUtils;
import util.FileUtils;
import util.MessageUtils;
import util.ServletHelp;
import util.StringUtils;
import domain.Account;
import domain.Note;

public class NoteAction extends BaseAction {
	private NoteService noteService = null;

	public void setNoteService(NoteService noteService) {
		this.noteService = noteService;
	}

	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map map = request.getParameterMap();
		Note paramNote = new Note();
		BeanUtils.populate(paramNote, map);

		JSONArray datas = new JSONArray();
		List noteList = this.noteService.selectByCriteriaForPaging(paramNote);
		if (noteList != null) {
			datas = JSONArray.fromObject(noteList);
		}
		int count = this.noteService.countByCriteria(paramNote);

		JSONObject res = new JSONObject();
		res.put("success", Boolean.valueOf(true));
		res.put("datas", datas);
		res.put("results", Integer.valueOf(count));
		ServletHelp.outRequestForJson(request, response, res.toString());
		return null;
	}

	public ActionForward getNote(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String noteId = request.getParameter("noteId");

		Note note = this.noteService.selectByPrimaryKey(Integer
				.parseInt(noteId));

		JSONObject res = JSONObject.fromObject(note);
		res.put("success", Boolean.valueOf(true));
		ServletHelp.outRequestForJson(request, response, res.toString());
		return null;
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map map = ServletHelp.getParameterMap(request);
		map.put("type", request.getParameter("type"));

		String savePath = ServletHelp.getRealPath(request, MessageUtils
				.setParamMessage("/websrc/file/{0}/picture",
						new String[] { SessionUtils.getCurrentUserId() }));
		map.put("savePath", savePath);

		String domain = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + request.getContextPath();
		map.put("domain", domain);

		this.noteService.save(map);

		JSONObject res = new JSONObject();
		res.put("success", Boolean.valueOf(true));
		String result = res.toString();

		ServletHelp.outRequestForJson(request, response, result);
		return null;
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String noteIdStr = request.getParameter("noteIds");

		if (StringUtils.isNotEmpty(noteIdStr)) {
			this.noteService.deleteNote(noteIdStr);
		}

		JSONObject res = new JSONObject();
		res.put("success", Boolean.valueOf(true));
		String result = res.toString();

		ServletHelp.outRequestForJson(request, response, result);
		return null;
	}

	public ActionForward returnNote(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String noteIdStr = request.getParameter("noteIds");

		if (StringUtils.isNotEmpty(noteIdStr)) {
			this.noteService.returnNote(noteIdStr);
		}

		JSONObject res = new JSONObject();
		res.put("success", Boolean.valueOf(true));
		String result = res.toString();

		ServletHelp.outRequestForJson(request, response, result);
		return null;
	}

	public ActionForward move(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String noteIdStr = request.getParameter("noteIds");

		String moveCategoryId = request.getParameter("moveCategoryId");

		if ((StringUtils.isNotEmpty(noteIdStr))
				&& (StringUtils.isNotEmpty(moveCategoryId))) {
			this.noteService.moveNote(noteIdStr, moveCategoryId);
		}

		JSONObject res = new JSONObject();
		res.put("success", Boolean.valueOf(true));
		String result = res.toString();

		ServletHelp.outRequestForJson(request, response, result);
		return null;
	}

	public ActionForward clear(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String noteIdStr = request.getParameter("noteIds");

		if (StringUtils.isNotEmpty(noteIdStr)) {
			this.noteService.deleteByNoteIds(noteIdStr);
		}

		JSONObject res = new JSONObject();
		res.put("success", Boolean.valueOf(true));
		String result = res.toString();

		ServletHelp.outRequestForJson(request, response, result);
		return null;
	}

	public ActionForward sendEmail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String emails = request.getParameter("emails");

		String noteId = request.getParameter("noteId");

		boolean hasSend = this.noteService.sendEmail(noteId, emails);

		JSONObject res = new JSONObject();
		if (hasSend)
			res.put("success", Boolean.valueOf(true));
		else {
			res.put("success", Boolean.valueOf(false));
		}
		String result = res.toString();

		ServletHelp.outRequestForJson(request, response, result);
		return null;
	}

	public ActionForward print(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String noteId = request.getParameter("noteId");

		Note note = this.noteService.selectByPrimaryKey(Integer
				.parseInt(noteId));

		String fileName = "/websrc/file/temp/printNote.html";

		String filePath = ServletHelp.getRealPath(request, fileName);

		FileUtils.delete(filePath);

		String html = "<html><head><title>Anynote</title><meta http-equiv='Content-Type' content='text/html; charset=utf-8' /></head><body><center><h3>"
				+ note.getTitle()
				+ "</h3></center>"
				+ note.getContent()
				+ "</body></html>";
		FileUtils.createFileWithEncoder(filePath, html, "UTF-8");

		JSONObject res = JSONObject.fromObject(note);
		res.put("success", Boolean.valueOf(true));
		res.put("url", fileName);
		ServletHelp.outRequestForJson(request, response, res.toString());
		return null;
	}

	public ActionForward upload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		JSONObject res = new JSONObject();
		res.put("success", Boolean.valueOf(true));
		res.put("error", Integer.valueOf(1));

		String savePath = ServletHelp.getRealPath(request, MessageUtils
				.setParamMessage("/websrc/file/{0}/picture",
						new String[] { SessionUtils.getCurrentUserId() }));

		String saveUrl = request.getContextPath()
				+ MessageUtils.setParamMessage("/websrc/file/{0}/picture",
						new String[] { SessionUtils.getCurrentUserId() });

		String[] fileTypes = { "gif", "jpg", "jpeg", "png", "bmp" };

		String ymd = DateUtils.formatDate2Str("yyyyMMdd");
		savePath = savePath + "/" + ymd;
		saveUrl = saveUrl + "/" + ymd;
		File dirFile = new File(savePath);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		List items = upload.parseRequest(request);
		Iterator itr = items.iterator();
		while (itr.hasNext()) {
			FileItem item = (FileItem) itr.next();
			String fileName = item.getName();
			if (!item.isFormField()) {
				long maxSize = 1000000L;
				if (item.getSize() > maxSize) {
					res.put("message", "上传文件大小超过限制。");
					response.getWriter().print(res.toString());
					return null;
				}

				String fileExt = fileName.substring(
						fileName.lastIndexOf(".") + 1).toLowerCase();
				if (!Arrays.asList(fileTypes).contains(fileExt)) {
					res.put("message", "上传文件扩展名是不允许的扩展名。");
					response.getWriter().print(res.toString());
					return null;
				}

				String newFileName = DateUtils.formatDate2Str("yyyyMMddHHmmss")
						+ "_" + new Random().nextInt(1000) + "." + fileExt;

				File uploadedFile = new File(savePath, newFileName);
				item.write(uploadedFile);

				res.put("error", Integer.valueOf(0));
				res.put("url", saveUrl + "/" + newFileName);
				response.getWriter().print(res.toString());
				return null;
			}
		}
		return null;
	}

	public ActionForward fileManager(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String rootPath = ServletHelp.getRealPath(request, MessageUtils
				.setParamMessage("/websrc/file/{0}/picture",
						new String[] { SessionUtils.getCurrentUserId() }))
				+ "/";

		String rootUrl = request.getContextPath()
				+ MessageUtils.setParamMessage("/websrc/file/{0}/picture",
						new String[] { SessionUtils.getCurrentUserId() }) + "/";

		String[] fileTypes = { "gif", "jpg", "jpeg", "png", "bmp" };

		String path = request.getParameter("path") != null ? request
				.getParameter("path") : "";
		String currentPath = rootPath + path;
		String currentUrl = rootUrl + path;
		String currentDirPath = path;
		String moveupDirPath = "";
		if (!"".equals(path)) {
			String str = currentDirPath.substring(0,
					currentDirPath.length() - 1);
			moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0,
					str.lastIndexOf("/") + 1) : "";
		}

		String order = request.getParameter("order") != null ? request
				.getParameter("order").toLowerCase() : "name";

		File currentPathFile = new File(currentPath);

		List fileList = new ArrayList();
		if (currentPathFile.listFiles() != null) {
			for (File file : currentPathFile.listFiles()) {
				Hashtable hash = new Hashtable();
				String fileName = file.getName();
				if (file.isDirectory()) {
					hash.put("is_dir", Boolean.valueOf(true));
					hash.put("has_file",
							Boolean.valueOf(file.listFiles() != null));
					hash.put("filesize", Long.valueOf(0L));
					hash.put("is_photo", Boolean.valueOf(false));
					hash.put("filetype", "");
				} else if (file.isFile()) {
					String fileExt = fileName.substring(
							fileName.lastIndexOf(".") + 1).toLowerCase();
					hash.put("is_dir", Boolean.valueOf(false));
					hash.put("has_file", Boolean.valueOf(false));
					hash.put("filesize", Long.valueOf(file.length()));
					hash.put(
							"is_photo",
							Boolean.valueOf(Arrays.asList(fileTypes).contains(
									fileExt)));
					hash.put("filetype", fileExt);
				}
				hash.put("filename", fileName);
				hash.put("datetime",
						new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Long
								.valueOf(file.lastModified())));
				fileList.add(hash);
			}

		}

		if ("size".equals(order))
			Collections.sort(fileList, new SizeComparator());
		else if ("type".equals(order))
			Collections.sort(fileList, new TypeComparator());
		else {
			Collections.sort(fileList, new NameComparator());
		}
		JSONObject result = new JSONObject();
		result.put("moveup_dir_path", moveupDirPath);
		result.put("current_dir_path", currentDirPath);
		result.put("current_url", currentUrl);
		result.put("total_count", Integer.valueOf(fileList.size()));
		result.put("file_list", fileList);

		JSONObject res = new JSONObject();
		res.put("success", Boolean.valueOf(true));
		ServletHelp.outRequestForJson(request, response, result.toString());
		return null;
	}
	
	public ActionForward queryNewNote(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		String countStr = request.getParameter("count");
		if(countStr == null){
			countStr = "10";
		}
		String content = this.noteService.queryNewNote(Integer.valueOf(countStr));
		request.getSession().setAttribute("newNoteContent", content);
		return null;
	}
}
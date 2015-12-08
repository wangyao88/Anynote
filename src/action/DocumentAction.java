package action;

import domain.Document;
import global.beanUtils.BeanUtils;
import global.security.SessionUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import service.DocumentService;
import util.FileUtils;
import util.MessageUtils;
import util.ServletHelp;
import util.StringUtils;

public class DocumentAction extends BaseAction
{
  private DocumentService documentService = null;

  public void setDocumentService(DocumentService documentService)
  {
    this.documentService = documentService;
  }

  public ActionForward queryForPaging(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Map map = request.getParameterMap();
    Document paramDocument = new Document();
    BeanUtils.populate(paramDocument, map);
    int parentId = Integer.parseInt(request.getParameter("documentId"));

    String go = request.getParameter("go");

    if ((StringUtils.isNotEmpty(go)) && (go.equals("prev"))) {
      Document currentDocument = this.documentService.selectByPrimaryKey(Integer.valueOf(parentId));
      if (currentDocument != null) {
        parentId = currentDocument.getParentId().intValue();
      }

    }

    List documentList = new ArrayList();
    JSONArray datas = new JSONArray();
    paramDocument.setDocumentId(null);
    paramDocument.setParentId(Integer.valueOf(parentId));
    documentList = this.documentService.selectByCriteriaForPaging(paramDocument);
    if (documentList != null) {
      datas = JSONArray.fromObject(documentList);
    }
    int count = this.documentService.countByCriteria(paramDocument);

    JSONObject res = new JSONObject();
    res.put("datas", datas);
    res.put("results", Integer.valueOf(count));
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }

  public ActionForward getDocument(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    JSONObject res = new JSONObject();

    String documentId = request.getParameter("documentId");
    if (StringUtils.isNotEmpty(documentId)) {
      Document document = this.documentService.selectByPrimaryKey(Integer.valueOf(Integer.parseInt(documentId)));
      if (document != null) {
        res = JSONObject.fromObject(document);
      }
    }
    res.put("success", Boolean.valueOf(true));
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }

  public ActionForward getPrevDocument(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    JSONObject res = new JSONObject();

    String documentId = request.getParameter("documentId");
    if (StringUtils.isNotEmpty(documentId)) {
      Document document = this.documentService.selectByPrimaryKey(Integer.valueOf(Integer.parseInt(documentId)));
      if (document != null) {
        Document prevDocument = new Document();
        if (document.getParentId().intValue() == 0) {
          prevDocument.setDocumentId(Integer.valueOf(0));
          prevDocument.setDocumentName("我的文档");
        } else {
          prevDocument = this.documentService.selectByPrimaryKey(document.getParentId());
        }
        if (prevDocument != null) {
          res = JSONObject.fromObject(prevDocument);
        }
      }
    }
    res.put("success", Boolean.valueOf(true));
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }

  public ActionForward getDocumentTree(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String parentId = request.getParameter("parentId");
    JSONArray res = this.documentService.selectDocumentForTree(parentId);
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }

  public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    JSONObject res = new JSONObject();

    String documentId = request.getParameter("documentId");
    String documentName = request.getParameter("documentName");
    String parentId = request.getParameter("parentId");
    String isleaf = request.getParameter("isleaf");

    Document document = new Document();
    if (StringUtils.isNotEmpty(documentId)) {
      document.setDocumentId(Integer.valueOf(Integer.parseInt(documentId)));
    }
    if (StringUtils.isNotEmpty(documentName)) {
      document.setDocumentName(documentName);
    }
    if (StringUtils.isNotEmpty(parentId)) {
      document.setParentId(Integer.valueOf(Integer.parseInt(parentId)));
    }
    if (StringUtils.isNotEmpty(isleaf)) {
      document.setIsleaf(isleaf);
    }
    document.setStatus("1");

    Document paramDocument = new Document();
    paramDocument.setParentId(Integer.valueOf(Integer.parseInt(parentId)));

    List<Document> documentList = new ArrayList<Document>();
    documentList = this.documentService.selectByCriteriaForPaging(paramDocument);
    if (documentList != null) {
      for (Document tempDocument : documentList) {
        if ((tempDocument.getDocumentName().equals(documentName)) && (tempDocument.getIsleaf().equals(isleaf))) {
          res.put("success", Boolean.valueOf(false));
          res.put("message", MessageUtils.setParamMessage("创建失败，文件名 {0} 重复.", new String[] { documentName }));
          ServletHelp.outRequestForJson(request, response, res.toString());
          return null;
        }
      }

    }

    if (StringUtils.isNotEmpty(documentId))
    {
      this.documentService.update(document);
    }
    else {
      this.documentService.insert(document);
    }

    res.put("success", Boolean.valueOf(true));
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }

  public ActionForward move(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String documentIds = request.getParameter("documentIds");
    String parentId = request.getParameter("parentId");

    if ((StringUtils.isNotEmpty(documentIds)) && (StringUtils.isNotEmpty(parentId))) {
      this.documentService.move(documentIds, parentId);
    }

    JSONObject res = new JSONObject();
    res.put("success", Boolean.valueOf(true));
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }

  public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String uploadFilePath = ServletHelp.getRealPath(request, 
      MessageUtils.setParamMessage("/websrc/file/{0}/document", new String[] { SessionUtils.getCurrentUserId() }));

    String documentIds = request.getParameter("documentIds");

    if (StringUtils.isNotEmpty(documentIds)) {
      this.documentService.delete(documentIds, uploadFilePath);
    }

    JSONObject res = new JSONObject();
    res.put("success", Boolean.valueOf(true));
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }

  public ActionForward upload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String userId = request.getParameter("userId");

    String documentId = request.getParameter("documentId");

    String uploadFilePath = ServletHelp.getRealPath(request, 
      MessageUtils.setParamMessage("/websrc/file/{0}/document", new String[] { SessionUtils.getCurrentUserId() }));
    DiskFileItemFactory factory = new DiskFileItemFactory();
    ServletFileUpload upload = new ServletFileUpload(factory);
    List items = null;

    items = upload.parseRequest(request);
    if ((StringUtils.isNotEmpty(documentId)) && (items != null)) {
      Iterator it = items.iterator();
      while (it.hasNext())
      {
        FileItem fi = (FileItem)it.next();
        if ("fileData".equals(fi.getFieldName())) {
          String fileUUID = FileUtils.getUUID();
          String fileType = FileUtils.getFileTypeByName(fi.getName());
          String link = uploadFilePath + "/" + fileUUID + "." + fileType;

          FileUtils.writeFileToDisk(fi.get(), link);

          Document document = new Document();
          document.setDocumentName(FileUtils.getFileName(fi.getName()));
          document.setLink(fileUUID + "." + fileType);
          document.setType(FileUtils.getFileTypeByName(fi.getName()));
          document.setSize(Integer.valueOf((int)fi.getSize()));
          document.setTags("");
          document.setParentId(Integer.valueOf(Integer.parseInt(documentId)));
          document.setIsleaf("1");
          document.setStatus("1");
          document.setDelflag("1");
          document.setCreateUser(userId);
          document.setUpdateUser(userId);

          this.documentService.insert(document);
        }
      }

    }

    JSONObject res = new JSONObject();
    res.put("success", Boolean.valueOf(true));
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }

  public ActionForward downloadDocument(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String documentId = request.getParameter("documentId");
    if (StringUtils.isNotEmpty(documentId))
    {
      Document document = this.documentService.selectByPrimaryKey(Integer.valueOf(Integer.parseInt(documentId)));
      if (document != null)
      {
        String filePath = ServletHelp.getRealPath(request, 
          MessageUtils.setParamMessage("/websrc/file/{0}/document", new String[] { SessionUtils.getCurrentUserId() }));
        String url = filePath + "/" + document.getLink();
        String fileName = document.getDocumentName() + "." + document.getType();

        ServletHelp.download(mapping, form, request, response, url, fileName);
      }
    }
    return null;
  }

  public ActionForward downloadDocumentZip(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String documentIdStrs = request.getParameter("documentIds");
    if (StringUtils.isNotEmpty(documentIdStrs)) {
      String[] documentIds = documentIdStrs.split(",");
      int length = documentIds.length;
      String[] urls = new String[length];
      String[] fileNames = new String[length];

      String filePath = ServletHelp.getRealPath(request, 
        MessageUtils.setParamMessage("/websrc/file/{0}/document", new String[] { SessionUtils.getCurrentUserId() }));
      for (int i = 0; i < length; i++)
      {
        Document document = this.documentService.selectByPrimaryKey(Integer.valueOf(Integer.parseInt(documentIds[i])));
        if (document != null) {
          String url = filePath + "/" + document.getLink();
          String fileName = document.getDocumentName() + "." + document.getType();
          urls[i] = url;
          fileNames[i] = fileName;
        }

      }

      ServletHelp.downloadZip(mapping, form, request, response, urls, fileNames);
    }
    return null;
  }
}
package service.impl;

import dao.DocumentDAO;
import domain.Document;
import global.Constants;
import global.security.SessionUtils;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import service.DocumentService;
import util.DateUtils;
import util.FileUtils;
import util.StringUtils;

public class DocumentServiceImpl
  implements DocumentService
{
  private DocumentDAO documentDao = null;

  public void setDocumentDao(DocumentDAO documentDao)
  {
    this.documentDao = documentDao;
  }

  public int countByCriteria(Document paramDocument)
  {
    paramDocument = getConditions(paramDocument);
    int count = this.documentDao.countByCriteria(paramDocument);
    return count;
  }

  public Document selectByPrimaryKey(Integer documentId)
  {
    return this.documentDao.selectByPrimaryKey(documentId.intValue());
  }

  public List<Document> selectByCriteria(Document paramDocument)
  {
    paramDocument = getConditions(paramDocument);
    List documentList = this.documentDao.selectByCriteria(paramDocument);
    if ((documentList != null) && (documentList.size() != 0)) {
      return documentList;
    }
    return null;
  }

  public List<Document> selectByCriteriaForPaging(Document paramDocument)
  {
    paramDocument = getConditions(paramDocument);
    List<Document> documentList = this.documentDao.selectByCriteriaForPaging(
      paramDocument, paramDocument.getStart(), paramDocument.getLimit());
    if ((documentList != null) && (documentList.size() != 0)) {
      List<Document> resultList = new ArrayList<Document>();
      for (Document document : documentList)
      {
        document.setCreateDateStr(DateUtils.formatDate2Str(document.getCreateTime()));
        resultList.add(document);
      }
      return resultList;
    }
    return null;
  }

  public JSONArray selectDocumentForTree(String parentId)
  {
    JSONArray res = new JSONArray();

    Document paramDocument = new Document();
    if (StringUtils.isNotEmpty(parentId)) {
      paramDocument.setParentId(Integer.valueOf(Integer.parseInt(parentId)));
    }

    List documentList = new ArrayList();
    documentList = selectByCriteria(paramDocument);
    res = getDocumentTreeFromList(documentList, parentId);
    return res;
  }

  public void insert(Document document)
  {
    document.setDelflag("1");

    Timestamp sysdate = new Timestamp(System.currentTimeMillis());
    document.setCreateUser(SessionUtils.getCurrentUserId());
    document.setCreateTime(sysdate);
    document.setUpdateUser(SessionUtils.getCurrentUserId());
    document.setUpdateTime(sysdate);
    this.documentDao.insert(document);
  }

  public void update(Document document)
  {
    Timestamp sysdate = new Timestamp(System.currentTimeMillis());
    document.setUpdateUser(SessionUtils.getCurrentUserId());
    document.setUpdateTime(sysdate);
    this.documentDao.updateByPrimaryKey(document);
  }

  public void delete(String documentIds, String uploadFilePath)
    throws Exception
  {
    String[] documentIdArr = documentIds.split(",");
    for (int i = 0; i < documentIdArr.length; i++)
    {
      List<Document> documentList = new ArrayList<Document>();

      Document parentDocument = new Document();
      if ("0".equals(documentIdArr[i]))
        parentDocument.setDocumentId(Integer.valueOf(0));
      else {
        parentDocument = selectByPrimaryKey(Integer.valueOf(Integer.parseInt(documentIdArr[i])));
      }
      getDocumentList(parentDocument, documentList);

      for (Document document : documentList) {
        if ("1".equals(document.getIsleaf()))
        {
          FileUtils.delete(uploadFilePath + "/" + document.getLink());
        }

        this.documentDao.deleteByPrimaryKey(document.getDocumentId().intValue());
      }
    }
  }

  public void move(String documentIds, String parentId)
  {
    String[] documentIdArr = documentIds.split(",");
    for (int i = 0; i < documentIdArr.length; i++) {
      Document document = new Document();
      document.setDocumentId(Integer.valueOf(Integer.parseInt(documentIdArr[i])));
      document.setParentId(Integer.valueOf(Integer.parseInt(parentId)));

      update(document);
    }
  }

  private JSONArray getDocumentTreeFromList(List<Document> documentList, String parentId)
  {
    JSONArray results = new JSONArray();
    if ("-1".equals(parentId)) {
      JSONObject document = new JSONObject();
      document.put("id", "0");
      document.put("text", "我的文档");
      document.put("leaf", Boolean.valueOf(false));
      results.add(document);
    }
    else if (documentList != null) {
      for (int i = 0; i < documentList.size(); i++) {
        if (((Document)documentList.get(i)).getIsleaf().equals("0")) {
          JSONObject documentNode = new JSONObject();
          documentNode.put("id", ((Document)documentList.get(i)).getDocumentId());
          documentNode.put("text", ((Document)documentList.get(i)).getDocumentName());
          documentNode.put("leaf", Constants.ISLEAF_MAP.get(((Document)documentList.get(i)).getIsleaf()));
          results.add(documentNode);
        }
      }
    }

    return results;
  }

  private void getDocumentList(Document parentDocument, List<Document> documentList)
  {
    documentList.add(parentDocument);

    Document paramDocument = new Document();
    paramDocument.setParentId(parentDocument.getDocumentId());
    List newDocumentList = selectByCriteria(paramDocument);
    if (newDocumentList != null)
      for (int i = 0; i < newDocumentList.size(); i++)
        getDocumentList((Document)newDocumentList.get(i), documentList);
  }

  private Document getConditions(Document parentDocument)
  {
    parentDocument.setCreateUser(SessionUtils.getCurrentUserId());
    return parentDocument;
  }
}
package service.impl;

import dao.NoteCategoryDAO;
import domain.Note;
import domain.NoteCategory;
import global.security.SessionUtils;
import java.sql.Timestamp;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import service.NoteCategoryService;
import service.NoteService;

public class NoteCategoryServiceImpl
  implements NoteCategoryService
{
  private NoteCategoryDAO noteCategoryDao = null;

  private NoteService noteService = null;

  public void setNoteCategoryDao(NoteCategoryDAO noteCategoryDao)
  {
    this.noteCategoryDao = noteCategoryDao;
  }

  public void setNoteService(NoteService noteService)
  {
    this.noteService = noteService;
  }

  public int countByCriteria(NoteCategory paramNoteCategory)
  {
    paramNoteCategory = getConditions(paramNoteCategory);
    int count = this.noteCategoryDao.countByCriteria(paramNoteCategory);
    return count;
  }

  public List<NoteCategory> selectByCriteria(NoteCategory paramNoteCategory)
  {
    paramNoteCategory = getConditions(paramNoteCategory);
    List categoryList = this.noteCategoryDao.selectByCriteria(paramNoteCategory);
    if ((categoryList != null) && (categoryList.size() != 0)) {
      return categoryList;
    }
    return null;
  }

  public JSONArray selectNoteCategoryForTree()
  {
    JSONArray res = new JSONArray();

    NoteCategory paramCategory = new NoteCategory();
    paramCategory.setCreateUser(SessionUtils.getCurrentUserId());

    List categoryList = this.noteCategoryDao.selectCategoryForTree(paramCategory);
    res = getCategoryTreeFromList(categoryList);
    return res;
  }

  public List<NoteCategory> selectByCriteriaForPaging(NoteCategory paramNoteCategory)
  {
    paramNoteCategory = getConditions(paramNoteCategory);
    List categoryList = this.noteCategoryDao.selectByCriteriaForPaging(paramNoteCategory, paramNoteCategory.getStart(), paramNoteCategory.getLimit());
    if ((categoryList != null) && (categoryList.size() != 0)) {
      return categoryList;
    }
    return null;
  }

  public void insert(NoteCategory paramNoteCategory)
  {
    paramNoteCategory.setDelflag("1");

    Timestamp sysdate = new Timestamp(System.currentTimeMillis());
    paramNoteCategory.setCreateUser(SessionUtils.getCurrentUserId());
    paramNoteCategory.setCreateTime(sysdate);
    paramNoteCategory.setUpdateUser(SessionUtils.getCurrentUserId());
    paramNoteCategory.setUpdateTime(sysdate);
    this.noteCategoryDao.insert(paramNoteCategory);
  }

  public void update(NoteCategory paramNoteCategory)
  {
    Timestamp sysdate = new Timestamp(System.currentTimeMillis());
    paramNoteCategory.setUpdateUser(SessionUtils.getCurrentUserId());
    paramNoteCategory.setUpdateTime(sysdate);
    this.noteCategoryDao.updateByPrimaryKey(paramNoteCategory);
  }

  public void delete(int categoryId)
  {
    this.noteCategoryDao.deleteByPrimaryKey(categoryId);
  }

  private JSONArray getCategoryTreeFromList(List<NoteCategory> categoryList)
  {
    JSONArray results = new JSONArray();

    Note paramNote = new Note();
    paramNote.setDelflag("1");
    int noteCount = this.noteService.countByCriteria(paramNote);
    JSONObject allCategory = new JSONObject();
    allCategory.put("id", "allCategory");
    allCategory.put("text", "所有笔记 (" + noteCount + ")");
    allCategory.put("leaf", Boolean.valueOf(true));
    allCategory.put("attributes", "{textNoCount:'所有笔记'}");
    results.add(allCategory);

    if (categoryList != null) {
      JSONArray children = new JSONArray();
      NoteCategory temp = new NoteCategory();
      for (int i = 0; i < categoryList.size(); i++) {
        temp = (NoteCategory)categoryList.get(i);
        JSONObject categoryNode = new JSONObject();
        categoryNode.put("id", temp.getCategoryId());
        categoryNode.put("text", temp.getCategoryName() + "(" + temp.getNoteCount() + ")");
        categoryNode.put("leaf", Boolean.valueOf(true));
        categoryNode.put("attributes", "{textNoCount:'" + temp.getCategoryName() + "'}");
        children.add(categoryNode);
      }
      JSONObject category = new JSONObject();
      category.put("id", "0");
      category.put("text", "笔记分类");
      category.put("children", children);

      results.add(category);
    }

    JSONObject rubbish = new JSONObject();
    rubbish.put("id", "rubbish");
    rubbish.put("text", "回收站");
    rubbish.put("leaf", Boolean.valueOf(true));
    results.add(rubbish);

    return results;
  }

  private NoteCategory getConditions(NoteCategory paramNoteCategory)
  {
    paramNoteCategory.setCreateUser(SessionUtils.getCurrentUserId());
    return paramNoteCategory;
  }
}
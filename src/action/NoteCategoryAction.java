package action;

import domain.NoteCategory;
import global.beanUtils.BeanUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import service.NoteCategoryService;
import util.ServletHelp;
import util.StringUtils;

public class NoteCategoryAction extends BaseAction
{
  private NoteCategoryService noteCategoryService = null;

  public void setNoteCategoryService(NoteCategoryService noteCategoryService)
  {
    this.noteCategoryService = noteCategoryService;
  }

  public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    NoteCategory paramNoteCategory = new NoteCategory();

    List noteCategoryList = new ArrayList();
    JSONArray datas = new JSONArray();
    noteCategoryList = this.noteCategoryService.selectByCriteria(paramNoteCategory);
    if (noteCategoryList != null) {
      datas = JSONArray.fromObject(noteCategoryList);
    }

    JSONObject res = new JSONObject();
    res.put("datas", datas);
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }

  public ActionForward queryForPaging(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Map map = request.getParameterMap();
    NoteCategory paramNoteCategory = new NoteCategory();
    BeanUtils.populate(paramNoteCategory, map);

    List noteCategoryList = new ArrayList();
    JSONArray datas = new JSONArray();
    noteCategoryList = this.noteCategoryService.selectByCriteriaForPaging(paramNoteCategory);
    if (noteCategoryList != null) {
      datas = JSONArray.fromObject(noteCategoryList);
    }
    int count = this.noteCategoryService.countByCriteria(paramNoteCategory);

    JSONObject res = new JSONObject();
    res.put("datas", datas);
    res.put("results", Integer.valueOf(count));
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }

  public ActionForward getNoteCategoryTree(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    JSONArray res = this.noteCategoryService.selectNoteCategoryForTree();
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }

  public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Map map = request.getParameterMap();
    NoteCategory noteCategory = new NoteCategory();
    BeanUtils.populate(noteCategory, map);
    noteCategory.setStatus("1");

    String categoryId = request.getParameter("categoryId");
    if (StringUtils.isNotEmpty(categoryId))
    {
      this.noteCategoryService.update(noteCategory);
    }
    else {
      this.noteCategoryService.insert(noteCategory);
    }

    JSONObject res = new JSONObject();
    res.put("success", Boolean.valueOf(true));
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }

  public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String categoryId = request.getParameter("categoryId");

    if (StringUtils.isNotEmpty(categoryId))
    {
      this.noteCategoryService.delete(Integer.parseInt(categoryId));
    }

    JSONObject res = new JSONObject();
    res.put("success", Boolean.valueOf(true));
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }
}
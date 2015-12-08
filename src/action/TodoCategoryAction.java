package action;

import domain.TodoCategory;
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
import service.TodoCategoryService;
import util.ServletHelp;
import util.StringUtils;

public class TodoCategoryAction extends BaseAction
{
  private TodoCategoryService todoCategoryService = null;

  public void setTodoCategoryService(TodoCategoryService todoCategoryService)
  {
    this.todoCategoryService = todoCategoryService;
  }

  public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    TodoCategory paramTodoCategory = new TodoCategory();

    List todoCategoryList = new ArrayList();
    JSONArray datas = new JSONArray();
    todoCategoryList = this.todoCategoryService.selectByCriteria(paramTodoCategory);
    if (todoCategoryList != null) {
      datas = JSONArray.fromObject(todoCategoryList);
    }

    JSONObject res = new JSONObject();
    res.put("datas", datas);
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }

  public ActionForward getTodoCategoryTree(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    List todoCategoryList = new ArrayList();
    TodoCategory paramTodoCategory = new TodoCategory();
    todoCategoryList = this.todoCategoryService.selectByCriteria(paramTodoCategory);
    JSONArray res = getTodoCategoryTreeFromList(todoCategoryList);
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }

  public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Map map = request.getParameterMap();
    TodoCategory todoCategory = new TodoCategory();
    BeanUtils.populate(todoCategory, map);
    todoCategory.setStatus("1");
    todoCategory.setDelflag("1");

    String categoryId = request.getParameter("categoryId");
    if (StringUtils.isNotEmpty(categoryId))
    {
      this.todoCategoryService.update(todoCategory);
    }
    else {
      this.todoCategoryService.insert(todoCategory);
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
      this.todoCategoryService.delete(Integer.valueOf(Integer.parseInt(categoryId)));
    }

    JSONObject res = new JSONObject();
    res.put("success", Boolean.valueOf(true));
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }

  private JSONArray getTodoCategoryTreeFromList(List<TodoCategory> todoCategoryList)
  {
    JSONArray results = new JSONArray();

    JSONObject allTodo = new JSONObject();
    allTodo.put("id", "allTodo");
    allTodo.put("text", "所有任务");
    allTodo.put("leaf", Boolean.valueOf(true));
    results.add(allTodo);

    JSONObject uncompleteTodo = new JSONObject();
    uncompleteTodo.put("id", "uncompleteTodo");
    uncompleteTodo.put("text", "未完成任务");
    uncompleteTodo.put("leaf", Boolean.valueOf(true));
    results.add(uncompleteTodo);

    JSONObject completeTodo = new JSONObject();
    completeTodo.put("id", "completeTodo");
    completeTodo.put("text", "已完成任务");
    completeTodo.put("leaf", Boolean.valueOf(true));
    results.add(completeTodo);

    if (todoCategoryList != null) {
      JSONArray children = new JSONArray();
      TodoCategory temp = new TodoCategory();
      for (int i = 0; i < todoCategoryList.size(); i++) {
        temp = (TodoCategory)todoCategoryList.get(i);
        JSONObject todoCategoryNode = new JSONObject();
        todoCategoryNode.put("id", temp.getCategoryId());
        todoCategoryNode.put("text", temp.getCategoryName());
        todoCategoryNode.put("leaf", Boolean.valueOf(true));
        children.add(todoCategoryNode);
      }
      JSONObject todoCategory = new JSONObject();
      todoCategory.put("id", "0");
      todoCategory.put("text", "任务分类");
      todoCategory.put("children", children);
      results.add(todoCategory);
    }
    return results;
  }
}
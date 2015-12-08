package service.impl;

import dao.TodoCategoryDAO;
import domain.Todo;
import domain.TodoCategory;
import global.security.SessionUtils;
import java.sql.Timestamp;
import java.util.List;
import service.TodoCategoryService;
import service.TodoService;
import util.StringUtils;

public class TodoCategoryServiceImpl
  implements TodoCategoryService
{
  private TodoService todoService = null;

  private TodoCategoryDAO todoCategoryDao = null;

  public void setTodoCategoryDao(TodoCategoryDAO todoCategoryDao)
  {
    this.todoCategoryDao = todoCategoryDao;
  }

  public void setTodoService(TodoService todoService)
  {
    this.todoService = todoService;
  }

  public List<TodoCategory> selectByCriteria(TodoCategory paramTodoCategory)
  {
    paramTodoCategory = getConditions(paramTodoCategory);
    List todoCategoryList = this.todoCategoryDao.selectByCriteria(paramTodoCategory);
    return todoCategoryList;
  }

  public void insert(TodoCategory todoCategory)
  {
    todoCategory.setDelflag("1");

    Timestamp sysdate = new Timestamp(System.currentTimeMillis());
    todoCategory.setCreateUser(SessionUtils.getCurrentUserId());
    todoCategory.setCreateTime(sysdate);
    todoCategory.setUpdateUser(SessionUtils.getCurrentUserId());
    todoCategory.setUpdateTime(sysdate);
    this.todoCategoryDao.insert(todoCategory);
  }

  public void update(TodoCategory todoCategory)
  {
    Timestamp sysdate = new Timestamp(System.currentTimeMillis());
    todoCategory.setUpdateUser(SessionUtils.getCurrentUserId());
    todoCategory.setUpdateTime(sysdate);
    this.todoCategoryDao.updateByPrimaryKey(todoCategory);
  }

  public void delete(Integer categoryId)
  {
    Todo paramTodo = new Todo();
    paramTodo.setCategoryId(categoryId);
    List<Todo> todoList = this.todoService.selectByCriteria(paramTodo);
    String todoIdStr = "";
    for (Todo todo : todoList) {
      todoIdStr = todoIdStr + todo.getTodoId() + ",";
    }
    if (StringUtils.isNotEmpty(todoIdStr))
    {
      this.todoService.delete(todoIdStr);
    }

    this.todoCategoryDao.deleteByPrimaryKey(categoryId.intValue());
  }

  private TodoCategory getConditions(TodoCategory paramTodoCategory)
  {
    paramTodoCategory.setCreateUser(SessionUtils.getCurrentUserId());
    return paramTodoCategory;
  }
}
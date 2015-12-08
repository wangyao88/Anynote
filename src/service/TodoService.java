package service;

import dao.TodoDAO;
import domain.Todo;
import java.util.List;

public abstract interface TodoService
{
  public abstract void setTodoDao(TodoDAO paramTodoDAO);

  public abstract int countByCriteria(Todo paramTodo);

  public abstract List<Todo> selectByCriteriaForPaging(Todo paramTodo);

  public abstract List<Todo> selectByCriteria(Todo paramTodo);

  public abstract void insert(Todo paramTodo);

  public abstract void update(Todo paramTodo);

  public abstract void delete(String paramString);

  public abstract String queryCountAndCategoryByCriteria(Todo paramTodo);

  public abstract String queryCountAndYearByCriteria(Todo paramTodo);

  public abstract void saveBatch(List<Todo> list);

  public abstract boolean checkTomorrowTodo(String formatDate2Str);

  public abstract int querytodayTodoCountByCriteria(Todo paramTodo);
}
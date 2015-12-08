package dao;

import domain.Todo;
import java.util.List;

public abstract interface TodoDAO
{
  public abstract Todo selectByPrimaryKey(int paramInt);

  public abstract List<Todo> selectByCriteria(Todo paramTodo);

  public abstract List<Todo> selectByCriteriaForPaging(Todo paramTodo, int paramInt1, int paramInt2);

  public abstract int countByCriteria(Todo paramTodo);

  public abstract void insert(Todo paramTodo);

  public abstract int updateByPrimaryKey(Todo paramTodo);

  public abstract int deleteByPrimaryKey(int paramInt);

  public abstract List<Todo> queryCountAndCategoryByCriteria(Todo paramTodo);

  public abstract List<Todo> queryCountAndYearByCriteria(Todo paramTodo);

  public abstract void saveBatch(List<Todo> list);

  public abstract int querytodayTodoCountByCriteria(Todo paramTodo);
}
package dao;

import domain.TodoCategory;
import java.util.List;

public abstract interface TodoCategoryDAO
{
  public abstract TodoCategory selectByPrimaryKey(int paramInt);

  public abstract List<TodoCategory> selectByCriteria(TodoCategory paramTodoCategory);

  public abstract int countByCriteria(TodoCategory paramTodoCategory);

  public abstract void insert(TodoCategory paramTodoCategory);

  public abstract int updateByPrimaryKey(TodoCategory paramTodoCategory);

  public abstract int deleteByPrimaryKey(int paramInt);
}
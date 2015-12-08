package service;

import dao.TodoCategoryDAO;
import domain.TodoCategory;
import java.util.List;

public abstract interface TodoCategoryService
{
  public abstract void setTodoCategoryDao(TodoCategoryDAO paramTodoCategoryDAO);

  public abstract void setTodoService(TodoService paramTodoService);

  public abstract List<TodoCategory> selectByCriteria(TodoCategory paramTodoCategory);

  public abstract void insert(TodoCategory paramTodoCategory);

  public abstract void update(TodoCategory paramTodoCategory);

  public abstract void delete(Integer paramInteger);
}
package dao.impl;

import dao.TodoCategoryDAO;
import domain.TodoCategory;
import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class TodoCategoryDAOImpl extends SqlMapClientDaoSupport
  implements TodoCategoryDAO
{
  public TodoCategory selectByPrimaryKey(int id)
  {
    TodoCategory record = (TodoCategory)getSqlMapClientTemplate().queryForObject("an_todo_category.selectByPrimaryKey", Integer.valueOf(id));
    return record;
  }

  public List<TodoCategory> selectByCriteria(TodoCategory todoCategory)
  {
    List list = getSqlMapClientTemplate().queryForList("an_todo_category.selectByCriteria", todoCategory);
    return list;
  }

  public int countByCriteria(TodoCategory todoCategory)
  {
    Integer count = (Integer)getSqlMapClientTemplate().queryForObject("an_todo_category.countByCriteria", todoCategory);
    return count.intValue();
  }

  public void insert(TodoCategory todoCategory)
  {
    getSqlMapClientTemplate().insert("an_todo_category.insert", todoCategory);
  }

  public int updateByPrimaryKey(TodoCategory todoCategory)
  {
    int rows = getSqlMapClientTemplate().update("an_todo_category.updateByPrimaryKey", todoCategory);
    return rows;
  }

  public int deleteByPrimaryKey(int id)
  {
    int rows = getSqlMapClientTemplate().delete("an_todo_category.deleteByPrimaryKey", Integer.valueOf(id));
    return rows;
  }
}
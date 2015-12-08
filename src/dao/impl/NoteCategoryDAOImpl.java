package dao.impl;

import dao.NoteCategoryDAO;
import domain.NoteCategory;
import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class NoteCategoryDAOImpl extends SqlMapClientDaoSupport
  implements NoteCategoryDAO
{
  public NoteCategory selectByPrimaryKey(int id)
  {
    NoteCategory record = (NoteCategory)getSqlMapClientTemplate().queryForObject("an_note_category.selectByPrimaryKey", Integer.valueOf(id));
    return record;
  }

  public List<NoteCategory> selectByCriteria(NoteCategory noteCategory)
  {
    List list = getSqlMapClientTemplate().queryForList("an_note_category.selectByCriteria", noteCategory);
    return list;
  }

  public List<NoteCategory> selectByCriteriaForPaging(NoteCategory noteCategory, int start, int limit)
  {
    List list = getSqlMapClientTemplate().queryForList("an_note_category.selectByCriteria", noteCategory, start, limit);
    return list;
  }

  public int countByCriteria(NoteCategory noteCategory)
  {
    Integer count = (Integer)getSqlMapClientTemplate().queryForObject("an_note_category.countByCriteria", noteCategory);
    return count.intValue();
  }

  public void insert(NoteCategory noteCategory)
  {
    getSqlMapClientTemplate().insert("an_note_category.insert", noteCategory);
  }

  public int updateByPrimaryKey(NoteCategory noteCategory)
  {
    int rows = getSqlMapClientTemplate().update("an_note_category.updateByPrimaryKey", noteCategory);
    return rows;
  }

  public int deleteByPrimaryKey(int id)
  {
    int rows = getSqlMapClientTemplate().delete("an_note_category.deleteByPrimaryKey", Integer.valueOf(id));
    return rows;
  }

  public List<NoteCategory> selectCategoryForTree(NoteCategory noteCategory)
  {
    List list = getSqlMapClientTemplate().queryForList("an_note_category.selectCategoryForTree", noteCategory);
    return list;
  }
}
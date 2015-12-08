package dao.impl;

import dao.PictureDAO;
import domain.Picture;
import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class PictureDAOImpl extends SqlMapClientDaoSupport
  implements PictureDAO
{
  public Picture selectByPrimaryKey(int id)
  {
    Picture record = (Picture)getSqlMapClientTemplate().queryForObject("an_picture.selectByPrimaryKey", Integer.valueOf(id));
    return record;
  }

  public List<Picture> selectByCriteria(Picture picture)
  {
    List list = getSqlMapClientTemplate().queryForList("an_picture.selectByCriteria", picture);
    return list;
  }

  public List<Picture> selectByCriteriaForPaging(Picture picture, int start, int limit)
  {
    List list = getSqlMapClientTemplate().queryForList("an_picture.selectByCriteria", picture, start, limit);
    return list;
  }

  public int countByCriteria(Picture picture)
  {
    Integer count = (Integer)getSqlMapClientTemplate().queryForObject("an_picture.countByCriteria", picture);
    return count.intValue();
  }

  public void insert(Picture picture)
  {
    getSqlMapClientTemplate().insert("an_picture.insert", picture);
  }

  public int updateByPrimaryKey(Picture picture)
  {
    int rows = getSqlMapClientTemplate().update("an_picture.updateByPrimaryKey", picture);
    return rows;
  }

  public int deleteByPrimaryKey(int id)
  {
    int rows = getSqlMapClientTemplate().delete("an_picture.deleteByPrimaryKey", Integer.valueOf(id));
    return rows;
  }
}
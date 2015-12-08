package dao.impl;

import dao.UserMetaDAO;
import domain.UserMeta;
import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class UserMetaDAOImpl extends SqlMapClientDaoSupport
  implements UserMetaDAO
{
  public UserMeta selectByPrimaryKey(int id)
  {
    UserMeta record = (UserMeta)getSqlMapClientTemplate().queryForObject("an_user_meta.selectByPrimaryKey", Integer.valueOf(id));
    return record;
  }

  public List<UserMeta> selectByCriteria(UserMeta userMeta)
  {
    List list = getSqlMapClientTemplate().queryForList("an_user_meta.selectByCriteria", userMeta);
    return list;
  }

  public int countByCriteria(UserMeta userMeta)
  {
    Integer count = (Integer)getSqlMapClientTemplate().queryForObject("an_user_meta.countByCriteria", userMeta);
    return count.intValue();
  }

  public void insert(UserMeta userMeta)
  {
    getSqlMapClientTemplate().insert("an_user_meta.insert", userMeta);
  }

  public int updateByPrimaryKey(UserMeta userMeta)
  {
    int rows = getSqlMapClientTemplate().update("an_user_meta.updateByPrimaryKey", userMeta);
    return rows;
  }

  public int updateByUserIdAndMetaKey(UserMeta userMeta)
  {
    int rows = getSqlMapClientTemplate().update("an_user_meta.updateByUserIdAndMetaKey", userMeta);
    return rows;
  }

  public int deleteByPrimaryKey(int id)
  {
    int rows = getSqlMapClientTemplate().delete("an_user_meta.deleteByPrimaryKey", Integer.valueOf(id));
    return rows;
  }

  public int deleteByUserId(String userId)
  {
    int rows = getSqlMapClientTemplate().delete("an_user_meta.deleteByUserId", userId);
    return rows;
  }
}
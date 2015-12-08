package dao.impl;

import dao.UserDAO;
import domain.User;
import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class UserDAOImpl extends SqlMapClientDaoSupport
  implements UserDAO
{
  public User selectByPrimaryKey(String id)
  {
    User record = (User)getSqlMapClientTemplate().queryForObject("an_user.selectByPrimaryKey", id);
    return record;
  }

  public List<User> selectByCriteria(User user)
  {
    List list = getSqlMapClientTemplate().queryForList("an_user.selectByCriteria", user);
    return list;
  }

  public List<User> selectByCriteriaForPaging(User user, int start, int limit)
  {
    List list = getSqlMapClientTemplate().queryForList("an_user.selectByCriteria", user, start, limit);
    return list;
  }

  public int countByCriteria(User user)
  {
    Integer count = (Integer)getSqlMapClientTemplate().queryForObject("an_user.countByCriteria", user);
    return count.intValue();
  }

  public void insert(User user)
  {
    getSqlMapClientTemplate().insert("an_user.insert", user);
  }

  public int updateByPrimaryKey(User user)
  {
    int rows = getSqlMapClientTemplate().update("an_user.updateByPrimaryKey", user);
    return rows;
  }

  public int deleteByPrimaryKey(String id)
  {
    int rows = getSqlMapClientTemplate().delete("an_user.deleteByPrimaryKey", id);
    return rows;
  }
}
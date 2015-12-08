package dao.impl;

import dao.FeedDAO;
import domain.Feed;
import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class FeedDAOImpl extends SqlMapClientDaoSupport
  implements FeedDAO
{
  public Feed selectByPrimaryKey(int id)
  {
    Feed record = (Feed)getSqlMapClientTemplate().queryForObject("an_feed.selectByPrimaryKey", Integer.valueOf(id));
    return record;
  }

  public List<Feed> selectByCriteria(Feed feed)
  {
    List list = getSqlMapClientTemplate().queryForList("an_feed.selectByCriteria", feed);
    return list;
  }

  public int countByCriteria(Feed feed)
  {
    Integer count = (Integer)getSqlMapClientTemplate().queryForObject("an_feed.countByCriteria", feed);
    return count.intValue();
  }

  public void insert(Feed feed)
  {
    getSqlMapClientTemplate().insert("an_feed.insert", feed);
  }

  public int updateByPrimaryKey(Feed feed)
  {
    int rows = getSqlMapClientTemplate().update("an_feed.updateByPrimaryKey", feed);
    return rows;
  }

  public int deleteByPrimaryKey(int id)
  {
    int rows = getSqlMapClientTemplate().delete("an_feed.deleteByPrimaryKey", Integer.valueOf(id));
    return rows;
  }
}
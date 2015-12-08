package dao.impl;

import dao.FeedFavoriteDAO;
import domain.FeedFavorite;
import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class FeedFavoriteDAOImpl extends SqlMapClientDaoSupport
  implements FeedFavoriteDAO
{
  public FeedFavorite selectByPrimaryKey(int id)
  {
    FeedFavorite record = (FeedFavorite)getSqlMapClientTemplate().queryForObject("an_feed_favorite.selectByPrimaryKey", Integer.valueOf(id));
    return record;
  }

  public List<FeedFavorite> selectByCriteria(FeedFavorite feedFavorite)
  {
    List list = getSqlMapClientTemplate().queryForList("an_feed_favorite.selectByCriteria", feedFavorite);
    return list;
  }

  public List<FeedFavorite> selectByCriteriaForPaging(FeedFavorite feedFavorite, int start, int limit)
  {
    List list = getSqlMapClientTemplate().queryForList("an_feed_favorite.selectByCriteria", feedFavorite, start, limit);
    return list;
  }

  public int countByCriteria(FeedFavorite feedFavorite)
  {
    Integer count = (Integer)getSqlMapClientTemplate().queryForObject("an_feed_favorite.countByCriteria", feedFavorite);
    return count.intValue();
  }

  public void insert(FeedFavorite feedFavorite)
  {
    getSqlMapClientTemplate().insert("an_feed_favorite.insert", feedFavorite);
  }

  public int updateByPrimaryKey(FeedFavorite feedFavorite)
  {
    int rows = getSqlMapClientTemplate().update("an_feed_favorite.updateByPrimaryKey", feedFavorite);
    return rows;
  }

  public int deleteByPrimaryKey(int id)
  {
    int rows = getSqlMapClientTemplate().delete("an_feed_favorite.deleteByPrimaryKey", Integer.valueOf(id));
    return rows;
  }
}
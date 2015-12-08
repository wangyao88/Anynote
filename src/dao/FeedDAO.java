package dao;

import domain.Feed;
import java.util.List;

public abstract interface FeedDAO
{
  public abstract Feed selectByPrimaryKey(int paramInt);

  public abstract List<Feed> selectByCriteria(Feed paramFeed);

  public abstract int countByCriteria(Feed paramFeed);

  public abstract void insert(Feed paramFeed);

  public abstract int updateByPrimaryKey(Feed paramFeed);

  public abstract int deleteByPrimaryKey(int paramInt);
}
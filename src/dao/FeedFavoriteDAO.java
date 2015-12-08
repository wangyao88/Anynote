package dao;

import domain.FeedFavorite;
import java.util.List;

public abstract interface FeedFavoriteDAO
{
  public abstract FeedFavorite selectByPrimaryKey(int paramInt);

  public abstract List<FeedFavorite> selectByCriteria(FeedFavorite paramFeedFavorite);

  public abstract List<FeedFavorite> selectByCriteriaForPaging(FeedFavorite paramFeedFavorite, int paramInt1, int paramInt2);

  public abstract int countByCriteria(FeedFavorite paramFeedFavorite);

  public abstract void insert(FeedFavorite paramFeedFavorite);

  public abstract int updateByPrimaryKey(FeedFavorite paramFeedFavorite);

  public abstract int deleteByPrimaryKey(int paramInt);
}
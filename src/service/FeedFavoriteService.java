package service;

import dao.FeedFavoriteDAO;
import domain.FeedFavorite;
import java.util.List;

public abstract interface FeedFavoriteService {
	public abstract void setFeedFavoriteDao(FeedFavoriteDAO paramFeedFavoriteDAO);

	public abstract FeedFavorite selectByPrimaryKey(int paramInt);

	public abstract int countByCriteria(FeedFavorite paramFeedFavorite);

	public abstract List<FeedFavorite> selectByCriteriaWithBLOBsForPaging(
			FeedFavorite paramFeedFavorite);

	public abstract void insert(FeedFavorite paramFeedFavorite);

	public abstract void delete(String paramString);
}
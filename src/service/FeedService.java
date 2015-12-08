package service;

import dao.FeedDAO;
import domain.Feed;
import java.io.InputStream;
import java.util.List;
import net.sf.json.JSONArray;

public abstract interface FeedService {
	public abstract void setFeedDao(FeedDAO paramFeedDAO);

	public abstract int countByCriteria(Feed paramFeed);

	public abstract Feed selectByPrimaryKey(Integer paramInteger);

	public abstract List<Feed> selectByCriteria(Feed paramFeed);

	public abstract JSONArray selectFeedForTree(String paramString);

	public abstract void insert(Feed paramFeed);

	public abstract void update(Feed paramFeed);

	public abstract void delete(int paramInt, String paramString)
			throws Exception;

	public abstract void refreshFeed(int paramInt, String paramString)
			throws Exception;

	public abstract void importOpml(String paramString,
			InputStream paramInputStream) throws Exception;

	public abstract void exportOpml(String paramString1, String paramString2)
			throws Exception;
}
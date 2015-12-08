package service.impl;

import dao.FeedFavoriteDAO;
import domain.FeedFavorite;
import global.security.SessionUtils;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import service.FeedFavoriteService;
import util.DateUtils;

public class FeedFavoriteServiceImpl
  implements FeedFavoriteService
{
  private FeedFavoriteDAO feedFavoriteDao = null;

  public void setFeedFavoriteDao(FeedFavoriteDAO feedFavoriteDao)
  {
    this.feedFavoriteDao = feedFavoriteDao;
  }

  public FeedFavorite selectByPrimaryKey(int feedFavoriteId)
  {
    FeedFavorite feedFavorite = this.feedFavoriteDao.selectByPrimaryKey(feedFavoriteId);
    return feedFavorite;
  }

  public int countByCriteria(FeedFavorite paramFeedFavorite)
  {
    paramFeedFavorite = getConditions(paramFeedFavorite);
    int count = this.feedFavoriteDao.countByCriteria(paramFeedFavorite);
    return count;
  }

  public List<FeedFavorite> selectByCriteriaWithBLOBsForPaging(FeedFavorite paramFeedFavorite)
  {
    paramFeedFavorite = getConditions(paramFeedFavorite);
    List<FeedFavorite> feedFavoriteList = this.feedFavoriteDao.selectByCriteriaForPaging(
      paramFeedFavorite, paramFeedFavorite.getStart(), paramFeedFavorite.getLimit());
    if (feedFavoriteList != null) {
      List<FeedFavorite> resultList = new ArrayList<FeedFavorite>();
      for (FeedFavorite feedFavorite : feedFavoriteList)
      {
        feedFavorite.setUpdatedStr(DateUtils.formatDate2Str(feedFavorite.getUpdated(), "yyyy/MM/dd HH:mm:ss"));
        resultList.add(feedFavorite);
      }
      return resultList;
    }
    return null;
  }

  public void insert(FeedFavorite feedFavorite)
  {
    feedFavorite.setDelflag("1");

    Timestamp sysdate = new Timestamp(System.currentTimeMillis());
    feedFavorite.setCreateUser(SessionUtils.getCurrentUserId());
    feedFavorite.setCreateTime(sysdate);
    feedFavorite.setUpdateUser(SessionUtils.getCurrentUserId());
    feedFavorite.setUpdateTime(sysdate);
    this.feedFavoriteDao.insert(feedFavorite);
  }

  public void delete(String feedFavoriteIds)
  {
    String[] feedFavoriteIdArr = feedFavoriteIds.split(",");
    for (int i = 0; i < feedFavoriteIdArr.length; i++)
      this.feedFavoriteDao.deleteByPrimaryKey(Integer.parseInt(feedFavoriteIdArr[i]));
  }

  private FeedFavorite getConditions(FeedFavorite paramFeedFavorite)
  {
    paramFeedFavorite.setCreateUser(SessionUtils.getCurrentUserId());
    return paramFeedFavorite;
  }
}
package service.impl;

import dao.FeedDAO;
import domain.Feed;
import global.Constants;
import global.security.SessionUtils;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import service.FeedService;
import util.FileUtils;
import util.StringUtils;

public class FeedServiceImpl
  implements FeedService
{
  private FeedDAO feedDao = null;

  public void setFeedDao(FeedDAO feedDao)
  {
    this.feedDao = feedDao;
  }

  public int countByCriteria(Feed paramFeed)
  {
    paramFeed = getConditions(paramFeed);
    int count = this.feedDao.countByCriteria(paramFeed);
    return count;
  }

  public Feed selectByPrimaryKey(Integer feedId)
  {
    return this.feedDao.selectByPrimaryKey(feedId.intValue());
  }

  public List<Feed> selectByCriteria(Feed paramFeed)
  {
    paramFeed = getConditions(paramFeed);
    List feedList = this.feedDao.selectByCriteria(paramFeed);
    if ((feedList != null) && (feedList.size() != 0)) {
      return feedList;
    }
    return null;
  }

  public JSONArray selectFeedForTree(String parentId)
  {
    JSONArray res = new JSONArray();
    Feed paramFeed = new Feed();
    if (StringUtils.isNotEmpty(parentId)) {
      paramFeed.setParentId(Integer.valueOf(Integer.parseInt(parentId)));
    }

    List feedList = new ArrayList();
    feedList = selectByCriteria(paramFeed);
    res = getFeedTreeFromList(feedList, parentId);
    return res;
  }

  public void insert(Feed feed)
  {
    feed.setDelflag("1");

    Timestamp sysdate = new Timestamp(System.currentTimeMillis());
    feed.setCreateUser(SessionUtils.getCurrentUserId());
    feed.setCreateTime(sysdate);
    feed.setUpdateUser(SessionUtils.getCurrentUserId());
    feed.setUpdateTime(sysdate);
    this.feedDao.insert(feed);
  }

  public void update(Feed feed)
  {
    Timestamp sysdate = new Timestamp(System.currentTimeMillis());
    feed.setUpdateUser(SessionUtils.getCurrentUserId());
    feed.setUpdateTime(sysdate);
    this.feedDao.updateByPrimaryKey(feed);
  }

  public void delete(int feedId, String feedFilePath)
    throws Exception
  {
    List<Feed> feedList = new ArrayList<Feed>();

    Feed parentFeed = new Feed();
    if (feedId == 0)
      parentFeed.setFeedId(Integer.valueOf(feedId));
    else {
      parentFeed = selectByPrimaryKey(Integer.valueOf(feedId));
    }
    getFeedList(parentFeed, feedList);

    for (Feed feed : feedList)
    {
      this.feedDao.deleteByPrimaryKey(feed.getFeedId().intValue());

      FileUtils.delete(feedFilePath + "/" + feed.getFeedId() + ".xml");
    }

    this.feedDao.deleteByPrimaryKey(feedId);
  }

  public void refreshFeed(int feedId, String feedFilePath)
    throws Exception
  {
    List<Feed> feedList = new ArrayList<Feed>();

    Feed parentFeed = new Feed();
    if (feedId == 0)
      parentFeed.setFeedId(Integer.valueOf(feedId));
    else {
      parentFeed = selectByPrimaryKey(Integer.valueOf(feedId));
    }
    getFeedList(parentFeed, feedList);
    for (Feed feed : feedList)
      if ("1".equals(feed.getIsleaf()))
        FileUtils.saveFileByUrl(feed.getFeedUrl(), feedFilePath + "/" + feed.getFeedId() + ".xml");
  }

  private JSONArray getFeedTreeFromList(List<Feed> feedList, String parentId)
  {
    JSONArray results = new JSONArray();

    if ("-1".equals(parentId)) {
      JSONObject feedFavorite = new JSONObject();
      feedFavorite.put("id", "feedFavorite");
      feedFavorite.put("text", "¶©ÔÄÊÕ²Ø¼Ð");
      feedFavorite.put("leaf", Boolean.valueOf(true));
      results.add(feedFavorite);
      JSONObject feed = new JSONObject();
      feed.put("id", "0");
      feed.put("text", "ÎÒµÄ¶©ÔÄ");
      feed.put("leaf", Boolean.valueOf(false));
      results.add(feed);
    }
    else if (feedList != null) {
      for (int i = 0; i < feedList.size(); i++) {
        Feed tempFeed = (Feed)feedList.get(i);
        JSONObject feedNode = new JSONObject();
        feedNode.put("id", tempFeed.getFeedId());
        feedNode.put("text", tempFeed.getFeedName());
        feedNode.put("leaf", Constants.ISLEAF_MAP.get(tempFeed.getIsleaf()));
        feedNode.put("attributes", "{feedUrl:'" + tempFeed.getFeedUrl() + "',feedCount:'" + tempFeed.getFeedCount() + "'}");
        results.add(feedNode);
      }
    }

    return results;
  }

  public void importOpml(String feedId, InputStream in)
    throws Exception
  {
    Document doc = Jsoup.parse(in, "UTF-8", "");

    Elements outlines = doc.getElementsByTag("outline");

    for (Element outline : outlines)
      if ((outline.hasAttr("title")) && (outline.hasAttr("xmlUrl"))) {
        String title = outline.attr("title");
        String xmlUrl = outline.attr("xmlUrl");
        if ((StringUtils.isNotEmpty(title)) && (StringUtils.isNotEmpty(xmlUrl))) {
          Feed feed = new Feed();
          feed.setParentId(Integer.valueOf(Integer.parseInt(feedId)));
          feed.setFeedName(title);
          feed.setFeedUrl(xmlUrl);
          feed.setIsleaf("1");
          feed.setFeedCount(Integer.valueOf(50));
          feed.setStatus("1");

          insert(feed);
        }
      }
  }

  public void exportOpml(String feedId, String templateFilePath)
    throws Exception
  {
    List<Feed> feedList = new ArrayList<Feed>();

    Feed paramFeed = new Feed();
    paramFeed.setFeedId(Integer.valueOf(Integer.parseInt(feedId)));
    getFeedList(paramFeed, feedList);

    String outlines = "";
    for (Feed feed : feedList) {
      if ("1".equals(feed.getIsleaf())) {
        outlines = outlines + "<outline title='" + feed.getFeedName() + "' text='" + feed.getFeedName() + "' xmlUrl='" + feed.getFeedUrl() + "' />" + "\r\n";
      }
    }
    FileUtils.createFileWithEncoder(templateFilePath, "<?xml version='1.0' encoding='UTF-8'?>\r\n<opml version='1.0'>\r\n<head><title>Anynote¶©ÔÄÁÐ±í</title></head>\r\n<body>\r\n" + outlines + "</body>\r\n</opml>", "UTF-8");
  }

  private void getFeedList(Feed parentFeed, List<Feed> feedList)
  {
    feedList.add(parentFeed);

    Feed paramFeed = new Feed();
    paramFeed.setParentId(parentFeed.getFeedId());
    List newFeedList = selectByCriteria(paramFeed);
    if (newFeedList != null)
      for (int i = 0; i < newFeedList.size(); i++)
        getFeedList((Feed)newFeedList.get(i), feedList);
  }

  private Feed getConditions(Feed paramFeed)
  {
    paramFeed.setCreateUser(SessionUtils.getCurrentUserId());
    return paramFeed;
  }
}
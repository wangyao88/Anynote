package action;

import domain.FeedFavorite;
import global.beanUtils.BeanUtils;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import service.FeedFavoriteService;
import util.ServletHelp;
import util.StringUtils;

public class FeedFavoriteAction extends BaseAction
{
  private FeedFavoriteService feedFavoriteService = null;

  public void setFeedFavoriteService(FeedFavoriteService feedFavoriteService)
  {
    this.feedFavoriteService = feedFavoriteService;
  }

  public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Map map = request.getParameterMap();
    FeedFavorite paramFeedFavorite = new FeedFavorite();
    BeanUtils.populate(paramFeedFavorite, map);

    JSONArray datas = new JSONArray();
    List feedFavoriteList = this.feedFavoriteService
      .selectByCriteriaWithBLOBsForPaging(paramFeedFavorite);
    if (feedFavoriteList != null) {
      datas = JSONArray.fromObject(feedFavoriteList);
    }
    int count = this.feedFavoriteService.countByCriteria(paramFeedFavorite);

    JSONObject res = new JSONObject();
    res.put("success", Boolean.valueOf(true));
    res.put("datas", datas);
    res.put("results", Integer.valueOf(count));
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }

  public ActionForward getFeedFavorite(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String feedFavoriteId = request.getParameter("feedFavoriteId");

    FeedFavorite feedFavorite = this.feedFavoriteService.selectByPrimaryKey(Integer.parseInt(feedFavoriteId));

    JSONObject res = JSONObject.fromObject(feedFavorite);
    res.put("success", Boolean.valueOf(true));
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }

  public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Map map = request.getParameterMap();
    FeedFavorite feedFavorite = new FeedFavorite();
    BeanUtils.populate(feedFavorite, map);
    feedFavorite.setDelflag("1");
    feedFavorite.setStatus("1");

    this.feedFavoriteService.insert(feedFavorite);

    JSONObject res = new JSONObject();
    res.put("success", Boolean.valueOf(true));
    String result = res.toString();

    ServletHelp.outRequestForJson(request, response, result);
    return null;
  }

  public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String feedFavoriteIdStr = request.getParameter("feedFavoriteIds");

    if (StringUtils.isNotEmpty(feedFavoriteIdStr))
    {
      this.feedFavoriteService.delete(feedFavoriteIdStr);
    }

    JSONObject res = new JSONObject();
    res.put("success", Boolean.valueOf(true));
    String result = res.toString();

    ServletHelp.outRequestForJson(request, response, result);
    return null;
  }
}
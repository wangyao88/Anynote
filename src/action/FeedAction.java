package action;

import domain.Feed;
import domain.FeedFavorite;
import global.beanUtils.BeanUtils;
import global.security.SessionUtils;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import service.FeedFavoriteService;
import service.FeedService;
import util.FileUtils;
import util.MessageUtils;
import util.ServletHelp;
import util.StringUtils;

public class FeedAction extends BaseAction
{
  private FeedService feedService = null;

  private FeedFavoriteService feedFavoriteService = null;

  public void setFeedService(FeedService feedService)
  {
    this.feedService = feedService;
  }

  public void setFeedFavoriteService(FeedFavoriteService feedFavoriteService)
  {
    this.feedFavoriteService = feedFavoriteService;
  }

  public ActionForward getFeedTree(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String parentId = request.getParameter("parentId");
    JSONArray res = this.feedService.selectFeedForTree(parentId);
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }

  public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Map map = request.getParameterMap();
    Feed feed = new Feed();
    BeanUtils.populate(feed, map);
    feed.setStatus("1");

    String feedId = request.getParameter("feedId");
    if (StringUtils.isNotEmpty(feedId))
    {
      this.feedService.update(feed);
    }
    else {
      this.feedService.insert(feed);
    }

    JSONObject res = new JSONObject();
    res.put("success", Boolean.valueOf(true));
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }

  public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String feedId = request.getParameter("feedId");

    if (StringUtils.isNotEmpty(feedId))
    {
      String feedFilePath = ServletHelp.getRealPath(request, 
        MessageUtils.setParamMessage("/websrc/file/{0}/feed", new String[] { SessionUtils.getCurrentUserId() }));
      this.feedService.delete(Integer.parseInt(feedId), feedFilePath);
    }

    JSONObject res = new JSONObject();
    res.put("success", Boolean.valueOf(true));
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }

  public ActionForward sendFeed(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String emails = request.getParameter("emails");

    String title = request.getParameter("title");

    String link = request.getParameter("link");

    String description = request.getParameter("description");

    boolean hasSend = ServletHelp.sendEmail(emails, title, "<a href='" + link + "'>" + title + "</a><br/>" + description);

    JSONObject res = new JSONObject();
    if (hasSend)
      res.put("success", Boolean.valueOf(true));
    else {
      res.put("success", Boolean.valueOf(false));
    }
    String result = res.toString();

    ServletHelp.outRequestForJson(request, response, result);
    return null;
  }

  public ActionForward favorite(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
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

  public ActionForward refreshFeed(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String feedId = request.getParameter("feedId");
    if (StringUtils.isNotEmpty(feedId))
    {
      String feedFilePath = ServletHelp.getRealPath(request, 
        MessageUtils.setParamMessage("/websrc/file/{0}/feed", new String[] { SessionUtils.getCurrentUserId() }));
      this.feedService.refreshFeed(Integer.parseInt(feedId), feedFilePath);
    }

    JSONObject res = new JSONObject();
    res.put("success", Boolean.valueOf(true));
    String result = res.toString();

    ServletHelp.outRequestForJson(request, response, result);
    return null;
  }

  public ActionForward getFeed(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String feedId = request.getParameter("feedId");
    String feedUrl = request.getParameter("feedUrl");
    if ((StringUtils.isNotEmpty(feedId)) && (StringUtils.isNotEmpty(feedUrl)))
    {
      String feedFilePath = 
        ServletHelp.getRealPath(request, 
        MessageUtils.setParamMessage("/websrc/file/{0}/feed", new String[] { SessionUtils.getCurrentUserId() })) + "/" + feedId + ".xml";
      try
      {
        FileUtils.saveFileByUrl(feedUrl, feedFilePath);
      } catch (FileNotFoundException localFileNotFoundException) {
      }
      catch (ConnectException localConnectException) {
      }
      response.setContentType("application/xml");

      FileInputStream fis = null;

      BufferedOutputStream bos = null;
      try {
        fis = new FileInputStream(new File(feedFilePath));
        bos = new BufferedOutputStream(response.getOutputStream());
        byte[] buff = new byte[2048];
        int bytesRead;
        while (-1 != (bytesRead = fis.read(buff, 0, buff.length)))
        {
          bos.write(buff, 0, bytesRead);
        }
      }
      finally {
        if (fis != null)
          fis.close();
        if (bos != null)
          bos.close();
      }
    }
    return null;
  }

  public ActionForward importOpml(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String feedId = request.getParameter("feedId");
    DiskFileItemFactory factory = new DiskFileItemFactory();
    ServletFileUpload upload = new ServletFileUpload(factory);
    List items = null;

    items = upload.parseRequest(request);
    Iterator it = items.iterator();
    InputStream in = null;
    while (it.hasNext())
    {
      FileItem fi = (FileItem)it.next();
      if ("feedFile".equals(fi.getFieldName()))
        in = fi.getInputStream();
      else "feedId".equals(fi.getFieldName());

    }

    if ((StringUtils.isNotEmpty(feedId)) && (in != null)) {
      this.feedService.importOpml(feedId, in);
    }

    JSONObject res = new JSONObject();
    res.put("success", Boolean.valueOf(true));
    response.getWriter().print(res);
    return null;
  }

  public ActionForward exportOpml(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String feedId = request.getParameter("feedId");
    if (StringUtils.isNotEmpty(feedId))
    {
      String filePath = ServletHelp.getRealPath(request, "/websrc/file/temp/anynote-reader-opml.xml");

      this.feedService.exportOpml(feedId, filePath);

      ServletHelp.download(mapping, form, request, response, filePath, "anynote-reader-opml.xml");
    }
    return null;
  }
}
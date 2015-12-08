package action;

import domain.Album;
import global.beanUtils.BeanUtils;
import global.security.SessionUtils;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import service.AlbumService;
import util.MessageUtils;
import util.ServletHelp;
import util.StringUtils;

public class AlbumAction extends BaseAction
{
  private AlbumService albumService = null;

  public void setAlbumService(AlbumService albumService)
  {
    this.albumService = albumService;
  }

  public ActionForward getAlbumTree(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String parentId = request.getParameter("parentId");

    JSONArray res = this.albumService.selectAlbumForTree(parentId);
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }

  public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Map map = request.getParameterMap();
    Album album = new Album();
    BeanUtils.populate(album, map);
    album.setStatus("1");

    String albumId = request.getParameter("albumId");

    if (StringUtils.isNotEmpty(albumId))
    {
      this.albumService.update(album);
    }
    else {
      this.albumService.insert(album);
    }

    JSONObject res = new JSONObject();
    res.put("success", Boolean.valueOf(true));
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }

  public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String uploadFilePath = ServletHelp.getRealPath(request, 
      MessageUtils.setParamMessage("/websrc/file/{0}/picture", new String[] { SessionUtils.getCurrentUserId() }));

    String albumId = request.getParameter("albumId");
    if (StringUtils.isNotEmpty(albumId))
    {
      this.albumService.delete(Integer.parseInt(albumId), uploadFilePath);
    }

    JSONObject res = new JSONObject();
    res.put("success", Boolean.valueOf(true));
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }
}
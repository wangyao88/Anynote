package action;

import domain.UserMeta;
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
import service.UserMetaService;
import util.ServletHelp;
import util.StringUtils;

public class UserMetaAction extends BaseAction
{
  private UserMetaService userMetaService = null;

  public void setUserMetaService(UserMetaService userMetaService)
  {
    this.userMetaService = userMetaService;
  }

  public ActionForward getFavoriteMenuTree(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    JSONArray res = this.userMetaService.getFavoriteMenuTree();
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }

  public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Map map = request.getParameterMap();
    UserMeta userMeta = new UserMeta();
    BeanUtils.populate(userMeta, map);
    userMeta.setUserId(SessionUtils.getCurrentUserId());
    userMeta.setStatus("1");

    String userMetaId = request.getParameter("userMetaId");

    if (StringUtils.isNotEmpty(userMetaId))
    {
      this.userMetaService.update(userMeta);
    }
    else {
      this.userMetaService.insert(userMeta);
    }

    JSONObject res = new JSONObject();
    res.put("success", Boolean.valueOf(true));
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }

  public ActionForward addFavoriteMenu(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Map map = request.getParameterMap();
    UserMeta userMeta = new UserMeta();
    BeanUtils.populate(userMeta, map);
    userMeta.setUserId(SessionUtils.getCurrentUserId());
    userMeta.setStatus("1");

    this.userMetaService.addFavoriteMenu(userMeta);

    JSONObject res = new JSONObject();
    res.put("success", Boolean.valueOf(true));
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }

  public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String userMetaId = request.getParameter("userMetaId");
    if (StringUtils.isNotEmpty(userMetaId))
    {
      this.userMetaService.delete(Integer.valueOf(Integer.parseInt(userMetaId)));
    }

    JSONObject res = new JSONObject();
    res.put("success", Boolean.valueOf(true));
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }
}
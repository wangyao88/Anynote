package action;

import domain.User;
import domain.UserMeta;
import global.beanUtils.BeanUtils;
import global.security.AuthenticatorHolder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import service.UserMetaService;
import service.UserService;
import util.MD5Utils;
import util.ServletHelp;
import util.StringUtils;

public class UserAction extends BaseAction
{
  private UserService userService = null;

  private UserMetaService userMetaService = null;

  public void setUserService(UserService userService)
  {
    this.userService = userService;
  }

  public void setUserMetaService(UserMetaService userMetaService)
  {
    this.userMetaService = userMetaService;
  }

  public ActionForward queryForPaging(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Map map = request.getParameterMap();
    User paramUser = new User();
    BeanUtils.populate(paramUser, map);

    List userList = new ArrayList();
    JSONArray datas = new JSONArray();
    userList = this.userService.selectByCriteriaForPaging(paramUser);
    datas = JSONArray.fromObject(userList);
    int count = this.userService.countByCriteria(paramUser);

    JSONObject res = new JSONObject();
    res.put("success", Boolean.valueOf(true));
    res.put("datas", datas);
    res.put("results", Integer.valueOf(count));
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }

  public ActionForward getUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String userId = request.getParameter("userId");

    User user = this.userService.selectByPrimaryKey(userId);

    JSONArray datas = new JSONArray();
    List userMetaList = new ArrayList();
    UserMeta paramUserMeta = new UserMeta();
    paramUserMeta.setUserId(userId);
    userMetaList = this.userMetaService.selectByCriteria(paramUserMeta);
    datas = JSONArray.fromObject(userMetaList);

    JSONObject res = JSONObject.fromObject(user);
    res.put("success", Boolean.valueOf(true));
    res.put("userMeta", datas);
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }

  public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    HttpSession session = request.getSession();

    Map map = ServletHelp.getParameterMap(request);
    map.put("session", session);

    JSONObject res = this.userService.update(map);

    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }

  public ActionForward changePwd(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    boolean isSuccess = true;
    String errorMsg = "";

    HttpSession session = request.getSession();

    String userId = request.getParameter("userId");

    String oldpassword = request.getParameter("oldpassword");

    String password = request.getParameter("password");

    String repassword = request.getParameter("repassword");

    if ((StringUtils.isEmpty(password)) || (StringUtils.isEmpty(repassword)) || (!password.equals(repassword))) {
      isSuccess = false;
      errorMsg = "两次输入的密码不一致.";
    }

    Map loginMap = this.userService.login(userId, oldpassword);
    if (!((Boolean)loginMap.get("success")).booleanValue()) {
      isSuccess = false;
      errorMsg = "原密码错误.";
    }

    if (isSuccess)
    {
      User paramUser = new User();
      if (StringUtils.isNotEmpty(userId)) {
        paramUser.setUserId(userId);
      }
      if (StringUtils.isNotEmpty(password)) {
        paramUser.setPassword(MD5Utils.getMD5String(password));
      }

      this.userService.update(paramUser);

      session.removeAttribute("CLIENT_SESSION");
      AuthenticatorHolder.closeClientSession();
    }

    JSONObject res = new JSONObject();
    res.put("success", Boolean.valueOf(isSuccess));
    res.put("message", errorMsg);
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }

  public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String userIds = request.getParameter("userIds");

    if (StringUtils.isNotEmpty(userIds)) {
      String filePath = ServletHelp.getRealPath(request, "/websrc/file");
      this.userService.delete(userIds, filePath);
    }

    JSONObject res = new JSONObject();
    res.put("success", Boolean.valueOf(true));
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }

  public ActionForward updateStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String userIds = request.getParameter("userIds");

    String status = request.getParameter("status");

    if (StringUtils.isNotEmpty(userIds)) {
      Map map = new HashMap();
      map.put("status", status);
      this.userService.update(userIds, map);
    }

    JSONObject res = new JSONObject();
    res.put("success", Boolean.valueOf(true));
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }
}
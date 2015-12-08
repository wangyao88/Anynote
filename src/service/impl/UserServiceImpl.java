package service.impl;

import dao.UserDAO;
import domain.User;
import domain.UserMeta;
import global.Constants;
import global.beanUtils.BeanUtils;
import global.security.ClientSession;
import global.security.SessionUtils;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import org.apache.commons.lang.RandomStringUtils;
import service.UserMetaService;
import service.UserService;
import util.DateUtils;
import util.FileUtils;
import util.MD5Utils;
import util.MessageUtils;
import util.ServletHelp;
import util.StringUtils;

public class UserServiceImpl
  implements UserService
{
  private UserDAO userDao = null;

  private UserMetaService userMetaService = null;

  public void setUserDao(UserDAO userDao)
  {
    this.userDao = userDao;
  }

  public void setUserMetaService(UserMetaService userMetaService)
  {
    this.userMetaService = userMetaService;
  }

  public Map login(String userId, String password)
    throws Exception
  {
    Map res = new HashMap();
    res.put("success", Boolean.valueOf(true));

    User paramUser = new User();
    paramUser.setUserId(userId);
    paramUser.setPassword(MD5Utils.getMD5String(password));
    List userList = this.userDao.selectByCriteria(paramUser);
    if ((userList == null) || (userList.size() != 1)) {
      res.put("success", Boolean.valueOf(false));
      res.put("message", "用户名或密码错误.");
    }
    else {
      User currentUser = (User)userList.get(0);
      if (!"1".equals(currentUser.getStatus())) {
        res.put("success", Boolean.valueOf(false));
        res.put("message", "该用户已被禁用.");
      }
      else {
        currentUser = getUserWithMetaByUserId(currentUser.getUserId());

        ClientSession cs = SessionUtils.getClientSession(currentUser);
        res.put("session", cs);
      }
    }

    return res;
  }

  public int countByCriteria(User paramUser)
  {
    if ("1".equals(SessionUtils.getUserRole()))
      paramUser.setRole("'2','3'");
    else {
      paramUser.setRole("'3'");
    }
    int count = this.userDao.countByCriteria(paramUser);
    return count;
  }

  public User selectByPrimaryKey(String userId)
  {
    User user = this.userDao.selectByPrimaryKey(userId);
    if (user != null)
    {
      user.setBirthdayStr(DateUtils.formatDate2Str(user.getBirthday(), "yyyy-MM-dd"));
    }
    return user;
  }

  public List<User> selectByCriteria(User paramUser)
  {
    List userList = this.userDao.selectByCriteria(paramUser);
    List results = new ArrayList();
    if (userList != null) {
      for (int i = 0; i < userList.size(); i++) {
        User user = (User)userList.get(i);
        user.setBirthdayStr(DateUtils.formatDate2Str(user.getBirthday(), "yyyy-MM-dd"));
        user.setSex((String)Constants.SEX_MAP.get(user.getSex()));
        user.setStatus((String)Constants.USER_STATUS_MAP.get(user.getStatus()));
        user.setCreateDateStr(DateUtils.formatDate2Str(user.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
        results.add(user);
      }
    }
    return results;
  }

  public List<User> selectByCriteriaForPaging(User paramUser)
  {
    if ("1".equals(SessionUtils.getUserRole()))
      paramUser.setRole("'2','3'");
    else {
      paramUser.setRole("'3'");
    }
    List userList = this.userDao.selectByCriteriaForPaging(paramUser, 
      paramUser.getStart(), paramUser.getLimit());
    List results = new ArrayList();
    if (userList != null) {
      for (int i = 0; i < userList.size(); i++) {
        User user = (User)userList.get(i);
        user.setBirthdayStr(DateUtils.formatDate2Str(user.getBirthday(), "yyyy-MM-dd"));
        user.setRole((String)Constants.ROLE_MAP.get(user.getRole()));
        user.setSex((String)Constants.SEX_MAP.get(user.getSex()));
        user.setStatus((String)Constants.USER_STATUS_MAP.get(user.getStatus()));
        results.add(user);
      }
    }
    return results;
  }

  public void insert(User user)
  {
    String userId = SessionUtils.getCurrentUserId();
    if (StringUtils.isEmpty(userId)) {
      userId = user.getUserId();
    }

    user.setPassword(MD5Utils.getMD5String(user.getPassword()));

    user.setDelflag("1");

    Timestamp sysdate = new Timestamp(System.currentTimeMillis());
    user.setCreateUser(userId);
    user.setCreateTime(sysdate);
    user.setUpdateUser(userId);
    user.setUpdateTime(sysdate);
    this.userDao.insert(user);
  }

  public void update(User user)
  {
    Timestamp sysdate = new Timestamp(System.currentTimeMillis());
    user.setUpdateUser(SessionUtils.getCurrentUserId());
    user.setUpdateTime(sysdate);
    this.userDao.updateByPrimaryKey(user);
  }

  public JSONObject update(Map map)
    throws Exception
  {
    boolean isSuccess = true;
    String message = "";

    User paramUser = new User();
    BeanUtils.populate(paramUser, map);
    paramUser.setBirthday(DateUtils.formatStr2Date(paramUser.getBirthdayStr(), "yyyy-MM-dd"));

    List<User> userList = selectByCriteria(new User());
    if (userList != null) {
      for (User user : userList) {
        if ((paramUser.getEmail().equals(user.getEmail())) && (!paramUser.getUserId().equals(user.getUserId()))) {
          isSuccess = false;
          message = "邮箱已被使用.";
          break;
        }
      }
    }

    if (isSuccess)
    {
      update(paramUser);

      Map userMetaMap = new HashMap();
      userMetaMap.put("theme", (String)map.get("theme"));
      userMetaMap.put("homePage", (String)map.get("homePage"));
      userMetaMap.put("showTodo", (String)map.get("showTodo"));
      userMetaMap.put("showNote", (String)map.get("showNote"));
      userMetaMap.put("showPicture", (String)map.get("showPicture"));
      userMetaMap.put("showAccount", (String)map.get("showAccount"));
      userMetaMap.put("showFeed", (String)map.get("showFeed"));
      userMetaMap.put("showDocument", (String)map.get("showDocument"));
      userMetaMap.put("showSystem", (String)map.get("showSystem"));
      this.userMetaService.updateByUserIdAndMetaKey(paramUser.getUserId(), userMetaMap);

      ((HttpSession)map.get("session")).removeAttribute("CLIENT_SESSION");

      User currentUser = getUserWithMetaByUserId((String)map.get("userId"));

      ClientSession cs = SessionUtils.getClientSession(currentUser);
      ((HttpSession)map.get("session")).setAttribute("CLIENT_SESSION", cs);
    }

    JSONObject res = new JSONObject();
    res.put("success", Boolean.valueOf(isSuccess));
    res.put("message", message);
    return res;
  }

  public void update(String userIds, Map map)
    throws IllegalAccessException, InvocationTargetException
  {
    User paramUser = new User();
    BeanUtils.populate(paramUser, map);
    String[] userIdArr = userIds.split(",");
    for (int i = 0; i < userIdArr.length; i++) {
      paramUser.setUserId(userIdArr[i]);
      update(paramUser);
    }
  }

  public void delete(String userIds, String filePath)
    throws Exception
  {
    String[] userIdArr = userIds.split(",");
    for (int i = 0; i < userIdArr.length; i++)
    {
      this.userDao.deleteByPrimaryKey(userIdArr[i]);

      String userFilePath = filePath + "/" + userIdArr[i];
      FileUtils.deleteDirectory(new File(userFilePath));
    }
  }

  public JSONObject register(Map map)
    throws Exception
  {
    boolean isSuccess = true;
    String errorMsg = "";

    User paramUser = new User();
    BeanUtils.populate(paramUser, map);
    paramUser.setBirthday(DateUtils.formatStr2Date(paramUser.getBirthdayStr(), "yyyy-MM-dd"));
    paramUser.setStatus("1");
    if ((StringUtils.isEmpty(SessionUtils.getUserRole())) || ("3".equals(SessionUtils.getUserRole()))) {
      paramUser.setRole("3");
    }

    String userId = map.get("userId").toString();

    String password = map.get("password").toString();

    String repassword = map.get("repassword").toString();

    String verifyCode = map.get("verifyCode").toString();
    String verifyCodeInSession = map.get("verifyCodeInSession").toString();

    if ("1".equals(SessionUtils.getUserRole())) {
      if ((!"2".equals(paramUser.getRole())) && (!"3".equals(paramUser.getRole()))) {
        isSuccess = false;
        errorMsg = "角色设置错误.";
      }
    }
    else if (!"3".equals(paramUser.getRole())) {
      isSuccess = false;
      errorMsg = "角色设置错误.";
    }

    if (!password.equals(repassword)) {
      isSuccess = false;
      errorMsg = "两次输入的密码不一致.";
    }

    if (!verifyCode.equals(verifyCodeInSession)) {
      isSuccess = false;
      errorMsg = "验证码错误.";
    }

    List<User> userList = selectByCriteria(new User());
    if (userList != null) {
      for (User user : userList) {
        if (userId.equals(user.getUserId())) {
          isSuccess = false;
          errorMsg = "账号已存在.";
          break;
        }if (paramUser.getEmail().equals(user.getEmail())) {
          isSuccess = false;
          errorMsg = "邮箱已被使用.";
          break;
        }
      }
    }

    if (isSuccess)
    {
      insert(paramUser);

      Map userMetaMap = new HashMap();
      userMetaMap.put("theme", (String)map.get("theme"));
      userMetaMap.put("homePage", (String)map.get("homePage"));
      userMetaMap.put("showTodo", (String)map.get("showTodo"));
      userMetaMap.put("showNote", (String)map.get("showNote"));
      userMetaMap.put("showPicture", (String)map.get("showPicture"));
      userMetaMap.put("showAccount", (String)map.get("showAccount"));
      userMetaMap.put("showFeed", (String)map.get("showFeed"));
      userMetaMap.put("showDocument", (String)map.get("showDocument"));
      if ("3".equals(paramUser.getRole()))
        userMetaMap.put("showSystem", "off");
      else {
        userMetaMap.put("showSystem", "on");
      }
      this.userMetaService.insert(userId, userMetaMap);

      String uploadFilePath = ServletHelp.getRealPath((HttpServletRequest)map.get("request"), 
        MessageUtils.setParamMessage("/websrc/file/{0}/document", new String[] { userId }));
      FileUtils.createDirs(uploadFilePath);

      String uploadPicturePath = ServletHelp.getRealPath((HttpServletRequest)map.get("request"), 
        MessageUtils.setParamMessage("/websrc/file/{0}/picture", new String[] { userId }));
      FileUtils.createDirs(uploadPicturePath);

      FileUtils.createDirs(uploadPicturePath + "/" + "thumbnail");

      String feedFilePath = ServletHelp.getRealPath((HttpServletRequest)map.get("request"), 
        MessageUtils.setParamMessage("/websrc/file/{0}/feed", new String[] { userId }));
      FileUtils.createDirs(feedFilePath);
    }

    JSONObject res = new JSONObject();
    res.put("success", Boolean.valueOf(isSuccess));
    res.put("message", errorMsg);
    return res;
  }

  public JSONObject resetPwd(Map map)
    throws Exception
  {
    boolean isSuccess = true;
    String message = "";

    String userId = (String)map.get("userId");

    String email = (String)map.get("email");

    String verifyCode = (String)map.get("verifyCode");
    String verifyCodeInSession = (String)map.get("verifyCodeInSession");

    if (!verifyCode.equals(verifyCodeInSession)) {
      isSuccess = false;
      message = "验证码错误.";
    }

    List userList = null;
    if ((StringUtils.isNotEmpty(userId)) && (StringUtils.isNotEmpty(email)))
    {
      User paramUser = new User();
      paramUser.setUserId(userId);
      paramUser.setEmail(email);
      userList = selectByCriteria(paramUser);
    }
    if ((userList == null) || (userList.size() != 1)) {
      isSuccess = false;
      message = "用户名或邮箱错误.";
    }

    if (isSuccess)
    {
      User user = (User)userList.get(0);

      String newPassword = RandomStringUtils.random(6, true, true);

      String title = "密码重置";
      String content = user.getUserId() + "，您好：<br/>您的新密码是：" + 
        newPassword;
      boolean rs = ServletHelp.sendEmail(email, title, content);
      if (rs)
      {
        User paramUser = new User();
        paramUser.setUserId(userId);
        paramUser.setPassword(MD5Utils.getMD5String(newPassword));
        update(paramUser);
      } else {
        isSuccess = false;
        message = "邮件发送失败.";
      }

    }

    JSONObject res = new JSONObject();
    res.put("success", Boolean.valueOf(isSuccess));
    res.put("message", message);
    return res;
  }

  private User getUserWithMetaByUserId(String userId)
  {
    User currentUser = selectByPrimaryKey(userId);

    UserMeta userMeta = new UserMeta();
    userMeta.setUserId(userId);
    List<UserMeta> userMetaList = this.userMetaService.selectByCriteria(userMeta);
    Map userMetaMap = new HashMap();
    if (userMetaList != null) {
      for (UserMeta temp : userMetaList) {
        userMetaMap.put(temp.getMetaKey(), temp.getMetaValue());
      }
      currentUser.setUserMeta(userMetaMap);
    }
    return currentUser;
  }
}
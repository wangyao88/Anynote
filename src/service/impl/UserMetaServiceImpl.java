package service.impl;

import dao.UserMetaDAO;
import domain.UserMeta;
import global.security.SessionUtils;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import service.UserMetaService;
import util.StringUtils;

public class UserMetaServiceImpl
  implements UserMetaService
{
  private UserMetaDAO userMetaDao = null;

  public void setUserMetaDao(UserMetaDAO userMetaDao)
  {
    this.userMetaDao = userMetaDao;
  }

  public List<UserMeta> selectByCriteria(UserMeta paramUserMeta)
  {
    List userMetaList = this.userMetaDao.selectByCriteria(paramUserMeta);
    return userMetaList;
  }

  public void insert(UserMeta userMeta)
  {
    String userId = SessionUtils.getCurrentUserId();
    if (StringUtils.isEmpty(userId)) {
      userId = userMeta.getUserId();
    }

    userMeta.setStatus("1");

    userMeta.setDelflag("1");

    Timestamp sysdate = new Timestamp(System.currentTimeMillis());
    userMeta.setCreateUser(userId);
    userMeta.setCreateTime(sysdate);
    userMeta.setUpdateUser(userId);
    userMeta.setUpdateTime(sysdate);
    this.userMetaDao.insert(userMeta);
  }

  public void insert(String userId, Map map)
  {
    Set keySet = map.keySet();
    Iterator ite = keySet.iterator();
    String key = "";
    while (ite.hasNext()) {
      key = (String)ite.next();
      UserMeta userMeta = new UserMeta();
      userMeta.setUserId(userId);
      userMeta.setMetaKey(key);
      userMeta.setMetaValue((String)map.get(key));

      insert(userMeta);
    }
  }

  public void update(UserMeta userMeta)
  {
    userMeta.setStatus("1");

    userMeta.setDelflag("1");

    Timestamp sysdate = new Timestamp(System.currentTimeMillis());
    userMeta.setUpdateUser(SessionUtils.getCurrentUserId());
    userMeta.setUpdateTime(sysdate);
    this.userMetaDao.updateByPrimaryKey(userMeta);
  }

  public void updateByUserIdAndMetaKey(String userId, Map map)
  {
    Set keySet = map.keySet();
    Iterator ite = keySet.iterator();
    String key = "";
    while (ite.hasNext()) {
      key = (String)ite.next();
      UserMeta userMeta = new UserMeta();
      userMeta.setUserId(userId);
      userMeta.setMetaKey(key);
      if (StringUtils.isNotEmpty((String)map.get(key)))
        userMeta.setMetaValue((String)map.get(key));
      else {
        userMeta.setMetaValue("");
      }
      userMeta.setStatus("1");
      userMeta.setDelflag("1");
      Timestamp sysdate = new Timestamp(System.currentTimeMillis());
      userMeta.setUpdateUser(SessionUtils.getCurrentUserId());
      userMeta.setUpdateTime(sysdate);

      this.userMetaDao.updateByUserIdAndMetaKey(userMeta);
    }
  }

  public void delete(Integer userMetaId)
  {
    this.userMetaDao.deleteByPrimaryKey(userMetaId.intValue());
  }

  public void deleteByUserId(String userId)
  {
    this.userMetaDao.deleteByUserId(userId);
  }

  public JSONArray getFavoriteMenuTree()
  {
    JSONArray res = new JSONArray();

    UserMeta paramUserMeta = new UserMeta();
    paramUserMeta.setUserId(SessionUtils.getCurrentUserId());
    paramUserMeta.setMetaKey("favoriteMenu");

    List<UserMeta> userMetaList = new ArrayList<UserMeta>();
    userMetaList = selectByCriteria(paramUserMeta);

    JSONObject homePageMenu = new JSONObject();
    homePageMenu.put("id", "homePage");
    homePageMenu.put("text", "我的主页");
    homePageMenu.put("leaf", Boolean.valueOf(true));
    homePageMenu.put("attributes", "{url:'" + SessionUtils.getUserMeta().get("homePage") + "'}");
    res.add(homePageMenu);

    if ((userMetaList != null) && (userMetaList.size() > 0)) {
      JSONArray children = new JSONArray();
      for (UserMeta userMeta : userMetaList) {
        String[] menu = userMeta.getMetaValue().split("::");
        JSONObject favoriteMenu = new JSONObject();
        favoriteMenu.put("id", userMeta.getUserMetaId());
        favoriteMenu.put("text", menu[0]);
        favoriteMenu.put("leaf", Boolean.valueOf(true));
        favoriteMenu.put("attributes", "{url:'" + menu[1] + "'}");
        children.add(favoriteMenu);
      }
      JSONObject quickMenu = new JSONObject();
      quickMenu.put("id", "0");
      quickMenu.put("text", "快捷菜单");
      quickMenu.put("leaf", Boolean.valueOf(false));
      quickMenu.put("children", children);
      res.add(quickMenu);
    }
    return res;
  }

  public void addFavoriteMenu(UserMeta userMeta)
  {
    List userMetaList = selectByCriteria(userMeta);
    if ((userMetaList == null) || (userMetaList.size() == 0))
      insert(userMeta);
  }
}
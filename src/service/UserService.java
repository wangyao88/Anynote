package service;

import dao.UserDAO;
import domain.User;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;

public abstract interface UserService
{
  public abstract void setUserDao(UserDAO paramUserDAO);

  public abstract void setUserMetaService(UserMetaService paramUserMetaService);

  public abstract Map login(String paramString1, String paramString2)
    throws Exception;

  public abstract int countByCriteria(User paramUser);

  public abstract User selectByPrimaryKey(String paramString);

  public abstract List<User> selectByCriteria(User paramUser);

  public abstract List<User> selectByCriteriaForPaging(User paramUser);

  public abstract void insert(User paramUser);

  public abstract void update(User paramUser);

  public abstract JSONObject update(Map paramMap)
    throws Exception;

  public abstract void update(String paramString, Map paramMap)
    throws IllegalAccessException, InvocationTargetException;

  public abstract void delete(String paramString1, String paramString2)
    throws Exception;

  public abstract JSONObject register(Map paramMap)
    throws Exception;

  public abstract JSONObject resetPwd(Map paramMap)
    throws Exception;
}
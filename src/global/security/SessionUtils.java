package global.security;

import domain.User;
import global.beanUtils.BeanUtils;
import java.util.Map;
import util.DateUtils;

public class SessionUtils
{
  private static boolean isAvailable()
  {
    ClientSession clientSession = AuthenticatorHolder.getClientSession();
    return clientSession != null;
  }

  public static String getCurrentUserId()
  {
    if (!isAvailable()) {
      return null;
    }
    ClientSession clientSession = AuthenticatorHolder.getClientSession();
    return clientSession.getUserId();
  }

  public static String getCurrentUserName()
  {
    if (!isAvailable()) {
      return null;
    }
    ClientSession clientSession = AuthenticatorHolder.getClientSession();
    return clientSession.getUserName();
  }

  public static Map getUserMeta()
  {
    if (!isAvailable()) {
      return null;
    }
    ClientSession clientSession = AuthenticatorHolder.getClientSession();
    return clientSession.getUserMeta();
  }

  public static String getUserRole()
  {
    if (!isAvailable()) {
      return null;
    }
    ClientSession clientSession = AuthenticatorHolder.getClientSession();
    return clientSession.getRole();
  }

  public static String getUserSex()
  {
    if (!isAvailable()) {
      return null;
    }
    ClientSession clientSession = AuthenticatorHolder.getClientSession();
    return clientSession.getSex();
  }

  public static String getUserBirthday()
  {
    if (!isAvailable()) {
      return null;
    }
    ClientSession clientSession = AuthenticatorHolder.getClientSession();
    if (clientSession.getBirthday() != null) {
      return DateUtils.formatDate2Str("yyyy-MM-dd");
    }
    return "";
  }

  public static String getUserEmail()
  {
    if (!isAvailable()) {
      return null;
    }
    ClientSession clientSession = AuthenticatorHolder.getClientSession();
    return clientSession.getEmail();
  }

  public static String getUserPhone()
  {
    if (!isAvailable()) {
      return null;
    }
    ClientSession clientSession = AuthenticatorHolder.getClientSession();
    return clientSession.getPhone();
  }

  public static ClientSession getClientSession(User user)
    throws Exception
  {
    ClientSession cs = null;
    if (user != null) {
      cs = new ClientSession();
      BeanUtils.setProperty(cs, "userId", user.getUserId());
      BeanUtils.setProperty(cs, "userName", user.getUserName());
      BeanUtils.setProperty(cs, "role", user.getRole());
      BeanUtils.setProperty(cs, "sex", user.getSex());
      BeanUtils.setProperty(cs, "birthdayStr", user.getBirthdayStr());
      BeanUtils.setProperty(cs, "email", user.getEmail());
      BeanUtils.setProperty(cs, "phone", user.getPhone());
      BeanUtils.setProperty(cs, "userMeta", user.getUserMeta());
    }
    return cs;
  }
}
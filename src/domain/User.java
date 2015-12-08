package domain;

import java.util.Date;
import java.util.Map;

public class User extends PageDomain
{
  private static final long serialVersionUID = 7899464886197534786L;
  private String userId;
  private String userName;
  private String password;
  private String role;
  private String sex;
  private Date birthday;
  private String birthdayStr;
  private String email;
  private String phone;
  private String status;
  private String delflag;
  private Map<String, String> userMeta;
  private String createUser;
  private Date createTime;
  private String updateUser;
  private Date updateTime;

  public String getUserId()
  {
    return this.userId;
  }

  public void setUserId(String userId)
  {
    this.userId = userId;
  }

  public String getUserName()
  {
    return this.userName;
  }

  public void setUserName(String userName)
  {
    this.userName = userName;
  }

  public String getPassword()
  {
    return this.password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

  public String getRole()
  {
    return this.role;
  }

  public void setRole(String role)
  {
    this.role = role;
  }

  public String getSex()
  {
    return this.sex;
  }

  public void setSex(String sex)
  {
    this.sex = sex;
  }

  public Date getBirthday()
  {
    return this.birthday;
  }

  public void setBirthday(Date birthday)
  {
    this.birthday = birthday;
  }

  public String getBirthdayStr()
  {
    return this.birthdayStr;
  }

  public void setBirthdayStr(String birthdayStr)
  {
    this.birthdayStr = birthdayStr;
  }

  public String getEmail()
  {
    return this.email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String getPhone()
  {
    return this.phone;
  }

  public void setPhone(String phone)
  {
    this.phone = phone;
  }

  public String getStatus()
  {
    return this.status;
  }

  public void setStatus(String status)
  {
    this.status = status;
  }

  public String getDelflag()
  {
    return this.delflag;
  }

  public void setDelflag(String delflag)
  {
    this.delflag = delflag;
  }

  public Map<String, String> getUserMeta()
  {
    return this.userMeta;
  }

  public void setUserMeta(Map<String, String> userMeta)
  {
    this.userMeta = userMeta;
  }

  public String getCreateUser()
  {
    return this.createUser;
  }

  public void setCreateUser(String createUser)
  {
    this.createUser = createUser;
  }

  public Date getCreateTime()
  {
    return this.createTime;
  }

  public void setCreateTime(Date createTime)
  {
    this.createTime = createTime;
  }

  public String getUpdateUser()
  {
    return this.updateUser;
  }

  public void setUpdateUser(String updateUser)
  {
    this.updateUser = updateUser;
  }

  public Date getUpdateTime()
  {
    return this.updateTime;
  }

  public void setUpdateTime(Date updateTime)
  {
    this.updateTime = updateTime;
  }
}
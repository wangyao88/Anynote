package domain;

import java.util.Date;

public class UserMeta extends PageDomain
{
  private static final long serialVersionUID = 5180738772935205787L;
  private Integer userMetaId;
  private String userId;
  private String metaKey;
  private String metaValue;
  private String status;
  private String delflag;
  private String createUser;
  private Date createTime;
  private String updateUser;
  private Date updateTime;

  public Integer getUserMetaId()
  {
    return this.userMetaId;
  }

  public void setUserMetaId(Integer userMetaId)
  {
    this.userMetaId = userMetaId;
  }

  public String getUserId()
  {
    return this.userId;
  }

  public void setUserId(String userId)
  {
    this.userId = userId;
  }

  public String getMetaKey()
  {
    return this.metaKey;
  }

  public void setMetaKey(String metaKey)
  {
    this.metaKey = metaKey;
  }

  public String getMetaValue()
  {
    return this.metaValue;
  }

  public void setMetaValue(String metaValue)
  {
    this.metaValue = metaValue;
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
package domain;

import java.util.Date;

public class FeedFavorite extends PageDomain
{
  private static final long serialVersionUID = -8967988571179108440L;
  private Integer feedId;
  private String title;
  private String link;
  private Date updated;
  private String updatedStr;
  private String status;
  private String delflag;
  private String createUser;
  private Date createTime;
  private String updateUser;
  private Date updateTime;
  private String description;

  public Integer getFeedId()
  {
    return this.feedId;
  }

  public void setFeedId(Integer feedId)
  {
    this.feedId = feedId;
  }

  public String getTitle()
  {
    return this.title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public String getLink()
  {
    return this.link;
  }

  public void setLink(String link)
  {
    this.link = link;
  }

  public Date getUpdated()
  {
    return this.updated;
  }

  public void setUpdated(Date updated)
  {
    this.updated = updated;
  }

  public String getUpdatedStr()
  {
    return this.updatedStr;
  }

  public void setUpdatedStr(String updatedStr)
  {
    this.updatedStr = updatedStr;
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

  public String getDescription()
  {
    return this.description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }
}
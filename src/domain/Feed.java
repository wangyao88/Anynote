package domain;

import java.util.Date;

public class Feed extends PageDomain
{
  private static final long serialVersionUID = 8562658026836701282L;
  private Integer feedId;
  private String feedName;
  private String feedUrl;
  private Integer feedCount;
  private Integer parentId;
  private String isleaf;
  private String status;
  private String delflag;
  private String createUser;
  private Date createTime;
  private String updateUser;
  private Date updateTime;

  public Integer getFeedId()
  {
    return this.feedId;
  }

  public void setFeedId(Integer feedId)
  {
    this.feedId = feedId;
  }

  public String getFeedName()
  {
    return this.feedName;
  }

  public void setFeedName(String feedName)
  {
    this.feedName = feedName;
  }

  public String getFeedUrl()
  {
    return this.feedUrl;
  }

  public void setFeedUrl(String feedUrl)
  {
    this.feedUrl = feedUrl;
  }

  public Integer getFeedCount()
  {
    return this.feedCount;
  }

  public void setFeedCount(Integer feedCount)
  {
    this.feedCount = feedCount;
  }

  public Integer getParentId()
  {
    return this.parentId;
  }

  public void setParentId(Integer parentId)
  {
    this.parentId = parentId;
  }

  public String getIsleaf()
  {
    return this.isleaf;
  }

  public void setIsleaf(String isleaf)
  {
    this.isleaf = isleaf;
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
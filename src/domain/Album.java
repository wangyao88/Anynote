package domain;

import java.util.Date;

public class Album extends PageDomain
{
  private static final long serialVersionUID = -2664912367950031873L;
  private Integer albumId;
  private String albumName;
  private Integer parentId;
  private String isleaf;
  private String status;
  private String delflag;
  private String createUser;
  private Date createTime;
  private String updateUser;
  private Date updateTime;

  public Integer getAlbumId()
  {
    return this.albumId;
  }

  public void setAlbumId(Integer albumId)
  {
    this.albumId = albumId;
  }

  public String getAlbumName()
  {
    return this.albumName;
  }

  public void setAlbumName(String albumName)
  {
    this.albumName = albumName;
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
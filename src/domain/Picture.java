package domain;

import java.util.Date;

public class Picture extends PageDomain
{
  private static final long serialVersionUID = 3968883430538087863L;
  private Integer pictureId;
  private Integer albumId;
  private String pictureName;
  private String type;
  private String lpath;
  private String spath;
  private String status;
  private String delflag;
  private String createUser;
  private Date createTime;
  private String updateUser;
  private Date updateTime;

  public Integer getPictureId()
  {
    return this.pictureId;
  }

  public void setPictureId(Integer pictureId)
  {
    this.pictureId = pictureId;
  }

  public Integer getAlbumId()
  {
    return this.albumId;
  }

  public void setAlbumId(Integer albumId)
  {
    this.albumId = albumId;
  }

  public String getPictureName()
  {
    return this.pictureName;
  }

  public void setPictureName(String pictureName)
  {
    this.pictureName = pictureName;
  }

  public String getType()
  {
    return this.type;
  }

  public void setType(String type)
  {
    this.type = type;
  }

  public String getLpath()
  {
    return this.lpath;
  }

  public void setLpath(String lpath)
  {
    this.lpath = lpath;
  }

  public String getSpath()
  {
    return this.spath;
  }

  public void setSpath(String spath)
  {
    this.spath = spath;
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
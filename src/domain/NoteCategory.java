package domain;

import java.util.Date;

public class NoteCategory extends PageDomain
{
  private static final long serialVersionUID = 8886890615383931685L;
  private Integer categoryId;
  private String categoryName;
  private int noteCount;
  private String status;
  private String delflag;
  private String createUser;
  private Date createTime;
  private String updateUser;
  private Date updateTime;

  public Integer getCategoryId()
  {
    return this.categoryId;
  }

  public void setCategoryId(Integer categoryId)
  {
    this.categoryId = categoryId;
  }

  public String getCategoryName()
  {
    return this.categoryName;
  }

  public void setCategoryName(String categoryName)
  {
    this.categoryName = categoryName;
  }

  public int getNoteCount()
  {
    return this.noteCount;
  }

  public void setNoteCount(int noteCount)
  {
    this.noteCount = noteCount;
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
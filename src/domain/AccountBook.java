package domain;

import java.util.Date;

public class AccountBook extends PageDomain
{
  private static final long serialVersionUID = 4991683797469522925L;
  private Integer accountBookId;
  private String accountBookName;
  private String status;
  private String delflag;
  private String createUser;
  private Date createTime;
  private String updateUser;
  private Date updateTime;

  public Integer getAccountBookId()
  {
    return this.accountBookId;
  }

  public void setAccountBookId(Integer accountBookId)
  {
    this.accountBookId = accountBookId;
  }

  public String getAccountBookName()
  {
    return this.accountBookName;
  }

  public void setAccountBookName(String accountBookName)
  {
    this.accountBookName = accountBookName;
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
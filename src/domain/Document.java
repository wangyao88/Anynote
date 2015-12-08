package domain;

import java.util.Date;

public class Document extends PageDomain
{
  private static final long serialVersionUID = -2913965919556578984L;
  private Integer documentId;
  private String documentName;
  private String link;
  private String type;
  private Integer size;
  private String tags;
  private Integer parentId;
  private String isleaf;
  private String status;
  private String delflag;
  private String createUser;
  private Date createTime;
  private String updateUser;
  private Date updateTime;

  public Integer getDocumentId()
  {
    return this.documentId;
  }

  public void setDocumentId(Integer documentId)
  {
    this.documentId = documentId;
  }

  public String getDocumentName()
  {
    return this.documentName;
  }

  public void setDocumentName(String documentName)
  {
    this.documentName = documentName;
  }

  public String getLink()
  {
    return this.link;
  }

  public void setLink(String link)
  {
    this.link = link;
  }

  public String getType()
  {
    return this.type;
  }

  public void setType(String type)
  {
    this.type = type;
  }

  public Integer getSize()
  {
    return this.size;
  }

  public void setSize(Integer size)
  {
    this.size = size;
  }

  public String getTags()
  {
    return this.tags;
  }

  public void setTags(String tags)
  {
    this.tags = tags;
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
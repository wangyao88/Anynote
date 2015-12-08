package domain;

public class BaseDomain
{
  private String createDateStr;
  private String updateDateStr;
  private String fromDate;
  private String toDate;

  public String getCreateDateStr()
  {
    return this.createDateStr;
  }

  public void setCreateDateStr(String createDateStr)
  {
    this.createDateStr = createDateStr;
  }

  public String getUpdateDateStr()
  {
    return this.updateDateStr;
  }

  public void setUpdateDateStr(String updateDateStr)
  {
    this.updateDateStr = updateDateStr;
  }

  public String getFromDate()
  {
    return this.fromDate;
  }

  public void setFromDate(String fromDate)
  {
    this.fromDate = fromDate;
  }

  public String getToDate()
  {
    return this.toDate;
  }

  public void setToDate(String toDate)
  {
    this.toDate = toDate;
  }
}
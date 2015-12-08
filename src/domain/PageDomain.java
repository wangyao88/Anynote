package domain;

import java.io.Serializable;

public class PageDomain extends BaseDomain
  implements Serializable
{
  private static final long serialVersionUID = -1970099017324708241L;
  private int start = 0;
  private int limit = 20;

  public int getStart()
  {
    return this.start;
  }

  public void setStart(int start)
  {
    this.start = start;
  }

  public int getLimit()
  {
    return this.limit;
  }

  public void setLimit(int limit)
  {
    this.limit = limit;
  }
}
package dao;

import domain.AccountBook;
import java.util.List;

public abstract interface AccountBookDAO
{
  public abstract AccountBook selectByPrimaryKey(int paramInt);

  public abstract List<AccountBook> selectByCriteria(AccountBook paramAccountBook);

  public abstract int countByCriteria(AccountBook paramAccountBook);

  public abstract void insert(AccountBook paramAccountBook);

  public abstract int updateByPrimaryKey(AccountBook paramAccountBook);

  public abstract int deleteByPrimaryKey(int paramInt);
}
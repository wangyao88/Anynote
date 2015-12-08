package dao;

import domain.AccountCategory;
import java.util.List;

public abstract interface AccountCategoryDAO
{
  public abstract AccountCategory selectByPrimaryKey(int paramInt);

  public abstract List<AccountCategory> selectByCriteria(AccountCategory paramAccountCategory);

  public abstract List<AccountCategory> selectByCriteriaForPaging(AccountCategory paramAccountCategory, int paramInt1, int paramInt2);

  public abstract int countByCriteria(AccountCategory paramAccountCategory);

  public abstract void insert(AccountCategory paramAccountCategory);

  public abstract int updateByPrimaryKey(AccountCategory paramAccountCategory);

  public abstract int deleteByPrimaryKey(int paramInt);
}
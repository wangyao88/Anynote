package service.impl;

import dao.AccountCategoryDAO;
import domain.AccountCategory;
import global.security.SessionUtils;
import java.sql.Timestamp;
import java.util.List;
import service.AccountCategoryService;

public class AccountCategoryServiceImpl
  implements AccountCategoryService
{
  private AccountCategoryDAO accountCategoryDao = null;

  public void setAccountCategoryDao(AccountCategoryDAO accountCategoryDao)
  {
    this.accountCategoryDao = accountCategoryDao;
  }

  public int countByCriteria(AccountCategory paramAccountCategory)
  {
    paramAccountCategory = getConditions(paramAccountCategory);
    int count = this.accountCategoryDao.countByCriteria(paramAccountCategory);
    return count;
  }

  public List<AccountCategory> selectByCriteria(AccountCategory paramAccountCategory)
  {
    paramAccountCategory = getConditions(paramAccountCategory);
    List accountCategoryList = this.accountCategoryDao.selectByCriteria(paramAccountCategory);
    if ((accountCategoryList != null) && (accountCategoryList.size() != 0)) {
      return accountCategoryList;
    }
    return null;
  }

  public List<AccountCategory> selectByCriteriaForPaging(AccountCategory paramAccountCategory)
  {
    paramAccountCategory = getConditions(paramAccountCategory);
    List accountCategoryList = this.accountCategoryDao.selectByCriteriaForPaging(paramAccountCategory, paramAccountCategory.getStart(), paramAccountCategory.getLimit());
    if ((accountCategoryList != null) && (accountCategoryList.size() != 0)) {
      return accountCategoryList;
    }
    return null;
  }

  public void insert(AccountCategory accountCategory)
  {
    accountCategory.setDelflag("1");

    Timestamp sysdate = new Timestamp(System.currentTimeMillis());
    accountCategory.setCreateUser(SessionUtils.getCurrentUserId());
    accountCategory.setCreateTime(sysdate);
    accountCategory.setUpdateUser(SessionUtils.getCurrentUserId());
    accountCategory.setUpdateTime(sysdate);
    this.accountCategoryDao.insert(accountCategory);
  }

  public void update(AccountCategory accountCategory)
  {
    Timestamp sysdate = new Timestamp(System.currentTimeMillis());
    accountCategory.setUpdateUser(SessionUtils.getCurrentUserId());
    accountCategory.setUpdateTime(sysdate);
    this.accountCategoryDao.updateByPrimaryKey(accountCategory);
  }

  public void delete(String accountCategoryIdStr)
  {
    String[] categoryIds = accountCategoryIdStr.split(",");
    for (int i = 0; i < categoryIds.length; i++) {
      int categroyId = Integer.parseInt(categoryIds[i]);

      this.accountCategoryDao.deleteByPrimaryKey(categroyId);
    }
  }

  private AccountCategory getConditions(AccountCategory paramAccountCategory)
  {
    paramAccountCategory.setCreateUser(SessionUtils.getCurrentUserId());
    return paramAccountCategory;
  }
}
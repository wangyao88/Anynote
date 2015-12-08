package dao.impl;

import dao.AccountCategoryDAO;
import domain.AccountCategory;
import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class AccountCategoryDAOImpl extends SqlMapClientDaoSupport
  implements AccountCategoryDAO
{
  public AccountCategory selectByPrimaryKey(int id)
  {
    AccountCategory record = (AccountCategory)getSqlMapClientTemplate().queryForObject("an_account_category.selectByPrimaryKey", Integer.valueOf(id));
    return record;
  }

  public List<AccountCategory> selectByCriteria(AccountCategory accountCategory)
  {
    List list = getSqlMapClientTemplate().queryForList("an_account_category.selectByCriteria", accountCategory);
    return list;
  }

  public List<AccountCategory> selectByCriteriaForPaging(AccountCategory accountCategory, int start, int limit)
  {
    List list = getSqlMapClientTemplate().queryForList("an_account_category.selectByCriteria", accountCategory, start, limit);
    return list;
  }

  public int countByCriteria(AccountCategory accountCategory)
  {
    Integer count = (Integer)getSqlMapClientTemplate().queryForObject("an_account_category.countByCriteria", accountCategory);
    return count.intValue();
  }

  public void insert(AccountCategory accountCategory)
  {
    getSqlMapClientTemplate().insert("an_account_category.insert", accountCategory);
  }

  public int updateByPrimaryKey(AccountCategory accountCategory)
  {
    int rows = getSqlMapClientTemplate().update("an_account_category.updateByPrimaryKey", accountCategory);
    return rows;
  }

  public int deleteByPrimaryKey(int id)
  {
    int rows = getSqlMapClientTemplate().delete("an_account_category.deleteByPrimaryKey", Integer.valueOf(id));
    return rows;
  }
}
package dao.impl;

import dao.AccountBookDAO;
import domain.AccountBook;
import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class AccountBookDAOImpl extends SqlMapClientDaoSupport
  implements AccountBookDAO
{
  public AccountBook selectByPrimaryKey(int id)
  {
    AccountBook record = (AccountBook)getSqlMapClientTemplate().queryForObject("an_account_book.selectByPrimaryKey", Integer.valueOf(id));
    return record;
  }

  public List<AccountBook> selectByCriteria(AccountBook accountBook)
  {
    List list = getSqlMapClientTemplate().queryForList("an_account_book.selectByCriteria", accountBook);
    return list;
  }

  public int countByCriteria(AccountBook accountBook)
  {
    Integer count = (Integer)getSqlMapClientTemplate().queryForObject("an_account_book.countByCriteria", accountBook);
    return count.intValue();
  }

  public void insert(AccountBook accountBook)
  {
    getSqlMapClientTemplate().insert("an_account_book.insert", accountBook);
  }

  public int updateByPrimaryKey(AccountBook accountBook)
  {
    int rows = getSqlMapClientTemplate().update("an_account_book.updateByPrimaryKey", accountBook);
    return rows;
  }

  public int deleteByPrimaryKey(int id)
  {
    int rows = getSqlMapClientTemplate().delete("an_account_book.deleteByPrimaryKey", Integer.valueOf(id));
    return rows;
  }
}
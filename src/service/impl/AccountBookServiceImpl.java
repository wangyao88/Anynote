package service.impl;

import dao.AccountBookDAO;
import domain.AccountBook;
import global.security.SessionUtils;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import service.AccountBookService;
import util.DateUtils;

public class AccountBookServiceImpl
  implements AccountBookService
{
  private AccountBookDAO accountBookDao = null;

  public void setAccountBookDao(AccountBookDAO accountBookDao)
  {
    this.accountBookDao = accountBookDao;
  }

  public List<AccountBook> selectByCriteria(AccountBook paramAccountBook)
  {
    paramAccountBook = getConditions(paramAccountBook);
    List accountBookList = this.accountBookDao.selectByCriteria(paramAccountBook);
    List results = new ArrayList();
    if (accountBookList != null) {
      for (int i = 0; i < accountBookList.size(); i++) {
        AccountBook accountBook = (AccountBook)accountBookList.get(i);
        accountBook.setUpdateDateStr(DateUtils.formatDate2Str(accountBook.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"));
        results.add(accountBook);
      }
    }
    return results;
  }

  public void insert(AccountBook accountBook)
  {
    accountBook.setDelflag("1");

    Timestamp sysdate = new Timestamp(System.currentTimeMillis());
    accountBook.setCreateUser(SessionUtils.getCurrentUserId());
    accountBook.setCreateTime(sysdate);
    accountBook.setUpdateUser(SessionUtils.getCurrentUserId());
    accountBook.setUpdateTime(sysdate);
    this.accountBookDao.insert(accountBook);
  }

  public void update(AccountBook accountBook)
  {
    Timestamp sysdate = new Timestamp(System.currentTimeMillis());
    accountBook.setUpdateUser(SessionUtils.getCurrentUserId());
    accountBook.setUpdateTime(sysdate);
    this.accountBookDao.updateByPrimaryKey(accountBook);
  }

  public void delete(Integer accountBookId)
  {
    this.accountBookDao.deleteByPrimaryKey(accountBookId.intValue());
  }

  private AccountBook getConditions(AccountBook paramAccountBook)
  {
    paramAccountBook.setCreateUser(SessionUtils.getCurrentUserId());
    return paramAccountBook;
  }
}
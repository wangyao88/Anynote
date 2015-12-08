package dao;

import domain.User;
import java.util.List;

public abstract interface UserDAO
{
  public abstract User selectByPrimaryKey(String paramString);

  public abstract List<User> selectByCriteria(User paramUser);

  public abstract List<User> selectByCriteriaForPaging(User paramUser, int paramInt1, int paramInt2);

  public abstract int countByCriteria(User paramUser);

  public abstract void insert(User paramUser);

  public abstract int updateByPrimaryKey(User paramUser);

  public abstract int deleteByPrimaryKey(String paramString);
}
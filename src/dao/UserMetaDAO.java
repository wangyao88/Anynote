package dao;

import domain.UserMeta;
import java.util.List;

public abstract interface UserMetaDAO
{
  public abstract UserMeta selectByPrimaryKey(int paramInt);

  public abstract List<UserMeta> selectByCriteria(UserMeta paramUserMeta);

  public abstract int countByCriteria(UserMeta paramUserMeta);

  public abstract void insert(UserMeta paramUserMeta);

  public abstract int updateByPrimaryKey(UserMeta paramUserMeta);

  public abstract int updateByUserIdAndMetaKey(UserMeta paramUserMeta);

  public abstract int deleteByPrimaryKey(int paramInt);

  public abstract int deleteByUserId(String paramString);
}
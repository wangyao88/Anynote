package dao;

import domain.Picture;
import java.util.List;

public abstract interface PictureDAO
{
  public abstract Picture selectByPrimaryKey(int paramInt);

  public abstract List<Picture> selectByCriteria(Picture paramPicture);

  public abstract List<Picture> selectByCriteriaForPaging(Picture paramPicture, int paramInt1, int paramInt2);

  public abstract int countByCriteria(Picture paramPicture);

  public abstract void insert(Picture paramPicture);

  public abstract int updateByPrimaryKey(Picture paramPicture);

  public abstract int deleteByPrimaryKey(int paramInt);
}
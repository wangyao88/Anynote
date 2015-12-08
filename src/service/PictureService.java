package service;

import dao.PictureDAO;
import domain.Picture;
import domain.PictureWithBLOBs;
import java.util.List;

public abstract interface PictureService
{
  public abstract void setPictureDao(PictureDAO paramPictureDAO);

  public abstract Picture selectByPrimaryKey(int paramInt);

  public abstract int countByCriteria(Picture paramPicture);

  public abstract List<Picture> selectByCriteria(Picture paramPicture);

  public abstract List<Picture> selectByCriteriaForPaging(Picture paramPicture);

  public abstract void insert(PictureWithBLOBs paramPictureWithBLOBs);

  public abstract int update(PictureWithBLOBs paramPictureWithBLOBs);

  public abstract void update(String paramString1, String paramString2)
    throws Exception;

  public abstract void move(String paramString1, String paramString2);

  public abstract void delete(String paramString1, String paramString2, String paramString3)
    throws Exception;

  public abstract int deleteByPrimaryKey(int paramInt);
}
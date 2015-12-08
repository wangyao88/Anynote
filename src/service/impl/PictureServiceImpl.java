package service.impl;

import dao.PictureDAO;
import domain.Picture;
import domain.PictureWithBLOBs;
import global.security.SessionUtils;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import service.PictureService;
import util.FileUtils;
import util.StringUtils;

public class PictureServiceImpl
  implements PictureService
{
  private PictureDAO pictureDao = null;

  public void setPictureDao(PictureDAO pictureDao)
  {
    this.pictureDao = pictureDao;
  }

  public Picture selectByPrimaryKey(int pictureId)
  {
    Picture picture = this.pictureDao.selectByPrimaryKey(pictureId);
    return picture;
  }

  public int countByCriteria(Picture paramPicture)
  {
    paramPicture = getConditions(paramPicture);
    int count = this.pictureDao.countByCriteria(paramPicture);
    return count;
  }

  public List<Picture> selectByCriteria(Picture paramPicture)
  {
    paramPicture = getConditions(paramPicture);
    List pictureList = this.pictureDao.selectByCriteria(paramPicture);
    return pictureList;
  }

  public List<Picture> selectByCriteriaForPaging(Picture paramPicture)
  {
    paramPicture = getConditions(paramPicture);
    List<Picture> pictureList = this.pictureDao.selectByCriteriaForPaging(paramPicture, 
      paramPicture.getStart(), paramPicture.getLimit());
    if (pictureList != null) {
      List<Picture> resultList = new ArrayList<Picture>();
      for (Picture picture : pictureList) {
        resultList.add(picture);
      }
      return resultList;
    }
    return null;
  }

  public void insert(PictureWithBLOBs picture)
  {
    String userId = SessionUtils.getCurrentUserId();
    if (StringUtils.isEmpty(userId)) {
      userId = picture.getCreateUser();
    }

    picture.setDelflag("1");

    Timestamp sysdate = new Timestamp(System.currentTimeMillis());
    picture.setCreateUser(userId);
    picture.setCreateTime(sysdate);
    picture.setUpdateUser(userId);
    picture.setUpdateTime(sysdate);
    this.pictureDao.insert(picture);
  }

  public int update(PictureWithBLOBs picture)
  {
    Timestamp sysdate = new Timestamp(System.currentTimeMillis());
    picture.setUpdateUser(SessionUtils.getCurrentUserId());
    picture.setUpdateTime(sysdate);
    return this.pictureDao.updateByPrimaryKey(picture);
  }

  public void update(String pictureIds, String pictureNames)
    throws Exception
  {
    if ((StringUtils.isNotEmpty(pictureIds)) && (StringUtils.isNotEmpty(pictureNames))) {
      String[] pictureIdArr = pictureIds.split(",");
      String[] pictureNameArr = pictureNames.split(",");
      for (int i = 0; i < pictureIdArr.length; i++) {
        PictureWithBLOBs temp = new PictureWithBLOBs();
        temp.setPictureId(Integer.valueOf(Integer.parseInt(pictureIdArr[i])));
        temp.setPictureName(pictureNameArr[i]);

        this.pictureDao.updateByPrimaryKey(temp);
      }
    }
  }

  public void move(String pictureIds, String albumId)
  {
    if ((StringUtils.isNotEmpty(pictureIds)) && (StringUtils.isNotEmpty(albumId))) {
      String[] pictureIdArr = pictureIds.split(",");
      PictureWithBLOBs temp = new PictureWithBLOBs();
      temp.setAlbumId(Integer.valueOf(Integer.parseInt(albumId)));
      for (int i = 0; i < pictureIdArr.length; i++) {
        temp.setPictureId(Integer.valueOf(Integer.parseInt(pictureIdArr[i])));

        this.pictureDao.updateByPrimaryKey(temp);
      }
    }
  }

  public void delete(String pictureIds, String uuids, String uploadFilePath)
    throws Exception
  {
    String[] pictureIdArr = pictureIds.split(",");
    String[] uuidArr = uuids.split(",");
    for (int i = 0; i < pictureIdArr.length; i++)
    {
      this.pictureDao.deleteByPrimaryKey(Integer.parseInt(pictureIdArr[i]));

      String lpath = uploadFilePath + "/" + uuidArr[i];

      String spath = uploadFilePath + "/" + "thumbnail" + "/" + FileUtils.getFileName(uuidArr[i]) + "_s." + FileUtils.getFileTypeByName(uuidArr[i]);

      FileUtils.delete(lpath);

      FileUtils.delete(spath);
    }
  }

  public int deleteByPrimaryKey(int id)
  {
    return this.pictureDao.deleteByPrimaryKey(id);
  }

  private Picture getConditions(Picture paramPicture)
  {
    paramPicture.setCreateUser(SessionUtils.getCurrentUserId());
    return paramPicture;
  }
}
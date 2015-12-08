package service.impl;

import dao.AlbumDAO;
import domain.Album;
import domain.Picture;
import domain.PictureWithBLOBs;
import global.Constants;
import global.security.SessionUtils;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import service.AlbumService;
import service.PictureService;
import util.StringUtils;

public class AlbumServiceImpl
  implements AlbumService
{
  private AlbumDAO albumDao = null;

  private PictureService pictureService = null;

  public void setAlbumDao(AlbumDAO albumDao)
  {
    this.albumDao = albumDao;
  }

  public void setPictureService(PictureService pictureService)
  {
    this.pictureService = pictureService;
  }

  public int countByCriteria(Album paramAlbum)
  {
    paramAlbum = getConditions(paramAlbum);
    int count = this.albumDao.countByCriteria(paramAlbum);
    return count;
  }

  public Album selectByPrimaryKey(Integer albumId)
  {
    return this.albumDao.selectByPrimaryKey(albumId.intValue());
  }

  public List<Album> selectByCriteria(Album paramAlbum)
  {
    paramAlbum = getConditions(paramAlbum);
    List albumList = this.albumDao.selectByCriteria(paramAlbum);
    if ((albumList != null) && (albumList.size() != 0)) {
      return albumList;
    }
    return null;
  }

  public JSONArray selectAlbumForTree(String parentId)
  {
    JSONArray res = new JSONArray();

    Album paramAlbum = new Album();
    if (StringUtils.isNotEmpty(parentId)) {
      paramAlbum.setParentId(Integer.valueOf(Integer.parseInt(parentId)));
    }

    List albumList = new ArrayList();
    albumList = selectByCriteria(paramAlbum);
    res = getAlbumTreeFromList(albumList, parentId);
    return res;
  }

  public void insert(Album album)
  {
    album.setDelflag("1");

    Timestamp sysdate = new Timestamp(System.currentTimeMillis());
    album.setCreateUser(SessionUtils.getCurrentUserId());
    album.setCreateTime(sysdate);
    album.setUpdateUser(SessionUtils.getCurrentUserId());
    album.setUpdateTime(sysdate);
    this.albumDao.insert(album);
  }

  public void update(Album album)
  {
    Timestamp sysdate = new Timestamp(System.currentTimeMillis());
    album.setUpdateUser(SessionUtils.getCurrentUserId());
    album.setUpdateTime(sysdate);
    this.albumDao.updateByPrimaryKey(album);
  }

  public void delete(int albumId, String uploadFilePath)
  {
    List<Album> albumList = new ArrayList<Album>();

    Album parentAlbum = new Album();
    if (albumId == 0)
      parentAlbum.setAlbumId(Integer.valueOf(albumId));
    else {
      parentAlbum = selectByPrimaryKey(Integer.valueOf(albumId));
    }
    getAlbumList(parentAlbum, albumList);
    for (Album album : albumList)
    {
      if ("1".equals(album.getIsleaf()))
      {
        PictureWithBLOBs paramPicture = new PictureWithBLOBs();
        paramPicture.setAlbumId(album.getAlbumId());
        paramPicture.setDelflag("1");

        List<Picture> pictureList = this.pictureService.selectByCriteria(paramPicture);
        if ((pictureList != null) && (pictureList.size() > 0)) {
          for (Picture picture : pictureList)
          {
            File lfile = new File(uploadFilePath + "/" + picture.getLpath());
            if (lfile != null) {
              lfile.delete();
            }

            File sfile = new File(uploadFilePath + "/" + "thumbnail" + "/" + picture.getSpath());
            if (sfile != null) {
              sfile.delete();
            }
          }
        }
      }

      this.albumDao.deleteByPrimaryKey(album.getAlbumId().intValue());
    }
  }

  private JSONArray getAlbumTreeFromList(List<Album> albumList, String parentId)
  {
    JSONArray results = new JSONArray();

    if ("-1".equals(parentId)) {
      JSONObject allAlbum = new JSONObject();
      allAlbum.put("id", "allPicture");
      allAlbum.put("text", "所有图片");
      allAlbum.put("leaf", Boolean.valueOf(true));
      results.add(allAlbum);

      JSONObject album = new JSONObject();
      album.put("id", "0");
      album.put("text", "我的相册");
      album.put("leaf", Boolean.valueOf(false));
      results.add(album);
    }
    else if (albumList != null) {
      for (int i = 0; i < albumList.size(); i++) {
        JSONObject albumNode = new JSONObject();
        albumNode.put("id", ((Album)albumList.get(i)).getAlbumId());
        albumNode.put("text", ((Album)albumList.get(i)).getAlbumName());
        albumNode.put("leaf", Constants.ISLEAF_MAP.get(((Album)albumList.get(i)).getIsleaf()));
        results.add(albumNode);
      }
    }

    return results;
  }

  private void getAlbumList(Album parentAlbum, List<Album> albumList)
  {
    albumList.add(parentAlbum);

    Album paramAlbum = new Album();
    paramAlbum.setParentId(parentAlbum.getAlbumId());
    List newAlbumList = selectByCriteria(paramAlbum);
    if (newAlbumList != null)
      for (int i = 0; i < newAlbumList.size(); i++)
        getAlbumList((Album)newAlbumList.get(i), albumList);
  }

  private Album getConditions(Album parentAlbum)
  {
    parentAlbum.setCreateUser(SessionUtils.getCurrentUserId());
    return parentAlbum;
  }
}
package dao;

import domain.Album;
import java.util.List;

public abstract interface AlbumDAO
{
  public abstract Album selectByPrimaryKey(int paramInt);

  public abstract List<Album> selectByCriteria(Album paramAlbum);

  public abstract int countByCriteria(Album paramAlbum);

  public abstract void insert(Album paramAlbum);

  public abstract int updateByPrimaryKey(Album paramAlbum);

  public abstract int deleteByPrimaryKey(int paramInt);
}
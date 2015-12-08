package service;

import dao.AlbumDAO;
import domain.Album;
import java.util.List;
import net.sf.json.JSONArray;

public abstract interface AlbumService {
	public abstract void setAlbumDao(AlbumDAO paramAlbumDAO);

	public abstract void setPictureService(PictureService paramPictureService);

	public abstract int countByCriteria(Album paramAlbum);

	public abstract Album selectByPrimaryKey(Integer paramInteger);

	public abstract List<Album> selectByCriteria(Album paramAlbum);

	public abstract JSONArray selectAlbumForTree(String paramString);

	public abstract void insert(Album paramAlbum);

	public abstract void update(Album paramAlbum);

	public abstract void delete(int paramInt, String paramString);
}
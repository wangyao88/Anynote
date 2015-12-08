package dao.impl;

import dao.AlbumDAO;
import domain.Album;
import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class AlbumDAOImpl extends SqlMapClientDaoSupport
  implements AlbumDAO
{
  public Album selectByPrimaryKey(int id)
  {
    Album record = (Album)getSqlMapClientTemplate().queryForObject("an_album.selectByPrimaryKey", Integer.valueOf(id));
    return record;
  }

  public List<Album> selectByCriteria(Album album)
  {
    List list = getSqlMapClientTemplate().queryForList("an_album.selectByCriteria", album);
    return list;
  }

  public int countByCriteria(Album album)
  {
    Integer count = (Integer)getSqlMapClientTemplate().queryForObject("an_album.countByCriteria", album);
    return count.intValue();
  }

  public void insert(Album album)
  {
    getSqlMapClientTemplate().insert("an_album.insert", album);
  }

  public int updateByPrimaryKey(Album album)
  {
    int rows = getSqlMapClientTemplate().update("an_album.updateByPrimaryKey", album);
    return rows;
  }

  public int deleteByPrimaryKey(int id)
  {
    int rows = getSqlMapClientTemplate().delete("an_album.deleteByPrimaryKey", Integer.valueOf(id));
    return rows;
  }
}
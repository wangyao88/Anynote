package service;

import dao.UserMetaDAO;
import domain.UserMeta;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONArray;

public abstract interface UserMetaService
{
  public abstract void setUserMetaDao(UserMetaDAO paramUserMetaDAO);

  public abstract List<UserMeta> selectByCriteria(UserMeta paramUserMeta);

  public abstract void insert(UserMeta paramUserMeta);

  public abstract void insert(String paramString, Map paramMap);

  public abstract void update(UserMeta paramUserMeta);

  public abstract void updateByUserIdAndMetaKey(String paramString, Map paramMap);

  public abstract void delete(Integer paramInteger);

  public abstract void deleteByUserId(String paramString);

  public abstract JSONArray getFavoriteMenuTree();

  public abstract void addFavoriteMenu(UserMeta paramUserMeta);
}
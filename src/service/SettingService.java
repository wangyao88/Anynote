package service;

import net.sf.json.JSONObject;

public abstract interface SettingService
{
  public abstract JSONObject getSetting()
    throws Exception;

  public abstract void saveSetting(JSONObject paramJSONObject)
    throws Exception;
}
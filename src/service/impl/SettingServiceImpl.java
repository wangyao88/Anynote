package service.impl;

import global.Constants;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Properties;
import net.sf.json.JSONObject;
import service.SettingService;

public class SettingServiceImpl
  implements SettingService
{
  public JSONObject getSetting()
    throws Exception
  {
    JSONObject rs = new JSONObject();

    InputStream is = null;
    try
    {
      is = Constants.class.getClassLoader().getResourceAsStream("../setting.properties");
      Properties p = new Properties();
      p.load(is);
      rs.put("emailSmtpHost", p.getProperty("setting.emailSmtpHost"));
      rs.put("emailAccount", p.getProperty("setting.emailAccount"));
      rs.put("emailPassword", p.getProperty("setting.emailPassword"));
      rs.put("imageWidth", p.getProperty("setting.imageWidth"));
      rs.put("imageHeight", p.getProperty("setting.imageHeight"));
      if ("true".equals(p.getProperty("setting.openRegister")))
        rs.put("openRegister", "on");
      else {
        rs.put("openRegister", "off");
      }
      return rs;
    } finally {
      if (is != null)
        is.close();
    }
  }

  public void saveSetting(JSONObject setting)
    throws Exception
  {
    FileOutputStream fos = null;
    PrintStream ps = null;
    try
    {
      String path = URLDecoder.decode(Constants.class.getClassLoader().getResource("../setting.properties").getPath(), "utf-8");
      fos = new FileOutputStream(new File(path));
      ps = new PrintStream(fos);
      ps.println("setting.emailSmtpHost=" + setting.optString("emailSmtpHost"));
      ps.println("setting.emailAccount=" + setting.optString("emailAccount"));
      ps.println("setting.emailPassword=" + setting.optString("emailPassword"));
      ps.println("setting.imageWidth=" + setting.optString("imageWidth"));
      ps.println("setting.imageHeight=" + setting.optString("imageHeight"));
      if ("on".equals(setting.optString("openRegister")))
        ps.println("setting.openRegister=true");
      else
        ps.println("setting.openRegister=false");
    }
    finally {
      if (ps != null) {
        ps.close();
      }
      if (fos != null)
        fos.close();
    }
  }
}
package util;

import global.SendEmail;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

public class ServletHelp
{
  public static void outRequestForJson(HttpServletRequest request, HttpServletResponse response, String res)
    throws IOException
  {
    response.setContentType("application/x-json; charset=UTF-8");
    response.getWriter().print(res);
  }

  public static String getRealPath(HttpServletRequest request, String virtualPath)
  {
    return request.getSession().getServletContext()
      .getRealPath(virtualPath);
  }

  public static Map getParameterMap(HttpServletRequest request)
  {
    Map properties = request.getParameterMap();

    Map returnMap = new HashMap();
    Iterator entries = properties.entrySet().iterator();

    String name = "";
    String value = "";
    while (entries.hasNext()) {
      Map.Entry entry = (Map.Entry)entries.next();
      name = (String)entry.getKey();
      Object valueObj = entry.getValue();
      if (valueObj == null) {
        value = "";
      } else if ((valueObj instanceof String[])) {
        String[] values = (String[])valueObj;
        for (int i = 0; i < values.length; i++) {
          value = values[i] + ",";
        }
        value = value.substring(0, value.length() - 1);
      } else {
        value = valueObj.toString();
      }
      returnMap.put(name, value);
    }
    return returnMap;
  }

  public static String getArrayFromMap(Map map, String needEmpty)
  {
    if (map == null) {
      return null;
    }
    StringBuffer sb = null;
    if (StringUtils.isNotEmpty(needEmpty))
      sb = new StringBuffer("[['', '" + needEmpty + "'],");
    else {
      sb = new StringBuffer("[");
    }
    Set keySet = map.keySet();
    Iterator ite = keySet.iterator();
    String key = "";
    while (ite.hasNext()) {
      key = (String)ite.next();
      sb.append("['" + key + "', '" + map.get(key) + "'],");
    }
    String rs = sb.toString();
    if (rs.endsWith(",")) {
      rs = rs.substring(0, rs.length() - 1);
    }
    return rs += "]";
  }

  public static ActionForward download(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, String url, String fileName)
    throws Exception
  {
    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(
      url));

    fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
    response.setHeader("Content-Disposition", "attachment;filename=" + 
      fileName);

    response.setContentType("application/x-msdownload");

    ServletOutputStream sos = response.getOutputStream();
    BufferedOutputStream bos = new BufferedOutputStream(sos);
    byte[] buffer = new byte[1024];
    int len = -1;
    while ((len = bis.read(buffer)) != -1) {
      bos.write(buffer, 0, len);
    }

    if (bos != null) {
      bos.close();
    }
    if (sos != null) {
      sos.close();
    }
    if (bis != null) {
      bis.close();
    }

    return null;
  }

  public static ActionForward downloadZip(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, String[] urls, String[] fileNames)
    throws Exception
  {
    String zipFileName = 
      DateUtils.formatDate2Str("yyyyMMddHHmmss");

    response.setContentType("application/x-zip-compressed");
    response.setHeader("Content-Disposition", "inline; filename=" + 
      zipFileName + ".zip");

    int size = urls.length;
    ZipOutputStream zipOutput = new ZipOutputStream(response
      .getOutputStream());
    zipOutput.setMethod(8);
    zipOutput.setEncoding("UTF-8");
    for (int i = 0; i < size; i++) {
      zipOutput.putNextEntry(new ZipEntry(fileNames[i]));
      writeZipStream(zipOutput, new FileInputStream(new File(urls[i])));
      zipOutput.flush();
      response.flushBuffer();
    }
    if (zipOutput != null) {
      zipOutput.close();
    }
    return null;
  }

  public static boolean sendEmail(String emails, String title, String content)
    throws Exception
  {
    boolean result = false;
    if (StringUtils.isNotEmpty(emails)) {
      title = "À´×ÔAnynote£º" + title;
      String[] emailArr = emails.split(",");
      if (emailArr != null) {
        for (int i = 0; i < emailArr.length; i++) {
          SendEmail sendEmail = new SendEmail(emailArr[i], title, 
            content);
          result = sendEmail.execSend();
        }
      }
    }
    return result;
  }

  private static void writeZipStream(ZipOutputStream zipOutput, InputStream in)
    throws Exception
  {
    if (in == null) {
      return;
    }
    byte[] b = new byte[5120];
    int len = 0;
    while ((len = in.read(b)) != -1) {
      zipOutput.write(b, 0, len);
    }
    in.close();
  }
}
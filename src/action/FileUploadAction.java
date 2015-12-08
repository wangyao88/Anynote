package action;

import global.security.SessionUtils;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import util.FileUtils;
import util.MessageUtils;
import util.ServletHelp;

public class FileUploadAction extends BaseAction
{
  public ActionForward uploadFileToDisk(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String uploadFilePath = ServletHelp.getRealPath(request, 
      MessageUtils.setParamMessage("/websrc/file/{0}/picture", new String[] { SessionUtils.getCurrentUserId() }));

    DiskFileItemFactory factory = new DiskFileItemFactory();
    ServletFileUpload upload = new ServletFileUpload(factory);
    List items = null;

    items = upload.parseRequest(request);
    Iterator it = items.iterator();
    while (it.hasNext())
    {
      FileItem fi = (FileItem)it.next();
      if ("fileData".equals(fi.getFieldName()))
      {
        FileUtils.writeFileToDisk(fi.get(), uploadFilePath + "\\" + fi.getName());
      }

    }

    JSONObject res = new JSONObject();
    res.put("success", Boolean.valueOf(true));
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }
}
package action;

import global.BaseException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import util.ServletHelp;

public class BaseAction extends DispatchAction
{
  public final ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    try
    {
      return super.execute(mapping, form, request, response);
    }
    catch (Exception e) {
      Logger logger = Logger.getLogger(BaseAction.class);
      logger.warn(e.getLocalizedMessage(), e);
      String exceptionMsg = BaseException.getExceptionStackTrace(e);

      JSONObject res = new JSONObject();
      res.put("success", Boolean.valueOf(false));
      res.put("exceptionMsg", exceptionMsg);
      ServletHelp.outRequestForJson(request, response, res.toString());
    }
    return null;
  }
}
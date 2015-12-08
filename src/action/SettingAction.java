package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import service.SettingService;
import util.DBTool;
import util.NetConnectionUtils;
import util.ServletHelp;
import util.StringUtils;

public class SettingAction extends BaseAction {
	private SettingService settingService = null;

	public void setSettingService(SettingService settingService) {
		this.settingService = settingService;
	}

	public ActionForward getSetting(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JSONObject res = new JSONObject();

		JSONObject setting = this.settingService.getSetting();
		if (setting != null) {
			res = setting;
		}
		res.put("success", Boolean.valueOf(true));
		ServletHelp.outRequestForJson(request, response, res.toString());
		return null;
	}

	public ActionForward saveSetting(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String settingStr = request.getParameter("setting");
		if (StringUtils.isNotEmpty(settingStr)) {
			JSONObject setting = JSONObject.fromObject(settingStr);

			this.settingService.saveSetting(setting);
		}

		JSONObject res = new JSONObject();
		res.put("success", Boolean.valueOf(true));
		ServletHelp.outRequestForJson(request, response, res.toString());
		return null;
	}
	
	public ActionForward backup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DBTool.backupDatabase();
		JSONObject res = new JSONObject();
		res.put("success", Boolean.valueOf(true));
		ServletHelp.outRequestForJson(request, response, res.toString());
		return null;
	}
	
	public ActionForward checkNet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		boolean flag = NetConnectionUtils.isConnect();
		JSONObject res = new JSONObject();
		res.put("success", Boolean.valueOf(true));
		res.put("flag", Boolean.valueOf(flag));
		ServletHelp.outRequestForJson(request, response, res.toString());
		return null;
	}
	
}
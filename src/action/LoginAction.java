package action;

import global.Constants;
import global.VerifyCode;
import global.security.AuthenticatorHolder;
import global.security.ClientSession;
import global.security.SessionUtils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import service.UserService;
import util.HttpRequestServer;
import util.ServletHelp;

public class LoginAction extends BaseAction {
	private UserService userService = null;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public ActionForward login(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userId = request.getParameter("userId");
		String password = request.getParameter("password");

		Map loginMap = this.userService.login(userId, password);
		if (((Boolean) loginMap.get("success")).booleanValue()) {
			request.getSession().setAttribute("CLIENT_SESSION",
					(ClientSession) loginMap.get("session"));
		}
		
//		String loginAnynoteNewSystem = HttpRequestServer.sendPost(Constants.S_ANYNOTENEW_URL + "/login/login", "username=jadyer&password=jadyer");
		
		String loginHistoryId = HttpRequestServer.sendPost(Constants.S_ANYNOTENEW_URL + "/loginHistory/addLoginHistory.do", "userId="+userId);
		request.getSession().setAttribute("loginHistoryId", loginHistoryId);
		
		String attendenceTimeId = HttpRequestServer.sendPost(Constants.S_ANYNOTENEW_URL + "/attendenceTime/getStanderAttendenceTimeId.do", "1=1");
		
		//²¹È«´ò¿¨¼ÇÂ¼
		//String leaveRecordResult = HttpRequestServer.sendPost(Constants.S_ANYNOTENEW_URL + "/attendenceTime/addDeficiencyAttendenceTimeRecord.do", "userId="+userId);
		
		JSONObject res = new JSONObject();
		res.put("success", (Boolean) loginMap.get("success"));
		res.put("message", (String) loginMap.get("message"));
		ServletHelp.outRequestForJson(request, response, res.toString());
		return null;
	}

	public ActionForward logout(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.getSession().removeAttribute("CLIENT_SESSION");
		AuthenticatorHolder.closeClientSession();
        String loginHistoryId = (String)request.getSession().getAttribute("loginHistoryId");
		HttpRequestServer.sendPost(Constants.S_ANYNOTENEW_URL + "/loginHistory/setLoginOutTime.do", "loginHistoryId="+loginHistoryId);		
		JSONObject res = new JSONObject();
		res.put("success", Boolean.valueOf(true));
		ServletHelp.outRequestForJson(request, response, res.toString());
		return null;
	}

	public ActionForward register(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map map = ServletHelp.getParameterMap(request);
		HttpSession session = request.getSession();
		map.put("verifyCodeInSession",
				(String) session.getAttribute("verifyCode"));
		map.put("request", request);

		JSONObject res = this.userService.register(map);

		ServletHelp.outRequestForJson(request, response, res.toString());
		return null;
	}

	public ActionForward resetPwd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map map = ServletHelp.getParameterMap(request);
		HttpSession session = request.getSession();
		map.put("verifyCodeInSession",
				(String) session.getAttribute("verifyCode"));

		JSONObject res = this.userService.resetPwd(map);

		ServletHelp.outRequestForJson(request, response, res.toString());
		return null;
	}

	public ActionForward getVerifyCode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setHeader("Cache-Control", "no-cache");

		String verifyCode = VerifyCode.generateTextCode(4, 5, null);
		request.getSession().setAttribute("verifyCode", verifyCode);

		response.setContentType("image/jpeg");
		BufferedImage bim = VerifyCode.generateImageCode(verifyCode, 60, 20, 3,
				true, Color.WHITE, Color.BLACK, null);
		ImageIO.write(bim, "JPEG", response.getOutputStream());

		return null;
	}

	public ActionForward loginCheck(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userId = request.getParameter("userId");

		String password = request.getParameter("password");

		Map loginMap = this.userService.login(userId, password);
		if (((Boolean) loginMap.get("success")).booleanValue()) {
			request.getSession().setAttribute("CLIENT_SESSION",
					(ClientSession) loginMap.get("session"));
		}

		JSONObject res = new JSONObject();
		res.put("success", (Boolean) loginMap.get("success"));
		res.put("message", (String) loginMap.get("message"));
		ServletHelp.outRequestForJson(request, response, res.toString());
		return null;
	}
	
	public ActionForward queryLoginInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userId = SessionUtils.getCurrentUserId();
		String loginHistoryInfosTemp = HttpRequestServer.sendPost(Constants.S_ANYNOTENEW_URL + "/loginHistory/getLoginHistoryInfos.do", "userId="+userId);
		String loginHistoryInfos = new String(loginHistoryInfosTemp.getBytes(),"utf-8");
		ServletHelp.outRequestForJson(request, response, loginHistoryInfos);
		return null;
	}
	
	public ActionForward getCurrentMonthLoginHistory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String userId = SessionUtils.getCurrentUserId();
		String currentMonthLoginHistoryTemp = HttpRequestServer.sendPost(Constants.S_ANYNOTENEW_URL + "/loginHistory/getCurrentMonthLoginHistory.do", "userId="+userId);
		String currentMonthLoginHistory = new String(currentMonthLoginHistoryTemp.getBytes(),"utf-8");
		ServletHelp.outRequestForJson(request, response, currentMonthLoginHistory);
		return null;
	}
	
}
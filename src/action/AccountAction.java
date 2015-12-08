package action;

import domain.Account;
import global.beanUtils.BeanUtils;
import global.security.SessionUtils;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import service.AccountService;
import util.DateUtils;
import util.DecimalFormatUtils;
import util.ServletHelp;
import util.StringUtils;

public class AccountAction extends BaseAction {
	private AccountService accountService;

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map map = request.getParameterMap();
		Account paramAccount = new Account();
		BeanUtils.populate(paramAccount, map);

		List accountList = this.accountService
				.selectByCriteriaForPaging(paramAccount);
		JSONArray datas = new JSONArray();
		if (accountList != null) {
			datas = JSONArray.fromObject(accountList);
		}
		int count = this.accountService.countByCriteria(paramAccount);

		JSONObject res = new JSONObject();
		res.put("success", Boolean.valueOf(true));
		res.put("datas", datas);
		res.put("results", Integer.valueOf(count));
		ServletHelp.outRequestForJson(request, response, res.toString());
		return null;
	}

	public ActionForward getAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String accountId = request.getParameter("accountId");

		Account account = this.accountService.selectByPrimaryKey(Integer
				.parseInt(accountId));

		JSONObject res = JSONObject.fromObject(account);
		res.put("success", Boolean.valueOf(true));
		ServletHelp.outRequestForJson(request, response, res.toString());
		return null;
	}

	public ActionForward getSum(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map map = request.getParameterMap();
		Account paramAccount = new Account();
		BeanUtils.populate(paramAccount, map);

		paramAccount.setAccountType("1");
		double inMoney = this.accountService.getTotalMoney(paramAccount);

		paramAccount.setAccountType("2");
		double outMoney = this.accountService.getTotalMoney(paramAccount);

		paramAccount.setAccountType("1");
		double currentInMoney = this.accountService
				.getCurrentMoney(paramAccount);

		paramAccount.setAccountType("2");
		double currentOutMoney = this.accountService
				.getCurrentMoney(paramAccount);

		JSONObject res = new JSONObject();
		res.put("success", Boolean.valueOf(true));
		res.put("inMoney", Double.valueOf(inMoney));
		res.put("outMoney", Double.valueOf(outMoney));
		res.put("balance",
				new Double(new DecimalFormat(".00").format(inMoney - outMoney)));
		res.put("currentInMoney", Double.valueOf(currentInMoney));
		res.put("currentOutMoney", Double.valueOf(currentOutMoney));
		ServletHelp.outRequestForJson(request, response, res.toString());
		return null;
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map map = request.getParameterMap();
		Account account = new Account();
		BeanUtils.populate(account, map);
		account.setAccountDate(DateUtils.formatStr2Date(
				account.getAccountDateStr(), "yyyy-MM-dd"));
		account.setDelflag("1");
		account.setStatus("1");

		String type = request.getParameter("type");
		if (type.equals("CREATE")) {
			this.accountService.insert(account);
		} else if (type.equals("UPDATE")) {
			String accountId = request.getParameter("accountId");
			account.setAccountId(Integer.valueOf(Integer.parseInt(accountId)));

			this.accountService.update(account);
		}

		JSONObject res = new JSONObject();
		res.put("success", Boolean.valueOf(true));
		String result = res.toString();

		ServletHelp.outRequestForJson(request, response, result);
		return null;
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String accountIdStr = request.getParameter("accountIds");

		if (StringUtils.isNotEmpty(accountIdStr)) {
			this.accountService.delete(accountIdStr);
		}

		JSONObject res = new JSONObject();
		res.put("success", Boolean.valueOf(true));
		String result = res.toString();

		ServletHelp.outRequestForJson(request, response, result);
		return null;
	}

	public ActionForward getGroupSumMoney(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map map = request.getParameterMap();
		Account paramAccount = new Account();
		BeanUtils.populate(paramAccount, map);

		paramAccount.setCreateUser(SessionUtils.getCurrentUserId());

		List accountList = this.accountService.getGroupSumMoney(paramAccount);
		JSONArray datas = new JSONArray();
		if (accountList != null) {
			datas = JSONArray.fromObject(accountList);
		}
		int count = this.accountService.countByCriteria(paramAccount);

		JSONObject res = new JSONObject();
		res.put("success", Boolean.valueOf(true));
		res.put("datas", datas);
		res.put("results", Integer.valueOf(count));
		ServletHelp.outRequestForJson(request, response, res.toString());
		return null;
	}
	
	public ActionForward getRunningDayReportSumMoney(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Account paramAccount = new Account();
		paramAccount.setCreateUser(SessionUtils.getCurrentUserId());
		List<Account> accountList = this.accountService.getRunningDayReportSumMoney(paramAccount);
		JSONArray datas = new JSONArray();
		if (accountList != null) {
			datas = JSONArray.fromObject(accountList);
		}
		JSONObject res = new JSONObject();
		res.put("success", Boolean.valueOf(true));
		res.put("datas", datas);
		ServletHelp.outRequestForJson(request, response, res.toString());
		return null;
	}
	
	public ActionForward selectReportData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String date = "";
		if(request.getParameter("dateOfReport") != null){
			date = request.getParameter("dateOfReport");
		}
		String type = request.getParameter("type");
		Account paramAccount = new Account();
		paramAccount.setCreateUser(SessionUtils.getCurrentUserId());
		paramAccount.setAccountDateStr(date);
		if(date.length() == 7){
			paramAccount.setYear(date.substring(0, 4));
			paramAccount.setMonth(date.substring(5, date.length()));
		}
		if(date.length() == 4){
			paramAccount.setYear(date);
		}
		request.getSession().setAttribute("reportDate", date);
		
		String data = "";
		if(request.getParameter("isLogin") != null && request.getParameter("isLogin").equals("yes")){
			data = this.accountService.selectYearReportSumByCriteria(paramAccount);
			request.getSession().setAttribute("reportDataOfYear", data);
			data = this.accountService.selectMonthReportSumByCriteria(paramAccount);
			request.getSession().setAttribute("reportDataOfMonth", data);
			data = this.accountService.selectDayReportCastSumByCriteria(paramAccount);
			request.getSession().setAttribute("reportDataOfDay", data);
			JSONObject res = new JSONObject();
			res.put("success", Boolean.valueOf(true));
			ServletHelp.outRequestForJson(request, response, res.toString());
			return null;
		}else{
			if(type.equals("year")){
				data = this.accountService.selectYearReportSumByCriteria(paramAccount);
				request.getSession().setAttribute("reportDataOfYear", data);
			}else if(type.equals("month")){
				data = this.accountService.selectMonthReportSumByCriteria(paramAccount);
				request.getSession().setAttribute("reportDataOfMonth", data);
			}else if(type.equals("day")){
				data = this.accountService.selectDayReportCastSumByCriteria(paramAccount);
				request.getSession().setAttribute("reportDataOfDay", data);
			}
			JSONObject res = new JSONObject();
			res.put("success", Boolean.valueOf(true));
			ServletHelp.outRequestForJson(request, response, res.toString());
			return null;
		}
		
	}
	
	public ActionForward queryMonthCastReportData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map map = request.getParameterMap();
		Account paramAccount = new Account();
		BeanUtils.populate(paramAccount, map);
		String date = request.getSession().getAttribute("reportDate").toString();
		if(date.length() == 7){
			paramAccount.setYear(date.substring(0, 4));
			paramAccount.setMonth(date.substring(5, date.length()));
		}
		if(date.length() == 4){
			paramAccount.setYear(date);
		}
		
		List<Account> accountList = this.accountService.queryMonthCastReportDataForPaging(paramAccount);
		JSONArray datas = new JSONArray();
		if (accountList != null) {
			datas = JSONArray.fromObject(accountList);
		}
		int count = this.accountService.queryMonthCastReportCountByCriteria(paramAccount);

		JSONObject res = new JSONObject();
		res.put("success", Boolean.valueOf(true));
		res.put("datas", datas);
		res.put("results", Integer.valueOf(count));
		ServletHelp.outRequestForJson(request, response, res.toString());
		return null;
	}
	
	public ActionForward queryMonthImportReportData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map map = request.getParameterMap();
		Account paramAccount = new Account();
		BeanUtils.populate(paramAccount, map);
		String date = request.getSession().getAttribute("reportDate").toString();
		if(date.length() == 7){
			paramAccount.setYear(date.substring(0, 4));
			paramAccount.setMonth(date.substring(5, date.length()));
		}
		if(date.length() == 4){
			paramAccount.setYear(date);
		}
		
		List<Account> accountList = this.accountService.queryMonthImportReportDataForPaging(paramAccount);
		JSONArray datas = new JSONArray();
		if (accountList != null) {
			datas = JSONArray.fromObject(accountList);
		}
		int count = this.accountService.queryMonthImportReportCountByCriteria(paramAccount);

		JSONObject res = new JSONObject();
		res.put("success", Boolean.valueOf(true));
		res.put("datas", datas);
		res.put("results", Integer.valueOf(count));
		ServletHelp.outRequestForJson(request, response, res.toString());
		return null;
	}
	
	public ActionForward getMonthRate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		Calendar c = Calendar.getInstance();
		Account paramAccount = new Account();
		paramAccount.setYear(String.valueOf(c.get(Calendar.YEAR)));
		paramAccount.setMonth(String.valueOf(c.get(Calendar.MONTH)+1));
		double rate = this.accountService.getMonthRate(paramAccount)*100;
		String data = DecimalFormatUtils.format(rate, ".00");
		request.getSession().setAttribute("monthRate", data);
		JSONObject res = new JSONObject();
		res.put("success", Boolean.valueOf(true));
		ServletHelp.outRequestForJson(request, response, res.toString());
		return null;
	}
	
	public ActionForward getYearRate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		Calendar c = Calendar.getInstance();
		Account paramAccount = new Account();
		paramAccount.setYear(String.valueOf(c.get(Calendar.YEAR)));
		double rate = this.accountService.getYearRate(paramAccount)*100;
		String data =  DecimalFormatUtils.format(rate, ".00");
		request.getSession().setAttribute("yearRate",data);
		JSONObject res = new JSONObject();
		res.put("success", Boolean.valueOf(true));
		ServletHelp.outRequestForJson(request, response, res.toString());
		return null;
	}
}
package action;

import domain.AccountBook;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import service.AccountBookService;
import util.ServletHelp;
import util.StringUtils;

public class AccountBookAction extends BaseAction {
	private AccountBookService accountBookService = null;

	public void setAccountBookService(AccountBookService accountBookService) {
		this.accountBookService = accountBookService;
	}

	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AccountBook paramAccountBook = new AccountBook();

		List accountBookList = new ArrayList();
		JSONArray datas = new JSONArray();
		accountBookList = this.accountBookService
				.selectByCriteria(paramAccountBook);
		if (accountBookList != null) {
			datas = JSONArray.fromObject(accountBookList);
		}

		JSONObject res = new JSONObject();
		res.put("datas", datas);
		ServletHelp.outRequestForJson(request, response, res.toString());
		return null;
	}

	public ActionForward getAccountBookTree(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List accountBookList = new ArrayList();
		AccountBook paramAccountBook = new AccountBook();
		accountBookList = this.accountBookService
				.selectByCriteria(paramAccountBook);
		JSONArray res = getAccountBookTreeFromList(accountBookList);
		ServletHelp.outRequestForJson(request, response, res.toString());
		return null;
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String accountBookName = request.getParameter("accountBookName");
		String accountBookId = request.getParameter("accountBookId");

		AccountBook accountBook = new AccountBook();
		accountBook.setStatus("1");
		accountBook.setDelflag("1");
		if (StringUtils.isNotEmpty(accountBookName)) {
			if (StringUtils.isNotEmpty(accountBookId)) {
				accountBook.setAccountBookId(Integer.valueOf(Integer
						.parseInt(accountBookId)));
				accountBook.setAccountBookName(accountBookName);

				this.accountBookService.update(accountBook);
			} else {
				accountBook.setAccountBookName(accountBookName);

				this.accountBookService.insert(accountBook);
			}

		}

		JSONObject res = new JSONObject();
		res.put("success", Boolean.valueOf(true));
		ServletHelp.outRequestForJson(request, response, res.toString());
		return null;
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String accountBookId = request.getParameter("accountBookId");
		if (StringUtils.isNotEmpty(accountBookId)) {
			this.accountBookService.delete(Integer.valueOf(Integer
					.parseInt(accountBookId)));
		}

		JSONObject res = new JSONObject();
		res.put("success", Boolean.valueOf(true));
		ServletHelp.outRequestForJson(request, response, res.toString());
		return null;
	}

	private JSONArray getAccountBookTreeFromList(
			List<AccountBook> accountBookList) {
		JSONArray results = new JSONArray();

		JSONObject allAccount = new JSONObject();
		allAccount.put("id", "allAccount");
		allAccount.put("text", "所有账目");
		allAccount.put("leaf", Boolean.valueOf(true));
		results.add(allAccount);

		if (accountBookList != null) {
			JSONArray children = new JSONArray();
			AccountBook temp = new AccountBook();
			for (int i = 0; i < accountBookList.size(); i++) {
				temp = (AccountBook) accountBookList.get(i);
				JSONObject accountBookNode = new JSONObject();
				accountBookNode.put("id", temp.getAccountBookId());
				accountBookNode.put("text", temp.getAccountBookName());
				accountBookNode.put("leaf", Boolean.valueOf(true));
				children.add(accountBookNode);
			}
			JSONObject accountBook = new JSONObject();
			accountBook.put("id", "0");
			accountBook.put("text", "我的账本");
			accountBook.put("children", children);
			results.add(accountBook);
		}

		JSONObject accountBookOpt = new JSONObject();
		accountBookOpt.put("id", "accountCategory");
		accountBookOpt.put("text", "收支项目");
		accountBookOpt.put("leaf", Boolean.valueOf(true));
		results.add(accountBookOpt);
		
		JSONArray childrenOfReport = new JSONArray();
		JSONObject report = new JSONObject();
		report.put("id", "yearReport");
		report.put("text", "年收支图表");
		report.put("leaf", Boolean.valueOf(true));
		childrenOfReport.add(report);
		report = new JSONObject();
		report.put("id", "monthReport");
		report.put("text", "月收支图表");
		report.put("leaf", Boolean.valueOf(true));
		childrenOfReport.add(report);
		report = new JSONObject();
		report.put("id", "dayReport");
		report.put("text", "日支出图表");
		report.put("leaf", Boolean.valueOf(true));
		childrenOfReport.add(report);
		
		JSONObject accountReport = new JSONObject();
		accountReport.put("id", "accountReport");
		accountReport.put("text", "报表管理");
		accountReport.put("leaf", Boolean.valueOf(false));
		accountReport.put("children", childrenOfReport);
		results.add(accountReport);

		return results;
	}
}
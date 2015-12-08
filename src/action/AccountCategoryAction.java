package action;

import domain.AccountCategory;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import service.AccountCategoryService;
import util.ServletHelp;
import util.StringUtils;

public class AccountCategoryAction extends BaseAction
{
  private AccountCategoryService accountCategoryService = null;

  public void setAccountCategoryService(AccountCategoryService accountCategoryService)
  {
    this.accountCategoryService = accountCategoryService;
  }

  public ActionForward query(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    AccountCategory paramAccountCategory = new AccountCategory();
    paramAccountCategory.setStatus(request.getParameter("status"));

    List accountCategoryList = new ArrayList();
    JSONArray datas = new JSONArray();
    accountCategoryList = this.accountCategoryService.selectByCriteria(paramAccountCategory);
    if (accountCategoryList != null) {
      datas = JSONArray.fromObject(accountCategoryList);
    }

    JSONObject res = new JSONObject();
    res.put("datas", datas);
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }

  public ActionForward queryForPaging(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String start = request.getParameter("start");
    String limit = request.getParameter("limit");
    AccountCategory paramAccountCategory = new AccountCategory();
    paramAccountCategory.setStart(Integer.parseInt(start));
    paramAccountCategory.setLimit(Integer.parseInt(limit));

    List accountCategoryList = new ArrayList();
    JSONArray datas = new JSONArray();
    accountCategoryList = this.accountCategoryService.selectByCriteriaForPaging(paramAccountCategory);
    if (accountCategoryList != null) {
      datas = JSONArray.fromObject(accountCategoryList);
    }
    int count = this.accountCategoryService.countByCriteria(paramAccountCategory);

    JSONObject res = new JSONObject();
    res.put("datas", datas);
    res.put("results", Integer.valueOf(count));
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }

  public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String jsonArr = request.getParameter("jsonArr");
    if (StringUtils.isNotEmpty(jsonArr)) {
      JSONArray accountCategoryArr = JSONArray.fromObject(jsonArr);
      for (int i = 0; i < accountCategoryArr.size(); i++) {
        JSONObject accountCategoryObj = accountCategoryArr.getJSONObject(i);
        AccountCategory accountCategory = new AccountCategory();
        accountCategory.setCategoryName(accountCategoryObj.getString("categoryName"));
        accountCategory.setDelflag("1");
        accountCategory.setStatus(accountCategoryObj.getString("status"));
        if (StringUtils.isNotEmpty(accountCategoryObj.getString("categoryName"))) {
          if (StringUtils.isNotEmpty(accountCategoryObj.getString("categoryId"))) {
            accountCategory.setCategoryId(Integer.valueOf(Integer.parseInt(accountCategoryObj.getString("categoryId"))));

            this.accountCategoryService.update(accountCategory);
          }
          else {
            this.accountCategoryService.insert(accountCategory);
          }
        }
      }

    }

    JSONObject res = new JSONObject();
    res.put("success", Boolean.valueOf(true));
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }

  public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String accountCategoryIdStr = request.getParameter("accountCategoryIds");

    this.accountCategoryService.delete(accountCategoryIdStr);

    JSONObject res = new JSONObject();
    res.put("success", Boolean.valueOf(true));
    ServletHelp.outRequestForJson(request, response, res.toString());
    return null;
  }
}
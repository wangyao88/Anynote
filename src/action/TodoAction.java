package action;

import global.Constants;
import global.beanUtils.BeanUtils;
import global.security.SessionUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import service.TodoService;
import util.DateUtils;
import util.ServletHelp;
import util.StringUtils;
import domain.Todo;

public class TodoAction extends BaseAction {
	private TodoService todoService = null;

	public void setTodoService(TodoService todoService) {
		this.todoService = todoService;
	}

	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map map = request.getParameterMap();
		Todo paramTodo = new Todo();
		BeanUtils.populate(paramTodo, map);

		List todoList = new ArrayList();
		JSONArray datas = new JSONArray();
		todoList = this.todoService.selectByCriteriaForPaging(paramTodo);
		if (todoList != null) {
			datas = JSONArray.fromObject(todoList);
		}
		int count = this.todoService.countByCriteria(paramTodo);

		JSONObject res = new JSONObject();
		res.put("datas", datas);
		res.put("results", Integer.valueOf(count));
		ServletHelp.outRequestForJson(request, response, res.toString());
		return null;
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String jsonArr = request.getParameter("jsonArr");
		if (StringUtils.isNotEmpty(jsonArr)) {
			JSONArray todoArr = JSONArray.fromObject(jsonArr);
			for (int i = 0; i < todoArr.size(); i++) {
				JSONObject todoObj = todoArr.getJSONObject(i);
				Todo todo = new Todo();
				todo.setTodoContent(todoObj.getString("todoContent"));
				if (StringUtils.isNotEmpty(todoObj.optString("categoryId")))
					todo.setCategoryId(Integer.valueOf(todoObj
							.getInt("categoryId")));
				else {
					todo.setCategoryId(Integer.valueOf(0));
				}
				if (StringUtils.isNotEmpty(todoObj.optString("dealDateStr")))
					todo.setDealDate(DateUtils.formatStr2Date(
							todoObj.getString("dealDateStr"), "yyyy-MM-dd"));
				else {
					todo.setDealDate(DateUtils.formatStr2Date("9999-12-31",
							"yyyy-MM-dd"));
				}
				todo.setLevel(todoObj.getString("level"));
				todo.setStatus("1");
				todo.setDelflag("1");
				if (StringUtils.isNotEmpty(todoObj.getString("todoId"))) {
					todo.setTodoId(Integer.valueOf(Integer.parseInt(todoObj
							.getString("todoId"))));

					this.todoService.update(todo);
				} else {
					this.todoService.insert(todo);
				}
			}

		}

		JSONObject res = new JSONObject();
		res.put("success", Boolean.valueOf(true));
		ServletHelp.outRequestForJson(request, response, res.toString());
		return null;
	}

	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String todoId = request.getParameter("todoId");

		if (StringUtils.isNotEmpty(todoId)) {
			Map map = request.getParameterMap();
			Todo todo = new Todo();
			BeanUtils.populate(todo, map);

			this.todoService.update(todo);
		}

		JSONObject res = new JSONObject();
		res.put("success", Boolean.valueOf(true));
		ServletHelp.outRequestForJson(request, response, res.toString());
		return null;
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String todoIdStr = request.getParameter("todoIds");

		if (StringUtils.isNotEmpty(todoIdStr)) {
			this.todoService.delete(todoIdStr);
		}

		JSONObject res = new JSONObject();
		res.put("success", Boolean.valueOf(true));
		ServletHelp.outRequestForJson(request, response, res.toString());
		return null;
	}

	public ActionForward finish(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String todoIdStr = request.getParameter("todoIds");

		String delflag = request.getParameter("delflag");

		if (StringUtils.isNotEmpty(todoIdStr)) {
			String[] todoIds = todoIdStr.split(",");
			for (int i = 0; i < todoIds.length; i++) {
				Todo paramTodo = new Todo();
				paramTodo.setTodoId(Integer.valueOf(Integer
						.parseInt(todoIds[i])));
				paramTodo.setDelflag(delflag);

				this.todoService.update(paramTodo);
			}

		}

		JSONObject res = new JSONObject();
		res.put("success", Boolean.valueOf(true));
		ServletHelp.outRequestForJson(request, response, res.toString());
		return null;
	}
	
	public ActionForward todoMonth(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map map = request.getParameterMap();
		Todo paramTodo = new Todo();
		BeanUtils.populate(paramTodo, map);
		Calendar c = Calendar.getInstance();
		paramTodo.setMonth(String.valueOf(c.get(Calendar.MONTH)+1));
		String data = this.todoService.queryCountAndCategoryByCriteria(paramTodo);
		request.getSession().setAttribute("columnData", data);
		initTodoYear(request);
		return null;
	}
	
	private void initTodoYear(HttpServletRequest request) throws Exception{
		Map map = request.getParameterMap();
		Todo paramTodo = new Todo();
		BeanUtils.populate(paramTodo, map);
		Calendar c = Calendar.getInstance();
		paramTodo.setYear(String.valueOf(c.get(Calendar.YEAR)));
		String dataStr = this.todoService.queryCountAndYearByCriteria(paramTodo);
		request.getSession().setAttribute("pieData", dataStr);
	}

	public ActionForward todoYear(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map map = request.getParameterMap();
		Todo paramTodo = new Todo();
		BeanUtils.populate(paramTodo, map);
		Calendar c = Calendar.getInstance();
		paramTodo.setYear(String.valueOf(c.get(Calendar.YEAR)));
		String dataStr = this.todoService.queryCountAndYearByCriteria(paramTodo);
		request.getSession().setAttribute("pieData", dataStr);
		return null;
	}
	
	public ActionForward iniTtomorrowTodo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DAY_OF_WEEK);
		Todo todo = null;
		Date tomorrow = new Date();
		c.add(Calendar.DATE, 1);
		tomorrow = c.getTime();
		
		boolean flag = this.todoService.checkTomorrowTodo(DateUtils.formatDate2Str(tomorrow, "yyyy-MM-dd"));
		if(flag){
			List<Todo> list = new ArrayList<Todo>();
			if(day == 1 || day == 7){
				for(int i = 0; i < Constants.TODO_WEEKEND.length; i++){
					todo = new Todo();
					todo.setCategoryId(2);
					todo.setTodoContent("[" + DateUtils.formatDate2Str(tomorrow, "yyyy-MM-dd") + "-" + (i+1) + "]--:" + Constants.TODO_WEEKEND[i]);
					todo.setDealDate(tomorrow);
					todo.setLevel("2");
					todo.setStatus("1");
					todo.setDelflag("1");
					todo.setCreateUser(SessionUtils.getCurrentUserId());
					todo.setCreateTime(new Date());
					todo.setUpdateUser(SessionUtils.getCurrentUserId());
					todo.setUpdateTime(new Date());
					list.add(todo);
				}
			}else{
				for(int i = 0; i < Constants.TODO_WEEK.length; i++){
					todo = new Todo();
					todo.setCategoryId(2);
					todo.setTodoContent("[" + DateUtils.formatDate2Str(tomorrow, "yyyy-MM-dd") + "-" + (i+1) + "]--:" + Constants.TODO_WEEK[i]);
					todo.setDealDate(tomorrow);
					todo.setLevel("2");
					todo.setStatus("1");
					todo.setDelflag("1");
					todo.setCreateUser(SessionUtils.getCurrentUserId());
					todo.setCreateTime(new Date());
					todo.setUpdateUser(SessionUtils.getCurrentUserId());
					todo.setUpdateTime(new Date());
					list.add(todo);
				}
			}
			this.todoService.saveBatch(list);
		}
		JSONObject res = new JSONObject();
		res.put("success", Boolean.valueOf(true));
		ServletHelp.outRequestForJson(request, response, res.toString());
		return null;
	}
	
	public ActionForward initDateTodo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String dateStr = request.getParameter("date");
		Date date = DateUtils.formatStr2Date(dateStr,"yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		int day = c.get(Calendar.DAY_OF_WEEK);
		Todo todo = null;
		boolean flag = this.todoService.checkTomorrowTodo(DateUtils.formatDate2Str(date, "yyyy-MM-dd"));
		if(flag){
			List<Todo> list = new ArrayList<Todo>();
			if(day == 1 || day == 7){
				for(int i = 0; i < Constants.TODO_WEEKEND.length; i++){
					todo = new Todo();
					todo.setCategoryId(2);
					todo.setTodoContent("[" + DateUtils.formatDate2Str(date, "yyyy-MM-dd") + "-" + (i+1) + "]--:" + Constants.TODO_WEEKEND[i]);
					todo.setDealDate(date);
					todo.setLevel("2");
					todo.setStatus("1");
					todo.setDelflag("1");
					todo.setCreateUser(SessionUtils.getCurrentUserId());
					todo.setCreateTime(new Date());
					todo.setUpdateUser(SessionUtils.getCurrentUserId());
					todo.setUpdateTime(new Date());
					list.add(todo);
				}
			}else{
				for(int i = 0; i < Constants.TODO_WEEK.length; i++){
					todo = new Todo();
					todo.setCategoryId(2);
					todo.setTodoContent("[" + DateUtils.formatDate2Str(date, "yyyy-MM-dd") + "-" + (i+1) + "]--:" + Constants.TODO_WEEK[i]);
					todo.setDealDate(date);
					todo.setLevel("2");
					todo.setStatus("1");
					todo.setDelflag("1");
					todo.setCreateUser(SessionUtils.getCurrentUserId());
					todo.setCreateTime(new Date());
					todo.setUpdateUser(SessionUtils.getCurrentUserId());
					todo.setUpdateTime(new Date());
					list.add(todo);
				}
			}
			this.todoService.saveBatch(list);
		}
		JSONObject res = new JSONObject();
		res.put("success", Boolean.valueOf(true));
		ServletHelp.outRequestForJson(request, response, res.toString());
		return null;
	}
	
	public ActionForward checkTodayTodo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Todo paramTodo = new Todo();
		paramTodo.setDealDateStr(DateUtils.formatDate2Str(new Date(), "yyyy-MM-dd"));
		paramTodo.setDelflag("1");
		int count = 0;
		count = this.todoService.querytodayTodoCountByCriteria(paramTodo);
		String msg = "今天有" + count + "条待办";
		JSONObject res = new JSONObject();
		res.put("success", Boolean.valueOf(true));
		res.put("msg", msg);
		ServletHelp.outRequestForJson(request, response, res.toString());
		return null;
	}
	
	public ActionForward queryClickTodo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Map map = request.getParameterMap();
		Todo paramTodo = new Todo();
		BeanUtils.populate(paramTodo, map);
		String delflag = request.getParameter("delflag");
		paramTodo.setDelflag(delflag);
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH) + 1;
		paramTodo.setMonth(String.valueOf(month));

		List<Todo> todoList = new ArrayList<Todo>();
		JSONArray datas = new JSONArray();
		todoList = this.todoService.selectByCriteriaForPaging(paramTodo);
		if (todoList != null) {
			datas = JSONArray.fromObject(todoList);
		}
		int count = this.todoService.countByCriteria(paramTodo);

		JSONObject res = new JSONObject();
		res.put("success", Boolean.valueOf(true));
		res.put("datas", datas);
		res.put("results", Integer.valueOf(count));
		ServletHelp.outRequestForJson(request, response, res.toString());
		return null;
	}
	
}
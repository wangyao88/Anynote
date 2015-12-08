package service.impl;

import global.security.SessionUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import service.TodoService;
import util.DateUtils;
import dao.TodoDAO;
import domain.Todo;

public class TodoServiceImpl implements TodoService {
	private TodoDAO todoDao = null;
	
	private static final String[] color = new String[]{"#F90421","#F904CD","#7704F9","#19F904","#EDF904","#3E086D","#F257ED","#2D032C","#BF2891"};

	public void setTodoDao(TodoDAO todoDao) {
		this.todoDao = todoDao;
	}

	public int countByCriteria(Todo paramTodo) {
		paramTodo = getConditions(paramTodo);
		int count = this.todoDao.countByCriteria(paramTodo);
		return count;
	}

	public List<Todo> selectByCriteriaForPaging(Todo paramTodo) {
		paramTodo = getConditions(paramTodo);
		paramTodo.setCreateUser(SessionUtils.getCurrentUserId());
		List todoList = this.todoDao.selectByCriteriaForPaging(paramTodo,
				paramTodo.getStart(), paramTodo.getLimit());
		List results = new ArrayList();
		if (todoList != null) {
			String dealDateStr = "";
			for (int i = 0; i < todoList.size(); i++) {
				Todo todo = (Todo) todoList.get(i);
				dealDateStr = DateUtils.formatDate2Str(todo.getDealDate(),
						"yyyy-MM-dd");
				if ("9999-12-31".equals(dealDateStr))
					todo.setDealDateStr("");
				else {
					todo.setDealDateStr(dealDateStr);
				}
				todo.setCreateDateStr(DateUtils.formatDate2Str(
						todo.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
				results.add(todo);
			}
		}
		return results;
	}

	public List<Todo> selectByCriteria(Todo paramTodo) {
		paramTodo = getConditions(paramTodo);
		paramTodo.setCreateUser(SessionUtils.getCurrentUserId());
		List todoList = this.todoDao.selectByCriteria(paramTodo);
		List results = new ArrayList();
		if (todoList != null) {
			for (int i = 0; i < todoList.size(); i++) {
				Todo todo = (Todo) todoList.get(i);
				todo.setDealDateStr(DateUtils.formatDate2Str(
						todo.getDealDate(), "yyyy-MM-dd"));
				todo.setCreateDateStr(DateUtils.formatDate2Str(
						todo.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
				results.add(todo);
			}
		}
		return results;
	}

	public void insert(Todo todo) {
		todo.setDelflag("1");

		Timestamp sysdate = new Timestamp(System.currentTimeMillis());
		todo.setCreateUser(SessionUtils.getCurrentUserId());
		todo.setCreateTime(sysdate);
		todo.setUpdateUser(SessionUtils.getCurrentUserId());
		todo.setUpdateTime(sysdate);
		this.todoDao.insert(todo);
	}

	public void update(Todo todo) {
		Timestamp sysdate = new Timestamp(System.currentTimeMillis());
		todo.setUpdateUser(SessionUtils.getCurrentUserId());
		todo.setUpdateTime(sysdate);
		this.todoDao.updateByPrimaryKey(todo);
	}

	public void delete(String todoIds) {
		String[] todoIdStr = todoIds.split(",");
		for (int i = 0; i < todoIdStr.length; i++) {
			this.todoDao.deleteByPrimaryKey(Integer.parseInt(todoIdStr[i]));
		}
	}

	private Todo getConditions(Todo paramTodo) {
		paramTodo.setCreateUser(SessionUtils.getCurrentUserId());
		return paramTodo;
	}

	public String queryCountAndCategoryByCriteria(Todo paramTodo) {
		List<Todo> list = new ArrayList<Todo>();
		List<Todo> result = new ArrayList<Todo>();
		list = this.todoDao.queryCountAndCategoryByCriteria(paramTodo);
		if(list.size() == 2){
			for(Todo todo : list){
				if(todo.getDelflag().equals("1")){
					todo.setDelflag("待办");
				}
				if(todo.getDelflag().equals("2")){
					todo.setDelflag("已办");
				}
				result.add(todo);
			}
		}
		if(list.size() == 1){
			Todo todo = list.get(0);
			if(todo.getDelflag().equals("1")){
				todo.setDelflag("待办");
				result.add(todo);
				todo = new Todo();
				todo.setDelflag("已办");
				todo.setCount(0);
				result.add(todo);
			}
			if(todo.getDelflag().equals("2")){
				todo.setDelflag("已办");
				result.add(todo);
				todo = new Todo();
				todo.setDelflag("待办");
				todo.setCount(0);
				result.add(todo);
			}
			
			
		}
		if(list.size() == 0){
			Todo todo = new Todo();
			todo.setDelflag("待办");
			todo.setCount(0);
			result.add(todo);
			todo = new Todo();
			todo.setDelflag("已办");
			todo.setCount(0);
			result.add(todo);
		}
		Random r = new Random();
		StringBuffer temp = new StringBuffer();
		temp.append("[");
		for(Todo todo : result){
			temp.append("{name: '"+todo.getDelflag()+"', y: "+todo.getCount()+",color:'"+color[r.nextInt(9)]+"'},");
		}
		String tempStr = temp.toString();
		tempStr = tempStr.substring(0, tempStr.length()-1);
		StringBuffer data = new StringBuffer();
		data.append(tempStr);
		data.append("]");
		return data.toString();
	}

	public String queryCountAndYearByCriteria(Todo paramTodo) {
		Random r = new Random();
		List<Todo> list = new ArrayList<Todo>();
		list = this.todoDao.queryCountAndYearByCriteria(paramTodo);
		List<Todo> result = new ArrayList<Todo>();
		for(Todo todo : list){
			initTodo(todo);
			result.add(todo);
		}
		StringBuffer temp = new StringBuffer();
		temp.append("[");
		for(Todo todo : result){
			temp.append("{name: '"+todo.getMonth()+"', y: "+todo.getCount()+",color:'"+color[r.nextInt(9)]+"'},");
		}
		String tempStr = temp.toString();
		tempStr = tempStr.substring(0, tempStr.length()-1);
		StringBuffer data = new StringBuffer();
		data.append(tempStr);
		data.append("]");
		return data.toString();
	}

	private void initTodo(Todo todo) {
		if(todo.getMonth().equals("1")){
			todo.setMonth("1月");
			return;
		}
		if(todo.getMonth().equals("2")){
			todo.setMonth("2月");
			return;
		}
		if(todo.getMonth().equals("3")){
			todo.setMonth("3月");
			return;
		}
		if(todo.getMonth().equals("4")){
			todo.setMonth("4月");
			return;
		}
		if(todo.getMonth().equals("5")){
			todo.setMonth("5月");
			return;
		}
		if(todo.getMonth().equals("6")){
			todo.setMonth("6月");
			return;
		}
		if(todo.getMonth().equals("7")){
			todo.setMonth("7月");
			return;
		}
		if(todo.getMonth().equals("8")){
			todo.setMonth("8月");
			return;
		}
		if(todo.getMonth().equals("9")){
			todo.setMonth("9月");
			return;
		}
		if(todo.getMonth().equals("10")){
			todo.setMonth("10月");
			return;
		}
		if(todo.getMonth().equals("11")){
			todo.setMonth("11月");
			return;
		}
		if(todo.getMonth().equals("12")){
			todo.setMonth("12月");
			return;
		}
	}

	public void saveBatch(List<Todo> list) {
		this.todoDao.saveBatch(list);
	}

	public boolean checkTomorrowTodo(String formatDate2Str) {
		Todo todo = new Todo();
		todo.setTodoContent(formatDate2Str);
		todo.setCreateUser(SessionUtils.getCurrentUserId());
		List<Todo> todoList = this.todoDao.selectByCriteria(todo);
		if(todoList.size() > 0){
			return false;
		}
		return true;
	}

	public int querytodayTodoCountByCriteria(Todo paramTodo) {
		paramTodo = getConditions(paramTodo);
		int count = this.todoDao.querytodayTodoCountByCriteria(paramTodo);
		return count;
	}
	
	
}
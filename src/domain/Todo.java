package domain;

import java.util.Date;

public class Todo extends PageDomain {
	private static final long serialVersionUID = -166077776292936417L;
	private Integer todoId;
	private Integer categoryId;
	private String categoryName;
	private String todoContent;
	private Date dealDate;
	private String dealDateStr;
	private String level;
	private String status;
	private String delflag;
	private String createUser;
	private Date createTime;
	private String updateUser;
	private Date updateTime;
	private String month;
	private Integer count;
	private String year;

	public Integer getTodoId() {
		return this.todoId;
	}

	public void setTodoId(Integer todoId) {
		this.todoId = todoId;
	}

	public Integer getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getTodoContent() {
		return this.todoContent;
	}

	public void setTodoContent(String todoContent) {
		this.todoContent = todoContent;
	}

	public Date getDealDate() {
		return this.dealDate;
	}

	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}

	public String getDealDateStr() {
		return this.dealDateStr;
	}

	public void setDealDateStr(String dealDateStr) {
		this.dealDateStr = dealDateStr;
	}

	public String getLevel() {
		return this.level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDelflag() {
		return this.delflag;
	}

	public void setDelflag(String delflag) {
		this.delflag = delflag;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	
}
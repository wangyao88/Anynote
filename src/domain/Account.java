package domain;

import java.util.Date;

public class Account extends PageDomain {
	private static final long serialVersionUID = 2898295926961950185L;
	private Integer accountId;
	private Integer accountBookId;
	private String accountBookName;
	private Integer categoryId;
	private String categoryName;
	private Date accountDate;
	private String accountDateStr;
	private String accountType;
	private Double money;
	private String remark;
	private String status;
	private String delflag;
	private String createUser;
	private Date createTime;
	private String updateUser;
	private Date updateTime;
	private String createTimeStr;
	private String year;
	private String month;

	public Integer getAccountId() {
		return this.accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public Integer getAccountBookId() {
		return this.accountBookId;
	}

	public void setAccountBookId(Integer accountBookId) {
		this.accountBookId = accountBookId;
	}

	public Integer getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getAccountBookName() {
		return this.accountBookName;
	}

	public void setAccountBookName(String accountBookName) {
		this.accountBookName = accountBookName;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Date getAccountDate() {
		return this.accountDate;
	}

	public void setAccountDate(Date accountDate) {
		this.accountDate = accountDate;
	}

	public String getAccountDateStr() {
		return this.accountDateStr;
	}

	public void setAccountDateStr(String accountDateStr) {
		this.accountDateStr = accountDateStr;
	}

	public String getAccountType() {
		return this.accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Double getMoney() {
		return this.money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}
}
package domain;

import java.util.Date;

public class Note extends PageDomain {
	private static final long serialVersionUID = 6207891073710210684L;
	private Integer noteId;
	private Integer categoryId;
	private String categoryName;
	private String title;
	private String status;
	private String delflag;
	private String createUser;
	private Date createTime;
	private String updateUser;
	private Date updateTime;
	private String content;
	private Integer count;

	public Integer getNoteId() {
		return this.noteId;
	}

	public void setNoteId(Integer noteId) {
		this.noteId = noteId;
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

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
}
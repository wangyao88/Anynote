package com.sxkl.sign.in.po;

import java.util.Date;

import domain.PageDomain;

public class SignIn extends PageDomain {

	private static final long serialVersionUID = 3633789441275271440L;
	
	private String id;
	private String signUserId;
	private String signUserName;
	private Date date;
	private String dateStr;
	private String address;
	private String ip;
	private String remark;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSignUserId() {
		return signUserId;
	}
	public void setSignUserId(String signUserId) {
		this.signUserId = signUserId;
	}
	public String getSignUserName() {
		return signUserName;
	}
	public void setSignUserName(String signUserName) {
		this.signUserName = signUserName;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
}

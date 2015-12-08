package com.sxkl.loginFunction.po;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * 登陆记录
 * @author wangyao
 */
public class LoginHistory {
	
	//登陆记录ID
	public String id;
	//登陆人姓名
	public String loginUserName;
	//登陆人ID
	public String loginUserId;
	//登陆时间
	public Date loginTime;
	//退出时间
	public Date outTime;
	//本次登陆累计时间
	public long loginTimeSum;
	//登陆IP
	public String loginIP;

	public Set<OperationHistory> operationHistory;

	public Set<OperationHistory> getOperationHistory() {
		if (operationHistory == null)
			operationHistory = new HashSet<OperationHistory>();
		return operationHistory;
	}

	/** @pdGenerated default iterator getter */
	public Iterator<OperationHistory> getIteratorOperationHistory() {
		if (operationHistory == null)
			operationHistory = new HashSet<OperationHistory>();
		return operationHistory.iterator();
	}

	/**
	 * @pdGenerated default setter
	 * @param newOperationHistory
	 */
	public void setOperationHistory(Set<OperationHistory> newOperationHistory) {
		removeAllOperationHistory();
		for (Iterator<OperationHistory> iter = newOperationHistory.iterator(); iter
				.hasNext();)
			addOperationHistory((OperationHistory) iter.next());
	}

	/**
	 * @pdGenerated default add
	 * @param newOperationHistory
	 */
	public void addOperationHistory(OperationHistory newOperationHistory) {
		if (newOperationHistory == null)
			return;
		if (this.operationHistory == null)
			this.operationHistory = new HashSet<OperationHistory>();
		if (!this.operationHistory.contains(newOperationHistory)) {
			this.operationHistory.add(newOperationHistory);
			newOperationHistory.setLoginHistory(this);
		}
	}

	/**
	 * @pdGenerated default remove
	 * @param oldOperationHistory
	 */
	public void removeOperationHistory(OperationHistory oldOperationHistory) {
		if (oldOperationHistory == null)
			return;
		if (this.operationHistory != null)
			if (this.operationHistory.contains(oldOperationHistory)) {
				this.operationHistory.remove(oldOperationHistory);
				oldOperationHistory.setLoginHistory((LoginHistory) null);
			}
	}

	/** @pdGenerated default removeAll */
	public void removeAllOperationHistory() {
		if (operationHistory != null) {
			OperationHistory oldOperationHistory;
			for (Iterator<OperationHistory> iter = getIteratorOperationHistory(); iter
					.hasNext();) {
				oldOperationHistory = (OperationHistory) iter.next();
				iter.remove();
				oldOperationHistory.setLoginHistory((LoginHistory) null);
			}
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginUserName() {
		return loginUserName;
	}

	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}

	public String getLoginUserId() {
		return loginUserId;
	}

	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getOutTime() {
		return outTime;
	}

	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}

	public long getLoginTimeSum() {
		return loginTimeSum;
	}

	public void setLoginTimeSum(long loginTimeSum) {
		this.loginTimeSum = loginTimeSum;
	}

	public String getLoginIP() {
		return loginIP;
	}

	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}
	
	

}
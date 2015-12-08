package com.sxkl.loginFunction.po;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * ��½��¼
 * @author wangyao
 */
public class LoginHistory {
	
	//��½��¼ID
	public String id;
	//��½������
	public String loginUserName;
	//��½��ID
	public String loginUserId;
	//��½ʱ��
	public Date loginTime;
	//�˳�ʱ��
	public Date outTime;
	//���ε�½�ۼ�ʱ��
	public long loginTimeSum;
	//��½IP
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
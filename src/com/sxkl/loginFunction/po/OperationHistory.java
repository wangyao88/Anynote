package com.sxkl.loginFunction.po;

import java.util.Date;

/**
 * ������¼
 * @author wangyao
 */
public class OperationHistory {
	
	//������¼ID
	public String id;
	//������¼������
	public String operateUserName;
	//������¼��ID
	public String operateUserId;
	//������¼ʱ��
	public Date operateTime;
	//������¼����
	public String operateClassName;
	//������¼������
	public String operateFunctionName;

	public LoginHistory loginHistory;

	public LoginHistory getLoginHistory() {
		return loginHistory;
	}

	/**
	 * @pdGenerated default parent setter
	 * @param newLoginHistory
	 */
	public void setLoginHistory(LoginHistory newLoginHistory) {
		if (this.loginHistory == null
				|| !this.loginHistory.equals(newLoginHistory)) {
			if (this.loginHistory != null) {
				LoginHistory oldLoginHistory = this.loginHistory;
				this.loginHistory = null;
				oldLoginHistory.removeOperationHistory(this);
			}
			if (newLoginHistory != null) {
				this.loginHistory = newLoginHistory;
				this.loginHistory.addOperationHistory(this);
			}
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOperateUserName() {
		return operateUserName;
	}

	public void setOperateUserName(String operateUserName) {
		this.operateUserName = operateUserName;
	}

	public String getOperateUserId() {
		return operateUserId;
	}

	public void setOperateUserId(String operateUserId) {
		this.operateUserId = operateUserId;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getOperateClassName() {
		return operateClassName;
	}

	public void setOperateClassName(String operateClassName) {
		this.operateClassName = operateClassName;
	}

	public String getOperateFunctionName() {
		return operateFunctionName;
	}

	public void setOperateFunctionName(String operateFunctionName) {
		this.operateFunctionName = operateFunctionName;
	}
	
	

}
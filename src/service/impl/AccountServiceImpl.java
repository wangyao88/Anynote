package service.impl;

import global.Constants;
import global.security.SessionUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import service.AccountService;
import util.DateUtils;
import dao.AccountDAO;
import domain.Account;

public class AccountServiceImpl implements AccountService {
	private AccountDAO accountDao = null;
	
	private static final String[] color = new String[]{"#F90421","#F904CD","#7704F9","#19F904","#EDF904","#3E086D","#F257ED","#2D032C","#BF2891"};

	public void setAccountDao(AccountDAO accountDao) {
		this.accountDao = accountDao;
	}

	public Account selectByPrimaryKey(int accountId) {
		Account account = this.accountDao.selectByPrimaryKey(accountId);
		if (account != null) {
			account.setAccountDateStr(DateUtils.formatDate2Str(
					account.getAccountDate(), "yyyy-MM-dd"));
		}
		return account;
	}

	public int countByCriteria(Account paramAccount) {
		paramAccount = getConditions(paramAccount);
		int count = this.accountDao.countByCriteria(paramAccount);
		return count;
	}

	public List<Account> selectByCriteria(Account paramAccount) {
		paramAccount = getConditions(paramAccount);
		List accountList = this.accountDao.selectByCriteria(paramAccount);
		List results = new ArrayList();
		if (accountList != null) {
			for (int i = 0; i < accountList.size(); i++) {
				Account account = (Account) accountList.get(i);
				account.setAccountDateStr(DateUtils.formatDate2Str(
						account.getAccountDate(), "yyyy-MM-dd"));
				account.setAccountType((String) Constants.ACCOUNT_TYPE_MAP
						.get(account.getAccountType()));
				account.setUpdateDateStr(DateUtils.formatDate2Str(
						account.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"));
				results.add(account);
			}
		}
		return results;
	}

	public List<Account> selectByCriteriaForPaging(Account paramAccount) {
		paramAccount = getConditions(paramAccount);
		List accountList = this.accountDao.selectByCriteriaForPaging(
				paramAccount, paramAccount.getStart(), paramAccount.getLimit());
		List results = new ArrayList();
		if (accountList != null) {
			for (int i = 0; i < accountList.size(); i++) {
				Account account = (Account) accountList.get(i);
				account.setAccountDateStr(DateUtils.formatDate2Str(
						account.getAccountDate(), "yyyy-MM-dd"));
				account.setAccountType((String) Constants.ACCOUNT_TYPE_MAP
						.get(account.getAccountType()));
				account.setUpdateDateStr(DateUtils.formatDate2Str(
						account.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"));
				results.add(account);
			}
		}
		return results;
	}

	public void insert(Account account) {
		account.setDelflag("1");

		Timestamp sysdate = new Timestamp(System.currentTimeMillis());
		account.setCreateUser(SessionUtils.getCurrentUserId());
		account.setCreateTime(sysdate);
		account.setUpdateUser(SessionUtils.getCurrentUserId());
		account.setUpdateTime(sysdate);
		this.accountDao.insert(account);
	}

	public void update(Account account) {
		Timestamp sysdate = new Timestamp(System.currentTimeMillis());
		account.setUpdateUser(SessionUtils.getCurrentUserId());
		account.setUpdateTime(sysdate);
		this.accountDao.updateByPrimaryKey(account);
	}

	public void delete(String accountIds) {
		String[] accountIdArr = accountIds.split(",");
		for (int i = 0; i < accountIdArr.length; i++) {
			this.accountDao.deleteByPrimaryKey(Integer
					.parseInt(accountIdArr[i]));
		}
	}

	public double getTotalMoney(Account paramAccount) {
		double result = 0.0D;

		paramAccount = getConditions(paramAccount);
		Double money = Double.valueOf(this.accountDao
				.selectSumByCriteria(paramAccount));
		if (money != null) {
			result = money.doubleValue();
		}

		return result;
	}

	public double getCurrentMoney(Account paramAccount) {
		double result = 0.0D;

		paramAccount = getConditions(paramAccount);
		Double money = Double.valueOf(this.accountDao
				.selectSumByCriteria(paramAccount));
		if (money != null) {
			result = money.doubleValue();
		}

		return result;
	}

	public List<Account> getGroupSumMoney(Account paramAccount) {
		List accountList = this.accountDao
				.selectGroupSumByCriteria(paramAccount);
		List results = new ArrayList();
		if (accountList != null) {
			return accountList;
		}
		return results;
	}

	private Account getConditions(Account paramAccount) {
		paramAccount.setCreateUser(SessionUtils.getCurrentUserId());
		return paramAccount;
	}

	public List<Account> getRunningDayReportSumMoney(Account paramAccount) {
		List<Account> accountList = this.accountDao.getRunningDayReportSumMoney(paramAccount);
		List<Account> results = new ArrayList<Account>();
		if (accountList != null) {
			return accountList;
		}
		return results;
	}

	public String selectDayReportCastSumByCriteria(Account paramAccount) {
		List<Account> accountList = this.accountDao.selectDayReportCastSumByCriteria(paramAccount);
		StringBuffer temp = new StringBuffer();
		Random r = new Random();
		if (accountList != null) {
			temp.append("[");
			for(Account a : accountList){
				temp.append("{name: '"+a.getCategoryName()+"', y: "+a.getMoney()+",color:'"+color[r.nextInt(9)]+"'},");
			}
			String tempStr = temp.toString();
			tempStr = tempStr.substring(0, tempStr.length()-1);
			StringBuffer data = new StringBuffer(tempStr);
			data.append("]");
			return data.toString();
		}else{
			return "[]";
		}
	}

	public String selectMonthReportSumByCriteria(Account paramAccount) {
		List<Account> accountList = this.accountDao.selectMonthReportSumByCriteria(paramAccount);
		StringBuffer temp = new StringBuffer();
		Random r = new Random();
		if (accountList != null) {
			temp.append("{");
			temp.append("	  series: [");
			temp.append("							  {");
			temp.append("								    name: '日支出',");
			temp.append("								    point: [");
			for(Account a : accountList){
				temp.append("{name: '"+DateUtils.formatDate2Str(a.getAccountDate(), "yyyy-MM-dd")+"', y: "+a.getMoney()+",color:'"+color[r.nextInt(9)]+"'},");
			}
			String tempStr = temp.toString();
			tempStr = tempStr.substring(0, tempStr.length()-1);
			StringBuffer data = new StringBuffer(tempStr);
			data.append("								     ]");
			data.append("							  }");
			data.append("		]");
			data.append("}");
			return data.toString();
		}else{
			return "{}";
		}
	}

	@SuppressWarnings("unchecked")
	public List<Account> queryMonthCastReportDataForPaging(Account paramAccount) {
		paramAccount = getConditions(paramAccount);
		List<Account> accountList = this.accountDao.queryMonthCastReportDataForPaging(
				paramAccount, paramAccount.getStart(), paramAccount.getLimit());
		List<Account> results = new ArrayList<Account>();
		if (accountList != null) {
			for (int i = 0; i < accountList.size(); i++) {
				Account account = (Account) accountList.get(i);
				account.setAccountDateStr(DateUtils.formatDate2Str(
						account.getAccountDate(), "yyyy-MM-dd"));
				account.setAccountType((String) Constants.ACCOUNT_TYPE_MAP
						.get(account.getAccountType()));
				account.setUpdateDateStr(DateUtils.formatDate2Str(
						account.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"));
				results.add(account);
			}
		}
		return results;
	}

	public int queryMonthCastReportCountByCriteria(Account paramAccount) {
		paramAccount = getConditions(paramAccount);
		int count = this.accountDao.queryMonthCastReportCountByCriteria(paramAccount);
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<Account> queryMonthImportReportDataForPaging(Account paramAccount) {
		paramAccount = getConditions(paramAccount);
		List<Account> accountList = this.accountDao.queryMonthImportReportDataForPaging(
				paramAccount, paramAccount.getStart(), paramAccount.getLimit());
		List<Account> results = new ArrayList<Account>();
		if (accountList != null) {
			for (int i = 0; i < accountList.size(); i++) {
				Account account = (Account) accountList.get(i);
				account.setAccountDateStr(DateUtils.formatDate2Str(
						account.getAccountDate(), "yyyy-MM-dd"));
				account.setAccountType((String) Constants.ACCOUNT_TYPE_MAP
						.get(account.getAccountType()));
				account.setUpdateDateStr(DateUtils.formatDate2Str(
						account.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"));
				results.add(account);
			}
		}
		return results;
	}
	
	public int queryMonthImportReportCountByCriteria(Account paramAccount) {
		paramAccount = getConditions(paramAccount);
		int count = this.accountDao.queryMonthImportReportCountByCriteria(paramAccount);
		return count;
	}

	public String selectYearReportSumByCriteria(Account paramAccount) {
		List<Account> castAccountList = this.accountDao.selectYearReportCastSumByCriteria(paramAccount);
		List<Account> importAccountList = this.accountDao.selectYearReportImportSumByCriteria(paramAccount);
		StringBuffer temp1 = new StringBuffer();
		Random r = new Random();
		if (castAccountList != null) {
			temp1.append("{");
			temp1.append("	  series: [");
			temp1.append("							  {");
			temp1.append("								    name: '支出',");
			temp1.append("								    point: [");
			for(Account a : castAccountList){
				temp1.append("{name: '"+a.getMonth()+"月', y: "+a.getMoney()+",color:'"+color[r.nextInt(9)]+"'},");
			}
			String tempStr1 = temp1.toString();
			tempStr1 = tempStr1.substring(0, tempStr1.length()-1);
			StringBuffer temp2 = new StringBuffer(tempStr1);
			temp2.append("								     ]");
			temp2.append("							  },");
			temp2.append("							  {");
			temp2.append("								    name: '收入',");
			temp2.append("								    point: [");
			for(Account a : importAccountList){
				temp2.append("{name: '"+a.getMonth()+"月', y: "+a.getMoney()+",color:'"+color[r.nextInt(9)]+"'},");
			}
			String tempStr2 = temp2.toString();
			tempStr2 = tempStr2.substring(0, tempStr2.length()-1);
			StringBuffer data = new StringBuffer(tempStr2);
			data.append("								     ]");
			data.append("							  }");
			data.append("		]");
			data.append("}");
			return data.toString();
		}else{
			return "{}";
		}
	}

	public double getMonthRate(Account paramAccount) {
		paramAccount.setCreateUser(SessionUtils.getCurrentUserId());
		double rate = 1.00;
		List<Account> list = this.accountDao.getMonthRate(paramAccount);
		if(list != null && list.get(0).getMoney() != null){
			rate = (list.get(0).getMoney())/Constants.MONTH_PRE_CAST;
		}
		return rate;
	}

	public double getYearRate(Account paramAccount) {
		paramAccount.setCreateUser(SessionUtils.getCurrentUserId());
		double rate = 1.00;
		double importMoney = 1.00;
		double exportMoney = 1.00;
		List<Account> list = this.accountDao.getYearRate(paramAccount);
		if(list != null && list.get(0).getMoney() != null){
			importMoney = (list.get(0).getMoney());
			exportMoney = (list.get(1).getMoney());
			rate = exportMoney/importMoney;
		}
		return rate;
	}
	
}
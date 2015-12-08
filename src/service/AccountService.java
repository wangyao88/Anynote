package service;

import dao.AccountDAO;
import domain.Account;
import java.util.List;

public abstract interface AccountService {
	public abstract void setAccountDao(AccountDAO paramAccountDAO);

	public abstract Account selectByPrimaryKey(int paramInt);

	public abstract int countByCriteria(Account paramAccount);

	public abstract List<Account> selectByCriteria(Account paramAccount);

	public abstract List<Account> selectByCriteriaForPaging(Account paramAccount);

	public abstract void insert(Account paramAccount);

	public abstract void update(Account paramAccount);

	public abstract void delete(String paramString);

	public abstract double getTotalMoney(Account paramAccount);

	public abstract double getCurrentMoney(Account paramAccount);

	public abstract List<Account> getGroupSumMoney(Account paramAccount);

	public abstract List<Account> getRunningDayReportSumMoney(
			Account paramAccount);
	
	public abstract String selectDayReportCastSumByCriteria(
			Account paramAccount);

	public abstract String selectMonthReportSumByCriteria(Account paramAccount);

	public abstract List<Account> queryMonthCastReportDataForPaging(Account paramAccount);

	public abstract int queryMonthCastReportCountByCriteria(Account paramAccount);

	public abstract List<Account> queryMonthImportReportDataForPaging(Account paramAccount);

	public abstract int queryMonthImportReportCountByCriteria(Account paramAccount);

	public abstract String selectYearReportSumByCriteria(Account paramAccount);

	public abstract double getMonthRate(Account paramAccount);

	public abstract double getYearRate(Account paramAccount);
}
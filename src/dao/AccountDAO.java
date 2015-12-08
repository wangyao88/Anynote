package dao;

import domain.Account;
import java.util.List;

public abstract interface AccountDAO {
	public abstract Account selectByPrimaryKey(int paramInt);

	public abstract List<Account> selectByCriteria(Account paramAccount);

	public abstract List<Account> selectByCriteriaForPaging(
			Account paramAccount, int paramInt1, int paramInt2);

	public abstract int countByCriteria(Account paramAccount);

	public abstract void insert(Account paramAccount);

	public abstract int updateByPrimaryKey(Account paramAccount);

	public abstract int deleteByPrimaryKey(int paramInt);

	public abstract double selectSumByCriteria(Account paramAccount);

	public abstract List<Account> selectGroupSumByCriteria(Account paramAccount);

	public abstract List<Account> getRunningDayReportSumMoney(
			Account paramAccount);
	
	public abstract List<Account> selectDayReportCastSumByCriteria(
			Account paramAccount);

	public abstract List<Account> selectMonthReportSumByCriteria(Account paramAccount);

	public abstract List queryMonthCastReportDataForPaging(Account paramAccount, int start, int limit);

	public abstract int queryMonthCastReportCountByCriteria(Account paramAccount);

	public abstract List<Account> queryMonthImportReportDataForPaging(Account paramAccount, int start, int limit);

	public abstract int queryMonthImportReportCountByCriteria(Account paramAccount);

	public abstract List<Account> selectYearReportCastSumByCriteria(Account paramAccount);

	public abstract List<Account> selectYearReportImportSumByCriteria(Account paramAccount);

	public abstract List<Account> getMonthRate(Account paramAccount);

	public abstract List<Account> getYearRate(Account paramAccount);
	
	
}
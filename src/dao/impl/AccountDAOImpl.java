package dao.impl;

import dao.AccountDAO;
import domain.Account;
import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class AccountDAOImpl extends SqlMapClientDaoSupport implements
		AccountDAO {
	public Account selectByPrimaryKey(int id) {
		Account record = (Account) getSqlMapClientTemplate().queryForObject(
				"an_account.selectByPrimaryKey", Integer.valueOf(id));
		return record;
	}

	public List<Account> selectByCriteria(Account account) {
		List list = getSqlMapClientTemplate().queryForList(
				"an_account.selectByCriteria", account);
		return list;
	}

	public List<Account> selectByCriteriaForPaging(Account account, int start,
			int limit) {
		List list = getSqlMapClientTemplate().queryForList(
				"an_account.selectByCriteria", account, start, limit);
		return list;
	}

	public int countByCriteria(Account account) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				"an_account.countByCriteria", account);
		return count.intValue();
	}

	public void insert(Account account) {
		getSqlMapClientTemplate().insert("an_account.insert", account);
	}

	public int updateByPrimaryKey(Account account) {
		int rows = getSqlMapClientTemplate().update(
				"an_account.updateByPrimaryKey", account);
		return rows;
	}

	public int deleteByPrimaryKey(int id) {
		int rows = getSqlMapClientTemplate().delete(
				"an_account.deleteByPrimaryKey", Integer.valueOf(id));
		return rows;
	}

	public double selectSumByCriteria(Account account) {
		Double sum = (Double) getSqlMapClientTemplate().queryForObject(
				"an_account.selectSumByCriteria", account);
		if (sum != null) {
			return sum.doubleValue();
		}
		return 0.0D;
	}

	public List<Account> selectGroupSumByCriteria(Account account) {
		List list = getSqlMapClientTemplate().queryForList(
				"an_account.selectByCriteria", account);
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Account> getRunningDayReportSumMoney(Account paramAccount) {
		List<Account> list = getSqlMapClientTemplate().queryForList(
				"an_account.selectRunningDayReportSumByCriteria", paramAccount);
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Account> selectDayReportCastSumByCriteria(Account paramAccount) {
		List<Account> list = getSqlMapClientTemplate().queryForList(
				"an_account.selectDayReportCastSumByCriteria", paramAccount);
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Account> selectMonthReportSumByCriteria(Account paramAccount) {
		List<Account> list = getSqlMapClientTemplate().queryForList(
				"an_account.selectMonthReportSumByCriteria", paramAccount);
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Account> queryMonthCastReportDataForPaging(Account paramAccount,int start, int limit) {
		List<Account> list = getSqlMapClientTemplate().queryForList(
				"an_account.queryMonthCastReportDataForPaging", paramAccount, start, limit);
		return list;
	}

	public int queryMonthCastReportCountByCriteria(Account paramAccount) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				"an_account.queryMonthCastReportCountByCriteria", paramAccount);
		return count.intValue();
	}

	@SuppressWarnings("unchecked")
	public List<Account> queryMonthImportReportDataForPaging(Account paramAccount, int start, int limit) {
		List<Account> list = getSqlMapClientTemplate().queryForList(
				"an_account.queryMonthImportReportDataForPaging", paramAccount, start, limit);
		return list;
	}

	public int queryMonthImportReportCountByCriteria(Account paramAccount) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				"an_account.queryMonthImportReportCountByCriteria", paramAccount);
		return count.intValue();
	}

	@SuppressWarnings("unchecked")
	public List<Account> selectYearReportCastSumByCriteria(Account paramAccount) {
		List<Account> list = getSqlMapClientTemplate().queryForList(
				"an_account.selectYearReportCastSumByCriteria", paramAccount);
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Account> selectYearReportImportSumByCriteria(Account paramAccount) {
		List<Account> list = getSqlMapClientTemplate().queryForList(
				"an_account.selectYearReportImportSumByCriteria", paramAccount);
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Account> getMonthRate(Account paramAccount) {
		List<Account> list = getSqlMapClientTemplate().queryForList(
				"an_account.getMonthRate", paramAccount);
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Account> getYearRate(Account paramAccount) {
		List<Account> list = getSqlMapClientTemplate().queryForList(
				"an_account.selectYearRateByCriteria", paramAccount);
		return list;
	}
}
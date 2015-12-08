package service;

import dao.AccountCategoryDAO;
import domain.AccountCategory;
import java.util.List;

public abstract interface AccountCategoryService {
	public abstract void setAccountCategoryDao(
			AccountCategoryDAO paramAccountCategoryDAO);

	public abstract int countByCriteria(AccountCategory paramAccountCategory);

	public abstract List<AccountCategory> selectByCriteria(
			AccountCategory paramAccountCategory);

	public abstract List<AccountCategory> selectByCriteriaForPaging(
			AccountCategory paramAccountCategory);

	public abstract void insert(AccountCategory paramAccountCategory);

	public abstract void update(AccountCategory paramAccountCategory);

	public abstract void delete(String paramString);
}
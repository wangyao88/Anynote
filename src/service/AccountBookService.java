package service;

import dao.AccountBookDAO;
import domain.AccountBook;
import java.util.List;

public abstract interface AccountBookService {
	public abstract void setAccountBookDao(AccountBookDAO paramAccountBookDAO);

	public abstract List<AccountBook> selectByCriteria(
			AccountBook paramAccountBook);

	public abstract void insert(AccountBook paramAccountBook);

	public abstract void update(AccountBook paramAccountBook);

	public abstract void delete(Integer paramInteger);
}
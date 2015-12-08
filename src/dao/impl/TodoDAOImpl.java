package dao.impl;

import dao.TodoDAO;
import domain.Todo;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapExecutor;

public class TodoDAOImpl extends SqlMapClientDaoSupport implements TodoDAO {
	public Todo selectByPrimaryKey(int id) {
		Todo record = (Todo) getSqlMapClientTemplate().queryForObject(
				"an_todo.selectByPrimaryKey", Integer.valueOf(id));
		return record;
	}

	public List<Todo> selectByCriteria(Todo todo) {
		List list = getSqlMapClientTemplate().queryForList(
				"an_todo.selectByCriteria", todo);
		return list;
	}

	public List<Todo> selectByCriteriaForPaging(Todo todo, int start, int limit) {
		List list = getSqlMapClientTemplate().queryForList(
				"an_todo.selectByCriteria", todo, start, limit);
		return list;
	}

	public int countByCriteria(Todo todo) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				"an_todo.countByCriteria", todo);
		return count.intValue();
	}

	public void insert(Todo todo) {
		getSqlMapClientTemplate().insert("an_todo.insert", todo);
	}

	public int updateByPrimaryKey(Todo todo) {
		int rows = getSqlMapClientTemplate().update(
				"an_todo.updateByPrimaryKey", todo);
		return rows;
	}

	public int deleteByPrimaryKey(int id) {
		int rows = getSqlMapClientTemplate().delete(
				"an_todo.deleteByPrimaryKey", Integer.valueOf(id));
		return rows;
	}

	@SuppressWarnings("unchecked")
	public List<Todo> queryCountAndCategoryByCriteria(Todo paramTodo) {
		List<Todo> list = getSqlMapClientTemplate().queryForList(
				"an_todo.queryCountAndCategoryByCriteria", paramTodo);
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Todo> queryCountAndYearByCriteria(Todo paramTodo) {
		List<Todo> list = getSqlMapClientTemplate().queryForList(
				"an_todo.queryCountAndYearByCriteria", paramTodo);
		return list;
	}

	public void saveBatch(final List<Todo> list) {
//		this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
//			public Object doInSqlMapClient(SqlMapExecutor executor)
//					throws SQLException {
//				executor.startBatch();
//				// 每次提交最大条数
//				final int batchSize = 200;
//				int count = 0;
//				for (Todo todo : list) {
//					executor.insert("an_todo.insert", todo);
//					// 每200条数据提交一次
//					if (++count % batchSize == 0) {
//						executor.executeBatch();
//					}
//				}
//				// 提交剩余的数据
//				executor.executeBatch();
//				return null;
//			}
//		});
		for(Todo todo : list){
			this.insert(todo);
		}
	}

	public int querytodayTodoCountByCriteria(Todo paramTodo) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				"an_todo.countByCriteria", paramTodo);
		return count.intValue();
	}
	
	
}
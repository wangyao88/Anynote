package dao.impl;

import dao.NoteDAO;
import domain.Note;
import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class NoteDAOImpl extends SqlMapClientDaoSupport implements NoteDAO {
	public Note selectByPrimaryKey(int id) {
		Note record = (Note) getSqlMapClientTemplate().queryForObject(
				"an_note.selectByPrimaryKey", Integer.valueOf(id));
		return record;
	}

	public List<Note> selectByCriteria(Note note) {
		List list = getSqlMapClientTemplate().queryForList(
				"an_note.selectByCriteria", note);
		return list;
	}

	public List<Note> selectByCriteriaForPaging(Note note, int start, int limit) {
		List list = getSqlMapClientTemplate().queryForList(
				"an_note.selectByCriteria", note, start, limit);
		return list;
	}

	public int countByCriteria(Note note) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				"an_note.countByCriteria", note);
		return count.intValue();
	}

	public void insert(Note note) {
		getSqlMapClientTemplate().insert("an_note.insert", note);
	}

	public int updateByPrimaryKey(Note note) {
		int rows = getSqlMapClientTemplate().update(
				"an_note.updateByPrimaryKey", note);
		return rows;
	}

	public int deleteByPrimaryKey(int id) {
		int rows = getSqlMapClientTemplate().delete(
				"an_note.deleteByPrimaryKey", Integer.valueOf(id));
		return rows;
	}

	@SuppressWarnings("unchecked")
	public List<Note> queryNewNote(Integer count) {
		Note paramNote = new Note();
		paramNote.setCount(count);
		List<Note> list = getSqlMapClientTemplate().queryForList(
				"an_note.queryNewNote", paramNote);
		return list;
	}
}
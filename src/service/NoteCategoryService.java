package service;

import dao.NoteCategoryDAO;
import domain.NoteCategory;
import java.util.List;
import net.sf.json.JSONArray;

public abstract interface NoteCategoryService {
	public abstract void setNoteCategoryDao(NoteCategoryDAO paramNoteCategoryDAO);

	public abstract void setNoteService(NoteService paramNoteService);

	public abstract int countByCriteria(NoteCategory paramNoteCategory);

	public abstract List<NoteCategory> selectByCriteria(
			NoteCategory paramNoteCategory);

	public abstract JSONArray selectNoteCategoryForTree();

	public abstract List<NoteCategory> selectByCriteriaForPaging(
			NoteCategory paramNoteCategory);

	public abstract void insert(NoteCategory paramNoteCategory);

	public abstract void update(NoteCategory paramNoteCategory);

	public abstract void delete(int paramInt);
}
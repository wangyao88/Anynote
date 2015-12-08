package dao;

import domain.NoteCategory;
import java.util.List;

public abstract interface NoteCategoryDAO
{
  public abstract NoteCategory selectByPrimaryKey(int paramInt);

  public abstract List<NoteCategory> selectByCriteria(NoteCategory paramNoteCategory);

  public abstract List<NoteCategory> selectByCriteriaForPaging(NoteCategory paramNoteCategory, int paramInt1, int paramInt2);

  public abstract int countByCriteria(NoteCategory paramNoteCategory);

  public abstract void insert(NoteCategory paramNoteCategory);

  public abstract int updateByPrimaryKey(NoteCategory paramNoteCategory);

  public abstract int deleteByPrimaryKey(int paramInt);

  public abstract List<NoteCategory> selectCategoryForTree(NoteCategory paramNoteCategory);
}
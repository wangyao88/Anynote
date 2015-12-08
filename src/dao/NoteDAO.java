package dao;

import domain.Note;
import java.util.List;

public abstract interface NoteDAO
{
  public abstract Note selectByPrimaryKey(int paramInt);

  public abstract List<Note> selectByCriteria(Note paramNote);

  public abstract List<Note> selectByCriteriaForPaging(Note paramNote, int paramInt1, int paramInt2);

  public abstract int countByCriteria(Note paramNote);

  public abstract void insert(Note paramNote);

  public abstract int updateByPrimaryKey(Note paramNote);

  public abstract int deleteByPrimaryKey(int paramInt);

  public abstract List<Note> queryNewNote(Integer count);
}
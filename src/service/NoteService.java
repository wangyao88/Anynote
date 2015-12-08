package service;

import dao.NoteDAO;
import domain.Note;
import java.util.List;
import java.util.Map;

public abstract interface NoteService
{
  public abstract void setNoteDao(NoteDAO paramNoteDAO);

  public abstract Note selectByPrimaryKey(int paramInt);

  public abstract int countByCriteria(Note paramNote);

  public abstract List<Note> selectByCriteria(Note paramNote);

  public abstract List<Note> selectByCriteriaForPaging(Note paramNote);

  public abstract void save(Map paramMap)
    throws Exception;

  public abstract void insert(Note paramNote);

  public abstract void update(Note paramNote);

  public abstract void deleteNote(String paramString);

  public abstract void returnNote(String paramString);

  public abstract void moveNote(String paramString1, String paramString2);

  public abstract void deleteByNoteIds(String paramString);

  public abstract boolean sendEmail(String paramString1, String paramString2)
    throws Exception;

  public abstract String queryNewNote(Integer count);
}
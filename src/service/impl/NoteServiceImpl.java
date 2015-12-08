package service.impl;

import dao.NoteDAO;
import domain.Note;
import global.beanUtils.BeanUtils;
import global.security.SessionUtils;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import service.NoteService;
import util.DateUtils;
import util.FileUtils;
import util.ServletHelp;
import util.StringUtils;

public class NoteServiceImpl implements NoteService {
	private NoteDAO noteDao = null;

	public void setNoteDao(NoteDAO noteDao) {
		this.noteDao = noteDao;
	}

	public Note selectByPrimaryKey(int noteId) {
		Note note = this.noteDao.selectByPrimaryKey(noteId);
		return note;
	}

	public int countByCriteria(Note paramNote) {
		paramNote = getConditions(paramNote);
		int count = this.noteDao.countByCriteria(paramNote);
		return count;
	}

	public List<Note> selectByCriteria(Note paramNote) {
		paramNote = getConditions(paramNote);
		List noteList = this.noteDao.selectByCriteria(paramNote);
		return noteList;
	}

	public List<Note> selectByCriteriaForPaging(Note paramNote) {
		paramNote = getConditions(paramNote);
		List<Note> noteList = this.noteDao.selectByCriteriaForPaging(paramNote,
				paramNote.getStart(), paramNote.getLimit());
		if (noteList != null) {
			List<Note> resultList = new ArrayList<Note>();
			for (Note note : noteList) {
				note.setCreateDateStr(DateUtils.formatDate2Str(note
						.getCreateTime()));
				note.setUpdateDateStr(DateUtils.formatDate2Str(note
						.getUpdateTime()));
				resultList.add(note);
			}
			return resultList;
		}
		return noteList;
	}

	public void save(Map map) throws Exception {
		Note note = new Note();
		BeanUtils.populate(note, map);
		note.setDelflag("1");
		note.setStatus("1");

		if ("on".equals((String) map.get("saveRemoteImg"))) {
			String savePath = (String) map.get("savePath");
			String content = FileUtils.saveHtmlImgToLocal(
					(String) map.get("domain"), note.getContent(), savePath);
			note.setContent(content);
		}

		String type = (String) map.get("type");
		if (type.equals("CREATE")) {
			insert(note);
		} else if (type.equals("UPDATE")) {
			update(note);
		}
	}

	public void insert(Note note) {
		note.setDelflag("1");

		Timestamp sysdate = new Timestamp(System.currentTimeMillis());
		note.setCreateUser(SessionUtils.getCurrentUserId());
		note.setCreateTime(sysdate);
		note.setUpdateUser(SessionUtils.getCurrentUserId());
		note.setUpdateTime(sysdate);
		this.noteDao.insert(note);
	}

	public void update(Note note) {
		Timestamp sysdate = new Timestamp(System.currentTimeMillis());
		note.setUpdateUser(SessionUtils.getCurrentUserId());
		note.setUpdateTime(sysdate);
		this.noteDao.updateByPrimaryKey(note);
	}

	public void deleteNote(String noteIds) {
		List noteList = new ArrayList();
		String[] noteIdArr = noteIds.split(",");
		for (int i = 0; i < noteIdArr.length; i++) {
			Note note = new Note();
			note.setNoteId(Integer.valueOf(Integer.parseInt(noteIdArr[i])));
			note.setDelflag("2");
			noteList.add(note);
			update(note);
		}
	}

	public void returnNote(String noteIds) {
		List noteList = new ArrayList();
		String[] noteIdArr = noteIds.split(",");
		for (int i = 0; i < noteIdArr.length; i++) {
			Note note = new Note();
			note.setNoteId(Integer.valueOf(Integer.parseInt(noteIdArr[i])));
			note.setDelflag("1");
			noteList.add(note);
			update(note);
		}
	}

	public void moveNote(String noteIds, String categoryId) {
		List noteList = new ArrayList();
		String[] noteIdArr = noteIds.split(",");
		for (int i = 0; i < noteIdArr.length; i++) {
			Note note = new Note();
			note.setNoteId(Integer.valueOf(Integer.parseInt(noteIdArr[i])));
			note.setCategoryId(Integer.valueOf(Integer.parseInt(categoryId)));
			note.setDelflag("1");
			noteList.add(note);
			update(note);
		}
	}

	public void deleteByNoteIds(String noteIds) {
		String[] noteIdArr = noteIds.split(",");
		for (int i = 0; i < noteIdArr.length; i++)
			this.noteDao.deleteByPrimaryKey(Integer.parseInt(noteIdArr[i]));
	}

	public boolean sendEmail(String noteId, String emails) throws Exception {
		boolean result = false;
		if ((StringUtils.isNotEmpty(noteId))
				&& (StringUtils.isNotEmpty(emails))) {
			Note note = selectByPrimaryKey(Integer.parseInt(noteId));
			result = ServletHelp.sendEmail(emails, note.getTitle(),
					note.getContent());
		}

		return result;
	}

	private Note getConditions(Note paramNote) {
		paramNote.setCreateUser(SessionUtils.getCurrentUserId());
		return paramNote;
	}

	public String queryNewNote(Integer count) {
		List<Note> list = this.noteDao.queryNewNote(count);
		StringBuffer content = new StringBuffer();
		if(list == null){
			return "";
		}
		for(Note n : list){
			content.append(n.getContent());
		}
		return content.toString();
	}
}
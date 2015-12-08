package service;

import dao.DocumentDAO;
import domain.Document;
import java.util.List;
import net.sf.json.JSONArray;

public abstract interface DocumentService {
	public abstract void setDocumentDao(DocumentDAO paramDocumentDAO);

	public abstract int countByCriteria(Document paramDocument);

	public abstract Document selectByPrimaryKey(Integer paramInteger);

	public abstract List<Document> selectByCriteria(Document paramDocument);

	public abstract List<Document> selectByCriteriaForPaging(
			Document paramDocument);

	public abstract JSONArray selectDocumentForTree(String paramString);

	public abstract void insert(Document paramDocument);

	public abstract void update(Document paramDocument);

	public abstract void delete(String paramString1, String paramString2)
			throws Exception;

	public abstract void move(String paramString1, String paramString2);
}
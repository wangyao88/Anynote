package dao;

import domain.Document;
import java.util.List;

public abstract interface DocumentDAO
{
  public abstract Document selectByPrimaryKey(int paramInt);

  public abstract List<Document> selectByCriteria(Document paramDocument);

  public abstract List<Document> selectByCriteriaForPaging(Document paramDocument, int paramInt1, int paramInt2);

  public abstract int countByCriteria(Document paramDocument);

  public abstract void insert(Document paramDocument);

  public abstract int updateByPrimaryKey(Document paramDocument);

  public abstract int deleteByPrimaryKey(int paramInt);
}
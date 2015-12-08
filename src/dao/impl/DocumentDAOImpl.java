package dao.impl;

import dao.DocumentDAO;
import domain.Document;
import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class DocumentDAOImpl extends SqlMapClientDaoSupport
  implements DocumentDAO
{
  public Document selectByPrimaryKey(int id)
  {
    Document record = (Document)getSqlMapClientTemplate().queryForObject("an_document.selectByPrimaryKey", Integer.valueOf(id));
    return record;
  }

  public List<Document> selectByCriteria(Document document)
  {
    List list = getSqlMapClientTemplate().queryForList("an_document.selectByCriteria", document);
    return list;
  }

  public List<Document> selectByCriteriaForPaging(Document document, int start, int limit)
  {
    List list = getSqlMapClientTemplate().queryForList("an_document.selectByCriteria", document, start, limit);
    return list;
  }

  public int countByCriteria(Document document)
  {
    Integer count = (Integer)getSqlMapClientTemplate().queryForObject("an_document.countByCriteria", document);
    return count.intValue();
  }

  public void insert(Document document)
  {
    getSqlMapClientTemplate().insert("an_document.insert", document);
  }

  public int updateByPrimaryKey(Document document)
  {
    int rows = getSqlMapClientTemplate().update("an_document.updateByPrimaryKey", document);
    return rows;
  }

  public int deleteByPrimaryKey(int id)
  {
    int rows = getSqlMapClientTemplate().delete("an_document.deleteByPrimaryKey", Integer.valueOf(id));
    return rows;
  }
}
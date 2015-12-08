package com.sxkl.sign.in.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.sxkl.sign.in.dao.SignInDAO;
import com.sxkl.sign.in.po.SignIn;

public class SingnInDAOImpl extends SqlMapClientDaoSupport implements SignInDAO{

	public void insert(SignIn signIn) {
		getSqlMapClientTemplate().insert("an_sign_in.insert", signIn);
	}

	public void deleteByPrimaryKey(String id) {
		getSqlMapClientTemplate().delete("an_sign_in.deleteByPrimaryKey", id);
	}

	@SuppressWarnings("unchecked")
	public List<SignIn> selectByCriteriaForPaging(SignIn paramSignIn,int start, int limit) {
		List<SignIn> list = getSqlMapClientTemplate().queryForList(
				"an_sign_in.selectByCriteria", paramSignIn, start, limit);
		return list;
	}

	public int countByCriteria(SignIn paramSignIn) {
		Integer count = (Integer)getSqlMapClientTemplate().queryForObject("an_sign_in.countByCriteria", paramSignIn);
	    return count.intValue();
	}

}

package com.sxkl.sign.in.dao;

import java.util.List;

import com.sxkl.sign.in.po.SignIn;

public abstract interface SignInDAO {

	public abstract void insert(SignIn signIn);
	
	public abstract void deleteByPrimaryKey(String id);
	
	public abstract List<SignIn> selectByCriteriaForPaging(SignIn paramSignIn, int start, int limit);
	
	public abstract int countByCriteria(SignIn paramSignIn);
}

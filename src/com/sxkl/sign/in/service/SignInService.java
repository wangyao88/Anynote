package com.sxkl.sign.in.service;

import java.util.List;

import com.sxkl.sign.in.po.SignIn;

import domain.Account;

public abstract interface SignInService {

    public abstract void insert(SignIn signIn);
	
	public abstract void deleteByPrimaryKey(String id);
	
	public abstract List<Account> selectByCriteriaForPaging(SignIn paramSignIn, int start, int limit);
	
	public abstract int countByCriteria(SignIn paramSignIn);

	public abstract List<SignIn> selectByCriteriaForPaging(SignIn paramSignIn);

}

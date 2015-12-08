package com.sxkl.sign.in.service.impl;

import global.security.SessionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import util.DateUtils;

import com.sxkl.sign.in.dao.SignInDAO;
import com.sxkl.sign.in.po.SignIn;
import com.sxkl.sign.in.service.SignInService;

import domain.Account;

public class SignInServiceImpl implements SignInService{
	
	private SignInDAO signInDao;
	
	public SignInDAO getSignInDao() {
		return signInDao;
	}

	public void setSignInDao(SignInDAO signInDao) {
		this.signInDao = signInDao;
	}

	public void insert(SignIn signIn) {
		signIn.setId(UUID.randomUUID().toString());
		signIn.setSignUserId(SessionUtils.getCurrentUserId());
		signIn.setSignUserName(SessionUtils.getCurrentUserName());
		signIn.setDate(new Date());
		this.signInDao.insert(signIn);
	}

	public void deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		
	}

	public List<Account> selectByCriteriaForPaging(SignIn paramSignIn,
			int start, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	public int countByCriteria(SignIn paramSignIn) {
		paramSignIn.setSignUserId(SessionUtils.getCurrentUserId());
		int count = this.signInDao.countByCriteria(paramSignIn);
		return count;
	}

	public List<SignIn> selectByCriteriaForPaging(SignIn paramSignIn) {
		paramSignIn.setSignUserId(SessionUtils.getCurrentUserId());
		List<SignIn> signInList = this.signInDao.selectByCriteriaForPaging(
				paramSignIn, paramSignIn.getStart(), paramSignIn.getLimit());
		List<SignIn> results = new ArrayList<SignIn>();
		if (signInList != null) {
			for (int i = 0; i < signInList.size(); i++) {
				SignIn signIn = (SignIn) signInList.get(i);
				signIn.setDateStr(DateUtils.formatDate2Str(signIn.getDate(), "yyyy-MM-dd hh:mm:ss"));
				results.add(signIn);
			}
		}
		return results;
	}
	
}

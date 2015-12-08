package com.sxkl.sign.in.action;

import global.beanUtils.BeanUtils;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import util.ServletHelp;
import action.BaseAction;

import com.sxkl.sign.in.po.SignIn;
import com.sxkl.sign.in.service.SignInService;

public class SignInAction extends BaseAction{
	
	private SignInService signInService;
	
	public SignInService getSignInService() {
		return signInService;
	}

	public void setSignInService(SignInService signInService) {
		this.signInService = signInService;
	}

	@SuppressWarnings("rawtypes")
	public ActionForward signIn(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map map = request.getParameterMap();
		SignIn signIn = new SignIn();
		BeanUtils.populate(signIn, map);
		signIn.setIp("127.0.0.1");
		this.signInService.insert(signIn);
		JSONObject res = new JSONObject();
		res.put("success", Boolean.valueOf(true));
		String result = res.toString();
		ServletHelp.outRequestForJson(request, response, result);
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public ActionForward querySignInHistoryData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map map = request.getParameterMap();
		SignIn paramSignIn = new SignIn();
		BeanUtils.populate(paramSignIn, map);
		
		List<SignIn> signInList = this.signInService.selectByCriteriaForPaging(paramSignIn);
		JSONArray datas = new JSONArray();
		if (signInList != null) {
			datas = JSONArray.fromObject(signInList);
		}
		int count = this.signInService.countByCriteria(paramSignIn);

		JSONObject res = new JSONObject();
		res.put("success", Boolean.valueOf(true));
		res.put("datas", datas);
		res.put("results", Integer.valueOf(count));
		ServletHelp.outRequestForJson(request, response, res.toString());
		return null;
	}
}

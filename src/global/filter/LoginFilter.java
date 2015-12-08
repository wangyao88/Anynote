package global.filter;

import global.Constants;
import global.security.AuthenticatorHolder;
import global.security.ClientSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import util.ServletHelp;
import util.StringUtils;

public class LoginFilter implements Filter {
	private String unfilteredURIs;
	private String loginPageUrls;

	public void init(FilterConfig config) throws ServletException {
		this.unfilteredURIs = jointString(config
				.getInitParameter("unfilteredURIs"));
		this.loginPageUrls = jointString(config
				.getInitParameter("loginPageUrls"));
	}

	public void doFilter(ServletRequest servletRequest,
			ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		HttpSession session = request.getSession(false);
		String requestURI = request.getRequestURI();
		String userId = request.getParameter("userId");
		String actionType = request.getParameter("actionType");

		if (requestURI.matches(this.unfilteredURIs)) {
			chain.doFilter(request, response);
			return;
		}
		if ((requestURI.matches(this.loginPageUrls))
				|| (requestURI.contains("resetPwd.jsp"))
				|| (requestURI.contains("register.jsp"))) {
			InputStream is = Constants.class.getClassLoader()
					.getResourceAsStream("../setting.properties");
			Properties p = new Properties();
			try {
				if(is != null){
					p.load(is);
					request.setAttribute("openRegister",p.getProperty("setting.openRegister"));
				}
//				p.load(is);
//				request.setAttribute("openRegister",p.getProperty("setting.openRegister"));
			} finally {
				if(is != null){
					is.close();
				}
			}
			chain.doFilter(request, response);
			return;
		}

		if (!"UPLOAD_ACTION".equals(actionType)) {
			ClientSession cs = null;
			if (session != null) {
				cs = (ClientSession) session.getAttribute("CLIENT_SESSION");
			}
			if ((session == null) || (cs == null)) {
				if (requestURI.matches(this.loginPageUrls)) {
					chain.doFilter(request, response);
					return;
				}
				if (requestURI.contains("Action.do")) {
					JSONObject res = new JSONObject();
					res.put("success", Boolean.valueOf(false));
					res.put("message", "网络连接超时，请重新登录.");
					res.put("exceptionMsg", "网络连接超时，请重新登录.");
					ServletHelp.outRequestForJson(request, response,
							res.toString());
					return;
				}
				response.sendRedirect(request.getContextPath()
						+ "/websrc/page/common/sessionError.jsp");
				return;
			}

			AuthenticatorHolder.setClientSession(cs);
			if (requestURI.matches(this.loginPageUrls)) {
				response.sendRedirect(request.getContextPath()
						+ "/websrc/page/index.jsp");
			}

		} else if (StringUtils.isNotEmpty(userId)) {
			ClientSession cs = new ClientSession();
			cs.setUserId(userId);
			AuthenticatorHolder.setClientSession(cs);
		} else {
			response.sendRedirect(request.getContextPath()
					+ "/websrc/page/common/sessionError.jsp");
			return;
		}

		chain.doFilter(request, response);
	}

	public void destroy() {
	}

	protected String jointString(String str) {
		StringBuffer buf = new StringBuffer();
		for (StringTokenizer st = new StringTokenizer(str != null ? str : "",
				"\n", false); st.hasMoreTokens();) {
			buf.append(st.nextToken().trim());
		}
		return buf.toString();
	}
}
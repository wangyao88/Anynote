package global.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

	/* Session�����¼� */
	public void sessionCreated(HttpSessionEvent event) {
		//ServletContext ctx = event.getSession().getServletContext();
		
	}

	/* SessionʧЧ�¼� */
	public void sessionDestroyed(HttpSessionEvent se) {
//           String result = HttpRequestServer.sendPost(url, param);
	}
}
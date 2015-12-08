package global.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

	/* Session创建事件 */
	public void sessionCreated(HttpSessionEvent event) {
		//ServletContext ctx = event.getSession().getServletContext();
		
	}

	/* Session失效事件 */
	public void sessionDestroyed(HttpSessionEvent se) {
//           String result = HttpRequestServer.sendPost(url, param);
	}
}
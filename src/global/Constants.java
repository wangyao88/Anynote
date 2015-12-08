package global;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.log4j.Logger;

public class Constants {
	public static final String TYPE_UPDATE = "UPDATE";
	public static final String TYPE_CREATE = "CREATE";
	public static final String TYPE_UPLOAD = "UPLOAD";
	public static final int PAGE_SIZE = 20;
	public static final int PAGE_NO_COUNT = 9;
	public static final int DEFAULT_CATEGORYID = 1;
	public static final int PAGE_SIZE_PICTURE = 20;
	public static int IMAGE_WIDTH = 140;

	public static int IMAGE_HEIGHT = 140;
	public static final String UPLOAD_FILE_PARAM_NAME = "fileData";
	public static final String FILE_PATH = "/websrc/file";
	public static final String UPLOAD_PICTURE_TEMP_PATH = "/websrc/file/temp";
	public static final String UPLOAD_PICTURE_PATH = "/websrc/file/{0}/picture";
	public static final String THUMBNAIL = "thumbnail";
	public static final String UPLOAD_FILE_PATH = "/websrc/file/{0}/document";
	public static final String FEED_FILE_PATH = "/websrc/file/{0}/feed";
	public static final String UPLOAD_FILE_SIZE_LIMIT = "100MB";
	public static final String UPLOAD_FILE_TYPE_LIMIT = "*.*";
	public static final String UPLOAD_PICTURE_SIZE_LIMIT = "1MB";
	public static final double UPLOAD_PICTURE_MAX_SIZE = 1048576.0D;
	public static final String UPLOAD_PICTURE_TYPE_LIMIT = "*.jpg;*.jpeg;*.png;";
	public static final int UPLOAD_COUNT_LIMIT = 100;
	public static final int PAGE_SIZE_FEED = 10;
	public static final String EMAIL_BEFORE_TITLE = "来自Anynote：";
	public static String EMAIL_SMTP_HOST = "smtp.163.com";

	public static String EMAIL_ACCOUNT = "Anynote@163.com";

	public static String EMAIL_PASSWORD = "1914125xyting";
	public static final String TEMP_FILE_PATH = "/websrc/file/temp";
	public static final String HTML_TEMPLET_START = "<html><head><title>Anynote</title><meta http-equiv='Content-Type' content='text/html; charset=utf-8' /></head><body>";
	public static final String HTML_TEMPLET_END = "</body></html>";
	public static final String OPML_TEMPLET_START = "<?xml version='1.0' encoding='UTF-8'?>\r\n<opml version='1.0'>\r\n<head><title>Anynote订阅列表</title></head>\r\n<body>\r\n";
	public static final String OPML_TEMPLET_END = "</body>\r\n</opml>";
	public static final String ADMIN_USERID = "admin";
	public static final String MAX_DATE = "9999-12-31";
	public static final String DEL_FLAG_1 = "1";
	public static final String DEL_FLAG_2 = "2";
	public static final String DEL_FLAG_NAME_1 = "有效";
	public static final String DEL_FLAG_NAME_2 = "无效";
	public static final Map<String, String> DELFLAG_MAP = new HashMap();
	public static final String STATUS_1 = "1";
	public static final String STATUS_2 = "2";
	public static final String STATUS_NAME_1 = "公开";
	public static final String STATUS_NAME_2 = "私人";
	public static final Map<String, String> STATUS_MAP = new HashMap();
	public static final String USER_STATUS_1 = "1";
	public static final String USER_STATUS_2 = "2";
	public static final String USER_STATUS_NAME_1 = "正常";
	public static final String USER_STATUS_NAME_2 = "停用";
	public static final Map<String, String> USER_STATUS_MAP = new HashMap();
	public static final String ACCOUNT_TYPE_1 = "1";
	public static final String ACCOUNT_TYPE_2 = "2";
	public static final String ACCOUNT_TYPE_NAME_1 = "收入";
	public static final String ACCOUNT_TYPE_NAME_2 = "支出";
	public static final Map<String, String> ACCOUNT_TYPE_MAP = new HashMap();
	public static final String LEVEL_1 = "1";
	public static final String LEVEL_2 = "2";
	public static final String LEVEL_3 = "3";
	public static final String LEVEL_NAME_1 = "高";
	public static final String LEVEL_NAME_2 = "中";
	public static final String LEVEL_NAME_3 = "低";
	public static final Map<String, String> LEVEL_TYPE_MAP = new HashMap();
	public static final String COMPLETE_TYPE_1 = "1";
	public static final String COMPLETE_TYPE_2 = "2";
	public static final String COMPLETE_TYPE_NAME_1 = "未完成 ";
	public static final String COMPLETE_TYPE_NAME_2 = "已完成";
	public static final Map<String, String> COMPLETE_TYPE_MAP = new HashMap();
	public static final String SEX_M = "1";
	public static final String SEX_F = "2";
	public static final String SEX_NAME_M = "男 ";
	public static final String SEX_NAME_F = "女";
	public static final Map<String, String> SEX_MAP = new HashMap();
	public static final String HOME_PAGE_0_URL = "/websrc/page/home/home.jsp";
	public static final String HOME_PAGE_1_URL = "/websrc/page/todo/todoList.jsp";
	public static final String HOME_PAGE_2_URL = "/websrc/page/note/noteList.jsp";
	public static final String HOME_PAGE_3_URL = "/websrc/page/picture/pictureList.jsp";
	public static final String HOME_PAGE_4_URL = "/websrc/page/account/accountList.jsp";
	public static final String HOME_PAGE_5_URL = "/websrc/page/feedViewer/feedFavorite.jsp";
	public static final String HOME_PAGE_6_URL = "/websrc/page/user/userList.jsp";
	public static final String HOME_PAGE_7_URL = "/websrc/page/document/documentList.jsp";
	public static final Map<String, String> HOME_PAGE_MAP = new HashMap();
	public static final String MODULE_SHOW = "1";
	public static final String MODULE_HIDE = "2";
	public static final String MODULE_SHOW_NAME = "显示";
	public static final String MODULE_HIDE_NAME = "隐藏";
	public static final Map<String, String> MODULE_SHOW_HIDE_MAP = new HashMap();
	public static final String CHECK_TRUE = "on";
	public static final String CHECK_FALSE = "off";
	public static final String ISLEAF_TRUE_CODE = "1";
	public static final String ISLEAF_FALSE_CODE = "0";
	public static final boolean ISLEAF_TRUE_VALUE = true;
	public static final boolean ISLEAF_FALSE_VALUE = false;
	public static final Map<String, Boolean> ISLEAF_MAP = new HashMap();
	public static final String ROLE_CODE_1 = "1";
	public static final String ROLE_CODE_2 = "2";
	public static final String ROLE_CODE_3 = "3";
	public static final String ROLE_NAME_1 = "系统管理员";
	public static final String ROLE_NAME_2 = "管理员";
	public static final String ROLE_NAME_3 = "普通用户";
	public static final Map<String, String> ROLE_MAP = new HashMap();
	public static final String E_ACCOUNTCATEGORY_DEL = "行号为 {0} 的收支项目下存在账目信息，删除失败.";
	public static final String E_CATEGORY_DEL = "分类 {0} 下存在笔记，删除失败.";
	public static final String E_FAILURE_LOGIN = "用户名或密码错误.";
	public static final String E_LOGIN_PASSWORD = "登录密码错误.";
	public static final String E_FAILURE_RESETPWD = "用户名或邮箱错误.";
	public static final String E_PASSWORD = "原密码错误.";
	public static final String E_REPASSWORD = "两次输入的密码不一致.";
	public static final String E_LOGIN_TIMEOUT = "网络连接超时，请重新登录.";
	public static final String E_STOP_LOGIN = "该用户已被禁用.";
	public static final String E_USER_EXIST = "账号已存在.";
	public static final String E_EMAIL_EXIST = "邮箱已被使用.";
	public static final String E_ROLE_ERROR = "角色设置错误.";
	public static final String E_SEND_EMAIL = "邮件发送失败.";
	public static final String E_VERIFY_CODE = "验证码错误.";
	public static final String E_DITTO_FOLDER = "创建失败，文件名 {0} 重复.";
	public static String[] TODO_WEEK = new String[14]; 
	public static String[] TODO_WEEKEND = new String[10]; 
	public static final double MONTH_PRE_CAST = 2000;
	public static final String S_ANYNOTENEW_URL = "http://127.0.0.1:8888/AnynoteNew";

	static {
		TODO_WEEK[0] = "起床";
		TODO_WEEK[1] = "压腿";
		TODO_WEEK[2] = "做早饭吃早饭";
		TODO_WEEK[3] = "走路上班";
		TODO_WEEK[4] = "上班";
		TODO_WEEK[5] = "吃午饭";
		TODO_WEEK[6] = "午休";
		TODO_WEEK[7] = "走路下班";
		TODO_WEEK[8] = "跑步";
		TODO_WEEK[9] = "吃晚饭";
		TODO_WEEK[10] = "浏览网站-学习";
		TODO_WEEK[11] = "聊天";
		TODO_WEEK[12] = "洗漱";
		TODO_WEEK[13] = "睡觉";
		
		TODO_WEEKEND[0] = "起床";
		TODO_WEEKEND[1] = "压腿";
		TODO_WEEKEND[2] = "吃午饭";
		TODO_WEEKEND[3] = "午休";
		TODO_WEEKEND[4] = "跑步";
		TODO_WEEKEND[5] = "吃晚饭";
		TODO_WEEKEND[6] = "浏览网站-学习";
		TODO_WEEKEND[7] = "聊天";
		TODO_WEEKEND[8] = "洗漱";
		TODO_WEEKEND[9] = "睡觉";
		
		DELFLAG_MAP.put("1", "有效");
		DELFLAG_MAP.put("2", "无效");

		STATUS_MAP.put("1", "公开");
		STATUS_MAP.put("2", "私人");

		USER_STATUS_MAP.put("1", "正常");
		USER_STATUS_MAP.put("2", "停用");

		ACCOUNT_TYPE_MAP.put("1", "收入");
		ACCOUNT_TYPE_MAP.put("2", "支出");

		LEVEL_TYPE_MAP.put("1", "高");
		LEVEL_TYPE_MAP.put("2", "中");
		LEVEL_TYPE_MAP.put("3", "低");

		COMPLETE_TYPE_MAP.put("1", "未完成 ");
		COMPLETE_TYPE_MAP.put("2", "已完成");

		SEX_MAP.put("1", "男 ");
		SEX_MAP.put("2", "女");

		MODULE_SHOW_HIDE_MAP.put("1", "显示");
		MODULE_SHOW_HIDE_MAP.put("2", "隐藏");

		ISLEAF_MAP.put("1", Boolean.valueOf(true));
		ISLEAF_MAP.put("0", Boolean.valueOf(false));

		ROLE_MAP.put("1", "系统管理员");
		ROLE_MAP.put("2", "管理员");
		ROLE_MAP.put("3", "普通用户");

		InputStream is = Constants.class.getClassLoader().getResourceAsStream(
				"../setting.properties");
		Properties p = new Properties();
		try {
			p.load(is);

			EMAIL_SMTP_HOST = p.getProperty("setting.emailSmtpHost");
			EMAIL_ACCOUNT = p.getProperty("setting.emailAccount");
			EMAIL_PASSWORD = p.getProperty("setting.emailPassword");

			IMAGE_WIDTH = Integer.parseInt(p.getProperty("setting.imageWidth"));
			IMAGE_HEIGHT = Integer.parseInt(p
					.getProperty("setting.imageHeight"));
		} catch (IOException e) {
			Logger logger = Logger.getLogger(Constants.class);
			logger.error(e.getLocalizedMessage(), e);
		}
	}
}
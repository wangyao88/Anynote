package util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Date;

public class DBTool {

	private static Connection conn;

	public static Connection getConnection() {
		if (conn != null) {
			return conn;
		}
		String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // ����JDBC����
		String dbURL = "jdbc:sqlserver://localhost:1433; DatabaseName=Anynote"; // ���ӷ����������ݿ�test
		String userName = "sa"; // Ĭ���û���
		String userPwd = "19890426"; // ����
		try {
			Class.forName(driverName);
			conn = DriverManager.getConnection(dbURL, userName, userPwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void backupDatabase() throws Exception{
		String proc = "{call Backup_DataBase(?,?)}";
		String path = "F:\\SQLServerData\\backup\\Anynote\\Anynote(" 
		               + DateUtils.formatDate2Str(new Date(), "yyyy-MM-dd")
		               + ").bak";
		if(conn == null){
			conn = getConnection();
		}
		CallableStatement cst = conn.prepareCall(proc);
		cst.setString(1, "Anynote");// ����in������ֵ
		cst.setString(2, path);// ����in������ֵ
		//cst.registerOutParameter(2, Types.INTEGER);// ע��out����������
		cst.execute();
	}

}

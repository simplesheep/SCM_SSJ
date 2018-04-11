package simple.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import simple.tool.DataBaseConfigTool3;

public class BaseDao {
	
	private static String url;

	private static String driver;

	private static String user;

	private static String password;

	private static void init() {

		// ��ȡDataBaseConfigTool ��ʱ�ö����ǵ��� ������ΪBaseDao�ķ�����ʼ�� ���������ļ�Ƶ������ȡ��������
		DataBaseConfigTool3 baseConfigTool = DataBaseConfigTool3.getInstance();
		driver = baseConfigTool.getProperty("driver");
		user = baseConfigTool.getProperty("user");
		password = baseConfigTool.getProperty("password");
		url = baseConfigTool.getProperty("url");

	}

	static {
		init();
	}

	// ����
	public static Connection getConnection() {
		Connection connection = null;
		
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return connection;

	}

	// ��ɾ��
	public static int executeUpdate(Connection connection, String sql, Object[] params) {

		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int result = 0;
		// ע�����
		if (params != null) {
			for (int i = 0; i < params.length; i++) {

				try {
					ps.setObject(i + 1, params[i]);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		try {
			result = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

	}

	// ��ѯ
	public static ResultSet executeQuery(Connection connection, String sql, Object[] params) {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(sql);
			// ע�����
			if (params != null) {
				for (int i = 0; i < params.length; i++) {

					try {
						ps.setObject(i + 1, params[i]);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;

	}

	public static void close(Connection connection, Statement statement, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (statement != null) {
				statement.close();
				statement = null;
			}
			if (connection != null) {
				connection.close();
				connection = null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

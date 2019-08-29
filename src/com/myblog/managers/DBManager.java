package com.myblog.managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBManager {

	// dirverUrl
	private static final String driver = "com.mysql.jdbc.Driver";
	// connurl
	private static final String url = "jdbc:mysql://localhost/myblog?characterEncoding=utf-8";
	//userName
	private static final String userName = "root";
	//pwd
	private static final String pwd = "thq19990925";
	// conn
	private static Connection conn = null;
	// pst
	private static PreparedStatement pst = null;
	// res
	private static ResultSet res = null;

	static {
		try {
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// 初始化
	private static void initConn() {
		try {
			if (conn == null || conn.isClosed()) {
				conn = DriverManager.getConnection(url, userName, pwd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 查询
	public static synchronized ResultSet runQuery(String sql, Object[] par) {
		try {
			initConn();
			pst = conn.prepareStatement(sql);
			if (par != null) {
				for (int i = 0; i < par.length; i++) {
					pst.setObject(i + 1, par[i]);
				}
			}
			return pst.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} 
	}

	// 非查询
	public static synchronized boolean runChange(String sql, Object[] par) {
		try {
			initConn();
			conn.setAutoCommit(false);
			pst = conn.prepareStatement(sql);
			if (par != null) {
				for (int i = 0; i < par.length; i++) {
					pst.setObject(i + 1, par[i]);
				}
			}
			if (pst.executeUpdate() > 0) {
				conn.commit();
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		} finally {
			closeConn();
		}
	}

	//释放资源
	public static void closeConn(){
		try {
			pst.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		System.out.println(DBManager.runQuery("select * from article", null));
	}

}

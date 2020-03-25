package com.umf.mapper.dbbase;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import com.umf.utils.LogUtil;

@SuppressWarnings("all")
public class ConnectionManager {

	private static ComboPooledDataSource ds1 = new ComboPooledDataSource("data1");
	private static ConnectionManager instance;
	private LogUtil log = new LogUtil();
	
	/** 获取连接池管理实例对象  **/
	public static final ConnectionManager getInstance() {
		return (instance == null) ? new ConnectionManager() : instance;
	}

	/** 获取数据库连接  **/
	public synchronized final Connection getConnection() throws Exception {
		return ds1.getConnection();
	}

	/** 关闭数据源  **/
	protected void finalize(ComboPooledDataSource dataSource) throws Throwable {
		DataSources.destroy(dataSource);
		super.finalize();
	}

	/** 释放资源(数据库连接对象、执行编译对象、结果集对象) **/
	public void closeCon(Connection conn, PreparedStatement pstmt, ResultSet rs)
			throws Exception {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			log.errinfoE(e);
		}
	}

	/** 释放资源(数据库连接对象、执行编译对象) **/
	public void closeCon(Connection conn, PreparedStatement pstmt)
			throws Exception {
		try {
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			log.errinfoE(e);
		}
	}

	/** 释放资源(执行调用存储过程对象、数据库连接对象) **/
	public void closeCon(CallableStatement st, Connection conn)
			throws Exception {
		try {
			if (st != null) {
				st.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			log.errinfoE(e);
		}
	}
	
	public void myCommit(Connection conn) throws Exception {
		try {
			if (conn != null) {
				conn.commit();
				conn.close();
			}
		} catch (Exception e) {
			log.errinfoE(e);
		}
	}
	
	public void myRollback(Connection conn) throws Exception {
		try {
			if (conn != null) {
				conn.rollback();
				conn.close();
			}
		} catch (Exception e) {
			log.errinfoE(e);
		}
	}
}

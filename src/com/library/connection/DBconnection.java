package com.library.connection;
import com.library.config.ConfigData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * DB에 접속하기 위한 Connection 객체를 반환하는 메서드를 작성한 클래스.
 * 
 * getConnection()은 static로 선언되어 객체 생성 없이 사용 가능합니다.
 */
public class DBconnection {
	/**
	 * DB에 접속하기 위한 Connection 객체를 반환하는 메서드.
	 */
	public static Connection getConnection() throws SQLException {
		ConfigData configData  = new ConfigData();
		String url ="jdbc:oracle:thin:@localhost:1521/xe";
		Connection conn =DriverManager.getConnection(url, 
											configData.getId(), 
											configData.getPw());
		conn.setAutoCommit(false);//자동 커밋 off - commit를 개발자가 직접 호출
		// 자동 커밋이면 rollback이 불가합니다.
		
		return conn;
	}
}
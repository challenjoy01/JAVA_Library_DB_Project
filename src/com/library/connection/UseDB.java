package com.library.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DB를 이용하는 작업 실행 메서드를 포함한 추상 클래스.
 * 
 * sql문을 사용하는 메서드를 가진 클래스를 만들 때 상속해서 템플릿처럼 사용 가능합니다.
 * 1. 클래스에 sql문을 사용하는 작업이 하나 뿐일 때는 doExecute(Connection conn)를
 * 오버라이딩해서 사용합니다. 이럴 경우 실행 함수는 execute()입니다.
 * 2. 클래스에 sql문을 사용하는 작업이 여러 개일 경우에는
 * doExecute(Connection conn, String jobNum)를 오버라이딩해서 내부에 작업 함수들을
 * 모아 놓은 switch-case문을 작성합니다. 작업 함수들은 private로 만듭니다.
 * 이럴 경우 실행 함수는 execute(String jobNum)입니다.
 * 
 * @author challenjoy01
 */
public abstract class UseDB {
	
	/**
	 * DB를 이용한 단일 작업을 실행하는 함수. 
	 * 
	 * DB와 연결하는 코드, 작업 실행부 함수, DB와 연결 해제하는 코드가 작성되어 있습니다. 
	 */
	public void execute()
	{
		// DB 사용을 위한 객체 선언
		Connection conn = null;
		try {
			// DB 연결
			conn = DBconnection.getConnection();
			
			// 작업 함수 실행
			doExecute(conn);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("DB 접속 실패");
			e.printStackTrace();
		}
		finally
		{
			try {
				// 무조건 커밋하고 끝내기
				conn.commit();
				
				// DB 닫기
				if(conn != null)
				{
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("DB 오류");
				e.printStackTrace();
			}// catch
		}// finallly
	}// execute()
	
	
	/**
	 * 매개 변수에 따라 DB를 이용한 여러 작업을 실행하는 함수. 
	 * 
	 * DB와 연결하는 코드, 작업 실행부 함수, DB와 연결 해제하는 코드가 작성되어 
	 * 있습니다.
	 * 
	 * @param job 실행할 작업의 번호 또는 이름
	 */
	public void execute(String job)
	{
		// DB 사용을 위한 객체 선언
		Connection conn = null;
		try {
			// DB 연결
			conn = DBconnection.getConnection();
			
			// 작업 함수 실행
			doExecute(conn, job);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("DB 접속 실패");
			e.printStackTrace();
		}
		finally
		{
			try {
				// 무조건 커밋하고 끝내기
				conn.commit();
				
				if(conn != null)
				{
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("DB 오류");
				e.printStackTrace();
			}// catch
		}// finallly
	}// execute(String job)
	
	
	/**
	 * 실제로 실행할 단일 작업을 작성하는 함수 
	 * 
	 * @param conn DB에 접속하기 위한 도구
	 */
	protected void doExecute(Connection conn)
	{
		// TODO 실행할 작업에 따라 stmt와 pstmt 중 취사선택합니다.
		Statement stmt = null;
		PreparedStatement pstmt = null;

		// sql 실행 결과를 받아올 객체
		ResultSet rs = null;
		// TODO sql문 작성
		String sql = "";
		try {
			// TODO 실행할 작업을 여기에 작성
			// 작성했다면 아래의 안내문을 삭제합니다.
			System.out.println("단일 작업용 doExecute(Connection conn)를 작성해야 합니다.");
			
			// sql 실행
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try {
				if(rs != null)
				{
					rs.close();
				}
				if(stmt != null)
				{
					stmt.close();
				}
				if(pstmt != null)
				{
					pstmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}
	
	/**
	 * 실제로 실행할 작업을 모아 놓은 case문을 작성하는 함수. 
	 * 
	 * 매개 변수에 따라 다른 작업을 실행합니다.
	 * 
	 * @param conn DB에 접속하기 위한 도구
	 * @param jobNum 실행할 작업의 번호 또는 이름 
	 */
	protected void doExecute(Connection conn, String job)
	{
		// TODO switch문 아래의 case에 매개 변수에 맞는 함수들을 작성합니다.
		// 작성했다면 아래의 안내문을 삭제합니다.
		System.out.println("다중 작업용 doExecute(Connection conn, String job)를 작성해야 합니다.");

		switch(job)
		{
		
		}
	}
}

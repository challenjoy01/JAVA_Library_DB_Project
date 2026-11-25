package com.library.account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.library.connection.UseDB;

/** 로그인, 로그아웃과 계정 관련 작업을 실행하는 클래스.
 * 
 * 계정 관련 메서드들과 함수 통합 실행기인 execute(String job), 현재 로그인 상태를
 * 확인하는 getUserType()가 있습니다.
 * 
 * @author challenjoy01
 */
public class AccountController extends UseDB{
	/** 현재 로그인 상태를 나타내는 변수.
	 * <ul>
	 * 		<li>{@code "guest"} 게스트 상태</li>
	 * 		<li>{@code "user"} 일반 회원 계정</li>
	 * 		<li>{@code "admin"} 관리자 계정</li>
	 * </ul>
	 * */
	private static String userType = "GUEST";
	/** 현재 로그인한 회원 아이디를 나타내는 변수. */
	private static String userID = null;
	/**
	 * 계정 관련 함수를 실행하는 함수.
	 * @param job 실행할 작업을 결정하는 매개변수
	 * <ul>
	 * 		<li>{@code "logIn"} 로그인</li>
	 * 		<li>{@code "logOut"} 로그아웃</li>
	 * 		<li>{@code "signUp"} 회원 가입</li>
	 * 		<li>{@code "deleteAccount"} 회원 탈퇴</li>
	 * </ul>
	 */
	@Override
	public void execute(String job)
	{
		super.execute(job);
	}
	// 위 함수는 주석을 남기기 위한 것이며 오버라이딩으로 인해 생기는 차이는 없습니다.
	
	/**
	 * 실제로 실행할 작업 메서드들을 모아 놓은 메서드. 
	 * 
	 * 매개 변수에 따라 다른 작업을 실행합니다. execute(String job)에서 호출됩니다.
	 * 
	 * @param conn DB에 접속하기 위한 도구
	 * @param jobNum 실행할 작업의 번호 또는 이름 
	 * <ul>
	 * 		<li>{@code "logIn"} 로그인</li>
	 * 		<li>{@code "logOut"} 로그아웃</li>
	 * 		<li>{@code "signUp"} 회원 가입</li>
	 * 		<li>{@code "deleteAccount"} 회원 탈퇴</li>
	 * </ul>
	 */
	@Override
	protected void doExecute(Connection conn, String job)
	{
		// TODO switch문 아래의 case에 매개 변수에 맞는 함수들을 작성합니다.

		switch(job)
		{
			case "logIn": 
			{
				logIn(conn);
				break;
			}
			case "logOut": 
			{
				logOut(conn);
				break;
			}
			case "signUp": 
			{
				signUp(conn);
				break;
			}
			case "deleteAccount": 
			{
				deleteAccount(conn);
				break;
			}
			default :
			{
				System.out.println("AccountController 오류 : 잘못된 매개변수입니다.");
				break;
			}
		}
	}
	
	/**
	 * 로그인 실행 메서드
	 * 
	 * @param conn DB에 접속하기 위한 도구
	 */
	private void logIn(Connection conn)
	{
		// 로그인 처리 과정
		Scanner scan = new Scanner(System.in);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String id = new String();
		String password = new String();
		Boolean logInSuccess = false;
		
		System.out.println("");
		System.out.println("=====로그인=====");
		System.out.println("아이디 입력");
		System.out.print("아이디 : ");
		id = scan.next();
		System.out.println("비밀번호 입력");
		System.out.print("비밀번호 : ");
		password = scan.next();
		
		try {
			/** 동일한 ID가 있는지 탐색 */
			String sql = "SELECT user_id, password, user_type FROM member "
						+ "WHERE user_id = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			

			/** 
			 * 결과가 존재할 경우(일치하는 ID가 존재할 경우. sql 결과가 없으면 
			 * next 실행 시 false가 반환됩니다.) 
			 */
			if(rs.next())
			{
				/** 비밀번호도 일치하는지 확인 */
				if(password.equals(rs.getString("password")))
				{
					userID = id;
					userType = rs.getString("user_type");
					logInSuccess = true;
					System.out.println("로그인 성공!\n");
				}
			}
			// 로그인에 실패했을 경우
			if(!logInSuccess)
			{
				System.out.println("아이디 또는 비밀번호가 일치하지 않습니다.\n");
			}
			
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
				if(pstmt != null)
				{
					pstmt.close();
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return;
	}// logIn 메서드
	
	/**
	 * 로그아웃 실행 메서드
	 * 
	 * @param conn DB에 접속하기 위한 도구
	 */
	private void logOut(Connection conn)
	{
		// 로그아웃 처리 과정
		
		userID = null;
		userType = "GUEST";
		return;
	}// logOut 메서드
	
	/**
	 * 회원 가입 실행 메서드
	 * 
	 * @param conn DB에 접속하기 위한 도구
	 */
	private void signUp(Connection conn)
	{
		/** 회원 정보를 받은 후 알맞은 정보를 받았다면 DB에 저장 */
		/** 로그인 정보를 초기화 */
		userID = null;
		userType = "GUEST";
		return;
	}// signUp 메서드
	
	/**
	 * 회원 탈퇴 실행 메서드
	 * 
	 * @param conn DB에 접속하기 위한 도구
	 */
	private void deleteAccount(Connection conn)
	{
		/** 현재 아이디에 해당하는 회원 데이터 삭제 */
		PreparedStatement pstmt = null;
		
		try {
			/** 현재 아이디에 해당하는 열을 삭제 */
			String sql = "DELETE FROM member "
						+ "WHERE user_id = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userID);
			
            int p = pstmt.executeUpdate();  //갱신
            if(p > 0) {
                conn.commit();
                System.out.println("회원 탈퇴 성공");
                
                /** 성공했을 경우 로그인 정보를 초기화 */
        		userID = null;
        		userType = "GUEST";
            } else {
                conn.rollback();
                System.out.println("회원 탈퇴 실패");
            }
		}// try 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			/** 예외 발생 시 롤백 */
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            System.out.println("회원 탈퇴 오류");
			e.printStackTrace();
		}// catch
		finally
		{
			try {
				if(pstmt != null)
				{
					pstmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}// finally
		return;
	}// deleteAccount 메서드

	
	/** 현재 로그인 상태를 반환하는 getter 메서드.
	 * 
	 * @return userType
	 * <ul>
	 * 		<li>{@code "guest"} 게스트 상태</li>
	 * 		<li>{@code "user"} 일반 회원 계정</li>
	 * 		<li>{@code "admin"} 관리자 계정</li>
	 * </ul>
	 * */
	public static String getUserType() {
		return userType;
	}
	
	/** 현재 로그인한 ID를 반환하는 getter 메서드.
	 * 
	 * @return userID
	 * null일 경우 게스트, 나머지는 회원 ID
	 * */
	public static String getUserID() {
		return userID;
	}
	
	
}

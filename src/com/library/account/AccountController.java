package com.library.account;

import java.sql.Connection;
import java.sql.Statement;

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
	private static String userType = "guest";
	
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
		//userType = "user";
		userType = "admin";
		return;
	}
	
	/**
	 * 로그아웃 실행 메서드
	 * 
	 * @param conn DB에 접속하기 위한 도구
	 */
	private void logOut(Connection conn)
	{
		// 로그아웃 처리 과정
		
		userType = "guest";
		return;
	}
	
	/**
	 * 회원 가입 실행 메서드
	 * 
	 * @param conn DB에 접속하기 위한 도구
	 */
	private void signUp(Connection conn)
	{
		userType = "guest";
		return;
	}
	
	/**
	 * 회원 탈퇴 실행 메서드
	 * 
	 * @param conn DB에 접속하기 위한 도구
	 */
	private void deleteAccount(Connection conn)
	{
		
		userType = "guest";
		return;
	}

	
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
}

package com.library.main;

import java.util.Scanner;

import com.library.account.AccountController;

public class LibraryMain {
	private Scanner scan = new Scanner(System.in);
	/** 현재 계정 상태를 저장하는 변수. */
	private static String userType = "guest";
	/** 계속 실행할지 여부를 나타내는 플래그 변수. */
	private static boolean isRunning = true;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/** 각 메뉴에 대한 객체 선언 */
		GuestMenu guestMenu = new GuestMenu();
		UserMenu userMenu = new UserMenu();
		AdminMenu adminMenu = new AdminMenu();

		/** 실행할지 여부를 체크하고 알맞은 메뉴를 실행 */
		while(isRunning)
		{			
			/** 현재 실행할 메뉴가 무엇인지 검사하고, 알맞은 메뉴를 실행 */
			if(AccountController.getUserType().equals("guest"))
			{
				/** 최종 종료는 로그아웃 또는 회원 탈퇴 후 게스트 상태에서만 가능합니다.
				 *  최종 종료를 선택하면 false가 리턴됩니다.
				 */
				/** 게스트 메뉴 실행 후 이후에도 실행할지 여부를 플래그에 저장합니다. */
				isRunning = guestMenu.menu();
				
			}
			else if(AccountController.getUserType().equals("user"))
			{
				// 일반 사용자 메뉴 실행
				userMenu.menu();
			}
			else if(AccountController.getUserType().equals("admin"))
			{
				// 관리자 메뉴 실행
				adminMenu.menu();
			}
			
			/** 프로그램 종료를 선택했다면 종료를 알리기 */
			if(!isRunning)
			{
				System.out.println("\n프로그램 종료");
			}
		}
	}//main
	
	
	
		
	
}//class

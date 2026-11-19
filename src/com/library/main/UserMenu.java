package com.library.main;

import java.util.Scanner;

import com.library.account.AccountController;

public class UserMenu {
	private Scanner scan = new Scanner(System.in);
	AccountController account = new AccountController();

	public void menu()
	{
		System.out.println("=====도서관 프로그램=====");
		System.out.println("현재 위치 : 메인 화면");
		System.out.println("회원 모드 : 일반 회원 모드입니다.");
		System.out.println("1 : 도서 검색");
		System.out.println("2 : 내 대출 상태 확인");
		System.out.println("3 : 도서 대여");
		System.out.println("4 : 도서 반납");
		System.out.println("5 : 로그아웃");
		System.out.println("6 : 회원 탈퇴");

		System.out.print("메뉴선택>> ");
		String choice=scan.next();
		
		switch(choice)
		{
			case "1" :
			{
				System.out.println("\n1 : 도서 검색\n");
				
				break;	
			}
			case "2" :
			{
				System.out.println("\n2 : 내 대출 상태 확인\n");
				
				break;	
			}
			case "3" :
			{
				System.out.println("\n3 : 도서 대여\n");
				
				break;	
			}
			case "4" :
			{
				System.out.println("\n4 : 도서 반납\n");
				
				break;	
			}
			case "5" :
			{
				System.out.println("\n5 : 로그아웃\n");
				account.execute("logOut");
				break;	
			}
			case "6" :
			{
				System.out.println("\n6 : 회원 탈퇴\n");
				account.execute("deleteAccount");
				break;	
			}
			default :
			{
				System.out.println("\n정해진 메뉴 내에서 선택해야 합니다.\n");

				break;	
			}
		}//switch
		
		return;
	}//menu

}

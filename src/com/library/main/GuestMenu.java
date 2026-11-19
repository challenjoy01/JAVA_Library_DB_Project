package com.library.main;

import java.util.Scanner;

import com.library.account.AccountController;

public class GuestMenu {
	private Scanner scan = new Scanner(System.in);
	AccountController account = new AccountController();
	
	public boolean menu()
	{	
		System.out.println("=====도서관 프로그램=====");
		System.out.println("현재 위치 : 메인 화면");
		System.out.println("회원 모드 : 비회원 모드입니다.");
		System.out.println("1 : 도서 검색");
		System.out.println("2 : 로그인");
		System.out.println("3 : 회원 가입");
		System.out.println("q : 종료");
		System.out.print("메뉴선택>> ");
		String choice=scan.next();
			
		switch(choice)
		{
			case "1" :
			{
				System.out.println("\n1 : 도서 검색\n");

				return true;
			}
			case "2" :
			{
				System.out.println("\n2 : 로그인\n");
				account.execute("logIn");
				return true;
			}
			case "3" :
			{
				System.out.println("\n3 : 회원 가입\n");
				account.execute("signUp");
				return true;	
			}
			case "q" :
			{
				System.out.println("\nq : 종료\n");
				return false;	
			}
			default :
			{
				System.out.println("\n정해진 메뉴 내에서 선택해야 합니다.\n");
				break;
			}
		}// switch	
		return true;
	}// menu
}

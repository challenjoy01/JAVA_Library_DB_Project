package com.library.loan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.library.account.AccountController;
import com.library.connection.UseDB;

public class LoanController extends UseDB {
	
	private Scanner s = new Scanner(System.in);// 스캐너
	private Scanner sLine = new Scanner(System.in);// 스캐너
	//private Scanner keywordLineS = new Scanner(System.in);// 스캐너	
	private PhysicalBookDTO PhysicalBookDTO; //실물 책 객채
	//private BookInfoDTO BookInfoDTO; // 도서 종류 객체
	//private List<PhysicalBookDTO> P_Book_List = new ArrayList<>(); // 실물 책 객채 리스트

	
	public void execute(String job)
	{
		super.execute(job);
	}

	
	protected void doExecute(Connection conn, String job) {
		try {
			switch (job) {
				case "loan": loan_process(conn); break;
				case "book_return": return_Process(conn); break;
				default:
					System.out.println("알 수 없는 작업: " + job);
					break;
			}
			
		} catch (SQLException e) {
			System.out.println("DB 처리 중 오류 발생");
			e.printStackTrace();
		}
	}

	
	//책 대출 프로세스--------------------------------------------------
	private void loan_process(Connection conn) throws SQLException {
		
		BookInfoDTO BookInfoDTO;
		List<BookInfoDTO> BookInfoList = new ArrayList<>(); //도서 종류 객체 리스트
		
		System.out.print("대출 할 책를 검색해주세요.(예: 책이름, 저자, 도서ID, 출판사, 분류코드) >> ");
		String keywordLine = sLine.nextLine().trim(); //좌우 공백 제거
		String cleanKeyword = keywordLine.replaceAll("\\s+", "");// 중간 공백도 제거.
		System.out.println("입력값 확인: [" + keywordLine + "]");

		if (cleanKeyword.isEmpty()) {
		    System.out.println("검색어를 입력해주세요.");
		    return;
		}
		
		
		String sql = "SELECT book_id, title, author, publisher FROM book_info WHERE"
				+ " book_id like ? or REPLACE(title, ' ', '') like ? or "
				+ " REPLACE(author, ' ','') like ? or publisher like ? or "
				+ " category_code IN (select category_code from book_category where category_name like ?)";
		
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			String searchKeyword = "%" + cleanKeyword + "%";
			
			pstmt.setString(1, searchKeyword);
			pstmt.setString(2, searchKeyword);
			pstmt.setString(3, searchKeyword);
			pstmt.setString(4, searchKeyword);
			pstmt.setString(5, searchKeyword);
			
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				BookInfoDTO dto = new BookInfoDTO();
                dto.setBook_id(rs.getString("book_id")); // DTO의 setter 이름에 맞춰 수정
                dto.setTitle(rs.getString("title"));
                dto.setAuthor(rs.getString("author"));
                dto.setPublisher(rs.getString("publisher"));
                
                BookInfoList.add(dto);
			}
		}
		
		if(BookInfoList.isEmpty()) {
			System.out.println("일치하는 도서가 없습니다");
			return;
		}

	//대상 도서 확정
    //if (BookInfoList.size() == 1) {
    //    this.BookInfoDTO = BookInfoList.get(0);
    //} else {
        System.out.println("===== 검색 결과 =====");
	    for (int i = 0; i < BookInfoList.size(); i++) {
	        BookInfoDTO book = BookInfoList.get(i);
	        System.out.println("검색 번호 : [ " + (i + 1) + " ] . [" + book.getBook_id() + "] 제목 : " 
	        + book.getTitle() + " | 저자 : " + book.getAuthor() +" | 출판사 : " + book.getPublisher());
	    }

		System.out.print("대출할 검색 번호를 선택하세요 예 : 1, 2 >> ");
		int choice = 0;

		try {
		    choice = s.nextInt(); 
		} catch (InputMismatchException e) {
		    System.out.println("숫자만 입력해야 합니다.");
		    s.nextLine(); // 숫자 스캐너 버퍼에 남은 잘못된 문자 제거
		    //e.printStackTrace();
		    return;
		}

		if (choice < 1 || choice > BookInfoList.size()) {
		    System.out.println("잘못된 번호입니다.");
		    return;
		}
        BookInfoDTO = BookInfoList.get(choice - 1);
    //}

     
    // 실물 도서가 대출 가능한 상태인지 먼저 확인 및 배정
	    String p_Sql = "SELECT p.physical_book_id "
	            + "FROM physical_book p "
	            + "WHERE p.book_id = ? "
	            + "  AND (SELECT COUNT(*) "
	            + "       FROM book_loan l "
	            + "       WHERE l.physical_book_id = p.physical_book_id) = 0 "
	            + "  AND ROWNUM = 1";
	
	    String physical_book_id;  
	    
	    try (PreparedStatement pstmt = conn.prepareStatement(p_Sql)) {
	        pstmt.setString(1, BookInfoDTO.getBook_id());
	
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                physical_book_id = rs.getString("physical_book_id");
	            } else {
	                System.out.println("[" + BookInfoDTO.getTitle() + "] 도서는 현재 모두 대출 중이거나 보유 재고가 없습니다.");
	                return;
	            }
	        }
	    }
	
	    //실물 책 확보가 끝난 뒤에 최종 대출 여부 확인
	    System.out.println("대출 가능 도서 : [" + BookInfoDTO.getBook_id() + "] " + BookInfoDTO.getTitle());
	    System.out.print("해당 책을 빌리시겠습니까? (y/n) : ");
	    String answer = s.next();
	
	    if (!answer.equalsIgnoreCase("y")) {
	        System.out.println("대출이 취소되었습니다.");
	        return;
	    }
	
	    //  book_loan 테이블에 대출 기록 저장 (현재 일자: SYSDATE, 반납 예정일: SYSDATE + 7)
	    //현재 아이디를 기반으로 맴버넘버를 찾는 sql문
	    String currentUserId = AccountController.getUserID();
	    String memberFindSql = "select member_number from member where user_id = ?";
	    int mem_no = -1 ;
	    
	    String insertSql = "INSERT INTO book_loan (physical_book_id, member_number, loan_date, return_date) "
	            			+ "VALUES (?, ?, SYSDATE, SYSDATE + 7)";
	    
	    try (PreparedStatement pstmt = conn.prepareStatement(memberFindSql)) {
	        pstmt.setString(1, currentUserId);
	
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	        	mem_no = rs.getInt("member_number");
	        } else {
	            System.out.println("로그인/맴버 db접속 오류");
	            return;
	        }
	    	if(mem_no == -1) {
	            System.out.println("로그인/맴버 정보 오류");
	            return;}
	    }
	
	    try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
	        pstmt.setString(1, physical_book_id);
	        pstmt.setInt(2, mem_no);
	        
	        int result = pstmt.executeUpdate();
	        if (result > 0) {
	            System.out.println("대출이 완료되었습니다! (실물 도서 ID : " + physical_book_id + ", 대출 기간 : 7일)");
	        } else {
	            System.out.println("대출 처리 중 오류가 발생했습니다. 회원 정보를 확인해주세요.");
	        	}
	    }
    
	}
	
	

	// 도서 반납 프로세스 (delete 방식)--------------------------------------------------
	private void return_Process(Connection conn) throws SQLException {
		
		//유저 id로 현재 맴버넘버를 찾는 sql
	    String currentUserId = AccountController.getUserID();
	    String memberFindSql = "select member_number from member where user_id = ?";
	    int mem_no = -1 ;
	    
	    try (PreparedStatement pstmt = conn.prepareStatement(memberFindSql)) {
	        pstmt.setString(1, currentUserId);
	
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	        	mem_no = rs.getInt("member_number");
	        } else {
	            System.out.println("로그인/맴버 db접속 오류");
	            return;
	        }
	    	if(mem_no == -1) {
	            System.out.println("로그인/맴버 정보 오류");
	            return;}
	    }
	    
	    // 현재 로그인한 회원의 대출 목록 조회
	    String listSql = "SELECT l.physical_book_id, b.title, "
	            + "       TO_CHAR(l.loan_date, 'YYYY-MM-DD') AS loan_dt, "
	            + "       TO_CHAR(l.return_date, 'YYYY-MM-DD') AS return_dt "
	            + "FROM book_loan l "
	            + "JOIN physical_book p ON l.physical_book_id = p.physical_book_id "
	            + "JOIN book_info b ON p.book_id = b.book_id "
	            + "WHERE l.member_number = ? "
	            + "ORDER BY l.loan_date";
	    
	    // 내가 대출한 리스트
	    List<String[]> loanedList = new ArrayList<>();
	    
	    // 내가 대출한 리스트 연채상태만.
	    List<String[]> loanedOverdueList = new ArrayList<>();
	    
	    try (PreparedStatement pstmt = conn.prepareStatement(listSql)) {
	        pstmt.setInt(1, mem_no);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
                    String rd = rs.getString("return_dt");
        	        LocalDate dueDate = LocalDate.parse(rd);
        	        LocalDate today = LocalDate.now(); 
	            	
        	        int overdueDays = (int) ChronoUnit.DAYS.between(dueDate, today);
        	        if(overdueDays > 0) {
        	        		loanedOverdueList.add(new String[] {
    		                rs.getString("physical_book_id"),
    		                rs.getString("title"),
    		                rs.getString("loan_dt"),
    		                rs.getString("return_dt")
    		                });
        	        }else {
		                	loanedList.add(new String[] {
		                    rs.getString("physical_book_id"),
		                    rs.getString("title"),
		                    rs.getString("loan_dt"),
		                    rs.getString("return_dt")
	                });
        	        }
	            }
	        }
	    }
	
	    if (loanedList.isEmpty() || loanedOverdueList.isEmpty() ) {
	        System.out.println("현재 대출 중인 도서가 없습니다.");
	        return;
	    }
	    
	    
	    int j=1;
	    System.out.println("===== 현재 연채 도서 목록 =====");
	    for (int i = 0; i < loanedOverdueList.size(); i++) {
	        String[] item = loanedOverdueList.get(i);
	        System.out.println("번호 : [ " + j + " ] [" + item[0] + "] " + item[1]
	                + " (대출일: " + item[2] + " / 반납예정일: " + item[3] + ")");
	        j = j+1;
	    }
	    
	    System.out.println("===== 현재 대출 중인 도서 목록 =====");
	    for (int i = 0; i < loanedList.size(); i++) {
	        String[] item = loanedList.get(i);
		        System.out.println("번호 : [ " + j + " ] [" + item[0] + "] " + item[1]
		                + " (대출일: " + item[2] + " / 반납예정일: " + item[3] + ")");
		        j = j+1;
	    }
	    
	    //연채 리스트와 일반 대출 리스트 하나로 합침.
	    loanedOverdueList.addAll(loanedList);
	    
	    System.out.print("반납할 번호를 선택하세요 >> ");
	    int choice = s.nextInt();
	    if (choice < 1 || choice > loanedOverdueList.size()) {
	        System.out.println("잘못된 번호입니다.");
	        return;
	    }
	
	    String PhysicalId = loanedOverdueList.get(choice - 1)[0];
	    String Title = loanedOverdueList.get(choice - 1)[1];
	
	    String deleteSql = "DELETE FROM book_loan WHERE physical_book_id = ? AND member_number = ?";
	    try (PreparedStatement pstmt = conn.prepareStatement(deleteSql)) {
	        pstmt.setString(1, PhysicalId);
	        pstmt.setInt(2, mem_no);
	
	        int result = pstmt.executeUpdate();
	        if (result > 0) {
	            System.out.println("[" + Title + "] 도서가 정상적으로 반납되었습니다.");
	        } else {
	            System.out.println("반납 처리에 실패했습니다.");
	        }
	    }
	}

}


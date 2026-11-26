package com.library.loan;

public class BookLoanService {
	// 사용 예시 저장
	private LoanController loanController = new LoanController(); // LoanDAO 인스턴스 생성


		public void Loan() {
			//대출 매서드 호출
			loanController.execute("loan");
			
			//반납 매서드 호출
			loanController.execute("book_return");

	}
	
}

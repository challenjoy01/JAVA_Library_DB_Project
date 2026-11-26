package com.library.loan;

public class PhysicalBookDTO {
	private String physical_book_id;
	private int member_number;
	private String loan_date;
	private String return_date;
	
	public PhysicalBookDTO() {
    }

	public String getPhysical_book_id() {
		return physical_book_id;
	}

	public void setPhysical_book_id(String physical_book_id) {
		this.physical_book_id = physical_book_id;
		
	}

	public int getMember_number() {
		return member_number;
	}

	public void setMember_number(int member_number) {
		this.member_number = member_number;
	}

	public String getLoan_date() {
		return loan_date;
	}

	public void setLoan_date(String loan_date) {
		this.loan_date = loan_date;
	}

	public String getReturn_date() {
		return return_date;
	}

	public void setReturn_date(String return_date) {
		this.return_date = return_date;
	}

}

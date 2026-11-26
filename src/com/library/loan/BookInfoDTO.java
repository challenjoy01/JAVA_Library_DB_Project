package com.library.loan;

public class BookInfoDTO {
    private String book_id; 
    private String title;
    private String author;
    private String publisher;
    private String category_code; 
    
    public BookInfoDTO() {
    	
    }
    
    public String getBook_id() {
		return book_id;
	}
	public void setBook_id(String book_id) {
		this.book_id = book_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getCategory_code() {
		return category_code;
	}
	public void setCategory_code(String category_code) {
		this.category_code = category_code;
	}
	public int getSeries_info() {
		return series_info;
	}
	public void setSeries_info(int series_info) {
		this.series_info = series_info;
	}
	private int series_info;

}

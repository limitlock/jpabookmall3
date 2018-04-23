package com.cafe24.jpabookmall.dto;

public class BookDTO {

	private Long no;
	private String title;

	public BookDTO() {

	}

	public BookDTO(Long no, String title) {
		this.no = no;
		this.title = title;
	}

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "BookDTO [no=" + no + ", title=" + title + "]";
	}

}

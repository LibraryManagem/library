package com.domain;
//采购表
public class Buy {
	private String buy_isbn;
	private int buy_num;
	private String buy_bookname;
	private String buy_press;//出版社
	private String buy_author;
	public String getBuy_isbn() {
		return buy_isbn;
	}
	public void setBuy_isbn(String buy_isbn) {
		this.buy_isbn = buy_isbn;
	}
	public int getBuy_num() {
		return buy_num;
	}
	public void setBuy_num(int buy_num) {
		this.buy_num = buy_num;
	}
	public String getBuy_bookname() {
		return buy_bookname;
	}
	public void setBuy_bookname(String buy_bookname) {
		this.buy_bookname = buy_bookname;
	}
	public String getBuy_press() {
		return buy_press;
	}
	public void setBuy_press(String buy_press) {
		this.buy_press = buy_press;
	}
	public String getBuy_author() {
		return buy_author;
	}
	public void setBuy_author(String buy_author) {
		this.buy_author = buy_author;
	}

}

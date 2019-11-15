package com.domain;
//待上架表
public class Wait {

	private  String wait_isbn;
	private  int wait_num;//需要上架多少本
	private  String wait_bookname;
	private  String wait_press;//出版社
	private  String wait_author;
	public String getWait_isbn() {
		return wait_isbn;
	}
	public void setWait_isbn(String wait_isbn) {
		this.wait_isbn = wait_isbn;
	}
	public int getWait_num() {
		return wait_num;
	}
	public void setWait_num(int wait_num) {
		this.wait_num = wait_num;
	}
	public String getWait_bookname() {
		return wait_bookname;
	}
	public void setWait_bookname(String wait_bookname) {
		this.wait_bookname = wait_bookname;
	}
	public String getWait_press() {
		return wait_press;
	}
	public void setWait_press(String wait_press) {
		this.wait_press = wait_press;
	}
	public String getWait_author() {
		return wait_author;
	}
	public void setWait_author(String wait_author) {
		this.wait_author = wait_author;
	}
	
}

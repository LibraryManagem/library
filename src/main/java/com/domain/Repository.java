package com.domain;

public class Repository {

	private String repository_isbn;
	private int repository_sum;//总数
	private int repository_remain;//剩余
	private String repository_local;
	public String getRepository_isbn() {
		return repository_isbn;
	}
	public void setRepository_isbn(String repository_isbn) {
		this.repository_isbn = repository_isbn;
	}
	public int getRepository_sum() {
		return repository_sum;
	}
	public void setRepository_sum(int repository_sum) {
		this.repository_sum = repository_sum;
	}
	public int getRepository_remain() {
		return repository_remain;
	}
	public void setRepository_remain(int repository_remain) {
		this.repository_remain = repository_remain;
	}
	public String getRepository_local() {
		return repository_local;
	}
	public void setRepository_local(String repository_local) {
		this.repository_local = repository_local;
	}
	
}

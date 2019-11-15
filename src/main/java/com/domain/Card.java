package com.domain;

import java.util.Date;

public class Card {

	private int card_id;
	private int card_num;//借书的数量
	private Date card_date;
	private double card_balance;//余额
	private int card_score;//积分
	public int getCard_id() {
		return card_id;
	}
	public void setCard_id(int card_id) {
		this.card_id = card_id;
	}
	public int getCard_num() {
		return card_num;
	}
	public void setCard_num(int card_num) {
		this.card_num = card_num;
	}
	public Date getCard_date() {
		return card_date;
	}
	public void setCard_date(Date card_date) {
		this.card_date = card_date;
	}
	public double getCard_balance() {
		return card_balance;
	}
	public void setCard_balance(double card_balance) {
		this.card_balance = card_balance;
	}
	public int getCard_score() {
		return card_score;
	}
	public void setCard_score(int card_score) {
		this.card_score = card_score;
	}
	
}

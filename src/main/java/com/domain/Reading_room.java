package com.domain;

import java.util.Date;

public class Reading_room {

	private int seat_num;
	private int seat_remain;//剩余可预约座位
	private Date seat_time;
	public int getSeat_num() {
		return seat_num;
	}
	public void setSeat_num(int seat_num) {
		this.seat_num = seat_num;
	}
	public int getSeat_remain() {
		return seat_remain;
	}
	public void setSeat_remain(int seat_remain) {
		this.seat_remain = seat_remain;
	}
	public Date getSeat_time() {
		return seat_time;
	}
	public void setSeat_time(Date seat_time) {
		this.seat_time = seat_time;
	}
	
}

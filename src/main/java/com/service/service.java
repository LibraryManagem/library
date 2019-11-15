package com.service;

import com.dao.LendDao;

public class service {

	private LendDao lendao;
	public void set(LendDao lendao) {
		this.lendao=lendao;
	}
	
	public void ad() {
		lendao.add();
	}
}

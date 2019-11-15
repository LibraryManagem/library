package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.LendDao;
@Service
public class service {
@Autowired
	private LendDao lendao;
	public void setLenDao(LendDao lendao) {
		this.lendao=lendao;
	}
	
	public void ad() {
		lendao.add();
	}
}

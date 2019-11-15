package com.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public class LendDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void add() {
		// String sql="insert into buy(root,root);";
	
	
		jdbcTemplate.update("insert into buy(buy_isbn,buy_num,buy_bookname,buy_press,buy_author)value(?,?,?,?,?);", "abc456", "90", "aaa", "bbb", "ccc");
	}

	
}

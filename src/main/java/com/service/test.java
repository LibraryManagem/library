package com.service;

import javax.swing.text.AbstractDocument.Content;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test {
@Test
	public void jdbcTest() {
		ApplicationContext ac=new ClassPathXmlApplicationContext("resources/LibraryContext.xml");
	service ser=(service)ac.getBean("service");
	ser.ad();
	}
}

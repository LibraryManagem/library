package com.service;

import java.util.Arrays;

import javax.swing.text.AbstractDocument.Content;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test {
@Test
	public void jdbcTest() {
		ApplicationContext ac=new ClassPathXmlApplicationContext("LibraryContext.xml");
		System.out.println("Bean names: " + Arrays.toString(ac.getBeanNamesForType(service.class)));
	service ser=(service)ac.getBean("service");
	ser.ad();
	}
}

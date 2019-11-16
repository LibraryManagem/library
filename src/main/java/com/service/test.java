package com.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dao.*;
import com.domain.Book;
public class test {
	private ApplicationContext ioc=new ClassPathXmlApplicationContext("LibraryContext.xml");
	
	@Test
	public void test() {
		Book book=new Book();
		book.setBook_id(200);
		BookDao bd= (BookDao) ioc.getBean("bookDao");
		System.out.println(bd.addBook(book));
	}

}

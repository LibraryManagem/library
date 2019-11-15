package com.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.dao.BookDao;
import com.domain.Book;

public class Book_Service {

	private BookDao bookDao;
	
	@Autowired
    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }
	//返回匹配的书数
	public int countMatchedBooks(String searchWord) {
		return bookDao.countMatchedBooks(searchWord);
	}
	//用户查询图书
	public ArrayList<Book> searchBooks(String searchWord) {
		return bookDao.searchBooks(searchWord);
	}
	public boolean addBook(Book book) {//上架
		return bookDao.addBook(book) > 0;
	}
	public int deleteBook(long bookId) {//下架
		return bookDao.deletebook(bookId);
	}
	public boolean editBook(Book book) {//修改图书信息
		return bookDao.editBook(book) > 0;
	}
	public ArrayList<Book> getAllBooks(){//获取所有图书
        return bookDao.getAllBooks();
    }
	public Book getBook(long bookId) {//由id精确查找某本图书
		Book book = bookDao.getBook(bookId);
		return book;
	}
	public void buyBooks() {//买书进入待上架表
		//需统一采购表、待上架表、book表属性
	}
	
}
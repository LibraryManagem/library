package com.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.BookDao;
import com.domain.Book;
import com.domain.Buy;
import com.domain.Repo;
import com.domain.Wait;

@Service
public class Book_Service {

	private BookDao bookDao;
	
	@Autowired
    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }
	//返回匹配的书数
	public boolean matchBook(String searchWord) {
		return bookDao.countMatchedBooks(searchWord) > 0;
	}
	//用户查询图书
	public ArrayList<Book> searchBook(String searchWord) {
		return bookDao.searchBooks(searchWord);
	}
	public boolean addBook(Book book) {//上架
		return bookDao.addBook(book) > 0;
	}
	public int deleteBook(int bookId) {//下架
		return bookDao.deletebook(bookId);
	}
	public boolean editBook(Book book) {//修改图书信息
		return bookDao.editBook(book) > 0;
	}
	public ArrayList<Book> getAllBooks(){//获取所有图书
        return bookDao.getAllBooks();
    }
	public Book getBook(int bookId) {//由id精确查找某本图书
		Book book = bookDao.getBook(bookId);
		return book;
	}
	public boolean buyBook(Book book, int amount) {//买书进入待上架表
		
		return bookDao.buyBook(book, amount) > 0;
	}
	public boolean addToWait(int bookId, int amount) {
		return bookDao.addToWait(bookId, amount) > 0;
	}
	public boolean addToRepository(int bookId, int amount) {
		
		return bookDao.addToReppository(bookId, amount) > 0;
	}
	
	public ArrayList<Buy> getBuyList(){//获取所有图书
        return bookDao.getBuyList();
    }
	public ArrayList<Wait> getWaitList(){//获取所有图书
        return bookDao.getWaitList();
    }
	public ArrayList<Repo> getRepoList(){//获取所有图书
        return bookDao.getRepositoryList();
    }
}

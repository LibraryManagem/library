package com.dao;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.domain.Book;

@Repository
public class BookDao {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	private final static String ADD_BOOK_SQL = "INSERT INTO book VALUES(?,?,?,?,?,?,?,?,?)";
	private final static String DELETE_BOOK_SQL = "DELETE FROM book WHERE id = ? ";
	private final static String EDIT_BOOK_SQL = "UPDATE book set name= ? ,id= ? ,price= ? ,publish_date= ? ,press= ? ,author= ? ,lv= ? ,type= ? ,isbn= ? ";
	private final static String QUERY_ALL_BOOKS_SQL = "SELECT * FROM book";
	//按照书名或者id模糊查询
	private final static String QUERY_BOOK_SQL = "SELECT * FROM book WHERE name like ? or id like ? ";
	//匹配到的结果的个数
	private final static String COUNT_MATCHED_SQL = "SELECT COUNT(*) FROM book WHERE name like ? or id like ? ";
	//根据书号精确匹配图书
	private final static String GET_BOOK_BY_ID_SQL = "SELECT * FROM book WHERE id = ? ";
	//将要采购的书加入buy表中
	private final static String ADD_BOOK_TO_BUY_LIST = "INSERT INTO book VALUES(?,?,?,?,?,?,?,?,?,?)";
	//将buy表中选择的条目加入wait表中
	private final static String GET_BOOK_FROM_BUY_LIST = "SELECT * FROM buy WHERE id = ? ";
	private final static String GET_AMOUNT_FROM_BUY_LIST = "SELECT amount FROM buy WHERE id = ? ";
	private final static String ADD_BOOK_TO_WAIT_LIST = "INSERT INTO wait VALUES(?,?,?,?,?,?,?,?,?,?)";
	
	public int countMatchedBooks(String searchWord) {
		//拼接成模糊查询字符串
		String new_sw = "%" + searchWord + "%";
		return jdbcTemplate.queryForObject(COUNT_MATCHED_SQL, new Object[] {new_sw,new_sw}, Integer.class);
	}
	
	public ArrayList<Book> searchBooks(String searchWord) {
		//拼接成模糊查询字符串
		String new_sw = "%" + searchWord + "%";
		ArrayList<Book> books = (ArrayList<Book>) jdbcTemplate.query(QUERY_BOOK_SQL, new Object[] {new_sw, new_sw} , new BeanPropertyRowMapper(Book.class));
		return books;
	}
	
	public int addBook(Book book) {
		String name = book.getBook_name();
		int id = book.getBook_id();
		double price = book.getBook_price();
		Date pubDate = book.getBook_publish_date();
		String press = book.getBook_press();
		String author = book.getBook_author();
		int lv = book.getBook_lv();
		String type = book.getBook_type();
		String isbn = book.getBook_isbn();
		
		return jdbcTemplate.update(ADD_BOOK_SQL, 
				new Object[] {name, id, price, pubDate, press, author, lv, type, isbn});
	}
	
	public int deletebook(long bookId) {
		return jdbcTemplate.update(DELETE_BOOK_SQL, bookId);
	}
	
	public int editBook(Book book) {
		String name = book.getBook_name();
		int id = book.getBook_id();
		double price = book.getBook_price();
		Date pubDate = book.getBook_publish_date();
		String press = book.getBook_press();
		String author = book.getBook_author();
		int lv = book.getBook_lv();
		String type = book.getBook_type();
		String isbn = book.getBook_isbn();
	
		return jdbcTemplate.update(EDIT_BOOK_SQL, 
				new Object[] {name, id, price, pubDate, press, author, lv, type, isbn});
	}
	
	public ArrayList<Book> getAllBooks() {
		ArrayList<Book> books = (ArrayList<Book>) jdbcTemplate.query(QUERY_ALL_BOOKS_SQL, new BeanPropertyRowMapper(Book.class));
		return books;
	}
	
	public Book getBook(long bookId) {
		return (Book) jdbcTemplate.queryForObject(GET_BOOK_BY_ID_SQL, new Object[] {bookId}, new BeanPropertyRowMapper(Book.class));
	}
	
	public int buyBook(Book book, int amount) {
		String name = book.getBook_name();
		int id = book.getBook_id();
		double price = book.getBook_price();
		Date pubDate = book.getBook_publish_date();
		String press = book.getBook_press();
		String author = book.getBook_author();
		int lv = book.getBook_lv();
		String type = book.getBook_type();
		String isbn = book.getBook_isbn();
		
		return jdbcTemplate.update(ADD_BOOK_TO_BUY_LIST, 
				new Object[] {name, id, price, pubDate, press, author, lv, type, isbn, amount});
	}
	
	public int addToWait(long bookId) {
		
		Book book = (Book)jdbcTemplate.query(GET_BOOK_FROM_BUY_LIST, new Object[] {bookId}, new BeanPropertyRowMapper(Book.class));
		String name = book.getBook_name();
		int id = book.getBook_id();
		double price = book.getBook_price();
		Date pubDate = book.getBook_publish_date();
		String press = book.getBook_press();
		String author = book.getBook_author();
		int lv = book.getBook_lv();
		String type = book.getBook_type();
		String isbn = book.getBook_isbn();
		int amount = jdbcTemplate.queryForObject(GET_AMOUNT_FROM_BUY_LIST, new Object[] {bookId}, Integer.class);
		
		return jdbcTemplate.update(ADD_BOOK_TO_WAIT_LIST, 
				new Object[] {name, id, price, pubDate, press, author, lv, type, isbn, amount});
	}
	
	
}

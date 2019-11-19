package com.dao;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.domain.Book;
import com.domain.Buy;
import com.domain.Repo;
import com.domain.Wait;

@Repository
public class BookDao {

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	private final static String ADD_BOOK_SQL = "INSERT INTO book VALUES(?,NULL,?,?,?,?,?,?,?)";
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
	//从wait表上架至仓库
	private final static String GET_BOOK_FROM_WAIT_LIST = "SELECT * FROM wait WHERE id = ? ";
	private final static String GET_AMOUNT_FROM_WAIT_LIST = "SELECT amount FROM wait WHERE id = ? ";
	private final static String ADD_BOOK_TO_REPOSITORY = "INSERT INTO repository VALUES(?,?,?,?,?,?,?,?,?,?)";
	//获取buy表的所有条目
	private final static String QUERY_ALL_FROM_BUY = "SELECT * FROM buy";
	private final static String QUERY_ALL_FROM_WAIT = "SELECT * FROM buy";
	private final static String QUERY_ALL_FROM_REPOSITORY = "SELECT * FROM buy";
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
		double price = book.getBook_price();
		Date pubDate = book.getBook_publish_date();
		String press = book.getBook_press();
		String author = book.getBook_author();
		int lv = book.getBook_lv();
		String type = book.getBook_type();
		String isbn = book.getBook_isbn();
		
		return jdbcTemplate.update(ADD_BOOK_SQL, 
				new Object[] {name, price, pubDate, press, author, lv, type, isbn});
	}
	
	public int deletebook(int bookId) {
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
	
	public Book getBook(int bookId) {
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
				new Object[] {name, price, pubDate, press, author, lv, type, isbn, amount});
	}
	
	public int addToWait(int bookId, int amount) {
		
		int succ = 0;
		String amountInBuy = "select amount in buy where id = ?";
		String modifyAmountInBuy = "update buy set amount = ? where id = ?";
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
		//查询buy表中的amount
		int amount_buy = jdbcTemplate.queryForObject(amountInBuy, new Object[] {bookId}, Integer.class);
		//如果buy表的amount比参数中的amount大，则将其添加至wait表
		if ((amount_buy - amount) > 0) {
			succ =  jdbcTemplate.update(ADD_BOOK_TO_WAIT_LIST, 
					new Object[] {name, id, price, pubDate, press, author, lv, type, isbn, amount});
		} else {
			return 0;
		}
		//若添加成功，则将buy表的amount减去相应数目
		if (succ > 0) {
			return jdbcTemplate.update(modifyAmountInBuy, new Object[] {amount_buy - amount,bookId});
		} else {
			return 0;
		}
		
	}
	
	public int addToReppository(int bookId,int amount) {
		int succ = 0;
		String amountInWait = "select amount in wait where id = ?";
		String modifyAmountInWait = "update wait set amount = ? where id = ?";
		Book book = (Book)jdbcTemplate.query(GET_BOOK_FROM_WAIT_LIST, new Object[] {bookId}, new BeanPropertyRowMapper(Book.class));
		String name = book.getBook_name();
		int id = book.getBook_id();
		double price = book.getBook_price();
		Date pubDate = book.getBook_publish_date();
		String press = book.getBook_press();
		String author = book.getBook_author();
		int lv = book.getBook_lv();
		String type = book.getBook_type();
		String isbn = book.getBook_isbn();
		//查询wait表中的amount
		int amount_wait = jdbcTemplate.queryForObject(amountInWait, new Object[] {bookId}, Integer.class);
		//如果wait表的amount比参数中的amount大，则将其添加至repository表
		if ((amount_wait - amount) > 0) {
			succ =  jdbcTemplate.update(ADD_BOOK_TO_REPOSITORY, 
					new Object[] {name, id, price, pubDate, press, author, lv, type, isbn, amount});
		} else {
			return 0;
		}
		//若添加成功，则将wait表的amount减去相应数目
		if (succ > 0) {
			return jdbcTemplate.update(modifyAmountInWait, new Object[] {amount_wait - amount,bookId});
		} else {
			return 0;
		}
	}
	
	public ArrayList<Buy> getBuyList() {
		ArrayList<Buy> buys = (ArrayList<Buy>) jdbcTemplate.query(QUERY_ALL_FROM_BUY, new BeanPropertyRowMapper(Buy.class));
		return buys;
	}
	
	public ArrayList<Wait> getWaitList() {
		ArrayList<Wait> waits = (ArrayList<Wait>) jdbcTemplate.query(QUERY_ALL_FROM_WAIT, new BeanPropertyRowMapper(Wait.class));
		return waits;
	}
	
	public ArrayList<Repo> getRepositoryList() {
		ArrayList<Repo> repos = (ArrayList<Repo>) jdbcTemplate.query(QUERY_ALL_FROM_REPOSITORY, new BeanPropertyRowMapper(Repo.class));
		return repos;
	}
}

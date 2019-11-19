package com.web;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.domain.Book;
import com.domain.Buy;
import com.domain.Repo;
import com.domain.Wait;
import com.service.Book_Service;

@Controller
public class BookController {
	private Book_Service book_Service;

	@Autowired
	public void setBook_Service(Book_Service book_Service) {
		this.book_Service = book_Service;
	}
	
	@RequestMapping("/querybook.html")
	public ModelAndView queryBookDo(HttpServletRequest request, String searchWord) {
		boolean exist = book_Service.matchBook(searchWord);
		if (exist) {
			ArrayList<Book> books = book_Service.searchBook(searchWord);
			ModelAndView modelAndView = new ModelAndView("admin_books");
			modelAndView.addObject("books",books);
			return modelAndView;
		} else {
			return new ModelAndView("admin_books","error","没有匹配的图书");
		}
	}
	
	@RequestMapping("/reader_querybook.html")
	public ModelAndView readerquerybook() {
		return new ModelAndView("reader_book_query");
	}
	
	@RequestMapping("/reader_querybook_do.html")
	public String readerQueryBookDo(HttpServletRequest request, String searchWord, RedirectAttributes redirectAttributes) {
		boolean exist = book_Service.matchBook(searchWord);
		if (exist) {
			ArrayList<Book> books = book_Service.searchBook(searchWord);
			redirectAttributes.addFlashAttribute("books", books);
			return "redirect:/reader_querybook.html";
		} else {
			redirectAttributes.addFlashAttribute("error", "没有匹配的图书！");
			return "redirect:/reader_querybook.html";
		}
	}
	
	@RequestMapping("/allbooks.html")
	public ModelAndView allBook(){
        ArrayList<Book> books=book_Service.getAllBooks();
        ModelAndView modelAndView=new ModelAndView("admin_books");
        modelAndView.addObject("books",books);
        return modelAndView;
    }
	
	@RequestMapping("/deletebook.html")
    public String deleteBook(HttpServletRequest request,RedirectAttributes redirectAttributes){
        int bookId=Integer.parseInt(request.getParameter("bookId"));
        int res=book_Service.deleteBook(bookId);

        if (res==1){
            redirectAttributes.addFlashAttribute("succ", "图书删除成功！");
            return "redirect:/allbooks.html";
        }else {
            redirectAttributes.addFlashAttribute("error", "图书删除失败！");
            return "redirect:/allbooks.html";
        }
    }
	
	@RequestMapping("/book_add.html")
	public ModelAndView addBook(HttpServletRequest request)	{
		return new ModelAndView("admin_book_add");
	}
	
	@RequestMapping("/book_add_do.html")
	public String addBookDo(BookAddCommand bookAddCommand, RedirectAttributes redirectAttributes) {
		Book book = new Book();
		book.setBook_name(bookAddCommand.getName());
		book.setBook_id(0);
		book.setBook_price(bookAddCommand.getPrice());
		book.setBook_publish_date(bookAddCommand.getPubDate());
		book.setBook_press(bookAddCommand.getPress());
		book.setBook_author(bookAddCommand.getAuthor());
		book.setBook_lv(bookAddCommand.getLv());
		book.setBook_type(bookAddCommand.getType());
		book.setBook_isbn(bookAddCommand.getIsbn());
		
		boolean succ = book_Service.addBook(book);
		ArrayList<Book> books = book_Service.getAllBooks();
		if (succ) {
			redirectAttributes.addFlashAttribute("succ", "图书添加成功！");
			return "redirect:/allbooks.html";
		} else {
			redirectAttributes.addFlashAttribute("error", "图书添加失败！");
			return "redirect:/allbooks.html";
		}
	}
	
	@RequestMapping("/book_edit.html")
	public ModelAndView bookEdit(HttpServletRequest request) {
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		Book book = book_Service.getBook(bookId);
		ModelAndView modelAndView = new ModelAndView("admin_book_edit");
		modelAndView.addObject("detail.book");
		return modelAndView;
	}
	
	@RequestMapping("/book_edit_do.html")
	public String bookEditDo(HttpServletRequest request, BookAddCommand bookAddCommand, RedirectAttributes redirectAttributes) {
		int bookId = Integer.parseInt(request.getParameter("id"));
		Book book = new Book();
		book.setBook_name(bookAddCommand.getName());
		book.setBook_id(bookId);
		book.setBook_price(bookAddCommand.getPrice());
		book.setBook_publish_date(bookAddCommand.getPubDate());
		book.setBook_press(bookAddCommand.getPress());
		book.setBook_author(bookAddCommand.getAuthor());
		book.setBook_lv(bookAddCommand.getLv());
		book.setBook_type(bookAddCommand.getType());
		book.setBook_isbn(bookAddCommand.getIsbn());
		
		boolean succ = book_Service.editBook(book);
		if (succ) {
			redirectAttributes.addFlashAttribute("succ", "图书修改成功！");
			return "redirect:/allbooks.html";
		} else {
			redirectAttributes.addFlashAttribute("error", "图书修改失败！");
			return "redirect:/allbooks.html";
		}
	}
	
	@RequestMapping("/bookdetail.html")
	public ModelAndView bookDetail(HttpServletRequest request) {
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		Book book = book_Service.getBook(bookId);
		ModelAndView modelAndView = new ModelAndView("admin_book_detail");
		modelAndView.addObject("detail", book);
		return modelAndView;
	}
	
	@RequestMapping("/readerbookdetail.html")
	public ModelAndView readerBookDetail(HttpServletRequest request) {
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		Book book = book_Service.getBook(bookId);
		ModelAndView modelAndView = new ModelAndView("reader_book_detail");
		modelAndView.addObject("detail", book);
		return modelAndView;
	}
	
	@RequestMapping("/buybook.html")
	public String buyBook(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		int bookId = Integer.parseInt(request.getParameter("id"));
		int amount = Integer.parseInt(request.getParameter("amount"));
		Book book = book_Service.getBook(bookId);
		
		boolean succ = book_Service.buyBook(book, amount);
		if (succ) {
			redirectAttributes.addFlashAttribute("succ", "采购信息添加成功！");
			return "redirect:/buylist.html";
		} else {
			redirectAttributes.addFlashAttribute("error", "采购信息添加失败！");
			return "redirect:/buylist.html";
		}
	}
	
	@RequestMapping("/addbooktowait.html")
	public String addBookToWait(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		int bookId = Integer.parseInt(request.getParameter("id"));
		int amount = Integer.parseInt(request.getParameter("amount"));
		boolean succ = book_Service.addToWait(bookId, amount);
		if (succ) {
			redirectAttributes.addFlashAttribute("succ", "待上架信息添加成功！");
			return "redirect:/waitlist.html";
		} else {
			redirectAttributes.addFlashAttribute("error", "待上架信息添加失败！");
			return "redirect:/waitlist.html";
		}
	}
	
	@RequestMapping("/addbooktorepository.html")
	public String addBookToRepository(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		int bookId = Integer.parseInt(request.getParameter("id"));
		int amount = Integer.parseInt(request.getParameter("amount"));
		boolean succ = book_Service.addToRepository(bookId, amount);
		if (succ) {
			redirectAttributes.addFlashAttribute("succ", "上架成功！");
			return "redirect:/repolist.html";
		} else {
			redirectAttributes.addFlashAttribute("error", "上架失败！");
			return "redirect:/repolist.html";
		}
	}
	
	@RequestMapping("/buylist.html")
	public ModelAndView buyList(){
        ArrayList<Buy> buys=book_Service.getBuyList();
        ModelAndView modelAndView=new ModelAndView("admin_buylist");
        modelAndView.addObject("buys",buys);
        return modelAndView;
    }
	
	@RequestMapping("/waitlist.html")
	public ModelAndView waitList(){
        ArrayList<Wait> waits=book_Service.getWaitList();
        ModelAndView modelAndView=new ModelAndView("admin_waitlist");
        modelAndView.addObject("waits",waits);
        return modelAndView;
    }
	
	@RequestMapping("/repolist.html")
	public ModelAndView repoList(){
        ArrayList<Repo> repos=book_Service.getRepoList();
        ModelAndView modelAndView=new ModelAndView("admin_repolist");
        modelAndView.addObject("repos",repos);
        return modelAndView;
    }
	
}

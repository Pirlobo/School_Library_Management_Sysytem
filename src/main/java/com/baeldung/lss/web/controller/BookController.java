package com.baeldung.lss.web.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

import org.apache.jasper.tagplugins.jstl.core.If;
import org.cryptacular.bean.PemBasedPublicKeyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.method.P;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.baeldung.lss.book.Authors;
import com.baeldung.lss.book.BookItems;
import com.baeldung.lss.book.Books;
import com.baeldung.lss.book.Orders;
import com.baeldung.lss.book.PO;
import com.baeldung.lss.book.RO;
import com.baeldung.lss.dto.BookDto;
import com.baeldung.lss.dto.BookItemDto;
import com.baeldung.lss.model.Course;
import com.baeldung.lss.model.Teacher;
import com.baeldung.lss.model.User;
import com.baeldung.lss.persistence.BookRepository;
import com.baeldung.lss.persistence.CourseRepository;
import com.baeldung.lss.persistence.TeacherRepository;
import com.baeldung.lss.persistence.UserRepository;
import com.baeldung.lss.service.BookItemService;
import com.baeldung.lss.service.CalendarService;
import com.baeldung.lss.service.CourseService;
import com.baeldung.lss.service.IBookService;
import com.baeldung.lss.service.OrderService;
import com.baeldung.lss.service.UserService;
import com.sun.mail.handlers.image_gif;

import net.bytebuddy.asm.Advice.Return;

@Controller
public class BookController {

	@Autowired
	private IBookService bookService;

	@Autowired
	private CourseService courseService;

	@Autowired
	private UserService userService;

	@Autowired
	private BookRepository bookRepository;

	@GetMapping("/searchBook")
	public String getSearchingPage() {
		return "searchingPage.html";
	}

	@GetMapping("/getBook")
	@ResponseBody
	public BookDto getBook() {
		Books books = bookRepository.findById(1).orElse(null);
		BookDto bookDto = new BookDto(books);
		return bookDto;
	}

	@RequestMapping("/searchBookByTitleOrAuthor")
	// @ResponseBody
	public String getBookListByAuthor(Model model,
			@RequestParam("argument") @ModelAttribute("argument") String argument,
			RedirectAttributes redirectAttributes) {
		Set<Books> bookSet = bookService.getBookByTitleOrAuthor(argument);

		if (bookSet.size() == 0) {
			redirectAttributes.addFlashAttribute("error", "Can't find any book with the given name");
			return "redirect:/searchBook";

		} else {
			List<Books> bookList = new ArrayList<>();
			bookSet.forEach(e -> {
				bookList.add(e);
			});
			Collections.reverse(bookList);
			model.addAttribute("bookList", bookList);

			return "bookList.html";
		}

	}

	@RequestMapping("/getAllRequiredBooks")
	public ModelAndView getAllRequiredBooks(Model model, RedirectAttributes redirectAttributes) {
		List<Books> bookList = courseService.getAllRequiredBooks();
		if (bookList.size() == 0) {
			redirectAttributes.addFlashAttribute("error", "You have no required materials");
			return new ModelAndView("redirect:/studentPage");
		}
		model.addAttribute("bookList", bookList);
		return new ModelAndView("bookList.html");
	}

	@RequestMapping("/moreDetails")
	public ModelAndView getBookItem(Model model, RedirectAttributes redirectAttributes,
			@RequestParam("bookItem") @ModelAttribute("bookItem") Integer id) {
		Books book = bookService.findById(id);
		List<Integer> quantity = new ArrayList<Integer>();
		List<BookItems> bookItems = bookService.findBookItems(book);
		model.addAttribute("bookItems", bookItems);
		model.addAttribute("quantity", quantity);
		return new ModelAndView("bookItem.html");
	}

	@GetMapping("/buyBook")
	@ResponseBody
	public Orders buyBook(Model model, RedirectAttributes redirectAttributes) {

		List<BookItemDto> bookItemDto = new ArrayList<BookItemDto>();
		BookItemDto bookItemDto1 = new BookItemDto(1234, false, true, 1);
		BookItemDto bookItemDto2 = new BookItemDto(5678, false, true, 3);
		bookItemDto.add(bookItemDto1);
		bookItemDto.add(bookItemDto2);

		Orders orders = userService.placeOrder(bookItemDto);

		return orders;
	}

}

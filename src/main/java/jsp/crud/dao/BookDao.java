package jsp.crud.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import jsp.crud.entity.Book;
import jsp.crud.exception.IdNotFoundException;
import jsp.crud.exception.NoRecordAvailableException;
import jsp.crud.repository.BookRepository;

@Repository
public class BookDao {

	@Autowired
	private BookRepository bookRepository;

	public Book saveBook(Book book) {
		return bookRepository.save(book);
	}

	public void saveAll(List<Book> books) {

		bookRepository.saveAll(books);
	}

	public Book findById(Integer id) {
		Optional<Book> opt = bookRepository.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		} else {
			throw new IdNotFoundException("Book with id " + id + " not found");
		}
	}

	public List<Book> findAll() {
		List<Book> books = bookRepository.findAll();
		if (!books.isEmpty()) {
			return books;
		} else {
			throw new NoRecordAvailableException("no records are availible in you db");
		}
	}

	public void deleteById(Integer id) {
		if (bookRepository.existsById(id)) {
			bookRepository.deleteById(id);
		} else {
			throw new IdNotFoundException("Cannot delete. Book with id " + id + " not found");
		}

	}

	public List<Book> findByAuthor(String author) {
		List<Book> books = bookRepository.findByAuthor(author);
		if (!books.isEmpty()) {
			return books;
		} else {
			throw new NoRecordAvailableException("The author " + author + " books are not availabe");
		}
	}

	public Book findByTitleAndPublishedYear(String title, Integer year) {
		Optional<Book> books = bookRepository.findByTitleAndPublishedYear(title, year);
		if (books.isPresent()) {
			return books.get();
		} else {
			throw new NoRecordAvailableException("No Books found based on title and year");
		}
	}

	public List<Book> findByPriceGreaterThan(double price) {
		List<Book> books = bookRepository.findByPriceGreaterThan(price);
		if (!books.isEmpty()) {
			return books;
		} else {
			throw new NoRecordAvailableException("No Books found based on this price ");
		}

	}

	public List<Book> findByPriceLessThan(double price) {
		List<Book> books = bookRepository.findByPriceLessThan(price);
		if (!books.isEmpty()) {
			return books;
		} else {
			throw new NoRecordAvailableException("No Books found based on this price ");
		}
	}

	public List<Book> findByPriceBetween(double price, double price1) {
		List<Book> books = bookRepository.findByPriceBetween(price, price1);
		if (!books.isEmpty()) {
			return books;
		} else {
			throw new NoRecordAvailableException("No Books found based on price");
		}
	}

	public List<Book> getBookByAvailability() {
		List<Book> books = bookRepository.getBookByAvailability();
		if (!books.isEmpty()) {
			return books;
		} else {
			throw new NoRecordAvailableException("No Books available");
		}
	}

	public List<Book> getBooksByGenre(String genre) {
		List<Book> books = bookRepository.getBooksByGenre(genre);
		if (!books.isEmpty()) {
			return books;
		}else {
			throw new NoRecordAvailableException("No Books available on this genre");
		}
	}

	public List<Book> getBooksByYear(Integer year) {
		List<Book> books = bookRepository.getBooksByYear(year);
		if (!books.isEmpty()) {
			return books;
		}else {
			throw new NoRecordAvailableException("No Books available on this year " + year);
		}
	}
	
	public Page<Book> getBooksByPagination(Integer pageNumber,Integer pageSize){
		return bookRepository.findAll(PageRequest.of(pageNumber, pageSize));
	}
	
	public List<Book> sortABooksbyDecending(String field){
		List<Book> books=bookRepository.findAll(Sort.by(field).descending());
		if(!books.isEmpty()) {
			return bookRepository.findAll(Sort.by(field).descending());
		}
		else {
			throw new NoRecordAvailableException("No Records Are Availible to sort");
		}
	}
	public Page<Book> getBookswithPaginationandSort(Integer pageNo,Integer pageSize,String field){
		Page<Book> page=bookRepository.findAll(PageRequest.of(pageNo, pageSize,Sort.by(field)));
		if(!page.isEmpty()) {
			return bookRepository.findAll(PageRequest.of(pageNo, pageSize,Sort.by(field).ascending()));
		}
		else {
			throw new NoRecordAvailableException("This page was empty");
		}
	}

}

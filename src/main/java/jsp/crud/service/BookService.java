package jsp.crud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import jsp.crud.dao.BookDao;
import jsp.crud.dto.ResponseStructure;
import jsp.crud.entity.Book;
import jsp.crud.exception.IdNotFoundException;
import jsp.crud.exception.NoRecordAvailableException;

@Service
public class BookService {

	@Autowired
	private BookDao bookDao;

	public ResponseEntity<ResponseStructure<Book>> saveBook(Book book) {
		ResponseStructure<Book> res = new ResponseStructure<>();
		res.setStatusCode(HttpStatus.CREATED.value());
		res.setMessage("Book Record is saved");
		res.setData(bookDao.saveBook(book));

		return new ResponseEntity<>(res, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<String>> saveBooks(List<Book> books) {
		bookDao.saveAll(books);
		ResponseStructure<String> res = new ResponseStructure<>();
		res.setStatusCode(HttpStatus.CREATED.value());
		res.setMessage("Book Records are saved");
		res.setData("Total saved: " + books.size());

		return new ResponseEntity<>(res, HttpStatus.CREATED);
	}

	public ResponseEntity<ResponseStructure<Book>> getBook(Integer id) {
		ResponseStructure<Book> res = new ResponseStructure<>();
		res.setStatusCode(HttpStatus.FOUND.value());
		res.setMessage("Book found for id " + id);
		res.setData(bookDao.findById(id));
		return new ResponseEntity<>(res, HttpStatus.FOUND);

	}

	public ResponseEntity<ResponseStructure<List<Book>>> getBooks() {

		List<Book> book = bookDao.findAll();

		ResponseStructure<List<Book>> res = new ResponseStructure<>();
		res.setStatusCode(HttpStatus.FOUND.value());
		res.setMessage("All books retrieved");
		res.setData(book);
		return new ResponseEntity<>(res, HttpStatus.FOUND);

	}

	public ResponseEntity<ResponseStructure<Book>> updateBook(Book b) {
		ResponseStructure<Book> res = new ResponseStructure<>();

		if (b.getId() == null) {
			res.setStatusCode(HttpStatus.BAD_REQUEST.value());
			res.setMessage("Id must not be null for update");
			return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
		}

		Book book = bookDao.findById(b.getId());
		if (book != null) {
			bookDao.saveBook(b);
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("Successfully updated the record");
			res.setData(b);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} else {
			res.setStatusCode(HttpStatus.NOT_FOUND.value());
			res.setMessage("Record not found, update failed");
			return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<ResponseStructure<String>> deleteBook(Integer id) {
		ResponseStructure<String> res = new ResponseStructure<>();

		res.setStatusCode(HttpStatus.ACCEPTED.value());
		res.setMessage("Book Record deleted");
		res.setData("Deleted book with id: " + id);
		return new ResponseEntity<>(res, HttpStatus.ACCEPTED);

	}

	public ResponseEntity<ResponseStructure<List<Book>>> getBookByAuthor(String author) {

		ResponseStructure<List<Book>> response = new ResponseStructure<List<Book>>();

		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Matching records of the " + author + " is retrieved");
		response.setData(bookDao.findByAuthor(author));

		return new ResponseEntity<ResponseStructure<List<Book>>>(response, HttpStatus.OK);

	}

	public ResponseEntity<ResponseStructure<Book>> getBooksByTitleAndPublishedYear(String title, Integer year) {

		ResponseStructure<Book> response = new ResponseStructure<Book>();

		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Books retrived based on " + title + " and " + year);
		response.setData(bookDao.findByTitleAndPublishedYear(title, year));

		return new ResponseEntity<ResponseStructure<Book>>(response, HttpStatus.OK);

	}

	public ResponseEntity<ResponseStructure<List<Book>>> getBooksPriceGreater(double price) {

		ResponseStructure<List<Book>> response = new ResponseStructure<List<Book>>();

		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Books retrived greter than price " + price);
		response.setData(bookDao.findByPriceGreaterThan(price));

		return new ResponseEntity<ResponseStructure<List<Book>>>(response, HttpStatus.OK);

	}

	public ResponseEntity<ResponseStructure<List<Book>>> getBooksPriceLesser(double price) {

		ResponseStructure<List<Book>> response = new ResponseStructure<List<Book>>();

		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Books retrived greter than price " + price);
		response.setData(bookDao.findByPriceLessThan(price));

		return new ResponseEntity<ResponseStructure<List<Book>>>(response, HttpStatus.OK);
	}

	public ResponseEntity<ResponseStructure<List<Book>>> getBooksPriceBetweem(double price, double price1) {
		ResponseStructure<List<Book>> response = new ResponseStructure<List<Book>>();

		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Books retrived " + price + " between " + price1);
		response.setData(bookDao.findByPriceBetween(price, price1));

		return new ResponseEntity<ResponseStructure<List<Book>>>(response, HttpStatus.OK);

	}

	public ResponseEntity<ResponseStructure<List<Book>>> getBooksByAvailability() {
		ResponseStructure<List<Book>> response = new ResponseStructure<List<Book>>();

		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Books retrived based on availability");
		response.setData(bookDao.getBookByAvailability());

		return new ResponseEntity<ResponseStructure<List<Book>>>(response, HttpStatus.OK);

	}

	public ResponseEntity<ResponseStructure<List<Book>>> getBooksByGenre(String genre) {
		ResponseStructure<List<Book>> response = new ResponseStructure<List<Book>>();

		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Books retrived based on genre");
		response.setData(bookDao.getBooksByGenre(genre));

		return new ResponseEntity<ResponseStructure<List<Book>>>(response, HttpStatus.OK);

	}

	public ResponseEntity<ResponseStructure<List<Book>>> getBooksByPublishedYear(Integer year) {

		ResponseStructure<List<Book>> response = new ResponseStructure<List<Book>>();

		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Books retrived based on year " + year);
		response.setData(bookDao.getBooksByYear(year));

		return new ResponseEntity<ResponseStructure<List<Book>>>(response, HttpStatus.OK);

	}

	public ResponseEntity<ResponseStructure<Page<Book>>> getBooksByPagination(Integer pageNumber, Integer pageSize) {

		ResponseStructure<Page<Book>> response = new ResponseStructure<Page<Book>>();

		response.setStatusCode(HttpStatus.OK.value());
		response.setMessage("Books retrived");
		response.setData(bookDao.getBooksByPagination(pageNumber, pageSize));

		return new ResponseEntity<ResponseStructure<Page<Book>>>(response, HttpStatus.OK);

	}

	public ResponseEntity<ResponseStructure<List<Book>>> sortABooksbyDecending(String field) {
		ResponseStructure<List<Book>> rs = new ResponseStructure<>();
		rs.setData(bookDao.sortABooksbyDecending(field));
		rs.setMessage("datas are sorted succesfully");
		rs.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<List<Book>>>(rs, HttpStatus.OK);
	}

	public ResponseEntity<ResponseStructure<Page<Book>>> getBookswithPaginationandSort(Integer viewingpage,
			Integer pagetotalcontent, String field) {
		ResponseStructure<Page<Book>> rs = new ResponseStructure<>();
		rs.setData(bookDao.getBookswithPaginationandSort(viewingpage, pagetotalcontent, field));
		rs.setMessage("Pages succsfully Retrieved");
		rs.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<Page<Book>>>(rs, HttpStatus.OK);
	}

}

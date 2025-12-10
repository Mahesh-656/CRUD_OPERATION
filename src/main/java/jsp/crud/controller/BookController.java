package jsp.crud.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jsp.crud.dto.ResponseStructure;
import jsp.crud.entity.Book;
import jsp.crud.exception.IdNotFoundException;
import jsp.crud.exception.NoRecordAvailableException;
import jsp.crud.repository.BookRepository;
import jsp.crud.service.BookService;

@RestController
@RequestMapping("/book")
public class BookController {

	@Autowired
	private BookService bookService;

	// Create single book
	@PostMapping
	public ResponseEntity<ResponseStructure<Book>> saveBook(@RequestBody Book book) {
		return bookService.saveBook(book);
	}

	// Create multiple books
	@PostMapping("/all")
	public ResponseEntity<ResponseStructure<String>> saveBooks(@RequestBody List<Book> books) {
		return bookService.saveBooks(books);
	}

	// Fetch book by id
	@GetMapping("/{id}")
	public ResponseEntity<ResponseStructure<Book>> getBook(@PathVariable Integer id) {
		return bookService.getBook(id);
	}

	// Fetch all books
	@GetMapping
	public ResponseEntity<ResponseStructure<List<Book>>> getBooks() {
		return bookService.getBooks();
	}

	// Update book
	@PutMapping
	public ResponseEntity<ResponseStructure<Book>> updateBook(@RequestBody Book b) {
		return bookService.updateBook(b);
	}

	// Delete book
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteBook(@PathVariable Integer id) {
		return bookService.deleteBook(id);
	}

	@GetMapping("/author/{author}")
	public ResponseEntity<ResponseStructure<List<Book>>> getBookByAuthor(@PathVariable String author) {
		return bookService.getBookByAuthor(author);
	}

	@GetMapping("/author/{title}/{year}")
	public ResponseEntity<ResponseStructure<Book>> getBooksByTitleAndPublishedYear(@PathVariable String title,
			@PathVariable Integer year) {
		return bookService.getBooksByTitleAndPublishedYear(title, year);
	}

	@GetMapping("/price/{price}")
	public ResponseEntity<ResponseStructure<List<Book>>> getBooksPriceGreater(@PathVariable double price) {
		return bookService.getBooksPriceGreater(price);
	}

	@GetMapping("/lessprice/{price}")
	public ResponseEntity<ResponseStructure<List<Book>>> getBooksPriceLesser(@PathVariable double price) {
		return bookService.getBooksPriceLesser(price);
	}

	@GetMapping("/pricebetween/{price}/{price1}")
	public ResponseEntity<ResponseStructure<List<Book>>> getBooksPriceBetweem(@PathVariable double price,
			@PathVariable double price1) {
		return bookService.getBooksPriceBetweem(price, price1);
	}

	@GetMapping("/availability")
	public ResponseEntity<ResponseStructure<List<Book>>> getBooksByAvailability() {
		return bookService.getBooksByAvailability();
	}

	@GetMapping("/genre/{genre}")
	public ResponseEntity<ResponseStructure<List<Book>>> getBooksByGenre(@PathVariable String genre) {
		return bookService.getBooksByGenre(genre);
	}

	@GetMapping("/year/{year}")
	public ResponseEntity<ResponseStructure<List<Book>>> getBooksByPublishedYear(@PathVariable Integer year) {
		return bookService.getBooksByPublishedYear(year);
	}

	@GetMapping("/sort/acending/{pageNo}/{pagetotalcontent}/{field}")
	public ResponseEntity<ResponseStructure<Page<Book>>> getBooksBySortingAndPagination(@PathVariable Integer pageNo,
			@PathVariable(name = "pagetotalcontent") Integer pageSize, @PathVariable String field) {
		return bookService.getBookswithPaginationandSort(pageNo, pageSize, field);
	}

	@GetMapping("/books/{pageNumber}/{pageSize}")
	public ResponseEntity<ResponseStructure<Page<Book>>> getBooksByPagination(@PathVariable Integer pageNumber,@PathVariable Integer pageSize) {
		return bookService.getBooksByPagination(pageNumber, pageSize);
	}

	// get sortABooksbyDecending
	@GetMapping("/sort/decending/{field}")
	public ResponseEntity<ResponseStructure<List<Book>>> getBooksBySorting(@PathVariable String field) {
		return bookService.sortABooksbyDecending(field);
	}

	// get record with pagination and sort ascending

}
/*
 * 
 * //Sending and storing the data in the db
 * 
 * @PostMapping public ResponseEntity<ResponseStructure<Book>>
 * saveBook(@RequestBody Book book) { Book b = bookRepository.save(book);
 * ResponseStructure<Book> res = new ResponseStructure<Book>();
 * res.setStatusCode(HttpStatus.CREATED.value());
 * res.setMessage("Book Record is saved"); res.setData(b);
 * 
 * return new ResponseEntity<ResponseStructure<Book>>(res, HttpStatus.CREATED);
 * }
 * 
 * @PostMapping("/all") public ResponseStructure<String> saveBooks(@RequestBody
 * List<Book> b) { bookRepository.saveAll(b); ResponseStructure<String> res =
 * new ResponseStructure<String>();
 * res.setStatusCode(HttpStatus.CREATED.value());
 * res.setMessage("Book Records are saved"); return res; }
 * 
 * // Fetching the record by id // @GetMapping("/book/{id}") // public
 * Optional<Book> getBook(@PathVariable Integer id) { // return
 * bookRepository.findById(id); // }
 * 
 * @GetMapping("/{id}") public ResponseStructure<Book> getBook(@PathVariable
 * Integer id) { Optional<Book> opt = bookRepository.findById(id);
 * ResponseStructure<Book> res = new ResponseStructure<Book>();
 * 
 * if (opt.isPresent()) { res.setStatusCode(HttpStatus.FOUND.value());
 * res.setMessage("Book is displayed for this" + id); res.setData(opt.get());
 * return res; } else { res.setStatusCode(HttpStatus.NOT_FOUND.value());
 * res.setMessage("Record is not found"); return res; } }
 * 
 * @GetMapping public ResponseStructure<List<Book>> getBooks() {
 * ResponseStructure<List<Book>> res = new ResponseStructure();
 * res.setStatusCode(HttpStatus.FOUND.value()); res.setMessage("");
 * res.setData(bookRepository.findAll()); return res; }
 * 
 * // update a record using put mapping
 * 
 * @PutMapping public ResponseStructure<Book> updateBook(@RequestBody Book b) {
 * ResponseStructure<Book> res = new ResponseStructure<Book>();
 * 
 * if (b.getId() == null) { res.setStatusCode(HttpStatus.NOT_FOUND.value());
 * return res; } Optional<Book> opt = bookRepository.findById(b.getId()); if
 * (opt.isPresent()) { bookRepository.save(b);
 * res.setStatusCode(HttpStatus.CREATED.value());
 * res.setMessage("Successfully updated the record"); return res; } else {
 * res.setStatusCode(HttpStatus.NOT_MODIFIED.value());
 * res.setMessage("Record doesn't updated..!!"); return res; }
 * 
 * }
 * 
 * // @DeleteMapping("/{id}")
 * 
 * @RequestMapping(method = RequestMethod.DELETE, path = "/{id}") public
 * ResponseStructure<String> deleteBook(@PathVariable Integer id) {
 * bookRepository.deleteById(id); ResponseStructure<String> res = new
 * ResponseStructure(); res.setStatusCode(HttpStatus.ACCEPTED.value());
 * res.setMessage("Book Record is deleted"); res.setData("Deleted the book ");
 * return res; }
 */

/*
 * @PostMapping("/all") public ResponseEntity<ResponseStructure<String>>
 * saveBooks(@RequestBody List<Book> books) { bookRepository.saveAll(books);
 * ResponseStructure<String> res = new ResponseStructure<>();
 * res.setStatusCode(HttpStatus.CREATED.value());
 * res.setMessage("Book Records are saved"); res.setData("Total saved: " +
 * books.size());
 * 
 * return new ResponseEntity<>(res, HttpStatus.CREATED); }
 * 
 * // Fetch book by id
 * 
 * @GetMapping("/{id}") public ResponseEntity<ResponseStructure<Book>>
 * getBook(@PathVariable Integer id) { Optional<Book> opt =
 * bookRepository.findById(id); ResponseStructure<Book> res = new
 * ResponseStructure<>();
 * 
 * if (opt.isPresent()) { res.setStatusCode(HttpStatus.FOUND.value());
 * res.setMessage("Book found for id " + id); res.setData(opt.get()); return new
 * ResponseEntity<>(res, HttpStatus.FOUND); } else { throw new
 * IdNotFoundException("Book with id " + id + " not found"); } }
 * 
 * // Fetch all books
 * 
 * @GetMapping public ResponseEntity<ResponseStructure<List<Book>>> getBooks() {
 * 
 * List<Book> book = bookRepository.findAll(); if (book.isEmpty()) {
 * ResponseStructure<List<Book>> res = new ResponseStructure<>();
 * res.setStatusCode(HttpStatus.FOUND.value());
 * res.setMessage("All books retrieved"); res.setData(book); return new
 * ResponseEntity<>(res, HttpStatus.FOUND); } else
 * 
 * { throw new NoRecordAvailableException("no records are availible in you db");
 * 
 * } }
 * 
 * // Update book
 * 
 * @PutMapping public ResponseEntity<ResponseStructure<Book>>
 * updateBook(@RequestBody Book b) { ResponseStructure<Book> res = new
 * ResponseStructure<>();
 * 
 * if (b.getId() == null) { res.setStatusCode(HttpStatus.BAD_REQUEST.value());
 * res.setMessage("Id must not be null for update"); return new
 * ResponseEntity<>(res, HttpStatus.BAD_REQUEST); }
 * 
 * Optional<Book> opt = bookRepository.findById(b.getId()); if (opt.isPresent())
 * { bookRepository.save(b); res.setStatusCode(HttpStatus.OK.value());
 * res.setMessage("Successfully updated the record"); res.setData(b); return new
 * ResponseEntity<>(res, HttpStatus.OK); } else {
 * res.setStatusCode(HttpStatus.NOT_FOUND.value());
 * res.setMessage("Record not found, update failed"); return new
 * ResponseEntity<>(res, HttpStatus.NOT_FOUND); } }
 * 
 * // Delete book
 * 
 * @DeleteMapping("/{id}") public ResponseEntity<ResponseStructure<String>>
 * deleteBook(@PathVariable Integer id) { ResponseStructure<String> res = new
 * ResponseStructure<>();
 * 
 * if (bookRepository.existsById(id)) { bookRepository.deleteById(id);
 * res.setStatusCode(HttpStatus.ACCEPTED.value());
 * res.setMessage("Book Record deleted"); res.setData("Deleted book with id: " +
 * id); return new ResponseEntity<>(res, HttpStatus.ACCEPTED); } else { throw
 * new IdNotFoundException("Cannot delete. Book with id " + id + " not found");
 * } }
 * 
 * @GetMapping("/author/{author}") public
 * ResponseEntity<ResponseStructure<List<Book>>> getBookByAuthor(@PathVariable
 * String author){ List<Book> books=bookRepository.findByAuthor(author);
 * ResponseStructure<List<Book>> response=new ResponseStructure<List<Book>>();
 * if(!books.isEmpty()) { response.setStatusCode(HttpStatus.OK.value());
 * response.setMessage("Matching records of the "+author+" is retrieved");
 * response.setData(books);
 * 
 * return new
 * ResponseEntity<ResponseStructure<List<Book>>>(response,HttpStatus.OK); }else
 * { throw new
 * NoRecordAvailableException("The author "+author+" books are not availabe"); }
 * }
 * 
 * @GetMapping("/author/{title}/{year}") public
 * ResponseEntity<ResponseStructure<Book>>
 * getBooksByTitleAndPublishedYear(@PathVariable String title,@PathVariable
 * Integer year){ Optional<Book>
 * books=bookRepository.findByTitleAndPublishedYear(title,year);
 * ResponseStructure<Book> response=new ResponseStructure<Book>();
 * if(books.isPresent()) { response.setStatusCode(HttpStatus.OK.value());
 * response.setMessage("Books retrived based on "+title+" and "+year);
 * response.setData(books.get());
 * 
 * return new ResponseEntity<ResponseStructure<Book>>(response,HttpStatus.OK);
 * }else { throw new
 * NoRecordAvailableException("No Books found based on title and year"); } }
 * 
 * @GetMapping("/price/{price}") public
 * ResponseEntity<ResponseStructure<List<Book>>>
 * getBooksPriceGreater(@PathVariable double price){ List<Book>
 * books=bookRepository.findByPriceGreaterThan(price);
 * ResponseStructure<List<Book>> response=new ResponseStructure<List<Book>>();
 * if(!books.isEmpty()) { response.setStatusCode(HttpStatus.OK.value());
 * response.setMessage("Books retrived greter than price "+price);
 * response.setData(books);
 * 
 * return new
 * ResponseEntity<ResponseStructure<List<Book>>>(response,HttpStatus.OK); }else
 * { throw new
 * NoRecordAvailableException("No Books found based on this price "); } }
 * 
 * @GetMapping("/lessprice/{price}") public
 * ResponseEntity<ResponseStructure<List<Book>>>
 * getBooksPriceLesser(@PathVariable double price){ List<Book>
 * books=bookRepository.findByPriceLessThan(price);
 * ResponseStructure<List<Book>> response=new ResponseStructure<List<Book>>();
 * if(!books.isEmpty()) { response.setStatusCode(HttpStatus.OK.value());
 * response.setMessage("Books retrived greter than price "+price);
 * response.setData(books);
 * 
 * return new
 * ResponseEntity<ResponseStructure<List<Book>>>(response,HttpStatus.OK); }else
 * { throw new
 * NoRecordAvailableException("No Books found based on this price "); } }
 * 
 * @GetMapping("/pricebetween/{price}/{price1}") public
 * ResponseEntity<ResponseStructure<List<Book>>>
 * getBooksPriceBetweem(@PathVariable double price,@PathVariable double price1){
 * List<Book> books=bookRepository.findByPriceBetween(price,price1);
 * ResponseStructure<List<Book>> response=new ResponseStructure<List<Book>>();
 * if(!books.isEmpty()) { response.setStatusCode(HttpStatus.OK.value());
 * response.setMessage("Books retrived "+ price+ " between "+price1);
 * response.setData(books);
 * 
 * return new
 * ResponseEntity<ResponseStructure<List<Book>>>(response,HttpStatus.OK); }else
 * { throw new NoRecordAvailableException("No Books found based on price"); } }
 * 
 * @GetMapping("/availability") public
 * ResponseEntity<ResponseStructure<List<Book>>> getBooksByAvailability(){
 * List<Book> books=bookRepository.getBookByAvailability();
 * ResponseStructure<List<Book>> response=new ResponseStructure<List<Book>>();
 * if(!books.isEmpty()) { response.setStatusCode(HttpStatus.OK.value());
 * response.setMessage("Books retrived based on availability");
 * response.setData(books);
 * 
 * return new
 * ResponseEntity<ResponseStructure<List<Book>>>(response,HttpStatus.OK); }else
 * { throw new NoRecordAvailableException("No Books available"); } }
 * 
 * @GetMapping("/genre/{genre}") public
 * ResponseEntity<ResponseStructure<List<Book>>> getBooksByGenre(@PathVariable
 * String genre){ List<Book> books=bookRepository.getBooksByGenre(genre);
 * ResponseStructure<List<Book>> response=new ResponseStructure<List<Book>>();
 * if(!books.isEmpty()) { response.setStatusCode(HttpStatus.OK.value());
 * response.setMessage("Books retrived based on genre");
 * response.setData(books);
 * 
 * return new
 * ResponseEntity<ResponseStructure<List<Book>>>(response,HttpStatus.OK); }else
 * { throw new NoRecordAvailableException("No Books available on this genre"); }
 * }
 * 
 * @GetMapping("/year/{year}") public
 * ResponseEntity<ResponseStructure<List<Book>>>
 * getBooksByPublishedYear(@PathVariable Integer year){ List<Book>
 * books=bookRepository.getBooksByYear(year); ResponseStructure<List<Book>>
 * response=new ResponseStructure<List<Book>>(); if(!books.isEmpty()) {
 * response.setStatusCode(HttpStatus.OK.value());
 * response.setMessage("Books retrived based on year "+year);
 * response.setData(books);
 * 
 * return new
 * ResponseEntity<ResponseStructure<List<Book>>>(response,HttpStatus.OK); }else
 * { throw new
 * NoRecordAvailableException("No Books available on this year "+year); } }
 */

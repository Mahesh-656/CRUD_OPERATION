package jsp.crud.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import jsp.crud.entity.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {

	// fetch a book by author
	List<Book> findByAuthor(String author);

	// fetch book by year and title
	Optional<Book> findByTitleAndPublishedYear(String title, Integer publishedYear);

	// fetch book where price is greater than a value
	List<Book> findByPriceGreaterThan(Double price);

	// fetch book where price is less than a value
	List<Book> findByPriceLessThan(Double price);

	// fetch book where price is between a range
	List<Book> findByPriceBetween(Double startingRange, Double endingRange);

	@Query("select b from Book b where b.availability=true")
	List<Book> getBookByAvailability();

	@Query("select b from Book b where b.genre=:genre")
	List<Book> getBooksByGenre(String genre);

	@Query("select b from Book b where b.publishedYear=?1")
	List<Book> getBooksByYear(Integer year);

}

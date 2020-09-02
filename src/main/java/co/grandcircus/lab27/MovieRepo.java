package co.grandcircus.lab27;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;




public interface MovieRepo extends CrudRepository<Movie,Long>{

	List<Movie> findByCategoryContainsIgnoreCase(String category);
	List<Movie> findByCategory(String category);
	//List<Movie> findByTitleContainsIgnoreCase(String title);
	List<Movie> findByTitleContainsIgnoreCase(String keyword);
	@Query("SELECT DISTINCT category FROM Movie")
	List<String> findByDistinctCategory();
	
//	@Query("SELECT TOP ?1 FROM Movie")
//	List<Movie> findByMovieBySize(Integer size);
	
//	@Query("FROM Movie LIMIT :size")
//	List<Movie> findAllMovies(Integer size);

	//List<Movie> findTopByOrderByIdAsc(Integer size);
}

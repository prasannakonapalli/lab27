package co.grandcircus.lab27;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class MovieController {
	@Autowired
	MovieRepo movieDao;

	/*
	 * @GetMapping("/movie") public List<Movie> listMovies(){ List<Movie>
	 * movie=(List<Movie>)movieDao.findAll(); return movie;
	 * 
	 * }
	 */
	@GetMapping("/movie")
	public List<Movie> listMovies(@RequestParam(required = false) String category,
			@RequestParam(required = false) String keyword) {

		List<Movie> movie = new ArrayList<Movie>();
		if ((category == null || category.isEmpty()) && (keyword == null || keyword.isEmpty())) {
			movie = (List<Movie>) movieDao.findAll();
			return movie;
		} else if (category != null && !category.isEmpty()) {
			movie = (List<Movie>) movieDao.findByCategory(category);
			return movie;

		} else if (keyword != null && !keyword.isEmpty()) {
			movie = (List<Movie>) movieDao.findByTitleContainsIgnoreCase(keyword);
			return movie;

		} else {
			return movie;
		}
		// return movie;
	}

	@GetMapping("movie/{id}")
	@ResponseStatus(code=HttpStatus.NOT_FOUND)
	public Movie oneMovie(@PathVariable("id") Long id) {

		return movieDao.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such movie"));
		 
	}

	@GetMapping("movie/category")
	public List<String> listOfCategories() {
		return movieDao.findByDistinctCategory();

	}

	@GetMapping("movie/random")
	public Movie randomMovie() {
		List<Movie> movies = new ArrayList<Movie>();
		movies = (List<Movie>) movieDao.findAll();

//		Random randMovie = new Random(); 
//		int rand = randMovie.nextInt(movies.size());
//		Movie movie = movies.get(rand); 

		int rand = (int) (Math.random() * movies.size() - 1 + 0);
		Movie movie = movies.get(rand);

		// long rand = (long) (Math.random()*movies.size()-1 +0);
		// Movie moviee = movieDao.findById(rand).get();

		return movie;

	}

	@GetMapping("movie/randomCategory")
	public Movie randomMovieCategory(@RequestParam(required = false) String category) {
		List<Movie> movies = new ArrayList<Movie>();
		// movies = (List<Movie>) movieDao.findAll();

		movies = (List<Movie>) movieDao.findByCategory(category);
		//Movie movie = null;

//		Random randMovie = new Random(); 
//		int rand = randMovie.nextInt(movies.size());
//		Movie movie = movies.get(rand); 

		if(movies.size()>0) {
		int rand = (int) (Math.random() * movies.size() - 1 + 0);
		Movie movie = movies.get(rand);
		return movie;
		}

		return null;

	}

	@GetMapping("/movie/randomQuantity") 
	public List<Movie> listMoviesBySize(@RequestParam(required = false) Integer quantity) {
	 
		if(quantity==null || quantity.intValue()<=0) {
		 throw	new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Quantity is invalid");
			//return new ResponseEntity<Movie>(HttpStatus.BAD_REQUEST); 
		}

		List<Movie> movies = new ArrayList<Movie>();
		movies = (List<Movie>) movieDao.findAll();
		List<Movie> quantityMovies = new ArrayList<Movie>();

		for (int i = 0; i < quantity; i++) {
			int rand = (int) (Math.random() * movies.size() - 1 + 0);
			Movie movie = movies.get(rand);
			quantityMovies.add(movie);
		}

		return quantityMovies;

	}

}
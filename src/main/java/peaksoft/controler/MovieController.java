package peaksoft.controler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.model.enums.MovieGenres;
import peaksoft.model.Movie;
import peaksoft.service.impl.MovieService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Controller
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;


    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/save")
    public String save(Model model) {
        MovieGenres[] movieGenres = MovieGenres.values();
        List<MovieGenres> genres = new ArrayList<>(Arrays.asList(movieGenres));
        Movie movie = new Movie();
        model.addAttribute("movie", movie);
        model.addAttribute("genres", genres);
        return "mov/save_movie";
    }

    @PostMapping("/save_movie")
    public String saveCinema(@ModelAttribute("movie") Movie movie) {
        movieService.save(movie);
        return "redirect:/movie/find_all";
    }

    @GetMapping("/find_all")
    public String findAll(Model model) {
        model.addAttribute("all_movie", movieService.findAll());
        return "mov/all_movie";
    }

    @GetMapping("/find")
    public String finById(@PathVariable int id, Model model) {
        model.addAttribute("find_movie", movieService.findById(id));
        return "mov/find_movie";
    }

    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable int id) {
        MovieGenres[] movieGenres = MovieGenres.values();
        List<MovieGenres> genres = new ArrayList<>(Arrays.asList(movieGenres));
        Movie movie = movieService.findById(id);
        model.addAttribute("update", movie);
        model.addAttribute("genres1", genres);
        return "mov/update_movie";
    }

    @PostMapping("/merge_update/{id}")
    public String mergeUpdate(@ModelAttribute Movie movie, @PathVariable int id) {
        movieService.update(id, movie);
        return "redirect:/movie/find_all";
    }

    @PostMapping("delete_by_id/{id}")
    public String deleteById(@PathVariable int id) {
        movieService.deleteById(id);
        return "redirect:/movie/find_all";
    }


}



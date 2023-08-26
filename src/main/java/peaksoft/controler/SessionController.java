package peaksoft.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.model.Movie;
import peaksoft.model.Room;
import peaksoft.model.Session;
import peaksoft.model.enums.MovieGenres;
import peaksoft.service.impl.MovieService;
import peaksoft.service.impl.SessionService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/session")
public class SessionController {

    private final SessionService sessionService;
    private final MovieService movieService;

    @Autowired
    public SessionController(SessionService sessionService, MovieService movieService) {
        this.sessionService = sessionService;
        this.movieService = movieService;
    }

    @ModelAttribute("movieList")
    public List<Movie> movieList() {
        return movieService.findAll();
    }

    @GetMapping("/save")
    public String save(Model model) {
        Session session = new Session();
        model.addAttribute( "ses", session);
        return "sess/save_session";
    }

    @PostMapping("/save_session")
    public String saveRoom(@ModelAttribute("session") Session session) {
        sessionService.save(session);
        return "redirect:/session/find_all";
    }

    @GetMapping("/find_all")
    public String findAll(Model model) {
        model.addAttribute("all_session", sessionService.findAll());
        System.out.println(sessionService.findAll());
        return "sess/all_session";
    }

    @GetMapping("/find")
    public String finById(@PathVariable int id, Model model) {
        model.addAttribute("find_session", sessionService.findById(id));
        return "sess/find_session";
    }

    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable int id) {
        Session session = sessionService.findById(id);
        model.addAttribute("sess", session);
        return "sess/update_session";
    }

    @PostMapping("/merge_update/{id}")
    public String mergeUpdate(@ModelAttribute Session session, @PathVariable int id) {
        sessionService.update(id, session);
        return "redirect:/room/find_all";
    }

    @PostMapping("delete_by_id/{id}")
    public String deleteById(@PathVariable int id) {
        sessionService.deleteById(id);
        return "redirect:/session/find_all";
    }


}


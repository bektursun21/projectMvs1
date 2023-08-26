package peaksoft.controler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.model.Cinema;
import peaksoft.service.impl.CinemaService;

@Controller
@RequestMapping("/cinema")
public class CinemaController {

    private final CinemaService cinemaService;

    @Autowired
    public CinemaController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @GetMapping("/save")
    public String save(Model model) {
        Cinema cinema = new Cinema();
        model.addAttribute("cinema", cinema);
        return "cin/save_cinema";
    }

    @PostMapping("/save_cinema")
    public String saveCinema(@ModelAttribute("cinema") Cinema cinema) {
        cinemaService.save(cinema);
        return "redirect:find_all";
    }

    @GetMapping("/find_all")
    public String findAll(Model model) {
        model.addAttribute("all_cinema", cinemaService.findAll());
        return "cin/all_cinema";
    }

    @GetMapping("/fin/{id}")
    public String finById(@PathVariable int id, Model model) {
        model.addAttribute("find_cinema", cinemaService.findById(id));
        return "cin/find_cinema";
    }

    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable int id) {
        Cinema cinema = cinemaService.findById(id);
        model.addAttribute("cinema", cinema);
        return "cin/update_cinema";
    }

    @PostMapping("/merge_update/{id}")
    public String mergeUpdate(@ModelAttribute Cinema cinema, @PathVariable int id) {
        cinemaService.update(id, cinema);
        return "redirect:/cinema/find_all";
    }

    @PostMapping("/delete_by_id/{id}")
    public String deleteById(@PathVariable int id) {
        cinemaService.deleteById(id);
        return "redirect:/cinema/find_all";
    }

    @GetMapping("/page_front")
    public String getPageFront(Model model){
        Cinema cinema = new Cinema();
        model.addAttribute("cinema_front", cinema);
        return "cin/page_front";
    }

}



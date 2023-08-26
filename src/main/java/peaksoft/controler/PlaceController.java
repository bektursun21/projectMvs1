package peaksoft.controler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.model.Place;
import peaksoft.model.Room;
import peaksoft.service.impl.PlaceService;
import peaksoft.service.impl.RoomService;
import java.util.List;

@Controller
@RequestMapping("/place")
public class PlaceController {

    private final PlaceService placeService;
    private final RoomService roomService;
    @Autowired
    public PlaceController(PlaceService placeService, RoomService roomService) {
        this.placeService = placeService;
        this.roomService = roomService;
    }

    @ModelAttribute("roomList")
    public List<Room> roomList() {
        return roomService.findAll();
    }

    @GetMapping("/save")
    public String save(Model model) {
        Place place = new Place();
        model.addAttribute("place", place);
        return "pla/save_place";
    }

    @PostMapping("/save_place")
    public String saveCinema(@ModelAttribute("place") Place place) {
        placeService.save(place);
        return "redirect:find_all";
    }

    @GetMapping("/find_all")
    public String findAll(Model model) {
        model.addAttribute("all_place", placeService.findAll());
        return "pla/all_place";
    }

    @GetMapping("/find")
    public String finById(@PathVariable int id, Model model) {
        model.addAttribute("find_place", placeService.findById(id));
        return "pla/find_place";
    }

    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable int id) {
        Place place = placeService.findById(id);
        model.addAttribute("place", place);
        return "pla/update_place";
    }

    @PostMapping("/merge_update/{id}")
    public String mergeUpdate(@ModelAttribute Place place, @PathVariable int id) {
        placeService.update(id, place);
        return "redirect:/place/find_all";
    }

    @PostMapping("delete_by_id/{id}")
    public String deleteById(@PathVariable int id) {
        placeService.deleteById(id);
        return "redirect:/place/find_all";
    }
}
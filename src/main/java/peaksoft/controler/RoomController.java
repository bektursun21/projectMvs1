package peaksoft.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import peaksoft.model.Cinema;
import peaksoft.model.Room;
import peaksoft.service.impl.CinemaService;
import peaksoft.service.impl.RoomService;

import java.util.List;

@Controller
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;

    private final CinemaService cinemaService;


    @Autowired
    public RoomController(RoomService roomService, CinemaService cinemaService) {
        this.roomService = roomService;
        this.cinemaService = cinemaService;
    }
    @ModelAttribute("cinemaList")
    public List<Cinema> cinemaList() {
        return cinemaService.findAll();
    }


    @GetMapping("/save")
    public String save(Model model) {
        Room room = new Room();
        model.addAttribute("room", room);
        return "roo/save_room";
    }

    @PostMapping("/save_room")
    public String saveRoom(@ModelAttribute("room") Room room) {
        roomService.save(room);
        return "redirect:/room/find_all";
    }

    @GetMapping("/find_all")
    public String findAll(Model model) {
        model.addAttribute("all_room", roomService.findAll());
        return "roo/find_room";
    }

    @GetMapping("/find_all_by_cinema_id/{id}")
    public String findAllId(@PathVariable("id") int id, Model model){
        model.addAttribute("all_room_id", roomService.findAllId(id));
        return  "roo/all_room_id";
    }

    @GetMapping("/fin/{id}")
    public String finById(@PathVariable int id, Model model) {
        model.addAttribute("find_room", roomService.findById(id));
        return "roo/find_room";
    }

    @GetMapping("/update/{id}")
    public String update(Model model, @PathVariable int id) {
        Room room = roomService.findById(id);
        model.addAttribute("room", room);
        return "roo/update_room";
    }

    @PostMapping("/merge_update/{id}")
    public String mergeUpdate(@ModelAttribute Room room, @PathVariable int id) {
        roomService.update(id,room);
        return "redirect:/room/find_all";
    }

    @PostMapping("delete_by_id/{id}")
    public String deleteById(@PathVariable int id) {
        roomService.deleteById(id);
        return "redirect:/room/find_all";
    }
}
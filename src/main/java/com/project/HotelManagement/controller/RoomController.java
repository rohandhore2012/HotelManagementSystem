package com.project.HotelManagement.controller;

import com.project.HotelManagement.model.Guest;
import com.project.HotelManagement.model.Room;
import com.project.HotelManagement.repository.GuestRepository;
import com.project.HotelManagement.repository.RoomRepository;
import com.project.HotelManagement.service.GuestService;
import com.project.HotelManagement.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private GuestService guestService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private GuestRepository guestRepository;

    // 1. View all rooms
    @GetMapping
    public String listRooms(Model model) {
        List<Room> rooms = roomService.getAllRooms();
        model.addAttribute("rooms", rooms);
        return "room-list";
    }

    // 2. Show add room form
    @GetMapping("/new")
    public String showRoomForm(Model model) {
        model.addAttribute("room", new Room());
        return "room-form";
    }

    // 3. Save new room (POST)
    @PostMapping
    public String saveRoom(@ModelAttribute Room room,
                           @RequestParam(value = "bookNow", required = false) String bookNow) {
        roomService.saveRoom(room);
        if ("true".equals(bookNow)) {
            return "redirect:/guests/new?roomNumber=" + room.getRoomNumber();
        }
        return "redirect:/rooms";
    }

    // 4. Show edit room form
    @GetMapping("/edit/{roomNumber}")
    public String showEditRoomForm(@PathVariable int roomNumber, Model model) {
        Room room = roomService.getRoomByRoomNumber(roomNumber);
        if (room != null) {
            model.addAttribute("room", room);
            return "room-form";
        }
        return "redirect:/rooms";
    }

    // 5. Update room (POST)
    @PostMapping("/update")
    public String updateRoom(@ModelAttribute("room") Room room) {
        roomService.saveRoom(room);
        return "redirect:/rooms";
    }

    // 6. Delete room
    @GetMapping("/delete/{roomNumber}")
    public String deleteRoom(@PathVariable int roomNumber) {
        roomService.deleteRoom(roomNumber);
        return "redirect:/rooms";
    }

    // 7. Book room (manually)
    @PostMapping("/book/{roomNumber}")
    public String bookRoom(@PathVariable int roomNumber) {
        Room room = roomService.getRoomByRoomNumber(roomNumber);
        if (room != null && !room.isBooked()) {
            room.setBooked(true);
            roomService.saveRoom(room);
        }
        return "redirect:/rooms";
    }

    // 8. Cancel room booking (custom logic in service)
    @GetMapping("/cancel/{roomNumber}")
    public String cancelRoom(@PathVariable int roomNumber) {
        roomService.cancelBooking(roomNumber);
        return "redirect:/rooms";
    }

    // 9. Make Room Available (unbind guest and mark unbooked)
    @PostMapping("/make-available/{roomNumber}")
    public String makeRoomAvailable(@PathVariable int roomNumber, RedirectAttributes redirectAttributes) {
        Optional<Room> optionalRoom = roomRepository.findByRoomNumber(roomNumber);

        if (optionalRoom.isPresent()) {
            Room room = optionalRoom.get();

            Optional<Guest> optionalGuest = guestRepository.findByRoom(room);
            if (optionalGuest.isPresent()) {
                Guest guest = optionalGuest.get();

                guestService.deleteGuest(guest.getId());
            }

            redirectAttributes.addFlashAttribute("message", "Room " + roomNumber + " is now available.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Room not found.");
        }

        return "redirect:/rooms";
    }

}

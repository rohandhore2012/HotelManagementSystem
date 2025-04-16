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
@RequestMapping("/guests")
public class GuestController {

    @Autowired
    private GuestService guestService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private RoomRepository roomRepository;

    // 1.Listing All Guests
    @GetMapping
    public String listGuests(Model model) {
        List<Guest> guests = guestService.getAllGuests();
        model.addAttribute("guests", guests);
        return "guest-list";
    }

    // 2.Shows Guest Form
    @GetMapping("/new")
    public String showGuestForm(@RequestParam(value = "roomNumber", required = false) Integer roomNumber, Model model) {
        Guest guest = new Guest();
        guest.setNumberOfPeople(1);

        // Fetch only available rooms for the guest form
        List<Room> availableRooms = roomService.getAvailableRooms();
        model.addAttribute("availableRooms", availableRooms);

        // If a room number is provided, set the guest's room
        if (roomNumber != null) {
            Room room = roomService.getRoomByRoomNumber(roomNumber);
            if (room != null) {
                guest.setRoom(room);
            }
        }

        model.addAttribute("guest", guest);
        return "guest-form";
    }

    // 3.Save Guest
    @PostMapping("")
    public String saveGuest(@ModelAttribute Guest guest, RedirectAttributes redirectAttributes) {
        // Calculate total charge
        if (guest.getTotalCharge() == null || guest.getTotalCharge() == 0) {
            Double rate = guest.getRatePerDay();
            Integer days = guest.getStayDays();
            if (rate != null && days != null) {
                guest.setTotalCharge(rate * days);
            }
        }

        // Get Room by roomNumber using Optional
        Integer roomNumber = guest.getRoom().getRoomNumber();
        Optional<Room> optionalRoom = roomRepository.findByRoomNumber(roomNumber);

        if (optionalRoom.isPresent()) {
            Room selectedRoom = optionalRoom.get();
            selectedRoom.setBooked(true);
            guest.setRoom(selectedRoom);
        } else {
            redirectAttributes.addFlashAttribute("error", "Selected room not found.");
            return "redirect:/guests/new";
        }

        guestRepository.save(guest);
        return "redirect:/guests";
    }

    // 5. Edit Guest Details
    @GetMapping("/edit/{id}")
    public String editGuest(@PathVariable Long id, Model model) {
        Guest guest = guestService.getGuestById(id);
        model.addAttribute("guest", guest);

        List<Room> availableRooms = roomService.getAvailableRooms();

        // Include the currently booked room of this guest
        if (guest.getRoom() != null && !availableRooms.contains(guest.getRoom())) {
            availableRooms.add(guest.getRoom());
        }

        model.addAttribute("availableRooms", availableRooms);

        return "guest-form";
    }

    // 6.Update Guest Details
    @PostMapping("/update")
    public String updateGuest(@ModelAttribute Guest guest) {
        // Set total charge if not set
        if (guest.getTotalCharge() == null || guest.getTotalCharge() == 0) {
            Double rate = guest.getRatePerDay();
            Integer days = guest.getStayDays();
            if (rate != null && days != null) {
                guest.setTotalCharge(rate * days);
            }
        }

        // Update checkout date
        if (guest.getCheckInDate() != null && guest.getStayDays() != null) {
            guest.setCheckOutDate(guest.getCheckInDate().plusDays(guest.getStayDays()));
        }

        guestService.updateGuest(guest);
        return "redirect:/guests";
    }

    // 7.Delete Guest
    @GetMapping("/delete/{id}")
    public String deleteGuest(@PathVariable Long id) {
        Guest guest = guestService.getGuestById(id);
        Room room = guest.getRoom();

        // Unassign guest from the room
        room.setGuest(null);
        room.setBooked(false);
        roomRepository.save(room); // Assuming you have access to roomRepository

        guestService.deleteGuest(id);

        return "redirect:/guests";
    }

    // 8.Search Bar
    @GetMapping("/search")
    public String searchGuests(@RequestParam("keyword") String keyword, Model model) {
        List<Guest> result = guestService.searchGuests(keyword);
        model.addAttribute("guests", result);
        return "guest-list";
    }
}



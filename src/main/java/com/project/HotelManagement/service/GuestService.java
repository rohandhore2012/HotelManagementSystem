package com.project.HotelManagement.service;

import com.project.HotelManagement.model.Guest;
import com.project.HotelManagement.model.Room;
import com.project.HotelManagement.repository.GuestRepository;
import com.project.HotelManagement.repository.RoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class GuestService {

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private RoomRepository roomRepository;

    public List<Guest> getAllGuests() {
        return guestRepository.findAll();
    }

    public Guest getGuestById(Long id) {
        return guestRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Guest not found with id: " + id));
    }

    @Transactional
    public void saveGuest(Guest guest) {
        if (guest == null || guest.getRoom() == null) {
            throw new IllegalArgumentException("Guest or room information must not be null.");
        }

        int roomNumber = guest.getRoom().getRoomNumber();

        Room room = roomRepository.findByRoomNumber(roomNumber)
                .orElseThrow(() -> new IllegalStateException("Room with number " + roomNumber + " not found."));

        if (room.isBooked()) {
            throw new IllegalStateException("Room " + roomNumber + " is already booked.");
        }

        // Calculate checkout date
        if (guest.getCheckInDate() != null && guest.getStayDays() != null) {
            guest.setCheckOutDate(guest.getCheckInDate().plusDays(guest.getStayDays()));
        }

        // Calculate total charge
        if (guest.getTotalCharge() == null || guest.getTotalCharge() == 0) {
            Double rate = guest.getRatePerDay();
            Integer days = guest.getStayDays();
            if (rate != null && days != null) {
                guest.setTotalCharge(rate * days);
            }
        }

        // Set bidirectional references
        guest.setRoom(room);
        room.setGuest(guest);
        room.setBooked(true);

        guestRepository.save(guest); // ✅ Save only guest
    }

    @Transactional
    public void updateGuest(Guest guest) {
        Guest existingGuest = guestRepository.findById(guest.getId())
                .orElseThrow(() -> new RuntimeException("Guest not found"));

        // Free old room
        Room oldRoom = existingGuest.getRoom();
        if (oldRoom != null) {
            oldRoom.setBooked(false);
            oldRoom.setGuest(null);
            roomRepository.save(oldRoom);
        }

        // Book new room
        Room newRoom = roomRepository.findByRoomNumber(guest.getRoom().getRoomNumber())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        newRoom.setBooked(true);
        newRoom.setGuest(guest);
        roomRepository.save(newRoom);

        // Calculate checkout date
        if (guest.getCheckInDate() != null && guest.getStayDays() != null) {
            guest.setCheckOutDate(guest.getCheckInDate().plusDays(guest.getStayDays()));
        }

        // Calculate total charge
        if (guest.getTotalCharge() == null || guest.getTotalCharge() == 0) {
            Double rate = guest.getRatePerDay();
            Integer days = guest.getStayDays();
            if (rate != null && days != null) {
                guest.setTotalCharge(rate * days);
            }
        }
        // Update guest fields
        existingGuest.setName(guest.getName());
        existingGuest.setCheckInDate(guest.getCheckInDate());
        existingGuest.setCheckOutDate(guest.getCheckOutDate());
        existingGuest.setStayDays(guest.getStayDays());
        existingGuest.setRatePerDay(guest.getRatePerDay());
        existingGuest.setTotalCharge(guest.getTotalCharge());
        existingGuest.setNumberOfPeople(guest.getNumberOfPeople());
        existingGuest.setDescription(guest.getDescription());
        existingGuest.setRoom(newRoom);

        guestRepository.save(existingGuest);
    }

    public void deleteGuest(Long id) {
        Guest guest = guestRepository.findById(id).orElse(null);
        if (guest != null) {
            Room room = guest.getRoom();
            if (room != null) {
                room.setBooked(false);
                room.setGuest(null);
                roomRepository.save(room);
            }
            guestRepository.delete(guest);
        }
    }

    public void makeRoomAvailable(int roomNumber) {
        Optional<Room> optionalRoom = roomRepository.findByRoomNumber(roomNumber);
        if (optionalRoom.isPresent()) {
            Room room = optionalRoom.get();

            Optional<Guest> optionalGuest = guestRepository.findByRoom(room);
            if (optionalGuest.isPresent()) {
                guestRepository.delete(optionalGuest.get()); // actually delete the guest
            }

            room.setBooked(false);
            room.setGuest(null); // disconnect guest from room
            roomRepository.save(room);
        }
    }

    public List<Guest> searchGuests(String value) {
        return guestRepository.searchByNameOrRoomNumberOrDescription(value);
    }
}


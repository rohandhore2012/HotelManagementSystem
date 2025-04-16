package com.project.HotelManagement.service;

import com.project.HotelManagement.model.Room;
import com.project.HotelManagement.repository.RoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public List<Room> getAvailableRooms() {
        return roomRepository.findByBookedFalse();
    }

    public Room getRoomByRoomNumber(int roomNumber) {
        Optional<Room> optionalRoom = roomRepository.findByRoomNumber(roomNumber);
        return optionalRoom.orElse(null);  // ✅ Safely unwrap Optional
    }

    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    @Transactional
    public void deleteRoom(int roomNumber) {
        Optional<Room> optionalRoom = roomRepository.findByRoomNumber(roomNumber);
        if (optionalRoom.isPresent()) {
            roomRepository.delete(optionalRoom.get());
        } else {
            throw new IllegalArgumentException("Room not found with number: " + roomNumber);
        }
    }

    public void cancelBooking(int roomNumber) {
        Optional<Room> optionalRoom = roomRepository.findByRoomNumber(roomNumber);
        if (optionalRoom.isPresent()) {
            Room room = optionalRoom.get();
            if (room.isBooked()) {
                room.setBooked(false);
                roomRepository.save(room);
            }
        } else {
            throw new IllegalArgumentException("Room not found with number: " + roomNumber);
        }
    }
}

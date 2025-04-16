package com.project.HotelManagement.repository;

import com.project.HotelManagement.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByBookedFalse();
    Optional<Room> findByRoomNumber(int roomNumber);

}

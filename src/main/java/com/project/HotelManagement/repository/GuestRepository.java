package com.project.HotelManagement.repository;

import com.project.HotelManagement.model.Guest;
import com.project.HotelManagement.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GuestRepository extends JpaRepository<Guest, Long> {
    Optional<Guest> findByRoom(Room room);
    @Query("SELECT g FROM Guest g WHERE LOWER(g.name) LIKE LOWER(CONCAT('%', :value, '%')) OR str(g.room.roomNumber) LIKE CONCAT('%', :value, '%') OR LOWER(g.description) LIKE LOWER(CONCAT('%', :value, '%'))")
    List<Guest> searchByNameOrRoomNumberOrDescription(@Param("value") String value);

}


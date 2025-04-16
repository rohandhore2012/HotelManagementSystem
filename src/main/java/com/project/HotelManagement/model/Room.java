package com.project.HotelManagement.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class Room {

    @Id
    private Integer roomNumber;

    @Column(nullable = false)
    private boolean booked = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type",nullable = false)
    private RoomType roomType;

    @OneToOne(mappedBy = "room", cascade = CascadeType.ALL)
    @JsonBackReference
    private Guest guest;

    // Constructors
    public Room() {
    }

    public Room(int roomNumber, RoomType roomType) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.booked = false;
    }

    // Getters and Setters

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    // equals and hashCode based on roomNumber
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;
        Room room = (Room) o;
        return roomNumber == room.roomNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber);
    }

    // toString method
    @Override
    public String toString() {
        return roomNumber + " - " + roomType;
    }
}

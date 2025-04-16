package com.project.HotelManagement.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"room_number"})
)
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer stayDays;
    private double ratePerDay;
    private Double totalCharge;
    private int numberOfPeople;
    private String description;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "check_in_date")
    private LocalDate checkInDate;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "check_out_date")
    private LocalDate checkOutDate;

    @OneToOne
    @JoinColumn(name = "room_number", referencedColumnName = "roomNumber", unique = true)
    @JsonManagedReference
    private Room room;


    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStayDays() {
        return stayDays;
    }

    public void setStayDays(Integer stayDays) {
        this.stayDays = stayDays;
    }

    public Double getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(Double totalCharge) {
        this.totalCharge = totalCharge;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public double getRatePerDay() {
        return ratePerDay;
    }

    public void setRatePerDay(double ratePerDay) {
        this.ratePerDay = ratePerDay;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
}

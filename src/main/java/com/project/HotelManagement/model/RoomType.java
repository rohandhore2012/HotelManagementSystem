package com.project.HotelManagement.model;

public enum RoomType {
    DELUXE("Deluxe"),
    STANDARD("Standard"),
    SUITE("Suite"),
    FAMILY("Family");

    private final String displayName;

    RoomType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}

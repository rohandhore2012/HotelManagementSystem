# 🏨 Ultimate Hotel Management System

A full-fledged **Hotel Management System** built using **Spring Boot** and **Thymeleaf**.
This system allows hotel staff to manage rooms, guests, and bookings efficiently through a clean and modern web interface.


## 📌 Features

- 🔐 Room Booking System
- 👤 Guest Management (Add, Update, Delete, View)
- 🏠 Room Management (Add, Update, Delete, View)
- 📅 Check-in and Check-out Date Management
- ✅ Only Unbooked Rooms are Available for Booking
- 💵 Room Charges with Rate Per Day
- 📊 Intuitive and User-Friendly Interface (Thymeleaf)
- 📁 All data managed through a connected MySQL Database

## ⚙️ Technologies Used

- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **MySQL**
- **Thymeleaf**
- **HTML & CSS**
- **Maven**

## 🛠️ Project Structure

Ultimate-Hotel-Management-System/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/project/HotelManagement/
│   │   │       ├── controller/
│   │   │       │   ├── GuestController.java
│   │   │       │   ├── RoomController.java
│   │   │       │   └── MainController.java
│   │   │       │
│   │   │       ├── model/
│   │   │       │   ├── Guest.java
│   │   │       │   ├── Room.java
│   │   │       │   └── RoomType.java
│   │   │       │
│   │   │       ├── repository/
│   │   │       │   ├── GuestRepository.java
│   │   │       │   └── RoomRepository.java
│   │   │       │
│   │   │       ├── service/
│   │   │       │   └── GuestService.java
│   │   │       │
│   │   │       └── HotelManagementApplication.java
│   │   │
│   │   ├── resources/
│   │   │   ├── static/
│   │   │   │   └── css/
│   │   │   │       └── style.css
│   │   │   │            (more style sheets according to frontend)
│   │   │   ├── templates/
│   │   │   │   ├── index.html
│   │   │   │   ├── guest-form.html
│   │   │   │   ├── guest-list.html
│   │   │   │   ├── room-form.html
│   │   │   │   └── room-list.html
│   │   │   │
│   │   │   └── application.properties
│   │
│   └── test/
│       └── java/
│           └── com/project/HotelManagement/
│               └── HotelManagementApplicationTests.java
│
├── .gitignore
├── pom.xml
└── README.md


### Prerequisites
- Java 17+
- Maven
- MySQL
- IDE (like IntelliJ or Eclipse)

 **To Clone the Repository:**
   ```bash
   git clone https://github.com/rohandhore2012/HotelManagementSystem.git

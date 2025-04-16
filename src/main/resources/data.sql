-- Clear existing data (optional)
DELETE FROM guests;
DELETE FROM rooms;

-- Reset auto-increment counters (MySQL syntax)
ALTER TABLE guests AUTO_INCREMENT = 1;
ALTER TABLE rooms AUTO_INCREMENT = 1;

-- Insert sample rooms
INSERT INTO rooms (room_number, type, rate_per_day, available) VALUES
(101, 'Standard', 100.00, true),
(102, 'Standard', 100.00, true),
(103, 'Standard', 100.00, true),
(201, 'Deluxe', 150.00, true),
(202, 'Deluxe', 150.00, true),
(301, 'Suite', 250.00, true),
(302, 'Suite', 250.00, true);

-- Insert sample guests (with bookings)
INSERT INTO guests (first_name, last_name, email, phone, check_in_date, check_out_date, stay_days, total_charge, room_number) VALUES
('John', 'Doe', 'john.doe@example.com', '555-0101', '2023-06-01', '2023-06-05', 4, 400.00, 101),
('Jane', 'Smith', 'jane.smith@example.com', '555-0102', '2023-06-02', '2023-06-04', 2, 300.00, 201),
('Robert', 'Johnson', 'robert.j@example.com', '555-0103', '2023-06-03', '2023-06-08', 5, 1250.00, 301);

-- Update room availability based on guest bookings
UPDATE rooms SET available = false WHERE room_number IN (101, 201, 301);
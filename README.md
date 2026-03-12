# ✈️ Airline Reservation System (Java GUI)

A **desktop-based Airline Reservation System** developed using **Java Swing GUI**.
The application allows users to manage airline bookings, view flights, reserve seats, and cancel reservations through a graphical user interface.

The system simulates real-world airline reservation operations including **user registration, flight management, ticket booking, and reservation cancellation**.

---

## 🚀 Project Overview

This project demonstrates the use of **Java Swing for GUI development**, **file handling for persistent data storage**, and **object-oriented programming concepts**.

The system supports two types of users:

**1. Admin**

* Manage flights
* View all reservations
* Add or remove flights

**2. User**

* Register and login
* View available flights
* Book tickets
* Cancel reservations

---

## 📌 Features

### 👤 User Features

• User Sign Up and Login
• View available flights
• Book airline tickets
• View personal reservations
• Cancel booked tickets

### 👨‍✈️ Admin Features

• Admin login authentication
• Add new flights
• Remove flights
• View all reservations

---

## 🧠 System Workflow

1. User registers or logs in
2. User can view available flights
3. User books a ticket if seats are available
4. Reservation details are saved to files
5. User can cancel reservations if needed
6. Admin can manage flights and monitor reservations

---

## 🛠 Technologies Used

Programming Language
• Java

GUI Framework
• Java Swing

Concepts Used
• Object-Oriented Programming (OOP)
• File Handling
• Event Handling
• Collections (ArrayList)

---

## 📂 Project Structure

```text
AirlineReservationSystem
│
├── AirlineReservationSystemGUI.java
├── flights.txt
├── users.txt
└── reservations.txt
```

### File Description

**AirlineReservationSystemGUI.java**
Main source code containing GUI, logic, and system workflow.

**flights.txt**
Stores flight information including flight number, destination, and available seats.

**users.txt**
Stores registered user credentials.

**reservations.txt**
Stores booking records linking users to flights.

---

## 🔐 Default Admin Credentials

Username

```
admin
```

Password

```
admin123
```

---

## 📊 Default Flights

If no flight data file exists, the system automatically creates sample flights:

| Flight Number | Destination | Seats |
| ------------- | ----------- | ----- |
| 101           | Delhi       | 10    |
| 102           | Mumbai      | 8     |
| 103           | Chennai     | 12    |
| 104           | Kolkata     | 7     |

---

## 📌 Future Improvements

• Database integration (MySQL / PostgreSQL)
• Online payment system
• Seat selection interface
• Flight search and filtering
• Email ticket confirmation

---

## 👨‍💻 Author

**Anas Kareem**

GitHub
https://github.com/anasniet77

---

## 🙏 Acknowledgements

• Java Swing for GUI development
• File handling techniques in Java
• GitHub for project hosting

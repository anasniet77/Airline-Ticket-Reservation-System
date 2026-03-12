import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

class Flight {
    int flightNumber;
    String destination;
    int seatsAvailable;

    Flight(int flightNumber, String destination, int seatsAvailable) {
        this.flightNumber = flightNumber;
        this.destination = destination;
        this.seatsAvailable = seatsAvailable;
    }

    boolean bookSeat() {
        if (seatsAvailable > 0) {
            seatsAvailable--;
            return true;
        }
        return false;
    }

    void cancelSeat() {
        seatsAvailable++;
    }

    @Override
    public String toString() {
        return flightNumber + "," + destination + "," + seatsAvailable;
    }

    static Flight fromString(String line) {
        String[] parts = line.split(",");
        return new Flight(Integer.parseInt(parts[0]), parts[1], Integer.parseInt(parts[2]));
    }
}

class User {
    String username, password;

    User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return username + "," + password;
    }

    static User fromString(String line) {
        String[] parts = line.split(",");
        return new User(parts[0], parts[1]);
    }
}

class Reservation {
    String username;
    int flightNumber;

    Reservation(String username, int flightNumber) {
        this.username = username;
        this.flightNumber = flightNumber;
    }

    @Override
    public String toString() {
        return username + "," + flightNumber;
    }

    static Reservation fromString(String line) {
        String[] parts = line.split(",");
        return new Reservation(parts[0], Integer.parseInt(parts[1]));
    }
}

public class AirlineReservationSystemGUI {
    static java.util.List<Flight> flights = new ArrayList<>();
    static java.util.List<User> users = new ArrayList<>();
    static java.util.List<Reservation> reservations = new ArrayList<>();

    static final String FLIGHT_FILE = "flights.txt";
    static final String USER_FILE = "users.txt";
    static final String RES_FILE = "reservations.txt";

    public static void main(String[] args) {
        loadData();
        SwingUtilities.invokeLater(() -> new MainMenu());
    }

    // ======== MAIN MENU ========
    static class MainMenu extends JFrame {
        MainMenu() {
            setTitle("Airline Reservation System");
            setSize(400, 300);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            JButton signup = new JButton("User Sign Up");
            JButton login = new JButton("User Login");
            JButton admin = new JButton("Admin Login");
            JButton exit = new JButton("Exit");

            signup.addActionListener(e -> new SignUpPage(this));
            login.addActionListener(e -> new UserLoginPage(this));
            admin.addActionListener(e -> new AdminLoginPage(this));
            exit.addActionListener(e -> {
                saveData();
                System.exit(0);
            });

            setLayout(new GridLayout(4, 1, 10, 10));
            add(signup);
            add(login);
            add(admin);
            add(exit);

            setVisible(true);
        }
    }

    // ======== SIGN UP ========
    static class SignUpPage extends JFrame {
        SignUpPage(JFrame parent) {
            setTitle("User Sign Up");
            setSize(350, 200);
            setLocationRelativeTo(parent);

            JTextField userField = new JTextField();
            JPasswordField passField = new JPasswordField();
            JButton signupBtn = new JButton("Sign Up");

            signupBtn.addActionListener(e -> {
                String u = userField.getText().trim();
                String p = new String(passField.getPassword());

                for (User user : users) {
                    if (user.username.equalsIgnoreCase(u)) {
                        JOptionPane.showMessageDialog(this, "Username already exists!");
                        return;
                    }
                }
                users.add(new User(u, p));
                saveUsers();
                JOptionPane.showMessageDialog(this, "Sign Up Successful!");
                dispose();
            });

            setLayout(new GridLayout(3, 2, 5, 5));
            add(new JLabel("Username:"));
            add(userField);
            add(new JLabel("Password:"));
            add(passField);
            add(signupBtn);

            setVisible(true);
        }
    }

    // ======== USER LOGIN ========
    static class UserLoginPage extends JFrame {
        UserLoginPage(JFrame parent) {
            setTitle("User Login");
            setSize(350, 200);
            setLocationRelativeTo(parent);

            JTextField userField = new JTextField();
            JPasswordField passField = new JPasswordField();
            JButton loginBtn = new JButton("Login");

            loginBtn.addActionListener(e -> {
                String u = userField.getText().trim();
                String p = new String(passField.getPassword());
                for (User user : users) {
                    if (user.username.equals(u) && user.password.equals(p)) {
                        JOptionPane.showMessageDialog(this, "Login Successful!");
                        new UserMenu(u);
                        dispose();
                        return;
                    }
                }
                JOptionPane.showMessageDialog(this, "Invalid credentials!");
            });

            setLayout(new GridLayout(3, 2, 5, 5));
            add(new JLabel("Username:"));
            add(userField);
            add(new JLabel("Password:"));
            add(passField);
            add(loginBtn);

            setVisible(true);
        }
    }

    // ======== ADMIN LOGIN ========
    static class AdminLoginPage extends JFrame {
        AdminLoginPage(JFrame parent) {
            setTitle("Admin Login");
            setSize(350, 200);
            setLocationRelativeTo(parent);

            JTextField userField = new JTextField();
            JPasswordField passField = new JPasswordField();
            JButton loginBtn = new JButton("Login");

            loginBtn.addActionListener(e -> {
                String u = userField.getText();
                String p = new String(passField.getPassword());
                if (u.equals("admin") && p.equals("admin123")) {
                    JOptionPane.showMessageDialog(this, "Admin Login Successful!");
                    new AdminMenu();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid admin credentials!");
                }
            });

            setLayout(new GridLayout(3, 2, 5, 5));
            add(new JLabel("Admin Username:"));
            add(userField);
            add(new JLabel("Admin Password:"));
            add(passField);
            add(loginBtn);

            setVisible(true);
        }
    }

    // ======== USER MENU ========
    static class UserMenu extends JFrame {
        UserMenu(String username) {
            setTitle("User Menu - " + username);
            setSize(400, 300);
            setLocationRelativeTo(null);

            JButton viewFlights = new JButton("View Flights");
            JButton bookTicket = new JButton("Book Ticket");
            JButton viewRes = new JButton("My Reservations");
            JButton cancelRes = new JButton("Cancel Reservation");
            JButton logout = new JButton("Logout");

            viewFlights.addActionListener(e -> showFlights());
            bookTicket.addActionListener(e -> bookTicket(username));
            viewRes.addActionListener(e -> viewMyReservations(username));
            cancelRes.addActionListener(e -> cancelReservation(username));
            logout.addActionListener(e -> dispose());

            setLayout(new GridLayout(5, 1, 10, 10));
            add(viewFlights);
            add(bookTicket);
            add(viewRes);
            add(cancelRes);
            add(logout);

            setVisible(true);
        }
    }

    // ======== ADMIN MENU ========
    static class AdminMenu extends JFrame {
        AdminMenu() {
            setTitle("Admin Menu");
            setSize(400, 300);
            setLocationRelativeTo(null);

            JButton viewFlights = new JButton("View Flights");
            JButton addFlight = new JButton("Add Flight");
            JButton removeFlight = new JButton("Remove Flight");
            JButton viewRes = new JButton("View All Reservations");
            JButton logout = new JButton("Logout");

            viewFlights.addActionListener(e -> showFlights());
            addFlight.addActionListener(e -> addFlight());
            removeFlight.addActionListener(e -> removeFlight());
            viewRes.addActionListener(e -> viewAllReservations());
            logout.addActionListener(e -> dispose());

            setLayout(new GridLayout(5, 1, 10, 10));
            add(viewFlights);
            add(addFlight);
            add(removeFlight);
            add(viewRes);
            add(logout);

            setVisible(true);
        }
    }

    // ======== FUNCTIONS ========
    static void showFlights() {
        StringBuilder sb = new StringBuilder("Available Flights:\n\n");
        for (Flight f : flights)
            sb.append("No: ").append(f.flightNumber)
              .append(" | To: ").append(f.destination)
              .append(" | Seats: ").append(f.seatsAvailable).append("\n");
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    static void addFlight() {
        try {
            int fno = Integer.parseInt(JOptionPane.showInputDialog("Enter Flight Number:"));
            String dest = JOptionPane.showInputDialog("Enter Destination:");
            int seats = Integer.parseInt(JOptionPane.showInputDialog("Enter Seats:"));
            flights.add(new Flight(fno, dest, seats));
            saveFlights();
            JOptionPane.showMessageDialog(null, "Flight Added!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Invalid input!");
        }
    }

    static void removeFlight() {
        int fno = Integer.parseInt(JOptionPane.showInputDialog("Enter Flight Number to remove:"));
        flights.removeIf(f -> f.flightNumber == fno);
        saveFlights();
        JOptionPane.showMessageDialog(null, "Flight Removed!");
    }

    static void viewAllReservations() {
        if (reservations.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No reservations found.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Reservation r : reservations)
            sb.append("User: ").append(r.username).append(" | Flight: ").append(r.flightNumber).append("\n");
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    static void bookTicket(String username) {
        int fno = Integer.parseInt(JOptionPane.showInputDialog("Enter Flight Number to Book:"));
        for (Flight f : flights) {
            if (f.flightNumber == fno) {
                if (f.bookSeat()) {
                    reservations.add(new Reservation(username, fno));
                    saveData();
                    JOptionPane.showMessageDialog(null, "Ticket Booked!");
                } else JOptionPane.showMessageDialog(null, "No seats available!");
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Flight not found!");
    }

    static void viewMyReservations(String username) {
        StringBuilder sb = new StringBuilder("Your Reservations:\n");
        boolean found = false;
        for (Reservation r : reservations) {
            if (r.username.equals(username)) {
                sb.append("Flight: ").append(r.flightNumber).append("\n");
                found = true;
            }
        }
        JOptionPane.showMessageDialog(null, found ? sb.toString() : "No reservations found.");
    }

    static void cancelReservation(String username) {
        int fno = Integer.parseInt(JOptionPane.showInputDialog("Enter Flight Number to cancel:"));
        Iterator<Reservation> it = reservations.iterator();
        while (it.hasNext()) {
            Reservation r = it.next();
            if (r.username.equals(username) && r.flightNumber == fno) {
                it.remove();
                for (Flight f : flights)
                    if (f.flightNumber == fno)
                        f.cancelSeat();
                saveData();
                JOptionPane.showMessageDialog(null, "Reservation Cancelled!");
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "No such reservation found.");
    }

    // ======== FILE HANDLING ========
    static void loadData() {
        try {
            File f = new File(FLIGHT_FILE);
            if (!f.exists()) {
                flights.add(new Flight(101, "Delhi", 10));
                flights.add(new Flight(102, "Mumbai", 8));
                flights.add(new Flight(103, "Chennai", 12));
                flights.add(new Flight(104, "Kolkata", 7));
                saveFlights();
            } else {
                BufferedReader br = new BufferedReader(new FileReader(f));
                String line;
                while ((line = br.readLine()) != null)
                    flights.add(Flight.fromString(line));
                br.close();
            }

            File uf = new File(USER_FILE);
            if (uf.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(uf));
                String line;
                while ((line = br.readLine()) != null)
                    users.add(User.fromString(line));
                br.close();
            }

            File rf = new File(RES_FILE);
            if (rf.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(rf));
                String line;
                while ((line = br.readLine()) != null)
                    reservations.add(Reservation.fromString(line));
                br.close();
            }
        } catch (Exception e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    static void saveData() {
        saveFlights();
        saveUsers();
        saveReservations();
    }

    static void saveFlights() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FLIGHT_FILE))) {
            for (Flight f : flights) bw.write(f.toString() + "\n");
        } catch (Exception e) {}
    }

    static void saveUsers() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USER_FILE))) {
            for (User u : users) bw.write(u.toString() + "\n");
        } catch (Exception e) {}
    }

    static void saveReservations() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RES_FILE))) {
            for (Reservation r : reservations) bw.write(r.toString() + "\n");
        } catch (Exception e) {}
    }
}

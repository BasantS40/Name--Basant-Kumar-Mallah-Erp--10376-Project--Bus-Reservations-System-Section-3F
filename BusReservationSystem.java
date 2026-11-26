import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

class Bus {
    int busId;
    String route;
    int availableSeats;

    Bus(int busId, String route, int availableSeats) {
        this.busId = busId;
        this.route = route;
        this.availableSeats = availableSeats;
    }
}

class Booking {
    String bookingId;
    int busId;
    String passengerName;

    Booking(String bookingId, int busId, String passengerName) {
        this.bookingId = bookingId;
        this.busId = busId;
        this.passengerName = passengerName;
    }

    @Override
    public String toString() {
        return "[" + bookingId + "] Passenger: " + passengerName + " | Bus ID: " + busId;
    }
}

public class BusReservationSystem {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Bus> buses = new ArrayList<>();
    static HashMap<String, Booking> reservations = new HashMap<>();
    static Queue<Booking> waitingList = new LinkedList<>();
    static Stack<Booking> recentBookings = new Stack<>();
    static int bookingCounter = 1;

    static String readLine(String message) {
        System.out.print(message);
        return sc.nextLine().trim();
    }

    static Bus findBus(int id) {
        for (Bus bus : buses) {
            if (bus.busId == id) return bus;
        }
        return null;
    }

    static void header() {
        System.out.println("\n==========================================");
        System.out.println("        BUS RESERVATION SYSTEM");
        System.out.println("==========================================");
    }

    static void seedBuses() {
        buses.add(new Bus(101, "Bhilai -> Raipur", 2));
        buses.add(new Bus(102, "Raipur -> Nagpur", 3));
    }

    static void bookTicket() {
        int busId = Integer.parseInt(readLine("Enter Bus ID: "));
        Bus bus = findBus(busId);
        if (bus == null) {
            System.out.println("Bus not found.");
            return;
        }

        String name = readLine("Enter Passenger Name: ");
        String bookingId = "BK" + (bookingCounter++);
        Booking booking = new Booking(bookingId, busId, name);

        if (bus.availableSeats > 0) {
            bus.availableSeats--;
            reservations.put(bookingId, booking);
            recentBookings.push(booking);
            System.out.println("Confirmed: " + booking);
        } else {
            waitingList.add(booking);
            System.out.println("Bus full. Added to waiting list: " + booking);
        }
    }

    static void showStatus() {
        System.out.println("\n--- Buses ---");
        for (Bus b : buses) {
            System.out.println("Bus " + b.busId + " (" + b.route + ") - Seats left: " + b.availableSeats);
        }
        System.out.println("\n--- Confirmed Reservations ---");
        reservations.values().forEach(System.out::println);
        System.out.println("\n--- Waiting List ---");
        waitingList.forEach(System.out::println);
        System.out.println("\n--- Most Recent Booking ---");
        System.out.println(recentBookings.isEmpty() ? "None" : recentBookings.peek());
    }

    public static void main(String[] args) {
        seedBuses();
        while (true) {
            header();
            System.out.println("1. Book Ticket");
            System.out.println("2. View Status");
            System.out.println("3. Exit");
            String choice = readLine("Choose an option: ");

            switch (choice) {
                case "1":
                    bookTicket();
                    break;
                case "2":
                    showStatus();
                    break;
                case "3":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
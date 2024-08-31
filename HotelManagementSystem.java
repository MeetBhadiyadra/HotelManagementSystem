import java.io.*;
import java.util.*;

class Hotel {
        ArrayDeque<Integer> cook;

        Hotel() {
                cook = new ArrayDeque<>();
        }
}

class Room {
        private int roomNumber;
        private String roomType;
        private double price;
        private boolean isAvailable;

        public Room(int roomNumber, String roomType, double price, boolean isAvailable) {
                this.roomNumber = roomNumber;
                this.roomType = roomType;
                this.price = price;
                this.isAvailable = isAvailable;
        }

        public int getRoomNumber() {
                return roomNumber;
        }

        public String getRoomType() {
                return roomType;
        }

        public double getPrice() {
                return price;
        }

        public boolean isAvailable() {
                return isAvailable;
        }

        public void setAvailable(boolean available) {
                isAvailable = available;
        }

        @Override
        public String toString() {
                return "Room Number: " + roomNumber +
                                ", Type: " + roomType +
                                ", Price: $" + price +
                                ", Availability: " + (isAvailable ? "Available" : "Occupied");
        }
}

class HMS {
        private List<Room> roomList = new ArrayList<>();
        private static final String ROOMS_FILE = "rooms.txt";

        public HMS() {
                loadRooms();
        }

        public void loadRooms() {
                try (BufferedReader reader = new BufferedReader(new FileReader(ROOMS_FILE))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                                String[] parts = line.split(",");
                                if (parts.length == 4) {
                                        int roomNumber = Integer.parseInt(parts[0].trim());
                                        String roomType = parts[1].trim();
                                        double price = Double.parseDouble(parts[2].trim());
                                        boolean isAvailable = Boolean.parseBoolean(parts[3].trim());
                                        Room room = new Room(roomNumber, roomType, price, isAvailable);
                                        roomList.add(room);
                                }
                        }
                        System.out.println("Rooms loaded successfully.");
                } catch (IOException e) {
                        System.out.println("Error loading room data: " + e.getMessage());
                }
        }

        public void saveRooms() {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(ROOMS_FILE))) {
                        for (Room room : roomList) {
                                writer.write(room.getRoomNumber() + "," + room.getRoomType() + "," + room.getPrice()
                                                + ","
                                                + room.isAvailable());
                                writer.newLine();
                        }
                        System.out.println("Rooms data saved successfully.");
                } catch (IOException e) {
                        System.out.println("Error saving room data: " + e.getMessage());
                }
        }

        public double calculateTotalAmount(int roomNumber) {
                for (Room room : roomList) {
                        if (room.getRoomNumber() == roomNumber && room.isAvailable()) {
                                return room.getPrice();
                        }
                }
                return -1.0; // Return -1 to indicate room not found or not available
        }

        public void bookRoomByType() {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter the room type you want to book (e.g., Single, Deluxe, Suite, Luxury): ");
                String roomType = scanner.nextLine();

                List<Room> availableRooms = new ArrayList<>();

                for (Room room : roomList) {
                        if (room.getRoomType().equalsIgnoreCase(roomType) && room.isAvailable()) {
                                availableRooms.add(room);
                        }
                }

                if (availableRooms.isEmpty()) {
                        System.out.println("No " + roomType + " rooms are available.");
                } else {
                        System.out.println("Available " + roomType + " Rooms:");
                        for (Room room : availableRooms) {
                                System.out.println(room);
                        }

                        System.out.print("Enter the room number you want to book (Type: " + roomType + "): ");
                        int roomNumberToBook = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        boolean roomFound = false;
                        for (Room room : availableRooms) {
                                if (room.getRoomNumber() == roomNumberToBook) {
                                        room.setAvailable(false);
                                        roomFound = true;
                                        System.out.println("Room " + roomNumberToBook + " (Type: " + roomType
                                                        + ") booked successfully.");
                                        saveRooms();
                                        break;
                                }
                        }

                        if (!roomFound) {
                                System.out.println("Room " + roomNumberToBook + " is not available or does not exist.");
                        }
                }
        }

        public void cancelBooking(int roomNumber) {
                for (Room room : roomList) {
                        if (room.getRoomNumber() == roomNumber && !room.isAvailable()) {
                                room.setAvailable(true);
                                System.out.println("Booking for Room " + roomNumber + " canceled successfully.");
                                saveRooms();
                                return;
                        }
                }
                System.out.println("Room " + roomNumber + " is not booked or does not exist.");
        }

        public void displayAvailableRooms() {
                System.out.println("Available Rooms:");
                for (Room room : roomList) {
                        if (room.isAvailable()) {
                                System.out.println(room);
                        }
                }
        }

        public void bookRoom(int numRoomsToBook) {
                Scanner sc = new Scanner(System.in);
                List<Integer> bookedRoomNumbers = new ArrayList<>();
                double totalAmount = 0.0;

                for (int i = 0; i < numRoomsToBook; i++) {
                        System.out.print("Enter the room number for booking (Room " + (i + 1) + "/" + numRoomsToBook
                                        + "): ");
                        int roomNumber = sc.nextInt();
                        sc.nextLine(); // Consume newline

                        Room roomToBook = findRoom(roomNumber);
                        if (roomToBook != null && roomToBook.isAvailable()) {
                                roomToBook.setAvailable(false);
                                bookedRoomNumbers.add(roomNumber);
                                totalAmount += roomToBook.getPrice();
                        } else {
                                System.out.println("Room " + roomNumber
                                                + " is not available or does not exist. Try again.");
                                i--; // Decrement i to re-enter the room number
                        }
                }

                if (bookedRoomNumbers.isEmpty()) {
                        System.out.println("No rooms were booked.");
                } else {
                        System.out.println("Rooms booked successfully: " + bookedRoomNumbers);
                        System.out.println("Total Amount: Rs" + totalAmount); // Display the total bill
                        saveRooms();
                }
        }

        private Room findRoom(int roomNumber) {
                for (Room room : roomList) {
                        if (room.getRoomNumber() == roomNumber) {
                                return room;
                        }
                }
                return null; // Room not found
        }

}

class HotelManagementSystem {

        public static void main(String[] args) throws Exception {
                Scanner sc = new Scanner(System.in);
                Hotel r = new Hotel();
                HMS hotelSystem = new HMS();
                int ch, c1, c2, c3, Choi, Choice, t_cost;
                double Bill;
                do {
                        System.out.println("Hello");
                        System.out.println("Welcome to Our Hotel");
                        System.out.println("How May I Help You");
                        System.out.println("Please Enter");
                        System.out.println("1. For Food Ordering");
                        System.out.println("2. For Room Booking");
                        System.out.println("6. Exiting");
                        Choice = sc.nextInt();
                        switch (Choice) {
                                case 1:
                                        System.out.println("Hii");
                                        System.out.println("Welcome to the Restaurent");
                                        System.out.println("1.View  Menu");
                                        System.out.println("2.Dining");
                                        System.out.println("3.Take Away");
                                        System.out.println("4.Online Order");
                                        System.out.println("enter your choice");
                                        ch = sc.nextInt();
                                        switch (ch) {
                                                case 1:
                                                        do {
                                                                System.out.println("enter your choice of order");
                                                                System.out.println("1.unlimited menu");
                                                                System.out.println("2.punjabi Combos");
                                                                System.out.println("3.gujarati combos");
                                                                System.out.println("4.rajastani combos");
                                                                System.out.println("5.our special combos");
                                                                System.out.println("6.exit");
                                                                c2 = sc.nextInt();
                                                                switch (c2) {
                                                                        case 1: {
                                                                                BufferedReader br1 = new BufferedReader(
                                                                                                new FileReader("Unlimited.txt"));
                                                                                String line;
                                                                                while ((line = br1
                                                                                                .readLine()) != null) {
                                                                                        System.out.println(line);
                                                                                }
                                                                                br1.close();
                                                                        }
                                                                                break;
                                                                        case 2: {
                                                                                BufferedReader br1 = new BufferedReader(
                                                                                                new FileReader("Punjabi.txt"));
                                                                                String line;
                                                                                while ((line = br1
                                                                                                .readLine()) != null) {
                                                                                        System.out.println(line);
                                                                                }
                                                                                br1.close();
                                                                        }
                                                                                break;
                                                                        case 3: {
                                                                                BufferedReader br1 = new BufferedReader(
                                                                                                new FileReader("Gujarati.txt"));
                                                                                String line;
                                                                                while ((line = br1
                                                                                                .readLine()) != null) {
                                                                                        System.out.println(line);
                                                                                }
                                                                                br1.close();
                                                                        }
                                                                                break;
                                                                        case 4: {
                                                                                BufferedReader br1 = new BufferedReader(
                                                                                                new FileReader("Rajasthani.txt"));
                                                                                String line;
                                                                                while ((line = br1
                                                                                                .readLine()) != null) {
                                                                                        System.out.println(line);
                                                                                }
                                                                                br1.close();
                                                                        }
                                                                                break;
                                                                        case 5: {
                                                                                BufferedReader br1 = new BufferedReader(
                                                                                                new FileReader("Special.txt"));
                                                                                String line;
                                                                                while ((line = br1
                                                                                                .readLine()) != null) {
                                                                                        System.out.println(line);
                                                                                }
                                                                                br1.close();
                                                                        }
                                                                                break;
                                                                        case 6: {
                                                                                System.out.println("Exiting menu");
                                                                        }
                                                                                break;
                                                                        default:
                                                                                System.out.println(
                                                                                                "Please enter Valid Choice");
                                                                                break;
                                                                }
                                                        } while (c2 != 6);
                                                        break;
                                                case 2: {
                                                        do {
                                                                System.out.println("Welcome to Thala's Restaurent");
                                                                System.out.println("enter your choice of order");
                                                                System.out.println("1.unlimited menu-Price=500/-");
                                                                System.out.println("2.punjabi Combos-Price=300/-");
                                                                System.out.println("3.gujarati combos-Price=250/-");
                                                                System.out.println("4.rajasthani combos-Price=200/-");
                                                                System.out.println("5.our special combos-Price=350/-");
                                                                // System.out.println("Maximum 9 orders can b placed at
                                                                // once for ");
                                                                System.out.println("6.exit");
                                                                c2 = sc.nextInt();
                                                                sc.nextLine();
                                                                switch (c2) {
                                                                        case 1:
                                                                                System.out.println(
                                                                                                "enter number of people");
                                                                                int n_p = sc.nextInt();
                                                                                r.cook.addLast(n_p);
                                                                                System.out.println(
                                                                                                "Your order for item "
                                                                                                                + n_p
                                                                                                                + " has been placed...");
                                                                                t_cost = n_p * 500;
                                                                                Bill = t_cost + (t_cost * 0.05);
                                                                                System.out
                                                                                                .println(" __________________________________________________________");
                                                                                System.out
                                                                                                .println("                      THALA'S RESTAURENT                  ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out.println(
                                                                                                "  Order        " + "  "
                                                                                                                + " Price per item"
                                                                                                                + "  "
                                                                                                                + "  Quantity    ");
                                                                                System.out
                                                                                                .println("__________________________________________________________");
                                                                                System.out.println("Unlimited platter"
                                                                                                + "  "
                                                                                                + "   500         "
                                                                                                + "      "
                                                                                                + n_p + "    ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out
                                                                                                .println("__________________________________________________________");
                                                                                System.out.println(
                                                                                                "                            Total Bill="
                                                                                                                + t_cost
                                                                                                                + "           ");
                                                                                System.out
                                                                                                .println("                                   CGST=2.5%              ");
                                                                                System.out
                                                                                                .println("                                  SGST=2.5%               ");
                                                                                System.out
                                                                                                .println("__________________________________________________________");
                                                                                System.out.println(
                                                                                                "                            Final Bill="
                                                                                                                + Bill
                                                                                                                + "             ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out
                                                                                                .println("             Thank U For Visiting                         ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out
                                                                                                .println("Our Other Branches                                        ");
                                                                                System.out
                                                                                                .println("       ~Maninagar~ ~Navrangpura~ ~IIM Road~               ");
                                                                                System.out
                                                                                                .println("__________________________________________________________");
                                                                                break;
                                                                        case 2: {
                                                                                System.out.println(
                                                                                                "enter number of platters");
                                                                                int p_p = sc.nextInt();
                                                                                r.cook.addLast(p_p);
                                                                                System.out.println(
                                                                                                "Your order for item "
                                                                                                                + p_p
                                                                                                                + " has been placed...");
                                                                                t_cost = p_p * 300;
                                                                                Bill = t_cost + (t_cost * 0.05);
                                                                                System.out
                                                                                                .println("__________________________________________________________");
                                                                                System.out
                                                                                                .println("                      THALA'S RESTAURENT                  ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out.println(
                                                                                                "  Order        " + "  "
                                                                                                                + " Price per item"
                                                                                                                + "  "
                                                                                                                + "  Quantity    ");
                                                                                System.out
                                                                                                .println("__________________________________________________________");
                                                                                System.out.println("Punjabi platter"
                                                                                                + "  "
                                                                                                + "   300         "
                                                                                                + "       "
                                                                                                + p_p + "     ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out
                                                                                                .println("__________________________________________________________");
                                                                                System.out.println(
                                                                                                "                            Total Bill="
                                                                                                                + t_cost
                                                                                                                + "           ");
                                                                                System.out
                                                                                                .println("                                   CGST=2.5%              ");
                                                                                System.out
                                                                                                .println("                                  SGST=2.5%               ");
                                                                                System.out
                                                                                                .println("__________________________________________________________");
                                                                                System.out.println(
                                                                                                "                            Final Bill="
                                                                                                                + Bill
                                                                                                                + "             ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out
                                                                                                .println("             Thank U For Visiting                         ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out
                                                                                                .println("Our Other Branches                                        ");
                                                                                System.out
                                                                                                .println("       ~Maninagar~ ~Navrangpura~ ~IIM Road~               ");
                                                                                System.out
                                                                                                .println("__________________________________________________________");
                                                                        }
                                                                                break;
                                                                        case 3: {
                                                                                System.out.println(
                                                                                                "enter number of platters");
                                                                                int g_p = sc.nextInt();
                                                                                r.cook.addLast(g_p);
                                                                                System.out.println(
                                                                                                "Your order for item "
                                                                                                                + g_p
                                                                                                                + " has been placed...");
                                                                                t_cost = g_p * 250;
                                                                                Bill = t_cost + (t_cost * 0.05);
                                                                                System.out
                                                                                                .println("__________________________________________________________");
                                                                                System.out
                                                                                                .println("                      THALA'S RESTAURENT                  ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out.println(
                                                                                                " Order        " + "  "
                                                                                                                + " Price per item"
                                                                                                                + "  "
                                                                                                                + "  Quantity    ");
                                                                                System.out
                                                                                                .println("__________________________________________________________");
                                                                                System.out.println("Gujarati platter"
                                                                                                + "  "
                                                                                                + "   250         "
                                                                                                + "       "
                                                                                                + g_p + "    ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out
                                                                                                .println("__________________________________________________________");
                                                                                System.out.println(
                                                                                                "                            Total Bill="
                                                                                                                + t_cost
                                                                                                                + "           ");
                                                                                System.out
                                                                                                .println("                                   CGST=2.5%              ");
                                                                                System.out
                                                                                                .println("                                  SGST=2.5%               ");
                                                                                System.out
                                                                                                .println("__________________________________________________________");
                                                                                System.out.println(
                                                                                                "                            Final Bill="
                                                                                                                + Bill
                                                                                                                + "             ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out
                                                                                                .println("             Thank U For Visiting                         ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out
                                                                                                .println("Our Other Branches                                        ");
                                                                                System.out
                                                                                                .println("       ~Maninagar~ ~Navrangpura~ ~IIM Road~               ");
                                                                                System.out
                                                                                                .println("__________________________________________________________");
                                                                        }
                                                                                break;
                                                                        case 4: {
                                                                                System.out.println(
                                                                                                "enter number of platters");
                                                                                int r_p = sc.nextInt();
                                                                                r.cook.addLast(r_p);
                                                                                System.out.println(
                                                                                                "Your order for item "
                                                                                                                + r_p
                                                                                                                + " has been placed...");
                                                                                t_cost = r_p * 200;
                                                                                Bill = t_cost + (t_cost * 0.05);
                                                                                System.out
                                                                                                .println("__________________________________________________________");
                                                                                System.out
                                                                                                .println("                      THALA'S RESTAURENT                  ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out.println(
                                                                                                "  Order        " + "  "
                                                                                                                + " Price per item"
                                                                                                                + "  "
                                                                                                                + "  Quantity    ");
                                                                                System.out
                                                                                                .println("__________________________________________________________");
                                                                                System.out.println("Rajasthani platter"
                                                                                                + "  "
                                                                                                + "   200         "
                                                                                                + "       "
                                                                                                + r_p + "  ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out
                                                                                                .println("__________________________________________________________");
                                                                                System.out.println(
                                                                                                "                            Total Bill="
                                                                                                                + t_cost
                                                                                                                + "           ");
                                                                                System.out
                                                                                                .println("                                   CGST=2.5%              ");
                                                                                System.out
                                                                                                .println("                                  SGST=2.5%               ");
                                                                                System.out
                                                                                                .println("__________________________________________________________");
                                                                                System.out.println(
                                                                                                "                            Final Bill="
                                                                                                                + Bill
                                                                                                                + "             ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out
                                                                                                .println("             Thank U For Visiting                         ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out
                                                                                                .println("Our Other Branches                                        ");
                                                                                System.out
                                                                                                .println("       ~Maninagar~ ~Navrangpura~ ~IIM Road~               ");
                                                                                System.out
                                                                                                .println("__________________________________________________________");
                                                                        }
                                                                                break;
                                                                        case 5: {
                                                                                System.out.println(
                                                                                                "enter number of platters");
                                                                                int a_p = sc.nextInt();
                                                                                r.cook.addLast(a_p);
                                                                                System.out.println(
                                                                                                "Your order for item "
                                                                                                                + a_p
                                                                                                                + " has been placed...");
                                                                                t_cost = a_p * 350;
                                                                                Bill = t_cost + (t_cost * 0.05);
                                                                                System.out
                                                                                                .println("__________________________________________________________");
                                                                                System.out
                                                                                                .println("                      THALA'S RESTAURENT                  ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out.println(
                                                                                                " Order        " + "  "
                                                                                                                + " Price per item"
                                                                                                                + "  "
                                                                                                                + "  Quantity    ");
                                                                                System.out
                                                                                                .println("__________________________________________________________");
                                                                                System.out.println("Special platter"
                                                                                                + "  "
                                                                                                + "   350         "
                                                                                                + "       "
                                                                                                + a_p + "     ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out
                                                                                                .println("__________________________________________________________");
                                                                                System.out.println(
                                                                                                "                           Total Bill="
                                                                                                                + t_cost
                                                                                                                + "           ");
                                                                                System.out
                                                                                                .println("                                   CGST=2.5%              ");
                                                                                System.out
                                                                                                .println("                                  SGST=2.5%               ");
                                                                                System.out
                                                                                                .println("__________________________________________________________");
                                                                                System.out.println(
                                                                                                "                            Final Bill="
                                                                                                                + Bill
                                                                                                                + "             ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out
                                                                                                .println("             Thank U For Visiting                         ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out
                                                                                                .println("Our Other Branches                                        ");
                                                                                System.out
                                                                                                .println("       ~Maninagar~ ~Navrangpura~ ~IIM Road~               ");
                                                                                System.out
                                                                                                .println("__________________________________________________________");
                                                                        }
                                                                                break;
                                                                        case 6: {
                                                                                System.out.println("order done");
                                                                        }
                                                                                break;
                                                                        default:
                                                                                System.out.println(
                                                                                                "Please enter Valid Choice");
                                                                                break;
                                                                }
                                                        } while (c2 != 6);
                                                }
                                                        break;
                                                case 3: {
                                                        do {
                                                                System.out.println("Welcome to Thala's Restaurent");
                                                                System.out.println(
                                                                                "Please enter your Order For Take Away");
                                                                System.out.println("enter your choice of order");
                                                                System.out.println(
                                                                                "1.punjabi Combos-Price=300 + ParcelCharges/-");
                                                                System.out.println(
                                                                                "2.gujarati combos-Price=250 + ParcelCharges/-");
                                                                System.out.println(
                                                                                "3.rajasthani combos-Price=200 + ParcelCharges/-");
                                                                System.out.println(
                                                                                "4.our special combos-Price=350 + ParcelCharges/-");
                                                                System.out.println("6.exit");
                                                                c2 = sc.nextInt();
                                                                sc.nextLine();
                                                                switch (c2) {
                                                                        case 1: {
                                                                                System.out.println(
                                                                                                "enter number of platters");
                                                                                int p_p = sc.nextInt();
                                                                                r.cook.addLast(p_p);
                                                                                System.out.println(
                                                                                                "Your order for item "
                                                                                                                + p_p
                                                                                                                + " has been placed...");
                                                                                t_cost = p_p * 300;
                                                                                Bill = t_cost + (t_cost * 0.05) + 60;
                                                                                System.out
                                                                                                .println(" __________________________________________________________");
                                                                                System.out
                                                                                                .println("                      THALA'S RESTAURENT                  ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out.println(
                                                                                                "  Order        " + "  "
                                                                                                                + " Price per item"
                                                                                                                + "  "
                                                                                                                + "  Quantity    ");
                                                                                System.out
                                                                                                .println("__________________________________________________________");
                                                                                System.out.println("Punjabi platter"
                                                                                                + "  "
                                                                                                + "   300         "
                                                                                                + "       "
                                                                                                + p_p + "     ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out
                                                                                                .println("__________________________________________________________");
                                                                                System.out.println(
                                                                                                "                            Total Bill="
                                                                                                                + t_cost
                                                                                                                + "           ");
                                                                                System.out
                                                                                                .println("                                   CGST=2.5%              ");
                                                                                System.out
                                                                                                .println("                                  SGST=2.5%               ");
                                                                                System.out
                                                                                                .println("                       Cutlery Charges=10.0               ");
                                                                                System.out
                                                                                                .println("                 Delivery Partner cost=50.0               ");
                                                                                System.out
                                                                                                .println("__________________________________________________________");
                                                                                System.out.println(
                                                                                                "                            Final Bill="
                                                                                                                + Bill
                                                                                                                + "             ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out
                                                                                                .println("             Thank U For Visiting                         ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out
                                                                                                .println("Our Other Branches                                        ");
                                                                                System.out
                                                                                                .println("       ~Maninagar~ ~Navrangpura~ ~IIM Road~               ");
                                                                                System.out
                                                                                                .println("__________________________________________________________");
                                                                        }
                                                                                break;
                                                                        case 2: {
                                                                                System.out.println(
                                                                                                "enter number of platters");
                                                                                int g_p = sc.nextInt();
                                                                                r.cook.addLast(g_p);
                                                                                System.out.println(
                                                                                                "Your order for item "
                                                                                                                + g_p
                                                                                                                + " has been placed...");
                                                                                t_cost = g_p * 250;
                                                                                Bill = t_cost + (t_cost * 0.05) + 60;
                                                                                System.out
                                                                                                .println(" __________________________________________________________");
                                                                                System.out
                                                                                                .println("                      THALA'S RESTAURENT                  ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out.println(
                                                                                                "  Order        " + "  "
                                                                                                                + " Price per item"
                                                                                                                + "  "
                                                                                                                + "  Quantity    ");
                                                                                System.out
                                                                                                .println("__________________________________________________________");
                                                                                System.out.println("Gujarati platter"
                                                                                                + "  "
                                                                                                + "   250         "
                                                                                                + "       "
                                                                                                + g_p + "    ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out
                                                                                                .println("__________________________________________________________");
                                                                                System.out.println(
                                                                                                "                            Total Bill="
                                                                                                                + t_cost
                                                                                                                + "               ");
                                                                                System.out
                                                                                                .println("                                   CGST=2.5%              ");
                                                                                System.out
                                                                                                .println("                                  SGST=2.5%               ");
                                                                                System.out
                                                                                                .println("                       Cutlery Charges=10.0               ");
                                                                                System.out
                                                                                                .println("                 Delivery Partner cost=50.0               ");
                                                                                System.out
                                                                                                .println("__________________________________________________________");
                                                                                System.out.println(
                                                                                                "                            Final Bill="
                                                                                                                + Bill
                                                                                                                + "             ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out
                                                                                                .println("             Thank U For Visiting                         ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out
                                                                                                .println("Our Other Branches                                        ");
                                                                                System.out
                                                                                                .println("       ~Maninagar~ ~Navrangpura~ ~IIM Road~               ");
                                                                                System.out
                                                                                                .println("__________________________________________________________");
                                                                        }
                                                                                break;
                                                                        case 3: {
                                                                                System.out.println(
                                                                                                "enter number of platters");
                                                                                int r_p = sc.nextInt();
                                                                                r.cook.addLast(r_p);
                                                                                System.out.println(
                                                                                                "Your order for item "
                                                                                                                + r_p
                                                                                                                + " has been placed...");
                                                                                t_cost = r_p * 200;
                                                                                Bill = t_cost + (t_cost * 0.05) + 60;
                                                                                System.out
                                                                                                .println(" __________________________________________________________");
                                                                                System.out
                                                                                                .println("                      THALA'S RESTAURENT                  ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out.println(
                                                                                                "  Order        " + "  "
                                                                                                                + " Price per item"
                                                                                                                + "  "
                                                                                                                + "  Quantity    ");
                                                                                System.out
                                                                                                .println("__________________________________________________________");
                                                                                System.out.println("Rajasthani platter"
                                                                                                + "  "
                                                                                                + "   200         "
                                                                                                + "       "
                                                                                                + r_p + "  ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out
                                                                                                .println("__________________________________________________________");
                                                                                System.out.println(
                                                                                                "                            Total Bill="
                                                                                                                + t_cost
                                                                                                                + "           ");
                                                                                System.out
                                                                                                .println("                                   CGST=2.5%              ");
                                                                                System.out
                                                                                                .println("                                  SGST=2.5%               ");
                                                                                System.out
                                                                                                .println("                       Cutlery Charges=10.0               ");
                                                                                System.out
                                                                                                .println("                 Delivery Partner cost=50.0               ");
                                                                                System.out
                                                                                                .println("__________________________________________________________");
                                                                                System.out.println(
                                                                                                "                            Final Bill="
                                                                                                                + Bill
                                                                                                                + "             ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out
                                                                                                .println("             Thank U For Visiting                         ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out
                                                                                                .println("Our Other Branches                                        ");
                                                                                System.out
                                                                                                .println("       ~Maninagar~ ~Navrangpura~ ~IIM Road~               ");
                                                                                System.out
                                                                                                .println("__________________________________________________________");
                                                                        }
                                                                                break;
                                                                        case 4: {
                                                                                System.out.println(
                                                                                                "enter number of platters");
                                                                                int a_p = sc.nextInt();
                                                                                r.cook.addLast(a_p);
                                                                                System.out.println(
                                                                                                "Your order for item "
                                                                                                                + a_p
                                                                                                                + " has been placed...");
                                                                                t_cost = a_p * 350;
                                                                                Bill = t_cost + (t_cost * 0.05) + 60;
                                                                                System.out
                                                                                                .println(" __________________________________________________________");
                                                                                System.out
                                                                                                .println("                      THALA'S RESTAURENT                  ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out.println(
                                                                                                "  Order        " + "  "
                                                                                                                + " Price per item"
                                                                                                                + "  "
                                                                                                                + "  Quantity    ");
                                                                                System.out
                                                                                                .println("__________________________________________________________");
                                                                                System.out.println("Special platter"
                                                                                                + "  "
                                                                                                + "   350         "
                                                                                                + "       "
                                                                                                + a_p + "     ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out
                                                                                                .println("__________________________________________________________");
                                                                                System.out.println(
                                                                                                "                            Total Bill="
                                                                                                                + t_cost
                                                                                                                + "           ");
                                                                                System.out
                                                                                                .println("                                   CGST=2.5%              ");
                                                                                System.out
                                                                                                .println("                                  SGST=2.5%               ");
                                                                                System.out
                                                                                                .println("                       Cutlery Charges=10.0               ");
                                                                                System.out
                                                                                                .println("                 Delivery Partner cost=50.0               ");
                                                                                System.out
                                                                                                .println("__________________________________________________________");
                                                                                System.out.println(
                                                                                                "                            Final Bill="
                                                                                                                + Bill
                                                                                                                + "             ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out
                                                                                                .println("             Thank U For Visiting                         ");
                                                                                System.out
                                                                                                .println("                                                          ");
                                                                                System.out
                                                                                                .println("Our Other Branches                                        ");
                                                                                System.out
                                                                                                .println("       ~Maninagar~ ~Navrangpura~ ~IIM Road~               ");
                                                                                System.out
                                                                                                .println("__________________________________________________________");
                                                                        }
                                                                                break;
                                                                        case 6: {
                                                                                System.out.println("order done");
                                                                                break;
                                                                        }
                                                                        default:
                                                                                System.out.println(
                                                                                                "Please enter Valid Choice");
                                                                                break;
                                                                }
                                                        } while (c2 != 6);
                                                }
                                                        break;
                                                case 4: {
                                                        do {
                                                                System.out.println("Please Choice your Take Away ");
                                                                System.out.println("1.Swiggy");
                                                                System.out.println("2.Zomato");
                                                                System.out.println("6.Exit");
                                                                System.out.println("enter your choice");
                                                                c3 = sc.nextInt();
                                                                switch (c3) {
                                                                        case 1: {
                                                                                do {
                                                                                        System.out.println(
                                                                                                        "enter your choice of order");
                                                                                        System.out.println(
                                                                                                        "1.punjabi Combos-Price=350 + Delivery Charges/-");
                                                                                        System.out.println(
                                                                                                        "2.gujarati combos-Price=300 + Delivery Charges/-");
                                                                                        System.out.println(
                                                                                                        "3.rajasthani combos-Price=250 + Delivery Charges/-");
                                                                                        System.out.println(
                                                                                                        "4.our special combos-Price=400 + Delivery Charges/-");
                                                                                        System.out.println("6.exit");
                                                                                        c1 = sc.nextInt();
                                                                                        sc.nextLine();
                                                                                        switch (c1) {
                                                                                                case 1: {
                                                                                                        System.out.println(
                                                                                                                        "enter number of platters");
                                                                                                        int p_p = sc.nextInt();
                                                                                                        r.cook.addLast(p_p);
                                                                                                        System.out.println(
                                                                                                                        "Your order for item "
                                                                                                                                        + p_p
                                                                                                                                        + " has been placed...");
                                                                                                        t_cost = p_p * 350;
                                                                                                        Bill = t_cost + (t_cost
                                                                                                                        * 0.05)
                                                                                                                        + 60;
                                                                                                        System.out.println(
                                                                                                                        " __________________________________________________________");
                                                                                                        System.out.println(
                                                                                                                        "                      THALA'S RESTAURENT                  ");
                                                                                                        System.out.println(
                                                                                                                        "                                                          ");
                                                                                                        System.out.println(
                                                                                                                        "  Order        "
                                                                                                                                        + "  "
                                                                                                                                        + " Price per item"
                                                                                                                                        + "  "
                                                                                                                                        + "  Quantity    ");
                                                                                                        System.out.println(
                                                                                                                        "__________________________________________________________");
                                                                                                        System.out.println(
                                                                                                                        "Punjabi platter"
                                                                                                                                        + "  "
                                                                                                                                        + "   350         "
                                                                                                                                        + "       "
                                                                                                                                        + p_p
                                                                                                                                        + "     ");
                                                                                                        System.out.println(
                                                                                                                        "                                                          ");
                                                                                                        System.out.println(
                                                                                                                        "                                                          ");
                                                                                                        System.out.println(
                                                                                                                        "__________________________________________________________");
                                                                                                        System.out.println(
                                                                                                                        "                            Total Bill="
                                                                                                                                        + t_cost
                                                                                                                                        + "           ");
                                                                                                        System.out.println(
                                                                                                                        "                                   CGST=2.5%              ");
                                                                                                        System.out.println(
                                                                                                                        "                                  SGST=2.5%               ");
                                                                                                        System.out.println(
                                                                                                                        "                       Cutlery Charges=10.0               ");
                                                                                                        System.out.println(
                                                                                                                        "                 Delivery Partner cost=50.0               ");
                                                                                                        System.out.println(
                                                                                                                        "__________________________________________________________");
                                                                                                        System.out.println(
                                                                                                                        "                            Final Bill="
                                                                                                                                        + Bill
                                                                                                                                        + "             ");
                                                                                                        System.out.println(
                                                                                                                        "                                                          ");
                                                                                                        System.out.println(
                                                                                                                        "             Thank U For Visiting                         ");
                                                                                                        System.out.println(
                                                                                                                        "                                                          ");
                                                                                                        System.out.println(
                                                                                                                        "Our Other Branches                                        ");
                                                                                                        System.out.println(
                                                                                                                        "       ~Maninagar~ ~Navrangpura~ ~IIM Road~               ");
                                                                                                        System.out.println(
                                                                                                                        "__________________________________________________________");
                                                                                                }
                                                                                                        break;
                                                                                                case 2: {
                                                                                                        System.out.println(
                                                                                                                        "enter number of platters");
                                                                                                        int g_p = sc.nextInt();
                                                                                                        r.cook.addLast(g_p);
                                                                                                        System.out.println(
                                                                                                                        "Your order for item "
                                                                                                                                        + g_p
                                                                                                                                        + " has been placed...");
                                                                                                        t_cost = g_p * 300;
                                                                                                        Bill = t_cost + (t_cost
                                                                                                                        * 0.05)
                                                                                                                        + 60;
                                                                                                        System.out.println(
                                                                                                                        " __________________________________________________________");
                                                                                                        System.out.println(
                                                                                                                        "                      THALA'S RESTAURENT                 ");
                                                                                                        System.out.println(
                                                                                                                        "                                                         ");
                                                                                                        System.out.println(
                                                                                                                        " Order        " + "  "
                                                                                                                                        + " Price per item"
                                                                                                                                        + "  "
                                                                                                                                        + "  Quantity    ");
                                                                                                        System.out.println(
                                                                                                                        "_________________________________________________________");
                                                                                                        System.out.println(
                                                                                                                        "Gujarati platter"
                                                                                                                                        + "  "
                                                                                                                                        + "   300         "
                                                                                                                                        + "       "
                                                                                                                                        + g_p
                                                                                                                                        + "   ");
                                                                                                        System.out.println(
                                                                                                                        "                                                          ");
                                                                                                        System.out.println(
                                                                                                                        "                                                          ");
                                                                                                        System.out.println(
                                                                                                                        "__________________________________________________________");
                                                                                                        System.out.println(
                                                                                                                        "                            Total Bill="
                                                                                                                                        + t_cost
                                                                                                                                        + "           ");
                                                                                                        System.out.println(
                                                                                                                        "                                   CGST=2.5%              ");
                                                                                                        System.out.println(
                                                                                                                        "                                  SGST=2.5%               ");
                                                                                                        System.out.println(
                                                                                                                        "                       Cutlery Charges=10.0               ");
                                                                                                        System.out.println(
                                                                                                                        "                 Delivery Partner cost=50.0               ");
                                                                                                        System.out.println(
                                                                                                                        "__________________________________________________________");
                                                                                                        System.out.println(
                                                                                                                        "                           Final Bill="
                                                                                                                                        + Bill
                                                                                                                                        + "              ");
                                                                                                        System.out.println(
                                                                                                                        "                                                          ");
                                                                                                        System.out.println(
                                                                                                                        "             Thank U For Visiting                         ");
                                                                                                        System.out.println(
                                                                                                                        "                                                          ");
                                                                                                        System.out.println(
                                                                                                                        "Our Other Branches                                        ");
                                                                                                        System.out.println(
                                                                                                                        "       ~Maninagar~ ~Navrangpura~ ~IIM Road~               ");
                                                                                                        System.out.println(
                                                                                                                        "__________________________________________________________");
                                                                                                }
                                                                                                        break;
                                                                                                case 3: {
                                                                                                        System.out.println(
                                                                                                                        "enter number of platters");
                                                                                                        int r_p = sc.nextInt();
                                                                                                        r.cook.addLast(r_p);
                                                                                                        System.out.println(
                                                                                                                        "Your order for item "
                                                                                                                                        + r_p
                                                                                                                                        + " has been placed...");
                                                                                                        t_cost = r_p * 250;
                                                                                                        Bill = t_cost + (t_cost
                                                                                                                        * 0.05)
                                                                                                                        + 60;
                                                                                                        System.out.println(
                                                                                                                        " __________________________________________________________");
                                                                                                        System.out.println(
                                                                                                                        "                      THALA'S RESTAURENT                   ");
                                                                                                        System.out.println(
                                                                                                                        "                                                           ");
                                                                                                        System.out.println(
                                                                                                                        "  Order        "
                                                                                                                                        + "  "
                                                                                                                                        + " Price per item"
                                                                                                                                        + "  "
                                                                                                                                        + "  Quantity              ");
                                                                                                        System.out.println(
                                                                                                                        "__________________________________________________________ ");
                                                                                                        System.out.println(
                                                                                                                        " Rajasthani platter"
                                                                                                                                        + "  "
                                                                                                                                        + "   250         "
                                                                                                                                        + "       "
                                                                                                                                        + r_p
                                                                                                                                        + "                ");
                                                                                                        System.out.println(
                                                                                                                        "                                                           ");
                                                                                                        System.out.println(
                                                                                                                        "                                                           ");
                                                                                                        System.out.println(
                                                                                                                        " __________________________________________________________ ");
                                                                                                        System.out.println(
                                                                                                                        "                            Total Bill="
                                                                                                                                        + t_cost
                                                                                                                                        + "                ");
                                                                                                        System.out.println(
                                                                                                                        "                                   CGST=2.5%               ");
                                                                                                        System.out.println(
                                                                                                                        "                                  SGST=2.5%                ");
                                                                                                        System.out.println(
                                                                                                                        "                       Cutlery Charges=10.0                ");
                                                                                                        System.out.println(
                                                                                                                        "                 Delivery Partner cost=50.0                ");
                                                                                                        System.out.println(
                                                                                                                        "__________________________________________________________ ");
                                                                                                        System.out.println(
                                                                                                                        "                            Final Bill="
                                                                                                                                        + Bill
                                                                                                                                        + "               ");
                                                                                                        System.out.println(
                                                                                                                        "                                                           ");
                                                                                                        System.out.println(
                                                                                                                        "             Thank U For Visiting                          ");
                                                                                                        System.out.println(
                                                                                                                        "                                                           ");
                                                                                                        System.out.println(
                                                                                                                        " Our Other Branches                                        ");
                                                                                                        System.out.println(
                                                                                                                        "       ~Maninagar~ ~Navrangpura~ ~IIM Road~                ");
                                                                                                        System.out.println(
                                                                                                                        " __________________________________________________________");
                                                                                                }
                                                                                                        break;
                                                                                                case 4: {
                                                                                                        System.out.println(
                                                                                                                        "enter number of platters");
                                                                                                        int a_p = sc.nextInt();
                                                                                                        r.cook.addLast(a_p);
                                                                                                        System.out.println(
                                                                                                                        "Your order for item "
                                                                                                                                        + a_p
                                                                                                                                        + " has been placed...");
                                                                                                        t_cost = a_p * 400;
                                                                                                        Bill = t_cost + (t_cost
                                                                                                                        * 0.05)
                                                                                                                        + 60;
                                                                                                        System.out.println(
                                                                                                                        " __________________________________________________________");
                                                                                                        System.out.println(
                                                                                                                        "                      THALA'S RESTAURENT                  ");
                                                                                                        System.out.println(
                                                                                                                        "                                                          ");
                                                                                                        System.out.println(
                                                                                                                        "  Order        "
                                                                                                                                        + "  "
                                                                                                                                        + " Price per item"
                                                                                                                                        + "  "
                                                                                                                                        + "  Quantity              ");
                                                                                                        System.out.println(
                                                                                                                        "__________________________________________________________");
                                                                                                        System.out.println(
                                                                                                                        " Special platter"
                                                                                                                                        + "  "
                                                                                                                                        + "   400         "
                                                                                                                                        + "       "
                                                                                                                                        + a_p
                                                                                                                                        + "                  ");
                                                                                                        System.out.println(
                                                                                                                        "                                                          ");
                                                                                                        System.out.println(
                                                                                                                        "                                                          ");
                                                                                                        System.out.println(
                                                                                                                        "__________________________________________________________");
                                                                                                        System.out.println(
                                                                                                                        "                            Total Bill="
                                                                                                                                        + t_cost
                                                                                                                                        + "                ");
                                                                                                        System.out.println(
                                                                                                                        "                                   CGST=2.5%              ");
                                                                                                        System.out.println(
                                                                                                                        "                                  SGST=2.5%               ");
                                                                                                        System.out.println(
                                                                                                                        "                       Cutlery Charges=10.0               ");
                                                                                                        System.out.println(
                                                                                                                        "                 Delivery Partner cost=50.0               ");
                                                                                                        System.out.println(
                                                                                                                        "__________________________________________________________");
                                                                                                        System.out.println(
                                                                                                                        "                            Final Bill="
                                                                                                                                        + Bill
                                                                                                                                        + "              ");
                                                                                                        System.out.println(
                                                                                                                        "                                                          ");
                                                                                                        System.out.println(
                                                                                                                        "             Thank U For Visiting                         ");
                                                                                                        System.out.println(
                                                                                                                        "                                                          ");
                                                                                                        System.out.println(
                                                                                                                        "Our Other Branches                                        ");
                                                                                                        System.out.println(
                                                                                                                        "       ~Maninagar~ ~Navrangpura~ ~IIM Road~               ");
                                                                                                        System.out.println(
                                                                                                                        "__________________________________________________________");
                                                                                                }
                                                                                                        break;
                                                                                                case 6: {
                                                                                                        System.out.println(
                                                                                                                        "order done");
                                                                                                        break;
                                                                                                }
                                                                                                default:
                                                                                                        System.out.println(
                                                                                                                        "Please enter Valid Choice");
                                                                                                        break;
                                                                                        }
                                                                                } while (c1 != 6);
                                                                        }
                                                                                break;
                                                                        case 2: {
                                                                                do {
                                                                                        System.out.println(
                                                                                                        "enter your choice of order");
                                                                                        System.out.println(
                                                                                                        "1.punjabi Combos-Price=350 + Delivery Charges/-");
                                                                                        System.out.println(
                                                                                                        "2.gujarati combos-Price=300 + Delivery Charges/-");
                                                                                        System.out.println(
                                                                                                        "3.rajasthani combos-Price=250 + Delivery Charges/-");
                                                                                        System.out.println(
                                                                                                        "4.our special combos-Price=400 + Delivery Charges/-");
                                                                                        System.out.println("6.exit");
                                                                                        c1 = sc.nextInt();
                                                                                        sc.nextLine();
                                                                                        switch (c1) {
                                                                                                case 1: {
                                                                                                        System.out.println(
                                                                                                                        "enter number of platters");
                                                                                                        int p_p = sc.nextInt();
                                                                                                        r.cook.addLast(p_p);
                                                                                                        System.out.println(
                                                                                                                        "Your order for item "
                                                                                                                                        + p_p
                                                                                                                                        + " has been placed...");
                                                                                                        t_cost = p_p * 350;
                                                                                                        Bill = t_cost + (t_cost
                                                                                                                        * 0.05)
                                                                                                                        + 60;
                                                                                                        System.out.println(
                                                                                                                        " __________________________________________________________");
                                                                                                        System.out.println(
                                                                                                                        "                     THALA'S RESTAURENT                  ");
                                                                                                        System.out.println(
                                                                                                                        "                                                          ");
                                                                                                        System.out.println(
                                                                                                                        "  Order        "
                                                                                                                                        + "  "
                                                                                                                                        + " Price per item"
                                                                                                                                        + "  "
                                                                                                                                        + "  Quantity              ");
                                                                                                        System.out.println(
                                                                                                                        "__________________________________________________________");
                                                                                                        System.out.println(
                                                                                                                        "Punjabi platter"
                                                                                                                                        + "  "
                                                                                                                                        + "   350         "
                                                                                                                                        + "       "
                                                                                                                                        + p_p
                                                                                                                                        + "                  ");
                                                                                                        System.out.println(
                                                                                                                        "                                                          ");
                                                                                                        System.out.println(
                                                                                                                        "                                                          ");
                                                                                                        System.out.println(
                                                                                                                        "__________________________________________________________");
                                                                                                        System.out.println(
                                                                                                                        "                           Total Bill="
                                                                                                                                        + t_cost
                                                                                                                                        + "                ");
                                                                                                        System.out.println(
                                                                                                                        "                                   CGST=2.5%              ");
                                                                                                        System.out.println(
                                                                                                                        "                                  SGST=2.5%               ");
                                                                                                        System.out.println(
                                                                                                                        "                       Cutlery Charges=10.0               ");
                                                                                                        System.out.println(
                                                                                                                        "                 Delivery Partner cost=50.0               ");
                                                                                                        System.out.println(
                                                                                                                        "__________________________________________________________");
                                                                                                        System.out.println(
                                                                                                                        "                            Final Bill="
                                                                                                                                        + Bill
                                                                                                                                        + "             ");
                                                                                                        System.out.println(
                                                                                                                        "                                                          ");
                                                                                                        System.out.println(
                                                                                                                        "             Thank U For Visiting                         ");
                                                                                                        System.out.println(
                                                                                                                        "                                                          ");
                                                                                                        System.out.println(
                                                                                                                        "Our Other Branches                                        ");
                                                                                                        System.out.println(
                                                                                                                        "       ~Maninagar~ ~Navrangpura~ ~IIM Road~               ");
                                                                                                        System.out.println(
                                                                                                                        "__________________________________________________________");
                                                                                                }
                                                                                                        break;
                                                                                                case 2: {
                                                                                                        System.out.println(
                                                                                                                        "enter number of platters");
                                                                                                        int g_p = sc.nextInt();
                                                                                                        r.cook.addLast(g_p);
                                                                                                        System.out.println(
                                                                                                                        "Your order for item "
                                                                                                                                        + g_p
                                                                                                                                        + " has been placed...");
                                                                                                        t_cost = g_p * 300;
                                                                                                        Bill = t_cost + (t_cost
                                                                                                                        * 0.05)
                                                                                                                        + 60;
                                                                                                        System.out.println(
                                                                                                                        " __________________________________________________________");
                                                                                                        System.out.println(
                                                                                                                        "                      THALA'S RESTAURENT                  ");
                                                                                                        System.out.println(
                                                                                                                        "                                                          ");
                                                                                                        System.out.println(
                                                                                                                        "  Order        "
                                                                                                                                        + "  "
                                                                                                                                        + " Price per item"
                                                                                                                                        + "  "
                                                                                                                                        + "  Quantity    ");
                                                                                                        System.out.println(
                                                                                                                        "__________________________________________________________");
                                                                                                        System.out.println(
                                                                                                                        "Gujarati platter"
                                                                                                                                        + "  "
                                                                                                                                        + "   300         "
                                                                                                                                        + "       "
                                                                                                                                        + g_p
                                                                                                                                        + "    ");
                                                                                                        System.out.println(
                                                                                                                        "                                                          ");
                                                                                                        System.out.println(
                                                                                                                        "                                                          ");
                                                                                                        System.out.println(
                                                                                                                        "__________________________________________________________");
                                                                                                        System.out.println(
                                                                                                                        "                            Total Bill="
                                                                                                                                        + t_cost
                                                                                                                                        + "           ");
                                                                                                        System.out.println(
                                                                                                                        "                                   CGST=2.5%              ");
                                                                                                        System.out.println(
                                                                                                                        "                       Cutlery Charges=10.0               ");
                                                                                                        System.out.println(
                                                                                                                        "                                  SGST=2.5%               ");
                                                                                                        System.out.println(
                                                                                                                        "                 Delivery Partner cost=50.0               ");
                                                                                                        System.out.println(
                                                                                                                        "__________________________________________________________");
                                                                                                        System.out.println(
                                                                                                                        "                            Final Bill="
                                                                                                                                        + Bill
                                                                                                                                        + "             ");
                                                                                                        System.out.println(
                                                                                                                        "                                                          ");
                                                                                                        System.out.println(
                                                                                                                        "             Thank U For Visiting                         ");
                                                                                                        System.out.println(
                                                                                                                        "                                                          ");
                                                                                                        System.out.println(
                                                                                                                        "Our Other Branches                                        ");
                                                                                                        System.out.println(
                                                                                                                        "       ~Maninagar~ ~Navrangpura~ ~IIM Road~               ");
                                                                                                        System.out.println(
                                                                                                                        "__________________________________________________________");
                                                                                                }
                                                                                                        break;
                                                                                                case 3: {
                                                                                                        System.out.println(
                                                                                                                        "enter number of platters");
                                                                                                        int r_p = sc.nextInt();
                                                                                                        r.cook.addLast(r_p);
                                                                                                        System.out.println(
                                                                                                                        "Your order for item "
                                                                                                                                        + r_p
                                                                                                                                        + " has been placed...");
                                                                                                        t_cost = r_p * 250;
                                                                                                        Bill = t_cost + (t_cost
                                                                                                                        * 0.05)
                                                                                                                        + 60;
                                                                                                        System.out.println(
                                                                                                                        " __________________________________________________________");
                                                                                                        System.out.println(
                                                                                                                        "                      THALA'S RESTAURENT                  ");
                                                                                                        System.out.println(
                                                                                                                        "                                                          ");
                                                                                                        System.out.println(
                                                                                                                        "  Order        "
                                                                                                                                        + "  "
                                                                                                                                        + " Price per item"
                                                                                                                                        + "  "
                                                                                                                                        + "  Quantity    ");
                                                                                                        System.out.println(
                                                                                                                        "__________________________________________________________");
                                                                                                        System.out.println(
                                                                                                                        "Rajasthani platter"
                                                                                                                                        + "  "
                                                                                                                                        + "   250         "
                                                                                                                                        + "       "
                                                                                                                                        + r_p
                                                                                                                                        + "  ");
                                                                                                        System.out.println(
                                                                                                                        "                                                          ");
                                                                                                        System.out.println(
                                                                                                                        "                                                          ");
                                                                                                        System.out.println(
                                                                                                                        "__________________________________________________________");
                                                                                                        System.out.println(
                                                                                                                        "                           Total Bill="
                                                                                                                                        + t_cost
                                                                                                                                        + "           ");
                                                                                                        System.out.println(
                                                                                                                        "                                   CGST=2.5%              ");
                                                                                                        System.out.println(
                                                                                                                        "                                  SGST=2.5%               ");
                                                                                                        System.out.println(
                                                                                                                        "                       Cutlery Charges=10.0               ");
                                                                                                        System.out.println(
                                                                                                                        "                 Delivery Partner cost=50.0               ");
                                                                                                        System.out.println(
                                                                                                                        "__________________________________________________________");
                                                                                                        System.out.println(
                                                                                                                        "                            Final Bill="
                                                                                                                                        + Bill
                                                                                                                                        + "             ");
                                                                                                        System.out.println(
                                                                                                                        "                                                          ");
                                                                                                        System.out.println(
                                                                                                                        "             Thank U For Visiting                         ");
                                                                                                        System.out.println(
                                                                                                                        "                                                         ");
                                                                                                        System.out.println(
                                                                                                                        "Our Other Branches                                        ");
                                                                                                        System.out.println(
                                                                                                                        "       ~Maninagar~ ~Navrangpura~ ~IIM Road~               ");
                                                                                                        System.out.println(
                                                                                                                        "__________________________________________________________");
                                                                                                }
                                                                                                        break;
                                                                                                case 4: {
                                                                                                        System.out.println(
                                                                                                                        "enter number of platters");
                                                                                                        int a_p = sc.nextInt();
                                                                                                        r.cook.addLast(a_p);
                                                                                                        System.out.println(
                                                                                                                        "Your order for item "
                                                                                                                                        + a_p
                                                                                                                                        + " has been placed...");
                                                                                                        t_cost = a_p * 400;
                                                                                                        Bill = t_cost + (t_cost
                                                                                                                        * 0.05)
                                                                                                                        + 60;
                                                                                                        System.out.println(
                                                                                                                        " __________________________________________________________");
                                                                                                        System.out.println(
                                                                                                                        "                      THALA'S RESTAURENT                  ");
                                                                                                        System.out.println(
                                                                                                                        "                                                          ");
                                                                                                        System.out.println(
                                                                                                                        "  Order        "
                                                                                                                                        + "  "
                                                                                                                                        + " Price per item"
                                                                                                                                        + "  "
                                                                                                                                        + "  Quantity    ");
                                                                                                        System.out.println(
                                                                                                                        "__________________________________________________________");
                                                                                                        System.out.println(
                                                                                                                        "Special platter"
                                                                                                                                        + "  "
                                                                                                                                        + "   400         "
                                                                                                                                        + "       "
                                                                                                                                        + a_p
                                                                                                                                        + "     ");
                                                                                                        System.out.println(
                                                                                                                        "                                                          ");
                                                                                                        System.out.println(
                                                                                                                        "                                                          ");
                                                                                                        System.out.println(
                                                                                                                        "__________________________________________________________");
                                                                                                        System.out.println(
                                                                                                                        "                            Total Bill="
                                                                                                                                        + t_cost
                                                                                                                                        + "           ");
                                                                                                        System.out.println(
                                                                                                                        "                                   CGST=2.5%              ");
                                                                                                        System.out.println(
                                                                                                                        "                                  SGST=2.5%               ");
                                                                                                        System.out.println(
                                                                                                                        "                       Cutlery Charges=10.0               ");
                                                                                                        System.out.println(
                                                                                                                        "                 Delivery Partner cost=50.0               ");
                                                                                                        System.out.println(
                                                                                                                        "__________________________________________________________");
                                                                                                        System.out.println(
                                                                                                                        "                            Final Bill="
                                                                                                                                        + Bill
                                                                                                                                        + "             ");
                                                                                                        System.out.println(
                                                                                                                        "                                                          ");
                                                                                                        System.out.println(
                                                                                                                        "             Thank U For Visiting                         ");
                                                                                                        System.out.println(
                                                                                                                        "                                                          ");
                                                                                                        System.out.println(
                                                                                                                        "Our Other Branches                                        ");
                                                                                                        System.out.println(
                                                                                                                        "       ~Maninagar~ ~Navrangpura~ ~IIM Road~               ");
                                                                                                        System.out.println(
                                                                                                                        "__________________________________________________________");
                                                                                                }
                                                                                                        break;
                                                                                                case 6: {
                                                                                                        System.out.println(
                                                                                                                        "order done");
                                                                                                }
                                                                                                default:
                                                                                                        System.out.println(
                                                                                                                        "Please enter Valid Choice");
                                                                                        }
                                                                                } while (c1 != 6);
                                                                        }
                                                                                break;
                                                                        case 6:
                                                                                System.out.println(
                                                                                                "Exiting Thank You for Visiting");
                                                                                break;
                                                                        default:
                                                                                System.out.println(
                                                                                                "Please enter Valid Choice");
                                                                                break;
                                                                }
                                                                break;
                                                        } while (ch != 6);

                                                }
                                                        break;
                                        }
                                        break;
                                case 2:
                                        do {
                                                System.out.println("\nHotel Management System Menu:");
                                                System.out.println("1. View Available Rooms");
                                                System.out.println("2. Book Room(s)");
                                                System.out.println("3. Book Room by Type");
                                                System.out.println("4. Cancel Booking");
                                                System.out.println("5. Calculate Total Amount");
                                                System.out.println("6. Exit");

                                                Choi = sc.nextInt();
                                                sc.nextLine(); // Consume newline

                                                switch (Choi) {
                                                        case 1:
                                                                hotelSystem.displayAvailableRooms();
                                                                break;
                                                        case 2:
                                                                System.out.print(
                                                                                "Enter the number of rooms you want to book: ");
                                                                int numRoomsToBook = sc.nextInt();
                                                                sc.nextLine(); // Consume newline
                                                                hotelSystem.bookRoom(numRoomsToBook);
                                                                break;
                                                        case 3:
                                                                hotelSystem.bookRoomByType();
                                                                break;
                                                        case 4:
                                                                System.out.print(
                                                                                "Enter the room number you want to cancel the booking for: ");
                                                                int cancelRoomNumber = sc.nextInt();
                                                                sc.nextLine(); // Consume newline
                                                                hotelSystem.cancelBooking(cancelRoomNumber);
                                                                break;
                                                        case 5:
                                                                System.out.print(
                                                                                "Enter the room number to calculate total amount: ");
                                                                int calcRoomNumber = sc.nextInt();
                                                                sc.nextLine(); // Consume newline
                                                                double calcTotalAmount = hotelSystem
                                                                                .calculateTotalAmount(calcRoomNumber);
                                                                if (calcTotalAmount >= 0) {
                                                                        System.out.println("Total Amount: $"
                                                                                        + calcTotalAmount);
                                                                } else {
                                                                        System.out
                                                                                        .println("Room " + calcRoomNumber
                                                                                                        + " is not available or does not exist.");
                                                                }
                                                                break;
                                                        case 6:
                                                                System.out.println(
                                                                                "Exiting the Hotel Management System. Goodbye!");
                                                                System.out.println();
                                                                break;
                                                        default:
                                                                System.out.println(
                                                                                "Invalid choice. Please select a valid option.");
                                                }
                                        } while (Choi != 6);
                                        break;
                                case 6:
                                        System.out.println("Exiting Program");
                                        break;
                                default:
                                        System.out.println("Invalid choice");
                        }
                } while (Choice != 6);
                sc.close();
        }

}
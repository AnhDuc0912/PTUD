package ui.component;

import java.util.ArrayList;
import java.util.List;

public class Carriage {
    private List<Cabin> cabins;

    public Carriage() {
        cabins = new ArrayList<>();
    }

    // Tạo một toa với 50 chỗ
    public static Carriage createCarriageWith50Seats() {
        Carriage carriage = new Carriage();
        Cabin cabin = Cabin.createFiftySeaterCabin();
        carriage.addCabin(cabin);
        return carriage;
    }

    // Tạo một toa với 8 khoang, mỗi khoang có 6 chỗ
    public static Carriage createCarriageWith8Cabins6Seats() {
        Carriage carriage = new Carriage();
        for (int i = 0; i < 8; i++) {
            List<Seat> seats = new ArrayList<>();
            for (int j = 0; j < 6; j++) {
                seats.add(new Seat(j + 1)); // Tạo mới một đối tượng Seat cho mỗi ghế
            }
            carriage.addCabin(new Cabin(seats));
        }
        return carriage;
    }

    // Tạo một toa với 8 khoang, mỗi khoang có 4 chỗ
    public static Carriage createCarriageWith8Cabins4Seats() {
        Carriage carriage = new Carriage();
        for (int i = 0; i < 8; i++) {
            List<Seat> seats = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                seats.add(new Seat(j + 1)); // Tạo mới một đối tượng Seat cho mỗi ghế
            }
            carriage.addCabin(new Cabin(seats));
        }
        return carriage;
    }

    public void addCabin(Cabin cabin) {
        cabins.add(cabin);
    }

    public List<Cabin> getCabins() {
        return cabins;
    }

    public int getTotalNumberOfSeats() {
        int totalSeats = 0;
        for (Cabin cabin : cabins) {
            totalSeats += cabin.getNumberOfSeats();
        }
        return totalSeats;
    }
    public static void main(String[] args) {
        // Tạo một toa với 50 chỗ
        Carriage carriage1 = Carriage.createCarriageWith50Seats();
        System.out.println("Carriage 1:");
        displayCarriageInfo(carriage1);

        // Tạo một toa với 8 khoang, mỗi khoang có 6 chỗ
        Carriage carriage2 = Carriage.createCarriageWith8Cabins6Seats();
        System.out.println("\nCarriage 2:");
        displayCarriageInfo(carriage2);

        // Tạo một toa với 8 khoang, mỗi khoang có 4 chỗ
        Carriage carriage3 = Carriage.createCarriageWith8Cabins4Seats();
        System.out.println("\nCarriage 3:");
        displayCarriageInfo(carriage3);
    }

    public static void displayCarriageInfo(Carriage carriage) {
        List<Cabin> cabins = carriage.getCabins();
        int totalSeats = carriage.getTotalNumberOfSeats();
        System.out.println("Total number of seats: " + totalSeats);
        System.out.println("Number of cabins: " + cabins.size());
        for (int i = 0; i < cabins.size(); i++) {
            Cabin cabin = cabins.get(i);
            int cabinNumber = i + 1;
            System.out.println("Cabin " + cabinNumber + ":");
            System.out.println("   Number of seats: " + cabin.getNumberOfSeats());
        }
    }
}

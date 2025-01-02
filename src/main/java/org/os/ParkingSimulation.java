// Team Members:
// Abdelrahman Mostafa Sayed - 20220197
// Omar Mohamed El-Sayed - 20221109
// Donia Kareem Mohamed - 20221051
// Asmaa Yasser Hussein - 20221022

package org.os;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ParkingSimulation {

    // Static ParkingLot instance and list to store all cars in simulation
    static ParkingLot lot = new ParkingLot();
    static List<Car> cars = new ArrayList<>();

    // Method to parse input line and create Car instance
    private static Car parseInput(String line) {
        int gate = 0;
        int id = 0;
        int arriveTime = 0;
        int parkingDuration = 0;

        // Split line by commas to extract car details
        String[] parts = line.split(",");

        // Loop through parts to identify and assign each parameter
        for (String part : parts) {
            part = part.trim();
            if (part.startsWith("Gate")) {
                gate = Integer.parseInt(part.split(" ")[1]);
            }
            else if (part.startsWith("Car")) {
                id = Integer.parseInt(part.split(" ")[1]);
            }
            else if (part.startsWith("Arrive")) {
                arriveTime = Integer.parseInt(part.split(" ")[1]);
            }
            else if (part.startsWith("Parks")) {
                parkingDuration = Integer.parseInt(part.split(" ")[1]);
            }
        }
        return new Car(gate, id, arriveTime, parkingDuration, lot); // Create a new Car object with parsed details
    }

    // Method to read car data from a file
    private static void readFromFile(String fileName) {
        try {
            File file = new File(fileName);
            Scanner fileScanner = new Scanner(file);

            // Read each line from file, parse it, and add to car list
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                cars.add(parseInput(line));
            }
            fileScanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
            e.printStackTrace();
        }
    }

    // Main method to initiate parking simulation
    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);

        System.out.print("Welcome to Parking System Simulator, Please enter file name: ");
        String fileName = sc.nextLine();

        readFromFile(fileName); // Load car data from file

        // Start each car's thread to simulate arrival and parking
        for (Car car : cars) {
            car.start();
        }

        // Wait for all car threads to complete
        for (Car car : cars) {
            car.join();
        }

        Thread.sleep(1000);

        // Display simulation summary
        lot.printSummary();

        sc.close();
    }
}

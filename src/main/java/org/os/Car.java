package org.os;

class Car extends Thread {
    private final int id; // Unique identifier for the car
    private final int gate; // Gate number through which the car enters
    private final int arrivalTime; // Time after which the car arrives at the parking lot (in seconds)
    private final int parkDuration; // Duration for which the car will stay parked (in seconds)
    private final ParkingLot parkingLot; // Reference to the shared ParkingLot instance

    // Constructor to initialize the car's entry details
    public Car(int gate, int id, int arrivalTime, int parkDuration, ParkingLot parkingLot) {
        this.id = id;
        this.gate = gate;
        this.arrivalTime = arrivalTime;
        this.parkDuration = parkDuration;
        this.parkingLot = parkingLot;
    }

    @Override
    public void run() {
        try {
            // Simulate arrival delay by waiting until the specified arrival time
            Thread.sleep(arrivalTime * 1000);

            // Create a unique name for the car based on its ID and gate
            String carName = "Car " + id + " from Gate " + gate;

            // Attempt to park the car in the parking lot
            parkingLot.parkCar(carName, parkDuration, arrivalTime, gate);
        } catch (InterruptedException e) {
            e.printStackTrace(); // Handle interruption in the simulation
        }
    }
}

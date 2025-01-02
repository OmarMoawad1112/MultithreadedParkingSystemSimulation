package org.os;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

class ParkingLot {
    int size = 4;
    private final Semaphore space = new Semaphore(4);

    private final AtomicInteger occupiedSpots = new AtomicInteger(0); // Tracks currently parked cars
    private final AtomicInteger totalCarsServed = new AtomicInteger(0); // Tracks total cars served
    private final ReentrantLock printLock = new ReentrantLock();
    static Gate g1 = new Gate();
    static Gate g2 = new Gate();
    static Gate g3 = new Gate();

    // Method to simulate parking a car
    public void parkCar(String carName, int parkDuration, int arrivalTime, int gate) throws InterruptedException {

        if(gate == 1){ g1.incrementServedCars();}
        else if(gate == 2){ g2.incrementServedCars();}
        else if (gate == 3 ) { g3.incrementServedCars(); }

        printLock.lock();
        System.out.println(carName + " arrived at time " + arrivalTime);

        long startWaitTime = System.currentTimeMillis();

        // Try to park the car
        if (space.getvalue() > 0) { // If a spot is available, park immediately
            space.P(); // Acquire a parking spot
            log(carName + " parked. (Parking Status: " + occupiedSpots.incrementAndGet() + " spots occupied)");
            printLock.unlock();

            totalCarsServed.incrementAndGet();

            // Simulate the parking duration
            Thread.sleep(parkDuration * 1000);

            // Car leaves
            occupiedSpots.decrementAndGet();
            log(carName + " left after " + parkDuration + " units of time. (Parking Status: " + occupiedSpots.get() + " spots occupied)");

            space.V(); // Release the parking spot
        } else {
            // Car waits for a spot
            log(carName + " waiting for a spot.");
            printLock.unlock();
            space.P(); // Wait until a spot is available
            long waitedTime = (System.currentTimeMillis() - startWaitTime+(999)) / 1000;
            log(carName + " parked after waiting for " + waitedTime + " units of time. (Parking Status: " + occupiedSpots.incrementAndGet() + " spots occupied)");

            totalCarsServed.incrementAndGet();
            // Simulate parking duration
            Thread.sleep(parkDuration * 1000);
            // Car leaves
            occupiedSpots.decrementAndGet();
            log(carName + " left after " + parkDuration + " units of time. (Parking Status: " + occupiedSpots.get() + " spots occupied)");

            space.V(); // Release the parking spot
        }
    }

    private void log(String message) {
        System.out.println(message);
    }



    public void printSummary(){
        System.out.println("\nSimulation finished. \nTotal cars served: " + totalCarsServed.get() );
        System.out.println("Current cars in parking = " + occupiedSpots.get() );
        System.out.println("Details: ");
        System.out.println("Gate 1 served " + g1.getServedCars() + " cars.");
        System.out.println("Gate 2 served " + g2.getServedCars() + " cars.");
        System.out.println("Gate 3 served " + g3.getServedCars() + " cars.");
    }
}

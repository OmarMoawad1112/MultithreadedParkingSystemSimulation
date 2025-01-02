package org.os;

public class Gate {
    int servedCars;

    Gate() {
        servedCars = 0; // Initialize served car count to zero
    }

    public void incrementServedCars() {
        servedCars++; // Increment served cars count
    }

    public int getServedCars() {
        return servedCars; // Return total served cars
    }
}

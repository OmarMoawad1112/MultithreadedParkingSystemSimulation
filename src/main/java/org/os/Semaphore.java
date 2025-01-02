package org.os;

class Semaphore {
    private int value;

    // Initialize semaphore with a given number of permits
    public Semaphore(int permits) {
        this.value = permits;
    }

    // Acquire a permit, blocking if none are available
    public synchronized void P() throws InterruptedException {
        while (value == 0) {
            wait(); // Block until a permit is available
        }
        value--; // Decrease the permit count
    }

    // Release a permit, waking up a waiting thread if necessary
    public synchronized void V() {
        value++; // Increase the permit count
        notify(); // Notify a waiting thread
    }

    // Get current permit count (for debugging)
    public synchronized int getvalue() {
        return value;
    }
}

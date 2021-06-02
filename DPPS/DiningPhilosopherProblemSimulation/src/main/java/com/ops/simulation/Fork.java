package com.ops.simulation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Fork {

    private static Logger log = LoggerFactory.getLogger(Fork.class);

    private final int id;
    private volatile Philosopher currentOwner;

    public Fork(int id){
        this.id = id;
        currentOwner = null;
    }



    public synchronized void getPickedUpBy(Philosopher philosopher) {

        log.info("{} wants to pick up {}", philosopher, this);
        while (!isAvailable()) {
            try {
                log.info("{} waiting to pick up {}", philosopher, this);
                wait();
            } catch (InterruptedException e) {
                log.error("Error when {} was waiting for a fork", philosopher, e);
            }
        }
        setCurrentOwner(philosopher);
        log.info("{} picked up {}", philosopher, this);


    }

    public synchronized void getPutDownBy(Philosopher philosopher) {
        if(!currentOwner.equals(philosopher)){
            log.error("{} tries to put down fork it does not own: {}", philosopher, this);
            return;
        }
        log.info("{} puts down {}", philosopher, this);
        setCurrentOwner(null);
        notifyAll();
    }

    public boolean isAvailable() {
        return currentOwner == null;
    }

    public int getId() {
        return id;
    }

    public Philosopher getCurrentOwner() {
        return currentOwner;
    }

    public void setCurrentOwner(Philosopher currentOwner) {
        this.currentOwner = currentOwner;
    }

    @Override
    public String toString() {
        return "Fork " + id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fork fork = (Fork) o;

        return id == fork.id;

    }



}

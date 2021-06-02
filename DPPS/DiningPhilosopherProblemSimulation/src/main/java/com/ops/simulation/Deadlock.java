package com.ops.simulation;


public class Deadlock extends Philosopher {

    public Deadlock(int id, Table table, Fork leftFork, Fork rightFork) {
        super(id, table, leftFork, rightFork);
    }

    protected void pickUpForks() {
        pickUpLeftFork();
        pickUpRightFork();
    }

    protected void putDownForks() {
        putDownLeftFork();
        putDownRightFork();
    }


}

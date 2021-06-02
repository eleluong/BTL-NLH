package com.ops.simulation;

public class Ordered extends Philosopher {

    public Ordered(int id, Table table, Fork leftFork, Fork rightFork) {
        super(id, table, leftFork, rightFork);
    }

    protected void pickUpForks() {
        if(this.getId()%2==0){
            pickUpLeftFork();
            pickUpRightFork();
        }else{
            pickUpRightFork();
            pickUpLeftFork();
        }
    }

    protected void putDownForks() {
        putDownLeftFork();
        putDownRightFork();
    }
}

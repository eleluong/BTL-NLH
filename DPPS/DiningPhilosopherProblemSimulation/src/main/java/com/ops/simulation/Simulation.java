package com.ops.simulation;

import com.ops.gui.swing.SwingGui;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Simulation {

    private static final Logger log = LoggerFactory.getLogger(Simulation.class);

    private Table table;
    private List<Philosopher> philosophers;
    private SwingGui swingGui;

    private volatile int DEFAULT_SLEEP_TIME = 500;

    public Simulation(){
        super();
        table = new Table(DEFAULT_SLEEP_TIME);
        philosophers = createPhilosophers(table, SimulationType.DEADLOCK);
    }

    public Simulation(SwingGui swingGui, SimulationType simulationType){
        super();
        this.swingGui = swingGui;
        table = new Table(DEFAULT_SLEEP_TIME, swingGui);
        philosophers = createPhilosophers(table, simulationType);
    }

    public void startSimulation(SimulationType simulationType, int sleepTime){
        log.info("Starting simulation");
        table = new Table(sleepTime, swingGui);
        philosophers = createPhilosophers(table, simulationType);
        Collections.shuffle(philosophers);
        ExecutorService executor = Executors.newCachedThreadPool();
        for (Philosopher philosopher : philosophers){
            log.info("Start {}", philosopher);
            executor.execute(philosopher);
        }
    }

    public static void main(String[] args){
        new Simulation().startSimulation(SimulationType.ORDERED, 250);

    }

    public static List<Philosopher> createPhilosophers(Table table, SimulationType simulationType){


        List<Philosopher> philosophers = new ArrayList<Philosopher>();
        for(int i = 0; i < 5; i++){
            Philosopher philosopher = getNewPhilosoperForSimulationType(table, i, simulationType);
            philosophers.add(philosopher);
        }
        return philosophers;
    }

    private static Philosopher getNewPhilosoperForSimulationType(Table table, int i, SimulationType simulationType) {
        List<Fork> forks = table.getForks();
        Fork leftFork = forks.get(i);
        Fork rightFork = forks.get((i+1)%5);

        switch (simulationType){
            case DEADLOCK: return new Deadlock(i, table, leftFork, rightFork);
            case ORDERED: return new Ordered(i, table, leftFork, rightFork);
            default:
                //log.error("Invalid Simulation Type {}", simulationType);
                return null;
        }
    }

    public Table getTable(){
        return table;
    }

    public List<Philosopher> getPhilosophers() {
        return philosophers;
    }

    public void stopSimulation(){
        table.setSimulationRunning(false);
    }

    public void setSleepTime(int sleepTime) {
        table.setSleepTime(sleepTime);
    }
}

package com.ops.gui.swing;

import com.ops.simulation.Simulation;
import com.ops.simulation.SimulationType;

import javax.swing.*;
import java.awt.*;


public class SwingGui extends JFrame {

    private PhilosophersPanel philosophersPanel;
    private ControlPanel controlPanel;
    private Simulation simulation;

    public SwingGui(){
        simulation = new Simulation(this, SimulationType.ORDERED);
        initGui();
    }

    private void initGui() {
        setSize(500,650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        philosophersPanel = new PhilosophersPanel(simulation);
        controlPanel = new ControlPanel(simulation);

        getContentPane().add(philosophersPanel, BorderLayout.CENTER);
        getContentPane().add(controlPanel, BorderLayout. SOUTH);

        setVisible(true);
    }

    public static void main(String[] args){
        SwingGui gui = new SwingGui();
    }

    public void refresh() {
        //System.out.println("Repaint");
        philosophersPanel.repaint();
    }
}

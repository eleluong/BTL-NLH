package com.ops.gui.swing;

import com.ops.simulation.Simulation;
import com.ops.simulation.SimulationType;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ControlPanel extends JPanel {

    private Simulation simulation;
    JRadioButton orderedButton;
    JRadioButton deadlockButton;
    JSlider slider;


    public ControlPanel(Simulation simulation) {
        this.simulation = simulation;
        init();
    }

    private void init(){
        setPreferredSize(new Dimension(500,100));
        addStartNewSimulationButton();
        addStopSimulationButton();
        addSimulationSpeedSelect();
        addPhilosoperTypeRadio();
    }

    private void addStartNewSimulationButton(){
        final JButton startSimulationButton = new JButton("Start New Simulation");
        startSimulationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                SimulationType simulationType = orderedButton.isSelected() ? SimulationType.ORDERED : SimulationType.DEADLOCK;
                simulation.startSimulation(simulationType, slider.getValue());
            }
        });
        add(startSimulationButton);
    }

    private void addStopSimulationButton(){
        final JButton stopSimulationButton = new JButton("Stop Simulation");
        stopSimulationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                simulation.stopSimulation();
            }
        });
        add(stopSimulationButton);
    }

    private void addSimulationSpeedSelect(){
        slider = new JSlider(JSlider.HORIZONTAL, 5, 2000, 500);
        slider.setMajorTickSpacing(500);
        slider.setSnapToTicks(true);
        slider.setPaintTicks(false);
        slider.setPaintLabels(true);

        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
                JSlider source = (JSlider)changeEvent.getSource();
                if (!source.getValueIsAdjusting()) {
                    int sleepTime = (int)source.getValue();
                    simulation.setSleepTime(sleepTime);
                }
            }
        });

        add(slider);
    }

    private void addPhilosoperTypeRadio(){
        orderedButton = new JRadioButton("Ordered");
        deadlockButton = new JRadioButton("Deadlock");
        orderedButton.setSelected(true);
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(orderedButton);
        buttonGroup.add(deadlockButton);
        add(orderedButton);
        add(deadlockButton);
    }
}

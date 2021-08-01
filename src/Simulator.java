package src;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Color;

public class Simulator {
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;
    // The probability that a fox will be created in any given grid position.
    private static final double cellBirthRate = 0.2;

    // List of cells in the field.
    private List<Cell> cells;
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;

    public Simulator() {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }

    public Simulator(int depth, int width) {
        if (width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }

        cells = new ArrayList<>();
        field = new Field(depth, width);

        Cell.setBirthRate(cellBirthRate);

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        view.setColor(Cell.class, Color.GREEN, Color.WHITE);

        // Setup a valid starting point.
        reset();
    }

    public void runLongSimulation() {
        simulate(4000);
    }

    public void simulate(int numSteps) {
        for (int step = 1; step <= numSteps; step++) {
            simulateOneStep();
            delay(300); // uncomment this to run more slowly
        }
    }

    public void simulateOneStep() {
        step++;
        // Provide space for newborn cells.
        List<Cell> newCells = new ArrayList<>();
        // Let all rabbits act.
        for (Iterator<Cell> it = cells.iterator(); it.hasNext();) {
            Cell cell = it.next();
            System.out.println(cells.size());
            cell.act(newCells);
            if (!cell.isAlive()) {
                it.remove();
            }
        }

        // Add the newly born foxes and rabbits to the main lists.
        cells.addAll(newCells);

        view.showStatus(step, field);
    }

    public void reset() {
        step = 0;
        cells.clear();
        populate();

        // Show the starting state in the view.
        view.showStatus(step, field);
    }

    private void populate() {
        field.clear();
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Location location = new Location(row, col);
                Cell cell = new Cell(field, location);
                cells.add(cell);
            }
        }
    }

    private void delay(int millisec) {
        try {
            Thread.sleep(millisec);
        } catch (InterruptedException ie) {
            // wake up
        }
    }
}

package src;

import java.util.List;
import java.lang.Math;

/**
 * A class representing shared characteristics of animals.
 * 
 */

public class Cell {
    private boolean alive;
    private Field field;
    private Location location;
    private static double birthRate;
    private boolean markedForDeath;
    private boolean markedForLife;

    /**
     * Create a new animal at location in field.
     */
    public Cell(Field field, Location location) {
        alive = false;
        if (Math.random() < birthRate) {
            alive = true;
        }
        this.field = field;
        setLocation(location);
    }
    
    public boolean isMarkedForDeath(){
        return markedForDeath;
    }

    public boolean isMarkedForLife(){
        return markedForLife;
    }

    public static void setBirthRate(double birthRateIn) {
        birthRate = birthRateIn;
    }

    public void act() {
        List<Location> surroundingCells = field.getOccupiedAdjacentLocations(getLocation());
        int numberOfNeighbors = surroundingCells.size();

        if (isAlive()) {
            if (numberOfNeighbors < 2 || numberOfNeighbors > 3) {
                markedForDeath = true;
            }
        }

        else if (numberOfNeighbors == 3) {
            markedForLife = true;
        }

    }
    // incrementAge();
    // incrementHunger();
    // if(isAlive()) {
    // giveBirth(newCells);
    // Move towards a source of food if found.
    // Location newLocation = findFood();
    // No food found - try to move to a free location.
    // else {
    // Overcrowding
    // setDead();
    // }

    private void giveBirth(List<Cell> newCells) {
        // New foxes are born into adjacent locations.
        // Get a list of adjacent free locations.
        // Field field = getField();
        // List<Location> free = field.getFreeAdjacentLocations(getLocation());
        // int births = breed();
        // for(int b = 0; b < births && free.size() > 0; b++) {
        // Location loc = free.remove(0);
        // Cell young = new Cell(field, loc);
        // newCells.add(young);
        // }

        // BIRTH CODE FOR NOW!
        // Field field = getField();
        // List<Location> surroundingCells = field.adjacentLocations(location);
        // System.out.println(surroundingCells);

    }

    private int breed() {
        int births = 0;
        // if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
        // births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        // }
        return births;
    }

    public boolean isAlive() {
        markedForLife = false;
        return alive;
    }

    public void setDead(){
        markedForDeath = false;
        alive = false;
        // if(location != null) {
        // field.clear(location);
        // location = null;
        // field = null;
        // }
    }

    public void setAlive() {
        alive = true;
    }

    protected Location getLocation() {
        return location;
    }

    protected void setLocation(Location newLocation) {
        if (location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    protected Field getField() {
        return field;
    }

}

package src;

import java.util.List;

/**
 * A class representing shared characteristics of animals.
 * 
 */

public class Cell
{
    private boolean alive;
    private Field field;
    private Location location;
    
    /**
     * Create a new animal at location in field.
     */
    public Cell(Field field, Location location)
    {
        alive = true;
        this.field = field;
        setLocation(location);
    }
    
    public void act(List<Cell> newCells){
        
        //incrementAge();
        //incrementHunger();
        if(isAlive()) {
            giveBirth(newCells);            
            // Move towards a source of food if found.
            //Location newLocation = findFood();
                // No food found - try to move to a free location.
            }
            else {
                // Overcrowding
                //setDead();
            }
    }
    private void giveBirth(List<Cell> newCells)
    {
        // New foxes are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Cell young = new Cell(field, loc);
            newCells.add(young);
        }
    }
        
    private int breed()
    {
        int births = 0;
        // if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
        //     births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        // }
        return births;
    }

    protected boolean isAlive()
    {
        return alive;
    }

    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    protected Location getLocation()
    {
        return location;
    }
    
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    protected Field getField()
    {
        return field;
    }
}

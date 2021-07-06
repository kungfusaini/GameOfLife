package src;
import java.util.List;
import java.util.Iterator;
import java.util.Random;

public class Cell extends Animal
{
    
    private static final int BREEDING_AGE = 15;
    private static final int MAX_AGE = 1000;
    private static final double BREEDING_PROBABILITY = 0.08;
    private static final int MAX_LITTER_SIZE = 2;
    private static final Random rand = Randomizer.getRandom();
    
    private int age;
    private int foodLevel;

    public Cell(boolean randomAge, Field field, Location location)
    {
        super(field, location);
        if(randomAge) {
            age = rand.nextInt(MAX_AGE);
        }
        else {
            age = 0;
        }
    }
    
    public void act(List<Animal> newCells)
    {
        incrementAge();
        //incrementHunger();
        if(isAlive()) {
            giveBirth(newCells);            
            // Move towards a source of food if found.
            Location newLocation = findFood();
            System.out.println("Hello");
            System.out.println(newLocation);
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            else {
                // Overcrowding
                //setDead();
            }
        }
    }

    private void incrementAge()
    {
        age++;
        if(age > MAX_AGE) {
            setDead();
        }
    }
    
    
    private Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal = field.getObjectAt(where);
                    return where;
                }
        return null;
    }
    
    /**
     * Check whether or not this fox is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newCells A list to return newly born foxes.
     */
    private void giveBirth(List<Animal> newCells)
    {
        // New foxes are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Cell young = new Cell(false, field, loc);
            newCells.add(young);
        }
    }
        
    private int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    private boolean canBreed()
    {
        return age >= BREEDING_AGE;
    }
}

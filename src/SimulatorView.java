package src;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class SimulatorView extends JFrame
{
    // Colors used for empty locations.
    private static final Color EMPTY_COLOR = Color.white;

    // Color used for objects that have no defined color.
    private static final Color UNKNOWN_COLOR = Color.gray;

    private final String STEP_PREFIX = "Step: ";
    private final String POPULATION_PREFIX = "Alive Cells: ";
    private JLabel stepLabel, population, infoLabel, startButton;
    private FieldView fieldView;
    
    // A map for storing colors for participants in the simulation
    private Map<Class, ArrayList<Color>> colors;
    // A statistics object computing and storing simulation information
    private FieldStats stats;

    public SimulatorView(int height, int width)
    {
        stats = new FieldStats();
        colors = new LinkedHashMap<>();

        setTitle("Game of Life");
        stepLabel = new JLabel(STEP_PREFIX, JLabel.CENTER);
        infoLabel = new JLabel("  ", JLabel.CENTER);
        population = new JLabel(POPULATION_PREFIX, JLabel.CENTER);
        startButton = new JLabel("Start", JLabel.CENTER);
        
        setLocation(100, 50);
        
        fieldView = new FieldView(height, width);

        Container contents = getContentPane();
        
        JPanel infoPane = new JPanel(new BorderLayout());
            infoPane.add(stepLabel, BorderLayout.WEST);
            infoPane.add(infoLabel, BorderLayout.CENTER);
        contents.add(infoPane, BorderLayout.NORTH);
        contents.add(fieldView, BorderLayout.CENTER);
        contents.add(population, BorderLayout.SOUTH);
        contents.add(startButton, BorderLayout.EAST);
        pack();
        setVisible(true);
    }
    
    public void setColor(Class animalClass, Color aliveColor, Color deadColor)
    {
        ArrayList<Color> colourList = new ArrayList<>();
        colourList.add(aliveColor); 
        colourList.add(deadColor); 
        colors.put(animalClass, colourList);
        System.out.println(colors);
    }

    public void setInfoText(String text)
    {
        infoLabel.setText(text);
    }

    private Color getColor(Class animalClass, boolean isAlive)
    {
        int colorIndex= 0;
        ArrayList<Color> colorList = colors.get(animalClass);
        if(!isAlive){
            colorIndex = 1;
        }
        Color col = colorList.get(colorIndex);
        if(col == null) {
            // no color defined for this class
            return UNKNOWN_COLOR;
        }
        else {
            return col;
        }
    }

    public void showStatus(int step, Field field)
    {
        if(!isVisible()) {
            setVisible(true);
        }
            
        stepLabel.setText(STEP_PREFIX + step);
        stats.reset();
        
        fieldView.preparePaint();

        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                Cell animal = field.getObjectAt(row, col);
                if(animal != null) {
                    fieldView.drawMark(col, row, getColor(animal.getClass(),animal.isAlive()));
                    if(animal.isAlive()){
                        stats.incrementCount(animal.getClass());
                    }
                }
                else {
                    fieldView.drawMark(col, row, EMPTY_COLOR);
                }
            }
        }
        stats.countFinished();

        population.setText(POPULATION_PREFIX + stats.getPopulationDetails(field));
        fieldView.repaint();
    }

    public boolean isViable(Field field)
    {
        return stats.isViable(field);
    }
    
    private class FieldView extends JPanel
    {
        private final int GRID_VIEW_SCALING_FACTOR = 6;

        private int gridWidth, gridHeight;
        private int xScale, yScale;
        Dimension size;
        private Graphics g;
        private Image fieldImage;

        public FieldView(int height, int width)
        {
            gridHeight = height;
            gridWidth = width;
            size = new Dimension(0, 0);
        }

        public Dimension getPreferredSize()
        {
            return new Dimension(gridWidth * GRID_VIEW_SCALING_FACTOR,
                                 gridHeight * GRID_VIEW_SCALING_FACTOR);
        }

        public void preparePaint()
        {
            if(! size.equals(getSize())) {  // if the size has changed...
                size = getSize();
                fieldImage = fieldView.createImage(size.width, size.height);
                g = fieldImage.getGraphics();

                xScale = size.width / gridWidth;
                if(xScale < 1) {
                    xScale = GRID_VIEW_SCALING_FACTOR;
                }
                yScale = size.height / gridHeight;
                if(yScale < 1) {
                    yScale = GRID_VIEW_SCALING_FACTOR;
                }
            }
        }
        
        public void drawMark(int x, int y, Color color)
        {
            g.setColor(color);
            g.fillRect(x * xScale, y * yScale, xScale-1, yScale-1);
        }

        public void paintComponent(Graphics g)
        {
            if(fieldImage != null) {
                Dimension currentSize = getSize();
                if(size.equals(currentSize)) {
                    g.drawImage(fieldImage, 0, 0, null);
                }
                else {
                    // Rescale the previous image.
                    g.drawImage(fieldImage, 0, 0, currentSize.width, currentSize.height, null);
                }
            }
        }
    }
}

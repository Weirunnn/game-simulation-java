import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.Color;
import java.util.Arrays;
import java.util.Random;


/**
 * Contains information about the world (i.e., the grid of squares)<br>
 * and handles most of the game play work that is NOT GUI specific
 */
public class FlyWorld
{
    protected int numRows;
    protected int numCols;
    protected int numFrog;
    protected int numSpider;

    protected GridLocation [][] world;

    protected GridLocation start;
    protected GridLocation goal;
    
    protected Fly mosca;



    protected Frog[] frogs;
    protected Spider[] spiders;

    /**
     * Reads a file containing information about<br>
     * the grid setup.  Initializes the grid<br>
     * and other instance variables for use by<br>
     * FlyWorldGUI and other pieces of code.
     *
     *@param fileName the file containing the world grid information
     */
    public FlyWorld(String fileName) {
        //1111111111111111111111111111111111111111111111111111111111111
    // Read the file and set the grid size
    // Assume that the first line of the file contains the number of rows and columns
        try (Scanner scanner = new Scanner(new File(fileName))) {
            if (scanner.hasNextInt()) {
                numRows = scanner.nextInt();
            }
            if (scanner.hasNextInt()) {
                numCols = scanner.nextInt();
            }
    
            world = new GridLocation[numRows][numCols];
            frogs = new Frog[numRows*numCols]; // Initializes the frog array
            spiders = new Spider[numRows*numCols];

            scanner.nextLine(); // Skip the first line
            for (int r = 0; r < numRows; r++) {
                String line = scanner.nextLine();
                for (int c = 0; c < numCols; c++) {
                    char ch = line.charAt(c);
                    world[r][c] = new GridLocation(r, c); // Initialize each grid position
                    switch (ch) {
                        case 's':
                            start = world[r][c];
                            start.setBackground(Color.green);
                            mosca = new Fly(start, this);
                            break;
                        case 'h':
                            goal = world[r][c];
                            goal.setBackground(Color.red);
                            break;
                        case 'f':
                            Frog newFrog = new Frog(world[r][c], this);
                            frogs[numFrog++] = newFrog;
                            break;
                        case 'a':
                            Spider newSpider = new Spider(world[r][c], this);
                            spiders[numSpider++] = newSpider;
                            break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
    }
    }

        // The following print statements are just here to help you know 
        // if you've done part 1 correctly.  You can comment them out or 

    

    /**
     * @return int, the number of rows in the world
     */
    public int getNumRows(){
        return numRows;
    }

    /**
     * @return int, the number of columns in the world
     */
    public int getNumCols(){
        return numCols;
    }

    /**
     * Deterimes if a specific row/column location is<br>
     * a valid location in the world (i.e., it is not out of bounds)
     *
     * @param r a row
     * @param c a column
     *
     * @return boolean
     */
    public boolean isValidLoc(int r, int c){
        // FILL IN// THIS LINE IS JUST SO CODE COMPILES
        return (r >= 0 && r < numRows) && (c >= 0 && c < numCols);
    }//2222222222222222222222222222222222222222
     
    

    /**
     * Returns a specific location based on the given row and column
     *
     * @param r the row
     * @param c the column
     *
     * @return GridLocation
     */
    public GridLocation getLocation(int r, int c){
        return world[r][c];
    }

    /**
     * @return FlyWorldLocation, the location of the fly in the world
     */
    public GridLocation getFlyLocation(){
        return mosca.getLocation();
    }

    /**
     * Moves the fly in the given direction (if possible)
     * Checks if the fly got home or was eaten
     *
     * @param direction the direction, N,S,E,W to move
     *
     * @return int, determines the outcome of moving fly<br>
     *              there are three possibilities<br>
     *              1. fly is at home, return ATHOME (defined in FlyWorldGUI)<br>
     *              2. fly is eaten, return EATEN (defined in FlyWorldGUI)<br>
     *              3. fly not at home or eaten, return NOACTION (defined in FlyWorldGUI)
     */
    public int moveFly(int direction) {//3333333333333333333333333333333333333
        // Move the fly based on the direction given.
        this.mosca.update(direction);

        // Get the new location of the fly after it has moved.
        GridLocation currentLoc = mosca.getLocation();

        // Check if the fly has reached the goal.
        if (currentLoc.equals(goal)) {
            return FlyWorldGUI.ATHOME;
        }

        // Check if the fly has been eaten by any predators.
        if (movePredators()) { 
            return FlyWorldGUI.EATEN;
        }

        // If neither at home nor eaten, the game continues with no action.
        return FlyWorldGUI.NOACTION;
    }


    /**
     * Moves all predators. After it moves a predator, checks if it eats fly
     *
     * @return boolean, return true if any predator eats fly, false otherwise
     */
    public boolean movePredators(){
        // FILL IN
        //4444444444444444444444444444444444444444444444444444444444444
        for (Frog frog : frogs) {
            if (frog == null) {
                continue;
            }

            frog.update();
            if (frog.eatsFly()) {
            return true;
            }
        }
        for (Spider spider : spiders) {
            if (spider == null) {
                continue;
            }
            spider.update();
            if (spider.eatsFly()) {
            return true;
            }
        }
        return false; // THIS LINE IS JUST SO CODE COMPILES
    }
}

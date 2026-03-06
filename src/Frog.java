import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;



/**
 * Handles display, movement, and fly eating capabalities for frogs
 */
public class Frog
{
    protected static final String imgFile = "frog.png";

    protected GridLocation location;

    protected FlyWorld world;

    protected BufferedImage image;

    /**
     * Creates a new Frog object.<br>
     * The image file for a frog is frog.jpg<br>
     *
     * @param loc a GridLocation
     * @param fw the FlyWorld the frog is in
     */
    public Frog(GridLocation loc, FlyWorld fw)
    {
    // FILL IN
    this.location = loc;
    this.world = fw;
    try
        {
            image = ImageIO.read(new File(imgFile));
        }
        catch (IOException ioe)
        {
            System.out.println("Unable to read image file: " + imgFile);
            System.exit(0);
        }
        location.setFrog(this);
    }
    

    /**
     * @return BufferedImage the image of the frog
     */
    public BufferedImage getImage()
    {
    return image;
    }

    /**
     * @return GridLocation the location of the frog
     */
    public GridLocation getLocation()
    {
    return location;
    }

    /**
     * @return boolean, always true
     */
    public boolean isPredator()
    {
    return true;
    }

    /**
    * Returns a string representation of this Frog showing
    * the location coordinates and the world.
    *
    * @return the string representation
    */
    public String toString(){
        String s = "Frog in world:  " + this.world + "  at location (" + this.location.getRow() + ", " + this.location.getCol() + ")";
        return s;
    }

    /**
     * Generates a list of <strong>ALL</strong> possible legal moves<br>
     * for a frog.<br>
     * You should select all possible grid locations from<br>
     * the <strong>world</strong> based on the following restrictions<br>
     * Frogs can move one space in any of the four cardinal directions but<br>
     * 1. Can not move off the grid<br>
     * 2. Can not move onto a square that already has frog on it<br>
     * GridLocation has a method to help you determine if there is a frog<br>
     * on a location or not.<br>
     *
     * @return GridLocation[] a collection of legal grid locations from<br>
     * the <strong>world</strong> that the frog can move to
     */
    public GridLocation[] generateLegalMoves()
    {
        // FILL IN
        List<GridLocation> legalMoves = new ArrayList<>();

    // Gets the frog's current location
        int currentRow = location.getRow();
        int currentCol = location.getCol();

    // Check the legality of the four directions
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // location
        for (int[] dir : directions) {
            int newRow = currentRow + dir[0];
            int newCol = currentCol + dir[1];

        // Check that the new location is within the grid and that there are no other frogs
            if (world.isValidLoc(newRow, newCol) && !world.world[newRow][newCol].hasPredator()) {
                legalMoves.add(world.getLocation(newRow, newCol));
            }
        }

    // Converts a list to an array and returns it
        return legalMoves.toArray(new GridLocation[0]);
    }

    
    /**
     * This method updates the frog's position.<br>
     * It should randomly select one of the legal locations(if there any)<br>
     * and set the frog's location to the chosen updated location.
     */
    public void update() {
    GridLocation[] legalMoves = generateLegalMoves();
    System.out.println(this.location.getRow() + ", " + this.location.getCol());
    for (int i = 0; i < legalMoves.length; i++) {
        System.out.println("  " + legalMoves[i].getRow() + ", " + legalMoves[i].getCol());
    }
    if (legalMoves.length > 0) {
        Random rand = new Random();
        int idx = rand.nextInt(legalMoves.length);
        GridLocation newLoc = legalMoves[idx];
        
        // Update the frog's position
        location.removeFrog(); // Remove the frog from its current location
        newLoc.setFrog(this); // Place the frog in a new location
        location = newLoc; // Update frog location references
    }
}
    /**
     * This method helps determine if a frog is in a location<br>
     * where it can eat a fly or not. A frog can eat the fly if it<br>
     * is on the same square as the fly or 1 spaces away in<br>
     * one of the cardinal directions
     *
     * @return boolean true if the fly can be eaten, false otherwise
     */ 
    public boolean eatsFly() {
        GridLocation flyLocation = world.getFlyLocation();
        int flyRow = flyLocation.getRow();
        int flyCol = flyLocation.getCol();
    
        int spiderRow = location.getRow();
        int spiderCol = location.getCol();
    
        // Check whether the spider and the fly are in the same or next to each other
        if ((spiderRow == flyRow && Math.abs(spiderCol - flyCol) <= 1) ||
            (spiderCol == flyCol && Math.abs(spiderRow - flyRow) <= 1)) {
           return true;
        }
    
        return false;
        
    }
}


    
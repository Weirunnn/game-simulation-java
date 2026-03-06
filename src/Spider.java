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
public class Spider
{
    protected static final String imgFile = "spider.png";

    protected GridLocation location;

    protected FlyWorld world;

    protected BufferedImage image;

    /**
     * Creates a new Spider object.<br>
     * The image file for a spider is spider.jpg<br>
     *
     * @param loc a GridLocation
     * @param fw the FlyWorld the spider is in
     */
    public Spider(GridLocation loc, FlyWorld fw)
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
        location.setSpider(this);
    }
    

    /**
     * @return BufferedImage the image of the spider
     */
    public BufferedImage getImage()
    {
    return image;
    }

    /**
     * @return GridLocation the location of the spider
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
    * Returns a string representation of this Spider showing
    * the location coordinates and the world.
    *
    * @return the string representation
    */
    public String toString(){
        String s = "Spider in world:  " + this.world + "  at location (" + this.location.getRow() + ", " + this.location.getCol() + ")";
        return s;
    }

    /**
     * Generates a list of <strong>ALL</strong> possible legal moves<br>
     * for a spider.<br>
     * You should select all possible grid locations from<br>
     * the <strong>world</strong> based on the following restrictions<br>
     * Spiders can move one space in any of the four cardinal directions but<br>
     * 1. Can not move off the grid<br>
     * 2. Can not move onto a square that already has spider on it<br>
     * GridLocation has a method to help you determine if there is a frog<br>
     * on a location or not.<br>
     *
     * @return GridLocation[] a collection of legal grid locations from<br>
     * the <strong>world</strong> that the spider can move to
     */
    public GridLocation[] generateLegalMoves() {
        List<GridLocation> legalMoves = new ArrayList<>();
    
        // Get the spider's current location.
        int currentRow = location.getRow();
        int currentCol = location.getCol();
    
        // Get Mosca's location.
        GridLocation moscaLocation = world.getFlyLocation();
        int moscaRow = moscaLocation.getRow();
        int moscaCol = moscaLocation.getCol();
    
        // Same row, move in column.
        if (currentCol < moscaCol && world.isValidLoc(currentRow, currentCol + 1) && !world.getLocation(currentRow, currentCol + 1).hasPredator()) {
            legalMoves.add(world.getLocation(currentRow, currentCol + 1));
        }
        if (currentCol > moscaCol && world.isValidLoc(currentRow, currentCol - 1) && !world.getLocation(currentRow, currentCol - 1).hasPredator()) {
            legalMoves.add(world.getLocation(currentRow, currentCol - 1));
        }

        // Same column, move in row.
        if (currentRow < moscaRow && world.isValidLoc(currentRow + 1, currentCol) && !world.getLocation(currentRow + 1, currentCol).hasPredator()) {
            legalMoves.add(world.getLocation(currentRow + 1, currentCol));
        }
        if (currentRow > moscaRow && world.isValidLoc(currentRow - 1, currentCol) && !world.getLocation(currentRow - 1, currentCol).hasPredator()) {
            legalMoves.add(world.getLocation(currentRow - 1, currentCol));
        }
            
        
        
    
        // Convert the list to an array and return it.
        return legalMoves.toArray(new GridLocation[0]);
    }
    /**
     * This method updates the spider's position.<br>
     * It should randomly select one of the legal locations(if there any)<br>
     * and set the spider's location to the chosen updated location.
     */
    public void update() {
    GridLocation[] legalMoves = generateLegalMoves();
    if (legalMoves.length > 0) {
        Random rand = new Random();
        int idx = rand.nextInt(legalMoves.length);
        GridLocation newLoc = legalMoves[idx];
        
        // Update the spider's position
        location.removeSpider(); // Remove the spider from its current location
        newLoc.setSpider(this); // Place the spider in a new location
        location = newLoc; // Update spider location references
    }
}
    /**
     * This method helps determine if a spider is in a location<br>
     * where it can eat a fly or not. A spider can eat the fly if it<br>
     * is on the same square as the fly or 1 spaces away in<br>
     * one of the cardinal directions
     *
     * @return boolean true if the fly can be eaten, false otherwise
     */ 
    public boolean eatsFly() {
        GridLocation flyLocation = world.getFlyLocation();
        // int flyRow = flyLocation.getRow();
        // int flyCol = flyLocation.getCol();
    
        // int spiderRow = location.getRow();
        // int spiderCol = location.getCol();
     
        // // Check whether the spider and the fly are in the same or next to each other
        // if ((spiderRow == flyRow && Math.abs(spiderCol - flyCol) <= 1) ||
        //     (spiderCol == flyCol && Math.abs(spiderRow - flyRow) <= 1)) {
        //     return true;
        // }
    
        // return false;
        return flyLocation.equals(this.location);
    }
}


    
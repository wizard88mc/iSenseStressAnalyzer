package isensestressanalyzer.utils;

/**
 * class Point
 * 
 * This class represents a point on the screen with float precision
 * 
 * @author  Matteo Ciman
 * @version 1.0
 * @since   2014-10-17
 */
public class Point {
    
    private final int x;
    private final int y;
    
    public Point(int x, int y) {
        this.x = x; this.y = y;
    }
    
    /**
     * Returns the x coordinate of the point
     * @return the x coordinate
     */
    public int getX() {
        return this.x;
    }
    
    /**
     * Returns the y coordinate of the point
     * @return the y coordinate
     */
    public int getY() {
        return this.y;
    }
}

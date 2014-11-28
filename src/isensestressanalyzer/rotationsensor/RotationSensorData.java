package isensestressanalyzer.rotationsensor;

/**
 * RotationSensorData
 * 
 * This class represents a single reading from the Rotation Sensor
 * 
 * @author Matteo Ciman
 * @version 1.0
 */
public class RotationSensorData 
{
    private final long timestamp;
    private final double x;
    private final double y;
    private final double z;
    
    /**
     * Instantiate a new RotationSensorData object with the given value
     * @param timestamp the timestamp of the reading
     * @param x x rotation
     * @param y y rotation
     * @param z z rotation
     */
    public RotationSensorData(long timestamp, double x, double y, double z)
    {
        this.timestamp = timestamp; this.x = x; this.y = y; this.z = z;
    }
    
    /**
     * Timestamp value
     * @return timestamp
     */
    public long getTimestamp()
    {
        return this.timestamp;
    }
    
    /**
     * X rotation value
     * @return x
     */
    public double getX() 
    {
        return this.x;
    }
    
    /**
     * Y rotation value
     * @return y
     */
    public double getY() 
    {
        return this.y;
    }
    
    /**
     * Z rotation value
     * @return z
     */
    public double getZ()
    {
        return this.z;
    }   
}

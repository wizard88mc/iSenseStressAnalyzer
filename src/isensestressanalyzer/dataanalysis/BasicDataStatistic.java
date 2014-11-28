package isensestressanalyzer.dataanalysis;

import java.util.ArrayList;

/**
 * class BasicDataStatistics
 * 
 * This data holds basic information about a set of data, i.e. mean, minimum, 
 * max, Standard Deviation and variance
 * 
 * @author  Matteo Ciman
 * @version 0.1
 * @since   2014-10-20
 */
public class BasicDataStatistic 
{
    private double min = Double.MAX_VALUE;
    private double max = Double.MIN_VALUE;
    private double average = 0;
    private double std = 0;
    private double variance = 0;
    
    public BasicDataStatistic(ArrayList<Double> data, boolean discardZeroValues)
    {
        int count = 0;
        for (Double value: data)
        {
            if (!discardZeroValues || (discardZeroValues && value != 0))
            {
                if (value < min) 
                {
                    min = value;
                }
                if (value > max)
                {
                    max = value;
                }
                average += value;
                
                count++;
            }
        }
        average /= count;
        
        for (Double value: data)
        {
            if (!discardZeroValues || discardZeroValues && value != 0)
            {
                variance += Math.pow(value - average, 2);
            }
        }
        variance /= count;
        
        std = (float) Math.sqrt(variance);
    }
    
    public BasicDataStatistic(double value)
    {
        average = value; min = value; max = value;
    }
    
    /**
     * Returns the min value of all the provided values
     * @return the min value
     */
    public Double getMin()
    {
        return this.min;
    }
    
    /**
     * Returns the max value of all the provided values
     * @return the max value
     */
    public Double getMax()
    {
        return this.max;
    }
    
    /**
     * Returns the average value of all the provided values
     * @return the average of the values
     */
    public Double getAverage()
    {
        return this.average;
    }
    
    /**
     * Returns the Standard Deviation of all the provided values
     * @return the Standard Deviation of the values
     */
    public Double getStd()
    {
        return this.std;
    }
    
    /**
     * Returns the variance of all the provided values
     * @return the variance of the values
     */
    public Double getVariance()
    {
        return this.variance;
    }
}

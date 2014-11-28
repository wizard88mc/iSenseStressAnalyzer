/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isensestressanalyzer.dataanalysis;

import isensestressanalyzer.rotationsensor.RotationSensorData;
import java.util.ArrayList;

/**
 *
 * @author Matteo
 */
public class RotationDataWrapper 
{
    private final BasicDataStatistic dataStatisticXAxis;
    private final BasicDataStatistic dataStatisticYAxis;
    private final BasicDataStatistic dataStatisticZAxis;
    
    public RotationDataWrapper(ArrayList<RotationSensorData> rotationSensorData)
    {
        ArrayList<Double> dataX = new ArrayList<>(), 
                dataY = new ArrayList<>(), 
                dataZ = new ArrayList<>();
        
        for (RotationSensorData data: rotationSensorData)
        {
            dataX.add(data.getX()); dataY.add(data.getY()); dataZ.add(data.getZ());
        }
        
        dataStatisticXAxis = new BasicDataStatistic(dataX, false);
        dataStatisticYAxis = new BasicDataStatistic(dataY, false);
        dataStatisticZAxis = new BasicDataStatistic(dataZ, false);
    }
    
    public BasicDataStatistic getXAxis()
    {
        return this.dataStatisticXAxis;
    }
    
    public BasicDataStatistic getYAxis()
    {
        return this.dataStatisticYAxis;
    }
    
    public BasicDataStatistic getZAxis()
    {
        return this.dataStatisticZAxis;
    }
}

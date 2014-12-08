/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isensestressanalyzer.dataanalysis;

import java.util.ArrayList;

/**
 *
 * @author Matteo Ciman
 */
public class StressNoStressRotationData 
{
    private final String feature = "Rotation Vector: ";
    private final ArrayList<RotationDataWrapper> dataNoStress;
    private final ArrayList<RotationDataWrapper> dataStress;
    
    public StressNoStressRotationData(ArrayList<RotationDataWrapper> noStress, 
            ArrayList<RotationDataWrapper> stress)
    {
        this.dataNoStress = noStress; this.dataStress = stress;
    }
    
    /*public void makeAndPrintTest()
    {
        System.out.println(feature);
        testOnXAxis(); testOnYAxis(); testOnZAxis();
    }
    
    private void testOnXAxis()
    {
        ArrayList<BasicDataStatistic> xAxisNoStress = new ArrayList<>(), 
                xAxisStress = new ArrayList<>();
        
        for (RotationDataWrapper data: dataNoStress)
        {
            xAxisNoStress.add(data.getXAxis());
        }
        for (RotationDataWrapper data: dataStress)
        {
            xAxisStress.add(data.getXAxis());
        }
        
        if (!xAxisNoStress.isEmpty() && !xAxisStress.isEmpty())
        {
            testOnSingleAxis("X", xAxisNoStress, xAxisStress);
        }
    }
    
    private void testOnYAxis()
    {
        ArrayList<BasicDataStatistic> yAxisNoStress = new ArrayList<>(), 
                yAxisStress = new ArrayList<>();
        
        for (RotationDataWrapper data: dataNoStress)
        {
            yAxisNoStress.add(data.getYAxis());
        }
        for (RotationDataWrapper data: dataStress)
        {
            yAxisStress.add(data.getYAxis());
        }
        
        if (!yAxisNoStress.isEmpty() && !yAxisStress.isEmpty())
        {
            testOnSingleAxis("Y", yAxisNoStress, yAxisStress);
        }
    }
    
    private void testOnZAxis()
    {
        ArrayList<BasicDataStatistic> zAxisNoStress = new ArrayList<>(), 
                zAxisStress = new ArrayList<>();
        
        for (RotationDataWrapper data: dataNoStress)
        {
            zAxisNoStress.add(data.getZAxis());
        }
        for (RotationDataWrapper data: dataStress)
        {
            zAxisStress.add(data.getZAxis());
        }
        
        if (!zAxisNoStress.isEmpty() && !zAxisStress.isEmpty())
        {
            testOnSingleAxis("Z", zAxisNoStress, zAxisStress);
        }
    }
    
    private void testOnSingleAxis(String name, ArrayList<BasicDataStatistic> axisNostress, 
            ArrayList<BasicDataStatistic> axisStress)
    {
        new StressNoStressData(name + " Axis", axisNostress, axisStress).makeAndPrintTTest();
    }*/
}

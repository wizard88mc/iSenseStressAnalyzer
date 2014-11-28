package isensestressanalyzer.dataanalysis;

import java.util.ArrayList;
import org.apache.commons.math3.stat.inference.TTest;

/**
 * This class holds BasicDataStatistic for the stress and not stress state of a
 * particular feature
 * @author Matteo Ciman
 * @since 2014-11-11
 * @version 0.1
 */
public class StressNoStressData 
{
    private final String feature;
    private final ArrayList<BasicDataStatistic> noStress;
    private final ArrayList<BasicDataStatistic> stress;
    
    public StressNoStressData(String feature)
    {
        this(feature, new ArrayList<BasicDataStatistic>(), 
                new ArrayList<BasicDataStatistic>());
    }
    
    public StressNoStressData(String feature, ArrayList<BasicDataStatistic> noStress, 
            ArrayList<BasicDataStatistic> stress)
    {
        this.feature = feature; this.noStress = noStress; this.stress = stress;
    }
    
    public boolean isEmpty()
    {
        return noStress.isEmpty();
    }
    
    public void addNoStressData(BasicDataStatistic data)
    {
        noStress.add(data);
    }
    
    public void addStressData(BasicDataStatistic data)
    {
        stress.add(data);
    }
    
    public void addData(BasicDataStatistic dataNoStress, 
            BasicDataStatistic dataStress)
    {
        addNoStressData(dataNoStress); addStressData(dataStress);
    }
    
    public void makeAndPrintTTest()
    {   
        adjustData(noStress, stress);
        double[] arrayNoStress = new double[noStress.size()],
                arrayStress = new double[stress.size()], 
                arrayIncreases = new double[stress.size()];
        
        System.out.println(feature);
        if (noStress.size() > 1)
        {
            //printCoupleValuesNoStressStress();
            copyMeanValueIntoDoubleArray(arrayNoStress, noStress);
            copyMeanValueIntoDoubleArray(arrayStress, stress);
            createIncreaseArray(arrayIncreases);
            
            float mean = 0; 
            for (double value: arrayIncreases)
            {
                mean += value;
            }
            mean /= arrayIncreases.length;
        
            System.out.println("TTest: " + 
                new TTest().pairedTTest(arrayNoStress, arrayStress));
            System.out.println("TTest increase: " + 
                new TTest().tTest(0, arrayIncreases));
            System.out.println("Mean difference: " + mean);
        }
        else
        {
            if (noStress.size() == 1 && stress.size() == 1)
            {
            try
            {
            System.out.println("Difference: " + 
                    (100 * (stress.get(0).getAverage() - noStress.get(0).getAverage()) 
                            / noStress.get(0).getAverage()));
            }
            catch(IndexOutOfBoundsException exc)
            {
                System.out.println(exc.toString());
            }
            }
        }
    }
    
    private void copyMeanValueIntoDoubleArray(double[] destination, 
            ArrayList<BasicDataStatistic> origin)
    {
        for (int i = 0; i < origin.size(); i++)
        {
            if (!origin.get(i).getAverage().isNaN() || 
                    !origin.get(i).getAverage().isInfinite())
            {
                destination[i] = origin.get(i).getAverage();
            }
            else
            {
                System.out.println("NaN");
            }
        }
    }
    
    private void createIncreaseArray(double[] arrayIncrease)
    {
        for (int i = 0; i < stress.size(); i++)
        {
            arrayIncrease[i] = 100 * (stress.get(i).getAverage() - noStress.get(i).getAverage())
                / noStress.get(i).getAverage();
        }
    }
    
    private void printCoupleValuesNoStressStress()
    {
        for (int i = 0; i < noStress.size(); i++)
        {
            System.out.println(noStress.get(i).getAverage() 
                    + "(+- " + noStress.get(i).getStd() + ") - " 
                    + stress.get(i).getAverage() + "(+- " 
                    + stress.get(i).getStd() + " )");
        }
    }
    
    private void adjustData(ArrayList<BasicDataStatistic> first, ArrayList<BasicDataStatistic> second)
    {
        for (int i = 0; i < first.size(); )
        {
            if (first.get(i).getAverage().isNaN() || first.get(i).getAverage().isInfinite())
            {
                first.remove(i);
            }
            else
            {
                i++;
            }
        }
        for (int i = 0; i < second.size();)
        {
            if (second.get(i).getAverage().isInfinite() || second.get(i).getAverage().isNaN())
            {
                second.remove(i);
            }
            else
            {
                i++;
            }
        }
        
        try
        {
        if (first.size() > second.size())
        {
            while (first.size() != second.size())
            {
                BasicDataStatistic newData = new BasicDataStatistic((first.get(first.size() - 1).getAverage() + first.get(first.size() - 2).getAverage()) / 2);
                first.remove(first.size() - 2);
                first.remove(first.size() - 1);
                first.add(newData);
            }
        }
        else if (second.size() > first.size())
        {
            while (second.size() != first.size())
            {
                BasicDataStatistic newData = new BasicDataStatistic((second.get(second.size() - 1).getAverage() + second.get(second.size() - 2).getAverage()) / 2);
                second.remove(second.size() - 2);
                second.remove(second.size() - 1);
                second.add(newData);
            }
        }
        }
        catch(IndexOutOfBoundsException exc)
        {
            if (!second.isEmpty())
            {
                second.clear();
            }
            if (!first.isEmpty())
            {
                first.clear();
            }
            //System.out.println(exc.toString());
        }
    }
}

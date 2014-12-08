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
    private BasicDataStatistic noStress;
    private BasicDataStatistic stress;
    
    public StressNoStressData(String feature)
    {
        this(feature, null, null);
    }
    
    public StressNoStressData(String feature, BasicDataStatistic noStress, 
            BasicDataStatistic stress)
    {
        this.feature = feature; this.noStress = noStress; this.stress = stress;
    }
    
    public void setNoStressData(BasicDataStatistic data)
    {
        noStress = data;
    }
    
    public void setStressData(BasicDataStatistic data)
    {
        stress = data;
    }
    
    public void setData(BasicDataStatistic dataNoStress, 
            BasicDataStatistic dataStress)
    {
        stress = dataStress; noStress = dataNoStress;
    }
    
    public BasicDataStatistic getNoStressData()
    {
        return this.noStress;
    }
    
    public BasicDataStatistic getStressData()
    {
        return this.stress;
    }
    
    public static void makeAndPrintTTest(String featureName, 
            ArrayList<Double> noStress, ArrayList<Double> stress) {
    
        adjustData(noStress, stress);
        double[] arrayNoStress = new double[noStress.size()],
                arrayStress = new double[stress.size()], 
                arrayIncreases = new double[stress.size()];

        System.out.println("******" + featureName + "******");

        if (noStress.size() > 1)
        {
            //printCoupleValuesNoStressStress();
            copyMeanValueIntoDoubleArray(arrayNoStress, noStress);
            copyMeanValueIntoDoubleArray(arrayStress, stress);
            createIncreaseArray(noStress, stress, arrayIncreases);

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
                    (100 * (stress.get(0) - noStress.get(0)) 
                            / noStress.get(0)));
            }
            catch(IndexOutOfBoundsException exc)
            {
                System.out.println(exc.toString());
            }
            }
        }
        
    }
    
    private static void copyMeanValueIntoDoubleArray(double[] destination, 
            ArrayList<Double> origin)
    {
        for (int i = 0; i < origin.size(); i++)
        {
            if (!origin.get(i).isNaN() || 
                    !origin.get(i).isInfinite())
            {
                destination[i] = origin.get(i);
            }
            else
            {
                System.out.println("NaN");
            }
        }
    }
    
    private static void createIncreaseArray(ArrayList<Double> noStress, 
            ArrayList<Double> stress, double[] arrayIncrease)
    {
        for (int i = 0; i < stress.size(); i++)
        {
            arrayIncrease[i] = 100 * (stress.get(i) - noStress.get(i))
                / noStress.get(i);
        }
    }
    
    /*private void printCoupleValuesNoStressStress(ArrayList<Double> noStress, 
            ArrayList<Double> stress)
    {
        for (int i = 0; i < noStress.size(); i++)
        {
            System.out.println(noStress.get(i) 
                    + "(+- " + noStress.get(i).getStd() + ") - " 
                    + stress.get(i).getAverage() + "(+- " 
                    + stress.get(i).getStd() + " )");
        }
    }*/
    
    private static void adjustData(ArrayList<Double> first, ArrayList<Double> second)
    {
        for (int i = 0; i < first.size(); )
        {
            if (first.get(i).isNaN() || first.get(i).isInfinite())
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
            if (second.get(i).isInfinite() || second.get(i).isNaN())
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
                Double newData = (first.get(first.size() - 1) + first.get(first.size() - 2)) / 2;
                first.remove(first.size() - 2);
                first.remove(first.size() - 1);
                first.add(newData);
            }
        }
        else if (second.size() > first.size())
        {
            while (second.size() != first.size())
            {
                Double newData = (second.get(second.size() - 1) + second.get(second.size() - 2)) / 2;
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

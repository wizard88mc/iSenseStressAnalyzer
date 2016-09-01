package isensestressanalyzer.dataanalysis;

import isensestressanalyzer.filereader.DigitsHeatmapReader;
import isensestressanalyzer.outputwriter.DigitsDistancesOutputWriter;
import isensestressanalyzer.outputwriter.TTestTouchDistanceWriter;
import isensestressanalyzer.tester.Tester;
import isensestressanalyzer.utils.MathUtils;
import java.util.ArrayList;
import org.apache.commons.math3.stat.inference.TTest;

/**
 *
 * @author Matteo Ciman
 */
public class WriteTouchesPositionFeatures {
    
    public static void workOnKeyDistances(ArrayList<Tester> listTesters) {
        
        DigitsDistancesOutputWriter outputWriter = 
                new DigitsDistancesOutputWriter();
        TTestTouchDistanceWriter tTestWriter = new TTestTouchDistanceWriter();
        
        ArrayList<ArrayList<Double>> allDigitsPerPointNoStress = 
                new ArrayList<>(), 
            allDigitsPerPointStress = new ArrayList<>(), 
            allDigitsCenterNoStress = new ArrayList<>(), 
            allDigitsCenterStress = new ArrayList<>();
        
        ArrayList<String> digitsToConsider = 
                DigitsHeatmapReader.getDigitsForHeathmap();
            
        for (Tester tester: listTesters) {
            
            outputWriter.writeIMEI(tester.getName());
            
            for (int i = 0; i < digitsToConsider.size(); 
                    i++) {
                
                if (allDigitsPerPointNoStress.size() < digitsToConsider.size()) {
                        
                    allDigitsPerPointNoStress.add(new ArrayList<Double>());
                    allDigitsPerPointStress.add(new ArrayList<Double>());
                    
                    allDigitsCenterNoStress.add(new ArrayList<Double>());
                    allDigitsCenterStress.add(new ArrayList<Double>());
                }
                
                Double[] statisticsDistanceTouchPointsNoStress = 
                    new AverageDistanceBetweenEachPoints().
                        calculateAverageDistanceBetweenTouchPoints(tester, 
                        digitsToConsider.get(i), false), 
                    statisticsDistanceTouchPointsStress = 
                        new AverageDistanceBetweenEachPoints().
                            calculateAverageDistanceBetweenTouchPoints(tester, 
                        digitsToConsider.get(i), true);
                
                Double[] statisticsDistanceCenterNoStress = 
                        new AverageDistanceFromCenter().
                            calculateAverageDistanceFromCenter(tester, 
                                digitsToConsider.get(i), false), 
                    statisticsDistanceCenterStress = 
                        new AverageDistanceFromCenter().
                            calculateAverageDistanceFromCenter(tester, 
                                digitsToConsider.get(i), true);
                
                if (statisticsDistanceCenterNoStress != null && 
                    statisticsDistanceCenterStress != null && 
                    statisticsDistanceTouchPointsNoStress != null && 
                    statisticsDistanceTouchPointsStress != null) {
                    
                    outputWriter.addValueDistance(MathUtils.DECIMAL_FORMAT.
                            format(statisticsDistanceTouchPointsNoStress[0]), 
                        MathUtils.DECIMAL_FORMAT.
                            format(statisticsDistanceCenterNoStress[0]));
                    
                    outputWriter.addValueDistance(MathUtils.DECIMAL_FORMAT.
                            format(statisticsDistanceTouchPointsStress[0]), 
                        MathUtils.DECIMAL_FORMAT.
                            format(statisticsDistanceCenterStress[0]));
                    
                    Double percentageChangeCenter = MathUtils.
                            calculatePercentageVariation(statisticsDistanceCenterNoStress[0], 
                                statisticsDistanceCenterStress[0]),
                        percentageChangePoints = MathUtils.
                            calculatePercentageVariation(statisticsDistanceTouchPointsNoStress[0], 
                                statisticsDistanceTouchPointsStress[0]);
                    
                    outputWriter.addValueDistance(MathUtils.DECIMAL_FORMAT.format(percentageChangePoints), 
                            MathUtils.DECIMAL_FORMAT.format(percentageChangeCenter));
                
                    allDigitsPerPointNoStress.get(i).
                            add(statisticsDistanceTouchPointsNoStress[0]);
                    allDigitsPerPointStress.get(i).
                            add(statisticsDistanceTouchPointsStress[0]);
                
                    allDigitsCenterNoStress.get(i).
                            add(statisticsDistanceCenterNoStress[0]);
                    allDigitsCenterStress.get(i).
                            add(statisticsDistanceCenterStress[0]);
                }
                else {
                    outputWriter.addValueDistance("?", "?");
                    outputWriter.addValueDistance("?", "?");
                    outputWriter.addValueDistance("-", "-");
                }
            }
            
            outputWriter.participantCompleted();
        }
        
        outputWriter.close();
        
        /**
         * Performing steps for the tTest
         */
        performtTestForDigits(digitsToConsider, tTestWriter, 
                allDigitsPerPointNoStress, allDigitsPerPointStress, 
                allDigitsCenterNoStress, allDigitsCenterStress);
    }
    
    /**
     * Performs the steps for applying the ttest to distance data from all
     * participants
     * @param digit the digits currently considered
     * @param writer the output writer
     * @param distancePerPointAllNoStress the average distances from each point 
     * from all participants in no stress
     * @param distancePerPointAllStress the average distance from each point 
     * from all participants in stress
     * @param distanceFromCenterAllNoStress the average distance from the center
     * from all participants in no stress
     * @param distanceFromCenterAllStress the average distance from the center 
     * from all participants in stress
     */
    private static void performtTestForDigits(ArrayList<String> listDigits, 
        TTestTouchDistanceWriter writer,
        ArrayList<ArrayList<Double>> distancePerPointAllNoStress, 
        ArrayList<ArrayList<Double>> distancePerPointAllStress, 
        ArrayList<ArrayList<Double>> distanceFromCenterAllNoStress, 
        ArrayList<ArrayList<Double>> distanceFromCenterAllStress) {
        
        for (int i = 0; i < listDigits.size(); i++) {
        
            if (distancePerPointAllNoStress.get(i).size() >= 2 && 
                distancePerPointAllStress.get(i).size() >= 2 && 
                distanceFromCenterAllNoStress.get(i).size() >= 2 && 
                distanceFromCenterAllStress.get(i).size() >= 2) {

            double[] distancePointsNoStress = 
                MathUtils.convertToArrayDouble(distancePerPointAllNoStress.get(i)), 
                distancePointsStress = MathUtils.
                    convertToArrayDouble(distancePerPointAllStress.get(i)), 
                distanceCenterNoStress = MathUtils.
                    convertToArrayDouble(distanceFromCenterAllNoStress.get(i)), 
                distanceCenterStress = MathUtils.
                    convertToArrayDouble(distanceFromCenterAllStress.get(i));

            double tValuePoints = new TTest().pairedTTest(distancePointsNoStress, 
                    distancePointsStress), 
                tValueCenter = new TTest().pairedTTest(distanceCenterNoStress, 
                    distanceCenterStress);

            writer.writeTTestResult(listDigits.get(i), 
                MathUtils.DECIMAL_FORMAT.format(tValuePoints), 
                MathUtils.DECIMAL_FORMAT.format(tValueCenter));
            }
        }
        
        writer.close();
    }
    
    
}

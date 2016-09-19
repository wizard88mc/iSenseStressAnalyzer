package isensestressanalyzer.dataanalysis;

import isensestressanalyzer.filereader.DigitsHeatmapReader;
import isensestressanalyzer.outputwriter.DigitsKeyboardFeaturesOutputWriter;
import isensestressanalyzer.outputwriter.TTestTouchDigitsAnalysis;
import isensestressanalyzer.tester.Tester;
import isensestressanalyzer.utils.MathUtils;
import java.util.ArrayList;
import org.apache.commons.math3.stat.inference.TTest;

/**
 *
 * @author Matteo Ciman
 */
public class WriteKeyboardFeatures {
    
    private final Feature[] LIST_FEATURES = {new AverageDistanceBetweenEachPoints(), 
        new AverageDistanceFromCenter(), new AverageDuration(), new AveragePressure(), 
        new AveragePressureOverSize(), new AverageSize()};
    
    public void workOnKeyDistances(ArrayList<Tester> listTesters) {
        
        for (Feature feature: LIST_FEATURES) {
        
            DigitsKeyboardFeaturesOutputWriter outputWriter = 
                new DigitsKeyboardFeaturesOutputWriter(feature.getFeatureName());
        
            ArrayList<ArrayList<Double>> featureValueNoStress = new ArrayList<>(), 
                featureValueStress = new ArrayList<>();
        
            ArrayList<String> digitsToConsider = 
                DigitsHeatmapReader.getDigitsForHeathmap();
            
            ArrayList<Double> digitsOccurrenceNoStress = new ArrayList<>(),
                    digitsOccurrenceStress = new ArrayList<>();
            
            for (Tester tester: listTesters) {

                outputWriter.writeIMEI(tester.getName());

                for (int i = 0; i < digitsToConsider.size(); 
                        i++) {

                    if (featureValueNoStress.size() < digitsToConsider.size()) {

                        featureValueNoStress.add(new ArrayList<Double>());
                        featureValueStress.add(new ArrayList<Double>());
                    }
                    
                    if (digitsOccurrenceNoStress.size() < digitsToConsider.size()) {
                        
                        digitsOccurrenceNoStress.add(0.0);
                        digitsOccurrenceStress.add(0.0);
                    }
                    
                    Double currentValueNoStress = digitsOccurrenceNoStress.get(i) + 
                        tester.countNumberOfOccurrencesForDigit(digitsToConsider.get(i), false),
                        currentValueStress = digitsOccurrenceStress.get(i) + 
                            tester.countNumberOfOccurrencesForDigit(digitsToConsider.get(i), true);
                    
                    digitsOccurrenceNoStress.set(i, currentValueNoStress);
                    digitsOccurrenceStress.set(i, currentValueStress);

                    Double[] statisticsNoStress = feature.
                            calculateFeatureValues(tester, 
                                digitsToConsider.get(i), false),
                        statisticsStress = feature.
                            calculateFeatureValues(tester, 
                                digitsToConsider.get(i), true);

                    if (statisticsNoStress != null && 
                        statisticsStress != null) {

                        outputWriter.addValue(MathUtils.DECIMAL_FORMAT.
                                format(statisticsNoStress[0]));

                        outputWriter.addValue(MathUtils.DECIMAL_FORMAT.
                                format(statisticsStress[0]));

                        Double percentageChange = MathUtils.
                                calculatePercentageVariation(statisticsNoStress[0], 
                                    statisticsStress[0]);

                        outputWriter.addValue(MathUtils.DECIMAL_FORMAT.
                                format(percentageChange));

                        featureValueNoStress.get(i).
                                add(statisticsNoStress[0]);

                        featureValueStress.get(i).
                                add(statisticsStress[0]);
                    }
                    else {
                        outputWriter.addValue("?");
                        outputWriter.addValue("?");
                        outputWriter.addValue("-");
                    }
                }

                outputWriter.participantCompleted();
            }
        
            outputWriter.close();
            
            TTestTouchDigitsAnalysis tTestWriter = new 
                TTestTouchDigitsAnalysis(feature.getFeatureName());
        
            /**
             * Performing steps for the tTest
             */
            performtTestForDigits(digitsToConsider, tTestWriter, 
                    featureValueNoStress, featureValueStress, 
                    digitsOccurrenceNoStress, digitsOccurrenceStress);
        }
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
        TTestTouchDigitsAnalysis writer,
        ArrayList<ArrayList<Double>> featureValuesNoStress, 
        ArrayList<ArrayList<Double>> featureValueStress, 
        ArrayList<Double> digitsOccurrenceNoStress, 
        ArrayList<Double> digitsOccurrenceStress){
        
        for (int i = 0; i < listDigits.size(); i++) {
        
            if (featureValuesNoStress.get(i).size() >= 2 && 
                featureValueStress.get(i).size() >= 2) {

            double[] valuesNoStress = 
                MathUtils.convertToArrayDouble(featureValuesNoStress.get(i)), 
                valuesStress = MathUtils.
                    convertToArrayDouble(featureValueStress.get(i));

            double tValuePoints = new TTest().pairedTTest(valuesNoStress, 
                    valuesStress);

            writer.writeTTestResult(listDigits.get(i), 
                MathUtils.DECIMAL_FORMAT.format(tValuePoints), 
                String.valueOf(digitsOccurrenceNoStress.get(i)), 
                String.valueOf(digitsOccurrenceStress.get(i)));
            }
            else {
                writer.writeTTestResult(listDigits.get(i), "-", 
                    String.valueOf(digitsOccurrenceNoStress.get(i)), 
                    String.valueOf(digitsOccurrenceStress.get(i)));
            }
        }
        
        writer.close();
    }
}

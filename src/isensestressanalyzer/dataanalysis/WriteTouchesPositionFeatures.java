package isensestressanalyzer.dataanalysis;

import isensestressanalyzer.ISenseStressAnalyzer;
import isensestressanalyzer.exercise.Write;
import isensestressanalyzer.filereader.DigitsHeatmapReader;
import isensestressanalyzer.interaction.Digit;
import isensestressanalyzer.interaction.Interaction;
import isensestressanalyzer.outputwriter.DigitsDistancesOutputWriter;
import isensestressanalyzer.outputwriter.TTestTouchDistanceWriter;
import isensestressanalyzer.tester.Tester;
import isensestressanalyzer.utils.MathUtils;
import isensestressanalyzer.utils.Point;
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
                        calculateAverageDistanceBetweenTouchPoints(tester, 
                        digitsToConsider.get(i), false), 
                    statisticsDistanceTouchPointsStress = 
                        calculateAverageDistanceBetweenTouchPoints(tester, 
                        digitsToConsider.get(i), true);
                
                Double[] statisticsDistanceCenterNoStress = 
                        calculateAverageDistanceFromCenter(tester, digitsToConsider.get(i), 
                        false), 
                    statisticsDistanceCenterStress = 
                        calculateAverageDistanceFromCenter(tester, digitsToConsider.get(i), 
                        true);
                
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
    
    /**
     * Calculates the average distance of all the digits
     * @param exercise the write exercise to consider
     * @param digits the list of digits as comma separated values
     * @return the average distance if more than one digit, null otherwise
     */
    public static Double calculateAverageDistanceBetweenTouchPoints(Write exercise, 
            String digits) {
        
        String[] listCharacters = digits.split(",");
        
        ArrayList<Digit> listDigits = new ArrayList<>();
        
        for (String character: listCharacters) {
            listDigits.addAll(exercise.
                getAllDigitsForAParticularCharacter(character));
        }
        
        if (!listDigits.isEmpty()) {
            
            return MathUtils.calculateStatisticInformation(
                    calculateDistancesBetweenPoints(listDigits))[0];
        }
        else {
            return null;
        }
    }
    
    /**
     * Calculates the average distance of the digit from the key center
     * @param exercise the write exercise to consider
     * @param digits the digits to consider as a comma separated values
     * @return the average distance from the center if more than one digits, 
     * null otherwise
     */
    public static Double calculateAverageDistanceFromCenter(Write exercise, 
            String digits) {
        
        String[] listCharacters = digits.split(",");
        
        ArrayList<Digit> listDigits = new ArrayList<>();
        
        for (String character: listCharacters) {
            listDigits.addAll(exercise.
                getAllDigitsForAParticularCharacter(character));
        }
        
        if (!listDigits.isEmpty()) {
            
            ArrayList<Double> distances = new ArrayList<>();
            
            for (Digit digit: listDigits) {
                for (Interaction interaction: digit.getInteractions()) {
                    distances.add(calculateDistance(interaction.getPoint(), 
                        digit.getKeyClicked().getCenterPosition()));
                }
            }
            
            return MathUtils.calculateStatisticInformation(distances)[0];
        }
        else {
            return null;
        }
        
    }
    
    /**
     * Calculates the average distance between all the touch points during 
     * the write exercises
     * @param digits the digits we want to consider as a string with comma 
     * separated values
     * @param tester the participant to consider
     * @param stress if use stress values or not
     * @return [average, variance, standard_deviation] if more than zero digits, 
     * null otherwise
     */
    public static Double[] calculateAverageDistanceBetweenTouchPoints(Tester tester, 
            String digits, boolean stress) {
        
        String[] listCharacters = digits.split(",");
        
        ArrayList<Digit> listDigits = new ArrayList<>();
        
        /**
         * Retrieving write exercises
         */
        ArrayList<Write> listWriteExercises = tester.
            getWriteExercisesForProtocol(ISenseStressAnalyzer.PROTOCOLS[0]);
        
        for (String character: listCharacters) {
            for (Write writeExercise: listWriteExercises) {
                if (stress == writeExercise.stress()) {
                    listDigits.addAll(writeExercise.
                        getAllDigitsForAParticularCharacter(character));
                }
            }
        }
        
        if (!listDigits.isEmpty()) {
            
            return MathUtils.calculateStatisticInformation(
                calculateDistancesBetweenPoints(listDigits));
        }
        else {
            return null;
        }
    }
    
    /**
     * Calculates the average distance of the touches from the center of the 
     * key of the keyboard
     * @param tester the current participant considered
     * @param digits the list of digits to consider
     * @param stress if stress exercise or not
     * @return [average, variance, standard deviation] if there are any digits, 
     * null otherwise
     */
    public static Double[] calculateAverageDistanceFromCenter(Tester tester, 
            String digits, boolean stress) {
        
        String[] listCharacters = digits.split(",");
        
        ArrayList<Digit> listDigits = new ArrayList<>();
        
        /**
         * Retrieving write exercises
         */
        ArrayList<Write> listWriteExercises = tester.
            getWriteExercisesForProtocol(ISenseStressAnalyzer.PROTOCOLS[0]);
        
        for (String character: listCharacters) {
            for (Write writeExercise: listWriteExercises) {
                if (stress == writeExercise.stress()) {
                    listDigits.addAll(writeExercise.
                        getAllDigitsForAParticularCharacter(character));
                }
            }
        }
        
        if (!listDigits.isEmpty()) {
            
            return MathUtils.calculateStatisticInformation(
                calculateDistancesFromCenter(listDigits));
        }
        else {
            return null;
        }
    }
    
    /**
     * Calculates the distance between two points
     * @param first the first point
     * @param second the second point
     * @return the euclidian distance between two points
     */
    private static Double calculateDistance(Point first, Point second) {
        
        return Math.sqrt(Math.pow(first.getX() - second.getX(), 2) + 
                Math.pow(first.getY() - second.getY(), 2));
        
    }
    
    /**
     * Calculates the distances of all the digits
     * @param listDigits the list of digit events to consider
     * @return a list of distances between each interaction of the digits
     */
    private static ArrayList<Double> 
        calculateDistancesBetweenPoints(ArrayList<Digit> listDigits) {
        
        ArrayList<Point> listTouchPoints = new ArrayList<>();
            
        for (Digit digit: listDigits) {

            for (Interaction interaction: digit.getInteractions()) {

                listTouchPoints.add(interaction.getPoint());
            }
        }

        ArrayList<Double> distances = new ArrayList<>();

        for (int i = 0; i < listTouchPoints.size() - 1; i++) {
            for (int j = i + 1; j < listTouchPoints.size(); j++) {
                distances.add(calculateDistance(listTouchPoints.get(i), 
                        listTouchPoints.get(j)));
            }
        }
        
        return distances;
    }
    
    /**
     * Calculates the distances of each digit from the center of the clicked 
     * object
     * @param listDigits the list of digits events
     * @return a list of distances values from the center of the keyboard key
     */
    private static ArrayList<Double> 
        calculateDistancesFromCenter(ArrayList<Digit> listDigits) {

        ArrayList<Double> distances = new ArrayList<>();

        for (Digit digit: listDigits) {

            for (Interaction interaction: digit.getInteractions()) {

                distances.add(calculateDistance(interaction.getPoint(), 
                    digit.getKeyClicked().getCenterPosition()));
            }
        }

        return distances;
    }
}

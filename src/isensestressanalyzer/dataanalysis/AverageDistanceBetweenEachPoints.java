package isensestressanalyzer.dataanalysis;

import isensestressanalyzer.ISenseStressAnalyzer;
import isensestressanalyzer.exercise.Write;
import isensestressanalyzer.interaction.Digit;
import isensestressanalyzer.interaction.Interaction;
import isensestressanalyzer.tester.Tester;
import isensestressanalyzer.utils.MathUtils;
import isensestressanalyzer.utils.Point;
import java.util.ArrayList;

/**
 * Class to calculate average distance between each point
 * @author Matteo Ciman
 */
public class AverageDistanceBetweenEachPoints extends Feature {
    
    public AverageDistanceBetweenEachPoints() {
        super("Average_Distance_Between_Each_Point");
    }
    
    @Override
    public Double[] calculateFeatureValues(Tester tester, String digits, 
            boolean stress) {
        
        return calculateAverageDistanceBetweenTouchPoints(tester, digits, stress);
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
    private Double[] calculateAverageDistanceBetweenTouchPoints(Tester tester, 
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
     * Calculates the distances of all the digits
     * @param listDigits the list of digit events to consider
     * @return a list of distances between each interaction of the digits
     */
    private ArrayList<Double> 
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
}

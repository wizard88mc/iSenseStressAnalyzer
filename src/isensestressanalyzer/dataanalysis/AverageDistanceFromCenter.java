package isensestressanalyzer.dataanalysis;

import isensestressanalyzer.exercise.Write;
import isensestressanalyzer.interaction.Digit;
import isensestressanalyzer.interaction.Interaction;
import isensestressanalyzer.tester.Tester;
import isensestressanalyzer.utils.MathUtils;
import java.util.ArrayList;

/**
 *
 * @author Matteo Ciman
 */
public class AverageDistanceFromCenter extends Feature {
    
    public AverageDistanceFromCenter() {
        super("Average_Distance_From_Center");
    }
    
    @Override
    public Double[] calculateFeatureValues(Tester tester, String digits, 
            boolean stress) {
        
        return calculateAverageDistanceFromCenter(tester, digits, stress);
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
    private Double[] calculateAverageDistanceFromCenter(Tester tester, 
            String digits, boolean stress) {
        
        ArrayList<Digit> listDigits = getListDigits(tester, digits, stress);
        
        if (!listDigits.isEmpty()) {
            
            return MathUtils.calculateStatisticInformation(
                calculateDistancesFromCenter(listDigits));
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
    private Double calculateAverageDistanceFromCenter(Write exercise, 
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
     * Calculates the average distance of all the digits
     * @param exercise the write exercise to consider
     * @param digits the list of digits as comma separated values
     * @return the average distance if more than one digit, null otherwise
     */
    /*public Double calculateAverageDistanceBetweenTouchPoints(Write exercise, 
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
    }*/
    
    /**
     * Calculates the distances of each digit from the center of the clicked 
     * object
     * @param listDigits the list of digits events
     * @return a list of distances values from the center of the keyboard key
     */
    private ArrayList<Double> 
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

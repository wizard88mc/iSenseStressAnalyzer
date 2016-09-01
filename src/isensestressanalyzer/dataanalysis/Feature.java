package isensestressanalyzer.dataanalysis;

import isensestressanalyzer.ISenseStressAnalyzer;
import isensestressanalyzer.exercise.Write;
import isensestressanalyzer.interaction.Digit;
import isensestressanalyzer.tester.Tester;
import isensestressanalyzer.utils.Point;
import java.util.ArrayList;

/**
 *
 * @author Matteo
 */
public class Feature {
    
    /**
     * Calculates the distance between two points
     * @param first the first point
     * @param second the second point
     * @return the euclidian distance between two points
     */
    protected Double calculateDistance(Point first, Point second) {
        
        return Math.sqrt(Math.pow(first.getX() - second.getX(), 2) + 
                Math.pow(first.getY() - second.getY(), 2));
        
    }
    
    protected ArrayList<Digit> getListDigits(Tester tester, String digits, 
        boolean stress) {
        
        String[] listCharacters = digits.split(",");
        
        ArrayList<Digit> listDigits = new ArrayList<>();
        
        ArrayList<Write> listWriteExercises = tester.
            getWriteExercisesForProtocol(ISenseStressAnalyzer.PROTOCOLS[0]);
        
        for (String character: listCharacters) {
            for (Write writeExercises: listWriteExercises) {
                if (stress == writeExercises.stress()) {
                    listDigits.addAll(writeExercises.
                        getAllDigitsForAParticularCharacter(character));
                }
            }
        }
        
        return listDigits;
    }
}

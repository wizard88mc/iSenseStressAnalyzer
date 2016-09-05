package isensestressanalyzer.dataanalysis;

import isensestressanalyzer.interaction.Digit;
import isensestressanalyzer.tester.Tester;
import isensestressanalyzer.utils.MathUtils;
import java.util.ArrayList;

/**
 *
 * @author Matteo
 */
public class AverageDuration extends Feature {
    
    public AverageDuration() {
        super("Average_Duration");
    }

    @Override
    public Double[] calculateFeatureValues(Tester tester, String digits, 
            boolean stress) {
        
        return calculateAverageDuration(tester, digits, stress);
    }
    
    private Double[] calculateAverageDuration(Tester tester, String digits, 
        boolean stress) {
        
        ArrayList<Digit> listDigits = getListDigits(tester, digits, stress);
        
        if (!listDigits.isEmpty()) {
            return MathUtils.
                calculateStatisticInformation(getAllDurationValues(listDigits));
        }
        else {
            return null;
        }
    }
    
    private static ArrayList<Double> getAllDurationValues(ArrayList<Digit> 
        listDigits) {
        
        ArrayList<Double> duration = new ArrayList<>();
        for (Digit digit: listDigits) {
            duration.add(digit.getTouchDuration());
        }
        
        return duration;
    }
}

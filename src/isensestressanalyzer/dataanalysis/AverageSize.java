package isensestressanalyzer.dataanalysis;

import isensestressanalyzer.interaction.Digit;
import isensestressanalyzer.tester.Tester;
import isensestressanalyzer.utils.MathUtils;
import java.util.ArrayList;

/**
 * Average digit size feature
 * @author Matteo Ciman
 */
public class AverageSize extends Feature {
    
    public Double[] calculateAverageSize(Tester tester, String digits, 
        boolean stress) {
        
        ArrayList<Digit> listDigits = getListDigits(tester, digits, stress);
        
        if (!listDigits.isEmpty()) {
            return MathUtils.
                calculateStatisticInformation(getAllSizeValues(listDigits));
        }
        else {
            return null;
        }
    }
    
    private ArrayList<Double> getAllSizeValues(ArrayList<Digit> 
        listDigits) {
            
        ArrayList<Double> size = new ArrayList<>();
        for (Digit digit: listDigits) {
            size.add(digit.getSizeBasicData().getAverage());
        }
        
        return size;
    }
}

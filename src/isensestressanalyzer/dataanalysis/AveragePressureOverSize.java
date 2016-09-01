package isensestressanalyzer.dataanalysis;

import isensestressanalyzer.interaction.Digit;
import isensestressanalyzer.tester.Tester;
import isensestressanalyzer.utils.MathUtils;
import java.util.ArrayList;

/**
 *
 * @author Matteo Ciman
 */
public class AveragePressureOverSize extends Feature {
    
    public Double[] calculateAveragePressureOverSize(Tester tester, 
        String digits, boolean stress) {
        
        ArrayList<Digit> listDigits = getListDigits(tester, digits, stress);
        
        if (!listDigits.isEmpty()) {
            return MathUtils.
                calculateStatisticInformation(getAllPressureOverSizeRatios(listDigits));
        }
        else {
            return null;
        }
    }

    private ArrayList<Double> getAllPressureOverSizeRatios(
        ArrayList<Digit> listDigits) {
        
        ArrayList<Double> ratio = new ArrayList<>();
        for (Digit digit: listDigits) {
            ratio.add(digit.getPressureBasicData().getAverage() / 
                digit.getSizeBasicData().getAverage());
        }
        
        return ratio;
    }
            
            
}

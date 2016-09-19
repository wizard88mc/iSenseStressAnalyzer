package isensestressanalyzer.dataanalysis;

import isensestressanalyzer.interaction.Digit;
import isensestressanalyzer.tester.Tester;
import isensestressanalyzer.utils.MathUtils;
import java.util.ArrayList;

/**
 *
 * @author Matteo Ciman
 */
public class AveragePressure extends Feature {
    
    public AveragePressure() {
        super("Average_Pressure");
    }
    
    @Override
    public Double[] calculateFeatureValues(Tester tester, String digits, 
            boolean stress) {
        
        Double[] values = calculateAveragePressure(tester, digits, stress);
        if (values != null) {
            System.out.println("Pressure value: " + values[0]);
        }
        return values;
    }
    
    private Double[] calculateAveragePressure(Tester tester, 
            String digits, boolean stress) {
        
        ArrayList<Digit> listDigits = getListDigits(tester, digits, stress);
        
        if (!listDigits.isEmpty()) {
            return MathUtils.
                calculateStatisticInformation(getAllPressureValues(listDigits));
        }
        else {
            return null;
        }
    }
    
    /**
     * Retrieves all the pressure values
     * @param listDigits
     * @return 
     */
    private ArrayList<Double> 
        getAllPressureValues(ArrayList<Digit> listDigits) {

        ArrayList<Double> pressure = new ArrayList<>();
        for(Digit digit: listDigits) {
            pressure.add(digit.getPressureBasicData().getAverage());
        }

        return pressure;
    }
}

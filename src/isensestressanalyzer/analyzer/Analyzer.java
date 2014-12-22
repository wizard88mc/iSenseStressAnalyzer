package isensestressanalyzer.analyzer;

import isensestressanalyzer.dataanalysis.RotationDataWrapper;
import isensestressanalyzer.dataanalysis.StressNoStressData;
import isensestressanalyzer.exercise.Exercise;
import isensestressanalyzer.rotationsensor.RotationSensorData;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Matteo Ciman
 */
public class Analyzer 
{
	public static String OUTPUT_FOLDER = "data" + File.separator + 
			"output" + File.separator;
	
    protected void manageRotationSensorData(ArrayList<? extends Exercise> noStress, 
            ArrayList<? extends Exercise> stress, 
            ArrayList<RotationDataWrapper> axisDataNoStress, 
            ArrayList<RotationDataWrapper> axisDataStress, boolean divide)
    {   
        if (!divide)
        {
            ArrayList<RotationSensorData> rotationNoStress = new ArrayList<>(),
                rotationStress = new ArrayList<>();

            for (Exercise exercise: noStress)
            {
                rotationNoStress.addAll(exercise.getListRotationSensorData());
            }
            for (Exercise exercise: stress)
            {
                rotationStress.addAll(exercise.getListRotationSensorData());
            }

            axisDataNoStress.add(new RotationDataWrapper(rotationNoStress));
            axisDataStress.add(new RotationDataWrapper(rotationStress));
        }
        else
        {
            for (int i = 0; i < noStress.size(); i++)
            {
                axisDataNoStress.add(new RotationDataWrapper(noStress.get(i).getListRotationSensorData()));
                axisDataStress.add(new RotationDataWrapper(stress.get(i).getListRotationSensorData()));
            }
        }
        
    }
    
    protected static boolean[] printReport(boolean local, 
    		StressNoStressData... featuresData) {
        
    	boolean[] pass = new boolean[featuresData.length];
    	
    	for (int i = 0; i < featuresData.length; i++) {
    		pass[i] = featuresData[i].makeAndPrintTTest();
    	}
        
        return pass;
    }
}

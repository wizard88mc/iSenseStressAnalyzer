package isensestressanalyzer.analyzer;

import isensestressanalyzer.dataanalysis.RotationDataWrapper;
import isensestressanalyzer.exercise.Exercise;
import isensestressanalyzer.rotationsensor.RotationSensorData;
import java.util.ArrayList;

/**
 *
 * @author Matteo Ciman
 */
public class Analyzer 
{    
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
}

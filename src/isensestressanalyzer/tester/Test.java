package isensestressanalyzer.tester;

import isensestressanalyzer.exercise.Exercise;
import isensestressanalyzer.exercise.Protocol;
import isensestressanalyzer.exercise.Search;
import isensestressanalyzer.exercise.Stressor;
import isensestressanalyzer.exercise.Survey;
import isensestressanalyzer.exercise.Write;
import java.util.ArrayList;

/**
 * This class represents a Test for a Tester, with a protocol and a sequence of 
 * exercises
 * @author Matteo Ciman
 */
public class Test {
    
    private final Protocol protocol;
    private final ArrayList<Exercise> listExercises;
    
    public Test(Protocol protocol, ArrayList<Exercise> listExercises) {
        this.protocol = protocol; this.listExercises = listExercises;
    }
    
    public Protocol getProtocol() {
        return this.protocol;
    }
    
    /**
     * Returns all the Write exercises in this Test
     * @return a List of Write exercises
     */
    public ArrayList<Write> getWriteExercises() {
        
        ArrayList<Write> toReturn = new ArrayList<>();
        
        for (Exercise exercise: listExercises) {
            if (exercise instanceof Write) {
                toReturn.add((Write) exercise);
            }
        }
        return toReturn;
    }
    
    /**
     * Returns all the Search exercises in this Test
     * @return a list of Search exercises
     */
    public ArrayList<Search> getSearchExercises() {
        ArrayList<Search> toReturn = new ArrayList<>();
        
        for (Exercise exercise: listExercises)
        {
            if (exercise instanceof Search)
            {
                toReturn.add((Search) exercise);
            }
        }
        return toReturn;
    }
    
    /**
     * Returns all the Survey exercises in the Test
     * @return a list of Survey objects
     */
    public ArrayList<Survey> getSurveys() {
        
        ArrayList<Survey> toReturn = new ArrayList<>();
        
        for (Exercise exercise: listExercises) {
            if (exercise instanceof Survey) {
                toReturn.add((Survey) exercise);
            }
        }
        
        return toReturn;
    }
    
    public ArrayList<Stressor> getStressorExercises() {
        
        ArrayList<Stressor> toReturn = new ArrayList<>();
        
        for (Exercise exercise: listExercises) {
            if (exercise instanceof Stressor) {
                toReturn.add((Stressor) exercise);
            }
        }
        
        return toReturn;
    }
}

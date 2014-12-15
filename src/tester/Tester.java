package tester;

import isensestressanalyzer.analyzer.SearchAnalyzer;
import isensestressanalyzer.analyzer.WriteAnalyzer;
import isensestressanalyzer.exercise.Exercise;
import isensestressanalyzer.exercise.Protocol;
import isensestressanalyzer.exercise.Search;
import isensestressanalyzer.exercise.Survey;
import isensestressanalyzer.exercise.Write;
import isensestressanalyzer.utils.PhoneSettings;
import java.util.ArrayList;

/**
 * This class represents a tester with a name (IMEI) and a list of Test performed
 * @author Matteo Ciman
 */
public class Tester 
{
    private final String name;
    private String gender;
    private String nickname;
    private String email;
    private int age;
    /**
     * Each single exercise (Write or Search) is a test
     */
    private ArrayList<Test> listTests = new ArrayList<>();
    private WriteAnalyzer mWriteAnalyzer = new WriteAnalyzer();
    private SearchAnalyzer mSearchAnalyzer = new SearchAnalyzer();
    private ArrayList<Survey> mListSurvey = new ArrayList<>();
    private PhoneSettings mPhoneSettigns = null;
    
    public Tester(String name)
    {
        this.name = name;
    }
    
    /**
     * Sets the phone settings for the Tester
     * @param mPhoneSettings 
     */
    public void setPhoneSettings(PhoneSettings mPhoneSettings)
    {
        if (this.mPhoneSettigns == null)
        {
            this.mPhoneSettigns = mPhoneSettings;
        }
    }
    
    /**
     * Add a new test to the list of performed tests
     * @param test the new test 
     */
    public void addTest(Test test)
    {
        listTests.add(test);
    }
    
    /**
     * Retrieves all the Write exercises of a Tester for a particular protocol
     * (if defined, all the Write exercises otherwise)
     * @param protocol
     * @return a List of Write exercises
     */
    public ArrayList<Write> getWriteExercisesForProtocol(Protocol protocol)
    {
        ArrayList<Write> exercises = new ArrayList<>();
            // Search for all the writing exercises of a particular protocol
        for (Test test: listTests)
        {
            if (protocol != null)
            {
                if (test.getProtocol().toString().equals(protocol.toString()))
                {
                    exercises.addAll(test.getWritingExercises());
                }
            }
            else
            {
                exercises.addAll(test.getWritingExercises());
            }
        }
        return exercises;
    }
    
    /**
     * Return all the Search exercises for a specific protocol 
     * @param protocol the searched protocol (null if we do not care about the protocol)
     * @return a list of Search exercises
     */
    public ArrayList<Search> getSearchExercisesForProtocol(Protocol protocol)
    {
        ArrayList<Search> exercises = new ArrayList<>();
        
        for (Test test: listTests)
        {
            if (protocol != null)
            {
                if (test.getProtocol().toString().equals(protocol.toString()))
                {
                    exercises.addAll(test.getSearchExercises());
                }
            }
            else
            {
                exercises.addAll(test.getSearchExercises());
            }
        }
        return exercises;
    }
    
    public ArrayList<Survey> getSurveyListForProtocol(Protocol protocol) {
        
        ArrayList<Survey> surveys = new ArrayList<>();
        
        for (Test test: listTests) {
            if (protocol != null) {
                if (test.getProtocol().toString().equals(protocol.toString())) {
                    surveys.addAll(test.getSurveys());
                }
            }
            else {
                surveys.addAll(test.getSurveys());
            }
        }
        
        return surveys;
    }
    
    public static void touchExerciseClassification(ArrayList<Exercise> exercises)
    {
        int indexStressor =  -1;
        for (int i = 0; i < exercises.size() && indexStressor == -1; i++)
        {
            if (exercises.get(i).getBasicString().equals(Exercise.STRESSOR_STRING))
            {
                indexStressor = i;
            }
        }
        
        if (indexStressor != -1)
        {
            for (int i = indexStressor + 1; i < exercises.size(); i++)
            {
                exercises.get(i).setStress(true);
            }
        }
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public PhoneSettings getPhoneSettings()
    {
        return this.mPhoneSettigns;
    }
    
    public void performWriteAnalysis()
    {
        this.mWriteAnalyzer.performAnalysis(this);
    }
    
    public WriteAnalyzer getWriteAnalyzer()
    {
        return mWriteAnalyzer;
    }
    
    public SearchAnalyzer getSearchAnalyzer()
    {
        return this.mSearchAnalyzer;
    }
    
    public void setUserDetails(String details) {
        details = details.replace("User: ", "").trim();
       
        String[] elements = details.split(",");
        gender = elements[0]; age = Integer.valueOf(elements[1]); 
        email = elements[1]; nickname = elements[2];
    }
}

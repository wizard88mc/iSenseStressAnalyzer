package isensestressanalyzer.tester;

import isensestressanalyzer.ISenseStressAnalyzer;
import isensestressanalyzer.analyzer.SearchAnalyzer;
import isensestressanalyzer.analyzer.WriteAnalyzer;
import isensestressanalyzer.exercise.Exercise;
import isensestressanalyzer.exercise.Protocol;
import isensestressanalyzer.exercise.Search;
import isensestressanalyzer.exercise.Stressor;
import isensestressanalyzer.exercise.Survey;
import isensestressanalyzer.exercise.Write;
import isensestressanalyzer.outputwriter.OutputWriter;
import isensestressanalyzer.utils.PhoneSettings;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * This class represents a tester with a name (IMEI) and a list of Test performed
 * @author Matteo Ciman
 */
public class Tester {
    
    private final String name;
    private String gender;
    private String nickname;
    private String email;
    private int age;
    /**
     * Each single exercise (Write or Search) is a test
     */
    private final ArrayList<Test> listTests = new ArrayList<>();
    private final WriteAnalyzer mWriteAnalyzer = new WriteAnalyzer();
    private final SearchAnalyzer mSearchAnalyzer = new SearchAnalyzer();
    private final ArrayList<Survey> mListSurvey = new ArrayList<>();
    private PhoneSettings mPhoneSettigns = null;
    
    public Tester(String name) {
        
        this.name = name;
    }
    
    /**
     * Sets the phone settings for the Tester
     * @param mPhoneSettings 
     */
    public void setPhoneSettings(PhoneSettings mPhoneSettings) {
        
        if (this.mPhoneSettigns == null) {
            this.mPhoneSettigns = mPhoneSettings;
        }
    }
    
    /**
     * Add a new test to the list of performed tests
     * @param test the new test 
     */
    public void addTest(Test test) {
        listTests.add(test);
    }
    
    /**
     * Retrieves all the Write exercises of a Tester for a particular protocol
     * (if defined, all the Write exercises otherwise)
     * @param protocol
     * @return a List of Write exercises
     */
    public ArrayList<Write> getWriteExercisesForProtocol(Protocol protocol) {
        
        ArrayList<Write> exercises = new ArrayList<>();
        // Search for all the writing exercises of a particular protocol
        for (Test test: listTests) {
            
            if (protocol != null) {
                if (test.getProtocol().toString().equals(protocol.toString())) {
                    exercises.addAll(test.getWriteExercises());
                }
            }
            else {
                exercises.addAll(test.getWriteExercises());
            }
        }
        return exercises;
    }
    
    /**
     * Return all the Search exercises for a specific protocol 
     * @param protocol the searched protocol (null if we do not care about the protocol)
     * @return a list of Search exercises
     */
    public ArrayList<Search> getSearchExercisesForProtocol(Protocol protocol) {
        
        ArrayList<Search> exercises = new ArrayList<>();
        
        for (Test test: listTests) {
            if (protocol != null) {
                if (test.getProtocol().toString().equals(protocol.toString())) {
                    exercises.addAll(test.getSearchExercises());
                }
            }
            else {
                exercises.addAll(test.getSearchExercises());
            }
        }
        return exercises;
    }
    
    /**
     * Return all the Stressor exercises for a specific protocol 
     * @param protocol the searched protocol (null if we do not care about the protocol)
     * @return a list of Stressor exercises
     */
    public ArrayList<Stressor> getStressorExercisesForProtocol(Protocol protocol) {
        
        ArrayList<Stressor> exercises = new ArrayList<>();
        
        for (Test test: listTests) {
            if (protocol != null) {
                if (test.getProtocol().toString().equals(protocol.toString())) {
                    exercises.addAll(test.getStressorExercises());
                }
            }
            else {
                exercises.addAll(test.getStressorExercises());
            }
        }
        return exercises;
    }
    
    /**
     * Returns the list of Surveys of the tester for a particular protocol
     * @param protocol the protocol to search (null if we are considering all
     * the protocols)
     * @return a list of Survey objects
     */
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
    
    /**
     * Changes the stress classification to put as stressed exercises after
     * the stressor task (necessary for a particular protocol)
     * @param exercises
     */
    public static void touchExerciseClassification(ArrayList<Exercise> exercises) {
        int indexStressor =  -1;
        for (int i = 0; i < exercises.size() && indexStressor == -1; i++) {
            
            if (exercises.get(i).getBasicString().equals(Exercise.STRESSOR_STRING)) {
                indexStressor = i;
            }
        }
        
        if (indexStressor != -1) {
            for (int i = indexStressor + 1; i < exercises.size(); i++) {
                exercises.get(i).setStress(true);
            }
        }
    }
    
    /**
     * Returns the name of the tester
     * @return tester name
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Returns the phone settings for the tester
     * @return the Phone Settings
     */
    public PhoneSettings getPhoneSettings() {
        return this.mPhoneSettigns;
    }
    
    /**
     * Calls on the WriteAnalyzer to perform the data analysis for the tester
     */
    public void performWriteAnalysis() {
        this.mWriteAnalyzer.performAnalysis(this);
    }
    
    /**
     * Returns the WriteAnalyzer
     * @return the write analyzer
     */
    public WriteAnalyzer getWriteAnalyzer() {
        return mWriteAnalyzer;
    }
    
    /**
     * Returns the Search analyzer
     * @return the search analyzer
     */
    public SearchAnalyzer getSearchAnalyzer() {
        return this.mSearchAnalyzer;
    }
    
    /**
     * Saves user details
     * @param details the string of the settings file with the user information
     */
    public void setUserDetails(String details) {
    	if (details != null) {
	        details = details.replace("User: ", "").trim();
	       
	        String[] elements = details.split(",");
	        gender = elements[0]; age = Integer.valueOf(elements[1]); 
	        email = elements[1]; nickname = elements[2];
    	}
    }
    
    /**
     * Creates the ARFF files with scroll information for the tester
     */
    public void createARFFFileForSearchTask() {
    	
    	try {
    		
            File fileVertical = new File(OutputWriter.OUTPUT_FOLDER + 
                this.getName() + File.separator + "VerticalScroll.arff"), 
            fileHorizontal = new File(OutputWriter.OUTPUT_FOLDER + 
                this.getName() + File.separator + "HorizontalScroll.arff"),
            fileVerticalHorizontal = new File(OutputWriter.OUTPUT_FOLDER + 
                this.getName() + File.separator + "VerticalHorizontalScroll.arff");

            if (!fileVertical.exists()) {
                    fileVertical.getParentFile().mkdirs();
                    fileVertical.createNewFile();
            }
            if (!fileHorizontal.exists()) {
                    fileVertical.getParentFile().mkdirs();
                    fileHorizontal.createNewFile();
            }
            if (!fileVerticalHorizontal.exists()) {
                    fileVerticalHorizontal.getParentFile().mkdirs();
                    fileVerticalHorizontal.createNewFile();
            }

            BufferedWriter writerVertical = 
                    new BufferedWriter(new FileWriter(fileVertical)), 
                writerHorizontal = 
                    new BufferedWriter(new FileWriter(fileHorizontal)), 
                writerVerticalHorizontal = 
                    new BufferedWriter(new FileWriter(fileVerticalHorizontal));

            createPreambleARFFFile(writerVertical, false, true, false);
            createPreambleARFFFile(writerHorizontal, false, false, true);
            createPreambleARFFFile(writerVerticalHorizontal, false, true, true);

            addScrollsToFile(this, writerVertical, true, false);
            addScrollsToFile(this, writerHorizontal, false, true);
            addScrollsToFile(this, writerVerticalHorizontal, true, true);

            writerVertical.flush(); writerVertical.close();
            writerHorizontal.flush(); writerHorizontal.close();
            writerVerticalHorizontal.flush(); writerVerticalHorizontal.close();

            ISenseStressAnalyzer.listCreatedFiles.add(fileVertical);
            ISenseStressAnalyzer.listCreatedFiles.add(fileHorizontal);
            ISenseStressAnalyzer.listCreatedFiles.add(fileVerticalHorizontal);
    	}
    	catch(Exception exc) {
            System.out.println(exc.toString());
            exc.printStackTrace();
    	}
    }
    
    /**
     * Creates the ARFF file with Digit data for the tester
     */
    public void createARFFFileForWriteTask() {
    	try {
    		
            File fileOutput = new File(OutputWriter.OUTPUT_FOLDER +
                this.getName() + File.separator + "write.arff");
    	    
            if (!fileOutput.exists()) {
                fileOutput.getParentFile().mkdirs();
                fileOutput.createNewFile();
            }
    		
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileOutput));
            createPreambleARFFFile(writer, true, false, false);
    		
            addDigitsToFile(this, writer);
    		
            writer.flush(); writer.close();
            ISenseStressAnalyzer.listCreatedFiles.add(fileOutput);	
    	}
    	catch(Exception exc) {}
    }

    /**
     * Creates an ARFF file with all the attributes for all the testers, 
     * trying to build a global model for stress recognition
     * @param mListTester the list of Tester
     */
    public static void createGlobalARFF(ArrayList<Tester> mListTester) {

        try {

            File globalFileVertical = new File(OutputWriter.OUTPUT_FOLDER + 
                    "globalModelVertical.arff"),
                globalFileHorizontal = new File(OutputWriter.OUTPUT_FOLDER + 
                    "globalModelHorizontal.arff"),
                globalFileVerticalHorizontal = new File(OutputWriter.OUTPUT_FOLDER + 
                    "gloablModelVerticalHorizontal.arff"), 
                globalFileWrite = new File(OutputWriter.OUTPUT_FOLDER + 
                    "globalModelWrite.arff");

            if (!globalFileVertical.exists()) {
                globalFileVertical.createNewFile();
            }
            if (!globalFileHorizontal.exists()) {
                globalFileHorizontal.createNewFile();
            }
            if (!globalFileVerticalHorizontal.exists()) {
                globalFileVerticalHorizontal.createNewFile();
            }
            if (!globalFileWrite.exists()) {
                globalFileWrite.createNewFile();
            }

            BufferedWriter writerVertical = 
                    new BufferedWriter(new FileWriter(globalFileVertical)),
                writerHorizontal = 
                    new BufferedWriter(new FileWriter(globalFileHorizontal)), 
                writerVerticalHorizontal = 
                    new BufferedWriter(new FileWriter(globalFileVerticalHorizontal)), 
                writerWrite = new BufferedWriter(new FileWriter(globalFileWrite));

            createPreambleARFFFile(writerVertical, false, true, false);
            createPreambleARFFFile(writerHorizontal, false, false, true);
            createPreambleARFFFile(writerVerticalHorizontal, false, true, true);
            createPreambleARFFFile(writerWrite, true, false, false);

            for (Tester tester: mListTester) {
                addScrollsToFile(tester, writerVertical, true, false);
                addScrollsToFile(tester, writerHorizontal, false, true);
                addScrollsToFile(tester, writerVerticalHorizontal, true, true);
                addDigitsToFile(tester, writerWrite);
            }

            writerVertical.flush(); writerVertical.close();
            writerHorizontal.flush(); writerHorizontal.close();
            writerVerticalHorizontal.flush(); writerVerticalHorizontal.close();

            ISenseStressAnalyzer.listCreatedFiles.add(globalFileVertical);
            ISenseStressAnalyzer.listCreatedFiles.add(globalFileHorizontal);
            ISenseStressAnalyzer.listCreatedFiles.add(globalFileVerticalHorizontal);
        }
        catch(Exception exc) {}
    }

    /**
     * Adds to the ARFF file the preamble for ARFF files with the list of 
     * attributes and classes
     * @param writer BufferedWriter to write the preamble
     * @param write if add digits attributes
     * @param vertical if add vertical scroll attributes
     * @param horizontal if add horizontal scroll attributes
     */
    private static void createPreambleARFFFile(BufferedWriter writer, 
            boolean write, boolean vertical, boolean horizontal) {

        try {
            writer.write("@RELATION stress"); writer.newLine();
            if (vertical) {
        
                ArrayList<String> attributesVertical = 
                        Search.getListARFFAttributesNames(true);
                    
                for (String attribute: attributesVertical) {
                    writer.write("@ATTRIBUTE " + attribute + " NUMERIC");
                    writer.newLine();
                }
            }
            
            if (horizontal) {
                ArrayList<String> attributesHorizontal = 
                        Search.getListARFFAttributesNames(false);
                for (String attribute: attributesHorizontal) {
                    writer.write("@ATTRIBUTE " + attribute + " NUMERIC");
                    writer.newLine();
                }
            }
            
            if (write) {
               
                ArrayList<String> attributesWrite = Write.getARFFListAttributesNames();
                for (String attribute: attributesWrite) {
                    writer.write("@ATTRIBUTE " + attribute + " NUMERIC");
                    writer.newLine();
                }
            }

            writer.write("@ATTRIBUTE class {no-stress, stress}"); writer.newLine();
            writer.write("@data"); writer.newLine();
        } 
        catch (Exception e) {
            e.printStackTrace();
        } 

    }

    /**
     * Adds scroll attributes to the ARFF file
     * @param tester the tester to take the scroll data
     * @param writer the BufferedWriter where write
     * @param vertical if retrieve vertical scroll attributes
     * @param horizontal if retrieve horizontal scroll attributes
     */
    private static void addScrollsToFile(Tester tester, BufferedWriter writer, 
        boolean vertical, boolean horizontal) {

        try {

            ArrayList<Search> searches = tester.
                    getSearchExercisesForProtocol(ISenseStressAnalyzer.PROTOCOLS[0]);

            for (Search exercise: searches) {

                ArrayList<ArrayList<Double>> attribuesForAllVerticalScrolls = 
                    exercise.ARFFAttributesForScroll(true, 
                            tester.getPhoneSettings()),

                    attributesForAllHorizontalScrolls = 
                        exercise.ARFFAttributesForScroll(false, 
                                tester.getPhoneSettings());

                if (vertical) {

                    for (ArrayList<Double> attributesValues: 
                            attribuesForAllVerticalScrolls) {

                        if (attributesValues.size() == 
                            Search.getListARFFAttributesNames(true).size()) {

                            String finalListAttributes = "";

                            for (Double value: attributesValues) {
                                finalListAttributes += value + ",";
                            }

                            if (horizontal) {
                                finalListAttributes += 
                                    Tester.createStringMissingAttributes(
                                        Search.getListARFFAttributesNames(false)
                                            .size());
                            }

                            if (exercise.stress()) { 
                                finalListAttributes += "stress";
                            }
                            else {
                                finalListAttributes += "no-stress";
                            }

                            if (!finalListAttributes.contains("null")) {
                                writer.write(finalListAttributes); writer.newLine();
                            }
                        }
                    }
                }

                if (horizontal) {

                    for (ArrayList<Double> attributesValues: 
                            attributesForAllHorizontalScrolls) {

                        if (attributesValues.size() == 
                            Search.getListARFFAttributesNames(false).size()) {

                            String finalListAttributes = "";
                            if (vertical) {
                                finalListAttributes = Tester.
                                    createStringMissingAttributes(Search.
                                        getListARFFAttributesNames(true).
                                        size());
                            }

                            for (Double value: attributesValues) {
                                finalListAttributes += value + ",";
                            }

                            if (exercise.stress()) {
                                finalListAttributes += "stress";
                            }
                            else {
                                finalListAttributes += "no-stress";
                            }

                            if (!finalListAttributes.contains("null")) {
                                writer.write(finalListAttributes); writer.newLine();
                            }
                        }
                    }
                }
            }
        }
        catch(Exception exc) {
            exc.printStackTrace();
        }
    }

    /**
     * Adds digits attributes to the ARFF file
     * @param tester tester to take the digits data
     * @param writer BufferedWriter where write 
     */
    private static void addDigitsToFile(Tester tester, BufferedWriter writer) {

        try {

            ArrayList<Write> allWrites = 
                    tester.getWriteExercisesForProtocol(ISenseStressAnalyzer.PROTOCOLS[0]);

            for (Write writeExc: allWrites) {

                ArrayList<ArrayList<Double>> attributesForDigits = 
                    writeExc.ARFFAttributesForWrite();

                for (ArrayList<Double> attributes: attributesForDigits) {

                    if (attributes.size() == 
                        Write.getARFFListAttributesNames().size()) {

                        String stringAttributes = "";
                        for (Double value: attributes) {
                            if (value != null) {
                                stringAttributes += value + ",";
                            }
                            else {
                                stringAttributes += "?,";
                            }
                        }

                        if (writeExc.stress()) {
                            stringAttributes += "stress";
                        }
                        else {
                            stringAttributes += "no-stress";
                        }

                        writer.write(stringAttributes); writer.newLine();
                    }
                }
            }
        }
        catch(Exception exc) {

        }
    }

    /**
     * If some attributes are missing, creates a sequence of ? depending on 
     * the number of missing attributes
     * @param numberAttributes the number of missing attributes
     * @return a string as a sequence of ?,?.. depending on the number of missing
     * attributes
     */
    private static String createStringMissingAttributes(int numberAttributes) {

        String toReturn = "";
        for (int i = 0; i < numberAttributes; i++) {
                toReturn += "?,";
        }
        return toReturn;
    }
}

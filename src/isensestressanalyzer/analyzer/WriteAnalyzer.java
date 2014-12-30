package isensestressanalyzer.analyzer;

import isensestressanalyzer.ISenseStressAnalyzer;
import isensestressanalyzer.exercise.Write;
import isensestressanalyzer.interaction.Digit;
import isensestressanalyzer.interaction.Interaction;
import isensestressanalyzer.tester.Tester;
import isensestressanalyzer.utils.KeyboardKeyObject;
import isensestressanalyzer.utils.ScreenObject;
import isensestressanalyzer.dataanalysis.BasicDataStatistic;
import isensestressanalyzer.dataanalysis.RotationDataWrapper;
import isensestressanalyzer.dataanalysis.StressNoStressData;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.tc33.jheatchart.HeatChart;

/**
 * Handles all the Writing exercises, both in stress and non stress mode, 
 * and all the digits for a single user
 * @author Matteo Ciman
 */
public class WriteAnalyzer extends Analyzer
{   
    private static final String[] featuresName = new String[]{"Digits pressure", 
        "Digits size", "Pressure size ratio", "Digits movement", "Digits duration", "Digits precision", 
        "Ratio BACK over all digits", "Ratio wrong words over all words", 
        "Digits time distance"};
    private static int[] featurePassesTestForTester = new int[]{0, 0, 0, 0, 0, 
    	0, 0, 0, 0};
    private static int totalTesters = 0;
    private ArrayList<WriteAnalysisResume> noStressResumes = new ArrayList<>();
    private ArrayList<WriteAnalysisResume> stressResumes = new ArrayList<>();
    /**
     * Performs all the operations necessary to calculate the information 
     * and the statistics about the exercises not stress and stress
     * @param tester
     */
    public void performAnalysis(Tester tester)
    {
        // I extract the writing exercise for that particular
        // protocol and analyze them    
        ArrayList<RotationDataWrapper> rotationNoStress = new ArrayList<>(), 
                    rotationStress = new ArrayList<>();
        
        ArrayList<Write> exercises = 
                tester.getWriteExercisesForProtocol(isensestressanalyzer.ISenseStressAnalyzer.protocols[0]);
        /**
         * Now I have all the exercises for that particular protocol
         * for this particular user. I have to perform analysis for all 
         * the info, add them
         */
        if (!exercises.isEmpty())
        {   
            for (Write exercise: exercises)
            {
                WriteAnalysisResume resume = new WriteAnalysisResume();
                   resume.pressureData(exercise.getPressionDigitsBasicData());
                   resume.sizeData(exercise.getSizeDigitsBasicData());
                   resume.ratioPressureSizeData(exercise.getRatioPressureSizeData());
                   resume.movementData(exercise.getMovementDigitsBasicData());
                   resume.durationData(exercise.getDurationDigitsBasicData());
                   resume.precisionData(exercise.getTouchPrecisionDigitsBasicData());
                   resume.ratioBackOverDigitsData(
                           new BasicDataStatistic(exercise.getRatioBackButtonsOverDigits()));
                   resume.ratioWrongAllWords(new
                        BasicDataStatistic(ratioWrongTotalWords(exercise.getTextToWrite(), 
                                exercise.getWrittenText())));
                   resume.digitsFrequency(new BasicDataStatistic(exercise.calculateDigitsFrequency()));
               
                if (!exercise.stress())
                {
                    noStressResumes.add(resume);
                }
                else
                {
                   stressResumes.add(resume);
               }
            }
        }
    }
    
    /**
     * Calculates the mean value of the pressure feature
     * @param stress if consider
     * @return the average pressure data
     */
    private Double getMeanPressureData(boolean stress)
    {
        double mean = 0;
        ArrayList<WriteAnalysisResume> toUse = noStressResumes;
        if (stress)
        {
            toUse = stressResumes;
        }
        for (WriteAnalysisResume resume: toUse)
        {
            mean += resume.getPressureData().getAverage();
        }
        return mean / toUse.size();
    }
    
    /**
     * Retrieves all the pressure data
     * @param stress if retrieve stress or relax data
     * @return a list with all the pressure values
     */
    private ArrayList<Double> getAllPressureData(boolean stress) {
        
        ArrayList<WriteAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        ArrayList<Double> valuesToReturn = new ArrayList<>();
        for (WriteAnalysisResume resume: toUse) {
            valuesToReturn.add(resume.getPressureData().getAverage());
        }
        return valuesToReturn;
    }
    
    /**
     * Calculates the mean value of the size of the touch
     * @param stress if relax o stress data
     * @return the mean value of the size data
     */
    private Double getMeanSizeData(boolean stress)
    {
        double mean = 0;
        ArrayList<WriteAnalysisResume> toUse = noStressResumes;
        if (stress)
        {
            toUse = stressResumes;
        }
        for (WriteAnalysisResume resume: toUse)
        {
            mean += resume.getSizeData().getAverage();
        }
        return mean / toUse.size();
    }
    
    /**
     * Retrieves all the size data of the digits
     * @param stress if use relax or stress data
     * @return all the values for size feature
     */
    public ArrayList<Double> getAllSizeData(boolean stress) {
        ArrayList<WriteAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        ArrayList<Double> valuesToReturn = new ArrayList<>();
        for (WriteAnalysisResume resume: toUse) {
            valuesToReturn.add(resume.getSizeData().getAverage());
        }
        return valuesToReturn;
    }
    
    /**
     * Returns the mean value of the ratio between the pressure and the size 
     * of all the digits
     * @param stress: if stress or not
     * @return the mean value of the pressure / size ratio
     */
    private Double getMeanRatioPressureSizeData(boolean stress) {
    	
    	double mean = 0;
    	ArrayList<WriteAnalysisResume> toUse = noStressResumes;
    	if (stress) {
    		toUse = stressResumes;
    	}
    	for (WriteAnalysisResume resume: toUse) {
    		mean += resume.getRatioPressureSizeData().getAverage();
    	}
    	return mean / toUse.size();
    }
    
    /**
     * Returns all the value of the ratio between the pressure and the size of
     * the digits
     * @param stress is tress or not
     * @return a List with all the pressure / size ratios
     */
    public ArrayList<Double> getAllRatioPressureSizeData(boolean stress) {
    	ArrayList<WriteAnalysisResume> toUse = noStressResumes;
    	if (stress) {
    		toUse = stressResumes;
    	}
    	ArrayList<Double> valuesToReturn = new ArrayList<>();
    	for (WriteAnalysisResume resume: toUse) {
    		valuesToReturn.add(resume.getRatioPressureSizeData().getAverage());
    	}
    	return valuesToReturn;
    }
    
    /**
     * Calculates the average movement while digiting
     * @param stress use relax or stress data
     * @return the average movement
     */
    private Double getMeanMovementData(boolean stress)
    {
        double mean = 0;
        ArrayList<WriteAnalysisResume> toUse = noStressResumes;
        if (stress)
        {
            toUse = stressResumes;
        }
        for (WriteAnalysisResume resume: toUse)
        {
            mean += resume.getMovementData().getAverage();
        }
        return mean / toUse.size();
    }
    
    /**
     * Retrieves all the data for movement feature
     * @param stress use relax or stress value
     * @return a list of movement data
     */
    public ArrayList<Double> getAllMovementData(boolean stress) {
        ArrayList<WriteAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        ArrayList<Double> valuesToReturn = new ArrayList<>();
        for (WriteAnalysisResume resume: toUse) {
            valuesToReturn.add(resume.getMovementData().getAverage());
        }
        return valuesToReturn;
    }
    
    /**
     * Calculates mean value for digit duration
     * @param stress use relax or stress data
     * @return the average digit duration
     */
    private Double getMeanDurationData(boolean stress) {
        
        double mean = 0;
        ArrayList<WriteAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        for (WriteAnalysisResume resume: toUse) {
            mean += resume.getDurationData().getAverage();
        }
        return mean / toUse.size();
    }
    
    /**
     * Retrieves all the digit duration values
     * @param stress use relax or stress value
     * @return a list with all the duration values
     */
    private ArrayList<Double> getAllDurationData(boolean stress) {
        ArrayList<WriteAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        ArrayList<Double> valuesToReturn = new ArrayList<>();
        for (WriteAnalysisResume resume: toUse) {
            valuesToReturn.add(resume.getDurationData().getAverage());
        }
        return valuesToReturn;
    }
    
    /**
     * Calculates the mean value of the precision evaluation
     * @param stress use relax or stress values
     * @return the mean precision value
     */
    private Double getMeanPrecisionData(boolean stress) {
        
        double mean = 0;
        ArrayList<WriteAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        for (WriteAnalysisResume resume: toUse) {
            mean += resume.getPrecisionData().getAverage();
        }
        return mean / toUse.size();
    }
    
    /**
     * Retrieves all the values of the precision evaluation
     * @param stress use relax or stress data
     * @return a list of precision data
     */
    private ArrayList<Double> getAllPrecisionData(boolean stress) {
        ArrayList<WriteAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        ArrayList<Double> valuesToReturn = new ArrayList<>();
        for (WriteAnalysisResume resume: toUse) {
            valuesToReturn.add(resume.getPrecisionData().getAverage());
        }
        return valuesToReturn;
    }
    
    /**
     * Calculates the average ratio of back digits over all the digits
     * @param stress use relax or stress data
     * @return the mean ratio back digits over all the digits
     */
    private Double getMeanRatioBackOverDigits(boolean stress) {
        
        double mean = 0;
        ArrayList<WriteAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        for (WriteAnalysisResume resume: toUse) {
            mean += resume.getRatioBackOverDigits().getAverage();
        }
        return mean / toUse.size();
    }
    
    /**
     * Retrieves all the values of the ratio of back digits over all the digits
     * @param stress use relax or stress data
     * @return a list of ratio back digits 
     */
    private ArrayList<Double> getAllRatioBackOVerDigits(boolean stress) {
        ArrayList<WriteAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        ArrayList<Double> valuesToReturn = new ArrayList<>();
        for (WriteAnalysisResume resume: toUse) {
            valuesToReturn.add(resume.getRatioBackOverDigits().getAverage());
        }
        return valuesToReturn;
    }
    
    private Double getMeanRatioWrongAllWords(boolean stress) {
        
        double mean = 0;
        ArrayList<WriteAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        for (WriteAnalysisResume resume: toUse) {
            mean += resume.getRatioWrongAllWords().getAverage();
        }
        return mean / toUse.size();
    }
    
    private ArrayList<Double> getAllRatioWrongAllWords(boolean stress) {
        ArrayList<WriteAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        ArrayList<Double> valuesToReturn = new ArrayList<>();
        for (WriteAnalysisResume resume: toUse) {
            valuesToReturn.add(resume.getRatioWrongAllWords().getAverage());
        }
        return valuesToReturn;
    }
    
    private Double getMeanDigitsFrequency(boolean stress) {
        
        double mean = 0;
        ArrayList<WriteAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        for (WriteAnalysisResume resume: toUse) {
            mean += resume.getDigitsFrequencyData().getAverage();
        }
        return mean / toUse.size();
    }
    
    private ArrayList<Double> getAllDigitsFrequency(boolean stress) {
        ArrayList<WriteAnalysisResume> toUse = noStressResumes;
        if (stress) {
            toUse = stressResumes;
        }
        ArrayList<Double> valuesToReturn = new ArrayList<>();
        for (WriteAnalysisResume resume: toUse) {
            valuesToReturn.add(resume.getDigitsFrequencyData().getAverage());
        }
        return valuesToReturn;
    }
    
    public static void performGlobalAnalysis(ArrayList<Tester> listTester)
    {
        ArrayList<ArrayList<Double>> pressureData = new ArrayList<>(),
                sizeData = new ArrayList<>(), ratioPressureSizeData = new ArrayList<>(),
                movementData = new ArrayList<>(), durationData = new ArrayList<>(), 
                precisionData = new ArrayList<>(), ratioBackOverDigitsData = new ArrayList<>(), 
                ratioWrongAllWordsData = new ArrayList<>(), digitsFrequencyData = new ArrayList<>();
        
        for (Tester tester: listTester) {
            
            if (pressureData.isEmpty()) {
                pressureData.add(new ArrayList<Double>()); pressureData.add(new ArrayList<Double>());
                sizeData.add(new ArrayList<Double>()); sizeData.add(new ArrayList<Double>());
                ratioPressureSizeData.add(new ArrayList<Double>()); ratioPressureSizeData.add(new ArrayList<Double>());
                movementData.add(new ArrayList<Double>()); movementData.add(new ArrayList<Double>());
                durationData.add(new ArrayList<Double>()); durationData.add(new ArrayList<Double>());
                precisionData.add(new ArrayList<Double>()); precisionData.add(new ArrayList<Double>());
                ratioBackOverDigitsData.add(new ArrayList<Double>()); ratioBackOverDigitsData.add(new ArrayList<Double>());
                ratioWrongAllWordsData.add(new ArrayList<Double>()); ratioWrongAllWordsData.add(new ArrayList<Double>());
                digitsFrequencyData.add(new ArrayList<Double>()); digitsFrequencyData.add(new ArrayList<Double>());
            }
            
            pressureData.get(0).add(tester.getWriteAnalyzer().getMeanPressureData(false)); 
            pressureData.get(1).add(tester.getWriteAnalyzer().getMeanPressureData(true));
            sizeData.get(0).add(tester.getWriteAnalyzer().getMeanSizeData(false));
            sizeData.get(1).add(tester.getWriteAnalyzer().getMeanSizeData(true));
            ratioPressureSizeData.get(0).add(tester.getWriteAnalyzer().getMeanRatioPressureSizeData(false));
            ratioPressureSizeData.get(1).add(tester.getWriteAnalyzer().getMeanRatioPressureSizeData(true));
            movementData.get(0).add(tester.getWriteAnalyzer().getMeanMovementData(false));
            movementData.get(1).add(tester.getWriteAnalyzer().getMeanMovementData(true));
            durationData.get(0).add(tester.getWriteAnalyzer().getMeanDurationData(false));
            durationData.get(1).add(tester.getWriteAnalyzer().getMeanDurationData(true));
            precisionData.get(0).add(tester.getWriteAnalyzer().getMeanPrecisionData(false));
            precisionData.get(1).add(tester.getWriteAnalyzer().getMeanPrecisionData(true));
            ratioBackOverDigitsData.get(0).add(tester.getWriteAnalyzer().getMeanRatioBackOverDigits(false));
            ratioBackOverDigitsData.get(1).add(tester.getWriteAnalyzer().getMeanRatioBackOverDigits(true));
            ratioWrongAllWordsData.get(0).add(tester.getWriteAnalyzer().getMeanRatioWrongAllWords(false));
            ratioWrongAllWordsData.get(1).add(tester.getWriteAnalyzer().getMeanRatioWrongAllWords(true));
            digitsFrequencyData.get(0).add(tester.getWriteAnalyzer().getMeanDigitsFrequency(false));
            digitsFrequencyData.get(1).add(tester.getWriteAnalyzer().getMeanDigitsFrequency(true));
        }
        
        printReport(false, new StressNoStressData(featuresName[0], pressureData.get(0), pressureData.get(1)), 
                new StressNoStressData(featuresName[1], sizeData.get(0), sizeData.get(1)), 
                new StressNoStressData(featuresName[2], ratioPressureSizeData.get(0), ratioPressureSizeData.get(1)),
                new StressNoStressData(featuresName[3], movementData.get(0), movementData.get(1)), 
                new StressNoStressData(featuresName[4], durationData.get(0), durationData.get(1)), 
                new StressNoStressData(featuresName[5], precisionData.get(0), precisionData.get(1)), 
                new StressNoStressData(featuresName[6], ratioBackOverDigitsData.get(0), ratioBackOverDigitsData.get(1)), 
                new StressNoStressData(featuresName[7], ratioWrongAllWordsData.get(0), ratioWrongAllWordsData.get(1)), 
                new StressNoStressData(featuresName[8], digitsFrequencyData.get(0), digitsFrequencyData.get(1)));
        }
    
    /**
     * Perform statistical analysis for a single tester
     * @param tester the User we want to analyze
     */
    public static void performLocalAnalysis(Tester tester)
    {
        System.out.println("*********** Tester: " + tester.getName() + " ***********");
        
        WriteAnalyzer analyzerTester = tester.getWriteAnalyzer();
        
        boolean[] results = printReport(true, new StressNoStressData(featuresName[0], analyzerTester.getAllPressureData(false), analyzerTester.getAllPressureData(true)), 
                new StressNoStressData(featuresName[1], analyzerTester.getAllSizeData(false), analyzerTester.getAllSizeData(true)), 
                new StressNoStressData(featuresName[2], analyzerTester.getAllRatioPressureSizeData(false), analyzerTester.getAllRatioPressureSizeData(true)),
                new StressNoStressData(featuresName[3], analyzerTester.getAllMovementData(false), analyzerTester.getAllMovementData(true)), 
                new StressNoStressData(featuresName[4], analyzerTester.getAllDurationData(false), analyzerTester.getAllDurationData(true)), 
                new StressNoStressData(featuresName[5], analyzerTester.getAllPrecisionData(false), analyzerTester.getAllPrecisionData(true)), 
                new StressNoStressData(featuresName[6], analyzerTester.getAllRatioBackOVerDigits(false), analyzerTester.getAllRatioBackOVerDigits(true)), 
                new StressNoStressData(featuresName[7], analyzerTester.getAllRatioWrongAllWords(false), analyzerTester.getAllRatioWrongAllWords(true)), 
                new StressNoStressData(featuresName[8], analyzerTester.getAllDigitsFrequency(false), analyzerTester.getAllDigitsFrequency(true)));
        
        for (int i = 0; i < results.length; i++) {
        	if (results[i]) {
        		featurePassesTestForTester[i]++;
        	}
        }
        
        totalTesters++;
    }
    
    /**
     * Prints for how many tester the feature has a statistical significance
     */
    public static void printPercentageSingleFeature() {
    	
    	for (int i = 0; i < featuresName.length; i++) {
    		System.out.println(featuresName[i] + ": " + 
    				featurePassesTestForTester[i] + "/" + totalTesters + "(" + 
    				featurePassesTestForTester[i] * 100 / totalTesters + ")");
    	}
    }
    
    /**
     * Calculates the ratio of wrong words written over the all words to write
     * @param targetString the string to write
     * @param writtenString the written string
     * @return wrong_words / total_words
     */
    private double ratioWrongTotalWords(String targetString, 
            String writtenString)
    {
        targetString = targetString.replaceAll("[;,.!?]", " ");
        writtenString = writtenString.replaceAll("[;,.!]", " ");
        
        String[] wordsTargetString = targetString.split(" "), 
                wordsWrittenString = writtenString.split(" ");
        
        int wrongWords = 0, correctWords = 0;
        
        for (int i = 0; i < wordsTargetString.length; i++)
        {
            if (!wordsTargetString[i].equals(""))
            {
                if (i < wordsWrittenString.length)
                {
                    if (wordsTargetString[i].equals(wordsWrittenString[i]))
                    {
                        correctWords++;
                    }
                    else
                    {
                        wrongWords++;
                    }
                }
                else
                {
                    wrongWords++;
                }
            }
        }
        
        return ((double) wrongWords) / ((double) wordsTargetString.length);
    }

    public static void createHeatMapForDigit(Tester tester, String digitText) {
    	
    	ArrayList<Write> listWriteExercises = 
    			tester.getWriteExercisesForProtocol(ISenseStressAnalyzer.protocols[0]);
    	
    	ScreenObject spacebar = listWriteExercises.get(0).findScreenObject("Keyboard.Key", 
    			"space");
    	
    	ArrayList<Digit> noStressDigits = new ArrayList<>(), 
    			stressDigits = new ArrayList<>();
    	
    	for (Write writeExercise: listWriteExercises) {
    		if (writeExercise.stress()) {
    			stressDigits.addAll(writeExercise.retrieveAllDigitsForAParticularCharacter(" "));
    		}
    		else {
    			noStressDigits.addAll(writeExercise.retrieveAllDigitsForAParticularCharacter(" "));
    		}
    	}
    	
    	ArrayList<ArrayList<Double>> forHeatMapNoStress = new ArrayList<ArrayList<Double>>(), 
    			forHeatMapStress = new ArrayList<ArrayList<Double>>();
    	
    	int width = Math.round(spacebar.getWidth()), 
    			height = Math.round(spacebar.getHeight());
    	
    	for (int i = 0; i < height; i++) {
    		ArrayList<Double> secondDimensionNoStress = new ArrayList<>(),
    				secondDimensionStress = new ArrayList<>();
    		for (int j = 0; j < width; j++) {
    			secondDimensionNoStress.add(0.0);
    			secondDimensionStress.add(0.0);
    		}
    		forHeatMapNoStress.add(secondDimensionNoStress);
    		forHeatMapStress.add(secondDimensionStress);
    	}
    	
    	completeHeatmapValues(forHeatMapNoStress, spacebar, noStressDigits);
    	completeHeatmapValues(forHeatMapStress, spacebar, stressDigits);
    	
    	double[][] heatMapNoStressArray = new double[forHeatMapNoStress.size()][];
    	double[][] heatMapStressArray = new double[forHeatMapStress.size()][];
    	
    	copyDoubleArrayListToDoubleArray(forHeatMapNoStress, heatMapNoStressArray);
    	copyDoubleArrayListToDoubleArray(forHeatMapStress, heatMapStressArray);
    	
    	HeatChart mapNoStress = new HeatChart(heatMapNoStressArray), 
    			mapStress = new HeatChart(heatMapStressArray);
    	mapNoStress.setTitle("No Stress condition");
    	mapStress.setTitle("Stress condition");
    	try {
			mapNoStress.saveToFile(new File(Analyzer.OUTPUT_FOLDER + File.separator 
					+ tester.getName() + File.separator + "NoStress.png"));
			mapStress.saveToFile(new File(Analyzer.OUTPUT_FOLDER + File.separator
					+ tester.getName() + File.separator + "Stress.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * For each digit, it completes the heatmap adding each digit over it
     * @param heatmap the heatmap we have to complete
     * @param targetObject the ScreenObject we are studying
     * @param listDigits the list of digits over the object
     */
    private static void completeHeatmapValues(ArrayList<ArrayList<Double>> heatmap, 
    		ScreenObject targetObject, ArrayList<Digit> listDigits) {
    	
    	for (Digit digit: listDigits) {
    		
    		ArrayList<Interaction> listInteractions = digit.getInteractions();
    		for (Interaction interaction: listInteractions) {
    			int xPosition = Math.round(interaction.getPoint().getX() 
    					- targetObject.getPosition().getX() 
    					- KeyboardKeyObject.mViewKeyboard.getPosition().getX());
    			
    			int yPosition = Math.round(interaction.getPoint().getY() 
    					- targetObject.getPosition().getY()
    					- KeyboardKeyObject.mViewKeyboard.getPosition().getY());
    			
    			if (yPosition >=0 && xPosition >= 0 && 
    					yPosition < heatmap.size() && 
    					xPosition < heatmap.get(0).size()) {
    				
    				Double current = heatmap.get(yPosition).get(xPosition);
    				heatmap.get(yPosition).set(xPosition, current.doubleValue() + 1);
    			}
    		}
    	}
    }
    
    private static void copyDoubleArrayListToDoubleArray(ArrayList<ArrayList<Double>> list, 
    		double[][] array) {
    	
    	final int listSize = list.size();
    	for(int i = 0; i < listSize; i++) {
    	    ArrayList<Double> sublist = list.get(i);
    	    final int sublistSize = sublist.size();
    	    array[i] = new double[sublistSize];
    	    for(int j = 0; j < sublistSize; j++) {
    	    	array[i][j] = sublist.get(j);
    	    }
    	}
    	
    }
}

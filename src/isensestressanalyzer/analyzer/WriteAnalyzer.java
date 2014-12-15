package isensestressanalyzer.analyzer;

import isensestressanalyzer.exercise.Write;
import isensestressanalyzer.dataanalysis.BasicDataStatistic;
import isensestressanalyzer.dataanalysis.RotationDataWrapper;
import isensestressanalyzer.dataanalysis.StressNoStressData;
import java.util.ArrayList;
import tester.Tester;

/**
 * Handles all the Writing exercises, both in stress and non stress mode, 
 * and all the digits for a single user
 * @author Matteo Ciman
 */
public class WriteAnalyzer extends Analyzer
{   
    private static final String[] featuresName = new String[]{"Digits pressure", 
        "Digits size", "Digits movement", "Digits duration", "Digits precision", 
        "Ratio BACK over all digits", "Ratio wrong words over all words", 
        "Digits time distance"};
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
        // protocol and analyse them    
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
                   resume.movementData(exercise.getMovementDigitsBasicData());
                   resume.durationData(exercise.getDurationDigitsBasicData());
                   resume.precisionData(exercise.getTouchPresicisionDigitsBasicData());
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
                sizeData = new ArrayList<>(), movementData = new ArrayList<>(),
                durationData = new ArrayList<>(), precisionData = new ArrayList<>(),
                ratioBackOverDigitsData = new ArrayList<>(), ratioWrongAllWordsData = new ArrayList<>(),
                digitsFrequencyData = new ArrayList<>();
        
        for (Tester tester: listTester) {
            
            if (pressureData.isEmpty()) {
                pressureData.add(new ArrayList<Double>()); pressureData.add(new ArrayList<Double>());
                sizeData.add(new ArrayList<Double>()); sizeData.add(new ArrayList<Double>());
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
        
        printReport(new StressNoStressData(featuresName[0], pressureData.get(0), pressureData.get(1)), 
                new StressNoStressData(featuresName[1], sizeData.get(0), sizeData.get(1)), 
                new StressNoStressData(featuresName[2], movementData.get(0), movementData.get(1)), 
                new StressNoStressData(featuresName[3], durationData.get(0), durationData.get(1)), 
                new StressNoStressData(featuresName[4], precisionData.get(0), precisionData.get(1)), 
                new StressNoStressData(featuresName[5], ratioBackOverDigitsData.get(0), ratioBackOverDigitsData.get(1)), 
                new StressNoStressData(featuresName[6], ratioWrongAllWordsData.get(0), ratioWrongAllWordsData.get(1)), 
                new StressNoStressData(featuresName[7], digitsFrequencyData.get(0), digitsFrequencyData.get(1)));
        }
    
    public static void performLocalAnalysis(Tester tester)
    {
        System.out.println("*********** Tester: " + tester.getName() + " ***********");
        
        WriteAnalyzer analyzerTester = tester.getWriteAnalyzer();
        
        printReport(new StressNoStressData(featuresName[0], analyzerTester.getAllPressureData(false), analyzerTester.getAllPressureData(true)), 
                new StressNoStressData(featuresName[1], analyzerTester.getAllSizeData(false), analyzerTester.getAllSizeData(true)), 
                new StressNoStressData(featuresName[2], analyzerTester.getAllMovementData(false), analyzerTester.getAllMovementData(true)), 
                new StressNoStressData(featuresName[3], analyzerTester.getAllDurationData(false), analyzerTester.getAllDurationData(true)), 
                new StressNoStressData(featuresName[4], analyzerTester.getAllPrecisionData(false), analyzerTester.getAllPrecisionData(true)), 
                new StressNoStressData(featuresName[5], analyzerTester.getAllRatioBackOVerDigits(false), analyzerTester.getAllRatioBackOVerDigits(true)), 
                new StressNoStressData(featuresName[6], analyzerTester.getAllRatioWrongAllWords(false), analyzerTester.getAllRatioWrongAllWords(true)), 
                new StressNoStressData(featuresName[7], analyzerTester.getAllDigitsFrequency(false), analyzerTester.getAllDigitsFrequency(true)));
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
}

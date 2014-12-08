package isensestressanalyzer.analyzer;

import isensestressanalyzer.ISenseStressAnalyzer;
import isensestressanalyzer.exercise.Write;
import isensestressanalyzer.interaction.Digit;
import isensestressanalyzer.dataanalysis.BasicDataStatistic;
import isensestressanalyzer.dataanalysis.RotationDataWrapper;
import isensestressanalyzer.exercise.Protocol;
import java.util.ArrayList;
import tester.Tester;

/**
 * Handles all the Writing exercises, both in stress and non stress mode, 
 * and all the digits for a single user
 * @author Matteo Ciman
 */
public class WriteAnalyzer extends Analyzer
{   
    /**
     * Performs all the operations necessary to calculate the information 
     * and the statistics about the exercises not stress and stress
     */
    public void performAnalysis(Tester tester)
    {
        // I extract the writing exercise for that particular
        // protocol and analyse them    
        ArrayList<RotationDataWrapper> rotationNoStress = new ArrayList<>(), 
                    rotationStress = new ArrayList<>();
            
              
        ArrayList<Write> exercises = 
                tester.getWritingExercisesForProtocol(isensestressanalyzer.ISenseStressAnalyzer.protocols[0]);
        /**
         * Now I have all the exercises for that particular protocol
         * for this particular user. I have to perform analysis for all 
         * the info, add them
         */
        if (!exercises.isEmpty())
        {
            BasicDataStatistic pressureDataNoStress = null,
                pressureDataStress = null,
                sizeDataNoStress = null,
                sizeDataStress = null,
                movementDataNoStress = null,
                movementDataStress = null,
                durationDataNoStress = null,
                durationDataStress = null,
                precisionDataNoStress = null,
                precisionDataStress = null, 
                ratioBackOverDigitsDataNoStress = null,
                ratioBackOverDigitsDataStress = null,
                ratioWrongCorrectWordsDataNoStress = null,
                ratioWrongCorrectWordsDataStress = null,
                digitsFrequencyDataNoStress = null,
                digitsFrequencyDataStress = null;
            
            for (Write exercise: exercises)
            {
               if (!exercise.stress())
               {
                   pressureDataNoStress = exercise.getPressionDigitsBasicData();
                   sizeDataNoStress = exercise.getSizeDigitsBasicData();
                   movementDataNoStress = exercise.getMovementDigitsBasicData();
                   durationDataNoStress = exercise.getDurationDigitsBasicData();
                   precisionDataNoStress = exercise.getTouchPresicisionDigitsBasicData();
                   ratioBackOverDigitsDataNoStress = 
                           new BasicDataStatistic(exercise.getRatioBackButtonsOverDigits());
                   ratioWrongCorrectWordsDataNoStress = new
                        BasicDataStatistic(ratioWrongTotalWords(exercise.getTextToWrite(), 
                                exercise.getWrittenText()));
                   digitsFrequencyDataNoStress = new BasicDataStatistic(exercise.calculateDigitsFrequency());
               }
               else
               {
                   pressureDataStress = exercise.getPressionDigitsBasicData();
                   sizeDataStress = exercise.getSizeDigitsBasicData();
                   movementDataStress = exercise.getMovementDigitsBasicData();
                   durationDataStress = exercise.getDurationDigitsBasicData();
                   precisionDataStress = exercise.getTouchPresicisionDigitsBasicData();
                   ratioBackOverDigitsDataStress = new BasicDataStatistic(exercise.getRatioBackButtonsOverDigits());
                   ratioWrongCorrectWordsDataStress = new 
                        BasicDataStatistic(ratioWrongTotalWords(exercise.getTextToWrite(), 
                                exercise.getWrittenText()));
                   digitsFrequencyDataStress = new BasicDataStatistic(exercise.calculateDigitsFrequency());
               }
               
               if (pressureDataNoStress != null)
                {
                    WriteAnalysisResume resume = new WriteAnalysisResume();
                    resume.pressureData(pressureDataNoStress, pressureDataStress);
                    resume.sizeData(sizeDataNoStress, sizeDataStress);
                    resume.movementData(movementDataNoStress, movementDataStress);
                    resume.durationData(durationDataNoStress, durationDataStress);
                    resume.precisionData(precisionDataNoStress, precisionDataStress);
                    resume.ratioBackOverDigitsData(ratioBackOverDigitsDataNoStress, ratioBackOverDigitsDataStress);
                    resume.ratioWrongAllWords(ratioWrongCorrectWordsDataNoStress, ratioWrongCorrectWordsDataStress);
                    resume.digitsFrequency(digitsFrequencyDataNoStress, digitsFrequencyDataStress);
                    resume.rotationData(rotationNoStress, rotationStress);
                    
                    tester.addNewWriteAnalysisResume(resume);
                }
            }
        }
    }
    
    public static WriteAnalysisResume performingGlobalAnalysis(Tester tester)
    {
        ArrayList<WriteAnalysisResume> listResumes = tester.getWriteAnalysisResumes();
        WriteAnalysisResume resumeGlobalForUser = new WriteAnalysisResume();
        pressureDataForGlobalAnalysis(listResumes, resumeGlobalForUser);
        sizeDataForGlobalAnalysis(listResumes, resumeGlobalForUser);
        movementDataForGlobalAnalysis(listResumes, resumeGlobalForUser);
        durationDataForGlobalAnalysis(listResumes, resumeGlobalForUser);
        precisionDataForGlobalAnalysis(listResumes, resumeGlobalForUser);
        ratioBackOverDigitsDataForGlobalAnalysis(listResumes, resumeGlobalForUser);
        ratioWrongAllWordsForGlobalAnalysis(listResumes, resumeGlobalForUser);
        digitsFrequencyForGlobalAnalysis(listResumes, resumeGlobalForUser);
        
        return resumeGlobalForUser;
    }
    
    private static void pressureDataForGlobalAnalysis(ArrayList<WriteAnalysisResume> resumes, 
            WriteAnalysisResume resume)
    {
        ArrayList<Double> meanValuesNoStress = new ArrayList<>(), 
                meanValuesStress = new ArrayList<>();
        
        for (WriteAnalysisResume resumeWrite: resumes)
        {
            meanValuesNoStress.add(resumeWrite.getPressureData().getNoStressData().getAverage());
            meanValuesStress.add(resumeWrite.getPressureData().getStressData().getAverage());
        }
        resume.pressureData(new BasicDataStatistic(meanValuesNoStress, false), 
                new BasicDataStatistic(meanValuesStress, false));
    }
    
    private static void sizeDataForGlobalAnalysis(ArrayList<WriteAnalysisResume> resumes, 
            WriteAnalysisResume resume)
    {
        ArrayList<Double> meanValueNoStress = new ArrayList<>(), 
                meanValueStress = new ArrayList<>();
        
        for (WriteAnalysisResume resumeWrite: resumes)
        {
            meanValueNoStress.add(resumeWrite.getSizeData().getNoStressData().getAverage());
            meanValueStress.add(resumeWrite.getSizeData().getStressData().getAverage());
        }
        resume.sizeData(new BasicDataStatistic(meanValueNoStress, false), 
                new BasicDataStatistic(meanValueStress, false));
    }
    
    private static void movementDataForGlobalAnalysis(ArrayList<WriteAnalysisResume> resumes, 
            WriteAnalysisResume resume)
    {
        ArrayList<Double> meanValueNoStress = new ArrayList<>(), 
                meanValueStress = new ArrayList<>();
        
        for (WriteAnalysisResume resumeWrite: resumes)
        {
            meanValueNoStress.add(resumeWrite.getMovementData().getNoStressData().getAverage());
            meanValueStress.add(resumeWrite.getMovementData().getStressData().getAverage());
        }
        resume.movementData(new BasicDataStatistic(meanValueNoStress, false), 
                new BasicDataStatistic(meanValueStress, false));
    }
    
    private static void durationDataForGlobalAnalysis(ArrayList<WriteAnalysisResume> resumes, 
            WriteAnalysisResume resume)
    {
        ArrayList<Double> meanValueNoStress = new ArrayList<>(), 
                meanValueStress = new ArrayList<>();
        
        for (WriteAnalysisResume resumeWrite: resumes)
        {
            meanValueNoStress.add(resumeWrite.getDurationData().getNoStressData().getAverage());
            meanValueStress.add(resumeWrite.getDurationData().getStressData().getAverage());
        }
        resume.durationData(new BasicDataStatistic(meanValueNoStress, false), 
                new BasicDataStatistic(meanValueStress, false));
    }
    
    private static void precisionDataForGlobalAnalysis(ArrayList<WriteAnalysisResume> resumes, 
            WriteAnalysisResume resume)
    {
        ArrayList<Double> meanValueNoStress = new ArrayList<>(),
                meanValueStress = new ArrayList<>();
        
        for (WriteAnalysisResume resumeWrite: resumes)
        {
            meanValueNoStress.add(resumeWrite.getPrecisionData().getNoStressData().getAverage());
            meanValueStress.add(resumeWrite.getPrecisionData().getStressData().getAverage());
        }
        resume.precisionData(new BasicDataStatistic(meanValueNoStress, false), 
                new BasicDataStatistic(meanValueStress, false));
    }
    

    private static void ratioBackOverDigitsDataForGlobalAnalysis(ArrayList<WriteAnalysisResume> resumes, 
            WriteAnalysisResume resume)
    {
        ArrayList<Double> meanValueNoStress = new ArrayList<>(),
                meanValueStress = new ArrayList<>();
        
        for (WriteAnalysisResume resumeWrite: resumes)
        {
            meanValueNoStress.add(resumeWrite.getRatioBackOverDigits().getNoStressData().getAverage());
            meanValueStress.add(resumeWrite.getRatioBackOverDigits().getStressData().getAverage());
        }
        resume.ratioBackOverDigitsData(new BasicDataStatistic(meanValueNoStress, false), 
                new BasicDataStatistic(meanValueStress, false));
    }
    
    private static void ratioWrongAllWordsForGlobalAnalysis(ArrayList<WriteAnalysisResume> resumes, 
            WriteAnalysisResume resume)
    {
        ArrayList<Double> meanValueNoStress = new ArrayList<>(), 
                meanValueStress = new ArrayList<>();
        
        for (WriteAnalysisResume resumeWrite: resumes)
        {
            meanValueNoStress.add(resumeWrite.getRatioWrongAllWords().getNoStressData().getAverage());
            meanValueStress.add(resumeWrite.getRatioWrongAllWords().getStressData().getAverage());
        }
        resume.ratioWrongAllWords(new BasicDataStatistic(meanValueNoStress, false), 
                new BasicDataStatistic(meanValueStress, false));
    }
    
    private static void digitsFrequencyForGlobalAnalysis(ArrayList<WriteAnalysisResume> resumes, 
            WriteAnalysisResume resume)
    {
        ArrayList<Double> meanValueNoStress = new ArrayList<>(),
                meanValueStress = new ArrayList<>();
        
        for (WriteAnalysisResume resumeWrite: resumes)
        {
            meanValueNoStress.add(resumeWrite.getDigitsFrequencyData().getNoStressData().getAverage());
            meanValueStress.add(resumeWrite.getDigitsFrequencyData().getStressData().getAverage());
        }
        resume.digitsFrequency(new BasicDataStatistic(meanValueNoStress, false), 
                new BasicDataStatistic(meanValueStress, false));
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

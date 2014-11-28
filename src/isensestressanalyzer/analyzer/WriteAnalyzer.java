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
    private final ArrayList<Tester> mListTester;
    
    public WriteAnalyzer(ArrayList<Tester> mListTester)
    {
        this.mListTester = mListTester;
    }
    
    public WriteAnalyzer(Tester singleTester)
    {
        this.mListTester = new ArrayList<>(); this.mListTester.add(singleTester);
    }
    
    /**
     * Performs all the operations necessary to calculate the information 
     * and the statistics about the exercises not stress and stress
     */
    public void performAnalysis()
    {
        for (Protocol protocol: ISenseStressAnalyzer.protocols)
        {
            // From each user I extract the writing exercise for that particular
            // protocol and analyse them
           
            ArrayList<BasicDataStatistic> pressureDataNoStress = new ArrayList<>(),
                    pressureDataStress = new ArrayList<>(),
                    sizeDataNoStress = new ArrayList<>(),
                    sizeDataStress = new ArrayList<>(),
                    movementDataNoStress = new ArrayList<>(),
                    movementDataStress = new ArrayList<>(),
                    durationDataNoStress = new ArrayList<>(),
                    durationDataStress = new ArrayList<>(),
                    precisionDataNoStress = new ArrayList<>(),
                    precisionDataStress = new ArrayList<>(), 
                    ratioBackOverDigitsDataNoStress = new ArrayList<>(),
                    ratioBackOverDigitsDataStress = new ArrayList<>(),
                    ratioWrongCorrectWordsDataNoStress = new ArrayList<>(),
                    ratioWrongCorrectWordsDataStress = new ArrayList<>(),
                    digitsFrequencyDataNoStress = new ArrayList<>(),
                    digitsFrequencyDataStress = new ArrayList<>();
            
            ArrayList<RotationDataWrapper> rotationNoStress = new ArrayList<>(), 
                    rotationStress = new ArrayList<>();
            
            for (Tester tester: mListTester)
            {   
                ArrayList<Write> exercises = tester.getWritingExercisesForProtocol(protocol);
                /**
                 * Now I have all the exercises for that particular protocol
                 * for this particular user. I have to perform analysis for all 
                 * the info, add them
                 */
                if (!exercises.isEmpty() && mListTester.size() != 1)
                {
                    ArrayList<Digit> allDigitsNoStress = new ArrayList<>(), 
                            allDigitsStress = new ArrayList<>();
                    ArrayList<Write> noStressExercises = new ArrayList<>(),
                            stressExercises = new ArrayList<>();
                    for (Write write: exercises)
                    {
                        if (!write.stress())
                        {
                            noStressExercises.add(write);
                            allDigitsNoStress.addAll(write.getDigits());
                        }
                        else
                        {
                            stressExercises.add(write);
                            allDigitsStress.addAll(write.getDigits());
                        }
                    }

                    managePressureData(allDigitsStress, allDigitsNoStress, 
                            pressureDataNoStress, pressureDataStress);
                    manageSizeData(allDigitsNoStress, allDigitsStress, 
                            sizeDataNoStress, sizeDataStress);
                    manageMovementData(allDigitsNoStress, allDigitsStress, 
                            movementDataNoStress, movementDataStress);
                    manageDurationData(allDigitsNoStress, allDigitsStress, 
                            durationDataNoStress, durationDataStress);
                    managePrecisionData(stressExercises, noStressExercises, 
                            precisionDataNoStress, precisionDataStress);
                    manageWritingTextData(stressExercises, noStressExercises, 
                            ratioBackOverDigitsDataNoStress, ratioBackOverDigitsDataStress, 
                            ratioWrongCorrectWordsDataNoStress, ratioWrongCorrectWordsDataStress, 
                            digitsFrequencyDataNoStress, digitsFrequencyDataStress);
                    manageRotationSensorData(noStressExercises, stressExercises, 
                                rotationStress, rotationNoStress, mListTester.size() == 1);
                }
                else if (!exercises.isEmpty() && mListTester.size() == 1)
                {
                    for (Write exercise: exercises)
                    {
                       if (!exercise.stress())
                       {
                           pressureDataNoStress.add(exercise.getPressionDigitsBasicData());
                           sizeDataNoStress.add(exercise.getSizeDigitsBasicData());
                           movementDataNoStress.add(exercise.getMovementDigitsBasicData());
                           durationDataNoStress.add(exercise.getDurationDigitsBasicData());
                           precisionDataNoStress.add(exercise.getTouchPresicisionDigitsBasicData());
                           ratioBackOverDigitsDataNoStress.add(new BasicDataStatistic(exercise.getRatioBackButtonsOverDigits()));
                           ratioWrongCorrectWordsDataNoStress.add(new 
                                BasicDataStatistic(ratioWrongTotalWords(exercise.getTextToWrite(), 
                                        exercise.getWrittenText())));
                           digitsFrequencyDataNoStress.add(new BasicDataStatistic(exercise.calculateDigitsFrequency()));
                       }
                       else
                       {
                           pressureDataStress.add(exercise.getPressionDigitsBasicData());
                           sizeDataStress.add(exercise.getSizeDigitsBasicData());
                           movementDataStress.add(exercise.getMovementDigitsBasicData());
                           durationDataStress.add(exercise.getDurationDigitsBasicData());
                           precisionDataStress.add(exercise.getTouchPresicisionDigitsBasicData());
                           ratioBackOverDigitsDataStress.add(new BasicDataStatistic(exercise.getRatioBackButtonsOverDigits()));
                           ratioWrongCorrectWordsDataStress.add(new 
                                BasicDataStatistic(ratioWrongTotalWords(exercise.getTextToWrite(), 
                                        exercise.getWrittenText())));
                           digitsFrequencyDataStress.add(new BasicDataStatistic(exercise.calculateDigitsFrequency()));
                       }
                    }
                }
            }
            
            if (!pressureDataNoStress.isEmpty())
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

                System.out.println("** RESUME WRITING **");
                System.out.println(protocol.toString());
                resume.printAnalysis();
            }
        }
    }
    
    public void performGlobalAnalysis()
    {
        
        ArrayList<BasicDataStatistic> pressureDataNoStress = new ArrayList<>(),
                    pressureDataStress = new ArrayList<>(),
                    sizeDataNoStress = new ArrayList<>(),
                    sizeDataStress = new ArrayList<>(),
                    movementDataNoStress = new ArrayList<>(),
                    movementDataStress = new ArrayList<>(),
                    durationDataNoStress = new ArrayList<>(),
                    durationDataStress = new ArrayList<>(),
                    precisionDataNoStress = new ArrayList<>(),
                    precisionDataStress = new ArrayList<>(), 
                    ratioBackOverDigitsDataNoStress = new ArrayList<>(),
                    ratioBackOverDigitsDataStress = new ArrayList<>(),
                    ratioWrongCorrectWordsDataNoStress = new ArrayList<>(),
                    ratioWrongCorrectWordsDataStress = new ArrayList<>(),
                    digitsFrequencyDataNoStress = new ArrayList<>(),
                    digitsFrequencyDataStress = new ArrayList<>();
            
            ArrayList<RotationDataWrapper> rotationNoStress = new ArrayList<>(), 
                    rotationStress = new ArrayList<>();
        
        for (Protocol protocol: ISenseStressAnalyzer.protocols)
        {
            // From each user I extract the writing exercise for that particular
            // protocol and analyse them
            
            for (Tester tester: mListTester)
            {   
                ArrayList<Write> exercises = tester.getWritingExercisesForProtocol(protocol);
                /**
                 * Now I have all the exercises for that particular protocol
                 * for this particular user. I have to perform analysis for all 
                 * the info, add them
                 */
                if (!exercises.isEmpty())
                {
                    ArrayList<Digit> allDigitsNoStress = new ArrayList<>(), 
                            allDigitsStress = new ArrayList<>();
                    ArrayList<Write> noStressExercises = new ArrayList<>(),
                            stressExercises = new ArrayList<>();
                    for (Write write: exercises)
                    {
                        if (!write.stress())
                        {
                            noStressExercises.add(write);
                            allDigitsNoStress.addAll(write.getDigits());
                        }
                        else
                        {
                            stressExercises.add(write);
                            allDigitsStress.addAll(write.getDigits());
                        }
                    }

                    managePressureData(allDigitsStress, allDigitsNoStress, 
                            pressureDataNoStress, pressureDataStress);
                    manageSizeData(allDigitsNoStress, allDigitsStress, 
                            sizeDataNoStress, sizeDataStress);
                    manageMovementData(allDigitsNoStress, allDigitsStress, 
                            movementDataNoStress, movementDataStress);
                    manageDurationData(allDigitsNoStress, allDigitsStress, 
                            durationDataNoStress, durationDataStress);
                    managePrecisionData(stressExercises, noStressExercises, 
                            precisionDataNoStress, precisionDataStress);
                    manageWritingTextData(stressExercises, noStressExercises, 
                            ratioBackOverDigitsDataNoStress, ratioBackOverDigitsDataStress, 
                            ratioWrongCorrectWordsDataNoStress, ratioWrongCorrectWordsDataStress, 
                            digitsFrequencyDataNoStress, digitsFrequencyDataStress);
                    manageRotationSensorData(noStressExercises, stressExercises, 
                                rotationStress, rotationNoStress, mListTester.size() == 1);
                }
            }
        }
        
        if (!pressureDataNoStress.isEmpty())
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

                System.out.println("** RESUME WRITING **");
                resume.printAnalysis();
            }
    }
    
    /**
     * 
     * @param allDigitsStress
     * @param allDigitsNoStress
     * @param pressureDataNoStress
     * @param pressureDataStress 
     */
    protected void managePressureData(ArrayList<Digit> allDigitsStress, 
            ArrayList<Digit> allDigitsNoStress, ArrayList<BasicDataStatistic> pressureDataNoStress,
            ArrayList<BasicDataStatistic> pressureDataStress)
    {
        ArrayList<Double> pressureStressedData = new ArrayList<>(),
                pressureNotStressedData = new ArrayList<>();

        for (Digit digit: allDigitsStress)
        {
            pressureStressedData.add(digit.getPressureBasicData().getAverage());
        }
        for (Digit digit: allDigitsNoStress)
        {
            pressureNotStressedData.add(digit.getPressureBasicData().getAverage());
        }

        if (!pressureStressedData.isEmpty() && 
                !pressureNotStressedData.isEmpty())
        {
            pressureDataStress.add(new BasicDataStatistic(pressureStressedData, false));
            pressureDataNoStress.add(new BasicDataStatistic(pressureNotStressedData, false));
        }
    }
    
    protected void managePressureDataOneTester(ArrayList<Write> exercises, 
            ArrayList<BasicDataStatistic> pressureDataNoStress, 
            ArrayList<BasicDataStatistic> pressureDataStress)
    {
        for (Write write: exercises)
        {
            ArrayList<Double> pressureNoStress = new ArrayList<>(),
                    pressureStress = new ArrayList<>();
            if (write.stress())
            {
                for (Digit digit: write.getDigits())
                {
                    pressureStress.add(digit.getPressureBasicData().getAverage());
                }
            }
            else
            {
                for (Digit digit: write.getDigits())
                {
                    pressureNoStress.add(digit.getPressureBasicData().getAverage());
                }
            }
            
            pressureDataNoStress.add(new BasicDataStatistic(pressureNoStress, false));
            pressureDataStress.add(new BasicDataStatistic(pressureStress, false));
        }
    }
    
    /**
     * 
     * @param allDigitsNoStress
     * @param allDigitsStress
     * @param sizeDataNoStress
     * @param sizeDataStress 
     */
    protected void manageSizeData(ArrayList<Digit> allDigitsNoStress, 
            ArrayList<Digit> allDigitsStress, ArrayList<BasicDataStatistic> sizeDataNoStress, 
            ArrayList<BasicDataStatistic> sizeDataStress)
    {
        ArrayList<Double> sizeStressedData = new ArrayList<>(),
                sizeNotStressedData = new ArrayList<>();
        
        for (Digit digit: allDigitsStress)
        {
            sizeStressedData.add(digit.getSizeBasicData().getAverage());
        }
        for (Digit digit: allDigitsNoStress)
        {
            sizeNotStressedData.add(digit.getSizeBasicData().getAverage());
        }
        
        if (!sizeNotStressedData.isEmpty() && !sizeStressedData.isEmpty())
        {
            sizeDataNoStress.add(new BasicDataStatistic(sizeNotStressedData, false));
            sizeDataStress.add(new BasicDataStatistic(sizeStressedData, false));
        }
    }
    
    /**
     * 
     * @param allDigitsNoStress
     * @param allDigitsStress
     * @param movementDataNoStress
     * @param movementDataStress 
     */
    protected void manageMovementData(ArrayList<Digit> allDigitsNoStress, 
            ArrayList<Digit> allDigitsStress, ArrayList<BasicDataStatistic> movementDataNoStress,
            ArrayList<BasicDataStatistic> movementDataStress)
    {
        ArrayList<Double> movementStressData = new ArrayList<>(),
                movementNoStressData = new ArrayList<>();
        
        for (Digit digit: allDigitsStress)
        {
            if (digit.isValid())
            {
                movementStressData.add(digit.getTouchMovement());
            }
        }
        for (Digit digit: allDigitsNoStress)
        {
            if (digit.isValid())
            {
                movementNoStressData.add(digit.getTouchMovement());
            }
        }
        
        if (!movementNoStressData.isEmpty() && !movementStressData.isEmpty())
        {
            movementDataNoStress.add(new BasicDataStatistic(movementNoStressData, true));
            movementDataStress.add(new BasicDataStatistic(movementStressData, true));
        }
    }
    
    /**
     * 
     * @param allDigitsNoStress
     * @param allDigitsStress
     * @param durationDataNoStress
     * @param durationDataStress 
     */
    protected void manageDurationData(ArrayList<Digit> allDigitsNoStress, 
            ArrayList<Digit> allDigitsStress, ArrayList<BasicDataStatistic> durationDataNoStress, 
            ArrayList<BasicDataStatistic> durationDataStress)
    {
        ArrayList<Double> durationStressData = new ArrayList<>(),
                durationNoStressData = new ArrayList<>();
        
        for (Digit digit: allDigitsStress)
        {
            if (digit.isValid())
            {
                durationStressData.add(digit.getTouchDuration());
            }
        }
        for (Digit digit: allDigitsNoStress)
        {
            if (digit.isValid())
            {
                durationNoStressData.add(digit.getTouchDuration());
            }
        }
        
        if (!durationNoStressData.isEmpty() && !durationStressData.isEmpty())
        {
            durationDataNoStress.add(new BasicDataStatistic(durationNoStressData, true));
            durationDataStress.add(new BasicDataStatistic(durationStressData, true));
        }
    }

    protected void managePrecisionData(ArrayList<Write> stressExercises, 
            ArrayList<Write> noStressExercises, ArrayList<BasicDataStatistic> precisionDataNoStress, 
            ArrayList<BasicDataStatistic> precisionDataStress)
    {
        ArrayList<Double> precisionStress = new ArrayList<>(),
                precisionNoStress = new ArrayList<>();
        
        for (Write write: stressExercises)
        {
            precisionStress.add(write.rateTouchPrecisionExercise());
        }
        for (Write write: noStressExercises)
        {
            precisionNoStress.add(write.rateTouchPrecisionExercise());
        }
        
        if (!precisionNoStress.isEmpty() && !precisionStress.isEmpty())
        {
            precisionDataNoStress.add(new BasicDataStatistic(precisionNoStress, true));
            precisionDataStress.add(new BasicDataStatistic(precisionStress, true));
        }
    }
    
    /**
     * Performs analysis of the written text of the user, comparing it to the 
     * target text to write
     * @param stressExercises the exercises in stress mode
     * @param noStressExercises the exercises in no stress mode
     * @param ratioBackOverDigitsNoStressData where to put the BasicDataStatistic about back press in no stress mode
     * @param ratioBackOverDigitsStressData where to put the BasicDataStatistic about back press in stress mode
     * @param ratioWrongCorrectWordsNoStressData where to put the BasicDataStatistic 
     * about the ratio of wrong words over all the words of the test in no stress mode
     * @param ratioWrongCorrectWordsStressData where to put the BasicDataStatistic 
     * about the ratio of wrong words over all the words of the text in stress mode
     * @param digitsFrequencyNoStressData where to put the BasicDataStatistic about
     * the frequency of digits in no stress mode
     * @param digitsFrequencyStressData where to put the BasicDataStatistic about
     * the frequency of digits in stress mode
     */
    protected void manageWritingTextData(ArrayList<Write> stressExercises, 
            ArrayList<Write> noStressExercises, ArrayList<BasicDataStatistic> ratioBackOverDigitsNoStressData, 
            ArrayList<BasicDataStatistic> ratioBackOverDigitsStressData, 
            ArrayList<BasicDataStatistic> ratioWrongCorrectWordsNoStressData, 
            ArrayList<BasicDataStatistic> ratioWrongCorrectWordsStressData, 
            ArrayList<BasicDataStatistic> digitsFrequencyNoStressData, 
            ArrayList<BasicDataStatistic> digitsFrequencyStressData)
    {
        ArrayList<Double> backNoStress = new ArrayList<>(),
                backStress = new ArrayList<>(), 
                ratioWrongCorrectNoStress = new ArrayList<>(), 
                ratioWrongCorrectStress = new ArrayList<>(), 
                digitsFrequencyNoStress = new ArrayList<>(), 
                digitsFrequencyStress = new ArrayList<>();
        
        for (Write write: noStressExercises)
        {
            backNoStress.add(write.getRatioBackButtonsOverDigits());
            ratioWrongCorrectNoStress.add(ratioWrongTotalWords(write.getTextToWrite(), 
                    write.getWrittenText()));
            digitsFrequencyNoStress.add(write.calculateDigitsFrequency());
        }
        for (Write write: stressExercises)
        {
            try
            {
            backStress.add(write.getRatioBackButtonsOverDigits());
            ratioWrongCorrectStress.add(ratioWrongTotalWords(write.getTextToWrite(), 
                    write.getWrittenText()));
            digitsFrequencyStress.add(write.calculateDigitsFrequency());
            }
            catch(Exception exc) 
            {
                System.out.println(exc.toString());
            }
        }
        
        if (!backNoStress.isEmpty() && !backStress.isEmpty() && 
                !ratioWrongCorrectNoStress.isEmpty() && !ratioWrongCorrectStress.isEmpty() 
                && !digitsFrequencyStress.isEmpty() && !digitsFrequencyNoStress.isEmpty())
        {
            ratioBackOverDigitsNoStressData.add(new BasicDataStatistic(backNoStress, true));
            ratioBackOverDigitsStressData.add(new BasicDataStatistic(backStress, true));

            ratioWrongCorrectWordsNoStressData.add(new BasicDataStatistic(ratioWrongCorrectNoStress, false));
            ratioWrongCorrectWordsStressData.add(new BasicDataStatistic(ratioWrongCorrectStress, false));

            digitsFrequencyNoStressData.add(new BasicDataStatistic(digitsFrequencyNoStress, true));
            digitsFrequencyStressData.add(new BasicDataStatistic(digitsFrequencyStress, true));
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
}

package isensestressanalyzer.heatmap;

import isensestressanalyzer.exercise.Stressor;
import isensestressanalyzer.tester.Tester;
import isensestressanalyzer.ISenseStressAnalyzer;
import isensestressanalyzer.interaction.Interaction;
import isensestressanalyzer.interaction.NumberPickerInteraction;
import isensestressanalyzer.outputwriter.StressorHeatmapWriter;
import isensestressanalyzer.utils.ScreenObject;
import java.util.ArrayList;

/**
 *
 * @author Matteo
 */
public class StressorTouchesHeatmapCreator extends HeatMapCreator {
    
    private static final String[] LABELS_HEATMAP_MOMENT = {"Begin", "Middle", 
        "End"};
    private static final String[] GROUPING_NAMES = {"first-second-third-fourth", 
        "first,second-third,fourth", "first,second,third,fourth", 
        "first-second,third-fourth"};
    private static final String[] MOMENT_IDS = {"firstMiddleEnd", 
        "aroundFirstMiddleEnd"};
    
    public StressorTouchesHeatmapCreator(Tester tester) {
        super(tester);
    }
    
    public void createHeatmapForStressors() {
        
        ArrayList<Stressor> listStressors = this.tester.
            getStressorExercisesForProtocol(ISenseStressAnalyzer.PROTOCOLS[0]);
        
        ArrayList<ScreenObject> numberPickers = listStressors.get(0).getNumberPickers();
        
        /**
         * First grouping: no grouping, one each
         */
        workWithFirstGrouping(listStressors, numberPickers);
        
        /**
         * Second grouping: first and second, third and fourth
         */
        workWithSecondGrouping(listStressors, numberPickers);
        
        /**
         * Third grouping: all together
         */
        workWithThirdGrouping(listStressors, numberPickers);
        
        /**
         * Fourth grouping: first, second and third, fourth
         */
        workWithFourthGrouping(listStressors, numberPickers);
    }
    
    private void workWithFirstGrouping(ArrayList<Stressor> listStressors, 
            ArrayList<ScreenObject> numberPickers) {
        
        ArrayList<ArrayList<ScreenObject>> firstGrouping = new ArrayList<>();
        firstGrouping.add(new ArrayList<ScreenObject>());
        firstGrouping.add(new ArrayList<ScreenObject>());
        firstGrouping.add(new ArrayList<ScreenObject>());
        firstGrouping.add(new ArrayList<ScreenObject>());
        
        firstGrouping.get(0).add(numberPickers.get(0));
        firstGrouping.get(1).add(numberPickers.get(1));
        firstGrouping.get(2).add(numberPickers.get(2));
        firstGrouping.get(3).add(numberPickers.get(3));
        
        firstMiddleLastStressor(firstGrouping, listStressors, GROUPING_NAMES[0]);
        aroundFirstMiddleLastStressor(firstGrouping, listStressors, GROUPING_NAMES[0]);
    }
    
    private void workWithSecondGrouping(ArrayList<Stressor> listStressors, 
            ArrayList<ScreenObject> numberPickers) {
        
        ArrayList<ArrayList<ScreenObject>> secondGrouping = new ArrayList<>();
        secondGrouping.add(new ArrayList<ScreenObject>());
        secondGrouping.add(new ArrayList<ScreenObject>());
        
        secondGrouping.get(0).add(numberPickers.get(0));
        secondGrouping.get(0).add(numberPickers.get(1));
        
        secondGrouping.get(1).add(numberPickers.get(2));
        secondGrouping.get(1).add(numberPickers.get(3));
        
        firstMiddleLastStressor(secondGrouping, listStressors, GROUPING_NAMES[1]);
        aroundFirstMiddleLastStressor(secondGrouping, listStressors, GROUPING_NAMES[1]);
    }
    
    private void workWithThirdGrouping(ArrayList<Stressor> listStressors, 
            ArrayList<ScreenObject> numberPickers) {
        
        ArrayList<ArrayList<ScreenObject>> thirdGrouping = new ArrayList<>();
        thirdGrouping.add(new ArrayList<ScreenObject>());
        
        thirdGrouping.get(0).addAll(numberPickers);
        
        firstMiddleLastStressor(thirdGrouping, listStressors, GROUPING_NAMES[2]);
        aroundFirstMiddleLastStressor(thirdGrouping, listStressors, GROUPING_NAMES[2]);
    }
    
    private void workWithFourthGrouping(ArrayList<Stressor> listStressors, 
            ArrayList<ScreenObject> numberPickers) {
        
        ArrayList<ArrayList<ScreenObject>> fourthGrouping = new ArrayList<>();
        fourthGrouping.add(new ArrayList<ScreenObject>());
        fourthGrouping.add(new ArrayList<ScreenObject>());
        fourthGrouping.add(new ArrayList<ScreenObject>());
        
        fourthGrouping.get(0).add(numberPickers.get(0));
        
        fourthGrouping.get(1).add(numberPickers.get(1));
        fourthGrouping.get(1).add(numberPickers.get(2));
        
        fourthGrouping.get(2).add(numberPickers.get(3));
        
        firstMiddleLastStressor(fourthGrouping, listStressors, GROUPING_NAMES[3]);
        aroundFirstMiddleLastStressor(fourthGrouping, listStressors, GROUPING_NAMES[3]);
    }
    
    /**
     * 
     * @param listNumberPickers how I want to group NumberPickers
     * @param listStressors 
     */
    private void firstMiddleLastStressor(ArrayList<ArrayList<ScreenObject>> listNumberPickers, 
            ArrayList<Stressor> listStressors, String groupingName) {
        
        ArrayList<ArrayList<Stressor>> exerciseToAnalyze = new ArrayList<>();
        exerciseToAnalyze.add(new ArrayList<Stressor>()); //BEGIN
        exerciseToAnalyze.add(new ArrayList<Stressor>()); //MIDDLE
        exerciseToAnalyze.add(new ArrayList<Stressor>()); //END
        
        exerciseToAnalyze.get(0).add(listStressors.get(0));
        exerciseToAnalyze.get(1).add(listStressors.get(listStressors.size() / 2));
        exerciseToAnalyze.get(2).add(listStressors.get(listStressors.size() - 1));
        
        workWithStressors(exerciseToAnalyze, listNumberPickers, groupingName, 
            MOMENT_IDS[0]);
    }
    
    private void aroundFirstMiddleLastStressor(ArrayList<ArrayList<ScreenObject>> 
        listNumberPickers, ArrayList<Stressor> listStressors, String groupingName) {
        
        ArrayList<ArrayList<Stressor>> exerciseToAnalyze = new ArrayList<>();
        exerciseToAnalyze.add(new ArrayList<Stressor>()); //BEGIN
        exerciseToAnalyze.add(new ArrayList<Stressor>()); //MIDDLE
        exerciseToAnalyze.add(new ArrayList<Stressor>()); //END
        
        exerciseToAnalyze.get(0).add(listStressors.get(0));
        exerciseToAnalyze.get(0).add(listStressors.get(1));
        exerciseToAnalyze.get(0).add(listStressors.get(2));
        
        exerciseToAnalyze.get(1).add(listStressors.get(listStressors.size() / 2 - 1));
        exerciseToAnalyze.get(1).add(listStressors.get(listStressors.size() / 2));
        exerciseToAnalyze.get(1).add(listStressors.get(listStressors.size() / 2 + 1));
        
        exerciseToAnalyze.get(2).add(listStressors.get(listStressors.size() - 3));
        exerciseToAnalyze.get(2).add(listStressors.get(listStressors.size() - 2));
        exerciseToAnalyze.get(2).add(listStressors.get(listStressors.size() - 1));
        
        workWithStressors(exerciseToAnalyze, listNumberPickers, groupingName, 
            MOMENT_IDS[1]);
    }
    
    private void workWithStressors(ArrayList<ArrayList<Stressor>> exerciseToAnalyze, 
        ArrayList<ArrayList<ScreenObject>> listNumberPickers, 
        String groupingName, String momentID) {
        
        String[] groups = groupingName.split("-");
            
        for (int indexNumberPickers = 0; 
            indexNumberPickers < listNumberPickers.size(); indexNumberPickers++) {
            
            for (Integer size: digitSize) {
                
                ArrayList<ArrayList<ArrayList<Double>>> finalHeatmapsBME = 
                    calculateHeatmapForExercises(exerciseToAnalyze, size, 
                        listNumberPickers.get(indexNumberPickers));
                
                /**
                 * I have heatmap, to output
                 */
                for (int i = 0; i < finalHeatmapsBME.size(); i++) {
                    /**
                     * Write output
                     */
                    // TODO copy heatmap
                    double[][] heatmapArray = 
                        new double[finalHeatmapsBME.get(i).get(0).size()][];
                    
                    copyDoubleArrayListToDoubleArray(finalHeatmapsBME.get(i), 
                            heatmapArray);
                    
                    StressorHeatmapWriter.saveHeatmap(heatmapArray, 
                        tester.getName(), size, groupingName, 
                        groups[indexNumberPickers], momentID, 
                        LABELS_HEATMAP_MOMENT[i]);
                }
            }
        }
    }
    
    /**
     * Calcola heatmap, da chiamare con inizio, mezzo e fine, per un insieme
     * di number pickers (o uno singolo)
     * @param exercises [Beginning, Middle, End]
     * @param digitSize the size of the digit to create the heatmap
     * @param numberPickers the list of numberpicker to consider (could be one
     * or two)
     * @return 
     */
    private ArrayList<ArrayList<ArrayList<Double>>>
        calculateHeatmapForExercises(ArrayList<ArrayList<Stressor>> exercises, 
            int digitSize, ArrayList<ScreenObject> numberPickers) {
        
        ArrayList<ArrayList<ArrayList<Double>>> heatmaps = new ArrayList<>();
        
        for (int i = 0; i < exercises.size(); i++) {
            
            ArrayList<ArrayList<Double>> heatmap = new ArrayList<>();
            
            int width = numberPickers.get(0).getWidth();
            int height = numberPickers.get(0).getHeight();
            
            for (int indexWidth = 0; indexWidth < width; indexWidth++) {
                
                ArrayList<Double> forHeight = new ArrayList<>();
                for (int indexHeight = 0; indexHeight < height; indexHeight++) {
                    forHeight.add(0.0);
                }
                heatmap.add(forHeight);
            }
            
            heatmaps.add(heatmap);
        }
        
        for (ScreenObject numberPicker: numberPickers) {
            
            for (int indexExercises = 0; indexExercises < exercises.size(); 
                    indexExercises++) {
                
                ArrayList<Stressor> stressors = exercises.get(indexExercises);
                
                /**
                 * For each stressor exercise (could be one or more)
                 */
                for (int indexStressor = 0; indexStressor < stressors.size(); 
                    indexStressor++) {
                    
                    /**
                     * Step 1: retrieving all the NumberPickerInteractions
                     */
                    ArrayList<NumberPickerInteraction> numberPickerInteractionList = 
                            stressors.get(indexStressor).getNumberPickerInteractions();
                    
                    /**
                     * Step 2: iterating over NumberPickerInteractions
                     */
                    for (NumberPickerInteraction numberPickerInteraction : 
                        numberPickerInteractionList) {
                        
                        if (numberPickerInteraction.isInside(numberPicker)) {
                            
                            updateHeatmapForNumberPicker(numberPickerInteraction, 
                                numberPicker, digitSize, 
                                heatmaps.get(indexExercises));
                        }
                    }
                }
            }
        }
        
        return heatmaps;
    }
    
    /**
     * Adds interaction to the heatmap for a particular NumberPicker
     * @param interaction the list of interactions
     * @param numberPicker the current NumberPicker
     * @param digitSize the size of the digit
     * @param heatmap the heatmap where to put data
     */
    private void updateHeatmapForNumberPicker(NumberPickerInteraction interactionNP, 
            ScreenObject numberPicker, int digitSize, 
            ArrayList<ArrayList<Double>> heatmap) {
        
        for (Interaction interaction: interactionNP.getListInteractions()) {
            
            int xPoint = Math.round(interaction.getPoint().getX() - 
                    numberPicker.getPosition().getX());
            int yPoint = Math.round(interaction.getPoint().getY() - 
                    numberPicker.getPosition().getY());
            
            if (xPoint >= 0 && xPoint < heatmap.size() && 
                yPoint >= 0 && yPoint < heatmap.get(xPoint).size()) {
                
                updateHeatmapScore(xPoint, yPoint, digitSize, heatmap);
            }
        }
    }
}

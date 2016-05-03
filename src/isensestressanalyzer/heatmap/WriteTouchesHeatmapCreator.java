package isensestressanalyzer.heatmap;

import isensestressanalyzer.ISenseStressAnalyzer;
import isensestressanalyzer.exercise.Write;
import isensestressanalyzer.filereader.DigitsHeatmapReader;
import isensestressanalyzer.interaction.Digit;
import isensestressanalyzer.outputwriter.HeatmapWriter;
import isensestressanalyzer.tester.Tester;
import java.util.ArrayList;

/**
 *
 * @author Matteo Ciman
 */
public class WriteTouchesHeatmapCreator extends HeatMapCreator {
    
    public WriteTouchesHeatmapCreator(Tester tester) {
        super(tester);
    }
    
    /**
     * For each digit we want to analyze creates an heatmap considering 
     * different size of the digit
     */
    public void createHeatMapForAllDigits() {
        
        ArrayList<String> characters = 
            DigitsHeatmapReader.getDigitsForHeathmap();
        
        /**
         * For each size of the digit, i.e., how much need to spread the tap 
         * interaction
         */
        for (Integer size: digitSize) {
            
            /**
             * For all the digits we want to consider
             */
            for (String charactersToCombine: characters) {
             
                String[] listCharacters = charactersToCombine.split(",");
                /**
                 * Retrieve write tasks both in stress and relax state
                 */
                ArrayList<Write> listWriteExercises = tester
                        .getWriteExercisesForProtocol(ISenseStressAnalyzer.PROTOCOLS[0]);
                
                ArrayList<Digit> stressDigits = new ArrayList<>(), 
                    noStressDigits = new ArrayList<>();
                
                /**
                 * Used to give the possibility of combining together different
                 * characters depending for example on their position on the
                 * keyboard
                 */
                for(String character: listCharacters) {
                    for (Write writeExercise: listWriteExercises) {
                        if (writeExercise.stress()) {
                            stressDigits.addAll(writeExercise.
                                getAllDigitsForAParticularCharacter(character));
                        }
                        else {
                            noStressDigits.addAll(writeExercise.
                                getAllDigitsForAParticularCharacter(character));
                        }
                    }
                }
                
                if (!stressDigits.isEmpty() && !noStressDigits.isEmpty()) {
                
                    ArrayList<ArrayList<Double>> forHeatMapNoStress = new ArrayList<>(), 
                    forHeatMapStress = new ArrayList<>();

                    int width = Math.round(stressDigits.get(0).getKeyClicked().getWidth()), 
                    height = Math.round(stressDigits.get(0).getKeyClicked().getHeight());

                    for (int i = 0; i < width; i++) {
                        ArrayList<Double> secondDimensionNoStress = new ArrayList<>(),
                            secondDimensionStress = new ArrayList<>();

                        for (int j = 0; j < height; j++) {
                            secondDimensionNoStress.add(0.0);
                            secondDimensionStress.add(0.0);
                        }
                        forHeatMapNoStress.add(secondDimensionNoStress);
                        forHeatMapStress.add(secondDimensionStress);
                    }

                    completeHeatmapValues(forHeatMapNoStress, 
                            noStressDigits.get(0).getKeyClicked(), noStressDigits, size);
                    completeHeatmapValues(forHeatMapStress, 
                            stressDigits.get(0).getKeyClicked(), stressDigits, size);

                    saveHeatmaps(forHeatMapNoStress, forHeatMapStress, charactersToCombine, 
                            tester.getName(), size);
                }
            }
        }
    }
    
    /**
     * Saves hitmap in the utput file
     * @param heatmapNoStress the heatmap of the no stress digits
     * @param heatmapStress the heatmap of the stress digits
     * @param textDigit the text of the digit considered
     * @param imei the IMEI of the participant
     * @param digitSize how much the touch is expanded
     */
    private void saveHeatmaps(ArrayList<ArrayList<Double>> heatmapNoStress, 
            ArrayList<ArrayList<Double>> heatmapStress, String textDigit, 
            String imei, int digitSize) {
      
        double[][] heatMapNoStressArray = new double[heatmapNoStress.get(0).size()][];
        double[][] heatMapStressArray = new double[heatmapStress.get(0).size()][];
                
        copyDoubleArrayListToDoubleArray(heatmapNoStress, heatMapNoStressArray);
        copyDoubleArrayListToDoubleArray(heatmapStress, heatMapStressArray);

        HeatmapWriter.saveHeatmap(heatMapNoStressArray, heatMapStressArray, imei, 
                textDigit, digitSize);
    }
}

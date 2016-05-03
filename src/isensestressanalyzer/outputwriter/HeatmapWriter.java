package isensestressanalyzer.outputwriter;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import org.tc33.jheatchart.HeatChart;

/**
 *
 * @author Matteo
 */
public class HeatmapWriter extends OutputWriter {
    
    private static final Color LOW_COLOR = new Color(34, 139, 34);
    private static final Color HIGH_COLOR = Color.red;
    
    public static void saveHeatmap(double[][] noStressHeatmap, 
            double[][] stressHeatmap, String IMEI, String digit, int digitSize) {
        
        HeatChart mapNoStress = new HeatChart(noStressHeatmap), 
            mapStress = new HeatChart(stressHeatmap);
        
        setHeatmapParameters(mapStress, "Letter: " + digit + 
                " - Stress condition");
        setHeatmapParameters(mapNoStress, "Letter: " + digit + 
                " - No Stress condition");
        
        String baseFileName = OUTPUT_FOLDER + FOLDER_HEATMAP_WRITE + 
            File.separator + FOLDER_HEATMAP_DIGIT_SIZE + File.separator + 
            String.valueOf(digitSize) + File.separator + IMEI + File.separator + 
            "DIGIT_" + digit + "_";
        
        try {
            
            File noStressFile = new File(baseFileName + "NOStress.png"), 
                    stressFile = new File(baseFileName + "Stress.png");
            noStressFile.getParentFile().mkdirs();
            stressFile.getParentFile().mkdirs();
            
            mapNoStress.saveToFile(noStressFile);
            mapStress.saveToFile(stressFile);
        } 
        catch (IOException e) {
            System.out.println("IOException in writing heatmap");
            e.printStackTrace();
        }
        
    }
    
    /**
     * Sets heatmap parameters
     * @param heatmap the heatmap
     * @param title the title of the heatmap
     */
    protected static void setHeatmapParameters(HeatChart heatmap, String title) {
        heatmap.setTitle(title);
        heatmap.setLowValueColour(LOW_COLOR);
        heatmap.setHighValueColour(HIGH_COLOR);
        
        heatmap.setShowXAxisValues(false);
        heatmap.setShowYAxisValues(false);
    }
}

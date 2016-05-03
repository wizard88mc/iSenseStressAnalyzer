package isensestressanalyzer.outputwriter;

import static isensestressanalyzer.outputwriter.OutputWriter.OUTPUT_FOLDER;
import java.io.File;
import java.io.IOException;
import org.tc33.jheatchart.HeatChart;

/**
 *
 * @author Matteo Ciman
 */
public class StressorHeatmapWriter extends HeatmapWriter {
    
    public static void saveHeatmap(double[][] heatmap,
            String IMEI, int digitSize, String groupingName, String group, 
            String momentID, String moment) {
        
        HeatChart heatChart = new HeatChart(heatmap);
        
        setHeatmapParameters(heatChart, "Grouping name: " + groupingName + 
            " - Moment ID: " + momentID + " Moment: " + moment);
        
        String baseFileName = OUTPUT_FOLDER + FOLDER_HEATMAP_STRESSOR + 
            File.separator + FOLDER_HEATMAP_DIGIT_SIZE + File.separator + 
            String.valueOf(digitSize) + File.separator + groupingName + 
            File.separator + momentID + File.separator + 
                IMEI + File.separator + group +"_" + moment + ".png";
            
        try {
            File file = new File(baseFileName);
            file.getParentFile().mkdirs();
            
            heatChart.saveToFile(file);
        }
        catch(IOException exc) {
            System.out.println("IOException in saving stressor heatmap");
            exc.printStackTrace();
        }
    }
}

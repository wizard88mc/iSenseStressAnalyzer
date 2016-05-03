package isensestressanalyzer.outputwriter;

import java.io.File;

/**
 *
 * @author Matteo Ciman
 */
public class OutputWriter {
    
    public static String OUTPUT_FOLDER = "data" + File.separator + 
			"output" + File.separator;
    protected static final String FOLDER_HEATMAP_WRITE = "heatmap_write";
    protected static final String FOLDER_HEATMAP_STRESSOR = "heatmap_stressor";
    protected static final String FOLDER_HEATMAP_DIGIT_SIZE = "digit_size";
    protected static final String FOLDER_WRITE_DATA_DISTANCES = "write_data_distances";
    protected static final String FOLDER_STRESSOR_FEATURES = "stressor_features";
}

package isensestressanalyzer.classifiers;

import isensestressanalyzer.analyzer.Analyzer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class ClassifiersTester {
	
	ArrayList<File> listCreatedFiles = null;

	public ClassifiersTester(ArrayList<File> listCreatedFiles) {
		this.listCreatedFiles = listCreatedFiles;
	}
	
	
	public void performEvaluation() {
		
		writeAllOutputFiles();
		
		try {
			
			File outputFile = new File(Analyzer.OUTPUT_FOLDER + "evaluation.txt");
			
			if (!outputFile.exists()) {
				outputFile.createNewFile();
			}
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
			
			for (int i = 0; i < listCreatedFiles.size(); i++) {
				
				DataSource source = new DataSource(listCreatedFiles.get(i).getPath());
				Instances data = source.getDataSet();
				
				if (data.classIndex() == -1) {
					data.setClassIndex(data.numAttributes() - 1);
				}
				
				if (data.numInstances() > 10) {
					J48 tree = new J48();
					tree.buildClassifier(data);
					Evaluation eval = new Evaluation(data);
					eval.crossValidateModel(tree, data, 10, new Random(1));
					
					writer.write(listCreatedFiles.get(i).getPath() + System.getProperty("line.separator"));
					writer.write("TREE" + System.getProperty("line.separator"));
					writer.write(eval.toClassDetailsString() + System.getProperty("line.separator"));
					
					for (int index = 1; index < 3; index++) {
						
						IBk knn = new IBk(index);
						knn.buildClassifier(data);
						Evaluation evalKnn = new Evaluation(data);
						evalKnn.crossValidateModel(knn, data, 10, new Random(10));
						
						writer.write(index + "kNN" + System.getProperty("line.separator"));
						writer.write(evalKnn.toClassDetailsString() + System.getProperty("line.separator"));
					}
				}
			}
			
			writer.flush(); writer.close();
		}
		catch(Exception exc) {
			exc.printStackTrace();
			System.out.println(exc.toString());
		}
	}
	
	private void writeAllOutputFiles() {
		
		try {
			File outputAllFiles = new File(Analyzer.OUTPUT_FOLDER + "outputFiles.txt");
			
			if (!outputAllFiles.exists()) {
				outputAllFiles.createNewFile();
			}
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(outputAllFiles));
			
			for (File file: listCreatedFiles) {
				writer.write(file.getPath() + ";");
			}
			writer.flush(); writer.close();
		}
		catch(Exception exc) {
			
		}
	}
}

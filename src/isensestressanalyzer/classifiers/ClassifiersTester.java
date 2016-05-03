package isensestressanalyzer.classifiers;

import isensestressanalyzer.analyzer.Analyzer;
import isensestressanalyzer.outputwriter.OutputWriter;

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
import weka.classifiers.functions.LibSVM;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.VotedPerceptron;

public class ClassifiersTester {
	
    ArrayList<File> listCreatedFiles = null;

    public ClassifiersTester(ArrayList<File> listCreatedFiles) {
        this.listCreatedFiles = listCreatedFiles;
    }
	
    public void performEvaluation() {

            writeAllOutputFiles();

            try {

                File outputFile = new File(OutputWriter.OUTPUT_FOLDER + 
                    "evaluation.txt");

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
                        eval.crossValidateModel(tree, data, 10, new Random(10));

                        writer.write(listCreatedFiles.get(i).getPath() + System.getProperty("line.separator"));
                        writer.write("TREE" + System.getProperty("line.separator"));
                        writer.write(eval.toClassDetailsString() + System.getProperty("line.separator"));

                        for (int index = 1; index < 3; index++) {

                            IBk knn = new IBk(index);
                            knn.setOptions(weka.core.Utils.splitOptions("-W 0 -X -A weka.core.neighboursearch.LinearNNSearch"));
                            knn.buildClassifier(data);
                            Evaluation evalKnn = new Evaluation(data);
                            evalKnn.crossValidateModel(knn, data, 10, new Random(10));

                            writer.write(index + "kNN" + System.getProperty("line.separator"));
                            writer.write(evalKnn.toClassDetailsString() + System.getProperty("line.separator"));
                        }

                        try {
                            LibSVM svm = new LibSVM();
                            svm.setOptions(weka.core.Utils.
                                splitOptions("-S 1 -K 2 -D 5 -G 0.0 -R 0.0 "
                                    + "-N 0.5 -M 40 -C 2.0 -E 0.001 -P 0.1 "
                                    + "-Z -seed 1"));
                            svm.buildClassifier(data);
                            Evaluation evalSVM = new Evaluation(data);
                            evalSVM.crossValidateModel(svm, data, 10, new Random(10));

                            writer.write("SVM" + System.getProperty("line.separator"));
                            writer.write(evalSVM.toClassDetailsString() + System.getProperty("line.separator"));
                        }
                        catch(Exception exc) {}

                        MultilayerPerceptron multiPerceptron = new MultilayerPerceptron();
                        multiPerceptron.setOptions(weka.core.Utils.
                            splitOptions("-L 0.3 -M 0.2 -N 1000 -V 0 -S 1 -E 20 "
                                + "-H a"));
                        multiPerceptron.buildClassifier(data);
                        Evaluation evalMultiP = new Evaluation(data);
                        evalMultiP.crossValidateModel(multiPerceptron, data, 10, 
                            new Random(10));

                        writer.write("Multilayer Perceptron" + 
                            System.getProperty("line.separator"));
                        writer.write(evalMultiP.toClassDetailsString() + 
                            System.getProperty("line.separator"));

                        VotedPerceptron votedPerceptron = new VotedPerceptron();
                        votedPerceptron.setOptions(weka.core.Utils.
                            splitOptions("-I 10 -E 1.0 -S 2 -M 10000"));
                        votedPerceptron.buildClassifier(data);
                        Evaluation evalVotedP = new Evaluation(data);
                        evalVotedP.crossValidateModel(votedPerceptron, data, 10, 
                                new Random(10));

                        writer.write("Voted Perceptron" + 
                            System.getProperty("line.separator"));
                        writer.write(evalVotedP.toClassDetailsString() + 
                            System.getProperty("line.separator"));

                        /*BayesNet bayes = new BayesNet();
                        bayes.setOptions(weka.core.Utils.splitOptions("-D -Q "
                                + "weka.classifiers.bayes.net.search.local.SimulatedAnnealing "
                                + "-- -A 10.0 -U 10000 -D 0.999 -R 1 -S BAYES -E "
                                + "weka.classifiers.bayes.net.estimate.SimpleEstimator "
                                + "-- -A 0.5"));
                        bayes.buildClassifier(data);
                        Evaluation evalBayes = new Evaluation(data);
                        evalBayes.crossValidateModel(bayes, data, 10, new Random(10));

                        writer.write("Bayes network" + System.getProperty("line.separator"));
                        writer.write(evalBayes.toClassDetailsString() + System.getProperty("line.separator"));*/
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
            File outputAllFiles = new File(OutputWriter.OUTPUT_FOLDER + 
                    "outputFiles.txt");

            if (!outputAllFiles.exists()) {
                    outputAllFiles.createNewFile();
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(outputAllFiles));

            for (File file: listCreatedFiles) {
                    writer.write(file.getPath() + ";");
            }
            writer.flush(); writer.close();
        }
        catch(Exception exc) {}
    }
}

package HW1;

import weka.classifiers.Classifier;
import weka.core.Capabilities;
import weka.core.Instance;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LinearRegression implements Classifier {
	
    private int m_ClassIndex;
	private int m_truNumAttributes;
	private double[] m_coefficients;
	private double m_alpha;
	
	//the method which runs to train the linear regression predictor, i.e.
	//finds its weights.
	@Override
	public void buildClassifier(Instances trainingData) throws Exception {
		m_ClassIndex = trainingData.classIndex();
		//TODO: complete this method
		m_coefficients = gradientDescent(trainingData);
	}
	
	private void findAlpha(Instances data) throws Exception {
		
	}
	
	/**
	 * An implementation of the gradient descent algorithm which should
	 * return the weights of a linear regression predictor which minimizes
	 * the average squared error.
     * 
	 * @param trainingData
	 * @throws Exception
	 */
	private static double[] gradientDescent(Instances trainingData) throws Exception {
        double alpha = 0.05;

        // CREATES AN ARRAY OF THETAS WITH GUESS = 1
        int numAttributes = trainingData.numAttributes() - 1;
        double[] weights = new double[numAttributes];
        for (int i = 0; i < weights.length; i++) {
            weights[i] = 1;
        }
        // CREATES ARRAY OF TEMPS THETAS
        double[] temp = new double[numAttributes];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = 0;
        }

        double sum, inSum, partDerivative = 0;

        // While the error is too big do this

        // For all thetas
        for (int k = 0; k < temp.length; k++) {
            sum = 0;

            // Sum on all instances
            for (int i = 0; i < trainingData.numInstances(); i++) {
                inSum = 0;

                for (int l = 0; l < weights.length; l++) {
                    inSum += (weights[l] * trainingData.instance(i).value(l));
                }
                inSum -= trainingData.instance(i).value(trainingData.numAttributes() - 1);
                inSum *= trainingData.instance(i).value(k);
                sum += inSum;
            }
            partDerivative = (1/((double)trainingData.numInstances())) * sum;
            temp[k] = weights[k] - (alpha * partDerivative);
        }

        for (int i = 0; i < weights.length; i++) {
            weights[i] = temp[i];
        }

        String s = "";
        for (int i = 0; i < weights.length; i++) {
            s += " Theta"+i+" = " + weights[i];
        }
        System.out.println(s);

        return weights;
	}
	
	/**
	 * Returns the prediction of a linear regression predictor with weights
	 * given by m_coefficients on a single instance.
     *
	 * @param instance
	 * @return
	 * @throws Exception
	 */
	public double regressionPrediction(Instance instance) throws Exception {
        double result = 0;
        for (int i = 0; i < instance.numAttributes() - 1; i++) {
            result += instance.value(i) * m_coefficients[i];
        }
        return result;
	}
	
	/**
	 * Calculates the total squared error over the data on a linear regression
	 * predictor with weights given by m_coefficients.
     *
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public double calculateMSE(Instances data) throws Exception {
        double sum = 0;
        for (int i = 0; i < data.numInstances(); i++) {
            sum += Math.pow(regressionPrediction(data.instance(i)) -
                    data.instance(i).value(data.numAttributes()-1), 2);
        }
        return 0.5 * (1/(double)data.numInstances()) * sum;
	}
    
    @Override
	public double classifyInstance(Instance arg0) throws Exception {
		// Don't change
		return 0;
	}

	@Override
	public double[] distributionForInstance(Instance arg0) throws Exception {
		// Don't change
		return null;
	}

	@Override
	public Capabilities getCapabilities() {
		// Don't change
		return null;
	}

	private static double scalarProduct(double[] v1, double[] v2) {
        double result = 0;
        for (int i = 0; i <v1.length; i++) {
            result += v1[i] * v2[i];
        }
        return result;
    }

    public static void main(String[] args) throws Exception {

        // CREATES INSTANCE OF OUR DATA
        Instances data = null;
        try {
            data = new Instances(new BufferedReader(new FileReader("wind_training.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        gradientDescent(data);



    }
}

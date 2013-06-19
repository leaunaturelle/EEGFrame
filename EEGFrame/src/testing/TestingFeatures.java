package testing;

import statisticMeasure.Statistics;

import features.linear.frequency.SpectralAnalysis;
import features.linear.timeDomain.AutocorrelationCoefficient;
import features.nonlinear.entropy.ApEn;
import features.nonlinear.entropy.CorrectedConditionalShannonEntropy;
import features.nonlinear.entropy.RenyiEntropy;
import features.nonlinear.entropy.SampEn;
import features.nonlinear.fractal.DFA;
import features.nonlinear.fractal.HiguchiDimension;
import features.nonlinear.fractal.HurstExponent;
import features.nonlinear.multiSeries.MutualDimension;
import features.nonlinear.multiSeries.SynchronizationLikelihood;
import features.nonlinear.other.LempelZivComplexity;
import features.nonlinear.phaseSpace.CTM;
import features.nonlinear.phaseSpace.CorrelationDimension;
import features.nonlinear.phaseSpace.LyapunovExponent;
import features.nonlinear.phaseSpace.RecurrencePlot;
import features.nonlinear.phaseSpace.SpatialFillingIndex;
import features.nonlinear.phaseSpace.StandardDeviationRatio;
import features.timeFrequency.HilbertHuangTransform;

public class TestingFeatures {
	
	//private EdfDataFile file;
	private double[] series;
	private double sampFreq;
	
	public TestingFeatures(){
		
//		int sigNum = 1;
//		ArrayList<Short> samples = EdfDataFile.getSignalSamples(sigNum); 
//		int n = samples.size();	
//		series = new double[n];
//		sampFreq = EdfDataFile.getHeader().getSignalParameters().get(sigNum).getFrequency();
//		
//		double physMax = EdfDataFile.getHeader().getSignalParameters().get(sigNum).getPhysMax();
//		double physMin = EdfDataFile.getHeader().getSignalParameters().get(sigNum).getPhysMin();
//		double digMax = EdfDataFile.getHeader().getSignalParameters().get(sigNum).getDigMax();
//		double digMin = EdfDataFile.getHeader().getSignalParameters().get(sigNum).getDigMin();
//		double koef = (physMax -physMin)/(digMax-digMin);
//	
//		for(int i=0; i < n; i++){
//			series[i] = (double) (samples.get(i) * koef);
//		}
//		try {
//			CSVFile.createCSVFile("file.csv");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
	public void testStatisticalFeatures(){

		//STANDARD STATISTICAL FEATURES
		
		double test1 = features.linear.timeDomain.Mean.calculateMean(series);
		System.out.println("Srednja vrijednost na drugom signalu u ovom edf fileu iznosi " + test1);
		
		double test3 = features.linear.timeDomain.Mean.calculateMean(series, 0, 5);
		System.out.println("Srednja vrijednost na drugom signalu za odreðeni interval u ovom edf fileu iznosi " + test3);
		
		//nije testirano
		double test2 = features.linear.timeDomain.StandardDeviation.calculateStandardDeviation(series);
		System.out.println("Standardna devijacija na drugom signalu u ovom edf fileu iznosi " + test2);
	
		
		double test4 = features.linear.timeDomain.StandardDeviation.calculateStandardDeviation(series, 153595, 153599);
		System.out.println("Standardna devijacija na drugom signalu za odreðeni interval u ovom edf fileu iznosi " + test4);
		
		//nije testirano
		double test5 = features.linear.timeDomain.MeanOfAbsoluteValuesOfTheFirstDifferences.calculateMeanOfFirstDiff(series);
		System.out.println("Srednja vrijednost apsolutnih vrijednosti prvih razlika na drugom signalu u ovom edf fileu iznosi " + test5);
		
		double test6 = features.linear.timeDomain.MeanOfAbsoluteValuesOfTheFirstDifferences.calculateMeanOfFirstDiff(series, 153595, 153599);
		System.out.println("Srednja vrijednost apsolutnih vrijednosti prvih razlika za odreðen interval na drugom signalu u ovom edf fileu iznosi " + test6);
		
		//nije testirano
		double test7 = features.linear.timeDomain.MeanOfAbsoluteValuesOfTheSecondDifferences.calculateMeanOfSecondDiff(series);
		System.out.println("Srednja vrijednost apsolutnih vrijednosti drugih razlika na drugom signalu u ovom edf fileu iznosi " + test7);
		
		double test8 = features.linear.timeDomain.MeanOfAbsoluteValuesOfTheSecondDifferences.calculateMeanOfSecondDiff(series, 153595, 153599);
		System.out.println("Srednja vrijednost apsolutnih vrijednosti drugih razlika za odreðen interval na drugom signalu u ovom edf fileu iznosi " + test8);
		
		double test9 = features.linear.timeDomain.MeanOfAbsoluteValuesOfFirstDiffNormalized.calculateMeanOfFirstDiffNormalized(series);
		System.out.println("Srednja vrijednost apsolutnih vrijednosti prvih razlika na normaliziranom drugom signalu u ovom edf fileu iznosi " + test9);
		
		double test10 = features.linear.timeDomain.MeanOfAbsoluteValuesOfFirstDiffNormalized.calculateMeanOfFirstDiffNormalized(series, 153595, 153599);
		System.out.println("Srednja vrijednost apsolutnih vrijednosti prvih razlika za odreðen interval na normaliziranom drugom signalu u ovom edf fileu iznosi " + test10);
		
		double test11 = features.linear.timeDomain.MeanOfAbsoluteValuesOfSecondDiffNormalized.calculateMeanOfSecondDiffNormalized(series);
		System.out.println("Srednja vrijednost apsolutnih vrijednosti drugih razlika na normaliziranom drugom signalu u ovom edf fileu iznosi " + test11);
		
		double test12 = features.linear.timeDomain.MeanOfAbsoluteValuesOfSecondDiffNormalized.calculateMeanOfSecondDiffNormalized(series, 153595, 153599);
		System.out.println("Srednja vrijednost apsolutnih vrijednosti drugih razlika za odreðen interval na normaliziranom drugom signalu u ovom edf fileu iznosi " + test12);
		
	}
	
	public void testFrequencyFeatures(){
		/*
		 * 
		 * SPEKTRALNA ANALIZA
		 * 
		 */
		//nije testirano
		int length = 1024;
		double	[] series2 = new double[length];
		for(int i = 0; i < length; i++){
			series2[i] = series[i];
		}
		SpectralAnalysis SA = new SpectralAnalysis(series2, sampFreq);
		SA.calculateSpectrumFourier(2); //2 - HANN WINDOW
	}
	
	public void testAutocorrelation(){
		
		//nije testirano
		int length = 200;
		double	[] series2 = new double[length];
		for(int i = 0; i < length; i++){
			series2[i] = series[i];
		}
		//AutocorrelationCoefficient AC = new AutocorrelationCoefficient();
		double result = AutocorrelationCoefficient.calculateAutocorrelationCoefficient(series2);
		System.out.println("Rezultat autokorelacijske fje je: "+ result);
		
	}
	
	public void testRecurrencePlot(){
		
		//nije testirano
		int k = 150000;
		int length = 500;
		double	[] series2 = new double[length];
		for(int i = 0; i < length; i++){
			series2[i] = series[i+k];
		}
		RecurrencePlot RP = new RecurrencePlot(series2, 4, 20, 30);	
		System.out.println("Prosjeèan broj susjeda svake toèke je: " +RP.calculateAVGNumOfNeighbours());
		System.out.println("DET: "+RP.calculateDET());
		System.out.println("Laminarity: "+RP.calculateLaminarity());
		System.out.println("Prosjeèna duljina dijagonalnih linija LMean: "+RP.calculateLMean());
		System.out.println("Recurrence rate: "+RP.calculateRecurrenceRate());
		System.out.println("Shannon entropy: "+RP.calculateShannonEntropyRecurrence());
	}
	public void testHiguchi(){
		int length = 400;
		int k = 1000;
		double	[] series2 = new double[length];
		for(int i = 0; i < length; i++){
			series2[i] = series[i+k];
		}
		HiguchiDimension hd = new HiguchiDimension(series2, 3);
		//TEST
		System.out.println("Higuchi dimenzija ispada: "+ hd.getHiguchiDimension());
	
	}
	public void testHurst(){
		int length = 300;
		int k = 1000;
		double	[] series2 = new double[length];
		for(int i = 0; i < length; i++){
			series2[i] = series[i+k];
		}
		//HurstExponent H = new HurstExponent();
		double slope = HurstExponent.calculateHurstExponent(series2);
		System.out.println("Hurst exponent ispada: "+slope);
	}
	public void testDFA(){
		int length = 300;
		int k = 150000;
		double	[] series2 = new double[length];
		for(int i = 0; i < length; i++){
			series2[i] = series[i+k];
		}
		double polje[] = new double[2];
		try {
			polje = DFA.calculateDFA(series2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("alphaS= "+polje[0]+ " , alphaL= "+polje[1]);
	}
	public void testApEn(){
			int length = 1000;
			int k = 1000;
			double	[] series2 = new double[length];
			for(int i = 0; i < length; i++){
				series2[i] = series[i+k];
			}
			double apEn = ApEn.calculateApEn(series2, 3, 0.15*Statistics.standardDeviation(series2));
			double maxApEn[] = ApEn.calculateMaxApEn(series2, 2);
			System.out.println("ApEn iznosi: "+apEn);
			System.out.println("Max apEn iznosi: "+maxApEn[0] +" a maxIndex: "+maxApEn[1] );
	}
	public void testSampEn(){
		int length = 1000;
		int k = 1000;
		double	[] series2 = new double[length];
		for(int i = 0; i < length; i++){
			series2[i] = series[i+k];
		}
		double sampEn = SampEn.calculateSampEn(series2, 2, 0.15*Statistics.standardDeviation(series2));
		double maxSampEn[] = SampEn.calculateMaxSampEn(series2, 2);
		System.out.println("SampEn iznosi: "+sampEn);
		System.out.println("Max sampEn iznosi: "+maxSampEn[0] +" a maxIndex: "+maxSampEn[1] );
	}
	
	public void testCD(){
		int length = 300;
		int k = 1500;
		int dimension = 10;
		int lags = 3;
		
		
		double	[] series2 = new double[length];
		for(int i = 0; i < length; i++){
			series2[i] = series[i+k];
		}
		double aMatrix[][] = new double[series2.length-(dimension-1)*lags][dimension];
		for (int g=0; g<series2.length-(dimension-1)*lags; g++){
			for (int n=1; n<=dimension; n++){
				aMatrix[g][n-1] = series2[g+(n-1)*lags];
			}
		}
		
		try {
			double cd = CorrelationDimension.calculateCorrelationDimension(aMatrix, 20);
			System.out.println("Correlation dimension: "+cd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testRenyi(){
		
		int length = 1000;
		int k = 1500;
		double	[] series2 = new double[length];
		for(int i = 0; i < length; i++){
			series2[i] = series[i+k];
		}
		double re = RenyiEntropy.calculateRenyiEntropy(series2, 2);
		System.out.println("Renyi entropy: "+re);	
	}
	
	public void testSFI(){
		
		int dimension = 3;
		int lags[] = new int[10];
		
	
		double sfindices[] = new double[lags.length];
		for (int k=0; k<lags.length; k++){
			lags[k] = k+1;
			if (series.length-dimension*lags[k]<SpatialFillingIndex.MINIMAL_LENGTH_FOR_EXTRACTION){
				sfindices[k] = 0.0;
				System.err.print("SFI estimation impossible in this segment.\n");
				continue;
				
			}
			double aMatrix [][] = new double[series.length-(dimension-1)*lags[k]][dimension];
			for (int g=0; g<aMatrix.length; g++){
				for (int n=1; n<=dimension; n++){
					aMatrix[g][n-1] = series[g+(n-1)*lags[k]];
				}
			}
			double max2 = Statistics.maximum(series);
			sfindices[k] = new SpatialFillingIndex(10,max2,aMatrix).getIndex();
			
		}
		for(int i=0; i<sfindices.length; i++){
			System.out.println("SFindex "+i+ ": "+ sfindices[i]);
		}
	}
	
	public void testHilbertHuang(){
		int length = 1000;
		int k = 1500;
		double	[] series2 = new double[length];
		for(int i = 0; i < length; i++){
			series2[i] = series[i+k];
		}
		
		HilbertHuangTransform hh = new HilbertHuangTransform(series2, sampFreq);
		double amplitudes[][] = hh.getAmplitudes();
//		double instFreq[][] = hh.getInstantaneousFrequencies();
//		double maxSmp[] = hh.getAmplitudesForMaximumInstantaneousFrequencies();
		
		System.out.println("Broj IMF fja je: "+amplitudes.length);
//		for(int i = 0; i < amplitudes.length; i++){
//			System.out.println("BROJ "+i);
//			for(int j=0; j < amplitudes[i].length; j++){
//				System.out.println(amplitudes[i][j]);
//			}
//		}
	}
	public void testStdDevRatio(){
		int length = 1000;
		int k = 1500;
		double	[] series2 = new double[length];
		for(int i = 0; i < length; i++){
			series2[i] = series[i+k];
		}
		StandardDeviationRatio sdr = new StandardDeviationRatio(series2);
		System.out.println("SD2/SD1: "+sdr.getCSI());
		System.out.println("CVI: "+sdr.getCVI());		
	}
	
	public void testLZ(){
		int length = 1000;
		int k = 1500;
		double	[] series2 = new double[length];
		for(int i = 0; i < length; i++){
			series2[i] = series[i+k];
		}
		System.out.println("Lempel-Ziv complexity: "+LempelZivComplexity.calculateLempelZivComplexity(series2));
	}
	
	public void testShannon(){
		int length = 1000;
		int k = 1500;
		double	[] series2 = new double[length];
		for(int i = 0; i < length; i++){
			series2[i] = series[i+k];
		}
		System.out.println("Shannon entropy: "+CorrectedConditionalShannonEntropy.calculateCorrectedConditionalShannonEntropy(series2, 2, 10));
	}
	
	public void testCTM(){
		int length = 1000;
		int k = 1500;
		double	[] series2 = new double[length];
		for(int i = 0; i < length; i++){
			series2[i] = series[i+k];
		}
		
		int dimension = 3;
		int lags[] = new int[10];	
		double mind, maxd, r;
			
		double ctmindices[] = new double[lags.length];
		for (k=0; k<lags.length; k++){
			lags[k]= k+1;
			if (series2.length-dimension*lags[k]<CTM.MINIMAL_LENGTH_FOR_EXTRACTION){
				ctmindices[k] = 0.0;
				System.err.print("CTM estimation impossible in this segment.\n");
				continue;
			}
			mind = Statistics.getDifferenceMinimumNthOrder(series2, lags[k], 0, series2.length, false, Statistics.DESCENDING);
			maxd = Statistics.getDifferenceMaximumNthOrder(series2, lags[k], 0, series2.length, false, Statistics.DESCENDING);
			r = (maxd-mind)/8;
			ctmindices[k] = CTM.calculateCTM(series2, r, dimension, lags[k]);
		}
		for(int i=0; i<ctmindices.length; i++){
			System.out.println("CTM index "+i+ ": "+ ctmindices[i]);
		}
	}
	
	public void testLLE(){
		int length = 500;
		int k = 15000;
		double	[] series2 = new double[length];
		for(int i = 0; i < length; i++){
			series2[i] = series[i+k];
		}
		int trajectoryLength;
		if (series2.length >= 200){
			trajectoryLength = LyapunovExponent.DEFAULT_TRAJECTORY_LENGTH;
		}
		else {
			trajectoryLength = LyapunovExponent.SMALL_TRAJECTORY_LENGTH; 
		}
		
		try {
			double lle = LyapunovExponent.calculateLargestLyapunovExponentRosenstien(series2, LyapunovExponent.DEFAULT_PHASE_DIMENSION, trajectoryLength);
			System.out.println("Largest Lyapunov exp: "+lle);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public void testMutualDim(){
		int length = 1000;
		int k = 15000;
		double	[] series2 = new double[length];
		for(int i = 0; i < length; i++){
			series2[i] = series[i+k];
		}
		
		double dm = MutualDimension.calculateMutualDimension(series2, series2, MutualDimension.MAXIMUM_EMBEDDING_DIMENSION, MutualDimension.MAXIMUM_EMBEDDING_DIMENSION, 5, 5, 20);
		System.out.println("Dm prvog je "+MutualDimension.D2A + ", Dm drugog je " +MutualDimension.D2B+ ", Dm treæeg je "+MutualDimension.D2C+", Mutual dimension iznosi: "+ dm);
	}
	
	public void testSynchroLikelihood(){
	
//		double[][] synchro = SynchronizationLikelihood.calculateSynchronizationLikelihood(new int[]{0,2,4}, 1000, 1500, 3, 5, 10, 0.05, 20);
//		
//		for(int i = 0; i < synchro.length; i++){
//			System.out.println("Signal s indexom: "+ i);
//			for(int j = 0; j < synchro[0].length; j++){
//				System.out.print(synchro[i][j] +" ");
//			}
//		}
	}
}


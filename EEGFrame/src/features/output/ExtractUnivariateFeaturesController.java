/**
 * 
 */
package features.output;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import statisticMeasure.Statistics;

import dataHandling.InputFile;
import features.linear.frequency.SpectralAnalysis;
import features.linear.timeDomain.AutocorrelationCoefficient;
import features.linear.timeDomain.Mean;
import features.linear.timeDomain.MeanOfAbsoluteValuesOfFirstDiffNormalized;
import features.linear.timeDomain.MeanOfAbsoluteValuesOfSecondDiffNormalized;
import features.linear.timeDomain.MeanOfAbsoluteValuesOfTheFirstDifferences;
import features.linear.timeDomain.MeanOfAbsoluteValuesOfTheSecondDifferences;
import features.linear.timeDomain.StandardDeviation;
import features.nonlinear.entropy.ApEn;
import features.nonlinear.entropy.RenyiEntropy;
import features.nonlinear.entropy.SampEn;
import features.nonlinear.fractal.DFA;
import features.nonlinear.fractal.HiguchiDimension;
import features.nonlinear.fractal.HurstExponent;
import features.nonlinear.phaseSpace.CorrelationDimension;
import features.nonlinear.phaseSpace.LyapunovExponent;
import features.nonlinear.phaseSpace.RecurrencePlot;
import features.nonlinear.phaseSpace.SpatialFillingIndex;
import features.nonlinear.phaseSpace.StandardDeviationRatio;
import gui.SelectedSignal;
import gui.features.ExtractUnivariateFeaturesWindow;

/**
 * @author lsuc
 *
 */
public class ExtractUnivariateFeaturesController extends ExtractFeaturesController {

	private ExtractUnivariateFeaturesWindow extractUnivariateFeaturesWindow;
	private ExtractMixedFeaturesController extractMixedFeaturesController;

	public synchronized ExtractUnivariateFeaturesWindow getExtractUnivariateFeaturesWindow() {
		if(extractUnivariateFeaturesWindow == null){
			extractUnivariateFeaturesWindow = new ExtractUnivariateFeaturesWindow(this);
			selectedFeatures = new ArrayList<Features>();
			selectedFeatures.add(new UnivariateFeatures());
		}
		return extractUnivariateFeaturesWindow;
	}
	
	public void setUnivariateFeaturesSignalsList(SelectedSignal[] signals){
		this.extractUnivariateFeaturesWindow.setSignalsLabelList(signals);
		this.extractUnivariateFeaturesWindow.setVisible(true);
	}

	/* (non-Javadoc)
	 * @see features.output.ExtractFeaturesController#extractFeatures()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void extractFeatures() {
		UnivariateFeatures features = (UnivariateFeatures) selectedFeatures.get(0);
		
		for(int k = 0; k < selectedFeatures.size(); k++){
			HashMap<String, String>[] map =  (HashMap<String, String>[])new HashMap[features.getSignals().length];
			for(int i = 0; i < features.getSignals().length; i++){
				map[i] = new HashMap<String, String>();
				for(int j = 0; j < features.getOptionsToPrint().size(); j++){
					map[i].put(features.getOptionsToPrint().get(j), "?");
				}
			}	
			selectedFeatures.get(k).setExtractedFeatures(map);
		}
		for(int featuresInterval = 0; featuresInterval < selectedFeatures.size(); featuresInterval++){
			features = (UnivariateFeatures) selectedFeatures.get(featuresInterval);
			System.out.println("Interval broj " + featuresInterval);
			System.out.println("interval je " + features.getTimeInterval().get(0)[0] + "- " + features.getTimeInterval().get(0)[1]);
			InputFile file;
			SelectedSignal signal;
			Long[] samples = new Long[2];
			if(features.getSampleInterval() != null){
				System.out.println("Tu ne smijem doc");
				samples = features.getSampleInterval().get(0);				
				for(int i = 0; i < features.getSignals().length; i++){
//					index = features.getIndex();
					signal = features.getSignals()[i];
					file = signal.getFile();
					long samplesLength = samples[1];
					if(samplesLength == 0){
						samplesLength = file.calculateSignalSamplesNum(signal.getSignalIndex()) - samples[0];
					}
					else{
						samplesLength -= samples[0];
					}
					features.getExtractedFeatures()[i].put(UnivariateFeatures.START_SAMPLE, Long.toString(samples[0]));
					features.getExtractedFeatures()[i].put(UnivariateFeatures.SEGMENT_LENGTH_SAMPLES, Long.toString(samplesLength));
				}
			}
			for(int i = 0; i < features.getSignals().length; i++){
				signal = features.getSignals()[i];
				file = signal.getFile();
				if(features.getTimeInterval() != null){
//					System.out.println("Evo me nize u kodu, Interval broj " + featuresInterval);
//					System.out.println("Evo me nize u kodu, interval je " + features.getTimeInterval().get(0)[0] + "- " + features.getTimeInterval().get(0)[1]);
					
					Double[] time = features.getTimeInterval().get(0);
					samples[0] = file.calculateSampleFromTime(time[0], signal.getSignalIndex());
					if(time[1] == 0.0){
						samples[1] = file.calculateSignalSamplesNum(signal.getSignalIndex());
						time[1] = file.calculateDuration(signal.getSignalIndex())-time[0];
					}
					else{
						samples[1] = Math.min(file.calculateSampleFromTime(time[1], signal.getSignalIndex()), file.calculateSignalSamplesNum(signal.getSignalIndex()));
					}
					for(int k = 0; k < features.getSignals().length; k++){
						features.getExtractedFeatures()[k].put(UnivariateFeatures.START_TIME, Double.toString(time[0]));
						features.getExtractedFeatures()[k].put(UnivariateFeatures.SEGMENT_LENGTH_SEC, Double.toString(time[1]-time[0]));
					}
				}		
				int startSample = (int)samples[0].longValue();
				int endSample = (int)samples[1].longValue();
				System.out.println("izracunati start sample je "+ startSample + " a end sample "+endSample);
				double[] series = file.getSamplesFromInterval(signal.getSignalIndex(), startSample, endSample);
				features.getExtractedFeatures()[i].put(UnivariateFeatures.FILE_LABEL, file.getName());
				features.getExtractedFeatures()[i].put(UnivariateFeatures.SIGNAL_LABEL, signal.getSignalLabel());

				calculateUnivariateFeatures(features, series, i, file, signal);

			}
			
		}
	}
	
	public void removeUnknownOptions(){
		ArrayList<Features> f = getSelectedFeatures();
		for(int i = 0; i < f.size(); i++){
			UnivariateFeatures univariateFeatures = (UnivariateFeatures) f.get(i);
			System.out.println("velicina options to print za " + i +" je "+ univariateFeatures.getOptionsToPrintNoParams().size());
			Iterator featuresIt = univariateFeatures.getFeatures().entrySet().iterator();
		    while (featuresIt.hasNext()) {
		        Map.Entry pairs = (Map.Entry)featuresIt.next();
		        if((Boolean) pairs.getValue() == false){
		        	univariateFeatures.getOptionsToPrint().remove(pairs.getKey());
		        	univariateFeatures.getOptionsToPrintNoParams().remove(pairs.getKey());

		        }	

		    }
        	System.out.println("velicina options to print za " + i +" je "+ univariateFeatures.getOptionsToPrintNoParams().size());
		}
	
	}
	
	public void calculateUnivariateFeatures(Features selectedFeatures, double[] series, int i, InputFile file, SelectedSignal signal){
		if(selectedFeatures.getFeatures().get(UnivariateFeatures.MEAN)){
			double mean = Mean.calculateMean(series);
			System.out.println("signal "+i+ "mean je " + mean);
			selectedFeatures.getExtractedFeatures()[i].put(UnivariateFeatures.MEAN, Double.toString(mean));
		}
		if(selectedFeatures.getFeatures().get(UnivariateFeatures.STANDARD_DEVIATION)){
			double stdDev = StandardDeviation.calculateStandardDeviation(series);
			selectedFeatures.getExtractedFeatures()[i].put(UnivariateFeatures.STANDARD_DEVIATION, Double.toString(stdDev));
		}
		if(selectedFeatures.getFeatures().get(UnivariateFeatures.MEAN_OF_ABS_VALUES_OF_FIRST_DIFFERENCES_NORMALIZED)){
			double meanOfAbsFirstDiffN = MeanOfAbsoluteValuesOfFirstDiffNormalized.calculateMeanOfFirstDiffNormalized(series);
			selectedFeatures.getExtractedFeatures()[i].put(UnivariateFeatures.MEAN_OF_ABS_VALUES_OF_FIRST_DIFFERENCES_NORMALIZED, Double.toString(meanOfAbsFirstDiffN ));
		}
		if(selectedFeatures.getFeatures().get(UnivariateFeatures.MEAN_OF_ABS_VALUES_OF_SECOND_DIFFERENCES_NORMALIZED)){
			double meanOfAbsSecondDiffN = MeanOfAbsoluteValuesOfSecondDiffNormalized.calculateMeanOfSecondDiffNormalized(series);
			selectedFeatures.getExtractedFeatures()[i].put(UnivariateFeatures.MEAN_OF_ABS_VALUES_OF_SECOND_DIFFERENCES_NORMALIZED, Double.toString(meanOfAbsSecondDiffN ));
		}
		if(selectedFeatures.getFeatures().get(UnivariateFeatures.MEAN_OF_ABS_VALUES_OF_FIRST_DIFFERENCES_RAW)){
			double meanOfAbsFirstDiffRaw = MeanOfAbsoluteValuesOfTheFirstDifferences.calculateMeanOfFirstDiff(series);
			selectedFeatures.getExtractedFeatures()[i].put(UnivariateFeatures.MEAN_OF_ABS_VALUES_OF_FIRST_DIFFERENCES_RAW, Double.toString(meanOfAbsFirstDiffRaw ));
		}
		if(selectedFeatures.getFeatures().get(UnivariateFeatures.MEAN_OF_ABS_VALUES_OF_SECOND_DIFFERENCES_RAW)){
			double meanOfAbsSecondDiffRaw = MeanOfAbsoluteValuesOfTheSecondDifferences.calculateMeanOfSecondDiff(series);
			selectedFeatures.getExtractedFeatures()[i].put(UnivariateFeatures.MEAN_OF_ABS_VALUES_OF_SECOND_DIFFERENCES_RAW, Double.toString(meanOfAbsSecondDiffRaw ));
		}
		if(selectedFeatures.getFeatures().get(UnivariateFeatures.AUTOCORRELATION_COEF)){
			double autocorrelationCoef = AutocorrelationCoefficient.calculateAutocorrelationCoefficient(series);
			selectedFeatures.getExtractedFeatures()[i].put(UnivariateFeatures.AUTOCORRELATION_COEF, Double.toString(autocorrelationCoef));
		}
		
		if(selectedFeatures.getFeatures().get(UnivariateFeatures.RECURRENCE_PLOT_FEATURES)){
			int phaseSpaceDim = (int)Double.parseDouble(extractUnivariateFeaturesWindow.getPhaseSpaceFeaturesDialog().getPhaseSpaceDimensionTextField().getText());
			int phaseSpaceLag = (int)Double.parseDouble(extractUnivariateFeaturesWindow.getPhaseSpaceFeaturesDialog().getPhaseSpaceLagsTextField().getText());
	
			selectedFeatures.getExtractedFeatures()[i].put(UnivariateFeatures.PHASE_SPACE_DIM, Integer.toString(phaseSpaceDim));
			selectedFeatures.getExtractedFeatures()[i].put(UnivariateFeatures.PHASE_SPACE_LAG, Integer.toString(phaseSpaceLag));
			RecurrencePlot rp = new RecurrencePlot(series, phaseSpaceDim, phaseSpaceLag, Statistics.standardDeviation(series));
			
			if(selectedFeatures.getFeatures().get(UnivariateFeatures.REC_LAM)){		
				double recLam = rp.calculateLaminarity();
				selectedFeatures.getExtractedFeatures()[i].put(UnivariateFeatures.REC_LAM, Double.toString(recLam));			
			}
			if(selectedFeatures.getFeatures().get(UnivariateFeatures.REC_RATE)){
				double recRate = rp.calculateRecurrenceRate();
				selectedFeatures.getExtractedFeatures()[i].put(UnivariateFeatures.REC_RATE, Double.toString(recRate));								
			}
			if(selectedFeatures.getFeatures().get(UnivariateFeatures.REC_LMEAN)){
				double recLMean = rp.calculateLMean();
				selectedFeatures.getExtractedFeatures()[i].put(UnivariateFeatures.REC_LMEAN, Double.toString(recLMean));
			}
			if(selectedFeatures.getFeatures().get(UnivariateFeatures.REC_DET)){
				double recDet = rp.calculateDET();
				selectedFeatures.getExtractedFeatures()[i].put(UnivariateFeatures.REC_DET, Double.toString(recDet));
			}
			if(selectedFeatures.getFeatures().get(UnivariateFeatures.REC_SHANNON)){
				double recShEn = rp.calculateShannonEntropyRecurrence();
				selectedFeatures.getExtractedFeatures()[i].put(UnivariateFeatures.REC_SHANNON, Double.toString(recShEn));
			}
			if(selectedFeatures.getFeatures().get(UnivariateFeatures.REC_AVG_NEIGHBOURS_NUM)){
				double recAVGNeigh = rp.calculateAVGNumOfNeighbours();
				selectedFeatures.getExtractedFeatures()[i].put(UnivariateFeatures.REC_AVG_NEIGHBOURS_NUM, Double.toString(recAVGNeigh));
			}
		}
		if(selectedFeatures.getFeatures().get(UnivariateFeatures.LLE)){
			int phaseSpaceDim = (int)Double.parseDouble(extractUnivariateFeaturesWindow.getPhaseSpaceFeaturesDialog().getPhaseSpaceDimensionTextField().getText());
//			int phaseSpaceLag = (int)Double.parseDouble(extractUnivariateFeaturesWindow.getPhaseSpaceFeaturesDialog().getPhaseSpaceLagsTextField().getText());
			
			int trajectoryLength;
			if (series.length >= 200){
				trajectoryLength = LyapunovExponent.DEFAULT_TRAJECTORY_LENGTH;
			}
			else {
				trajectoryLength = LyapunovExponent.SMALL_TRAJECTORY_LENGTH; 
			}
			double lle = 0;
			try {
				lle = LyapunovExponent.calculateLargestLyapunovExponentRosenstien(series, phaseSpaceDim, trajectoryLength);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			selectedFeatures.getExtractedFeatures()[i].put(UnivariateFeatures.LLE, Double.toString(lle));
		}
		
		if(selectedFeatures.getFeatures().get(UnivariateFeatures.CORRELATION_DIM)){
			int phaseSpaceDim = (int)Double.parseDouble(extractUnivariateFeaturesWindow.getPhaseSpaceFeaturesDialog().getPhaseSpaceDimensionTextField().getText());
			int phaseSpaceLag = (int)Double.parseDouble(extractUnivariateFeaturesWindow.getPhaseSpaceFeaturesDialog().getPhaseSpaceLagsTextField().getText());
			
			double aMatrix [][] = new double[series.length-(phaseSpaceDim-1)*phaseSpaceLag][phaseSpaceDim];
			for (int g=0; g<aMatrix.length; g++){
				for (int n=1; n <= phaseSpaceDim; n++){
					aMatrix[g][n-1] = series[g+(n-1)*phaseSpaceLag];
				}
			}
			double cd = 0;
			try {
				cd = CorrelationDimension.calculateCorrelationDimension(aMatrix, 100);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			selectedFeatures.getExtractedFeatures()[i].put(UnivariateFeatures.CORRELATION_DIM, Double.toString(cd));
		}
		
		if(selectedFeatures.getFeatures().get(UnivariateFeatures.SFI)){
			int phaseSpaceDim = (int)Double.parseDouble(extractUnivariateFeaturesWindow.getPhaseSpaceFeaturesDialog().getPhaseSpaceDimensionTextField().getText());
			int phaseSpaceLag = (int)Double.parseDouble(extractUnivariateFeaturesWindow.getPhaseSpaceFeaturesDialog().getPhaseSpaceLagsTextField().getText());
			
			int sfiDiv = (int) Double.parseDouble(extractUnivariateFeaturesWindow.getPhaseSpaceFeaturesDialog().getSFITextField().getText());
			
			double aMatrix [][] = new double[series.length-(phaseSpaceDim-1)*phaseSpaceLag][phaseSpaceDim];
			for (int g=0; g<aMatrix.length; g++){
				for (int n=1; n <= phaseSpaceDim; n++){
					aMatrix[g][n-1] = series[g+(n-1)*phaseSpaceLag];
				}
			}
			double xMax = Statistics.maximum(series);
			//sfindices[k] = new SpatialFillingIndex(10,max2,aMatrix).getIndex();
			SpatialFillingIndex s = new SpatialFillingIndex(sfiDiv, xMax, aMatrix);
			double sfi = s.getIndex();
			selectedFeatures.getExtractedFeatures()[i].put(UnivariateFeatures.SFI, Double.toString(sfi));
			
		}
		if(selectedFeatures.getFeatures().get(UnivariateFeatures.SD1_SD2)){
			StandardDeviationRatio sdr = new StandardDeviationRatio(series);
			double sdrRes = sdr.getSD1SD2Ratio();
			selectedFeatures.getExtractedFeatures()[i].put(UnivariateFeatures.SD1_SD2, Double.toString(sdrRes));
		}
		

		if(selectedFeatures.getFeatures().get(UnivariateFeatures.DFA)){
		//ZASAD SAMO SHORT
			double dfaS = 0;
			try {
				dfaS = DFA.calculateDFA(series, false, DFA.DEFAULT_MINIMAL_ANALYZED_SEGMENT_LENGTH, DFA.DEFAULT_BOUND_FOR_ALPHA_LONG_CALCULATION)[0];
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			selectedFeatures.getExtractedFeatures()[i].put(UnivariateFeatures.DFA, Double.toString(dfaS));
		}
		
		if(selectedFeatures.getFeatures().get(UnivariateFeatures.HIGUCHI)){
			int kmax = (int)Double.parseDouble(extractUnivariateFeaturesWindow.getFractalFeaturesDialog().getHiguchiTextField().getText());
			HiguchiDimension h = new HiguchiDimension(series, kmax);
			double higuchiFD = h.getHiguchiDimension();
			selectedFeatures.getExtractedFeatures()[i].put(UnivariateFeatures.HIGUCHI, Double.toString(higuchiFD));
		}
		
		if(selectedFeatures.getFeatures().get(UnivariateFeatures.HURST)){
			double hurstE = HurstExponent.calculateHurstExponent(series);
			selectedFeatures.getExtractedFeatures()[i].put(UnivariateFeatures.HURST, Double.toString(hurstE));
		}

		
		if(selectedFeatures.getFeatures().get(UnivariateFeatures.AP_EN)){
			int mAp = (int) Double.parseDouble(extractUnivariateFeaturesWindow.getEntropiesDialog().getApEnTextField().getText());
			double resApEn = ApEn.calculateApEn(series, mAp, 0.15*Statistics.standardDeviation(series));
			selectedFeatures.getExtractedFeatures()[i].put(UnivariateFeatures.AP_EN, Double.toString(resApEn));
			selectedFeatures.getExtractedFeatures()[i].put(UnivariateFeatures.AP_EN_M_FACTOR, Integer.toString(mAp));
			
//			if(features.map.get(UnivariateFeatures.MAX_AP_EN)){
//				double maxApEn = ApEn.calculateMaxApEn(series, mAp);
//				map.put("Maximum approximate entropy", Double.toString(features.maxApEn[0]));
//			}
		}
		
		if(selectedFeatures.getFeatures().get(UnivariateFeatures.SAMP_EN)){
			int mSamp = (int) Double.parseDouble(extractUnivariateFeaturesWindow.getEntropiesDialog().getSampEnTextField().getText());
			double resSampEn = SampEn.calculateSampEn(series, mSamp, 0.15*Statistics.standardDeviation(series));
			selectedFeatures.getExtractedFeatures()[i].put("Sample entropy", Double.toString(resSampEn));
			selectedFeatures.getExtractedFeatures()[i].put("Sample entropy m factor", Integer.toString(mSamp));
//			if(features.map.get("Maximum sample entropy")){
//				features.maxSampEn = SampEn.calculateMaxSampEn(series, features.mSamp);
//				map.put("Maximum sample entropy", Double.toString(features.maxSampEn[0]));
//			
//			}
		}

		
		if(selectedFeatures.getFeatures().get(UnivariateFeatures.RENYI_EN)){
			int renOrder = (int) Double.parseDouble(extractUnivariateFeaturesWindow.getEntropiesDialog().getRenyiTextField().getText());
			double renyi = RenyiEntropy.calculateRenyiEntropy(series, renOrder);
			selectedFeatures.getExtractedFeatures()[i].put(UnivariateFeatures.RENYI_EN, Double.toString(renyi));
			selectedFeatures.getExtractedFeatures()[i].put(UnivariateFeatures.RENYI_EN_ORDER, Integer.toString(renOrder));
		}
		
		if(selectedFeatures.getFeatures().get(UnivariateFeatures.CORRECTED_CONDITIONAL_SHANNON)){
//			int renOrder = (int) Double.parseDouble(extractUnivariateFeaturesWindow.getEntropiesDialog().getRenyiTextField().getText());
//			double renyi = RenyiEntropy.calculateRenyiEntropy(series, renOrder);
//			map[i].put("Renyi entropy", Double.toString(renyi));
//			map[i].put("Renyi entropy order", Integer.toString(renOrder));
		}
		if (selectedFeatures.getFeatures().get(UnivariateFeatures.FFT)){
			SpectralAnalysis spectAnalysis = new SpectralAnalysis(series, file.calculateFrequency(signal.getSignalIndex()));
			boolean b;
			if (selectedFeatures.getFeatures().get(UnivariateFeatures.HAMMING)){
				b = spectAnalysis.calculateSpectrumFourier(SpectralAnalysis.HAMMING_WINDOW);
			}
			else if (selectedFeatures.getFeatures().get(UnivariateFeatures.HANN)){
				b = spectAnalysis.calculateSpectrumFourier(SpectralAnalysis.HANN_WINDOW);
			}
			else { // no window
				b = spectAnalysis.calculateSpectrumFourier(SpectralAnalysis.NO_WINDOW);
			}
			if (b){
				if (selectedFeatures.getFeatures().get(UnivariateFeatures.ALPHA_PSD)){
					double lowerBound = (int) Double.parseDouble(extractUnivariateFeaturesWindow.getLinearFrequencyFeaturesDialog().getAlphaLowerTextField().getText());
					double upperBound = (int) Double.parseDouble(extractUnivariateFeaturesWindow.getLinearFrequencyFeaturesDialog().getAlphaUpperTextField().getText());
					double alphaPsd = spectAnalysis.getPSDForFrequencyBand(lowerBound, upperBound);
					selectedFeatures.getExtractedFeatures()[i].put(UnivariateFeatures.ALPHA_PSD, Double.toString(alphaPsd));
				}
				if (selectedFeatures.getFeatures().get(UnivariateFeatures.BETA_PSD)){
					double lowerBound = (int) Double.parseDouble(extractUnivariateFeaturesWindow.getLinearFrequencyFeaturesDialog().getBetaLowerTextField().getText());
					double upperBound = (int) Double.parseDouble(extractUnivariateFeaturesWindow.getLinearFrequencyFeaturesDialog().getBetaUpperTextField().getText());
					double betaPsd = spectAnalysis.getPSDForFrequencyBand(lowerBound, upperBound);
					selectedFeatures.getExtractedFeatures()[i].put(UnivariateFeatures.BETA_PSD, Double.toString(betaPsd));
				}
				if (selectedFeatures.getFeatures().get(UnivariateFeatures.GAMMA_PSD)){
					double lowerBound = (int) Double.parseDouble(extractUnivariateFeaturesWindow.getLinearFrequencyFeaturesDialog().getGammaLowerTextField().getText());
					double upperBound = (int) Double.parseDouble(extractUnivariateFeaturesWindow.getLinearFrequencyFeaturesDialog().getGammaUpperTextField().getText());
					double gammaPsd = spectAnalysis.getPSDForFrequencyBand(lowerBound, upperBound);
					selectedFeatures.getExtractedFeatures()[i].put(UnivariateFeatures.GAMMA_PSD, Double.toString(gammaPsd));
				}
				if (selectedFeatures.getFeatures().get(UnivariateFeatures.DELTA_PSD)){
					double lowerBound = (int) Double.parseDouble(extractUnivariateFeaturesWindow.getLinearFrequencyFeaturesDialog().getDeltaLowerTextField().getText());
					double upperBound = (int) Double.parseDouble(extractUnivariateFeaturesWindow.getLinearFrequencyFeaturesDialog().getDeltaUpperTextField().getText());
					double deltaPsd = spectAnalysis.getPSDForFrequencyBand(lowerBound, upperBound);
					selectedFeatures.getExtractedFeatures()[i].put(UnivariateFeatures.DELTA_PSD, Double.toString(deltaPsd));
				}
				if (selectedFeatures.getFeatures().get(UnivariateFeatures.THETA_PSD)){
					double lowerBound = (int) Double.parseDouble(extractUnivariateFeaturesWindow.getLinearFrequencyFeaturesDialog().getThetaLowerTextField().getText());
					double upperBound = (int) Double.parseDouble(extractUnivariateFeaturesWindow.getLinearFrequencyFeaturesDialog().getThetaUpperTextField().getText());
					double thetaPsd = spectAnalysis.getPSDForFrequencyBand(lowerBound, upperBound);
					selectedFeatures.getExtractedFeatures()[i].put(UnivariateFeatures.THETA_PSD, Double.toString(thetaPsd));
				}
				if (selectedFeatures.getFeatures().get(UnivariateFeatures.TOTAL_PSD)){
					double totalPsd = spectAnalysis.getTotalPSD();
					selectedFeatures.getExtractedFeatures()[i].put(UnivariateFeatures.TOTAL_PSD, Double.toString(totalPsd));
				}
			}
		}
	}

	public ExtractMixedFeaturesController getExtractMixedFeaturesController() {
		return extractMixedFeaturesController;
	}

	public void setExtractMixedFeaturesController(
			ExtractMixedFeaturesController extractMixedFeaturesController) {
		this.extractMixedFeaturesController = extractMixedFeaturesController;
	}
}

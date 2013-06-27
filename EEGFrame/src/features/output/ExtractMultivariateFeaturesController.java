/**
 * 
 */
package features.output;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import statisticMeasure.Statistics;

import dataHandling.InputFile;

import features.nonlinear.multiSeries.CrossRecurrence;
import features.nonlinear.multiSeries.MutualDimension;
import features.nonlinear.multiSeries.SynchronizationLikelihood;
import gui.SelectedSignal;
import gui.features.ExtractMultivariateFeaturesWindow;

/**
 * @author lsuc
 *
 */
public class ExtractMultivariateFeaturesController extends
		ExtractFeaturesController {
	/* (non-Javadoc)
	 * @see features.output.ExtractFeaturesController#extractFeatures()
	 */
	
	private ExtractMultivariateFeaturesWindow extractMultivariateFeaturesWindow;
	private ExtractMixedFeaturesController extractMixedFeaturesController;

	public ExtractMultivariateFeaturesController(){
		featuresType = MULTIVARIATE_FEATURES;
		selectedFeatures = new ArrayList<Features>();
		selectedFeatures.add(new MultivariateFeatures());
	}
	
	public synchronized ExtractMultivariateFeaturesWindow getExtractMultivariateFeaturesWindow() {
		if(extractMultivariateFeaturesWindow == null){
			extractMultivariateFeaturesWindow = new ExtractMultivariateFeaturesWindow(this);
//			selectedFeatures = new ArrayList<Features>();
//			selectedFeatures.add(new MultivariateFeatures());
		}
		return extractMultivariateFeaturesWindow;
	}
	
	public void setMultivariateFeaturesSignalsList(SelectedSignal[] signals){
		extractMultivariateFeaturesWindow.setSignalsLabelList(signals);
		extractMultivariateFeaturesWindow.setVisible(true);
	}

	/* (non-Javadoc)
	 * @see features.output.ExtractFeaturesController#extractFeatures()
	 */

	@Override
	public void extractFeatures() {
		MultivariateFeatures features = (MultivariateFeatures) selectedFeatures.get(0);
		for(int k = 0; k < selectedFeatures.size(); k++){
			HashMap<String, String>[] map =  (HashMap<String, String>[])new HashMap[features.getSignals().size()];
			for(int i = 0; i < features.getSignals().size(); i++){
				map[i] = new HashMap<String, String>();
				for(int j = 0; j < features.getOptionsToPrint().size(); j++){
					map[i].put(features.getOptionsToPrint().get(j), "?");
				}
			}	
			selectedFeatures.get(k).setExtractedFeatures(map);
		}

		for(int featuresInterval = 0; featuresInterval < selectedFeatures.size(); featuresInterval++){
			features = (MultivariateFeatures) selectedFeatures.get(featuresInterval);
//			System.out.println("Interval broj " + featuresInterval);
//			System.out.println("interval je " + features.getTimeInterval().get(0)[0] + "- " + features.getTimeInterval().get(0)[1]);
			InputFile file;
			SelectedSignal signal;
			Long[] samples = new Long[2];
			
			if(features.getSampleInterval() != null){	
				samples = features.getSampleInterval().get(0);				
				for(int i = 0; i < features.getSignals().size(); i++){
					long maxSamplesLength = 0;				
					for(int j = 0; j < features.getSignals().get(i).length; j++){						
						signal = features.getSignals().get(i)[j];
						file = signal.getFile();
						long samplesLength = samples[1];
						if(samplesLength == 0){
							samplesLength = file.calculateSignalSamplesNum(signal.getSignalIndex()) - samples[0];
							if(samplesLength > maxSamplesLength){
								maxSamplesLength = samplesLength;
							}
						}
						else{
							samplesLength -= samples[0];
						}
					}
					//MISLIM DA TU MORA BITI featuresInterval umjesto i, popraviti
//					features.getExtractedFeatures()[i].put(MultivariateFeatures.START_SAMPLE, Long.toString(samples[0]));
//					features.getExtractedFeatures()[i].put(MultivariateFeatures.SEGMENT_LENGTH_SAMPLES, Long.toString(maxSamplesLength));
				}				
				
			}
			else if(features.getTimeInterval() != null){
				Double[] time = features.getTimeInterval().get(0);
				
				for(int i = 0; i < features.getSignals().size(); i++){
					double maxTimeLength = 0;
				
					for(int j = 0; j < features.getSignals().get(i).length; j++){	
						signal = features.getSignals().get(i)[j];
						file = signal.getFile();
						samples[0] = file.calculateSampleFromTime(time[0], signal.getSignalIndex());
						if(time[1] == 0.0){
							samples[1] = file.calculateSignalSamplesNum(signal.getSignalIndex());
							time[1] = file.calculateDuration(signal.getSignalIndex())-time[0];
						}
						else{
							samples[1] = Math.min(file.calculateSampleFromTime(time[1], signal.getSignalIndex()), file.calculateSignalSamplesNum(signal.getSignalIndex()));
						}
//						long samplesLength = samples[1];
//						if(samplesLength == 0){
//							samplesLength = file.calculateSignalSamplesNum(signal.getSignalIndex()) - samples[0];
//							if(samplesLength > maxSamplesLength){
//								maxSamplesLength = samplesLength;
//							}
//						}
//						else{
//							samplesLength -= samples[0];
//						}	
					
					}
					features.getExtractedFeatures()[i].put(MultivariateFeatures.START_TIME, Double.toString(time[0]));
					features.getExtractedFeatures()[i].put(MultivariateFeatures.SEGMENT_LENGTH_SEC, Double.toString(maxTimeLength));
				}
			}				
				
			ArrayList<double[][]> signalList = new ArrayList<double[][]>();
			for(int i = 0; i < features.getSignals().size(); i++){
				double[][] signalCombination = new double[features.getSignals().get(i).length][];
				for(int j = 0; j < features.getSignals().get(i).length; j++){
					signal = features.getSignals().get(i)[j];
					file = signal.getFile();	
					int startSample = (int)samples[0].longValue();
					int endSample = (int)samples[1].longValue();
//					System.out.println("izracunati start sample je "+ startSample + " a end sample "+endSample);
					signalCombination[j] = file.getSamplesFromInterval(signal.getSignalIndex(), startSample, endSample);
				}
				signalList.add(signalCombination);
			}
			for(int i = 0; i < signalList.size(); i++){
				calculateMultivariateFeatures(features, signalList.get(i), i);
			}
//			
		}
		
	}
	public void calculateMultivariateFeatures(Features selectedFeatures, double[][] series, int i){	
		if(selectedFeatures.getFeatures().get(MultivariateFeatures.MUTUAL_DIM)){
			int phaseSpaceDim1 = (int)Double.parseDouble(extractMultivariateFeaturesWindow.getNonlinearMultivariateFeaturesDialog().getDimensionTextField().getText());
			int phaseSpaceDim2 = (int)Double.parseDouble(extractMultivariateFeaturesWindow.getNonlinearMultivariateFeaturesDialog().getDimension2TextField().getText());			
			int phaseSpaceLag1 = (int)Double.parseDouble(extractMultivariateFeaturesWindow.getNonlinearMultivariateFeaturesDialog().getLagsTextField().getText());
			int phaseSpaceLag2 = (int)Double.parseDouble(extractMultivariateFeaturesWindow.getNonlinearMultivariateFeaturesDialog().getLags2TextField().getText());

			int finesse = Integer.parseInt(extractMultivariateFeaturesWindow.getNonlinearMultivariateFeaturesDialog().getFinesseTextField().getText());
			double mutualDim = MutualDimension.calculateMutualDimension(series[0], series[1], phaseSpaceDim1, phaseSpaceDim2, phaseSpaceLag1, phaseSpaceLag2, finesse);
//			System.out.println("mutual dim je " + mutualDim + "skup signala u trenutnom intervalu " + i);
			selectedFeatures.getExtractedFeatures()[i].put(MultivariateFeatures.MUTUAL_DIM, Double.toString(mutualDim));
		}
		if(selectedFeatures.getFeatures().get(MultivariateFeatures.CROSS_RECURRENCE)){
			int phaseSpaceDim1 = (int)Double.parseDouble(extractMultivariateFeaturesWindow.getNonlinearMultivariateFeaturesDialog().getDimensionTextField().getText());
			int phaseSpaceDim2 = (int)Double.parseDouble(extractMultivariateFeaturesWindow.getNonlinearMultivariateFeaturesDialog().getDimension2TextField().getText());			
			int phaseSpaceLag1 = (int)Double.parseDouble(extractMultivariateFeaturesWindow.getNonlinearMultivariateFeaturesDialog().getLagsTextField().getText());
			int phaseSpaceLag2 = (int)Double.parseDouble(extractMultivariateFeaturesWindow.getNonlinearMultivariateFeaturesDialog().getLags2TextField().getText());

			CrossRecurrence crpPlot = new CrossRecurrence(series[0], series[1], Math.max(phaseSpaceDim1, phaseSpaceDim2), Math.min(phaseSpaceLag1, phaseSpaceLag2), Math.max(Statistics.standardDeviation(series[0]), Statistics.standardDeviation(series[1])));
//			map[i].put(UnivariateFeatures.PHASE_SPACE_DIM, Integer.toString(phaseSpaceDim));
//			map[i].put(UnivariateFeatures.PHASE_SPACE_LAG, Integer.toString(phaseSpaceLag));

//			if(selectedFeatures.getFeatures().get(UnivariateFeatures.REC_LAM)){		
//				double recLam = rp.calculateLaminarity();
//				map[i].put(UnivariateFeatures.REC_LAM, Double.toString(recLam));			
//			}
//			attractorDim

			if(selectedFeatures.getFeatures().get(MultivariateFeatures.CRP_DET)){
				double crpDet = crpPlot.calculateDET();
				selectedFeatures.getExtractedFeatures()[i].put(MultivariateFeatures.CRP_DET, Double.toString(crpDet));
			}
			if(selectedFeatures.getFeatures().get(MultivariateFeatures.CRP_LAM)){
				double crpLam = crpPlot.calculateLaminarity();
				selectedFeatures.getExtractedFeatures()[i].put(MultivariateFeatures.CRP_LAM, Double.toString(crpLam));
			}
			if(selectedFeatures.getFeatures().get(MultivariateFeatures.CRP_LMEAN)){
				double crpLMean = crpPlot.calculateLMean();
				selectedFeatures.getExtractedFeatures()[i].put(MultivariateFeatures.CRP_LMEAN, Double.toString(crpLMean));
			}
			if(selectedFeatures.getFeatures().get(MultivariateFeatures.CRP_RATE)){
				double crpRate = crpPlot.calculateCC();
				selectedFeatures.getExtractedFeatures()[i].put(MultivariateFeatures.CRP_RATE, Double.toString(crpRate));
			}
			if(selectedFeatures.getFeatures().get(MultivariateFeatures.CRP_SHANNON)){
				double crpShannon = crpPlot.calculateShannonEntropyRecurrence();
				selectedFeatures.getExtractedFeatures()[i].put(MultivariateFeatures.CRP_SHANNON, Double.toString(crpShannon));
			}
		}
//		if(selectedFeatures.getFeatures().get(MultivariateFeatures.SYNCHRO_LIKELIHOOD)){
//			SynchronizationLikelihood.calculateSynchronizationLikelihood(series, startSample, seriesLength, dimension, lag, recNum, roRef, epsMax)
//		}
	}
	public void removeUnknownOptions(){
		ArrayList<Features> f = getSelectedFeatures();
		for(int i = 0; i < f.size(); i++){
			MultivariateFeatures multivariateFeatures = (MultivariateFeatures) f.get(i);
//			System.out.println("velicina options to print za " + i +" je "+ multivariateFeatures.getOptionsToPrintNoParams().size());
			Iterator featuresIt = multivariateFeatures.getFeatures().entrySet().iterator();
		    while (featuresIt.hasNext()) {
		        Map.Entry pairs = (Map.Entry)featuresIt.next();
		        if((Boolean) pairs.getValue() == false){
		        	multivariateFeatures.getOptionsToPrint().remove(pairs.getKey());
		        	multivariateFeatures.getOptionsToPrintNoParams().remove(pairs.getKey());

		        }	

		    }
//        	System.out.println("velicina options to print za " + i +" je "+ multivariateFeatures.getOptionsToPrintNoParams().size());
		}	
	}

	public ArrayList<SelectedSignal[]>  processSubsets(SelectedSignal[] set, int k) {
		ArrayList<SelectedSignal[]> subsets = new ArrayList<SelectedSignal[]>();
		SelectedSignal[] subset = new SelectedSignal[k];
	    processLargerSubsets(set, subset, 0, 0, subsets);
	    
	    return subsets;
	}

	public void processLargerSubsets(SelectedSignal[] set, SelectedSignal[] subset, int subsetSize, int nextIndex, ArrayList<SelectedSignal[]> subsets) {
	    if (subsetSize == subset.length) {
	        process(subset, subsets);
	    } else {
	        for (int j = nextIndex; j < set.length; j++) {
	            subset[subsetSize] = set[j];
	            processLargerSubsets(set, subset, subsetSize + 1, j + 1, subsets);
	        }
	    }
	}
	public void process(SelectedSignal[] subset, ArrayList<SelectedSignal[]> subsets) {
		SelectedSignal[] sub = new SelectedSignal[subset.length];
	    for(int i = 0; i < subset.length; i++){
	    	sub[i] = subset[i];
	    }
	    subsets.add(sub);
	}

	/* (non-Javadoc)
	 * @see features.output.ExtractFeaturesController#createNewExtractFeaturesController()
	 */
	@Override
	public void createNewExtractFeaturesController() {
		ExtractMultivariateFeaturesController controller = new ExtractMultivariateFeaturesController();
		controller.setExtractMixedFeaturesController(extractMixedFeaturesController);
		extractMixedFeaturesController.setExtractMultivariateFeaturesController(controller);
		
	}

	public ExtractMixedFeaturesController getExtractMixedFeaturesController() {
		return extractMixedFeaturesController;
	}

	public void setExtractMixedFeaturesController(
			ExtractMixedFeaturesController extractMixedFeaturesController) {
		this.extractMixedFeaturesController = extractMixedFeaturesController;
	}

}

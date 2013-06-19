/**
 * 
 */
package features.output;

import java.util.ArrayList;
import java.util.HashMap;

import statisticMeasure.Statistics;

import dataHandling.InputFile;

import features.nonlinear.multiSeries.CrossRecurrence;
import features.nonlinear.multiSeries.MutualDimension;
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

	public synchronized ExtractMultivariateFeaturesWindow getExtractMultivariateFeaturesWindow() {
		if(extractMultivariateFeaturesWindow == null){
			extractMultivariateFeaturesWindow = new ExtractMultivariateFeaturesWindow(this);
			selectedFeatures = new ArrayList<Features>();
			selectedFeatures.add(new MultivariateFeatures());
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
		@SuppressWarnings("unchecked")
		HashMap<String, String>[] map =  (HashMap<String, String>[])new HashMap[1];
		features.setExtractedFeatures(map);
	
		map[0] = new HashMap<String, String>();
		for(int j = 0; j < features.getOptionsToPrint().size(); j++){
			map[0].put(features.getOptionsToPrint().get(j), "?");
			map[0].put(MultivariateFeatures.SIGNAL_LABEL, features.getLabel());			
		}
		InputFile file;
		SelectedSignal signal;
		Long[] samples = new Long[2];
		long maxSamplesLength = 0;
		if(features.getSampleInterval() != null){
			samples = features.getSampleInterval().get(0);
		
			for(int i = 0; i < features.getSignals().length; i++){
				signal = features.getSignals()[i];
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
			map[0].put(MultivariateFeatures.START_SAMPLE, Long.toString(samples[0]));
			map[0].put(MultivariateFeatures.SEGMENT_LENGTH_SAMPLES, Long.toString(maxSamplesLength));
		}

		else if(features.getTimeInterval() != null){
			Double[] time = features.getTimeInterval().get(0);
			double maxTimeLength = 0;
			for(int i = 0; i < features.getSignals().length; i++){
				signal = features.getSignals()[i];
				file = signal.getFile();
				samples[0] = file.calculateSampleFromTime(time[0], signal.getSignalIndex());
				if(time[1] == 0.0){
					samples[1] = file.calculateSignalSamplesNum(signal.getSignalIndex());
					time[1] = file.calculateDuration(signal.getSignalIndex())-time[0];
					if(time[1] > maxTimeLength){
						maxTimeLength = time[1];
					}
				}
				else{
					samples[1] = Math.min(file.calculateSampleFromTime(time[1], signal.getSignalIndex()), file.calculateSignalSamplesNum(signal.getSignalIndex()));
					time[1]-=time[0];
				}
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
			
			map[0].put(MultivariateFeatures.START_TIME, Double.toString(time[0]));
			map[0].put(MultivariateFeatures.SEGMENT_LENGTH_SEC, Double.toString(maxTimeLength));
		}
		ArrayList<double[]> signalList = new ArrayList<double[]>();
		for(int i = 0; i < features.getSignals().length; i++){
			int startSample = (int)samples[0].longValue();
			int endSample = (int)samples[1].longValue();
			signal = features.getSignals()[i];
			file = signal.getFile();
			double[] series = file.getSamplesFromInterval(signal.getSignalIndex(), startSample, endSample);
			signalList.add(series);
		}
//		if(selectedFeatures.getFeatures().get(MultivariateFeatures.MUTUAL_DIM)){
//			int phaseSpaceDim1 = (int)Double.parseDouble(extractMultivariateFeaturesWindow.getNonlinearMultivariateFeaturesDialog().getDimensionTextField().getText());
//			int phaseSpaceDim2 = (int)Double.parseDouble(extractMultivariateFeaturesWindow.getNonlinearMultivariateFeaturesDialog().getDimension2TextField().getText());			
//			int phaseSpaceLag1 = (int)Double.parseDouble(extractMultivariateFeaturesWindow.getNonlinearMultivariateFeaturesDialog().getLagsTextField().getText());
//			int phaseSpaceLag2 = (int)Double.parseDouble(extractMultivariateFeaturesWindow.getNonlinearMultivariateFeaturesDialog().getLags2TextField().getText());
//
//			int finesse = Integer.parseInt(extractMultivariateFeaturesWindow.getNonlinearMultivariateFeaturesDialog().getFinesseTextField().getText());
//			double mutualDim = MutualDimension.calculateMutualDimension(signalList.get(0), signalList.get(1), phaseSpaceDim1, phaseSpaceDim2, phaseSpaceLag1, phaseSpaceLag2, finesse)	;
//			map[0].put(MultivariateFeatures.MUTUAL_DIM, Double.toString(mutualDim));
//		}
//		
//		
//		if(selectedFeatures.getFeatures().get(MultivariateFeatures.CROSS_RECURRENCE)){
//			int phaseSpaceDim1 = (int)Double.parseDouble(extractMultivariateFeaturesWindow.getNonlinearMultivariateFeaturesDialog().getDimensionTextField().getText());
//			int phaseSpaceDim2 = (int)Double.parseDouble(extractMultivariateFeaturesWindow.getNonlinearMultivariateFeaturesDialog().getDimension2TextField().getText());			
//			int phaseSpaceLag1 = (int)Double.parseDouble(extractMultivariateFeaturesWindow.getNonlinearMultivariateFeaturesDialog().getLagsTextField().getText());
//			int phaseSpaceLag2 = (int)Double.parseDouble(extractMultivariateFeaturesWindow.getNonlinearMultivariateFeaturesDialog().getLags2TextField().getText());
//
//			CrossRecurrence crpPlot = new CrossRecurrence(signalList.get(0), signalList.get(1), Math.max(phaseSpaceDim1, phaseSpaceDim2), Math.min(phaseSpaceLag1, phaseSpaceLag2), Math.max(Statistics.standardDeviation(signalList.get(0)), Statistics.standardDeviation(signalList.get(1))));
////			map[i].put(UnivariateFeatures.PHASE_SPACE_DIM, Integer.toString(phaseSpaceDim));
////			map[i].put(UnivariateFeatures.PHASE_SPACE_LAG, Integer.toString(phaseSpaceLag));
//
////			if(selectedFeatures.getFeatures().get(UnivariateFeatures.REC_LAM)){		
////				double recLam = rp.calculateLaminarity();
////				map[i].put(UnivariateFeatures.REC_LAM, Double.toString(recLam));			
////			}
////			attractorDim
//
//			if(selectedFeatures.getFeatures().get(MultivariateFeatures.CRP_DET)){
//				double crpDet = crpPlot.calculateDET();
//				map[0].put(MultivariateFeatures.CRP_DET, Double.toString(crpDet));
//			}
//			if(selectedFeatures.getFeatures().get(MultivariateFeatures.CRP_LAM)){
//				double crpLam = crpPlot.calculateLaminarity();
//				map[0].put(MultivariateFeatures.CRP_LAM, Double.toString(crpLam));
//			}
//			if(selectedFeatures.getFeatures().get(MultivariateFeatures.CRP_LMEAN)){
//				double crpLMean = crpPlot.calculateLMean();
//				map[0].put(MultivariateFeatures.CRP_LMEAN, Double.toString(crpLMean));
//			}
//			if(selectedFeatures.getFeatures().get(MultivariateFeatures.CRP_RATE)){
//				double crpRate = crpPlot.calculateCC();
//				map[0].put(MultivariateFeatures.CRP_RATE, Double.toString(crpRate));
//			}
//			if(selectedFeatures.getFeatures().get(MultivariateFeatures.CRP_SHANNON)){
//				double crpShannon = crpPlot.calculateShannonEntropyRecurrence();
//				map[0].put(MultivariateFeatures.CRP_SHANNON, Double.toString(crpShannon));
//			}
//		}
	
		
		
		
	}

}

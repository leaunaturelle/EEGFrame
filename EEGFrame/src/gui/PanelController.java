/**
 * 
 */
package gui;


import java.awt.Dimension;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import dataHandling.InputFile;
import dataHandling.SignalParameterData;
import features.output.ExtractMixedFeaturesController;
import features.output.ExtractMultivariateFeaturesController;
import features.output.ExtractUnivariateFeaturesController;
import gui.features.AnalysisMultivariateParametersDialog;
import gui.features.AnalysisUnivariateParametersDialog;

/**
 * @author lsuc
 *
 */
public class PanelController implements AdjustmentListener {
	private JFrame frame;
	private SignaViewingPanel panel;
	private boolean isStartPoint;
	private boolean isStandardView;
	private ExtractMixedFeaturesController extractMixedFeaturesController;
//	private ExtractUnivariateFeaturesController extractionUnivariateController;
//	private ExtractMultivariateFeaturesController extractionMultivariateController;
	
	public PanelController(JFrame frame, SignaViewingPanel panel, ExtractMixedFeaturesController extractionMixedFeaturesController){
		this.frame = frame;
		this.panel = panel;	;
		this.extractMixedFeaturesController = extractionMixedFeaturesController;
//		this.extractionUnivariateController = extractionUnivariateController;
//		this.extractionMultivariateController = extractionMultivariateController;
		panel.setController(this);
	}
	
	public double calculateSignalXScale(int horizontalWindowSize, SelectedSignal signal, ViewingParameters parameters){
		int signalIndex = signal.getSignalIndex();
		InputFile file = signal.getFile();
		long dataRecordSamplesNum = file.getMetadata().getSignalParameters()[signalIndex].getSamplesInDataRecordNum();
		double secondsInDataRecord = file.getMetadata().getDataRecordDurationInSec();
		double secondsPerDisplay = parameters.getSecondsPerDisplay();		
		double xScale = horizontalWindowSize / (dataRecordSamplesNum * secondsPerDisplay / secondsInDataRecord);
		return xScale;
	 }
	
	public void calculateSignalsXScales(int horizontalWindowSize, ViewingParameters parameters){
		double[] signalsXScales = new double[parameters.getSignalsForViewing().size()];
		for(int i = 0; i < parameters.getSignalsForViewing().size(); i++){
			signalsXScales[i] = calculateSignalXScale(horizontalWindowSize, parameters.getSignalsForViewing().get(i), parameters);
		}
		parameters.setSignalXScale(signalsXScales);
	}		
	
	public double calculateSignalYScale(SelectedSignal signal, ViewingParameters parameters){
		int signalIndex = signal.getSignalIndex();
		InputFile file = signal.getFile();	
		SignalParameterData signalParameter = file.getMetadata().getSignalParameters()[signalIndex];
		double physicalMin = signalParameter.getPhysicalMin();
		double physicalMax = signalParameter.getPhysicalMax();		
		double signalWindowsHeight = SignaViewingPanel.Y_VALUE * (physicalMax - physicalMin)/parameters.getAmplitude();		
		double signalYScale = signalWindowsHeight / (signalParameter.getPhysicalMax() - signalParameter.getPhysicalMin()); 
		return signalYScale;
	}
	
	public void calculateSignalsYScales(ViewingParameters parameters){
		double[] signalsYScales = new double[parameters.getSignalsForViewing().size()];
		for(int i = 0; i < parameters.getSignalsForViewing().size(); i++){
			signalsYScales[i] = calculateSignalYScale(parameters.getSignalsForViewing().get(i), parameters);
		}
		parameters.setSignalYScale(signalsYScales);
	}

	public void calculateVerticalLinesPos(ViewingParameters parameters){
		if(parameters.getLinesPerDisplayNum() > 0 && !isStandardView){
			ArrayList<Double> xCoordinates = new ArrayList<Double>();
			int k = 1;
			double space = (double)getVisiblePanelWidth() / parameters.getLinesPerDisplayNum();
			for(double i = space; i < parameters.getDimension().getWidth(); i += space, k++){
				xCoordinates.add(i-1);
				if(k % parameters.getLinesPerDisplayNum() == 0){
					i = (k / parameters.getLinesPerDisplayNum()) * getVisiblePanelWidth();
				}
	    	}
			parameters.setVerticalLineXcoordinates(xCoordinates);
		}
		else if(isStandardView){
			ArrayList<Double> xCoordinates = new ArrayList<Double>();
			int k = 1;
			double linesPerDisplayNum =  parameters.getSecondsPerDisplay()/ViewingParameters.STANDARD_VIEW_DISTANCE;
			double space = getVisiblePanelWidth() / linesPerDisplayNum;
			for(double i = space; i < parameters.getDimension().getWidth(); i += space, k++){
				xCoordinates.add(i-1);
				if(k % linesPerDisplayNum == 0){
					i = (k / linesPerDisplayNum) * getVisiblePanelWidth();
				}
	    	}
			parameters.setVerticalLineXcoordinates(xCoordinates);
		}
	}
	public static String getFormatted(double secondsInterval){
		int hours = 0;
		int minutes = 0;
		int seconds = 0;
		int milliseconds = 0;
				
		String[] list = Double.toString(secondsInterval).split("\\D");
		
		seconds = Integer.parseInt(list[0]);	
		milliseconds = (int)Math.round(Double.parseDouble("0."+list[1])*1000);
    	
		
		if(seconds >= 60){
			
			minutes += (int)seconds/60;
			seconds %= 60;
		}
		
		if(minutes >= 60){
			
			hours += (int)minutes/60;
			minutes %= 60;
		}
		
		hours %= 24;	    
		
		String formattedTime = String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds);
		return formattedTime;
	}
	
	public int getVisiblePanelHeight(){
		return getScrollPane().getViewport().getHeight();	
	}
	
	public int getVisiblePanelWidth(){
		return getScrollPane().getViewport().getWidth();	
	}
	

	
	public double calculateSignalWidth(SelectedSignal signal, double signalXScale){  
		double signalWidth = signal.getFile().calculateSignalSamplesNum(signal.getSignalIndex()) * signalXScale;
		return signalWidth;
    }
	
	public JScrollPane getScrollPane(){
		return(JScrollPane) frame.getContentPane().getComponent(0);
	}
	
	public JFrame getFrame() {
		return frame;
	}
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	public SignaViewingPanel getPanel() {
		return panel;
	}
	public void setPanel(SignaViewingPanel panel) {
		this.panel = panel;
	}
	
	public void setSignalsForViewing(ArrayList<SelectedSignal> signals) {
		if(signals == null){
			reset();
		}
		else{
			panel.getParameters().setSignalsForViewing(signals);
			panel.getParameters().setSignalsToViewNum(signals.size());
			createPanelParametersWorker();
		}
	}
	public Dimension calculatePreferredSize(ViewingParameters parameters){		
		if(parameters.getSignalsToViewNum() > 0){	
        	calculateSignalsXScales(getVisiblePanelWidth(), parameters); 
        	double maxWidth = calculateSignalWidth(parameters.getSignalsForViewing().get(0), parameters.getSignalXScale()[0]);
        	for(int i = 1; i < parameters.getSignalsToViewNum(); i++){
        		double width = calculateSignalWidth(parameters.getSignalsForViewing().get(i), parameters.getSignalXScale()[i]);
        		if(width > maxWidth){
        			maxWidth = width;
        		}
        	}
        	double ySize = getVisiblePanelHeight();
  		  	if(parameters.getSignalsToViewNum() > parameters.getMaxSignalNumPerDisplay() && !isStandardView){
  		  		ySize = (parameters.getSignalsToViewNum()/(double)parameters.getMaxSignalNumPerDisplay())* getVisiblePanelHeight();
  		  	}
  		  	
			return new Dimension((int)Math.round(maxWidth), (int)Math.round(ySize)); 
		}
		else
			return new Dimension(0,0);	
	}
	
	public boolean isStandardView() {
		return isStandardView;
	}

	public void setStandardView(boolean isStandardView) {
		this.isStandardView = isStandardView;
		panel.getParameters().setyScaleFactor(1);
		
	}

	public void createPanelParametersWorker(){
		if(panel.getParameters().getSignalsToViewNum() > 0){
			new PanelParametersWorker(panel.getParameters(), this).execute();
		}
		else{
			panel.getParameters().setDimension(calculatePreferredSize(panel.getParameters()));
			panel.revalidate();
		}
	}
	
	public void reset(){
		panel.getParameters().clear();
		panel.repaint();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.AdjustmentListener#adjustmentValueChanged(java.awt.event.AdjustmentEvent)
	 */
	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		if(!e.getValueIsAdjusting()){
			panel.repaint();
		}
	}
	
	public void createHandleSelectionDialog(double x){
		Object[] options = {"Yes",
                "No, try again",
                "Cancel"};
		int value  = JOptionPane.showOptionDialog(null, "Are you sure you want to save this selection?", "Selection dialog", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
		
		if (value != JOptionPane.NO_OPTION) {
			if(value == JOptionPane.YES_OPTION){
				AnalysisUnivariateParametersDialog analysisUnivariateDialog = extractMixedFeaturesController.getExtractUnivariateFeaturesController().getExtractUnivariateFeaturesWindow().getAnalysisParametersDialog();
				AnalysisMultivariateParametersDialog  analysisMultivariateDialog = extractMixedFeaturesController.getExtractMultivariateFeaturesController().getExtractMultivariateFeaturesWindow().getAnalysisMultivariateParametersDialog();
				if(isStartPoint){
					analysisUnivariateDialog.setStartTime(Double.toString(calculateTimeFromCoordinate(x)));
					analysisMultivariateDialog.setStartTime(Double.toString(calculateTimeFromCoordinate(x)));
				}
				else{
					analysisUnivariateDialog.setSegmentLengthTime(Double.toString(calculateTimeSegmentLengthFromEndCoordinate(x, analysisUnivariateDialog)));
					analysisMultivariateDialog.setSegmentLengthTime(Double.toString(calculateTimeSegmentLengthFromEndCoordinate(x, analysisUnivariateDialog)));
				}
			}
			panel.removeMouseListener(panel.getMouseListener());
			panel.setMouseListener(null);
			panel.setCursor(null);
		}
		panel.setxSelection(0);
		panel.repaint();
	}
	
	public double calculateTimeFromCoordinate(double x){
		double time = (x/(getVisiblePanelWidth()-1)) * panel.getParameters().getSecondsPerDisplay();
		return time;
	}
	
	public double calculateTimeSegmentLengthFromEndCoordinate(double x, AnalysisUnivariateParametersDialog analysisDialog){
		double start = Double.parseDouble(analysisDialog.getStartTime());
		double end = (x/(getVisiblePanelWidth()-1)) * panel.getParameters().getSecondsPerDisplay();
		if(end < start){
			analysisDialog.setStartTime("0");
			return end;
		}
		return (end-start);
	}
	public double calculateSampleFromCoordinate(double x, int index){
		double sample = (x/panel.getParameters().getSignalXScale()[index]);
		return sample;
	}
	
	
	public ArrayList<SelectedSignal> getSignalsForViewing(){
		return this.panel.getParameters().getSignalsForViewing();
	}

	public boolean isStartPoint() {
		return isStartPoint;
	}

	public void setStartPoint(boolean isStartPoint) {
		this.isStartPoint = isStartPoint;
	}

}

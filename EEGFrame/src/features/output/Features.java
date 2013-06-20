/**
 * 
 */
package features.output;

import java.util.ArrayList;
import java.util.HashMap;

import gui.SelectedSignal;

/**
 * @author lsuc
 *
 */
public abstract class Features {
//	protected String featuresType;
	protected ArrayList<SelectedSignal[]> signals;
	
	protected ArrayList<Long[]> sampleInterval;
	protected ArrayList<Double[]> timeInterval;
	protected ArrayList<String> optionsToPrint;
	protected ArrayList<String> optionsToPrintNoParams;
	protected HashMap<String, Boolean> features = new HashMap<String, Boolean>();
//	protected HashMap<String, Boolean> featuresNoParams = new HashMap<String, Boolean>();
	public ArrayList<String> getOptionsToPrintNoParams() {
		return optionsToPrintNoParams;
	}

	public void setOptionsToPrintNoParams(ArrayList<String> optionsToPrintNoParams) {
		this.optionsToPrintNoParams = optionsToPrintNoParams;
	}

//	public HashMap<String, Boolean> getFeaturesNoParams() {
//		return featuresNoParams;
//	}
//
//	public void setFeaturesNoParams(HashMap<String, Boolean> featuresNoParams) {
//		this.featuresNoParams = featuresNoParams;
//	}

	protected HashMap<String, String>[] extractedFeatures;
	
	public ArrayList<String> getOptionsToPrint() {
		return optionsToPrint;
	}

	public void setOptionsToPrint(ArrayList<String> optionsToPrint) {
		this.optionsToPrint = optionsToPrint;
	}
	public abstract String getLabel();

//	public String getFeaturesType() {
//		return featuresType;
//	}
//
//	public void setFeaturesType(String featuresType) {
//		this.featuresType = featuresType;
//	}

	public HashMap<String, Boolean> getFeatures() {
		return features;
	}

	public void setFeatures(HashMap<String, Boolean> features) {
		this.features = features;
	}

	public ArrayList<Long[]> getSampleInterval() {
		return sampleInterval;
	}

	public void setSampleInterval(ArrayList<Long[]> interval) {
		this.sampleInterval = interval;
	}

	public HashMap<String, String>[] getExtractedFeatures() {
		return extractedFeatures;
	}

	public void setExtractedFeatures(HashMap<String, String>[] extractedFeatures) {
		this.extractedFeatures = extractedFeatures;
	}
	
	public ArrayList<Double[]> getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(ArrayList<Double[]> timeInterval) {
		this.timeInterval = timeInterval;
	}
	public ArrayList<SelectedSignal[]> getSignals() {
		return signals;
	}

	public void setSignals(ArrayList<SelectedSignal[]> signals) {
		this.signals = signals;
	}

	
	public static final String FILE_LABEL = "File_label";
	public static final String SIGNAL_LABEL = "Signal_label";
	public static final String START_TIME = "Start_time";
	public static final String SEGMENT_LENGTH_SEC = "Segment_length_seconds";
	public static final String START_SAMPLE = "Start_sample";
	public static final String SEGMENT_LENGTH_SAMPLES = "Segment_length_samples";
	public static final String PHASE_SPACE_DIM = "Phase_space_dim";
	public static final String PHASE_SPACE_LAG = "Phase_space_lag(s)";
}

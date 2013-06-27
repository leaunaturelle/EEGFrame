/**
 * 
 */
package features.output;

import gui.SelectedSignal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author lsuc
 *
 */
public class MultivariateFeatures extends Features {

	public MultivariateFeatures(){
//		featuresType = "MultivariateFeatures";
		signals = new ArrayList<SelectedSignal[]>();
		features.put(MUTUAL_DIM, false);
		features.put(SYNCHRO_LIKELIHOOD, false);
		features.put(CROSS_RECURRENCE, false);
		features.put(CRP_DET, false);
		features.put(CRP_LAM, false);
		features.put(CRP_LMEAN, false);
		features.put(CRP_RATE, false);
		features.put(CRP_SHANNON, false);
		
		createOptionsToPrint();
		createOptionsToPrintNoParams();
	}
	
	public void createOptionsToPrint(){
		optionsToPrint = new ArrayList<String>(); 
		optionsToPrint.add(FILE_LABEL);
		optionsToPrint.add(SIGNAL_LABEL);
		optionsToPrint.add(START_TIME);
		optionsToPrint.add(SEGMENT_LENGTH_SEC);
		optionsToPrint.add(START_SAMPLE);
		optionsToPrint.add(SEGMENT_LENGTH_SAMPLES);
		optionsToPrint.add(PHASE_SPACE_DIM);
		optionsToPrint.add(PHASE_SPACE_LAG);
		optionsToPrint.add(MUTUAL_DIM);
		optionsToPrint.add(MUTUAL_DIM_FINESSE);
		optionsToPrint.add(SYNCHRO_LIKELIHOOD_LABEL);
		optionsToPrint.add(SYNCHRO_LIKELIHOOD_VALUE);
		optionsToPrint.add(CRP_DET);
		optionsToPrint.add(CRP_LAM);
		optionsToPrint.add(CRP_LMEAN);
		optionsToPrint.add(CRP_RATE);
		optionsToPrint.add(CRP_SHANNON);
	}
	public void createOptionsToPrintNoParams(){
		optionsToPrintNoParams = new ArrayList<String>(); 
		optionsToPrintNoParams.add(MUTUAL_DIM);
		optionsToPrintNoParams.add(CRP_DET);
		optionsToPrintNoParams.add(CRP_LAM);
		optionsToPrintNoParams.add(CRP_LMEAN);
		optionsToPrintNoParams.add(CRP_RATE);
		optionsToPrintNoParams.add(CRP_SHANNON);
		optionsToPrint.add(SYNCHRO_LIKELIHOOD);
	}
	/* (non-Javadoc)
	 * @see features.output.Features#getLabel()
	 */
	@Override
	public String getLabel() {
//		StringBuilder labelBuilder = new StringBuilder();
//		for(int i = 0; i < signals.length; i++){
//			labelBuilder.append(getLabel(i));
//		}
//		return labelBuilder.toString();
		return "";
	}
	
	public String getLabel(int index) {
//		return signals[index].getSignalLabel();
		return "";
	}

	public static final String MUTUAL_DIM = "Mutual_dim";
	public static final String MUTUAL_DIM_FINESSE = "Mutual_dim_finesse";
	public static final String SYNCHRO_LIKELIHOOD = "SYNCHRO_LIKELIHOOD";
	public static final String CROSS_RECURRENCE = "CROSS_RECURRENCE";
	public static final String CRP_RATE = "CRP_rate";
	public static final String CRP_LMEAN = "CRP_LMean";
	public static final String CRP_DET = "CRP_DET";
	public static final String CRP_SHANNON = "CRP_ShannonEn";
	public static final String CRP_LAM = "CRP_Laminarity";
	public static final String SYNCHRO_LIKELIHOOD_LABEL = "Synchronization_likelihood_label";
	public static final String SYNCHRO_LIKELIHOOD_VALUE = "Synchronization_likelihood_value";
	
	static final String[] SET_VALUES = new String[] {MUTUAL_DIM, CRP_RATE, CRP_LMEAN, CRP_DET, CRP_SHANNON, CRP_LAM};
	public static final Set<String> MULTIVARIATE_SET = new HashSet<String>(Arrays.asList(SET_VALUES));
}

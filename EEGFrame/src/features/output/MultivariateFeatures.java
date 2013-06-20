/**
 * 
 */
package features.output;

import java.util.ArrayList;

/**
 * @author lsuc
 *
 */
public class MultivariateFeatures extends Features {

	public MultivariateFeatures(){
//		featuresType = "MultivariateFeatures";
		features.put(MUTUAL_DIM, false);
		features.put(SYNCHRO_LIKELIHOOD, false);
		features.put(CROSS_RECURRENCE, false);
		features.put(CRP_DET, false);
		features.put(CRP_LAM, false);
		features.put(CRP_LMEAN, false);
		features.put(CRP_RATE, false);
		features.put(CRP_SHANNON, false);
		
		createOptionsToPrint();
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
		return "HOHOHO";
	}
	
	public String getLabel(int index) {
//		return signals[index].getSignalLabel();
		return "Santa_baby";
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
}

/**
 * 
 */
package features.output;

import java.util.ArrayList;

/**
 * @author lsuc
 *
 */
public class UnivariateFeatures extends Features {

//	private int index;

	
	public UnivariateFeatures(){
//		featuresType = "UnivariateFeatures";
		features.put(MEAN, false);
		features.put(STANDARD_DEVIATION, false);
		features.put(MEAN_OF_ABS_VALUES_OF_FIRST_DIFFERENCES_RAW, false);
		features.put(MEAN_OF_ABS_VALUES_OF_SECOND_DIFFERENCES_RAW, false);		
		features.put(MEAN_OF_ABS_VALUES_OF_FIRST_DIFFERENCES_NORMALIZED, false);
		features.put(MEAN_OF_ABS_VALUES_OF_SECOND_DIFFERENCES_NORMALIZED, false);
		features.put(AUTOCORRELATION_COEF, false);
		features.put(AP_EN, false);
		features.put(MAX_AP_EN, false);
		features.put(SAMP_EN, false);
		features.put(MAX_SAMP_EN, false);		
		features.put(RENYI_EN, false);
		features.put(CORRECTED_CONDITIONAL_SHANNON, false);
		features.put(DFA, false);
		features.put(HIGUCHI, false);
		features.put(HURST, false);
		features.put(SFI, false);
		features.put(SD1_SD2, false);
		features.put(CORRELATION_DIM, false);
		features.put(LLE, false);
		features.put(FFT, false);
		features.put(HAMMING, false);
		features.put(HANN, false);
		features.put(ALPHA_PSD, false);
		features.put(BETA_PSD, false);
		features.put(GAMMA_PSD, false);
		features.put(DELTA_PSD, false);
		features.put(THETA_PSD, false);
		features.put(TOTAL_PSD, false);
		features.put(RECURRENCE_PLOT_FEATURES, false);
		features.put(REC_RATE, false);
		features.put(REC_LMEAN, false);
		features.put(REC_DET, false);
		features.put(REC_SHANNON, false);
		features.put(REC_LAM, false);
		features.put(REC_AVG_NEIGHBOURS_NUM, false);
		features.put(LEMPEL_ZIV, false);
		features.put(ALLAN_FACTOR, false);
		features.put(NONLINEAR_FORECASTING, false);
		
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
		optionsToPrint.add(MEAN);
		optionsToPrint.add(MEAN_OF_ABS_VALUES_OF_FIRST_DIFFERENCES_RAW);
		optionsToPrint.add(MEAN_OF_ABS_VALUES_OF_SECOND_DIFFERENCES_RAW);
		optionsToPrint.add(MEAN_OF_ABS_VALUES_OF_FIRST_DIFFERENCES_NORMALIZED);
		optionsToPrint.add(MEAN_OF_ABS_VALUES_OF_SECOND_DIFFERENCES_NORMALIZED);
		optionsToPrint.add(STANDARD_DEVIATION);
		optionsToPrint.add(AUTOCORRELATION_COEF);
		optionsToPrint.add(AP_EN);
		optionsToPrint.add(AP_EN_M_FACTOR);
		optionsToPrint.add(MAX_AP_EN);
		optionsToPrint.add(SAMP_EN);
		optionsToPrint.add(SAMP_EN_M_FACTOR);
		optionsToPrint.add(MAX_SAMP_EN);
		optionsToPrint.add(RENYI_EN);
		optionsToPrint.add(RENYI_EN_ORDER);
		optionsToPrint.add(CORRECTED_CONDITIONAL_SHANNON);
		optionsToPrint.add(DFA);
		optionsToPrint.add(HIGUCHI);
		optionsToPrint.add(HURST);
		optionsToPrint.add(SFI);
		optionsToPrint.add(SD1_SD2);
		optionsToPrint.add(CORRELATION_DIM);
		optionsToPrint.add(LLE);
		optionsToPrint.add(ALPHA_PSD);
		optionsToPrint.add(BETA_PSD);
		optionsToPrint.add(GAMMA_PSD);
		optionsToPrint.add(DELTA_PSD);
		optionsToPrint.add(THETA_PSD);
		optionsToPrint.add(TOTAL_PSD);
		optionsToPrint.add(REC_RATE);
		optionsToPrint.add(REC_LMEAN);
		optionsToPrint.add(REC_DET);
		optionsToPrint.add(REC_SHANNON);
		optionsToPrint.add(REC_LAM);
		optionsToPrint.add(REC_AVG_NEIGHBOURS_NUM);
		optionsToPrint.add(LEMPEL_ZIV);
		optionsToPrint.add(ALLAN_FACTOR);
		optionsToPrint.add(NONLINEAR_FORECASTING);
		optionsToPrint.add(FFT_WINDOW);
		optionsToPrint.add(FFT);
	}
	
	public void createOptionsToPrintNoParams(){
		optionsToPrintNoParams = new ArrayList<String>(); 
		optionsToPrintNoParams.add(MEAN);
		optionsToPrintNoParams.add(MEAN_OF_ABS_VALUES_OF_FIRST_DIFFERENCES_RAW);
		optionsToPrintNoParams.add(MEAN_OF_ABS_VALUES_OF_SECOND_DIFFERENCES_RAW);
		optionsToPrintNoParams.add(MEAN_OF_ABS_VALUES_OF_FIRST_DIFFERENCES_NORMALIZED);
		optionsToPrintNoParams.add(MEAN_OF_ABS_VALUES_OF_SECOND_DIFFERENCES_NORMALIZED);
		optionsToPrintNoParams.add(STANDARD_DEVIATION);
		optionsToPrintNoParams.add(AUTOCORRELATION_COEF);
		optionsToPrintNoParams.add(AP_EN);
		optionsToPrintNoParams.add(MAX_AP_EN);
		optionsToPrintNoParams.add(SAMP_EN);
		optionsToPrintNoParams.add(MAX_SAMP_EN);
		optionsToPrintNoParams.add(RENYI_EN);
		optionsToPrintNoParams.add(CORRECTED_CONDITIONAL_SHANNON);
		optionsToPrintNoParams.add(DFA);
		optionsToPrintNoParams.add(HIGUCHI);
		optionsToPrintNoParams.add(HURST);
		optionsToPrintNoParams.add(SFI);
		optionsToPrintNoParams.add(SD1_SD2);
		optionsToPrintNoParams.add(CORRELATION_DIM);
		optionsToPrintNoParams.add(LLE);
		optionsToPrintNoParams.add(ALPHA_PSD);
		optionsToPrintNoParams.add(BETA_PSD);
		optionsToPrintNoParams.add(GAMMA_PSD);
		optionsToPrintNoParams.add(DELTA_PSD);
		optionsToPrintNoParams.add(THETA_PSD);
		optionsToPrintNoParams.add(TOTAL_PSD);
		optionsToPrintNoParams.add(REC_RATE);
		optionsToPrintNoParams.add(REC_LMEAN);
		optionsToPrintNoParams.add(REC_DET);
		optionsToPrintNoParams.add(REC_SHANNON);
		optionsToPrintNoParams.add(REC_LAM);
		optionsToPrintNoParams.add(REC_AVG_NEIGHBOURS_NUM);
		optionsToPrintNoParams.add(LEMPEL_ZIV);
		optionsToPrintNoParams.add(ALLAN_FACTOR);
		optionsToPrintNoParams.add(NONLINEAR_FORECASTING);
	}
	
	@Override
	public String getLabel() {
		return null;
//		return signals[index].getSignalLabel();
	}

//	public int getIndex() {
//		return index;
//	}
//	public void setIndex(int index) {
//		this.index = index;
//	}

	public static final String MEAN = "Mean";
	public static final String STANDARD_DEVIATION = "Std_dev";
	public static final String MEAN_OF_ABS_VALUES_OF_FIRST_DIFFERENCES_RAW = "Mean_of_abs_values_first_differences_raw";
	public static final String MEAN_OF_ABS_VALUES_OF_SECOND_DIFFERENCES_RAW = "Mean_of_abs_values_second_differences_raw";
	public static final String MEAN_OF_ABS_VALUES_OF_FIRST_DIFFERENCES_NORMALIZED = "Mean_of_abs_values_first_differences_normalized";
	public static final String MEAN_OF_ABS_VALUES_OF_SECOND_DIFFERENCES_NORMALIZED = "Mean_of_abs_values_second_differences_normalized";
	public static final String AUTOCORRELATION_COEF = "Autocorrelation_coef";
	public static final String AP_EN = "ApEn";
	public static final String AP_EN_M_FACTOR = "ApEn_m_factor";
	public static final String MAX_AP_EN = "MaxApEn";
	public static final String SAMP_EN = "SampEn";
	public static final String SAMP_EN_M_FACTOR = "SampEn_m_factor";
	public static final String MAX_SAMP_EN = "MaxSampEn";
	public static final String RENYI_EN = "RenyiEn";
	public static final String RENYI_EN_ORDER = "RenyiEn_order";
	public static final String CORRECTED_CONDITIONAL_SHANNON = "Corrected_conditional_Shannon";
	public static final String DFA = "DFA";
	public static final String HIGUCHI = "Higuchi";
	public static final String HURST = "Hurst_exp";
	public static final String SFI = "Spatial_filling_index" ;
	public static final String CTM = "CTM"; 
	public static final String SD1_SD2 = "SD1/SD2";
	public static final String CORRELATION_DIM = "Correlation_dim";
	public static final String LLE = "Largest_Lyapunov_exp";
	public static final String ALPHA_PSD = "Alpha_PSD";
	public static final String BETA_PSD = "Beta_PSD";
	public static final String GAMMA_PSD = "Gamma_PSD";
	public static final String DELTA_PSD = "Delta_PSD";
	public static final String THETA_PSD = "Theta_PSD";
	public static final String TOTAL_PSD = "Total_PSD";
	public static final String RECURRENCE_PLOT_FEATURES = "RECURRENCE_PLOT_FEATURES";
	public static final String REC_RATE = "Recurrence_rate";
	public static final String REC_LMEAN = "Recurrence_LMean";
	public static final String REC_DET = "Recurrence_DET";
	public static final String REC_SHANNON = "Recurrence_ShannonEn";
	public static final String REC_LAM = "Recurrence_Laminarity";
	public static final String REC_AVG_NEIGHBOURS_NUM = "Recurrence_AVG_neighbour_num";
	public static final String LEMPEL_ZIV = "Lempel_Ziv_complexity";
	public static final String ALLAN_FACTOR = "Allan_factor";
	public static final String NONLINEAR_FORECASTING = "Nonlinear_forecasting";
	public static final String FFT = "FFT";
	public static final String HANN = "HANN";
	public static final String HAMMING = "HAMMING";
	public static final String FFT_WINDOW = "FFT_window";
	public static final String BURG = "Burg_autoregressive_model";
	public static final String HAAR_WAVELET = "Haar_Wavelet_std_dev";
	public static final String HILBERT_HUANG = "Hilbert_Huang";
	
	
//	options.add("Carnap entropy 1D");
//	options.add("Spectral analysis")

}

package features.timeFrequency;

import statisticMeasure.Statistics;
/**
 * The class calculated standard deviation of haar wavelet transform as proposed by Teich et al.
 * "Heart rate variability, measures and models", 2001.
 * 
 * General formula:
 * 
 * HWSTDEV(m) = sqrt( 1/N-1*(sum(i=1,i<=N)(W(m,i)-average(W(m)))^2)),
 * 
 * where W(m)= 1/sqrt(m)*sum(j=0,j<=L-1)(RR[j]*H((j/m)-n)),
 * 
 * H(x) = {+1, 0<x<m/2
 *         -1, m/2<x<m
 *          0, otherwise 
 * 
 * We simplify the method for Haar wavelet as proposed by Teich to calculate W(m,i):
 * 
 * W(m,i) = 1/sqrt(m)*(sum(i,i=i+m/2)(RR[i])-sum(i=i+m/2;i<m)(RR[i]))
 * 
 * Wavelet transform of scale m=32 and a large number of RR intervals (32000+) has been shown to be a perfect indicator for normal / CHF patient distinction
 * A special case where scale=2 yields RMSSD
 * 
 * @author Alan Jovic
 *
 */
public class HaarWaveletStandardDeviation {
	public static final double calculateHaarWaveletSTDEV(double [] segment, int scale) throws Exception{
		int intervals = segment.length / scale;
		double [] waves = new double[intervals];
		
		if (intervals<=2){
			throw new Exception("Too few wavelet intervals!");
		}
		if (scale<2){
			throw new Exception("Too small scale!");
		}
		if (scale % 2==0){
			for (int i=0; i<intervals; i++){
				for (int j=0; j<scale/2; j++){
					waves[i] += segment[i*scale+j];
				}
				for (int j=scale/2; j<scale; j++){
					waves[i] -= segment[i*scale+j];
				}
			}
		}
		else {
			for (int i=0; i<intervals; i++){
				for (int j=0; j<scale/2; j++){
					waves[i] += segment[i*scale+j];
				}
				for (int j=1+scale/2; j<scale; j++){
					waves[i] -= segment[i*scale+j];
				}
			}
		}
		return Statistics.standardDeviation(waves);
	}
}
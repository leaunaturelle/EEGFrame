package features.nonlinear.other;


import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import statisticMeasure.DistanceMeasure;

public class NonlinearForecasting {
//G. Sugihara and R. M. May, “Non-linear forecasting as a way of distinguishing chaos from measurement error in time series,” Nature, vol. 344, pp. 734–740, 1990.
	
	public static double calculateNonlinearForecasting(double [] segment, int dimension, int lag, int T) throws Exception{
		int size = segment.length/2;
		double learningSet [] = new double[size];
		double testSet [] = new double[size];
		double xMatrix[][] = new double[size-(dimension-1)*lag][dimension], yMatrix[][] = new double[size-(dimension-1)*lag][dimension]; 
		//double yFutureMatrix[][] = new double[segment.length-(dimension-1)*lag][dimension];
		
		
		for(int i = 0; i < size; i++){
			learningSet[i] = segment[i]; 
			testSet[i] = segment[size+i];
		}
		
		
		//REKONSTRUKCIJA ATRAKTORA
		for(int g = 0; g < size-(dimension-1)*lag; g++){
			for (int n=1; n<=dimension; n++){
				yMatrix[g][n-1] = learningSet[g+(n-1)*lag];
				xMatrix[g][n-1] = testSet[g+(n-1)*lag];
			}
			
		}
//		double [][] neighbours = locateNearestNeighbours(xMatrix[0], yMatrix, dimension);
//		double [] weights = calculateWeights(xMatrix[0], neighbours);
//		double [] futureX = predictValue(weights, yFutureMatrix, T);
		return 0;
	}
	
	
	public static double[][] locateNearestNeighbours(double [] point, double yMatrix [][], int dimension){
		double[][] neighbours = new double[dimension+1][dimension];

		Map<Integer, Double> map = new HashMap<Integer, Double>();
		
		for(int i = 0; i < yMatrix.length; i++){
			double m = DistanceMeasure.euclideanDistance(point, yMatrix[i]);
			map.put(i, m);
			
		}
		map = MapUtil.sortByValue(map);
		
		Object[] s = map.keySet().toArray();
			
		for(int i = 0; i < dimension + 1; i++){
			
			neighbours[i] = yMatrix[Integer.parseInt(s[i].toString())];
		}
		return neighbours;
	}
	
	public static double [] calculateWeights(double [] point, double [][] neighbours){
		
		double [] weights = new double[neighbours.length];
		double sum = 0;		
		for(int i = 0; i < neighbours.length; i++){
			sum += DistanceMeasure.euclideanDistance(point, neighbours[i]);
		}
		for(int i = 0; i < neighbours.length; i++){
			weights[i] = DistanceMeasure.euclideanDistance(point, neighbours[i])/sum;
		}
		return weights;
	}
	
	public static double[] predictValue(double [] weights, double [][] yFutureMatrix, int T){
		double [] futureX = new double[yFutureMatrix[0].length];
		
		for(int i = 0; i < futureX.length; i++){
			for(int k = 0; k < weights.length; k++){
				futureX[i] += weights[k]*yFutureMatrix[k+T][i];
			}
		}
		return futureX;
	}

	public static class MapUtil
	{
	    public static <K, V extends Comparable<? super V>> Map<K, V> 
	        sortByValue( Map<K, V> map )
	    {
	        List<Map.Entry<K, V>> list =
	            new LinkedList<Map.Entry<K, V>>( map.entrySet() );
	        Collections.sort( list, new Comparator<Map.Entry<K, V>>()
	        {
	            public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
	            {
	                return (o1.getValue()).compareTo( o2.getValue() );
	            }
	        } );

	        Map<K, V> result = new LinkedHashMap<K, V>();
	        for (Map.Entry<K, V> entry : list)
	        {
	            result.put( entry.getKey(), entry.getValue() );
	        }
	        return result;
	    }
	}
	
}

/**
 * 
 */
package features.output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author lsuc
 *
 */
public class WekaCsvFile extends OutputFile {

	public WekaCsvFile(String filePath){
		this.filePath = filePath;
	}
	/* (non-Javadoc)
	 * @see features.output.OutputFile#writeToFile(features.output.Features, boolean)
	 */
	@Override
	public void writeToFile(ArrayList<Features> features, boolean append)throws IOException {
		BufferedWriter out = null;
		try {
			File extractionFile = new File(getFilePath());
			if(!extractionFile.exists()) {
				extractionFile.createNewFile();
			} 
			out = new BufferedWriter(new FileWriter(extractionFile, append));	
			if(!append){
				StringBuilder optionsToPrintBuilder = new StringBuilder();
//				System.out.println("kombinacije signala su " + features.get(0).getSignals().size());
				for(int index = 0; index < features.get(0).getSignals().size()-1; index++){
//					System.out.println("broj options to print " + features.get(0).getOptionsToPrintNoParams().size());
					
					for(int i = 0; i < features.get(0).getOptionsToPrintNoParams().size(); i++){
						if((features.get(0).getSignals().get(index).length == 1 && !MultivariateFeatures.MULTIVARIATE_SET.contains(features.get(0).getOptionsToPrintNoParams().get(i))) 
								|| (features.get(0).getSignals().get(index).length > 1 && MultivariateFeatures.MULTIVARIATE_SET.contains(features.get(0).getOptionsToPrintNoParams().get(i)))){
//							System.out.println("za "+ index + " kombinaciju signala ima " + features.get(0).getSignals().get(index).length + " signala");
							for(int sigNum = 0; sigNum < features.get(0).getSignals().get(index).length; sigNum++){
								optionsToPrintBuilder.append(features.get(0).getSignals().get(index)[sigNum].getSignalLabel().trim());
								optionsToPrintBuilder.append("_");								
							}	
							optionsToPrintBuilder.append(features.get(0).getOptionsToPrintNoParams().get(i));
							optionsToPrintBuilder.append(", ");
						}												
					}
				}
				
				for(int i = 0; i < features.get(0).getOptionsToPrintNoParams().size()-1; i++){	
					if((features.get(0).getSignals().get(features.get(0).getSignals().size()-1).length == 1 && !MultivariateFeatures.MULTIVARIATE_SET.contains(features.get(0).getOptionsToPrintNoParams().get(i))) 
							|| (features.get(0).getSignals().get(features.get(0).getSignals().size()-1).length > 1 && MultivariateFeatures.MULTIVARIATE_SET.contains(features.get(0).getOptionsToPrintNoParams().get(i)))){
						for(int sigNum = 0; sigNum < features.get(0).getSignals().get(features.get(0).getSignals().size()-1).length; sigNum++){
							optionsToPrintBuilder.append(features.get(0).getSignals().get(features.get(0).getSignals().size()-1)[sigNum].getSignalLabel().trim());
							optionsToPrintBuilder.append("_");
						}
						optionsToPrintBuilder.append(features.get(0).getOptionsToPrintNoParams().get(i));
						optionsToPrintBuilder.append(", ");
					}
					
				}
				if((features.get(0).getSignals().get(features.get(0).getSignals().size()-1).length == 1 && !MultivariateFeatures.MULTIVARIATE_SET.contains(features.get(0).getOptionsToPrintNoParams().get( features.get(0).getOptionsToPrintNoParams().size()-1))) 
						|| (features.get(0).getSignals().get(features.get(0).getSignals().size()-1).length > 1 && MultivariateFeatures.MULTIVARIATE_SET.contains(features.get(0).getOptionsToPrintNoParams().get( features.get(0).getOptionsToPrintNoParams().size()-1)))){
					for(int sigNum = 0; sigNum < features.get(0).getSignals().get(features.get(0).getSignals().size()-1).length; sigNum++){
						optionsToPrintBuilder.append(features.get(0).getSignals().get(features.get(0).getSignals().size()-1)[sigNum].getSignalLabel().trim());
						optionsToPrintBuilder.append("_");
					}
					optionsToPrintBuilder.append(features.get(0).getOptionsToPrintNoParams().get(features.get(0).getOptionsToPrintNoParams().size()-1));
					
					out.write(optionsToPrintBuilder.toString());
				}
				
				out.newLine();				
			}	
			
			for(int index = 0; index < features.size(); index++){
				StringBuilder extractedFeaturesBuilder = new StringBuilder();

//				System.out.println("interval "+index +" broj signala "+ features.get(index).getExtractedFeatures().length );
				for(int i = 0; i < features.get(index).getExtractedFeatures().length-1; i++){	
//					System.out.println("interval "+index +" broj optionsa "+features.get(index).getOptionsToPrintNoParams().size() );
					for(int j = 0; j < features.get(index).getOptionsToPrintNoParams().size(); j++){
						if((features.get(index).getSignals().get(i).length == 1 && !MultivariateFeatures.MULTIVARIATE_SET.contains(features.get(index).getOptionsToPrintNoParams().get(j))) 
								|| (features.get(index).getSignals().get(i).length > 1 && MultivariateFeatures.MULTIVARIATE_SET.contains(features.get(index).getOptionsToPrintNoParams().get(j)))){
							extractedFeaturesBuilder.append(features.get(index).getExtractedFeatures()[i].get(features.get(index).getOptionsToPrintNoParams().get(j)));
							extractedFeaturesBuilder.append(", ");
						}
					}
				}
				int lastSignalIndex = features.get(index).getExtractedFeatures().length-1;
				
				for(int j = 0; j < features.get(index).getOptionsToPrintNoParams().size()-1; j++){
					if((features.get(index).getSignals().get(lastSignalIndex).length == 1 && !MultivariateFeatures.MULTIVARIATE_SET.contains(features.get(index).getOptionsToPrintNoParams().get(j))) 
							|| (features.get(index).getSignals().get(lastSignalIndex).length > 1 && MultivariateFeatures.MULTIVARIATE_SET.contains(features.get(index).getOptionsToPrintNoParams().get(j)))){
						System.out.println("evo me u "+index +" intervalu, opcija je "+features.get(index).getOptionsToPrintNoParams().get(j));
						extractedFeaturesBuilder.append(features.get(index).getExtractedFeatures()[lastSignalIndex].get(features.get(index).getOptionsToPrintNoParams().get(j)));
						extractedFeaturesBuilder.append(", ");
					}	
				}
				int lastOptionIndex = features.get(index).getOptionsToPrintNoParams().size()-1;
//				System.out.println("Problematicni dio 1 " + (features.get(index).getExtractedFeatures().length-1));
//				System.out.println("Problematicni dio 2 " + (features.get(index).getOptionsToPrintNoParams().size()-1));
				if((features.get(index).getSignals().get(lastSignalIndex).length == 1 && !MultivariateFeatures.MULTIVARIATE_SET.contains(features.get(index).getOptionsToPrintNoParams().get(lastOptionIndex ))) 
						|| (features.get(index).getSignals().get(lastSignalIndex).length > 1 && MultivariateFeatures.MULTIVARIATE_SET.contains(features.get(index).getOptionsToPrintNoParams().get(lastOptionIndex )))){
//					System.out.println("evo me u "+index +" intervalu, opcija je "+features.get(index).getOptionsToPrintNoParams().get(lastOptionIndex));
					
					extractedFeaturesBuilder.append(features.get(index).getExtractedFeatures()[lastSignalIndex].get(features.get(index).getOptionsToPrintNoParams().get((lastOptionIndex))));				
				}
//				
				out.write(extractedFeaturesBuilder.toString());				
				out.newLine();
			}
			out.flush();

	
			
			
		} finally {
			if (out != null) {
				out.close();
			}
		}	
	
	}	

}

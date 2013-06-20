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
//				System.out.println(features.get(0).getOptionsToPrintNoParams().size()* features.get(0).getSignals().length);
				for(int index = 0; index < features.get(0).getSignals().size()-1; index++){
					for(int i = 0; i < features.get(0).getOptionsToPrintNoParams().size(); i++){
						for(int sigNum = 0; sigNum < features.get(0).getSignals().get(index).length; sigNum++){
							optionsToPrintBuilder.append(features.get(0).getSignals().get(index)[sigNum].getSignalLabel().trim());
							optionsToPrintBuilder.append("_");
							optionsToPrintBuilder.append(features.get(0).getOptionsToPrintNoParams().get(i));
							optionsToPrintBuilder.append(", ");
						}	
					}
				}
				for(int i = 0; i < features.get(0).getOptionsToPrintNoParams().size()-1; i++){	
					for(int sigNum = 0; sigNum < features.get(0).getSignals().get(features.get(0).getSignals().size()-1).length; sigNum++){
						optionsToPrintBuilder.append(features.get(0).getSignals().get(features.get(0).getSignals().size()-1)[sigNum].getSignalLabel().trim());
						optionsToPrintBuilder.append("_");
						optionsToPrintBuilder.append(features.get(0).getOptionsToPrintNoParams().get(i));
						optionsToPrintBuilder.append(", ");
					}	
				}
				for(int sigNum = 0; sigNum < features.get(0).getSignals().get(features.get(0).getSignals().size()-1).length; sigNum++){
					optionsToPrintBuilder.append(features.get(0).getSignals().get(features.get(0).getSignals().size()-1)[sigNum].getSignalLabel().trim());
					optionsToPrintBuilder.append("_");
				}
				optionsToPrintBuilder.append(features.get(0).getOptionsToPrintNoParams().get(features.get(0).getOptionsToPrintNoParams().size()-1));
				
				out.write(optionsToPrintBuilder.toString());
				out.newLine();				
			}	
			
			for(int index = 0; index < features.size(); index++){
				StringBuilder extractedFeaturesBuilder = new StringBuilder();
				for(int i = 0; i < features.get(index).getExtractedFeatures().length-1; i++){				
					for(int j = 0; j < features.get(index).getOptionsToPrintNoParams().size(); j++){
						extractedFeaturesBuilder.append(features.get(index).getExtractedFeatures()[i].get(features.get(index).getOptionsToPrintNoParams().get(j)));
						extractedFeaturesBuilder.append(", ");
					}
				}
				for(int j = 0; j < features.get(index).getOptionsToPrintNoParams().size()-1; j++){
					extractedFeaturesBuilder.append(features.get(index).getExtractedFeatures()[features.get(index).getExtractedFeatures().length-1].get(features.get(index).getOptionsToPrintNoParams().get(j)));
					extractedFeaturesBuilder.append(", ");
				}
//				System.out.println("Problematicni dio 1 " + (features.get(index).getExtractedFeatures().length-1));
//				System.out.println("Problematicni dio 2 " + (features.get(index).getOptionsToPrintNoParams().size()-1));
				extractedFeaturesBuilder.append(features.get(index).getExtractedFeatures()[features.get(index).getExtractedFeatures().length-1].get(features.get(index).getOptionsToPrintNoParams().get((features.get(index).getOptionsToPrintNoParams().size()-1))));
				
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

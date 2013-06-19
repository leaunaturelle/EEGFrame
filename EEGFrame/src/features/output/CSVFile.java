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
public class CSVFile extends OutputFile {


	public CSVFile(String filePath){
		this.filePath = filePath;
	}
	/* (non-Javadoc)
	 * @see features.output.OutputFile#writeToFile(features.output.Features)
	 */
	@Override
	public void writeToFile(ArrayList<Features> features, boolean append) throws IOException {
		
		
		BufferedWriter out = null;
		try {
			File extractionFile = new File(getFilePath());
			if(!extractionFile.exists()) {
				extractionFile.createNewFile();
			} 
			out = new BufferedWriter(new FileWriter(extractionFile, append));	
			if(!append){
				StringBuilder optionsToPrintBuilder = new StringBuilder();
				for(int i = 0; i < features.get(0).getOptionsToPrint().size()-1; i++){
					optionsToPrintBuilder.append(features.get(0).getOptionsToPrint().get(i));
					optionsToPrintBuilder.append(", ");
				}
				optionsToPrintBuilder.append(features.get(0).getOptionsToPrint().get(features.get(0).getOptionsToPrint().size()-1));
				out.write(optionsToPrintBuilder.toString());
				out.newLine();
			}
			
//			for(int i = 0; i < features.getExtractedFeatures().length; i++){
//				StringBuilder extractedFeaturesBuilder = new StringBuilder();
//				for(int j = 0; j < features.getOptionsToPrint().size()-1; j++){
//					extractedFeaturesBuilder.append(features.getExtractedFeatures()[i].get(features.getOptionsToPrint().get(j)));
//					extractedFeaturesBuilder.append(", ");
//				}
//				extractedFeaturesBuilder.append(features.getExtractedFeatures()[i].get(features.getOptionsToPrint().get(features.getOptionsToPrint().size()-1)));
//				out.write(extractedFeaturesBuilder.toString());
//				out.newLine();
//			}
//			out.flush();	

		} finally {
			if (out != null) {
				out.close();
			}
		}					
	}
}

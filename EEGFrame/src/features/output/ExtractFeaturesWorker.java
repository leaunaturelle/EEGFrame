/**
 * 
 */
package features.output;

import gui.EEGFrameMain;
import java.io.IOException;

import javax.swing.SwingWorker;

/**
 * @author lsuc
 *
 */
public class ExtractFeaturesWorker extends SwingWorker<Boolean, Void> {

	private String fileName;
	private ExtractFeaturesController extractionController;
	private boolean append;
	
	public ExtractFeaturesWorker(String fileName, ExtractFeaturesController extractionController, boolean append){
		this.fileName = fileName;
		this.extractionController = extractionController;
		this.append = append;
	}
	/* (non-Javadoc)
	 * @see javax.swing.SwingWorker#doInBackground()
	 */
	@Override
	protected Boolean doInBackground(){
		extractionController.extractFeatures();
		OutputFile extractionOutputFile = null;
		if(extractionController.getOutputFileType().equals(extractionController.CSV)){
			extractionOutputFile = new CSVFile(fileName);
		}
		else{
			extractionOutputFile = new WekaCsvFile(fileName);
		}
		
		try {
			extractionOutputFile.writeToFile(extractionController.getSelectedFeatures(), append);
		
			
		} catch(IOException e){
			// TODO Auto-generated catch block
			System.out.println("File not found. Sorry for the inconvenience.");
			e.printStackTrace();
		}
		return true;
	}
	
	@Override
	public void done() {
		EEGFrameMain.checkOnEventDispatchThread();
		 try {
			 boolean done = get();
			 if(done){
				 extractionController.getWaitDialog().setVisible(false);
				 extractionController.setWaitDialog(null); 
				 extractionController.createNewExtractFeaturesController();
			 }
	        } catch (InterruptedException ignore) {}
	        catch (java.util.concurrent.ExecutionException e) {
	            String why = null;
	            Throwable cause = e.getCause();
	            if (cause != null) {
	                why = cause.getMessage();
	            } else {
	                why = e.getMessage();
	            }
	            System.err.println("Error retrieving file: " + why);
	            e.printStackTrace();
	        }
	}
}

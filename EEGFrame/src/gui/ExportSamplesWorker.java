/**
 * 
 */
package gui;

import java.io.IOException;

import javax.swing.SwingWorker;

/**
 * @author lsuc
 *
 */
public class ExportSamplesWorker extends SwingWorker<Boolean, Void> {

	private ExportSamplesWindow exportSamplesWindow;
	private String filePath;
	
	public ExportSamplesWorker(ExportSamplesWindow exportSamplesWindow, String filePath){
		this.exportSamplesWindow = exportSamplesWindow;
		this.filePath = filePath;
	}
	/* (non-Javadoc)
	 * @see javax.swing.SwingWorker#doInBackground()
	 */
	@Override
	protected Boolean doInBackground(){
		try {
			exportSamplesWindow.exportSamples(filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
//				 extractionController.getWaitDialog().setVisible(false);
//				 extractionController.setWaitDialog(null); 
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
	        }
	}
}

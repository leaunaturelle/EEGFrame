/**
 * 
 */
package gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.SwingWorker;

import dataHandling.EdfFile;
import dataHandling.InputFile;


/**
 * @author lsuc
 *
 */
public class LoadFileWorker extends SwingWorker<InputFile, Void> {

	private File file;
	private Controller controller;
	
	public LoadFileWorker(File file, Controller c){
		this.file = file;
		this.controller = c;
	}
	/* (non-Javadoc)
	 * @see javax.swing.SwingWorker#doInBackground()
	 */
	@Override
	protected InputFile doInBackground() {
		InputFile f = null;
		try {
			f = new EdfFile(file);
			f.setName(file.getAbsolutePath());
			f.findMinAndMaxSampleAllSignals();
			f.calculateSignalCoefAllSignals();
//			PrinterClass pc = new PrinterClass(f);
//			pc.printHeader();
			//pc.printDataRecord(0);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File not found. Sorry for the inconvenience.");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("An error occurred while loading file. The file had to be closed.");
			e.printStackTrace();
		} 	
		return  f;
	}
	
	@Override
	public void done() {
		EEGFrameMain.checkOnEventDispatchThread();
		 try {
			 InputFile file = get();
			 controller.getFiles().add(file);
			 controller.getFrame().setCursor(null);
			 controller.getSignalChooser().setFileList(controller.getFiles());
			 controller.getSignalChooser().setVisible(true);
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

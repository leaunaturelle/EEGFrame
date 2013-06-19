/**
 * 
 */
package gui;

import javax.swing.SwingWorker;

/**
 * @author lsuc
 *
 */
public class PanelParametersWorker extends SwingWorker<Boolean, Void> {

	private ViewingParameters parameters;
	private PanelController panelController;
	
	public PanelParametersWorker(ViewingParameters parameters, PanelController controller){
		this.parameters = parameters;
		this.panelController = controller;
	}
	/* (non-Javadoc)
	 * @see javax.swing.SwingWorker#doInBackground()
	 */
	@Override
	protected Boolean doInBackground() throws Exception {
		parameters.setDimension(panelController.calculatePreferredSize(parameters));
		double eachSignalMaxWindowsHeight = parameters.getDimension().getHeight() / (double) parameters.getSignalsToViewNum();
		double [] signalMiddleY = new double[parameters.getSignalsToViewNum()];
		for(int i = 0; i < signalMiddleY.length; i++){
			signalMiddleY[i] = eachSignalMaxWindowsHeight * i + eachSignalMaxWindowsHeight/2;
		}
		parameters.setSignalMiddleY(signalMiddleY);
		panelController.calculateSignalsYScales(parameters);
		panelController.calculateVerticalLinesPos(parameters);
		return true;
	}
	@Override
	public void done() {
		EEGFrameMain.checkOnEventDispatchThread();
		 try {
			 boolean done = get();
			 if(done){
				 panelController.getPanel().revalidate();
				 panelController.getPanel().repaint();
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

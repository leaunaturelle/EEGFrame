/**
 * 
 */
package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import javax.swing.JPanel;
import dataHandling.DataRecord;
import dataHandling.SignalParameterData;

/**
 * @author lsuc
 *
 */
public class SignaViewingPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int Y_VALUE = 10;
	private ViewingParameters parameters;
	private PanelController panelController;
	private MouseListener mouseListener;
	private MouseMotionListener mouseMotionListener;
	private double xSelection;
	
	public SignaViewingPanel(ViewingParameters parameters){
	
		mouseMotionListener = createMouseMotionListener();
		addMouseMotionListener(mouseMotionListener);
		this.parameters = parameters;
	}
	
	protected void paintComponent(Graphics graphics){
    	super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;

		this.setBackground(parameters.getBackgroundColor());
		
		EEGFrameMain.checkOnEventDispatchThread();
    	if(parameters.getSignalsToViewNum() > 0){
    		int horizontalScrollBar = panelController.getScrollPane().getHorizontalScrollBar().getValue(); 	
    		SelectedSignal signal;
    		
    		for(int sigNum = 0; sigNum < parameters.getSignalsToViewNum(); sigNum++){
    			signal = parameters.getSignalsForViewing().get(sigNum);
    		
    			
    			//Horizontal grid drawing
    			if(!panelController.isStandardView()){
    				g.setColor(parameters.getHorizontalLinesColor());  				
    				g.draw(new Line2D.Double(0, parameters.getSignalMiddleY()[sigNum],  this.getWidth(), parameters.getSignalMiddleY()[sigNum]));
    				g.draw(new Line2D.Double(0, parameters.getSignalMiddleY()[sigNum]-Y_VALUE*parameters.getyScaleFactor(), this.getWidth(), parameters.getSignalMiddleY()[sigNum]-Y_VALUE*parameters.getyScaleFactor()));
    				g.draw(new Line2D.Double(0, parameters.getSignalMiddleY()[sigNum]+Y_VALUE*parameters.getyScaleFactor(), this.getWidth(), parameters.getSignalMiddleY()[sigNum]+Y_VALUE*parameters.getyScaleFactor()));
    			}
    			else{
    				g.setColor(Color.LIGHT_GRAY);
    				g.draw(new Line2D.Double(0, parameters.getSignalMiddleY()[sigNum], getWidth(), parameters.getSignalMiddleY()[sigNum]));
    				if(parameters.getSignalsToViewNum() < 20){
    					double signalWindowsHeight = this.getHeight();
    					if(parameters.getSignalMiddleY().length > 1){
    						signalWindowsHeight = parameters.getSignalMiddleY()[1] - parameters.getSignalMiddleY()[0];
    					}
    					
    					for(int i = 1; i <= signalWindowsHeight/Y_VALUE/2; i++){
    						g.draw(new Line2D.Double(0, parameters.getSignalMiddleY()[sigNum]-Y_VALUE*i, getWidth(),parameters.getSignalMiddleY()[sigNum]-Y_VALUE*parameters.getyScaleFactor()*i));
    						g.draw(new Line2D.Double(0, parameters.getSignalMiddleY()[sigNum]+Y_VALUE*i, getWidth(), parameters.getSignalMiddleY()[sigNum]+Y_VALUE*parameters.getyScaleFactor()*i));
    		      			
    					}
    				}
    				else{
    					g.draw(new Line2D.Double(0, parameters.getSignalMiddleY()[sigNum]-Y_VALUE, getWidth(),parameters.getSignalMiddleY()[sigNum]-Y_VALUE*parameters.getyScaleFactor()));
						g.draw(new Line2D.Double(0, parameters.getSignalMiddleY()[sigNum]+Y_VALUE, getWidth(), parameters.getSignalMiddleY()[sigNum]+Y_VALUE*parameters.getyScaleFactor()));
		      			
    				}
    			}
    			g.setFont(new Font("Arial", Font.BOLD, 10)); 
    	        g.setColor(parameters.getSignalLabelColor());   
    	        SignalParameterData signalData = signal.getFile().getMetadata().getSignalParameters()[signal.getSignalIndex()];
  				
    	    	if(!panelController.isStandardView()){
    	    		g.drawString("- " + Float.toString(parameters.getAmplitude()) + " " + signalData.getPhysicalDimension(), horizontalScrollBar,  Math.round(parameters.getSignalMiddleY()[sigNum]-Y_VALUE*parameters.getyScaleFactor()));
    	    		g.drawString("+ " + Float.toString(parameters.getAmplitude()) + " " + signalData.getPhysicalDimension(), horizontalScrollBar,  Math.round(parameters.getSignalMiddleY()[sigNum]+Y_VALUE*parameters.getyScaleFactor()));
    	    	}
    	    	g.setFont(new Font("Arial", Font.BOLD, 12));
    	        g.drawString(signal.getSignalLabel(), horizontalScrollBar, Math.round(parameters.getSignalMiddleY()[sigNum]));
	        	
    			//Vertical grid drawing
    			g.setColor(Color.LIGHT_GRAY);
    			if(parameters.getLinesPerDisplayNum() > 0){
    				for(int i = 0; i < parameters.getVerticalLineXcoordinates().size(); i++){
    					if((i + 1)%parameters.getLinesPerDisplayNum() == 0 && !panelController.isStandardView()){
    						g.setColor(Color.RED);
    						double time = panelController.calculateTimeFromCoordinate(parameters.getVerticalLineXcoordinates().get(i));
    						g.drawString(PanelController.getFormatted(time), Math.round(parameters.getVerticalLineXcoordinates().get(i)), this.getHeight());
    					}
    					else if((i+1) % 2 == 0 && !panelController.isStandardView()){
    						g.setColor(Color.RED);
    						double time = panelController.calculateTimeFromCoordinate(parameters.getVerticalLineXcoordinates().get(i));
    						g.draw(new Line2D.Double(parameters.getVerticalLineXcoordinates().get(i), panelController.getVisiblePanelHeight()-50, parameters.getVerticalLineXcoordinates().get(i), panelController.getVisiblePanelHeight()));
    		    			
    						g.drawString(PanelController.getFormatted(time), Math.round(parameters.getVerticalLineXcoordinates().get(i)), this.getHeight());
    					
    					}
    					else{
    						g.setColor(Color.LIGHT_GRAY);	
    					}
    					g.draw(new Line2D.Double(parameters.getVerticalLineXcoordinates().get(i), 0, parameters.getVerticalLineXcoordinates().get(i), this.getHeight()));
    				}
    			}
    			 
    			
    			/**calculate voltage: [(physical miniumum) + (digital value in the data record - digital minimum) x 
    			 * (physical maximum - physical minimum) / (digital maximum - digital minimum)].
    			 */
    			
    			g.setColor(parameters.getSignalColor());
    			double physicalMin = signalData.getPhysicalMin();
    			double digitalMin = signalData.getDigitalMin();
    			double coef = signalData.getCoef();
  			
    			int startIndex = (int) Math.round(horizontalScrollBar/parameters.getSignalXScale()[sigNum]);
    			int endIndex = (int) Math.round((horizontalScrollBar + panelController.getVisiblePanelWidth())/parameters.getSignalXScale()[sigNum]);

    			int dataRecordIndex = (int) (startIndex / signalData.getSamplesInDataRecordNum());
    			int endDataRecordIndex = (int) (endIndex / signalData.getSamplesInDataRecordNum());

    			if(endDataRecordIndex >= signal.getFile().getDataRecords().length){
    				endDataRecordIndex = signal.getFile().getDataRecords().length - 1;
    			}
    		
    			int signalIndex = signal.getSignalIndex();
    			int currentIndex = startIndex;
    			int lastSampleIndex = startIndex;
    			double lastSample = signal.getFile().getDataRecords()[0].getSignalData()[signalIndex][0];
    			int i;
    
    			for(int index = dataRecordIndex; index <= endDataRecordIndex; index++){
    				DataRecord dataRecord = signal.getFile().getDataRecords()[index];
    				if(dataRecord.getStartTimeOffset() == 0){
    					short[] samples = dataRecord.getSignalData()[signalIndex];
    					double y = physicalMin + (lastSample-digitalMin)*coef;
    					double y1 = physicalMin + (samples[0]-digitalMin)*coef;
    					g.draw(new Line2D.Double(lastSampleIndex * parameters.getSignalXScale()[sigNum], 
    							parameters.getSignalMiddleY()[sigNum] + y * parameters.getSignalYScale()[sigNum] * parameters.getyScaleFactor(), 
    							currentIndex * parameters.getSignalXScale()[sigNum], 
    							parameters.getSignalMiddleY()[sigNum] + y1 * parameters.getSignalYScale()[sigNum]*parameters.getyScaleFactor()));

    					for(i = currentIndex; (i-currentIndex) < (samples.length-1) && i < endIndex; i++){      		  		
  	  		  				y = physicalMin + (samples[i-currentIndex]-digitalMin)*coef;      		  		
  	  		  				y1 = physicalMin + (samples[i+1-currentIndex]-digitalMin)*coef;
  	  		  				g.draw(new Line2D.Double(i * parameters.getSignalXScale()[sigNum], 
  	  		  						parameters.getSignalMiddleY()[sigNum] + y * parameters.getSignalYScale()[sigNum] * parameters.getyScaleFactor(),
  	  		  						(i+1) * parameters.getSignalXScale()[sigNum], 
  	  		  						parameters.getSignalMiddleY()[sigNum] + y1 * parameters.getSignalYScale()[sigNum]*parameters.getyScaleFactor()));	
    					}   
    					lastSample = samples[i-currentIndex];
    					lastSampleIndex = i;
    					currentIndex = i+1;	
    				}
    			}
    		}
    		if(xSelection > 0){
    			g.setColor(Color.CYAN);
    			g.draw(new Line2D.Double(xSelection, 0, xSelection, getHeight()));
    		}
    	}
	}

	protected MouseListener createMouseListener() {
	    MouseListener l = new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				xSelection = e.getX();
				repaint();
				panelController.createHandleSelectionDialog(xSelection);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
	    };
	   return l;
	}
	
	protected MouseMotionListener createMouseMotionListener() {
	    MouseMotionListener l = new MouseMotionListener(){

			@Override
			public void mouseDragged(MouseEvent e) {				
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				double x = e.getX();
				if(parameters.getVerticalLineXcoordinates() != null){
					if(parameters.getVerticalLineXcoordinates().contains(x)){
					}
				}
			}
	    };
	    return l;
	}

	public Dimension getPreferredSize(){	
		return parameters.getDimension();
	}
	
	public PanelController getController() {
		return panelController;
	}

	public void setController(PanelController controller) {
		this.panelController = controller;
	}

	public ViewingParameters getParameters() {
		return parameters;
	}

	public void setParameters(ViewingParameters parameters) {
		this.parameters = parameters;
	}

	public MouseListener getMouseListener() {
		return mouseListener;
	}

	public void setMouseListener(MouseListener mouseListener) {
		this.mouseListener = mouseListener;
	}

	public double getxSelection() {
		return xSelection;
	}

	public void setxSelection(double xSelection) {
		this.xSelection = xSelection;
	}
}

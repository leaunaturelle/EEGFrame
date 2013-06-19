/**
 * 
 */
package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

/**
 * @author lsuc
 *
 */
public class ViewingParameters {
	private float amplitude;
	private Color signalColor;
	private Color horizontalLinesColor;
	private Color backgroundColor;
	private Color signalLabelColor;
	private float secondsPerDisplay;
	private float yScaleFactor;	
	private int signalsToViewNum;	
	private double[] signalXScale;
	private double[] signalYScale;
	private short maxSignalNumPerDisplay;
	private short linesPerDisplayNum;
	private Dimension dimension;
	private double[] signalMiddleY;
	private ArrayList<SelectedSignal> signalsForViewing;
	private ArrayList<Double> verticalLineXcoordinates;
	
	public ViewingParameters() {
		setDefaultParameters();
	}
	public void setDefaultParameters(){
		amplitude = DEFAULT_AMPLITUDE;
		yScaleFactor = DEFAULT_Y_SCALE_FACTOR;
		secondsPerDisplay = DEFAULT_SECONDS_PER_DISPLAY;
		linesPerDisplayNum = DEFAULT_LINES_PER_DISPLAY_NUM;
		maxSignalNumPerDisplay = MAX_SIGNAL_NUM_PER_DISPLAY;
		horizontalLinesColor = Color.BLUE;
		backgroundColor =  Color.WHITE;
		signalColor = Color.BLACK;
		setSignalLabelColor(Color.BLUE);
		dimension = new Dimension(0,0);
	}
	
	public void clear(){
		signalXScale = null;
		signalYScale = null;
		signalMiddleY = null;
		signalsToViewNum = 0;
		signalsForViewing = null;
		setVerticalLineXcoordinates(null);
	}
	
	public float getAmplitude() {
		return amplitude;
	}

	public void setAmplitude(float amplitude) {
		this.amplitude = amplitude;
	}
	
	public double[] getSignalYScale() {
		return signalYScale;
	}

	public void setSignalYScale(double[] signalYScale) {
		this.signalYScale = signalYScale;
	}

	public Color getSignalColor() {
		return signalColor;
	}

	public void setSignalColor(Color signalColor) {
		this.signalColor = signalColor;
	}

	public Color getHorizontalLinesColor() {
		return horizontalLinesColor;
	}

	public void setHorizontalLinesColor(Color horizontalLinesColor) {
		this.horizontalLinesColor = horizontalLinesColor;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	
	public float getSecondsPerDisplay() {
		return secondsPerDisplay;
	}

	public void setSecondsPerDisplay(float secondsPerDisplay) {
		this.secondsPerDisplay = secondsPerDisplay;
	}

	public float getyScaleFactor() {
		return yScaleFactor;
	}

	public void setyScaleFactor(float yScaleFactor) {
		this.yScaleFactor = yScaleFactor;
	}

	public int getSignalsToViewNum() {
		return signalsToViewNum;
	}

	public void setSignalsToViewNum(int i) {
		this.signalsToViewNum = i;
	}

	public short getMaxSignalNumPerDisplay() {
		return maxSignalNumPerDisplay;
	}

	public void setMaxSignalNumPerDisplay(short maxSignalNumPerDisplay) {
		this.maxSignalNumPerDisplay = maxSignalNumPerDisplay;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}

	public double[] getSignalXScale() {
		return signalXScale;
	}

	public void setSignalXScale(double[] signalXScale) {
		this.signalXScale = signalXScale;
	}

	public short getLinesPerDisplayNum() {
		return linesPerDisplayNum;
	}

	public void setLinesPerDisplayNum(short linesPerDisplayNum) {
		this.linesPerDisplayNum = linesPerDisplayNum;
	}

	public double[] getSignalMiddleY() {
		return signalMiddleY;
	}
	public void setSignalMiddleY(double[] signalMiddleY) {
		this.signalMiddleY = signalMiddleY;
	}

	public ArrayList<SelectedSignal> getSignalsForViewing() {
		return signalsForViewing;
	}
	public void setSignalsForViewing(ArrayList<SelectedSignal> signalsForViewing) {
		this.signalsForViewing = signalsForViewing;
	}


	public ArrayList<Double> getVerticalLineXcoordinates() {
		return verticalLineXcoordinates;
	}

	public void setVerticalLineXcoordinates(ArrayList<Double> verticalLineXcoordinates) {
		this.verticalLineXcoordinates = verticalLineXcoordinates;
	}

	public Color getSignalLabelColor() {
		return signalLabelColor;
	}

	public void setSignalLabelColor(Color signalLabelColor) {
		this.signalLabelColor = signalLabelColor;
	}


	private final int DEFAULT_SECONDS_PER_DISPLAY = 10;
	private final int DEFAULT_AMPLITUDE = 100;
	private final int DEFAULT_Y_SCALE_FACTOR = 1;
	public static final float STANDARD_VIEW_DISTANCE = 0.2f;
	private final short DEFAULT_LINES_PER_DISPLAY_NUM = 10;
	private final int MAX_SIGNAL_NUM_PER_DISPLAY = 10;
}

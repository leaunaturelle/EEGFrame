/**
 * 
 */
package gui.Dialogs;

import features.output.ExtractUnivariateFeaturesController;
import features.output.Features;
import gui.EEGFrameMain;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * @author lsuc
 *
 */
public class AnalysisUnivariateParametersDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4465520728631375383L;
	private JTextField startTimeTextField, startSampleTextField, segmentLengthTimeTextField, segmentLengthSampleTextField;
	private JCheckBox checkBox;
	private String startSample, segmentLengthSamples, startTime, segmentLengthTime;
	private JRadioButton startTimeButton, startSampleButton;
	private ExtractUnivariateFeaturesWindow window;
	
	public AnalysisUnivariateParametersDialog(ExtractUnivariateFeaturesWindow window){
		EEGFrameMain.checkOnEventDispatchThread();
		this.window = window;
		this.setTitle ("Analysis parameters");
		startSample = "0";
		segmentLengthSamples = "100";
		startTime = "0";
		segmentLengthTime = "10";
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setPreferredSize(new Dimension(300,400));	
		this.setLayout(new BorderLayout());
		JPanel panel = addAnalysisParametersPanel();
		this.add(panel, BorderLayout.CENTER);
	    this.setResizable(false);
	    this.setModal(true);
	    this.pack();
	}

	private JPanel addAnalysisParametersPanel(){
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel intervalPanel = new JPanel();
		intervalPanel.setLayout(new BoxLayout(intervalPanel, BoxLayout.X_AXIS));
		JLabel intervalPanelLabel = new JLabel("Select interval for feature extraction");
		intervalPanel.add(Box.createRigidArea(new Dimension(5,0)));
		intervalPanel.add(intervalPanelLabel);
		intervalPanel.add(Box.createHorizontalGlue());
		panel.add(intervalPanel);
		panel.add(Box.createRigidArea(new Dimension(5,5)));
		
		JPanel startTimeButtonPanel = new JPanel();
		startTimeButtonPanel.setLayout(new BoxLayout(startTimeButtonPanel, BoxLayout.X_AXIS));
		startTimeButtonPanel.add(Box.createRigidArea(new Dimension(5,0)));
		startTimeButton = new JRadioButton("Use start time, in seconds");
		startTimeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				enableUseTime();
				disableUseSample();
			}
		});
		startTimeButtonPanel.add(startTimeButton);
		startTimeButtonPanel.add(Box.createHorizontalGlue());
		panel.add(startTimeButtonPanel);
		panel.add(Box.createRigidArea(new Dimension(5,5)));
		
		JPanel startTimePanel = new JPanel();
		startTimePanel.setLayout(new BoxLayout(startTimePanel, BoxLayout.X_AXIS));
		startTimePanel.add(Box.createHorizontalGlue());
		JLabel startTimeLabel = new JLabel("Start time:");
		startTimeLabel.setPreferredSize(new Dimension(150, 20));
		startTimePanel.add(startTimeLabel);
		startTimePanel.add(Box.createHorizontalGlue());
		startTimeTextField = new JTextField(startTime);
		startTimeTextField.setPreferredSize(new Dimension(50, 20));
		startTimeTextField.setMaximumSize(new Dimension(50, 20));
		startTimeTextField.setEnabled(false);
		startTimePanel.add(startTimeTextField);
		startTimePanel.add(Box.createHorizontalGlue());
		panel.add(Box.createRigidArea(new Dimension(10,0)));
		panel.add(startTimePanel);
		
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		JPanel segmentLengthTimePanel = new JPanel();
		segmentLengthTimePanel.setLayout(new BoxLayout(segmentLengthTimePanel, BoxLayout.X_AXIS));
		segmentLengthTimePanel.add(Box.createHorizontalGlue());
		JLabel segmentLengthTimeLabel = new JLabel("Segment length:");
		segmentLengthTimeLabel.setPreferredSize(new Dimension(150, 20));
		segmentLengthTimePanel.add(segmentLengthTimeLabel);
		segmentLengthTimePanel.add(Box.createHorizontalGlue());
		segmentLengthTimeTextField = new JTextField(segmentLengthTime);
		segmentLengthTimeTextField.setEnabled(false);
		segmentLengthTimeTextField.setPreferredSize(new Dimension(50,20));
		segmentLengthTimeTextField.setMaximumSize(new Dimension(50,20));
		segmentLengthTimePanel.add(segmentLengthTimeTextField);
		segmentLengthTimePanel.add(Box.createHorizontalGlue());
		panel.add(Box.createRigidArea(new Dimension(10,0)));
		panel.add(segmentLengthTimePanel);
		
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		JPanel startSampleButtonPanel = new JPanel();
		startSampleButtonPanel.setLayout(new BoxLayout(startSampleButtonPanel, BoxLayout.X_AXIS));
		startSampleButtonPanel.add(Box.createRigidArea(new Dimension(5,0)));
		startSampleButton = new JRadioButton("Use start sample");
		startSampleButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				enableUseSample();
				disableUseTime();
			}
		});
		startSampleButtonPanel.add(startSampleButton);
		startSampleButtonPanel.add(Box.createHorizontalGlue());
		panel.add(startSampleButtonPanel);
	
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		JPanel startSamplePanel = new JPanel();
		startSamplePanel.setLayout(new BoxLayout(startSamplePanel, BoxLayout.X_AXIS));
		startSamplePanel.add(Box.createHorizontalGlue());
		JLabel startSampleLabel = new JLabel("Start sample:");
		startSampleLabel.setPreferredSize(new Dimension(150, 20));
		startSamplePanel.add(startSampleLabel);
		startSamplePanel.add(Box.createHorizontalGlue());
		startSampleTextField = new JTextField(startSample);
		startSampleTextField.setEnabled(false);
		startSampleTextField.setPreferredSize(new Dimension(50,20));
		startSampleTextField.setMaximumSize(new Dimension(50,20));
		startSamplePanel.add(startSampleTextField);
		startSamplePanel.add(Box.createHorizontalGlue());
		panel.add(Box.createRigidArea(new Dimension(10,0)));
		panel.add(startSamplePanel);
		
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		JPanel segmentLengthSamplePanel = new JPanel();
		segmentLengthSamplePanel.setLayout(new BoxLayout(segmentLengthSamplePanel, BoxLayout.X_AXIS));
		segmentLengthSamplePanel.add(Box.createHorizontalGlue());
		JLabel segmentLengthSampleLabel = new JLabel("Segment length:");
		segmentLengthSampleLabel.setPreferredSize(new Dimension(150, 20));
		segmentLengthSamplePanel.add(segmentLengthSampleLabel);
		segmentLengthSamplePanel.add(Box.createHorizontalGlue());
		segmentLengthSampleTextField = new JTextField(segmentLengthSamples);
		segmentLengthSampleTextField.setEnabled(false);
		segmentLengthSampleTextField.setPreferredSize(new Dimension(50,20));
		segmentLengthSampleTextField.setMaximumSize(new Dimension(50,20));
		segmentLengthSamplePanel.add(segmentLengthSampleTextField);
		segmentLengthSamplePanel.add(Box.createHorizontalGlue());
		panel.add(Box.createRigidArea(new Dimension(10,0)));
		panel.add(segmentLengthSamplePanel);
		
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		panel.add(Box.createRigidArea(new Dimension(10,0)));
		checkBox = new JCheckBox("Until end of record");
		panel.add(checkBox);


		ButtonGroup selectionGroup = new ButtonGroup();
		selectionGroup.add(startTimeButton);
		selectionGroup.add(startSampleButton);
		startTimeButton.setSelected(true);
		enableUseTime();
		
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		panel.add(new JSeparator());
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		JPanel buttonPanel = new JPanel();
		panel.add(buttonPanel);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(Box.createHorizontalGlue());
		JButton selectIntervalButton = new JButton("Select interval");
		selectIntervalButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Go to Extract features >> Select interval >> Select start/end time", "Interval selection", JOptionPane.INFORMATION_MESSAGE);
				setVisible(false);
				window.setVisible(false);
			}
		});
		buttonPanel.add(selectIntervalButton);
		buttonPanel.add(Box.createHorizontalGlue());
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				startTimeTextField.setText(startTime);
				segmentLengthTimeTextField.setText(segmentLengthTime);
				startSampleTextField.setText(startSample);
				segmentLengthSampleTextField.setText(segmentLengthSamples);
				setVisible(false);
			}
		});
		buttonPanel.add(cancelButton);
		buttonPanel.add(Box.createHorizontalGlue());
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				startTime = startTimeTextField.getText();
				segmentLengthTime = segmentLengthTimeTextField.getText();
				startSample = startSampleTextField.getText();				
				segmentLengthSamples = segmentLengthSampleTextField.getText();
				//TODO show message if < 0 or to little samples
				if(Double.parseDouble(startSample) < 0){
					setStartSample("0");
				}
				if(Double.parseDouble(segmentLengthSamples) < 100){
					setSegmentLengthSamples("100");
				}
				if(Double.parseDouble(startTime) < 0){
					setStartTime("0");
				}
				if(Double.parseDouble(segmentLengthTime) <= 0){
					setSegmentLengthTime("1");
				}
				setVisible(false);
			}
		});
		buttonPanel.add(okButton);
		buttonPanel.add(Box.createHorizontalGlue());
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		return panel;
	}
	public void saveInterval(ExtractUnivariateFeaturesController univariateController){
		Features f = univariateController.getSelectedFeatures().get(0);
		if(startSampleButton.isSelected()){
			f.setTimeInterval(null);
			ArrayList<Long[]> intervalList = new ArrayList<Long[]>();
			Long[] interval = new Long[2];
			interval[0] = Math.round(Double.parseDouble(startSample));
			if(!checkBox.isSelected()){
				interval[1] = Math.round(interval[0] + Double.parseDouble(segmentLengthSamples));
			}
			else{
				interval[1] = 0l;
			}
			intervalList.add(interval);
			f.setSampleInterval(intervalList);
			
		}
		else if(startTimeButton.isSelected()){
			f.setSampleInterval(null);
			ArrayList<Double[]> intervalList = new ArrayList<Double[]>();
			Double[] interval = new Double[2];
			interval[0] = Double.parseDouble(startTime);
			if(!checkBox.isSelected()){
				interval[1] = interval[0] + Double.parseDouble(segmentLengthTime);
			}
			else{
				interval[1] = 0.0;
			}
			intervalList.add(interval);
			f.setTimeInterval(intervalList);
		}
	}
	public void disableUseTime(){
		startTimeTextField.setEnabled(false);
		segmentLengthTimeTextField.setEnabled(false);
	}
	
	public void enableUseTime(){
		startTimeTextField.setEnabled(true);
		segmentLengthTimeTextField.setEnabled(true);
	}
	
	public void disableUseSample(){
		startSampleTextField.setEnabled(false);
		segmentLengthSampleTextField.setEnabled(false);
	}
	
	public void enableUseSample(){
		startSampleTextField.setEnabled(true);
		segmentLengthSampleTextField.setEnabled(true);
	}

	public JTextField getStartSampleTextField() {
		return startSampleTextField;
	}

	public void setStartSampleTextField(JTextField startSampleTextField) {
		this.startSampleTextField = startSampleTextField;
	}

	public JTextField getSegmentLengthSampleTextField() {
		return segmentLengthSampleTextField;
	}

	public void setSegmentLengthSampleTextField(JTextField segmentLengthSampleTextField) {
		this.segmentLengthSampleTextField = segmentLengthSampleTextField;
	}

	public String getStartSample() {
		return startSample;
	}

	public void setStartSample(String startSample) {
		this.startSample = startSample;
		this.startSampleTextField.setText(startSample);
	}

	public String getSegmentLengthSamples() {
		return segmentLengthSamples;
	}

	public void setSegmentLengthSamples(String segmentLengthSamples) {
		this.segmentLengthSamples = segmentLengthSamples;
		this.segmentLengthSampleTextField.setText(segmentLengthSamples);
	}

	public String getStartTime() {
		return startTime;
	}

	public JRadioButton getStartTimeButton() {
		return startTimeButton;
	}

	public void setStartTimeButton(JRadioButton startTimeButton) {
		this.startTimeButton = startTimeButton;
	}

	public JRadioButton getStartSampleButton() {
		return startSampleButton;
	}

	public void setStartSampleButton(JRadioButton startSampleButton) {
		this.startSampleButton = startSampleButton;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
		this.startTimeTextField.setText(startTime);
	}

	public String getSegmentLengthTime() {
		return segmentLengthTime;
	}

	public void setSegmentLengthTime(String segmentLengthTime) {
		this.segmentLengthTime = segmentLengthTime;
		this.segmentLengthTimeTextField.setText(segmentLengthTime);
	}

	public JTextField getSegmentLengthTimeTextField() {
		return segmentLengthTimeTextField;
	}

	public void setSegmentLengthTimeTextField(JTextField segmentLengthTimeTextField) {
		this.segmentLengthTimeTextField = segmentLengthTimeTextField;
	}

	public JTextField getStartTimeTextField() {
		return startTimeTextField;
	}

	public void setStartTimeTextField(JTextField startTimeTextField) {
		this.startTimeTextField = startTimeTextField;
	}
}

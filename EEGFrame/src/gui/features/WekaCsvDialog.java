/**
 * 
 */
package gui.features;

import features.output.ExtractUnivariateFeaturesController;
import features.output.Features;
import features.output.UnivariateFeatures;
import gui.EEGFrameMain;
import gui.SelectedSignal;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

/**
 * @author lsuc
 *
 */
public class WekaCsvDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3675121460405855036L;

	private ExtractUnivariateFeaturesController univariateFeaturesController;
	private JCheckBox wekaCheckBox, movingWindowCheckBox, removeUnknownCheckBox, multivariateCheckBox, classCheckBox;
	private JButton selectMultivariateButton;
	private JTextField movingWindowSizeTextField, classTextField;
	private JSpinner percentageSpinner;
	
	public WekaCsvDialog(ExtractUnivariateFeaturesController univariateFeaturesController){
		EEGFrameMain.checkOnEventDispatchThread();
		this.univariateFeaturesController = univariateFeaturesController;
		this.setTitle ("Csv file for Weka analysis");
		
//		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setPreferredSize(new Dimension(450,400));	
		this.setLayout(new BorderLayout());
		JPanel panel = addWekaCsvPanel();
		this.add(panel, BorderLayout.CENTER);
	    this.setResizable(false);
	    this.setModal(true);
	    this.pack();
	}
	private JPanel addWekaCsvPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel wekaPanel = new JPanel();
		wekaPanel.setLayout(new BoxLayout(wekaPanel, BoxLayout.X_AXIS));
		wekaPanel.add(Box.createRigidArea(new Dimension(5,0)));
		wekaCheckBox = new JCheckBox("Generate Csv file for analysis in Weka");
		wekaCheckBox.addItemListener(new ItemListener() {			
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					removeUnknownCheckBox.setEnabled(true);
					movingWindowCheckBox.setEnabled(true);
					multivariateCheckBox.setEnabled(true);
					classCheckBox.setEnabled(true);
				}	
				else{
					removeUnknownCheckBox.setEnabled(false);
					movingWindowCheckBox.setEnabled(false);
					multivariateCheckBox.setEnabled(false);
					classCheckBox.setEnabled(false);
				}
			}
		});		
		wekaPanel.add(wekaCheckBox);
		wekaPanel.add(Box.createHorizontalGlue());
		panel.add(wekaPanel);
		panel.add(Box.createRigidArea(new Dimension(5,5)));
		

		JPanel removeUnknownPanel = new JPanel();
		removeUnknownPanel.setLayout(new BoxLayout(removeUnknownPanel, BoxLayout.X_AXIS));
		removeUnknownPanel.add(Box.createRigidArea(new Dimension(5,0)));
		removeUnknownCheckBox = new JCheckBox("Remove unknown features (not calculated)");
		removeUnknownCheckBox.setEnabled(false);
		removeUnknownPanel.add(removeUnknownCheckBox);
		removeUnknownPanel.add(Box.createHorizontalGlue());
		panel.add(removeUnknownPanel);
		panel.add(Box.createRigidArea(new Dimension(5,5)));
		
		JPanel movingWindowPanel = new JPanel();
		movingWindowPanel.setLayout(new BoxLayout(movingWindowPanel, BoxLayout.X_AXIS));
		movingWindowPanel.add(Box.createRigidArea(new Dimension(5,0)));
		movingWindowCheckBox = new JCheckBox("Use moving window");
		movingWindowCheckBox.setEnabled(false);
		movingWindowCheckBox.addItemListener(new ItemListener() {			
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					movingWindowSizeTextField.setEnabled(true);
					percentageSpinner.setEnabled(true);

				}		
				else{
					movingWindowSizeTextField.setEnabled(false);
					percentageSpinner.setEnabled(false);
				
				}
			}
		});		
		movingWindowPanel.add(movingWindowCheckBox);
		movingWindowPanel.add(Box.createHorizontalGlue());
		panel.add(movingWindowPanel);
		panel.add(Box.createRigidArea(new Dimension(5,5)));
		
		JPanel movingWindowSizePanel = new JPanel();
		movingWindowSizePanel.setLayout(new BoxLayout(movingWindowSizePanel, BoxLayout.X_AXIS));
		JLabel movingWindowSizeLabel = new JLabel("Moving window size, in seconds ");
		movingWindowSizeLabel.setPreferredSize(new Dimension(350, 20));
		movingWindowSizePanel.add(Box.createRigidArea(new Dimension(15,0)));
		movingWindowSizePanel.add(Box.createHorizontalGlue());		
		movingWindowSizePanel.add(movingWindowSizeLabel);
		movingWindowSizePanel.add(Box.createHorizontalGlue());		
		movingWindowSizeTextField = new JTextField("5");
		movingWindowSizeTextField.setEnabled(false);
		movingWindowSizeTextField.setPreferredSize(new Dimension(50,20));
		movingWindowSizeTextField.setMaximumSize(new Dimension(50,20));
		movingWindowSizePanel.add(movingWindowSizeTextField);
		movingWindowSizePanel.add(Box.createHorizontalGlue());
//		movingWindowSizePanel.add(Box.createHorizontalGlue());
		panel.add(movingWindowSizePanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel movingWindowOverlapPercentPanel = new JPanel();
		movingWindowOverlapPercentPanel.setLayout(new BoxLayout(movingWindowOverlapPercentPanel, BoxLayout.X_AXIS));
		JLabel movingWindowOverlapPercentLabel = new JLabel("Overlapping between successive windows (percentage) ");
		movingWindowOverlapPercentLabel.setPreferredSize(new Dimension(350, 20));
		movingWindowOverlapPercentPanel.add(Box.createRigidArea(new Dimension(15,0)));
		movingWindowOverlapPercentPanel.add(Box.createHorizontalGlue());		
		movingWindowOverlapPercentPanel.add(movingWindowOverlapPercentLabel);
		movingWindowOverlapPercentPanel.add(Box.createHorizontalGlue());		
		percentageSpinner = new JSpinner(new SpinnerNumberModel(25, 0, 99, 1));
//		movingWindowSizeTextField = new JTextField("5");
		percentageSpinner.setEnabled(false);
		percentageSpinner.setPreferredSize(new Dimension(40,20));
		percentageSpinner.setMaximumSize(new Dimension(40,20));
		movingWindowOverlapPercentPanel.add(percentageSpinner);
		movingWindowOverlapPercentPanel.add(Box.createHorizontalGlue());
		movingWindowOverlapPercentPanel.add(new JLabel("%"));
		movingWindowOverlapPercentPanel.add(Box.createHorizontalGlue());
//		movingWindowOverlapPercentPanel.add(Box.createHorizontalGlue());
		panel.add(movingWindowOverlapPercentPanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel classPanel = new JPanel();
		classPanel.setLayout(new BoxLayout(classPanel, BoxLayout.X_AXIS));
		classPanel.add(Box.createRigidArea(new Dimension(5,0)));
		classCheckBox = new JCheckBox("Add class label");
		classCheckBox.setEnabled(false);
		classCheckBox.addItemListener(new ItemListener(){
            
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					classTextField.setEnabled(true);
                }
				else{
					classTextField.setEnabled(false);
				}
			}
		});
//		classPanel.add(Box.createHorizontalGlue());
		classPanel.add(classCheckBox);
		classPanel.add(Box.createHorizontalGlue());
		JLabel orderLabel = new JLabel("Class label: ");
		orderLabel.setHorizontalAlignment(JLabel.RIGHT);
//		orderLabel.setPreferredSize(new Dimension(100, 20));
		classPanel.add(orderLabel);
		classPanel.add(Box.createHorizontalGlue());
		classTextField = new JTextField("0");
		classTextField.setEnabled(false);
		classTextField.setPreferredSize(new Dimension(50,20));
		classTextField.setMaximumSize(new Dimension(50,20));
		classPanel.add(classTextField);
		classPanel.add(Box.createHorizontalGlue());
		panel.add(classPanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel multivariatePanel = new JPanel();
		multivariatePanel.setLayout(new BoxLayout(multivariatePanel, BoxLayout.X_AXIS));
		multivariatePanel.add(Box.createRigidArea(new Dimension(5,0)));
		multivariateCheckBox = new JCheckBox("Add multivariate features");
		multivariateCheckBox.setEnabled(false);	
		multivariateCheckBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					selectMultivariateButton.setEnabled(true);
				}
				else{
					selectMultivariateButton.setEnabled(false);
				}
			}
		});
		multivariatePanel.add(multivariateCheckBox);
		multivariatePanel.add(Box.createHorizontalGlue());
		panel.add(multivariatePanel);
		panel.add(Box.createRigidArea(new Dimension(5,5)));
		
		JPanel selectMultivariatePanel = new JPanel();
		selectMultivariatePanel.setLayout(new BoxLayout(selectMultivariatePanel, BoxLayout.X_AXIS));
//		selectMultivariatePanel.add(Box.createRigidArea(new Dimension(5,0)));
		selectMultivariatePanel.add(Box.createHorizontalGlue());
		selectMultivariateButton = new JButton("Select...");
		selectMultivariateButton.setEnabled(false);	
		selectMultivariateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				univariateFeaturesController.getExtractMixedFeaturesController().getExtractMultivariateFeaturesController().getExtractMultivariateFeaturesWindow().showNonlinearMultivariateFeaturesDialog();
			}
		});
		selectMultivariatePanel.add(selectMultivariateButton);
		selectMultivariatePanel.add(Box.createHorizontalGlue());
		panel.add(selectMultivariatePanel);
		panel.add(Box.createRigidArea(new Dimension(5,5)));


		panel.add(new JSeparator());
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		JPanel buttonPanel = new JPanel();
		panel.add(buttonPanel);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(Box.createHorizontalGlue());
		JButton selectAllButton = new JButton("Select all");
		selectAllButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				wekaCheckBox.setSelected(true);
				movingWindowCheckBox.setSelected(true);
				removeUnknownCheckBox.setSelected(true);
				multivariateCheckBox.setSelected(true);
				classCheckBox.setSelected(true);
			}
		});
		buttonPanel.add(selectAllButton);
		buttonPanel.add(Box.createHorizontalGlue());
		JButton clearAllButton = new JButton("Clear all");
		clearAllButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				wekaCheckBox.setSelected(false);
				movingWindowCheckBox.setSelected(false);
				removeUnknownCheckBox.setSelected(false);
				multivariateCheckBox.setSelected(false);
				classCheckBox.setSelected(false);
				
			}
		});
		buttonPanel.add(clearAllButton);
		buttonPanel.add(Box.createHorizontalGlue());
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			
			
			@Override
			public void actionPerformed(ActionEvent e) {		
				if(classCheckBox.isSelected()){
					univariateFeaturesController.getSelectedFeatures().get(0).setClassSelected(true);
					univariateFeaturesController.getSelectedFeatures().get(0).setClassLabel(classTextField.getText());
				}
				setVisible(false);
			}
		});
		buttonPanel.add(okButton);
		buttonPanel.add(Box.createHorizontalGlue());
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		return panel;
	}
	
	public JCheckBox getMultivariateCheckBox() {
		return multivariateCheckBox;
	}
	public void setMultivariateCheckBox(JCheckBox multivariateCheckBox) {
		this.multivariateCheckBox = multivariateCheckBox;
	}
	public JCheckBox getRemoveUnknownCheckBox() {
		return removeUnknownCheckBox;
	}
	public void setRemoveUnknownCheckBox(JCheckBox removeUnknownCheckBox) {
		this.removeUnknownCheckBox = removeUnknownCheckBox;
	}
	public JCheckBox getWekaCheckBox() {
		return wekaCheckBox;
	}
	public void setWekaCheckBox(JCheckBox wekaCheckBox) {
		this.wekaCheckBox = wekaCheckBox;
	}
	public JCheckBox getMovingWindowCheckBox() {
		return movingWindowCheckBox;
	}
	public void setMovingWindowCheckBox(JCheckBox movingWindowCheckBox) {
		this.movingWindowCheckBox = movingWindowCheckBox;
	}
	public JTextField getMovingWindowSizeTextField() {
		return movingWindowSizeTextField;
	}
	public void setMovingWindowSizeTextField(JTextField movingWindowSizeTextField) {
		this.movingWindowSizeTextField = movingWindowSizeTextField;
	}
	public JSpinner getPercentageSpinner() {
		return percentageSpinner;
	}
	public void setPercentageSpinner(JSpinner percentageSpinner) {
		this.percentageSpinner = percentageSpinner;
	}
}

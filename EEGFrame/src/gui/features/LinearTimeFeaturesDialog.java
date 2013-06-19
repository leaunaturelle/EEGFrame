/**
 * 
 */
package gui.features;

import features.output.ExtractUnivariateFeaturesController;
import features.output.Features;
import features.output.UnivariateFeatures;
import gui.EEGFrameMain;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.WindowConstants;

/**
 * @author lsuc
 *
 */
public class LinearTimeFeaturesDialog extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 735216553160692135L;

	private ExtractUnivariateFeaturesController univariateFeaturesController;
	private JCheckBox mean, meanAbsFirstN, meanAbsSecondN, meanAbsFirstRaw, meanAbsSecondRaw, stdDev, autoCorrelationCoef;
	
	public LinearTimeFeaturesDialog (ExtractUnivariateFeaturesController univariateFeaturesController){
		EEGFrameMain.checkOnEventDispatchThread();			
		this.setTitle ("Linear time domain features");
		this.univariateFeaturesController = univariateFeaturesController;
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setPreferredSize(new Dimension(500,400));	
		this.setLayout(new BorderLayout());
		JPanel panel = addLinearTimeFeaturesPanel();
		this.add(panel, BorderLayout.CENTER);
	    this.setResizable(false);
	    this.setModal(true);
	    this.pack();
	}

	public JPanel addLinearTimeFeaturesPanel(){
		JPanel panel = new JPanel();
		
		panel.setLayout(new GridLayout(9, 0, 0, 5));
		
		mean = new JCheckBox("Mean");
		mean.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		panel.add(mean);
		meanAbsFirstN = new JCheckBox("Mean of the absolute values of the first differences - normalized signal");
		panel.add(meanAbsFirstN);
		meanAbsFirstN.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		meanAbsSecondN = new JCheckBox("Mean of the absolute values of the second differences - normalized signal");
		panel.add(meanAbsSecondN);
		meanAbsSecondN.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		meanAbsFirstRaw = new JCheckBox("Mean of the absolute values of the first differences - raw signal");
		panel.add(meanAbsFirstRaw);
		meanAbsFirstRaw.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		meanAbsSecondRaw = new JCheckBox("Mean of the absolute values of the second differences - raw signal");
		panel.add(meanAbsSecondRaw);
		meanAbsSecondRaw.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		stdDev = new JCheckBox("Standard deviation");
		panel.add(stdDev);
		stdDev.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
		autoCorrelationCoef = new JCheckBox("Autocorrelation coefficient");
		panel.add(autoCorrelationCoef);
		autoCorrelationCoef.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));

		panel.add(new JSeparator());
		JPanel buttonPanel = new JPanel();
		panel.add(buttonPanel);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(Box.createHorizontalGlue());
		JButton selectAllButton = new JButton("Select all");
		selectAllButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mean.setSelected(true);
				meanAbsFirstN.setSelected(true);
				meanAbsSecondN.setSelected(true);
				meanAbsFirstRaw.setSelected(true);
				meanAbsSecondRaw.setSelected(true);
				stdDev.setSelected(true);
				autoCorrelationCoef.setSelected(true);
			}
		});
		buttonPanel.add(selectAllButton);
		buttonPanel.add(Box.createHorizontalGlue());
		JButton clearAllButton = new JButton("Clear all");
		clearAllButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mean.setSelected(false);
				meanAbsFirstN.setSelected(false);
				meanAbsSecondN.setSelected(false);
				meanAbsFirstRaw.setSelected(false);
				meanAbsSecondRaw.setSelected(false);
				stdDev.setSelected(false);
				autoCorrelationCoef.setSelected(false);
				
			}
		});
		buttonPanel.add(clearAllButton);
		buttonPanel.add(Box.createHorizontalGlue());
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Features f = univariateFeaturesController.getSelectedFeatures().get(0);
				if(mean.isSelected()){
					f.getFeatures().put(UnivariateFeatures.MEAN, true);
				}
				if(meanAbsFirstN.isSelected()){
					f.getFeatures().put(UnivariateFeatures.MEAN_OF_ABS_VALUES_OF_FIRST_DIFFERENCES_NORMALIZED, true);
				}
				if(meanAbsSecondN.isSelected()){
					f.getFeatures().put(UnivariateFeatures.MEAN_OF_ABS_VALUES_OF_SECOND_DIFFERENCES_NORMALIZED, true);
				}
				if(meanAbsFirstRaw.isSelected()){
					f.getFeatures().put(UnivariateFeatures.MEAN_OF_ABS_VALUES_OF_FIRST_DIFFERENCES_RAW, true);
				}
				if(meanAbsSecondRaw.isSelected()){
					f.getFeatures().put(UnivariateFeatures.MEAN_OF_ABS_VALUES_OF_SECOND_DIFFERENCES_RAW, true);
				}
				if(stdDev.isSelected()){
					f.getFeatures().put(UnivariateFeatures.STANDARD_DEVIATION, true);
				}
				if(autoCorrelationCoef.isSelected()){
					f.getFeatures().put(UnivariateFeatures.AUTOCORRELATION_COEF, true);
				}
				setVisible(false);
			}
		});
		buttonPanel.add(okButton);
		buttonPanel.add(Box.createHorizontalGlue());
		
		return panel;
	}

}

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
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * @author lsuc
 *
 */
public class PhaseSpaceFeaturesDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -671721423422442966L;
	
	private ExtractUnivariateFeaturesController univariateFeaturesController;
	private JTextField phaseSpaceDimensionTextField, phaseSpaceLagsTextField, SFITextField, forecastingTimeTextField; 
	public JTextField getForecastingTimeTextField() {
		return forecastingTimeTextField;
	}


	public void setForecastingTimeTextField(JTextField forecastingTimeTextField) {
		this.forecastingTimeTextField = forecastingTimeTextField;
	}

	private JCheckBox standardDeviationRatioCheckBox, ctmCheckBox, SFICheckBox, lleCheckBox, d2CheckBox, recurrenceCheckBox, recLamCheckBox, recRateCheckBox, recNeighborsNumCheckBox,recLMeanCheckBox, recDetCheckBox, recShannonCheckBox, forecastingCheckBox;   
	
	public PhaseSpaceFeaturesDialog (ExtractUnivariateFeaturesController univariateFeaturesController){
		EEGFrameMain.checkOnEventDispatchThread();	
		this.univariateFeaturesController = univariateFeaturesController;
		this.setTitle ("Phase space features");
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setPreferredSize(new Dimension(450,600));	
		this.setLayout(new BorderLayout());
		JPanel panel = addPhaseSpaceFeaturesPanel();
		this.add(panel, BorderLayout.CENTER);
	    this.setResizable(false);
	    this.setModal(true);
	    this.pack();
	}


	public JPanel addPhaseSpaceFeaturesPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel phaseSpaceDimensionPanel = new JPanel();
		phaseSpaceDimensionPanel.setLayout(new BoxLayout(phaseSpaceDimensionPanel, BoxLayout.X_AXIS));
		JLabel phaseSpaceDimensionLabel = new JLabel("Phase space dimension: ");
		phaseSpaceDimensionLabel.setPreferredSize(new Dimension(400, 20));
		phaseSpaceDimensionPanel.add(Box.createRigidArea(new Dimension(5,0)));
//		phaseSpaceDimensionPanel.add(Box.createHorizontalGlue());
		phaseSpaceDimensionPanel.add(phaseSpaceDimensionLabel);
		phaseSpaceDimensionPanel.add(Box.createHorizontalGlue());		
		phaseSpaceDimensionTextField = new JTextField("2");
		phaseSpaceDimensionTextField.setEnabled(true);
		phaseSpaceDimensionTextField.setPreferredSize(new Dimension(50,20));
		phaseSpaceDimensionTextField.setMaximumSize(new Dimension(50,20));
		phaseSpaceDimensionPanel.add(phaseSpaceDimensionTextField);
//		phaseSpaceDimensionTextField.setHorizontalAlignment(JTextField.RIGHT);
		phaseSpaceDimensionPanel.add(Box.createHorizontalGlue());
		panel.add(phaseSpaceDimensionPanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel phaseSpaceLagsPanel = new JPanel();
		phaseSpaceLagsPanel.setLayout(new BoxLayout(phaseSpaceLagsPanel, BoxLayout.X_AXIS));
		JLabel phaseSpaceLagsLabel = new JLabel("Phase space lag(s), comma separated: ");
		phaseSpaceLagsLabel.setPreferredSize(new Dimension(400, 20));
		phaseSpaceLagsPanel.add(Box.createRigidArea(new Dimension(5,0)));
//		phaseSpaceLagsPanel.add(Box.createHorizontalGlue());
		phaseSpaceLagsPanel.add(phaseSpaceLagsLabel);
		phaseSpaceLagsPanel.add(Box.createHorizontalGlue());		
		phaseSpaceLagsTextField = new JTextField("1");
		phaseSpaceLagsTextField.setEnabled(true);
		phaseSpaceLagsTextField.setPreferredSize(new Dimension(50,20));
		phaseSpaceLagsTextField.setMaximumSize(new Dimension(50,20));
		phaseSpaceLagsPanel.add(phaseSpaceLagsTextField);
		phaseSpaceLagsPanel.add(Box.createHorizontalGlue());
		panel.add(phaseSpaceLagsPanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		panel.add(new JSeparator());
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel stdDevRatioPanel = new JPanel();
		stdDevRatioPanel.setLayout(new BoxLayout(stdDevRatioPanel, BoxLayout.X_AXIS));
		JLabel standardDeviationRatioLabel = new JLabel("Standard deviation ratio SD1/SD2");
		standardDeviationRatioLabel.setPreferredSize(new Dimension(400, 20));
		standardDeviationRatioCheckBox = new JCheckBox();
		stdDevRatioPanel.add(Box.createRigidArea(new Dimension(5,0)));
//		stdDevRatioPanel.add(Box.createHorizontalGlue());
		stdDevRatioPanel.add(standardDeviationRatioLabel);
		stdDevRatioPanel.add(Box.createHorizontalGlue());
		stdDevRatioPanel.add(standardDeviationRatioCheckBox);
		stdDevRatioPanel.add(Box.createHorizontalGlue());
		panel.add(stdDevRatioPanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel SFIPanel = new JPanel();
		SFIPanel.setLayout(new BoxLayout(SFIPanel, BoxLayout.X_AXIS));
		JLabel SFILabel = new JLabel("Spatial filling index (SFI) ");
//		SFILabel.setPreferredSize(new Dimension(200, 20));
//		SFIPanel.add(Box.createHorizontalGlue());
		SFIPanel.add(Box.createRigidArea(new Dimension(5,0)));
		SFIPanel.add(SFILabel);
		SFIPanel.add(Box.createHorizontalGlue());	
		SFICheckBox = new JCheckBox();
		SFICheckBox.addItemListener(new ItemListener(){
            
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					SFITextField.setEnabled(true);
                }
				else{
					SFITextField.setEnabled(false);
				}
			}
		});
		SFIPanel.add(Box.createHorizontalGlue());
		SFIPanel.add(SFICheckBox);
		SFIPanel.add(Box.createHorizontalGlue());
		JLabel psDivisionLabel = new JLabel("PS Division: ");
		psDivisionLabel.setHorizontalAlignment(JLabel.RIGHT);
		SFIPanel.add(psDivisionLabel);
		SFIPanel.add(Box.createHorizontalGlue());
		SFITextField = new JTextField("10");
		SFITextField.setEnabled(false);
		SFITextField.setPreferredSize(new Dimension(50,20));
		SFITextField.setMaximumSize(new Dimension(50,20));
		SFIPanel.add(SFITextField);
		SFIPanel.add(Box.createHorizontalGlue());
		panel.add(SFIPanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
	
		JPanel ctmPanel = new JPanel();
		ctmPanel.setLayout(new BoxLayout(ctmPanel, BoxLayout.X_AXIS));
		JLabel ctmLabel = new JLabel("Central tendency measure (CTM) for second order difference space");
		ctmLabel.setPreferredSize(new Dimension(400, 20));
		ctmCheckBox = new JCheckBox();
//		ctmPanel.add(Box.createHorizontalGlue());
		ctmPanel.add(Box.createRigidArea(new Dimension(5,0)));
		ctmPanel.add(ctmLabel);
		ctmPanel.add(Box.createHorizontalGlue());
		ctmPanel.add(ctmCheckBox);
		ctmPanel.add(Box.createHorizontalGlue());
		panel.add(ctmPanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel llePanel = new JPanel();
		llePanel.setLayout(new BoxLayout(llePanel, BoxLayout.X_AXIS));
		JLabel lleLabel = new JLabel("Largest Lyapunov exponent (LLE)");
		lleLabel.setPreferredSize(new Dimension(400, 20));
		lleCheckBox = new JCheckBox();
//		llePanel.add(Box.createHorizontalGlue());
		llePanel.add(Box.createRigidArea(new Dimension(5,0)));
		llePanel.add(lleLabel);
		llePanel.add(Box.createHorizontalGlue());
		llePanel.add(lleCheckBox);
		llePanel.add(Box.createHorizontalGlue());
		panel.add(llePanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
			
		JPanel d2Panel = new JPanel();
		d2Panel.setLayout(new BoxLayout(d2Panel, BoxLayout.X_AXIS));
		JLabel d2Label = new JLabel("Correlation dimension (D2)");
		d2Label.setPreferredSize(new Dimension(400, 20));
		d2CheckBox = new JCheckBox();
//		d2Panel.add(Box.createHorizontalGlue());
		d2Panel.add(Box.createRigidArea(new Dimension(5,0)));
		d2Panel.add(d2Label);
		d2Panel.add(Box.createHorizontalGlue());
		d2Panel.add(d2CheckBox);
		d2Panel.add(Box.createHorizontalGlue());
		panel.add(d2Panel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel recurrencePanel = new JPanel();
		recurrencePanel.setLayout(new BoxLayout(recurrencePanel, BoxLayout.X_AXIS));
		JLabel recurrenceLabel = new JLabel("Recurrence plot features");
//		recurrenceLabel.setHorizontalAlignment(JLabel.LEFT);
		recurrenceLabel.setPreferredSize(new Dimension(400, 20));
		recurrenceCheckBox = new JCheckBox();
		recurrenceCheckBox.addItemListener(new ItemListener(){
            
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					setRecurrenceEnabled();
                }
				else{
					setRecurrenceDisabled();
				}
			}
		});
//		recurrencePanel.add(Box.createHorizontalGlue());
		recurrencePanel.add(Box.createRigidArea(new Dimension(5,0)));
		recurrencePanel.add(recurrenceLabel);
		recurrencePanel.add(Box.createHorizontalGlue());
		recurrencePanel.add(recurrenceCheckBox);
		recurrencePanel.add(Box.createHorizontalGlue());
		panel.add(recurrencePanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
//		panel.add(Box.createRigidArea(new Dimension(5,0)));
		JPanel recLamPanel = new JPanel();
		recLamPanel.setLayout(new BoxLayout(recLamPanel, BoxLayout.X_AXIS));
		JLabel recLamLabel = new JLabel("Recurrence Laminarity");
		recLamLabel.setPreferredSize(new Dimension(350, 20));
		recLamCheckBox = new JCheckBox();
		recLamCheckBox.setEnabled(false);
//		recLamPanel.add(Box.createHorizontalGlue());
		recLamPanel.add(Box.createRigidArea(new Dimension(50,0)));
		recLamPanel.add(recLamLabel);
		recLamPanel.add(Box.createHorizontalGlue());
		recLamPanel.add(recLamCheckBox);
		recLamPanel.add(Box.createHorizontalGlue());
		panel.add(recLamPanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
//		panel.add(Box.createRigidArea(new Dimension(5,0)));
		JPanel recRatePanel = new JPanel();
		recRatePanel.setLayout(new BoxLayout(recRatePanel, BoxLayout.X_AXIS));
		JLabel recRateLabel = new JLabel("Recurrence rate");
		recRateLabel.setPreferredSize(new Dimension(350, 20));
		recRateCheckBox = new JCheckBox();
		recRateCheckBox.setEnabled(false);
		recRatePanel.add(Box.createRigidArea(new Dimension(50,0)));
//		recRatePanel.add(Box.createHorizontalGlue());
		recRatePanel.add(recRateLabel);
		recRatePanel.add(Box.createHorizontalGlue());
		recRatePanel.add(recRateCheckBox);
		recRatePanel.add(Box.createHorizontalGlue());
		panel.add(recRatePanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
//		panel.add(Box.createRigidArea(new Dimension(5,0)));
		JPanel recNeighborsNumPanel = new JPanel();
		recNeighborsNumPanel.setLayout(new BoxLayout(recNeighborsNumPanel, BoxLayout.X_AXIS));
		JLabel recNeighborsNumLabel = new JLabel("Recurrence AVG num of neighbours");
		recNeighborsNumLabel.setPreferredSize(new Dimension(350, 20));
		recNeighborsNumCheckBox = new JCheckBox();
		recNeighborsNumCheckBox.setEnabled(false);
//		recNeighborsNumPanel.add(Box.createHorizontalGlue());
		recNeighborsNumPanel.add(Box.createRigidArea(new Dimension(50,0)));
		recNeighborsNumPanel.add(recNeighborsNumLabel);
		recNeighborsNumPanel.add(Box.createHorizontalGlue());
		recNeighborsNumPanel.add(recNeighborsNumCheckBox);
		recNeighborsNumPanel.add(Box.createHorizontalGlue());
		panel.add(recNeighborsNumPanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));

		JPanel recLMeanPanel = new JPanel();
		recLMeanPanel.setLayout(new BoxLayout(recLMeanPanel, BoxLayout.X_AXIS));
		JLabel recLMeanLabel = new JLabel("Recurrence LMean");
		recLMeanLabel.setPreferredSize(new Dimension(350, 20));
		recLMeanCheckBox = new JCheckBox();
		recLMeanCheckBox.setEnabled(false);
		recLMeanPanel.add(Box.createRigidArea(new Dimension(50,0)));
//		recLMeanPanel.add(Box.createHorizontalGlue());
		recLMeanPanel.add(recLMeanLabel);
		recLMeanPanel.add(Box.createHorizontalGlue());
		recLMeanPanel.add(recLMeanCheckBox);
		recLMeanPanel.add(Box.createHorizontalGlue());
		panel.add(recLMeanPanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel recDetPanel = new JPanel();
		recDetPanel.setLayout(new BoxLayout(recDetPanel, BoxLayout.X_AXIS));
		JLabel recDetLabel = new JLabel("Recurrence DET");
		recDetLabel.setPreferredSize(new Dimension(350, 20));
		recDetCheckBox = new JCheckBox();
		recDetCheckBox.setEnabled(false);
		recDetPanel.add(Box.createRigidArea(new Dimension(50,0)));
//		recDetPanel.add(Box.createHorizontalGlue());
		recDetPanel.add(recDetLabel);
		recDetPanel.add(Box.createHorizontalGlue());
		recDetPanel.add(recDetCheckBox);
		recDetPanel.add(Box.createHorizontalGlue());
		panel.add(recDetPanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel recShannonPanel = new JPanel();
		recShannonPanel.setLayout(new BoxLayout(recShannonPanel, BoxLayout.X_AXIS));
		JLabel recShannonLabel = new JLabel("Recurrence ShannonEn");
		recShannonLabel.setPreferredSize(new Dimension(350, 20));
		recShannonCheckBox = new JCheckBox();
		recShannonCheckBox.setEnabled(false);
		recShannonPanel.add(Box.createRigidArea(new Dimension(50,0)));
//		recShannonPanel.add(Box.createHorizontalGlue());
		recShannonPanel.add(recShannonLabel);
		recShannonPanel.add(Box.createHorizontalGlue());
		recShannonPanel.add(recShannonCheckBox);
		recShannonPanel.add(Box.createHorizontalGlue());
		panel.add(recShannonPanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel forecastingPanel = new JPanel();
		forecastingPanel.setLayout(new BoxLayout(forecastingPanel, BoxLayout.X_AXIS));
		JLabel forecastingLabel = new JLabel("Nonlinear forecasting");
//		forecastingLabel.setHorizontalAlignment(JLabel.LEFT);
		forecastingLabel.setPreferredSize(new Dimension(400, 20));
		forecastingCheckBox = new JCheckBox();
//		forecastingPanel.add(Box.createHorizontalGlue());
		forecastingPanel.add(Box.createRigidArea(new Dimension(5,0)));
		forecastingPanel.add(forecastingLabel);
		forecastingPanel.add(Box.createHorizontalGlue());
		forecastingPanel.add(forecastingCheckBox);
		forecastingCheckBox.addItemListener(new ItemListener(){
            
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					forecastingTimeTextField.setEnabled(true);
                }
				else{
					forecastingTimeTextField.setEnabled(false);
				}
			}
		});
		forecastingPanel.add(Box.createHorizontalGlue());
		panel.add(forecastingPanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel nonlinearTimePanel = new JPanel();
		nonlinearTimePanel.setLayout(new BoxLayout(nonlinearTimePanel, BoxLayout.X_AXIS));
		JLabel nonlinearTimeLabel = new JLabel("T in samples, comma separated: ");
		nonlinearTimeLabel.setPreferredSize(new Dimension(300, 20));		
		nonlinearTimePanel.add(Box.createRigidArea(new Dimension(30,0)));
		nonlinearTimePanel.add(nonlinearTimeLabel);
		nonlinearTimePanel.add(Box.createHorizontalGlue());
		forecastingTimeTextField = new JTextField("1");
		forecastingTimeTextField.setEnabled(false);
		forecastingTimeTextField.setPreferredSize(new Dimension(100,20));
		forecastingTimeTextField.setMaximumSize(new Dimension(100,20));
		nonlinearTimePanel.add(forecastingTimeTextField);
		nonlinearTimePanel.add(Box.createHorizontalGlue());
		panel.add(nonlinearTimePanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		
		panel.add(new JSeparator());
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		JPanel buttonPanel = new JPanel();
////		buttonPanel.add(BorderFactory.createEmptyBorder(0, 20, 0, 0)
		panel.add(buttonPanel);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(Box.createHorizontalGlue());
		JButton selectAllButton = new JButton("Select all");
		selectAllButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				standardDeviationRatioCheckBox.setSelected(true);
				ctmCheckBox.setSelected(true);
				SFICheckBox.setSelected(true);
				lleCheckBox.setSelected(true);
				recurrenceCheckBox.setSelected(true);
				recLamCheckBox.setSelected(true);
				recRateCheckBox.setSelected(true);
				recNeighborsNumCheckBox.setSelected(true);
				recLMeanCheckBox.setSelected(true);
				recDetCheckBox.setSelected(true);
				recShannonCheckBox.setSelected(true); 
				d2CheckBox.setSelected(true); 
				forecastingCheckBox.setSelected(true);
				
			}
		});
		buttonPanel.add(selectAllButton);
		buttonPanel.add(Box.createHorizontalGlue());
		JButton clearAllButton = new JButton("Clear all");
		clearAllButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				standardDeviationRatioCheckBox.setSelected(false);
				ctmCheckBox.setSelected(false);
				SFICheckBox.setSelected(false);
				lleCheckBox.setSelected(false);
				recurrenceCheckBox.setSelected(false);
				recLamCheckBox.setSelected(false);
				recRateCheckBox.setSelected(false);
				recNeighborsNumCheckBox.setSelected(false);
				recLMeanCheckBox.setSelected(false);
				recDetCheckBox.setSelected(false);
				recShannonCheckBox.setSelected(false); 
				d2CheckBox.setSelected(false); 
				forecastingCheckBox.setSelected(false);
				
			}
		});
		buttonPanel.add(clearAllButton);
		buttonPanel.add(Box.createHorizontalGlue());
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Features f = univariateFeaturesController.getSelectedFeatures().get(0);
				
				if(standardDeviationRatioCheckBox.isSelected()){
					f.getFeatures().put(UnivariateFeatures.SD1_SD2, true);
				}
				if(ctmCheckBox.isSelected()){
					f.getFeatures().put(UnivariateFeatures.CTM_PHASE_SPACE, true);
				}
				if(SFICheckBox.isSelected()){
					f.getFeatures().put(UnivariateFeatures.SFI, true);
				}
				if(lleCheckBox.isSelected()){
					f.getFeatures().put(UnivariateFeatures.LLE, true);
				}
				if(d2CheckBox.isSelected()){
					f.getFeatures().put(UnivariateFeatures.CORRELATION_DIM, true);
				}
				if(recurrenceCheckBox.isSelected()){
					f.getFeatures().put(UnivariateFeatures.RECURRENCE_PLOT_FEATURES, true);
				}
				if(recLamCheckBox.isSelected()){
					f.getFeatures().put(UnivariateFeatures.REC_LAM, true);
				}
				if(recRateCheckBox.isSelected()){
					f.getFeatures().put(UnivariateFeatures.REC_RATE, true);
				}
				if(recNeighborsNumCheckBox.isSelected()){
					f.getFeatures().put(UnivariateFeatures.REC_AVG_NEIGHBOURS_NUM, true);
				}
				if(recLMeanCheckBox.isSelected()){
					f.getFeatures().put(UnivariateFeatures.REC_LMEAN, true);
				}
				if(recDetCheckBox.isSelected()){
					f.getFeatures().put(UnivariateFeatures.REC_DET, true);
				}
				if(recShannonCheckBox.isSelected()){
					f.getFeatures().put(UnivariateFeatures.REC_SHANNON, true);
				}
				if(forecastingCheckBox.isSelected()){
					f.getFeatures().put(UnivariateFeatures.NLPE, true);
				}
				setVisible(false);
			}
		});
		buttonPanel.add(okButton);
		buttonPanel.add(Box.createHorizontalGlue());
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		return panel;
	}
	
	public void setRecurrenceDisabled(){
		recLamCheckBox.setEnabled(false);
		recRateCheckBox.setEnabled(false);
		recNeighborsNumCheckBox.setEnabled(false);
		recLMeanCheckBox.setEnabled(false);
		recDetCheckBox.setEnabled(false);
		recShannonCheckBox.setEnabled(false);
	}
	public void setRecurrenceEnabled(){
		recLamCheckBox.setEnabled(true);
		recRateCheckBox.setEnabled(true);
		recNeighborsNumCheckBox.setEnabled(true);
		recLMeanCheckBox.setEnabled(true);
		recDetCheckBox.setEnabled(true);
		recShannonCheckBox.setEnabled(true);
	}

	public JTextField getPhaseSpaceDimensionTextField() {
		return phaseSpaceDimensionTextField;
	}

	public void setPhaseSpaceDimensionTextField(
			JTextField phaseSpaceDimensionTextField) {
		this.phaseSpaceDimensionTextField = phaseSpaceDimensionTextField;
	}

	public JTextField getPhaseSpaceLagsTextField() {
		return phaseSpaceLagsTextField;
	}

	public void setPhaseSpaceLagsTextField(JTextField phaseSpaceLagsTextField) {
		this.phaseSpaceLagsTextField = phaseSpaceLagsTextField;
	}

	public JTextField getSFITextField() {
		return SFITextField;
	}

	public void setSFITextField(JTextField sFITextField) {
		SFITextField = sFITextField;
	}
}

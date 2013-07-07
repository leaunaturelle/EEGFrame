/**
 * 
 */
package gui.Dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

import features.nonlinear.multiSeries.MutualDimension;
import features.output.ExtractMultivariateFeaturesController;
import features.output.Features;
import features.output.MultivariateFeatures;
import gui.EEGFrameMain;
import gui.SelectedSignal;

/**
 * @author lsuc
 *
 */
public class NonlinearMultivariateFeaturesDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3736711491186323510L;

	private ExtractMultivariateFeaturesController multivariateController;
	private JCheckBox crpCheckBox, crpLamCheckBox, crpRateCheckBox, crpLMeanCheckBox, crpDetCheckBox, crpShannonCheckBox, mutualDimCheckBox;
	private JTextField  dimensionTextField, lagsTextField, dimension2TextField, lags2TextField, finesseTextField;
	private JList signalsLabelList;
	
	public NonlinearMultivariateFeaturesDialog(ExtractMultivariateFeaturesController multivariateController){
		EEGFrameMain.checkOnEventDispatchThread();			
		this.setTitle ("Multivariate nonlinear features");
		this.multivariateController = multivariateController;
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setPreferredSize(new Dimension(500,600));	
		this.setLayout(new BorderLayout());
		JPanel panel = addNonlinearMultivariateFeaturesPanel();
		this.add(panel, BorderLayout.CENTER);
	    this.setResizable(false);
	    this.setModal(true);
	    this.pack();
	}
	
	public JPanel addNonlinearMultivariateFeaturesPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel dimensionPanel = new JPanel();
		dimensionPanel.setLayout(new BoxLayout(dimensionPanel, BoxLayout.X_AXIS));
		JLabel dimensionLabel = new JLabel("Phase space dimension 1: ");
		dimensionLabel.setPreferredSize(new Dimension(400, 20));
//		mutualDimDimensionPanel.add(Box.createRigidArea(new Dimension(10,0)));
		dimensionPanel.add(Box.createHorizontalGlue());
		dimensionPanel.add(Box.createRigidArea(new Dimension(5,0)));
		dimensionPanel.add(dimensionLabel);
		dimensionPanel.add(Box.createHorizontalGlue());		
		dimensionTextField = new JTextField("2");
//		dimensionTextField.setEnabled(false);
		dimensionTextField.setPreferredSize(new Dimension(50,20));
		dimensionTextField.setMaximumSize(new Dimension(50,20));
		dimensionPanel.add(dimensionTextField);
//		mutualDimDimensionTextField.setHorizontalAlignment(JTextField.RIGHT);
		dimensionPanel.add(Box.createHorizontalGlue());
//		panel.add(Box.createRigidArea(new Dimension(10,0)));
		panel.add(dimensionPanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel lagsPanel = new JPanel();
		lagsPanel.setLayout(new BoxLayout(lagsPanel, BoxLayout.X_AXIS));
		JLabel lagsLabel = new JLabel("Phase space lag(s), comma separated: ");
		lagsLabel.setPreferredSize(new Dimension(400, 20));
//		mutualDimLagsPanel.add(Box.createRigidArea(new Dimension(10,0)));
		lagsPanel.add(Box.createHorizontalGlue());
		lagsPanel.add(Box.createRigidArea(new Dimension(5,0)));
		lagsPanel.add(lagsLabel);
		lagsPanel.add(Box.createHorizontalGlue());		
		lagsTextField = new JTextField("1");
//		lagsTextField.setEnabled(false);
		lagsTextField.setPreferredSize(new Dimension(50,20));
		lagsTextField.setMaximumSize(new Dimension(50,20));
		lagsPanel.add(lagsTextField);
		lagsPanel.add(Box.createHorizontalGlue());
//		panel.add(Box.createRigidArea(new Dimension(10,0)));
		panel.add(lagsPanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel dimension2Panel = new JPanel();
		dimension2Panel.setLayout(new BoxLayout(dimension2Panel, BoxLayout.X_AXIS));
		JLabel dimension2Label = new JLabel("Phase space dimension 2: ");
		dimension2Label.setPreferredSize(new Dimension(400, 20));
//		mutualDimDimension2Panel.add(Box.createRigidArea(new Dimension(10,0)));
		dimension2Panel.add(Box.createHorizontalGlue());
		dimension2Panel.add(Box.createRigidArea(new Dimension(5,0)));
		dimension2Panel.add(dimension2Label);
		dimension2Panel.add(Box.createHorizontalGlue());		
		dimension2TextField = new JTextField("2");
//		dimension2TextField.setEnabled(false);
		dimension2TextField.setPreferredSize(new Dimension(50,20));
		dimension2TextField.setMaximumSize(new Dimension(50,20));
		dimension2Panel.add(dimension2TextField);
//		mutualDimDimensionTextField.setHorizontalAlignment(JTextField.RIGHT);
		dimension2Panel.add(Box.createHorizontalGlue());
		panel.add(Box.createRigidArea(new Dimension(10,0)));
		panel.add(dimension2Panel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel lags2Panel = new JPanel();
		lags2Panel.setLayout(new BoxLayout(lags2Panel, BoxLayout.X_AXIS));
		JLabel lags2Label = new JLabel("Phase space lag(s), comma separated: ");
		lags2Label.setPreferredSize(new Dimension(400, 20));
//		mutualDimLags2Panel.add(Box.createRigidArea(new Dimension(10,0)));
		lags2Panel.add(Box.createHorizontalGlue());
		lags2Panel.add(Box.createRigidArea(new Dimension(5,0)));
		lags2Panel.add(lags2Label);
		lags2Panel.add(Box.createHorizontalGlue());		
		lags2TextField = new JTextField("1");
//		lags2TextField.setEnabled(false);
		lags2TextField.setPreferredSize(new Dimension(50,20));
		lags2TextField.setMaximumSize(new Dimension(50,20));
		lags2Panel.add(lags2TextField);
		lags2Panel.add(Box.createHorizontalGlue());
//		panel.add(Box.createRigidArea(new Dimension(10,0)));
		panel.add(lags2Panel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel mutualDimPanel = new JPanel();
		mutualDimPanel.setLayout(new BoxLayout(mutualDimPanel, BoxLayout.X_AXIS));
		JLabel mutualDimLabel = new JLabel("Mutual dimension");
		mutualDimLabel.setPreferredSize(new Dimension(400, 20));
		mutualDimCheckBox = new JCheckBox();
		mutualDimCheckBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					finesseTextField.setEnabled(true);
					if(signalsLabelList.getModel().getSize() == 0){
						SelectedSignal[] signals = multivariateController.getExtractMixedFeaturesController().getExtractUnivariateFeaturesController().getExtractUnivariateFeaturesWindow().getSignalsList();
						setSignalsLabelList(signals);						
					}
					
				}
				else{
					finesseTextField.setEnabled(false);
				}
			}
		});
		mutualDimPanel.add(Box.createHorizontalGlue());
		mutualDimPanel.add(Box.createRigidArea(new Dimension(5,0)));
		mutualDimPanel.add(mutualDimLabel);
		mutualDimPanel.add(Box.createHorizontalGlue());
		mutualDimPanel.add(mutualDimCheckBox);
		mutualDimPanel.add(Box.createHorizontalGlue());
		panel.add(mutualDimPanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel finessePanel = new JPanel();
		finessePanel.setLayout(new BoxLayout(finessePanel, BoxLayout.X_AXIS));
		JLabel finesseLabel = new JLabel("Finesse: ");
		finesseLabel.setPreferredSize(new Dimension(250, 20));
//		mutualDimDimensionPanel.add(Box.createRigidArea(new Dimension(10,0)));
		finessePanel.add(Box.createHorizontalGlue());
		finessePanel.add(finesseLabel);
		finessePanel.add(Box.createHorizontalGlue());		
		finesseTextField = new JTextField(Integer.toString(MutualDimension.FINESSE));
		finesseTextField.setEnabled(false);
		finesseTextField.setPreferredSize(new Dimension(50,20));
		finesseTextField.setMaximumSize(new Dimension(50,20));
		finessePanel.add(finesseTextField);
//		mutualDimDimensionTextField.setHorizontalAlignment(JTextField.RIGHT);
		finessePanel.add(Box.createHorizontalGlue());
		panel.add(Box.createRigidArea(new Dimension(10,0)));
		panel.add(finessePanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		
		
		
//		panel.add(new JSeparator());
//		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel crpPanel = new JPanel();
		crpPanel.setLayout(new BoxLayout(crpPanel, BoxLayout.X_AXIS));
		JLabel crpLabel = new JLabel("Cross recurrence plot features");

		crpLabel.setPreferredSize(new Dimension(400, 20));
		crpCheckBox = new JCheckBox();
		crpCheckBox.addItemListener(new ItemListener(){
            
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					setcrpEnabled();
					if(signalsLabelList.getModel().getSize() == 0){
						SelectedSignal[] signals = multivariateController.getExtractMixedFeaturesController().getExtractUnivariateFeaturesController().getExtractUnivariateFeaturesWindow().getSignalsList();
						setSignalsLabelList(signals);						
					}
                }
				else{
					setcrpDisabled();
				}
			}
		});
		crpPanel.add(Box.createHorizontalGlue());
		crpPanel.add(Box.createRigidArea(new Dimension(5,0)));
		crpPanel.add(crpLabel);
		crpPanel.add(Box.createHorizontalGlue());
		crpPanel.add(crpCheckBox);
		crpPanel.add(Box.createHorizontalGlue());
		panel.add(crpPanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
//		panel.add(Box.createRigidArea(new Dimension(5,0)));
		JPanel crpLamPanel = new JPanel();
		crpLamPanel.setLayout(new BoxLayout(crpLamPanel, BoxLayout.X_AXIS));
		JLabel crpLamLabel = new JLabel("Cross recurrence laminarity");
		crpLamLabel.setPreferredSize(new Dimension(350, 20));
		crpLamCheckBox = new JCheckBox();
		crpLamCheckBox.setEnabled(false);
//		crpLamPanel.add(Box.createHorizontalGlue());
		crpLamPanel.add(Box.createRigidArea(new Dimension(50,0)));
		crpLamPanel.add(crpLamLabel);
		crpLamPanel.add(Box.createHorizontalGlue());
		crpLamPanel.add(crpLamCheckBox);
		crpLamPanel.add(Box.createHorizontalGlue());
		panel.add(crpLamPanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
//		panel.add(Box.createRigidArea(new Dimension(5,0)));
		JPanel crpRatePanel = new JPanel();
		crpRatePanel.setLayout(new BoxLayout(crpRatePanel, BoxLayout.X_AXIS));
		JLabel crpRateLabel = new JLabel("Cross recurrence rate");
		crpRateLabel.setPreferredSize(new Dimension(350, 20));
		crpRateCheckBox = new JCheckBox();
		crpRateCheckBox.setEnabled(false);
		crpRatePanel.add(Box.createRigidArea(new Dimension(50,0)));
//		crpRatePanel.add(Box.createHorizontalGlue());
		crpRatePanel.add(crpRateLabel);
		crpRatePanel.add(Box.createHorizontalGlue());
		crpRatePanel.add(crpRateCheckBox);
		crpRatePanel.add(Box.createHorizontalGlue());
		panel.add(crpRatePanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));

		JPanel crpLMeanPanel = new JPanel();
		crpLMeanPanel.setLayout(new BoxLayout(crpLMeanPanel, BoxLayout.X_AXIS));
		JLabel crpLMeanLabel = new JLabel("Cross recurrence LMean");
		crpLMeanLabel.setPreferredSize(new Dimension(350, 20));
		crpLMeanCheckBox = new JCheckBox();
		crpLMeanCheckBox.setEnabled(false);
		crpLMeanPanel.add(Box.createRigidArea(new Dimension(50,0)));
//		crpLMeanPanel.add(Box.createHorizontalGlue());
		crpLMeanPanel.add(crpLMeanLabel);
		crpLMeanPanel.add(Box.createHorizontalGlue());
		crpLMeanPanel.add(crpLMeanCheckBox);
		crpLMeanPanel.add(Box.createHorizontalGlue());
		panel.add(crpLMeanPanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel crpDetPanel = new JPanel();
		crpDetPanel.setLayout(new BoxLayout(crpDetPanel, BoxLayout.X_AXIS));
		JLabel crpDetLabel = new JLabel("Cross recurrence DET");
		crpDetLabel.setPreferredSize(new Dimension(350, 20));
		crpDetCheckBox = new JCheckBox();
		crpDetCheckBox.setEnabled(false);
		crpDetPanel.add(Box.createRigidArea(new Dimension(50,0)));
//		crpDetPanel.add(Box.createHorizontalGlue());
		crpDetPanel.add(crpDetLabel);
		crpDetPanel.add(Box.createHorizontalGlue());
		crpDetPanel.add(crpDetCheckBox);
		crpDetPanel.add(Box.createHorizontalGlue());
		panel.add(crpDetPanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel crpShannonPanel = new JPanel();
		crpShannonPanel.setLayout(new BoxLayout(crpShannonPanel, BoxLayout.X_AXIS));
		JLabel crpShannonLabel = new JLabel("Cross recurrence ShannonEn");
		crpShannonLabel.setPreferredSize(new Dimension(350, 20));
		crpShannonCheckBox = new JCheckBox();
		crpShannonCheckBox.setEnabled(false);
		crpShannonPanel.add(Box.createRigidArea(new Dimension(50,0)));
//		crpShannonPanel.add(Box.createHorizontalGlue());
		crpShannonPanel.add(crpShannonLabel);
		crpShannonPanel.add(Box.createHorizontalGlue());
		crpShannonPanel.add(crpShannonCheckBox);
		crpShannonPanel.add(Box.createHorizontalGlue());
		panel.add(crpShannonPanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel signalSelectionPanel = new JPanel();
		signalSelectionPanel.setLayout(new BoxLayout(signalSelectionPanel, BoxLayout.X_AXIS));
		signalsLabelList = new JList(new DefaultListModel());
//		signalsLabelList.setPreferredSize(new Dimension(50,10));
		signalsLabelList.setVisibleRowCount(5);
		signalsLabelList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JScrollPane signalsLabelSrollPane = new JScrollPane(signalsLabelList);	
		signalsLabelSrollPane.setBorder(BorderFactory.createTitledBorder("Select signals: "));
		signalsLabelList.setCellRenderer(new DefaultListCellRenderer(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				SelectedSignal[] toBeRendered = (SelectedSignal[]) value;
				StringBuilder b = new StringBuilder();
				for(int i = 0; i < toBeRendered.length-1; i++){
					b.append(toBeRendered[i].getSignalLabel().trim());
					b.append("_");
				}
				b.append(toBeRendered[toBeRendered.length-1].getSignalLabel().trim());			
				setText(b.toString());
				if(isSelected){
					setBackground(Color.LIGHT_GRAY);
				}
				else{
					setBackground(null);
				}
				return this;
	        }
		});
		signalSelectionPanel.add(Box.createHorizontalGlue());
		signalSelectionPanel.add(signalsLabelSrollPane);	
		signalSelectionPanel.add(Box.createHorizontalGlue());
		JButton selectAllSignalsButton = new JButton("Select All");
		selectAllSignalsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int start = 0;
			    int end = signalsLabelList.getModel().getSize() - 1;
			    if (end >= 0) {
			    	signalsLabelList.setSelectionInterval(start, end);
			    }
			}
		});
		signalSelectionPanel.add(selectAllSignalsButton);
		signalSelectionPanel.add(Box.createHorizontalGlue());
		panel.add(signalSelectionPanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
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
				crpCheckBox.setSelected(true);
				crpLamCheckBox.setSelected(true);
				crpRateCheckBox.setSelected(true);
				crpLMeanCheckBox.setSelected(true);
				crpDetCheckBox.setSelected(true);
				crpShannonCheckBox.setSelected(true);
				mutualDimCheckBox.setSelected(true);
			}
		});
		buttonPanel.add(selectAllButton);
		buttonPanel.add(Box.createHorizontalGlue());
		JButton clearAllButton = new JButton("Clear all");
		clearAllButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				crpCheckBox.setSelected(false);
				crpLamCheckBox.setSelected(false);
				crpRateCheckBox.setSelected(false);
				crpLMeanCheckBox.setSelected(false);
				crpDetCheckBox.setSelected(false);
				crpShannonCheckBox.setSelected(false);
				mutualDimCheckBox.setSelected(false);				
			}
		});
		buttonPanel.add(clearAllButton);
		buttonPanel.add(Box.createHorizontalGlue());
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(signalsLabelList.getModel().getSize() > 0 && signalsLabelList.getSelectedValues().length > 0){
					Features f = multivariateController.getSelectedFeatures().get(0);
					
					if(mutualDimCheckBox.isSelected()){
						f.getFeatures().put(MultivariateFeatures.MUTUAL_DIM, true);
					}
					if(crpCheckBox.isSelected()){
						f.getFeatures().put(MultivariateFeatures.CROSS_RECURRENCE, true);
						if(crpRateCheckBox.isSelected()){
							f.getFeatures().put(MultivariateFeatures.CRP_RATE, true);
						}
						if(crpLamCheckBox.isSelected()){
							f.getFeatures().put(MultivariateFeatures.CRP_LAM, true);
						}
						if(crpLMeanCheckBox.isSelected()){
							f.getFeatures().put(MultivariateFeatures.CRP_LMEAN, true);
						}
						if(crpDetCheckBox.isSelected()){
							f.getFeatures().put(MultivariateFeatures.CRP_DET, true);
						}
						if(crpShannonCheckBox.isSelected()){
							f.getFeatures().put(MultivariateFeatures.CRP_SHANNON, true);
						}
					}
					ArrayList<SelectedSignal[]> signalCombinations = new ArrayList<SelectedSignal[]>();						
					for(int i = 0; i < signalsLabelList.getSelectedValues().length; i++){						
						SelectedSignal[] signals = (SelectedSignal[])signalsLabelList.getSelectedValues()[i];
						signalCombinations.add(signals);	
					}
					multivariateController.getSelectedFeatures().get(0).setSignals( signalCombinations);
					setVisible(false);
				}
				else{
					JOptionPane.showMessageDialog(null, "At least one signal should be selected!", "Extraction error", JOptionPane.ERROR_MESSAGE);
				}
				
			
			}
		});
		buttonPanel.add(okButton);
		buttonPanel.add(Box.createHorizontalGlue());
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		return panel;
	}
//	public void enableMutualDimTextFields(){
//		dimensionTextField.setEnabled(true);
//		lagsTextField.setEnabled(true);
//		dimension2TextField.setEnabled(true);
//		lags2TextField.setEnabled(true);
//		
//	}
//	public void disableMutualDimTextFields(){
//		dimensionTextField.setEnabled(false);
//		lagsTextField.setEnabled(false);
//		dimension2TextField.setEnabled(false);
//		lags2TextField.setEnabled(false);
//	}
	
	public void setcrpDisabled(){
		crpLamCheckBox.setEnabled(false);
		crpRateCheckBox.setEnabled(false);
		crpLMeanCheckBox.setEnabled(false);
		crpDetCheckBox.setEnabled(false);
		crpShannonCheckBox.setEnabled(false);
	}
	public void setcrpEnabled(){
		crpLamCheckBox.setEnabled(true);
		crpRateCheckBox.setEnabled(true);
		crpLMeanCheckBox.setEnabled(true);
		crpDetCheckBox.setEnabled(true);
		crpShannonCheckBox.setEnabled(true);
	}
	public JTextField getDimensionTextField() {
		return dimensionTextField;
	}

	public void setDimensionTextField(JTextField dimensionTextField) {
		this.dimensionTextField = dimensionTextField;
	}

	public JTextField getLagsTextField() {
		return lagsTextField;
	}

	public void setLagsTextField(JTextField lagsTextField) {
		this.lagsTextField = lagsTextField;
	}

	public JTextField getDimension2TextField() {
		return dimension2TextField;
	}

	public void setDimension2TextField(JTextField dimension2TextField) {
		this.dimension2TextField = dimension2TextField;
	}

	public JTextField getLags2TextField() {
		return lags2TextField;
	}

	public void setLags2TextField(JTextField lags2TextField) {
		this.lags2TextField = lags2TextField;
	}

	public JTextField getFinesseTextField() {
		return finesseTextField;
	}

	public void setFinesseTextField(JTextField finesseTextField) {
		this.finesseTextField = finesseTextField;
	}
	public void setSignalsLabelList(SelectedSignal[] signals) {
		DefaultListModel signalsLabelModel = (DefaultListModel) this.signalsLabelList.getModel();
		signalsLabelModel.clear();
		ArrayList<SelectedSignal[]> signalIndicesCombinations = multivariateController.processSubsets(signals, 2);
		
		for(int i = 0; i < signalIndicesCombinations.size(); i++){
			SelectedSignal[] s = signalIndicesCombinations.get(i);
			signalsLabelModel.addElement(s);
		}
	}

}

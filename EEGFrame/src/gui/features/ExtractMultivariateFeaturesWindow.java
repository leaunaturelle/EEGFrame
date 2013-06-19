/**
 * 
 */
package gui.features;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

import features.output.ExtractMultivariateFeaturesController;
import gui.EEGFrameMain;
import gui.SelectedSignal;

/**
 * @author lsuc
 *
 */
public class ExtractMultivariateFeaturesWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3112268096700801149L;
	
	private ExtractMultivariateFeaturesController multivariateController;
	private JList signalsLabelList;
	private JRadioButton appendButton, createButton;
	private NonlinearMultivariateFeaturesDialog nonlinearMultivariateFeaturesDialog;
	
	private AnalysisMultivariateParametersDialog analysisMultivariateParametersDialog;
	
	public ExtractMultivariateFeaturesWindow(ExtractMultivariateFeaturesController multivariateController){
		EEGFrameMain.checkOnEventDispatchThread();
		this.multivariateController = multivariateController;
		this.analysisMultivariateParametersDialog = new AnalysisMultivariateParametersDialog(this);
		this.setTitle ("Features extraction");
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.setPreferredSize(new Dimension(550,500));	
		this.setLocation(0, 0);	
		this.setLayout(new BorderLayout());
		JPanel panel = addFeaturesExtractionPanel();
		this.add(panel, BorderLayout.CENTER);
	    this.setResizable(false);
	    this.pack();
	}
	private JPanel addFeaturesExtractionPanel(){
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel signalsLabelPanel = new JPanel();
		signalsLabelPanel.setLayout(new BoxLayout(signalsLabelPanel, BoxLayout.X_AXIS));
		JLabel signalsLabel = new JLabel("Signals for features extraction:");
		signalsLabelPanel.add(Box.createRigidArea(new Dimension(5,0)));
		signalsLabelPanel.add(signalsLabel);
		signalsLabelPanel.add(Box.createHorizontalGlue());
		
		panel.add(signalsLabelPanel);
		panel.add(Box.createRigidArea(new Dimension(5,5)));
		
		JPanel signalSelectionPanel = new JPanel();
		signalSelectionPanel.setLayout(new BoxLayout(signalSelectionPanel, BoxLayout.X_AXIS));
		signalsLabelList = new JList(new DefaultListModel());
//		signalsLabelList.setPreferredSize(new Dimension(50,10));
		signalsLabelList.setVisibleRowCount(5);
		signalsLabelList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JScrollPane signalsLabelSrollPane = new JScrollPane(signalsLabelList);	
		signalsLabelSrollPane.setBorder(BorderFactory.createTitledBorder("Select signals for feature extraction: "));
		signalsLabelList.setCellRenderer(new DefaultListCellRenderer(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				SelectedSignal toBeRendered = (SelectedSignal) value;
				setText(toBeRendered.getFile().getName() +": " +toBeRendered.getSignalLabel());
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
		
		JPanel analysisParametersPanel = new JPanel();
		analysisParametersPanel.setLayout(new BoxLayout(analysisParametersPanel, BoxLayout.X_AXIS));
		JLabel analysisParametersLabel = new JLabel("Analysis parameters:");
		analysisParametersPanel.add(Box.createRigidArea(new Dimension(5,0)));
		analysisParametersPanel.add(Box.createHorizontalGlue());
		analysisParametersPanel.add(analysisParametersLabel);
		analysisParametersPanel.add(Box.createHorizontalGlue());
		JButton selectAnalysisParametersButton = new JButton("Select parameters");
		selectAnalysisParametersButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				showAnalysisMultivariateParametersDialog();
			}
		});
		analysisParametersPanel.add(selectAnalysisParametersButton);
		analysisParametersPanel.add(Box.createHorizontalGlue());
		
		panel.add(new JSeparator());
		panel.add(Box.createRigidArea(new Dimension(10,20)));
		panel.add(analysisParametersPanel);
		panel.add(Box.createRigidArea(new Dimension(10,20)));
		
		JPanel multivariateFeaturesPanel = new JPanel();
		multivariateFeaturesPanel.setLayout(new BoxLayout(multivariateFeaturesPanel , BoxLayout.Y_AXIS));
		Border etchedMultivariate = BorderFactory.createEtchedBorder();
		Border titledMultivariate = BorderFactory.createTitledBorder(etchedMultivariate,"Multivariate features");
		multivariateFeaturesPanel .setBorder(titledMultivariate);
		panel.add(multivariateFeaturesPanel);
		
		JPanel nonlinearMultivariatePanel = new JPanel();
		multivariateFeaturesPanel.add(nonlinearMultivariatePanel);
		nonlinearMultivariatePanel.setLayout(new BoxLayout(nonlinearMultivariatePanel, BoxLayout.X_AXIS));
		JLabel nonlinearMultivariateLabel = new JLabel("Nonlinear - Multivariate features");
		nonlinearMultivariateLabel.setPreferredSize(new Dimension(300, 15));
		nonlinearMultivariatePanel.add(Box.createHorizontalGlue());
		nonlinearMultivariatePanel.add(nonlinearMultivariateLabel);
		nonlinearMultivariatePanel.add(Box.createHorizontalGlue());
		JButton nonlinearMultivariateButton = new JButton("Select");
		nonlinearMultivariateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				showNonlinearMultivariateFeaturesDialog();
				
			}
		});
		nonlinearMultivariatePanel.add(nonlinearMultivariateButton);
		nonlinearMultivariatePanel.add(Box.createHorizontalGlue());
		multivariateFeaturesPanel.add(Box.createRigidArea(new Dimension(5,10)));

		JPanel appendCreatePanel = new JPanel();
		appendCreatePanel.setLayout(new BoxLayout(appendCreatePanel, BoxLayout.X_AXIS));
		appendCreatePanel.add(Box.createHorizontalGlue());
		appendButton = new JRadioButton("Append features to existing file");
		appendCreatePanel.add(appendButton);
		appendCreatePanel.add(Box.createHorizontalGlue());
		createButton = new JRadioButton("Extract features to new file");
		appendCreatePanel.add(createButton);
		appendCreatePanel.add(Box.createHorizontalGlue());
	
		ButtonGroup appendCreateGroup = new ButtonGroup();
		appendCreateGroup.add(appendButton);
		appendCreateGroup.add(createButton);
		appendButton.setSelected(true);
		panel.add(Box.createRigidArea(new Dimension(5, 15)));	
		panel.add(appendCreatePanel);
		panel.add(Box.createRigidArea(new Dimension(5,15)));	
		panel.add(new JSeparator());
		panel.add(Box.createRigidArea(new Dimension(5,15)));
		
		JPanel extractFeaturesPanel = new JPanel();
		panel.add(extractFeaturesPanel);
		extractFeaturesPanel.setLayout(new BoxLayout(extractFeaturesPanel, BoxLayout.X_AXIS));
		extractFeaturesPanel.add(Box.createHorizontalGlue());
		JButton resetSelectionButton = new JButton("Reset selection");
		extractFeaturesPanel.add(resetSelectionButton);
		extractFeaturesPanel.add(Box.createHorizontalGlue());
		JButton saveSelectionButton = new JButton("Save selection");
		extractFeaturesPanel.add(saveSelectionButton);
		extractFeaturesPanel.add(Box.createHorizontalGlue());
		JButton extractFeaturesButton = new JButton("Extract features");
		extractFeaturesButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(signalsLabelList.getSelectedValues() != null && signalsLabelList.getSelectedValues().length > 1){
					
					analysisMultivariateParametersDialog.saveInterval(multivariateController);
					SelectedSignal[] signals = new SelectedSignal[signalsLabelList.getSelectedValues().length];
					for(int i = 0; i < signalsLabelList.getSelectedValues().length; i++){
						signals[i] = (SelectedSignal)signalsLabelList.getSelectedValues()[i];
					}
					multivariateController.getSelectedFeatures().get(0).setSignals(signals);
					if(createButton.isSelected()){
						multivariateController.beginFeatureExtraction(signals, false);
					}
					else {
						multivariateController.beginFeatureExtraction(signals, true);
					}
					setVisible(false);
					
				}
				else{
					JOptionPane.showMessageDialog(null, "At least two signals should be selected!", "Extraction error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		extractFeaturesPanel.add(extractFeaturesButton);
		extractFeaturesPanel.add(Box.createHorizontalGlue());
		panel.add(Box.createRigidArea(new Dimension(5,15)));
		
		return panel;
	}
	
	public void setSignalsLabelList(SelectedSignal[] signals) {
		DefaultListModel signalsLabelModel = (DefaultListModel) this.signalsLabelList.getModel();
		signalsLabelModel.clear();
		for(int i = 0; i < signals.length; i++){
			signalsLabelModel.addElement(signals[i]);
		}
	}
	private synchronized void showNonlinearMultivariateFeaturesDialog(){
		if(nonlinearMultivariateFeaturesDialog == null){
			nonlinearMultivariateFeaturesDialog = new NonlinearMultivariateFeaturesDialog(multivariateController);
		}
		nonlinearMultivariateFeaturesDialog.setLocationRelativeTo(this);
		nonlinearMultivariateFeaturesDialog.setVisible(true);
	}
	
	public AnalysisMultivariateParametersDialog getAnalysisMultivariateParametersDialog() {
		return analysisMultivariateParametersDialog;
	}
	public void setAnalysisMultivariateParametersDialog(
			AnalysisMultivariateParametersDialog analysisMultivariateParametersDialog) {
		this.analysisMultivariateParametersDialog = analysisMultivariateParametersDialog;
	}
	private synchronized void showAnalysisMultivariateParametersDialog(){
		if(analysisMultivariateParametersDialog == null){
			analysisMultivariateParametersDialog = new AnalysisMultivariateParametersDialog(this);
		}
		analysisMultivariateParametersDialog.setLocationRelativeTo(this);
		analysisMultivariateParametersDialog.setVisible(true);
	}
	public NonlinearMultivariateFeaturesDialog getNonlinearMultivariateFeaturesDialog() {
		return nonlinearMultivariateFeaturesDialog;
	}
	public void setNonlinearMultivariateFeaturesDialog(
			NonlinearMultivariateFeaturesDialog nonlinearMultivariateFeaturesDialog) {
		this.nonlinearMultivariateFeaturesDialog = nonlinearMultivariateFeaturesDialog;
	}
}

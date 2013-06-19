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
public class EntropiesDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7510898809685550554L;
	
	private JTextField ccShannonTextField;
	
	private JTextField renyiTextField;
	private JTextField apEnTextField;
	private JTextField sampEnTextField;
	private ExtractUnivariateFeaturesController univariateController;
	
	JCheckBox ccShannonCheckBox, renyiCheckBox, apEnCheckBox, maxApEnCheckBox, maxSampEnCheckBox, sampEnCheckBox;
	
	public EntropiesDialog (ExtractUnivariateFeaturesController univariateController){
		EEGFrameMain.checkOnEventDispatchThread();	
		this.univariateController = univariateController;
		this.setTitle ("Entropies");
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		this.setPreferredSize(new Dimension(500,300));	
		this.setLayout(new BorderLayout());
		JPanel panel = addEntropiesPanel();
		this.add(panel, BorderLayout.CENTER);
	    this.setResizable(false);
	    this.setModal(true);
	    this.pack();
	}
	
	public JPanel addEntropiesPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel ccShannonPanel = new JPanel();
		ccShannonPanel.setLayout(new BoxLayout(ccShannonPanel, BoxLayout.X_AXIS));
		JLabel ccShannonLabel = new JLabel("Corrected conditional Shannon entropy");
		ccShannonLabel.setPreferredSize(new Dimension(250, 20));
		ccShannonPanel.add(Box.createRigidArea(new Dimension(5,0)));
		ccShannonPanel.add(ccShannonLabel);
		ccShannonPanel.add(Box.createHorizontalGlue());	
		ccShannonCheckBox = new JCheckBox();
		ccShannonCheckBox.addItemListener(new ItemListener(){
            
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					ccShannonTextField.setEnabled(true);
                }
				else{
					ccShannonTextField.setEnabled(false);
				}
			}
		});
		ccShannonPanel.add(Box.createHorizontalGlue());
		ccShannonPanel.add(ccShannonCheckBox);
		ccShannonPanel.add(Box.createHorizontalGlue());
		JLabel psDivisionLabel = new JLabel("PS Division: ");
		psDivisionLabel.setHorizontalAlignment(JLabel.RIGHT);
		psDivisionLabel.setPreferredSize(new Dimension(100, 20));
		ccShannonPanel.add(psDivisionLabel);
		ccShannonPanel.add(Box.createHorizontalGlue());
		ccShannonTextField = new JTextField("6");
		ccShannonTextField.setEnabled(false);
		ccShannonTextField.setPreferredSize(new Dimension(50,20));
		ccShannonTextField.setMaximumSize(new Dimension(50,20));
		ccShannonPanel.add(ccShannonTextField);
		ccShannonPanel.add(Box.createHorizontalGlue());
		panel.add(ccShannonPanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));

		JPanel renyiPanel = new JPanel();
		renyiPanel.setLayout(new BoxLayout(renyiPanel, BoxLayout.X_AXIS));
		JLabel renyiLabel = new JLabel("Renyi entropy");
		renyiLabel.setPreferredSize(new Dimension(250, 20));
		renyiPanel.add(Box.createRigidArea(new Dimension(5,0)));
		renyiPanel.add(renyiLabel);
		renyiPanel.add(Box.createHorizontalGlue());	
		renyiCheckBox = new JCheckBox();
		renyiCheckBox.addItemListener(new ItemListener(){
            
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					renyiTextField.setEnabled(true);
                }
				else{
					renyiTextField.setEnabled(false);
				}
			}
		});
		renyiPanel.add(Box.createHorizontalGlue());
		renyiPanel.add(renyiCheckBox);
		renyiPanel.add(Box.createHorizontalGlue());
		JLabel orderLabel = new JLabel("Order: ");
		orderLabel.setHorizontalAlignment(JLabel.RIGHT);
		orderLabel.setPreferredSize(new Dimension(100, 20));
		renyiPanel.add(orderLabel);
		renyiPanel.add(Box.createHorizontalGlue());
		renyiTextField = new JTextField("2");
		renyiTextField.setEnabled(false);
		renyiTextField.setPreferredSize(new Dimension(50,20));
		renyiTextField.setMaximumSize(new Dimension(50,20));
		renyiPanel.add(renyiTextField);
		renyiPanel.add(Box.createHorizontalGlue());
		panel.add(renyiPanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel apEnPanel = new JPanel();
		apEnPanel.setLayout(new BoxLayout(apEnPanel, BoxLayout.X_AXIS));
		JLabel apEnLabel = new JLabel("Approximate entropy (ApEn)");
		apEnLabel.setPreferredSize(new Dimension(250, 20));
		apEnPanel.add(Box.createRigidArea(new Dimension(5,0)));
		apEnPanel.add(apEnLabel);
		apEnPanel.add(Box.createHorizontalGlue());	
		apEnCheckBox = new JCheckBox();
		apEnCheckBox.addItemListener(new ItemListener(){
            
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					apEnTextField.setEnabled(true);
					maxApEnCheckBox.setEnabled(true);
                }
				else{
					apEnTextField.setEnabled(false);
					maxApEnCheckBox.setEnabled(false);
				}
			}
		});

		apEnPanel.add(Box.createHorizontalGlue());
		apEnPanel.add(apEnCheckBox);
		apEnPanel.add(Box.createHorizontalGlue());
		JLabel apEnMFactorLabel = new JLabel("m factor: ");
		apEnMFactorLabel.setHorizontalAlignment(JLabel.RIGHT);
		apEnMFactorLabel.setPreferredSize(new Dimension(100, 20));
		apEnPanel.add(apEnMFactorLabel);
		apEnPanel.add(Box.createHorizontalGlue());
		apEnTextField = new JTextField("2");
		apEnTextField.setEnabled(false);
		apEnTextField.setPreferredSize(new Dimension(50,20));
		apEnTextField.setMaximumSize(new Dimension(50,20));
		apEnPanel.add(apEnTextField);
		apEnPanel.add(Box.createHorizontalGlue());
		panel.add(apEnPanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel maxApEnPanel = new JPanel();
		maxApEnPanel.setLayout(new BoxLayout(maxApEnPanel, BoxLayout.X_AXIS));
		JLabel maxApEnLabel = new JLabel("Maximum approximate entropy");
		maxApEnLabel.setPreferredSize(new Dimension(350, 20));
		maxApEnCheckBox = new JCheckBox();
		maxApEnCheckBox.setEnabled(false);
		maxApEnPanel.add(Box.createRigidArea(new Dimension(50,0)));
//		maxApEnPanel.add(Box.createHorizontalGlue());
		maxApEnPanel.add(maxApEnLabel);
		maxApEnPanel.add(Box.createHorizontalGlue());
		maxApEnPanel.add(maxApEnCheckBox);
		maxApEnPanel.add(Box.createHorizontalGlue());
		panel.add(maxApEnPanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel sampEnPanel = new JPanel();
		sampEnPanel.setLayout(new BoxLayout(sampEnPanel, BoxLayout.X_AXIS));
		JLabel sampEnLabel = new JLabel("Sample entropy (SampEn)");
		sampEnLabel.setPreferredSize(new Dimension(250, 20));
		sampEnPanel.add(Box.createRigidArea(new Dimension(5,0)));
		sampEnPanel.add(sampEnLabel);
		sampEnPanel.add(Box.createHorizontalGlue());	
		sampEnCheckBox = new JCheckBox();
		sampEnCheckBox.addItemListener(new ItemListener(){
            
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					sampEnTextField.setEnabled(true);
					maxSampEnCheckBox.setEnabled(true);
                }
				else{
					sampEnTextField.setEnabled(false);
					maxSampEnCheckBox.setEnabled(false);
				}
			}
		});

		sampEnPanel.add(Box.createHorizontalGlue());
		sampEnPanel.add(sampEnCheckBox);
		sampEnPanel.add(Box.createHorizontalGlue());
		JLabel sampEnMFactorLabel = new JLabel("m factor: ");
		sampEnMFactorLabel.setHorizontalAlignment(JLabel.RIGHT);
		sampEnMFactorLabel.setPreferredSize(new Dimension(100, 20));
		sampEnPanel.add(sampEnMFactorLabel);
		sampEnPanel.add(Box.createHorizontalGlue());
		sampEnTextField = new JTextField("2");
		sampEnTextField.setEnabled(false);
		sampEnTextField.setPreferredSize(new Dimension(50,20));
		sampEnTextField.setMaximumSize(new Dimension(50,20));
		sampEnPanel.add(sampEnTextField);
		sampEnPanel.add(Box.createHorizontalGlue());
		panel.add(sampEnPanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel maxSampEnPanel = new JPanel();
		maxSampEnPanel.setLayout(new BoxLayout(maxSampEnPanel, BoxLayout.X_AXIS));
		JLabel maxSampEnLabel = new JLabel("Maximum sample entropy");
		maxSampEnLabel.setPreferredSize(new Dimension(350, 20));
		maxSampEnCheckBox = new JCheckBox();
		maxSampEnCheckBox.setEnabled(false);
		maxSampEnPanel.add(Box.createRigidArea(new Dimension(50,0)));
//		maxSampEnPanel.add(Box.createHorizontalGlue());
		maxSampEnPanel.add(maxSampEnLabel);
		maxSampEnPanel.add(Box.createHorizontalGlue());
		maxSampEnPanel.add(maxSampEnCheckBox);
		maxSampEnPanel.add(Box.createHorizontalGlue());
		panel.add(maxSampEnPanel);
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
				ccShannonCheckBox.setSelected(true);
				renyiCheckBox.setSelected(true);
				apEnCheckBox.setSelected(true);
				maxApEnCheckBox.setSelected(true);
				maxSampEnCheckBox.setSelected(true);
				sampEnCheckBox.setSelected(true);
			}
		});
		buttonPanel.add(selectAllButton);
		buttonPanel.add(Box.createHorizontalGlue());
		JButton clearAllButton = new JButton("Clear all");
		clearAllButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ccShannonCheckBox.setSelected(false);
				renyiCheckBox.setSelected(false);
				apEnCheckBox.setSelected(false);
				maxApEnCheckBox.setSelected(false);
				maxSampEnCheckBox.setSelected(false);
				sampEnCheckBox.setSelected(false);
			}
		});
		buttonPanel.add(clearAllButton);
		buttonPanel.add(Box.createHorizontalGlue());
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Features f = univariateController.getSelectedFeatures().get(0);
				
				if(apEnCheckBox.isSelected()){
					f.getFeatures().put(UnivariateFeatures.AP_EN, true);
				}
				if(maxApEnCheckBox.isSelected()){
					f.getFeatures().put(UnivariateFeatures.MAX_AP_EN, true);
				}
				if(sampEnCheckBox.isSelected()){
					f.getFeatures().put(UnivariateFeatures.SAMP_EN, true);
				}
				if(maxSampEnCheckBox.isSelected()){
					f.getFeatures().put(UnivariateFeatures.MAX_SAMP_EN, true);
				}
				if(renyiCheckBox.isSelected()){
					f.getFeatures().put(UnivariateFeatures.RENYI_EN, true);
				}
				if(ccShannonCheckBox.isSelected()){
					f.getFeatures().put(UnivariateFeatures.CORRECTED_CONDITIONAL_SHANNON, true);
				}
				setVisible(false);
			}
		});
		buttonPanel.add(okButton);
		buttonPanel.add(Box.createHorizontalGlue());
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		return panel;
	}
	public JTextField getCcShannonTextField() {
		return ccShannonTextField;
	}

	public JTextField getApEnTextField() {
		return apEnTextField;
	}

	public void setApEnTextField(JTextField apEnTextField) {
		this.apEnTextField = apEnTextField;
	}

	public void setCcShannonTextField(JTextField ccShannonTextField) {
		this.ccShannonTextField = ccShannonTextField;
	}

	public JTextField getRenyiTextField() {
		return renyiTextField;
	}

	public void setRenyiTextField(JTextField renyiTextField) {
		this.renyiTextField = renyiTextField;
	}

	public JTextField getSampEnTextField() {
		return sampEnTextField;
	}

	public void setSampEnTextField(JTextField sampEnTextField) {
		this.sampEnTextField = sampEnTextField;
	}

}

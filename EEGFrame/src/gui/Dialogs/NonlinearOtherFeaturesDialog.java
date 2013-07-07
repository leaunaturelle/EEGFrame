/**
 * 
 */
package gui.Dialogs;

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
public class NonlinearOtherFeaturesDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5340424357968825400L;
	private JTextField allanFactorTextField, ctmTextField;
	public JTextField getCtmTextField() {
		return ctmTextField;
	}
	public void setCtmTextField(JTextField ctmTextField) {
		this.ctmTextField = ctmTextField;
	}
	private JCheckBox allanFactorCheckBox, lempelZivCheckBox, ctmCheckBox;
	private ExtractUnivariateFeaturesController univariateController;

	public NonlinearOtherFeaturesDialog(ExtractUnivariateFeaturesController univariateController){
		EEGFrameMain.checkOnEventDispatchThread();
		this.univariateController = univariateController;		
		this.setTitle ("Nonlinear other features");
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setPreferredSize(new Dimension(400,200));	
		this.setLayout(new BorderLayout());
		JPanel panel = addNonlinearOtherFeaturesPanel();
		this.add(panel, BorderLayout.CENTER);
	    this.setResizable(false);
	    this.setModal(true);
	    this.pack();
	}
	public JPanel addNonlinearOtherFeaturesPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel allanFactorPanel = new JPanel();
		allanFactorPanel.setLayout(new BoxLayout(allanFactorPanel, BoxLayout.X_AXIS));
		JLabel allanFactorLabel = new JLabel("Allan factor");
		allanFactorLabel.setPreferredSize(new Dimension(250, 20));
		allanFactorPanel.add(Box.createRigidArea(new Dimension(5,0)));
		allanFactorPanel.add(allanFactorLabel);
		allanFactorPanel.add(Box.createHorizontalGlue());	
		allanFactorCheckBox = new JCheckBox();
		allanFactorCheckBox.addItemListener(new ItemListener(){
            
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					allanFactorTextField.setEnabled(true);
                }
				else{
					allanFactorTextField.setEnabled(false);
				}
			}
		});

		allanFactorPanel.add(Box.createHorizontalGlue());
		allanFactorPanel.add(allanFactorCheckBox);
		allanFactorPanel.add(Box.createHorizontalGlue());
		JLabel orderLabel = new JLabel("Scale: ");
		orderLabel.setHorizontalAlignment(JLabel.RIGHT);
		orderLabel.setPreferredSize(new Dimension(100, 20));
		allanFactorPanel.add(orderLabel);
		allanFactorPanel.add(Box.createHorizontalGlue());
		allanFactorTextField = new JTextField("10");
		allanFactorTextField.setEnabled(false);
		allanFactorTextField.setPreferredSize(new Dimension(50,20));
		allanFactorTextField.setMaximumSize(new Dimension(50,20));
		allanFactorPanel.add(allanFactorTextField);
		allanFactorPanel.add(Box.createHorizontalGlue());
		panel.add(allanFactorPanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel lempelZivPanel = new JPanel();
		lempelZivPanel.setLayout(new BoxLayout(lempelZivPanel, BoxLayout.X_AXIS));
		JLabel lempelZivLabel = new JLabel("Lempel-Ziv complexity");
//		lempelZivLabel.setHorizontalAlignment(JLabel.LEFT);
		lempelZivLabel.setPreferredSize(new Dimension(400, 20));
		lempelZivCheckBox = new JCheckBox();
//		lempelZivPanel.add(Box.createHorizontalGlue());
		lempelZivPanel.add(Box.createRigidArea(new Dimension(5,0)));
		lempelZivPanel.add(lempelZivLabel);
		lempelZivPanel.add(Box.createHorizontalGlue());
		lempelZivPanel.add(lempelZivCheckBox);
		lempelZivPanel.add(Box.createHorizontalGlue());
		panel.add(lempelZivPanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		
		JPanel ctmPanel = new JPanel();
		ctmPanel.setLayout(new BoxLayout(ctmPanel, BoxLayout.X_AXIS));
		JLabel ctmLabel = new JLabel("CTM");
		ctmLabel.setPreferredSize(new Dimension(250, 20));
		ctmPanel.add(Box.createRigidArea(new Dimension(5,0)));
		ctmPanel.add(ctmLabel);
		ctmPanel.add(Box.createHorizontalGlue());	
		ctmCheckBox = new JCheckBox();
		ctmCheckBox.addItemListener(new ItemListener(){
            
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					ctmTextField.setEnabled(true);
                }
				else{
					ctmTextField.setEnabled(false);
				}
			}
		});

		ctmPanel.add(Box.createHorizontalGlue());
		ctmPanel.add(ctmCheckBox);
		ctmPanel.add(Box.createHorizontalGlue());
		JLabel radiusLabel = new JLabel("Radius (times std dev): ");
		orderLabel.setHorizontalAlignment(JLabel.RIGHT);
		orderLabel.setPreferredSize(new Dimension(100, 20));
		ctmPanel.add(radiusLabel);
		ctmPanel.add(Box.createHorizontalGlue());
		ctmTextField = new JTextField("0.5");
		ctmTextField.setEnabled(false);
		ctmTextField.setPreferredSize(new Dimension(50,20));
		ctmTextField.setMaximumSize(new Dimension(50,20));
		ctmPanel.add(ctmTextField);
		ctmPanel.add(new JLabel(" x SD"));
		ctmPanel.add(Box.createHorizontalGlue());
		panel.add(ctmPanel);
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
				allanFactorCheckBox.setSelected(true);
				lempelZivCheckBox.setSelected(true);
				ctmCheckBox.setSelected(true);
			}
		});
		buttonPanel.add(selectAllButton);
		buttonPanel.add(Box.createHorizontalGlue());
		JButton clearAllButton = new JButton("Clear all");
		clearAllButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				allanFactorCheckBox.setSelected(false);
				lempelZivCheckBox.setSelected(false);
				ctmCheckBox.setSelected(false);
				
			}
		});
		buttonPanel.add(clearAllButton);
		buttonPanel.add(Box.createHorizontalGlue());
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Features f = univariateController.getSelectedFeatures().get(0);
				if(allanFactorCheckBox.isSelected()){
					f.getFeatures().put(UnivariateFeatures.ALLAN_FACTOR, true);
				}
				if(lempelZivCheckBox.isSelected()){
					f.getFeatures().put(UnivariateFeatures.LEMPEL_ZIV, true);
				}
				if(ctmCheckBox.isSelected()){
					f.getFeatures().put(UnivariateFeatures.CTM, true);
				}
				setVisible(false);
			}
		});
		buttonPanel.add(okButton);
		buttonPanel.add(Box.createHorizontalGlue());
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		return panel;
	}
	public JTextField getAllanFactorTextField() {
		return allanFactorTextField;
	}
	public void setAllanFactorTextField(JTextField allanFactorTextField) {
		this.allanFactorTextField = allanFactorTextField;
	}
}

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
public class FractalFeaturesDialog extends JDialog {

	private static final long serialVersionUID = 5735820148524348242L;

	private JTextField higuchiTextField;
	private JTextField minSegmentLengthTextField;
	private JTextField alphaLongBoundTextField;
	
	public JTextField getHiguchiTextField() {
		return higuchiTextField;
	}

	public void setHiguchiTextField(JTextField higuchiTextField) {
		this.higuchiTextField = higuchiTextField;
	}

	public JTextField getMinSegmentLengthTextField() {
		return minSegmentLengthTextField;
	}

	public void setMinSegmentLengthTextField(JTextField minSegmentLengthTextField) {
		this.minSegmentLengthTextField = minSegmentLengthTextField;
	}

	public JTextField getAlphaLongBoundTextField() {
		return alphaLongBoundTextField;
	}

	public void setAlphaLongBoundTextField(JTextField alphaLongBoundTextField) {
		this.alphaLongBoundTextField = alphaLongBoundTextField;
	}

	private JCheckBox hurstECheckBox, higuchiCheckBox, dfaCheckBox;
	private ExtractUnivariateFeaturesController univariateController;
	
	public FractalFeaturesDialog (ExtractUnivariateFeaturesController univariateController){
		EEGFrameMain.checkOnEventDispatchThread();
		this.univariateController = univariateController;
		this.setTitle ("Fractal features");
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setPreferredSize(new Dimension(500,250));	
		this.setLayout(new BorderLayout());
		JPanel panel = addFractalFeaturesPanel();
		this.add(panel, BorderLayout.CENTER);
	    this.setResizable(false);
	    this.setModal(true);
	    this.pack();
	}
	
	public JPanel addFractalFeaturesPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel hurstEPanel = new JPanel();
		hurstEPanel.setLayout(new BoxLayout(hurstEPanel, BoxLayout.X_AXIS));
		JLabel hurstELabel = new JLabel("Hurst exponent");
//		hurstELabel.setHorizontalAlignment(JLabel.LEFT);
		hurstELabel.setPreferredSize(new Dimension(400, 20));
		hurstECheckBox = new JCheckBox();
//		hurstEPanel.add(Box.createHorizontalGlue());
		hurstEPanel.add(Box.createRigidArea(new Dimension(5,0)));
		hurstEPanel.add(hurstELabel);
		hurstEPanel.add(Box.createHorizontalGlue());
		hurstEPanel.add(hurstECheckBox);
		hurstEPanel.add(Box.createHorizontalGlue());
		panel.add(hurstEPanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel higuchiPanel = new JPanel();
		higuchiPanel.setLayout(new BoxLayout(higuchiPanel, BoxLayout.X_AXIS));
		JLabel higuchiLabel = new JLabel("Higuchi's fractal dimension");
		higuchiLabel.setPreferredSize(new Dimension(250, 20));
		higuchiPanel.add(Box.createRigidArea(new Dimension(5,0)));
		higuchiPanel.add(higuchiLabel);
		higuchiPanel.add(Box.createHorizontalGlue());	
		higuchiCheckBox = new JCheckBox();
		higuchiCheckBox.addItemListener(new ItemListener(){
            
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					higuchiTextField.setEnabled(true);
                }
				else{
					higuchiTextField.setEnabled(false);
				}
			}
		});

		higuchiPanel.add(Box.createHorizontalGlue());
		higuchiPanel.add(higuchiCheckBox);
		higuchiPanel.add(Box.createHorizontalGlue());
		JLabel kmaxLabel = new JLabel("kmax");
		kmaxLabel.setHorizontalAlignment(JLabel.RIGHT);
		kmaxLabel.setPreferredSize(new Dimension(100, 20));
		higuchiPanel.add(kmaxLabel);
		higuchiPanel.add(Box.createHorizontalGlue());
		higuchiTextField = new JTextField("8");
		higuchiTextField.setEnabled(false);
		higuchiTextField.setPreferredSize(new Dimension(50,20));
		higuchiTextField.setMaximumSize(new Dimension(50,20));
		higuchiPanel.add(higuchiTextField);
		higuchiPanel.add(Box.createHorizontalGlue());
		panel.add(higuchiPanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));

		JPanel dfaPanel = new JPanel();
		dfaPanel.setLayout(new BoxLayout(dfaPanel, BoxLayout.X_AXIS));
		JLabel dfaLabel = new JLabel("Detrended fluctuation analysis");
//		dfaLabel.setHorizontalAlignment(JLabel.LEFT);
		dfaLabel.setPreferredSize(new Dimension(400, 20));
		dfaCheckBox = new JCheckBox();
		dfaCheckBox.addItemListener(new ItemListener(){
            
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					minSegmentLengthTextField.setEnabled(true);
					alphaLongBoundTextField.setEnabled(true);
                }
				else{
					minSegmentLengthTextField.setEnabled(false);
					alphaLongBoundTextField.setEnabled(false);
				}
			}
		});

		dfaPanel.add(Box.createHorizontalGlue());
		dfaPanel.add(Box.createRigidArea(new Dimension(5,0)));
		dfaPanel.add(dfaLabel);
		dfaPanel.add(Box.createHorizontalGlue());
		dfaPanel.add(dfaCheckBox);
		dfaPanel.add(Box.createHorizontalGlue());
		panel.add(dfaPanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel minSegmentLengthPanel = new JPanel();
		minSegmentLengthPanel.setLayout(new BoxLayout(minSegmentLengthPanel, BoxLayout.X_AXIS));
		JLabel minSegmentLengthLabel = new JLabel("Minimal segment length for DFA calculation:");
		minSegmentLengthLabel.setPreferredSize(new Dimension(350, 20));
		minSegmentLengthPanel.add(Box.createRigidArea(new Dimension(50,0)));
		minSegmentLengthPanel.add(minSegmentLengthLabel);
		minSegmentLengthPanel.add(Box.createHorizontalGlue());
		minSegmentLengthTextField = new JTextField("5");
		minSegmentLengthTextField.setEnabled(false);
		minSegmentLengthTextField.setPreferredSize(new Dimension(50,20));
		minSegmentLengthTextField.setMaximumSize(new Dimension(50,20));
		minSegmentLengthPanel.add(minSegmentLengthTextField);
		minSegmentLengthPanel.add(Box.createHorizontalGlue());
		panel.add(minSegmentLengthPanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel alphaLongBoundPanel = new JPanel();
		alphaLongBoundPanel.setLayout(new BoxLayout(alphaLongBoundPanel, BoxLayout.X_AXIS));
		JLabel alphaLongBoundLabel = new JLabel("Bound for calculation of alpha long:");
		alphaLongBoundLabel.setPreferredSize(new Dimension(350, 20));
		alphaLongBoundPanel.add(Box.createRigidArea(new Dimension(50,0)));
		alphaLongBoundPanel.add(alphaLongBoundLabel);
		alphaLongBoundPanel.add(Box.createHorizontalGlue());
		alphaLongBoundTextField = new JTextField("13");
		alphaLongBoundTextField.setEnabled(false);
		alphaLongBoundTextField.setPreferredSize(new Dimension(50,20));
		alphaLongBoundTextField.setMaximumSize(new Dimension(50,20));
		alphaLongBoundPanel.add(alphaLongBoundTextField);
		alphaLongBoundPanel.add(Box.createHorizontalGlue());
		panel.add(alphaLongBoundPanel);
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
				hurstECheckBox.setSelected(true);
				higuchiCheckBox.setSelected(true);
				dfaCheckBox.setSelected(true);
			}
		});
		buttonPanel.add(selectAllButton);
		buttonPanel.add(Box.createHorizontalGlue());
		JButton clearAllButton = new JButton("Clear all");
		clearAllButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				hurstECheckBox.setSelected(false);
				higuchiCheckBox.setSelected(false);
				dfaCheckBox.setSelected(false);
			}
		});
		buttonPanel.add(clearAllButton);
		buttonPanel.add(Box.createHorizontalGlue());
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Features f = univariateController.getSelectedFeatures().get(0);
				
				if(hurstECheckBox.isSelected()){
					f.getFeatures().put(UnivariateFeatures.HURST, true);
				}
				if(higuchiCheckBox.isSelected()){
					f.getFeatures().put(UnivariateFeatures.HIGUCHI, true);
				}
				if(dfaCheckBox.isSelected()){
					f.getFeatures().put(UnivariateFeatures.DFA, true);
				}
				setVisible(false);
			}
		});
		buttonPanel.add(okButton);
		buttonPanel.add(Box.createHorizontalGlue());
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		return panel;
	}
}

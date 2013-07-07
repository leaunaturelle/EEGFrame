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

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.WindowConstants;

/**
 * @author lsuc
 *
 */
public class TimeFrequencyFeaturesDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6039897566438455651L;

	private ExtractUnivariateFeaturesController univariateFeaturesController;
	private JCheckBox haarWaveletCheckBox, scale3CheckBox, scale4CheckBox, scale8CheckBox, scale16CheckBox, scale32CheckBox, hilbertHuangCheckBox; 
	
	public TimeFrequencyFeaturesDialog(ExtractUnivariateFeaturesController univariateFeaturesController){
		EEGFrameMain.checkOnEventDispatchThread();	
		this.univariateFeaturesController = univariateFeaturesController;
		this.setTitle ("Nonlinear time-frequency features");
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setPreferredSize(new Dimension(300,150));	
		this.setLayout(new BorderLayout());
		JPanel panel = addTimeFrequencyFeaturesPanel();
//		disableCheckBoxes();
		this.add(panel, BorderLayout.CENTER);
	    this.setResizable(false);
	    this.setModal(true);
	    this.pack();
	}
	
	public JPanel addTimeFrequencyFeaturesPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel haarWaveletPanel = new JPanel();
		haarWaveletPanel.setLayout(new BoxLayout(haarWaveletPanel, BoxLayout.X_AXIS));
		JLabel haarWaveletLabel = new JLabel("Haar wavelet standard deviation:");
		haarWaveletLabel.setPreferredSize(new Dimension(400, 20));
		haarWaveletPanel.add(Box.createRigidArea(new Dimension(5,0)));
		haarWaveletPanel.add(haarWaveletLabel);
		haarWaveletPanel.add(Box.createHorizontalGlue());	
		haarWaveletCheckBox = new JCheckBox();
		haarWaveletCheckBox.addItemListener(new ItemListener(){
             
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
//					enableCheckBoxes();
                }
				else{
//					disableCheckBoxes();
				}
			}
		});
		
		haarWaveletPanel.add(Box.createHorizontalGlue());
		haarWaveletPanel.add(haarWaveletCheckBox);
		haarWaveletPanel.add(Box.createHorizontalGlue());
		panel.add(haarWaveletPanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
//		JPanel scalePanel = new JPanel();
//		scalePanel.setLayout(new BoxLayout(scalePanel, BoxLayout.Y_AXIS));
//		scalePanel.setBorder(BorderFactory.createTitledBorder("Scale: "));
//		panel.add(Box.createRigidArea(new Dimension(5,10)));
//		
//		JPanel scale3Panel = new JPanel();
//		scale3Panel.setLayout(new BoxLayout(scale3Panel, BoxLayout.X_AXIS));
//		JLabel scale3Label = new JLabel("Scale 3");
//		scale3Label.setHorizontalAlignment(JLabel.RIGHT);
//		scale3Label.setPreferredSize(new Dimension(100, 20));
//		scale3Panel.add(Box.createHorizontalGlue());
//		scale3Panel.add(scale3Label);
//		scale3Panel.add(Box.createHorizontalGlue());
//		scale3CheckBox = new JCheckBox();
//		scale3Panel.add(scale3CheckBox);
//		scale3Panel.add(Box.createHorizontalGlue());
//		scalePanel.add(scale3Panel);
//		
//		JPanel scale4Panel = new JPanel();
//		scale4Panel.setLayout(new BoxLayout(scale4Panel, BoxLayout.X_AXIS));
//		JLabel scale4Label = new JLabel("Scale 4");
//		scale4Label.setHorizontalAlignment(JLabel.RIGHT);
//		scale4Label.setPreferredSize(new Dimension(100, 20));
//		scale4Panel.add(Box.createHorizontalGlue());
//		scale4Panel.add(scale4Label);
//		scale4Panel.add(Box.createHorizontalGlue());
//		scale4CheckBox = new JCheckBox();
//		scale4Panel.add(scale4CheckBox);
//		scale4Panel.add(Box.createHorizontalGlue());
//		scalePanel.add(scale4Panel);
//		
//		JPanel scale8Panel = new JPanel();
//		scale8Panel.setLayout(new BoxLayout(scale8Panel, BoxLayout.X_AXIS));
//		JLabel scale8Label = new JLabel("Scale 8");
//		scale8Label.setHorizontalAlignment(JLabel.RIGHT);
//		scale8Label.setPreferredSize(new Dimension(100, 20));
//		scale8Panel.add(Box.createHorizontalGlue());
//		scale8Panel.add(scale8Label);
//		scale8Panel.add(Box.createHorizontalGlue());
//		scale8CheckBox = new JCheckBox();
//		scale8Panel.add(scale8CheckBox);
//		scale8Panel.add(Box.createHorizontalGlue());
//		scalePanel.add(scale8Panel);
//		
//		JPanel scale16Panel = new JPanel();
//		scale16Panel.setLayout(new BoxLayout(scale16Panel, BoxLayout.X_AXIS));
//		JLabel scale16Label = new JLabel("Scale 16");
//		scale16Label.setHorizontalAlignment(JLabel.RIGHT);
//		scale16Label.setPreferredSize(new Dimension(100, 20));
//		scale16Panel.add(Box.createHorizontalGlue());
//		scale16Panel.add(scale16Label);
//		scale16Panel.add(Box.createHorizontalGlue());
//		scale16CheckBox = new JCheckBox();
//		scale16Panel.add(scale16CheckBox);
//		scale16Panel.add(Box.createHorizontalGlue());
//		scalePanel.add(scale16Panel);
//		
//		JPanel scale32Panel = new JPanel();
//		scale32Panel.setLayout(new BoxLayout(scale32Panel, BoxLayout.X_AXIS));
//		JLabel scale32Label = new JLabel("Scale 32");
//		scale32Label.setHorizontalAlignment(JLabel.RIGHT);
//		scale32Label.setPreferredSize(new Dimension(100, 20));
//		scale32Panel.add(Box.createHorizontalGlue());
//		scale32Panel.add(scale32Label);
//		scale32Panel.add(Box.createHorizontalGlue());
//		scale32CheckBox = new JCheckBox();
//		scale32Panel.add(scale32CheckBox);
//		scale32Panel.add(Box.createHorizontalGlue());
//		scalePanel.add(scale32Panel);
//		
//		haarWaveletPanel.add(scalePanel);
//		haarWaveletPanel.add(Box.createHorizontalGlue());

//		
		JPanel hilbertHuangPanel = new JPanel();
		hilbertHuangPanel.setLayout(new BoxLayout(hilbertHuangPanel, BoxLayout.X_AXIS));
		JLabel hilbertHuangLabel = new JLabel("Hilbert-Huang transform");
//		hilbertHuangLabel.setHorizontalAlignment(JLabel.LEFT);
		hilbertHuangLabel.setPreferredSize(new Dimension(400, 20));
		hilbertHuangCheckBox = new JCheckBox();
//		hilbertHuangPanel.add(Box.createHorizontalGlue());
		hilbertHuangPanel.add(Box.createRigidArea(new Dimension(5,0)));
		hilbertHuangPanel.add(hilbertHuangLabel);
		hilbertHuangPanel.add(Box.createHorizontalGlue());
		hilbertHuangPanel.add(hilbertHuangCheckBox);
		hilbertHuangPanel.add(Box.createHorizontalGlue());
		panel.add(hilbertHuangPanel);
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
				haarWaveletCheckBox.setSelected(true);
				scale3CheckBox.setSelected(true);
				scale4CheckBox.setSelected(true);
				scale8CheckBox.setSelected(true);
				scale16CheckBox.setSelected(true);
				scale32CheckBox.setSelected(true);
				hilbertHuangCheckBox.setSelected(true); 
				
			}
		});
		buttonPanel.add(selectAllButton);
		buttonPanel.add(Box.createHorizontalGlue());
		JButton clearAllButton = new JButton("Clear all");
		clearAllButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				haarWaveletCheckBox.setSelected(false);
//				scale3CheckBox.setSelected(false);
//				scale4CheckBox.setSelected(false);
//				scale8CheckBox.setSelected(false);
//				scale16CheckBox.setSelected(false);
//				scale32CheckBox.setSelected(false);
				hilbertHuangCheckBox.setSelected(false); 
				
			}
		});
		buttonPanel.add(clearAllButton);
		buttonPanel.add(Box.createHorizontalGlue());
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Features f = univariateFeaturesController.getSelectedFeatures().get(0);
				if(haarWaveletCheckBox.isSelected()){
					f.getFeatures().put(UnivariateFeatures.HAAR_WAVELET, true);
				}
				if(hilbertHuangCheckBox.isSelected()){
					
				}
				setVisible(false);
			}
		});
		buttonPanel.add(okButton);
		buttonPanel.add(Box.createHorizontalGlue());
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
	
		return panel;
	}
	
//	public void disableCheckBoxes(){
//		scale3CheckBox.setEnabled(false);
//		scale4CheckBox.setEnabled(false);
//		scale8CheckBox.setEnabled(false);
//		scale16CheckBox.setEnabled(false);
//		scale32CheckBox.setEnabled(false);
//	}
//	public void enableCheckBoxes(){
//		scale3CheckBox.setEnabled(true);
//		scale4CheckBox.setEnabled(true);
//		scale8CheckBox.setEnabled(true);
//		scale16CheckBox.setEnabled(true);
//		scale32CheckBox.setEnabled(true);
//	}
}

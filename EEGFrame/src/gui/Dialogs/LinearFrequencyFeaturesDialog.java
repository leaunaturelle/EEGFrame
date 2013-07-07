/**
 * 
 */
package gui.Dialogs;

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
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import features.output.ExtractUnivariateFeaturesController;
import features.output.Features;
import features.output.UnivariateFeatures;
import gui.EEGFrameMain;

/**
 * @author lsuc
 *
 */
public class LinearFrequencyFeaturesDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4122044058280174066L;
	
	private ExtractUnivariateFeaturesController univariateFeaturesController;
	private JTextField burgTextField, alphaLowerTextField, alphaUpperTextField, betaLowerTextField, betaUpperTextField, gammaLowerTextField, gammaUpperTextField, deltaLowerTextField, deltaUpperTextField, thetaLowerTextField, thetaUpperTextField;
	
	private JCheckBox fftCheckBox, hannCheckBox, hammingCheckBox, burgCheckBox, outputWholeSpectrumBox, alphaCheckBox, betaCheckBox,  gammaCheckBox, deltaCheckBox, thetaCheckBox, totalPSDCheckBox;

	public LinearFrequencyFeaturesDialog(ExtractUnivariateFeaturesController univariateFeaturesController){
		EEGFrameMain.checkOnEventDispatchThread();	
		this.univariateFeaturesController = univariateFeaturesController;
		this.setTitle ("Linear frequency domain features");
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setPreferredSize(new Dimension(450,480));	
		this.setLayout(new BorderLayout());
		JPanel panel = addLinearFrequencyFeaturesPanel();
		this.add(panel, BorderLayout.CENTER);
	    this.setResizable(false);
	    this.setModal(true);
	    this.pack();
	}
	
	public JPanel addLinearFrequencyFeaturesPanel(){
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel psdLabelPanel = new JPanel();
		psdLabelPanel.setLayout(new BoxLayout(psdLabelPanel, BoxLayout.X_AXIS));
		psdLabelPanel.add(Box.createRigidArea(new Dimension(5,0)));
		JLabel psdLabel = new JLabel("PSD estimation method: ");
		psdLabel.setPreferredSize(new Dimension(200, 10));
		psdLabelPanel.add(psdLabel);
		psdLabelPanel.add(Box.createHorizontalGlue());		
		panel.add(psdLabelPanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel fftPanel = new JPanel();
		fftPanel.setLayout(new BoxLayout(fftPanel, BoxLayout.X_AXIS));
		JLabel fftLabel = new JLabel("Fast fourier transform");
		fftLabel.setPreferredSize(new Dimension(150, 10));
		fftCheckBox = new JCheckBox();
		fftCheckBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					hannCheckBox.setEnabled(true);
					hammingCheckBox.setEnabled(true);
                }
				else{
					hannCheckBox.setEnabled(false);
					hammingCheckBox.setEnabled(false);
				}
				
			}
		});
		fftPanel.add(Box.createRigidArea(new Dimension(15,0)));
		fftPanel.add(fftLabel);
//		fftPanel.add(Box.createHorizontalGlue());
		fftPanel.add(fftCheckBox);
		fftPanel.add(Box.createHorizontalGlue());		
		
		JPanel windowPanel = new JPanel();
		windowPanel.setBorder(BorderFactory.createTitledBorder("Window: "));
		windowPanel.setLayout(new BoxLayout(windowPanel, BoxLayout.X_AXIS));
		windowPanel.add(Box.createHorizontalGlue());
		JLabel hannLabel = new JLabel("Hann ");
		windowPanel.add(hannLabel);
		windowPanel.add(Box.createHorizontalGlue());
		hannCheckBox = new JCheckBox();
		hannCheckBox.setEnabled(false);
		windowPanel.add(hannCheckBox);
		windowPanel.add(Box.createHorizontalGlue());
		JLabel hammingLabel = new JLabel("Hamming ");
		windowPanel.add(hammingLabel);
		hammingCheckBox = new JCheckBox();
		hammingCheckBox.setEnabled(false);
		windowPanel.add(hammingCheckBox);
		windowPanel.add(Box.createHorizontalGlue());
		fftPanel.add(windowPanel);
		panel.add(fftPanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel burgPanel = new JPanel();
		burgPanel.add(Box.createRigidArea(new Dimension(15,0)));
		burgPanel.setLayout(new BoxLayout(burgPanel, BoxLayout.X_AXIS));
		JLabel burgLabel = new JLabel("Burg autoreggresive model");
		burgPanel.add(burgLabel);
		burgPanel.add(Box.createHorizontalGlue());	
		burgCheckBox = new JCheckBox();
		burgCheckBox.addItemListener(new ItemListener(){
            
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					burgTextField.setEnabled(true);
                }
				else{
					burgTextField.setEnabled(false);
				}
			}
		});
		burgPanel.add(Box.createHorizontalGlue());
		burgPanel.add(burgCheckBox);
		burgPanel.add(Box.createHorizontalGlue());
		JLabel burgOrderLabel = new JLabel("Order: ");
		burgOrderLabel.setHorizontalAlignment(JLabel.RIGHT);
		burgPanel.add(burgOrderLabel);
		burgPanel.add(Box.createHorizontalGlue());
		burgTextField = new JTextField("12");
		burgTextField.setEnabled(false);
		burgTextField.setPreferredSize(new Dimension(50,20));
		burgTextField.setMaximumSize(new Dimension(50,20));
		burgPanel.add(burgTextField);
		burgPanel.add(Box.createHorizontalGlue());
		panel.add(burgPanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel outputWholeSpectrumPanel = new JPanel();
		outputWholeSpectrumPanel.setLayout(new BoxLayout(outputWholeSpectrumPanel, BoxLayout.X_AXIS));
		JLabel lleLabel = new JLabel("Output whole PSD spectrum");
		lleLabel.setPreferredSize(new Dimension(200, 10));
		outputWholeSpectrumBox = new JCheckBox();
		outputWholeSpectrumPanel.add(Box.createRigidArea(new Dimension(5,0)));
		outputWholeSpectrumPanel.add(lleLabel);
		outputWholeSpectrumPanel.add(Box.createHorizontalGlue());
		outputWholeSpectrumPanel.add(outputWholeSpectrumBox);
		outputWholeSpectrumPanel.add(Box.createHorizontalGlue());
		panel.add(outputWholeSpectrumPanel);
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel frequencyBandsPanel = new JPanel();
		frequencyBandsPanel.setBorder(BorderFactory.createTitledBorder("Frequency bands "));
		frequencyBandsPanel.setLayout(new BoxLayout(frequencyBandsPanel, BoxLayout.Y_AXIS));
		frequencyBandsPanel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel alphaPanel = new JPanel();
		alphaPanel.add(Box.createRigidArea(new Dimension(15,0)));
		alphaPanel.setLayout(new BoxLayout(alphaPanel, BoxLayout.X_AXIS));
		JLabel alphaLabel = new JLabel("Alpha band");
		alphaLabel.setPreferredSize(new Dimension(100,10));
		alphaPanel.add(alphaLabel);
		alphaPanel.add(Box.createHorizontalGlue());	
		alphaCheckBox = new JCheckBox();
		alphaCheckBox.addItemListener(new ItemListener(){
            
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					alphaLowerTextField.setEnabled(true);
					alphaUpperTextField.setEnabled(true);
                }
				else{
					alphaLowerTextField.setEnabled(false);
					alphaUpperTextField.setEnabled(false);
				}
			}
		});
		alphaPanel.add(Box.createHorizontalGlue());
		alphaPanel.add(alphaCheckBox);
		alphaPanel.add(Box.createHorizontalGlue());		
		alphaLowerTextField = new JTextField(Double.toString(DEFAULT_ALPHA_BAND_LOWER_BOUND));
		alphaLowerTextField.setEnabled(false);
		alphaLowerTextField.setPreferredSize(new Dimension(50,20));
		alphaLowerTextField.setMaximumSize(new Dimension(50,20));
		alphaPanel.add(alphaLowerTextField);
		alphaPanel.add(new JLabel(" - "));
		alphaUpperTextField = new JTextField(Double.toString(DEFAULT_ALPHA_BAND_UPPER_BOUND));
		alphaUpperTextField.setEnabled(false);
		alphaUpperTextField.setPreferredSize(new Dimension(50,20));
		alphaUpperTextField.setMaximumSize(new Dimension(50,20));
		alphaPanel.add(alphaUpperTextField);
		alphaPanel.add(new JLabel(" Hz "));
		alphaPanel.add(Box.createHorizontalGlue());		
		frequencyBandsPanel.add(alphaPanel);
		frequencyBandsPanel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel betaPanel = new JPanel();
		betaPanel.add(Box.createRigidArea(new Dimension(15,0)));
		betaPanel.setLayout(new BoxLayout(betaPanel, BoxLayout.X_AXIS));
		JLabel betaLabel = new JLabel("Beta band");
		betaLabel.setPreferredSize(new Dimension(100,10));
		betaPanel.add(betaLabel);
		betaPanel.add(Box.createHorizontalGlue());	
		betaCheckBox = new JCheckBox();
		betaCheckBox.addItemListener(new ItemListener(){
            
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					betaLowerTextField.setEnabled(true);
					betaUpperTextField.setEnabled(true);
                }
				else{
					betaLowerTextField.setEnabled(false);
					betaUpperTextField.setEnabled(false);
				}
			}
		});
		betaPanel.add(Box.createHorizontalGlue());
		betaPanel.add(betaCheckBox);
		betaPanel.add(Box.createHorizontalGlue());		
		betaLowerTextField = new JTextField(Double.toString(DEFAULT_BETA_BAND_LOWER_BOUND));
		betaLowerTextField.setEnabled(false);
		betaLowerTextField.setPreferredSize(new Dimension(50,20));
		betaLowerTextField.setMaximumSize(new Dimension(50,20));
		betaPanel.add(betaLowerTextField);
		betaPanel.add(new JLabel(" - "));
		betaUpperTextField = new JTextField(Double.toString(DEFAULT_BETA_BAND_UPPER_BOUND));
		betaUpperTextField.setEnabled(false);
		betaUpperTextField.setPreferredSize(new Dimension(50,20));
		betaUpperTextField.setMaximumSize(new Dimension(50,20));
		betaPanel.add(betaUpperTextField);
		betaPanel.add(new JLabel(" Hz "));
		betaPanel.add(Box.createHorizontalGlue());		
		frequencyBandsPanel.add(betaPanel);
		frequencyBandsPanel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel gammaPanel = new JPanel();
		gammaPanel.add(Box.createRigidArea(new Dimension(15,0)));
		gammaPanel.setLayout(new BoxLayout(gammaPanel, BoxLayout.X_AXIS));
		JLabel gammaLabel = new JLabel("Gamma band");
		gammaLabel.setPreferredSize(new Dimension(100,10));
		gammaPanel.add(gammaLabel);
		gammaPanel.add(Box.createHorizontalGlue());	
		gammaCheckBox = new JCheckBox();
		gammaCheckBox.addItemListener(new ItemListener(){
            
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					gammaLowerTextField.setEnabled(true);
					gammaUpperTextField.setEnabled(true);
                }
				else{
					gammaLowerTextField.setEnabled(false);
					gammaUpperTextField.setEnabled(false);
				}
			}
		});
		gammaPanel.add(Box.createHorizontalGlue());
		gammaPanel.add(gammaCheckBox);
		gammaPanel.add(Box.createHorizontalGlue());		
		gammaLowerTextField = new JTextField(Double.toString(DEFAULT_GAMMA_BAND_LOWER_BOUND));
		gammaLowerTextField.setEnabled(false);
		gammaLowerTextField.setPreferredSize(new Dimension(50,20));
		gammaLowerTextField.setMaximumSize(new Dimension(50,20));
		gammaPanel.add(gammaLowerTextField);
		gammaPanel.add(new JLabel(" - "));
		gammaUpperTextField = new JTextField(Double.toString(DEFAULT_GAMMA_BAND_UPPER_BOUND));
		gammaUpperTextField.setEnabled(false);
		gammaUpperTextField.setPreferredSize(new Dimension(50,20));
		gammaUpperTextField.setMaximumSize(new Dimension(50,20));
		gammaPanel.add(gammaUpperTextField);
		gammaPanel.add(new JLabel(" Hz "));
		gammaPanel.add(Box.createHorizontalGlue());		
		frequencyBandsPanel.add(gammaPanel);
		frequencyBandsPanel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel deltaPanel = new JPanel();
		deltaPanel.add(Box.createRigidArea(new Dimension(15,0)));
		deltaPanel.setLayout(new BoxLayout(deltaPanel, BoxLayout.X_AXIS));
		JLabel deltaLabel = new JLabel("Delta band");
		deltaLabel.setPreferredSize(new Dimension(100,10));
		deltaPanel.add(deltaLabel);
		deltaPanel.add(Box.createHorizontalGlue());	
		deltaCheckBox = new JCheckBox();
		deltaCheckBox.addItemListener(new ItemListener(){
            
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					deltaLowerTextField.setEnabled(true);
					deltaUpperTextField.setEnabled(true);
                }
				else{
					deltaLowerTextField.setEnabled(false);
					deltaUpperTextField.setEnabled(false);
				}
			}
		});
		deltaPanel.add(Box.createHorizontalGlue());
		deltaPanel.add(deltaCheckBox);
		deltaPanel.add(Box.createHorizontalGlue());		
		deltaLowerTextField = new JTextField(Double.toString(DEFAULT_DELTA_BAND_LOWER_BOUND));
		deltaLowerTextField.setEnabled(false);
		deltaLowerTextField.setPreferredSize(new Dimension(50,20));
		deltaLowerTextField.setMaximumSize(new Dimension(50,20));
		deltaPanel.add(deltaLowerTextField);
		deltaPanel.add(new JLabel(" - "));
		deltaUpperTextField = new JTextField(Double.toString(DEFAULT_DELTA_BAND_UPPER_BOUND));
		deltaUpperTextField.setEnabled(false);
		deltaUpperTextField.setPreferredSize(new Dimension(50,20));
		deltaUpperTextField.setMaximumSize(new Dimension(50,20));
		deltaPanel.add(deltaUpperTextField);
		deltaPanel.add(new JLabel(" Hz "));
		deltaPanel.add(Box.createHorizontalGlue());		
		frequencyBandsPanel.add(deltaPanel);
		frequencyBandsPanel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel thetaPanel = new JPanel();
		thetaPanel.add(Box.createRigidArea(new Dimension(15,0)));
		thetaPanel.setLayout(new BoxLayout(thetaPanel, BoxLayout.X_AXIS));
		JLabel thetaLabel = new JLabel("Theta band");
		thetaLabel.setPreferredSize(new Dimension(100,10));
		thetaPanel.add(thetaLabel);
		thetaPanel.add(Box.createHorizontalGlue());	
		thetaCheckBox = new JCheckBox();
		thetaCheckBox.addItemListener(new ItemListener(){
            
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					thetaLowerTextField.setEnabled(true);
					thetaUpperTextField.setEnabled(true);
                }
				else{
					thetaLowerTextField.setEnabled(false);
					thetaUpperTextField.setEnabled(false);
				}
			}
		});
		thetaPanel.add(Box.createHorizontalGlue());
		thetaPanel.add(thetaCheckBox);
		thetaPanel.add(Box.createHorizontalGlue());		
		thetaLowerTextField = new JTextField(Double.toString(DEFAULT_THETA_BAND_LOWER_BOUND));
		thetaLowerTextField.setEnabled(false);
		thetaLowerTextField.setPreferredSize(new Dimension(50,20));
		thetaLowerTextField.setMaximumSize(new Dimension(50,20));
		thetaPanel.add(thetaLowerTextField);
		thetaPanel.add(new JLabel(" - "));
		thetaUpperTextField = new JTextField(Double.toString(DEFAULT_THETA_BAND_UPPER_BOUND));
		thetaUpperTextField.setEnabled(false);
		thetaUpperTextField.setPreferredSize(new Dimension(50,20));
		thetaUpperTextField.setMaximumSize(new Dimension(50,20));
		thetaPanel.add(thetaUpperTextField);
		thetaPanel.add(new JLabel(" Hz "));
		thetaPanel.add(Box.createHorizontalGlue());		
		frequencyBandsPanel.add(thetaPanel);
		frequencyBandsPanel.add(Box.createRigidArea(new Dimension(5,10)));
		
		JPanel totalPSDPanel = new JPanel();
		totalPSDPanel.add(Box.createRigidArea(new Dimension(15,0)));
		totalPSDPanel.setLayout(new BoxLayout(totalPSDPanel, BoxLayout.X_AXIS));
		JLabel totalPSDLabel = new JLabel("Total PSD");
		totalPSDLabel.setPreferredSize(new Dimension(100,10));
		totalPSDPanel.add(totalPSDLabel);
		totalPSDPanel.add(Box.createHorizontalGlue());	
		totalPSDCheckBox = new JCheckBox();
		
		totalPSDPanel.add(Box.createHorizontalGlue());
		totalPSDPanel.add(totalPSDCheckBox);
		totalPSDPanel.add(Box.createHorizontalGlue());		
		frequencyBandsPanel.add(totalPSDPanel);
		frequencyBandsPanel.add(Box.createRigidArea(new Dimension(5,10)));
		
		panel.add(frequencyBandsPanel);
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
				fftCheckBox.setSelected(true);
				hannCheckBox.setSelected(true);
				hammingCheckBox.setSelected(true);
				burgCheckBox.setSelected(true);
				outputWholeSpectrumBox.setSelected(true);
				alphaCheckBox.setSelected(true);
				betaCheckBox.setSelected(true);
				gammaCheckBox.setSelected(true);
				deltaCheckBox.setSelected(true);
				thetaCheckBox.setSelected(true);
				totalPSDCheckBox.setSelected(true);
				
			}
		});
		buttonPanel.add(selectAllButton);
		buttonPanel.add(Box.createHorizontalGlue());
		JButton clearAllButton = new JButton("Clear all");
		clearAllButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fftCheckBox.setSelected(false);
				hannCheckBox.setSelected(false);
				hammingCheckBox.setSelected(false);
				burgCheckBox.setSelected(false);
				outputWholeSpectrumBox.setSelected(false);
				alphaCheckBox.setSelected(false);
				betaCheckBox.setSelected(false);
				gammaCheckBox.setSelected(false);
				deltaCheckBox.setSelected(false);
				thetaCheckBox.setSelected(false);
				totalPSDCheckBox.setSelected(false);
				
			}
		});
		buttonPanel.add(clearAllButton);
		buttonPanel.add(Box.createHorizontalGlue());
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Features f = univariateFeaturesController.getSelectedFeatures().get(0);
				if(fftCheckBox.isSelected()){
					f.getFeatures().put(UnivariateFeatures.FFT, true);
				}
				if(hannCheckBox.isSelected()){
					f.getFeatures().put(UnivariateFeatures.HANN, true);
				}
				if(hammingCheckBox.isSelected()){
					f.getFeatures().put(UnivariateFeatures.HAMMING, true);
				}
				if(burgCheckBox.isSelected()){
					f.getFeatures().put(UnivariateFeatures.BURG, true);
				}
//				if(outputWholeSpectrumBox.isSelected()){
//					f.getFeatures().put(UnivariateFeatures.RECURRENCE_PLOT_FEATURES, true);
//				}
				if(alphaCheckBox.isSelected()){
					f.getFeatures().put(UnivariateFeatures.ALPHA_PSD, true);
				}
				if(betaCheckBox.isSelected()){
					f.getFeatures().put(UnivariateFeatures.BETA_PSD, true);
				}
				if(gammaCheckBox.isSelected()){
					f.getFeatures().put(UnivariateFeatures.GAMMA_PSD, true);
				}
				if(deltaCheckBox.isSelected()){
					f.getFeatures().put(UnivariateFeatures.DELTA_PSD, true);
				}
				if(thetaCheckBox.isSelected()){
					f.getFeatures().put(UnivariateFeatures.THETA_PSD, true);
				}
				if(totalPSDCheckBox.isSelected()){
					f.getFeatures().put(UnivariateFeatures.TOTAL_PSD, true);
				}
				setVisible(false);
			}
		});
		buttonPanel.add(okButton);
		buttonPanel.add(Box.createHorizontalGlue());
		panel.add(Box.createRigidArea(new Dimension(5,10)));
		return panel;
	}
	
	public JTextField getBurgTextField() {
		return burgTextField;
	}

	public void setBurgTextField(JTextField burgTextField) {
		this.burgTextField = burgTextField;
	}

	public JTextField getAlphaLowerTextField() {
		return alphaLowerTextField;
	}

	public void setAlphaLowerTextField(JTextField alphaLowerTextField) {
		this.alphaLowerTextField = alphaLowerTextField;
	}

	public JTextField getAlphaUpperTextField() {
		return alphaUpperTextField;
	}

	public void setAlphaUpperTextField(JTextField alphaUpperTextField) {
		this.alphaUpperTextField = alphaUpperTextField;
	}

	public JTextField getBetaLowerTextField() {
		return betaLowerTextField;
	}

	public void setBetaLowerTextField(JTextField betaLowerTextField) {
		this.betaLowerTextField = betaLowerTextField;
	}

	public JTextField getBetaUpperTextField() {
		return betaUpperTextField;
	}

	public void setBetaUpperTextField(JTextField betaUpperTextField) {
		this.betaUpperTextField = betaUpperTextField;
	}

	public JTextField getGammaLowerTextField() {
		return gammaLowerTextField;
	}

	public void setGammaLowerTextField(JTextField gammaLowerTextField) {
		this.gammaLowerTextField = gammaLowerTextField;
	}

	public JTextField getGammaUpperTextField() {
		return gammaUpperTextField;
	}

	public void setGammaUpperTextField(JTextField gammaUpperTextField) {
		this.gammaUpperTextField = gammaUpperTextField;
	}

	public JTextField getDeltaLowerTextField() {
		return deltaLowerTextField;
	}

	public void setDeltaLowerTextField(JTextField deltaLowerTextField) {
		this.deltaLowerTextField = deltaLowerTextField;
	}

	public JTextField getDeltaUpperTextField() {
		return deltaUpperTextField;
	}

	public void setDeltaUpperTextField(JTextField deltaUpperTextField) {
		this.deltaUpperTextField = deltaUpperTextField;
	}

	public JTextField getThetaLowerTextField() {
		return thetaLowerTextField;
	}

	public void setThetaLowerTextField(JTextField thetaLowerTextField) {
		this.thetaLowerTextField = thetaLowerTextField;
	}

	public JTextField getThetaUpperTextField() {
		return thetaUpperTextField;
	}

	public void setThetaUpperTextField(JTextField thetaUpperTextField) {
		this.thetaUpperTextField = thetaUpperTextField;
	}
	
	public static final double DEFAULT_ALPHA_BAND_LOWER_BOUND = 8;
	public static final double DEFAULT_ALPHA_BAND_UPPER_BOUND = 13;
	public static final double DEFAULT_BETA_BAND_LOWER_BOUND = 13;
	public static final double DEFAULT_BETA_BAND_UPPER_BOUND = 30;
	public static final double DEFAULT_GAMMA_BAND_LOWER_BOUND = 30;
	public static final double DEFAULT_GAMMA_BAND_UPPER_BOUND = 100;
	public static final double DEFAULT_DELTA_BAND_LOWER_BOUND = 4;
	public static final double DEFAULT_DELTA_BAND_UPPER_BOUND = 8;
	public static final double DEFAULT_THETA_BAND_LOWER_BOUND = 0.5;
	public static final double DEFAULT_THETA_BAND_UPPER_BOUND = 4;
}

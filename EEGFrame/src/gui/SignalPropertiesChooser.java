/**
 * 
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import dataHandling.InputFile;
import dataHandling.SignalParameterData;

/**
 * @author lsuc
 *
 */
public class SignalPropertiesChooser extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5967814832372532837L;

	private JComboBox fileList = null, signalList = null;
	private JTextField signalLabel, transducerType, physicalDimension, physicalMin, physicalMax, digitalMin, digitalMax, dataRecordSamplesNum, prefiltering;

	SignalPropertiesChooser(){

		this.setTitle ("Signal properties");
		this.setPreferredSize(new Dimension(600,400));
		this.setResizable(false);
		this.setLocationRelativeTo(null);	
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		this.add(panel, BorderLayout.CENTER);
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));		
		fileList = new JComboBox();
		fileList.setMaximumRowCount(3);
		fileList.setBorder(BorderFactory.createTitledBorder("Select file: "));
		panel.add(fileList);		
		fileList.setRenderer(new DefaultListCellRenderer(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				InputFile toBeRendered = (InputFile) value;
				setText(toBeRendered.getName());
				if(isSelected){
					setBackground(Color.LIGHT_GRAY);
				}
				else{
					setBackground(null);
				}
				return this;
			}
		});
		
	    fileList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				InputFile file = (InputFile) ((JComboBox)e.getSource()).getSelectedItem();
				setSignals(file);
			}								    			
		});
	    
	    signalList = new JComboBox();
//		signalList.setMaximumRowCount(3);
		signalList.setBorder(BorderFactory.createTitledBorder("Select signal: "));
		panel.add(signalList);		
		signalList.setRenderer(new DefaultListCellRenderer(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				SignalParameterData toBeRendered = (SignalParameterData) value;
				setText(toBeRendered.getLabel());
				if(isSelected){
					setBackground(Color.LIGHT_GRAY);
				}
				else{
					setBackground(null);
				}
				return this;
			}
		});
		
	    signalList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SignalParameterData signal = (SignalParameterData) ((JComboBox)e.getSource()).getSelectedItem();
				showSignalProperties(signal);
			}
			
										    			
		});
	    JPanel signalPropertiesPanel = new JPanel();
	    signalPropertiesPanel.setPreferredSize(new Dimension(200, 200));
	    signalPropertiesPanel.setLayout(new GridLayout(10,2));
	    JScrollPane scrollPane = new JScrollPane( signalPropertiesPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    scrollPane.setViewportView( signalPropertiesPanel);	
	    panel.add(scrollPane);
	    createTextFields();
	
	    signalPropertiesPanel.add(new JLabel(" Signal label: "));
	    JPanel signalLabelPanel = new JPanel();
	    signalLabelPanel.setLayout(new BoxLayout(signalLabelPanel, BoxLayout.Y_AXIS));
	    signalLabelPanel.add(Box.createVerticalGlue());
	    signalLabelPanel.add(signalLabel);
	    signalLabelPanel.add(Box.createVerticalGlue());
	    signalPropertiesPanel.add(signalLabelPanel);
	    signalPropertiesPanel.add(new JLabel(" Transducer type: "));
	    JPanel transducerTypePanel = new JPanel();
	    transducerTypePanel.setLayout(new BoxLayout(transducerTypePanel, BoxLayout.Y_AXIS));
	    transducerTypePanel.add(Box.createVerticalGlue());
	    transducerTypePanel.add(transducerType);
	    transducerTypePanel.add(Box.createVerticalGlue());
	    signalPropertiesPanel.add(transducerTypePanel);
	    signalPropertiesPanel.add(new JLabel(" Physical dimension: "));
	    JPanel physicalDimPanel = new JPanel();
	    physicalDimPanel.setLayout(new BoxLayout(physicalDimPanel, BoxLayout.Y_AXIS));
	    physicalDimPanel.add(Box.createVerticalGlue());
	    physicalDimPanel.add(physicalDimension);
	    physicalDimPanel.add(Box.createVerticalGlue());
	    signalPropertiesPanel.add(physicalDimPanel);
	    signalPropertiesPanel.add(new JLabel(" Physical minimum: "));
	    JPanel physicalMinPanel = new JPanel();
	    physicalMinPanel.setLayout(new BoxLayout(physicalMinPanel, BoxLayout.Y_AXIS));
	    physicalMinPanel.add(Box.createVerticalGlue());
	    physicalMinPanel.add(physicalMin);
	    physicalMinPanel.add(Box.createVerticalGlue());
	    signalPropertiesPanel.add(physicalMinPanel);
	    signalPropertiesPanel.add(new JLabel(" Physical maximum: "));
	    JPanel physicalMaxPanel = new JPanel();
	    physicalMaxPanel.setLayout(new BoxLayout(physicalMaxPanel, BoxLayout.Y_AXIS));
	    physicalMaxPanel.add(Box.createVerticalGlue());
	    physicalMaxPanel.add(physicalMax);
	    physicalMaxPanel.add(Box.createVerticalGlue());
	    signalPropertiesPanel.add(physicalMaxPanel);
	    signalPropertiesPanel.add(new JLabel(" Digital minimum: "));
	    JPanel digitalMinPanel = new JPanel();
	    digitalMinPanel.setLayout(new BoxLayout(digitalMinPanel, BoxLayout.Y_AXIS));
	    digitalMinPanel.add(Box.createVerticalGlue());
	    digitalMinPanel.add(digitalMin);
	    digitalMinPanel.add(Box.createVerticalGlue());
	    signalPropertiesPanel.add(digitalMinPanel);
	    signalPropertiesPanel.add(new JLabel(" Digital maximum: "));
	    JPanel digitalMaxPanel = new JPanel();
	    digitalMaxPanel.setLayout(new BoxLayout(digitalMaxPanel, BoxLayout.Y_AXIS));
	    digitalMaxPanel.add(Box.createVerticalGlue());
	    digitalMaxPanel.add(digitalMax);
	    digitalMaxPanel.add(Box.createVerticalGlue());
	    signalPropertiesPanel.add(digitalMaxPanel);
	    signalPropertiesPanel.add(new JLabel(" Number of samples in each data record: "));
	    JPanel dataRecordSamplesNumPanel = new JPanel();
	    dataRecordSamplesNumPanel.setLayout(new BoxLayout(dataRecordSamplesNumPanel, BoxLayout.Y_AXIS));
	    dataRecordSamplesNumPanel.add(Box.createVerticalGlue());
	    dataRecordSamplesNumPanel.add(dataRecordSamplesNum);
	    dataRecordSamplesNumPanel.add(Box.createVerticalGlue());
	    signalPropertiesPanel.add(dataRecordSamplesNumPanel);	   
	    signalPropertiesPanel.add(new JLabel(" Prefiltering: "));
	    JPanel prefilteringPanel = new JPanel();
	    prefilteringPanel.setLayout(new BoxLayout(prefilteringPanel, BoxLayout.Y_AXIS));
	    prefilteringPanel.add(Box.createVerticalGlue());
	    prefilteringPanel.add(prefiltering);
	    prefilteringPanel.add(Box.createVerticalGlue());
	    signalPropertiesPanel.add(prefilteringPanel);
	    
		
	    JPanel buttonPanel = new JPanel();
	    JButton closeButton = new JButton("Close");
	    closeButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
		});
	    buttonPanel.add(closeButton);	
	    panel.add(buttonPanel);
		this.pack();
	}
	
	public void setFiles(ArrayList<InputFile> list){
		InputFile[] files = list.toArray(new InputFile[list.size()]);
		fileList.setModel(new DefaultComboBoxModel(files));
		fileList.setSelectedIndex(files.length-1);
	}
	
	public void setSignals(InputFile file){
		SignalParameterData[] signals = file.getMetadata().getSignalParameters();
		signalList.setModel(new DefaultComboBoxModel(signals));
		signalList.setSelectedIndex(0);
	}
	
	public void createTextFields(){
    	
		signalLabel = new JTextField();
		signalLabel.setEditable(false);
	    transducerType = new JTextField();
	    transducerType.setEditable(false);
	    physicalDimension = new JTextField();
	    physicalDimension.setEditable(false);
	    physicalMin = new JTextField();
	    physicalMin.setEditable(false);
	    physicalMax = new JTextField();
	    physicalMax.setEditable(false);
	    digitalMin = new JTextField();
	    digitalMin.setEditable(false);
	    digitalMax = new JTextField();
	    digitalMax.setEditable(false);
	    dataRecordSamplesNum = new JTextField();
	    dataRecordSamplesNum.setEditable(false);
	    prefiltering = new JTextField();
	    prefiltering.setEditable(false);
	}

	private void showSignalProperties(SignalParameterData signal) {

		signalLabel.setText(signal.getLabel().trim());
	    transducerType.setText(signal.getTransducerType().trim());	   
	    physicalDimension.setText(signal.getPhysicalDimension().trim());	    
	    physicalMin.setText(Double.toString(signal.getPhysicalMin()).trim());
	    physicalMax.setText(Double.toString(signal.getPhysicalMax()).trim());
	    digitalMin.setText(Double.toString(signal.getDigitalMin()).trim());	    
	    digitalMax.setText(Double.toString(signal.getDigitalMax()).trim());
	    dataRecordSamplesNum.setText(Long.toString(signal.getSamplesInDataRecordNum()).trim());
	    prefiltering.setText(signal.getPrefiltering().trim());
	}	
}

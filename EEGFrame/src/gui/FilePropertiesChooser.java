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
import dataHandling.Metadata;

/**
 * @author lsuc
 *
 */
public class FilePropertiesChooser extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8223056956679113252L;
	private JComboBox fileList = null;
//	private ArrayList<String> headerDataLabels, headerData;
//	private JPanel filePropertiesPanel;
	private JTextField dataFormatVersion, localPatientId, localRecordingId, startDate, startTime, headerLengthBytes, fileType, dataRecordsNum, dataRecordDurationSeconds, signalsNum;
	
	FilePropertiesChooser(){

		this.setTitle ("File metadata");
		this.setPreferredSize(new Dimension(500,400));
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
				setFilePropertiesData(file);
			}								    			
		});
	    JPanel filePropertiesPanel = new JPanel();
	    filePropertiesPanel.setPreferredSize(new Dimension(200, 200));
	    filePropertiesPanel.setLayout(new GridLayout(10,2));
	    JScrollPane scrollPane = new JScrollPane(filePropertiesPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    scrollPane.setViewportView(filePropertiesPanel);	
	    panel.add(scrollPane);
	    createTextFields();
	
	    filePropertiesPanel.add(new JLabel(" Version Of Data Format: "));
	    JPanel versionPanel = new JPanel();
	    versionPanel.setLayout(new BoxLayout(versionPanel, BoxLayout.Y_AXIS));
	    versionPanel.add(Box.createVerticalGlue());
	    versionPanel.add(dataFormatVersion);
	    versionPanel.add(Box.createVerticalGlue());
	    filePropertiesPanel.add(versionPanel);
	    filePropertiesPanel.add(new JLabel(" Local Patient Identification: "));
	    JPanel patientIdPanel = new JPanel();
	    patientIdPanel.setLayout(new BoxLayout(patientIdPanel, BoxLayout.Y_AXIS));
	    patientIdPanel.add(Box.createVerticalGlue());
	    patientIdPanel.add(localPatientId);
	    patientIdPanel.add(Box.createVerticalGlue());
	    filePropertiesPanel.add(patientIdPanel);
	    filePropertiesPanel.add(new JLabel(" Local Recording Identification: "));
	    JPanel recordingPanel = new JPanel();
	    recordingPanel.setLayout(new BoxLayout(recordingPanel, BoxLayout.Y_AXIS));
	    recordingPanel.add(Box.createVerticalGlue());
	    recordingPanel.add(localRecordingId);
	    recordingPanel.add(Box.createVerticalGlue());
	    filePropertiesPanel.add(recordingPanel);
	    filePropertiesPanel.add(new JLabel(" Recording Start Date: "));
	    JPanel startDatePanel = new JPanel();
	    startDatePanel.setLayout(new BoxLayout(startDatePanel, BoxLayout.Y_AXIS));
	    startDatePanel.add(Box.createVerticalGlue());
	    startDatePanel.add(startDate);
	    startDatePanel.add(Box.createVerticalGlue());
	    filePropertiesPanel.add(startDatePanel);
	    filePropertiesPanel.add(new JLabel(" Recording Start Time: "));
	    JPanel startTimePanel = new JPanel();
	    startTimePanel.setLayout(new BoxLayout(startTimePanel, BoxLayout.Y_AXIS));
	    startTimePanel.add(Box.createVerticalGlue());
	    startTimePanel.add(startTime);
	    startTimePanel.add(Box.createVerticalGlue());
	    filePropertiesPanel.add(startTimePanel);
	    filePropertiesPanel.add(new JLabel(" Header Length In Bytes: "));
	    JPanel headerLengthPanel = new JPanel();
	    headerLengthPanel.setLayout(new BoxLayout(headerLengthPanel, BoxLayout.Y_AXIS));
	    headerLengthPanel.add(Box.createVerticalGlue());
	    headerLengthPanel.add(headerLengthBytes);
	    headerLengthPanel.add(Box.createVerticalGlue());
	    filePropertiesPanel.add(headerLengthPanel);
	    filePropertiesPanel.add(new JLabel(" File Type: "));
	    JPanel fileTypePanel = new JPanel();
	    fileTypePanel.setLayout(new BoxLayout(fileTypePanel, BoxLayout.Y_AXIS));
	    fileTypePanel.add(Box.createVerticalGlue());
	    fileTypePanel.add(fileType);
	    fileTypePanel.add(Box.createVerticalGlue());
	    filePropertiesPanel.add(fileTypePanel);
	    filePropertiesPanel.add(new JLabel(" Number Of Data Records: "));
	    JPanel dataRecordsNumPanel = new JPanel();
	    dataRecordsNumPanel.setLayout(new BoxLayout(dataRecordsNumPanel, BoxLayout.Y_AXIS));
	    dataRecordsNumPanel.add(Box.createVerticalGlue());
	    dataRecordsNumPanel.add(dataRecordsNum);
	    dataRecordsNumPanel.add(Box.createVerticalGlue());
	    filePropertiesPanel.add(dataRecordsNumPanel);
	    filePropertiesPanel.add(new JLabel(" Data Record Duration, In Seconds: "));
	    JPanel dataRecordsDurationPanel = new JPanel();
	    dataRecordsDurationPanel.setLayout(new BoxLayout(dataRecordsDurationPanel, BoxLayout.Y_AXIS));
	    dataRecordsDurationPanel.add(Box.createVerticalGlue());
	    dataRecordsDurationPanel.add(dataRecordDurationSeconds);
	    dataRecordsDurationPanel.add(Box.createVerticalGlue());
	    filePropertiesPanel.add(dataRecordsDurationPanel);
	    filePropertiesPanel.add(new JLabel(" Number Of Signals: "));
	    JPanel signalsNumPanel = new JPanel();
	    signalsNumPanel.setLayout(new BoxLayout(signalsNumPanel, BoxLayout.Y_AXIS));
	    signalsNumPanel.add(Box.createVerticalGlue());
	    signalsNumPanel.add(signalsNum);
	    signalsNumPanel.add(Box.createVerticalGlue());
	    filePropertiesPanel.add(signalsNumPanel);
	    
		
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
	
	public void createTextFields(){
		
		dataFormatVersion = new JTextField();
		dataFormatVersion.setEditable(false);
	    localPatientId = new JTextField();
	    localPatientId.setEditable(false);
	    localRecordingId = new JTextField();
	    localRecordingId.setEditable(false);
	    startDate = new JTextField();
	    startDate.setEditable(false);
	    startTime = new JTextField();
	    startTime.setEditable(false);
	    headerLengthBytes = new JTextField();
	    headerLengthBytes.setEditable(false);
	    fileType = new JTextField();
	    fileType.setEditable(false);
	    dataRecordsNum = new JTextField();
	    dataRecordsNum.setEditable(false);
	    dataRecordDurationSeconds = new JTextField();
	    dataRecordDurationSeconds.setEditable(false);
	    signalsNum = new JTextField();
	    signalsNum.setEditable(false);
	}
	
	private void setFilePropertiesData(InputFile file){
		Metadata metadata = file.getMetadata();
		dataFormatVersion.setText(metadata.getVersionOfDataFormat().trim());
	    localPatientId.setText(metadata.getLocalPatientIdentification().trim());	   
	    localRecordingId.setText(metadata.getLocalRecordingIdentification().trim());	    
	    startDate.setText(metadata.getRecordingStartDate().trim());
	    startTime.setText(metadata.getRecordingStartTime().trim());
	    headerLengthBytes.setText(Long.toString(metadata.getHeaderLengthInBytes()).trim());	    
	    fileType.setText(metadata.getReserved().trim());
	    dataRecordsNum.setText(Long.toString(metadata.getDataRecordsNum()).trim());
	    dataRecordDurationSeconds.setText(Double.toString(metadata.getDataRecordDurationInSec()).trim());
	    signalsNum.setText(Integer.toString(metadata.getSignalsNum()).trim());
	}
}


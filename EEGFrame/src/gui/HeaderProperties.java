/**
 * 
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import dataHandling.InputFile;
import dataHandling.Metadata;

/**
 * @author lsuc
 *
 */
public class HeaderProperties extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4324120657870176698L;
	private InputFile file;
	private ArrayList<String> headerDataLabels, headerData;
	private JPanel panel;

	public HeaderProperties(InputFile file){
		this.file = file;
		setTitle ("File metadata");
		setSize(100, 200);
		setResizable(false);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);		
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(true);
		panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		JScrollPane scrollPane = new JScrollPane(panel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    scrollPane.setViewportView(panel);	    
	    getContentPane().add(BorderLayout.CENTER, scrollPane);
		
	    JPanel buttonPanel = new JPanel();
	    getContentPane().add(BorderLayout.SOUTH, buttonPanel);
	    JButton OKButton = new JButton("OK");
	    buttonPanel.add(OKButton);
	    OKButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dispose();
			}
		});
		
	    createHeaderDataLabels();
	    getHeaderData();
	    showProperties();
	 
	}
	



	 private void createHeaderDataLabels(){
		 	headerDataLabels = new ArrayList<String>();
	   	 	headerDataLabels.add(" Version Of Data Format: ");
	    	headerDataLabels.add(" Local Patient Identification: ");
	    	headerDataLabels.add(" Local Recording Identification: ");
	    	headerDataLabels.add(" Recording Start Date: ");
	    	headerDataLabels.add(" Recording Start Time: ");
	    	headerDataLabels.add(" Header Length In Bytes: ");
	    	headerDataLabels.add(" File Type: ");
	    	headerDataLabels.add(" Number Of Data Records: ");
	    	headerDataLabels.add(" Data Record Duration: ");
	    	headerDataLabels.add(" Number Of Signals: ");
	    }
	 
	 private void getHeaderData(){
		 
		 Metadata metadata = file.getMetadata();
		 headerData = new ArrayList<String>();
		 headerData.add(metadata.getVersionOfDataFormat());
		 headerData.add(metadata.getLocalPatientIdentification());
		 headerData.add(metadata.getLocalRecordingIdentification());
		 headerData.add(metadata.getRecordingStartDate());
		 headerData.add(metadata.getRecordingStartTime());
		 headerData.add(Long.toString(metadata.getHeaderLengthInBytes()));
		 headerData.add(metadata.getReserved());
		 headerData.add(Long.toString(metadata.getDataRecordsNum()));
		 headerData.add(Double.toString(metadata.getDataRecordDurationInSec()));
		 headerData.add(Integer.toString(metadata.getSignalsNum()));
	 }
	 
	 public void showProperties(){
		 for(int i = 0; i < headerDataLabels.size(); i++){
			 panel.add(new JLabel(headerDataLabels.get(i)));
			 JLabel label = new JLabel(headerData.get(i).trim()+ " ");
			 label.setForeground(Color.BLUE);
			 panel.add(label);
		 }
	 }
	 
		public InputFile getFile() {
			return file;
		}
		public void setFile(InputFile file) {
			this.file = file;
			getHeaderData();
			showProperties();
			setVisible(true);
		}
}

/**
 * 
 */
package gui;

/**
 * @author lsuc
 *
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.MaskFormatter;

import dataHandling.InputFile;
import dataHandling.Metadata;

public class ExportSamplesWindow extends JDialog { 
	 
	private static final long serialVersionUID = 1L;
	private JList signalsList;
	private JList signalsToExportList = null;
	private final String DEFAULT_OPTION = "Samples";
	private String exportOption = DEFAULT_OPTION;
	private final String DATE_TIME_OPTION = "Date/Time";
	private final String ELAPSED_TIME_OPTION = "Elapsed time";
	private JFileChooser saveChooser;
	private JFormattedTextField startField;
	private JFormattedTextField endField;
	
	private final String SELECTED = "SELECTED";
	private final String ALL = "ALL";
	private String signalsToExport = SELECTED;
	
	private double start = 0;
	private double end = 0;
	
	ExportSamplesWindow(){
		

		this.initExportSamplesWindow();
		this.pack();
				
		return;
	}
	
	public void initExportSamplesWindow(){
		
		this.setTitle ("Samples To Text");
		this.setSize(500, 500);
		this.setLocationRelativeTo(null);		
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		this.setLeftPanel();
		this.setRightPanel();
		this.setCenterPanel();		
	    this.setResizable(false);
	}
	
	public void setLeftPanel(){
		
		JPanel panelLeft = new JPanel();	
		panelLeft.setPreferredSize(new Dimension(200,300));
		JPanel buttonPanel = new JPanel();
		panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));
		this.add(BorderLayout.WEST, panelLeft);
		
		
		this.signalsList = new JList(new DefaultListModel());		
		this.signalsList.setVisibleRowCount(10);
		this.signalsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		this.signalsList.setCellRenderer(new DefaultListCellRenderer(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				SelectedSignal toBeRendered = (SelectedSignal) value;
				setText(toBeRendered.getFile().getName() + ": " + toBeRendered.getSignalLabel());
				if(isSelected){
					setBackground(Color.LIGHT_GRAY);
				}
				else{
					setBackground(null);
				}
				return this;
			}
		});
		
	    
	    this.signalsList.addListSelectionListener(new ListSelectionListener(){
	    											public void valueChanged(ListSelectionEvent event){
	    													if (!event.getValueIsAdjusting()){
	    														for(Object o : signalsList.getSelectedValues()){	    															
	    															DefaultListModel selectedSignalsModel = (DefaultListModel)signalsToExportList.getModel(); 
									    				
	    															if(!selectedSignalsModel.contains(o)){ 
	    																selectedSignalsModel.addElement(o);
	    															} 
	    														}
	    													}
	    											}
		});
	    
	    JButton exportAllSignalsButton = new JButton("Export All");
	    exportAllSignalsButton.addActionListener(new ActionListener(){
						    						public void actionPerformed(ActionEvent e){
						    							setVisible(false);						    							
						    							signalsToExport = ALL;
						    							
    													String startTime = startField.getText();    													
    											        start = getTimeInSeconds(startTime);
    											        
    											        String endTime = endField.getText();      													
    											        end = getTimeInSeconds(endTime);   											        

						    							createSaveChooser();
						    							
						    						}
		});
	    
		JScrollPane paneLeft = new JScrollPane(signalsList);
		panelLeft.add(paneLeft);
		
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.setPreferredSize(new Dimension(150,100));
		panelLeft.add(Box.createVerticalGlue());
		
		panelLeft.add(buttonPanel);
		buttonPanel.add(Box.createVerticalGlue());
		
		buttonPanel.add(exportAllSignalsButton);
		exportAllSignalsButton.setAlignmentX(CENTER_ALIGNMENT);
		
		buttonPanel.add(Box.createVerticalGlue());
		panelLeft.add(Box.createVerticalGlue());
	}

	public void setSignals(ArrayList<SelectedSignal> signals){
		
		DefaultListModel signalsModel = (DefaultListModel) this.signalsList.getModel();
		if(signalsModel.size() > 0){
			signalsModel.clear();
		}
		for(int i = 0; i < signals.size(); i++){
			signalsModel.addElement(signals.get(i));
		}
	}
	
	public void setRightPanel(){
		
		JPanel panelRight = new JPanel();
	    panelRight.setPreferredSize(new Dimension(200,300));
		JPanel buttonPanel = new JPanel();
		panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.Y_AXIS));
		this.add(BorderLayout.EAST, panelRight);
		
		this.signalsToExportList = new JList(new DefaultListModel());
	    this.signalsToExportList.setVisibleRowCount(10);
	    this.signalsToExportList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	    this.signalsToExportList.setCellRenderer(new DefaultListCellRenderer(){
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				SelectedSignal toBeRendered = (SelectedSignal) value;
				setText(toBeRendered.getFile().getName() + ": "+toBeRendered.getSignalLabel());
				if(isSelected){
					setBackground(Color.LIGHT_GRAY);
				}
				else{
					setBackground(null);
				}
				return this;
			}
		});
		
	    
	    JButton removeButton = new JButton("<< Remove");
		removeButton.addActionListener(new ActionListener(){
							    		public void actionPerformed(ActionEvent e){
							    			for(Object o : signalsToExportList.getSelectedValues()){	
							    				DefaultListModel signalsToExportModel = (DefaultListModel)(signalsToExportList.getModel());
							    				signalsToExportModel.removeElement(o);
						    					DefaultListModel signalsToSelectModel = (DefaultListModel)(signalsList.getModel());		
						    					if(signalsToSelectModel.contains(o)){ 	
						    						signalsList.removeSelectionInterval(signalsToSelectModel.indexOf(o), signalsToSelectModel.indexOf(o));
						    					} 
							    			}
							    		}		
		});
	    
		JButton exportSelectedButton = new JButton("Export");
		exportSelectedButton.addActionListener(new ActionListener(){
	    										public void actionPerformed(ActionEvent e){
	    											ArrayList<SelectedSignal> selectedSignals = new ArrayList<SelectedSignal>();
	    												for(int i = 0; i < signalsToExportList.getModel().getSize(); i++){	    													
	    													SelectedSignal signal = (SelectedSignal) signalsToExportList.getModel().getElementAt(i);
	    													selectedSignals.add(signal);
	    												}	    												
	    												
	    												if(selectedSignals.size() > 0){
	    													setVisible(false);	    													
	    													signalsToExport = SELECTED;	    													
	    													String startTime = startField.getText(); 	    													
	    											        start = getTimeInSeconds(startTime);	    											        
	    											        String endTime = endField.getText();  	    													
	    											        end = getTimeInSeconds(endTime);
	    													createSaveChooser();	    													
	    												}	    											
	    										}
	    });
		 
		JScrollPane paneRight = new JScrollPane(signalsToExportList);
		panelRight.add(paneRight); 
	
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
//		buttonPanel.setPreferredSize(new Dimension(150,80));
		panelRight.add(Box.createVerticalGlue());
		panelRight.add(buttonPanel); 
	
		panelRight.add(Box.createVerticalGlue());
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(removeButton);
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(exportSelectedButton);
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(Box.createVerticalGlue());
		panelRight.add(Box.createVerticalGlue());
	}
	
	public void setCenterPanel(){
		
		JPanel centerPanel = new JPanel();
	    this.add(BorderLayout.CENTER, centerPanel);
	    centerPanel.setPreferredSize(new Dimension(170,200));
	    centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
	    
	    JRadioButton onlySamplesButton = new JRadioButton("Only Samples");
		onlySamplesButton.setSelected(true);
		onlySamplesButton.addActionListener(new ActionListener(){	
												public void actionPerformed(ActionEvent e){
													exportOption = DEFAULT_OPTION;
												}
		});
		
		JRadioButton dateTimeButton = new JRadioButton("Date/Time");
		dateTimeButton.addActionListener(new ActionListener(){	
											public void actionPerformed(ActionEvent e){
												exportOption = DATE_TIME_OPTION;
											}
		});
		
		JRadioButton elapsedTimeButton = new JRadioButton("Elapsed Time");
		elapsedTimeButton.addActionListener(new ActionListener(){	
												public void actionPerformed(ActionEvent e){
													exportOption = ELAPSED_TIME_OPTION;
												}
		});
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

		ButtonGroup samplesViewGroup = new ButtonGroup();
		samplesViewGroup.add(onlySamplesButton);
		samplesViewGroup.add(dateTimeButton);
		samplesViewGroup.add(elapsedTimeButton);
		
		buttonPanel.setBorder(BorderFactory.createTitledBorder("Export option"));
		
		JPanel startPanel = new JPanel();		
		
		Border titledBorder = BorderFactory.createTitledBorder("Enter start time(optional)");
		startPanel.setBorder(titledBorder);	
		
		MaskFormatter mask = null;
		try {
			mask = new MaskFormatter("##:##:##.###");
			mask.setPlaceholderCharacter('0');
		} catch (ParseException e) {
		e.printStackTrace();
		}

		startField = new JFormattedTextField(mask);
		startField.setPreferredSize(new Dimension(80,20));
		startField.setAlignmentX(CENTER_ALIGNMENT);

		startPanel.add(startField);
		
		JPanel endPanel = new JPanel();		
		Border titled = BorderFactory.createTitledBorder("Enter end time(optional)");
		endField = new JFormattedTextField(mask);
		endField.setPreferredSize(new Dimension(80,20));
		endField.setAlignmentX(CENTER_ALIGNMENT);
		
		endPanel.add(endField);
		endPanel.setBorder(titled);
		
		buttonPanel.add(onlySamplesButton);
		buttonPanel.add(dateTimeButton);
		buttonPanel.add(elapsedTimeButton);
		
		centerPanel.add(Box.createVerticalGlue());
		centerPanel.add(buttonPanel);
		buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
		centerPanel.add(Box.createVerticalGlue());
		centerPanel.add(startPanel);
		startPanel.setAlignmentX(CENTER_ALIGNMENT);
		centerPanel.add(Box.createVerticalGlue());
		centerPanel.add(endPanel);
		endPanel.setAlignmentX(CENTER_ALIGNMENT);
		centerPanel.add(Box.createVerticalGlue());
	}
	
	public boolean createSaveChooser(){
		if(saveChooser == null){
			saveChooser = new JFileChooser();
			saveChooser.setCurrentDirectory(null);
		}	
		saveChooser.setSelectedFile(new File("Samples.txt"));
		FileFilter[] oldFilters =  saveChooser.getChoosableFileFilters();
		for (FileFilter filter : oldFilters){
			saveChooser.removeChoosableFileFilter(filter);
		}		
		saveChooser.addChoosableFileFilter(new SaveFileFilter(){});
        
		if(saveChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
			File selectedFile = saveChooser.getSelectedFile();
			if (selectedFile == null){
				JOptionPane.showMessageDialog(null, "Error while selecting file. Please select another file.", "Export samples", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			if (selectedFile.exists()== true){
				int value = JOptionPane.showConfirmDialog(null, "File already exists! \n" + "Are you sure you want to override this file? ", "Export samples", JOptionPane.YES_NO_CANCEL_OPTION);
				if(value != JOptionPane.YES_OPTION){
					return false;
				}
			}		
			saveChooser.setCurrentDirectory(selectedFile);	 
			createExportSamplesWorker(selectedFile.getAbsolutePath());
			return true;
		}
		return false;
	}


	
	public void createExportSamplesWorker(String filePath){
		new ExportSamplesWorker(this, filePath).execute();
	}
	
	public void createWaitDialog(){
		//TODO dodati kursor za cekanje
//        waitDialog = new JDialog();
//        waitDialog.setUndecorated(true);
//        JPanel panel = new JPanel();
//        JLabel label = new JLabel("Extracting features...Please wait...");
//        panel.add(label);
//        waitDialog.add(panel);
//        waitDialog.pack();
//        waitDialog.setLocationRelativeTo(null);
//        waitDialog.setVisible(true);
	}
	public void exportSamples(String filePath) throws IOException {
	
		PrintWriter writer = null;
		try {
			File extractionFile = new File(filePath);
			if(!extractionFile.exists()) {
				extractionFile.createNewFile();
			} 
			writer = new PrintWriter(new BufferedWriter(new FileWriter(extractionFile)));
			
			JList signals = new JList();		
			if(signalsToExport.equals(SELECTED)){
				signals = signalsToExportList;
			}
			else if(signalsToExport.equals(ALL)){
				signals = signalsList;			
			}	
			writer.format("Signal names\t");
			SelectedSignal signal;
			for(int i = 0; i < signals.getModel().getSize()-1; i++){
				signal = (SelectedSignal)(signals.getModel().getElementAt(i));
				writer.format("%10s\t", signal.getSignalLabel().trim());
			}
			signal = (SelectedSignal)(signals.getModel().getElementAt(signals.getModel().getSize()-1));
			writer.format("%10s", signal.getSignalLabel().trim());
			writer.println();

			if(exportOption.equals(DEFAULT_OPTION)){
				Metadata metadata;
				writer.format("Sample interval\t");
				for(int i = 0; i < signals.getModel().getSize()-1; i++){
					signal = (SelectedSignal)(signals.getModel().getElementAt(i));
					metadata = signal.getFile().getMetadata();
					String interval = Double.toString(metadata.getDataRecordDurationInSec()/metadata.getSignalParameters()[signal.getSignalIndex()].getSamplesInDataRecordNum());
					while(interval.length() < 9){
						interval += "0";
					}
					if(interval.length() > 9){
					
						if (Integer.parseInt(interval.substring(9,10)) > 4) {
							Integer decimal = Integer.parseInt(interval.substring(8,9));
							if( decimal < 9){
								decimal++;
								interval = interval.substring(0,8);
								interval += decimal;
							}
				
							else {
								interval = interval.substring(0,9);
							}
						}
					
					}
					writer.format("(%9s sec)\t",interval);		
					}
					signal = (SelectedSignal)(signals.getModel().getElementAt(signals.getModel().getSize()-1));
					metadata = signal.getFile().getMetadata();
					String interval = Double.toString(metadata.getDataRecordDurationInSec()/metadata.getSignalParameters()[signal.getSignalIndex()].getSamplesInDataRecordNum());
					while(interval.length() < 9){
						interval += "0";
					}
					if(interval.length() > 9){				
						if (Integer.parseInt(interval.substring(9,10)) > 4) {
							Integer decimal = Integer.parseInt(interval.substring(8,9));
							if( decimal < 9){
								decimal++;
								interval = interval.substring(0,8);
								interval += decimal;
							}
			
							else {
								interval = interval.substring(0,9);
							}
						}
				
					}
					writer.format("(%9s sec)\t",interval);	
					writer.println();
			}
			else if(exportOption.equals(DATE_TIME_OPTION)){
				writer.format("%25s\t", "Time Date");
				writer.println();
				writer.format("(hh:mm:ss.mmm dd/mm/yyyy)\t");
				writer.println();
			}
			
			else if(exportOption.equals(ELAPSED_TIME_OPTION)) {
				writer.format("%15s\t", "Elapsed time");
				writer.println();
				writer.format("hh:mm:ss.mmm\t");
				writer.println();
			}
		
			Metadata metadata;
			writer.format("Physical dimension\t");
			for(int i = 0; i < signals.getModel().getSize()-1; i++){
				signal = (SelectedSignal)(signals.getModel().getElementAt(i));
				metadata = signal.getFile().getMetadata();
				String physicalDimension = "(" + metadata.getSignalParameters()[signal.getSignalIndex()].getPhysicalDimension().trim()+")";
				writer.format("%10s\t", physicalDimension);
			}
			signal = (SelectedSignal)(signals.getModel().getElementAt(signals.getModel().getSize()-1));
			metadata = signal.getFile().getMetadata();
			String physicalDimension = "(" + metadata.getSignalParameters()[signal.getSignalIndex()].getPhysicalDimension().trim()+")";
			writer.format("%10s\t", physicalDimension);
			writer.println();	

			double[] frequencies = new double[signals.getModel().getSize()];
			for(int i = 0; i < signals.getModel().getSize(); i++){
				signal = (SelectedSignal)(signals.getModel().getElementAt(i));
				frequencies[i] = signal.getFile().calculateFrequency(signal.getSignalIndex());
			}
			double maxFrequency = calculateMaxFreq(frequencies);
			signal = (SelectedSignal)(signals.getModel().getElementAt(0));
			InputFile file = signal.getFile();
		
			String startTime = "";
			String startDate = "";	
			startTime = "00:00:00.000";
			if(exportOption.equals(DATE_TIME_OPTION)){					
				startTime = file.getMetadata().getRecordingStartTime().trim();
				System.out.println(startTime);
				startTime = formatTimeWithSemicolon(startTime);
				startDate = file.getMetadata().getRecordingStartDate().trim();
				startDate = formatDate(startDate);
			}
			double duration = file.getMetadata().getDataRecordDurationInSec()* file.getMetadata().getDataRecordsNum();
			int startSample = (int)Math.ceil((start-getTimeInSeconds(startTime))* maxFrequency);
			if(startSample < 0){
				startSample = 0;
			}
			int endSample = (int)Math.floor((end-getTimeInSeconds(startTime))* maxFrequency);
			if(endSample <= 0 || endSample < startSample || endSample > duration*maxFrequency){
				endSample = (int)Math.ceil(duration*maxFrequency);
			}
			ArrayList<double[]> allSignalsamples = new ArrayList<double[]>();				
			for(int j = 0; j < signals.getModel().getSize(); j++){
				signal = (SelectedSignal)(signals.getModel().getElementAt(j));
				double[] samples = file.getSamplesFromInterval(signal.getSignalIndex(), startSample, endSample);
				allSignalsamples.add(samples);
			}
			writer.println();	
			for(int i = startSample; i < endSample; i++){
				if(exportOption.equals(DEFAULT_OPTION)){
				
					writer.format("%15s\t", i);
				}
				else if(exportOption.equals(DATE_TIME_OPTION)){
					
					if(i == 0){	
						writer.format("("+ startTime+ " " + startDate+")\t");
					}
					else{
						double intervalSec = getTimeInSeconds(startTime);
						String formattedTime = getFormatted(intervalSec+i/maxFrequency);
						writer.format("("+ formattedTime+ " " + startDate+")\t");
					}				
				}	
				else if(exportOption.equals(ELAPSED_TIME_OPTION)){
					if(i == 0){
						
						writer.format(startTime + "\t");
					}
					else{
						startTime = getFormatted(i/maxFrequency);
						writer.format(startTime + "\t");
					}
				}
				
				for(int j = 0; j < allSignalsamples.size(); j++){
					if(frequencies[j] == maxFrequency){
						double realSample = allSignalsamples.get(j)[i];
						writer.format("%10.3f\t", realSample);
					}	
					else {
						double time = i/maxFrequency;
						double index = time * frequencies[j];
						if(Math.floor(index)== index ){
							double realSample = allSignalsamples.get(j)[(int)index];
							writer.format("%10.3f\t", realSample);
						}
						else {
							writer.format("%10s\t", " ");
						}
					}
				}
				writer.println();
			
			}
		} finally {
			if (writer != null) {
				writer.close();
			}
		}	
	}

	public void setSignalsList(DefaultListModel defaultListModel) {
		this.signalsList = new JList(defaultListModel);
	}

	public JList getSignalsToExportList() {
		return signalsToExportList;
	}
	public String formatDate(String date){
		
		if(date.contains(".") || date.contains("-") || date.contains(":")){ 
			date = date.replace(".", "/");
			date = date.replace("-", "/");
			date = date.replace(":", "/");
		}
		int index = date.length()-1;
		int yearDigit = 0;
		
		while(Character.isDigit(date.trim().charAt(index))){
			index--;
			yearDigit++;
		}
		if(yearDigit == 2){
			String oldDigits = date.substring(index+1, date.length());
			date = date.substring(0, index+1);
			date += "20"+oldDigits;
		}
		return date;
		
	}
	
	public static String formatTimeWithSemicolon(String time){ 
		
		String list[] = time.split("\\D");
		int hours = Integer.parseInt(list[0]);
		int minutes = Integer.parseInt(list[1]);
		int seconds = Integer.parseInt(list[2]);
		time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
		time+=".000";
		return time;		
		
	}
	
	
	public int getMaxSamplesNum(ArrayList<ArrayList<Short>> samples){
		int maxSamples = samples.get(0).size();
		for(int i = 1; i < samples.size(); i++){
			if(samples.get(i).size() > maxSamples){
				maxSamples = samples.get(i).size();
			}
		}
		return maxSamples;
	}
	

	public double getTimeInSeconds(String time){
		
		String[] list = time.split("\\D");
		double milisec = 0;
		
		int hours = Integer.parseInt(list[0]);
		int minutes = Integer.parseInt(list[1]);
		int seconds = Integer.parseInt(list[2]);
		if(list.length == 4){
			milisec = Double.parseDouble("0."+list[3]);
		}
		
		return hours*3600+minutes*60+seconds+milisec;		
		
	}
	
	public double calculateMaxFreq(double[] frequencies){
		
		double maxFreq = frequencies[0];
		for(int i = 1; i < frequencies.length; i++ ){
			if(frequencies[i] > maxFreq){
				maxFreq = frequencies[i];
			}
		}
		return maxFreq;
	}
	
	public String getFormatted(double secondsInterval){
		int hours = 0;
		int minutes = 0;
		int seconds = 0;
		int milliseconds = 0;
				
		String[] list = Double.toString(secondsInterval).split("\\D");
		
		seconds = Integer.parseInt(list[0]);	
		milliseconds = (int)Math.round(Double.parseDouble("0."+list[1])*1000);
    	
		
		if(seconds >= 60){
			
			minutes += (int)seconds/60;
			seconds %= 60;
		}
		
		if(minutes >= 60){
			
			hours += (int)minutes/60;
			minutes %= 60;
		}
		
		hours %= 24;	    
		
		String formattedTime = String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds);
		return formattedTime;
	}
}


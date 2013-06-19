/**
 * 
 */
package gui;

import java.awt.Cursor;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import dataHandling.InputFile;


/**
 * @author lsuc
 */
public class Controller {
	
	private JFrame frame;
	private JFileChooser fileChooser;
	private ArrayList<InputFile> files;
	private SignalChooserWindow signalChooser;
	private PanelController panelController;
	private FilePropertiesChooser filePropertiesChooser;
	private SignalPropertiesChooser signalPropertiesChooser;
	private ExportSamplesWindow exportSamplesWindow;
	
	public Controller(JFrame frame, PanelController panelController){
		this.frame = frame;
		this.panelController = panelController;
//		this.univariateFeaturesExtractionController = univariateFeaturesExtractionController;
		signalChooser = SignalChooserWindow.getInstance(this);
	}
	
	public void disableMenus(JMenuBar menuBar){	
		EEGFrameMain.checkOnEventDispatchThread();
		for(int i = 0; i < menuBar.getMenuCount(); i++){
			if(menuBar.getMenu(i).getName() != EEGFrameMain.FILE_MENU){
				menuBar.getMenu(i).setEnabled(false);
			}
		}
	}
	
	public void enableMenus(JMenuBar menuBar){	
		EEGFrameMain.checkOnEventDispatchThread();
		for(int i = 0; i < menuBar.getMenuCount(); i++){
			if(!menuBar.getMenu(i).isEnabled()){
				menuBar.getMenu(i).setEnabled(true);
			}
		}
	}	
	
	
	public void disableFileLoadedNoSignalsMenus(JMenuBar menuBar){	
		EEGFrameMain.checkOnEventDispatchThread();
		for(int i = 0; i < menuBar.getMenuCount(); i++){
			if((menuBar.getMenu(i).getName().equals(EEGFrameMain.FILE_MENU)) 
					|| (menuBar.getMenu(i).getName().equals(EEGFrameMain.SIGNALS_MENU)) 
					|| (menuBar.getMenu(i).getName().equals(EEGFrameMain.TOOLS_MENU)) 
					|| (menuBar.getMenu(i).getName().equals(EEGFrameMain.PROPERTIES_MENU))
					){
				menuBar.getMenu(i).setEnabled(true);
			}
			else{
				menuBar.getMenu(i).setEnabled(false);
			}
		}
	}	
	public boolean openFile(){
		EEGFrameMain.checkOnEventDispatchThread();
		if(fileChooser == null){
			FileFilter filter = new FileNameExtensionFilter("EDF files", "edf", "rec");			 
			fileChooser = new JFileChooser();
			fileChooser.addChoosableFileFilter(filter);
			fileChooser.setCurrentDirectory(null);
			files = new ArrayList<InputFile>();
		}			
//		FileFilter[] oldFilters = fileChooser.getChoosableFileFilters();
//		for (FileFilter filter : oldFilters){
//			fileChooser.removeChoosableFileFilter(filter);
//		}
//		this.fileChooser.addChoosableFileFilter(new OpenFileFilter(){});
//		 FileFilter filter = new FileNameExtensionFilter("JPEG file", "jpg", "jpeg");
//		 JFileChooser fileChooser = ...;
//		 fileChooser.addChoosableFileFilter(filter);
		 
		
	    if( fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
	    	File selectedFile = fileChooser.getSelectedFile();
			if (selectedFile == null){
				JOptionPane.showMessageDialog(null, "Error while loading file. Please select another file.", "Loading selected file", JOptionPane.ERROR_MESSAGE);
				
				return false;
			}
			if (selectedFile.exists()== false){
				JOptionPane.showMessageDialog(null, "File doesn't exist.", "Loading selected file", JOptionPane.ERROR_MESSAGE);
				return false;
			}		
			fileChooser.setCurrentDirectory(selectedFile);	  
			for(int i = 0; i < files.size(); i++){
				if(files.get(i).getName().equals(selectedFile.getAbsolutePath())){
					return true;
				}
			}
			loadFile(selectedFile);
			return true;
	    }
	    else {
	    	return false;
	    }
	}
	
	public void clearYScaleFactorSelection(){	
		EEGFrameMain.checkOnEventDispatchThread();
		JMenuBar menuBar = frame.getJMenuBar();
		for(int i = 0; i < menuBar.getMenuCount(); i++){
			if(menuBar.getMenu(i).getName().equals(EEGFrameMain.Y_SCALE_FACTOR_MENU)){
				for(int j = 0; j < menuBar.getMenu(i).getItemCount(); j++){
					if(menuBar.getMenu(i).getItem(j).getText().equals(EEGFrameMain.INVISIBLE_Y_SCALE_MENU_ITEM)){
						JRadioButtonMenuItem invisibleButton = (JRadioButtonMenuItem) menuBar.getMenu(i).getItem(j);
							invisibleButton.setSelected(true);
							return;
					}
				}  
			}
		}
	}	
	
	public void setCustomYScaleFactorText(float value){	
		EEGFrameMain.checkOnEventDispatchThread();
		JMenuBar menuBar = frame.getJMenuBar();
		for(int i = 0; i < menuBar.getMenuCount(); i++){
			if(menuBar.getMenu(i).getName().equals(EEGFrameMain.Y_SCALE_FACTOR_MENU)){
				int count = menuBar.getMenu(i).getItemCount();
				JMenuItem customItem = menuBar.getMenu(i).getItem(count-1);
				if(customItem.getName().equals(EEGFrameMain.CUSTOM_Y_SCALE_MENU_ITEM)){
					customItem.setText("Custom: " + Float.toString(value));
				}  
			}
		}
	}	
	
	public void setCustomAmplitudeText(float value){	
		EEGFrameMain.checkOnEventDispatchThread();
		JMenuBar menuBar = frame.getJMenuBar();
		for(int i = 0; i < menuBar.getMenuCount(); i++){
			if(menuBar.getMenu(i).getName().equals(EEGFrameMain.AMPLITUDE_MENU)){
				int count = menuBar.getMenu(i).getItemCount();
				JMenuItem customItem = menuBar.getMenu(i).getItem(count-1);
				if(customItem.getName().equals(EEGFrameMain.CUSTOM_AMPLITUDE_MENU_ITEM)){
					customItem.setText("Custom: " + Float.toString(value));
				}  
			}
		}
	}	
	
	public void loadFile(File selectedFile){	
		 frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		 new LoadFileWorker(selectedFile, this).execute();
	}
	
	
	public void updateSignalsToSelect(InputFile file){
		SelectedSignal[] signals = new SelectedSignal[file.getMetadata().getSignalsNum()];
		 for(int i = 0; i < signals.length; i++){
			 signals[i] = new SelectedSignal(i, file.getMetadata().getSignalParameters()[i].getLabel(), file, i);
		 }				 
		 this.signalChooser.setSignalsToSelect(signals);
	}
	
	public void updateMenu(ArrayList<SelectedSignal> list){
		this.panelController.setSignalsForViewing(list);
		this.signalChooser.setVisible(false);
		if(list.size() > 0){
			enableMenus(this.frame.getJMenuBar());
		}
		else{
			disableFileLoadedNoSignalsMenus(this.frame.getJMenuBar());
		}
	}
	
	public float createCustomDialog(float lastValue, Object message, String title, Object warningMessage){
		String response = "";
		boolean isANumber = false;
		float value = 0;
		while(!isANumber){
			//TODO only absolute value of scale factor is taken, zero is ignored
			response = (String)JOptionPane.showInputDialog(null,
					message,
					title, 
					JOptionPane.PLAIN_MESSAGE,
					null, null, lastValue
			);
			if(response == null){
				return 0;
			}
			try {
				value = Float.parseFloat(response);
	   			isANumber = true;

			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, warningMessage, "Input error", JOptionPane.ERROR_MESSAGE);
				isANumber = false;
			}
		}
		value = Float.parseFloat(response);
		return value;
	}
	
	public JFrame getFrame() {
		return frame;
	}

	public ArrayList<InputFile> getFiles() {
		return files;
	}

	public void setFiles(ArrayList<InputFile> files) {
		this.files = files;
	}

	public SignalChooserWindow getSignalChooser() {
		return signalChooser;
	}

	public void setSignalChooser(SignalChooserWindow signalChooser) {
		this.signalChooser = signalChooser;
	}

//	public ExtractUnivariateFeaturesController getUnivariateFeaturesExtractionController() {
//		return univariateFeaturesExtractionController;
//	}
//
//	public void setUnivariateFeaturesExtractionController(
//			ExtractUnivariateFeaturesController univariateFeaturesExtractionController) {
//		this.univariateFeaturesExtractionController = univariateFeaturesExtractionController;
//	}

	public synchronized void showFilePropertiesChooser(){
		if(filePropertiesChooser == null){
			filePropertiesChooser = new FilePropertiesChooser();
		}
	
		filePropertiesChooser.setFiles(files);
		filePropertiesChooser.setVisible(true);
		
	}
	
	public synchronized void showSignalPropertiesChooser(){
		if(signalPropertiesChooser == null){
			signalPropertiesChooser = new SignalPropertiesChooser();
		}	
		signalPropertiesChooser.setFiles(files);
		signalPropertiesChooser.setVisible(true);
		
	}
	
	public synchronized void showExportSamplesWindow(ArrayList<SelectedSignal> signals){
		if(exportSamplesWindow == null){
			exportSamplesWindow = new ExportSamplesWindow();
		}	
		exportSamplesWindow.setSignals(signals);
		exportSamplesWindow.setVisible(true);
		
	}
//	public void setScalePosibilities(){
//		int sigNum = getRightList().getModel().getSize();
//		if(sigNum > 0){
//			if(sigNum == 1){
//				getFrame().getScaleTwentyItem().setEnabled(true);
//				getFrame().getScaleFifteenItem().setEnabled(true);
//				getFrame().getScaleTenItem().setEnabled(true);
//				getFrame().getScaleFiveItem().setEnabled(true);
//				getFrame().getScaleThreeItem().setEnabled(true);
//			}	
//			
//			else if (sigNum == 2){
//				getFrame().getScaleTwentyItem().setEnabled(false);
//				getFrame().getScaleFifteenItem().setEnabled(true);
//				getFrame().getScaleTenItem().setEnabled(true);
//				getFrame().getScaleFiveItem().setEnabled(true);
//				getFrame().getScaleFourItem().setEnabled(true);
//				getFrame().getScaleThreeItem().setEnabled(true);
//				getFrame().getScaleOneItem().setSelected(true);
//				getFrame().getParameters().setyScaleFactor(MainFrame.yScale.ONE.getValue());
//				
//				
//			}
//			else if(sigNum == 3){
//				getFrame().getScaleTwentyItem().setEnabled(false);
//				getFrame().getScaleFifteenItem().setEnabled(false);
//				getFrame().getScaleTenItem().setEnabled(true);
//				getFrame().getScaleFiveItem().setEnabled(true);
//				getFrame().getScaleFourItem().setEnabled(true);
//				getFrame().getScaleThreeItem().setEnabled(true);
//				
//				getFrame().getScaleOneItem().setSelected(true);
//				getFrame().getParameters().setyScaleFactor(MainFrame.yScale.ONE.getValue());
//			}
//			
//			else if(sigNum == 4){
//				getFrame().getScaleTwentyItem().setEnabled(false);
//				getFrame().getScaleFifteenItem().setEnabled(false);
//				getFrame().getScaleTenItem().setEnabled(false);
//				getFrame().getScaleFiveItem().setEnabled(true);
//				getFrame().getScaleFourItem().setEnabled(true);
//				getFrame().getScaleThreeItem().setEnabled(true);
//				
//				getFrame().getScaleOneItem().setSelected(true);
//				getFrame().getParameters().setyScaleFactor(MainFrame.yScale.ONE.getValue());
//			}
//			
//			else if(sigNum >= 5 && sigNum <= 7){
//				getFrame().getScaleTwentyItem().setEnabled(false);
//				getFrame().getScaleFifteenItem().setEnabled(false);
//				getFrame().getScaleTenItem().setEnabled(false);
//				getFrame().getScaleFiveItem().setEnabled(false);
//				getFrame().getScaleFourItem().setEnabled(true);
//				getFrame().getScaleThreeItem().setEnabled(true);
//				
//				getFrame().getScaleOneItem().setSelected(true);
//				getFrame().getParameters().setyScaleFactor(MainFrame.yScale.ONE.getValue());
//			}
//			else if(sigNum >= 8 && sigNum <= 9){
//				getFrame().getScaleTwentyItem().setEnabled(false);
//				getFrame().getScaleFifteenItem().setEnabled(false);
//				getFrame().getScaleTenItem().setEnabled(false);
//				getFrame().getScaleFiveItem().setEnabled(false);
//				getFrame().getScaleFourItem().setEnabled(false);
//				getFrame().getScaleThreeItem().setEnabled(true);
//				
//				getFrame().getScaleOneItem().setSelected(true);
//				getFrame().getParameters().setyScaleFactor(MainFrame.yScale.ONE.getValue());
//			}
//			else {
//				getFrame().getScaleTwentyItem().setEnabled(false);
//				getFrame().getScaleFifteenItem().setEnabled(false);
//				getFrame().getScaleTenItem().setEnabled(false);
//				getFrame().getScaleFiveItem().setEnabled(false);
//				getFrame().getScaleFourItem().setEnabled(false);
//				getFrame().getScaleThreeItem().setEnabled(false);
//				
//				getFrame().getScaleOneItem().setSelected(true);
//				getFrame().getParameters().setyScaleFactor(MainFrame.yScale.ONE.getValue());
//			}
//		}
//	}
}

/**
 * 
 */
package features.output;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import gui.SelectedSignal;

/**
 * @author lsuc
 *
 */
public abstract class ExtractFeaturesController {
	
	protected ArrayList<Features> selectedFeatures;
	protected JFileChooser saveChooser;
	protected JDialog waitDialog;
	protected String outputFileType;
	protected String featuresType;
	
	public abstract void createNewExtractFeaturesController();
	
	public String getOutputFileType() {
		return outputFileType;
	}

	public void setOutputFileType(String outputFileType) {
		this.outputFileType = outputFileType;
	}

	public boolean createSaveChooser(String fileName, FileFilter fileFilter, String extension, boolean append){ 
		if(saveChooser == null){
			saveChooser = new JFileChooser();
			saveChooser.setCurrentDirectory(null);
		}		
		saveChooser.setSelectedFile(new File(fileName + extension));
		
		FileFilter[] oldFilters =  saveChooser.getChoosableFileFilters();
		for (FileFilter filter : oldFilters){
			saveChooser.removeChoosableFileFilter(filter);
		}	
		saveChooser.addChoosableFileFilter(fileFilter);
        
		if(saveChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
			File selectedFile = saveChooser.getSelectedFile();
    		if (selectedFile == null){
				JOptionPane.showMessageDialog(null, "Error while selecting file. Please select another file.", "Loading selected file", JOptionPane.ERROR_MESSAGE);
				return false;
    		}
    		if (selectedFile.exists()== true && !append){
    			int value = JOptionPane.showConfirmDialog(null, "File already exists! If you want to append text to file \n" +"press 'Cancel' and select append option. \n" + "Are you sure you want to override this file? ", "Loading selected file", JOptionPane.YES_NO_CANCEL_OPTION);
    			if(value != JOptionPane.YES_OPTION){
    				return false;
    			}
    		}		
    		saveChooser.setCurrentDirectory(selectedFile);	 
    		createExtractionWorker(selectedFile.getAbsolutePath(), append);
    		return true;
		}
		else {
			return false;
		}
	}
	
	public ArrayList<Features> getSelectedFeatures() {
		return selectedFeatures;
	}

	
	public void beginFeatureExtraction(boolean append){
//		selectedFeatures.get(0).setSignals(signals);		
		createSaveChooser(featuresType, new CsvFileFilter(){}, ".csv", append);
	}
	
	public void createExtractionWorker(String fileName, boolean append){
		createWaitDialog();
		new ExtractFeaturesWorker(fileName, this, append).execute();
	}
	
	abstract public void extractFeatures();
	
	public JDialog getWaitDialog() {
		return waitDialog;
	}

	public void setWaitDialog(JDialog waitDialog) {
		this.waitDialog = waitDialog;
	}
	
	public void createWaitDialog(){
		//TODO dodati kursor za cekanje
        waitDialog = new JDialog();
        waitDialog.setUndecorated(true);
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Extracting features...Please wait...");
        panel.add(label);
        waitDialog.add(panel);
        waitDialog.pack();
        waitDialog.setLocationRelativeTo(null);
        waitDialog.setVisible(true);
	}
	public String CSV = "CSV";
	public String DATA_MINING_CSV = "DATA_MINING_CSV";
	public static String UNIVARIATE_FEATURES = "UnivariateFeatures";
	public static String MULTIVARIATE_FEATURES = "MultivariateFeatures";
	public static String MIXED_FEATURES = "MixedFeatures";
}

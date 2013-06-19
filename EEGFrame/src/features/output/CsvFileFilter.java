/**
 * 
 */
package features.output;

import java.io.File;

import javax.swing.filechooser.FileFilter;


/**
 * @author lsuc
 *
 */
abstract class CsvFileFilter extends FileFilter {

	
	public boolean accept(File f){
			
		if (f.isDirectory()){
			return true;
		}
			
		String extension = null;
	    String fileName = f.getName();
	    int index = fileName.lastIndexOf('.');
	    
	    if (index > 0 && index < fileName.length() - 1){
	    	extension = fileName.substring(index + 1).toLowerCase();
	    } 
	    if (extension.equals(null)){
	    	return false;
	    } 
	    if (extension.equals("csv")){
	    	return true;
	    } 
	    return false;
	}	
		
	public String getDescription(){
		return "CSV files (*.csv, *.CSV)";
	}	
}

package gui;

import java.io.File;

abstract class SaveFileFilter extends javax.swing.filechooser.FileFilter {
	
	public String getDescription(){
		return "Text Documents (*.txt)";
	}	
	
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
        
        if (extension.equals("txt")){
        	return true;
        } 
        
        return false;
	}	
}

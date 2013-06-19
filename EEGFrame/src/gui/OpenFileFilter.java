/**
 * 
 */
package gui;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * @author lsuc
 *
 */
public abstract class OpenFileFilter extends FileFilter {

	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
	 */
	@Override
	public boolean accept(File f) {
		if (f.isDirectory()){
			System.out.println("File is : " + f);
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
        
        if (extension.equals("edf") || extension.equals("rec")){
        	return true;
        } 
        
        return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#getDescription()
	 */
	@Override
	public String getDescription() {
		return "EDF files (*.edf, *.EDF, *.rec, *.REC)";
	}

}

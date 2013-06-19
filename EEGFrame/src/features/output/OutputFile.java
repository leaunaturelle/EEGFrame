/**
 * 
 */
package features.output;

import java.io.IOException;
import java.util.ArrayList;


/**
 * @author lsuc
 *
 */
public abstract class OutputFile {

	protected String filePath;
	
	
	public abstract void writeToFile(ArrayList<Features> features, boolean append) throws IOException;
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}

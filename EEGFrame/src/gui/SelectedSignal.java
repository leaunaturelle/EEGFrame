/**
 * 
 */
package gui;

import dataHandling.InputFile;

/**
 * @author lsuc
 *
 */
public class SelectedSignal {
	
	private int signalIndex;
	private InputFile file;
	private String signalLabel;
	private int indexInList;
	
	public SelectedSignal(int signalIndex, String signalLabel, InputFile file, int indexInList){
		this.signalIndex = signalIndex;
		this.signalLabel = signalLabel;
		this.setFile(file);
		this.indexInList = indexInList;
	}
	
	public String toString(){
		return signalLabel;		
	}
	
	public int getSignalIndex() {
		return signalIndex;
	}
	public void setSignalIndex(int signalIndex) {
		this.signalIndex = signalIndex;
	}

	public String getSignalLabel() {
		return signalLabel;
	}

	public void setSignalLabel(String signalLabel) {
		this.signalLabel = signalLabel;
	}

	public int getIndexInList() {
		return indexInList;
	}

	public void setIndexInList(int indexInList) {
		this.indexInList = indexInList;
	}

	public InputFile getFile() {
		return file;
	}

	public void setFile(InputFile file) {
		this.file = file;
	}
}

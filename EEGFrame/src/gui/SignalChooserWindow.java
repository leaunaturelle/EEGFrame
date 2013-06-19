/**
 * 
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import dataHandling.InputFile;

/**
 * @author lsuc
 *
 */
public class SignalChooserWindow extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Controller controller;
	private static SignalChooserWindow signalChooser;
	private JList signalsToSelect;
	private JList signalsToView;
	private JComboBox fileList;
	
	private SignalChooserWindow( Controller controller){
		EEGFrameMain.checkOnEventDispatchThread();
		this.controller = controller;
		this.setTitle ("Select signals");
		this.setPreferredSize(new Dimension(520, 400));
		this.setLocationRelativeTo(null);		
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.createLeftPanel();
		this.createRightPanel();
		this.createCenterPanel();	
	    this.setResizable(false);
	    this.setModal(true);
	    this.pack();
	}
	
	public void createLeftPanel(){
		
		JPanel panelLeft = new JPanel();
		panelLeft.setPreferredSize(new Dimension(200,400));
		panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));
		this.add(BorderLayout.WEST, panelLeft);
		
		panelLeft.add(Box.createVerticalGlue());
		
		fileList = new JComboBox();
		fileList.setMaximumRowCount(3);
		fileList.setBorder(BorderFactory.createTitledBorder("Select file: "));
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
		//TODO - add separators to JComboBox and Font, using the ListCellRenderer
		fileList.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				 InputFile file = (InputFile) ((JComboBox)e.getSource()).getSelectedItem();
				 controller.updateSignalsToSelect(file);
			}
		});

		panelLeft.add(fileList);
		panelLeft.add(Box.createVerticalGlue());

		this.signalsToSelect = new JList(new DefaultListModel());		
		this.signalsToSelect.setVisibleRowCount(10);
		this.signalsToSelect.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		this.signalsToSelect.setCellRenderer(new DefaultListCellRenderer() {
	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				SelectedSignal toBeRendered = (SelectedSignal) value;
				setText(toBeRendered.getSignalIndex() +". " +toBeRendered.getSignalLabel());
				if(isSelected){
					setBackground(Color.LIGHT_GRAY);
				}
				else{
					setBackground(null);
				}
				return this;
	        }
	    });
		
	    this.signalsToSelect.addListSelectionListener(new ListSelectionListener(){
	    										public void valueChanged(ListSelectionEvent event){
	    											if (!event.getValueIsAdjusting()){
	    												for(Object o : signalsToSelect.getSelectedValues()){									    					
									    					DefaultListModel signalsToViewModel = (DefaultListModel)signalsToView.getModel();									    				
									    					if(!signalsToViewModel.contains(o)){ 
									    						signalsToViewModel.addElement(o);
									    					} 
									    				}
								    				}
								    			}
		});	   
		JButton selectAllLeftButton = new JButton("Select All");
		selectAllLeftButton.addActionListener(new ActionListener(){
						    			public void actionPerformed(ActionEvent e){
						    				int start = 0;
						    			    int end = signalsToSelect.getModel().getSize() - 1;
						    			    if (end >= 0) {
						    			    	signalsToSelect.setSelectionInterval(start, end);
						    			    }
						    			}
		});		
		JScrollPane paneLeft = new JScrollPane(signalsToSelect);
		panelLeft.add(paneLeft);		
		JPanel buttonPanel = new JPanel();
		panelLeft.add(buttonPanel);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(Box.createVerticalGlue());
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(selectAllLeftButton);
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(Box.createVerticalGlue());
	}
	
	
	public void createRightPanel(){
		
		JPanel panelRight = new JPanel();
	    panelRight.setPreferredSize(new Dimension(200,400));
	    panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.Y_AXIS));
		this.add(BorderLayout.EAST, panelRight);
		panelRight.add(Box.createVerticalGlue());
		panelRight.add(Box.createVerticalGlue());
		JPanel labelPanel = new JPanel();
		JLabel label = new JLabel("Signals for visualization: ");	
		label.setHorizontalAlignment(JLabel.LEFT);
		label.setPreferredSize(new Dimension(200, 50));
		labelPanel.add(label);
		panelRight.add(labelPanel);
		panelRight.add(Box.createVerticalGlue());
		this.signalsToView = new JList(new DefaultListModel());
	    this.signalsToView .setVisibleRowCount(10);
	    this.signalsToView.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	    this.signalsToView.setCellRenderer(new DefaultListCellRenderer() {
	        /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
	            SelectedSignal toBeRendered = (SelectedSignal) value;	            
	        	setText(toBeRendered.getSignalIndex() +". " +toBeRendered.getSignalLabel());
				if(isSelected){
					setBackground(Color.LIGHT_GRAY);
				}
				else{
					setBackground(null);
				}
				return this;
	        }
	    });
	    
		JButton okButton = new JButton("OK");
	    okButton.addActionListener(new ActionListener(){
	    							public void actionPerformed(ActionEvent e){
											ArrayList<SelectedSignal> signalsForViewing = new ArrayList<SelectedSignal>();
						    				for(int i = 0; i < signalsToView.getModel().getSize(); i++){
						    					SelectedSignal signal = (SelectedSignal) signalsToView.getModel().getElementAt(i);
						    					signalsForViewing.add(signal);	
						       				}
						    				controller.updateMenu(signalsForViewing);
						    		}
	    });	    
	    JButton selectAllRightButton = new JButton("Select All");
		selectAllRightButton.addActionListener(new ActionListener(){
						    					public void actionPerformed(ActionEvent e){
						    						int start = 0;
						    						int end = signalsToView.getModel().getSize() - 1;
						    						if (end >= 0) {
						    							signalsToView.setSelectionInterval(start, end);
						    						}
						    					}
		});	  
		JScrollPane paneRight = new JScrollPane(signalsToView);
		panelRight.add(paneRight);		
		JPanel buttonPanel = new JPanel();
		panelRight.add(buttonPanel);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(Box.createVerticalGlue());
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(selectAllRightButton);
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(okButton);
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(Box.createVerticalGlue());
	}
	
	public void createCenterPanel(){
		
		JButton removeButton = new JButton("<< Remove");
		
		removeButton.addActionListener(new ActionListener(){
							    		public void actionPerformed(ActionEvent e){
							    			for(Object o : signalsToView.getSelectedValues()){	
							    				DefaultListModel signalsToViewModel = (DefaultListModel)(signalsToView.getModel());
							    				signalsToViewModel.removeElement(o);
						    					DefaultListModel signalsToSelectModel = (DefaultListModel)(signalsToSelect.getModel());		
						    					if(signalsToSelectModel.contains(o)){ 
						    						SelectedSignal signal = (SelectedSignal) o;
						    						signalsToSelect.removeSelectionInterval(signal.getIndexInList(), signal.getIndexInList());
						    					} 
							    			}
							    		}		
		});
		
	    JPanel centerPanel = new JPanel();
	    this.add(BorderLayout.CENTER, centerPanel);
	    centerPanel.setPreferredSize(new Dimension(120,400));
	    centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
	    centerPanel.add(Box.createVerticalGlue());
	    centerPanel.add(Box.createHorizontalGlue());
	    centerPanel.add(removeButton);
	    centerPanel.add(Box.createHorizontalGlue());
	    centerPanel.add(Box.createVerticalGlue());
	    removeButton.setAlignmentX(CENTER_ALIGNMENT);
	    removeButton.setAlignmentY(CENTER_ALIGNMENT);
	}
	
	public JList getSignalsToSelect() {
		return signalsToSelect;
	}
	
	public void setSignalsToSelect(SelectedSignal[] signalsToSelect) {
		DefaultListModel signalsToSelectModel = (DefaultListModel) this.signalsToSelect.getModel();
		signalsToSelectModel.clear();
		for(int i = 0; i < signalsToSelect.length; i++){
			signalsToSelectModel.addElement(signalsToSelect[i])	;
		}
	}
	
	public void setFileList(ArrayList<InputFile> list){
		InputFile[] files = list.toArray(new InputFile[list.size()]);
		fileList.setModel(new DefaultComboBoxModel(files));
		fileList.setSelectedIndex(files.length-1);	
	}

	public JList getSignalsToView() {
		return signalsToView;
	}

	public void setSignalsToView(JList signalsToView) {
		this.signalsToView = signalsToView;
	}
	
	public static synchronized SignalChooserWindow getInstance(Controller controller){
		EEGFrameMain.checkOnEventDispatchThread();
		if(signalChooser == null){
			signalChooser = new SignalChooserWindow(controller);
		}
		return signalChooser;
	}

	public JComboBox getFileList() {
		return fileList;
	}

	public void setFileList(JComboBox fileList) {
		this.fileList = fileList;
	}
}

/**
 * 
 */
package gui;



import features.output.ExtractMixedFeaturesController;
import features.output.ExtractMultivariateFeaturesController;
import features.output.ExtractUnivariateFeaturesController;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 * @author lsuc
 *op
 */
public class EEGFrameMain {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		        createAndShowGUI();
		    }
		});
	}
	
	public static void createAndShowGUI(){
		JFrame frame = new JFrame("EEGFrame");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		ViewingParameters parameters = new ViewingParameters();
		SignaViewingPanel panel = new SignaViewingPanel(parameters);
		JScrollPane scrollPane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.getHorizontalScrollBar().setUnitIncrement(100);
		scrollPane.setViewportView(panel);
		
	
		frame.getContentPane().add(BorderLayout.CENTER, scrollPane);
		ExtractUnivariateFeaturesController extractUnivariateFeaturesController = new ExtractUnivariateFeaturesController();
		ExtractMultivariateFeaturesController extractMultivariateFeaturesController = new ExtractMultivariateFeaturesController();
		ExtractMixedFeaturesController extractMixedFeaturesController = new ExtractMixedFeaturesController(extractUnivariateFeaturesController, extractMultivariateFeaturesController);
		extractUnivariateFeaturesController.setExtractMixedFeaturesController(extractMixedFeaturesController);
		extractMultivariateFeaturesController.setExtractMixedFeaturesController(extractMixedFeaturesController);
		PanelController pc = new PanelController(frame, panel, extractMixedFeaturesController);		
		scrollPane.getHorizontalScrollBar().addAdjustmentListener(pc);
		Controller c = new Controller(frame, pc);
		Map<String, ActionListener> actionListeners = createActionListeners(c, pc, extractMixedFeaturesController);
		JMenuBar menu = createMainMenu(actionListeners, pc, c);
		frame.setJMenuBar(menu);
		frame.pack();
		c.disableMenus(menu);
		frame.setVisible(true);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
	}
	
	public static JMenuBar createMainMenu(Map<String, ActionListener> actionListeners, final PanelController pc, final Controller c){
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.PINK);
		menuBar.setPreferredSize(new Dimension(50,30));
		
		JMenu fileMenu = new JMenu("File");
		fileMenu.setName(FILE_MENU);
		JMenuItem openMenuItem = new JMenuItem("Open...");
		openMenuItem.addActionListener(actionListeners.get(OPEN_FILE_ACTION));	
		fileMenu.add(openMenuItem);
		fileMenu.addSeparator();	
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(actionListeners.get(EXIT_ACTION));
		fileMenu.add(exitMenuItem);

		JMenu signalsMenu = new JMenu("Display"); 
		signalsMenu.setName(SIGNALS_MENU);
		JMenuItem addRemoveMenuItem = new JMenuItem("Add/Remove signals");
		addRemoveMenuItem.addActionListener(actionListeners.get(ADD_REMOVE_SIGNALS_ACTION));
		signalsMenu.add(addRemoveMenuItem);
		signalsMenu.addSeparator();
		JMenuItem clearAllMenuItem = new JMenuItem("Clear all");
		clearAllMenuItem.addActionListener(actionListeners.get(CLEAR_ALL_ACTION));
		signalsMenu.add(clearAllMenuItem);
		
		JMenu showMenu = new JMenu("Show");	
		showMenu.setName(SHOW_MENU);
		JRadioButtonMenuItem oneSecPerDisplayMenuItem = new JRadioButtonMenuItem("1 Sec/Display");
		oneSecPerDisplayMenuItem.addActionListener(actionListeners.get(SHOW_ONE_SEC_PER_DISPLAY_ACTION));
		showMenu.add(oneSecPerDisplayMenuItem);		
		JRadioButtonMenuItem fiveSecPerDisplayMenuItem= new JRadioButtonMenuItem("5 Sec/Display");
		fiveSecPerDisplayMenuItem.addActionListener(actionListeners.get(SHOW_FIVE_SEC_PER_DISPLAY_ACTION));
		showMenu.add(fiveSecPerDisplayMenuItem);		
		JRadioButtonMenuItem tenSecPerDisplayMenuItem = new JRadioButtonMenuItem("10 Sec/Display");		
		tenSecPerDisplayMenuItem.setSelected(true);
		tenSecPerDisplayMenuItem.addActionListener(actionListeners.get(SHOW_TEN_SEC_PER_DISPLAY_ACTION));
		showMenu.add(tenSecPerDisplayMenuItem);		
		JRadioButtonMenuItem thirtySecPerDisplayMenuItem = new JRadioButtonMenuItem("30 Sec/Display");		
		thirtySecPerDisplayMenuItem.addActionListener(actionListeners.get(SHOW_THIRTY_SEC_PER_DISPLAY_ACTION));
		showMenu.add(thirtySecPerDisplayMenuItem);		
		JRadioButtonMenuItem oneMinPerDisplayMenuItem = new JRadioButtonMenuItem("1 Min/Display");
		oneMinPerDisplayMenuItem.addActionListener(actionListeners.get(SHOW_ONE_MIN_PER_DISPLAY_ACTION));
		showMenu.add(oneMinPerDisplayMenuItem);				
		JRadioButtonMenuItem thirtyMinPerDisplayMenuItem = new JRadioButtonMenuItem("30 Min/Display");	
		thirtyMinPerDisplayMenuItem.addActionListener(actionListeners.get(SHOW_THIRTY_MIN_PER_DISPLAY_ACTION));
		showMenu.add(thirtyMinPerDisplayMenuItem);		
		JRadioButtonMenuItem oneHourPerDisplayMenuItem = new JRadioButtonMenuItem("1 Hour/Display");		
		oneHourPerDisplayMenuItem.addActionListener(actionListeners.get(SHOW_ONE_HOUR_PER_DISPLAY_ACTION));
		showMenu.add(oneHourPerDisplayMenuItem);		
		JRadioButtonMenuItem wholeRecordingMenuItem = new JRadioButtonMenuItem("Whole Recording");		
		wholeRecordingMenuItem.addActionListener(actionListeners.get(SHOW_WHOLE_RECORDING_ACTION));
		showMenu.add(wholeRecordingMenuItem);
		
		ButtonGroup showMenuGroup = new ButtonGroup();
		showMenuGroup.add(oneSecPerDisplayMenuItem);
		showMenuGroup.add(fiveSecPerDisplayMenuItem);
		showMenuGroup.add(tenSecPerDisplayMenuItem);
		showMenuGroup.add(thirtySecPerDisplayMenuItem);
		showMenuGroup.add(oneMinPerDisplayMenuItem);
		showMenuGroup.add(thirtyMinPerDisplayMenuItem);
		showMenuGroup.add(oneHourPerDisplayMenuItem);
		showMenuGroup.add(wholeRecordingMenuItem);		
		
	
		JMenu amplitudeMenu = new JMenu("Amplitude");	
		amplitudeMenu.setName(AMPLITUDE_MENU);
		JMenuItem oneThousandItem = new JMenuItem("1000");		
		oneThousandItem.addActionListener(actionListeners.get(ONE_THOUSAND_AMPLITUDE_ACTION));
		amplitudeMenu.add(oneThousandItem);		
		JMenuItem fiveHundredItem = new JMenuItem("500");
		fiveHundredItem.addActionListener(actionListeners.get(FIVE_HUNDRED_AMPLITUDE_ACTION));		
		amplitudeMenu.add(fiveHundredItem);		
		JMenuItem twoHundredItem = new JMenuItem("200");
		twoHundredItem.addActionListener(actionListeners.get(TWO_HUNDRED_AMPLITUDE_ACTION));
		amplitudeMenu.add(twoHundredItem);				
		JMenuItem oneHundredItem = new JMenuItem("100");
		oneHundredItem.addActionListener(actionListeners.get(ONE_HUNDRED_AMPLITUDE_ACTION));
		amplitudeMenu.add(oneHundredItem);
		JMenuItem fiftyItem = new JMenuItem("50");
		fiftyItem.addActionListener(actionListeners.get(FIFTY_AMPLITUDE_ACTION));
		amplitudeMenu.add(fiftyItem);			
		JMenuItem twentyItem = new JMenuItem("20");
		twentyItem.addActionListener(actionListeners.get(TWENTY_AMPLITUDE_ACTION));
		amplitudeMenu.add(twentyItem);		
		JMenuItem tenItem = new JMenuItem("10");
		tenItem.addActionListener(actionListeners.get(TEN_AMPLITUDE_ACTION));
		amplitudeMenu.add(tenItem);		
		JMenuItem oneItem = new JMenuItem("1");
		oneItem.addActionListener(actionListeners.get(ONE_AMPLITUDE_ACTION));
		amplitudeMenu.add(oneItem);
		JMenuItem halfItem = new JMenuItem("0.5");
		halfItem.addActionListener(actionListeners.get(HALF_AMPLITUDE_ACTION));
		amplitudeMenu.add(halfItem);
		amplitudeMenu.addSeparator();
		JMenuItem amplitudeCustomItem = new JMenuItem("Custom");
		amplitudeCustomItem.setName(CUSTOM_AMPLITUDE_MENU_ITEM);
		amplitudeCustomItem.addActionListener(actionListeners.get(AMPLITUDE_CUSTOM_ACTION));
		amplitudeMenu.add(amplitudeCustomItem);
		
		JMenu yScaleFactorMenu = new JMenu("y scale");
		yScaleFactorMenu.setName(Y_SCALE_FACTOR_MENU);
		JRadioButtonMenuItem scaleOneItem = new JRadioButtonMenuItem("1");
		scaleOneItem.addActionListener(actionListeners.get(SCALE_ONE_ACTION));
		yScaleFactorMenu.add(scaleOneItem);		
		JRadioButtonMenuItem scaleTwoItem = new JRadioButtonMenuItem("2");
		scaleTwoItem.addActionListener(actionListeners.get(SCALE_TWO_ACTION));
		yScaleFactorMenu.add(scaleTwoItem);		
		JRadioButtonMenuItem scaleThreeItem = new JRadioButtonMenuItem("3");
		scaleThreeItem.addActionListener(actionListeners.get(SCALE_THREE_ACTION));
		yScaleFactorMenu.add(scaleThreeItem);		
		JRadioButtonMenuItem scaleFourItem = new JRadioButtonMenuItem("4");
		scaleFourItem.addActionListener(actionListeners.get(SCALE_FOUR_ACTION));
		yScaleFactorMenu.add(scaleFourItem);		
		JRadioButtonMenuItem scaleFiveItem = new JRadioButtonMenuItem("5");
		scaleFiveItem.addActionListener(actionListeners.get(SCALE_FIVE_ACTION));
		yScaleFactorMenu.add(scaleFiveItem);		
		JRadioButtonMenuItem scaleTenItem = new JRadioButtonMenuItem("10");
		scaleTenItem.addActionListener(actionListeners.get(SCALE_TEN_ACTION));
		yScaleFactorMenu.add(scaleTenItem);		
		JRadioButtonMenuItem scaleFifteenItem = new JRadioButtonMenuItem("15");
		scaleFifteenItem.addActionListener(actionListeners.get(SCALE_FIFTEEN_ACTION));
		yScaleFactorMenu.add(scaleFifteenItem);				
		JRadioButtonMenuItem scaleTwentyItem = new JRadioButtonMenuItem("20");
		scaleTwentyItem.addActionListener(actionListeners.get(SCALE_TWENTY_ACTION));
		yScaleFactorMenu.add(scaleTwentyItem);
		
		JRadioButtonMenuItem invisibleYScaleMenuItem = new JRadioButtonMenuItem(INVISIBLE_Y_SCALE_MENU_ITEM);
		invisibleYScaleMenuItem.setVisible(false);
		yScaleFactorMenu.add(invisibleYScaleMenuItem);
	
		
		ButtonGroup yScaleMenuGroup = new ButtonGroup();
		yScaleMenuGroup.add(scaleOneItem);
		yScaleMenuGroup.add(scaleTwoItem);
		yScaleMenuGroup.add(scaleThreeItem);
		yScaleMenuGroup.add(scaleFourItem);
		yScaleMenuGroup.add(scaleFiveItem);
		yScaleMenuGroup.add(scaleTenItem);
		yScaleMenuGroup.add(scaleFifteenItem);
		yScaleMenuGroup.add(scaleTwentyItem);	
		yScaleMenuGroup.add(invisibleYScaleMenuItem);		
		scaleOneItem.setSelected(true);
		
		yScaleFactorMenu.addSeparator();
		JMenuItem scaleCustomItem = new JMenuItem("Custom");
		scaleCustomItem.setName(CUSTOM_Y_SCALE_MENU_ITEM);
		scaleCustomItem.addActionListener(actionListeners.get(SCALE_CUSTOM_ACTION));
		yScaleFactorMenu.add(scaleCustomItem);
		
	
		JMenu toolsMenu = new JMenu("Tools");	
		toolsMenu.setName(TOOLS_MENU);
		JMenuItem exportSamplesMenuItem = new JMenuItem("Export samples as text");				
		exportSamplesMenuItem.addActionListener(actionListeners.get(EXPORT_SIGNAL_SAMPLES_AS_TEXT_ACTION));
		toolsMenu.add(exportSamplesMenuItem);
		toolsMenu.addSeparator();
		JMenuItem changeSignalColorMenu = new JMenuItem("Change signal color");
		changeSignalColorMenu.addActionListener(actionListeners.get(CHANGE_SIGNAL_COLOR_ACTION));
		toolsMenu.add(changeSignalColorMenu);
		toolsMenu.addSeparator();
		JCheckBox standardView = new JCheckBox("Standard display (0.2sx50uV)");
		standardView.addItemListener(new ItemListener(){
            
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if(e.getStateChange() == ItemEvent.SELECTED){
					pc.setStandardView(true);
					pc.getPanel().getParameters().setAmplitude(50);
					c.disableFileLoadedNoSignalsMenus(c.getFrame().getJMenuBar());
					pc.createPanelParametersWorker();
				
					
                }
				else{
					pc.setStandardView(false);
					pc.getPanel().getParameters().setDefaultParameters();
					c.enableMenus(c.getFrame().getJMenuBar());
					pc.createPanelParametersWorker();
				}
			}
		});
		standardView.addActionListener(actionListeners.get(STANDARD_DISPLAY_ACTION));
		toolsMenu.add(standardView);
		
		JMenu propertiesMenu = new JMenu("Properties");
		propertiesMenu.setName(PROPERTIES_MENU);
		JMenuItem edfHeaderPropertiesItem = new JMenuItem("EDF file");
		edfHeaderPropertiesItem.addActionListener(actionListeners.get(HEADER_PROPERTIES_ACTION));
		propertiesMenu.add(edfHeaderPropertiesItem);
		propertiesMenu.addSeparator();
		JMenuItem signalsPropertiesMenuItem = new JMenuItem("Signals");		
		signalsPropertiesMenuItem.addActionListener(actionListeners.get(SIGNAL_PROPERTIES_ACTION));
		propertiesMenu.add(signalsPropertiesMenuItem);
		
		JMenu extractFeaturesMenu = new JMenu("Extract features");		
		extractFeaturesMenu.setName(EXTRACT_FEATURES_MENU);

		JMenu selectInterval = new JMenu("Select interval");
		JMenuItem selectStartItem = new JMenuItem("Select start point");
		selectStartItem.addActionListener(actionListeners.get(SELECT_START_POINT_ACTION));
		selectInterval.add(selectStartItem);
		JMenuItem selectEndItem = new JMenuItem("Select end point");
		selectEndItem.addActionListener(actionListeners.get(SELECT_END_POINT_ACTION));
		selectInterval.add(selectEndItem);
		extractFeaturesMenu.add(selectInterval);
		JMenuItem univariateFeaturesMenuItem = new JMenuItem("Univariate features");
		univariateFeaturesMenuItem.addActionListener(actionListeners.get(EXTRACT_UNIVARIATE_FEATURES_ACTION));
		extractFeaturesMenu.add(univariateFeaturesMenuItem);
		
		JMenuItem multivariateFeaturesMenuItem = new JMenuItem("Multivariate features");		
		multivariateFeaturesMenuItem.addActionListener(actionListeners.get(EXTRACT_MULTIVARIATE_FEATURES_ACTION));
		extractFeaturesMenu.add(multivariateFeaturesMenuItem);
					
		menuBar.add(fileMenu);
		menuBar.add(signalsMenu);
		menuBar.add(showMenu);
		menuBar.add(amplitudeMenu);
		menuBar.add(yScaleFactorMenu);
		menuBar.add(toolsMenu);
		menuBar.add(propertiesMenu);
		menuBar.add(extractFeaturesMenu);
		
		return menuBar;
	}
	
	public static HashMap<String, ActionListener> createActionListeners(final Controller controller, final PanelController panelController, final ExtractMixedFeaturesController extractFeaturesController){
		checkOnEventDispatchThread();
		
		HashMap<String, ActionListener> actionListeners = new HashMap<String, ActionListener>();
		actionListeners.put(OPEN_FILE_ACTION, 
						new ActionListener() {
							public void actionPerformed(ActionEvent e){
								controller.openFile();
							}
						}
		);
		
		actionListeners.put(EXIT_ACTION, 
						new ActionListener() {
							public void actionPerformed(ActionEvent e){
								int answer = JOptionPane.showConfirmDialog (null, "Are you sure you want to quit?");
								if (answer == JOptionPane.YES_OPTION) {
									System.exit(0);
								}
							}
						}
		);
		
		actionListeners.put(ADD_REMOVE_SIGNALS_ACTION, 
						new ActionListener(){
							public void actionPerformed(ActionEvent e){
								controller.getSignalChooser().setVisible(true);
							}
						}
		);
		
		actionListeners.put(CLEAR_ALL_ACTION, 
						new ActionListener(){
							public void actionPerformed(ActionEvent e){	
								DefaultListModel signalsToViewModel = (DefaultListModel) controller.getSignalChooser().getSignalsToView().getModel();
								signalsToViewModel.clear();
								controller.getSignalChooser().getSignalsToSelect().clearSelection();
								panelController.getPanel().getParameters().clear();
								panelController.createPanelParametersWorker();
								panelController.getPanel().repaint();
							}
						}
		);
		
		actionListeners.put(SHOW_ONE_SEC_PER_DISPLAY_ACTION, 
						new ActionListener(){
							public void actionPerformed(ActionEvent e){
								panelController.getPanel().getParameters().setSecondsPerDisplay(perDisplay.ONE_SECOND.getValue());
								panelController.createPanelParametersWorker();
								panelController.getPanel().repaint();
							}
						}
		);
		
		actionListeners.put(SHOW_FIVE_SEC_PER_DISPLAY_ACTION, 
						new ActionListener(){
							public void actionPerformed(ActionEvent e){
								panelController.getPanel().getParameters().setSecondsPerDisplay(perDisplay.FIVE_SECONDS.getValue());
								panelController.createPanelParametersWorker();
								panelController.getPanel().repaint();
							}
						}
		);
		
		actionListeners.put(SHOW_TEN_SEC_PER_DISPLAY_ACTION, 
						new ActionListener() {
							public void actionPerformed(ActionEvent e){
								panelController.getPanel().getParameters().setSecondsPerDisplay(perDisplay.TEN_SECONDS.getValue());
								panelController.createPanelParametersWorker();
								panelController.getPanel().repaint();
							}
						}
		);
		
		actionListeners.put(SHOW_THIRTY_SEC_PER_DISPLAY_ACTION, 
						new ActionListener() {
							public void actionPerformed(ActionEvent e){
								panelController.getPanel().getParameters().setSecondsPerDisplay(perDisplay.THIRTY_SECONDS.getValue());
								panelController.createPanelParametersWorker();
								panelController.getPanel().repaint();
							}
						}
		);
		
		actionListeners.put(SHOW_ONE_MIN_PER_DISPLAY_ACTION, 
						new ActionListener() {
							public void actionPerformed(ActionEvent e){
								panelController.getPanel().getParameters().setSecondsPerDisplay(perDisplay.ONE_MINUTE.getValue());
								panelController.createPanelParametersWorker();
								panelController.getPanel().repaint();
							}
						}
		);
		
		actionListeners.put(SHOW_THIRTY_MIN_PER_DISPLAY_ACTION, 
						new ActionListener() {
							public void actionPerformed(ActionEvent e){
								panelController.getPanel().getParameters().setSecondsPerDisplay(perDisplay.THIRTY_MINUTES.getValue());
								panelController.createPanelParametersWorker();
								panelController.getPanel().repaint();
							}
						}
		);
		
		actionListeners.put(SHOW_ONE_HOUR_PER_DISPLAY_ACTION, 
						new ActionListener(){
							public void actionPerformed(ActionEvent e){
								panelController.getPanel().getParameters().setSecondsPerDisplay(perDisplay.ONE_HOUR.getValue());
								panelController.createPanelParametersWorker();
								panelController.getPanel().repaint();
							}
						}
		);
		
//		actionListeners.put(SHOW_WHOLE_RECORDING_ACTION, 
//						new ActionListener(){
//							public void actionPerformed(ActionEvent e){							
//								double duration = EdfDataFile.getHeader().getDataRecordsNum() * EdfDataFile.getHeader().getDataRecordDuration(); 
//								getParameters().setSecondsPerDisplay(duration);		
//								
//						}
//		);
		
		actionListeners.put(EXPORT_SIGNAL_SAMPLES_AS_TEXT_ACTION,
				new ActionListener() {
					public void actionPerformed(ActionEvent e){
						DefaultListModel model = (DefaultListModel) controller.getSignalChooser().getSignalsToSelect().getModel();
						ArrayList<SelectedSignal> signals = new ArrayList<SelectedSignal>();
						for(int i = 0; i < model.size(); i++){
							signals.add((SelectedSignal) model.get(i));
						}
						controller.showExportSamplesWindow(signals);
				}
		});
		
		actionListeners.put(HEADER_PROPERTIES_ACTION, 
				new ActionListener(){
					public void actionPerformed(ActionEvent e){	
						controller.showFilePropertiesChooser();
					}
				}
		);

		actionListeners.put(SIGNAL_PROPERTIES_ACTION, 
				new ActionListener(){
					public void actionPerformed(ActionEvent e){	
						controller.showSignalPropertiesChooser();
					}
				}
		);
		

		actionListeners.put(ONE_THOUSAND_AMPLITUDE_ACTION, 
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						panelController.getPanel().getParameters().setAmplitude(amplitude.ONE_THOUSAND.getValue());
						panelController.createPanelParametersWorker();
						panelController.getPanel().repaint();
					}
				}
		);
		actionListeners.put(FIVE_HUNDRED_AMPLITUDE_ACTION, 
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						panelController.getPanel().getParameters().setAmplitude(amplitude.FIVE_HUNDRED.getValue());
						panelController.createPanelParametersWorker();
						panelController.getPanel().repaint();
					}
				}
		);
		actionListeners.put(TWO_HUNDRED_AMPLITUDE_ACTION, 
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						panelController.getPanel().getParameters().setAmplitude(amplitude.TWO_HUNDRED.getValue());
						panelController.createPanelParametersWorker();
						panelController.getPanel().repaint();
					}
				}
		);
		actionListeners.put(ONE_HUNDRED_AMPLITUDE_ACTION, 
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						panelController.getPanel().getParameters().setAmplitude(amplitude.ONE_HUNDRED.getValue());
						panelController.createPanelParametersWorker();
						panelController.getPanel().repaint();
					}
				}
		);
		actionListeners.put(FIFTY_AMPLITUDE_ACTION, 
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						panelController.getPanel().getParameters().setAmplitude(amplitude.FIFTY.getValue());
						panelController.createPanelParametersWorker();
						panelController.getPanel().repaint();
					}
				}
		);
		actionListeners.put(TWENTY_AMPLITUDE_ACTION, 
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						panelController.getPanel().getParameters().setAmplitude(amplitude.TWENTY.getValue());
						panelController.createPanelParametersWorker();
						panelController.getPanel().repaint();
					}
				}
		);
		actionListeners.put(TEN_AMPLITUDE_ACTION, 
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						panelController.getPanel().getParameters().setAmplitude(amplitude.TEN.getValue());
						panelController.createPanelParametersWorker();
						panelController.getPanel().repaint();
					}
				}
		);
		actionListeners.put(ONE_AMPLITUDE_ACTION, 
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						panelController.getPanel().getParameters().setAmplitude(amplitude.ONE.getValue());
						panelController.createPanelParametersWorker();
						panelController.getPanel().repaint();
					}
		});
		
		actionListeners.put(HALF_AMPLITUDE_ACTION, 
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						panelController.getPanel().getParameters().setAmplitude(amplitude.HALF.getValue());
						panelController.createPanelParametersWorker();
						panelController.getPanel().repaint();
					}
		});
		
		actionListeners.put(AMPLITUDE_CUSTOM_ACTION, 
				new ActionListener(){
					private float lastValue;
					public void actionPerformed(ActionEvent e){
						float amplitude = controller.createCustomDialog(lastValue, AMPLITUDE_CUSTOM_MESSAGE, AMPLITUDE_CUSTOM_TITLE, AMPLITUDE_CUSTOM_WARNING_MESSAGE);
						if(amplitude > 0){
							controller.setCustomAmplitudeText(amplitude);
							lastValue = amplitude;
							panelController.getPanel().getParameters().setAmplitude(amplitude);	
							panelController.createPanelParametersWorker();
							panelController.getPanel().repaint();
						}
					}
				}
		);
		

		actionListeners.put(SCALE_ONE_ACTION, 
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						panelController.getPanel().getParameters().setyScaleFactor(ScaleYAction.SCALE_ONE.getValue());
						panelController.getPanel().repaint();
					}
				}
		);
		
		actionListeners.put(SCALE_TWO_ACTION, 
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						panelController.getPanel().getParameters().setyScaleFactor(ScaleYAction.SCALE_TWO.getValue());
						panelController.getPanel().repaint();
					}
				}
		);
		
		actionListeners.put(SCALE_THREE_ACTION, 
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						panelController.getPanel().getParameters().setyScaleFactor(ScaleYAction.SCALE_THREE.getValue());
						panelController.getPanel().repaint();
					}
				}
		);

		actionListeners.put(SCALE_FOUR_ACTION, 
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						panelController.getPanel().getParameters().setyScaleFactor(ScaleYAction.SCALE_FOUR.getValue());
						panelController.getPanel().repaint();
					}
				}
		);


		actionListeners.put(SCALE_FIVE_ACTION, 
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						panelController.getPanel().getParameters().setyScaleFactor(ScaleYAction.SCALE_FIVE.getValue());
						panelController.getPanel().repaint();
					}
				}
		);

	
		actionListeners.put(SCALE_TEN_ACTION, 
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						panelController.getPanel().getParameters().setyScaleFactor(ScaleYAction.SCALE_TEN.getValue());
						panelController.getPanel().repaint();
					}
				}
		);
		actionListeners.put(SCALE_FIFTEEN_ACTION, 
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						panelController.getPanel().getParameters().setyScaleFactor(ScaleYAction.SCALE_FIFTEEN.getValue());
						panelController.getPanel().repaint();
					}
				}
		);
		
		actionListeners.put(SCALE_TWENTY_ACTION, 
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						panelController.getPanel().getParameters().setyScaleFactor(ScaleYAction.SCALE_TWENTY.getValue());
						panelController.getPanel().repaint();
					}
				}
		);
		
		actionListeners.put(SCALE_CUSTOM_ACTION, 
				new ActionListener(){
					private float lastValue;
					public void actionPerformed(ActionEvent e){
						float scaleFactor = controller.createCustomDialog(lastValue, Y_SCALE_FACTOR_CUSTOM_MESSAGE, Y_SCALE_FACTOR_CUSTOM_TITLE, Y_SCALE_FACTOR_CUSTOM_WARNING_MESSAGE);
						if(scaleFactor > 0){
							controller.clearYScaleFactorSelection();
							controller.setCustomYScaleFactorText(scaleFactor);
							lastValue = scaleFactor;
							panelController.getPanel().getParameters().setyScaleFactor(scaleFactor);	
							panelController.getPanel().repaint();
						}
					}
				}
		);
//		actionListeners.put(STANDARD_DISPLAY_ACTION, 
//				new ActionListener(){
//					public void actionPerformed(ActionEvent e){
//						
//						
////						if(standardView.isSelected()){
////							drawingParameters.setAmplitude(50);
////							drawingParameters.setyScaleFactor(1);
////							amplitudeMenu.setEnabled(false);
////							yScaleFactorMenu.setEnabled(false);
////							drawingPanel.setStandardView(true);
////														
////						}
////						else {
////							drawingPanel.setStandardView(false);
////							amplitudeMenu.setEnabled(true);
////							yScaleFactorMenu.setEnabled(true);
////						}					
////						
////						repaint();
////						
//					}
//					
//				}
//		);
		actionListeners.put(SELECT_START_POINT_ACTION,
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						panelController.setStartPoint(true);
						SignaViewingPanel panel = panelController.getPanel();
						panel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
						panel.setMouseListener(panel.createMouseListener());
						panel.addMouseListener(panel.getMouseListener());
					}
					
		});
		
		actionListeners.put(SELECT_END_POINT_ACTION,
				new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						panelController.setStartPoint(false);
						SignaViewingPanel panel = panelController.getPanel();
						panel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
						panel.setMouseListener(panel.createMouseListener());
						panel.addMouseListener(panel.getMouseListener());
					}
					
		});

		actionListeners.put(EXTRACT_UNIVARIATE_FEATURES_ACTION,
				new ActionListener() {
					public void actionPerformed(ActionEvent e){
						
						extractFeaturesController.getExtractUnivariateFeaturesController().getExtractUnivariateFeaturesWindow();
						SelectedSignal[] signals = new SelectedSignal[panelController.getSignalsForViewing().size()];
						panelController.getSignalsForViewing().toArray(signals);
						extractFeaturesController.getExtractUnivariateFeaturesController().setUnivariateFeaturesSignalsList(signals);
					}
		});
		
		actionListeners.put(EXTRACT_MULTIVARIATE_FEATURES_ACTION,
				new ActionListener() {
					public void actionPerformed(ActionEvent e){
						
						extractFeaturesController.getExtractMultivariateFeaturesController().getExtractMultivariateFeaturesWindow();
						SelectedSignal[] signals = new SelectedSignal[panelController.getSignalsForViewing().size()];
						panelController.getSignalsForViewing().toArray(signals);
						extractFeaturesController.getExtractMultivariateFeaturesController().setMultivariateFeaturesSignalsList(signals);
					}
		});
//		actionListeners.put(EXPORT_MULTIVARIATE_FEATURES_ALL_SIGNALS_ACTION, 
//				new ActionListener() {
//				
//					public void actionPerformed(ActionEvent e){
//						if(signalsMultivariateFeaturesChooser==null){
//							createSignalsMultivariateFeaturesChooser();
//						}
//						((DefaultListModel) signalsMultivariateFeaturesChooser.getSignalsList().getModel()).clear();
//						DefaultListModel leftModel = (DefaultListModel)signalChooser.getLeftList().getModel();
//						ArrayList<Integer> signalsForMultivariateFeaturesExtraction = new ArrayList<Integer>();
//	    				for(int i = 0; i < leftModel.getSize(); i++){
//	    					int signal = SignalChooserWindow.getSignalIndex(leftModel.getElementAt(i).toString());
//	    					signalsForMultivariateFeaturesExtraction.add(signal);	
//	    				}
//	    				
//	    				signalsMultivariateFeaturesChooser.setSignals(signalsForMultivariateFeaturesExtraction);
//	    				signalsMultivariateFeaturesChooser.setVisible(true);															
//				}
//		});
//
//			actionListeners.put(EXPORT_MULTIVARIATE_FEATURES_DISPLAYED_SIGNALS_ACTION,
//					new ActionListener() {
//			
//					public void actionPerformed(ActionEvent e){
//						if(signalsMultivariateFeaturesChooser==null){
//							createSignalsMultivariateFeaturesChooser();
//						}
//						((DefaultListModel) signalsMultivariateFeaturesChooser.getSignalsList().getModel()).clear();
//						DefaultListModel rightModel = (DefaultListModel)signalChooser.getRightList().getModel();
//						ArrayList<Integer> signalsForMultivariateFeaturesExtraction = new ArrayList<Integer>();
//						for(int i = 0; i < rightModel.getSize(); i++){
//							int signal = SignalChooserWindow.getSignalIndex(rightModel.getElementAt(i).toString());
//							signalsForMultivariateFeaturesExtraction.add(signal);	
//						}
//				
//						signalsMultivariateFeaturesChooser.setSignals(signalsForMultivariateFeaturesExtraction);
//						signalsMultivariateFeaturesChooser.setVisible(true);															
//					}
//		});
		actionListeners.put(CHANGE_SIGNAL_COLOR_ACTION, 
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						new SimpleColorChooser(panelController);
					}
				}
);
		return actionListeners;
	}
	public final static void checkOnEventDispatchThread() {
	    if (!SwingUtilities.isEventDispatchThread()) {
	        throw new RuntimeException("This method can only be run on the EDT");
	    }
	}
	public static enum amplitude {		
		 ONE_THOUSAND(1000), 
		 FIVE_HUNDRED(500),
		 TWO_HUNDRED(200),
		 ONE_HUNDRED(100),
		 FIFTY(50),
		 TWENTY(20),
		 TEN(10),
		 ONE(1),	
		 HALF(0.5f);
		 private final float id;
		 amplitude(float id){
			 this.id = id;
		 }
		 public float getValue(){
			 return id; 
		 }
	}
	
	public static enum perDisplay {		
		 ONE_SECOND(1), 
		 FIVE_SECONDS(5),
		 TEN_SECONDS(10),
		 THIRTY_SECONDS(30),
		 ONE_MINUTE(60),
		 THIRTY_MINUTES(1800),
		 ONE_HOUR(3600);		 
		 private final float id;
		 perDisplay(float id){
			 this.id = id;
		 }
		 public float getValue(){
			 return id; 
		 }
	} 
	
	public static enum ScaleYAction {		
		SCALE_ONE(1),
		SCALE_TWO(2),
		SCALE_THREE(3),
		SCALE_FOUR(4),
		SCALE_FIVE(5),
		SCALE_TEN(10),
		SCALE_FIFTEEN(15),
		SCALE_TWENTY(20);		 
		private final float id;
		ScaleYAction(float id){
			this.id = id;
		}
		public float getValue(){
			return id; 
		}
	}
	
	private static final String OPEN_FILE_ACTION = "OPEN_FILE_ACTION";	
	private static final String EXIT_ACTION = "EXIT_ACTION";
	private static final String ADD_REMOVE_SIGNALS_ACTION = "ADD_REMOVE_SIGNALS_ACTION";
	private static final String SHOW_ONE_SEC_PER_DISPLAY_ACTION = "SHOW_ONE_SEC_PER_DISPLAY_ACTION";
	private static final String SHOW_FIVE_SEC_PER_DISPLAY_ACTION = "SHOW_FIVE_SEC_PER_DISPLAY_ACTION";
	private static final String SHOW_TEN_SEC_PER_DISPLAY_ACTION = "SHOW_TEN_SEC_PER_DISPLAY_ACTION";
	private static final String SHOW_THIRTY_SEC_PER_DISPLAY_ACTION = "SHOW_THIRTY_SEC_PER_DISPLAY_ACTION";
	private static final String SHOW_ONE_MIN_PER_DISPLAY_ACTION = "SHOW_ONE_MIN_PER_DISPLAY_ACTION";
	private static final String SHOW_THIRTY_MIN_PER_DISPLAY_ACTION = "SHOW_THIRTY_MIN_PER_DISPLAY_ACTION";
	private static final String SHOW_ONE_HOUR_PER_DISPLAY_ACTION = "SHOW_ONE_HOUR_PER_DISPLAY_ACTION";
	private static final String SHOW_WHOLE_RECORDING_ACTION = "SHOW_WHOLE_RECORDING_ACTION";
	private static final String CLEAR_ALL_ACTION = "CLEAR_ALL_ACTION";
	
	private static final String ONE_THOUSAND_AMPLITUDE_ACTION = "ONE_THOUSAND_AMPLITUDE_ACTION";
	private static final String FIVE_HUNDRED_AMPLITUDE_ACTION = "FIVE_HUNDRED_AMPLITUDE_ACTION";
	private static final String TWO_HUNDRED_AMPLITUDE_ACTION = "TWO_HUNDRED_AMPLITUDE_ACTION";
	private static final String ONE_HUNDRED_AMPLITUDE_ACTION = "ONE_HUNDRED_AMPLITUDE_ACTION";
	private static final String FIFTY_AMPLITUDE_ACTION = "FIFTY_AMPLITUDE_ACTION";
	private static final String TWENTY_AMPLITUDE_ACTION = "TWENTY_AMPLITUDE_ACTION";
	private static final String TEN_AMPLITUDE_ACTION = "TEN_AMPLITUDE_ACTION";
	private static final String ONE_AMPLITUDE_ACTION = "ONE_AMPLITUDE_ACTION";
	private static final String HALF_AMPLITUDE_ACTION = "HALF_AMPLITUDE_ACTION";
	private static final String AMPLITUDE_CUSTOM_ACTION = "HALF_AMPLITUDE_ACTION";
	
	private static final String SCALE_ONE_ACTION = "SCALE_ONE_ACTION";
	private static final String SCALE_TWO_ACTION = "SCALE_TWO_ACTION";
	private static final String SCALE_THREE_ACTION = "SCALE_THREE_ACTION";
	private static final String SCALE_FOUR_ACTION = "SCALE_FOUR_ACTION";
	private static final String SCALE_FIVE_ACTION = "SCALE_FIVE_ACTION";
	private static final String SCALE_TEN_ACTION = "SCALE_TEN_ACTION";
	private static final String SCALE_FIFTEEN_ACTION = "SCALE_FIFTEEN_ACTION";
	private static final String SCALE_TWENTY_ACTION = "SCALE_TWENTY_ACTION";
	private static final String SCALE_CUSTOM_ACTION = "SCALE_CUSTOM_ACTION";
	
	private static final String EXPORT_SIGNAL_SAMPLES_AS_TEXT_ACTION = "EXPORT_SIGNAL_SAMPLES_AS_TEXT_ACTION";
	private static final String HEADER_PROPERTIES_ACTION = "HEADER_PROPERTIES_ACTION";
	private static final String SIGNAL_PROPERTIES_ACTION = "SIGNAL_PROPERTIES_ACTION";
	private static final String STANDARD_DISPLAY_ACTION = "STANDARD_DISPLAY_ACTION";
	
	
	private static final String EXTRACT_UNIVARIATE_FEATURES_ACTION = "EXPORT_UNIVARIATE_FEATURES_DISPLAYED_SIGNALS_ACTION";
	private static final String EXTRACT_MULTIVARIATE_FEATURES_ACTION = "EXPORT_MULTIVARIATE_FEATURES_DISPLAYED_SIGNALS_ACTION";
	
	public static final String FILE_MENU = "FILE_MENU";
	public static final String EXTRACT_FEATURES_MENU = "EXTRACT_FEATURES_MENU";
	public static final String PROPERTIES_MENU = "PROPERTIES_MENU";
	public static final String TOOLS_MENU = "TOOLS_MENU";
	public static final String Y_SCALE_FACTOR_MENU = "Y_SCALE_FACTOR_MENU";
	public static final String AMPLITUDE_MENU = "AMPLITUDE_MENU";
	public static final String SHOW_MENU = "SHOW_MENU";
	public static final String SIGNALS_MENU = "SIGNALS_MENU";
	public static final String INVISIBLE_Y_SCALE_MENU_ITEM = "NONE";
	public static final String CUSTOM_Y_SCALE_MENU_ITEM = "Custom";
	public static final String CUSTOM_AMPLITUDE_MENU_ITEM = "Custom";
	
	public static final String CHANGE_SIGNAL_COLOR_ACTION = "CHANGE_SIGNAL_COLOR_ACTION";
	
	private static final String Y_SCALE_FACTOR_CUSTOM_MESSAGE = "Enter scale factor different from zero: ";
	private static final String Y_SCALE_FACTOR_CUSTOM_TITLE = "Customized scale factor";
	private static final String Y_SCALE_FACTOR_CUSTOM_WARNING_MESSAGE = "Input needs to be a number!";
	
	private static final String AMPLITUDE_CUSTOM_MESSAGE = "Enter amplitude value different from zero: ";
	private static final String AMPLITUDE_CUSTOM_TITLE = "Customized amplitude";
	private static final String AMPLITUDE_CUSTOM_WARNING_MESSAGE = "Input needs to be a number!";
	
	private static final String SELECT_START_POINT_ACTION = "SELECT_START_POINT_ACTION";
	private static final String SELECT_END_POINT_ACTION = "SELECT_END_POINT_ACTION";
	
	
}
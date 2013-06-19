/**
 * 
 */
package gui;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * @author lsuc
 *
 */
public class SimpleColorChooser extends JFrame implements ChangeListener {

		/**
	 * 
	 */
	private static final long serialVersionUID = 186211270417739586L;
	private JSlider red, green, blue;
	private JLabel labelColor, labelText;
		
		public SimpleColorChooser(final PanelController pc) {
			super("Color chooser");
			
			JPanel p = new JPanel(new GridLayout(5,1));
			JPanel twoLabel = new JPanel(new GridLayout(1,2));
			labelColor = new JLabel(" ");
			labelColor.setOpaque(true);
			twoLabel.add(labelColor);
			labelText = new JLabel("");
			labelText.setHorizontalTextPosition(SwingConstants.CENTER);
			labelText.setHorizontalAlignment(SwingConstants.CENTER);
			twoLabel.add(labelText);
			twoLabel.add(labelText);
			twoLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			p.add(twoLabel);
			
			red = new JSlider(0, 255, 125);
			red.setForeground(Color.RED);
			red.setMajorTickSpacing(50);
			red.setPaintLabels(true);
			red.setPaintTicks(true);
			p.add(red);
			green = new JSlider(0, 255, 125);
			green.setForeground(Color.GREEN);
			green.setMajorTickSpacing(50);
			green.setPaintLabels(true);
			green.setPaintTicks(true);
			p.add(green);
			blue = new JSlider(0, 255, 125);
			blue.setForeground(Color.BLUE);
			blue.setMajorTickSpacing(50);
			blue.setPaintLabels(true);
			blue.setPaintTicks(true);
			p.add(blue);
			
			red.addChangeListener(this);
			green.addChangeListener(this);
			blue.addChangeListener(this);
			
			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
			JButton okButton = new JButton("OK");
			okButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					pc.getPanel().getParameters().setSignalColor(getSelectedColor());
					pc.getPanel().repaint();
					dispose();
				}
			});
			buttonPanel.add(Box.createHorizontalGlue());
			buttonPanel.add(okButton);
			buttonPanel.add(Box.createHorizontalGlue());
			p.add(buttonPanel);
			
			
			add(p);
			setSize(350, 300);
			setVisible(true);
			stateChanged(null);
		}

		public void stateChanged(ChangeEvent arg0) {
			int r = red.getValue();
			int g = green.getValue();
			int b = blue.getValue();
			labelColor.setBackground(new Color(r, g, b));
			labelText.setText("Red: " + r + "   Green: " + g + "   Blue: " + b);
		}
		
		public Color getSelectedColor(){
			return labelColor.getBackground();
		}
}

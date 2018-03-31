package ser516.project3.server.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ser516.project3.utilities.InputVerifierNumericals;
import ser516.project3.utilities.ServerCommonData;

public class ServerPanelGenerator {

	private static final Font FONT = new Font("Courier New", Font.BOLD, 17);

	/**
	 * This method will initialize the top JPanels of the server application
	 * 
	 * @return the top JPanel
	 */
	public static JPanel createTopPanels() {
		JPanel topPanel = new JPanel();

		topPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		topPanel.setOpaque(false);

		Border titledBorder = new TitledBorder(null, "Graph", TitledBorder.LEADING, TitledBorder.TOP, FONT, null);
		Border marginBorder = BorderFactory.createEmptyBorder(30, 10, 10, 10);

		Border compound = BorderFactory.createCompoundBorder(marginBorder, titledBorder);
		topPanel.setBorder(compound);

		JLabel intervalLabel = new JLabel("Interval (seconds):  ");
		intervalLabel.setHorizontalAlignment(SwingConstants.CENTER);
		intervalLabel.setOpaque(true);

		JTextField intervalInputTextField = new JTextField("1");
		intervalInputTextField.setInputVerifier(new InputVerifierNumericals());
		intervalInputTextField.setBorder(BorderFactory.createLineBorder(Color.black));
		intervalInputTextField.setColumns(3);
		intervalInputTextField.setHorizontalAlignment(SwingConstants.CENTER);
		intervalInputTextField.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
//				updateIntervalInputTextField(intervalInputTextField);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
//				updateIntervalInputTextField(intervalInputTextField);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				updateIntervalInputTextField(intervalInputTextField);
			}

		});

		JButton buttonToggle = new JButton("Start / Stop");

		JCheckBox autoRepeatCheckBox = new JCheckBox("Auto Repeat", false);
		autoRepeatCheckBox.setHorizontalAlignment(SwingConstants.CENTER);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 40;
		topPanel.add(intervalLabel, c);

		buttonToggle.setHorizontalAlignment(SwingConstants.CENTER);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 0;
		c.ipady = 10;
		topPanel.add(intervalInputTextField, c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 2.0;
		c.gridx = 2;
		c.gridy = 0;
		c.ipady = 40;
		c.insets = new Insets(0, 50, 0, 50); // left-right padding
		topPanel.add(buttonToggle, c);

		c.ipady = 10;
		c.insets = new Insets(0, 0, 0, 0); // reset
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 1;

		topPanel.add(Box.createGlue(), c);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 1;
		c.gridy = 1;
		topPanel.add(autoRepeatCheckBox, c);

		return topPanel;

	}

	/**
	 * This method will initialize the second sub panel of the Server window
	 * 
	 * @return the second sub-panel
	 */
	public static Component createConfigurationPanels() {
		JPanel configPanel = new JPanel();
		configPanel.setOpaque(false);
		Border titledBorder = new TitledBorder(null, "Configuration", TitledBorder.LEADING, TitledBorder.TOP, FONT,
				null);
		Border marginBorder = BorderFactory.createEmptyBorder(30, 10, 30, 10);

		Border compound = BorderFactory.createCompoundBorder(marginBorder, titledBorder);
		configPanel.setBorder(compound);

		configPanel.setLayout(new GridBagLayout());
		new GridBagConstraints();

		return configPanel;
	}

	private static void updateIntervalInputTextField(JTextField intervalInputTextField) {
		ServerCommonData.getInstance().setInterval(Integer.parseInt(intervalInputTextField.getText()));
	}

}

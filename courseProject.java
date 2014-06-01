/***********************************************************************
Program Name: courseProject.java
Programmer's Name: Ricky Mesny
Program Description: This is an advanced program that utilizes JTabbedPane. It has many different functions,
						including a volume calculator, customer and contractor information, a measurement
						converter and finally a temperature converter.
 ***********************************************************************/

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;

public class courseProject {
	public static void main(String[] args) {
		new volumeCalculator();
	}
}

class volumeCalculator extends JFrame {
	private static final long serialVersionUID = 1L;

	// Declare variables
	public static final double MILLIMETER = 1;
	public static final double METER = 1000 * MILLIMETER;
	public static final double INCH = 25.4 * MILLIMETER;
	public static final double FOOT = 304.8 * MILLIMETER;
	public static final double YARD = 914.4 * MILLIMETER;

	// Declare decimal format
	private static final DecimalFormat df = new DecimalFormat("#,###.##");
	private static final DecimalFormat df2 = new DecimalFormat("#,###.###");

	// Declare components
	private JTabbedPane tabs;
	private JPanel generalPanel;
	private JPanel optionsPanel;
	private JPanel customerPanel;
	private JPanel contractorPanel;
	private JPanel poolPanel;
	private JPanel hotTubPanel;
	private JPanel tempConPanel;
	private JPanel lengthConPanel;

	// Declare the JTabbedPane components and settings
	public volumeCalculator() {
		super("Enter a company name in Options");
		setSize(360, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		// Call components
		initializeComponents();

		setLocationRelativeTo(null);
		setVisible(true);
	}

	// Create all components of the JTabbedPane
	private void initializeComponents() {
		Container pane = getContentPane();

		// Create new JTabbedPane
		tabs = new JTabbedPane();

		// Instantiate Tabs
		generalTab();
		optionsTab();
		customerTab();
		contractorTab();
		poolsTab();
		hotTubsTab();
		tempConTab();
		lengthConTab();

		// Declare Tabs and their names
		tabs.add("General", generalPanel);
		tabs.add("Options", optionsPanel);
		tabs.add("Customers", customerPanel);
		tabs.add("Contractors", contractorPanel);
		tabs.add("Pools", poolPanel);
		tabs.add("Hot Tubs", hotTubPanel);
		tabs.add("Temp Conv", tempConPanel);
		tabs.add("Length Conv", lengthConPanel);

		// Add tabs to the pane
		pane.add(tabs);
	}

	// Create exit button used in all tabs
	private JButton createExitButton() {
		JButton exitButton = new JButton("Exit");
		exitButton.setMnemonic('x');
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		return exitButton;
	}

	// Create JTextArea used in most tabs
	private JTextArea createMessageArea(int rows, int cols, String text) {
		final JTextArea messageArea = new JTextArea(rows, cols);
		messageArea.setBorder(BorderFactory.createEmptyBorder());
		messageArea.setLineWrap(true);
		messageArea.setWrapStyleWord(true);
		messageArea.setFont(new Font("System", Font.PLAIN, 12));
		messageArea.setText(text);
		messageArea.setBackground(null);
		messageArea.setEnabled(true);
		messageArea.setFocusable(false);
		return messageArea;
	}

	// General Tab
	private void generalTab() {
		// Set layout
		generalPanel = new JPanel(new FlowLayout());

		String date = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
		generalPanel.add(new JLabel("Today's Date: " + date)); // Display the
																// current date
		generalPanel.add(createExitButton());

	}

	// Options Tab
	public void optionsTab() {
		// Set layout
		optionsPanel = new JPanel(new FlowLayout());

		// Add components
		JLabel optionsLabel = new JLabel("Change Company Name:");
		optionsPanel.add(optionsLabel);

		final JTextField newTitle = new JTextField(20);
		optionsPanel.add(newTitle);

		JButton newName = new JButton("Set New Name");
		newName.setMnemonic('S');
		optionsPanel.add(newName);
		optionsPanel.add(createExitButton());

		// Set new title
		newName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setTitle(newTitle.getText());
			}
		});
	}

	// Customer Tab * Redirects to customerPanel
	private void customerTab() {
		customerPanel = createContactPanel("Customer", "customer.txt");
	}

	// Contractor Tab * Redirects to contractorPanel
	private void contractorTab() {
		contractorPanel = createContactPanel("Contractor", "contractor.txt");
	}

	// Pools Tab
	private void poolsTab() {
		// Set Layout
		poolPanel = new JPanel(new FlowLayout());

		// Add components
		poolPanel.add(new JLabel("Enter the pool's length (ft): "));
		final JTextField lengthField = new JTextField(10);
		poolPanel.add(lengthField);

		poolPanel.add(new JLabel("Enter the pool's width (ft): "));
		final JTextField widthField = new JTextField(10);
		poolPanel.add(widthField);

		poolPanel.add(new JLabel("Enter the pool's depth (ft): "));
		final JTextField depthField = new JTextField(10);
		poolPanel.add(depthField);

		JButton calculateVolume = new JButton("Calculate Volume");
		calculateVolume.setMnemonic('C');
		poolPanel.add(calculateVolume);
		poolPanel.add(createExitButton());

		poolPanel.add(new JLabel("The pool's volume is (ft^3): "));
		final JTextField volumeField = new JTextField(10);
		volumeField.setEditable(false);
		poolPanel.add(volumeField);

		final JTextArea messageArea = createMessageArea(2, 20, "");
		poolPanel.add(messageArea);

		// Validate fields
		calculateVolume.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ValidationResult result = validateFields(new JTextField[] {
						lengthField, widthField, depthField });

				String errors = "";
				if (result.filled != 3) {
					errors += "Please fill out all fields! "; // A field is
																// blank
				}

				if (result.valid != 3 && result.filled != result.valid) {
					errors += "Please enter valid numbers!"; // A number is
																// invalid
				}

				if (errors != "") {
					messageArea.setText(errors);
					messageArea.setVisible(true);
				}

				// If fields are valid then calculate volume
				else {
					messageArea.setVisible(false);

					// Declare and call variables
					double length = Double.parseDouble(lengthField.getText());
					double width = Double.parseDouble(widthField.getText());
					double depth = Double.parseDouble(depthField.getText());
					double volume;

					volume = length * width * depth; // Determine volume
					volumeField.setText(df.format(volume)); // Set decimal
															// location
				}
			}
		});
	}

	// Hot Tubs Tab
	private void hotTubsTab() {
		// Set layout
		hotTubPanel = new JPanel(new FlowLayout());

		// Create radio buttons
		final JRadioButton roundTub = new JRadioButton("Round Tub       ");
		final JRadioButton ovalTub = new JRadioButton("Oval Tub       ");

		// Assign button group to allow only one button at a time
		ButtonGroup group = new ButtonGroup();
		group.add(roundTub);
		group.add(ovalTub);

		// Add components
		hotTubPanel.add(roundTub);
		hotTubPanel.add(ovalTub);

		hotTubPanel.add(new JLabel("Enter the tub's length (ft): "));
		final JTextField lengthField = new JTextField(10);
		hotTubPanel.add(lengthField);

		hotTubPanel.add(new JLabel("Enter the tub's width (ft): "));
		final JTextField widthField = new JTextField(10);
		hotTubPanel.add(widthField);

		hotTubPanel.add(new JLabel("Enter the tub's depth (ft): "));
		final JTextField depthField = new JTextField(10);
		hotTubPanel.add(depthField);

		JButton calculateVolume = new JButton("Calculate Volume");
		calculateVolume.setMnemonic('C');
		hotTubPanel.add(calculateVolume);
		hotTubPanel.add(createExitButton());

		hotTubPanel.add(new JLabel("The tub's volume is (ft^3): "));
		final JTextField volumeField = new JTextField(10);
		volumeField.setEditable(false);
		hotTubPanel.add(volumeField);

		final JTextArea messageArea = createMessageArea(1, 25,
				"Width will be set to the same value as length");
		hotTubPanel.add(messageArea); // Round tub only

		calculateVolume.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (roundTub.isSelected()) {
					widthField.setText(lengthField.getText());
				}

				ValidationResult result = validateFields(new JTextField[] {
						lengthField, widthField, depthField });

				String errors = "";
				if (result.filled != 3) {
					errors += "Please fill out all fields! "; // A field is
																// blank
				}

				if (result.valid != 3 && result.filled != result.valid) {
					errors += "Please enter valid numbers!"; // A number is
																// invalid
				}

				if (errors != "") {
					messageArea.setText(errors);
					messageArea.setVisible(true);
				}

				// If fields are valid then calculate volume
				else {
					messageArea.setVisible(false);

					// Declare and call variables
					double length = Double.parseDouble(lengthField.getText());
					double width = Double.parseDouble(widthField.getText());
					double depth = Double.parseDouble(depthField.getText());
					double volume = 0;

					// Check which button is selected
					if (roundTub.isSelected()) {
						volume = (Math.PI * Math.pow((length / 2), 2) * depth); // Round
																				// tub
					}

					else if (ovalTub.isSelected()) {
						volume = (Math.PI * (length / 2) * (width / 2)) * depth; // Oval
																					// Tub
					}

					volumeField.setText(df.format(volume)); // Set decimal
															// location
				}
			}
		});

		// Disable width field and display message for Round Tub
		ActionListener tubsListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == roundTub) {
					widthField.setEnabled(false);
					widthField.setText(lengthField.getText());
					messageArea.setText("Tub's width set to length");
					messageArea.setVisible(true);
				}

				// Enable width field for Oval Tub
				else if (e.getSource() == ovalTub) {
					widthField.setEnabled(true);
					messageArea.setVisible(false);
				}
			}
		};
		roundTub.addActionListener(tubsListener);
		ovalTub.addActionListener(tubsListener);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	// Temperature Calculator Tab
	private void tempConTab() {
		// Set layout
		tempConPanel = new JPanel(new FlowLayout());

		// Add components
		tempConPanel.add(new JLabel("Enter temperature: "));

		final JTextField temperatureField = new JTextField(16);
		tempConPanel.add(temperatureField);

		// Create drop-down list
		final JComboBox optionComboBox = new JComboBox(
				new String[] { "C", "F" });
		tempConPanel.add(optionComboBox);

		tempConPanel.add(new JLabel("Result: "));
		final JTextField resultField = new JTextField(20);
		resultField.setEditable(false);
		tempConPanel.add(resultField);

		final JLabel oppositeLabel = new JLabel("F");
		tempConPanel.add(oppositeLabel);

		JButton convertButton = new JButton("Convert");
		convertButton.setMnemonic('C');
		tempConPanel.add(convertButton);
		tempConPanel.add(createExitButton());

		final JTextArea messageArea = createMessageArea(1, 20,
				"System Messages");
		tempConPanel.add(messageArea);

		// Change temperature label depending on user selection
		optionComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if ("F".equals(e.getItem())) {
						oppositeLabel.setText("C");
					}

					else {
						oppositeLabel.setText("F");
					}
				}
			}
		});

		// Validate JTextField
		convertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ValidationResult result = validateFields(new JTextField[] { temperatureField });
				String errors = "";
				if (result.filled != 1 || result.valid != 1) {
					errors += "Value set to zero";
				}

				if (errors != "") {
					messageArea.setText(errors);
					messageArea.setVisible(true);
					temperatureField.setText("0"); // Set value to zero if blank
				}

				else {
					messageArea.setVisible(false);
				}

				double temperature = Double.parseDouble(temperatureField
						.getText());

				double resultValue = 0;
				if (oppositeLabel.getText().equals("C")) {
					resultValue = (temperature - 32.0) / 9.0 * 5.0; // Convert F
																	// to C
				}

				else if (oppositeLabel.getText().equals("F")) {
					resultValue = ((temperature * 9.0) / 5.0) + 32.0; // Convert
																		// C to
																		// F
				}

				resultField.setText(df2.format(resultValue)); // Set decimal
																// location
			}

		});
	}

	// Create title field for Length Tab
	private JTextField createTitleField(String text, int length) {
		JTextField tf = new JTextField(length);
		tf.setEditable(false);
		tf.setFocusable(false);
		tf.setText(text);
		return tf;
	}

	// Length Tab
	private void lengthConTab() {
		// Set layout
		lengthConPanel = new JPanel(new FlowLayout());

		// Add components
		lengthConPanel.add(createTitleField("Millimeters", 6));
		lengthConPanel.add(createTitleField("Meters", 4));
		lengthConPanel.add(createTitleField("Yards", 4));
		lengthConPanel.add(createTitleField("Feet", 3));
		lengthConPanel.add(createTitleField("Inches", 6));

		final JTextField millimetersField = new JTextField(6);
		final JTextField metersField = new JTextField(4);
		final JTextField yardsField = new JTextField(4);
		final JTextField feetField = new JTextField(3);
		final JTextField inchesField = new JTextField(6);

		lengthConPanel.add(millimetersField);
		lengthConPanel.add(metersField);
		lengthConPanel.add(yardsField);
		lengthConPanel.add(feetField);
		lengthConPanel.add(inchesField);

		JButton convertButton = new JButton("Convert");
		convertButton.setMnemonic('C');

		// Determine length conversions
		convertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Declare and call variables
				double millimeters = convertToDouble(millimetersField.getText());
				double meters = convertToDouble(metersField.getText());
				double yards = convertToDouble(yardsField.getText());
				double feet = convertToDouble(feetField.getText());
				double inches = convertToDouble(inchesField.getText());
				double value = 0;

				if (millimeters != 0) {
					value = millimeters * MILLIMETER;
				}

				else if (meters != 0) {
					value = meters * METER;
				}

				else if (yards != 0) {
					value = yards * YARD;
				}

				else if (feet != 0) {
					value = feet * FOOT;
				}

				else if (inches != 0) {
					value = inches * INCH;
				}

				millimeters = value / MILLIMETER;
				meters = value / METER;
				yards = value / YARD;
				feet = value / FOOT;
				inches = value / INCH;

				// Set decimal location
				millimetersField.setText(df.format(millimeters));
				metersField.setText(df.format(meters));
				yardsField.setText(df.format(yards));
				feetField.setText(df.format(feet));
				inchesField.setText(df.format(inches));
			}

			// Convert user entered String into Double
			private double convertToDouble(String s) {
				try {
					return Double.parseDouble(s);
				}

				catch (Exception e) {
					return 0;
				}
			}
		});

		lengthConPanel.add(convertButton);
		lengthConPanel.add(createExitButton());
	}

	// Check if files exist for Customers and Contractors Tabs
	private String loadDataFromFile(String fileName) {
		String data = "";

		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			while (reader.ready()) {
				data += reader.readLine() + "\n";
			}

			reader.close();
		}

		catch (IOException e) {
		}

		return data;
	}

	// Main JPanel used for both Customers and Contractors Tabs
	private JPanel createContactPanel(final String contactName,
			final String fileName) {
		// Set layout
		JPanel pane = new JPanel(new FlowLayout());

		final JTextArea customerDisplay = new JTextArea(8, 28);
		customerDisplay.setLineWrap(true);
		customerDisplay.setWrapStyleWord(true);
		customerDisplay.setEditable(false);

		// Add components
		pane.add(new JScrollPane(customerDisplay));
		pane.add(createExitButton());

		JButton addCustomerButton = new JButton("Add " + contactName);
		addCustomerButton.setMnemonic('A');
		pane.add(addCustomerButton);

		JButton refreshButton = new JButton("Refresh");
		refreshButton.setMnemonic('R');
		pane.add(refreshButton);

		final JTextArea messageArea = createMessageArea(2, 25, "");
		messageArea.setWrapStyleWord(true);
		pane.add(messageArea);

		// Action listener for Add button
		addCustomerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new EditContact(contactName, fileName);
			}
		});

		// Action listener for Refresh button
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String data = loadDataFromFile(fileName);
				// If data exists display text
				if (data != "") {
					customerDisplay.setText(data);
					customerDisplay.setEnabled(true);
					messageArea.setText("File " + fileName
							+ " exists and can be read from!");
				}
				// If data does not exist display text
				else {
					customerDisplay.setText("Select Add " + contactName
							+ " to add " + contactName.toLowerCase()
							+ "s.  Select Refresh to refresh this pane.");
					customerDisplay.setEnabled(false);
					messageArea.setText("File " + fileName + " does not "
							+ "exist yet! It will be created "
							+ "when you add " + contactName.toLowerCase()
							+ "s!");
				}
			}
		});

		refreshButton.doClick();
		return pane;
	}

	// Check for validation, used in many Tabs
	private class ValidationResult {
		int filled;
		int valid;
	}

	// Validation and error checking used in many Tabs
	private ValidationResult validateFields(JTextField[] fields) {
		ValidationResult result = new ValidationResult();
		for (int i = 0; i < fields.length; i++) {
			JTextField field = fields[i];
			if ((field.getText() != null) && (field.getText().length() > 0)) {
				result.filled++;
			}

			try {
				Double.parseDouble(field.getText());
				field.setBackground(Color.white);
				result.valid++; // When a field is valid
			}

			catch (Exception e) {
				field.setBackground(Color.orange); // When a field is invalid
			}
		}

		return result;
	}

	// Main class for editing Customers and Contractors
	private class EditContact extends JDialog {
		private static final long serialVersionUID = 1L;

		// Declare variables and components
		private final String contactName;
		private final String fileName;
		private File file;
		private JTextField nameField;
		private JTextField addressField;
		private JTextField cityField;

		@SuppressWarnings("rawtypes")
		private JComboBox stateComboBox;
		private JTextField zipField;
		private JTextField phoneField;
		private JTextArea messageArea;

		// Edit contact method used by Customers and Contractors
		public EditContact(String contactName, String fileName) {
			super(volumeCalculator.this, contactName + "s");
			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			setModalityType(ModalityType.APPLICATION_MODAL);
			setSize(400, 300);
			this.contactName = contactName;
			this.fileName = fileName;

			initializeComponents();

			if (openFile()) {
				displayMessage(null); // If file does exist
			}

			else {
				displayMessage("File " + fileName + " does not exist yet! "
						+ "Will be created when you add a "
						+ contactName.toLowerCase() + "!"); // If file does not
															// exist
			}

			setLocationRelativeTo(null);
			setVisible(true);
		}

		// Method to display messages used in Customers and Contractors
		private void displayMessage(String message) {
			if (message != null) {
				messageArea.setVisible(true);
				messageArea.setText(message);
			}

			else {
				messageArea.setVisible(false);
			}
		}

		// Method used to check if file exists
		private boolean openFile() {
			file = new File(fileName);
			return file.exists();
		}

		// Method used to delete file on user input
		private boolean deleteFile() {
			return file.exists() && file.delete();
		}

		// Method used to save data to a file
		private boolean saveToFile() {
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(file,
						true));

				writer.write("Name: " + nameField.getText() + "\n");
				writer.write("Address: " + addressField.getText() + "\n");
				writer.write("City: " + cityField.getText() + "\n");
				writer.write("State: "
						+ stateComboBox.getSelectedItem().toString() + "\n");
				writer.write("ZIP: " + zipField.getText() + "\n");
				writer.write("Phone: " + phoneField.getText() + "\n");
				writer.write("\n");

				writer.close();
				return true; // Saved information successfully

			}

			catch (IOException e) {
				return false; // Error in saving information
			}
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		private void initializeComponents() {
			// Set layout
			Container pane = getContentPane();
			pane.setLayout(new FlowLayout());

			// Add components
			pane.add(new JLabel(contactName + " Name "));
			nameField = new JTextField(25);
			pane.add(nameField);

			pane.add(new JLabel("Address "));
			addressField = new JTextField(28);
			pane.add(addressField);

			pane.add(new JLabel("City "));
			cityField = new JTextField(30);
			pane.add(cityField);

			// Create State drop-down list
			pane.add(new JLabel("State "));
			stateComboBox = new JComboBox(new String[] { "AL", "AK", "AZ",
					"AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL",
					"IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN",
					"MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC",
					"ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX",
					"UT", "VT", "VA", "WA", "WV", "WI", "WY" });
			pane.add(stateComboBox);

			pane.add(new JLabel("ZIP "));
			zipField = new JTextField(5);
			pane.add(zipField);

			pane.add(new JLabel("Phone "));
			phoneField = new JTextField(10);
			pane.add(phoneField);

			JButton addContactButton = new JButton("Add " + this.contactName);
			addContactButton.setMnemonic('A');
			addContactButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (saveToFile()) {
						displayMessage("Customer added!"); // Customer addition
															// successful
					}

					else {
						displayMessage("Error saving file!"); // Customer
																// addition
																// unsuccessful
					}
				}
			});

			pane.add(addContactButton);

			// Close secondary window used to add customers and contractors
			JButton closeButton = new JButton("Close");
			closeButton.setMnemonic('C');
			closeButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
				}
			});

			pane.add(closeButton);

			// Delete file currently being edited
			JButton deleteFileButton = new JButton("Delete File");
			deleteFileButton.setMnemonic('D');
			deleteFileButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (deleteFile()) {
						displayMessage("File " + fileName + " deleted!"); // Display
																			// confirmation
																			// message
					}
				}
			});

			pane.add(deleteFileButton);

			messageArea = createMessageArea(2, 30, "");
			pane.add(messageArea);
		}
	}
}
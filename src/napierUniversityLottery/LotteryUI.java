package napierUniversityLottery;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

/**
 * Resposible for drawing the UI and handling events occured on the UI
 * @author Gabor Buzasi
 *
 */
public class LotteryUI {
	
	private static JPanel panel = new JPanel();
	private static int rowIndexer = 0;
	
	private static JLabel headerLabel = new JLabel();
	private static JButton resetButton = new JButton("Reset Lottery");
	private static JButton drawButton = new JButton("Draw!");
	private static List<JTextField> lstOfInputs = new ArrayList<JTextField>();
	
	private static final DefaultListModel<String> listModel = new DefaultListModel<String>();
	
	public static void start() {
		
		// Creating the frame
		JFrame frame = new JFrame();
		frame.setLayout(new GridBagLayout());
		frame.setPreferredSize(new Dimension(500, 600));
		frame.setTitle("Napier University Lottery");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		GridBagConstraints frameConstraints = new GridBagConstraints();
		
		// Construct panel
		panel.setPreferredSize(new Dimension(450, 550));
		panel.setLayout(new GridBagLayout());
		panel.setBorder(LineBorder.createBlackLineBorder());
		
		// Add panel to frame
		frameConstraints.gridx = 0;
		frameConstraints.gridy = 1;
		frameConstraints.weighty = 1;
		frame.add(panel, frameConstraints);
		
		// Add header message to UI
		addHeaderMessage();
		
		// Add Name input field to UI
		addLabelAndInputPair("Your name: ", 20);
		
		// Add the required amount of input fields for the numbers in a bet
		addLotteryInputFields();
		
		addButtons();
		
		// Add list that will display the results once bet is drawn
		addList();
		
		frame.pack();
		
		frame.setVisible(true);
	}
	
	/**
	 * Adds a label at the top of the UI 
	 * that asks the user to enter the 
	 * chosen numbers for a bet
	 */
	private static void addHeaderMessage() {
		GridBagConstraints headerConstraint = new GridBagConstraints();
		headerConstraint.gridy = rowIndexer;
		headerConstraint.gridwidth = 2;
		headerConstraint.ipady = 30;
		headerLabel.setText("Please enter your bet number " + Lottery.getBetIndexer() + ": ");
		panel.add(headerLabel, headerConstraint);
		rowIndexer++;
		
		GridBagConstraints header2Constraint = new GridBagConstraints();
		header2Constraint.gridy = rowIndexer;
		header2Constraint.gridwidth = 2;
		header2Constraint.ipady = 30;
		JLabel headerLabel2 = new JLabel("There's a maximum of 20 bets you can place.");
		
		panel.add(headerLabel2, header2Constraint);
		rowIndexer++;
	}
	
	/**
	 * Adds all the four buttons that are required
	 * to be able to control the game:
	 * Submit bet, Lucky dip, Draw game, Reset Lottery 
	 */
	private static void addButtons() {
		GridBagConstraints submitConstraint = new GridBagConstraints();
		submitConstraint.gridwidth = 2;
		submitConstraint.gridy = rowIndexer++;
		
		JButton submitButton = new JButton("Submit bet!");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				betSubmitHandler();
			}
		});
		
		JButton luckyButton = new JButton("Lucky dip!");
		luckyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				luckyBetSubmitHandler();
			}
		});
		
		JPanel actionPanel = new JPanel();
		actionPanel.setLayout(new FlowLayout());
		
		actionPanel.add(submitButton);
		actionPanel.add(luckyButton);
		
		panel.add(actionPanel, submitConstraint);
		
		drawButton.setEnabled(false);
		drawButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawGameUI();
			}
		});
		
		resetButton.setEnabled(false);
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Lottery.resetLottery();
				listModel.clear();
				updateBetNumberAndClearInputs();
				resetButton.setEnabled(false);
			}
		});
		
		JPanel drawPanel = new JPanel();
		drawPanel.setLayout(new FlowLayout());
		drawPanel.add(drawButton);
		drawPanel.add(resetButton);
		
		GridBagConstraints drawConstraint = new GridBagConstraints();
		drawConstraint.gridwidth = 2;
		drawConstraint.gridy = rowIndexer++;
		
		panel.add(drawPanel, drawConstraint);
	}
	
	/**
	 * Handles the event when the 'Submit bet' button was clicked
	 */
	private static void betSubmitHandler() {
		if (Lottery.isBetOverLimit()) {
			JOptionPane.showMessageDialog(panel, "You need to reset the lottery first.");
			return;
		}
		
		if (Lottery.addBet(validateInput())) {
			JOptionPane.showMessageDialog(panel, "Bet was successfully added!");
			drawButton.setEnabled(true);
			updateBetNumberAndClearInputs();
			if (Lottery.checkNumberOfBetsForDraw()) {
				drawButton.setEnabled(false);
				JOptionPane.showMessageDialog(panel, "The lottery has been drawn");
			}
		}
	}
	
	/**
	 * Handles the event when the 'Lucky bet' button was clicked
	 */
	private static void luckyBetSubmitHandler() {
		String name = lstOfInputs.get(0).getText();
		if (name.length() > 2) {
			if (Lottery.isMaxNameCountReached(name)) {
				maxNameCountNotification();
				return;
			}

			Bet b = Lottery.luckyBet(name);
			
			if (b == null) {
				JOptionPane.showMessageDialog(panel, "You need to reset the lottery first.");
				return;
			}
			
			JOptionPane.showMessageDialog(panel, "Lucky dip (" + ConversionHelpers.arrayToCommaDelimited(b.getChosenNumbers()) + ") was successfully added!");
			drawButton.setEnabled(true);
			updateBetNumberAndClearInputs();
			if (Lottery.checkNumberOfBetsForDraw()) {
				drawButton.setEnabled(false);
				JOptionPane.showMessageDialog(panel, "The lottery has been drawn");
			}
		}
		else {
			wrongBetterNameNotification();
		}
	}

	/**
	 * Adds a number label and input field for
	 * the amount of inputs allowed per bet
	 */
	private static void addLotteryInputFields() {
		//	Adding as many fields as it is allowed for a bet
		for (int i = 0; i < Lottery.MAX_NUMBERS_PER_BET; i++) {
			addLabelAndInputPair("Number " + (i + 1) + ": ", 20);
		}
	}
	
	/**
	 * Creates a label and an input field pair
	 * for the passed text and length
	 * @param labelText The text to be shown on the label
	 * @param textFieldLength The length of the input should allow
	 */
	private static void addLabelAndInputPair(String labelText, int textFieldLength) {
		// Create label and text field
		JTextField textInput = new JTextField(textFieldLength);
		JLabel textLabel = new JLabel(labelText);
					
		// Create constraints
		GridBagConstraints labelConstraints = new GridBagConstraints();
		GridBagConstraints inputConstraints = new GridBagConstraints();
					
		labelConstraints.gridx = 0;
		labelConstraints.gridy = rowIndexer;
		labelConstraints.ipady = 10;
					
		inputConstraints.gridx = 1;
		inputConstraints.gridy = rowIndexer;
		inputConstraints.ipady = 10;
					
		panel.add(textLabel, labelConstraints);
		panel.add(textInput, inputConstraints);
		
		lstOfInputs.add(textInput);
					
		rowIndexer++;
	}
	
	/**
	 * Adds a list panel to the UI
	 * that will be used to display results
	 */
	private static void addList() {
		JPanel listPanel = new JPanel();
		JList<String> list = new JList<String>(listModel);
		
		listPanel.setLayout(new FlowLayout());
		listPanel.add(list);
		
		GridBagConstraints listConstraint = new GridBagConstraints();
		listConstraint.gridwidth = 2;
		listConstraint.gridy = rowIndexer++;
		
		panel.add(listPanel, listConstraint);
	}
	
	/**
	 * Validates the user entry
	 * @return a Bet if user input was ok
	 */
	private static Bet validateInput() {
		Bet b = new Bet();
		String betterName = lstOfInputs.get(0).getText().trim();
		
		if (betterName.length() > 2) {
			if (Lottery.isMaxNameCountReached(betterName)) {
				maxNameCountNotification();
				return null;
			}
			
			b.setName(betterName);	
		}
		else {
			wrongBetterNameNotification();
			return null;
		}
		
		for (int i = 1; i < lstOfInputs.size(); i++) {
			String input = lstOfInputs.get(i).getText().trim();
			
			if (ConversionHelpers.tryParseInt(input)) {
				int numInput = Integer.parseInt(input);
				
				if (numInput >= 1 && numInput <= 50) {
					int indexIfDuplicate = b.findNumberInBet(numInput);
					if (indexIfDuplicate > -1) {
						duplicateNumberNotification(indexIfDuplicate + 1, i);
						return null;
					}
					else {
						b.addChosenNumber(numInput);	
					}
					
				}
				else {
					wrongInputNotification(i);
					return null;
				}
			}
			else {
				wrongInputNotification(i);
				return null;
			}
		}
		
		return b;
	}

	
	private static void maxNameCountNotification() {
		JOptionPane.showMessageDialog(panel, "Unfortunately, you have already submitted three bets. You can't bet any more.");
	}

	private static void wrongInputNotification(int indexer) {
		JOptionPane.showMessageDialog(panel, "Please enter a number between " + 
											  Lottery.MIN_NUMBER  + " and " + Lottery.MAX_NUMBER + 
											  " for number '" + indexer + "'");
	}
	private static void wrongBetterNameNotification() {
		JOptionPane.showMessageDialog(panel, "Please enter a name with at least 3 characters!");
	}
	private static void duplicateNumberNotification(int duplicateIndex, int currentIndex) {
		JOptionPane.showMessageDialog(panel, "Entries Number '" + duplicateIndex + "' and '" + currentIndex + "' are the same!");
	}
	
	public static void updateBetNumberAndClearInputs() {
		headerLabel.setText("Please enter your bet number " + Lottery.getBetIndexer() + ": ");
		
		for (int i = 1; i < lstOfInputs.size(); i++) {
			lstOfInputs.get(i).setText("");
		}
	}
	
	public static void drawGameUI() {
		String[] data = Lottery.drawGame();
		for (String string : data) {
			listModel.addElement(string);
		}
		
		drawButton.setEnabled(false);
		resetButton.setEnabled(true);
	}
}
